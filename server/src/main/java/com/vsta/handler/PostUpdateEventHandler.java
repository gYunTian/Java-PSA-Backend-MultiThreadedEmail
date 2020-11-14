package com.vsta.handler;

import java.util.HashMap;
import java.util.List;

import com.vsta.model.User;
import com.vsta.service.MailService;
import com.vsta.service.UserService;
import com.vsta.service.SubscriptionService;

import org.hibernate.event.spi.PostUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Event handler that finds the subbed users and sends them an email on the changes.
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
    public Runnable newRunnable(final SubscriptionService service, final MailService mailService) {
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

                List<String> emails = service.getSubs(uniqueId);
                // currently voyage sub is empty
                for (String email : emails) {
                    User user = userService.getUserByEmail(email);
                    HashMap<String, String> emailContent = mailService.getEmailContent(user, changes, uniqueId);
                    mailService.sendEmail(emailContent);
                }
            }
        };
    }

}