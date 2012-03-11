/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/Subscriber.java,v 1.7 2008/11/09 09:25:44 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/11/09 09:25:44 $
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
package com.npower.dm.core;

import java.util.Date;
import java.util.Set;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/11/09 09:25:44 $
 */
public interface Subscriber {

  /**
   * Constances related with SMS State.
   */
  public static final String SMS_STATE_QUEUED = "queued";

  public static final String SMS_STATE_ACCEPTED = "accepted";

  public static final String SMS_STATE_NO_NEED_NOTIFICATION = "does not need notification";

  /**
   * Constances related with SubscriberEntity State
   * 
   */
  public static final String STATE_INIT = "start";

  /**
   * Return ID
   * @return
   */
  public abstract long getID();

  /**
   * Reserved for future, and now equals with getCarrier()
   * @return
   */
  public abstract Carrier getNewCarrier();

  /**
   * Reserved for future, and now equals with setCarrier()
   * @param nwDmCarrierByNewCarrierId
   */
  public abstract void setNewCarrier(Carrier nwDmCarrierByNewCarrierId);

  /**
   * Return the carrier of this subscriber.
   * @return
   */
  public abstract Carrier getCarrier();

  /**
   * Set a carrier for this subscriber.
   * @param carrier
   */
  public abstract void setCarrier(Carrier carrier);

  /**
   * Return the subscriber's externalID.
   * @return
   */
  public abstract String getExternalId();

  /**
   * Set an external ID for this subscriber.
   * @param externalId
   */
  public abstract void setExternalId(String externalId);

  /**
   * Return phonenumber
   * @return
   */
  public abstract String getPhoneNumber();

  /**
   * Set a phonenumber for this subscriber.
   * @param phoneNumber
   */
  public abstract void setPhoneNumber(String phoneNumber);

  /**
   * Return subscriber's IMSI.
   * @return
   */
  public abstract String getIMSI();

  /**
   * Set an IMSI for this subscriber.
   * @param imsi
   */
  public abstract void setIMSI(String imsi);

  /**
   * Return the password of this subscriber.
   * @return
   */
  public abstract String getPassword();

  /**
   * Set a password for this.subscriber.
   * @param password
   */
  public abstract void setPassword(String password);

  /**
   * Return the newPassword of this subscriber.
   * @return
   */
  public abstract String getNewPassword();

  /**
   * Set a new password for this subscriber.
   * @param newPassword
   */
  public abstract void setNewPassword(String newPassword);

  /**
   * Return the state of this subscriber.
   * @return
   */
  public abstract String getState();

  /**
   * Set a state for this subscriber. 
   * @param state
   */
  public abstract void setState(String state);

  /**
   * Return the SMS state of this subscriber.
   * @return
   */
  public abstract String getSMSState();

  /**
   * Set the SMSState
   * @param smsState
   */
  public abstract void setSMSState(String smsState);

  /**
   * @return Returns the bootstrapPinType.
   */
  public String getBootstrapPinType();

  /**
   * @param bootstrapPinType The bootstrapPinType to set.
   */
  public void setBootstrapPinType(String bootstrapPinType);

  /**
   * Return the PIN
   * @return
   */
  public abstract String getPin();

  /**
   * Set the PIN
   * @param pin
   */
  public abstract void setPin(String pin);

  /**
   * If true , this subscriber is activated.
   * @return
   */
  public abstract boolean getIsActivated();

  /**
   * Set true, if this subscriber will be activated.
   * @param isActivated
   */
  public abstract void setIsActivated(boolean isActivated);

  /**
   * @return the firstname
   */
  public abstract String getFirstname();

  /**
   * @param firstname the firstname to set
   */
  public abstract void setFirstname(String firstname);

  /**
   * @return the lastname
   */
  public abstract String getLastname();

  /**
   * @param lastname the lastname to set
   */
  public abstract void setLastname(String lastname);

  /**
   * @return the email
   */
  public abstract String getEmail();

  /**
   * @param email the email to set
   */
  public abstract void setEmail(String email);

  /**
   * Return a set of device which be owned by this subscriber.
   * @return Return a Set of {@link com.npower.dm.core.Device} objects.
   */
  public abstract Set<Device> getDevices();
  
  /**
   * Return first actived device.
   * @return
   */
  public abstract Device getActivedDevice();

  /**
   * 
   * @return
   */
  public abstract boolean getAskPermissionOnTrigger();

  /**
   * 
   * @param askPermissionOnTrigger
   */
  public abstract void setAskPermissionOnTrigger(boolean askPermissionOnTrigger);

  /**
   * 
   * @return
   */
  public abstract Date getNotificationTime();

  /**
   * 
   * @param notificationTime
   */
  public abstract void setNotificationTime(Date notificationTime);

  /**
   * 
   * @return
   */
  public abstract long getNoSMSRetries();

  /**
   * 
   * @param noSmsRetries
   */
  public abstract void setNoSMSRetries(long noSmsRetries);

  /**
   * 
   * @return
   */
  public abstract String getSMSMessageId();

  /**
   * 
   * @param smsMessageId
   */
  public abstract void setSMSMessageId(String smsMessageId);

  /**
   * 
   * @return
   */
  public abstract long getMsgSeqNo();

  /**
   * Return the ServiceProvider of this subscriber.
   * @return
   */
  public abstract ServiceProvider getServiceProvider();

  /**
   * Set a ServiceProvider for this subscriber.
   * @param carrier
   */
  public abstract void setServiceProvider(ServiceProvider sp);

  /**
   * Return the lastRegistrationTime
   * @return
   */
  public abstract Date getLastRegistrationTime();

  /**
   * Set lastRegistrationTime
   * @param lastRegistrationTime
   */
  public abstract void setLastRegistrationTime(Date lastRegistrationTime);

  /**
   * 
   * @param msgSeqNo
   */
  public abstract void setMsgSeqNo(long msgSeqNo);


  /**
   * Getter 
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * Setter
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Getter
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * Getter
   * @return
   */
  public abstract long getChangeVersion();

  /**
   * @return
   */
  public abstract String getCreatedBy() ;

  /**
   * @param createdBy
   */
  public abstract void setCreatedBy(String createdBy);

  /**
   * @return
   */
  public abstract Date getCreatedTime();

  /**
   * @param createdTime
   */
  public abstract void setCreatedTime(Date createdTime);

}