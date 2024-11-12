package com.gusta.dscatalog.services.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gusta.dscatalog.controllers.exceptions.FieldMessage;
import com.gusta.dscatalog.dtos.UserDTO;
import com.gusta.dscatalog.repositories.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserDTO.Create> {

  private final UserRepository userRepository;

  @Override
  public void initialize(UserInsertValid ann) {}

  @Override
  public boolean isValid(UserDTO.Create dto, ConstraintValidatorContext context) {
    List<FieldMessage> list = new ArrayList<>();

    var user = userRepository.findByEmail(dto.email());
    if (Objects.nonNull(user)) {
      list.add(new FieldMessage("email", "Email already exists"));
    }


    list.forEach(e -> {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(e.getMessage())
          .addPropertyNode(e.getFieldName())
          .addConstraintViolation();
    });

    return list.isEmpty();
  }
}