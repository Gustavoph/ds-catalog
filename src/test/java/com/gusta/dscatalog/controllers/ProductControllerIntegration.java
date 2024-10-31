package com.gusta.dscatalog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gusta.dscatalog.tests.ProductFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIntegration {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void listShouldReturnSortedPageWhenSortByName() throws Exception {
    var result = mockMvc.perform(get("/products?page=0&size=8&sort=name,asc")
      .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.totalElements").value(25));
  }

  @Test
  public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
    var productDTO = ProductFactory.createProductDto();
    String requestBody = objectMapper.writeValueAsString(productDTO);

    var result = mockMvc.perform(put("/products/{id}", productDTO.getId())
      .content(requestBody)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isAccepted());
    result.andExpect(jsonPath("$.id").value(productDTO.getId()));
    result.andExpect(jsonPath("$.name").value(productDTO.getName()));
    result.andExpect(jsonPath("$.description").value(productDTO.getDescription()));
  }

  @Test
  public void updateShouldThrowNotFoundWhenIdDoesNotExists() throws Exception {
    var productDTO = ProductFactory.createProductDto();
    String requestBody = objectMapper.writeValueAsString(productDTO);

    var result = mockMvc.perform(put("/products/{id}", 9999)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }
}
