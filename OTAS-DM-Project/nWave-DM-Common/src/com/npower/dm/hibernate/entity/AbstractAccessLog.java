package com.npower.dm.hibernate.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.npower.dm.tracking.AccessLog;

/**
 * AbstractAccessLog entity provides the base persistence definition of the
 * AccessLogEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractAccessLog implements java.io.Serializable, AccessLog {

  // Fields

  private long   id;

  private Date   timeStamp = new Date();

  private String url;

  private String query;

  private String clientIp;

  private String userAgent;

  private String sessionId;

  private Set<AccessLogHeaderEntity>    headers    = new HashSet<AccessLogHeaderEntity>(0);

  private Set<AccessLogParameterEntity>    parameters = new HashSet<AccessLogParameterEntity>(0);

  // Constructors

  /** default constructor */
  public AbstractAccessLog() {
  }

  /** minimal constructor */
  public AbstractAccessLog(String url, String clientIp) {
    this.url = url;
    this.clientIp = clientIp;
  }

  /** full constructor */
  public AbstractAccessLog(String url, String query, String clientIp, String userAgent,
      String sessionId) {
    this.url = url;
    this.query = query;
    this.clientIp = clientIp;
    this.userAgent = userAgent;
    this.sessionId = sessionId;
  }

  // Property accessors

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getTimeStamp() {
    return this.timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getQuery() {
    return this.query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public String getClientIp() {
    return this.clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getUserAgent() {
    return this.userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public String getSessionId() {
    return this.sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public Set<AccessLogHeaderEntity> getHeaders() {
    return this.headers;
  }

  public void setHeaders(Set<AccessLogHeaderEntity> headers) {
    this.headers = headers;
  }

  public Set<AccessLogParameterEntity> getParameters() {
    return this.parameters;
  }

  public void setParameters(Set<AccessLogParameterEntity> parameters) {
    this.parameters = parameters;
  }

}