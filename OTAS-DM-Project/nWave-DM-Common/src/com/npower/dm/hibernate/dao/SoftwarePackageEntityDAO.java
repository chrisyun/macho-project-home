/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/dao/SoftwarePackageEntityDAO.java,v 1.1 2008/01/27 09:50:16 zhao Exp $
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

import com.npower.dm.core.SoftwarePackage;

/**
 * Data access object (DAO) for domain model class SoftwarePackageEntity.
 * 
 * @see com.npower.dm.hibernate.entity.SoftwarePackageEntity
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/01/27 09:50:16 $
 */

public class SoftwarePackageEntityDAO extends BaseHibernateDAO {
  private static final Log   log           = LogFactory.getLog(SoftwarePackageEntityDAO.class);

  // property constants
  public static final String LANGUAGE      = "language";

  public static final String MIME_TYPE     = "mimeType";

  public static final String URL           = "url";

  public static final String BLOB_FILENAME = "blobFilename";

  public void save(SoftwarePackage transientInstance) {
    log.debug("saving SoftwarePackageEntity instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public void delete(SoftwarePackage persistentInstance) {
    log.debug("deleting SoftwarePackageEntity instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public SoftwarePackage findById(Long id) {
    log.debug("getting SoftwarePackageEntity instance with id: " + id);
    try {
      SoftwarePackage instance = (SoftwarePackage) getSession().get(
          "com.npower.dm.hibernate.entity.SoftwarePackageEntity", id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<SoftwarePackage> findByExample(SoftwarePackage instance) {
    log.debug("finding SoftwarePackageEntity instance by example");
    try {
      List<SoftwarePackage> results = getSession().createCriteria("com.npower.dm.hibernate.entity.SoftwarePackageEntity").add(
          Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<SoftwarePackage> findByProperty(String propertyName, Object value) {
    log.debug("finding SoftwarePackageEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from SoftwarePackageEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  public List<SoftwarePackage> findByLanguage(Object language) {
    return findByProperty(LANGUAGE, language);
  }

  public List<SoftwarePackage> findByMimeType(Object mimeType) {
    return findByProperty(MIME_TYPE, mimeType);
  }

  public List<SoftwarePackage> findByUrl(Object url) {
    return findByProperty(URL, url);
  }

  public List<SoftwarePackage> findByBlobFilename(Object blobFilename) {
    return findByProperty(BLOB_FILENAME, blobFilename);
  }

  @SuppressWarnings("unchecked")
  public List<SoftwarePackage> findAll() {
    log.debug("finding all SoftwarePackageEntity instances");
    try {
      String queryString = "from SoftwarePackageEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public SoftwarePackage merge(SoftwarePackage detachedInstance) {
    log.debug("merging SoftwarePackageEntity instance");
    try {
      SoftwarePackage result = (SoftwarePackage) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(SoftwarePackage instance) {
    log.debug("attaching dirty SoftwarePackageEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(SoftwarePackage instance) {
    log.debug("attaching clean SoftwarePackageEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }
}