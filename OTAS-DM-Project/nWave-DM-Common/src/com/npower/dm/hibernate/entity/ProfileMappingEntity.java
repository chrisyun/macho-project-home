/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileMappingEntity.java,v 1.13 2008/12/10 05:24:20 zhao Exp $
 * $Revision: 1.13 $
 * $Date: 2008/12/10 05:24:20 $
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

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.13 $ $Date: 2008/12/10 05:24:20 $
 */
public class ProfileMappingEntity extends AbstractProfileMapping implements java.io.Serializable {

  private String modelExternalID = null;

  private String manufacturerExternalID = null;

  // Constructors

  /** default constructor */
  public ProfileMappingEntity() {
  }

  /** minimal constructor */
  public ProfileMappingEntity(DDFNode nwDmDdfNode, ProfileTemplate nwDmProfileTemplate, long changeVersion) {
    super(nwDmDdfNode, nwDmProfileTemplate, changeVersion);
  }

  /**
   * Set the Template's name of the Mapping. This method will find the
   * ProfileTemplateEntity from the DM inventory by the Template's name. if the
   * ProfileTemplateEntity wich specofoed in the Mapping, this method will throw
   * a DMException.
   * 
   * @param template
   */
  public void setProfileTemplateString(String templateName) throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ProfileTemplateBean bean = factory.createProfileTemplateBean();
        ProfileTemplate template = bean.getTemplateByName(templateName);
        if (template == null) {
           throw new DMException("Could not found template: " + templateName + 
                                 " for ProfileMapping: " + this.getManufacturerExternalID() + " " + this.getModelExternalID());
        }
        this.setProfileTemplate(template);
    } catch (DMException e) {
      throw e;
    } finally {
      //if (factory != null) {
      //   factory.release();
      //}
    }
  }

  /**
   * Setter of ShareRootNode true 1 yes 1 false 0 no 0
   * 
   * @param value
   */
  public void setShareRootNodeString(String value) {
    if (value != null && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes)"))) {
      this.setShareRootNode(true);
    } else {
      this.setShareRootNode(false);
    }
  }

  /**
   * Setter of assignToDevice true 1 yes 1 false 0 no 0
   * 
   * @param value
   */
  public void setAssignToDeviceString(String value) {
    if (value != null && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes)"))) {
      this.setAssignToDevice(true);
    } else {
      this.setAssignToDevice(false);
    }
  }

  /**
   * Setter of Linked Profile Type
   * 
   * @param value
   */
  public void setLinkedProfileType(String value) {
    super.setLinkedProfileType(value);
  }

  /**
   * Setter of NodeMapping, and link the ProfileNodeMappingEntity to
   * ProfileMappingEntity
   * 
   * @param set
   *          java.util.Set
   */
  public void setProfileNodeMappings(Set set) {
    if (set != null) {
      for (Iterator i = set.iterator(); i.hasNext();) {
        ProfileNodeMapping map = (ProfileNodeMapping) i.next();
        map.setProfileMapping(this);
      }
      super.setProfileNodeMappings(set);
    }
  }

  /**
   * Setter of rootNodePath, and find the DDFNodeEntity by the ModelEntity and
   * ManufacturerEntity. If the node could not be found in the DDFTreeEntity,
   * will throw a DMException.
   * 
   * @param nodePath
   * @throws DMException
   */
  public void setDdfNodeString(String nodePath) throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(this.getManufacturerExternalID());
        if (manufacturer == null) {
          throw new DMException("Could not find the Manufactuer: " + this.getManufacturerExternalID());
        }
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, this.getModelExternalID());
        if (model == null) {
          throw new DMException("Could not find the ModelEntity by ManufacturerEntity: " + this.getManufacturerExternalID()
              + " and the ModelEntity: " + this.getModelExternalID());
        }
  
        DDFTreeBean treeBean = factory.createDDFTreeBean();
  
        DDFNode node = treeBean.findDDFNodeByNodePath(model, nodePath);
        if (node == null) {
          throw new DMException("Could not found the DDFNodeEntity related with the RootNodePath: " + nodePath);
        }
        super.setRootDDFNode(node);
        super.setRootNodePath(nodePath.trim());
  
        // Link the ProfileMappingEntity to the DDFNodeEntity;
        node.getProfileMappings().add(this);
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
   * Getter of the ModelID of this ProfileMappingEntity represented by a
   * ProfileMappingEntity XML.
   * 
   * @return String ModelID of the ProfileMappingEntity in the
   *         ProfileMappingEntity
   */
  public String getModelExternalID() {
    return modelExternalID;
  }

  /**
   * Setter of the ModelIID of the ProfileMappingEntity represented by a
   * ProfileMappingEntity XML. This method used by the Common Digester for
   * Parsing the ProflleMapping XML.
   * 
   * @param modelExternalID
   */
  public void setModelExternalID(String modelExternalID) {
    this.modelExternalID = modelExternalID;
  }

  /**
   * Getter of the ManufacturerExternal ID of the ProfileMappingEntity
   * represented by a ProfileMappingEntity XML.
   * 
   * @return
   */
  public String getManufacturerExternalID() {
    return manufacturerExternalID;
  }

  /**
   * Setter of the ManufacturerEntity External ID of this ProfileMappingEntity
   * represented by a ProfileMappingEntity XML.
   * 
   * @param manufacturerID
   */
  public void setManufacturerExternalID(String manufacturerID) {
    this.manufacturerExternalID = manufacturerID;
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
  public void add(ProfileNodeMapping node) throws DMException {
    // String nodeRelativePath = node.getNodeRelativePathString();
    Set mappings = this.getProfileNodeMappings();
    ProfileNodeMappingEntity nd = (ProfileNodeMappingEntity)node;
    for (Iterator i = mappings.iterator(); i.hasNext();) {
        ProfileNodeMappingEntity nodeMap = (ProfileNodeMappingEntity) i.next();
        if (nodeMap.getNodeRelativePath().equals(nd.getNodeRelativePath())) {
          throw new DMException("The ProfileNodeMappingEntity which have same NodeRelativePath: "
              + nd.getNodeRelativePath());
        }
    }

    // Keep the NodeMapping order
    node.setParamIndex(counter4ParamIndex++);

    this.getProfileNodeMappings().add(node);
    // Link the ProfileMappingEntity to NodeMapping
    node.setProfileMapping(this);

    ((ProfileNodeMappingEntity)node).findDDFNode();
    ((ProfileNodeMappingEntity)node).findProfileAttribute();
  }

  public String toString() {
    // return ToStringBuilder.reflectionToString(this,
    // ToStringStyle.MULTI_LINE_STYLE);
    return new ToStringBuilder(this).append("profileTemplate", this.getProfileTemplate()).toString();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileMapping#getModels()
   */
  public Set<Model> getModels() {
    Set<ModelProfileMap> modelProfileMaps = this.getModelProfileMaps();
    Set<Model> models = new TreeSet();
    for (ModelProfileMap modelProfileMap: modelProfileMaps) {
        models.add(modelProfileMap.getModel());
    }
    return models;
  }

}
