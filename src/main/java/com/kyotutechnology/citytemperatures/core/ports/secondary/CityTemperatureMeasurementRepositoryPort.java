package com.kyotutechnology.citytemperatures.core.ports.secondary;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface CityTemperatureMeasurementRepositoryPort {
  void consumeAllAsStream(Consumer<Stream<CityTemperatureMeasurementEntity>> measurementsConsumer) throws IOException;
}
