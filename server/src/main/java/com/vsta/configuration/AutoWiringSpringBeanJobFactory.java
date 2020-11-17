// References:
// http://www.btmatthews.com/blog/2011/inject-application-context+dependencies-in-quartz-job-beans.html
package com.vsta.configuration;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * Auto-wiring support for quartz jobs. As
 * SpringBeanJobFactory do not support injecting
 * of spring beans out from applicationContext,
 * this class is required to help to do so.
 */

public final class AutoWiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

  private transient AutowireCapableBeanFactory beanFactory;

  /**
   * Sets the instance variable beanFactory.
   */
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    beanFactory = applicationContext.getAutowireCapableBeanFactory();
  }

  /**
   * Creates and return a Object super type containing details about quartz job.
   */
  @Override
  protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
    final Object job = super.createJobInstance(bundle);
    beanFactory.autowireBean(job);
    return job;
  }

}