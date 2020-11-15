// https://medium.com/swlh/harnessing-hibernate-events-for-data-change-detection-52981f4badf0

package com.vsta.listener;

import com.vsta.handler.DefaultExecutorServiceFactory;
import com.vsta.handler.PostUpdateEventHandler;
import com.vsta.utility.MailUtility;
import com.vsta.service.SubscriptionService;

import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This listener class listens for database update events. It will register the
 * event to a thread managed by Executor service. The event will then be handled
 * by our custom handler.
 */

@Component
public class UpdateListener implements PostUpdateEventListener {

  private final DefaultExecutorServiceFactory factory;

  // required for passing into the handler as param which otherwise is unable to
  // auto inject the dependencies
  @Autowired
  private SubscriptionService subscriptionService;

  @Autowired
  private MailUtility mailUtility;

  @Autowired
  public UpdateListener(DefaultExecutorServiceFactory factory) {
    this.factory = factory;
  }

  @Override
  public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
    PostUpdateEventHandler handler = new PostUpdateEventHandler();
    handler.register(postUpdateEvent);
    this.factory.getExecutorService().execute(handler.newRunnable(subscriptionService, mailUtility));
  }

  @Override
  public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
    return false;
  }
}