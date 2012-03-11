/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestDeviceBeanBind.java,v 1.4 2008/08/06 07:59:23 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/08/06 07:59:23 $
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

import org.apache.commons.lang.StringUtils;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;

/**
 * @author Zhao DongLu
 * 
 */
public class TestDeviceBeanBind extends TestCase {

  //private static Log log = LogFactory.getLog(TestProfileConfigBean.class);

  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID_1 = "Test.Carrier.1";
  private String Carrier_External_ID_1_Phone_Policy = "138";

  private String Carrier_External_ID_2 = "Test.Carrier.2";
  private String Carrier_External_ID_2_Phone_Policy = "130";

  private static final String MANUFACTURER_External_ID_1 = "TEST.MANUFACTUER.1";

  private static final String MANUFACTURER_External_ID_2 = "TEST.MANUFACTUER.2";

  private static final String MANUFACTURER_External_ID_3 = "TEST.MANUFACTUER.3";

  private static final String MODEL_External_ID_1 = "TEST.MODEL.1";

  private static final String MODEL_External_ID_2 = "TEST.MODEL.2";

  private static final String MODEL_External_ID_3 = "TEST.MODEL.3";

  // device#2
  private static final String subscriberExternalID_1 = "13001351111";
  private static final String subscriberPhoneNumber_1 = "13001351111";
  private static final String subscriberPassword_1 = "12345678";
  private static final String deviceExternalID_1 = "IMEI:12345678904321";

  // device#2
  private static final String subscriberExternalID_2 = "13801352222";
  private static final String subscriberPhoneNumber_2 = "13801352222";
  private static final String subscriberPassword_2 = "87654321";
  private static final String deviceExternalID_2 = "IMEI:09876543214321";

  // device#3
  //private static final String subscriberExternalID_3 = "13801353333";
  private static final String subscriberPhoneNumber_3 = "13801353333";
  //private static final String subscriberPassword_3 = "87658765";
  private static final String deviceExternalID_3 = "IMEI:1122334455667788";

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    // Clear TAC info
    this.cleanUpCarriers();
    
    this.setUpCarrier(Carrier_External_ID_1, Carrier_External_ID_1_Phone_Policy);
    this.setUpCarrier(Carrier_External_ID_2, Carrier_External_ID_2_Phone_Policy);
    this.setupModel(MANUFACTURER_External_ID_1, MODEL_External_ID_1, null);
    this.setupModel(MANUFACTURER_External_ID_2, MODEL_External_ID_2, null);
    this.setupModel(MANUFACTURER_External_ID_3, MODEL_External_ID_3, deviceExternalID_3);
    
