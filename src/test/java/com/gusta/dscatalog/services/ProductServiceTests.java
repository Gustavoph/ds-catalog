package com.gusta.dscatalog.services;

import com.gusta.dscatalog.dtos.ProductDto;
import com.gusta.dscatalog.entities.Product;
import com.gusta.dscatalog.repositories.CategoryRepository;
import com.gusta.dscatalog.services.exceptions.EntityNotFoundException;
import com.gusta.dscatalog.tests.CategoryFactory;
import com.gusta.dscatalog.tests.ProductFactory;
import com.gusta.dscatalog.repositories.ProductRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

  @InjectMocks
  private ProductService service;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @Test
  public void listShouldReturnAPageOfProducts() {
    var product = ProductFactory.create();
    var products = new PageImpl<>(List.of(product));
    var pageable = PageRequest.of(0, 10);

    Mockito
      .when(productRepository.findAll(ArgumentMatchers.any(Pageable.class)))
      .thenReturn(products);

    var result = service.list(pageable);

    Assertions.assertNotNull(result);
    Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);
  }

  @Test
  public void getByIdShouldThrowEntityNotFoundExceptionWhenProductNotFound() {
    var productId = Long.valueOf(1);

    Mockito
      .when(productRepository.findById(productId))
      .thenReturn(Optional.empty());

    Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(productId));
  }

  @Test
  public void getByIdShouldReturnAProductWhenProductFound() {
    var productId = Long.valueOf(1);
    var product = ProductFactory.create();

    Mockito
      .when(productRepository.findById(productId))
      .thenReturn(Optional.of(product));

    var result = service.getById(productId);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(product.getName(), result.getName());
  }

  @Test
  public void createShouldCreateProduct() {
    var productDto = ProductFactory.createProductDto();
    var product = ProductDto.toEntity(productDto);
    var category = CategoryFactory.create();

    Mockito
        .when(categoryRepository.findAllById(ArgumentMatchers.any(List.class)))
        .thenReturn(List.of(category));

    Mockito
      .when(productRepository.save(Mockito.any(Product.class)))
      .thenReturn(product);

    var result = service.create(productDto);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(productDto.getName(), result.getName());
    Assertions.assertEquals(productDto.getDescription(), result.getDescription());
    Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
  }

  @Test
  public void updateShouldUpdateProduct() {
    var productId = Long.valueOf(1);
    var product = ProductFactory.create();
    var category = CategoryFactory.create();

    var updatedProductDto = ProductFactory.updateProductDto();

    Mockito
      .when(categoryRepository.findAllById(ArgumentMatchers.any(List.class)))
      .thenReturn(List.of(category));

    Mockito
      .when(productRepository.findById(productId))
      .thenReturn(Optional.of(product));

    Mockito
      .when(productRepository.save(Mockito.any(Product.class)))
      .thenReturn(product);


    var result = service.update(productId, updatedProductDto);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(updatedProductDto.getName(), result.getName());
    Assertions.assertEquals(updatedProductDto.getDescription(), result.getDescription());

    Mockito
      .verify(productRepository, Mockito.times(1))
      .findById(productId);

    Mockito
      .verify(productRepository, Mockito.times(1))
      .save(product);

    Assertions.assertEquals(updatedProductDto.getName(), product.getName());
    Assertions.assertEquals(updatedProductDto.getDescription(), product.getDescription());
  }

  @Test
  public void updateShouldThrowEntityNotFoundExceptionWhenProductNotFound() {
    var productId = Long.valueOf(1);
    var updatedProductDto = ProductFactory.updateProductDto();

    Mockito
      .when(productRepository.findById(productId))
      .thenReturn(Optional.empty());

    Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(productId, updatedProductDto));
  }

  @Test
  public void deleteShouldNotThrowWhenProductIdExists() {
    var productId = Long.valueOf(1);

    Mockito
      .when(productRepository.existsById(productId))
      .thenReturn(Boolean.TRUE);

    Mockito.doNothing()
      .when(productRepository)
      .deleteById(productId);

    Assertions.assertDoesNotThrow(() -> service.delete(productId));
    Mockito.verify(productRepository, Mockito.times(1)).deleteById(productId);
  }

  @Test
  public void deleteShouldThrowEntityNotFoundExceptionWhenProductIdDoesNotExist() {
    var productId = Long.valueOf(1);

    Mockito
      .when(productRepository.existsById(productId))
      .thenReturn(Boolean.FALSE);

    Assertions.assertThrows(EntityNotFoundException.class, () ->  service.delete(productId));
    Mockito.verify(productRepository, Mockito.times(0)).deleteById(productId);
  }
}
