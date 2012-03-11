/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/processor/TestSmartProfileAssignmentProcessor4Motorola.java,v 1.9 2008/08/04 07:13:34 zhao Exp $
  * $Revision: 1.9 $
  * $Date: 2008/08/04 07:13:34 $
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
package com.npower.dm.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
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
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileAssignmentBean;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProfileMappingBean;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SubscriberBean;

/**
 * <pre>
 * 
 * 由于Motorola ProfileNodeMapping的映射模式涉及较多的功能要求, 因此本测试类提供如下功能的测试：
 * 1. Profile Node Mapping的LinkedCategoryName模式, 由Email with NAP测试完成
 * 2. Profile Node Mapping的APName模式, 由MMS的属性URI测试完成
 * 3. Profile Node Mapping的Value Translation模式, 由Email with NAP中的属性Mailbox Protocol完成
 * 
 * </pre>
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/08/04 07:13:34 $
 */
public class TestSmartProfileAssignmentProcessor4Motorola extends TestCase {

  /**
   * 
   */
  private static final String NAP_PROF_AUTH_SECRET = "NAP_PROF_AUTH_SECRET";

  /**
   * 
   */
  private static final String NAP_PROF_AUTH_NAME = "NAP_PROF_AUTH_NAME";

  /**
   * 
   */
  private static final String NAP_PROF_DNS_ADDR = "NAP_PROF_DNS_ADDR";

  /**
   * 
   */
  private static final String NAP_PROF_ADDR_TYPE = "NAP_PROF_ADDR_TYPE";

  /**
   * 
   */
  private static final String NAP_PROF_ADDRESS = "NAP_PROF_ADDRESS";

  /**
   * 
   */
  private static final String NAP_PROF_BEAR = "NAP_PROF_BEAR";

  /**
   * 
   */
  private static final String NAP_PROF_NAME = "NAP_PROF_NAME";

  private static final String BASE_DIR = AllTests.BASE_DIR;

  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER";
  private static final String MODEL_External_ID = "TEST.MODEL";

  /**
   * 
   */
  private static final String CLIENT_PASSWORD = "sync4j";
  /**
   * 
   */
  private static final String CLIENT_USERNAME = "sync4j";

  private String serverPassword = "srvpwd";
  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID = "Test.Carrier";

  /**
   * DeviceEntity IMEI, ExternalID
   */
  private String Device_External_ID = "IMEI:353755000569915";

  private static final String MAPPING_TEST_FILE = BASE_DIR + "/metadata/setup/models/motorola/mapping.basic.xml";
  /**
   * DDF files
   */
  private static final String FILENAME_TEST_MOTOROLA_DDF_1 = BASE_DIR + "/metadata/setup/ddf/motorola/P044_DDF_File.xml";

  /**
   * Profile Template XML File for Testing
   */
  private static final String FILENAME_PROFILE_METADATA = BASE_DIR + "/metadata/setup/profiles/profile.meta.install.xml";
  private static final String FILENAME_PROFILE_NAP = BASE_DIR + "/metadata/setup/profiles/profile.template.NAP.install.xml";
  private static final String FILENAME_PROFILE_PROXY = BASE_DIR + "/metadata/setup/profiles/profile.template.PROXY.install.xml";
  private static final String FILENAME_PROFILE_MMS = BASE_DIR + "/metadata/setup/profiles/profile.template.MMS.install.xml";
  private static final String FILENAME_PROFILE_EMAIL = BASE_DIR + "/metadata/setup/profiles/profile.template.EMAIL.install.xml";

  /**
   * ProfileTemplate name for testcase
   */
  private static final String PROFILE_TEMPLATE_NAME_4_NAP = "NAP Default Template";

  private static final String PROFILE_TEMPLATE_NAME_4_PROXY = "Proxy Default Template";

  private static final String PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP = "Email Default Template";

