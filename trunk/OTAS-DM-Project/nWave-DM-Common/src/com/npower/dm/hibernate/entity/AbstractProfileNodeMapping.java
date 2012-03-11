/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProfileNodeMapping.java,v 1.8 2008/12/12 10:33:35 zhao Exp $
 * $Revision: 1.8 $
 * $Date: 2008/12/12 10:33:35 $
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

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileNodeMapping;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2008/12/12 10:33:35 $
 */
public abstract class AbstractProfileNodeMapping implements java.io.Serializable, Comparable, ProfileNodeMapping {

  // Fields

  private long ID;

  private ProfileMapping profileMapping;

  private ProfileAttribute profileAttribute;

  private DDFNode ddfNode;

  private String nodeRelativePath = null;

  private String mappingType = null;

  private String value;
  
  private String valueFormat;
  
  private String defaultValueMimeType;

  private String displayName;

  private String categoryName;

  private long paramIndex;
  
  private String command;

  private Set attribTranslationses = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractProfileNodeMapping() {
    super();
  }

  /** minimal constructor */
  public AbstractProfileNodeMapping(DDFNode nwDmDdfNode, String mappingType) {
    this.ddfNode = nwDmDdfNode;
    this.mappingType = mappingType;
  }

  /** full constructor */
  public AbstractProfileNodeMapping(ProfileMapping nwDmProfileMapping, ProfileAttribute nwDmProfileAttribute,
      DDFNode nwDmDdfNode, String mappingType, String value, String displayName, String categoryName, long paramIndex,
      Set nwDmAttribTranslationses) {
    this.profileMapping = nwDmProfileMapping;
    this.profileAttribute = nwDmProfileAttribute;
    this.ddfNode = nwDmDdfNode;
    this.mappingType = mappingType;
    this.value = value;
    this.displayName = displayName;
    this.categoryName = categoryName;
    this.paramIndex = paramIndex;
    this.attribTranslationses = nwDmAttribTranslationses;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long nodeMappingId) {
    this.ID = nodeMappingId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#getProfileMapping()
   */
  public ProfileMapping getProfileMapping() {
    return this.profileMapping;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#setProfileMapping(com.npower.dm.hibernate.ProfileMapping)
   */
  public void setProfileMapping(ProfileMapping nwDmProfileMapping) {
    this.profileMapping = nwDmProfileMapping;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#getProfileAttribute()
   */
  public ProfileAttribute getProfileAttribute() {
    return this.profileAttribute;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#setProfileAttribute(com.npower.dm.hibernate.ProfileAttribute)
   */
  public void setProfileAttribute(ProfileAttribute nwDmProfileAttribute) {
    this.profileAttribute = nwDmProfileAttribute;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#getDdfNode()
   */
  public DDFNode getDdfNode() {
    return this.ddfNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#setDdfNode(com.npower.dm.hibernate.DDFNode)
   */
  public void setDdfNode(DDFNode nwDmDdfNode) {
    this.ddfNode = nwDmDdfNode;
  }

  /**
   * Setter of AttributeName for Digester
   * 
   * @param value
   */
  public void setNodeRelativePath(String value) {
    this.nodeRelativePath = value;
  }

  /**
   * Setter NodeRelatvePath for Digester
   * 
   * @param value
   */
  public String getNodeRelativePath() {
    if (StringUtils.isEmpty(this.nodeRelativePath)) {
       if (this.getProfileMapping() != null) {
         DDFNode rootNode = this.getProfileMapping().getRootDDFNode();
         DDFNode node = this.getDdfNode();
         if (rootNode != null && node != null) {
            try {
                this.nodeRelativePath = node.caculateRelativeNodePath(rootNode);
            } catch (DMException e) {
              // Ignore error.
              //throw new RuntimeException("Error in caculate relative node path: " + e.getMessage(), e);
            }
         }
       }
       
    }
    return this.nodeRelativePath;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#getMappingType()
   */
  public String getMappingType() {
    return this.mappingType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#setMappingType(java.lang.String)
   */
  public void setMappingType(String mappingType) {
    this.mappingType = mappingType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#getValue()
   */
  public String getValue() {
    return this.value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#setValue(java.lang.String)
   */
  public void setValue(String value) {
    this.value = value;
    if (value != null && this.mappingType != null) {
       this.setMappingType(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#getDisplayName()
   */
  public String getDisplayName() {
    return this.displayName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#setDisplayName(java.lang.String)
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#getCategoryName()
   */
  public String getCategoryName() {
    return this.categoryName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#setCategoryName(java.lang.String)
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#getParamIndex()
   */
  public long getParamIndex() {
    return this.paramIndex;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#setParamIndex(long)
   */
  public void setParamIndex(long paramIndex) {
    this.paramIndex = paramIndex;
  }

  /**
   * @return the valueFormat
   */
  public String getValueFormat() {
    return valueFormat;
  }

  /**
   * @param valueFormat the valueFormat to set
   */
  public void setValueFormat(String valueFormat) {
    this.valueFormat = valueFormat;
  }

  /**
   * @return the command
   */
  public String getCommand() {
    return command;
  }

  /**
   * @param command the command to set
   */
  public void setCommand(String command) {
    this.command = command;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileNodeMapping#getAttribTranslationses()
   */
  public Set getAttribTranslationses() {
    return this.attribTranslationses;
  }

  public void setAttribTranslationses(Set nwDmAttribTranslationses) {
    this.attribTranslationses = nwDmAttribTranslationses;
  }

  /**
   * @return the defaultValueMimeType
   */
  public String getDefaultValueMimeType() {
    return defaultValueMimeType;
  }

  /**
   * @param defaultValueMimeType the defaultValueMimeType to set
   */
  public void setDefaultValueMimeType(String defaultValueMimeType) {
    this.defaultValueMimeType = defaultValueMimeType;
  }

}