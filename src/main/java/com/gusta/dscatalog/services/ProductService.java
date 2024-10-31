package com.gusta.dscatalog.services;

import com.gusta.dscatalog.dtos.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusta.dscatalog.dtos.ProductDto;
import com.gusta.dscatalog.entities.Product;
import com.gusta.dscatalog.entities.Category;
import com.gusta.dscatalog.repositories.CategoryRepository;
import com.gusta.dscatalog.repositories.ProductRepository;
import com.gusta.dscatalog.services.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  @Transactional(readOnly = true)
  public Page<ProductDto> list(Pageable pageable) {
    var productList = productRepository.findAll(pageable);
    return productList.map(ProductDto::new);
  }

  @Transactional(readOnly = true)
  public ProductDto getById(Long id) {
    return productRepository.findById(id)
        .map(product -> new ProductDto(product, product.getCategories()))
        .orElseThrow(() -> new EntityNotFoundException("Product not found"));
  }

  @Transactional
  public ProductDto create(ProductDto dto) {
    var product = ProductDto.toEntity(dto);
    validateDependencies(dto, product);

    product = productRepository.save(product);
    return new ProductDto(product);
  }

  @Transactional
  public ProductDto update(Long id, ProductDto dto) {
    var product = productRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Product not found"));

    ProductDto.toEntity(dto, product);
    validateDependencies(dto, product);

    product = productRepository.save(product);
    return new ProductDto(product);
  }

  @Transactional
  public void delete(Long id) {
    if (!productRepository.existsById(id))
      throw new EntityNotFoundException("Product not found");

    productRepository.deleteById(id);
  }

  private void validateDependencies(ProductDto dto, Product entity) {
    var categoryIds = dto.getCategories().stream()
        .map(CategoryDto::getId)
        .toList();

    var categories = categoryRepository.findAllById(categoryIds);
    entity.setCategories(new HashSet<>(categories));
  }
}
