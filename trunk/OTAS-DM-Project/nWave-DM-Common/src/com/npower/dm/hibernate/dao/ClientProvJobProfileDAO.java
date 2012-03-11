/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/dao/ClientProvJobProfileDAO.java,v 1.1 2008/02/18 10:10:22 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/02/18 10:10:22 $
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

import com.npower.dm.hibernate.entity.ClientProvJobProfile;

/**
 * Data access object (DAO) for domain model class ClientProvJobProfile.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/02/18 10:10:22 $
 */
public class ClientProvJobProfileDAO extends BaseHibernateDAO {
  private static final Log   log                 = LogFactory.getLog(ClientProvJobProfileDAO.class);

  // property constants
  public static final String PROFILE_EXTERNAL_ID = "profileExternalId";

  public void save(ClientProvJobProfile transientInstance) {
    log.debug("saving ClientProvJobProfile instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public void delete(ClientProvJobProfile persistentInstance) {
    log.debug("deleting ClientProvJobProfile instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public ClientProvJobProfile findById(Long id) {
    log.debug("getting ClientProvJobProfile instance with id: " + id);
    try {
      ClientProvJobProfile instance = (ClientProvJobProfile) getSession().get("ClientProvJobProfile", id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  public List findByExample(ClientProvJobProfile instance) {
    log.debug("finding ClientProvJobProfile instance by example");
    try {
      List results = getSession().createCriteria("ClientProvJobProfile").add(Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  public List findByProperty(String propertyName, Object value) {
    log.debug("finding ClientProvJobProfile instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from ClientProvJobProfile as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  public List findByProfileExternalId(Object profileExternalId) {
    return findByProperty(PROFILE_EXTERNAL_ID, profileExternalId);
  }

  public List findAll() {
    log.debug("finding all ClientProvJobProfile instances");
    try {
      String queryString = "from ClientProvJobProfile";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public ClientProvJobProfile merge(ClientProvJobProfile detachedInstance) {
    log.debug("merging ClientProvJobProfile instance");
    try {
      ClientProvJobProfile result = (ClientProvJobProfile) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(ClientProvJobProfile instance) {
    log.debug("attaching dirty ClientProvJobProfile instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(ClientProvJobProfile instance) {
    log.debug("attaching clean ClientProvJobProfile instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }
}