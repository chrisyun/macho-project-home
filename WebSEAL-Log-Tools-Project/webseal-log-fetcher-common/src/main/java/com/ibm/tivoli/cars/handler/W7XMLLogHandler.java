/**
 * 
 */
package com.ibm.tivoli.cars.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cars.WebSEALRequestLogEvent;

/**
 * @author zhaodonglu
 *
 */
public class W7XMLLogHandler implements EventHandler {
  
  private static Log log = LogFactory.getLog(W7XMLLogHandler.class);

  private String webSEALUrl = "HTTP://oa.tiakanglife.com";
  private String webSEALInstaceId = "default-webseald-tivoli1";

  /**
   * 
   */
  public W7XMLLogHandler() {
    super();
  }

  /**
   * @return the webSEALUrl
   */
  public String getWebSEALUrl() {
    return webSEALUrl;
  }

  /**
   * @param webSEALUrl the webSEALUrl to set
   */
  public void setWebSEALUrl(String webSEALUrl) {
    this.webSEALUrl = webSEALUrl;
  }

  /**
   * @return the webSEALInstaceId
   */
  public String getWebSEALInstaceId() {
    return webSEALInstaceId;
  }

  /**
   * @param webSEALInstaceId the webSEALInstaceId to set
   */
  public void setWebSEALInstaceId(String webSEALInstaceId) {
    this.webSEALInstaceId = webSEALInstaceId;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cars.handler.EventHandler#handle(java.lang.String, com.ibm.tivoli.cars.WebSEALRequestLogEvent)
   */
  /**
   * 
   * <pre>
   * Sample: 
   * <event>
   *   <when>${logEvent.timstamp} as format[YYYY-MM-ddTHH:mm:ss:s ¡À.hh:mm]</when>
   *   <what verb="${logEvent.action.name}" noun="${logEvent.application.name}" success="Success"/>
   *   <onwhat type="${logEvent.httpProtocol}" path="${logEvent.httpMethod}" name="${logEvent.resourceUrl}"/>
   *   <who logonname="${logEvent.uid}" realname="${logEvent.uid}"/>
   *   <where type="" name="${webSEALInstaceId}"/>
   *   <whereto type="application" name="${logEvent.application.name}"/>
   *   <wherefrom type="ip" name="${logEvent.clientIP}"/>
   *   <info>httpcode: ${logEvent.httpCode}</info>
   * </event>
   * </pre>
   */
  public void handle(String logLine, WebSEALRequestLogEvent logEvent) throws Exception {
  }

}
