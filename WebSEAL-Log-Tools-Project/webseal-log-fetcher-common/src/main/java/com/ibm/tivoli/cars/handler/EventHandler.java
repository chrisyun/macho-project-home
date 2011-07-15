/**
 * 
 */
package com.ibm.tivoli.cars.handler;

import com.ibm.tivoli.cars.WebSEALRequestLogEvent;

/**
 * @author ZhaoDongLu
 *
 */
public interface EventHandler {

  public void handle(String logLine, WebSEALRequestLogEvent logEvent) throws Exception;

}
