/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestSubscriberBean.java,v 1.12 2008/06/16 10:11:14 zhao Exp $
 * $Revision: 1.12 $
 * $Date: 2008/06/16 10:11:14 $
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
import com.npower.dm.core.DMException;
import com.npower.dm.core.ServiceProvider;
import com.npower.dm.core.Subscriber;

/**
 * @author Zhao DongLu
 * 
 */
public class TestSubscriberBean extends TestCase {

  //private static Log log = LogFactory.getLog(TestProfileConfigBean.class);


  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID = "Test.Carrier";

  /**
   * ServiceProviderEntity External ID
   */
  private String Service_Provider_External_ID = "Test.SP";

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        
        this.setUpCarriers(factory);
        this.setUpServiceProviders(factory);
    } catch (RuntimeException e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        tearDownCarriers(factory);
    } catch (RuntimeException e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  public void setUpCarriers(ManagementBeanFactory factory) throws Exception {
    try {
        CarrierBean carrierBean = factory.createCarrierBean();
        
        Country country = factory.createCountryBean().getCountryByISOCode("CN");
        assertNotNull(country);
        assertEquals(country.getCountryCode(), "86");
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
        if (carrier == null) {
          carrier = carrierBean.newCarrierInstance(country, Carrier_External_ID, Carrier_External_ID);
          factory.beginTransaction();
          carrierBean.update(carrier);
          factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    }
  }

  public void setUpServiceProviders(ManagementBeanFactory factory) throws Exception {
    try {
        ServiceProviderBean bean = factory.createServiceProviderBean();
        
        ServiceProvider sp = bean.getServiceProviderByExternalID(Service_Provider_External_ID);
        if (sp == null) {
          sp = bean.newServiceProviderInstance(Service_Provider_External_ID, Service_Provider_External_ID);
          factory.beginTransaction();
          bean.update(sp);
          factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    }
  }

  public void tearDownCarriers(ManagementBeanFactory factory) {
  }

  /**
   * Test
   * @throws Exception
   */
  public void testBasicMethods() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        SubscriberBean bean = factory.createSubcriberBean();
        CarrierBean carrierBean = factory.createCarrierBean();
    
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
        assertNotNull(carrier);
  
        String externalID = "Zhao DongLu";
        String phoneNumber = "13801356729";
        String password = "123456!@#";
  
        List<Subscriber> list = bean.findSubscriber("from SubscriberEntity where externalId='" + externalID + "'");
        for (int i = 0; i < list.size(); i++) {
          Subscriber s = (Subscriber) list.get(i);
          factory.beginTransaction();
          bean.delete(s);
          factory.commit();
        }
  
        Subscriber subscriber = bean.newSubscriberInstance(carrier, externalID, phoneNumber, password);
  
        factory.beginTransaction();
        bean.update(subscriber);
        factory.commit();
  
        factory.beginTransaction();
        subscriber.setNewCarrier(carrier);
        bean.update(subscriber);
        factory.commit();
  
        // Test found By ID
        subscriber = bean.getSubscriberByID("" + subscriber.getID());
        assertNotNull(subscriber);
        assertEquals(carrier.getName(), subscriber.getCarrier().getName());
        assertEquals(password, subscriber.getPassword());
        assertEquals(carrier.getName(), subscriber.getNewCarrier().getName());
        assertEquals(password, subscriber.getNewPassword());
        assertEquals(externalID, subscriber.getExternalId());
        assertEquals(phoneNumber, subscriber.getPhoneNumber());
        assertEquals(Subscriber.SMS_STATE_NO_NEED_NOTIFICATION, subscriber.getSMSState());
        assertNull(subscriber.getServiceProvider());
  
        // Test found By externalID
        subscriber = bean.getSubscriberByExternalID(subscriber.getExternalId());
        assertNotNull(subscriber);
        assertEquals(carrier.getName(), subscriber.getCarrier().getName());
        assertEquals(password, subscriber.getPassword());
        assertEquals(carrier.getName(), subscriber.getNewCarrier().getName());
        assertEquals(password, subscriber.getNewPassword());
        assertEquals(externalID, subscriber.getExternalId());
        assertEquals(phoneNumber, subscriber.getPhoneNumber());
        assertEquals(Subscriber.SMS_STATE_NO_NEED_NOTIFICATION, subscriber.getSMSState());
        assertNull(subscriber.getServiceProvider());
  
        // Test found By phoneNumber
        subscriber = bean.getSubscriberByPhoneNumber(subscriber.getPhoneNumber());
        assertNotNull(subscriber);
        assertEquals(carrier.getName(), subscriber.getCarrier().getName());
        assertEquals(password, subscriber.getPassword());
        assertEquals(carrier.getName(), subscriber.getNewCarrier().getName());
        assertEquals(password, subscriber.getNewPassword());
        assertEquals(externalID, subscriber.getExternalId());
        assertEquals(phoneNumber, subscriber.getPhoneNumber());
        assertEquals(Subscriber.SMS_STATE_NO_NEED_NOTIFICATION, subscriber.getSMSState());
        assertNull(subscriber.getServiceProvider());
  
        // Test delete
        factory.beginTransaction();
        bean.delete(subscriber);
        factory.commit();
  
        subscriber = bean.getSubscriberByID("" + subscriber.getID());
        assertNull(subscriber);
  
    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testBasicMethods4ServiceProvider() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        SubscriberBean bean = factory.createSubcriberBean();
        CarrierBean carrierBean = factory.createCarrierBean();
        ServiceProviderBean spBean = factory.createServiceProviderBean();
    
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
        assertNotNull(carrier);
        
        ServiceProvider sp = spBean.getServiceProviderByExternalID(Service_Provider_External_ID);
        assertNotNull(sp);
  
        String externalID = "Zhao DongLu";
        String phoneNumber = "13801356729";
        String password = "123456!@#";
  
        List<Subscriber> list = bean.findSubscriber("from SubscriberEntity where externalId='" + externalID + "'");
        for (int i = 0; i < list.size(); i++) {
          Subscriber s = (Subscriber) list.get(i);
          factory.beginTransaction();
          bean.delete(s);
          factory.commit();
        }
  
        Subscriber subscriber = bean.newSubscriberInstance(carrier, externalID, phoneNumber, password);
        subscriber.setServiceProvider(sp);
        
        factory.beginTransaction();
        bean.update(subscriber);
        factory.commit();
  
        factory.beginTransaction();
        subscriber.setNewCarrier(carrier);
        bean.update(subscriber);
        factory.commit();
  
        // Test found By ID
        subscriber = bean.getSubscriberByID("" + subscriber.getID());
        assertNotNull(subscriber);
        assertEquals(carrier.getName(), subscriber.getCarrier().getName());
        assertEquals(password, subscriber.getPassword());
        assertEquals(carrier.getName(), subscriber.getNewCarrier().getName());
        assertEquals(password, subscriber.getNewPassword());
        assertEquals(externalID, subscriber.getExternalId());
        assertEquals(phoneNumber, subscriber.getPhoneNumber());
        assertEquals(Subscriber.SMS_STATE_NO_NEED_NOTIFICATION, subscriber.getSMSState());
        assertNotNull(subscriber.getServiceProvider());
        assertEquals(Service_Provider_External_ID, subscriber.getServiceProvider().getExternalID());
  
        // Test found By externalID
        subscriber = bean.getSubscriberByExternalID(subscriber.getExternalId());
        assertNotNull(subscriber);
        assertEquals(carrier.getName(), subscriber.getCarrier().getName());
        assertEquals(password, subscriber.getPassword());
        assertEquals(carrier.getName(), subscriber.getNewCarrier().getName());
        assertEquals(password, subscriber.getNewPassword());
        assertEquals(externalID, subscriber.getExternalId());
        assertEquals(phoneNumber, subscriber.getPhoneNumber());
        assertEquals(Subscriber.SMS_STATE_NO_NEED_NOTIFICATION, subscriber.getSMSState());
        assertNotNull(subscriber.getServiceProvider());
        assertEquals(Service_Provider_External_ID, subscriber.getServiceProvider().getExternalID());
  
        // Test found By phoneNumber
        subscriber = bean.getSubscriberByPhoneNumber(subscriber.getPhoneNumber());
        assertNotNull(subscriber);
        assertEquals(carrier.getName(), subscriber.getCarrier().getName());
        assertEquals(password, subscriber.getPassword());
        assertEquals(carrier.getName(), subscriber.getNewCarrier().getName());
        assertEquals(password, subscriber.getNewPassword());
        assertEquals(externalID, subscriber.getExternalId());
        assertEquals(phoneNumber, subscriber.getPhoneNumber());
        assertEquals(Subscriber.SMS_STATE_NO_NEED_NOTIFICATION, subscriber.getSMSState());
        assertNotNull(subscriber.getServiceProvider());
        assertEquals(Service_Provider_External_ID, subscriber.getServiceProvider().getExternalID());
  
        // Test delete
        factory.beginTransaction();
        bean.delete(subscriber);
        factory.commit();
  
        subscriber = bean.getSubscriberByID("" + subscriber.getID());
        assertNull(subscriber);
  
    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestSubscriberBean.class);
  }

}
