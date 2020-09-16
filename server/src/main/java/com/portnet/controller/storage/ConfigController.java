package com.portnet.controller.storage;

import com.portnet.entity.storage.Config;
import com.portnet.service.storage.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST APIs using service methods for Config
 */

@RestController
public class ConfigController {

    @Autowired
    private ConfigService service;

    /**
     * Add methods
     */

    @PostMapping("/addConfig")
    public void addConfig(@RequestBody Config config) {
        service.saveConfig(config);
    }

    /**
     * Get methods
     */

    @GetMapping("/configs")
    public Config findConfig() {
        return service.getConfig();
    }

    /**
     * Delete methods
     */

    @DeleteMapping("/deleteConfig")
    public void deleteConfig() {
        service.deleteConfig();
    }

    /**
     * Update methods
     */

    @PutMapping("/updateConfig")
    public void updateConfig(@RequestBody Config config) {
        service.updateConfig(config);
    }
}