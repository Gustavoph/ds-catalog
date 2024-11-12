package com.gusta.dscatalog.services;

import com.gusta.dscatalog.dtos.CategoryDto;
import com.gusta.dscatalog.entities.Category;
import com.gusta.dscatalog.entities.Product;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import com.gusta.dscatalog.repositories.CategoryRepository;
import com.gusta.dscatalog.services.exceptions.EntityNotFoundException;
import com.gusta.dscatalog.tests.CategoryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

  @InjectMocks
  private CategoryService service;

  @Mock
  private CategoryRepository categoryRepository;

  @Test
  public void getByIdShouldReturnACategoryWhenCategoryFound() {
    var categoryId = Long.valueOf(1);
    var category = CategoryFactory.create();

    Mockito
      .when(categoryRepository.findById(categoryId))
      .thenReturn(Optional.of(category));

    var result = service.getById(categoryId);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(categoryId, result.getId());
  }

  @Test
  public void getByIdShouldThrowEntityNotFoundExceptionWhenCategoryNotFound() {
    var categoryNotFoundId = Long.valueOf(1);

    Mockito
      .when(categoryRepository.findById(categoryNotFoundId))
      .thenReturn(Optional.empty());

    Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(categoryNotFoundId));
  }

  @Test
  public void listShouldReturnAPageOfCategoryDTO() {
    var category = CategoryFactory.create();
    var categories = new PageImpl<>(List.of(category));
    var pageable = PageRequest.of(0, 10);

    Mockito
      .when(categoryRepository.findAll(pageable))
      .thenReturn(categories);

    var result = service.list(pageable);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(category.getId(), result.getContent().get(0).getId());
    Mockito.verify(categoryRepository, Mockito.times(1)).findAll(pageable);
  }

  @Test
  public void createShouldCreateANewCategory() {
    var categoryDto = CategoryFactory.createDto();
    var savedCategory = CategoryFactory.create();

    when(categoryRepository.save(any(Category.class)))
      .thenReturn(savedCategory);

    var result = service.create(categoryDto);

    Assertions.assertNotNull(result);
    Assertions.assertNotNull(result.getId());

    verify(categoryRepository, Mockito.times(1)).save(any(Category.class));
  }
}
