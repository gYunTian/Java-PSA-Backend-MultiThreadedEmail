package com.portnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:/reload.properties")
public class PortnetApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PortnetApplication.class, args);
    }
}
