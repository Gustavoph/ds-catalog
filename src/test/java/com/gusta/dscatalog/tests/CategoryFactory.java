package com.gusta.dscatalog.tests;


import com.gusta.dscatalog.dtos.CategoryDto;
import com.gusta.dscatalog.entities.Category;

import java.time.LocalDateTime;

public class CategoryFactory {
  public static Category create() {
    var category = new Category();

    category.setId(1L);
    category.setName("Name Test");
    category.setCreatedAt(LocalDateTime.now());
    category.setUpdatedAt(LocalDateTime.now());

    return category;
  }

  public static CategoryDto createDto() {
    var category = new CategoryDto();
    category.setName("Name Test");
    return category;
  }
}
