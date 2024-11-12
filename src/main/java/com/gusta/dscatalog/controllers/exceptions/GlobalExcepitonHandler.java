package com.gusta.dscatalog.controllers.exceptions;

import java.time.Instant;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomException> methodArgumentNotValidation(
      MethodArgumentNotValidException e,
      HttpServletRequest request) {

    var exception = ValidationException.builder()
        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
        .timestamp(Instant.now())
        .error("Validation Error")
        .message(e.getMessage())
        .errors(new ArrayList<>())
        .path(request.getRequestURI())
        .build();

    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      exception.addError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception);
  }
}
