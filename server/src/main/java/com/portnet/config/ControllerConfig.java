package com.portnet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Auto configuration class to overide the default api base url
 */

@Configuration
@EnableWebMvc
public class ControllerConfig implements WebMvcConfigurer{
    
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", 
            HandlerTypePredicate.forAnnotation(RestController.class));
    }
}