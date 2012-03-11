/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDevice4DeviceGroup.java,v 1.3 2006/04/25 16:31:09 zhao Exp $
 * $Revision: 1.3 $
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

import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractDevice4DeviceGroup implements java.io.Serializable {

  // Fields

  private Device4DeviceGroupID ID;

  private DeviceGroup deviceGroup;

  private DeviceGroup initialDeviceGroup;

  private Device device;

  private boolean marked;

  // Constructors

  /** default constructor */
  public AbstractDevice4DeviceGroup() {
    super();
  }

  /** minimal constructor */
  public AbstractDevice4DeviceGroup(Device4DeviceGroupID id, DeviceGroup group, Device device) {
    this.ID = id;
    this.deviceGroup = group;
    this.device = device;
  }

  /** full constructor */
  public AbstractDevice4DeviceGroup(Device4DeviceGroupID id, DeviceGroup group,
      DeviceGroup initialGroup, Device device) {
    this.ID = id;
    this.deviceGroup = group;
    this.initialDeviceGroup = initialGroup;
    this.device = device;
  }

  // Property accessors

  public Device4DeviceGroupID getID() {
    return this.ID;
  }

  public void setID(Device4DeviceGroupID id) {
    this.ID = id;
  }

  public DeviceGroup getDeviceGroup() {
    return this.deviceGroup;
  }

  public void setDeviceGroup(DeviceGroup nwDmDeviceGroupByDeviceGroupId) {
    this.deviceGroup = nwDmDeviceGroupByDeviceGroupId;
  }

  public DeviceGroup getInitialDeviceGroup() {
    return this.initialDeviceGroup;
  }

  public void setInitialDeviceGroup(DeviceGroup nwDmDeviceGroupByInitialGroupId) {
    this.initialDeviceGroup = nwDmDeviceGroupByInitialGroupId;
  }

  public Device getDevice() {
    return this.device;
  }

  public void setDevice(Device nwDmDevice) {
    this.device = nwDmDevice;
  }

  public boolean getMarked() {
    return this.marked;
  }

  public void setMarked(boolean marked) {
    this.marked = marked;
  }

}