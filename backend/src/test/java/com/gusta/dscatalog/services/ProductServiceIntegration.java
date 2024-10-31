package com.gusta.dscatalog.services;

import com.gusta.dscatalog.repositories.ProductRepository;
import com.gusta.dscatalog.services.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIntegration {

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void deleteShouldDeleteProductWhenIdExists() {
    var existingId = 1L;
    var totalProducts = productRepository.count();

    productService.delete(existingId);
    Assertions.assertEquals(totalProducts - 1, productRepository.count());
  }

  @Test
  public void deleteShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
    var notExistingId = 999999L;

    Assertions.assertThrows(EntityNotFoundException.class, () -> {
      productService.delete(notExistingId);
    });
  }

  @Test
  public void listShouldReturnAPageOfProducts() {
    PageRequest pageRequest = PageRequest.of(0, 10);
    var result = productService.list(pageRequest);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(25, result.getTotalElements());
    Assertions.assertEquals(0, result.getNumber());
    Assertions.assertEquals(10, result.getSize());
  }

  @Test
  public void listShouldReturnAEmptyPageWhenPageDoesNotExists() {
    PageRequest pageRequest = PageRequest.of(50, 10);
    var result = productService.list(pageRequest);

    Assertions.assertNotNull(result);
    Assertions.assertTrue(result.isEmpty());
    Assertions.assertEquals(50, result.getNumber());
    Assertions.assertEquals(10, result.getSize());
  }
}
