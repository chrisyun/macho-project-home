/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/TestSoftwareManagementJobAdapter.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.4 $
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
package com.npower.dm.msm;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import sync4j.framework.engine.dm.ManagementProcessor;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelCharacter;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.management.SubscriberBean;

/**
 * Test FumoProcessor
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestSoftwareManagementJobAdapter extends TestCase {

  private static final String BASE_DIR = AllTests.BASE_DIR;
  private static final String FILENAME_TEST_NOKIA_DDF = BASE_DIR + "/metadata/setup/ddf/nokia/s60.3rd/AM_DDF.xml";

  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER";
  
  /**
   * Model for FOTA DM Mode
   */
  private static final String MODEL_EXT_ID_FOTA_DM_MODE = "TEST.MODEL.FOTA_DM_MODE";
  /**
   * Model for FOTA Download Mode
   */
  private static final String MODEL_EXT_ID_FOTA_DL_MODE = "TEST.MODEL.FOTA_DL_MODE";

  /**
   * 
   */
  private static final String CLIENT_PASSWORD = "otasdm";
  /**
   * 
   */
  private static final String CLIENT_USERNAME = "otasdm";

  private String serverPassword = "otasdm";
  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID = "Test.Carrier";

  /**
   * DeviceEntity IMEI, ExternalID,  FOTA DM mode
   */
  private String DEVICE_EXT_ID_FOTA_DM_MODE = "IMEI:353755000569915";
  /**
   * DeviceEntity IMEI, ExternalID,  FOTA Download mode
   */
  private String DEVICE_EXT_ID_FOTA_DL_MODE = "IMEI:353755000569916";

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    // Clear all
    this.tearDown();
        
    this.setupManufacturer(MANUFACTURER_External_ID);
    // Setup 2 models for Download and DM mode.
    this.setupModel(MANUFACTURER_External_ID, MODEL_EXT_ID_FOTA_DM_MODE, false);
    this.setupModel(MANUFACTURER_External_ID, MODEL_EXT_ID_FOTA_DL_MODE, true);

    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_EXT_ID_FOTA_DM_MODE, FILENAME_TEST_NOKIA_DDF);
    
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_EXT_ID_FOTA_DL_MODE, FILENAME_TEST_NOKIA_DDF);
    
    this.setUpCarrier(Carrier_External_ID);
    
    this.setupDevice(Carrier_External_ID, MANUFACTURER_External_ID, MODEL_EXT_ID_FOTA_DM_MODE, DEVICE_EXT_ID_FOTA_DM_MODE);
    this.setupDevice(Carrier_External_ID, MANUFACTURER_External_ID, MODEL_EXT_ID_FOTA_DL_MODE, DEVICE_EXT_ID_FOTA_DL_MODE);
  }
  
  private void setupManufacturer(String manufacturerExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer == null) {
           manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, manufacturerExternalID);
           factory.beginTransaction();
           modelBean.update(manufacturer);
           factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

  private void setupDDFTree(String manufacturerExternalID, String modelExternalID, String DDFFilename) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        DDFTreeBean treeBean = factory.createDDFTreeBean();

        InputStream in = new FileInputStream(new File(DDFFilename));
        
        factory.beginTransaction();
        DDFTree tree = treeBean.parseDDFTree(in);
        Set<DDFNode> nodes = tree.getRootDDFNodes();
        assertEquals(nodes.size(), 1);
        in.close();

        treeBean.addDDFTree(tree);
        modelBean.attachDDFTree(model, tree.getID());
        
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  private void setupModel(String manufacturerExternalID, String modelExternalID, boolean supportDownloadMethod) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        assertNotNull(manufacturer);
        
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        if (model != null) {
           factory.beginTransaction();
           modelBean.delete(model);
           factory.commit();
        }
        
        factory.beginTransaction();
        model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
        factory.beginTransaction();
        model.setSupportedDownloadMethods(supportDownloadMethod);
        model.setFirmwareVersionNode("./DevDetail/SwV");
        model.setFirmwareDownloadNode("./FUMO/OTASDL/Download");
        model.setFirmwareUpdateNode("./FUMO/OTASDL/Update");
        model.setFirmwareDownloadAndUpdateNode("./FUMO/OTASDL/DownloadAndUpdate");
        modelBean.update(model);
        factory.commit();
        
        // Set up model spec: Nokia S60 3rd
        factory.beginTransaction();
        ModelCharacter character = modelBean.newModelCharacterInstance(model, "general", "developer.platform");
        character.setValue("S60 3rd Edition");
        modelBean.update(character);
        factory.commit();
        
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }
  
  private void setUpCarrier(String carrierExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        CarrierBean carrierBean = factory.createCarrierBean();
        Country country = factory.createCountryBean().getCountryByISOCode("CN");
        assertNotNull(country);
        assertEquals(country.getCountryCode(), "86");
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
        if (carrier == null) {
           carrier = carrierBean.newCarrierInstance(country, carrierExternalID, carrierExternalID);
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
  
  private void setupDevice(String carrierExternalID, String manufacturerExternalID, String modelExternalID, String deviceExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      CarrierBean carrierBean = factory.createCarrierBean();
      ModelBean modelBean = factory.createModelBean();
      SubscriberBean subscriberBean = factory.createSubcriberBean();
      
      Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
      assertNotNull(carrier);

      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
      assertNotNull(manufacturer);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
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

      DeviceBean deviceBean = factory.createDeviceBean();
      Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
      if (device == null) {
        // Create a DeviceEntity
        device = deviceBean.newDeviceInstance(subscriber, model, deviceExternalID);
        device.setOMAClientUsername(CLIENT_USERNAME);
        device.setOMAClientPassword(CLIENT_PASSWORD);
        device.setOMAServerPassword(serverPassword);
        factory.beginTransaction();
        deviceBean.update(device);
        factory.commit();
      }

      // Test found
      device = deviceBean.getDeviceByID("" + device.getID());
      assertNotNull(device);

      assertEquals(deviceExternalID, device.getExternalId());

    } catch (DMException e) {
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
    this.tearDownManufacturer(MANUFACTURER_External_ID);
    // Remove all of softwares
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        factory.beginTransaction();
        SoftwareBean bean = factory.createSoftwareBean();
        for (SoftwareVendor vendor: bean.getAllOfVendors()) {
            bean.delete(vendor);
        }
        factory.commit();
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  private void tearDownManufacturer(String manufacturerExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean modelBean = factory.createModelBean();
    Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
    if (manufacturer != null) {
       factory.beginTransaction();
       modelBean.delete(manufacturer);
       factory.commit();
    }
    factory.release();
    
  }


  /**
   * Test 
   * @throws Exception
   */
  public void testGetProcessor4Nokia_S60_3rd_Installation() throws Exception {
    assertTrue(true);
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(DEVICE_EXT_ID_FOTA_DM_MODE);
        SoftwareManagementJobAdapter adapter = new SoftwareManagementJobAdapterImpl(factory);
        ManagementProcessor processor = adapter.getProcessor(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL, device.getExternalId());
        assertNotNull(processor);
        assertTrue(processor instanceof S60V3InstallProcessor4DL);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

  }


}
