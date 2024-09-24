package com.kyotutechnology.citytemperatures.core.ports.primary;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TemperatureServicePort {
  CompletableFuture<List<YearlyAvgTemperature>> findYearlyAvgTemperaturesByCityName(String cityName) throws MeasurementsReadException;
}
