/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/management/TestPhoneNumberPolicy.java,v 1.2 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.2 $
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

import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestPhoneNumberPolicy extends TestCase {

  private static final String carrierID_1 = "CAR_TEST_1_" + System.currentTimeMillis();

  private static final String carrierID_2 = "CAR_TEST_2_" + System.currentTimeMillis();

  private static final String carrierID_3 = "CAR_TEST_3_" + System.currentTimeMillis();

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    this.cleanUpCarriers();
    
    this.setUpCarrier(carrierID_1, null);
    this.setUpCarrier(carrierID_2, null);
    this.setUpCarrier(carrierID_3, null);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void setUpCarrier(String carrierID, String phoneNumberPolicy) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    CarrierBean carrierBean = factory.createCarrierBean();
    try {
        Country country = factory.createCountryBean().getCountryByISOCode("CN");
        assertNotNull(country);
        assertEquals(country.getCountryCode(), "86");
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierID);
        if (carrier != null) {
           factory.beginTransaction();
           carrierBean.delete(carrier);
           factory.commit();
        }
        factory.beginTransaction();
        carrier = carrierBean.newCarrierInstance(country, carrierID, carrierID);
        carrier.setPhoneNumberPolicy(phoneNumberPolicy);
        carrierBean.update(carrier);
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  public void cleanUpCarriers() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    CarrierBean carrierBean = factory.createCarrierBean();
    try {
        List<Carrier> carriers = carrierBean.getAllCarriers();
        for (Carrier carrier: carriers) {
            factory.beginTransaction();
            carrier.setPhoneNumberPolicy(null);
            carrierBean.update(carrier);
            factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * 
   * @throws Exception
   */
  public void testCase1() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        String phoneNumberPolicy = ".*";
  
        factory = AllTests.getManagementBeanFactory();
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier_1 = carrierBean.getCarrierByExternalID(carrierID_1);
        carrier_1.setPhoneNumberPolicy(phoneNumberPolicy);
        factory.beginTransaction();
        carrierBean.update(carrier_1);
        factory.commit();
        
        Carrier carrier = carrierBean.findCarrierByPhoneNumberPolicy("123456789");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  /**
   * 
   * @throws Exception
   */
  public void testCase2() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        String phoneNumberPolicy = "138,139,133";
  
        factory = AllTests.getManagementBeanFactory();
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier_1 = carrierBean.getCarrierByExternalID(carrierID_1);
        carrier_1.setPhoneNumberPolicy(phoneNumberPolicy);
        factory.beginTransaction();
        carrierBean.update(carrier_1);
        factory.commit();
        
        Carrier carrier = carrierBean.findCarrierByPhoneNumberPolicy("1384343298789");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("13983928932");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());
        
        carrier = carrierBean.findCarrierByPhoneNumberPolicy("13383928932");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("1300000438743");
        assertNull(carrier);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  /**
   * 
   * @throws Exception
   */
  public void testCase3() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        String phoneNumberPolicy = "13[0-5]{1}.*";
  
        factory = AllTests.getManagementBeanFactory();
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier_1 = carrierBean.getCarrierByExternalID(carrierID_1);
        carrier_1.setPhoneNumberPolicy(phoneNumberPolicy);
        factory.beginTransaction();
        carrierBean.update(carrier_1);
        factory.commit();
        
        Carrier carrier = carrierBean.findCarrierByPhoneNumberPolicy("1304343298789");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("13183928932");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());
        
        carrier = carrierBean.findCarrierByPhoneNumberPolicy("13583928932");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("1380000438743");
        assertNull(carrier);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  /**
   * 
   * @throws Exception
   */
  public void testCase4() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        String phoneNumberPolicy = "13[0-5]{1}.*|18[0-5]{1}.*";
  
        factory = AllTests.getManagementBeanFactory();
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier_1 = carrierBean.getCarrierByExternalID(carrierID_1);
        carrier_1.setPhoneNumberPolicy(phoneNumberPolicy);
        factory.beginTransaction();
        carrierBean.update(carrier_1);
        factory.commit();
        
        Carrier carrier = carrierBean.findCarrierByPhoneNumberPolicy("1304343298789");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("18183928932");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());
        
        carrier = carrierBean.findCarrierByPhoneNumberPolicy("18583928932");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("1380000438743");
        assertNull(carrier);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  /**
   * 
   * @throws Exception
   */
  public void testCase5() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        String phoneNumberPolicy_1 = "13[0-5]{1}.*|18[0-5]{1}.*";
        String phoneNumberPolicy_2 = "13[6-9]{1}.*|18[6-9]{1}.*";
        String phoneNumberPolicy_3 = "15[0-9]{1}.*";
  
        factory = AllTests.getManagementBeanFactory();
        
        CarrierBean carrierBean = factory.createCarrierBean();
        
        Carrier carrier_1 = carrierBean.getCarrierByExternalID(carrierID_1);
        carrier_1.setPhoneNumberPolicy(phoneNumberPolicy_1);
        
        Carrier carrier_2 = carrierBean.getCarrierByExternalID(carrierID_2);
        carrier_2.setPhoneNumberPolicy(phoneNumberPolicy_2);
        
        Carrier carrier_3 = carrierBean.getCarrierByExternalID(carrierID_3);
        carrier_3.setPhoneNumberPolicy(phoneNumberPolicy_3);
        
        factory.beginTransaction();
        carrierBean.update(carrier_1);
        carrierBean.update(carrier_2);
        carrierBean.update(carrier_3);
        factory.commit();
        
        Carrier carrier = carrierBean.findCarrierByPhoneNumberPolicy("1304343298789");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("18183928932");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());
        
        carrier = carrierBean.findCarrierByPhoneNumberPolicy("18583928932");
        assertNotNull(carrier);
        assertEquals(carrierID_1, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("1380000438743");
        assertNotNull(carrier);
        assertEquals(carrierID_2, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("1880000438743");
        assertNotNull(carrier);
        assertEquals(carrierID_2, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("1360000438743");
        assertNotNull(carrier);
        assertEquals(carrierID_2, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("1580000438743");
        assertNotNull(carrier);
        assertEquals(carrierID_3, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("1580000438743");
        assertNotNull(carrier);
        assertEquals(carrierID_3, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("1550000438743");
        assertNotNull(carrier);
        assertEquals(carrierID_3, carrier.getExternalID());

        carrier = carrierBean.findCarrierByPhoneNumberPolicy("2222380000438743");
        assertNull(carrier);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
}
