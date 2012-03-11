/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/SubscriberEntity.java,v 1.3 2008/11/09 09:25:44 zhao Exp $
 * $Revision: 1.3 $
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
package com.npower.dm.hibernate.entity;

import java.util.Set;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.Device;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/11/09 09:25:44 $
 */
public class SubscriberEntity extends AbstractSubscriber implements java.io.Serializable {

  /** default constructor */
  public SubscriberEntity() {
    super();
  }

  /**
   * Minimal constructor.
   * 
   * @param externalID
   * @param carrier
   * @param phoneNumber
   * @param password
   */
  public SubscriberEntity(String externalID, Carrier carrier, String phoneNumber, String password) {
    this();
    this.setCarrier(carrier);
    this.setExternalId(externalID);
    this.setPhoneNumber(phoneNumber);
    this.setPassword(password);
  }

  /**
   * Override the default setter. If newCarrier is null, will assign carrier to
   * newCarrier.
   */
  public Carrier getNewCarrier() {
    if (super.getNewCarrier() == null) {
      this.setNewCarrier(this.getCarrier());
    }
    return super.getNewCarrier();
  }

  /**
   * Override the default setter. If newpassword is null, will assign password
   * to new Password.
   */
  public String getNewPassword() {
    if (super.getNewPassword() == null) {
      this.setNewPassword(this.getPassword());
    }
    return super.getNewPassword();
  }

  /**
   * Override the default setter. If ExternalId is null, will assign
   * subscriberID to ExternalId.
   */
  public String getExternalId() {
    if (super.getExternalId() == null) {
      this.setExternalId("" + this.getID());
    }
    return super.getExternalId();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Subscriber#getActivedDevice()
   */
  public Device getActivedDevice() {
    Set<Device> devices = this.getDevices();
    if (devices == null) {
       return null;
    }
    for (Device device: devices) {
        if (device.getIsActivated()) {
           return device;
        }
    }
    return null;
  }

}