    this.setupDevices();
  }

  /*
   * @see TestCase#tearDown()
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
  
  public void setUpCarrier(String carrierID, String phonePolicy) throws Exception {
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
        carrier.setPhoneNumberPolicy(phonePolicy);
        carrierBean.update(carrier);
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  private void setupModel(String manufacturerExternalID, String modelExternalID, String tacInfo) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    ModelBean modelBean = factory.createModelBean();

    try {
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer != null) {
           factory.beginTransaction();
           modelBean.delete(manufacturer);
           factory.commit();
        }
        manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, manufacturerExternalID);
        factory.beginTransaction();
        modelBean.update(manufacturer);
        factory.commit();
  
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        if (model != null) {
           factory.beginTransaction();
           modelBean.delete(model);
           factory.commit();
        }
        factory.beginTransaction();
        model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
        modelBean.update(model);
        if (StringUtils.isNotEmpty(tacInfo)) {
           modelBean.addTACInfo(model, tacInfo);
        }
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  protected void setupDevices() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        DeviceBean bean = factory.createDeviceBean();
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        CarrierBean carrierBean = factory.createCarrierBean();
        ModelBean modelBean = factory.createModelBean();
        
        Carrier carrier_1 = carrierBean.getCarrierByExternalID(Carrier_External_ID_1);
        Carrier carrier_2 = carrierBean.getCarrierByExternalID(Carrier_External_ID_2);
        Manufacturer manufacturer_1 = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID_1);
        Manufacturer manufacturer_2 = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID_2);
        Model model_1 = modelBean.getModelByManufacturerModelID(manufacturer_1, MODEL_External_ID_1);
        Model model_2= modelBean.getModelByManufacturerModelID(manufacturer_2, MODEL_External_ID_2);
    
        factory.beginTransaction();
        // Setup device#1
        Subscriber subscriber_1 = subscriberBean.newSubscriberInstance(carrier_1, subscriberExternalID_1, subscriberPhoneNumber_1, subscriberPassword_1);
        subscriberBean.update(subscriber_1);
        Device device_1 = bean.newDeviceInstance(subscriber_1, model_1, deviceExternalID_1);
        bean.update(device_1);
    
        // Setup device#1
        Subscriber subscriber_2 = subscriberBean.newSubscriberInstance(carrier_2, subscriberExternalID_2, subscriberPhoneNumber_2, subscriberPassword_2);
        subscriberBean.update(subscriber_2);
        Device device_2 = bean.newDeviceInstance(subscriber_2, model_2, deviceExternalID_2);
        bean.update(device_2);
        
        factory.commit();
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
      factory.release();
    }
  }

  /**
   *  Case#1: 设备存在,无变化,不做任何操作
   * @throws Exception
   */
  public void testBindCase1() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();

    try {
        Subscriber subscriber_1 = subscriberBean.getSubscriberByExternalID(subscriberExternalID_1);
        assertNotNull(subscriber_1);
        Device device_1 = bean.getDeviceByExternalID(deviceExternalID_1);
        assertNotNull(device_1);
  
        Subscriber subscriber_2 = subscriberBean.getSubscriberByExternalID(subscriberExternalID_2);
        assertNotNull(subscriber_2);
        Device device_2 = bean.getDeviceByExternalID(deviceExternalID_2);
        assertNotNull(device_2);
        
        {
        factory.beginTransaction();
        Device device = bean.bind(deviceExternalID_1, subscriberPhoneNumber_1);
        factory.commit();
        
        assertNotNull(device);
        assertEquals(device_1.getID(), device.getID());
        assertEquals(subscriber_1.getID(), device.getSubscriber().getID());
        }
        
        {
        factory.beginTransaction();
        Device device = bean.bind(deviceExternalID_2, subscriberPhoneNumber_2);
        factory.commit();
        
        assertNotNull(device);
        assertEquals(device_2.getID(), device.getID());
        assertEquals(subscriber_2.getID(), device.getSubscriber().getID());
        }
        
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  

  /**
   *  Case#2: msisdn和ExternalID对应与不同的设备，均存在
   * @throws Exception
   */
  public void testBindCase2_1() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();

    try {
        Subscriber subscriber_1 = subscriberBean.getSubscriberByExternalID(subscriberExternalID_1);
        assertNotNull(subscriber_1);
        Device device_1 = bean.getDeviceByExternalID(deviceExternalID_1);
        assertNotNull(device_1);
  
        Subscriber subscriber_2 = subscriberBean.getSubscriberByExternalID(subscriberExternalID_2);
        assertNotNull(subscriber_2);
        Device device_2 = bean.getDeviceByExternalID(deviceExternalID_2);
        assertNotNull(device_2);
        
        factory.beginTransaction();
        Device device = bean.bind(deviceExternalID_2, subscriberPhoneNumber_1);
        factory.commit();
        
        assertNotNull(device);
        assertEquals(device_2.getExternalId(), device.getExternalId());
        assertEquals(subscriber_1.getExternalId(), device.getSubscriber().getExternalId());
        assertEquals(Carrier_External_ID_1, device.getSubscriber().getCarrier().getExternalID());
        
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  /**
   *  Case#2: msisdn和ExternalID对应与不同的设备，均存在
   * @throws Exception
   */
  public void testBindCase2_2() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();

    try {
        Subscriber subscriber_1 = subscriberBean.getSubscriberByExternalID(subscriberExternalID_1);
        assertNotNull(subscriber_1);
        Device device_1 = bean.getDeviceByExternalID(deviceExternalID_1);
        assertNotNull(device_1);
  
        Subscriber subscriber_2 = subscriberBean.getSubscriberByExternalID(subscriberExternalID_2);
        assertNotNull(subscriber_2);
        Device device_2 = bean.getDeviceByExternalID(deviceExternalID_2);
        assertNotNull(device_2);

        factory.beginTransaction();
        Device device = bean.bind(deviceExternalID_2, subscriberPhoneNumber_1);
        factory.commit();
        
        assertNotNull(device);
        assertEquals(device_2.getExternalId(), device.getExternalId());
        assertEquals(subscriber_1.getExternalId(), device.getSubscriber().getExternalId());
        assertEquals(Carrier_External_ID_1, device.getSubscriber().getCarrier().getExternalID());
        
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   *  Case#3: ExternalID 对应的设备存在,但msisdn发生变化,修改存在设备的msisdn
   * @throws Exception
   */
  public void testBindCase3() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();

    try {
        Subscriber subscriber_1 = subscriberBean.getSubscriberByExternalID(subscriberExternalID_1);
        assertNotNull(subscriber_1);
        Device device_1 = bean.getDeviceByExternalID(deviceExternalID_1);
        assertNotNull(device_1);
  
        Subscriber subscriber_2 = subscriberBean.getSubscriberByExternalID(subscriberExternalID_2);
        assertNotNull(subscriber_2);
        Device device_2 = bean.getDeviceByExternalID(deviceExternalID_2);
        assertNotNull(device_2);

        String newPhonenumber = subscriberPhoneNumber_3;
        
        factory.beginTransaction();
        Device device = bean.bind(deviceExternalID_1, newPhonenumber);
        factory.commit();
        
        assertNotNull(device);
        assertEquals(device_1.getExternalId(), device.getExternalId());
        assertEquals(newPhonenumber, device.getSubscriber().getPhoneNumber());
        assertEquals(newPhonenumber, device.getSubscriberPhoneNumber());
        assertEquals(Carrier_External_ID_1, device.getSubscriber().getCarrier().getExternalID());
        
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   *  Case#4: msisdn 对应的设备存在, 但该设备的externalID发生变化, 修改存在设备的externalID
   * @throws Exception
   */
  public void testBindCase4() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();

    try {
        Subscriber subscriber_1 = subscriberBean.getSubscriberByExternalID(subscriberExternalID_1);
        assertNotNull(subscriber_1);
        Device device_1 = bean.getDeviceByExternalID(deviceExternalID_1);
        assertNotNull(device_1);
  
        Subscriber subscriber_2 = subscriberBean.getSubscriberByExternalID(subscriberExternalID_2);
        assertNotNull(subscriber_2);
        Device device_2 = bean.getDeviceByExternalID(deviceExternalID_2);
        assertNotNull(device_2);

        String newExternalID = deviceExternalID_3;

        factory.beginTransaction();
        Device device = bean.bind(newExternalID, subscriberPhoneNumber_1);
        factory.commit();
        
        assertNotNull(device);
        assertEquals(newExternalID, device.getExternalId());
        assertEquals(device_1.getSubscriber().getPhoneNumber(), device.getSubscriber().getPhoneNumber());
        assertEquals(device_1.getSubscriber().getPhoneNumber(), device.getSubscriberPhoneNumber());
        assertEquals(MODEL_External_ID_3, device.getModel().getManufacturerModelId());
        assertEquals(Carrier_External_ID_1, device.getSubscriber().getCarrier().getExternalID());
        
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   *  Case#5: 均不存在
   * @throws Exception
   */
  public void testBindCase5() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    try {
        String newExternalID = "IMEI:112233442211779";
        String phoneNumber = "13844556677";
        Device device = bean.bind(newExternalID, phoneNumber);
        assertNull(device);
        
        //assertNotNull(device);
        //assertEquals(newExternalID, device.getExternalId());
        //assertEquals(phoneNumber, device.getSubscriber().getPhoneNumber());
        
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestDeviceBeanBind.class);
  }

}
