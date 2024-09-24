package com.kyotutechnology.citytemperatures.core.application;

import static java.time.temporal.ChronoField.YEAR;
import static java.util.stream.Collectors.toMap;

import com.kyotutechnology.citytemperatures.core.ports.primary.AverageTemperatureServicePort;
import com.kyotutechnology.citytemperatures.core.ports.primary.MeasurementsReadException;
import com.kyotutechnology.citytemperatures.core.ports.primary.YearlyAvgTemperature;
import com.kyotutechnology.citytemperatures.core.ports.secondary.CityTemperatureMeasurementEntity;
import com.kyotutechnology.citytemperatures.core.ports.secondary.CityTemperatureMeasurementRepositoryPort;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class CityTemperatureService implements AverageTemperatureServicePort {
  private final CityTemperatureMeasurementRepositoryPort repositoryPort;

  @Async
  public CompletableFuture<List<YearlyAvgTemperature>> findYearAvgTemperaturesByCityName(String cityName) throws MeasurementsReadException {
    try (Stream<CityTemperatureMeasurementEntity> measurementsStream = repositoryPort.findMeasurementsByCityAsStream(cityName)) {
      List<YearlyAvgTemperature> result = measurementsStream
          .collect(toMap(
              this::getMeasurementYear,
              MeasurementsAverageAccumulator::new,
              MeasurementsAverageAccumulator::merge))
          .entrySet()
          .stream()
          .map(entry -> new YearlyAvgTemperature(entry.getKey(), entry.getValue().getAverageValue()))
          .sorted(Comparator.comparingInt(YearlyAvgTemperature::getYear))
          .toList();
      return CompletableFuture.completedFuture(result);
    } catch (IOException e) {
      String message = "Could not read measurements";
      log.warn(message, e);
      throw new MeasurementsReadException(message, e);
    }
  }

  private int getMeasurementYear(CityTemperatureMeasurementEntity measurement) {
    return measurement.getMeasuredAt().get(YEAR); // TODO the year should be universal (UTC, not local to city). Improvement might be converting LocalDateTime to Instant based on city.
  }

  @Value
  private static class MeasurementsAverageAccumulator {
    int measurementsCount;
    double measurementsSum;

    public MeasurementsAverageAccumulator(CityTemperatureMeasurementEntity initialMeasurement) {
      this.measurementsCount = 1;
      this.measurementsSum = initialMeasurement.getTemperature();
    }

    private MeasurementsAverageAccumulator(MeasurementsAverageAccumulator firstAcc, MeasurementsAverageAccumulator secondAcc) {
      this.measurementsSum = firstAcc.measurementsSum + secondAcc.measurementsSum;
      this.measurementsCount = firstAcc.measurementsCount + secondAcc.measurementsCount;
    }

    static MeasurementsAverageAccumulator merge(MeasurementsAverageAccumulator firstAcc, MeasurementsAverageAccumulator secondAcc) {
      return new MeasurementsAverageAccumulator(firstAcc, secondAcc);
    }

    double getAverageValue() {
      return this.measurementsSum / this.measurementsCount;
    }
  }
}
