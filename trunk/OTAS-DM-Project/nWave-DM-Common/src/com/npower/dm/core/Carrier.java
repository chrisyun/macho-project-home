/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/Carrier.java,v 1.8 2009/02/17 03:38:59 zhao Exp $
 * $Revision: 1.8 $
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
package com.npower.dm.core;

import java.util.Date;
import java.util.Set;

/**
 * <p>Represnet a Carrier.</p>
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2009/02/17 03:38:59 $
 */
public interface Carrier {

  /**
   * DM Server Authentication type: Basic
   */
  public final static String SERVER_AUTH_TYPE_BASIC = "syncml:auth-basic";

  /**
   * DM Server Authentication type: Digest
   */
  public final static String SERVER_AUTH_TYPE_DIGEST = "syncml:auth-md5";

  /**
   * DM Server Authentication type: HMAC
   */
  public final static String SERVER_AUTH_TYPE_HMAC = "syncml:auth-MAC";

  /**
   * DM Server Authentication type: None
   */
  public final static String SERVER_AUTH_TYPE_NONE = "none";

  /**
   * Notification Type: WAP Push
   */
  public final static String NOTIFICATION_TYPE_WAP_PUSH = "wapPush";

  /**
   * Default seconds for timeout of notification
   */
  public final static long DEFAULT_NOTIFICATION_STATE_TIMEOUT = 7200L;

  /**
   * Default seconds for timeout of bootstrap
   */
  public final static long DEFAULT_BOOTSTRAP_TIMEOUT = 7200L;

  /**
   * Default times of maxium retrie for notification
   */
  public final static long DEFAULT_NOTIFICATION_MAX_RETRIES = 3;

  /**
   * Default User PIN for Bootstrap
   */
  public static final String DEFAULT_BOOTSTRAP_USER_PIN = "1234";

  /**
   * Return the ID.
   * @return
   */
  public abstract long getID();

  /**
   * Return Bootstrap Profile for this carrier, the type of profile will be "NAP" or "Proxy".<br>
   * This Profile will be used for bootstrap.
   * 
   * @return
   */
  public abstract ProfileConfig getBootstrapNapProfile();

  /**
   * Set up a NAP Profile for bootstrap.
   * 
   * @param nwDmProfileConfig
   */
  public abstract void setBootstrapNapProfile(ProfileConfig nwDmProfileConfig);

  /**
   * Return Bootstrap Profile for this carrier, the type of profile will be "DM".<br>
   * This Profile will be used for bootstrap.
   * @return Returns the bootstrapDmProfile.
   */
  public ProfileConfig getBootstrapDmProfile();

  /**
   * @param bootstrapDmProfile The bootstrapDmProfile to set.
   */
  public void setBootstrapDmProfile(ProfileConfig bootstrapDmProfile);

  /**
   * Return the country of this carrier.
   * 
   * @return
   */
  public abstract Country getCountry();

  /**
   * Set the country for the carrier.
   * 
   * @param nwDmCountry
   */
  public abstract void setCountry(Country nwDmCountry);

  /**
   * Return the alias name of the carrier. <br>
   * Eg: China Mobile's externalID is "CMCC".
   * @return
   */
  public abstract String getExternalID();

  /**
   * Setup the externalID for the carrier.
   * 
   * @param carrierExternalId
   */
  public abstract void setExternalID(String carrierExternalId);

  /**
   * Return the name of carrier.
   * @return
   */
  public abstract String getName();

  /**
   * Set the name of carrier.
   * @param name
   */
  public abstract void setName(String name);

  /**
   * Return the DM Server Authen Type.
   * 
   * @see SERVER_AUTH_TYPE_BASIC
   * @see SERVER_AUTH_TYPE_DIGEST
   * @see SERVER_AUTH_TYPE_HMAC
   * @see SERVER_AUTH_TYPE_NONE
   * @return
   */
  public abstract String getServerAuthType();

  /**
   * Set the Authentication type for the carrier.
   * 
   * @see SERVER_AUTH_TYPE_BASIC
   * @see SERVER_AUTH_TYPE_DIGEST
   * @see SERVER_AUTH_TYPE_HMAC
   * @see SERVER_AUTH_TYPE_NONE
   * @param serverAuthType
   */
  public abstract void setServerAuthType(String serverAuthType);

  /**
   * Return PhoneNumber policy for this carrier.
   * @return the phoneNumberPolicy
   */
  public abstract String getPhoneNumberPolicy();

