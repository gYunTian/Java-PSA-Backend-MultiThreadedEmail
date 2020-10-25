// https://www.baeldung.com/spring-events
package com.portnet.entity.event;

import org.hibernate.event.spi.PostUpdateEvent;
import org.springframework.context.ApplicationEvent;

// generic event
public class DbUpdateEvent extends ApplicationEvent{
  
  private String message;
  private PostUpdateEvent postUpdateEvent;
  
  public DbUpdateEvent(final Object source, final PostUpdateEvent event, final String message) {
    super(source);
    this.message = message;
    this.postUpdateEvent = event;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public PostUpdateEvent getPostUpdateEvent() {
    return postUpdateEvent;
  }

  public void setPostUpdateEvent(PostUpdateEvent postUpdateEvent) {
    this.postUpdateEvent = postUpdateEvent;
  }


}