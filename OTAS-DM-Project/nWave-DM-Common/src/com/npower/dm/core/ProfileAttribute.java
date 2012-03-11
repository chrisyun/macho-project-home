/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ProfileAttribute.java,v 1.5 2007/01/25 10:33:36 zhao Exp $
 * $Revision: 1.5 $
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
package com.npower.dm.core;

import java.util.Set;


/**
 * <p>Represent a ProfileAttribute.</p>
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/01/25 10:33:36 $
 * 
 */
public interface ProfileAttribute {

  /**
   * Return the ID.
   * @return
   */
  public abstract long getID();

  /**
   * Return the type of attribute.
   * 
   * @return
   */
  public abstract ProfileAttributeType getProfileAttribType();

  /**
   * Set the type of attribute.
   * 
   * @param nwDmProfileAttribType
   */
  public abstract void setProfileAttribType(ProfileAttributeType nwDmProfileAttribType);

  /**
   * Return the template which include this attribute.
   * @return
   */
  public abstract ProfileTemplate getProfileTemplate();

  /**
   * Set the template for this attribute.
   * 
   * @param nwDmProfileTemplate
   */
  public abstract void setProfileTemplate(ProfileTemplate nwDmProfileTemplate);

  /**
   * Return the name of attribute.
   * @return
   */
  public abstract String getName();

  /**
   * Set the name of the attribute.
   * @param name
   */
  public abstract void setName(String name);

  /**
   * Return the display name
   * @return the displayName
   */
  public abstract String getDisplayName();
  
  /**
   * Set the display name for this attribute.
   * @param displayName the displayName to set
   */
  public abstract void setDisplayName(String displayName);
  
  /**
   * Return description information.
   * @return the description
   */
  public abstract String getDescription();

  /**
   * Set description information.
   * @param description the description to set
   */
  public abstract void setDescription(String description);

  /**
   * If true, the value of this attribute do not allow NULL.
   * @return
   */
  public abstract boolean getIsRequired();

  /**
   * Set true, if the value of this attribute is not allowed NULL.
   * 
   * @param isRequired
   */
  public abstract void setIsRequired(boolean isRequired);

  /**
   * Return the default value.
   * @return
   */
  public abstract String getDefaultValue();

  /**
   * Set the default value.
   * @param defaultValue
   */
  public abstract void setDefaultValue(String defaultValue);

  /**
   * Return true, if this attributes is user's attribute, 
   * allow user assign value to this attribute.
   * 
   * @return
   */
  public abstract boolean getIsUserAttribute();

  /**
   * Set true, if this attribute allow user to assign value.
   * @param isUserAttribute
   */
  public abstract void setIsUserAttribute(boolean isUserAttribute);

  /**
   * Return true, multiple values are permmitted.
   * @return
   */
  public abstract boolean getIsMultivalued();

  /**
   * Set <code>true</code>, if multiple values are permitted.
   * @param isMultivalued
   */
  public abstract void setIsMultivalued(boolean isMultivalued);

  /**
   * Return true, if this attribute value will be displayed in the form.
   * @return
   */
  public abstract boolean getDisplayValue();

  /**
   * Set true, if this attribute value will be display in the form.
   * @param displayValue
   */
  public abstract void setDisplayValue(boolean displayValue);

  /**
   * Return true, if the value must be uniqued.
   * @return
   */
  public abstract boolean getIsValueUnique();

  /**
   * Set true, if the value must be uniqued.
   * @param isValueUnique
   */
  public abstract void setIsValueUnique(boolean isValueUnique);

  /**
   * Return the order of the attribute in ProfileTemplate.
   * @return
   */
  public abstract long getAttributeIndex();

  /**
   * Set the oder of the attribute in ProfileTemplate.
   * @param attributeIndex
   */
  public abstract void setAttributeIndex(long attributeIndex);

  /**
   * A set of parameters which belongs to this attributes.
   * 
   * @return Return a <code>Set</code> of {@link } objects.
   */
  //public abstract Set getProfileParameters();

  /**
   * A set of ProfileNodeMapping which reference this attribute.
   * 
   * @return Return a <code>Set</code> of {@link com.npower.dm.core.ProfileNodeMapping} objects
   */
  //public abstract Set getProfileNodeMappings();

  /**
   * A set of values for this attribute.
   * 
   * @return Return a set of {@link com.npower.dm.core.ProfileAttributeValue} objects.
   */
  public abstract Set<ProfileAttributeValue> getProfileAttribValues();

}