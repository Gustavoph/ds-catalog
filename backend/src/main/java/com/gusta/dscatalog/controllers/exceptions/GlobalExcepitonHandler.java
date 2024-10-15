package com.gusta.dscatalog.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gusta.dscatalog.services.exceptions.EntityNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExcepitonHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<CustomException> entityNotFound(
      EntityNotFoundException e,
      HttpServletRequest request) {
    CustomException exception = CustomException.builder()
        .timestamp(Instant.now())
        .status(HttpStatus.NOT_FOUND.value())
        .error(e.getClass().getSimpleName())
        .message(e.getMessage())
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
  }
}
