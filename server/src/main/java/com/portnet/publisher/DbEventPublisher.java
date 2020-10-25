package com.portnet.publisher;

import com.portnet.entity.event.DbUpdateEvent;

import org.hibernate.event.spi.PostUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DbEventPublisher {
  
  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;
  
  public void publishCustomEvent(PostUpdateEvent event, final String message) {
    // System.out.println("Publishing event");

    DbUpdateEvent customEvent = new DbUpdateEvent(this, event, message);
    applicationEventPublisher.publishEvent(customEvent);
    // System.out.println("Published event");


  }
}
