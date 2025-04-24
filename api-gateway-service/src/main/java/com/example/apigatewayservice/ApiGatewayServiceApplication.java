package com.example.apigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ApiGatewayServiceApplication {

    // On déclare un bean RestTemplate pour que Spring puisse l’injecter là où on en a besoin
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(
                ApiGatewayServiceApplication.class, args);

        // Pour afficher les profils actifs si on en utilise (utile pour le débogage)
        System.out.println("Active Profile(s): " +
                String.join(", ", context.getEnvironment().getActiveProfiles()));
    }
}
