/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDmTrackingLogSum.java,v 1.1 2008/08/13 10:17:47 zhaowx Exp $
  * $Revision: 1.1 $
  * $Date: 2008/08/13 10:17:47 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.hibernate.entity;

import java.io.Serializable;
import java.util.Date;

public class AbstractDmTrackingLogSum implements Serializable{

  private String sessionId;
  private long jobId;
  private String deviceId;
  private Date beginTimeStamp;
  private Date endTimeStamp;
  private long request;
  private long response;
  
  public AbstractDmTrackingLogSum() {
    super();
  }
  
  public AbstractDmTrackingLogSum(long jobId, String deviceId, Date beginTimeStamp, Date endTimeStamp, long requestSum, long responseSum) {
    super();
    this.jobId = jobId;
    this.deviceId = deviceId;
    this.beginTimeStamp = beginTimeStamp;
    this.endTimeStamp = endTimeStamp;
    this.request = requestSum;
    this.response = responseSum;
  }
  
  public String getSessionId() {
      return this.sessionId;
  }

  public void setSessionId(String sessionId) {
      this.sessionId = sessionId;
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

  public void setResponse(long responseSum) {
      this.response = responseSum;
  }

}