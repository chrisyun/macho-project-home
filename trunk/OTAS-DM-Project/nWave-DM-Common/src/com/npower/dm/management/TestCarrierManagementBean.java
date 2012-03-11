/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestCarrierManagementBean.java,v 1.12 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.12 $
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

import java.util.Set;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;

/**
 * @author Zhao DongLu
 * 
 */
public class TestCarrierManagementBean extends TestCase {

  private String countryISOCode = "CN";

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
    String externalID = "carrier_test_" + now;
    String carrierName = "Test CarrierEntity " + now;

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();

    CarrierBean bean = beanFactory.createCarrierBean();

    try {

      beanFactory.beginTransaction();

      Carrier carrier = bean.getCarrierByExternalID(externalID);
      if (carrier != null) {
        bean.delete(carrier);
      }

      CountryBean carrierBean = beanFactory.createCountryBean();
      Country country = carrierBean.getCountryByISOCode(countryISOCode);

      carrier = bean.newCarrierInstance(country, externalID, carrierName);
      bean.update(carrier);

      carrier = bean.getCarrierByExternalID(externalID);
      assertEquals(carrier.getExternalID(), externalID);
      assertEquals(carrier.getName(), carrierName);
      assertEquals(carrier.getCountry().getISOCode(), countryISOCode);

      Country c = carrier.getCountry();
      Set<Carrier> set = c.getCarriers();
      assertTrue(set.size() > 0);

      bean.delete(carrier);
      carrier = bean.getCarrierByExternalID(externalID);
      assertNull(carrier);

      beanFactory.commit();
    } catch (Exception e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }
  
  public void testPhoneNumberPolicy() throws Exception {
    
  }
  

}
