package com.kyotutechnology.citytemperatures.adapter.secondary;

import com.kyotutechnology.citytemperatures.core.ports.secondary.TemperatureMeasurementEntity;
import com.kyotutechnology.citytemperatures.core.ports.secondary.TemperatureMeasurementRepositoryPort;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
class CsvTemperatureMeasurementRepositoryAdapter implements TemperatureMeasurementRepositoryPort {
  private final Path dataFilePath;
  private final TemperatureMeasurementEntityConverter converter;

  public CsvTemperatureMeasurementRepositoryAdapter(@Value("${repository.file.path}") String dataFilePath,
                                                    TemperatureMeasurementEntityConverter converter) {
    this.dataFilePath = Paths.get(dataFilePath);
    this.converter = converter;
  }

  @Override
  public void consumeAllAsStream(Consumer<Stream<TemperatureMeasurementEntity>> measurementsConsumer)
      throws IOException {
    try (Stream<String> csvStream = Files.lines(this.dataFilePath)) {
      measurementsConsumer.accept(csvStream.map(converter::convert));
    }
  }
}
