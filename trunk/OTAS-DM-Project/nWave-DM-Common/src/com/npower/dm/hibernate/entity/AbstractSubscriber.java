/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractSubscriber.java,v 1.6 2008/11/19 04:27:49 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2008/11/19 04:27:49 $
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
import java.util.HashSet;
import java.util.Set;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.ServiceProvider;
import com.npower.dm.core.Subscriber;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/11/19 04:27:49 $
 */
public abstract class AbstractSubscriber implements java.io.Serializable, Subscriber {

  // Fields

  private long ID;

  private Carrier newCarrier;

  private Carrier carrier;

  private String externalId;

  private String phoneNumber;

  private String IMSI;
  
  private String firstname;
  
  private String lastname;
  
  private String email;

  private String password;

  private String newPassword;

  private String state = Subscriber.STATE_INIT;

  private String SMSState = Subscriber.SMS_STATE_NO_NEED_NOTIFICATION;

  private boolean isActivated = true;

  private String lastUpdatedBy;

  private Date lastUpdatedTime = new Date();

  private String createdBy;

  private Date createdTime = new Date();

  private Date lastRegistrationTime = new Date();

  private String pin;

  private boolean askPermissionOnTrigger;

  private Date notificationTime;

  private long noSMSRetries;

  private String SMSMessageId;

  private long msgSeqNo;

  private long changeVersion;
  
  private ServiceProvider serviceProvider;

  private Set devices = new HashSet(0);

  /**
   * Default id UserPIN
   */
  private String bootstrapPinType = Byte.toString(OMACPSecurityMethod.USERPIN.getValue());

  // Constructors

  /** default constructor */
  public AbstractSubscriber() {
    super();
  }

  /** minimal constructor */
  public AbstractSubscriber(Carrier nwDmCarrierByCarrierId, String subscriberExternalId, String phoneNumber,
      String password, String smsState, boolean askPermissionOnTrigger, long noSmsRetries, long msgSeqNo,
      boolean isActivated, long changeVersion) {
    this.carrier = nwDmCarrierByCarrierId;
    this.externalId = subscriberExternalId;
    this.phoneNumber = phoneNumber;
    this.password = password;
    this.SMSState = smsState;
    this.askPermissionOnTrigger = askPermissionOnTrigger;
    this.noSMSRetries = noSmsRetries;
    this.msgSeqNo = msgSeqNo;
    this.isActivated = isActivated;
    this.changeVersion = changeVersion;
  }

