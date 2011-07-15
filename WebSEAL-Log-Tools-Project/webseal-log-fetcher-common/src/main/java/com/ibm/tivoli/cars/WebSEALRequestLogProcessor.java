/**
 * 
 */
package com.ibm.tivoli.cars;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cars.entity.Action;
import com.ibm.tivoli.cars.entity.ApplicationConfigHelper;
import com.ibm.tivoli.cars.entity.ApplicationConfigHelper.ApplicationAndJunction;
import com.ibm.tivoli.cars.handler.EventHandler;

/**
 * @author ZhaoDongLu
 * 
 */
public class WebSEALRequestLogProcessor {
  private static Log log = LogFactory.getLog(WebSEALRequestLogProcessor.class);


  private Reader eventReader = new InputStreamReader(System.in);

  private EventHandler eventHandler = null;

  private ApplicationConfigHelper applicationConfigHelper = null;


  private LogParser logParser = new LogParser();
  /**
   * 
   */
  public WebSEALRequestLogProcessor() {
    super();
  }

  public WebSEALRequestLogProcessor(Reader eventReader, EventHandler eventUploader, ApplicationConfigHelper applicationConfigHelper) {
    super();
    this.eventReader = eventReader;
    this.eventHandler = eventUploader;
    this.applicationConfigHelper = applicationConfigHelper;
  }

  /**
   * @return the applicationConfigHelper
   */
  public ApplicationConfigHelper getApplicationConfigHelper() {
    return applicationConfigHelper;
  }

  /**
   * @param applicationConfigHelper the applicationConfigHelper to set
   */
  public void setApplicationConfigHelper(ApplicationConfigHelper applicationConfigHelper) {
    this.applicationConfigHelper = applicationConfigHelper;
  }

  public Reader getEventReader() {
    return eventReader;
  }

  public void setEventReader(Reader eventReader) {
    this.eventReader = eventReader;
  }

  public EventHandler getEventHandler() {
    return eventHandler;
  }

  public void setEventHandler(EventHandler eventUploader) {
    this.eventHandler = eventUploader;
  }

  public void process() throws Exception {
    BufferedReader in = new BufferedReader(eventReader);
    String line = in.readLine();
    while (line != null) {
      try {
        WebSEALRequestLogEvent logEvent = logParser.parseWebSEALRequestLogEvent(line);
        if (logEvent != null) {
          ApplicationAndJunction appAndJunction = this.applicationConfigHelper.getMatchedApplication(logEvent.getResourceUrl());
          Action action = this.applicationConfigHelper.getMatchedAction(appAndJunction, logEvent.getResourceUrl());
          if (action != null) {
             log.debug("match pattern: app=[" + appAndJunction + "], line: " + line);
          }
          logEvent.setApplication((appAndJunction != null)?appAndJunction.getApplication():null);
          logEvent.setAction(action);
          this.eventHandler.handle(line, logEvent);
        }
      } catch (Throwable e) {
        log.error("error to upload event: [" + line + "], cause: " + e.getMessage(), e);
      } finally {
        log.debug("processed: [" + line + "]");
        line = in.readLine();
      }
    }
  }

}
