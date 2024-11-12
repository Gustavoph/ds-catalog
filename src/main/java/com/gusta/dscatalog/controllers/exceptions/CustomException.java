package com.gusta.dscatalog.controllers.exceptions;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomException {
  public Instant timestamp;
  public Integer status;
  public String error;
  public String message;
  public String path;
}
