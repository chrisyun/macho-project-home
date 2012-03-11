package com.npower.dm.management;

import java.util.List;

import org.hibernate.Criteria;

import com.npower.dm.core.Favorite;
import com.npower.dm.hibernate.entity.FavoriteEntity;

public interface FavoriteBean {
  
  public Favorite newInstance();

  public abstract void save(Favorite transientInstance);

  public abstract void delete(Favorite persistentInstance);

  public abstract Favorite findById(java.lang.Long id);

  public abstract List<Favorite> findByExample(Favorite instance);

  public abstract List<Favorite> findByProperty(String propertyName, Object value);

  public abstract List<Favorite> findByName(Object name);

  public abstract List<Favorite> findByDescription(Object description);

  public abstract List<Favorite> findByIsshare(Object isshare);

  public abstract List<Favorite> findByOwner(Object owner);

  public abstract List<Favorite> findAll();

  public abstract Favorite merge(FavoriteEntity detachedInstance);

  public abstract void attachDirty(FavoriteEntity instance);

  public abstract void attachClean(FavoriteEntity instance);
  
  /**
   * <pre>
   *  该方法为查询方法，查出的数据按owner降序排列。
   *  举例如下：
   *  
   * ---------------------------------------------
   *          Name   Share    Owner
   * ---------------------------------------------
   *           A1      T       ZDL
   *           A2      F       ZDL
   *           B1      T       HCP
   *           B2      F       HCP
   * ---------------------------------------------
   *      场景1、Owner为ZDL，当Share为true时，列出A1,A2,B1.
   *      场景2、Owner为ZDL，当Share为false时，列出A1,A2.
   * </pre>
   * @param owner
   *        不允许为null
   * @param shared
   * @param searchText
   *        允许为null
   * @return
   */
  public abstract List<Favorite> findBySimpleSearch(String owner, boolean shared, String searchText);
  
  public abstract Criteria getCriteria();

}