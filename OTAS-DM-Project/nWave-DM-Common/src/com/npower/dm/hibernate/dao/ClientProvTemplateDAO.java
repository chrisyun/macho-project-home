/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/dao/ClientProvTemplateDAO.java,v 1.1 2007/05/18 03:15:55 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2007/05/18 03:15:55 $
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
package com.npower.dm.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.npower.dm.core.DMException;
import com.npower.dm.hibernate.entity.ClientProvTemplateEntity;

/**
 * Data access object (DAO) for domain model class ClientProvTemplateEntity.
 * 
 * @see com.npower.dm.hibernate.entity.ClientProvTemplateEntity
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/05/18 03:15:55 $
 */
public class ClientProvTemplateDAO extends BaseHibernateDAO {

  private static final Log   log             = LogFactory.getLog(ClientProvTemplateDAO.class);

  // property constants
  public static final String NAME            = "name";

  public static final String DESCRIPTION     = "description";

  public static final String CONTENT         = "content";

  public static final String LAST_UPDATED_BY = "lastUpdatedBy";

  public static final String CHANGE_VERSION  = "changeVersion";

  /**
   * Save
   * @param transientInstance
   * @throws DMException
   */
  public void save(ClientProvTemplateEntity transientInstance) throws DMException {
    log.debug("saving ClientProvTemplateEntity instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (Exception re) {
      log.error("save failed", re);
      throw new DMException("save failed", re);
    }
  }

  /**
   * Delete
   * @param persistentInstance
   * @throws DMException
   */
  public void delete(ClientProvTemplateEntity persistentInstance) throws DMException {
    log.debug("deleting ClientProvTemplateEntity instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (Exception re) {
      log.error("delete failed", re);
      throw new DMException("delete failed", re);
    }
  }

  /**
   * Find by ID
   * @param id
   * @return
   * @throws DMException
   */
  public ClientProvTemplateEntity findById(java.lang.Long id) throws DMException {
    log.debug("getting ClientProvTemplateEntity instance with id: " + id);
    try {
      ClientProvTemplateEntity instance = (ClientProvTemplateEntity) getSession().get(
          "com.npower.dm.hibernate.entity.ClientProvTemplateEntity", id);
      return instance;
    } catch (Exception re) {
      log.error("get failed", re);
      throw new DMException("get failed", re);
    }
  }

  /**
   * Find by example
   * @param instance
   * @return
   * @throws DMException
   */
  public List<ClientProvTemplateEntity> findByExample(ClientProvTemplateEntity instance) throws DMException {
    log.debug("finding ClientProvTemplateEntity instance by example");
    try {
      List<ClientProvTemplateEntity> results = getSession().createCriteria("com.npower.dm.hibernate.entity.ClientProvTemplateEntity").add(
          Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (Exception re) {
      log.error("find by example failed", re);
      throw new DMException("find by example failed", re);
    }
  }

  /**
   * Find by property
   * @param propertyName
   * @param value
   * @return
   * @throws DMException
   */
  public List<ClientProvTemplateEntity> findByProperty(String propertyName, Object value) throws DMException {
    log.debug("finding ClientProvTemplateEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from ClientProvTemplateEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (Exception re) {
      log.error("find by property name failed", re);
      throw new DMException("find by property name failed", re);
    }
  }

  /**
   * Find by name
   * @param name
   * @return
   * @throws DMException
   */
  public List<ClientProvTemplateEntity> findByName(Object name) throws DMException {
    return findByProperty(NAME, name);
  }

  /**
   * Find by description
   * @param description
   * @return
   * @throws DMException
   */
  public List<ClientProvTemplateEntity> findByDescription(Object description) throws DMException {
    return findByProperty(DESCRIPTION, description);
  }

  /**
   * Find by content
   * @param content
   * @return
   * @throws DMException
   */
  public List<ClientProvTemplateEntity> findByContent(Object content) throws DMException {
    return findByProperty(CONTENT, content);
  }

  /**
   * Find by last-updated-by
   * @param lastUpdatedBy
   * @return
   * @throws DMException
   */
  public List<ClientProvTemplateEntity> findByLastUpdatedBy(Object lastUpdatedBy) throws DMException {
    return findByProperty(LAST_UPDATED_BY, lastUpdatedBy);
  }

  /**
   * Find by change-version
   * @param changeVersion
   * @return
   * @throws DMException
   */
  public List<ClientProvTemplateEntity> findByChangeVersion(Object changeVersion) throws DMException {
    return findByProperty(CHANGE_VERSION, changeVersion);
  }

  /**
   * @param detachedInstance
   * @return
   * @throws DMException
   */
  public ClientProvTemplateEntity merge(ClientProvTemplateEntity detachedInstance) throws DMException {
    log.debug("merging ClientProvTemplateEntity instance");
    try {
      ClientProvTemplateEntity result = (ClientProvTemplateEntity) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (Exception re) {
      log.error("merge failed", re);
      throw new DMException("merge failed", re);
    }
  }

  /**
   * @param instance
   * @throws DMException
   */
  public void attachDirty(ClientProvTemplateEntity instance) throws DMException {
    log.debug("attaching dirty ClientProvTemplateEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (Exception re) {
      log.error("attach failed", re);
      throw new DMException("attach failed", re);
    }
  }

  /**
   * @param instance
   * @throws DMException
   */
  public void attachClean(ClientProvTemplateEntity instance) throws DMException {
    log.debug("attaching clean ClientProvTemplateEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (Exception re) {
      log.error("attach failed", re);
      throw new DMException("attach failed", re);
    }
  }
}