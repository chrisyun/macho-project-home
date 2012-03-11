/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/ProfileTemplateManagementBeanImpl.java,v 1.14 2008/02/28 06:17:11 zhao Exp $
 * $Revision: 1.14 $
 * $Date: 2008/02/28 06:17:11 $
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
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.AttributeTypeValue;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.hibernate.entity.AttributeTypeValueEntity;
import com.npower.dm.hibernate.entity.ProfileAttributeEntity;
import com.npower.dm.hibernate.entity.ProfileAttributeTypeEntity;
import com.npower.dm.hibernate.entity.ProfileCategoryEntity;
import com.npower.dm.hibernate.entity.ProfileTemplateEntity;
import com.npower.dm.hibernate.management.digester.ProfileAttributeEntityFactory;
import com.npower.dm.hibernate.management.digester.ProfileTemplateEntityFactory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.14 $ $Date: 2008/02/28 06:17:11 $
 */
public class ProfileTemplateManagementBeanImpl extends AbstractBean implements ProfileTemplateBean {

  private Log log = LogFactory.getLog(ProfileTemplateManagementBeanImpl.class);

  /**
   * Default Constructor
   * 
   */
  protected ProfileTemplateManagementBeanImpl() {
    super();
  }

  /**
   * Constructor
   */
  public ProfileTemplateManagementBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }

  // Methods relate with ProfileCategoryEntity
  // ****************************************************************

  /**
   * Create a instance of profile category
   */
  public ProfileCategory newProfileCategoryInstance() throws DMException {
    return new ProfileCategoryEntity();
  }

  /**
   * Create a instance of profile category
   */
  public ProfileCategory newProfileCategoryInstance(String name, String description) throws DMException {
    return new ProfileCategoryEntity(name, description);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#addCategory(com.npower.dm.hibernate.ProfileCategoryEntity)
   */
  public void updateCategory(ProfileCategory category) throws DMException {
    if (category == null) {
      throw new NullPointerException("Could not add a null category into database.");
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      // Transaction tx = session.beginTransaction();
      session.saveOrUpdate(category);
      // tx.commit();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#getProfileCategoryByID(java.lang.String)
   */
  public ProfileCategory getProfileCategoryByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ProfileCategoryEntity where ID='" + id + "'");
      List<ProfileCategory> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileCategory) list.get(0);
      } else {
        throw new DMException(
            "Result is not unique, many ProfileCategoryEntity have the same ProfileCategoryEntity ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#getProfileCategoryByName(java.lang.String)
   */
  public ProfileCategory getProfileCategoryByName(String name) throws DMException {
    if (name == null || name.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ProfileCategoryEntity where name='" + name + "'");
      List<ProfileCategory> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileCategory) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ProfileCategoryEntity have the same name: " + name);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#findProfileCategories(java.lang.String)
   */
  public List<ProfileCategory> findProfileCategories(String whereClause) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery(whereClause);
      List<ProfileCategory> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileTemplateBean#getAllOfProfileCategories()
   */
  public List<ProfileCategory> getAllOfProfileCategories() throws DMException {
    return this.findProfileCategories("from ProfileCategoryEntity");
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#deleteCategory(com.npower.dm.hibernate.ProfileCategoryEntity)
   */
  public void deleteCategory(ProfileCategory profileCategory) throws DMException {
    Session session = this.getHibernateSession();
    try {
      log.trace("deleting:" + profileCategory);
      Set<ProfileTemplate> templates = profileCategory.getProfileTemplates();
      if (templates != null) {
        log.trace("delete ProfileTemplates reference to the Category");
        for (Iterator<ProfileTemplate> i = templates.iterator(); i.hasNext();) {
          ProfileTemplate template = (ProfileTemplate) i.next();
          this.delete(template);
        }
      }
      session.delete(profileCategory);
      log.trace("deleted:" + profileCategory);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#parseCategory(java.io.InputStream)
   */
  public List<ProfileCategory> parseCategory(InputStream in) throws DMException {
    List<ProfileCategory> categories = new ArrayList<ProfileCategory>();

    // log.info("Stopping Pulsar Server");

    // Create and execute our Digester
    Digester digester = createProfileCategoryDigester();
    try {
      digester.push(categories);
      digester.parse(in);
      return categories;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#importCategory(java.io.InputStream)
   */
  public int importCategory(InputStream in) throws DMException {

    Transaction tx = null;
    try {
      List<ProfileCategory> categories = this.parseCategory(in);
      tx = this.getHibernateSession().beginTransaction();
      for (int i = 0; i < categories.size(); i++) {
        ProfileCategory category = (ProfileCategory) categories.get(i);
        if (this.getProfileCategoryByName(category.getName()) == null) {
          // Ignore exists
          log.trace("add:" + category);
          this.updateCategory(category);
        } else {
          log.trace("bypass:" + category);
        }
      }
      tx.commit();
      return categories.size();
    } catch (Exception e) {
      if (tx != null && tx.isActive()) {
        tx.rollback();
      }
      throw new DMException(e);
    }

  }

  /**
   * Create and configure the Digester we will be using for parsing metadata.
   */
  private Digester createProfileCategoryDigester() {

    // Initialize the digester
    Digester digester = new Digester();
    digester.setValidating(false);

    // digester.addObjectCreate("ProfileMetaData/ProfileCategories",
    // "java.util.ArrayList");

    // Parsing ProfileCategories
    digester.addObjectCreate("ProfileMetaData/ProfileCategories/ProfileCategory",
        "com.npower.dm.hibernate.entity.ProfileCategoryEntity");
    digester.addCallMethod("ProfileMetaData/ProfileCategories/ProfileCategory/Name", "setName", 1);
    digester.addCallParam("ProfileMetaData/ProfileCategories/ProfileCategory/Name", 0);
    digester.addCallMethod("ProfileMetaData/ProfileCategories/ProfileCategory/Description", "setDescription", 1);
    digester.addCallParam("ProfileMetaData/ProfileCategories/ProfileCategory/Description", 0);
    digester.addSetNext("ProfileMetaData/ProfileCategories/ProfileCategory", "add",
        "com.npower.dm.hibernate.entity.ProfileCategoryEntity");

    return (digester);
  }

  // Methods relate with ProfileAttributeTypeEntity
  // **********************************************************

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileTemplateBean#newAttributeTypeInstance()
   */
  public ProfileAttributeType newAttributeTypeInstance() throws DMException {
    return new ProfileAttributeTypeEntity();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#addAttributeType(com.npower.dm.hibernate.ProfileAttributeTypeEntity)
   */
  public void updateAttributeType(ProfileAttributeType attributeType) throws DMException {
    if (attributeType == null) {
      throw new NullPointerException("Could not add a null ProfileAttributeTypeEntity into database.");
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      // Transaction tx = session.beginTransaction();
      session.saveOrUpdate(attributeType);
      // tx.commit();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#getProfileAttributeTypeByID(java.lang.String)
   */
  public ProfileAttributeType getProfileAttributeTypeByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ProfileAttributeTypeEntity where ID='" + id + "'");
      List<ProfileAttributeType> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileAttributeType) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ProfileAttributeTypeEntity have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#getProfileAttributeTypeByName(java.lang.String)
   */
  public ProfileAttributeType getProfileAttributeTypeByName(String name) throws DMException {
    if (name == null || name.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ProfileAttributeTypeEntity where name='" + name + "'");
      List<ProfileAttributeType> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileAttributeType) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ProfileAttributeTypeEntity have the same name: " + name);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#findProfileAttributeTypes(java.lang.String)
   */
  public List<ProfileAttributeType> findProfileAttributeTypes(String whereClause) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery(whereClause);
      List<ProfileAttributeType> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#deleteAttributeType(com.npower.dm.hibernate.ProfileAttributeTypeEntity)
   */
  public void deleteAttributeType(ProfileAttributeType type) throws DMException {
    Session session = this.getHibernateSession();
    try {
      log.trace("deleting:" + type);
      // session = HibernateSessionFactory.currentSession();
      // Transaction tx = session.beginTransaction();
      Set<AttributeTypeValue> valueSet = type.getAttribTypeValues();
      for (Iterator<AttributeTypeValue> values = valueSet.iterator(); values.hasNext();) {
        AttributeTypeValue value = (AttributeTypeValue) values.next();
        session.delete(value);
      }
      session.delete(type);
      // tx.commit();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * Create and configure the Digester we will be using for parsing metadata.
   */
  private Digester createProfileAttributeTypeDigester() {

    // Initialize the digester
    Digester digester = new Digester();
    digester.setValidating(false);

    // digester.addObjectCreate("ProfileMetaData/ProfileCategories",
    // "java.util.ArrayList");

    // Parsing ProfileAttributeTypes
    digester.addObjectCreate("ProfileMetaData/AttributeTypes/AttributeType",
        "com.npower.dm.hibernate.entity.ProfileAttributeTypeEntity");
    digester.addCallMethod("ProfileMetaData/AttributeTypes/AttributeType/Name", "setName", 1);
    digester.addCallParam("ProfileMetaData/AttributeTypes/AttributeType/Name", 0);
    digester.addCallMethod("ProfileMetaData/AttributeTypes/AttributeType/Description", "setDescription", 1);
    digester.addCallParam("ProfileMetaData/AttributeTypes/AttributeType/Description", 0);
    digester.addSetNext("ProfileMetaData/AttributeTypes/AttributeType", "add",
        "com.npower.dm.hibernate.entity.ProfileAttributeTypeEntity");

    // Parsing AttributeTypeValueEntity
    digester.addObjectCreate("ProfileMetaData/AttributeTypes/AttributeType/Values", "java.util.HashSet");
    digester.addObjectCreate("ProfileMetaData/AttributeTypes/AttributeType/Values/Value",
        "com.npower.dm.hibernate.entity.AttributeTypeValueEntity");
    digester.addCallMethod("ProfileMetaData/AttributeTypes/AttributeType/Values/Value", "setValue", 1);
    digester.addCallParam("ProfileMetaData/AttributeTypes/AttributeType/Values/Value", 0);
    digester.addSetNext("ProfileMetaData/AttributeTypes/AttributeType/Values/Value", "add");
    digester.addSetNext("ProfileMetaData/AttributeTypes/AttributeType/Values", "setAttribTypeValues", "java.util.Set");
    return (digester);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#parseProfileAttributeType(java.io.InputStream)
   */
  public List<ProfileAttributeType> parseProfileAttributeType(InputStream in) throws DMException {
    List<ProfileAttributeType> types = new ArrayList<ProfileAttributeType>();

    // Create and execute our Digester
    Digester digester = createProfileAttributeTypeDigester();
    try {
      digester.push(types);
      digester.parse(in);
      log.trace(types);
      return types;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#importProfileAttributeType(java.io.InputStream)
   */
  public int importProfileAttributeType(InputStream in) throws DMException {

    Transaction tx = null;
    try {
      List<ProfileAttributeType> types = this.parseProfileAttributeType(in);
      Session session = this.getHibernateSession();
      tx = session.beginTransaction();
      for (int i = 0; i < types.size(); i++) {
        ProfileAttributeType type = (ProfileAttributeType) types.get(i);
        if (this.getProfileAttributeTypeByName(type.getName()) == null) {
           this.updateAttributeType(type);
           log.trace("add:" + type);

           Set<AttributeTypeValue> values = type.getAttribTypeValues();
           for (Iterator<AttributeTypeValue> it = values.iterator(); it.hasNext();) {
             AttributeTypeValue value = (AttributeTypeValue) it.next();
             value.setProfileAttribType(type);
             session.save(value);
             log.trace("add:" + value);
           }
        } else {
          // Ignore exists
          log.trace("bypass:" + type);
        }
      }
      tx.commit();
      return types.size();
    } catch (Exception e) {
      if (tx != null && tx.isActive()) {
        tx.rollback();
      }
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileTemplateBean#newAttributeTypeValueInstance(ProfileAttributeType)
   */
  public AttributeTypeValue newAttributeTypeValueInstance(ProfileAttributeType type) throws DMException {
    if (type == null) {
       throw new DMException("The ProfileAttributeType is null, could not new a value instance");
    }
    if (type.getID() == 0) {
       throw new DMException("Please save the ProfileAttributeType, before create a value instance.");
    }
    AttributeTypeValue value = new AttributeTypeValueEntity();
    value.setProfileAttribType(type);
    return value;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileTemplateBean#update(com.npower.dm.core.AttributeTypeValue)
   */
  public void update(AttributeTypeValue attributeTypeValue) throws DMException {
    if (attributeTypeValue == null) {
      throw new NullPointerException("Could not add a null AttributeTypeValue into DM inventory.");
    }
    Session session = this.getHibernateSession();
    try {
        session.saveOrUpdate(attributeTypeValue);
        attributeTypeValue.getProfileAttribType().getAttribTypeValues().add(attributeTypeValue);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileTemplateBean#delete(com.npower.dm.core.AttributeTypeValue)
   */
  public void delete(AttributeTypeValue attributeTypeValue) throws DMException {
    if (attributeTypeValue == null) {
      throw new NullPointerException("Could not delete a null AttributeTypeValue from DM inventory.");
    }
    Session session = this.getHibernateSession();
    try {
        session.delete(attributeTypeValue);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileTemplateBean#getAttributeTypeValueByID(java.lang.String)
   */
  public AttributeTypeValue getAttributeTypeValueByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
       return null;
    }
    Session session = this.getHibernateSession();
    try {
        Query query = session.createQuery("from AttributeTypeValueEntity where ID='" + id + "'");
        List<AttributeTypeValue> list = query.list();
  
        if (list.size() == 0) {
          return null;
        }
  
        if (list.size() == 1) {
          return (AttributeTypeValue) list.get(0);
        } else {
          throw new DMException("Result is not unique, more than one AttributeTypeValueEntity have the same ID: " + id);
        }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  // Methods relate with ProfileAttributeEntity
  // **********************************************************
  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileTemplateBean#newAttributeInstance()
   */
  public ProfileAttribute newAttributeInstance() throws DMException {
    return new ProfileAttributeEntity();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#addAttribute(com.npower.dm.hibernate.ProfileAttributeEntity)
   */
  public void update(ProfileAttribute attribute) throws DMException {
    if (attribute == null) {
      throw new DMException("Could not add a null ProfileAttributeEntity into database.", new NullPointerException());
    }
    ProfileAttributeType type = attribute.getProfileAttribType();
    if (type == null) {
      throw new DMException("Attribute must reference a AttributeType.", new NullPointerException());
    }
    Session session = null;
    try {
        session = this.getHibernateSession();
      session.saveOrUpdate(attribute);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#getProfileAttributeByID(java.lang.String)
   */
  public ProfileAttribute getProfileAttributeByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ProfileAttributeEntity where ID='" + id + "'");
      List<ProfileAttribute> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileAttribute) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ProfileAttributeEntity have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#getProfileAttributeByName(java.lang.String,
   *      java.lang.String)
   */
  public ProfileAttribute getProfileAttributeByName(String templateName, String name) throws DMException {
    if (templateName == null || templateName.trim().length() == 0 || name == null || name.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      ProfileTemplate template = this.getTemplateByName(templateName);
      if (template == null) {
        return null;
      }
      Criteria criteria = session.createCriteria(ProfileAttribute.class);
      Criteria templateCriteria = criteria.createCriteria("profileTemplate");
      criteria.add(Expression.eq("name", name));
      templateCriteria.add(Expression.eq("name", templateName));

      List<ProfileAttribute> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileAttribute) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ProfileAttributeEntity have the same name: " + name);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#findProfileAttributes(java.lang.String)
   */
  public List<ProfileAttribute> findProfileAttributes(String clause) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery(clause);
      List<ProfileAttribute> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#deleteAttribute(com.npower.dm.hibernate.ProfileAttributeEntity)
   */
  public void delete(ProfileAttribute attr) throws DMException {
    Session session = this.getHibernateSession();
    try {
      log.trace("deleting:" + attr);
      session.delete(attr);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  // Methods relate with ProfileTemplateEntity
  // **********************************************************
  /* (non-Javadoc)
   * @see com.npower.dm.management.ProfileTemplateBean#newTemplateInstance()
   */
  public ProfileTemplate newTemplateInstance() throws DMException {
    return new ProfileTemplateEntity();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#addTemplate(com.npower.dm.hibernate.ProfileTemplateEntity)
   */
  public void update(ProfileTemplate template) throws DMException {
    if (template == null) {
      throw new DMException("Could not add a null ProfileTemplateEntity into database.", new NullPointerException());
    }
    ProfileCategory category = template.getProfileCategory();
    if (category == null) {
      throw new DMException("ProfileTemplateEntity must reference a ProfileCategoryEntity.", new NullPointerException());
    }
    Session session = this.getHibernateSession();
    try {
      session.saveOrUpdate(template);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#getTemplateByID(java.lang.String)
   */
  public ProfileTemplate getTemplateByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ProfileTemplateEntity where ID='" + id + "'");
      List<ProfileTemplate> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileTemplate) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ProfileTemplateEntity have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#getTemplateByName(java.lang.String)
   */
  public ProfileTemplate getTemplateByName(String name) throws DMException {
    if (name == null || name.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ProfileTemplateEntity where name='" + name + "'");
      List<ProfileTemplate> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ProfileTemplate) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ProfileTemplateEntity have the same name: " + name);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#findTemplates(java.lang.String)
   */
  public List<ProfileTemplate> findTemplates(String clause) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery(clause);
      List<ProfileTemplate> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#deleteTemplate(com.npower.dm.hibernate.ProfileTemplateEntity)
   */
  public void delete(ProfileTemplate template) throws DMException {
    Session session = this.getHibernateSession();
    try {
      log.trace("deleting:" + template);
      Set<ProfileAttribute> attrs = template.getProfileAttributes();
      for (Iterator<ProfileAttribute> i = attrs.iterator(); i.hasNext();) {
        ProfileAttribute attr = (ProfileAttribute) i.next();
        log.trace("deleting:" + attr);
        session.delete(attr);
      }
      // Delete all of ProfileConfigEntity related with the ProfileTemplates
      // The field of ConfigProfiles is in mode of cascade="delele"

      session.delete(template);
      log.trace("deleted:" + template);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * Create and configure the Digester we will be using for parsing metadata.
   */
  private Digester createProfileTemplateDigester() {

    // Initialize the digester
    Digester digester = new Digester();
    digester.setValidating(false);

    // digester.addObjectCreate("ProfileMetaData/ProfileCategories",
    // "java.util.ArrayList");

    // Parsing ProfileTemplates
    //digester.addObjectCreate("ProfileMetaData/ProfileTemplates/ProfileTemplate", "com.npower.dm.hibernate.entity.ProfileTemplateEntity");
    digester.addFactoryCreate("ProfileMetaData/ProfileTemplates/ProfileTemplate", ProfileTemplateEntityFactory.class);
    
    digester.addBeanPropertySetter("ProfileMetaData/ProfileTemplates/ProfileTemplate/Name", "name");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileTemplates/ProfileTemplate/CategoryName",
        "profileCategoryByName");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileTemplates/ProfileTemplate/NeedsNAP", "needsNapByString");
    digester.addBeanPropertySetter("ProfileMetaData/ProfileTemplates/ProfileTemplate/NeedsProxy", "needsProxyByString");
    digester.addSetNext("ProfileMetaData/ProfileTemplates/ProfileTemplate", "add");

    // Parsing Attribute
    //digester.addObjectCreate("ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute",
    //    "com.npower.dm.hibernate.entity.ProfileAttributeEntity");
    digester.addFactoryCreate("ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute", ProfileAttributeEntityFactory.class);
    digester.addBeanPropertySetter(
        "ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute/Name", "name");
    digester.addBeanPropertySetter(
        "ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute/DisplayName", "displayName");
    digester.addBeanPropertySetter(
        "ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute/Description", "description");
    digester.addBeanPropertySetter(
        "ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute/IsRequired",
        "isRequiredByString");
    digester.addBeanPropertySetter(
        "ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute/IsUserAttribute",
        "isUserAttributeByString");
    digester.addBeanPropertySetter(
        "ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute/IsMultiValued", "isMultivaluedByString");
    digester.addBeanPropertySetter(
        "ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute/DisplayValue", "displayValueByString");
    digester.addBeanPropertySetter(
        "ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute/DefaultValue", "defaultValue");
    digester.addBeanPropertySetter(
        "ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute/TypeName",
        "attributeTypeByName");
    digester.addSetNext("ProfileMetaData/ProfileTemplates/ProfileTemplate/Attributes/Attribute", "add");

    return (digester);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#parseProfileTemplate(java.io.InputStream)
   */
  public List<ProfileTemplate> parseProfileTemplate(InputStream in) throws DMException {
    List<ProfileTemplate> types = new ArrayList<ProfileTemplate>();

    // Create and execute our Digester
    Digester digester = createProfileTemplateDigester();
    try {
      digester.push(this.getManagementBeanFactory());
      digester.push(types);
      digester.parse(in);
      log.trace(types);
      return types;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ProfileTemplateBean#importProfileTemplates(java.io.InputStream)
   */
  public int importProfileTemplates(InputStream in) throws DMException {

    Transaction tx = null;
    try {
      List<ProfileTemplate> templates = this.parseProfileTemplate(in);
      tx = this.getHibernateSession().beginTransaction();
      for (int i = 0; i < templates.size(); i++) {
        ProfileTemplate template = (ProfileTemplate) templates.get(i);
        if (this.getTemplateByName(template.getName()) == null) {
          this.update(template);
          log.trace("add:" + template);

          Set<ProfileAttribute> values = template.getProfileAttributes();
          for (Iterator<ProfileAttribute> it = values.iterator(); it.hasNext();) {
            ProfileAttribute attr = (ProfileAttribute) it.next();
            attr.setProfileTemplate(template);
            log.trace("add:" + attr);
            this.getHibernateSession().save(attr);
          }
        } else {
          // Ignore exists
          log.trace("bypass:" + template);
        }
      }
      tx.commit();
      return templates.size();
    } catch (Exception e) {
      if (tx != null && tx.isActive()) {
        tx.rollback();
      }
      throw new DMException(e);
    }
  }


}
