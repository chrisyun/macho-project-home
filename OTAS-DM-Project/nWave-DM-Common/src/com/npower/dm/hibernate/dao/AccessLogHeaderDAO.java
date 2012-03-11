package com.npower.dm.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.npower.dm.hibernate.entity.AccessLogHeaderEntity;
import com.npower.dm.tracking.AccessLogHeader;

/**
 * A data access object (DAO) providing persistence and search support for
 * AccessLogHeaderEntity entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.npower.dm.hibernate.entity.AccessLogHeaderEntity
 * @author MyEclipse Persistence Tools
 */

public class AccessLogHeaderDAO extends BaseHibernateDAO {
  private static final Log   log   = LogFactory.getLog(AccessLogHeaderDAO.class);

  // property constants
  public static final String NAME  = "name";

  public static final String VALUE = "value";

  public void save(AccessLogHeader transientInstance) {
    log.debug("saving AccessLogHeaderEntity instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public void delete(AccessLogHeader persistentInstance) {
    log.debug("deleting AccessLogHeaderEntity instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public AccessLogHeader findById(Long id) {
    log.debug("getting AccessLogHeaderEntity instance with id: " + id);
    try {
      AccessLogHeader instance = (AccessLogHeader) getSession()
          .get("com.npower.dm.hibernate.entity.AccessLogHeaderEntity", id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<AccessLogHeader> findByExample(AccessLogHeader instance) {
    log.debug("finding AccessLogHeaderEntity instance by example");
    try {
      List<AccessLogHeader> results = getSession().createCriteria(AccessLogHeaderEntity.class.getName()).add(
          Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<AccessLogHeader> findByProperty(String propertyName, Object value) {
    log.debug("finding AccessLogHeaderEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from AccessLogHeaderEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  public List<AccessLogHeader> findByName(Object name) {
    return findByProperty(NAME, name);
  }

  public List<AccessLogHeader> findByValue(Object value) {
    return findByProperty(VALUE, value);
  }

  @SuppressWarnings("unchecked")
  public List<AccessLogHeader> findAll() {
    log.debug("finding all AccessLogHeaderEntity instances");
    try {
      String queryString = "from AccessLogHeaderEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public AccessLogHeader merge(AccessLogHeader detachedInstance) {
    log.debug("merging AccessLogHeaderEntity instance");
    try {
      AccessLogHeader result = (AccessLogHeader) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(AccessLogHeader instance) {
    log.debug("attaching dirty AccessLogHeaderEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(AccessLogHeader instance) {
    log.debug("attaching clean AccessLogHeaderEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }
}