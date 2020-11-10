package com.portnet.handler;

import java.util.HashMap;
import java.util.List;

import com.portnet.service.storage.MailService;
import com.portnet.service.voyagebyuser.VoyageSubService;

import org.hibernate.event.spi.PostUpdateEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class PostUpdateEventHandler {

    protected PostUpdateEvent event;

    public final void register(PostUpdateEvent event) {
        this.event = event;
    }

    @Bean
    @Scope("prototype")
    public Runnable newRunnable(final VoyageSubService service, final MailService mailService) {
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
                for (String email: emails) {
                    HashMap<String,String> emailContent = mailService.getEmailContent(email, changes, uniqueId);
                    System.out.println(emailContent.get("body"));
                    mailService.sendEmail(emailContent);
                }
            }
        };
    }

}