// https://medium.com/swlh/harnessing-hibernate-events-for-data-change-detection-52981f4badf0

package com.portnet.listener;

import com.portnet.entity.storage.Vessel;
import com.portnet.publisher.DbEventPublisher;

import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbUpdateListener implements PostUpdateEventListener {

  @Autowired DbEventPublisher customEventPublisher;

  @Override
  public void onPostUpdate(PostUpdateEvent event) {

    customEventPublisher.publishCustomEvent(event, "Vessel post update event");
    // get updated entity
    final Object entity = event.getEntity();
    
    if(entity instanceof Vessel) {
      Vessel vesselDTO = (Vessel) entity;
      // System.out.println("Intercepted");
      // System.out.println(vesselDTO);

      // event.getSession().createNativeQuery(

      // )
    } 

  }

  @Override
  public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
      return false;
  }

}
