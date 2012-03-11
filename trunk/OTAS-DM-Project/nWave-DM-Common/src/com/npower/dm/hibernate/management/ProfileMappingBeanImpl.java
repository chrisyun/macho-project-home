/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/ProfileMappingBeanImpl.java,v 1.17 2008/12/12 10:33:35 zhao Exp $
 * $Revision: 1.17 $
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
package com.npower.dm.hibernate.management;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.hibernate.entity.AttributeTranslations;
import com.npower.dm.hibernate.entity.AttributeTranslationsID;
import com.npower.dm.hibernate.entity.ProfileMappingEntity;
import com.npower.dm.hibernate.entity.ProfileNodeMappingEntity;
import com.npower.dm.hibernate.management.digester.ProfileMappingEntityFactory;
import com.npower.dm.hibernate.management.digester.ProfileNodeMappingEntityFactory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileMappingBean;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.17 $ $Date: 2008/12/12 10:33:35 $
 */
public class ProfileMappingBeanImpl extends AbstractBean implements ProfileMappingBean {

  /** Log Handle ******************************************************* */
  private static Log log = LogFactory.getLog(ProfileMappingBeanImpl.class);

  /**
   * Default constructor
   */
  protected ProfileMappingBeanImpl() {
    super();
  }

  public ProfileMappingBeanImpl(ManagementBeanFactory factory, Session session) {
    super(factory, session);
  }

  // Methods related with ProfileMappingEntity.class
  // *******************************************************************
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileMappingBean#add(com.npower.dm.hibernate.ProfileMappingEntity)
   */
  public void update(ProfileMapping mapping) throws DMException {
    // Save ProfileMappingEntity first
    Session session = this.getHibernateSession();
    session.save(mapping);

    // Save all of NodeMapping
    Set<ProfileNodeMapping> nodeMappings = mapping.getProfileNodeMappings();
    for (Iterator<ProfileNodeMapping> i = nodeMappings.iterator(); i.hasNext();) {
        ProfileNodeMapping nodeMapping = (ProfileNodeMapping) i.next();
        session.saveOrUpdate(nodeMapping);
        
        Map<String, String> valueTranslationMap = ((ProfileNodeMappingEntity)nodeMapping).getValueTranslateMap();
        for (String oriValue: valueTranslationMap.keySet()) {
            String newValue = valueTranslationMap.get(oriValue);
            AttributeTranslationsID translationID = new AttributeTranslationsID();
            translationID.setNodeMappingId(nodeMapping.getID());
            translationID.setOriginalValue(oriValue);
            translationID.setValue(newValue);
            AttributeTranslations translation = new AttributeTranslations(translationID, nodeMapping);
            session.saveOrUpdate(translation);
            ((ProfileNodeMappingEntity)nodeMapping).getAttribTranslationses().add(translation);
        }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileMappingBean#delete(com.npower.dm.hibernate.ProfileMappingEntity)
   */
  public void delete(ProfileMapping mapping) throws DMException {
    Set<ProfileNodeMapping> nodeMappings = mapping.getProfileNodeMappings();
    for (Iterator<ProfileNodeMapping> i = nodeMappings.iterator(); i.hasNext();) {
      this.getHibernateSession().delete((ProfileNodeMapping) i.next());
    }

    this.getHibernateSession().delete(mapping);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileMappingBean#getProfileMappingByID(long)
   */
  public ProfileMapping getProfileMappingByID(long id) throws DMException {
    Session session = this.getHibernateSession();
    try {
      ProfileMapping map = (ProfileMapping) session.get(ProfileMappingEntity.class, new Long(id));
      return map;
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileMappingBean#parsingProfileMapping(java.io.InputStream)
   */
  public List<ProfileMapping> parsingProfileMapping(InputStream in, String defaultManufacturerExternalID, String defaultModelExternalID) throws DMException {
    // Create and execute our Digester
    Digester digester = createProfileMappingDigester(defaultManufacturerExternalID, defaultModelExternalID);
    try {
      // Push a ManagementBeanFactory into the stack.
      digester.push(this.getManagementBeanFactory());
      
      List<ProfileMapping> result = new ArrayList<ProfileMapping>();
      digester.push(result);
      digester.parse(in);
      return result;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileMappingBean#parsingProfileMapping(java.io.InputStream)
   */
  public List<ProfileMapping> parsingProfileMapping(InputStream in) throws DMException {
    return this.parsingProfileMapping(in, null, null);
  }

  private Digester createProfileMappingDigester(String defaultManufacturerExternalID, String defaultModelExternalID) {
    // Initialize the digester
    Digester digester = new Digester();
    digester.setValidating(false);

    // Parsing ProfileMappingEntity
    //digester.addObjectCreate("ProfileMetaData/ProfileMappings/ProfileMapping", "com.npower.dm.hibernate.entity.ProfileMappingEntity");
    ProfileMappingEntityFactory mappingEntityFactory = new ProfileMappingEntityFactory();
    mappingEntityFactory.setDefaultManufacturerExternalID(defaultManufacturerExternalID);
    mappingEntityFactory.setDefaultModelExternalID(defaultModelExternalID);
    digester.addFactoryCreate("ProfileMetaData/ProfileMappings/ProfileMapping", mappingEntityFactory);
    if (StringUtils.isEmpty(defaultManufacturerExternalID) || StringUtils.isEmpty(defaultModelExternalID)) {
       // If Default modelExternal is null, load modelExtID from XML file.
       digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/ModelName", "modelExternalID");
       digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/ManufacturerExternalId", "manufacturerExternalID");
    }
    
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/ShareRootNode", "shareRootNodeString");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/CanAssignToDevice", "assignToDeviceString");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/LinkedProfileCategoryName", "linkedProfileType");

    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/TemplateName", "profileTemplateString");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/RootNodePath", "ddfNodeString");

    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/NeedToDiscovery", "needToDiscovery");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/DiscoveryNodePaths", "discoveryNodePaths");

    digester.addSetNext("ProfileMetaData/ProfileMappings/ProfileMapping", "add");

    // digester.addObjectCreate("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings",
    // "java.util.HashSet");

    digester.addBeanPropertySetter("*/Command", "command");
    digester.addBeanPropertySetter("*/ValueFormat", "valueFormat");
    digester.addBeanPropertySetter("*/DefaultValueMimeType", "defaultValueMimeType");

    // MapptingType: value
    //digester.addObjectCreate("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/ValueMapping",
    //    "com.npower.dm.hibernate.entity.ProfileNodeMappingEntity");
    digester.addFactoryCreate("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/ValueMapping",
                              ProfileNodeMappingEntityFactory.class);
    
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/ValueMapping/Name",
        "displayName");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/ValueMapping/Name",
        "attributeNameString");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/ValueMapping/Value",
        "value");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/ValueMapping/NodeRelativePath",
        "nodeRelativePath");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/ValueMapping/NodeAbsolutePath",
        "nodeAbsolutePath");
    digester.addSetNext("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/ValueMapping", "add");

