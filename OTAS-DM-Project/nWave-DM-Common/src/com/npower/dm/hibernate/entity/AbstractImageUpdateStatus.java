/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractImageUpdateStatus.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

import java.util.HashSet;
import java.util.Set;

import com.npower.dm.core.ImageUpdateStatus;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractImageUpdateStatus implements java.io.Serializable, ImageUpdateStatus {

  // Fields

  private long statusId;

  private String name;

  private long changeVersion;

  private Set updates = new HashSet(0);

  private Set updateCarrierses = new HashSet(0);

  private Set imageCarrierses = new HashSet(0);

  private Set images = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractImageUpdateStatus() {
  }

  /** minimal constructor */
  protected AbstractImageUpdateStatus(long statusId, String name) {
    this.name = name;
    this.statusId = statusId;
  }

  /** full constructor */
  protected AbstractImageUpdateStatus(long statusId, String name, long changeVersion) {
    this.statusId = statusId;
    this.name = name;
    this.changeVersion = changeVersion;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ImageUpdateStatus#getStatusId()
   */
  public long getStatusId() {
    return this.statusId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ImageUpdateStatus#setStatusId(long)
   */
  public void setStatusId(long statusId) {
    this.statusId = statusId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ImageUpdateStatus#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ImageUpdateStatus#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ImageUpdateStatus#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  public Set getUpdates() {
    return this.updates;
  }

  public void setUpdates(Set nwDmUpdates) {
    this.updates = nwDmUpdates;
  }

  public Set getUpdateCarrierses() {
    return this.updateCarrierses;
  }

  public void setUpdateCarrierses(Set nwDmUpdateCarrierses) {
    this.updateCarrierses = nwDmUpdateCarrierses;
  }

  public Set getImageCarrierses() {
    return this.imageCarrierses;
  }

  public void setImageCarrierses(Set nwDmImageCarrierses) {
    this.imageCarrierses = nwDmImageCarrierses;
  }

  public Set getImages() {
    return this.images;
  }

  public void setImages(Set nwDmImages) {
    this.images = nwDmImages;
  }

}