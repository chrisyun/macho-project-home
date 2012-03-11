/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDevice.java,v 1.12 2008/06/05 10:34:41 zhao Exp $
 * $Revision: 1.12 $
 * $Date: 2008/06/05 10:34:41 $
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
import java.util.TreeSet;

import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceTree;
import com.npower.dm.core.Image;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Subscriber;
import com.npower.dm.core.Update;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.12 $ $Date: 2008/06/05 10:34:41 $
 */
public abstract class AbstractDevice implements java.io.Serializable, Device {

  // Fields

  private long ID;

  private Image cuurentImage;

  private Update update;

  private Subscriber subscriber;

  private ProvisionJobStatus inProgressDeviceProvReq;

  private Element4Provision prElement;

  private Model model;

  private DeviceTree deviceTree;

  private String externalId;

  private String manufacturerName;

  private long manufacturerId;

  private String modelName;

  private String description;

  private String subscriberPassword;

  private String subscriberState;

  private long subscriberCarrierId;

  /**
   * Default SMSState: SMS_STATE_NO_NEED_NOTIFICATION
   */
  private String SMSState = Subscriber.SMS_STATE_NO_NEED_NOTIFICATION;

  private boolean isActivated = true;
  
  private boolean booted = false;
  
  private int bootstrapAskCounter = 0;
  
  private Date lastBootstrapTime = null;
  
  /**
   * Default is NULL
   */
  private String bootstrapPinType = null;
  
  private String bootstrapUserPin = null;

  private long noSMSRetries;

  private String SMSMessageId;

  private Date timeSmsStateLastUpdated;

  private String daVersion = "0.2";

  private String uaVersion = "0.2";

  private String pedigreeId;

  private String lastUpdatedBy;

  private Date lastUpdatedTime = new Date();
  
  private Date createdTime = new Date();

  private String OMAServerNonce;

  private String OMAClientNonce;

  private String OMAServerPassword;

  private String OMAClientPassword;

  private String OMAClientUsername;

  private String OMAClientAuthScheme;

  private long guidCounter;

  private long changeVersion;

  private String subscriberPhoneNumber;

  private boolean subscriberAskPermTrig;

  private boolean subscriberIsActivated;

  private String subscriberCarrierName;

  private Set jobStates = new HashSet(0);

  private Set jobAdapters = new HashSet(0);

  private Set<ProvisionJobStatus> provisionJobStatus = new TreeSet<ProvisionJobStatus>();

  private Set deviceLogs = new HashSet(0);

  private Set sessionAuths = new HashSet(0);

  private Set deviceGroupDevices = new HashSet(0);

  private Set profileAssignments = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDevice() {
    super();
  }

  /** minimal constructor */
  public AbstractDevice(Subscriber nwDmSubscriber, Model nwDmModel, String deviceExternalId, String smsState,
      boolean isActivated, long noSmsRetries, String daVersion, String uaVersion, long guidCounter, long changeVersion,
      String manufacturerName, String modelName, long manufacturerId, String subscriberPhoneNumber,
      String subscriberPassword, String subscriberState, boolean subscriberAskPermTrig, long subscriberCarrierId,
      boolean subscriberIsActivated, String subscriberCarrierName) {
    this.subscriber = nwDmSubscriber;
    this.model = nwDmModel;
    this.externalId = deviceExternalId;
    this.SMSState = smsState;
    this.isActivated = isActivated;
    this.noSMSRetries = noSmsRetries;
    this.daVersion = daVersion;
    this.uaVersion = uaVersion;
    this.guidCounter = guidCounter;
    this.changeVersion = changeVersion;
    this.manufacturerName = manufacturerName;
    this.modelName = modelName;
    this.manufacturerId = manufacturerId;
    this.subscriberPhoneNumber = subscriberPhoneNumber;
    this.subscriberPassword = subscriberPassword;
    this.subscriberState = subscriberState;
    this.subscriberAskPermTrig = subscriberAskPermTrig;
    this.subscriberCarrierId = subscriberCarrierId;
    this.subscriberIsActivated = subscriberIsActivated;
    this.subscriberCarrierName = subscriberCarrierName;
  }

