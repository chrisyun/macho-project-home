/**
 * 
 */
package com.ibm.tivoli.cars.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cars.WebSEALRequestLogEvent;

/**
 * @author zhaodonglu
 * 
 */
public class ChainEventHandler implements EventHandler, PropertiesAware {

  private static Log log = LogFactory.getLog(ChainEventHandler.class);
  
  private List<EventHandler> handlers = new ArrayList<EventHandler>();
  private Properties properties = new Properties();

  /**
   * 
   */
  public ChainEventHandler() {
    super();
  }

  /**
   * @throws Exception  
  */
  public ChainEventHandler(EventHandler[] handlers) throws Exception {
    super();
    this.setHandlers(Arrays.asList(handlers));
  }

  /**
   * @throws Exception  
 */
  public ChainEventHandler(EventHandler handler) throws Exception {
    super();
    if (handler instanceof PropertiesAware) {
      ((PropertiesAware)handler).setProperties(this.properties);
    }
    this.handlers.add(handler);
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cars.handler.EventHandler#handle(java.lang.String, com.ibm.tivoli.cars.WebSEALRequestLogEvent)
   */
  public void handle(String logLine, WebSEALRequestLogEvent logEvent) throws Exception {
    for (EventHandler handler : this.handlers) {
      try {
        handler.handle(logLine, logEvent);
      } catch (Exception e) {
        log.error(String.format("Failure to handle event[%s]", logEvent), e);
      }
    }
  }

  /**
   * @param handlers
   *          the handlers to set
   * @throws Exception 
   */
  public void setHandlers(List<EventHandler> handlers) throws Exception {
    this.handlers = handlers;
    for (EventHandler handler : this.handlers) {
      if (handler instanceof PropertiesAware) {
        ((PropertiesAware)handler).setProperties(this.properties);
      }
    }
  }

  public void setProperties(Properties props) throws Exception {
    this.properties  = props;
    String s = this.properties.getProperty("wb.log.process.chain.classes");
    String[] classNames = StringUtils.split(s, ",");
    for (String className: classNames) {
        EventHandler handler = (EventHandler) Class.forName(className).newInstance();
        if (handler instanceof PropertiesAware) {
           ((PropertiesAware)handler).setProperties(props);
        }
        log.info(String.format("Added processor [%s]", className));
        this.handlers.add(handler);
    }
  }
}
