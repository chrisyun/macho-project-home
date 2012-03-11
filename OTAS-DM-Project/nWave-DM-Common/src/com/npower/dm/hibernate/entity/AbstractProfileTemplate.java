/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProfileTemplate.java,v 1.3 2006/06/26 03:48:13 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2006/06/26 03:48:13 $
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
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2006/06/26 03:48:13 $
 */
public abstract class AbstractProfileTemplate implements java.io.Serializable, ProfileTemplate {

  // Fields

  private long ID;

  private ProfileCategory profileCategory;

  private String name;

  private boolean needsNap;

  private boolean needsProxy;

  private String lastUpdatedBy;

  private Date lastUpdatedTime;

  private long changeVersion;

  private Set profileMappings = new HashSet(0);

  private Set profileAttributes = new TreeSet();

  private Set profileConfigs = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractProfileTemplate() {
  }

  /** minimal constructor */
  public AbstractProfileTemplate(ProfileCategory nwDmProfileCategory, String name, long changeVersion) {
    this.profileCategory = nwDmProfileCategory;
    this.name = name;
    this.changeVersion = changeVersion;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long templateId) {
    this.ID = templateId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#getProfileCategory()
   */
  public ProfileCategory getProfileCategory() {
    return this.profileCategory;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#setProfileCategory(com.npower.dm.hibernate.ProfileCategory)
   */
  public void setProfileCategory(ProfileCategory nwDmProfileCategory) {
    this.profileCategory = nwDmProfileCategory;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#getNeedsNap()
   */
  public boolean getNeedsNap() {
    return this.needsNap;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#setNeedsNap(boolean)
   */
  public void setNeedsNap(boolean needsNap) {
    this.needsNap = needsNap;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#getNeedsProxy()
   */
  public boolean getNeedsProxy() {
    return this.needsProxy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#setNeedsProxy(boolean)
   */
  public void setNeedsProxy(boolean needsProxy) {
    this.needsProxy = needsProxy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#getLastUpdatedTime()
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
   * @see com.npower.dm.hibernate.ProfileTemplate#getChangeVersion()
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
   * @see com.npower.dm.hibernate.ProfileTemplate#getProfileMappings()
   */
  public Set getProfileMappings() {
    return this.profileMappings;
  }

  public void setProfileMappings(Set nwDmProfileMappings) {
    this.profileMappings = nwDmProfileMappings;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#getProfileAttributes()
   */
  public Set getProfileAttributes() {
    return this.profileAttributes;
  }

  public void setProfileAttributes(Set nwDmProfileAttributes) {
    this.profileAttributes = nwDmProfileAttributes;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileTemplate#getProfileConfigs()
   */
  public Set getProfileConfigs() {
    return this.profileConfigs;
  }

  public void setProfileConfigs(Set nwDmProfileConfigs) {
    this.profileConfigs = nwDmProfileConfigs;
  }

}