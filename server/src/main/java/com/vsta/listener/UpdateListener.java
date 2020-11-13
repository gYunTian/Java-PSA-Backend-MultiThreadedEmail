// https://medium.com/swlh/harnessing-hibernate-events-for-data-change-detection-52981f4badf0

package com.vsta.listener;

import com.vsta.handler.DefaultExecutorServiceFactory;
import com.vsta.handler.PostUpdateEventHandler;
import com.vsta.service.storage.MailService;
import com.vsta.service.voyagebyuser.VoyageSubService;

import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateListener implements PostUpdateEventListener {

  private final DefaultExecutorServiceFactory factory;
  
  // required for passing into the handler as param which otherwise is unable to auto inject the dependencies
  @Autowired
  private VoyageSubService voyageService;

  @Autowired
  private MailService mailService;

  @Autowired
  public UpdateListener(DefaultExecutorServiceFactory factory) {
      this.factory = factory;
  }
  
  @Override
  public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
    PostUpdateEventHandler handler = new PostUpdateEventHandler();
    handler.register(postUpdateEvent);
    this.factory.getExecutorService().execute(handler.newRunnable(voyageService, mailService));
  }
  
  @Override
  public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
    return false;
  }
}