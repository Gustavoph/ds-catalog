package com.gusta.dscatalog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gusta.dscatalog.dtos.ProductDto;
import com.gusta.dscatalog.entities.Product;
import com.gusta.dscatalog.services.ProductService;

import com.gusta.dscatalog.services.exceptions.EntityNotFoundException;
import com.gusta.dscatalog.tests.ProductFactory;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductService productService;

  @Test
  public void listShouldReturnAPageOfProductDTO() throws Exception {
    var productDTO = ProductFactory.createProductDto();
    var products = new PageImpl<>(List.of(productDTO));

    Mockito
      .when(productService.list(ArgumentMatchers.any(Pageable.class)))
      .thenReturn(products);

    var response = mockMvc.perform(get("/products")
      .accept(MediaType.APPLICATION_JSON));
    response.andExpect(status().isOk());
  }

  @Test
  public void getByIdShouldReturnAProductDTOWhenProductExists() throws Exception {
    var productDTO = ProductFactory.createProductDto();

    Mockito
        .when(productService.getById(productDTO.getId()))
        .thenReturn(productDTO);

    var response = mockMvc.perform(get("/products/{id}", productDTO.getId())
      .accept(MediaType.APPLICATION_JSON));

    response.andExpect(status().isOk());
    response.andExpect(jsonPath("$.id").value(productDTO.getId()));
  }

  @Test
  public void getByIdShouldReturnNotFoundWhenProductDoesNotExists() throws Exception {
    var nonExistingId = 1L;

    Mockito
      .when(productService.getById(nonExistingId))
      .thenThrow(EntityNotFoundException.class);

    var response = mockMvc.perform(get("/products/{id}", nonExistingId)
      .accept(MediaType.APPLICATION_JSON));

    response.andExpect(status().isNotFound());
  }

  @Test
  public void updateShouldReturnProductDTOWhenProductExists() throws Exception {
    var productDTO = ProductFactory.createProductDto();
    String requestBody = objectMapper.writeValueAsString(productDTO);

    Mockito
      .when(productService.update(eq(productDTO.getId()), ArgumentMatchers.any(ProductDto.class)))
      .thenReturn(productDTO);

    var response = mockMvc.perform(put("/products/{id}", productDTO.getId())
      .content(requestBody)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON));

    response.andExpect(status().isAccepted());
    response.andExpect(jsonPath("$.id").value(productDTO.getId()));
  }

  @Test
  public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
    var nonExistingId = 1L;
    var productDTO = ProductFactory.createProductDto();
    String requestBody = objectMapper.writeValueAsString(productDTO);

    Mockito
        .when(productService.update(eq(nonExistingId), ArgumentMatchers.any(ProductDto.class)))
        .thenThrow(EntityNotFoundException.class);

    var response = mockMvc.perform(put("/products/{id}", nonExistingId)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    response.andExpect(status().isNotFound());
  }

  @Test
  public void deleteShouldReturnNoContentWhenProductExists() throws Exception {
    var productDTO = ProductFactory.createProductDto();

    Mockito.doNothing()
        .when(productService)
        .delete(productDTO.getId());

    var response = mockMvc.perform(delete("/products/{id}", productDTO.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    response.andExpect(status().isNoContent());
  }

  @Test
  public void deleteShouldThrowEntityNotFoundWhenProductDoesNotExists() throws Exception {
    var nonExistingId = 1L;

    Mockito
      .doThrow(EntityNotFoundException.class)
      .when(productService)
      .delete(nonExistingId);

    var response = mockMvc.perform(delete("/products/{id}", nonExistingId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    response.andExpect(status().isNotFound());
  }
}
