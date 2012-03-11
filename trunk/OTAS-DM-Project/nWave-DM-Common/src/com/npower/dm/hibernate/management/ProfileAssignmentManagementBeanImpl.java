/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/ProfileAssignmentManagementBeanImpl.java,v 1.7 2007/08/29 06:20:59 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2007/08/29 06:20:59 $
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
import org.hibernate.criterion.Order;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.hibernate.entity.ProfileAssignmentEntity;
import com.npower.dm.hibernate.entity.ProfileAssignmentValue;
import com.npower.dm.hibernate.entity.ProfileAssignmentValueID;
import com.npower.dm.hibernate.entity.ProfileAttributeValueEntity;
import com.npower.dm.hibernate.entity.ProfileValueItem;
import com.npower.dm.management.BaseBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileAssignmentBean;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2007/08/29 06:20:59 $
 */
public class ProfileAssignmentManagementBeanImpl extends AbstractBean implements BaseBean, ProfileAssignmentBean {

  private static Log log = LogFactory.getLog(ProfileConfigManagementBeanImp.class);

  /**
   * 
   */
  protected ProfileAssignmentManagementBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  ProfileAssignmentManagementBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }

  // public methods
  // ******************************************************************************************
  public ProfileAssignment newProfileAssignmentInstance(ProfileConfig profileConfig, Device device) throws DMException {
    return new ProfileAssignmentEntity(profileConfig, device);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.ProfileAssignmentBean#update(com.npower.dm.hibernate.ProfileAssignmentEntity)
   */
  public void update(ProfileAssignment assignment) throws DMException {
    if (assignment == null) {
      throw new NullPointerException("Could not add a null ProfileAssignmentEntity into database.");
    }
    Session session = this.getHibernateSession();
    try {
        ProfileConfig profile = assignment.getProfileConfig();
        
        ProfileTemplate template = profile.getProfileTemplate();
        Device device = assignment.getDevice();
        Model model = device.getModel();
        
        ProfileMapping mapping = model.getProfileMap(template);
        if (mapping == null) {
           // TODO uncomments the following line, make sure the device had a ProfileMapping for this template.
           //throw new DMException("Could not assign the profile to this device, no ProfileMapping defined for this device.");
        }
  
        long id = assignment.getID();
        if (id == 0) {
          // is new!
          // Automaticly caculate assignIndex
          
          Query query = session.createQuery("select max(assignmentIndex) from ProfileAssignmentEntity "
              + " where DEVICE_ID=? and PROFILE_ID=?");
          query.setLong(0, device.getID());
          query.setLong(1, profile.getID());
  
          Long assignmentIndex = (Long) query.uniqueResult();
          if (assignmentIndex == null) {
            assignment.setAssignmentIndex(1L);
          } else {
            assignment.setAssignmentIndex(assignmentIndex.longValue() + 1);
          }
        }
        
        // TODO checking this assignment before update(), make sure every attributes has been filled value and match the policy defined by ProfileTemplate
        session.saveOrUpdate(assignment);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.ProfileAssignmentBean#getProfileAssignmentByID(java.lang.String)
   */
  public ProfileAssignment getProfileAssignmentByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ProfileAssignmentEntity where ID='" + id + "'");
      List<ProfileAssignment> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileAssignment) list.get(0);
      } else {
        throw new DMException("Result is not unique, more ProfileAssignmentEntity have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.ProfileAssignmentBean#findProfileAssignments(java.lang.String)
   */
  public List<ProfileAssignment> findProfileAssignments(String whereClause) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery(whereClause);
      List<ProfileAssignment> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.ProfileAssignmentBean#findProfileAssignmentsByStatus(com.npower.dm.hibernate.DeviceEntity,
   *      java.lang.String)
   */
  public List<ProfileAssignment> findProfileAssignmentsByStatus(Device device, String status) throws DMException {
    Criteria mainCrt = this.getHibernateSession().createCriteria(ProfileAssignmentEntity.class);
    if (status != null) {
       Criteria deviceStateCrt = mainCrt.createCriteria("jobAssignmentses").createCriteria("jobState").createCriteria("deviceProvisionRequests");
       deviceStateCrt.add(Expression.eq("device", device));
       deviceStateCrt.add(Expression.eq("state", status));
    }
    mainCrt.add(Expression.eq("device", device));
    mainCrt.addOrder(Order.asc("assignmentIndex"));
    return mainCrt.list();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.ProfileAssignmentBean#deleteProfileAssignment(com.npower.dm.hibernate.ProfileAssignmentEntity)
   */
  public void deleteProfileAssignment(ProfileAssignment assignment) throws DMException {
    Session session = this.getHibernateSession();
    try {
      log.trace("deleting:" + assignment);
      session.delete(assignment);
      log.trace("deleted:" + assignment);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * Add or UpdateEntity the value of ProfileAttributeEntity ownen by the
   * ProfileAssignmentEntity. If not found ProfileAttributeValueEntity by the
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
  public void setAttributeValue(ProfileAssignment assignment, String name, InputStream value) throws DMException, IOException {

    // update this profile config, first. make sure the profileID will generated
    // by hibernate.
    Session hsession = this.getHibernateSession();
    hsession.saveOrUpdate(assignment);

    Blob blobValue = null;
    try {
      blobValue = (value != null) ? Hibernate.createBlob(value) : null;
    } catch (IOException e) {
      throw e;
    }

    // Check exists?
    Set<ProfileAssignmentValue> vMaps = ((ProfileAssignmentEntity)assignment).getProfileAssignValues();
    for (Iterator<ProfileAssignmentValue> i = vMaps.iterator(); i.hasNext();) {
        ProfileAssignmentValue vMap = (ProfileAssignmentValue) i.next();
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
    ProfileTemplate template = assignment.getProfileConfig().getProfileTemplate();
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

    // New a ProfileAssignmentValueID
    ProfileAssignmentValueID mapID = new ProfileAssignmentValueID();
    mapID.setAttributeValueId(av.getID());
    mapID.setProfileAssignmentId(assignment.getID());
    // New a ProfileAssignmentValue
    // long index = this.getProfileValueMaps().size() + 1;
    ProfileAssignmentValue map = new ProfileAssignmentValue(mapID, av, assignment);

    // Link to ProfileAssignmentEntity
    ((ProfileAssignmentEntity)assignment).getProfileAssignValues().add(map);

    hsession.saveOrUpdate(map);
  }

  /**
   * Add or UpdateEntity the value of ProfileAttributeEntity ownen by the
   * ProfileAssignmentEntity. If not found ProfileAttributeValueEntity by the
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
  public void setAttributeValue(ProfileAssignment assignment, String name, String value) throws DMException {

    // update this profile config, first. make sure the profileID will generated
    // by hibernate.
    Session hsession = this.getHibernateSession();
    hsession.saveOrUpdate(assignment);

    Clob clobValue = (value != null) ? Hibernate.createClob(value) : null;

    // Check exists?
    Set<ProfileAssignmentValue> vMaps = ((ProfileAssignmentEntity)assignment).getProfileAssignValues();
    for (Iterator<ProfileAssignmentValue> i = vMaps.iterator(); i.hasNext();) {
        ProfileAssignmentValue vMap = (ProfileAssignmentValue) i.next();
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
    ProfileTemplate template = assignment.getProfileConfig().getProfileTemplate();
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

    // New a ProfileAssignmentValueID
    ProfileAssignmentValueID mapID = new ProfileAssignmentValueID();
    mapID.setAttributeValueId(av.getID());
    mapID.setProfileAssignmentId(assignment.getID());
    // New a ProfileAssignmentValue
    // long index = this.getProfileValueMaps().size() + 1;
    ProfileAssignmentValue map = new ProfileAssignmentValue(mapID, av, assignment);

    // Link to ProfileAssignmentEntity
    ((ProfileAssignmentEntity)assignment).getProfileAssignValues().add(map);

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
  public void setAttributeValue(ProfileAssignment assignment, String name, String value[]) throws DMException {
    // update this profile config, first. make sure the profileID will generated
    // by hibernate.
    Session hsession = this.getHibernateSession();
    hsession.saveOrUpdate(assignment);

    Clob[] clobValues = null;
    if (value != null) {
      clobValues = new Clob[value.length];
      for (int i = 0; value != null && i < value.length; i++) {
        clobValues[i] = (value[i] == null) ? null : Hibernate.createClob(value[i]);
      }
    }

    // Check exists?
    Set<ProfileAssignmentValue> vMaps = ((ProfileAssignmentEntity)assignment).getProfileAssignValues();
    for (Iterator<ProfileAssignmentValue> i = vMaps.iterator(); i.hasNext();) {
      ProfileAssignmentValue vMap = (ProfileAssignmentValue) i.next();
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
    ProfileTemplate template = assignment.getProfileConfig().getProfileTemplate();
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

    // New a ProfileAssignmentValue
    ProfileAssignmentValueID mapID = new ProfileAssignmentValueID();
    mapID.setAttributeValueId(av.getID());
    mapID.setProfileAssignmentId(assignment.getID());
    // New a ProfileAssignmentValue
    // long index = this.getProfileValueMaps().size() + 1;
    ProfileAssignmentValue map = new ProfileAssignmentValue(mapID, av, assignment);

    // Link to ProfileAssignmentEntity
    Set<ProfileAssignmentValue> maps = ((ProfileAssignmentEntity)assignment).getProfileAssignValues();
    maps.add(map);

    // Add the ProfileAssignmentEntity into DM inventory.
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
  public void setAttributeValue(ProfileAssignment assignment, String name, InputStream value[]) throws DMException, IOException {
    // update this profile config, first. make sure the profileID will generated
    // by hibernate.
    Session hsession = this.getHibernateSession();
    hsession.saveOrUpdate(assignment);

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
    Set<ProfileAssignmentValue> vMaps = ((ProfileAssignmentEntity)assignment).getProfileAssignValues();
    for (Iterator<ProfileAssignmentValue> i = vMaps.iterator(); i.hasNext();) {
      ProfileAssignmentValue vMap = (ProfileAssignmentValue) i.next();
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
    ProfileTemplate template = assignment.getProfileConfig().getProfileTemplate();
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

    // New a ProfileAssignmentValueID
    ProfileAssignmentValueID mapID = new ProfileAssignmentValueID();
    mapID.setAttributeValueId(av.getID());
    mapID.setProfileAssignmentId(assignment.getID());
    // New a ProfileAssignmentValue
    // long index = this.getProfileValueMaps().size() + 1;
    ProfileAssignmentValue map = new ProfileAssignmentValue(mapID, av, assignment);

    // Link to ProfileAssignmentEntity
    Set<ProfileAssignmentValue> maps = ((ProfileAssignmentEntity)assignment).getProfileAssignValues();
    maps.add(map);

    // Add the ProfileValueMap into DM inventory.
    hsession.saveOrUpdate(map);

  }

  /**
   * Set attribute values of the ProfileAssignmentEntity, and the order of
   * attributes will based on the index of array.
   * 
   * TODO compose testcases for this method.
   * 
   * @param values
   * @throws DMException
   */
  public void setAttributeValues(ProfileAssignment assignment, ProfileAttributeValue[] values) throws DMException {

    // update this profile config, first. make sure the profileID will generated
    // by hibernate.
    Session hsession = this.getHibernateSession();
    hsession.saveOrUpdate(assignment);

    // Clear up value map
    Set<ProfileAssignmentValue> avSet = ((ProfileAssignmentEntity)assignment).getProfileAssignValues();
    for (Iterator<ProfileAssignmentValue> i = avSet.iterator(); i.hasNext();) {
      hsession.delete((ProfileAssignmentValue) i.next());
    }
    ((ProfileAssignmentEntity)assignment).getProfileAssignValues().clear();

    ProfileTemplate template = assignment.getProfileConfig().getProfileTemplate();
    Set<ProfileAttribute> attrSet = template.getProfileAttributes();

    for (int i = 0; i < values.length; i++) {
      // Validate the ProfileAttributeValueEntity
      if (!attrSet.contains(values[i])) {
        throw new DMException("ProfileAttributeValueEntity[" + i + "] do not match the Attribute of the Template["
            + template.getName() + "]");
      }
      // Save first, fire-up the hibernate to generate the
      // ProfileAttributeValueID
      hsession.save(values[i]);

      // New a ProfileAssignmentValueID
      ProfileAssignmentValueID mapID = new ProfileAssignmentValueID();
      mapID.setAttributeValueId(values[i].getID());
      mapID.setProfileAssignmentId(assignment.getID());

      // New a ProfileValueMap
      ProfileAssignmentValue map = new ProfileAssignmentValue(mapID, values[i], assignment);

      // Save into DM Inventory
      hsession.save(map);
      ((ProfileAssignmentEntity)assignment).getProfileAssignValues().add(map);
    }

  }
}
