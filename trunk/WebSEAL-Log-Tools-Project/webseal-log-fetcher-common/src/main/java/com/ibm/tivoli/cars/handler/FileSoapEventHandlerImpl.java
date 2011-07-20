/**
 * 
 */
package com.ibm.tivoli.cars.handler;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cars.WebSEALRequestLogEvent;
import com.ibm.tivoli.cars.entity.Action;
import com.ibm.tivoli.cars.entity.Application;

/**
 * @author ZhaoDongLu
 *
 */
public class FileSoapEventHandlerImpl extends BaseCarsEventHandler implements EventHandler {
  private static Log log = LogFactory.getLog(FileSoapEventHandlerImpl.class);

  private static Log eventLog = LogFactory.getLog("event.log.output");

  public FileSoapEventHandlerImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cars.EventUploaded#upload(com.ibm.tivoli.cars.WebSEALRequestLogEvent)
   */
  public void handle(String logLine, WebSEALRequestLogEvent logEvent) throws Exception {
    if (logEvent == null) {
      return;
    }  
    Application application = logEvent.getApplication();
    if (application == null) {
       return;
    }
    Action action = logEvent.getAction();
    if (action == null || !action.isUploaded()) {
       return;
    }
    if (logEvent.getUserid().equals("Unauth")) {
      return;
    }
    eventLog.info(logLine);
    
    String msg = getMessage(logEvent);
    log.info(msg);
  }

}
