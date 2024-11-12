package com.gusta.dscatalog.dtos;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.gusta.dscatalog.entities.Category;
import com.gusta.dscatalog.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto {
  private Long id;
  private String name;
  private String description;
  private Double price;
  private String imgUrl;
  private Instant date;
  private Set<CategoryDto> categories = new HashSet<>();

  public ProductDto() {
  }

  public ProductDto(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.price = product.getPrice();
    this.imgUrl = product.getImgUrl();
    this.date = product.getDate();
  }

  public ProductDto(Product product, Set<Category> categories) {
    this(product);
    categories.forEach(category -> this.categories.add(new CategoryDto(category)));
  }

  public static Product toEntity(ProductDto dto) {
    Product entity = new Product();
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setDate(dto.getDate());
    entity.setImgUrl(dto.getImgUrl());
    entity.setPrice(dto.getPrice());
    return entity;
  }

  public static void toEntity(ProductDto dto, Product entity) {
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setDate(dto.getDate());
    entity.setImgUrl(dto.getImgUrl());
    entity.setPrice(dto.getPrice());
  }
}
