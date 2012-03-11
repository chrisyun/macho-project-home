/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractClientProvJobTargetDevice.java,v 1.3 2008/07/29 07:17:40 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/07/29 07:17:40 $
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

import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.npower.dm.core.ClientProvJobTargetDevice;
import com.npower.dm.core.ProvisionJob;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/07/29 07:17:40 $
 */
public abstract class AbstractClientProvJobTargetDevice implements java.io.Serializable, ClientProvJobTargetDevice {

  // Fields

  private long         id;

  private ProvisionJob job;

  private String       deviceId;

  private String       phoneNumber;

  private String       manufacturerExternalId;

  private String       modelExternalId;

  private String       carrierExternalId;

  private String       status;
  
  private long numberOfEnqueueRetries = 1;
  
  private Date lastEnqueueRetriesTime = null;

  private String       messageId;

  private String       messageType;

  private String       messageContent;

  private String       securityMethod;

  private String       securityPin;

  private String       profileExternalId;

  private Blob         parameters;

  private Date         finishedTime;

  private Date         createdTime = new Date();;

  private String       createdBy;

  private Date         lastUpdatedTime = new Date();

  private String       lastUpdatedBy;

  private Set          profiles = new HashSet(0);

  private Blob messageRaw;

  // Constructors

  /** default constructor */
  public AbstractClientProvJobTargetDevice() {
  }

  /** minimal constructor */
  public AbstractClientProvJobTargetDevice(String status, Date createdTime) {
    this.status = status;
    this.createdTime = createdTime;
  }

  /** full constructor */
  public AbstractClientProvJobTargetDevice(ProvisionJob job, String deviceId, String phoneNumber,
      String manufacturerExternalId, String modelExternalId, String carrierExternalId, String status, String messageId,
      String messageType, String messageContent, String securityMethod, String securityPin, String profileExternalId,
      Blob parameters, Date finishedTime, Date createdTime, String createdBy, Date lastUpdatedTime,
      String lastUpdatedBy, Set nwCpJobProfiles) {
    this.job = job;
    this.deviceId = deviceId;
    this.phoneNumber = phoneNumber;
    this.manufacturerExternalId = manufacturerExternalId;
    this.modelExternalId = modelExternalId;
    this.carrierExternalId = carrierExternalId;
    this.status = status;
    this.messageId = messageId;
    this.messageType = messageType;
    this.messageContent = messageContent;
    this.securityMethod = securityMethod;
    this.securityPin = securityPin;
    this.profileExternalId = profileExternalId;
    this.parameters = parameters;
    this.finishedTime = finishedTime;
    this.createdTime = createdTime;
    this.createdBy = createdBy;
    this.lastUpdatedTime = lastUpdatedTime;
    this.lastUpdatedBy = lastUpdatedBy;
    this.profiles = nwCpJobProfiles;
  }

  // Property accessors

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getId()
   */
  public long getId() {
    return this.id;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setId(long)
   */
  public void setId(long id) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getJob()
   */
  public ProvisionJob getJob() {
    return this.job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setJob(com.npower.dm.hibernate.entity.ClientProvJobEntity)
   */
  public void setJob(ProvisionJob job) {
    this.job = job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getDeviceId()
   */
  public String getDeviceId() {
    return this.deviceId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setDeviceId(java.lang.String)
   */
  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getPhoneNumber()
   */
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setPhoneNumber(java.lang.String)
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getManufacturerExternalId()
   */
  public String getManufacturerExternalId() {
    return this.manufacturerExternalId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setManufacturerExternalId(java.lang.String)
   */
  public void setManufacturerExternalId(String manufacturerExternalId) {
    this.manufacturerExternalId = manufacturerExternalId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getModelExternalId()
   */
  public String getModelExternalId() {
    return this.modelExternalId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setModelExternalId(java.lang.String)
   */
  public void setModelExternalId(String modelExternalId) {
    this.modelExternalId = modelExternalId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getCarrierExternalId()
   */
  public String getCarrierExternalId() {
    return this.carrierExternalId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setCarrierExternalId(java.lang.String)
   */
  public void setCarrierExternalId(String carrierExternalId) {
    this.carrierExternalId = carrierExternalId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getStatus()
   */
  public String getStatus() {
    return this.status;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setStatus(java.lang.String)
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getMessageId()
   */
  public String getMessageId() {
    return this.messageId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setMessageId(java.lang.String)
   */
  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getMessageType()
   */
  public String getMessageType() {
    return this.messageType;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setMessageType(java.lang.String)
   */
  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getMessageContent()
   */
  public String getMessageContent() {
    return this.messageContent;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setMessageContent(java.sql.Clob)
   */
  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }

  public Blob getMessageRaw() {
    return this.messageRaw;
  }

  public void setMessageRaw(Blob messageRaw) {
    this.messageRaw = messageRaw;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getSecurityMethod()
   */
  public String getSecurityMethod() {
    return this.securityMethod;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setSecurityMethod(java.lang.String)
   */
  public void setSecurityMethod(String securityMethod) {
    this.securityMethod = securityMethod;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getSecurityPin()
   */
  public String getSecurityPin() {
    return this.securityPin;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setSecurityPin(java.lang.String)
   */
  public void setSecurityPin(String securityPin) {
    this.securityPin = securityPin;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getProfileExternalId()
   */
  public String getProfileExternalId() {
    return this.profileExternalId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setProfileExternalId(java.lang.String)
   */
  public void setProfileExternalId(String profileExternalId) {
    this.profileExternalId = profileExternalId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getParameters()
   */
  public Blob getParameters() {
    return this.parameters;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setParameters(java.sql.Blob)
   */
  public void setParameters(Blob parameters) {
    this.parameters = parameters;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getFinishedTime()
   */
  public Date getFinishedTime() {
    return this.finishedTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setFinishedTime(java.util.Date)
   */
  public void setFinishedTime(Date finishedTime) {
    this.finishedTime = finishedTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getCreatedTime()
   */
  public Date getCreatedTime() {
    return this.createdTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setCreatedTime(java.util.Date)
   */
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getCreatedBy()
   */
  public String getCreatedBy() {
    return this.createdBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setCreatedBy(java.lang.String)
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    return this.lastUpdatedTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setLastUpdatedTime(java.util.Date)
   */
  public void setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#getProfiles()
   */
  public Set getProfiles() {
    return this.profiles;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvJobTargetDevice#setProfiles(java.util.Set)
   */
  public void setProfiles(Set nwCpJobProfiles) {
    this.profiles = nwCpJobProfiles;
  }

  /**
   * @return the numberOfEnqueueRetries
   */
  public long getNumberOfEnqueueRetries() {
    return numberOfEnqueueRetries;
  }

  /**
   * @param numberOfEnqueueRetries the numberOfEnqueueRetries to set
   */
  public void setNumberOfEnqueueRetries(long numberOfEnqueueRetries) {
    this.numberOfEnqueueRetries = numberOfEnqueueRetries;
  }

  /**
   * @return the lastEnqueueRetriesTime
   */
  public Date getLastEnqueueRetriesTime() {
    return lastEnqueueRetriesTime;
  }

  /**
   * @param lastEnqueueRetriesTime the lastEnqueueRetriesTime to set
   */
  public void setLastEnqueueRetriesTime(Date lastEnqueueRetriesTime) {
    this.lastEnqueueRetriesTime = lastEnqueueRetriesTime;
  }

}