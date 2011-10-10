/**
 * 
 */
package com.ibm.tivoli.cars.handler;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cars.WebSEALRequestLogEvent;
import com.ibm.tivoli.cars.entity.Action;
import com.ibm.tivoli.cars.entity.Application;

/**
 * @author ZhaoDongLu
 *
 */
public class SoapEventHandlerImpl extends BaseCarsEventHandler implements EventHandler {
  private static Log log = LogFactory.getLog(SoapEventHandlerImpl.class);
  
  private int numberOfGroupEvent = 1;
  
  private List<WebSEALRequestLogEvent> eventQueue = new ArrayList<WebSEALRequestLogEvent>();

  private String carsServiceURL = "http://10.9.2.100:9080/CommonAuditService/services/Emitter";
  private String carsUsername = null;
  private String carsPassword = null;
  
  private boolean ignoreUnauth = false;
  
  public SoapEventHandlerImpl() {
    super();
  }

  public String getCarsServiceURL() {
    return carsServiceURL;
  }

  public void setCarsServiceURL(String carsServiceURL) {
    this.carsServiceURL = carsServiceURL;
  }

  public String getCarsUsername() {
    return carsUsername;
  }

  public void setCarsUsername(String carsUsername) {
    this.carsUsername = carsUsername;
  }

  public String getCarsPassword() {
    return carsPassword;
  }

  public void setCarsPassword(String carsPassword) {
    this.carsPassword = carsPassword;
  }

  /**
   * @return the ignoreUnauth
   */
  public boolean isIgnoreUnauth() {
    return ignoreUnauth;
  }

  /**
   * @param ignoreUnauth the ignoreUnauth to set
   */
  public void setIgnoreUnauth(boolean ignoreUnauth) {
    this.ignoreUnauth = ignoreUnauth;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cars.EventUploaded#upload(com.ibm.tivoli.cars.WebSEALRequestLogEvent)
   */
  public void handle(String logLine, WebSEALRequestLogEvent logEvent) throws Exception {
    // Check queue and post data, increase check point
    checkQueueAndPostData();

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
    if (this.ignoreUnauth && logEvent.getUserid().equals("Unauth")) {
       return;
    }
    this.eventQueue.add(logEvent);
    
    // Check queue and post data
    checkQueueAndPostData();
  }

  private void checkQueueAndPostData() {
    // Check and post
    if (this.eventQueue.size() < this.numberOfGroupEvent) {
      return;
    }
    PostMethod method = new PostMethod(carsServiceURL);
    try {
      String msg = getMessage(eventQueue);
     
      log.debug(msg);

      method.setRequestHeader("Content-Type", "text/xml;charset=UTF-8");
      method.setRequestHeader("SOAPAction", "\"\"");
      method.setRequestBody(msg);
      
      HttpClient client = new HttpClient();
      if (!StringUtils.isEmpty(this.carsUsername)) {
         client.getParams().setAuthenticationPreemptive(true);
         Credentials defaultCreds = new UsernamePasswordCredentials(this.carsUsername, this.carsPassword);
         client.getState().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM), defaultCreds);
      }
      
      int statusCode = client.executeMethod(method);
      
      if (statusCode != HttpStatus.SC_OK) {
        log.error("Method failed: " + method.getStatusLine());
      }

      // Read the response body.
      byte[] responseBody = method.getResponseBody();

      // Deal with the response.
      // Use caution: ensure correct character encoding and is not binary data
      String resp = new String(responseBody);
      log.debug(resp);
      
    } catch (Exception e) {
      log.error("Fail to upload event log to CARS server, cause: ", e);
    } finally {
      eventQueue.clear();
      method.releaseConnection();
    }
  }

}
