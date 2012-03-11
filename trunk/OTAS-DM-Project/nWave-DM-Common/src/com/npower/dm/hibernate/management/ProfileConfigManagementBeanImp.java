/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/ProfileConfigManagementBeanImp.java,v 1.9 2008/04/28 10:38:56 zhao Exp $
 * $Revision: 1.9 $
 * $Date: 2008/04/28 10:38:56 $
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

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.hibernate.entity.ProfileAttributeValueEntity;
import com.npower.dm.hibernate.entity.ProfileConfigEntity;
import com.npower.dm.hibernate.entity.ProfileValueItem;
import com.npower.dm.hibernate.entity.ProfileValueMap;
import com.npower.dm.hibernate.entity.ProfileValueMapID;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/04/28 10:38:56 $
 */
public class ProfileConfigManagementBeanImp extends AbstractBean implements ProfileConfigBean {

  private static Log log = LogFactory.getLog(ProfileConfigManagementBeanImp.class);

  /**
   * 
   */
  protected ProfileConfigManagementBeanImp() {
    super();
  }

  /**
   * 
   * @param factory
   * @param hsession
   */
  public ProfileConfigManagementBeanImp(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }

  // public methods
  // *******************************************************************************************
  public ProfileConfig newProfileConfigInstance(String name, Carrier carrier, ProfileTemplate template, String type)
      throws DMException {
    // Generate a uniqued External ID
    String externalID = template.getProfileCategory().getName() + "." + name;
    if (carrier != null) {
       externalID = carrier.getExternalID() + "." + externalID;
    }
    return this.newProfileConfigInstance(externalID, name, carrier, template, type);
  }

  public ProfileConfig newProfileConfigInstance(String externalID, String name, Carrier carrier, ProfileTemplate template, String type)
      throws DMException {
    return new ProfileConfigEntity(externalID, name, carrier, template, type);
  }

