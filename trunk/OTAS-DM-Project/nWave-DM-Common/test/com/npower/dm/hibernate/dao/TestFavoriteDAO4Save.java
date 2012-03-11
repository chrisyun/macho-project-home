/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/hibernate/dao/TestFavoriteDAO4Save.java,v 1.1 2008/12/24 10:02:49 hcp Exp $
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

import com.npower.dm.AllTests;
import com.npower.dm.core.Favorite;
import com.npower.dm.hibernate.dao.FavoriteBeanImpl;
import com.npower.dm.management.FavoriteBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.1 $ ${date}上午03:20:19$ com.hcp.dm.test.dao HCP-DM-Test
 *          TestDmFavorite.java
 */
public class TestFavoriteDAO4Save extends BaseTestCase {

  /**
   * @param name
   */
  public TestFavoriteDAO4Save() throws Exception {
    super.setUp();
  }

  protected void setUp() throws Exception {
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
    }
    
  }

  protected void tearDown() throws Exception {
  }

  /**
   * 创建新的记录
   * 
   * @throws Exception
   */
  public void testSaveCase1() throws Exception {
    long favoriteId = 10010;
    String name = "test device favorite";
    String description = "description";
    boolean isshare = true;
    String owner = "hcp";
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      FavoriteBean favoriteBean = (FavoriteBean) factory.createBean(FavoriteBeanImpl.class);
      Favorite favorite = favoriteBean.newInstance();
      favorite.setFavoriteId(favoriteId);
      favorite.setName(name);
      favorite.setDescription(description);
      favorite.setShared(isshare);
      favorite.setOwner(owner);
      factory.beginTransaction();
      favoriteBean.save(favorite);
      factory.commit();

      assertEquals(1, favoriteBean.findAll().size());
      Favorite tFavorite = favoriteBean.findById(favoriteId);
      assertEquals(new Long(favoriteId), tFavorite.getFavoriteId());
      assertEquals(name, tFavorite.getName());
      assertEquals(description, tFavorite.getDescription());
      assertEquals(isshare, tFavorite.isShared());

    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    } finally {
    }
  }

  /**
   * 修改存在的记录
   * 
   * @throws Exception
   */
  public void testSaveCase2() throws Exception {
    this.testSaveCase1();
    long favoriteId = 10010;
    String name = "test";
    String description = "desc";
    boolean isshare = false;
    String owner = "hp";
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      FavoriteBean favoriteBean = (FavoriteBean) factory.createBean(FavoriteBeanImpl.class);
      Favorite favorite = favoriteBean.findById(favoriteId);
      favorite.setName(name);
      favorite.setDescription(description);
      favorite.setShared(isshare);
      favorite.setOwner(owner);
      factory.beginTransaction();
      favoriteBean.save(favorite);
      factory.commit();

      Favorite tFavorite = favoriteBean.findById(favoriteId);
      assertEquals(new Long(favoriteId), tFavorite.getFavoriteId());
      assertEquals(name, tFavorite.getName());
      assertEquals(description, tFavorite.getDescription());
      assertEquals(isshare, tFavorite.isShared());
      
    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw e;
    } finally {
    }
  }
}
