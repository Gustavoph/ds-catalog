package com.gusta.dscatalog.dtos;

import com.gusta.dscatalog.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {
  private Long id;
  private String authority;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public RoleDTO(Role role) {
    this.id = role.getId();
    this.authority = role.getAuthority();
    this.createdAt = role.getCreatedAt();
    this.updatedAt = role.getUpdatedAt();
  }
}
