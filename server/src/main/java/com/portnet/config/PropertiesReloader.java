// https://stackoverflow.com/questions/40287771/how-to-reload-a-value-property-from-application-properties-in-spring/40288822#40288822
package com.portnet.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.Cleanup;

/**
 * Class to hot reload properties
 */

// to-do
// on reload, intercept quartz schedule and create new trigger

@Component
public class PropertiesReloader {
    
    @Autowired
    private StandardEnvironment environment;
    
    private static final String FILE_NAME = "reload.properties";
    private Path configPath;
    
    @Scheduled(fixedRate=10000)
    public void reload() throws IOException {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = now.format(formatter);
        System.out.println(date +  "  - Reloading properties");

        try {
            MutablePropertySources propertySources = environment.getPropertySources();
            PropertySource<?> resourcePropertySource = propertySources.get("class path resource [reload.properties]");
            Properties properties = new Properties();
    
            configPath = Paths.get("src/main/resources/" + FILE_NAME).toAbsolutePath();
            @Cleanup InputStream inputStream = Files.newInputStream(configPath);
            properties.load(inputStream);
    
            propertySources.replace("class path resource [reload.properties]", 
            new PropertiesPropertySource("class path resource [reload.properties]", properties));

        } catch (IOException e) {
            System.out.println(date +  "  - Your reload.properties file cannot be located - "+e);
            System.out.println(date +  "  - Make sure it is in resources folder");
            System.out.println(date +  "  - Quartz job will be stopped");

        }

        System.out.println(date +  "  - Reloaded properties");
    }
}
