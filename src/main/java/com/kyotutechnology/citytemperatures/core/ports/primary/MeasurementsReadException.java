package com.kyotutechnology.citytemperatures.core.ports.primary;

public class MeasurementsReadException extends Exception {
  public MeasurementsReadException(String message, Throwable cause) {
    super(message, cause);
  }
}
