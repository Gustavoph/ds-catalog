package com.gusta.dscatalog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gusta.dscatalog.entities.Category;
import com.gusta.dscatalog.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public List<Category> list() {
    return categoryRepository.findAll();
  }
}
