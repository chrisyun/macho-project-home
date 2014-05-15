package com.tutucha.model.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.tutucha.event.SimpleEventListener;

/**
 * Application Lifecycle Listener implementation class ServiceInjectionListener
 * 
 */
public class ServiceInjectionListener implements ServletContextListener {

  /**
   * Default constructor.
   */
  public ServiceInjectionListener() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @see ServletContextListener#contextInitialized(ServletContextEvent)
   */
  public void contextInitialized(ServletContextEvent event) {
    event.getServletContext().setAttribute("dataService", new SimpleDataServiceImpl());
    event.getServletContext().setAttribute("eventListener", new SimpleEventListener());
  }

  /**
   * @see ServletContextListener#contextDestroyed(ServletContextEvent)
   */
  public void contextDestroyed(ServletContextEvent event) {
    // TODO Auto-generated method stub
  }

}
