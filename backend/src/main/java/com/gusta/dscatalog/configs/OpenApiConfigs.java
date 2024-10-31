package com.gusta.dscatalog.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfigs {

  @Bean
  public OpenAPI GoLeadsOpenAPI() {
    final String securitySchemeName = "bearerAuth";
    final String apiTitle = String.format("GO LEADS API");
    return new OpenAPI()
        .addServersItem(new Server().url("/api/v1"))
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))

        .components(new Components().addSecuritySchemes(securitySchemeName,
            new SecurityScheme().name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP).scheme("bearer")
                .bearerFormat("JWT")))
        .info(new Info().title(apiTitle).version("v0.0.1").description(apiTitle)
            .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }
}