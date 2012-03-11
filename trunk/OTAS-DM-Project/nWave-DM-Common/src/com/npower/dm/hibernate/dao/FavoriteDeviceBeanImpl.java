package com.npower.dm.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.npower.dm.core.Favorite;
import com.npower.dm.core.FavoriteDevice;
import com.npower.dm.hibernate.entity.FavoriteDeviceEntity;
import com.npower.dm.hibernate.entity.FavoriteEntity;
import com.npower.dm.hibernate.management.AbstractBean;
import com.npower.dm.management.FavoriteBean;
import com.npower.dm.management.FavoriteDeviceBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * Data access object (DAO) for domain model class FavoriteDevice.
 * 
 * @see com.npower.dm.hibernate.entity.FavoriteDeviceEntity
 * @author MyEclipse Persistence Tools
 */

public class FavoriteDeviceBeanImpl extends AbstractBean implements FavoriteDeviceBean {
  private static final Log   log       = LogFactory.getLog(FavoriteDeviceBeanImpl.class);

  // property constants
  public static final String DEVICE_ID = "deviceId";

  public FavoriteDevice newInstance() {
    return new FavoriteDeviceEntity();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDeviceDAO#save(com.hcp.dm.test.hibernate.entity.FavoriteDeviceEntity)
   */
  public void save(FavoriteDevice transientInstance) {
    log.debug("saving FavoriteDevice instance");
    try {
      this.getHibernateSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDeviceDAO#delete(com.hcp.dm.test.hibernate.entity.FavoriteDeviceEntity)
   */
  public void delete(FavoriteDevice persistentInstance) {
    log.debug("deleting FavoriteDevice instance");
    try {
      this.getHibernateSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDeviceDAO#findById(java.lang.Long)
   */
  public FavoriteDevice findById(java.lang.Long id) {
    log.debug("getting FavoriteDevice instance with id: " + id);
    try {
      FavoriteDeviceEntity instance = (FavoriteDeviceEntity) this.getHibernateSession().get(FavoriteDeviceEntity.class.getName(), id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDeviceDAO#findByExample(com.hcp.dm.test.hibernate.entity.FavoriteDeviceEntity)
   */
  public List<FavoriteDevice> findByExample(FavoriteDevice instance) {
    log.debug("finding FavoriteDevice instance by example");
    try {
      List<FavoriteDevice> results = this.getHibernateSession().createCriteria(FavoriteDeviceEntity.class.getName()).add(
          Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDeviceDAO#findByProperty(java.lang.String,
   *      java.lang.Object)
   */
  public List<FavoriteDevice> findByProperty(String propertyName, Object value) {
    log.debug("finding FavoriteDevice instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from FavoriteDeviceEntity as model where model." + propertyName + "= ?";
      Query queryObject = this.getHibernateSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDeviceDAO#findByDeviceId(java.lang.Object)
   */
  public List<FavoriteDevice> findByDeviceId(Object deviceId) {
    return findByProperty(DEVICE_ID, deviceId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDeviceDAO#findAll()
   */
  public List<FavoriteDevice> findAll() {
    log.debug("finding all FavoriteDevice instances");
    try {
      String queryString = "from FavoriteDevice";
      Query queryObject = this.getHibernateSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDeviceDAO#merge(com.hcp.dm.test.hibernate.entity.FavoriteDeviceEntity)
   */
  public FavoriteDevice merge(FavoriteDeviceEntity detachedInstance) {
    log.debug("merging FavoriteDevice instance");
    try {
      FavoriteDeviceEntity result = (FavoriteDeviceEntity) this.getHibernateSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDeviceDAO#attachDirty(com.hcp.dm.test.hibernate.entity.FavoriteDeviceEntity)
   */
  public void attachDirty(FavoriteDeviceEntity instance) {
    log.debug("attaching dirty FavoriteDevice instance");
    try {
      this.getHibernateSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDeviceDAO#attachClean(com.hcp.dm.test.hibernate.entity.FavoriteDeviceEntity)
   */
  public void attachClean(FavoriteDeviceEntity instance) {
    log.debug("attaching clean FavoriteDevice instance");
    try {
      this.getHibernateSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.dao.FavoriteDeviceDAO#addDeviceFavorite(java.lang.String,
   *      java.lang.String)
   */
  public void addDeviceFavorite(String deviceId, String favoriteId, String owner) {
    ManagementBeanFactory factory = null;
    try {
      factory = this.getManagementBeanFactory();
      FavoriteBean favoriteBean = (FavoriteBean)factory.createBean(FavoriteBeanImpl.class);
      Favorite favorite = null;
      if (favoriteId == null) {
        favorite = favoriteBean.newInstance();
        factory.beginTransaction();
        favorite.setName("Default Favorite");
        favorite.setOwner(owner);
        favorite.setShared(true);
        favoriteBean.save(favorite);
        factory.commit();
      } else {
        favorite = favoriteBean.findById(new Long(favoriteId));
      }
      FavoriteDevice fDevice = this.newInstance();
      fDevice.setDeviceId(new Long(deviceId));
      fDevice.setFavorite(favorite);

      factory.beginTransaction();
      this.save(fDevice);
      factory.commit();
    } catch (Exception ex) {
      if (factory != null) {
        try {
          factory.rollback();
        } catch (Exception e) {
          log.error("Add device favorite rollback failed", e);
        }
      }
    } finally {
    }
  }
  public Criteria getCriteria() {
    return this.getHibernateSession().createCriteria(FavoriteEntity.class.getName());
  }

}