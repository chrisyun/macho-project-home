/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProfileAttributeType.java,v 1.3 2006/06/07 11:03:30 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2006/06/07 11:03:30 $
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

import org.apache.commons.lang.builder.ToStringBuilder;

import com.npower.dm.core.ProfileAttributeType;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2006/06/07 11:03:30 $
 */
public abstract class AbstractProfileAttributeType implements java.io.Serializable, ProfileAttributeType {

  // Fields

  private long ID;

  private String name;

  private String description;

  private String lastUpdatedBy;

  private Date lastUpdatedTime;

  private long changeVersion;

  private Set profileAttributes = new HashSet(0);

  private Set attribTypeValues = new TreeSet();

  // Constructors

  /** default constructor */
  public AbstractProfileAttributeType() {
  }

  /** minimal constructor */
  public AbstractProfileAttributeType(String name, long changeVersion) {
    this.name = name;
    this.changeVersion = changeVersion;
  }

  /** full constructor */
  public AbstractProfileAttributeType(String name, String description, String lastUpdatedBy, Date lastUpdatedTime,
      long changeVersion, Set nwDmProfileAttributes, Set nwDmAttribTypeValueses) {
    this.name = name;
    this.description = description;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedTime = lastUpdatedTime;
    this.changeVersion = changeVersion;
    this.profileAttributes = nwDmProfileAttributes;
    this.attribTypeValues = nwDmAttribTypeValueses;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeType#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long attributeTypeId) {
    this.ID = attributeTypeId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeType#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeType#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeType#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeType#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeType#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeType#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeType#getLastUpdatedTime()
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
   * @see com.npower.dm.hibernate.ProfileAttributeType#getChangeVersion()
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
   * @see com.npower.dm.hibernate.ProfileAttributeType#getProfileAttributes()
   */
  public Set getProfileAttributes() {
    return this.profileAttributes;
  }

  public void setProfileAttributes(Set profileAttributes) {
    this.profileAttributes = profileAttributes;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeType#getAttribTypeValues()
   */
  public Set getAttribTypeValues() {
    return this.attribTypeValues;
  }

  public void setAttribTypeValues(Set attribTypeValues) {
    this.attribTypeValues = attribTypeValues;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}