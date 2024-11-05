package com.gusta.dscatalog.dtos;

import com.gusta.dscatalog.entities.Role;
import com.gusta.dscatalog.entities.User;

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


  public UserDTO(User user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.password = null;
    user.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
  }

  public static User toEntity(UserDTO dto) {
    var entity = new User();

    entity.setFirstName(dto.getFirstName());
    entity.setLastName(dto.getLastName());
    entity.setEmail(dto.getEmail());
    entity.setPassword(dto.getPassword());

    return entity;
  }

  public static void toEntity(UserDTO dto, User entity) {
    entity.setFirstName(dto.getFirstName());
    entity.setLastName(dto.getLastName());
    entity.setEmail(dto.getEmail());
    entity.setPassword(dto.getPassword());
  }
}
