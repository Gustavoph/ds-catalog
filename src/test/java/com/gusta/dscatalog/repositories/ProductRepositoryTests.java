package com.gusta.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.gusta.dscatalog.entities.Product;
import com.gusta.dscatalog.tests.ProductFactory;

@DataJpaTest
public class ProductRepositoryTests {

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void deleteShouldDeleteObjectWhenIdExists() {
    long existingId = 1L;
    productRepository.deleteById(existingId);
    Optional<Product> result = productRepository.findById(existingId);
    Assertions.assertFalse(result.isPresent());
  }

  @Test
  public void saveShouldCreateProduct() {
    var product = ProductFactory.create();
    product.setId(null);

    product = productRepository.save(product);
    Assertions.assertNotNull(product.getId());
    Assertions.assertEquals(product.getId(), 26);
  }

  @Test
  public void findByIdShouldReturnProductWhenIdExists() {
    long existingId = 1L;
    Optional<Product> result = productRepository.findById(existingId);
    Assertions.assertEquals(result.isPresent(), true);
  }

  @Test
  public void findByIdShouldReturnNullWhenIdNotExists() {
    long notExistingId = 26L;
    Optional<Product> result = productRepository.findById(notExistingId);
    Assertions.assertEquals(result.isPresent(), false);
  }
}
