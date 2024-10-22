package com.gusta.dscatalog.tests;


import com.gusta.dscatalog.entities.Category;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;

public class CategoryFactory {
  public static Category create() {
    return new Category(1L, "Name Test", LocalDateTime.now(), LocalDateTime.now());
  }
}
