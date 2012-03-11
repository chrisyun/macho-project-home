package com.npower.dm.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.npower.dm.core.Favorite;
import com.npower.dm.hibernate.entity.FavoriteEntity;
import com.npower.dm.hibernate.management.AbstractBean;
import com.npower.dm.management.FavoriteBean;

/**
 * Data access object (DAO) for domain model class Favorite.
 * 
 * @see com.npower.dm.hibernate.entity.FavoriteEntity
 * @author MyEclipse Persistence Tools
 */

public class FavoriteBeanImpl extends AbstractBean implements FavoriteBean {
  private static final Log   log         = LogFactory.getLog(FavoriteBeanImpl.class);

  // property constants
  public static final String NAME        = "name";

  public static final String DESCRIPTION = "description";

  public static final String ISSHARE     = "shared";

  public static final String OWNER       = "owner";

  public Favorite newInstance() {
    return new FavoriteEntity();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#save(com.hcp.dm.test.hibernate.entity.FavoriteEntity)
   */
  public void save(Favorite transientInstance) {
    log.debug("saving Favorite instance");
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
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#delete(com.hcp.dm.test.hibernate.entity.FavoriteEntity)
   */
  public void delete(Favorite persistentInstance) {
    log.debug("deleting Favorite instance");
    try {
      if (persistentInstance == null) {
        return;
      }
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
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#findById(java.lang.Long)
   */
  public Favorite findById(java.lang.Long id) {
    log.debug("getting Favorite instance with id: " + id);
    try {
      FavoriteEntity instance = (FavoriteEntity) this.getHibernateSession().get(FavoriteEntity.class.getName(), id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#findByExample(com.hcp.dm.test.hibernate.entity.FavoriteEntity)
   */
  public List<Favorite> findByExample(Favorite instance) {
    log.debug("finding Favorite instance by example");
    try {
      List<Favorite> results = this.getHibernateSession().createCriteria(FavoriteEntity.class.getName())
          .add(Example.create(instance)).list();
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
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#findByProperty(java.lang.String,
   *      java.lang.Object)
   */
  public List<Favorite> findByProperty(String propertyName, Object value) {
    log.debug("finding Favorite instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from FavoriteEntity as model where model." + propertyName + "= ?";
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
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#findByName(java.lang.Object)
   */
  public List<Favorite> findByName(Object name) {
    return findByProperty(NAME, name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#findByDescription(java.lang.Object)
   */
  public List<Favorite> findByDescription(Object description) {
    return findByProperty(DESCRIPTION, description);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#findByIsshare(java.lang.Object)
   */
  public List<Favorite> findByIsshare(Object isshare) {
    return findByProperty(ISSHARE, isshare);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#findByOwner(java.lang.Object)
   */
  public List<Favorite> findByOwner(Object owner) {
    return findByProperty(OWNER, owner);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#findAll()
   */
  public List<Favorite> findAll() {
    log.debug("finding all FavoriteEntity instances");
    try {
      String queryString = "from FavoriteEntity";
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
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#merge(com.hcp.dm.test.hibernate.entity.FavoriteEntity)
   */
  public Favorite merge(FavoriteEntity detachedInstance) {
    log.debug("merging FavoriteEntity instance");
    try {
      FavoriteEntity result = (FavoriteEntity) this.getHibernateSession().merge(detachedInstance);
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
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#attachDirty(com.hcp.dm.test.hibernate.entity.FavoriteEntity)
   */
  public void attachDirty(FavoriteEntity instance) {
    log.debug("attaching dirty Favorite instance");
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
   * @see com.hcp.dm.test.hibernate.dao.FavoriteDAO#attachClean(com.hcp.dm.test.hibernate.entity.FavoriteEntity)
   */
  public void attachClean(FavoriteEntity instance) {
    log.debug("attaching clean Favorite instance");
    try {
      this.getHibernateSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public Criteria getCriteria() {
    return this.getHibernateSession().createCriteria(FavoriteEntity.class.getName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.hcp.dm.test.dao.FavoriteDAO#findBySimpleSearch(java.lang.String,
   *      java.lang.Boolean, java.lang.String)
   */
  public List<Favorite> findBySimpleSearch(String owner, boolean shared, String searchText) {
    if (StringUtils.isEmpty(owner)) {
      return new ArrayList<Favorite>();
    }
    Criteria criteria = this.getCriteria();
    if (shared) {
      criteria.add(Expression.or(Expression.eq(ISSHARE, new Boolean(shared)), Expression.eq(OWNER, owner)));
    } else {
      criteria.add(Expression.eq(OWNER, owner));
    }
    if (StringUtils.isNotEmpty(searchText)) {
      searchText = searchText.trim();
      criteria.add(Expression.or(Expression.like(NAME, searchText, MatchMode.ANYWHERE), Expression.like(DESCRIPTION, searchText, MatchMode.ANYWHERE)));
    }
    criteria.addOrder(Order.asc(NAME));
    List<Favorite> result = criteria.list();
    return result;
  }

}