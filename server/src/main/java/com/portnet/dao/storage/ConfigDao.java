package com.portnet.dao.storage;

import com.portnet.entity.storage.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "configuration" table to perform various operations
 */

@Repository
public interface ConfigDao extends JpaRepository<Config,String> {
}
