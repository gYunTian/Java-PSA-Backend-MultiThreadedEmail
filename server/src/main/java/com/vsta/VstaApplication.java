package com.vsta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestOperations;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:/reload.properties")
public class VstaApplication {

    public static void main(String[] args) {
        SpringApplication.run(VstaApplication.class, args);
    }

    @Bean
    RestOperations restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.basicAuthentication("g1t9", "999000").build();
    }
}
