package com.ultracar.ultracarweb.controller.dto;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Builder
@Getter
public class ApiErrorDTO {

  private HttpStatus code;
  private String message;
  @Default
  private Instant timestamp = Instant.now();
}
