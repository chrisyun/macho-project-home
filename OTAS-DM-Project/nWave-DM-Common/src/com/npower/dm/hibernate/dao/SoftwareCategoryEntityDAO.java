/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/dao/SoftwareCategoryEntityDAO.java,v 1.2 2008/08/19 09:16:12 chenlei Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/19 09:16:12 $
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

import com.npower.dm.core.SoftwareCategory;

/**
 * Data access object (DAO) for domain model class SoftwareCategoryEntity.
 * 
 * @see com.npower.dm.hibernate.entity.SoftwareCategoryEntity
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/08/19 09:16:12 $
 */

public class SoftwareCategoryEntityDAO extends BaseHibernateDAO {
  private static final Log   log  = LogFactory.getLog(SoftwareCategoryEntityDAO.class);

  // property constants
  public static final String NAME = "name";

  public void save(SoftwareCategory transientInstance) {
    log.debug("saving SoftwareCategoryEntity instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public void delete(SoftwareCategory persistentInstance) {
    log.debug("deleting SoftwareCategoryEntity instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public SoftwareCategory findById(Long id) {
    log.debug("getting SoftwareCategoryEntity instance with id: " + id);
    try {
      SoftwareCategory instance = (SoftwareCategory) getSession().get(
          "com.npower.dm.hibernate.entity.SoftwareCategoryEntity", id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<SoftwareCategory> findByExample(SoftwareCategory instance) {
    log.debug("finding SoftwareCategoryEntity instance by example");
    try {
      List<SoftwareCategory> results = getSession().createCriteria("com.npower.dm.hibernate.entity.SoftwareCategoryEntity").add(
          Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<SoftwareCategory> findByProperty(String propertyName, Object value) {
    log.debug("finding SoftwareCategoryEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from SoftwareCategoryEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  public List<SoftwareCategory> findByName(Object name) {
    return findByProperty(NAME, name);
  }

  @SuppressWarnings("unchecked")
  public List<SoftwareCategory> findAll() {
    log.debug("finding all SoftwareCategoryEntity instances");
    try {
      String queryString = "from SoftwareCategoryEntity";
     
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public SoftwareCategory merge(SoftwareCategory detachedInstance) {
    log.debug("merging SoftwareCategoryEntity instance");
    try {
      SoftwareCategory result = (SoftwareCategory) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(SoftwareCategory instance) {
    log.debug("attaching dirty SoftwareCategoryEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(SoftwareCategory instance) {
    log.debug("attaching clean SoftwareCategoryEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }
}