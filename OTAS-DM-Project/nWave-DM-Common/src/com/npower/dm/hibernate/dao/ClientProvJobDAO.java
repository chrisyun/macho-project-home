/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/dao/ClientProvJobDAO.java,v 1.1 2008/02/18 10:10:22 zhao Exp $
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

import com.npower.dm.core.ClientProvJob;
import com.npower.dm.hibernate.entity.ClientProvJobEntity;

/**
 * Data access object (DAO) for domain model class ClientProvJobEntity.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/02/18 10:10:22 $
 */
public class ClientProvJobDAO extends BaseHibernateDAO {
  private static final Log   log             = LogFactory.getLog(ClientProvJobDAO.class);

  // property constants
  public static final String NAME            = "name";

  public static final String TYPE            = "type";

  public static final String DESCRIPTION     = "description";

  public static final String STATE           = "state";

  public static final String MAX_RETRIES     = "maxRetries";

  public static final String MAX_DURATION    = "maxDuration";

  public static final String CREATED_BY      = "createdBy";

  public static final String LAST_UPDATED_BY = "lastUpdatedBy";

  public void save(ClientProvJobEntity transientInstance) {
    log.debug("saving ClientProvJobEntity instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public void delete(ClientProvJobEntity persistentInstance) {
    log.debug("deleting ClientProvJobEntity instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public ClientProvJob findById(Long id) {
    log.debug("getting ClientProvJobEntity instance with id: " + id);
    try {
      ClientProvJobEntity instance = (ClientProvJobEntity) getSession().get(ClientProvJobEntity.class, id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  public List<ClientProvJob> findByExample(ClientProvJobEntity instance) {
    log.debug("finding ClientProvJobEntity instance by example");
    try {
      List<ClientProvJob> results = getSession().createCriteria(ClientProvJobEntity.class).add(Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  public List<ClientProvJob> findByProperty(String propertyName, Object value) {
    log.debug("finding ClientProvJobEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from ClientProvJobEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  public List<ClientProvJob> findByName(Object name) {
    return findByProperty(NAME, name);
  }

  public List<ClientProvJob> findByType(Object type) {
    return findByProperty(TYPE, type);
  }

  public List<ClientProvJob> findByDescription(Object description) {
    return findByProperty(DESCRIPTION, description);
  }

  public List<ClientProvJob> findByState(Object state) {
    return findByProperty(STATE, state);
  }

  public List<ClientProvJob> findByMaxRetries(Object maxRetries) {
    return findByProperty(MAX_RETRIES, maxRetries);
  }

  public List<ClientProvJob> findByMaxDuration(Object maxDuration) {
    return findByProperty(MAX_DURATION, maxDuration);
  }

  public List<ClientProvJob> findByCreatedBy(Object createdBy) {
    return findByProperty(CREATED_BY, createdBy);
  }

  public List<ClientProvJob> findByLastUpdatedBy(Object lastUpdatedBy) {
    return findByProperty(LAST_UPDATED_BY, lastUpdatedBy);
  }

  public List<ClientProvJob> findAll() {
    log.debug("finding all ClientProvJobEntity instances");
    try {
      String queryString = "from ClientProvJobEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public ClientProvJobEntity merge(ClientProvJobEntity detachedInstance) {
    log.debug("merging ClientProvJobEntity instance");
    try {
      ClientProvJobEntity result = (ClientProvJobEntity) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(ClientProvJobEntity instance) {
    log.debug("attaching dirty ClientProvJobEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(ClientProvJobEntity instance) {
    log.debug("attaching clean ClientProvJobEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }
}