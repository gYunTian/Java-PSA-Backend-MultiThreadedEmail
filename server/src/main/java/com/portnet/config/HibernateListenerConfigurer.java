package com.portnet.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.portnet.listener.DbUpdateListener;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateListenerConfigurer {

  @PersistenceUnit
  private EntityManagerFactory emf;

  private DbUpdateListener updateListener;

  @Autowired
  public HibernateListenerConfigurer(DbUpdateListener updateListener) {
      this.updateListener = updateListener;
  }

  @PostConstruct
  protected void init() {
    System.out.println("Intercepted");
    SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
    EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
    registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(updateListener);
  }
  
}