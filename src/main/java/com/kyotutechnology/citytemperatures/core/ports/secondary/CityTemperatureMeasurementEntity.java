package com.kyotutechnology.citytemperatures.core.ports.secondary;

import java.time.LocalDateTime;
import lombok.Value;

@Value
public class CityTemperatureMeasurementEntity {
  String cityName;
  LocalDateTime measuredAt;
  double temperature;
}