  /**
   * Set the phone number policy for this carrier.
   * @param phoneNumberPolicy the phoneNumberPolicy to set
   */
  public abstract void setPhoneNumberPolicy(String phoneNumberPolicy);

  /**
   * Return the parent of this carrier.
   * @return the parent
   */
  public abstract Carrier getParent();

  /**
   * Set the parent of this carrier.
   * @param parent the parent to set
   */
  public abstract void setParent(Carrier parent);
  
  /**
   * Return the type of notification.
   * 
   * @return
   */
  public abstract String getNotificationType();

  /**
   * Set the type of notification.
   * 
   * @param notificationType
   */
  public abstract void setNotificationType(String notificationType);

  /**
   * Return the all of notification properties for this carrier.<br>
   * Eg: "name1=value1;name2=value2;..."
   * @return
   */
  public abstract String getNotificationProperties();

  /**
   * Set the notification properties for this carrier.<br>
   * Eg: "name1=value1;name2=value2;..."
   * 
   * @param notificationProperties
   */
  public abstract void setNotificationProperties(String notificationProperties);

  /**
   * return the nofication timeout in seconds.
   * 
   * @return
   */
  public abstract long getNotificationStateTimeout();

  /**
   * Set the timeout in seconds for notification.
   * 
   * @param notificationStateTimeout
   */
  public abstract void setNotificationStateTimeout(long notificationStateTimeout);

  /**
   * Return the timeout of bootstrap in seconds.
   * 
   * @return
   */
  public abstract long getBootstrapTimeout();

  /**
   * Set the timeout in seconds for bootstrap.
   * 
   * @param bootstrapTimeout
   */
  public abstract void setBootstrapTimeout(long bootstrapTimeout);

  /**
   * Return the maxmium number of retries for notification service.
   * 
   * @return
   */
  public abstract long getNotificationMaxNumRetries();

  /**
   * Set the maxmium number of retries for notification service.
   * 
   * @param notificationMaxNumRetries
   */
  public abstract void setNotificationMaxNumRetries(long notificationMaxNumRetries);

  /**
   * @return Returns the bootstrapMaxRetries.
   */
  public int getBootstrapMaxRetries();

  /**
   * @param bootstrapMaxRetries The bootstrapMaxRetries to set.
   */
  public void setBootstrapMaxRetries(int bootstrapMaxRetries);

  /**
   * @return Returns the defaultBootstrapPinType.
   */
  public String getDefaultBootstrapPinType();

  /**
   * @param defaultBootstrapPinType The defaultBootstrapPinType to set.
   */
  public void setDefaultBootstrapPinType(String defaultBootstrapPinType);

  /**
   * @return Returns the defaultBootstrapUserPin.
   */
  public String getDefaultBootstrapUserPin();

  /**
   * @param defaultBootstrapUserPin The defaultBootstrapUserPin to set.
   */
  public void setDefaultBootstrapUserPin(String defaultBootstrapUserPin);

  /**
   * Return the description.
   * 
   * @return
   */
  public abstract String getDescription();

  /**
   * Set the description.
   * 
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * Return all of profiles belong to this carrier.
   * 
   * @return Return a <code>Set</code> of the {@link com.npower.dm.core.ProfileConfig} objects.
   * 
   */
  public abstract Set<ProfileConfig> getProfileConfigs();

  /**
   * Return all of subscribers which belong to this carrier.
   * 
   * @return Return a <code>Set</code> of the {$@link com.npower.dm.core.Subscriber} objects.
   */
  public abstract Set<Subscriber> getSubscribers();

  //public abstract Set getSubscribersForNewCarrierId();

  //public abstract Set getPrElements();

  //public abstract Set getImageCarrierses();

  //public abstract Set getUpdateCarrierses();

  /**
   * Return the modifier in last modification.
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * Setter the modifier for modification track.
   * 
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Return the time of last modification.
   * 
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * Return the version number.
   * @return
   */
  public abstract long getChangeVersion();

  /**
   * 以秒为单位的任务缺省超期时间, 从任务调度时间开始, 当经历超期时间所指定的 时间间隔后, 
   * 仍然没有正常运行结束, 将自动被撤销.
   * 缺省值为7天.
   * @return
   */
  public abstract long getDefaultJobExpiredTimeInSeconds();
  
  /**
   * 以秒为单位的任务缺省超期时间, 从任务调度时间开始, 当经历超期时间所指定的 时间间隔后, 
   * 仍然没有正常运行结束, 将自动被撤销.
   * @param timeInSeconds
   */
  public abstract void setDefaultJobExpiredTimeInSeconds(long timeInSeconds);

}