  /** full constructor */
  public AbstractSubscriber(Carrier nwDmCarrierByNewCarrierId, Carrier nwDmCarrierByCarrierId,
      String subscriberExternalId, String phoneNumber, String imsi, String password, String newPassword, String state,
      String smsState, String pin, boolean askPermissionOnTrigger, Date notificationTime, long noSmsRetries,
      String smsMessageId, long msgSeqNo, boolean isActivated, String lastUpdatedBy, Date lastUpdatedTime,
      Date lastRegistrationTime, long changeVersion, Set nwDmDevices) {
    this.newCarrier = nwDmCarrierByNewCarrierId;
    this.carrier = nwDmCarrierByCarrierId;
    this.externalId = subscriberExternalId;
    this.phoneNumber = phoneNumber;
    this.IMSI = imsi;
    this.password = password;
    this.newPassword = newPassword;
    this.state = state;
    this.SMSState = smsState;
    this.pin = pin;
    this.askPermissionOnTrigger = askPermissionOnTrigger;
    this.notificationTime = notificationTime;
    this.noSMSRetries = noSmsRetries;
    this.SMSMessageId = smsMessageId;
    this.msgSeqNo = msgSeqNo;
    this.isActivated = isActivated;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedTime = lastUpdatedTime;
    this.lastRegistrationTime = lastRegistrationTime;
    this.changeVersion = changeVersion;
    this.devices = nwDmDevices;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long subscriberId) {
    this.ID = subscriberId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getNewCarrier()
   */
  public Carrier getNewCarrier() {
    return this.newCarrier;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setNewCarrier(com.npower.dm.hibernate.Carrier)
   */
  public void setNewCarrier(Carrier nwDmCarrierByNewCarrierId) {
    this.newCarrier = nwDmCarrierByNewCarrierId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getCarrier()
   */
  public Carrier getCarrier() {
    return this.carrier;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setCarrier(com.npower.dm.hibernate.Carrier)
   */
  public void setCarrier(Carrier nwDmCarrierByCarrierId) {
    this.carrier = nwDmCarrierByCarrierId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getExternalId()
   */
  public String getExternalId() {
    return this.externalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setExternalId(java.lang.String)
   */
  public void setExternalId(String subscriberExternalId) {
    this.externalId = subscriberExternalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getPhoneNumber()
   */
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setPhoneNumber(java.lang.String)
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getIMSI()
   */
  public String getIMSI() {
    return this.IMSI;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setIMSI(java.lang.String)
   */
  public void setIMSI(String imsi) {
    this.IMSI = imsi;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getPassword()
   */
  public String getPassword() {
    return this.password;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setPassword(java.lang.String)
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getNewPassword()
   */
  public String getNewPassword() {
    return this.newPassword;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setNewPassword(java.lang.String)
   */
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getState()
   */
  public String getState() {
    return this.state;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setState(java.lang.String)
   */
  public void setState(String state) {
    this.state = state;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getSMSState()
   */
  public String getSMSState() {
    return this.SMSState;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setSMSState(java.lang.String)
   */
  public void setSMSState(String smsState) {
    this.SMSState = smsState;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Subscriber#getBootstrapPinType()
   */
  public String getBootstrapPinType() {
    return bootstrapPinType ;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Subscriber#setBootstrapPinType(java.lang.String)
   */
  public void setBootstrapPinType(String bootstrapPinType) {
    this.bootstrapPinType = bootstrapPinType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getPin()
   */
  public String getPin() {
    return this.pin;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setPin(java.lang.String)
   */
  public void setPin(String pin) {
    this.pin = pin;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getAskPermissionOnTrigger()
   */
  public boolean getAskPermissionOnTrigger() {
    return this.askPermissionOnTrigger;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setAskPermissionOnTrigger(boolean)
   */
  public void setAskPermissionOnTrigger(boolean askPermissionOnTrigger) {
    this.askPermissionOnTrigger = askPermissionOnTrigger;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getNotificationTime()
   */
  public Date getNotificationTime() {
    return this.notificationTime;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setNotificationTime(java.util.Date)
   */
  public void setNotificationTime(Date notificationTime) {
    this.notificationTime = notificationTime;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getNoSMSRetries()
   */
  public long getNoSMSRetries() {
    return this.noSMSRetries;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setNoSMSRetries(long)
   */
  public void setNoSMSRetries(long noSmsRetries) {
    this.noSMSRetries = noSmsRetries;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getSMSMessageId()
   */
  public String getSMSMessageId() {
    return this.SMSMessageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setSMSMessageId(java.lang.String)
   */
  public void setSMSMessageId(String smsMessageId) {
    this.SMSMessageId = smsMessageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getMsgSeqNo()
   */
  public long getMsgSeqNo() {
    return this.msgSeqNo;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setMsgSeqNo(long)
   */
  public void setMsgSeqNo(long msgSeqNo) {
    this.msgSeqNo = msgSeqNo;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getIsActivated()
   */
  public boolean getIsActivated() {
    return this.isActivated;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setIsActivated(boolean)
   */
  public void setIsActivated(boolean isActivated) {
    this.isActivated = isActivated;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    return this.lastUpdatedTime;
  }

  public void setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getLastRegistrationTime()
   */
  public Date getLastRegistrationTime() {
    return this.lastRegistrationTime;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#setLastRegistrationTime(java.util.Date)
   */
  public void setLastRegistrationTime(Date lastRegistrationTime) {
    this.lastRegistrationTime = lastRegistrationTime;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Subscriber#getDevices()
   */
  public Set getDevices() {
    return this.devices;
  }

  public void setDevices(Set nwDmDevices) {
    this.devices = nwDmDevices;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  public ServiceProvider getServiceProvider() {
    return serviceProvider;
  }

  public void setServiceProvider(ServiceProvider serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  /**
   * @return the firstname
   */
  public String getFirstname() {
    return firstname;
  }

  /**
   * @param firstname the firstname to set
   */
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /**
   * @return the lastname
   */
  public String getLastname() {
    return lastname;
  }

  /**
   * @param lastname the lastname to set
   */
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

}