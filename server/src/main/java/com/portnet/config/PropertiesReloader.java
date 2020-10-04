package com.portnet.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

import lombok.Cleanup;

/**
 * Class to hot reload properties
 */

@Component
public class PropertiesReloader {
    
    @Autowired
    private StandardEnvironment environment;
    
    private static final String FILE_NAME = "reload.properties";
    private Path configPath;
    
    public void reload() throws IOException {

        MutablePropertySources propertySources = environment.getPropertySources();
        PropertySource<?> resourcePropertySource = propertySources.get("class path resource [reload.properties]");
        Properties properties = new Properties();

        configPath = Paths.get("src/main/resources/" + FILE_NAME).toAbsolutePath();
        @Cleanup InputStream inputStream = Files.newInputStream(configPath);
        properties.load(inputStream);

        propertySources.replace("class path resource [reload.properties]", 
        new PropertiesPropertySource("class path resource [reload.properties]", properties));

    }
}
