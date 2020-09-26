package com.portnet.service.storage;

import com.portnet.dao.storage.ConfigDao;
import com.portnet.entity.storage.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service tasks that use DAO methods
 * - retrieve and modify database
 * - useful for REST APIs for Config
 */

@Service
public class ConfigService {

    @Autowired
    private ConfigDao configDao;

    /**
     * Add Config to database
     * @param config object
     */
    public void saveConfig(Config config) {
        configDao.save(config);
    }

    /**
     * Get Config in database
     * @return config object
     */
    public Config getConfig() {
        return configDao.findAll().get(0);
    }

    /**
     * Remove Config with specified name from database
     */
    public void deleteConfig() {
        configDao.deleteAll();
    }

    /**
     * Update Config in database
     */
    public void updateConfig(Config config) {
        deleteConfig();
        saveConfig(config);
    }

}
