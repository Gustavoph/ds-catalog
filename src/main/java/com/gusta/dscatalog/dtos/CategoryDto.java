package com.gusta.dscatalog.dtos;

import com.gusta.dscatalog.entities.Category;

import lombok.Getter;

@Getter
public class CategoryDto {
  private Long id;
  private String name;

  public CategoryDto() {
  }

  public CategoryDto(Category category) {
    this.id = category.getId();
    this.name = category.getName();
  }
}
