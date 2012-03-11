/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestProvisionJobBean4ProfileAssignment.java,v 1.3 2008/06/16 10:11:14 zhao Exp $
  * $Revision: 1.3 $
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

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Subscriber;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/06/16 10:11:14 $
 */
public class TestProvisionJobBean4ProfileAssignment extends TestCase {

  private static Log log = LogFactory.getLog(TestProfileConfigBean.class);

  private static final String BASE_DIR = AllTests.BASE_DIR;

  /**
   * Profile Template XML File for Testing
   */
  private static final String FILENAME_PROFILE_METADATA = BASE_DIR + "/metadata/profile/metadata/profile.test.xml";

  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID = "Test.Carrier";

  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER";

  private static final String MODEL_External_ID = "TEST.MODEL";

  /**
   * ProfileTemplateEntity name for testcase
   */
  private static final String PROFILE_TEMPLATE_NAME_4_NAP = "TEST.Profile Template#1";
  
  private ProfileAssignment assignment1 = null;

  //private static final String PROFILE_TEMPLATE_NAME_4_PROXY = "Test.Proxy Template";

  //private static final String PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP = "TEST.Email Template";

  //private static final String PROFILE_TEMPLATE_NAME_4_MMS = "TEST.MMS Template";

  /**
   * DeviceEntity IMEI, ExternalID
   */
  private String imei = "1234567890123456";

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        setupTemplates(factory);
        setUpCarriers(factory);
    
        setupProfileNAP(factory);
    
        setupModel(factory);
    
        setupDevice(factory);
        