  private static final String PROFILE_TEMPLATE_NAME_4_MMS = "MMS Default Template";
  
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    this.setupManufacturer(MANUFACTURER_External_ID);
    this.setupModel(MANUFACTURER_External_ID, MODEL_External_ID);
    
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_MOTOROLA_DDF_1);
    
    this.cleanProfileTemplates();
    this.setupProfileTemplates(FILENAME_PROFILE_METADATA);
    this.setupProfileTemplates(FILENAME_PROFILE_NAP);
    this.setupProfileTemplates(FILENAME_PROFILE_PROXY);
    this.setupProfileTemplates(FILENAME_PROFILE_MMS);
    this.setupProfileTemplates(FILENAME_PROFILE_EMAIL);

    this.setUpCarrier(Carrier_External_ID);
    
    this.setUpProfileConfigs(Carrier_External_ID);
    
    this.setupDevice(Carrier_External_ID, MANUFACTURER_External_ID, MODEL_External_ID, Device_External_ID);
    
    this.setupProfileMapping(MANUFACTURER_External_ID, MODEL_External_ID, MAPPING_TEST_FILE);
    
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

  private void setupModel(String manufacturerExternalID, String modelExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        if (model == null) {
           model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
           factory.beginTransaction();
           modelBean.update(model);
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
  
  private void cleanProfileTemplates() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      String clause4AllTestTemplates = "from ProfileTemplateEntity where name like '%'";

      factory.beginTransaction();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      List<ProfileTemplate> templates = templateBean.findTemplates(clause4AllTestTemplates);
      for (int i = 0; i < templates.size(); i++) {
          ProfileTemplate template = (ProfileTemplate) templates.get(i);
          templateBean.delete(template);
      }

      templates = templateBean.findTemplates(clause4AllTestTemplates);
      assertEquals(templates.size(), 0);

      factory.commit();

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  private void setupProfileTemplates(String templateFilename) throws Exception {
    InputStream in = null;
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      in = new FileInputStream(templateFilename);

      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      
      in = new FileInputStream(templateFilename);
      templateBean.importCategory(in);
      in.close();

      in = new FileInputStream(templateFilename);
      templateBean.importProfileAttributeType(in);
      in.close();
      
      in = new FileInputStream(templateFilename);
      templateBean.importProfileTemplates(in);
      in.close();

    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  public void setUpCarrier(String carrierExternalID) throws Exception {
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

  /**
   * Create a profile config.
   * @param carrierExternalID
   * @param templateName
   * @param profileName
   * @param profileType
   * 
   * @return     Return a ProfileConfig.
   * @throws Exception
   */
  private ProfileConfig createProfileConfig(ManagementBeanFactory factory, String carrierExternalID, 
                                   String templateName, String profileName, 
                                   String profileType) throws Exception {
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      CarrierBean carrierBean = factory.createCarrierBean();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      
      ProfileTemplate template = templateBean.getTemplateByName(templateName);

      Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
      assertNotNull(carrier);

      ProfileConfig configProfile = profileBean.getProfileConfigByName(Carrier_External_ID, profileType, profileName);
      if (configProfile != null) {
         profileBean.deleteProfileConfig(configProfile);
      }

      configProfile = profileBean.newProfileConfigInstance(profileName, 
                                carrier, template,
                                profileType);
      // Add
      profileBean.update(configProfile);

      assertTrue(configProfile.getID() > 0);
      return configProfile;
  }
  
  private void setUpProfileConfigs(String carrierExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileConfigBean profileBean = factory.createProfileConfigBean();
    try {
        factory.beginTransaction();
        {
          String templateName = PROFILE_TEMPLATE_NAME_4_NAP;
          String profileName = templateName;
          String typeOfProfile = ProfileCategory.NAP_CATEGORY_NAME;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
          profileBean.setAttributeValue(profile, "NAME",          TestSmartProfileAssignmentProcessor4Motorola.NAP_PROF_NAME);
          profileBean.setAttributeValue(profile, "BEARER",        TestSmartProfileAssignmentProcessor4Motorola.NAP_PROF_BEAR);
          profileBean.setAttributeValue(profile, "NAP-ADDRESS",   TestSmartProfileAssignmentProcessor4Motorola.NAP_PROF_ADDRESS);
          profileBean.setAttributeValue(profile, "NAP-ADDRTYPE",  TestSmartProfileAssignmentProcessor4Motorola.NAP_PROF_ADDR_TYPE);
          profileBean.setAttributeValue(profile, "DNS-ADDR",      TestSmartProfileAssignmentProcessor4Motorola.NAP_PROF_DNS_ADDR);
          profileBean.setAttributeValue(profile, "AUTHNAME",      TestSmartProfileAssignmentProcessor4Motorola.NAP_PROF_AUTH_NAME);
          profileBean.setAttributeValue(profile, "AUTHSECRET",    TestSmartProfileAssignmentProcessor4Motorola.NAP_PROF_AUTH_SECRET);
        }
        {
          String templateName = PROFILE_TEMPLATE_NAME_4_PROXY;
          String profileName = templateName;
          String typeOfProfile = ProfileCategory.PROXY_CATEGORY_NAME;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
          profileBean.setAttributeValue(profile, "NAME",          "OTAS Proxy Profile");
          profileBean.setAttributeValue(profile, "STARTPAGE",     "http://startpage.otas.com");
          profileBean.setAttributeValue(profile, "PXADDRTYPE",     "IPV4");
          profileBean.setAttributeValue(profile, "PXADDR",     "1.2.3.4");
          profileBean.setAttributeValue(profile, "PXPHYSICAL PORTNBR",     "8080");
          profileBean.setAttributeValue(profile, "PXPHYSICAL SERVICE",     "5");
          profileBean.setAttributeValue(profile, "PXPHYSICAL DOMAIN",     "0.0.0.0");
          assertNotNull(profile);
        }
        {
          String templateName = PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP;
          String profileName = templateName;
          String typeOfProfile = ProfileCategory.EMAIL_CATEGORY_NAME;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
          profileBean.setAttributeValue(profile, "Display Name",                 "OTAS Email");
          profileBean.setAttributeValue(profile, "Username",                     "zhaodonglu");
          profileBean.setAttributeValue(profile, "Password",                     "zhaodonglu");
          profileBean.setAttributeValue(profile, "Email Address",                "zhaodonglu@otas.cn");
          profileBean.setAttributeValue(profile, "Reply To Address",             "zhaodonglu@otas.cn");
          profileBean.setAttributeValue(profile, "User Display Name",            "Zhao DongLu");
          profileBean.setAttributeValue(profile, "Receiving Server Address",     "pop.otas.cn");
          profileBean.setAttributeValue(profile, "Receiving Server Port",        "110");
          profileBean.setAttributeValue(profile, "Sending Server Address",       "smtp.otas.cn");
          profileBean.setAttributeValue(profile, "Sending Server Port",          "25");
          profileBean.setAttributeValue(profile, "Mailbox Protocol",             "POP");
          profileBean.setAttributeValue(profile, "APLink",                       "AP/INSG1");
        }
        {
          String templateName = PROFILE_TEMPLATE_NAME_4_MMS;
          String profileName = templateName;
          String typeOfProfile = ProfileCategory.MMS_CATEGORY_NAME;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
          profileBean.setAttributeValue(profile, "Display Name",                 "OTAS MMS Profile");
          profileBean.setAttributeValue(profile, "MMSC Server",                  "http://mmsc.service.com");
          assertNotNull(profile);
        }
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

  public void setupDevice(String carrierExternalID, String manufacturerExternalID, String modelExternalID, String deviceExternalID) throws Exception {
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

  // Test case: import profile mapping
  public void setupProfileMapping(String manufacturerExternalID, String modelExternalID, String mappingFilename) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        InputStream in = new FileInputStream(new File(mappingFilename));
        ProfileMappingBean pmBean = factory.createProfileMappingBean();
        List<ProfileMapping> mappings = pmBean.importProfileMapping(in, manufacturerExternalID, modelExternalID);
        in.close();
  
        factory.beginTransaction();
        
        assertEquals(mappings.size(), 4);
        // Attach the profileMapping to Model
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        for (int i = 0; i < mappings.size(); i++) {
            ProfileMapping map = (ProfileMapping) mappings.get(i);
            
            modelBean.attachProfileMapping(model, map.getID());
            
            ProfileTemplate template = map.getProfileTemplate();
            ProfileMapping map2 = model.getProfileMap(template);
            assertEquals(map, map2);
            
        }
        
        factory.commit();
        
        assertEquals(4, model.getProfileMappings().size());
      
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  private ProfileAssignment createNAPAssignment(ManagementBeanFactory factory, String deviceExternalID) throws Exception {
    try {
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
        
        ProfileConfig napProfile = profileBean.getProfileConfigByName(Carrier_External_ID, ProfileCategory.NAP_CATEGORY_NAME, PROFILE_TEMPLATE_NAME_4_NAP);
        assertNotNull(napProfile);
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        assertNotNull(device);
  
        // Assignment # 1
        ProfileAssignment assignment1 = assignmentBean.newProfileAssignmentInstance(napProfile, device);
        assignmentBean.update(assignment1);
  
        return assignment1;
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }
  
  private ProfileAssignment createEmailAssignment(ManagementBeanFactory factory, String deviceExternalID) throws Exception {
    try {
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
        
        ProfileConfig napProfile = profileBean.getProfileConfigByName(Carrier_External_ID, ProfileCategory.NAP_CATEGORY_NAME, PROFILE_TEMPLATE_NAME_4_NAP);
        ProfileConfig profile = profileBean.getProfileConfigByName(Carrier_External_ID, ProfileCategory.EMAIL_CATEGORY_NAME, PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP);
        profile.setNAPProfile(napProfile);
        
        assertNotNull(profile);
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        assertNotNull(device);
  
        // Assignment # 1
        ProfileAssignment assignment1 = assignmentBean.newProfileAssignmentInstance(profile, device);
        assignmentBean.update(assignment1);
  
        return assignment1;
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }
  
  private ProfileAssignment createProxyAssignment(ManagementBeanFactory factory, String deviceExternalID) throws Exception {
    try {
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
        
        ProfileConfig napProfile = profileBean.getProfileConfigByName(Carrier_External_ID, ProfileCategory.NAP_CATEGORY_NAME, PROFILE_TEMPLATE_NAME_4_NAP);
        ProfileConfig proxyProfile = profileBean.getProfileConfigByName(Carrier_External_ID, ProfileCategory.PROXY_CATEGORY_NAME, PROFILE_TEMPLATE_NAME_4_PROXY);
        proxyProfile.setNAPProfile(napProfile);
        
        assertNotNull(proxyProfile);
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        assertNotNull(device);
  
        // Assignment # 1
        ProfileAssignment assignment1 = assignmentBean.newProfileAssignmentInstance(proxyProfile, device);
        assignmentBean.update(assignment1);
  
        return assignment1;
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }

  private ProfileAssignment createMMSAssignment(ManagementBeanFactory factory, String deviceExternalID) throws Exception {
    try {
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
        
        ProfileConfig napProfile = profileBean.getProfileConfigByName(Carrier_External_ID, ProfileCategory.NAP_CATEGORY_NAME, PROFILE_TEMPLATE_NAME_4_NAP);
        ProfileConfig proxyProfile = profileBean.getProfileConfigByName(Carrier_External_ID, ProfileCategory.PROXY_CATEGORY_NAME, PROFILE_TEMPLATE_NAME_4_PROXY);
        proxyProfile.setNAPProfile(napProfile);
        
        ProfileConfig profile = profileBean.getProfileConfigByName(Carrier_External_ID, ProfileCategory.MMS_CATEGORY_NAME, PROFILE_TEMPLATE_NAME_4_MMS);
        profile.setProxyProfile(proxyProfile);
        
        assertNotNull(profile);
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        assertNotNull(device);
  
        // Assignment # 1
        ProfileAssignment assignment1 = assignmentBean.newProfileAssignmentInstance(profile, device);
        assignmentBean.update(assignment1);
  
        return assignment1;
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }
  
  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    this.tearDownManufacturer(MANUFACTURER_External_ID);
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
  private void emulate(DMClientEmulator emulator, long jobID) throws IOException, Exception {
    
    String processorName = "SmartProfileAssignmentProcessor";
    BaseProcessor processor = new SmartProfileAssignmentProcessor();
    processor.setName(processorName);
    assertEquals(processor.getName(), processorName);
    
    // Emulate the lifecycle of ManagementProcessor.
    String sessionID = "00001";
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, Device_External_ID);
    int sessionType = 0;
    DevInfo devInfo = new DevInfo(Device_External_ID, MANUFACTURER_External_ID, MODEL_External_ID, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(Device_External_ID);
    dmState.mssid = "" + jobID;
    
    try {
        SessionContext session = new SessionContext(sessionID, sessionID, (Sync4jPrincipal)principal, sessionType, devInfo, dmState, null, null);
        processor.beginSession(session);
        
        // Test JobStatus
        {
          ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
          DeviceBean deviceBean = factory.createDeviceBean();
          Device device = deviceBean.getDeviceByExternalID(Device_External_ID);
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
          Device device = deviceBean.getDeviceByExternalID(Device_External_ID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = jobBean.loadJobByID(jobID);
          ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
          assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_DOING, status.getState());
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
        Device device = deviceBean.getDeviceByExternalID(Device_External_ID);
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJob job = jobBean.loadJobByID(jobID);
        ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
        assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_DONE, status.getState());

        assertNull(device.getInProgressDeviceProvReq());
        factory.release();
      }
      
    }
  }

  public void testProcessor4NAP() throws Exception {
    assertTrue(true);
    
    long jobID = 0;
    
    // Submit ProfileAssignment Job
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        factory.beginTransaction();
        ProfileAssignment assignment = this.createNAPAssignment(factory, Device_External_ID);
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJob job = jobBean.newJob4Assignment(assignment);
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
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
        assertNotNull(model);
        
        // Initialize Emulator
        DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
        emulator.initialize();
        
        emulator.setValue("./Application", null);
        assertTrue(emulator.exists("./Application"));
        emulator.setValue("./Application/PXLOGICAL", null);
        assertTrue(emulator.exists("./Application/PXLOGICAL"));
        
        
        emulator.setValue("./Application/PXLOGICAL/OTAS1", null);
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS1"));
        emulator.setValue("./Application/PXLOGICAL/OTAS2", null);
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS2"));
        emulator.setValue("./Application/PXLOGICAL/OTAS3", null);
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS3"));
        emulator.setValue("./Application/PXLOGICAL/OTAS4", null);
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS4"));
        
        // Emulate the the process between OMAClient and Processor.
        emulate(emulator, jobID);
        
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS1"));
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS2"));
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS3"));
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS4"));
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS5"));
        
        assertEquals(NAP_PROF_NAME, emulator.getValue("./Application/PXLOGICAL/OTAS5/Name"));
        assertEquals("GPRS", emulator.getValue("./Application/PXLOGICAL/OTAS5/NAPDEF/gprs/AddrType"));
        assertEquals(NAP_PROF_ADDRESS, emulator.getValue("./Application/PXLOGICAL/OTAS5/NAPDEF/gprs/Addr"));
        assertEquals(NAP_PROF_DNS_ADDR, emulator.getValue("./Application/PXLOGICAL/OTAS5/NAPDEF/DNS/OTAS2"));
        assertEquals(NAP_PROF_AUTH_NAME, emulator.getValue("./Application/PXLOGICAL/OTAS5/NAPDEF/gprs/UserName"));
        assertEquals(NAP_PROF_AUTH_SECRET, emulator.getValue("./Application/PXLOGICAL/OTAS5/NAPDEF/gprs/PassWD"));
        
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

  }

  /**
   * 以Motorola手机为特征, 测试LinkedCategoryName模式的属性映射和ValueTranslation.
   * 
   * @throws Exception
   */
  public void testProcessor4EMAIL_With_NAP() throws Exception {
    assertTrue(true);
    
    long jobID4Nap = 0;
    long jobID4Email = 0;
    
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        factory.beginTransaction();
        {
          ProfileAssignment assignment = this.createNAPAssignment(factory, Device_External_ID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = jobBean.newJob4Assignment(assignment);
          jobID4Nap = job.getID();
        }
        {
          ProfileAssignment assignment = this.createEmailAssignment(factory, Device_External_ID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = jobBean.newJob4Assignment(assignment);
          jobID4Email = job.getID();
        }
        factory.commit();
        
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
    
    factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
        assertNotNull(model);
        
        // Initialize Emulator
        DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
        emulator.initialize();
        emulator.setValue("./Application", null);
        assertTrue(emulator.exists("./Application"));
        emulator.setValue("./Application/EMAIL", null);
        assertTrue(emulator.exists("./Application/EMAIL"));
        
        // Emulate the the process between OMAClient and Processor.
        emulate(emulator, jobID4Nap);

        emulate(emulator, jobID4Email);
        
        assertTrue(emulator.exists("./Application"));
        assertTrue(emulator.exists("./Application/EMAIL"));
        assertEquals("zhaodonglu", emulator.getValue("./Application/EMAIL/UserName"));
        // Testcase: Value Translate from POP -> POP3
        assertEquals("POP3", emulator.getValue("./Application/EMAIL/Service"));
        assertEquals("smtp.otas.cn", emulator.getValue("./Application/EMAIL/SendHost"));
        assertEquals("pop.otas.cn", emulator.getValue("./Application/EMAIL/RecvHost"));
        assertEquals("zhaodonglu@otas.cn", emulator.getValue("./Application/EMAIL/ReplyAddr"));
        assertEquals("zhaodonglu", emulator.getValue("./Application/EMAIL/PassWD"));

        // Testcase: LinkedProfileCategory Node Mapping
        assertEquals(NAP_PROF_ADDR_TYPE, emulator.getValue("./Application/EMAIL/NAP-AddrType"));
        assertEquals(NAP_PROF_ADDRESS, emulator.getValue("./Application/EMAIL/NAP-Addr"));
        assertEquals(NAP_PROF_DNS_ADDR, emulator.getValue("./Application/EMAIL/DNS"));
        assertEquals(NAP_PROF_AUTH_NAME, emulator.getValue("./Application/EMAIL/NAP-UserName"));
        assertEquals(NAP_PROF_AUTH_SECRET, emulator.getValue("./Application/EMAIL/NAP-PassWD"));
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * 以Motorola手机为特征, 测试APName模式的属性映射.
   * @throws Exception
   */
  public void testProcessor4MMS() throws Exception {
    assertTrue(true);
    
    long jobID4Nap = 0;
    long jobID4Proxy = 0;
    long jobID4MMS = 0;
    
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        factory.beginTransaction();
        {
          ProfileAssignment assignment = this.createNAPAssignment(factory, Device_External_ID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = jobBean.newJob4Assignment(assignment);
          jobID4Nap = job.getID();
        }
        {
          ProfileAssignment assignment = this.createProxyAssignment(factory, Device_External_ID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = jobBean.newJob4Assignment(assignment);
          jobID4Proxy = job.getID();
        }
        {
          ProfileAssignment assignment = this.createMMSAssignment(factory, Device_External_ID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = jobBean.newJob4Assignment(assignment);
          jobID4MMS = job.getID();
        }
        factory.commit();
        
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
    
    factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
        assertNotNull(model);
        
        // Initialize Emulator
        DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
        emulator.initialize();
        emulator.setValue("./Application", null);
        assertTrue(emulator.exists("./Application"));
        emulator.setValue("./Application/MMS", null);
        assertTrue(emulator.exists("./Application/MMS"));
        emulator.setValue("./Application", null);
        assertTrue(emulator.exists("./Application"));
        emulator.setValue("./Application/PXLOGICAL", null);
        assertTrue(emulator.exists("./Application/PXLOGICAL"));
        emulator.setValue("./Application/PXLOGICAL/OTAS1", null);
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS1"));
        emulator.setValue("./Application/PXLOGICAL/OTAS2", null);
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS2"));
        emulator.setValue("./Application/PXLOGICAL/OTAS3", null);
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS3"));
        emulator.setValue("./Application/PXLOGICAL/OTAS4", null);
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS4"));
                
        // Emulate the the process between OMAClient and Processor.
        emulate(emulator, jobID4Nap);

        emulate(emulator, jobID4Proxy);

        emulate(emulator, jobID4MMS);
        
        // Checking NAP Nodes
        assertTrue(emulator.exists("./Application"));
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS5"));
        assertEquals(NAP_PROF_NAME, emulator.getValue("./Application/PXLOGICAL/OTAS5/Name"));
        assertEquals("GPRS", emulator.getValue("./Application/PXLOGICAL/OTAS5/NAPDEF/gprs/AddrType"));
        assertEquals(NAP_PROF_ADDRESS, emulator.getValue("./Application/PXLOGICAL/OTAS5/NAPDEF/gprs/Addr"));
        assertEquals(NAP_PROF_DNS_ADDR, emulator.getValue("./Application/PXLOGICAL/OTAS5/NAPDEF/DNS/OTAS2"));
        assertEquals(NAP_PROF_AUTH_NAME, emulator.getValue("./Application/PXLOGICAL/OTAS5/NAPDEF/gprs/UserName"));
        assertEquals(NAP_PROF_AUTH_SECRET, emulator.getValue("./Application/PXLOGICAL/OTAS5/NAPDEF/gprs/PassWD"));
        
        // Checking Proxy Nodes
        assertTrue(emulator.exists("./Application/PXLOGICAL/OTAS5/PXPHYSICAL/OTAS1"));
        assertEquals("1.2.3.4", emulator.getValue("./Application/PXLOGICAL/OTAS5/PXPHYSICAL/OTAS1/Addr"));
        assertEquals("IPV4", emulator.getValue("./Application/PXLOGICAL/OTAS5/PXPHYSICAL/OTAS1/AddrType"));
        assertEquals("0.0.0.0", emulator.getValue("./Application/PXLOGICAL/OTAS5/PXPHYSICAL/OTAS1/Domain"));
        assertEquals("8080", emulator.getValue("./Application/PXLOGICAL/OTAS5/PXPHYSICAL/OTAS1/PortNbr"));
        assertEquals("5", emulator.getValue("./Application/PXLOGICAL/OTAS5/PXPHYSICAL/OTAS1/Service"));
    
        // Checking MMS Nodes
        assertTrue(emulator.exists("./Application"));
        assertTrue(emulator.exists("./Application/MMS/OTAS1"));
        assertEquals("OTAS MMS Profile", emulator.getValue("./Application/MMS/OTAS1/Name"));
        // **************************************************
        // Testcase: APName 
        // **************************************************
        assertEquals("OTAS5", emulator.getValue("./Application/MMS/OTAS1/To-Proxy"));
        assertEquals("http://startpage.otas.com", emulator.getValue("./Application/MMS/OTAS1/URI"));
        
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }
}
