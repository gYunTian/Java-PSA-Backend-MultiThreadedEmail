package com.vsta.handler;

import java.util.HashMap;
import java.util.List;

import com.vsta.dto.UserDTO;
import com.vsta.service.SubscriptionService;
import com.vsta.utility.MailUtil;

import org.hibernate.event.spi.PostUpdateEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Event handler class that acquires the database Post Update Event from our
 * Executor Service Factory instance. It contains methods that will find the
 * subbed users to a vessel update in the database update event. It will then
 * send them an email on the changes extract from the database update event
 * object.
 */
@Component
public class PostUpdateEventHandler {

    protected PostUpdateEvent event;

    /**
     * Create event.
     * 
     * @param event Database Post Update Event
     */
    public final void register(PostUpdateEvent event) {
        this.event = event;
    }

    /**
     * Execute the sending of subscription notification using a common protocol
     * 
     * @param service  SubscriptionService
     * @param mailUtil Class that allows sending of email
     * @return Runnable that executes the code
     */
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

                // currently voyage sub is empty
                List<UserDTO> users = service.getSubs(uniqueId);
                // System.out.println("Sending email");
                for (UserDTO user : users) {
                    String subject = "Changes to vessel detail: " + uniqueId;

                    String content = "Details of the vessel: " + uniqueId + " has changed.\n\n" + changes + "\n";
                    
                    mailUtil.sendEmail(user, subject, content);
                }
            }
        };
    }

}