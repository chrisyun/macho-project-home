/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractManufacturer.java,v 1.3 2007/02/10 03:47:51 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2007/02/10 03:47:51 $
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

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/02/10 03:47:51 $
 */
public abstract class AbstractManufacturer implements Serializable, Manufacturer {

  // Fields

  private long ID;

  private String name;

  private String description;

  private String externalId;

  private String lastUpdatedBy;

  private Date lastUpdatedTime;

  private long changeVersion;

  // private Set models = Collections.synchronizedSet(new HashSet(0));
  private Set<Model> models = new TreeSet<Model>();

  // Constructors

  /** default constructor */
  public AbstractManufacturer() {
  }

  /** minimal constructor */
  public AbstractManufacturer(String manufacturerExternalId) {
    this.externalId = manufacturerExternalId;
  }

  /** full constructor */
  public AbstractManufacturer(String name, String description, String manufacturerExternalId) {
    this.name = name;
    this.description = description;
    this.externalId = manufacturerExternalId;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long manufacturerId) {
    this.ID = manufacturerId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#getExternalId()
   */
  public String getExternalId() {
    return this.externalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#setExternalId(java.lang.String)
   */
  public void setExternalId(String manufacturerExternalId) {
    this.externalId = manufacturerExternalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    return this.lastUpdatedTime;
  }

  public void setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Manufacturer#getModels()
   */
  public Set<Model> getModels() {
    return this.models;
  }

  public void setModels(Set<Model> models) {
    this.models = models;
  }

}