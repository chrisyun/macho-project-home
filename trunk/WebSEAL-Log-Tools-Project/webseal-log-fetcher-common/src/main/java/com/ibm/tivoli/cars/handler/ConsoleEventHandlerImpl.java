/**
 * 
 */
package com.ibm.tivoli.cars.handler;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cars.WebSEALRequestLogEvent;

/**
 * @author ZhaoDongLu
 *
 */
public class ConsoleEventHandlerImpl extends BaseCarsEventHandler implements EventHandler {
  private static Log log = LogFactory.getLog(ConsoleEventHandlerImpl.class);

  public ConsoleEventHandlerImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cars.EventUploaded#upload(com.ibm.tivoli.cars.WebSEALRequestLogEvent)
   */
  public void handle(String logLine, WebSEALRequestLogEvent logEvent) throws Exception {

    try {
      String msg = getMessage(logEvent);
      System.out.println(msg);
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }

}
