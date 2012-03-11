/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/store/TestDevicePersistentStore.java,v 1.9 2008/06/16 10:11:14 zhao Exp $
  * $Revision: 1.9 $
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
package com.npower.dm.server.store;

import junit.framework.TestCase;
import sync4j.framework.security.Sync4jPrincipal;
import sync4j.framework.server.Sync4jDevice;
import sync4j.framework.server.store.PersistentStoreException;
import sync4j.framework.tools.Base64;
import sync4j.framework.tools.MD5;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.CountryBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SubscriberBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/06/16 10:11:14 $
 */
public class TestDevicePersistentStore extends TestCase {
  
  /**
   * 
   */
  private static final String CLIENT_PASSWORD = "client.password";
  /**
   * 
   */
  private static final String CLIENT_USERNAME = "client.username";
  
  private String manufacturerExternalID = "W32 Device Manufacturer";
  private String modelExternalID = "W32 Device Model";
  private String deviceExternalID = "w32dm";
  private String carrierExternalID = "W32 Carrier";

  
  private String serverPassword = "srvpwd";
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    try {
        // clear all
        this.tearDownEntities();
    
        setupEntities();
        
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @throws DMException
   */
  private void setupEntities() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, null);
        factory.beginTransaction();
        modelBean.update(manufacturer);
        factory.commit();
        
        factory.beginTransaction();
        Model model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
        modelBean.update(model);
        factory.commit();
        
        CountryBean countryBean = factory.createCountryBean();
        Country country = countryBean.getCountryByISOCode("CN");
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.newCarrierInstance(country, carrierExternalID, carrierExternalID);
        factory.beginTransaction();
        carrierBean.update(carrier);
        factory.commit();
        
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        Subscriber subscriber = subscriberBean.newSubscriberInstance(carrier, deviceExternalID, "13801356799", "password");
        factory.beginTransaction();
        subscriberBean.update(subscriber);
        factory.commit();
        
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.newDeviceInstance(subscriber, model, deviceExternalID);
        device.setOMAClientUsername(TestDevicePersistentStore.CLIENT_USERNAME);
        device.setOMAClientPassword(TestDevicePersistentStore.CLIENT_PASSWORD);
        device.setOMAServerPassword(serverPassword);
        factory.beginTransaction();
        deviceBean.update(device);
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
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
    this.tearDownEntities();
  }
  
  private void tearDownEntities() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        if (device != null) {
          factory.beginTransaction();
          deviceBean.delete(device);
          factory.commit();
        }
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
        if (carrier != null) {
          factory.beginTransaction();
          carrierBean.delete(carrier);
          factory.commit();
        }
        
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer != null) {
          factory.beginTransaction();
          modelBean.delete(manufacturer);
          factory.commit();
        }
    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

  /*
   * Test method for 'com.npower.dm.server.store.DevicePersistentStore.store(Object)'
   */
  public void testStore4Sync4jDevice() throws Exception {
    try {
        DevicePersistentStore persistentStore = new DevicePersistentStore();
        //persistentStore.setAutoRegUnknownDevice(false);
        
        byte[] clientNonce = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
        byte[] serverNonce = {0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01};
        {
          Sync4jDevice device = new Sync4jDevice();
          device.setDeviceId(deviceExternalID);
          device.setClientNonce(clientNonce);
          device.setServerNonce(serverNonce);
          device.setServerPassword(serverPassword);
          
          persistentStore.store(device);
          assertEquals(device.getDeviceId(), deviceExternalID);
          assertEquals(device.getServerPassword(), serverPassword);
        }
        
        {
          Sync4jDevice device = new Sync4jDevice();
          device.setDeviceId(deviceExternalID);
          persistentStore.read(device);
          assertEquals(device.getDeviceId(), deviceExternalID);
          assertEquals(device.getServerPassword(), serverPassword);
          assertEquals(device.getClientNonce()[0], clientNonce[0]);
          assertEquals(device.getServerNonce()[0], serverNonce[0]);
          String digest = new String(Base64.encode(MD5.digest( (CLIENT_USERNAME + ":" + CLIENT_PASSWORD).getBytes())));
          assertEquals(device.getDigest(), digest);              
        }
        
    } catch (PersistentStoreException e) {
      e.printStackTrace();
      throw e;
    }
  }

  /*
   * Test method for 'com.npower.dm.server.store.DevicePersistentStore.store(Object)'
   */
  public void testStore4Sync4jPrincipal() throws Exception {
    try {
        DevicePersistentStore persistentStore = new DevicePersistentStore();
        //persistentStore.setAutoRegUnknownDevice(false);
        
        {
          String username = deviceExternalID;
          Sync4jPrincipal principal = new Sync4jPrincipal(username, deviceExternalID);
          principal.setDeviceId(deviceExternalID);
          
          persistentStore.store(principal);
          assertEquals(principal.getDeviceId(), deviceExternalID);
          assertEquals(principal.getUsername(), username);
        }
        
        {
          String username = deviceExternalID;
          Sync4jPrincipal principal = new Sync4jPrincipal(username, deviceExternalID);
          persistentStore.read(principal);
          assertEquals(principal.getDeviceId(), deviceExternalID);
          assertEquals(principal.getUsername(), username);
        }
        
    } catch (PersistentStoreException e) {
      e.printStackTrace();
      throw e;
    }
  }

  /*
   * Test method for 'com.npower.dm.server.store.DevicePersistentStore.delete(Object)'
   */
  public void testDelete() {

  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestDevicePersistentStore.class);
  }

  
}
