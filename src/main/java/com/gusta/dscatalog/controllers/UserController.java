package com.gusta.dscatalog.controllers;

import com.gusta.dscatalog.dtos.UserDTO;
import com.gusta.dscatalog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<UserDTO> list(Pageable pageable) {
    return userService.list(pageable);
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserDTO getById(@PathVariable Long id) {
    return userService.getById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO create(@Valid @RequestBody UserDTO.Create dto) {
    return userService.create(dto);
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public UserDTO update(
      @PathVariable Long id,
      @Valid @RequestBody UserDTO.Update dto) {
    return userService.update(id, dto);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    userService.delete(id);
  }
}
