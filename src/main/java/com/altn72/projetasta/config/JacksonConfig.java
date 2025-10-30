package com.altn72.projetasta.config;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Hibernate5Module hibernate5Module() {
        Hibernate5Module module = new Hibernate5Module();
        // Cette option empêche Jackson d'essayer de charger les proxys non initialisés
        module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        module.disable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
        return module;
    }
}