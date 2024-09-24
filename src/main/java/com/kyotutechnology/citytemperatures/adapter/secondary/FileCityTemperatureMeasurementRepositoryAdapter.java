package com.kyotutechnology.citytemperatures.adapter.secondary;

import com.kyotutechnology.citytemperatures.core.ports.secondary.CityTemperatureMeasurementEntity;
import com.kyotutechnology.citytemperatures.core.ports.secondary.CityTemperatureMeasurementRepositoryPort;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
class FileCityTemperatureMeasurementRepositoryAdapter implements CityTemperatureMeasurementRepositoryPort {
  private final Path dataFilePath;
  private final CityTemperatureMeasurementEntityConverter converter;

  public FileCityTemperatureMeasurementRepositoryAdapter(@Value("${repository.file.path}") String dataFilePath,
                                                         CityTemperatureMeasurementEntityConverter converter) {
    this.dataFilePath = Paths.get(dataFilePath);
    this.converter = converter;
  }

  @Override
  public Stream<CityTemperatureMeasurementEntity> findMeasurementsByCityAsStream(String cityName) throws IOException {
    return Files.lines(this.dataFilePath)
        .map(converter::convert)
        .filter(measurement -> measurement.getCityName().equals(cityName));
  }
}