        setupAssignments(factory);
    } catch (Exception e) {
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
        tearDownAssignments(factory);
    
        tearDownProfiles(factory);
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
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

  public void setUpCarriers(ManagementBeanFactory factory) throws Exception {
    try {
      Country country = factory.createCountryBean().getCountryByISOCode("CN");
      assertNotNull(country);
      assertEquals(country.getCountryCode(), "86");
      CarrierBean carrierBean = factory.createCarrierBean();
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

  public void setupProfileNAP(ManagementBeanFactory factory) throws Exception {
    try {
      CarrierBean carrierBean = factory.createCarrierBean();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      
      ProfileTemplate template = templateBean.getTemplateByName(PROFILE_TEMPLATE_NAME_4_NAP);
      assertEquals(template.getName(), PROFILE_TEMPLATE_NAME_4_NAP);

      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      assertNotNull(carrier);

      ProfileConfig configProfile = profileBean.getProfileConfigByName(Carrier_External_ID, "TEST.NAP", "Test.NAP.ConfigProfile.Name");
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

  public void setupDevice(ManagementBeanFactory factory) throws Exception {
    try {
        CarrierBean carrierBean = factory.createCarrierBean();
        ModelBean modelBean = factory.createModelBean();
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        DeviceBean deviceBean = factory.createDeviceBean();
  
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
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
  
        Device device = deviceBean.getDeviceByExternalID(imei);
        if (device != null) {
          factory.beginTransaction();
          deviceBean.delete(device);
          factory.commit();
        }
  
        // Create a DeviceEntity
        device = deviceBean.newDeviceInstance(subscriber, model, imei);
  
        factory.beginTransaction();
        deviceBean.update(device);
        factory.commit();
  
        // Test found
        device = deviceBean.getDeviceByID("" + device.getID());
        assertNotNull(device);
  
        assertEquals(imei, device.getExternalId());
  
    } catch (DMException e) {
      factory.rollback();
      throw e;
    }
  }
  
  public void tearDownProfiles(ManagementBeanFactory factory) throws Exception {
    try {
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        // Delete all of ProfileConfigEntity created by setupProfileXXX()
        ProfileConfig configProfile = profileBean.getProfileConfigByName(Carrier_External_ID, "TEST.NAP", "Test.NAP.ConfigProfile.Name");
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


  public void setupAssignments(ManagementBeanFactory factory) throws Exception {
    try {
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      ProfileAssignmentBean bean = factory.createProfileAssignmentBean();
      DeviceBean deviceBean = factory.createDeviceBean();
      
      ProfileConfig napProfile = profileBean.getProfileConfigByName(Carrier_External_ID, "TEST.NAP", "Test.NAP.ConfigProfile.Name");
      assertNotNull(napProfile);
      Device device = deviceBean.getDeviceByExternalID(imei);
      assertNotNull(device);

      // Assignment # 1
      factory.beginTransaction();
      assignment1 = bean.newProfileAssignmentInstance(napProfile, device);
      bean.update(assignment1);
      factory.commit();

      // Attach value

      String name1 = "TEST Display Name";
      String value1 = "TEST Display Name Value";
      ProfileAttributeValue av1 = assignment1.getProfileAttributeValue(name1);
      assertNull(av1);

      String name2 = "TEST Bearer Direction";
      String[] value2 = { "a", "b", "c", "d" };
      ProfileAttributeValue av2 = assignment1.getProfileAttributeValue(name2);
      assertNull(av2);

      String name3 = "TEST NAP Address";
      String value3 = "TEST NAP Address";
      ProfileAttributeValue av3 = assignment1.getProfileAttributeValue(name3);
      assertNull(av3);

      String name4 = "TEST NAP Address Type";
      String value4 = "TEST NAP Address Type";
      ProfileAttributeValue av4 = assignment1.getProfileAttributeValue(name4);
      assertNull(av4);

      String name5 = "TEST DNS Address";
      String value5 = "TEST DNS Address";
      ProfileAttributeValue av5 = assignment1.getProfileAttributeValue(name5);
      assertNull(av5);

      String name6 = "TEST Authentication Username";
      String value6 = "TEST Authentication Username";
      ProfileAttributeValue av6 = assignment1.getProfileAttributeValue(name6);
      assertNull(av6);

      String name7 = "TEST Authentication Password";
      String value7 = "TEST Authentication Password";
      ProfileAttributeValue av7 = assignment1.getProfileAttributeValue(name7);
      assertNull(av7);

      factory.beginTransaction();
      bean.setAttributeValue(assignment1, name1, value1);
      bean.setAttributeValue(assignment1, name2, value2);
      bean.setAttributeValue(assignment1, name3, value3);
      bean.setAttributeValue(assignment1, name4, value4);
      bean.setAttributeValue(assignment1, name5, value5);
      bean.setAttributeValue(assignment1, name6, value6);
      bean.setAttributeValue(assignment1, name7, value7);
      factory.commit();

      av1 = assignment1.getProfileAttributeValue(name1);
      assertNotNull(av1);
      assertEquals(av1.getStringValue(), value1);
      assertEquals(av1.getProfileAttribute().getName(), name1);
      assertNotNull(av1.getRawData());

      av2 = assignment1.getProfileAttributeValue(name2);
      assertNotNull(av2);
      assertEquals(av2.getStringValues().length, 4);
      assertEquals(av2.getStringValues()[0].length(), 1);
      assertEquals(av2.getStringValues()[1].length(), 1);
      assertEquals(av2.getStringValues()[2].length(), 1);
      assertEquals(av2.getStringValues()[3].length(), 1);
      assertEquals(av2.getProfileAttribute().getName(), name2);
      // Multi-value mode
      assertNull(av2.getRawData());

      av3 = assignment1.getProfileAttributeValue(name3);
      assertNotNull(av3);
      assertEquals(av3.getStringValue(), value3);
      assertEquals(av3.getProfileAttribute().getName(), name3);
      assertNotNull(av3.getRawData());

      av4 = assignment1.getProfileAttributeValue(name4);
      assertNotNull(av4);
      assertEquals(av4.getStringValue(), value4);
      assertEquals(av4.getProfileAttribute().getName(), name4);
      assertNotNull(av4.getRawData());

      av5 = assignment1.getProfileAttributeValue(name5);
      assertNotNull(av5);
      assertEquals(av5.getStringValue(), value5);
      assertEquals(av5.getProfileAttribute().getName(), name5);
      assertNotNull(av5.getRawData());

      av6 = assignment1.getProfileAttributeValue(name6);
      assertNotNull(av6);
      assertEquals(av6.getStringValue(), value6);
      assertEquals(av6.getProfileAttribute().getName(), name6);
      assertNotNull(av6.getRawData());

      av7 = assignment1.getProfileAttributeValue(name7);
      assertNotNull(av7);
      assertEquals(av7.getStringValue(), value7);
      assertEquals(av7.getProfileAttribute().getName(), name7);
      assertNotNull(av7.getRawData());

      ProfileAttributeValue[] avs = assignment1.getAttributeValues();
      assertEquals(7, avs.length);
      // Test sorted by value_index
      assertEquals(avs[0].getProfileAttribute().getName(), name1);
      assertEquals(avs[1].getProfileAttribute().getName(), name2);
      assertEquals(avs[2].getProfileAttribute().getName(), name3);
      assertEquals(avs[3].getProfileAttribute().getName(), name4);
      assertEquals(avs[4].getProfileAttribute().getName(), name5);
      assertEquals(avs[5].getProfileAttribute().getName(), name6);
      assertEquals(avs[6].getProfileAttribute().getName(), name7);

    } catch (Exception e) {
      factory.rollback();
      throw e;
    }
  }
  
  private void tearDownAssignments(ManagementBeanFactory factory) throws Exception {
    try {
      ProfileAssignmentBean bean = factory.createProfileAssignmentBean();
      factory.beginTransaction();
      bean.deleteProfileAssignment(assignment1);
      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    }
    
  }

  public void testProfileAssignmentJob() throws Exception {
    assertTrue(true);
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
        assertNotNull(carrier);
        
        // Testcase: add a Job
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(imei);
        
        factory.beginTransaction();
        ProvisionJob job1 = jobBean.newJob4Assignment(assignment1);
        long jobID1 = job1.getID();
        factory.commit();
        
        
        // Testcase: load a Job
        job1 = jobBean.loadJobByID(jobID1);
        assertNotNull(job1);
        assertEquals(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job1.getJobType());
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());
        assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job1.getTargetType());
        assertEquals(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job1.getJobType());
        assertEquals(device.getID(), assignment1.getDevice().getID());
        
        ProfileAssignment assign = jobBean.getProfileAssignment(job1, device);
        assertEquals(assign.getID(), assignment1.getID());
        
        // Testcase: disable a job
        factory.beginTransaction();
        jobBean.disable(jobID1);
        factory.commit();
        assertEquals(ProvisionJob.JOB_STATE_DISABLE, job1.getState());
        job1 = jobBean.loadJobByID(jobID1);
        assertNotNull(job1);
        assertEquals(ProvisionJob.JOB_STATE_DISABLE, job1.getState());
       
        // Testcase: cancel a job
        factory.beginTransaction();
        jobBean.cancel(jobID1);
        factory.commit();
        assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job1.getState());
        job1 = jobBean.loadJobByID(jobID1);
        assertNotNull(job1);
        assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job1.getState());
        
        // Testcase: enable a job
        factory.beginTransaction();
        jobBean.enable(jobID1);
        factory.commit();
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());
        job1 = jobBean.loadJobByID(jobID1);
        assertNotNull(job1);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job1.getState());
        
        /**
         * TestCase: 重复创建时, 应该返回同一个Job
         */
        ProvisionJob jobTemp = jobBean.newJob4Assignment(assignment1);
        long jobTempID = jobTemp.getID();
        assertEquals(jobID1, jobTempID);
        
        /**
         * TestCase: Test re-assign
         */
        // Emulate the lifecycle of status.
        {
          ProvisionJobStatus jobStatus = jobBean.getProvisionJobStatus(job1, assignment1.getDevice());
          factory.beginTransaction();
          jobStatus.setState(ProvisionJobStatus.DEVICE_JOB_STATE_DONE);
          jobBean.update(jobStatus);
          factory.commit();
        }
        
        // Detect the status again
        {
          ProvisionJobStatus jobStatus = jobBean.getProvisionJobStatus(job1, assignment1.getDevice());
          assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_DONE, jobStatus.getState());
        }
        
        // Re-Assign the profile, detect the re-assign status.
        factory.beginTransaction();
        ProvisionJob job2 = jobBean.newJob4Assignment(assignment1);
        long jobID2 = job2.getID();
        factory.commit();
        
        job2 = jobBean.loadJobByID(jobID2);
        assertNotNull(job2);
        assertEquals(ProvisionJob.JOB_TYPE_RE_ASSIGN_PROFILE, job2.getJobType());
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job2.getState());
        assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job2.getTargetType());
        
        /**
         * TestCase: Test new-assign
         */
        // Testcase: delete all of Jobs
        factory.beginTransaction();
        jobBean.delete(jobID1);
        jobBean.delete(jobID2);
        factory.commit();
        
        // Re-Assign the profile, detect the new-assign status.
        factory.beginTransaction();
        ProvisionJob job3 = jobBean.newJob4Assignment(assignment1);
        long jobID3 = job3.getID();
        factory.commit();
        
        job2 = jobBean.loadJobByID(jobID3);
        assertNotNull(job3);
        assertEquals(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE, job3.getJobType());
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job3.getState());
        assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job3.getTargetType());
        
        
        // Testcase: delete a Job
        factory.beginTransaction();
        jobBean.delete(jobID3);
        factory.commit();
        
    } catch (Exception e) {
      //factory.rollback();
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestProvisionJobBean4ProfileAssignment.class);
  }

}
