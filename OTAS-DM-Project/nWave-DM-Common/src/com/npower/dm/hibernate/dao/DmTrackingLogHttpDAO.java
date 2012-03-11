package com.npower.dm.hibernate.dao;

import com.npower.dm.hibernate.entity.DmTrackingLogHttp;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class DmTrackingLogHttp.
 * 
 * @see com.npower.dm.hibernate.entity.DmTrackingLogHttp
 * @author MyEclipse Persistence Tools
 */

public class DmTrackingLogHttpDAO extends BaseHibernateDAO {
  private static final Log   log              = LogFactory.getLog(DmTrackingLogHttpDAO.class);

  // property constants
  public static final String DM_SESSION_ID    = "dmSessionId";

  public static final String DM_REQUEST_SIZE  = "dmRequestSize";

  public static final String DM_RESPONSE_SIZE = "dmResponseSize";

  public static final String CLIENT_IP        = "clientIp";

  public static final String USER_AGENT       = "userAgent";

  public void save(DmTrackingLogHttp transientInstance) {
    log.debug("saving DmTrackingLogHttp instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public void delete(DmTrackingLogHttp persistentInstance) {
    log.debug("deleting DmTrackingLogHttp instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public DmTrackingLogHttp findById(Long id) {
    log.debug("getting DmTrackingLogHttp instance with id: " + id);
    try {
      DmTrackingLogHttp instance = (DmTrackingLogHttp) getSession().get(
          "com.npower.dm.hibernate.entity.DmTrackingLogHttp", id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  public List<DmTrackingLogHttp> findByExample(DmTrackingLogHttp instance) {
    log.debug("finding DmTrackingLogHttp instance by example");
    try {
      List<DmTrackingLogHttp> results = getSession().createCriteria("com.npower.dm.hibernate.entity.DmTrackingLogHttp").add(
          Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  public List<DmTrackingLogHttp> findByProperty(String propertyName, Object value) {
    log.debug("finding DmTrackingLogHttp instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from DmTrackingLogHttp as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  public List<DmTrackingLogHttp> findByDmSessionId(Object dmSessionId) {
    return findByProperty(DM_SESSION_ID, dmSessionId);
  }

  public List<DmTrackingLogHttp> findByDmRequestSize(Object dmRequestSize) {
    return findByProperty(DM_REQUEST_SIZE, dmRequestSize);
  }

  public List<DmTrackingLogHttp> findByDmResponseSize(Object dmResponseSize) {
    return findByProperty(DM_RESPONSE_SIZE, dmResponseSize);
  }

  public List<DmTrackingLogHttp> findByClientIp(Object clientIp) {
    return findByProperty(CLIENT_IP, clientIp);
  }

  public List<DmTrackingLogHttp> findByUserAgent(Object userAgent) {
    return findByProperty(USER_AGENT, userAgent);
  }

  public List<DmTrackingLogHttp> findAll() {
    log.debug("finding all DmTrackingLogHttp instances");
    try {
      String queryString = "from DmTrackingLogHttp";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public DmTrackingLogHttp merge(DmTrackingLogHttp detachedInstance) {
    log.debug("merging DmTrackingLogHttp instance");
    try {
      DmTrackingLogHttp result = (DmTrackingLogHttp) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(DmTrackingLogHttp instance) {
    log.debug("attaching dirty DmTrackingLogHttp instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(DmTrackingLogHttp instance) {
    log.debug("attaching clean DmTrackingLogHttp instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }
}