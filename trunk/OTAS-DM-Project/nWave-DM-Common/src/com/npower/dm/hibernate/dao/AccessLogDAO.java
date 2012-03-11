package com.npower.dm.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.npower.dm.hibernate.entity.AccessLogEntity;
import com.npower.dm.tracking.AccessLog;

/**
 * A data access object (DAO) providing persistence and search support for
 * AccessLogEntity entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.npower.dm.hibernate.entity.AccessLogEntity
 * @author MyEclipse Persistence Tools
 */

public class AccessLogDAO extends BaseHibernateDAO {
  private static final Log   log        = LogFactory.getLog(AccessLogDAO.class);

  // property constants
  public static final String URL        = "url";

  public static final String QUERY      = "query";

  public static final String CLIENT_IP  = "clientIp";

  public static final String USER_AGENT = "userAgent";

  public static final String SESSION_ID = "sessionId";

  public void save(AccessLog transientInstance) {
    log.debug("saving AccessLogEntity instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public void delete(AccessLog persistentInstance) {
    log.debug("deleting AccessLogEntity instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public AccessLog findById(Long id) {
    log.debug("getting AccessLogEntity instance with id: " + id);
    try {
      AccessLog instance = (AccessLog) getSession().get(AccessLogEntity.class.getName(), id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<AccessLog> findByExample(AccessLog instance) {
    log.debug("finding AccessLogEntity instance by example");
    try {
      List<AccessLog> results = getSession().createCriteria(AccessLogEntity.class.getName()).add(Example.create(instance))
          .list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  @SuppressWarnings("unchecked")
  public List<AccessLog> findByProperty(String propertyName, Object value) {
    log.debug("finding AccessLogEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from AccessLogEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  public List<AccessLog> findByUrl(Object url) {
    return findByProperty(URL, url);
  }

  public List<AccessLog> findByQuery(Object query) {
    return findByProperty(QUERY, query);
  }

  public List<AccessLog> findByClientIp(Object clientIp) {
    return findByProperty(CLIENT_IP, clientIp);
  }

  public List<AccessLog> findByUserAgent(Object userAgent) {
    return findByProperty(USER_AGENT, userAgent);
  }

  public List<AccessLog> findBySessionId(Object sessionId) {
    return findByProperty(SESSION_ID, sessionId);
  }

  @SuppressWarnings("unchecked")
  public List<AccessLog> findAll() {
    log.debug("finding all AccessLogEntity instances");
    try {
      String queryString = "from AccessLogEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public AccessLog merge(AccessLog detachedInstance) {
    log.debug("merging AccessLogEntity instance");
    try {
      AccessLog result = (AccessLog) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(AccessLog instance) {
    log.debug("attaching dirty AccessLogEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(AccessLog instance) {
    log.debug("attaching clean AccessLogEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }
}