    // MapptingType: attribute
    //digester.addObjectCreate("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/AttributeMapping",
    //    "com.npower.dm.hibernate.entity.ProfileNodeMappingEntity");
    digester.addFactoryCreate("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/AttributeMapping",
                               ProfileNodeMappingEntityFactory.class);
    
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/AttributeMapping/AttributeName",
        "attributeNameString");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/AttributeMapping/NodeRelativePath",
        "nodeRelativePath");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/AttributeMapping/NodeAbsolutePath",
        "nodeAbsolutePath");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/AttributeMapping/LinkedProfileCategoryName",
        "categoryName");
    digester.addSetNext("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings/AttributeMapping", "add");
    
    // For value translation.
    digester.addCallMethod("*/ValueTranslations/ValueTranslation", "addValueTranslate", 2);
    digester.addCallParam("*/ValueTranslations/ValueTranslation/Value", 0);
    digester.addCallParam("*/ValueTranslations/ValueTranslation/DeviceValue", 1);

    // digester.addSetNext("ProfileMetaData/ProfileMappings/ProfileMapping/NodeMappings",
    // "setProfileNodeMappings");

    return (digester);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileMappingBean#importProfileMapping(java.io.InputStream)
   */
  public List<ProfileMapping> importProfileMapping(InputStream in, String defaultManufacturerExternalID, String defaultModelExternalID) throws DMException {

    Transaction tx = null;
    try {
      List<ProfileMapping> profileMappings = this.parsingProfileMapping(in, defaultManufacturerExternalID, defaultModelExternalID);
      tx = this.getHibernateSession().beginTransaction();
      for (int i = 0; i < profileMappings.size(); i++) {
        ProfileMapping profileMapping = (ProfileMapping) profileMappings.get(i);
        ProfileTemplate template = profileMapping.getProfileTemplate();
        if (template == null) {
           throw new DMException("The Model:" + profileMapping.getManufacturerExternalID() +
                                 "." + profileMapping.getModelExternalID() + 
                                 " ProfileMapping#" + (i + 1) + " which will be imported miss the ProfileTemplate.");
        }
        log.trace("add:" + profileMapping);
        this.update(profileMapping);
      }
      tx.commit();
      return profileMappings;
    } catch (Exception e) {
      if (tx != null && tx.isActive()) {
        tx.rollback();
      }
      throw new DMException(e);
    }

  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileMappingBean#importProfileMapping(java.io.InputStream)
   */
  public List<ProfileMapping> importProfileMapping(InputStream in) throws DMException {
    return this.importProfileMapping(in, null, null);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileMappingBean#newProfileMappingInstance()
   */
  public ProfileMapping newProfileMappingInstance() throws DMException {
    return new ProfileMappingEntity();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileMappingBean#newProfileNodeMappingInstance()
   */
  public ProfileNodeMapping newProfileNodeMappingInstance() throws DMException {
    return new ProfileNodeMappingEntity();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileMappingBean#getProfileNodeMappingByID(java.lang.String)
   */
  public ProfileNodeMapping getProfileNodeMappingByID(String id) throws DMException {
    Session session = this.getHibernateSession();
    try {
        ProfileNodeMapping node = (ProfileNodeMapping) session.get(ProfileNodeMappingEntity.class, new Long(id));
        return node;
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileMappingBean#delete(com.npower.dm.core.ProfileNodeMapping)
   */
  public void delete(ProfileNodeMapping nodeMapping) throws DMException {
    ProfileMapping mapping = nodeMapping.getProfileMapping();
    mapping.getProfileNodeMappings().remove(nodeMapping);
    this.getHibernateSession().delete(nodeMapping);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileMappingBean#update(com.npower.dm.core.ProfileNodeMapping)
   */
  public void update(ProfileNodeMapping nodeMapping) throws DMException {
    // Save ProfileMappingEntity first
    Session session = this.getHibernateSession();
    session.saveOrUpdate(nodeMapping);
    nodeMapping.getProfileMapping().getProfileNodeMappings().add(nodeMapping);
  }

}
