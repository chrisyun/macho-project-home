package com.ibm.tivoli.cars;

import java.util.Date;

import com.ibm.tivoli.cars.entity.Action;
import com.ibm.tivoli.cars.entity.Application;

public class WebSEALRequestLogEvent {
  private String clientIP = null;
  private String userid = null;
  private Date timestamp = null;
  private String httpMethod = null;
  private String resourceUrl = null;
  private String httpProtocol = null;
  private int httpCode = 0;
  private int contentLength = 0;
  
  private Application application = null;
  private Action action = null;

  /**
   * @return the application
   */
  public Application getApplication() {
    return application;
  }

  /**
   * @param application the application to set
   */
  public void setApplication(Application application) {
    this.application = application;
  }

  /**
   * @return the action
   */
  public Action getAction() {
    return action;
  }

  /**
   * @param action the action to set
   */
  public void setAction(Action action) {
    this.action = action;
  }

  public String getClientIP() {
    return clientIP;
  }

  public void setClientIP(String clientIP) {
    this.clientIP = clientIP;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getResourceUrl() {
    return resourceUrl;
  }

  public void setResourceUrl(String resourceUrl) {
    this.resourceUrl = resourceUrl;
  }

  public String getHttpProtocol() {
    return httpProtocol;
  }

  public void setHttpProtocol(String httpProtocol) {
    this.httpProtocol = httpProtocol;
  }

  public int getHttpCode() {
    return httpCode;
  }

  public void setHttpCode(int httpCode) {
    this.httpCode = httpCode;
  }

  public int getContentLength() {
    return contentLength;
  }

  public void setContentLength(int contentLength) {
    this.contentLength = contentLength;
  }

}
