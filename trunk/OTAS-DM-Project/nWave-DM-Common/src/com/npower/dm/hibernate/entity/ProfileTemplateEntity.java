/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileTemplateEntity.java,v 1.8 2006/11/08 02:16:52 zhao Exp $
 * $Revision: 1.8 $
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

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2006/11/08 02:16:52 $
 */
public class ProfileTemplateEntity extends AbstractProfileTemplate implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public ProfileTemplateEntity() {
  }

  /** minimal constructor */
  public ProfileTemplateEntity(ProfileCategory profileCategory, String name, long changeVersion) {
    super(profileCategory, name, changeVersion);
  }

  /**
   * Setter for string parameter, using by Digester rules
   * 
   * @param flag
   */
  public void setNeedsNapByString(String flag) {
    if (flag != null && (flag.equalsIgnoreCase("yes") || flag.equalsIgnoreCase("true"))) {
      this.setNeedsNap(true);
    } else {
      setNeedsNap(false);
    }
  }

  /**
   * Setter for string parameter, using by Digester rules
   * 
   * @param flag
   */
  public void setNeedsProxyByString(String flag) {
    if (flag != null && (flag.equalsIgnoreCase("yes") || flag.equalsIgnoreCase("true"))) {
      this.setNeedsProxy(true);
    } else {
      setNeedsProxy(false);
    }
  }

  /**
   * Load ProfileCategoryEntity by name from DM Inventory. The method will use
   * HibernateSession from thread pool
   * 
   * @param name
   * @throws DMException
   */
  public void setProfileCategoryByName(String name) throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ProfileTemplateBean bean = factory.createProfileTemplateBean();
        ProfileCategory category = bean.getProfileCategoryByName(name);
        if (category != null) {
           this.setProfileCategory(category);
        } else {
          throw new DMException("Could not found category: " + name + " for ProfileTemplateEntity:" + this.getName());
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

  /**
   * Find a attribute by name include by the ProfileTemplateEntity.
   * 
   * Attribute's name is case-sensetive.
   * 
   * @param name
   *          Attribute's name
   * @return If no attribute match the name, will return NULL
   */
  public ProfileAttribute getAttributeByAttributeName(String name) {
    Set set = this.getProfileAttributes();
    for (Iterator i = set.iterator(); i.hasNext();) {
      ProfileAttribute attribute = (ProfileAttribute) i.next();
      if (attribute.getName().equals(name)) {
        return attribute;
      }
    }
    return null;
  }

  public String toString() {
    // return ToStringBuilder.reflectionToString(this,
    // ToStringStyle.MULTI_LINE_STYLE);
    return new ToStringBuilder(this).append("templateID", this.getID()).append("name", this.getName()).toString();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileTemplate#getModels()
   */
  public Set<Model> getModels() {
    Set<ProfileMapping> profileMappings = this.getProfileMappings();
    Set<Model> models = new TreeSet();
    for (ProfileMapping profileMapping: profileMappings) {
        Set<Model> subset = profileMapping.getModels();
        models.addAll(subset);
    }
    return models;
  }
  
  /**
   * counter for order Index
   */
  private long counter4ParamIndex = 0;

  /**
   * Add a ProfileNodeMappingEntity into this ProfileMappingEntity.
   * 
   * @param node
   *          The node will be added into this ProfileMappingEntity.
   * @throws DMException
   */
  public void add(ProfileAttribute attribute) throws DMException {

    // Keep the order
    attribute.setAttributeIndex(counter4ParamIndex++);

    this.getProfileAttributes().add(attribute);
  }

}
