package com.gusta.dscatalog.tests;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;

import com.gusta.dscatalog.dtos.ProductDto;
import com.gusta.dscatalog.entities.Product;

public class ProductFactory {

  public static Product create() {
    var product =  new Product();

    product.setId(1L);
    product.setName("Name Test");
    product.setDescription("Description Test");
    product.setPrice(10.0);
    product.setDate(Instant.now());
    product.setCreatedAt(LocalDateTime.now());
    product.setUpdatedAt(LocalDateTime.now());

    return product;
  }

  public static ProductDto createProductDto () {
    ProductDto productDto = new ProductDto();

    productDto.setId(1L);
    productDto.setName("Test Product");
    productDto.setDescription("Test Description");
    productDto.setPrice(10.0);
    productDto.setImgUrl("http://example.com/image.jpg");
    productDto.setDate(Instant.now());

    return productDto;
  }

  public static ProductDto updateProductDto () {
    ProductDto productDto = new ProductDto();

    productDto.setName("Test Update");
    productDto.setDescription("Test Update");
    productDto.setPrice(11.0);
    productDto.setImgUrl("http://example.com/image-update.jpg");
    productDto.setDate(Instant.now());

    return productDto;
  }
}
