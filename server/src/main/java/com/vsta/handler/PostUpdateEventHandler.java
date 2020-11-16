package com.vsta.handler;

import java.util.HashMap;
import java.util.List;

import com.vsta.model.User;
import com.vsta.utility.MailUtil;
import com.vsta.service.UserService;
import com.vsta.service.SubscriptionService;

import org.hibernate.event.spi.PostUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Event handler class that acquires the DB POST UPDATE Event from our Executor
 * Service Factory instance. It contains methods that will find the subbed users
 * to a vessel update in the db update event. It will then send them an email on
 * the changes extract from the DB update event object.
 */
@Component
public class PostUpdateEventHandler {

    protected PostUpdateEvent event;

    @Autowired
    private UserService userService;

    public final void register(PostUpdateEvent event) {
        this.event = event;
    }

    @Bean
    @Scope("prototype")
    public Runnable newRunnable(final SubscriptionService service, final MailUtil mailUtil) {
        return new Runnable() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                for (int p : event.getDirtyProperties()) {
                    sb.append("\t");
                    sb.append(event.getPersister().getEntityMetamodel().getProperties()[p].getName());
                    sb.append(" (Old value: ").append(event.getOldState()[p]).append(", New value: ")
                            .append(event.getState()[p]).append(")\n");
                }
                String changes = sb.toString();
                String uniqueId = String.valueOf(event.getId());

                List<User> users = service.getSubs(uniqueId);
                // currently voyage sub is empty
                for (User user : users) {
                    String subject = "Changes to vessel detail: " + uniqueId;

                    String content = "Details of the vessel: " + uniqueId + " has changed.\n\n" +
                            changes + "\n";

                    HashMap<String, String> emailContent = mailUtil.getEmailContent(user, changes, uniqueId);
                    mailUtil.sendEmail(emailContent);
                }
            }
        };
    }

}