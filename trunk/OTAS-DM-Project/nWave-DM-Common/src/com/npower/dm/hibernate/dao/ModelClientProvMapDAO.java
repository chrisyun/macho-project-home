/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/dao/ModelClientProvMapDAO.java,v 1.2 2007/05/29 07:30:26 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2007/05/29 07:30:26 $
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
import com.npower.dm.hibernate.entity.ModelClientProvMap;
import com.npower.dm.hibernate.entity.ModelClientProvMapEntity;

/**
 * Data access object (DAO) for domain model class ModelClientProvMapEntity.
 * 
 * @see com.npower.dm.hibernate.entity.ModelClientProvMapEntity
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/05/29 07:30:26 $
 */
public class ModelClientProvMapDAO extends BaseHibernateDAO {

  private static final Log log = LogFactory.getLog(ModelClientProvMapDAO.class);

  /**
   * Save
   * @param transientInstance
   * @throws DMException
   */
  public void save(ModelClientProvMap transientInstance) throws DMException {
    log.debug("saving ModelClientProvMapEntity instance");
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
  public void delete(ModelClientProvMap persistentInstance) throws DMException {
    log.debug("deleting ModelClientProvMapEntity instance");
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
  public ModelClientProvMap findById(com.npower.dm.hibernate.entity.ModelClientProvMapId id) throws DMException {
    log.debug("getting ModelClientProvMapEntity instance with id: " + id);
    try {
      ModelClientProvMap instance = (ModelClientProvMap) getSession().get(
          "com.npower.dm.hibernate.entity.ModelClientProvMapEntity", id);
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
  public List<ModelClientProvMapEntity> findByExample(ModelClientProvMap instance) throws DMException {
    log.debug("finding ModelClientProvMapEntity instance by example");
    try {
      List<ModelClientProvMapEntity> results = getSession().createCriteria("com.npower.dm.hibernate.entity.ModelClientProvMapEntity").add(
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
  public List<ModelClientProvMapEntity> findByProperty(String propertyName, Object value) throws DMException {
    log.debug("finding ModelClientProvMapEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from ModelClientProvMapEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (Exception re) {
      log.error("find by property name failed", re);
      throw new DMException("find by property name failed", re);
    }
  }

  /**
   * @param detachedInstance
   * @return
   * @throws DMException
   */
  public ModelClientProvMap merge(ModelClientProvMap detachedInstance) throws DMException {
    log.debug("merging ModelClientProvMapEntity instance");
    try {
      ModelClientProvMap result = (ModelClientProvMap) getSession().merge(detachedInstance);
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
  public void attachDirty(ModelClientProvMap instance) throws DMException {
    log.debug("attaching dirty ModelClientProvMapEntity instance");
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
  public void attachClean(ModelClientProvMap instance) throws DMException {
    log.debug("attaching clean ModelClientProvMapEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (Exception re) {
      log.error("attach failed", re);
      throw new DMException("attach failed", re);
    }
  }
}