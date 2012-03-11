/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/Device.java,v 1.15 2008/03/09 05:39:03 zhao Exp $
 * $Revision: 1.15 $
 * $Date: 2008/03/09 05:39:03 $
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

import sync4j.framework.core.Constants;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.15 $ $Date: 2008/03/09 05:39:03 $
 */
public interface Device extends Comparable<Device> {
  
  public static final String AUTH_TYPE_BASIC = Constants.AUTH_TYPE_BASIC;
  
  public static final String AUTH_TYPE_MD5 = Constants.AUTH_TYPE_MD5;
  
  public static final String AUTH_TYPE_HMAC = Constants.AUTH_TYPE_HMAC;

  /**
   * Return ID
   * @return
   */
  public abstract long getID();
  
  /**
   * Return the current image of this device
   * @return
   */
  public abstract Image getCuurentImage();

  /**Set the current image of this device.
   * 
   * @param image
   */
  public abstract void setCuurentImage(Image image);

  /**
   * Return the Update of this device
   * @return
   */
  public abstract Update getUpdate();

  /**
   * Set an update for this device.
   * @param update
   */
  public abstract void setUpdate(Update update);

  /**
   * Return the subscriber of this device.
   * @return
   */
  public abstract Subscriber getSubscriber();

  /**
   * Boudle a subscriber to this device.
   * @param subscriber
   */
  public abstract void setSubscriber(Subscriber subscriber);

  /**
   * Return the model's name of this device.
   * @return
   */
  public abstract Model getModel();

  /**
   * Boudle the model to this device.
   * @param model
   */
  public abstract void setModel(Model model);

  /**
   * Return the externalID of this device.
   * In mobile phone, the external ID is IMEI.
   * @return
   */
  public abstract String getExternalId();

  /**
   * Set an externalID for this device.<br>
   * In mobile phone, the external ID is IMEI.
   * @param deviceExternalId
   */
  public abstract void setExternalId(String deviceExternalId);

  /**
   * Return description
   * @return
   */
  public abstract String getDescription();

  /**
   * set description
   * @param description
   */
  public abstract void setDescription(String description);
  
  /**
   * Return true , if this device is activated.
   * @return
   */
  public abstract boolean getIsActivated();

  /**
   * Set true, if this device id activated.
   * @param isActivated
   */
  public abstract void setIsActivated(boolean isActivated);


  /**
   * Return the manufacturer's name of this device.
   * @return
   */
  public abstract String getManufacturerName();

  //public abstract void setManufacturerName(String manufacturerName);

  /**
   * Return the model's name of this device.
   */
  public abstract String getModelName();

  //public abstract void setModelName(String modelName);

  /**
   * Return the manufacturID of this device
   * @return
   */
  public abstract long getManufacturerId();

  //public abstract void setManufacturerId(long manufacturerId);

  /**
   * Return the subscriber's phonenumber of this device
   * @return
   */
  public abstract String getSubscriberPhoneNumber();

  public abstract void setSubscriberPhoneNumber(String subscriberPhoneNumber);

  /**
   * Return subscriber's password
   * @return
   */
  public abstract String getSubscriberPassword();

  //public abstract void setSubscriberPassword(String subscriberPassword);
  
  /**
   * If return true, the device has been booted
   * @return Returns the booted.
   */
  public boolean isBooted();

  /**
   * @param booted The booted to set.
   */
  public void setBooted(boolean booted);

  /**
   * 
   * @return Returns the bootstrapAskCounter.
   */
  public int getBootstrapAskCounter();

  /**
   * @param bootstrapAskCounter The bootstrapAskCounter to set.
   */
  public void setBootstrapAskCounter(int bootstrapAskCounter);

  /**
   * @return Returns the lastBootstrapTime.
   */
  public Date getLastBootstrapTime();

  /**
   * @param lastBootstrapTime The lastBootstrapTime to set.
   */
  public void setLastBootstrapTime(Date lastBootstrapTime);

  /**
   * @return Returns the bootstrapPinType.
   */
  public String getBootstrapPinType();

  /**
   * @param bootstrapPinType The bootstrapPinType to set.
   */
  public void setBootstrapPinType(String bootstrapPinType);

  /**
   * @return Returns the bootstrapUserPin.
   */
  public String getBootstrapUserPin();

  /**
   * @param bootstrapUserPin The bootstrapUserPin to set.
   */
  public void setBootstrapUserPin(String bootstrapUserPin);

  /**
   * Return subscriber's state
   * @return
   */
  public abstract String getSubscriberState();

  
  //public abstract void setSubscriberState(String subscriberState);

  /**
   * Return subscriber's AskPermTrigger
   * @return
   */
  public abstract boolean getSubscriberAskPermTrig();

  //public abstract void setSubscriberAskPermTrig(boolean subscriberAskPermTrig);

