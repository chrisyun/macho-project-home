/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDeviceTree.java,v 1.5 2006/04/25 16:31:09 zhao Exp $
 * $Revision: 1.5 $
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

import com.npower.dm.core.DeviceTree;
import com.npower.dm.core.DeviceTreeNode;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractDeviceTree implements java.io.Serializable, DeviceTree {

  // Fields

  private long ID;

  private DeviceTreeNode rootNode;

  private long changeVersion;

  private Set devices = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDeviceTree() {
    super();
  }

  /** minimal constructor */
  public AbstractDeviceTree(DeviceTreeNode rootNode) {
    this.rootNode = rootNode;
  }

  // Property accessors

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTree#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long deviceTreeId) {
    this.ID = deviceTreeId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTree#getNode()
   */
  public DeviceTreeNode getRootNode() {
    return this.rootNode;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTree#setNode(com.npower.dm.hibernate.entity.DeviceTreeNode)
   */
  public void setRootNode(DeviceTreeNode rootNode) {
    this.rootNode = rootNode;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTree#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  public Set getDevices() {
    return this.devices;
  }

  public void setDevices(Set nwDmDevices) {
    this.devices = nwDmDevices;
  }

}