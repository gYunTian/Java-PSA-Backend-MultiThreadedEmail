package com.vsta.configuration;

import com.vsta.listener.UpdateListener;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Configuration class to setup database Post Update Event listening.
 */

@Component
public class HibernateListenerConfig {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private final UpdateListener updateListener;

    /**
     * Assigns an update listener object to its instance variable.
     * @param updateListener Object which listens for database
     *                       Post Update events.
     */
    @Autowired
    public HibernateListenerConfig(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    /**
     * Auto runs after the class is auto instantiated.
     * This method does the necessary stuff required for database update listening.
     */
    @PostConstruct
    protected void init() {
        SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(updateListener);
    }

}