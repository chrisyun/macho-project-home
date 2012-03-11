/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractCarrier.java,v 1.7 2009/02/17 03:38:59 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2009/02/17 03:38:59 $
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

import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.ProfileConfig;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2009/02/17 03:38:59 $
 */
public abstract class AbstractCarrier implements java.io.Serializable, Carrier {

  // Fields
  /**
   * ID of CarrierEntity
   */
  private long ID;

  /**
   * ProfileConfigEntity for Bootstrap, this profile must be type of NAP or
   * Proxy. Using to attach with bootstrap config.
   */
  private ProfileConfig bootstrapNapProfile;

  /**
   * ProfileConfigEntity for Bootstrap, this profile must be type of NAP or
   * Proxy. Using to attach with bootstrap config.
   */
  private ProfileConfig bootstrapDmProfile;

  /**
   * CountryEntity of the CarrierEntity
   */
  private Country country;

  /**
   * EnternalID of CarrierEntity
   */
  private String externalID;

  /**
   * Name of CarrierEntity
   */
  private String name;
  
  /**
   * Phone number policy
   */
  private String phoneNumberPolicy;

  /**
   * Parent of this carrier.
   */
  private Carrier parent;
  
  /**
   * Authentication Type of the CarrierEntity. Types include:
   * SERVER_AUTH_TYPE_BASIC SERVER_AUTH_TYPE_DIGEST SERVER_AUTH_TYPE_HMAC
   * SERVER_AUTH_TYPE_NONE
   * 
   * The constances of the type are defined by CarrierEntity class.
   * 
   * @see com.npower.hibernate.Carrier
   */
  private String serverAuthType;

  /**
   * Notification Type of the CarrierEntity, Types include:
   * NOTIFICATION_TYPE_WAP_PUSH
   * 
   * The constances of the type are defined by CarrierEntity class.
   * 
   * @see com.npower.hibernate.Carrier
   */
  private String notificationType;

  /**
   * Properties of the CarrierEntity.
   * 
   * For exmaple: "name1=value1;name1=value2;...
   * 
   */
  private String notificationProperties;

  /**
   * Timeout in seconds for notification service.
   */
  private long notificationStateTimeout;

  /**
   * Timeout in seconds for bootstrap service.
   */
  private long bootstrapTimeout;
  
  /**
   * 
   */
  private int bootstrapMaxRetries = 3;
  
  /**
   * 
   */
  private String defaultBootstrapPinType = Byte.toString(OMACPSecurityMethod.USERPIN.getValue());
  
  /**
   * 
   */
  private String defaultBootstrapUserPin = Carrier.DEFAULT_BOOTSTRAP_USER_PIN;

  /**
   * Maxium retrying number for notification services. The notification service
   * will retry after failure until reaching this parameter.
   */
  private long notificationMaxNumRetries;

  /**
   * Description of the carrier.
   */
  private String description;

  private String lastUpdatedBy;

  private Date lastUpdatedTime;

  private long changeVersion = 0L;

  /**
   * Set of the ProfileConfigEntity for this carrier. Order by
   * ProfileConfigEntity's name ASC.
   */
  private Set profileConfigs = new TreeSet();

  /**
   * Set of the ImageCarrieres for this carrier.
   */
  private Set imageCarrierses = new HashSet(0);

  /**
   * Set of the subscribers for this carrier.
   */
  private Set subscribers = new HashSet(0);

  /**
   * Reserved for future requirement.
   */
  private Set subscribersForNewCarrierId = new HashSet(0);

  /**
   * Provision elements
   */
  private Set prElements = new HashSet(0);

  /**
   * All of Updates for the carrier.
   */
  private Set updateCarrierses = new HashSet(0);

  /**
   * 以秒为单位的任务缺省超期时间, 从任务调度时间开始, 当经历超期时间所指定的 时间间隔后, 
   * 仍然没有正常运行结束, 将自动被撤销.
   * 缺省值为7天.
   */
  private long defaultJobExpiredTimeInSeconds = 7 * 24 * 3600;

  // Constructors

  /** default constructor */
  public AbstractCarrier() {
    super();
  }

