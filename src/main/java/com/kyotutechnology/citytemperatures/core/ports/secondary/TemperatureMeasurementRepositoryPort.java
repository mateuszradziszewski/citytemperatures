package com.kyotutechnology.citytemperatures.core.ports.secondary;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface TemperatureMeasurementRepositoryPort {
  void consumeAllAsStream(Consumer<Stream<TemperatureMeasurementEntity>> measurementsConsumer) throws IOException;
}
