package com.gusta.dscatalog.controllers.exceptions;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class ValidationException extends CustomException {
  private List<FieldMessage> errors;

  public ValidationException(
      Instant timestamp,
      Integer status,
      String error,
      String path,
      String message) {
    super(timestamp, status, error, path, message);
  }

  public void addError(String fieldName, String message) {
    this.errors.add(new FieldMessage(fieldName, message));
  }
}