  /**
   * Return subscriber's carrierID
   * @return
   */
  public abstract long getSubscriberCarrierId();

  //public abstract void setSubscriberCarrierId(long subscriberCarrierId);
  
  /**
   * Return true, if subscriber of this device is activated.
   * @return
   */
  public abstract boolean getSubscriberIsActivated();

  //public abstract void setSubscriberIsActivated(boolean subscriberIsActivated);
  
  /**
   * 
   * @return
   */
  public abstract String getSubscriberCarrierName();

  public abstract void setSubscriberCarrierName(String subscriberCarrierName);
  
  /**
   * Return the nonce of server
   * @return
   */
  public abstract String getOMAServerNonce();

  /**
   * Set the nonce of server
   * @param serverNonce
   */
  public abstract void setOMAServerNonce(String serverNonce);

  /**
   * Return the nonce of clinet.
   * @return
   */
  public abstract String getOMAClientNonce();

  /**
   * Set a client's nonce.
   * @param clientNonce
   */
  public abstract void setOMAClientNonce(String clientNonce);

  /**
   * Return the server's password
   * @return
   */
  public abstract String getOMAServerPassword();

  /**
   * Set a server's password
   * @param serverPassword
   */
  public abstract void setOMAServerPassword(String serverPassword);

  /**
   * Return the client's password
   * @return
   */
  public abstract String getOMAClientPassword();

  /**
   * Set a client password
   * @param clientPassword
   */
  public abstract void setOMAClientPassword(String clientPassword);

  /**
   * Return the client username.
   * @return
   */
  public abstract String getOMAClientUsername();

  /**
   * Return the client username
   * @param clientUsername
   */
  public abstract void setOMAClientUsername(String clientUsername);

  /**
   * Return the authentication's schema of client.
   * @return
   */
  public abstract String getOMAClientAuthScheme();

  /**
   * Set tht authentication schema of the client.
   * @param clientAuthScheme
   */
  public abstract void setOMAClientAuthScheme(String clientAuthScheme);
  
  /**
   * Return device tree
   * @return
   */
  public abstract DeviceTree getDeviceTree();

  /**
   * Set device tree
   * @param dtree
   */
  public abstract void setDeviceTree(DeviceTree dtree);

  /**
   * Return all of provision job status
   * @return
   */
  public Set<ProvisionJobStatus> getProvisionJobStatus();

  /**
   * Set all of provision job status
   * @param status
   */
  public void setProvisionJobStatus(Set<ProvisionJobStatus> status);
  
  /**
   * Return in progress provision job request
   * @return
   */
  public ProvisionJobStatus getInProgressDeviceProvReq();

  /**
   * Set in progress provision job request
   * @param deviceProvReq
   */
  public void setInProgressDeviceProvReq(ProvisionJobStatus deviceProvReq);

  /*  
  public abstract Element4Provision getPrElement();

  public abstract void setPrElement(Element4Provision prElement);

  public abstract String getSMSState();

  public abstract void setSMSState(String smsState);

  public abstract long getNoSMSRetries();

  public abstract void setNoSMSRetries(long noSmsRetries);

  public abstract String getSMSMessageId();

  public abstract void setSMSMessageId(String smsMessageId);

  public abstract Date getTimeSmsStateLastUpdated();

  public abstract void setTimeSmsStateLastUpdated(Date timeSmsStateLastUpdated);

  public abstract String getDaVersion();

  public abstract void setDaVersion(String daVersion);

  public abstract String getUaVersion();

  public abstract void setUaVersion(String uaVersion);

  public abstract String getPedigreeId();

  public abstract void setPedigreeId(String pedigreeId);
  


  public abstract long getGuidCounter();

  public abstract void setGuidCounter(long guidCounter);
  */
  
  /*
  
  */
  /*
  public abstract Set getJobStates();

  public abstract Set getJobAdapters();

  public abstract Set getDeviceProvReqs();

  public abstract Set getDeviceLogs();

  public abstract Set getSessionAuths();

  public abstract Set getDeviceGroupDevices();
  */
  /**
   * Return a set of ProfileAssignment to this device.
   * @return Return a Set of {@link com.npower.dm.core.ProfileAssignment} objects.
   */
  public abstract Set<ProfileAssignment> getProfileAssignments();
  
  /**
   * Return a set of DeviceGroups which contain this Device.
   * @return
   * @throws DMException
   */
  public abstract Set<DeviceGroup> getDeviceGroups() throws DMException;
  
  /**
   * Getter of LastUpdatedBy
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * Setter of LastUpdatedBy
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Getter of LastUpdatedTime
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * @return the createdTime
   */
  public Date getCreatedTime();
  
  /**
   * Getter of ChangeVersion
   * @return
   */
  public abstract long getChangeVersion();
  

}