/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProfileAttributeValue.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

import java.sql.Blob;
import java.sql.Clob;
import java.util.HashSet;
import java.util.Set;

import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeValue;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractProfileAttributeValue implements java.io.Serializable, ProfileAttributeValue {

  // Fields

  private long ID;

  private ProfileAttribute profileAttribute;

  private boolean isMultiValued;

  private String itemDataKind;

  private String updateId;

  private Clob rawData;

  private Blob binaryData;

  private String MFormat;

  private String MType;

  private Set profileValueMaps = new HashSet(0);

  private Set profileAssignValues = new HashSet(0);

  private Set profileValueItems = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractProfileAttributeValue() {
    super();
  }

  /** minimal constructor */
  public AbstractProfileAttributeValue(ProfileAttribute nwDmProfileAttribute, boolean isMultiValued) {
    this.profileAttribute = nwDmProfileAttribute;
    this.isMultiValued = isMultiValued;
  }

  /** full constructor */
  public AbstractProfileAttributeValue(ProfileAttribute nwDmProfileAttribute, boolean isMultiValued,
      String itemDataKind, String updateId, Clob rawData, Blob binaryData, String MFormat, String MType,
      Set nwDmProfileValueMaps, Set nwDmProfileAssignValues, Set nwDmProfileValueItems) {
    this.profileAttribute = nwDmProfileAttribute;
    this.isMultiValued = isMultiValued;
    this.itemDataKind = itemDataKind;
    this.updateId = updateId;
    this.rawData = rawData;
    this.binaryData = binaryData;
    this.MFormat = MFormat;
    this.MType = MType;
    this.profileValueMaps = nwDmProfileValueMaps;
    this.profileAssignValues = nwDmProfileAssignValues;
    this.profileValueItems = nwDmProfileValueItems;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long attributeValueId) {
    this.ID = attributeValueId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getProfileAttribute()
   */
  public ProfileAttribute getProfileAttribute() {
    return this.profileAttribute;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#setProfileAttribute(com.npower.dm.hibernate.ProfileAttribute)
   */
  public void setProfileAttribute(ProfileAttribute nwDmProfileAttribute) {
    this.profileAttribute = nwDmProfileAttribute;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getIsMultiValued()
   */
  public boolean getIsMultiValued() {
    return this.isMultiValued;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#setIsMultiValued(boolean)
   */
  public void setIsMultiValued(boolean isMultiValued) {
    this.isMultiValued = isMultiValued;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getItemDataKind()
   */
  public String getItemDataKind() {
    return this.itemDataKind;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#setItemDataKind(java.lang.String)
   */
  public void setItemDataKind(String itemDataKind) {
    this.itemDataKind = itemDataKind;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getUpdateId()
   */
  public String getUpdateId() {
    return this.updateId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#setUpdateId(java.lang.String)
   */
  public void setUpdateId(String updateId) {
    this.updateId = updateId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getRawData()
   */
  public Clob getRawData() {
    return this.rawData;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#setRawData(java.sql.Clob)
   */
  public void setRawData(Clob rawData) {
    this.rawData = rawData;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getBinaryData()
   */
  public Blob getBinaryData() {
    return this.binaryData;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#setBinaryData(java.sql.Blob)
   */
  public void setBinaryData(Blob binaryData) {
    this.binaryData = binaryData;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getMFormat()
   */
  public String getMFormat() {
    return this.MFormat;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#setMFormat(java.lang.String)
   */
  public void setMFormat(String MFormat) {
    this.MFormat = MFormat;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getMType()
   */
  public String getMType() {
    return this.MType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#setMType(java.lang.String)
   */
  public void setMType(String MType) {
    this.MType = MType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getProfileValueMaps()
   */
  public Set getProfileValueMaps() {
    return this.profileValueMaps;
  }

  public void setProfileValueMaps(Set nwDmProfileValueMaps) {
    this.profileValueMaps = nwDmProfileValueMaps;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getProfileAssignValues()
   */
  public Set getProfileAssignValues() {
    return this.profileAssignValues;
  }

  public void setProfileAssignValues(Set nwDmProfileAssignValues) {
    this.profileAssignValues = nwDmProfileAssignValues;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttributeValue#getProfileValueItems()
   */
  public Set getProfileValueItems() {
    return this.profileValueItems;
  }

  public void setProfileValueItems(Set nwDmProfileValueItems) {
    this.profileValueItems = nwDmProfileValueItems;
  }

}