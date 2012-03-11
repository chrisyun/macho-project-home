/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/hibernate/dao/TestFavoriteDAO4FindByProperty.java,v 1.1 2008/12/24 10:02:49 hcp Exp $
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

import java.util.List;

import com.npower.dm.AllTests;
import com.npower.dm.core.Favorite;
import com.npower.dm.hibernate.dao.FavoriteBeanImpl;
import com.npower.dm.management.FavoriteBean;
import com.npower.dm.management.ManagementBeanFactory;


/**
 * @author Huang ChunPing
 * @version $Revision: 1.1 $ ${date}ÉÏÎç03:41:03$
 * com.hcp.dm.test.dao
 * HCP-DM-Test
 * TestFavoriteDAO4FindByProperty.java
 */
public class TestFavoriteDAO4FindByProperty extends BaseTestCase {

  /**
   * @param name
   */
  public TestFavoriteDAO4FindByProperty(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    this.setupFavorites();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link com.npower.dm.hibernate.dao.FavoriteBeanImpl#findByProperty(java.lang.String, java.lang.Object)}.
   */
  public void testFindByProperty() throws Exception {
    ManagementBeanFactory factory = null;
    String favoriteName = "HCP: 5";
    try {
      factory = AllTests.getManagementBeanFactory();
      FavoriteBean favoriteBean = (FavoriteBean) factory.createBean(FavoriteBeanImpl.class);
      List<Favorite> favoriteList = favoriteBean.findByProperty("name", favoriteName);
      
      assertFalse(favoriteList.isEmpty());
      assertEquals(1, favoriteList.size());
      assertEquals(favoriteName, favoriteList.get(0).getName());
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
