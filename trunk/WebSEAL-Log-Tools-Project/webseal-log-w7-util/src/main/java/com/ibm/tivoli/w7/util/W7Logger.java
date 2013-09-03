/**
 * 
 */
package com.ibm.tivoli.w7.util;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaodonglu
 *
 */
public class W7Logger {
  
  private static Log selfLog = LogFactory.getLog(W7Logger.class);

  private static Log w7Log = LogFactory.getLog("W7XML.OUT");

  /**
   * 
   */
  public W7Logger() {
    super();
  }

  public static void log(W7Event event) {
    try {
      w7Log.info(event.toXML());
    } catch (IOException e) {
      selfLog.error("Failure to write W7 log, cause: " + e.getMessage(), e);
    } catch (ValidationException e) {
      selfLog.error("Invalidate W event, cause: " + e.getMessage(), e);
    }
  }
}
