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

import com.gusta.dscatalog.dtos.ProductDto;
import com.gusta.dscatalog.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService ProductService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<ProductDto> list(Pageable pageable) {
    return ProductService.list(pageable);
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDto getById(@PathVariable Long id) {
    return ProductService.getById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDto create(@RequestBody ProductDto dto) {
    return ProductService.create(dto);
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ProductDto update(
      @PathVariable Long id,
      @RequestBody ProductDto dto) {
    return ProductService.update(id, dto);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    ProductService.delete(id);
  }
}
