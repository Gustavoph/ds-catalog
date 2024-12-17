package com.gusta.dscatalog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ROLES")
public class Role extends BaseEntity implements GrantedAuthority {

  private String authority;

  @Override
  public String getAuthority() {
    return authority;
  }
}
