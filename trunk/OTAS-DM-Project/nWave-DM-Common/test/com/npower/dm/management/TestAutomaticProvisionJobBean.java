/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/management/TestAutomaticProvisionJobBean.java,v 1.10 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.10 $
  * $Date: 2008/06/16 10:11:15 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.AllTests;
import com.npower.dm.core.AutomaticProvisionJob;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.selector.AutomaticProvisionJobSelector;
import com.npower.dm.management.selector.CarrierAutoJobSelector;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.10 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestAutomaticProvisionJobBean extends TestCase {

  private static Log log = LogFactory.getLog(TestAutomaticProvisionJobBean.class);

  private static final String BASE_DIR = AllTests.BASE_DIR;

  /**
   * Profile Template XML File for Testing
   */
  private static final String FILENAME_PROFILE_METADATA = BASE_DIR + "/metadata/profile/metadata/profile.test.xml";

  private static final String PROFILE_TEMPLATE_NAME_4_NAP = "TEST.Profile Template#1";

  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER";

  private static final String MODEL_External_ID = "TEST.MODEL";

  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID_1 = "Test.Carrier.1";
  private String Carrier_External_ID_2 = "Test.Carrier.2";
  private String Carrier_External_ID_3 = "Test.Carrier.3";
  
  private String deviceExtID1 = "IMEI:11111111111111111";
  private String deviceExtID2 = "IMEI:22222222222222222";
  private String deviceExtID3 = "IMEI:33333333333333333";
  
  private String FILENAME_GET_SCRIPT = "/metadata/command/test/test.get.xml";
  
  /**
   * Read script from a script file.
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  private String readScript(File file) throws FileNotFoundException, IOException {
    BufferedReader scriptFile = new BufferedReader(new FileReader(file));
    String line = scriptFile.readLine();
    String script = "";
    while (line != null) {
          script += line;
          line = scriptFile.readLine();
    }
    return script;
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        this.setupTemplates(factory);
        this.setUpCarriers(factory);
        this.setupModel(factory);
        this.setupDevices(factory);
        
        this.setupProfileNAP(factory);
        
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        tearDownProfiles(factory);
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }
  
  private void setUpCarriers(ManagementBeanFactory factory) throws Exception {
    try {
      Country country = factory.createCountryBean().getCountryByISOCode("CN");
      assertNotNull(country);
      assertEquals(country.getCountryCode(), "86");
      CarrierBean carrierBean = factory.createCarrierBean();
      {
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID_1);
        if (carrier == null) {
           carrier = carrierBean.newCarrierInstance(country, Carrier_External_ID_1, Carrier_External_ID_1);
           factory.beginTransaction();
           carrierBean.update(carrier);
           factory.commit();
        }
      }
      {
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID_2);
        if (carrier == null) {
           carrier = carrierBean.newCarrierInstance(country, Carrier_External_ID_2, Carrier_External_ID_2);
           factory.beginTransaction();
           carrierBean.update(carrier);
           factory.commit();
        }
      }
      {
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID_3);
        if (carrier == null) {
           carrier = carrierBean.newCarrierInstance(country, Carrier_External_ID_3, Carrier_External_ID_3);
           factory.beginTransaction();
           carrierBean.update(carrier);
           factory.commit();
        }
      }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    }
  }

  private void setupModel(ManagementBeanFactory factory) throws Exception {
    try {
        ModelBean modelBean = factory.createModelBean();
  
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        
        factory.beginTransaction();
        if (manufacturer == null) {
           manufacturer = modelBean.newManufacturerInstance(MANUFACTURER_External_ID, MANUFACTURER_External_ID, MANUFACTURER_External_ID);
           modelBean.update(manufacturer);
        }
  
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
        if (model == null) {
           model = modelBean.newModelInstance(manufacturer, MODEL_External_ID, MODEL_External_ID, true, true, true, true, true);
           modelBean.update(model);
        }
        factory.commit();
    } catch (DMException e) {
      factory.rollback();
      throw e;
    }
  }

  private void setupDevices(ManagementBeanFactory factory) throws Exception {
    try {
        CarrierBean carrierBean = factory.createCarrierBean();
        ModelBean modelBean = factory.createModelBean();
  
        Carrier carrier1 = carrierBean.getCarrierByExternalID(Carrier_External_ID_1);
        assertNotNull(carrier1);
        Carrier carrier2 = carrierBean.getCarrierByExternalID(Carrier_External_ID_2);
        assertNotNull(carrier2);
        Carrier carrier3 = carrierBean.getCarrierByExternalID(Carrier_External_ID_3);
        assertNotNull(carrier3);
  
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
        assertNotNull(model);
        
        createDevice(factory, carrier1, model, deviceExtID1);
        createDevice(factory, carrier2, model, deviceExtID2);
        createDevice(factory, carrier3, model, deviceExtID3);
  
    } catch (DMException e) {
      factory.rollback();
      throw e;
    }
  }

  /**
   * @param factory
   * @param subscriberBean
   * @param deviceBean
   * @param carrier
   * @param model
   * @param deviceExtID
   * @throws DMException
   */
  private void createDevice(ManagementBeanFactory factory, Carrier carrier, Model model, String deviceExtID) throws DMException {
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    DeviceBean deviceBean = factory.createDeviceBean();
    
    List<Subscriber> list = subscriberBean.findSubscriber("from SubscriberEntity where externalId='" + deviceExtID + "'");
    Subscriber subscriber = null;
    if (list.size() > 0) {
       subscriber = (Subscriber) list.get(0);
       factory.beginTransaction();
       subscriberBean.delete(subscriber);
       factory.commit();
    }
    
    subscriber = subscriberBean.newSubscriberInstance(carrier, deviceExtID, deviceExtID, deviceExtID);
    
    factory.beginTransaction();
    subscriberBean.update(subscriber);
    factory.commit();
    assertNotNull(subscriber);
 
    Device device = deviceBean.getDeviceByExternalID(deviceExtID);
    if (device != null) {
      factory.beginTransaction();
      deviceBean.delete(device);
      factory.commit();
    }
 
    // Create a DeviceEntity
    device = deviceBean.newDeviceInstance(subscriber, model, deviceExtID);
 
    factory.beginTransaction();
    deviceBean.update(device);
    factory.commit();
 
    // Test found
    device = deviceBean.getDeviceByID("" + device.getID());
    assertNotNull(device);
 
    assertEquals(deviceExtID, device.getExternalId());
  }

  private void setupTemplates(ManagementBeanFactory factory) throws Exception {
    InputStream in = null;
    try {
        ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
        // in = getClass().getResourceAsStream("profile.xml");
        in = new FileInputStream(FILENAME_PROFILE_METADATA);
  
        String clause4AllTestTemplates = "from ProfileTemplateEntity where name like 'Test.%' or name like 'TEST.%'";
  
        factory.beginTransaction();
        List<ProfileTemplate> templates = templateBean.findTemplates(clause4AllTestTemplates);
        for (int i = 0; i < templates.size(); i++) {
          ProfileTemplate template = (ProfileTemplate) templates.get(i);
          log.info("delete:" + template);
          templateBean.delete(template);
        }
        factory.commit();
  
        templates = templateBean.findTemplates(clause4AllTestTemplates);
        assertEquals(templates.size(), 0);
  
        int total = templateBean.importProfileTemplates(in);
        in.close();
        assertTrue(total > 0);
  
        templates = templateBean.findTemplates(clause4AllTestTemplates);
        assertTrue(templates.size() > 0);
  
    } catch (Exception e) {
      factory.rollback();
      throw e;
    }
  }

  private void setupProfileNAP(ManagementBeanFactory factory) throws Exception {
    try {
      CarrierBean carrierBean = factory.createCarrierBean();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      
      ProfileTemplate template = templateBean.getTemplateByName(PROFILE_TEMPLATE_NAME_4_NAP);
      assertEquals(template.getName(), PROFILE_TEMPLATE_NAME_4_NAP);

      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID_1);
      assertNotNull(carrier);

      ProfileConfig configProfile = profileBean.getProfileConfigByName(carrier.getExternalID(), template.getProfileCategory().getName(), "Test.NAP.ConfigProfile.Name");
      if (configProfile != null) {
        factory.beginTransaction();
        profileBean.deleteProfileConfig(configProfile);
        factory.commit();
      }

      configProfile = profileBean.newProfileConfigInstance("Test.NAP.ConfigProfile.Name", carrier, template,
          ProfileConfig.PROFILE_TYPE_NAP);
      configProfile.setName("Test.NAP.ConfigProfile.Name");
      configProfile.setCarrier(carrier);
      configProfile.setProfileTemplate(template);
      configProfile.setProfileType(ProfileConfig.PROFILE_TYPE_NAP);

      // Add
      factory.beginTransaction();
      profileBean.update(configProfile);
      factory.commit();

      assertTrue(configProfile.getID() > 0);

      // Attach value
      String name1 = "TEST Display Name";
      String value1 = "TEST Display Name Value";
      ProfileAttributeValue av1 = configProfile.getProfileAttributeValue(name1);
      assertNull(av1);

      String name2 = "TEST Bearer Direction";
      String[] value2 = { "a", "b", "c", "d" };
      ProfileAttributeValue av2 = configProfile.getProfileAttributeValue(name2);
      assertNull(av2);

      String name3 = "TEST NAP Address";
      String value3 = "TEST NAP Address";
      ProfileAttributeValue av3 = configProfile.getProfileAttributeValue(name3);
      assertNull(av3);

      String name4 = "TEST NAP Address Type";
      String value4 = "TEST NAP Address Type";
      ProfileAttributeValue av4 = configProfile.getProfileAttributeValue(name4);
      assertNull(av4);

      String name5 = "TEST DNS Address";
      String value5 = "TEST DNS Address";
      ProfileAttributeValue av5 = configProfile.getProfileAttributeValue(name5);
      assertNull(av5);

      String name6 = "TEST Authentication Username";
      String value6 = "TEST Authentication Username";
      ProfileAttributeValue av6 = configProfile.getProfileAttributeValue(name6);
      assertNull(av6);

      String name7 = "TEST Authentication Password";
      String value7 = "TEST Authentication Password";
      ProfileAttributeValue av7 = configProfile.getProfileAttributeValue(name7);
      assertNull(av7);

      factory.beginTransaction();
      profileBean.setAttributeValue(configProfile, name1, value1);
      profileBean.setAttributeValue(configProfile, name2, value2);
      profileBean.setAttributeValue(configProfile, name3, value3);
      profileBean.setAttributeValue(configProfile, name4, value4);
      profileBean.setAttributeValue(configProfile, name5, value5);
      profileBean.setAttributeValue(configProfile, name6, value6);
      profileBean.setAttributeValue(configProfile, name7, value7);
      factory.commit();

      av1 = configProfile.getProfileAttributeValue(name1);
      assertNotNull(av1);
      assertEquals(av1.getStringValue(), value1);
      assertEquals(av1.getProfileAttribute().getName(), name1);
      assertNotNull(av1.getRawData());

      av2 = configProfile.getProfileAttributeValue(name2);
      assertNotNull(av2);
      assertEquals(av2.getStringValues().length, 4);
      assertEquals(av2.getStringValues()[0].length(), 1);
      assertEquals(av2.getStringValues()[1].length(), 1);
      assertEquals(av2.getStringValues()[2].length(), 1);
      assertEquals(av2.getStringValues()[3].length(), 1);
      assertEquals(av2.getProfileAttribute().getName(), name2);
      // Multi-value mode
      assertNull(av2.getRawData());

      av3 = configProfile.getProfileAttributeValue(name3);
      assertNotNull(av3);
      assertEquals(av3.getStringValue(), value3);
      assertEquals(av3.getProfileAttribute().getName(), name3);
      assertNotNull(av3.getRawData());

      av4 = configProfile.getProfileAttributeValue(name4);
      assertNotNull(av4);
      assertEquals(av4.getStringValue(), value4);
      assertEquals(av4.getProfileAttribute().getName(), name4);
      assertNotNull(av4.getRawData());

      av5 = configProfile.getProfileAttributeValue(name5);
      assertNotNull(av5);
      assertEquals(av5.getStringValue(), value5);
      assertEquals(av5.getProfileAttribute().getName(), name5);
      assertNotNull(av5.getRawData());

      av6 = configProfile.getProfileAttributeValue(name6);
      assertNotNull(av6);
      assertEquals(av6.getStringValue(), value6);
      assertEquals(av6.getProfileAttribute().getName(), name6);
      assertNotNull(av6.getRawData());

      av7 = configProfile.getProfileAttributeValue(name7);
      assertNotNull(av7);
      assertEquals(av7.getStringValue(), value7);
      assertEquals(av7.getProfileAttribute().getName(), name7);
      assertNotNull(av7.getRawData());

    } catch (DMException e) {
      factory.rollback();
      throw e;
    }
  }


  public void tearDownProfiles(ManagementBeanFactory factory) throws Exception {
    try {
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        // Delete all of ProfileConfigEntity created by setupProfileXXX()
        ProfileConfig configProfile = profileBean.getProfileConfigByName(Carrier_External_ID_1, "TEST.NAP", "Test.NAP.ConfigProfile.Name");
        if (configProfile != null) {
          factory.beginTransaction();
          profileBean.deleteProfileConfig(configProfile);
          factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    }
  }

  public void testDiscoveryJobLongID() throws Exception{
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        AutomaticProvisionJobBean jobBean = factory.createAutoProvisionJobBean();
      
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID_1);

        long jobID = 0;
        String[] nodes = {"./a", "./b", "./c"};
        // Create a job
        {
          factory.beginTransaction();
          AutomaticProvisionJob job = jobBean.newJob4Discovery(null, nodes);
          job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
          job.setCarrier(carrier);
          jobBean.update(job);
          factory.commit();
          
          jobID = job.getID();
          assertTrue(jobID > 0);
        }
        
        // Load job
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[1], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(2));
        }
        
        // Disable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.disable(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_DISABLE, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[1], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(2));
        }
        
        // Enable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.enable(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[1], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(2));
        }
        
        // Cancel job
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.cancel(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_CANCELLED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[1], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(2));
        }
        
        // Enable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.enable(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[1], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(2));
        }
        
        // Delete
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.delete(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNull(job);
        }
        
    } catch (Exception e) {
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void testDiscoveryJobStringID() throws Exception{
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        AutomaticProvisionJobBean jobBean = factory.createAutoProvisionJobBean();
      
        long jobID = 0;
        String[] nodes = {"./a", "./b", "./c"};
        // Create a job
        {
          factory.beginTransaction();
          AutomaticProvisionJob job = jobBean.newJob4Discovery(null, nodes);
          job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
          jobBean.update(job);
          factory.commit();
          
          jobID = job.getID();
          assertTrue(jobID > 0);
        }
        
        // Load job
        {
          AutomaticProvisionJob job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[1], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(2));
        }
        
        // Disable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.disable("" + jobID);
          factory.commit();
          
          job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_DISABLE, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[1], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(2));
        }
        
        // Enable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.enable("" + jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[1], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(2));
        }
        
        // Cancel job
        {
          AutomaticProvisionJob job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.cancel("" + jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_CANCELLED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[1], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(2));
        }
        
        // Enable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.enable("" + jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[1], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(2));
        }
        
        // Delete
        {
          AutomaticProvisionJob job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.delete("" + jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNull(job);
        }
    } catch (Exception e) {
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void testDiscoveryJobNodePaths() throws Exception{
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        AutomaticProvisionJobBean jobBean = factory.createAutoProvisionJobBean();
      
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID_1);

        long jobID = 0;
        String[] nodes = {"./a", null, "./b", "", "./c"};
        // Create a job
        {
          factory.beginTransaction();
          AutomaticProvisionJob job = jobBean.newJob4Discovery(null, nodes);
          job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
          job.setCarrier(carrier);
          jobBean.update(job);
          factory.commit();
          
          jobID = job.getID();
          assertTrue(jobID > 0);
        }
        
        // Load job
        {
          AutomaticProvisionJob job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[4], job.getDiscoveryNodes().get(2));
        }
        
        // Disable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.disable("" + jobID);
          factory.commit();
          
          job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_DISABLE, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[4], job.getDiscoveryNodes().get(2));
        }
        
        // Enable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.enable("" + jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[4], job.getDiscoveryNodes().get(2));
        }
        
        // Cancel job
        {
          AutomaticProvisionJob job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.cancel("" + jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_CANCELLED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[4], job.getDiscoveryNodes().get(2));
        }
        
        // Enable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.enable("" + jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(3, job.getDiscoveryNodes().size());
          assertEquals(nodes[0], job.getDiscoveryNodes().get(0));
          assertEquals(nodes[2], job.getDiscoveryNodes().get(1));
          assertEquals(nodes[4], job.getDiscoveryNodes().get(2));
        }
        
        // Delete
        {
          AutomaticProvisionJob job = jobBean.loadJobByID("" + jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.delete("" + jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNull(job);
        }
    } catch (Exception e) {
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void testScriptJob() throws Exception{
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        AutomaticProvisionJobBean jobBean = factory.createAutoProvisionJobBean();
      
        long jobID = 0;
        String scripts = readScript(new File(AllTests.BASE_DIR, FILENAME_GET_SCRIPT));
        assertNotNull(scripts);
        assertTrue(scripts.length() > 10);
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID_1);

        // Create a job
        {
          factory.beginTransaction();
          AutomaticProvisionJob job = jobBean.newJob4Command(null, scripts);
          job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
          job.setCarrier(carrier);
          jobBean.update(job);
          factory.commit();
          
          jobID = job.getID();
          assertTrue(jobID > 0);
        }
        
        // Load job
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_SCRIPT, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(scripts, job.getScript());
        }
        
        // Disable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.disable(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_DISABLE, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_SCRIPT, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(scripts, job.getScript());
        }
        
        // Enable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.enable(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_SCRIPT, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(scripts, job.getScript());
        }
        
        // Cancel job
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.cancel(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_CANCELLED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_SCRIPT, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(scripts, job.getScript());
        }
        
        // Enable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.enable(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_SCRIPT, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          
          assertNotNull(job.getCarrier());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(scripts, job.getScript());
        }
        
        // Delete
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.delete(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNull(job);
        }
        
    } catch (Exception e) {
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  public void testProfileAssignmentJob() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        AutomaticProvisionJobBean jobBean = factory.createAutoProvisionJobBean();
      
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        ProfileConfig configProfile = profileBean.getProfileConfigByName(Carrier_External_ID_1, "TEST.NAP", "Test.NAP.ConfigProfile.Name");
        ProfileConfig[] profiles = new ProfileConfig[]{configProfile};
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID_1);

        long jobID = 0;
        // Create a job
        {
          factory.beginTransaction();
          AutomaticProvisionJob job = jobBean.newJob4Assignment(null, profiles);
          job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
          job.setCarrier(carrier);
          jobBean.update(job);
          factory.commit();
          
          jobID = job.getID();
          assertTrue(jobID > 0);
        }
        
        // Load job
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(1, job.getProfileConfigs().size());
          assertEquals(profiles[0].getID(), job.getProfileConfigs().get(0).getID());
          assertEquals(profiles[0].getName(), job.getProfileConfigs().get(0).getName());
        }
        
        // Disable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.disable(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_DISABLE, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(1, job.getProfileConfigs().size());
          assertEquals(profiles[0].getID(), job.getProfileConfigs().get(0).getID());
          assertEquals(profiles[0].getName(), job.getProfileConfigs().get(0).getName());
        }
        
        // Enable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.enable(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(1, job.getProfileConfigs().size());
          assertEquals(profiles[0].getID(), job.getProfileConfigs().get(0).getID());
          assertEquals(profiles[0].getName(), job.getProfileConfigs().get(0).getName());
        }
        
        // Cancel job
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.cancel(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_CANCELLED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(1, job.getProfileConfigs().size());
          assertEquals(profiles[0].getID(), job.getProfileConfigs().get(0).getID());
          assertEquals(profiles[0].getName(), job.getProfileConfigs().get(0).getName());
        }
        
        // Enable
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.enable(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          assertEquals(jobID, job.getID());
          assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
          assertEquals(AutomaticProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job.getJobType());
          assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
          assertEquals(carrier.getID(), job.getCarrier().getID());
          
          assertEquals(1, job.getProfileConfigs().size());
          assertEquals(profiles[0].getID(), job.getProfileConfigs().get(0).getID());
          assertEquals(profiles[0].getName(), job.getProfileConfigs().get(0).getName());
        }
        
        // Delete
        {
          AutomaticProvisionJob job = jobBean.loadJobByID(jobID);
          assertNotNull(job);
          factory.beginTransaction();
          jobBean.delete(jobID);
          factory.commit();
          
          job = jobBean.loadJobByID(jobID);
          assertNull(job);
        }
        
    } catch (Exception e) {
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void testFindAllJobs() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        AutomaticProvisionJobBean jobBean = factory.createAutoProvisionJobBean();
      
        // First clear all
        {
          List<AutomaticProvisionJob> jobs = jobBean.findAllJobs();
          for (AutomaticProvisionJob job: jobs) {
              factory.beginTransaction();
              jobBean.delete(job.getID());
              factory.commit();
          }
        }
        
        // Create Job#1
        long jobID1 = 0;
        {
          String scripts = readScript(new File(AllTests.BASE_DIR, FILENAME_GET_SCRIPT));
          assertNotNull(scripts);
          assertTrue(scripts.length() > 10);
          
          // Create a job
          {
            factory.beginTransaction();
            AutomaticProvisionJob job = jobBean.newJob4Command(null, scripts);
            job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
            jobBean.update(job);
            factory.commit();
            
            jobID1 = job.getID();
            assertTrue(jobID1 > 0);
          }
        }
        
        // Create Job#2
        long jobID2 = 0;
        {
          String[] nodes = {"./a", "./b", "./c"};
          // Create a job
          {
            factory.beginTransaction();
            AutomaticProvisionJob job = jobBean.newJob4Discovery(null, nodes);
            job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
            jobBean.update(job);
            factory.commit();
            
            jobID2 = job.getID();
            assertTrue(jobID2 > 0);
          }
        }
        
        // Create Job#3
        long jobID3 = 0;
        {
          String[] nodes = {"./1", "./2", "./3"};
          // Create a job
          {
            factory.beginTransaction();
            AutomaticProvisionJob job = jobBean.newJob4Discovery(null, nodes);
            job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
            jobBean.update(job);
            factory.commit();
            
            jobID3 = job.getID();
            assertTrue(jobID3 > 0);
          }
        }
        
        // Test Find all
        {
          List<AutomaticProvisionJob> jobs = jobBean.findAllJobs();
          assertNotNull(jobs);
          assertEquals(3, jobs.size());
        }
        
    } catch (Exception e) {
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
  }

  public void testJobSelector() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        AutomaticProvisionJobBean jobBean = factory.createAutoProvisionJobBean();
      
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier1 = carrierBean.getCarrierByExternalID(Carrier_External_ID_1);
        Carrier carrier2 = carrierBean.getCarrierByExternalID(Carrier_External_ID_2);
        Carrier carrier3 = carrierBean.getCarrierByExternalID(Carrier_External_ID_3);

        // First clear all
        {
          List<AutomaticProvisionJob> jobs = jobBean.findAllJobs();
          for (AutomaticProvisionJob job: jobs) {
              factory.beginTransaction();
              jobBean.delete(job.getID());
              factory.commit();
          }
        }
        
        // Create Job#1
        long jobID1 = 0;
        String scripts = readScript(new File(AllTests.BASE_DIR, FILENAME_GET_SCRIPT));
        assertNotNull(scripts);
        assertTrue(scripts.length() > 10);
        {
          
          // Create a job
          String selectorName = "Name1";
          String selectorDescription = "Description1";
          {
            factory.beginTransaction();
            AutomaticProvisionJobSelector selector = new CarrierAutoJobSelector();
            selector.setName(selectorName);
            selector.setDescription(selectorDescription);
            AutomaticProvisionJob job = jobBean.newJob4Command(selector, scripts);
            job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
            job.setCarrier(carrier1);
            jobBean.update(job);
            factory.commit();
            
            jobID1 = job.getID();
            assertTrue(jobID1 > 0);
          }
          // Load job#1
          {
            AutomaticProvisionJob job = jobBean.loadJobByID(jobID1);
            assertNotNull(job);
            assertEquals(jobID1, job.getID());
            assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
            assertEquals(AutomaticProvisionJob.JOB_TYPE_SCRIPT, job.getJobType());
            assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
            
            assertEquals(scripts, job.getScript());
            
            assertNotNull(carrier1);
            assertEquals(Carrier_External_ID_1, carrier1.getExternalID());
            
            assertNotNull(job.getJobSelector());
            assertEquals(selectorName, job.getJobSelector().getName());
            assertEquals(selectorDescription, job.getJobSelector().getDescription());
          }
          
        }
        
        // Create Job#2
        long jobID2 = 0;
        {
          String[] nodes = {"./a", "./b", "./c"};
          // Create a job
          String selectorName = "Name2";
          String selectorDescription = "Description2";
          {
            factory.beginTransaction();
            AutomaticProvisionJobSelector selector = new CarrierAutoJobSelector();
            selector.setName(selectorName);
            selector.setDescription(selectorDescription);
            AutomaticProvisionJob job = jobBean.newJob4Discovery(selector, nodes);
            job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
            job.setCarrier(carrier2);
            jobBean.update(job);
            factory.commit();
            
            jobID2 = job.getID();
            assertTrue(jobID2 > 0);
          }
          
          // Load job#2
          {
            AutomaticProvisionJob job = jobBean.loadJobByID(jobID2);
            assertNotNull(job);
            assertEquals(jobID2, job.getID());
            
            assertNotNull(carrier1);
            assertEquals(Carrier_External_ID_1, carrier1.getExternalID());
            
            assertNotNull(job.getJobSelector());
            assertEquals(selectorName, job.getJobSelector().getName());
            assertEquals(selectorDescription, job.getJobSelector().getDescription());
          }
        }
        
        // Create Job#3
        long jobID3 = 0;
        {
          String[] nodes = {"./1", "./2", "./3"};
          // Create a job
          String selectorName = "Name3";
          String selectorDescription = "Description3";
          {
            factory.beginTransaction();
            AutomaticProvisionJobSelector selector = new CarrierAutoJobSelector();
            selector.setName(selectorName);
            selector.setDescription(selectorDescription);
            AutomaticProvisionJob job = jobBean.newJob4Discovery(selector, nodes);
            job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
            job.setCarrier(carrier3);
            jobBean.update(job);
            factory.commit();
            
            jobID3 = job.getID();
            assertTrue(jobID3 > 0);
          }
          // Load job#3
          {
            AutomaticProvisionJob job = jobBean.loadJobByID(jobID3);
            assertNotNull(job);
            assertEquals(jobID3, job.getID());
            
            assertNotNull(carrier1);
            assertEquals(Carrier_External_ID_1, carrier1.getExternalID());
            
            assertNotNull(job.getJobSelector());
            assertEquals(selectorName, job.getJobSelector().getName());
            assertEquals(selectorDescription, job.getJobSelector().getDescription());
          }
        }
        
        // Create Job#4
        long jobID4 = 0;
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        ProfileConfig configProfile = profileBean.getProfileConfigByName(Carrier_External_ID_1, "TEST.NAP", "Test.NAP.ConfigProfile.Name");
        ProfileConfig[] profiles = new ProfileConfig[]{configProfile};
        {
          String selectorName = "Name3";
          String selectorDescription = "Description3";
          {
            AutomaticProvisionJobSelector selector = new CarrierAutoJobSelector();
            selector.setName(selectorName);
            selector.setDescription(selectorDescription);
            
            factory.beginTransaction();
            AutomaticProvisionJob job = jobBean.newJob4Assignment(selector, profiles);
            job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
            job.setCarrier(carrier1);
            jobBean.update(job);
            factory.commit();
            
            jobID4 = job.getID();
            assertTrue(jobID4 > 0);
          }
          
          // Load job
          {
            AutomaticProvisionJob job = jobBean.loadJobByID(jobID4);
            assertNotNull(job);
            assertEquals(jobID4, job.getID());
            assertEquals(AutomaticProvisionJob.JOB_STATE_APPLIED, job.getState());
            assertEquals(AutomaticProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job.getJobType());
            assertEquals(AutomaticProvisionJob.TYPE_AUTO_REG, job.getType());
            assertEquals(carrier1.getID(), job.getCarrier().getID());
            
            assertEquals(1, job.getProfileConfigs().size());
            assertEquals(profiles[0].getID(), job.getProfileConfigs().get(0).getID());
            assertEquals(profiles[0].getName(), job.getProfileConfigs().get(0).getName());
          }
          
        }
        
        // Test Find all
        {
          List<AutomaticProvisionJob> jobs = jobBean.findAllJobs();
          assertNotNull(jobs);
          assertEquals(4, jobs.size());
          
          assertEquals(jobID1, jobs.get(0).getID());
          assertEquals(jobID2, jobs.get(1).getID());
          assertEquals(jobID3, jobs.get(2).getID());
          assertEquals(jobID4, jobs.get(3).getID());
        }
        
        // Test Carrier Job Selector
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device1 = deviceBean.getDeviceByExternalID(deviceExtID1);
        assertEquals(Carrier_External_ID_1, device1.getSubscriber().getCarrier().getExternalID());
        Device device2 = deviceBean.getDeviceByExternalID(deviceExtID2);
        assertEquals(Carrier_External_ID_2, device2.getSubscriber().getCarrier().getExternalID());
        Device device3 = deviceBean.getDeviceByExternalID(deviceExtID3);
        assertEquals(Carrier_External_ID_3, device3.getSubscriber().getCarrier().getExternalID());
        {
          List<AutomaticProvisionJob> jobs = jobBean.getSelectedJobs(device1);
          assertNotNull(jobs);
          assertEquals(2, jobs.size());
          assertEquals(jobID1, jobs.get(0).getID());
          assertEquals(jobID4, jobs.get(1).getID());
        }
        {
          List<AutomaticProvisionJob> jobs = jobBean.getSelectedJobs(device2);
          assertNotNull(jobs);
          assertEquals(1, jobs.size());
          assertEquals(jobID2, jobs.get(0).getID());
        }
        {
          List<AutomaticProvisionJob> jobs = jobBean.getSelectedJobs(device3);
          assertNotNull(jobs);
          assertEquals(1, jobs.size());
          assertEquals(jobID3, jobs.get(0).getID());
        }
        
        // Test auto submit job into device queue.
        {
          factory.beginTransaction();
          List<ProvisionJob> jobs = jobBean.submitSelectedJobs(device1);
          factory.commit();
          assertNotNull(jobs);
          assertEquals(2, jobs.size());
          
          ProvisionJobBean provisionBean = factory.createProvisionJobBean();
          List<ProvisionJob> provs = provisionBean.findJobsQueued(device1, true);
          assertNotNull(provs);
          assertEquals(2, provs.size());
          assertEquals(jobs.get(0).getID(), provs.get(0).getID());
          
          assertEquals(ProvisionJob.JOB_TYPE_SCRIPT, provs.get(0).getJobType());
          assertEquals(scripts, provs.get(0).getScriptString());
          assertEquals(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, provs.get(1).getJobType());
          assertNotNull(provisionBean.getProfileAssignment(provs.get(1), device1));
          assertEquals(configProfile.getID(), provisionBean.getProfileAssignment(provs.get(1), device1).getProfileConfig().getID());
        }
        
        {
          factory.beginTransaction();
          List<ProvisionJob> jobs = jobBean.submitSelectedJobs(device2);
          factory.commit();
          assertNotNull(jobs);
          assertEquals(1, jobs.size());
          
          ProvisionJobBean provisionBean = factory.createProvisionJobBean();
          List<ProvisionJob> provs = provisionBean.findJobsQueued(device2, true);
          assertNotNull(provs);
          assertEquals(1, provs.size());
          assertEquals(jobs.get(0).getID(), provs.get(0).getID());
          
          assertEquals(ProvisionJob.JOB_TYPE_DISCOVERY, provs.get(0).getJobType());
          assertEquals(3, provs.get(0).getNodes4Discovery().length);
          assertEquals("./a", provs.get(0).getNodes4Discovery()[0]);
          assertEquals("./b", provs.get(0).getNodes4Discovery()[1]);
          assertEquals("./c", provs.get(0).getNodes4Discovery()[2]);
        }

        {
          factory.beginTransaction();
          List<ProvisionJob> jobs = jobBean.submitSelectedJobs(device3);
          factory.commit();
          assertNotNull(jobs);
          assertEquals(1, jobs.size());
          
          ProvisionJobBean provisionBean = factory.createProvisionJobBean();
          List<ProvisionJob> provs = provisionBean.findJobsQueued(device3, true);
          assertNotNull(provs);
          assertEquals(1, provs.size());
          assertEquals(jobs.get(0).getID(), provs.get(0).getID());
          
          assertEquals(ProvisionJob.JOB_TYPE_DISCOVERY, provs.get(0).getJobType());
          assertEquals(3, provs.get(0).getNodes4Discovery().length);
          assertEquals("./1", provs.get(0).getNodes4Discovery()[0]);
          assertEquals("./2", provs.get(0).getNodes4Discovery()[1]);
          assertEquals("./3", provs.get(0).getNodes4Discovery()[2]);
        }
    } catch (Exception e) {
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
}
