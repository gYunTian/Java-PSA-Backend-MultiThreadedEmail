// https://medium.com/swlh/harnessing-hibernate-events-for-data-change-detection-52981f4badf0

package com.vsta.listener;

import com.vsta.handler.DefaultExecutorServiceFactory;
import com.vsta.handler.PostUpdateEventHandler;
import com.vsta.service.SubscriptionService;
import com.vsta.utility.MailUtil;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This listener class contains methods that listens for database update events.
 * It will register the events (containing details about the database row - i.e.
 * changes such as new and old value) to a thread managed by the Executor
 * service instance. The executor service will then create a handler called
 * PostUpdateEventHandler to work with the database event.
 */

@Component
public class UpdateListener implements PostUpdateEventListener {

    private final DefaultExecutorServiceFactory factory;

    // required for passing into the handler as param which otherwise is unable to
    // auto inject the dependencies
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private MailUtil mailUtil;

    /**
     * This method creates a Executor Service Factory object defined in
     * handler/DefaultExecutorServiceFactory.
     * @param factory DefaultExecutorServiceFactory object
     */
    @Autowired
    public UpdateListener(DefaultExecutorServiceFactory factory) {
        this.factory = factory;
    }

    /**
     * This method is triggered after every database Post Update event. It will capture
     * the event details and hand it to our custom handler called
     * PostUpdateEventHandler
     * @param event Database Post Update Event
     */
    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        PostUpdateEventHandler handler = new PostUpdateEventHandler();
        handler.register(event);
        this.factory.getExecutorService().execute(handler.newRunnable(subscriptionService, mailUtil));
    }

    /**
     * This method needs to be overriden otherwise Java compiler will complain.
     * @param entityPersister EntityPersister object
     * @return false
     */
    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }

}