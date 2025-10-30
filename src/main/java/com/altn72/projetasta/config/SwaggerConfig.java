package com.altn72.projetasta.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // Configuration générale de la documentation Swagger
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ASTA - API de suivi des apprentis")
                        .description("Documentation des endpoints REST de l’application ASTA. " +
                                "Permet de gérer les apprentis, entreprises, visites et évaluations.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Membres de l'équipe : Oumou, Lucas, Vicente"))


                );
    }
}