/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/dao/SoftwareVendorDAO.java,v 1.1 2008/01/27 09:50:16 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/01/27 09:50:16 $
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

import com.npower.dm.core.SoftwareVendor;

/**
 * Data access object (DAO) for domain model class SoftwareVendorEntity.
 * 
 * @see com.npower.dm.hibernate.entity.SoftwareVendorEntity
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/01/27 09:50:16 $
 */

public class SoftwareVendorDAO extends BaseHibernateDAO {
  private static final Log   log      = LogFactory.getLog(SoftwareVendorDAO.class);

  // property constants
  public static final String NAME     = "name";

  public static final String WEB_SITE = "webSite";

  public void save(SoftwareVendor transientInstance) {
    log.debug("saving SoftwareVendorEntity instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public void delete(SoftwareVendor persistentInstance) {
    log.debug("deleting SoftwareVendorEntity instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public SoftwareVendor findById(Long id) {
    log.debug("getting SoftwareVendorEntity instance with id: " + id);
    try {
      SoftwareVendor instance = (SoftwareVendor) getSession().get("com.npower.dm.hibernate.entity.SoftwareVendorEntity", id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<SoftwareVendor> findByExample(SoftwareVendor instance) {
    log.debug("finding SoftwareVendorEntity instance by example");
    try {
      List<SoftwareVendor> results = getSession().createCriteria("com.npower.dm.hibernate.entity.SoftwareVendorEntity").add(
          Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<SoftwareVendor> findByProperty(String propertyName, Object value) {
    log.debug("finding SoftwareVendorEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from SoftwareVendorEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  public List<SoftwareVendor> findByName(Object name) {
    return findByProperty(NAME, name);
  }

  public List<SoftwareVendor> findByWebSite(Object webSite) {
    return findByProperty(WEB_SITE, webSite);
  }

  @SuppressWarnings("unchecked")
  public List<SoftwareVendor> findAll() {
    log.debug("finding all SoftwareVendorEntity instances");
    try {
      String queryString = "from SoftwareVendorEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public SoftwareVendor merge(SoftwareVendor detachedInstance) {
    log.debug("merging SoftwareVendorEntity instance");
    try {
      SoftwareVendor result = (SoftwareVendor) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(SoftwareVendor instance) {
    log.debug("attaching dirty SoftwareVendorEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(SoftwareVendor instance) {
    log.debug("attaching clean SoftwareVendorEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }
}