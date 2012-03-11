/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDeviceGroup.java,v 1.3 2006/04/25 16:31:09 zhao Exp $
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

import java.util.HashSet;
import java.util.Set;

import com.npower.dm.core.DeviceGroup;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractDeviceGroup implements java.io.Serializable, DeviceGroup {

  // Fields

  private long ID;

  private long changeVersion;

  private Set devicesForDeviceGroup = new HashSet(0);

  private Set devicesForInitialGroup = new HashSet(0);

  private Set prElements = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDeviceGroup() {
    super();
  }

  /** full constructor */
  public AbstractDeviceGroup(Set devicesForDeviceGroup,
      Set devicesForInitialGroup, Set prElements) {
    this.devicesForDeviceGroup = devicesForDeviceGroup;
    this.devicesForInitialGroup = devicesForInitialGroup;
    this.prElements = prElements;
  }

  // Property accessors

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceGroup#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long deviceGroupId) {
    this.ID = deviceGroupId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceGroup#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  public Set getDevicesForDeviceGroup() {
    return this.devicesForDeviceGroup;
  }

  public void setDevicesForDeviceGroup(Set devicesForDeviceGroupId) {
    this.devicesForDeviceGroup = devicesForDeviceGroupId;
  }

  public Set getDevicesForInitialGroup() {
    return this.devicesForInitialGroup;
  }

  public void setDevicesForInitialGroup(Set devicesForInitialGroupId) {
    this.devicesForInitialGroup = devicesForInitialGroupId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceGroup#getPrElements()
   */
  public Set getPrElements() {
    return this.prElements;
  }

  public void setPrElements(Set prElements) {
    this.prElements = prElements;
  }

}