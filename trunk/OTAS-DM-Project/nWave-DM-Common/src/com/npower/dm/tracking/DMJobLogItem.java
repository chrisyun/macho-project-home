/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/DMJobLogItem.java,v 1.1 2008/08/04 02:33:23 zhao Exp $
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

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/08/04 02:33:23 $
 */
public class DMJobLogItem implements Serializable {
  
  private String dmSessionId = null;
  private String jobId = null;
  private String deviceExternalId = null;
  private String processor = null;
  private String statusCode = null;
  private Date startTimeStamp = null;
  private Date endTimeStamp = null;
  
  /**
   * 
   */
  public DMJobLogItem() {
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
   * @return the jobId
   */
  public String getJobId() {
    return jobId;
  }


  /**
   * @param jobId the jobId to set
   */
  public void setJobId(String jobId) {
    this.jobId = jobId;
  }


  /**
   * @return the deviceExternalId
   */
  public String getDeviceExternalId() {
    return deviceExternalId;
  }


  /**
   * @param deviceExternalId the deviceExternalId to set
   */
  public void setDeviceExternalId(String deviceExternalId) {
    this.deviceExternalId = deviceExternalId;
  }


  /**
   * @return the processor
   */
  public String getProcessor() {
    return processor;
  }


  /**
   * @param processor the processor to set
   */
  public void setProcessor(String processor) {
    this.processor = processor;
  }


  /**
   * @return the statusCode
   */
  public String getStatusCode() {
    return statusCode;
  }


  /**
   * @param statusCode the statusCode to set
   */
  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }


  /**
   * @return the startTimeStamp
   */
  public Date getStartTimeStamp() {
    return startTimeStamp;
  }


  /**
   * @param startTimeStamp the startTimeStamp to set
   */
  public void setStartTimeStamp(Date startTimeStamp) {
    this.startTimeStamp = startTimeStamp;
  }


  /**
   * @return the endTimeStamp
   */
  public Date getEndTimeStamp() {
    return endTimeStamp;
  }


  /**
   * @param endTimeStamp the endTimeStamp to set
   */
  public void setEndTimeStamp(Date endTimeStamp) {
    this.endTimeStamp = endTimeStamp;
  }


  
  
}
