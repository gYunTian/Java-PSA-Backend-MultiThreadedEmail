package com.portnet.listener;

import com.portnet.entity.event.DbUpdateEvent;
import com.portnet.entity.storage.Vessel;

import org.hibernate.event.spi.PostUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DbEventListener {
  
  @EventListener
  public void onApplicationEvent(final DbUpdateEvent event) {
    System.out.println("Received event - "+event.getMessage());
    PostUpdateEvent pUpdateEvent = event.getPostUpdateEvent();

    final Object entity = pUpdateEvent.getEntity();

    if (entity instanceof Vessel) {
      Vessel vesselDTO = (Vessel)entity;
      System.out.println(vesselDTO);
    }
    
  }
}
