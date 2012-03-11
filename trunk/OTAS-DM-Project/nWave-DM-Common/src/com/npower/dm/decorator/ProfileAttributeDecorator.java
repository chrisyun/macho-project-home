/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/ProfileAttributeDecorator.java,v 1.1 2007/09/06 11:21:54 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/09/06 11:21:54 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.decorator;

import java.util.Locale;
import java.util.Set;

import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileTemplate;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/09/06 11:21:54 $
 */
public class ProfileAttributeDecorator extends Decorator<ProfileAttribute> implements ProfileAttribute {

  /**
   * Default Constructor
   */
  public ProfileAttributeDecorator() {
    super();
  }

  /**
   * Constructor
   */
  public ProfileAttributeDecorator(Locale locale, ProfileAttribute decoratee, ResourceManager<ProfileAttribute> resources) {
    super(decoratee, locale, resources);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getAttributeIndex()
   */
  public long getAttributeIndex() {
    return this.getDecoratee().getAttributeIndex();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getDefaultValue()
   */
  public String getDefaultValue() {
    return this.getDecoratee().getDefaultValue();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getDescription()
   */
  public String getDescription() {
    String index = this.getDecoratee().getName();
    String orginalValue = this.getDecoratee().getDescription();
    String value = this.getResourceManager().getResource(this.getLocale(), this, index, "description", orginalValue);
    return value;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getDisplayName()
   */
  public String getDisplayName() {
    String index = this.getDecoratee().getName();
    String orginalValue = this.getDecoratee().getDisplayName();
    String value = this.getResourceManager().getResource(this.getLocale(), this, index, "name", orginalValue);
    return value;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getDisplayValue()
   */
  public boolean getDisplayValue() {
    return this.getDecoratee().getDisplayValue();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getID()
   */
  public long getID() {
    return this.getDecoratee().getID();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getIsMultivalued()
   */
  public boolean getIsMultivalued() {
    return this.getDecoratee().getIsMultivalued();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getIsRequired()
   */
  public boolean getIsRequired() {
    return this.getDecoratee().getIsRequired();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getIsUserAttribute()
   */
  public boolean getIsUserAttribute() {
    return this.getDecoratee().getIsUserAttribute();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getIsValueUnique()
   */
  public boolean getIsValueUnique() {
    return this.getDecoratee().getIsValueUnique();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getName()
   */
  public String getName() {
    return this.getDecoratee().getName();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getProfileAttribType()
   */
  public ProfileAttributeType getProfileAttribType() {
    return this.getDecoratee().getProfileAttribType();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getProfileAttribValues()
   */
  public Set<ProfileAttributeValue> getProfileAttribValues() {
    return this.getDecoratee().getProfileAttribValues();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#getProfileTemplate()
   */
  public ProfileTemplate getProfileTemplate() {
    return this.getDecoratee().getProfileTemplate();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setAttributeIndex(long)
   */
  public void setAttributeIndex(long attributeIndex) {
    this.getDecoratee().setAttributeIndex(attributeIndex);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setDefaultValue(java.lang.String)
   */
  public void setDefaultValue(String defaultValue) {
    this.getDecoratee().setDefaultValue(defaultValue);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.getDecoratee().setDescription(description);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setDisplayName(java.lang.String)
   */
  public void setDisplayName(String displayName) {
    this.getDecoratee().setDisplayName(displayName);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setDisplayValue(boolean)
   */
  public void setDisplayValue(boolean displayValue) {
    this.getDecoratee().setDisplayValue(displayValue);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setIsMultivalued(boolean)
   */
  public void setIsMultivalued(boolean isMultivalued) {
    this.getDecoratee().setIsMultivalued(isMultivalued);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setIsRequired(boolean)
   */
  public void setIsRequired(boolean isRequired) {
    this.getDecoratee().setIsRequired(isRequired);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setIsUserAttribute(boolean)
   */
  public void setIsUserAttribute(boolean isUserAttribute) {
    this.getDecoratee().setIsUserAttribute(isUserAttribute);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setIsValueUnique(boolean)
   */
  public void setIsValueUnique(boolean isValueUnique) {
    this.getDecoratee().setIsValueUnique(isValueUnique);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setName(java.lang.String)
   */
  public void setName(String name) {
    this.getDecoratee().setName(name);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setProfileAttribType(com.npower.dm.core.ProfileAttributeType)
   */
  public void setProfileAttribType(ProfileAttributeType nwDmProfileAttribType) {
    this.getDecoratee().setProfileAttribType(nwDmProfileAttribType);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAttribute#setProfileTemplate(com.npower.dm.core.ProfileTemplate)
   */
  public void setProfileTemplate(ProfileTemplate nwDmProfileTemplate) {
    this.getDecoratee().setProfileTemplate(nwDmProfileTemplate);
  }


}
