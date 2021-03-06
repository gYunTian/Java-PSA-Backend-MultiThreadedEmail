package com.vsta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point class to run SpringBootApplication.
 */

@SpringBootApplication
@EnableScheduling   // Annotation to enable fixed rate scheduling
@PropertySource("classpath:/reload.properties")     // annotation to add reload.properties to spring boot java class path
public class VstaApplication {

    /**
     * Entry point method to run SpringBootApplication.
     * @param args Additional parameters passed in.
     */
    public static void main(String[] args) {
        SpringApplication.run(VstaApplication.class, args);
    }

}
