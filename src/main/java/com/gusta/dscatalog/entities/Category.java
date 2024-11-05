package com.gusta.dscatalog.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {

  private String name;

  @ManyToMany(mappedBy = "categories")
  private Set<Product> products = new HashSet<>();

}
