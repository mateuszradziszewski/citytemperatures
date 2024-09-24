package com.kyotutechnology.citytemperatures.core.ports.primary;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AverageTemperatureServicePort {
  CompletableFuture<List<YearlyAvgTemperature>> findYearAvgTemperaturesByCityName(String cityName) throws MeasurementsReadException;
}
