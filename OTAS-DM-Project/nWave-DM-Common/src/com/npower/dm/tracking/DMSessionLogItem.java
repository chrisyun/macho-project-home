/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/DMSessionLogItem.java,v 1.1 2008/08/04 02:33:23 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/04 02:33:23 $
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
package com.npower.dm.tracking;

import java.util.Date;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/08/04 02:33:23 $
 */
public class DMSessionLogItem {

  private String dmSessionId = null;
  private Date timeStamp = null;
  private long sizeRequestData = 0;
  private long sizeResponseData = 0;
  private String clientIp = null;
  private String userAgent = null;
  
  /**
   * 
   */
  public DMSessionLogItem() {
    super();
  }

  /**
   * @return the dmSessionId
   */
  public String getDmSessionId() {
    return dmSessionId;
  }

  /**
   * @param dmSessionId the dmSessionId to set
   */
  public void setDmSessionId(String dmSessionId) {
    this.dmSessionId = dmSessionId;
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

  /**
   * @return the sizeRequestData
   */
  public long getSizeRequestData() {
    return sizeRequestData;
  }

  /**
   * @param sizeRequestData the sizeRequestData to set
   */
  public void setSizeRequestData(long sizeRequestData) {
    this.sizeRequestData = sizeRequestData;
  }

  /**
   * @return the sizeResponseData
   */
  public long getSizeResponseData() {
    return sizeResponseData;
  }

  /**
   * @param sizeResponseData the sizeResponseData to set
   */
  public void setSizeResponseData(long sizeResponseData) {
    this.sizeResponseData = sizeResponseData;
  }

  /**
   * @return the clientIp
   */
  public String getClientIp() {
    return clientIp;
  }

  /**
   * @param clientIp the clientIp to set
   */
  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  /**
   * @return the userAgent
   */
  public String getUserAgent() {
    return userAgent;
  }

  /**
   * @param userAgent the userAgent to set
   */
  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

}
