/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDeviceLogAction.java,v 1.3 2006/04/25 11:50:09 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2006/04/25 11:50:09 $
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

import com.npower.dm.core.DeviceLogAction;

/**
 * <p></p>
 * 
 * @author Zhao Yang
 * @version $Revision: 1.3 $ $Date: 2006/04/25 11:50:09 $
 */
public abstract class AbstractDeviceLogAction implements java.io.Serializable, DeviceLogAction {

  // Fields

  private String value;

  private String description;

  private Set    deviceLogs = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDeviceLogAction() {
  }

  /** minimal constructor */
  public AbstractDeviceLogAction(String value, String description) {
    this.value = value;
    this.description = description;
  }

  /** full constructor */
  public AbstractDeviceLogAction(String value, String description, Set nwDmDeviceLogs) {
    this.value = value;
    this.description = description;
    this.deviceLogs = nwDmDeviceLogs;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogActionInter#getValue()
   */
  public String getValue() {
    return this.value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogActionInter#setValue(java.lang.String)
   */
  public void setValue(String value) {
    this.value = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogActionInter#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogActionInter#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogActionInter#getNwDmDeviceLogs()
   */
  public Set getDeviceLogs() {
    return this.deviceLogs;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.DeviceLogActionInter#setNwDmDeviceLogs(java.util.Set)
   */
  public void setDeviceLogs(Set nwDmDeviceLogs) {
    this.deviceLogs = nwDmDeviceLogs;
  }

}