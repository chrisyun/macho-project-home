/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/CarrierEntity.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2006/04/25 16:31:09 $
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

import org.apache.commons.lang.builder.ToStringBuilder;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.ProfileConfig;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public class CarrierEntity extends AbstractCarrier implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // Constructors

  /**
   * Default Constructor Set default values: ServerAuthenType: DIGEST
   * NotificationType: WAP Push Notification State Timeout: default Notification
   * Max Num of retries: Default Bootstrap Timeout: Default
   */
  public CarrierEntity() {
    super();
    this.setServerAuthType(SERVER_AUTH_TYPE_DIGEST);
    this.setNotificationType(NOTIFICATION_TYPE_WAP_PUSH);
    this.setNotificationStateTimeout(DEFAULT_NOTIFICATION_STATE_TIMEOUT);
    this.setNotificationMaxNumRetries(DEFAULT_NOTIFICATION_MAX_RETRIES);
    this.setBootstrapTimeout(DEFAULT_BOOTSTRAP_TIMEOUT);
  }

  /**
   * Easy constructor, set the following attributes by default:
   * ServerAuthenType: DIGEST NotificationType: WAP Push Notification State
   * Timeout: default Notification Max Num of retries: Default Bootstrap
   * Timeout: Default
   * 
   * @param country
   *          CountryEntity
   * @param carrierExternalId
   *          String
   * @param name
   *          String
   */
  public CarrierEntity(Country country, String carrierExternalId, String name) {
    super(country, carrierExternalId, name, SERVER_AUTH_TYPE_DIGEST, NOTIFICATION_TYPE_WAP_PUSH,
        DEFAULT_NOTIFICATION_STATE_TIMEOUT, DEFAULT_BOOTSTRAP_TIMEOUT, DEFAULT_NOTIFICATION_MAX_RETRIES);
  }

  /**
   * Minimal constructor
   * 
   * @param country
   * @param carrierExternalId
   * @param name
   * @param serverAuthType
   * @param notificationType
   * @param notificationStateTimeout
   * @param bootstrapTimeout
   * @param notificationMaxNumRetries
   */
  public CarrierEntity(Country country, String carrierExternalId, String name, String serverAuthType,
      String notificationType, long notificationStateTimeout, long bootstrapTimeout, long notificationMaxNumRetries) {

    super(country, carrierExternalId, name, serverAuthType, notificationType, notificationStateTimeout,
        bootstrapTimeout, notificationMaxNumRetries);
  }

  /**
   * Full constructor
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
  public CarrierEntity(ProfileConfig nwDmProfileConfig, Country nwDmCountry, String carrierExternalId, String name,
      String serverAuthType, String notificationType, String notificationProperties, long notificationStateTimeout,
      long bootstrapTimeout, long notificationMaxNumRetries, String lastUpdatedBy, Date lastUpdatedTime,
      String description) {

    super(nwDmProfileConfig, nwDmCountry, carrierExternalId, name, serverAuthType, notificationType,
        notificationProperties, notificationStateTimeout, bootstrapTimeout, notificationMaxNumRetries, lastUpdatedBy,
        lastUpdatedTime, description);
  }

  /**
   * Implements toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("ID", this.getID()).append("externalID", this.getExternalID()).toString();
  }

  /**
   * Implements Comparable.compareTo()
   */
  public int compareTo(Object o) {
    if (o instanceof Carrier) {
      Carrier other = (Carrier) o;
      return this.getName().compareTo(other.getName());
    } else {
      return -1;
    }
  }

}
