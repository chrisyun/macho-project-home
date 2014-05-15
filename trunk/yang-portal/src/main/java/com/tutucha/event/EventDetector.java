package com.tutucha.event;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Application Lifecycle Listener implementation class EventDetector
 * 
 */
public class EventDetector implements ServletRequestListener {

  /**
   * Default constructor.
   */
  public EventDetector() {
    super();
  }

  /**
   * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
   */
  public void requestDestroyed(ServletRequestEvent evt) {
    // TODO Auto-generated method stub
  }

  /**
   * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
   */
  public void requestInitialized(ServletRequestEvent evt) {
    // TODO Auto-generated method stub
  }

}
