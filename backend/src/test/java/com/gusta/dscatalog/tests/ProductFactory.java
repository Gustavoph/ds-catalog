package com.gusta.dscatalog.tests;

import java.time.Instant;
import java.util.HashSet;

import com.gusta.dscatalog.dtos.ProductDto;
import com.gusta.dscatalog.entities.Product;

public class ProductFactory {

  public static Product create() {
    return new Product(
        1L,
        "Name Test",
        "Description Test",
        10.0,
        "test.png",
        Instant.now(),
        new HashSet<>());
  }

  public static ProductDto createProductDto () {
    ProductDto productDto = new ProductDto();

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