  /**
   * Constructor
   * 
   * @param nwDmCountry
   * @param carrierExternalId
   * @param name
   * @param serverAuthType
   * @param notificationType
   * @param notificationStateTimeout
   * @param bootstrapTimeout
   * @param notificationMaxNumRetries
   */
  public AbstractCarrier(Country nwDmCountry, String carrierExternalId, String name, String serverAuthType,
      String notificationType, long notificationStateTimeout, long bootstrapTimeout, long notificationMaxNumRetries) {

    this.country = nwDmCountry;
    this.externalID = carrierExternalId;
    this.name = name;
    this.serverAuthType = serverAuthType;
    this.notificationType = notificationType;
    this.notificationStateTimeout = notificationStateTimeout;
    this.bootstrapTimeout = bootstrapTimeout;
    this.notificationMaxNumRetries = notificationMaxNumRetries;
  }

  /**
   * Constrauctor
   * 
   * @param nwDmProfileConfig
   * @param nwDmCountry
   * @param carrierExternalId
   * @param name
   * @param serverAuthType
   * @param notificationType
   * @param notificationProperties
   * @param notificationStateTimeout
   * @param bootstrapTimeout
   * @param notificationMaxNumRetries
   * @param lastUpdatedBy
   * @param lastUpdatedTime
   * @param description
   */
  public AbstractCarrier(ProfileConfig nwDmProfileConfig, Country nwDmCountry, String carrierExternalId, String name,
      String serverAuthType, String notificationType, String notificationProperties, long notificationStateTimeout,
      long bootstrapTimeout, long notificationMaxNumRetries, String lastUpdatedBy, Date lastUpdatedTime,
      String description) {

    this.bootstrapNapProfile = nwDmProfileConfig;
    this.country = nwDmCountry;
    this.externalID = carrierExternalId;
    this.name = name;
    this.serverAuthType = serverAuthType;
    this.notificationType = notificationType;
    this.notificationProperties = notificationProperties;
    this.notificationStateTimeout = notificationStateTimeout;
    this.bootstrapTimeout = bootstrapTimeout;
    this.notificationMaxNumRetries = notificationMaxNumRetries;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedTime = lastUpdatedTime;
    this.description = description;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long carrierId) {
    this.ID = carrierId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getBootstrapProfileConfig()
   */
  public ProfileConfig getBootstrapNapProfile() {
    return this.bootstrapNapProfile;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setBootstrapProfileConfig(com.npower.dm.hibernate.ProfileConfigEntity)
   */
  public void setBootstrapNapProfile(ProfileConfig nwDmProfileConfig) {
    this.bootstrapNapProfile = nwDmProfileConfig;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getCountry()
   */
  public Country getCountry() {
    return this.country;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setCountry(com.npower.dm.hibernate.Country)
   */
  public void setCountry(Country nwDmCountry) {
    this.country = nwDmCountry;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getExternalID()
   */
  public String getExternalID() {
    return this.externalID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setExternalID(java.lang.String)
   */
  public void setExternalID(String carrierExternalId) {
    this.externalID = carrierExternalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getServerAuthType()
   */
  public String getServerAuthType() {
    return this.serverAuthType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setServerAuthType(java.lang.String)
   */
  public void setServerAuthType(String serverAuthType) {
    this.serverAuthType = serverAuthType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getNotificationType()
   */
  public String getNotificationType() {
    return this.notificationType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setNotificationType(java.lang.String)
   */
  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getNotificationProperties()
   */
  public String getNotificationProperties() {
    return this.notificationProperties;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setNotificationProperties(java.lang.String)
   */
  public void setNotificationProperties(String notificationProperties) {
    this.notificationProperties = notificationProperties;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getNotificationStateTimeout()
   */
  public long getNotificationStateTimeout() {
    return this.notificationStateTimeout;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setNotificationStateTimeout(long)
   */
  public void setNotificationStateTimeout(long notificationStateTimeout) {
    this.notificationStateTimeout = notificationStateTimeout;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getBootstrapTimeout()
   */
  public long getBootstrapTimeout() {
    return this.bootstrapTimeout;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setBootstrapTimeout(long)
   */
  public void setBootstrapTimeout(long bootstrapTimeout) {
    this.bootstrapTimeout = bootstrapTimeout;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getNotificationMaxNumRetries()
   */
  public long getNotificationMaxNumRetries() {
    return this.notificationMaxNumRetries;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setNotificationMaxNumRetries(long)
   */
  public void setNotificationMaxNumRetries(long notificationMaxNumRetries) {
    this.notificationMaxNumRetries = notificationMaxNumRetries;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getLastUpdatedTime()
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
   * @see com.npower.dm.hibernate.Carrier#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getChangeVersion()
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
   * @see com.npower.dm.hibernate.Carrier#getProfileConfigs()
   */
  public Set getProfileConfigs() {
    return this.profileConfigs;
  }

  public void setProfileConfigs(Set nwDmProfileConfigs) {
    this.profileConfigs = nwDmProfileConfigs;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getImageCarrierses()
   */
  public Set getImageCarrierses() {
    return this.imageCarrierses;
  }

  public void setImageCarrierses(Set nwDmImageCarrierses) {
    this.imageCarrierses = nwDmImageCarrierses;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getSubscribersForCarrierId()
   */
  public Set getSubscribers() {
    return this.subscribers;
  }

  public void setSubscribers(Set nwDmSubscribersForCarrierId) {
    this.subscribers = nwDmSubscribersForCarrierId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getSubscribersForNewCarrierId()
   */
  public Set getSubscribersForNewCarrierId() {
    return this.subscribersForNewCarrierId;
  }

  public void setSubscribersForNewCarrierId(Set nwDmSubscribersForNewCarrierId) {
    this.subscribersForNewCarrierId = nwDmSubscribersForNewCarrierId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getPrElements()
   */
  public Set getPrElements() {
    return this.prElements;
  }

  public void setPrElements(Set nwDmPrElements) {
    this.prElements = nwDmPrElements;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getUpdateCarrierses()
   */
  public Set getUpdateCarrierses() {
    return this.updateCarrierses;
  }

  public void setUpdateCarrierses(Set nwDmUpdateCarrierses) {
    this.updateCarrierses = nwDmUpdateCarrierses;
  }

  /**
   * @return the phoneNumberPolicy
   */
  public String getPhoneNumberPolicy() {
    return phoneNumberPolicy;
  }

  /**
   * @param phoneNumberPolicy the phoneNumberPolicy to set
   */
  public void setPhoneNumberPolicy(String phoneNumberPolicy) {
    this.phoneNumberPolicy = phoneNumberPolicy;
  }

  /**
   * @return the parent
   */
  public Carrier getParent() {
    return parent;
  }

  /**
   * @param parent the parent to set
   */
  public void setParent(Carrier parent) {
    this.parent = parent;
  }

  /**
   * @return Returns the bootstrapDmProfile.
   */
  public ProfileConfig getBootstrapDmProfile() {
    return bootstrapDmProfile;
  }

  /**
   * @param bootstrapDmProfile The bootstrapDmProfile to set.
   */
  public void setBootstrapDmProfile(ProfileConfig bootstrapDmProfile) {
    this.bootstrapDmProfile = bootstrapDmProfile;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Carrier#getBootstrapMaxRetries()
   */
  public int getBootstrapMaxRetries() {
    return bootstrapMaxRetries;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Carrier#setBootstrapMaxRetries(int)
   */
  public void setBootstrapMaxRetries(int bootstrapMaxRetries) {
    this.bootstrapMaxRetries = bootstrapMaxRetries;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Carrier#getDefaultBootstrapPinType()
   */
  public String getDefaultBootstrapPinType() {
    return defaultBootstrapPinType;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Carrier#setDefaultBootstrapPinType(java.lang.String)
   */
  public void setDefaultBootstrapPinType(String defaultBootstrapPinType) {
    this.defaultBootstrapPinType = defaultBootstrapPinType;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Carrier#getDefaultBootstrapUserPin()
   */
  public String getDefaultBootstrapUserPin() {
    return defaultBootstrapUserPin;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Carrier#setDefaultBootstrapUserPin(java.lang.String)
   */
  public void setDefaultBootstrapUserPin(String defaultBootstrapUserPin) {
    this.defaultBootstrapUserPin = defaultBootstrapUserPin;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Carrier#getDefaultJobExpiredTimeInSeconds()
   */
  public long getDefaultJobExpiredTimeInSeconds() {
    return this.defaultJobExpiredTimeInSeconds;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.core.Carrier#setDefaultJobExpiredTimeInSeconds(long)
   */
  public void setDefaultJobExpiredTimeInSeconds(long timeInSeconds) {
    this.defaultJobExpiredTimeInSeconds = timeInSeconds;
  }

}