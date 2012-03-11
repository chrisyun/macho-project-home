/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileAttributeEntity.java,v 1.7 2006/11/08 02:16:52 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2006/11/08 02:16:52 $
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

import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2006/11/08 02:16:52 $
 */
public class ProfileAttributeEntity extends AbstractProfileAttribute implements java.io.Serializable, Comparable {

  // Constructors

  /** default constructor */
  public ProfileAttributeEntity() {
  }

  /** minimal constructor */
  public ProfileAttributeEntity(String name, boolean isRequired, boolean isUserAttribute, boolean isMultivalued,
      boolean displayValue, boolean isValueUnique) {
    super(name, isRequired, isUserAttribute, isMultivalued, displayValue, isValueUnique);
  }

  /** full constructor */
  public ProfileAttributeEntity(ProfileAttributeType nwDmProfileAttribType, ProfileTemplate nwDmProfileTemplate,
      String name, boolean isRequired, String defaultValue, boolean isUserAttribute, boolean isMultivalued,
      boolean displayValue, boolean isValueUnique, long attributeIndex, Set nwDmProfileParameters,
      Set nwDmProfileNodeMappings, Set nwDmProfileAttribValues) {
    super(nwDmProfileAttribType, nwDmProfileTemplate, name, isRequired, defaultValue, isUserAttribute, isMultivalued,
        displayValue, isValueUnique, attributeIndex, nwDmProfileParameters, nwDmProfileNodeMappings,
        nwDmProfileAttribValues);
  }

  /**
   * Setter for string parameter, using by Digester rules
   * 
   * @param flag
   */
  public void setIsMultivaluedByString(String flag) {
    if (flag != null && (flag.equalsIgnoreCase("yes") || flag.equalsIgnoreCase("true"))) {
      this.setIsMultivalued(true);
    } else {
      setIsMultivalued(false);
    }
  }

  /**
   * Setter for string parameter, using by Digester rules
   * 
   * @param flag
   */
  public void setIsRequiredByString(String flag) {
    if (flag != null && (flag.equalsIgnoreCase("yes") || flag.equalsIgnoreCase("true"))) {
      this.setIsRequired(true);
    } else {
      setIsRequired(false);
    }
  }

  /**
   * Setter for string parameter, using by Digester rules
   * 
   * @param flag
   */
  public void setIsUserAttributeByString(String flag) {
    if (flag != null && (flag.equalsIgnoreCase("yes") || flag.equalsIgnoreCase("true"))) {
      this.setIsUserAttribute(true);
    } else {
      setIsUserAttribute(false);
    }
  }

  /**
   * Setter for string parameter, using by Digester rules
   * 
   * @param flag
   */
  public void setIsValueUniqueByString(String flag) {
    if (flag != null && (flag.equalsIgnoreCase("yes") || flag.equalsIgnoreCase("true"))) {
      this.setIsValueUnique(true);
    } else {
      setIsValueUnique(false);
    }
  }

  /**
   * Setter for string parameter, using by Digester rules
   * 
   * @param flag
   */
  public void setDisplayValueByString(String flag) {
    if (flag != null && (flag.equalsIgnoreCase("yes") || flag.equalsIgnoreCase("true"))) {
      this.setDisplayValue(true);
    } else {
      setDisplayValue(false);
    }
  }

  /**
   * Setter for AttributeType, using by Digester rules
   * @param name
   * @throws DMException
   */
  public void setAttributeTypeByName(String name) throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ProfileTemplateBean bean = factory.createProfileTemplateBean();
        ProfileAttributeType type = bean.getProfileAttributeTypeByName(name);
        
        if (type != null) {
          this.setProfileAttribType(type);
        } else {
          throw new DMException("Could not found AttributeType: " + name + " for ProfileAttributeEntity:" + this.getName());
        }
    } catch (DMException e) {
      throw e;
    } finally {
      //if (factory != null) {
      //   factory.release();
      //}
    }
  }
  
  private ManagementBeanFactory factory = null;
  public void setManagementBeanFactory(ManagementBeanFactory factory) {
    this.factory = factory;
  }

  public ManagementBeanFactory getManagementBeanFactory() {
    return this.factory;
  }

  // Common methods -------------------------------------------------------------------------------------
  // **************************************************************************
  /**
   * Compare two ProfileAttributeEntity, based on attributeIndex
   * 
   * @param o
   *          Object, instance of ProfileAttributeEntity.
   * 
   */
  public int compareTo(Object o) {
    if (o instanceof ProfileAttribute) {
      ProfileAttribute attr = (ProfileAttribute) o;
      return (int) (this.getAttributeIndex() - attr.getAttributeIndex());
    }
    return -1;
  }

  public String toString() {
    return new ToStringBuilder(this).append("name", this.getName()).append("defaultValue", this.getDefaultValue())
        .append("attributeIndex", this.getAttributeIndex()).append("displayValue", this.getDisplayValue()).append(
            "isRequired", this.getIsRequired()).append("isUserAttribute", this.getIsUserAttribute()).append(
            "isMultivalued", this.getIsMultivalued()).toString();

  }
}
