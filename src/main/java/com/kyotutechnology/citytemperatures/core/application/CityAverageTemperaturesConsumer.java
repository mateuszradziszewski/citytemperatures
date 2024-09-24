package com.kyotutechnology.citytemperatures.core.application;

import static java.time.temporal.ChronoField.YEAR;
import static java.util.stream.Collectors.toMap;

import com.kyotutechnology.citytemperatures.core.ports.primary.YearlyAvgTemperature;
import com.kyotutechnology.citytemperatures.core.ports.secondary.TemperatureMeasurementEntity;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
class CityAverageTemperaturesConsumer implements Consumer<Stream<TemperatureMeasurementEntity>> {
  private final String cityName;
  @Getter
  private List<YearlyAvgTemperature> avgTemperatures;

  @Override
  public void accept(Stream<TemperatureMeasurementEntity> measurementsStream) {
    this.avgTemperatures = measurementsStream
        .filter(measurement -> measurement.getCityName().equals(cityName))
        .collect(toMap(
            this::getMeasurementYear,
            MeasurementsAverageAccumulator::new,
            MeasurementsAverageAccumulator::merge))
        .entrySet()
        .stream()
        .map(entry -> new YearlyAvgTemperature(entry.getKey(), entry.getValue().getAverageValue()))
        .sorted(Comparator.comparingInt(YearlyAvgTemperature::getYear))
        .toList();
  }

  private int getMeasurementYear(TemperatureMeasurementEntity measurement) {
    // TODO the year should be universal (UTC, not local to city). Improvement might be converting LocalDateTime to Instant based on city.
    return measurement.getMeasuredAt().get(YEAR);
  }

  @Value
  private static class MeasurementsAverageAccumulator {
    int measurementsCount;
    double measurementsSum;

    public MeasurementsAverageAccumulator(TemperatureMeasurementEntity initialMeasurement) {
      this.measurementsCount = 1;
      this.measurementsSum = initialMeasurement.getTemperature();
    }

    private MeasurementsAverageAccumulator(MeasurementsAverageAccumulator firstAcc,
                                           MeasurementsAverageAccumulator secondAcc) {
      this.measurementsSum = firstAcc.measurementsSum + secondAcc.measurementsSum;
      this.measurementsCount = firstAcc.measurementsCount + secondAcc.measurementsCount;
    }

    static MeasurementsAverageAccumulator merge(MeasurementsAverageAccumulator firstAcc,
                                                MeasurementsAverageAccumulator secondAcc) {
      return new MeasurementsAverageAccumulator(firstAcc, secondAcc);
    }

    double getAverageValue() {
      return this.measurementsSum / this.measurementsCount;
    }
  }
}
