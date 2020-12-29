package com.ssau.shvaiko.games.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI config() {
        return new OpenAPI()
                .info(new Info()
                        .title("MRPO project backend")
                        .version("0.0.0-SNAPSHOT"))
                .components(new Components().addSecuritySchemes("basic",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")
                                .in(SecurityScheme.In.HEADER).name("Authorization")))
                .info(new Info().title("App API").version("snapshot"))
                .addSecurityItem(
                        new SecurityRequirement().addList("basic", Arrays.asList("read", "write")));

    }
}