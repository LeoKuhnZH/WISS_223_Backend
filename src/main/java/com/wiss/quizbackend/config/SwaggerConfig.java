package com.wiss.quizbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Swagger config.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Custom open api open api.
     *
     * @return the open api
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Quiz Backend API")
                        .version("1.0.0")
                        .description("REST API für Quiz-Anwendung mit CRUD-Operationen für Fragen")
                        .contact(new Contact()
                                .name("WISS Quiz Team")
                                .email("quiz@wiss.ch")));
    }
}