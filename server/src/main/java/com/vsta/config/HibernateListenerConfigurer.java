package com.vsta.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.vsta.listener.UpdateListener;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class to setup DB POST UPDATE Event listening.
 * 
 */
@Component
public class HibernateListenerConfigurer {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private final UpdateListener updateListener;

    /**
     * Assigns an update listener object to its instance variable
     * 
     * @param updateListener
     */
    @Autowired
    public HibernateListenerConfigurer(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    /**
     * Auto runs after class is auto instantiatied. This method does necessary stuff
     * required for DB update listening.
     * 
     * @param updateListener
     */
    @PostConstruct
    protected void init() {
        SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(updateListener);
    }
}