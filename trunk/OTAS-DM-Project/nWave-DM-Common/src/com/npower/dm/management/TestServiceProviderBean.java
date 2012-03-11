/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestServiceProviderBean.java,v 1.1 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/16 10:11:15 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.management;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.ServiceProvider;

/**
 * @author Zhao DongLu
 * 
 */
public class TestServiceProviderBean extends TestCase {

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test cases: add, delete, find.
   * @throws Exception
   */
  public void testAdd() throws Exception {

    long now = System.currentTimeMillis();
    String externalID = "sp_test_" + now;
    String name = "Test SP " + now;

    ManagementBeanFactory beanFactory = null;
    try {
      beanFactory = AllTests.getManagementBeanFactory();
      ServiceProviderBean bean = beanFactory.createServiceProviderBean();

      beanFactory.beginTransaction();

      ServiceProvider sp = bean.getServiceProviderByExternalID(externalID);
      if (sp != null) {
        bean.delete(sp);
      }

      sp = bean.newServiceProviderInstance(externalID, name);
      bean.update(sp);
      beanFactory.commit();
    } catch (Exception e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

    try {
        beanFactory = AllTests.getManagementBeanFactory();
        ServiceProviderBean bean = beanFactory.createServiceProviderBean();
        ServiceProvider sp = bean.getServiceProviderByExternalID(externalID);
        assertEquals(sp.getExternalID(), externalID);
        assertEquals(sp.getName(), name);

        beanFactory.beginTransaction();
        bean.delete(sp);
        beanFactory.commit();
    } catch (Exception e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

    try {
        beanFactory = AllTests.getManagementBeanFactory();
        ServiceProviderBean bean = beanFactory.createServiceProviderBean();
        ServiceProvider sp = bean.getServiceProviderByExternalID(externalID);
        assertNull(sp);
    } catch (Exception e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }
  
}
