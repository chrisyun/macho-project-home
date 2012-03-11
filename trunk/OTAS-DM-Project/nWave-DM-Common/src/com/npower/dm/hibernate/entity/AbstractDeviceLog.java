/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDeviceLog.java,v 1.3 2006/04/25 11:50:09 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2006/04/25 11:50:09 $
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

import java.util.Date;

import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceLog;
import com.npower.dm.core.DeviceLogAction;

/**
 * <p></p>
 * 
 * @author Zhao Yang
 * @version $Revision: 1.3 $ $Date: 2006/04/25 11:50:09 $
 */
public abstract class AbstractDeviceLog implements java.io.Serializable, DeviceLog {

  // Fields

  private long             ID;

  private DeviceLogAction  logAction;

  private ProvisionRequest provReq;

  private Device           device;

  private Date             creationDate = new Date();

  private String           deviceExternalId;

  private String           subscriberPhoneNumber;

  private String           jobType;

  private String           message;

  // Constructors

  /** default constructor */
  public AbstractDeviceLog() {
    super();
  }

  /** minimal constructor */
  public AbstractDeviceLog(DeviceLogAction logAction, Device device, String jobType, String message) {
    this.creationDate = new Date();
    this.deviceExternalId = device.getExternalId();
    this.logAction = logAction;
    this.device = device;
    this.jobType = jobType;
    this.message = message;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getID()
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getID()
   */
  public long getID() {
    return this.ID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setID(long)
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setID(long)
   */
  public void setID(long deviceLogId) {
    this.ID = deviceLogId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getLogAction()
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getLogAction()
   */
  public DeviceLogAction getLogAction() {
    return this.logAction;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setLogAction(com.npower.dm.hibernate.entity.DeviceLogActionEntity)
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setLogAction(com.npower.dm.hibernate.entity.DeviceLogActionEntity)
   */
  public void setLogAction(DeviceLogAction nwDmDeviceLogAction) {
    this.logAction = nwDmDeviceLogAction;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getProvReq()
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getProvReq()
   */
  public ProvisionRequest getProvReq() {
    return this.provReq;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setProvReq(com.npower.dm.hibernate.entity.ProvisionRequest)
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setProvReq(com.npower.dm.hibernate.entity.ProvisionRequest)
   */
  public void setProvReq(ProvisionRequest nwDmProvReq) {
    this.provReq = nwDmProvReq;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getDevice()
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getDevice()
   */
  public Device getDevice() {
    return this.device;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setDevice(com.npower.dm.core.Device)
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setDevice(com.npower.dm.core.Device)
   */
  public void setDevice(Device nwDmDevice) {
    this.device = nwDmDevice;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getCreationDate()
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getCreationDate()
   */
  public Date getCreationDate() {
    return this.creationDate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setCreationDate(java.util.Date)
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setCreationDate(java.util.Date)
   */
  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getDeviceExternalId()
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getDeviceExternalId()
   */
  public String getDeviceExternalId() {
    return this.deviceExternalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setDeviceExternalId(java.lang.String)
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setDeviceExternalId(java.lang.String)
   */
  public void setDeviceExternalId(String deviceExternalId) {
    this.deviceExternalId = deviceExternalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getSubscriberPhoneNumber()
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getSubscriberPhoneNumber()
   */
  public String getSubscriberPhoneNumber() {
    return this.subscriberPhoneNumber;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setSubscriberPhoneNumber(java.lang.String)
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setSubscriberPhoneNumber(java.lang.String)
   */
  public void setSubscriberPhoneNumber(String subscriberPhoneNumber) {
    this.subscriberPhoneNumber = subscriberPhoneNumber;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getJobType()
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getJobType()
   */
  public String getJobType() {
    return this.jobType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setJobType(java.lang.String)
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setJobType(java.lang.String)
   */
  public void setJobType(String jobType) {
    this.jobType = jobType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getMessage()
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#getMessage()
   */
  public String getMessage() {
    return this.message;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setMessage(java.lang.String)
   */
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogInter#setMessage(java.lang.String)
   */
  public void setMessage(String message) {
    this.message = message;
  }

}