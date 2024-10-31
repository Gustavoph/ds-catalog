package com.gusta.dscatalog.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfigs {

  @Bean
  public OpenAPI DsCatalogOpenAPI() {
    final String apiTitle = String.format("DS CATALOG API");

    return new OpenAPI()
        .addServersItem(new Server().url("/api"))
        .info(new Info().title(apiTitle).version("v0.0.1").description(apiTitle)
            .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }
}