package com.kyotutechnology.citytemperatures.adapter.secondary;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyotutechnology.citytemperatures.core.ports.secondary.TemperatureMeasurementEntity;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TemperatureMeasurementEntityConverterTest {

  private TemperatureMeasurementEntityConverter converter = new TemperatureMeasurementEntityConverter();

  @Test
  @DisplayName("GIVEN valid CSV line WHEN converting THEN returns valid measurement object")
  void returnsValidMeasurement() {
    TemperatureMeasurementEntity measurement = converter.convert("New York;2023-05-22 12:00:07.123;28.0");

    assertThat(measurement.getCityName()).isEqualTo("New York");
    assertThat(measurement.getMeasuredAt()).isEqualTo(LocalDateTime.of(2023, 5, 22, 12, 0, 7, 123000000));
    assertThat(measurement.getTemperature()).isEqualTo(28.0d);
  }
}