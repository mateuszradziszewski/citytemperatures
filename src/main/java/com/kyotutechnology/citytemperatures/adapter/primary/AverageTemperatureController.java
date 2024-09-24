package com.kyotutechnology.citytemperatures.adapter.primary;

import com.kyotutechnology.citytemperatures.core.ports.primary.TemperatureServicePort;
import com.kyotutechnology.citytemperatures.core.ports.primary.MeasurementsReadException;
import com.kyotutechnology.citytemperatures.core.ports.primary.YearlyAvgTemperature;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/average-temperatures")
@RequiredArgsConstructor
class AverageTemperatureController {
  private final TemperatureServicePort servicePort;

  @GetMapping("/{cityName}")
  @ResponseBody
  CompletableFuture<List<YearlyAvgTemperature>> findCityAverageTemperatures(@PathVariable String cityName)
      throws MeasurementsReadException {
    return servicePort.findYearlyAvgTemperaturesByCityName(cityName);
  }

  @ExceptionHandler
  ResponseEntity<ErrorResponseBody> handleException(MeasurementsReadException ex) {
    return ResponseEntity.internalServerError()
        .body(new ErrorResponseBody("MEASUREMENTS_READ_EXCEPTION", ex.getMessage()));
  }

  @Value
  private static class ErrorResponseBody {
    String errorCode;
    String errorMessage;
  }
}
