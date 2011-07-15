/**
 * 
 */
package com.ibm.tivoli.cars.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ibm.tivoli.cars.WebSEALRequestLogEvent;

/**
 * @author zhaodonglu
 * 
 */
public class ChainEventHandler implements EventHandler {

  private List<EventHandler> handlers = new ArrayList<EventHandler>();

  /**
   * 
   */
  public ChainEventHandler() {
    super();
  }

  /** 
  */
  public ChainEventHandler(EventHandler[] handlers) {
    super();
    this.handlers.addAll(Arrays.asList(handlers));
  }

  /** 
 */
  public ChainEventHandler(EventHandler handler) {
    super();
    this.handlers.add(handler);
  }

  public void handle(String logLine, WebSEALRequestLogEvent logEvent) throws Exception {
    for (EventHandler handler : this.handlers) {
      handler.handle(logLine, logEvent);
    }
  }

  /**
   * @return the handlers
   */
  public List<EventHandler> getHandlers() {
    return handlers;
  }

  /**
   * @param handlers
   *          the handlers to set
   */
  public void setHandlers(List<EventHandler> handlers) {
    this.handlers = handlers;
  }

}
