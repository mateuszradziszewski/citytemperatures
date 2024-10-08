package com.kyotutechnology.citytemperatures.core.application;

import static java.util.stream.Collectors.toMap;

import com.kyotutechnology.citytemperatures.core.ports.primary.TemperatureServicePort;
import com.kyotutechnology.citytemperatures.core.ports.primary.MeasurementsReadException;
import com.kyotutechnology.citytemperatures.core.ports.primary.YearlyAvgTemperature;
import com.kyotutechnology.citytemperatures.core.ports.secondary.TemperatureMeasurementRepositoryPort;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class TemperatureService implements TemperatureServicePort {
  private final TemperatureMeasurementRepositoryPort repositoryPort;

  @Async
  public CompletableFuture<List<YearlyAvgTemperature>> findYearlyAvgTemperaturesByCityName(String cityName) throws MeasurementsReadException {
    CityAverageTemperaturesConsumer consumer = new CityAverageTemperaturesConsumer(cityName);
    try {
      repositoryPort.consumeAllAsStream(consumer);
      return CompletableFuture.completedFuture(consumer.getAvgTemperatures());
    } catch (IOException e) {
      String message = "Could not read measurements";
      log.warn(message, e);
      throw new MeasurementsReadException(message, e);
    }
  }
}
