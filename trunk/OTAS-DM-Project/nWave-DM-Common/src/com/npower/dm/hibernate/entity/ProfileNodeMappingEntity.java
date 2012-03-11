/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileNodeMappingEntity.java,v 1.22 2008/12/04 09:59:46 zhao Exp $
 * $Revision: 1.22 $
 * $Date: 2008/12/04 09:59:46 $
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


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.22 $ $Date: 2008/12/04 09:59:46 $
 */
public class ProfileNodeMappingEntity extends AbstractProfileNodeMapping implements java.io.Serializable {

  /**
   * Log Handler
   */
  private static Log log = LogFactory.getLog(ProfileNodeMapping.class);

  private String attributeNameString = null;
  
  private Map<String, String> valueTranslateMap = new HashMap<String, String>();
  
  private String nodeAbsolutePath = null;

  // Constructors

  /** default constructor */
  public ProfileNodeMappingEntity() {
    super.setMappingType(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE);
  }

  /** minimal constructor */
  public ProfileNodeMappingEntity(DDFNode ddfNode, String mappingType) {
    super(ddfNode, mappingType);
    super.setMappingType(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE);
  }

  /**
   * Setter of AttributeName for Digester
   * 
   * @param value
   */
  public void setAttributeNameString(String value) {
    this.attributeNameString = value;
  }

  /**
   * Getter of AttributeName for Digester
   * 
   * @param value
   */
  public String getAttributeNameString() {
    return this.attributeNameString;
  }

  /**
   * @return the absoluteNodePath
   */
  public String getNodeAbsolutePath() {
    return nodeAbsolutePath;
  }

  /**
   * @param absoluteNodePath the absoluteNodePath to set
   */
  public void setNodeAbsolutePath(String absoluteNodePath) {
    this.nodeAbsolutePath = absoluteNodePath;
  }

  /**
   * Find the DDFNodeEntity by the NodeRelativePathString from ModelEntity
   * DDFTrees. This method will be called by parsing process, and link the
   * DDFNodeEntity to this NodeMapping object by the NodeRelativePath.
   * 
   * @throws DMException
   *           If the Mapping Type is 'attribute', but missing DDFNodeEntity,
   *           will throw a DMException.
   */
  public DDFNode findDDFNode() throws DMException {

    ProfileMapping mapping = this.getProfileMapping();
    DDFNode baseNode = mapping.getRootDDFNode();
    
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        DDFTreeBean treeBean = factory.createDDFTreeBean();
        DDFNode node = null;
        if (baseNode != null && StringUtils.isEmpty(this.getNodeAbsolutePath())) {
           node = treeBean.findDDFNode(baseNode.getChildren(), this.getNodeRelativePath());
        } else {
          String nodePath = this.getNodeRelativePath();
          if (StringUtils.isNotEmpty(this.getNodeAbsolutePath())) {
            nodePath = this.getNodeAbsolutePath();
          }
          ModelBean modelBean = factory.createModelBean();
          Manufacturer manufacturer = modelBean.getManufacturerByExternalID(mapping.getManufacturerExternalID());
          if (manufacturer != null) {
             Model model = modelBean.getModelByManufacturerModelID(manufacturer, mapping.getModelExternalID());
             if (model != null ) {
                node = treeBean.findDDFNodeByNodePath(model, nodePath);
             }
          }
        }
  
        if (this.getMappingType().equalsIgnoreCase(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE) && node == null) {
           log.trace(baseNode.dump());
           throw new DMException("NodeMappingType is 'attribute', but missing the DDFNodeEntity: "
                                 + this.getNodeRelativePath());
        }

        this.setDdfNode(node);
        if (node != null) {
           // Link the NodeMapping to DDFNodeEntity
           node.getProfileNodeMappings().add(this);
        }
        return node;
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
   * Find the ProfileAttributeEntity by attribute's name from the
   * ProfileTemplateEntity owened by ProfileMappingEntity.
   * 
   * @throws DMException
   */
  public ProfileAttribute findProfileAttribute() throws DMException {
    ProfileAttribute attribute = null;
    if (StringUtils.isEmpty(this.getCategoryName())) {
       // Find Attribute in current profile template
       ProfileMapping parrent = this.getProfileMapping();
       ProfileTemplate template = parrent.getProfileTemplate();
       if (template == null) {
          return null;
       }
       attribute = template.getAttributeByAttributeName(this.getAttributeNameString());
    } else {
      // Find Attribute in linked profile templates
      ProfileTemplateBean templateBean = this.getManagementBeanFactory().createProfileTemplateBean();
      ProfileCategory profileCategory = templateBean.getProfileCategoryByName(this.getCategoryName());
      if (profileCategory == null) {
         throw new DMException("Could not found attribute: " + this.getAttributeNameString() + " in linked profile templates by category: " + this.getCategoryName());
      }
      for (ProfileTemplate template: profileCategory.getProfileTemplates()) {
          attribute = template.getAttributeByAttributeName(this.getAttributeNameString());
          if (attribute != null) {
             break;
          }
      }
    }

    this.setProfileAttribute(attribute);
    if (attribute != null && attribute instanceof com.npower.dm.hibernate.entity.ProfileAttributeEntity) {
       // Link the NodeMapping to Attribute
       ((ProfileAttributeEntity)attribute).getProfileNodeMappings().add(this);
    }
    return attribute;
  }

  /**
   * @return the valueTranslateMap
   */
  public Map<String, String> getValueTranslateMap() {
    return valueTranslateMap;
  }

  /**
   * @param valueTranslateMap the valueTranslateMap to set
   */
  public void setValueTranslateMap(Map<String, String> valueTranslateMap) {
    this.valueTranslateMap = valueTranslateMap;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileNodeMapping#translateValue(java.lang.String)
   */
  public String translateValue(String originalValue) {
    if (originalValue == null) {
       return originalValue;
    }
    Set<AttributeTranslations> translationSet = this.getAttribTranslationses();
    if (translationSet == null || translationSet.size() == 0) {
       return originalValue;
    }
    originalValue = originalValue.trim();
    for (AttributeTranslations translation: translationSet) {
        if (originalValue.equals(translation.getID().getOriginalValue())) {
           return translation.getID().getValue();
        }
    }
    return originalValue;
  }

  /**
   * For common digester.
   * @param orginalValue
   * @param newValue
   */
  public void addValueTranslate(String orginalValue, String newValue) {
    this.valueTranslateMap.put(orginalValue, newValue);
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(T)
   */
  public int compareTo(Object o) {
    if (o != null && o instanceof ProfileNodeMapping) {
       ProfileNodeMapping other = (ProfileNodeMapping)o;
       // 基于Param Index计算次序
       int result = (int)(this.getParamIndex() - other.getParamIndex());
       if (result == 0) {
          // 若相等或未指定Param Index, 按照ID计算次序
         return (int)(this.getID() - other.getID());
       }
    }
    return -1;
  }

}
