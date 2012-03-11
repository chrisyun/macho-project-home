/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProfileAttribute.java,v 1.4 2007/01/25 10:33:36 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2007/01/25 10:33:36 $
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

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileTemplate;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/01/25 10:33:36 $
 */
public abstract class AbstractProfileAttribute implements java.io.Serializable, ProfileAttribute {

  // Fields

  private long ID;

  private ProfileAttributeType profileAttribType;

  private ProfileTemplate profileTemplate;

  private String name;

  private String displayName;

  private String description;
  
  private boolean isRequired;

  private String defaultValue;

  private boolean isUserAttribute;

  private boolean isMultivalued;

  private boolean displayValue;

  private boolean isValueUnique;

  private long attributeIndex;

  private Set profileParameters = new HashSet(0);

  private Set profileNodeMappings = new HashSet(0);

  private Set profileAttribValues = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractProfileAttribute() {
    super();
  }

  /** minimal constructor */
  public AbstractProfileAttribute(String name, boolean isRequired, boolean isUserAttribute, boolean isMultivalued,
      boolean displayValue, boolean isValueUnique) {
    this.name = name;
    this.isRequired = isRequired;
    this.isUserAttribute = isUserAttribute;
    this.isMultivalued = isMultivalued;
    this.displayValue = displayValue;
    this.isValueUnique = isValueUnique;
  }

  /** full constructor */
  public AbstractProfileAttribute(ProfileAttributeType nwDmProfileAttribType, ProfileTemplate nwDmProfileTemplate,
      String name, boolean isRequired, String defaultValue, boolean isUserAttribute, boolean isMultivalued,
      boolean displayValue, boolean isValueUnique, long attributeIndex, Set nwDmProfileParameters,
      Set nwDmProfileNodeMappings, Set nwDmProfileAttribValues) {
    this.profileAttribType = nwDmProfileAttribType;
    this.profileTemplate = nwDmProfileTemplate;
    this.name = name;
    this.isRequired = isRequired;
    this.defaultValue = defaultValue;
    this.isUserAttribute = isUserAttribute;
    this.isMultivalued = isMultivalued;
    this.displayValue = displayValue;
    this.isValueUnique = isValueUnique;
    this.attributeIndex = attributeIndex;
    this.profileParameters = nwDmProfileParameters;
    this.profileNodeMappings = nwDmProfileNodeMappings;
    this.profileAttribValues = nwDmProfileAttribValues;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long attributeId) {
    this.ID = attributeId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getProfileAttribType()
   */
  public ProfileAttributeType getProfileAttribType() {
    return this.profileAttribType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#setProfileAttribType(com.npower.dm.hibernate.ProfileAttributeType)
   */
  public void setProfileAttribType(ProfileAttributeType nwDmProfileAttribType) {
    this.profileAttribType = nwDmProfileAttribType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getProfileTemplate()
   */
  public ProfileTemplate getProfileTemplate() {
    return this.profileTemplate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#setProfileTemplate(com.npower.dm.hibernate.ProfileTemplateEntity)
   */
  public void setProfileTemplate(ProfileTemplate nwDmProfileTemplate) {
    this.profileTemplate = nwDmProfileTemplate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getIsRequired()
   */
  public boolean getIsRequired() {
    return this.isRequired;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#setIsRequired(boolean)
   */
  public void setIsRequired(boolean isRequired) {
    this.isRequired = isRequired;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getDefaultValue()
   */
  public String getDefaultValue() {
    return this.defaultValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#setDefaultValue(java.lang.String)
   */
  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getIsUserAttribute()
   */
  public boolean getIsUserAttribute() {
    return this.isUserAttribute;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#setIsUserAttribute(boolean)
   */
  public void setIsUserAttribute(boolean isUserAttribute) {
    this.isUserAttribute = isUserAttribute;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getIsMultivalued()
   */
  public boolean getIsMultivalued() {
    return this.isMultivalued;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#setIsMultivalued(boolean)
   */
  public void setIsMultivalued(boolean isMultivalued) {
    this.isMultivalued = isMultivalued;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getDisplayValue()
   */
  public boolean getDisplayValue() {
    return this.displayValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#setDisplayValue(boolean)
   */
  public void setDisplayValue(boolean displayValue) {
    this.displayValue = displayValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getIsValueUnique()
   */
  public boolean getIsValueUnique() {
    return this.isValueUnique;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#setIsValueUnique(boolean)
   */
  public void setIsValueUnique(boolean isValueUnique) {
    this.isValueUnique = isValueUnique;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getAttributeIndex()
   */
  public long getAttributeIndex() {
    return this.attributeIndex;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#setAttributeIndex(long)
   */
  public void setAttributeIndex(long attributeIndex) {
    this.attributeIndex = attributeIndex;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getProfileParameters()
   */
  public Set getProfileParameters() {
    return this.profileParameters;
  }

  public void setProfileParameters(Set nwDmProfileParameters) {
    this.profileParameters = nwDmProfileParameters;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getProfileNodeMappings()
   */
  public Set getProfileNodeMappings() {
    return this.profileNodeMappings;
  }

  public void setProfileNodeMappings(Set nwDmProfileNodeMappings) {
    this.profileNodeMappings = nwDmProfileNodeMappings;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAttribute#getProfileAttribValues()
   */
  public Set getProfileAttribValues() {
    return this.profileAttribValues;
  }

  public void setProfileAttribValues(Set nwDmProfileAttribValues) {
    this.profileAttribValues = nwDmProfileAttribValues;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the displayName
   */
  public String getDisplayName() {
    if (StringUtils.isEmpty(displayName)) {
      displayName = this.getName();
    }
    return displayName;
  }

  /**
   * @param displayName the displayName to set
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

}