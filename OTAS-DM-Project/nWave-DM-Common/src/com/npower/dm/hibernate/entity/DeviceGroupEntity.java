/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/DeviceGroupEntity.java,v 1.4 2006/06/22 03:20:01 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2006/06/22 03:20:01 $
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

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2006/06/22 03:20:01 $
 */
public class DeviceGroupEntity extends AbstractDeviceGroup implements java.io.Serializable {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public DeviceGroupEntity() {
    super();
  }

  /** full constructor */
  public DeviceGroupEntity(Set devicesForDeviceGroup, Set devicesForInitialGroup, Set prElements) {
    super(devicesForDeviceGroup, devicesForInitialGroup, prElements);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.DeviceGroup#getDevices()
   */
  public Set getDevices() throws DMException {
    Set result = new TreeSet();
    Set devices = this.getDevicesForDeviceGroup();
    for (Iterator i = devices.iterator(); i.hasNext(); ) {
        Device4DeviceGroup device4DeviceGroup = (Device4DeviceGroup)i.next();
        Device device = device4DeviceGroup.getDevice();
        result.add(device);
    }
    return result;
  }
}