  /**
   * Add a ProfileConfigEntity into DM inventory.
   * 
   * @see com.npower.dm.management.ProfileConfigBean#update(com.npower.dm.hibernate.entity.ProfileConfigEntity)
   */
  public void update(ProfileConfig profile) throws DMException {
    if (profile == null) {
      throw new NullPointerException("Could not add a null ProfileConfigEntity into database.");
    }
    // boolean isNew = (profile.getProfileId() == 0);

    Session session = this.getHibernateSession();
    try {
      // Save the Profile first
      session.save(profile);

      // Create the default value list

    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * Get and find a ProfileConfigEntity by ID
   * 
   * @see com.npower.dm.management.ProfileConfigBean#getProfileConfigByID(java.lang.String)
   */
  public ProfileConfig getProfileConfigByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ProfileConfigEntity where ID='" + id + "'");
      List<ProfileConfig> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileConfig) list.get(0);
      } else {
        throw new DMException("Result is not unique, more ConfigProfiles have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileConfigBean#getProfileConfigByExternalID(java.lang.String)
   */
  public ProfileConfig getProfileConfigByExternalID(String externalID) throws DMException {
    if (externalID == null || externalID.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ProfileConfigEntity where externalID='" + externalID + "'");
      List<ProfileConfig> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileConfig) list.get(0);
      } else {
        throw new DMException("Result is not unique, more ConfigProfiles have the same externalID: " + externalID);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * Find a ProfileConfigEntity By it's name.
   * 
   * @see com.npower.dm.management.ProfileConfigBean#getProfileConfigByName(java.lang.String)
   */
  public ProfileConfig getProfileConfigByName(String carrierExtID, String categoryName, String name) throws DMException {
    if (name == null || name.trim().length() == 0) {
      return null;
    }
    if (carrierExtID == null || carrierExtID.trim().length() == 0) {
       throw new DMException("Missing carrierExtID to find a profile config.");
    }
    if (categoryName == null || categoryName.trim().length() == 0) {
       throw new DMException("Missing categoryName to find a profile config.");
    }
    ManagementBeanFactory factory = this.getManagementBeanFactory();    
    CarrierBean carrierBean = factory.createCarrierBean();
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    
    Session session = this.getHibernateSession();
    try {
      ProfileCategory category = templateBean.getProfileCategoryByName(categoryName);
      if (category == null) {
         throw new DMException("Missing Profile Category to find a profile config, could not found category: " + categoryName);
      }
      Carrier carrier = carrierBean.getCarrierByExternalID(carrierExtID);
      if (carrier == null) {
         return null;
         //throw new DMException("Missing Carrier to find a profile config, could not found carrier: " + carrierExtID);
      }
      
      Criteria criteria = session.createCriteria(ProfileConfigEntity.class);
      
      Criteria subCriteria = criteria.createCriteria("profileTemplate");
      subCriteria.add(Expression.eq("profileCategory", category));
      criteria.add(Expression.eq("carrier", carrier));
      criteria.add(Expression.eq("name", name));      

      List<ProfileConfig> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileConfig) list.get(0);
      } else {
        throw new DMException("Result is not unique, more ProfileConfigEntity have the same name: " + name + "[Category: " + categoryName + ", carrier: " + carrierExtID + "]");
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * Fina and list all of the ProfileConfigs by the HibernateSQL. For Exmaple:
   * "from ProfileConfigEntity where ...."
   * 
   * @see com.npower.dm.management.ProfileConfigBean#findProfileConfigs(java.lang.String)
   */
  public List<ProfileConfig> findProfileConfigs(String whereClause) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery(whereClause);
      List<ProfileConfig> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * Delete a ProfileConfigEntity from the DM inventory.
   * 
   * @see com.npower.dm.management.ProfileConfigBean#deleteProfileConfig(com.npower.dm.hibernate.entity.ProfileConfigEntity)
   */
  public void deleteProfileConfig(ProfileConfig config) throws DMException {
    Session session = this.getHibernateSession();
    try {
      log.trace("deleting:" + config);
      session.delete(config);
      log.trace("deleted:" + config);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * Add or UpdateEntity the value of ProfileAttributeEntity ownen by the
   * ProfileConfigEntity. If not found ProfileAttributeValueEntity by the
   * attributename, will create a single value, binary mode value object. If
   * found it, will override the attribute to single value, binary mode value's
   * object. all of multi-value item will be deleted.
   * 
   * Caution: Assign null to value is permitted. this will set the value to
   * null, AttributeValue will not be deleted!
   * 
   * Caution: Order of AttributeValue will automaticlly increased! The
   * AttributeValue added lastestly will be bottom.
   * 
   * @param name
   * @param value
   * @throws DMException
   */
  public void setAttributeValue(ProfileConfig config, String name, InputStream value) throws DMException, IOException {

    // update this profile config, first. make sure the profileID will generated
    // by hibernate.
    Session hsession = this.getHibernateSession();
    hsession.saveOrUpdate(config);

    Blob blobValue = null;
    try {
      blobValue = (value != null) ? Hibernate.createBlob(value) : null;
    } catch (IOException e) {
      throw e;
    }

    // Check exists?
    Set<ProfileValueMap> vMaps = ((ProfileConfigEntity)config).getProfileValueMaps();
    for (Iterator<ProfileValueMap> i = vMaps.iterator(); i.hasNext();) {
      ProfileValueMap vMap = (ProfileValueMap) i.next();
      ProfileAttributeValueEntity v = (ProfileAttributeValueEntity)vMap.getProfileAttribValue();
      if (name.equals(v.getProfileAttribute().getName())) {
        v.setBinaryData(blobValue);
        // Set to single value mode
        v.setIsMultiValued(false);
        v.setItemDataKind(ProfileAttributeValue.ITEM_DATA_KIND_TEXT);
        v.setMFormat(DDFNode.DDF_FORMAT_BIN);
        // Delete multiple value
        Set<?> valueItemSet = v.getProfileValueItems();
        for (Iterator<?> j = valueItemSet.iterator(); j.hasNext();) {
          hsession.delete(j.next());
        }
        valueItemSet.clear();
        return;
      }
    }

    // Create a new AttributeValue
    ProfileTemplate template = config.getProfileTemplate();
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    ProfileAttribute attr = factory.createProfileTemplateBean().getProfileAttributeByName(template.getName(), name);
    if (attr == null) {
      throw new DMException("Could not find attribute by name: " + name + " from the template: " + template.getName());
    }

    ProfileAttributeValue av = new ProfileAttributeValueEntity();
    av.setProfileAttribute(attr);
    av.setBinaryData(blobValue);
    av.setIsMultiValued(false);
    av.setItemDataKind(ProfileAttributeValue.ITEM_DATA_KIND_TEXT);
    av.setMFormat(DDFNode.DDF_FORMAT_BIN);
    hsession.saveOrUpdate(av);

    // New a ValueMapID
    ProfileValueMapID mapID = new ProfileValueMapID();
    mapID.setAttributeValueId(av.getID());
    mapID.setProfileId(config.getID());
    // New a ProfileValueMap
    long index = ((ProfileConfigEntity)config).getProfileValueMaps().size() + 1;
    ProfileValueMap map = new ProfileValueMap(mapID, av, config, index);

    // Link to ProfileConfigEntity
    ((ProfileConfigEntity)config).getProfileValueMaps().add(map);

    hsession.saveOrUpdate(map);
  }

  /**
   * Add or UpdateEntity the value of ProfileAttributeEntity ownen by the
   * ProfileConfigEntity. If not found ProfileAttributeValueEntity by the
   * attributename, will create a single value, text mode value object. If found
   * it, will override the attribute to single value, text mode value's object.
   * all of multi-value item will be deleted.
   * 
   * Caution: Assign null to value is permitted. this will set the value to
   * null, AttributeValue will not be deleted!
   * 
   * Caution: Order of AttributeValue will automaticlly increased! The
   * AttributeValue added lastestly will be bottom.
   * 
   * @param name
   * @param value
   * @throws DMException
   */
  public void setAttributeValue(ProfileConfig config, String name, String value) throws DMException {

    // update this profile config, first. make sure the profileID will generated
    // by hibernate.
    Session hsession = this.getHibernateSession();
    hsession.saveOrUpdate(config);

    Clob clobValue = (value != null) ? Hibernate.createClob(value) : null;

    // Check exists?
    Set<ProfileValueMap> vMaps = ((ProfileConfigEntity)config).getProfileValueMaps();
    for (Iterator<ProfileValueMap> i = vMaps.iterator(); i.hasNext();) {
      ProfileValueMap vMap = (ProfileValueMap) i.next();
      ProfileAttributeValueEntity v = (ProfileAttributeValueEntity)vMap.getProfileAttribValue();
      if (name.equals(v.getProfileAttribute().getName())) {
         v.setRawData(clobValue);
         // Set to single value mode
         v.setIsMultiValued(false);
         v.setItemDataKind(ProfileAttributeValue.ITEM_DATA_KIND_BIN);
         v.setMFormat(DDFNode.DDF_FORMAT_CHR);

         Set<?> valueItemSet = v.getProfileValueItems();
         for (Iterator<?> j = valueItemSet.iterator(); j.hasNext();) {
             hsession.delete(j.next());
         }
         valueItemSet.clear();
         return;
      }
    }

    // Create a new AttributeValue
    ProfileTemplate template = config.getProfileTemplate();
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    ProfileAttribute attr = factory.createProfileTemplateBean().getProfileAttributeByName(template.getName(), name);
    if (attr == null) {
      throw new DMException("Could not find attribute by name: " + name + " from the template: " + template.getName());
    }

    ProfileAttributeValue av = new ProfileAttributeValueEntity();
    av.setProfileAttribute(attr);
    av.setRawData(clobValue);
    av.setIsMultiValued(false);
    av.setItemDataKind(ProfileAttributeValue.ITEM_DATA_KIND_BIN);
    av.setMFormat(DDFNode.DDF_FORMAT_CHR);
    hsession.saveOrUpdate(av);

    // New a ValueMapID
    ProfileValueMapID mapID = new ProfileValueMapID();
    mapID.setAttributeValueId(av.getID());
    mapID.setProfileId(config.getID());
    // New a ProfileValueMap
    long index = ((ProfileConfigEntity)config).getProfileValueMaps().size() + 1;
    ProfileValueMap map = new ProfileValueMap(mapID, av, config, index);

    // Link to ProfileConfigEntity
    ((ProfileConfigEntity)config).getProfileValueMaps().add(map);

    hsession.saveOrUpdate(map);
  }

  /**
   * Add or update the AttributeValue specified by the name. This is modifier of
   * AttributeValue in multiple-value mode.
   * 
   * Caution: Assign null to value is permitted. this will set the value to
   * null, AttributeValue will not be deleted!
   * 
   * Caution: Order of AttributeValue will automaticlly increased! The
   * AttributeValue added lastestly will be bottom.
   * 
   * @param name
   *          Attribute's name
   * @param value
   *          String[] array of multi-value
   * @throws DMException
   */
  public void setAttributeValue(ProfileConfig config, String name, String value[]) throws DMException {
    // update this profile config, first. make sure the profileID will generated
    // by hibernate.
    Session hsession = this.getHibernateSession();
    hsession.saveOrUpdate(config);

    Clob[] clobValues = null;
    if (value != null) {
      clobValues = new Clob[value.length];
      for (int i = 0; value != null && i < value.length; i++) {
        clobValues[i] = (value[i] == null) ? null : Hibernate.createClob(value[i]);
      }
    }

    // Check exists?
    Set<ProfileValueMap> vMaps = ((ProfileConfigEntity)config).getProfileValueMaps();
    for (Iterator<ProfileValueMap> i = vMaps.iterator(); i.hasNext();) {
        ProfileValueMap vMap = i.next();
        ProfileAttributeValueEntity v = (ProfileAttributeValueEntity)vMap.getProfileAttribValue();
        if (name.equals(v.getProfileAttribute().getName())) {
          // In multi-value mode, clear single value;
          v.setRawData(null);
          // Set to multi-value mode
          v.setIsMultiValued(true);
          v.setItemDataKind(ProfileAttributeValue.ITEM_DATA_KIND_TEXT);
          v.setMFormat(DDFNode.DDF_FORMAT_CHR);
          // Clear all of old values.
          Set<ProfileValueItem> items = v.getProfileValueItems();
          for (Iterator<ProfileValueItem> item = items.iterator(); item.hasNext();) {
            hsession.delete(item.next());
          }
          // clear up the set of items.
          items.clear();
          for (int j = 0; clobValues != null && j < clobValues.length; j++) {
            // Create a ProfileValueItem
            ProfileValueItem item = new ProfileValueItem(v);
            // Inherit property from AttributeValues
            item.setItemDataKind(v.getItemDataKind());
            item.setMFormat(v.getMFormat());
            item.setMType(v.getMType());
            item.setUpdateId(v.getUpdateId());
            // Assign the value
            item.setRawData(clobValues[j]);
  
            hsession.saveOrUpdate(item);
  
            // Save into DM inventory
            items.add(item);
          }
          return;
      }
    }

    // Create a new AttributeValue
    ProfileTemplate template = config.getProfileTemplate();
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    ProfileAttribute attr = factory.createProfileTemplateBean().getProfileAttributeByName(template.getName(), name);
    if (attr == null) {
      throw new DMException("Could not find attribute by name: " + name + " from the template: " + template.getName());
    }

    // Create a ProfileAttributeValueEntity
    ProfileAttributeValueEntity av = new ProfileAttributeValueEntity();
    av.setProfileAttribute(attr);
    // In Multi-value mode, clear the value of single-value mode.
    av.setRawData(null);
    // Set to multi-value mode
    av.setIsMultiValued(true);
    av.setItemDataKind(ProfileAttributeValue.ITEM_DATA_KIND_TEXT);
    av.setMFormat(DDFNode.DDF_FORMAT_CHR);
    hsession.saveOrUpdate(av);

    // Create all of items from clobValues
    for (int j = 0; clobValues != null && j < clobValues.length; j++) {
        // Create a ProfileValueItem
        ProfileValueItem item = new ProfileValueItem(av);
        // Inherit the value of properties from ProfileAttributeValueEntity.
        item.setItemDataKind(av.getItemDataKind());
        item.setMFormat(av.getMFormat());
        item.setMType(av.getMType());
        item.setUpdateId(av.getUpdateId());
        // set the value of Clob
        item.setRawData(clobValues[j]);
  
        // Add into DMInventory.
        hsession.saveOrUpdate(item);
  
        // Link the Item to ProfileAttributeValueEntity
        av.getProfileValueItems().add(item);
    }

    // New a ValueMapID
    ProfileValueMapID mapID = new ProfileValueMapID();
    mapID.setAttributeValueId(av.getID());
    mapID.setProfileId(config.getID());
    // New a ProfileValueMap
    long index = ((ProfileConfigEntity)config).getProfileValueMaps().size() + 1;
    ProfileValueMap map = new ProfileValueMap(mapID, av, config, index);

    // Link to ProfileConfigEntity
    Set<ProfileValueMap> maps = ((ProfileConfigEntity)config).getProfileValueMaps();
    maps.add(map);

    // Add the ProfileValueMap into DM inventory.
    hsession.saveOrUpdate(map);

  }

  /**
   * Add or update the value of AttributeValue specified by the name. This is
   * modifier of AttributeValue in multiple-value, binary mode.
   * 
   * Caution: Assign null to value is permitted. this will set the value to
   * null, AttributeValue will not be deleted!
   * 
   * Caution: Order of AttributeValue will automaticlly increased! The
   * AttributeValue added lastestly will be bottom.
   * 
   * @param name
   *          Attribute's name
   * @param value
   *          String[] array of multi-value
   * @throws DMException
   */
  public void setAttributeValue(ProfileConfig config, String name, InputStream value[]) throws DMException, IOException {
    // update this profile config, first. make sure the profileID will generated
    // by hibernate.
    Session hsession = this.getHibernateSession();
    hsession.saveOrUpdate(config);

    Blob[] blobValues = null;
    try {
      if (value != null) {
        blobValues = new Blob[value.length];
        for (int i = 0; value != null && i < value.length; i++) {
          blobValues[i] = (value[i] == null) ? null : Hibernate.createBlob(value[i]);
        }
      }
    } catch (IOException e) {
      throw e;
    }

    // Check exists?
    Set<ProfileValueMap> vMaps = ((ProfileConfigEntity)config).getProfileValueMaps();
    for (Iterator<ProfileValueMap> i = vMaps.iterator(); i.hasNext();) {
      ProfileValueMap vMap = (ProfileValueMap) i.next();
      ProfileAttributeValueEntity v = (ProfileAttributeValueEntity)vMap.getProfileAttribValue();
      if (name.equals(v.getProfileAttribute().getName())) {
        // In multi-value mode, clear single value;
        v.setRawData(null);
        // Set to multi-value mode
        v.setIsMultiValued(true);
        v.setMFormat(DDFNode.DDF_FORMAT_BIN);
        // Clear all of old values.
        Set<ProfileValueItem> items = v.getProfileValueItems();
        for (Iterator<ProfileValueItem> item = items.iterator(); item.hasNext();) {
          hsession.delete(item.next());
        }
        // clear up the set of items.
        items.clear();
        for (int j = 0; blobValues != null && j < blobValues.length; j++) {
          // Create a ProfileValueItem
          ProfileValueItem item = new ProfileValueItem(v);
          // Inherit property from AttributeValues
          item.setItemDataKind(v.getItemDataKind());
          item.setMFormat(v.getMFormat());
          item.setMType(v.getMType());
          item.setUpdateId(v.getUpdateId());
          // Assign the value
          item.setBinaryData(blobValues[j]);

          hsession.saveOrUpdate(item);

          // Save into DM inventory
          items.add(item);
        }
        return;
      }
    }

    // Create a new AttributeValue
    ProfileTemplate template = config.getProfileTemplate();
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    ProfileAttribute attr = factory.createProfileTemplateBean().getProfileAttributeByName(template.getName(), name);
    if (attr == null) {
      throw new DMException("Could not find attribute by name: " + name + " from the template: " + template.getName());
    }

    // Create a ProfileAttributeValueEntity
    ProfileAttributeValueEntity av = new ProfileAttributeValueEntity();
    av.setProfileAttribute(attr);
    // In Multi-value mode, clear the value of single-value mode.
    av.setRawData(null);
    // Set to multi-value mode
    av.setIsMultiValued(true);
    av.setMFormat(DDFNode.DDF_FORMAT_BIN);
    hsession.saveOrUpdate(av);

    // Create all of items from clobValues
    for (int j = 0; blobValues != null && j < blobValues.length; j++) {
      // Create a ProfileValueItem
      ProfileValueItem item = new ProfileValueItem(av);
      // Inherit the value of properties from ProfileAttributeValueEntity.
      item.setItemDataKind(av.getItemDataKind());
      item.setMFormat(av.getMFormat());
      item.setMType(av.getMType());
      item.setUpdateId(av.getUpdateId());
      // set the value of Clob
      item.setBinaryData(blobValues[j]);

      // Add into DMInventory.
      hsession.saveOrUpdate(item);

      // Link the Item to ProfileAttributeValueEntity
      av.getProfileValueItems().add(item);
    }

    // New a ValueMapID
    ProfileValueMapID mapID = new ProfileValueMapID();
    mapID.setAttributeValueId(av.getID());
    mapID.setProfileId(config.getID());
    // New a ProfileValueMap
    long index = ((ProfileConfigEntity)config).getProfileValueMaps().size() + 1;
    ProfileValueMap map = new ProfileValueMap(mapID, av, config, index);

    // Link to ProfileConfigEntity
    Set<ProfileValueMap> maps = ((ProfileConfigEntity)config).getProfileValueMaps();
    maps.add(map);

    // Add the ProfileValueMap into DM inventory.
    hsession.saveOrUpdate(map);

  }

  /**
   * Set attribute values of the ProfileConfigEntity, and the order of
   * attributes will based on the index of array.
   * 
   * TODO compose testcases for this method.
   * 
   * @param values
   * @throws DMException
   */
  public void setAttributeValues(ProfileConfig config, ProfileAttributeValue[] values) throws DMException {

    // update this profile config, first. make sure the profileID will generated
    // by hibernate.
    Session hsession = this.getHibernateSession();
    hsession.saveOrUpdate(config);

    // Clear up value map
    Set<ProfileValueMap> mapSet = ((ProfileConfigEntity)config).getProfileValueMaps();
    for (Iterator<ProfileValueMap> i = mapSet.iterator(); i.hasNext();) {
      hsession.delete((ProfileValueMap) i.next());
    }
    ((ProfileConfigEntity)config).getProfileValueMaps().clear();

    ProfileTemplate template = config.getProfileTemplate();
    Set<?> attrSet = template.getProfileAttributes();

    for (int i = 0; i < values.length; i++) {
      // Validate the ProfileAttributeValueEntity
      if (!attrSet.contains(values[i])) {
        throw new DMException("ProfileAttributeValueEntity[" + i + "] do not match the Attribute of the Template["
            + template.getName() + "]");
      }
      // Save first, fire-up the hibernate to generate the
      // ProfileAttributeValueID
      hsession.save(values[i]);

      // New a ValueMapID
      ProfileValueMapID mapID = new ProfileValueMapID();
      mapID.setAttributeValueId(values[i].getID());
      mapID.setProfileId(config.getID());

      // New a ProfileValueMap
      ProfileValueMap map = new ProfileValueMap(mapID, values[i], config, i);

      // Save into DM Inventory
      hsession.save(map);
      ((ProfileConfigEntity)config).getProfileValueMaps().add(map);
    }

  }

}
