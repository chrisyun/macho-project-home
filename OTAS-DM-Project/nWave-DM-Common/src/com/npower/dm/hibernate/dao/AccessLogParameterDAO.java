package com.npower.dm.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.npower.dm.tracking.AccessLogParameter;

/**
 * A data access object (DAO) providing persistence and search support for
 * AccessLogParameterEntity entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.npower.dm.hibernate.entity.AccessLogParameterEntity
 * @author MyEclipse Persistence Tools
 */

public class AccessLogParameterDAO extends BaseHibernateDAO {
  private static final Log   log   = LogFactory.getLog(AccessLogParameterDAO.class);

  // property constants
  public static final String NAME  = "name";

  public static final String VALUE = "value";

  public void save(AccessLogParameter transientInstance) {
    log.debug("saving AccessLogParameterEntity instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public void delete(AccessLogParameter persistentInstance) {
    log.debug("deleting AccessLogParameterEntity instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public AccessLogParameter findById(Long id) {
    log.debug("getting AccessLogParameterEntity instance with id: " + id);
    try {
      AccessLogParameter instance = (AccessLogParameter) getSession().get(
          "com.npower.dm.hibernate.entity.AccessLogParameterEntity", id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<AccessLogParameter> findByExample(AccessLogParameter instance) {
    log.debug("finding AccessLogParameterEntity instance by example");
    try {
      List<AccessLogParameter> results = getSession().createCriteria("com.npower.dm.hibernate.entity.AccessLogParameterEntity").add(
          Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<AccessLogParameter> findByProperty(String propertyName, Object value) {
    log.debug("finding AccessLogParameterEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from AccessLogParameterEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  public List<AccessLogParameter> findByName(Object name) {
    return findByProperty(NAME, name);
  }

  public List<AccessLogParameter> findByValue(Object value) {
    return findByProperty(VALUE, value);
  }

  @SuppressWarnings("unchecked")
  public List<AccessLogParameter> findAll() {
    log.debug("finding all AccessLogParameterEntity instances");
    try {
      String queryString = "from AccessLogParameterEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public AccessLogParameter merge(AccessLogParameter detachedInstance) {
    log.debug("merging AccessLogParameterEntity instance");
    try {
      AccessLogParameter result = (AccessLogParameter) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(AccessLogParameter instance) {
    log.debug("attaching dirty AccessLogParameterEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(AccessLogParameter instance) {
    log.debug("attaching clean AccessLogParameterEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }
}