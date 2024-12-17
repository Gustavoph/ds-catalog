package com.gusta.dscatalog.services.validations;

import com.gusta.dscatalog.controllers.exceptions.FieldMessage;
import com.gusta.dscatalog.dtos.UserDTO.Update;
import com.gusta.dscatalog.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, Update> {

  private final UserRepository userRepository;
  private final HttpServletRequest request;

  @Override
  public void initialize(UserUpdateValid ann) {}

  @Override
  public boolean isValid(Update dto, ConstraintValidatorContext context) {
    var uriAttributes = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    long userId = Long.parseLong(uriAttributes.get("id"));

    List<FieldMessage> list = new ArrayList<>();

    var user = userRepository.findByEmail(dto.email());
    if (user.isPresent() && !user.get().getId().equals(userId)) {
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