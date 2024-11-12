package com.gusta.dscatalog.dtos;

import com.gusta.dscatalog.entities.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
  private Long id;
  private String name;

  public CategoryDto() {
  }

  public CategoryDto(Category category) {
    this.id = category.getId();
    this.name = category.getName();
  }

  public static Category toEntity(CategoryDto dto) {
    Category category = new Category();
    category.setName(dto.getName());
    return category;
  }
}
