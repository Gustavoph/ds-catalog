package com.gusta.dscatalog.dtos;

import com.gusta.dscatalog.entities.User;

import com.gusta.dscatalog.services.validations.UserInsertValid;
import com.gusta.dscatalog.services.validations.UserUpdateValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Set<RoleDTO> roles = new HashSet<>();
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @UserInsertValid
  public record Create (
    @NotBlank(message = "FirstName is required")
    String firstName,

    @NotBlank(message = "LastName is required")
    String lastName,

    @Email(message = "Email is required")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "password must be 6-20 characters")
    String password,

    Set<RoleDTO> roles
  ) {}

  @UserUpdateValid
  public record Update (
    @NotBlank(message = "Password is required")
    String firstName,

    @NotBlank(message = "Password is required")
    String lastName,

    @Email(message = "Email is required")
    String email,

    Set<RoleDTO> roles
  ) {}

  public UserDTO(User user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.password = null;
    user.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
  }

  public static User createToEntity(UserDTO.Create dto) {
    var entity = new User();

    entity.setFirstName(dto.firstName());
    entity.setLastName(dto.lastName());
    entity.setEmail(dto.email());
    entity.setPassword(dto.password());

    return entity;
  }

  public static void updateToEntity(UserDTO.Update dto, User entity) {
    entity.setFirstName(dto.firstName());
    entity.setLastName(dto.lastName());
    entity.setEmail(dto.email());
  }
}
