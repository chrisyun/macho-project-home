/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestDeviceBean.java,v 1.15 2008/06/16 10:11:14 zhao Exp $
 * $Revision: 1.15 $
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
import java.util.Set;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;

/**
 * @author Zhao DongLu
 * 
 */
public class TestDeviceBean extends TestCase {

  //private static Log log = LogFactory.getLog(TestProfileConfigBean.class);

  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID = "Test.Carrier";

  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER";

  private static final String MODEL_External_ID = "TEST.MODEL";

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    this.setUpCarrier(Carrier_External_ID);
    this.setupModel(MANUFACTURER_External_ID, MODEL_External_ID);
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();

  }

  public void setUpCarrier(String carrierID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    CarrierBean carrierBean = factory.createCarrierBean();
    try {
      Country country = factory.createCountryBean().getCountryByISOCode("CN");
      assertNotNull(country);
      assertEquals(country.getCountryCode(), "86");
      Carrier carrier = carrierBean.getCarrierByExternalID(carrierID);
      if (carrier == null) {
        carrier = carrierBean.newCarrierInstance(country, carrierID, carrierID);
        factory.beginTransaction();
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

  private void setupModel(String manufacturerExternalID, String modelExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    ModelBean modelBean = factory.createModelBean();

    try {
        factory.beginTransaction();
        
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer == null) {
           manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, manufacturerExternalID);
           modelBean.update(manufacturer);
        }
  
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        if (model == null) {
           model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
           modelBean.update(model);
        }
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testBasicMethods() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    ModelBean modelBean = factory.createModelBean();

    try {

        Carrier carrier = carrierBean.getCarrierByExternalID(this.Carrier_External_ID);
        assertNotNull(carrier);
  
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
        assertNotNull(model);
  
        String externalID = "Zhao DongLu";
        String phoneNumber = "13801356729";
        String password = "123456!@#";
  
        List<Subscriber> list = subscriberBean.findSubscriber("from SubscriberEntity where externalId='" + externalID + "'");
        Subscriber subscriber = null;
        if (list.size() == 0) {
           subscriber = subscriberBean.newSubscriberInstance(carrier, externalID, phoneNumber, password);
  
           factory.beginTransaction();
           subscriberBean.update(subscriber);
           factory.commit();
        } else {
          subscriber = (Subscriber) list.get(0);
        }
        assertNotNull(subscriber);
  
        String imei = "1234567890123456";
        Device device = bean.getDeviceByExternalID(imei);
        if (device != null) {
           factory.beginTransaction();
           bean.delete(device);
           factory.commit();
        }
  
        // Create a Device
        device = bean.newDeviceInstance(subscriber, model, imei);
  
        factory.beginTransaction();
        bean.update(device);
        factory.commit();
  
        // Test found
        device = bean.getDeviceByID("" + device.getID());
        assertNotNull(device);
  
        assertEquals(imei, device.getExternalId());
  
        assertEquals(subscriber.getID(), device.getSubscriber().getID());
        assertEquals(subscriber.getAskPermissionOnTrigger(), device.getSubscriber().getAskPermissionOnTrigger());
        assertEquals(subscriber.getAskPermissionOnTrigger(), device.getSubscriberAskPermTrig());
        assertEquals(subscriber.getIsActivated(), device.getSubscriberIsActivated());
        assertEquals(subscriber.getPassword(), device.getSubscriberPassword());
        assertEquals(subscriber.getCarrier().getID(), device.getSubscriberCarrierId());
        assertEquals(subscriber.getCarrier().getName(), device.getSubscriberCarrierName());
        assertEquals(subscriber.getPhoneNumber(), device.getSubscriberPhoneNumber());
        assertEquals(subscriber.getState(), device.getSubscriberState());
  
        assertEquals(model.getID(), device.getModel().getID());
        assertEquals(model.getName(), device.getModelName());
        assertEquals(model.getManufacturer().getID(), device.getManufacturerId());
        assertEquals(model.getManufacturer().getName(), device.getManufacturerName());
  
        // Test delete
        factory.beginTransaction();
        bean.delete(device);
        factory.commit();
  
        device = bean.getDeviceByID("" + device.getID());
        assertNull(device);

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  public void testDeviceGroupBasicMethods() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    ModelBean modelBean = factory.createModelBean();
    try {
        Carrier carrier = carrierBean.getCarrierByExternalID(this.Carrier_External_ID);
        assertNotNull(carrier);
  
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
        assertNotNull(model);
  
        String externalID = "Zhao DongLu";
        String phoneNumber = "13801356729";
        String password = "123456!@#";
  
        List<Subscriber> list = subscriberBean.findSubscriber("from SubscriberEntity where externalId='" + externalID + "'");
        Subscriber subscriber = null;
        if (list.size() == 0) {
           subscriber = subscriberBean.newSubscriberInstance(carrier, externalID, phoneNumber, password);
  
           factory.beginTransaction();
           subscriberBean.update(subscriber);
           factory.commit();
        } else {
          subscriber = (Subscriber) list.get(0);
        }
        assertNotNull(subscriber);
  
        // Test case 
        DeviceGroup group = bean.newDeviceGroup();
        String imei_prefix = "123456789012345";
        int totalDevice = 10;
        for (int i = 0; i < totalDevice; i++ ) {
            String imei = imei_prefix + i;
            Device device = bean.getDeviceByExternalID(imei);
            if (device != null) {
               factory.beginTransaction();
               bean.delete(device);
               factory.commit();
            }
      
            // Create a Device
            device = bean.newDeviceInstance(subscriber, model, imei);
      
            factory.beginTransaction();
            bean.update(device);
            factory.commit();
            
            factory.beginTransaction();
            bean.add(group, device);
            factory.commit();
      
            // Test case for Device.getDeviceGroups()
            assertTrue(device.getDeviceGroups().contains(group));
            
            // Test found
            device = bean.getDeviceByID("" + device.getID());
            assertNotNull(device);
            
      
            assertEquals(imei, device.getExternalId());
      
            assertEquals(subscriber.getID(), device.getSubscriber().getID());
      
            assertEquals(model.getID(), device.getModel().getID());
        }
        
        // Test DeviceGroup.add()
        Set<Device> devices = group.getDevices();
        assertEquals(totalDevice, devices.size());
        
        // Test DeviceBean.getDeviceGroupByID()
        group = bean.getDeviceGroupByID("" + group.getID());
        assertNotNull(group);
        
        // Test DeviceGroup.remove()
        factory.beginTransaction();
        for (int i = 0; i < totalDevice; i++ ) {
            String imei = imei_prefix + i;
            Device device = bean.getDeviceByExternalID(imei);
            assertNotNull(device);
            bean.remove(group, device);
        }
        factory.commit();
        
        devices = group.getDevices();
        assertTrue(devices.isEmpty());

        factory.beginTransaction();
        for (int i = 0; i < 10; i++ ) {
            String imei = imei_prefix + i;
            Device device = bean.getDeviceByExternalID(imei);
            bean.delete(device);
        }
        factory.commit();
  
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testDeleteDeviceGroupMethod() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    ModelBean modelBean = factory.createModelBean();
    try {
        Carrier carrier = carrierBean.getCarrierByExternalID(this.Carrier_External_ID);
        assertNotNull(carrier);
  
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
        assertNotNull(model);
  
        String externalID = "Zhao DongLu";
        String phoneNumber = "13801356729";
        String password = "123456!@#";
  
        List<Subscriber> list = subscriberBean.findSubscriber("from SubscriberEntity where externalId='" + externalID + "'");
        Subscriber subscriber = null;
        if (list.size() == 0) {
           subscriber = subscriberBean.newSubscriberInstance(carrier, externalID, phoneNumber, password);
  
           factory.beginTransaction();
           subscriberBean.update(subscriber);
           factory.commit();
        } else {
          subscriber = (Subscriber) list.get(0);
        }
        assertNotNull(subscriber);
  
        // Test case 
        DeviceGroup group = bean.newDeviceGroup();
        String imei_prefix = "123456789012345";
        int totalDevice = 10;
        for (int i = 0; i < totalDevice; i++ ) {
            String imei = imei_prefix + i;
            Device device = bean.getDeviceByExternalID(imei);
            if (device != null) {
               factory.beginTransaction();
               bean.delete(device);
               factory.commit();
            }
      
            // Create a Device
            device = bean.newDeviceInstance(subscriber, model, imei);
      
            factory.beginTransaction();
            bean.update(device);
            factory.commit();
            
            factory.beginTransaction();
            bean.add(group, device);
            factory.commit();
      
            // Test found
            device = bean.getDeviceByID("" + device.getID());
            assertNotNull(device);
      
            assertEquals(imei, device.getExternalId());
      
            assertEquals(subscriber.getID(), device.getSubscriber().getID());
      
            assertEquals(model.getID(), device.getModel().getID());
        }
        
        // Test DeviceGroup.add()
        Set<Device> devices = group.getDevices();
        assertEquals(totalDevice, devices.size());
        
        // Test DeviceBean.getDeviceGroupByID()
        group = bean.getDeviceGroupByID("" + group.getID());
        assertNotNull(group);
        
        // Test DeviceBean.delete(DeviceGroup)
        factory.beginTransaction();
        bean.delete(group);
        factory.commit();
        
        group = bean.getDeviceGroupByID("" + group.getID());
        assertNull(group);

        factory.beginTransaction();
        for (int i = 0; i < 10; i++ ) {
            String imei = imei_prefix + i;
            Device device = bean.getDeviceByExternalID(imei);
            bean.delete(device);
        }
        factory.commit();
  
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestDeviceBean.class);
  }

}
