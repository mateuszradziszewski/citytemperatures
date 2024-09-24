package com.kyotutechnology.citytemperatures.core.ports.primary;

import lombok.Value;

@Value
public class YearlyAvgTemperature {
  int year;
  double averageTemperature;
}
