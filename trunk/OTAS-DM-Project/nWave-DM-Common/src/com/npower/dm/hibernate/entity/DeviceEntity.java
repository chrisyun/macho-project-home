/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/DeviceEntity.java,v 1.5 2007/08/29 06:21:00 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2007/08/29 06:21:00 $
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/08/29 06:21:00 $
 */
public class DeviceEntity extends AbstractDevice implements java.io.Serializable {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public DeviceEntity() {
    super();
  }

  public DeviceEntity(Subscriber subscriber, Model model, String externalID) {
    this();
    this.setExternalId(externalID);

    this.setModel(model);
    this.setModelName(model.getName());
    this.setManufacturerId(model.getManufacturer().getID());
    this.setManufacturerName(model.getManufacturer().getName());

    this.setSubscriber(subscriber);
    this.setSubscriberCarrierId(subscriber.getCarrier().getID());
    this.setSubscriberCarrierName(subscriber.getCarrier().getName());
    this.setSubscriberIsActivated(subscriber.getIsActivated());
    this.setSubscriberPassword(subscriber.getPassword());
    this.setSubscriberPhoneNumber(subscriber.getPhoneNumber());
    this.setSubscriberAskPermTrig(subscriber.getAskPermissionOnTrigger());
    this.setSubscriberState(subscriber.getState());
  }

  /**
   * Override the getSubscriberState(), make sure return default value if the
   * value has not initlized!
   * 
   */
  public String getSubscriberState() {
    if (super.getSubscriberState() == null) {
      super.setSubscriberState(Subscriber.STATE_INIT);
    }
    return super.getSubscriberState();
  }
  
  // Implements interface Device ************************************************************************

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Device other) {
    if (other == null) {
       return -1;
    } else {
      return this.getExternalId().compareTo(other.getExternalId());
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Device#getDeviceGroups()
   */
  public Set getDeviceGroups() throws DMException {
    Set set = this.getDeviceGroupDevices();
    Set result = new HashSet(0);
    for (Iterator i = set.iterator(); i.hasNext();) {
        Device4DeviceGroup dGroup = (Device4DeviceGroup)i.next();
        result.add(dGroup.getDeviceGroup());
    }
    return result;
  }

}
