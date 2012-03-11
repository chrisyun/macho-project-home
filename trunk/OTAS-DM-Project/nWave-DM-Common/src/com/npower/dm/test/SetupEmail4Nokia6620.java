/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/test/SetupEmail4Nokia6620.java,v 1.11 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.11 $
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
package com.npower.dm.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
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
 * @author Zhao DongLu
 * @version $Revision: 1.11 $ $Date: 2008/06/16 10:11:15 $
 */
public class SetupEmail4Nokia6620 extends TestCase {

  /**
   * 
   */
  private static final String ATTRIBUTE_DEFAULT_VALUE = "default value " + System.currentTimeMillis();

  private static final String BASE_DIR = AllTests.BASE_DIR;

  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER";
  private static final String MODEL_External_ID = "TEST.MODEL";

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
   * DeviceEntity IMEI, ExternalID
   */
  private String Device_External_ID = "IMEI:350149101482769";

  private static final String MAPPING_TEST_FILE = BASE_DIR + "/metadata/profile/mapping/test/mapping.test.1.xml";
  /**
   * DDF files
   */
  private static final String FILENAME_TEST_NOKIA_DDF_1 = BASE_DIR + "/metadata/ddf/test/test.nokia.1.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_2 = BASE_DIR + "/metadata/ddf/test/test.nokia.2.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_3 = BASE_DIR + "/metadata/ddf/test/test.nokia.3.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_4 = BASE_DIR + "/metadata/ddf/test/test.nokia.4.xml";

  /**
   * Profile Template XML File for Testing
   */
  private static final String FILENAME_PROFILE_METADATA = BASE_DIR + "/metadata/profile/metadata/profile.test.xml";

  /**
   * ProfileTemplate name for testcase
   */
  private static final String PROFILE_TEMPLATE_NAME_4_NAP = "TEST.Profile Template#1";

  private static final String PROFILE_TEMPLATE_NAME_4_PROXY = "Test.Proxy Template";

  private static final String PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP = "TEST.Email Template";

  private static final String PROFILE_TEMPLATE_NAME_4_MMS = "TEST.MMS Template";
  
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    this.setupManufacturer(MANUFACTURER_External_ID);
    this.setupModel(MANUFACTURER_External_ID, MODEL_External_ID);
    
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_1);
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_2);
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_3);
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_4);
    
    this.setupProfileTemplates(FILENAME_PROFILE_METADATA);

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
    } catch (RuntimeException e) {
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
    } catch (RuntimeException e) {
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
  
  private void setupProfileTemplates(String templateFilename) throws Exception {
    InputStream in = null;
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      in = new FileInputStream(templateFilename);

      String clause4AllTestTemplates = "from ProfileTemplateEntity where name like 'Test.%' or name like 'TEST.%'";

      factory.beginTransaction();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      List<ProfileTemplate> templates = templateBean.findTemplates(clause4AllTestTemplates);
      for (int i = 0; i < templates.size(); i++) {
          ProfileTemplate template = (ProfileTemplate) templates.get(i);
          templateBean.delete(template);
      }

      templates = templateBean.findTemplates(clause4AllTestTemplates);
      assertEquals(templates.size(), 0);

      int total = templateBean.importProfileTemplates(in);
      in.close();
      assertTrue(total > 0);

      factory.commit();

      templates = templateBean.findTemplates(clause4AllTestTemplates);
      assertTrue(templates.size() > 0);

    } catch (Exception e) {
      factory.rollback();
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
          String typeOfProfile = ProfileConfig.PROFILE_TYPE_NAP;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
          profileBean.setAttributeValue(profile, "TEST Display Name",            ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST Bearer Direction",        ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST NAP Address",             ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST NAP Address Type",        ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST DNS Address",             ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST Authentication Username", ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST Authentication Password", ATTRIBUTE_DEFAULT_VALUE);
        }
        {
          String templateName = PROFILE_TEMPLATE_NAME_4_PROXY;
          String profileName = templateName;
          String typeOfProfile = ProfileConfig.PROFILE_TYPE_PROXY;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
          assertNotNull(profile);
        }
        {
          String templateName = PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP;
          String profileName = templateName;
          String typeOfProfile = ProfileConfig.PROFILE_TYPE_SERVICE;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
          profileBean.setAttributeValue(profile, "Display Name",                 "OTAS Email ASS");
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
          String typeOfProfile = ProfileConfig.PROFILE_TYPE_SERVICE;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
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
        factory.beginTransaction();
  
        InputStream in = new FileInputStream(new File(mappingFilename));
        ProfileMappingBean pmBean = factory.createProfileMappingBean();
        List<ProfileMapping> mappings = pmBean.importProfileMapping(in);
        in.close();
  
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

  public ProfileAssignment createAssignment(ManagementBeanFactory factory, String profileName, String deviceExternalID, String categoryName) throws Exception {
    try {
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
        
        ProfileConfig napProfile = profileBean.getProfileConfigByName(Carrier_External_ID, categoryName, profileName);
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
  
  private ProfileAssignment setupProfileAssignment(ManagementBeanFactory factory, String profileName, String deviceExternalID, String categoryName) throws Exception {
    //factory.beginTransaction();
  
    ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
    ProfileAssignment assignment = this.createAssignment(factory, profileName, deviceExternalID, categoryName);
    assignmentBean.update(assignment);
    
    //ProvisionJobBean jobBean = factory.createProvisionJobBean();
    //ProvisionJob job1 = jobBean.newJob4Assignment(assignment);
    //long jobID1 = job1.getID();

    //factory.commit();
    return assignment;
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    //this.tearDownManufacturer(MANUFACTURER_External_ID);
  }

  @SuppressWarnings("unused")
  private void tearDownManufacturer(String manufacturerExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer != null) {
           factory.beginTransaction();
           modelBean.delete(manufacturer);
           factory.commit();
        }
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {    
      factory.release();
    }
    
  }


  public void testProcessor4EMAIL() throws Exception {
    assertTrue(true);
    
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      
        ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
        
        factory.beginTransaction();
        ProfileAssignment assignment = this.setupProfileAssignment(factory, PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP, Device_External_ID, ProfileCategory.EMAIL_CATEGORY_NAME);
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJob job = jobBean.newJob4Assignment(assignment);
        long jobID = job.getID();
        assertTrue(jobID > 0);
        factory.commit();
        
        // Set the job is done.
        Device device = factory.createDeviceBean().getDeviceByExternalID(this.Device_External_ID);
        ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
        status.setState(ProvisionJobStatus.DEVICE_JOB_STATE_DONE);
        assignment.setProfileRootNodePath("./Email/OTAS1");
        factory.beginTransaction();
        jobBean.update(status);
        assignmentBean.update(assignment);
        factory.commit();

        // Re-assign it
        factory.beginTransaction();
        job = jobBean.newJob4Assignment(assignment);
        factory.commit();
        jobID = job.getID();
        
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

}
