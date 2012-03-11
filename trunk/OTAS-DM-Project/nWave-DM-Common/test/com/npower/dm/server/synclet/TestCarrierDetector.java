/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/server/synclet/TestCarrierDetector.java,v 1.2 2008/06/16 10:11:15 zhao Exp $
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
package com.npower.dm.server.synclet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;
import sync4j.framework.core.SyncML;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestCarrierDetector extends TestCase {

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
    this.setUpCarrier(carrierID_2, "83[5-9]{1}.*");
    this.setUpCarrier(carrierID_3, "83[0-4]{1}.*");
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
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

  public void testSimpleCarrierDetectorCase1() throws Exception {
    String carrierExternalID = "NONE_EXISTS_CARRIER";
    SimpleCarrierDetector detector = new SimpleCarrierDetector();
    detector.setCarrierExternalID(carrierExternalID);
    
    String deviceExternalID = null;
    SyncML message = null;
    String phoneNumber = null;
    HttpServletRequest httpRequest = null;
    String carrierID = detector.getCarrierID(deviceExternalID, phoneNumber, httpRequest, message);
    assertNull(carrierID);
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        CarrierBean bean = factory.createCarrierBean();
        Carrier carrier = bean.getCarrierByID(carrierID);
        assertNull(carrier);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  public void testSimpleCarrierDetectorCase2() throws Exception {
    String carrierExternalID = carrierID_1;
    SimpleCarrierDetector detector = new SimpleCarrierDetector();
    detector.setCarrierExternalID(carrierExternalID);
    
    String deviceExternalID = null;
    SyncML message = null;
    String phoneNumber = null;
    HttpServletRequest httpRequest = null;
    String carrierID = detector.getCarrierID(deviceExternalID, phoneNumber, httpRequest, message);
    assertNotNull(carrierID);
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        CarrierBean bean = factory.createCarrierBean();
        Carrier carrier = bean.getCarrierByID(carrierID);
        assertNotNull(carrier);
        assertEquals(carrierExternalID, carrier.getExternalID());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void testSimpleCarrierDetectorCase3() throws Exception {
    SimpleCarrierDetector detector = new SimpleCarrierDetector();
    detector.setCarrierExternalID(null);
    
    String deviceExternalID = null;
    SyncML message = null;
    String phoneNumber = null;
    HttpServletRequest httpRequest = null;
    String carrierID = detector.getCarrierID(deviceExternalID, phoneNumber, httpRequest, message);
    assertNull(carrierID);
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        CarrierBean bean = factory.createCarrierBean();
        Carrier carrier = bean.getCarrierByID(carrierID);
        assertNull(carrier);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void testPhoneNumberCarrierDetectorCase1() throws Exception {
    CarrierDetector detector = new PhoneNumberCarrierDetector();
    
    String deviceExternalID = null;
    SyncML message = null;
    HttpServletRequest httpRequest = null;
    
    String phoneNumber = "838489384930";
    String carrierID = detector.getCarrierID(deviceExternalID, phoneNumber, httpRequest, message);
    assertNotNull(carrierID);
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        CarrierBean bean = factory.createCarrierBean();
        Carrier carrier = bean.getCarrierByID(carrierID);
        assertNotNull(carrier);
        assertEquals(carrierID_2, carrier.getExternalID());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void testPhoneNumberCarrierDetectorCase2() throws Exception {
    CarrierDetector detector = new PhoneNumberCarrierDetector();
    
    String deviceExternalID = null;
    SyncML message = null;
    HttpServletRequest httpRequest = null;
    
    String phoneNumber = "830489384930";
    String carrierID = detector.getCarrierID(deviceExternalID, phoneNumber, httpRequest, message);
    assertNotNull(carrierID);
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        CarrierBean bean = factory.createCarrierBean();
        Carrier carrier = bean.getCarrierByID(carrierID);
        assertNotNull(carrier);
        assertEquals(carrierID_3, carrier.getExternalID());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void testChainDetectorCase1() throws Exception {
    CarrierDetectorChain detector = new CarrierDetectorChain();
    
    String carrierExternalID = "NONE_EXISTS_CARRIER";
    SimpleCarrierDetector detector1 = new SimpleCarrierDetector();
    detector1.setCarrierExternalID(carrierExternalID);
    
    CarrierDetector[] chain = {detector1, new PhoneNumberCarrierDetector()};
    detector.setChain(chain);
    ;
    
    String deviceExternalID = null;
    SyncML message = null;
    HttpServletRequest httpRequest = null;
    
    String phoneNumber = "830489384930";
    String carrierID = detector.getCarrierID(deviceExternalID, phoneNumber, httpRequest, message);
    assertNotNull(carrierID);
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        CarrierBean bean = factory.createCarrierBean();
        Carrier carrier = bean.getCarrierByID(carrierID);
        assertNotNull(carrier);
        assertEquals(carrierID_3, carrier.getExternalID());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void testChainDetectorCase2() throws Exception {
    CarrierDetectorChain detector = new CarrierDetectorChain();
    
    String carrierExternalID = carrierID_1;
    SimpleCarrierDetector detector1 = new SimpleCarrierDetector();
    detector1.setCarrierExternalID(carrierExternalID);
    
    CarrierDetector[] chain = {detector1, new PhoneNumberCarrierDetector()};
    detector.setChain(chain);
    ;
    
    String deviceExternalID = null;
    SyncML message = null;
    HttpServletRequest httpRequest = null;
    
    String phoneNumber = "830489384930";
    String carrierID = detector.getCarrierID(deviceExternalID, phoneNumber, httpRequest, message);
    assertNotNull(carrierID);
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        CarrierBean bean = factory.createCarrierBean();
        Carrier carrier = bean.getCarrierByID(carrierID);
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

}
