package com.gusta.dscatalog.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gusta.dscatalog.dtos.CategoryDto;
import com.gusta.dscatalog.entities.Category;
import com.gusta.dscatalog.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<CategoryDto> list(Pageable pageable) {
    return categoryService.list(pageable);
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CategoryDto getById(@PathVariable Long id) {
    return categoryService.getById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryDto create(@RequestBody CategoryDto dto) {
    return categoryService.create(dto);
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CategoryDto update(
      @PathVariable Long id,
      @RequestBody CategoryDto dto) {
    return categoryService.update(id, dto);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    categoryService.delete(id);
  }
}
