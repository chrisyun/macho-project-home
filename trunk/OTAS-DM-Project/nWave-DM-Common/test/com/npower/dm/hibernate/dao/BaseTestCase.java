/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/hibernate/dao/BaseTestCase.java,v 1.1 2008/12/24 10:02:49 hcp Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/24 10:02:49 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Favorite;
import com.npower.dm.core.FavoriteDevice;
import com.npower.dm.hibernate.dao.FavoriteBeanImpl;
import com.npower.dm.hibernate.dao.FavoriteDeviceBeanImpl;
import com.npower.dm.management.FavoriteBean;
import com.npower.dm.management.FavoriteDeviceBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.1 $ ${date}上午08:43:21$ com.hcp.dm.test.dao HCP-DM-Test
 *          TestDmFavoriteDAO4Save.java
 */
public abstract class BaseTestCase extends TestCase {

  public BaseTestCase() {
    super();
  }

  /**
   * @param name
   */
  public BaseTestCase(String name) {
    super(name);
  }

  /**
   * @throws java.lang.Exception
   */
  protected void setUp() throws Exception {
    super.setUp();
    // 清楚Favorite、FavoriteDevice表中的数据
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      FavoriteBean favoriteBean = (FavoriteBean) factory.createBean(FavoriteBeanImpl.class);
      factory.beginTransaction();
      for (Favorite favorite : favoriteBean.findAll()) {
        favoriteBean.delete(favorite);
      }
      factory.commit();
    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    } finally {
      if (factory != null) {
        factory.release();
      }
    }

  }

  /**
   * @throws java.lang.Exception
   */
  protected void tearDown() throws Exception {
  }

  /**
   * 准备Favorite相关数据
   * 
   * <pre>
   * ------------------------------------------------------------------
   *     FavoriteId    Name      Descroption       Isshare      Owner
   * ------------------------------------------------------------------
   *         1        HCP: 1   HCP test case: 1     false        HCP
   *         2        ZDL: 2   ZDL test case: 2     true         ZDL
   *         3        HCP: 3   HCP test case: 3     false        HCP
   *         4        ZDL: 4   ZDL test case: 4     true         ZDL
   * </pre>
   * 
   * @throws Exception
   */
  protected void setupFavorites() throws Exception {

    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      FavoriteBean favoriteBean = (FavoriteBean) factory.createBean(FavoriteBeanImpl.class);

      factory.beginTransaction();
      for (int i = 1; i <= 20; i++) {
        Favorite favorite = favoriteBean.newInstance();
        if (i % 2 == 0) {
          favorite.setShared(true);
          favorite.setOwner("ZDL");
          favorite.setName("ZDL: " + i);
          favorite.setDescription("ZDL test case: " + i);
        } else {
          favorite.setShared(false);
          favorite.setOwner("HCP");
          favorite.setName("HCP: " + i);
          favorite.setDescription("HCP test case: " + i);
        }
        favoriteBean.save(favorite);
      }
      factory.commit();

    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

  /**
   * 准备FavoriteDevice相关数据
   * 
   * <pre>
   * ------------------------------------------
   *   FavoriteId    id    DeviceId
   *       1         1       1001
   *       2         2       1002
   *       3         3       1003
   *       4         4       1004
   * ------------------------------------------
   * </pre>
   * 
   * @throws Exception
   */
  protected void setupFavoriteDevices() throws Exception {
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      FavoriteBean favoriteBean = (FavoriteBean) factory.createBean(FavoriteBeanImpl.class);
      FavoriteDeviceBean favoriteDeviceBean = (FavoriteDeviceBean) factory.createBean(FavoriteDeviceBeanImpl.class);
      factory.beginTransaction();
      for (Favorite favorite : favoriteBean.findAll()) {
        FavoriteDevice favoriteDevice = favoriteDeviceBean.newInstance();
        favoriteDevice.setFavorite(favorite);
        favoriteDevice.setDeviceId(new Long(1000000000) + favorite.getFavoriteId());
        favoriteDeviceBean.save(favoriteDevice);
      }
      factory.commit();
    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

  /**
   * 为findBySimpleSearch准备测试数据如下：
   * 
   * <pre>
   * ---------------------------------------------------------------------
   *     FavoriteId    Name      Descroption       Isshare      Owner    
   * ---------------------------------------------------------------------
   *         1         HCP1          HCP1           true         HCP
   *         2         ZDL1          ZDL1           true         ZDL
   *         3         HCP2          HCP2           false        HCP
   *         4         ZDL2          ZDL2           false        ZDL
   * </pre>
   * 
   * @throws Exception
   */
  protected void setupSimpleSearch() throws Exception {
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      FavoriteBean dao = (FavoriteBean) factory.createBean(FavoriteBeanImpl.class);

      factory.beginTransaction();
      for (int i = 1; i <= 4; i++) {
        Favorite favorite = dao.newInstance();
        favorite.setFavoriteId(new Long(i));
        if (i == 1) {
          favorite.setName("HCP1");
          favorite.setDescription("HCP1");
          favorite.setShared(true);
          favorite.setOwner("HCP");
          dao.save(favorite);
        } else if (i == 2) {
          favorite.setName("ZDL1");
          favorite.setDescription("ZDL1");
          favorite.setShared(true);
          favorite.setOwner("ZDL");
          dao.save(favorite);
        } else if (i == 3) {
          favorite.setName("HCP2");
          favorite.setDescription("HCP2");
          favorite.setShared(false);
          favorite.setOwner("HCP");
          dao.save(favorite);
        } else if (i == 4) {
          favorite.setName("ZDL2");
          favorite.setDescription("ZDL2");
          favorite.setShared(false);
          favorite.setOwner("ZDL");
          dao.save(favorite);
        }
      }
      factory.commit();
    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

}
