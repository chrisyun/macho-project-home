/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/dao/SoftwareEntityDAO.java,v 1.1 2008/01/27 09:50:16 zhao Exp $
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

import com.npower.dm.core.Software;

/**
 * Data access object (DAO) for domain model class SoftwareEntity.
 * 
 * @see com.npower.dm.hibernate.entity.SoftwareEntity
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/01/27 09:50:16 $
 */

public class SoftwareEntityDAO extends BaseHibernateDAO {
  private static final Log   log          = LogFactory.getLog(SoftwareEntityDAO.class);

  // property constants
  public static final String EXTERNAL_ID  = "externalId";

  public static final String NAME         = "name";

  public static final String VERSION      = "version";

  public static final String LICENSE_TYPE = "licenseType";

  public void save(Software transientInstance) {
    log.debug("saving SoftwareEntity instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public void delete(Software persistentInstance) {
    log.debug("deleting SoftwareEntity instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public Software findById(Long id) {
    log.debug("getting SoftwareEntity instance with id: " + id);
    try {
      Software instance = (Software) getSession().get("com.npower.dm.hibernate.entity.SoftwareEntity", id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<Software> findByExample(Software instance) {
    log.debug("finding SoftwareEntity instance by example");
    try {
      List<Software>  results = getSession().createCriteria("com.npower.dm.hibernate.entity.SoftwareEntity").add(
          Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<Software>  findByProperty(String propertyName, Object value) {
    log.debug("finding SoftwareEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from SoftwareEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  public List<Software>  findByExternalId(Object externalId) {
    return findByProperty(EXTERNAL_ID, externalId);
  }

  public List<Software>  findByName(Object name) {
    return findByProperty(NAME, name);
  }

  public List<Software>  findByVersion(Object version) {
    return findByProperty(VERSION, version);
  }

  public List<Software>  findByLicenseType(Object licenseType) {
    return findByProperty(LICENSE_TYPE, licenseType);
  }

  @SuppressWarnings("unchecked")
  public List<Software>  findAll() {
    log.debug("finding all SoftwareEntity instances");
    try {
      String queryString = "from SoftwareEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public Software merge(Software detachedInstance) {
    log.debug("merging SoftwareEntity instance");
    try {
      Software result = (Software) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(Software instance) {
    log.debug("attaching dirty SoftwareEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(Software instance) {
    log.debug("attaching clean SoftwareEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }
}