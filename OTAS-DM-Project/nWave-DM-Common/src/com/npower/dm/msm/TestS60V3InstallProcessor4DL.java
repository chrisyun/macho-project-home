/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/TestS60V3InstallProcessor4DL.java,v 1.6 2008/09/10 09:59:42 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2008/09/10 09:59:42 $
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import junit.framework.TestCase;
import sync4j.framework.config.Configuration;
import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.security.Sync4jPrincipal;

import com.npower.dm.AllTests;
import com.npower.dm.client.DMClientEmulator;
import com.npower.dm.client.DMClientEmulatorImpl;
import com.npower.dm.client.RegistryImpl;
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
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareLicenseType;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.BaseTestSoftwareBean;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.processor.BaseProcessor;

/**
 * Test FumoProcessor
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/09/10 09:59:42 $
 */
public class TestS60V3InstallProcessor4DL extends TestCase {

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

  private static final String SOFTWARE_VENDOR_EXT_ID = "Vendor#1";
  
  private long softwareID = 0;
  
  private long softwarePackageID = 0;

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    // Clear all
    this.tearDown();
    
    // Setup OTAS Configuration for download.server.uri
    setupConfiguration();
    
    this.setupManufacturer(MANUFACTURER_External_ID);
    // Setup 2 models for Download and DM mode.
    this.setupModel(MANUFACTURER_External_ID, MODEL_EXT_ID_FOTA_DM_MODE, false);
    this.setupModel(MANUFACTURER_External_ID, MODEL_EXT_ID_FOTA_DL_MODE, true);

    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_EXT_ID_FOTA_DM_MODE, FILENAME_TEST_NOKIA_DDF);
    this.setupSoftwares(MANUFACTURER_External_ID, MODEL_EXT_ID_FOTA_DM_MODE);
    
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
        character.setValue("S60 3rd Edition (initial release)");
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

  public void setupSoftwares(String manExtID, String modelExtID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      ModelBean modelBean = factory.createModelBean();

      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manExtID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExtID);

      SoftwareVendor vendor = BaseTestSoftwareBean.setupSoftwareVendor(SOFTWARE_VENDOR_EXT_ID, "Mobile software vendor#1");

      SoftwareCategory category = BaseTestSoftwareBean.setupSoftwareCategory(null, "Category#1", "Mobile software category#1");
      
      // Create software
      SoftwareBean bean = factory.createSoftwareBean();
      factory.beginTransaction();
      Software s1 = bean.newSoftwareInstance(vendor, category, "MSN", "Windows Mobile Messager", "1.0",
                                             SoftwareLicenseType.FREE);
      s1.setDescription("Microsoft Winodws Mobile Messager");
      bean.update(s1);
      this.softwareID = s1.getId();
      factory.commit();

      // Create Software package
      factory.beginTransaction();
      Set<Model> setOfModel = new HashSet<Model>();
      setOfModel.add(model);
      SoftwarePackage pkg = bean.newPackageInstance(s1, null, null, setOfModel, "http://www.software.com/msn.3.1.s60.3rd.sisx");
      String installationOpts = "<InstOpts>" + 
      "<StdOpt name=\"drive\" value=\"!\"/>" + 
      "<StdOpt name=\"lang\" value=\"*\" />" + 
      "<StdOpt name=\"upgrade\" value=\"yes\"/>" + 
      "<StdOpt name=\"kill\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"pkginfo\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"optionals\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"ocsp\" value=\"no\"/>" + 
      "<StdSymOpt name=\"capabilities\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"untrusted\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"ignoreocspwarn\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"ignorewarn\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"fileoverwrite\" value=\"yes\"/>" + 
      "</InstOpts>";
      pkg.setInstallationOptions(installationOpts);
      bean.update(pkg);
      this.softwarePackageID = pkg.getId();
      factory.commit();
      
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
    removeAllOfSoftwares();
  }

  /**
   * @throws DMException
   * @throws Exception
   */
  private void removeAllOfSoftwares() throws DMException, Exception {
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
   * @param commandFile
   * @param resultFile
   * @throws IOException
   * @throws Exception
   */
  private void emulate(DMClientEmulator emulator, String deviceExtID, long jobID) throws IOException, Exception {
    
    String processorName = "S60_3rd_InstallationProcessor4DownloadMode";
    BaseProcessor processor = new S60V3InstallProcessor4DL();
    processor.setName(processorName);
    assertEquals(processor.getName(), processorName);
    
    // Emulate the lifecycle of ManagementProcessor.
    String sessionID = "00001";
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, deviceExtID);
    int sessionType = 0;
    DevInfo devInfo = new DevInfo(DEVICE_EXT_ID_FOTA_DM_MODE, MANUFACTURER_External_ID, MODEL_EXT_ID_FOTA_DM_MODE, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(deviceExtID);
    dmState.mssid = "" + jobID;
    
    try {
        SessionContext session = new SessionContext(sessionID, sessionID, (Sync4jPrincipal)principal, sessionType, devInfo, dmState, null, null);
        processor.beginSession(session);
        
        // Test JobStatus
        {
          ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
          DeviceBean deviceBean = factory.createDeviceBean();
          Device device = deviceBean.getDeviceByExternalID(deviceExtID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = jobBean.loadJobByID(jobID);
          ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
          assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_DOING, status.getState());

          assertNotNull(device.getInProgressDeviceProvReq());
          assertEquals(status.getID(), device.getInProgressDeviceProvReq().getID());
          factory.release();
        }
        
        ManagementOperation[] operations = processor.getNextOperations();
        while (operations != null && operations.length > 0) {
              ManagementOperationResult[] result = emulator.process(operations);
              processor.setOperationResults(result);
              operations = processor.getNextOperations();
        }

        // Test JobStatus
        {
          ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
          DeviceBean deviceBean = factory.createDeviceBean();
          Device device = deviceBean.getDeviceByExternalID(deviceExtID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = jobBean.loadJobByID(jobID);
          ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
          assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_DONE, status.getState());
          factory.release();
        }
        
    } catch (Exception ex) {
      throw ex;
    } finally {
      processor.endSession(DeviceDMState.STATE_COMPLETED);
      
      // Test JobStatus
      {
        ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExtID);
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJob job = jobBean.loadJobByID(jobID);
        ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
        assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_WAITING_CLIENT_INITIALIZED_SESSION, status.getState());

        assertNotNull(device.getInProgressDeviceProvReq());
        assertEquals(status.getID(), device.getInProgressDeviceProvReq().getID());
        factory.release();
      }
      
    }
  }

  /**
   * Test Fumo Processor in FOTA Download mode
   * Fumo Root Node exists.
   * @throws Exception
   */
  public void testProcessor() throws Exception {
    assertTrue(true);
    
    long jobID = 0;
    
    // Submit ProfileAssignment Job
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    Software software = null;
    SoftwarePackage softwarePackage = null;
    try {
        factory.beginTransaction();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(DEVICE_EXT_ID_FOTA_DM_MODE);
        SoftwareBean softwareBean = factory.createSoftwareBean();
        software = softwareBean.getSoftwareByID(this.softwareID);
        softwarePackage = softwareBean.getPackageByID(this.softwarePackageID);
        assertNotNull(software);
        ProvisionJob job = jobBean.newJob4SoftwareInstall(device, software);
        jobID = job.getID();
        factory.commit();
        
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
    // Emulator DM Session with DMClientEmulator.
    factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_EXT_ID_FOTA_DM_MODE);
        assertNotNull(model);
        assertEquals("S60 3rd Edition (initial release)", model.getCharacterValue("general", "developer.platform"));
        
        // Initialize Emulator
        DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
        emulator.initialize();
        // 初始化设备树
        emulator.setValue("./DevInfo", null);
        assertTrue(emulator.exists("./DevInfo"));
        emulator.setValue("./DevDetail", null);
        assertTrue(emulator.exists("./DevDetail"));
        emulator.setValue("./DevInfo/Man", MANUFACTURER_External_ID);
        emulator.setValue("./DevInfo/Mod", MODEL_EXT_ID_FOTA_DM_MODE);
        emulator.setValue("./DevDetail/DevTyp", "phone");
        emulator.setValue("./DevDetail/OEM", "");
        emulator.setValue("./DevInfo/Lang", "en");
        emulator.setValue("./DevDetail/FwV", "111");
        emulator.setValue("./DevDetail/SwV", "version_1_0");
        assertTrue(emulator.exists("./DevDetail/SwV"));
        
        emulator.setValue("./SCM", null);
        assertTrue(emulator.exists("./SCM"));
        
        emulator.setValue("./SCM/Download", null);
        assertTrue(emulator.exists("./SCM/Download"));
        
        // Emulate the the process between OMAClient and Processor.
        emulate(emulator, DEVICE_EXT_ID_FOTA_DM_MODE, jobID);
        
        // ******************************************
        // Test Phone Data 
        // ******************************************
        assertTrue(emulator.exists("./SCM/Download/" + software.getExternalId()));
        
        assertTrue(emulator.exists("./SCM/Download/" + software.getExternalId() + "/Name"));
        assertEquals(software.getName(), emulator.getValue("./SCM/Download/" + software.getExternalId() + "/Name"));
        
        assertTrue(emulator.exists("./SCM/Download/" + software.getExternalId() + "/Version"));
        assertEquals(software.getVersion(), emulator.getValue("./SCM/Download/" + software.getExternalId() + "/Version"));

        assertTrue(emulator.exists("./SCM/Download/" + software.getExternalId() + "/URI"));
        assertEquals(softwarePackage.getUrl(), emulator.getValue("./SCM/Download/" + software.getExternalId() + "/URI"));

        assertTrue(emulator.exists("./SCM/Download/" + software.getExternalId() + "/InstallOpts"));
        assertEquals(softwarePackage.getInstallationOptions(), emulator.getValue("./SCM/Download/" + software.getExternalId() + "/InstallOpts"));

        
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

  }


  /**
   * @throws IOException
   */
  private void setupConfiguration() throws IOException {
    Configuration configuration = Configuration.getConfiguration();
    Properties props = new Properties();
    props.setProperty("download.server.uri", "http://download.otas.com:8080/otasdm/dl/DownloadDescriptor/${update.id}.xml");
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    props.store(out, "");
    out.flush();
    out.close();
    byte[] bytes = out.toByteArray();
    InputStream in = new ByteArrayInputStream(bytes);
    configuration.load(in);
  }

}
