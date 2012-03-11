package com.npower.dm.hibernate.entity;


import java.util.Date;

public class AbstractDmTrackingLogDetail {

  private long did;
  private long jobId;
  private String deviceId;
  private String sessionId;
  private Date beginTimeStamp;
  private Date endTimeStamp;
  private long request;
  private long response;
  private String clientIp;
  private String userAgent;
  
  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public AbstractDmTrackingLogDetail() {
    super();
  }

  /** minimal constructor */
  public AbstractDmTrackingLogDetail(String deviceId, String sessionId, long requestSum, long responseSum ,String clientIp ,String userAgent) {
    this.deviceId = deviceId;
    this.sessionId = sessionId;
    this.request = requestSum;
    this.response = responseSum;
    this.clientIp = clientIp;
    this.userAgent = userAgent;
  }

  /** full constructor */
  public AbstractDmTrackingLogDetail(long jobId, String deviceId, String sessionId, Date beginTimeStamp, Date endTimeStamp,
      long requestSum, long responseSum,String clientIp ,String userAgent) {
    this.jobId = jobId;
    this.deviceId = deviceId;
    this.sessionId = sessionId;
    this.beginTimeStamp = beginTimeStamp;
    this.endTimeStamp = endTimeStamp;
    this.request = requestSum;
    this.response = responseSum;
    this.clientIp = clientIp;
    this.userAgent = userAgent;
  } 
  
  public long getDid() {
    return this.did;
  }

  public void setDid(long did) {
    this.did = did;
  }

  public long getJobId() {
    return this.jobId;
  }

  public void setJobId(long jobId) {
    this.jobId = jobId;
  }

  public String getDeviceId() {
    return this.deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getSessionId() {
    return this.sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public Date getBeginTimeStamp() {
    return this.beginTimeStamp;
  }

  public void setBeginTimeStamp(Date beginTimeStamp) {
    this.beginTimeStamp = beginTimeStamp;
  }

  public Date getEndTimeStamp() {
    return this.endTimeStamp;
  }

  public void setEndTimeStamp(Date endTimeStamp) {
    this.endTimeStamp = endTimeStamp;
  }

  public long getRequest() {
    return this.request;
  }

  public void setRequest(long requestSum) {
    this.request = requestSum;
  }

  public long getResponse() {
    return this.response;
  }

  public void setResponse(long response) {
    this.response = response;
  }

}