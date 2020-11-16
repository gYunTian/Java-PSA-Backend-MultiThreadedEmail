package com.vsta.handler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executor factory that is used to create a default executor service.
 * This class contains configuration methods. It also reads the threads property in application.properties file.
 */

@Component
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("threads")
public class DefaultExecutorServiceFactory {

  /**
   * int instance variable to store thread pool size
   */
  private int threadPoolSize;

  /**
   * Executor service reference variable to hold executor objects
   */
  private ExecutorService service;

  /**
   * Getter method for threadpoolsize
   */
  public int getThreadPoolSize() {
    return threadPoolSize;
  }

  /**
   * Setter method for threadpoolsize
   */
  public void setThreadPoolSize(int threadPoolSize) {
    this.threadPoolSize = threadPoolSize;
  }
  
  /**
   * Getter method for Executor service.
   */
  public ExecutorService getExecutorService() {
    if (this.service == null) {
      this.service = Executors.newFixedThreadPool(threadPoolSize);
    }

    return this.service;
  }
}