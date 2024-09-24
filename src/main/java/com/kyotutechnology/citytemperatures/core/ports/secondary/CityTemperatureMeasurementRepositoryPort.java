package com.kyotutechnology.citytemperatures.core.ports.secondary;

import java.io.IOException;
import java.util.stream.Stream;

public interface CityTemperatureMeasurementRepositoryPort {
  Stream<CityTemperatureMeasurementEntity> findMeasurementsByCityAsStream(String cityName) throws IOException;
}
