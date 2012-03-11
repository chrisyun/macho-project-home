/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/SoftwareTrackingEvent.java,v 1.4 2008/08/18 05:51:35 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/08/18 05:51:35 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.management;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/08/18 05:51:35 $
 */
public class SoftwareTrackingEvent implements Serializable {
  private Date timeStamp = new Date();
  private SoftwareTrackingType type = null;
  private String clientIP = null;
  private String phoneNumber = null;
  
  /**
   * 
   */
  public SoftwareTrackingEvent() {
    super();
  }

  /**
   * @param type
   */
  public SoftwareTrackingEvent(SoftwareTrackingType type) {
    super();
    this.type = type;
  }

  /**
   * @param type
   * @param clientIP
   */
  public SoftwareTrackingEvent(SoftwareTrackingType type, String clientIP) {
    super();
    this.type = type;
    this.clientIP = clientIP;
  }

  /**
   * @param type
   * @param clientIP
   * @param phoneNumber
   */
  public SoftwareTrackingEvent(SoftwareTrackingType type, String clientIP, String phoneNumber) {
    super();
    this.type = type;
    this.clientIP = clientIP;
    this.phoneNumber = phoneNumber;
  }

  /**
   * @return the type
   */
  public SoftwareTrackingType getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(SoftwareTrackingType type) {
    this.type = type;
  }

  /**
   * @return the clientIP
   */
  public String getClientIP() {
    return clientIP;
  }

  /**
   * @param clientIP the clientIP to set
   */
  public void setClientIP(String clientIP) {
    this.clientIP = clientIP;
  }

  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   * @return the timeStamp
   */
  public Date getTimeStamp() {
    return timeStamp;
  }

  /**
   * @param timeStamp the timeStamp to set
   */
  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }
}