  /** full constructor */
  public AbstractDevice(Image nwDmImage, Update nwDmUpdate, Subscriber nwDmSubscriber,
      ProvisionJobStatus nwDmDeviceProvReq, Element4Provision nwDmPrElement, Model nwDmModel, DeviceTree nwDmDtree,
      String deviceExternalId, String description, String smsState, boolean isActivated, long noSmsRetries,
      String smsMessageId, Date timeSmsStateLastUpdated, String daVersion, String uaVersion, String pedigreeId,
      String lastUpdatedBy, Date lastUpdatedTime, String omaServerNonce, String omaClientNonce,
      String omaServerPassword, String omaClientPassword, String omaClientUsername, String omaClientAuthScheme,
      long guidCounter, long changeVersion, String manufacturerName, String modelName, long manufacturerId,
      String subscriberPhoneNumber, String subscriberPassword, String subscriberState, boolean subscriberAskPermTrig,
      long subscriberCarrierId, boolean subscriberIsActivated, String subscriberCarrierName, Set nwDmJobStates,
      Set nwDmDmJobAdapters, Set nwDmDeviceProvReqs, Set nwDmDeviceLogs, Set nwDmSessionAuths,
      Set nwDmDeviceGroupDevices, Set nwDmProfileAssignments) {
    this.cuurentImage = nwDmImage;
    this.update = nwDmUpdate;
    this.subscriber = nwDmSubscriber;
    this.inProgressDeviceProvReq = nwDmDeviceProvReq;
    this.prElement = nwDmPrElement;
    this.model = nwDmModel;
    this.deviceTree = nwDmDtree;
    this.externalId = deviceExternalId;
    this.description = description;
    this.SMSState = smsState;
    this.isActivated = isActivated;
    this.noSMSRetries = noSmsRetries;
    this.SMSMessageId = smsMessageId;
    this.timeSmsStateLastUpdated = timeSmsStateLastUpdated;
    this.daVersion = daVersion;
    this.uaVersion = uaVersion;
    this.pedigreeId = pedigreeId;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedTime = lastUpdatedTime;
    this.OMAServerNonce = omaServerNonce;
    this.OMAClientNonce = omaClientNonce;
    this.OMAServerPassword = omaServerPassword;
    this.OMAClientPassword = omaClientPassword;
    this.OMAClientUsername = omaClientUsername;
    this.OMAClientAuthScheme = omaClientAuthScheme;
    this.guidCounter = guidCounter;
    this.changeVersion = changeVersion;
    this.manufacturerName = manufacturerName;
    this.modelName = modelName;
    this.manufacturerId = manufacturerId;
    this.subscriberPhoneNumber = subscriberPhoneNumber;
    this.subscriberPassword = subscriberPassword;
    this.subscriberState = subscriberState;
    this.subscriberAskPermTrig = subscriberAskPermTrig;
    this.subscriberCarrierId = subscriberCarrierId;
    this.subscriberIsActivated = subscriberIsActivated;
    this.subscriberCarrierName = subscriberCarrierName;
    this.jobStates = nwDmJobStates;
    this.jobAdapters = nwDmDmJobAdapters;
    this.provisionJobStatus = nwDmDeviceProvReqs;
    this.deviceLogs = nwDmDeviceLogs;
    this.sessionAuths = nwDmSessionAuths;
    this.deviceGroupDevices = nwDmDeviceGroupDevices;
    this.profileAssignments = nwDmProfileAssignments;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long deviceId) {
    this.ID = deviceId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getCuurentImage()
   */
  public Image getCuurentImage() {
    return this.cuurentImage;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setCuurentImage(com.npower.dm.hibernate.ImageEntity)
   */
  public void setCuurentImage(Image nwDmImage) {
    this.cuurentImage = nwDmImage;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getUpdate()
   */
  public Update getUpdate() {
    return this.update;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setUpdate(com.npower.dm.hibernate.UpdateEntity)
   */
  public void setUpdate(Update nwDmUpdate) {
    this.update = nwDmUpdate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getSubscriber()
   */
  public Subscriber getSubscriber() {
    return this.subscriber;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setSubscriber(com.npower.dm.hibernate.Subscriber)
   */
  public void setSubscriber(Subscriber nwDmSubscriber) {
    this.subscriber = nwDmSubscriber;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getDeviceProvReq()
   */
  public ProvisionJobStatus getInProgressDeviceProvReq() {
    return this.inProgressDeviceProvReq;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setDeviceProvReq(com.npower.dm.hibernate.DeviceProvisionRequest)
   */
  public void setInProgressDeviceProvReq(ProvisionJobStatus nwDmDeviceProvReq) {
    this.inProgressDeviceProvReq = nwDmDeviceProvReq;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getPrElement()
   */
  public Element4Provision getPrElement() {
    return this.prElement;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setPrElement(com.npower.dm.hibernate.Element4Provision)
   */
  public void setPrElement(Element4Provision nwDmPrElement) {
    this.prElement = nwDmPrElement;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getModel()
   */
  public Model getModel() {
    return this.model;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setModel(com.npower.dm.hibernate.Model)
   */
  public void setModel(Model nwDmModel) {
    this.model = nwDmModel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getDeviceTree()
   */
  public DeviceTree getDeviceTree() {
    return this.deviceTree;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setDeviceTree(com.npower.dm.hibernate.DeviceTree)
   */
  public void setDeviceTree(DeviceTree nwDmDtree) {
    this.deviceTree = nwDmDtree;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getExternalId()
   */
  public String getExternalId() {
    return this.externalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setExternalId(java.lang.String)
   */
  public void setExternalId(String deviceExternalId) {
    this.externalId = deviceExternalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getSMSState()
   */
  public String getSMSState() {
    return this.SMSState;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setSMSState(java.lang.String)
   */
  public void setSMSState(String smsState) {
    this.SMSState = smsState;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getIsActivated()
   */
  public boolean getIsActivated() {
    return this.isActivated;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setIsActivated(boolean)
   */
  public void setIsActivated(boolean isActivated) {
    this.isActivated = isActivated;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getNoSMSRetries()
   */
  public long getNoSMSRetries() {
    return this.noSMSRetries;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setNoSMSRetries(long)
   */
  public void setNoSMSRetries(long noSmsRetries) {
    this.noSMSRetries = noSmsRetries;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getSMSMessageId()
   */
  public String getSMSMessageId() {
    return this.SMSMessageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setSMSMessageId(java.lang.String)
   */
  public void setSMSMessageId(String smsMessageId) {
    this.SMSMessageId = smsMessageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getTimeSmsStateLastUpdated()
   */
  public Date getTimeSmsStateLastUpdated() {
    return this.timeSmsStateLastUpdated;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setTimeSmsStateLastUpdated(java.util.Date)
   */
  public void setTimeSmsStateLastUpdated(Date timeSmsStateLastUpdated) {
    this.timeSmsStateLastUpdated = timeSmsStateLastUpdated;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getDaVersion()
   */
  public String getDaVersion() {
    return this.daVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setDaVersion(java.lang.String)
   */
  public void setDaVersion(String daVersion) {
    this.daVersion = daVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getUaVersion()
   */
  public String getUaVersion() {
    return this.uaVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setUaVersion(java.lang.String)
   */
  public void setUaVersion(String uaVersion) {
    this.uaVersion = uaVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getPedigreeId()
   */
  public String getPedigreeId() {
    return this.pedigreeId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setPedigreeId(java.lang.String)
   */
  public void setPedigreeId(String pedigreeId) {
    this.pedigreeId = pedigreeId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getLastUpdatedTime()
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
   * @see com.npower.dm.hibernate.Device#getOMAServerNonce()
   */
  public String getOMAServerNonce() {
    return this.OMAServerNonce;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setOMAServerNonce(java.lang.String)
   */
  public void setOMAServerNonce(String omaServerNonce) {
    this.OMAServerNonce = omaServerNonce;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getOMAClientNonce()
   */
  public String getOMAClientNonce() {
    return this.OMAClientNonce;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setOMAClientNonce(java.lang.String)
   */
  public void setOMAClientNonce(String omaClientNonce) {
    this.OMAClientNonce = omaClientNonce;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getOMAServerPassword()
   */
  public String getOMAServerPassword() {
    return this.OMAServerPassword;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setOMAServerPassword(java.lang.String)
   */
  public void setOMAServerPassword(String omaServerPassword) {
    this.OMAServerPassword = omaServerPassword;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getOMAClientPassword()
   */
  public String getOMAClientPassword() {
    return this.OMAClientPassword;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setOMAClientPassword(java.lang.String)
   */
  public void setOMAClientPassword(String omaClientPassword) {
    this.OMAClientPassword = omaClientPassword;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getOMAClientUsername()
   */
  public String getOMAClientUsername() {
    return this.OMAClientUsername;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setOMAClientUsername(java.lang.String)
   */
  public void setOMAClientUsername(String omaClientUsername) {
    this.OMAClientUsername = omaClientUsername;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getOMAClientAuthScheme()
   */
  public String getOMAClientAuthScheme() {
    return this.OMAClientAuthScheme;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setOMAClientAuthScheme(java.lang.String)
   */
  public void setOMAClientAuthScheme(String omaClientAuthScheme) {
    this.OMAClientAuthScheme = omaClientAuthScheme;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getGuidCounter()
   */
  public long getGuidCounter() {
    return this.guidCounter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setGuidCounter(long)
   */
  public void setGuidCounter(long guidCounter) {
    this.guidCounter = guidCounter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getChangeVersion()
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
   * @see com.npower.dm.hibernate.Device#getManufacturerName()
   */
  public String getManufacturerName() {
    return this.manufacturerName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setManufacturerName(java.lang.String)
   */
  public void setManufacturerName(String manufacturerName) {
    this.manufacturerName = manufacturerName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getModelName()
   */
  public String getModelName() {
    return this.modelName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setModelName(java.lang.String)
   */
  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getManufacturerId()
   */
  public long getManufacturerId() {
    return this.manufacturerId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setManufacturerId(long)
   */
  public void setManufacturerId(long manufacturerId) {
    this.manufacturerId = manufacturerId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getSubscriberPhoneNumber()
   */
  public String getSubscriberPhoneNumber() {
    return this.subscriberPhoneNumber;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setSubscriberPhoneNumber(java.lang.String)
   */
  public void setSubscriberPhoneNumber(String subscriberPhoneNumber) {
    this.subscriberPhoneNumber = subscriberPhoneNumber;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getSubscriberPassword()
   */
  public String getSubscriberPassword() {
    return this.subscriberPassword;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setSubscriberPassword(java.lang.String)
   */
  public void setSubscriberPassword(String subscriberPassword) {
    this.subscriberPassword = subscriberPassword;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getSubscriberState()
   */
  public String getSubscriberState() {
    return this.subscriberState;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setSubscriberState(java.lang.String)
   */
  public void setSubscriberState(String subscriberState) {
    this.subscriberState = subscriberState;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getSubscriberAskPermTrig()
   */
  public boolean getSubscriberAskPermTrig() {
    return this.subscriberAskPermTrig;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setSubscriberAskPermTrig(boolean)
   */
  public void setSubscriberAskPermTrig(boolean subscriberAskPermTrig) {
    this.subscriberAskPermTrig = subscriberAskPermTrig;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getSubscriberCarrierId()
   */
  public long getSubscriberCarrierId() {
    return this.subscriberCarrierId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setSubscriberCarrierId(long)
   */
  public void setSubscriberCarrierId(long subscriberCarrierId) {
    this.subscriberCarrierId = subscriberCarrierId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getSubscriberIsActivated()
   */
  public boolean getSubscriberIsActivated() {
    return this.subscriberIsActivated;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setSubscriberIsActivated(boolean)
   */
  public void setSubscriberIsActivated(boolean subscriberIsActivated) {
    this.subscriberIsActivated = subscriberIsActivated;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getSubscriberCarrierName()
   */
  public String getSubscriberCarrierName() {
    return this.subscriberCarrierName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#setSubscriberCarrierName(java.lang.String)
   */
  public void setSubscriberCarrierName(String subscriberCarrierName) {
    this.subscriberCarrierName = subscriberCarrierName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getJobStates()
   */
  public Set getJobStates() {
    return this.jobStates;
  }

  public void setJobStates(Set nwDmJobStates) {
    this.jobStates = nwDmJobStates;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getJobAdapters()
   */
  public Set getJobAdapters() {
    return this.jobAdapters;
  }

  public void setJobAdapters(Set nwDmDmJobAdapters) {
    this.jobAdapters = nwDmDmJobAdapters;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#getProvisionJobStatus()
   */
  public Set<ProvisionJobStatus> getProvisionJobStatus() {
    return this.provisionJobStatus;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#setProvisionJobStatus(java.util.Set)
   */
  public void setProvisionJobStatus(Set<ProvisionJobStatus> nwDmDeviceProvReqs) {
    this.provisionJobStatus = nwDmDeviceProvReqs;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getDeviceLogs()
   */
  public Set getDeviceLogs() {
    return this.deviceLogs;
  }

  public void setDeviceLogs(Set nwDmDeviceLogs) {
    this.deviceLogs = nwDmDeviceLogs;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getSessionAuths()
   */
  public Set getSessionAuths() {
    return this.sessionAuths;
  }

  public void setSessionAuths(Set nwDmSessionAuths) {
    this.sessionAuths = nwDmSessionAuths;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getDeviceGroupDevices()
   */
  public Set getDeviceGroupDevices() {
    return this.deviceGroupDevices;
  }

  public void setDeviceGroupDevices(Set nwDmDeviceGroupDevices) {
    this.deviceGroupDevices = nwDmDeviceGroupDevices;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Device#getProfileAssignments()
   */
  public Set getProfileAssignments() {
    return this.profileAssignments;
  }

  public void setProfileAssignments(Set nwDmProfileAssignments) {
    this.profileAssignments = nwDmProfileAssignments;
  }

  /**
   * @return the createdTime
   */
  public Date getCreatedTime() {
    return createdTime;
  }

  /**
   * @param createdTime the createdTime to set
   */
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#isBooted()
   */
  public boolean isBooted() {
    return booted;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#setBooted(boolean)
   */
  public void setBooted(boolean booted) {
    this.booted = booted;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#getBootstrapAskCounter()
   */
  public int getBootstrapAskCounter() {
    return bootstrapAskCounter;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#setBootstrapAskCounter(int)
   */
  public void setBootstrapAskCounter(int bootstrapAskCounter) {
    this.bootstrapAskCounter = bootstrapAskCounter;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#getLastBootstrapTime()
   */
  public Date getLastBootstrapTime() {
    return lastBootstrapTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#setLastBootstrapTime(java.util.Date)
   */
  public void setLastBootstrapTime(Date lastBootstrapTime) {
    this.lastBootstrapTime = lastBootstrapTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#getBootstrapPinType()
   */
  public String getBootstrapPinType() {
    return bootstrapPinType;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#setBootstrapPinType(java.lang.String)
   */
  public void setBootstrapPinType(String bootstrapPinType) {
    this.bootstrapPinType = bootstrapPinType;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#getBootstrapUserPin()
   */
  public String getBootstrapUserPin() {
    return bootstrapUserPin;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#setBootstrapUserPin(java.lang.String)
   */
  public void setBootstrapUserPin(String bootstrapUserPin) {
    this.bootstrapUserPin = bootstrapUserPin;
  }

}