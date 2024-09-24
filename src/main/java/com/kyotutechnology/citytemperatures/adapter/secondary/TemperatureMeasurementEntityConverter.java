package com.kyotutechnology.citytemperatures.adapter.secondary;

import com.kyotutechnology.citytemperatures.core.ports.secondary.TemperatureMeasurementEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
class TemperatureMeasurementEntityConverter {
  private static final String DELIMITER = ";";
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

  TemperatureMeasurementEntity convert(String csvLine) {
    String[] partials = csvLine.split(DELIMITER);
    String cityName = partials[0];
    LocalDateTime measurementDateTime = LocalDateTime.parse(partials[1], FORMATTER);
    double measuredTemperature = Double.parseDouble(partials[2]);
    return new TemperatureMeasurementEntity(cityName, measurementDateTime, measuredTemperature);
  }
}
