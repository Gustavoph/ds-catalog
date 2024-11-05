package com.gusta.dscatalog.entities;

import java.time.Instant;
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
public class Product extends BaseEntity {
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;
  private Double price;

  @Column(name = "img_url")
  private String imgUrl;

  @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private Instant date;

  @ManyToMany
  @JoinTable(
    name = "PRODUCT_CATEGORY",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();
}
