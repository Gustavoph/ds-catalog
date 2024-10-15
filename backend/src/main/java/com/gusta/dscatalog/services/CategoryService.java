package com.gusta.dscatalog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusta.dscatalog.dtos.CategoryDto;
import com.gusta.dscatalog.entities.Category;
import com.gusta.dscatalog.repositories.CategoryRepository;
import com.gusta.dscatalog.services.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  @Transactional(readOnly = true)
  public Page<CategoryDto> list(Pageable pageable) {
    return categoryRepository.findAll(pageable).map(CategoryDto::new);
  }

  @Transactional(readOnly = true)
  public CategoryDto getById(Long id) {
    return categoryRepository.findById(id)
        .map(CategoryDto::new)
        .orElseThrow(() -> new EntityNotFoundException("Category not found"));
  }

  @Transactional
  public CategoryDto create(CategoryDto dto) {
    Category category = new Category();
    category.setName(dto.getName());
    category = categoryRepository.save(category);
    return new CategoryDto(category);
  }

  public CategoryDto update(Long id, CategoryDto dto) {
    var category = categoryRepository.findById(id).orElseThrow(() -> {
      throw new EntityNotFoundException("Category not found");
    });

    category.setName(dto.getName());
    category = categoryRepository.save(category);
    return new CategoryDto(category);
  }

  public void delete(Long id) {
    var category = getById(id);
    categoryRepository.deleteById(category.getId());
  }
}
