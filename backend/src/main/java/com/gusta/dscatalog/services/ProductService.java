package com.gusta.dscatalog.services;

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

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  @Transactional(readOnly = true)
  public Page<ProductDto> list(Pageable pageable) {
    return productRepository.findAll(pageable).map(ProductDto::new);
  }

  @Transactional(readOnly = true)
  public ProductDto getById(Long id) {
    return productRepository.findById(id)
        .map(product -> new ProductDto(product, product.getCategories()))
        .orElseThrow(() -> new EntityNotFoundException("Product not found"));
  }

  @Transactional
  public ProductDto create(ProductDto dto) {
    Product product = new Product();
    copyDtoToEntity(dto, product);
    product = productRepository.save(product);
    return new ProductDto(product);
  }

  public ProductDto update(Long id, ProductDto dto) {
    var product = productRepository.findById(id).orElseThrow(() -> {
      throw new EntityNotFoundException("Product not found");
    });

    copyDtoToEntity(dto, product);
    product = productRepository.save(product);
    return new ProductDto(product);
  }

  public void delete(Long id) {
    var Product = getById(id);
    productRepository.deleteById(Product.getId());
  }

  private void copyDtoToEntity(ProductDto dto, Product entity) {
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setDate(dto.getDate());
    entity.setImgUrl(dto.getImgUrl());
    entity.setPrice(dto.getPrice());

    entity.getCategories().clear();
    dto.getCategories().forEach(item -> {
      Category category = categoryRepository.getReferenceById(item.getId());
      entity.getCategories().add(category);
    });
  }
}
