/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestProfileConfigBean.java,v 1.24 2008/06/16 10:11:14 zhao Exp $
 * $Revision: 1.24 $
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
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;

/**
 * @author Zhao DongLu
 * 
 */
public class TestProfileConfigBean extends TestCase {

  private static Log log = LogFactory.getLog(TestProfileConfigBean.class);

  private static final String BASE_DIR = AllTests.BASE_DIR;

  /**
   * Profile Template XML File for Testing
   */
  private static final String FILENAME_PROFILE_METADATA = BASE_DIR + "/metadata/profile/metadata/profile.test.xml";

  /**
   * ProfileTemplateEntity name for testcase
   */
  private static final String PROFILE_TEMPLATE_NAME_4_NAP = "TEST.Profile Template#1";

  private static final String PROFILE_TEMPLATE_NAME_4_PROXY = "Test.Proxy Template";

  private static final String PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP = "TEST.Email Template";

  private static final String PROFILE_TEMPLATE_NAME_4_MMS = "TEST.MMS Template";

  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID = "Test.Carrier";

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

    this.setupTemplates();
    this.setUpCarriers();
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();

    this.tearDownCarriers();
  }

  private void setupTemplates() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    InputStream in = null;
    try {
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
      e.printStackTrace();
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void setUpCarriers() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    CarrierBean carrierBean = factory.createCarrierBean();
    try {
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
      e.printStackTrace();
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void tearDownCarriers() throws Exception {
    try {
      // CarrierEntity carrier =
      // this.carrierBean.getCarrierByExternalID(Carrier_External_ID);
      // carrierBean.delete(carrier);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /*
   * Test method for 'com.npower.dm.management.ProfileConfigBean'
   */
  public void testAddNAP() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    ProfileConfigBean bean = factory.createProfileConfigBean();
    try {
      ProfileTemplate template = templateBean.getTemplateByName(TestProfileConfigBean.PROFILE_TEMPLATE_NAME_4_NAP);
      assertEquals(template.getName(), TestProfileConfigBean.PROFILE_TEMPLATE_NAME_4_NAP);

      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      assertNotNull(carrier);

      factory.beginTransaction();

      ProfileConfig configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.NAP.ConfigProfile.Name");
      if (configProfile != null) {
        bean.deleteProfileConfig(configProfile);
      }

      configProfile = bean.newProfileConfigInstance("Test.NAP.ConfigProfile.Name", carrier, template,
          ProfileConfig.PROFILE_TYPE_NAP);

      // Add
      bean.update(configProfile);

      assertTrue(configProfile.getID() > 0);

      // Load
      configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.NAP.ConfigProfile.Name");
      assertNotNull(configProfile);
      assertEquals(configProfile.getName(), "Test.NAP.ConfigProfile.Name");
      assertNotNull(configProfile.getCarrier());
      assertEquals(configProfile.getCarrier().getExternalID(), Carrier_External_ID);
      assertNotNull(configProfile.getProfileTemplate());
      assertEquals(configProfile.getProfileTemplate().getName(), PROFILE_TEMPLATE_NAME_4_NAP);

      // Set this ProfileConfigEntity as Bootstrap ProfileConfigEntity
      carrier.setBootstrapNapProfile(configProfile);
      carrierBean.update(carrier);
      factory.commit();

      // Check Bootstrap NAP for the CarrierEntity
      carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      assertEquals(carrier.getBootstrapNapProfile().getName(), "Test.NAP.ConfigProfile.Name");

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

      String name8 = "No_Exists_Attribute_Name";
      String value8 = "TEST Bearer Direction";
      ProfileAttributeValue av8 = configProfile.getProfileAttributeValue(name8);
      assertNull(av8);

      factory.beginTransaction();
      bean.setAttributeValue(configProfile, name1, value1);
      bean.setAttributeValue(configProfile, name2, value2);
      bean.setAttributeValue(configProfile, name3, value3);
      bean.setAttributeValue(configProfile, name4, value4);
      bean.setAttributeValue(configProfile, name5, value5);
      bean.setAttributeValue(configProfile, name6, value6);
      bean.setAttributeValue(configProfile, name7, value7);
      try {
        bean.setAttributeValue(configProfile, name8, value8);
        // Test failue, with a No_Exists_Name will throw DMException
        assertTrue(false);
      } catch (DMException e) {
        assertTrue(true);
      }
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

      av6 = configProfile.getProfileAttributeValue( name6);
      assertNotNull(av6);
      assertEquals(av6.getStringValue(), value6);
      assertEquals(av6.getProfileAttribute().getName(), name6);
      assertNotNull(av6.getRawData());

      av7 = configProfile.getProfileAttributeValue(name7);
      assertNotNull(av7);
      assertEquals(av7.getStringValue(), value7);
      assertEquals(av7.getProfileAttribute().getName(), name7);
      assertNotNull(av7.getRawData());

      av8 = configProfile.getProfileAttributeValue(name8);
      assertNull(av8);

      ProfileAttributeValue[] avs = configProfile.getAttributeValues();
      assertEquals(7, avs.length);
      // Test sorted by value_index
      assertEquals(avs[0].getProfileAttribute().getName(), name1);
      assertEquals(avs[1].getProfileAttribute().getName(), name2);
      assertEquals(avs[2].getProfileAttribute().getName(), name3);
      assertEquals(avs[3].getProfileAttribute().getName(), name4);
      assertEquals(avs[4].getProfileAttribute().getName(), name5);
      assertEquals(avs[5].getProfileAttribute().getName(), name6);
      assertEquals(avs[6].getProfileAttribute().getName(), name7);

      // Delete
      factory.beginTransaction();

      bean.deleteProfileConfig(configProfile);
      configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.NAP.ConfigProfile.Name");
      assertNull(configProfile);

      factory.commit();

    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  /*
   * Test method for 'com.npower.dm.management.ProfileConfigBean'
   */
  public void testAddProxy() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    ProfileConfigBean bean = factory.createProfileConfigBean();
    try {
      ProfileTemplate template = templateBean.getTemplateByName(PROFILE_TEMPLATE_NAME_4_PROXY);
      assertEquals(template.getName(), PROFILE_TEMPLATE_NAME_4_PROXY);

      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      assertNotNull(carrier);

      factory.beginTransaction();

      ProfileConfig configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.PROXY.ConfigProfile.Name");
      if (configProfile != null) {
        bean.deleteProfileConfig(configProfile);
      }

      configProfile = bean.newProfileConfigInstance("Test.PROXY.ConfigProfile.Name", carrier, template,
          ProfileConfig.PROFILE_TYPE_PROXY);

      // Add
      bean.update(configProfile);

      assertTrue(configProfile.getID() > 0);

      // Load
      configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.PROXY.ConfigProfile.Name");
      assertNotNull(configProfile);
      assertEquals(configProfile.getName(), "Test.PROXY.ConfigProfile.Name");
      assertNotNull(configProfile.getCarrier());
      assertEquals(configProfile.getCarrier().getExternalID(), Carrier_External_ID);
      assertNotNull(configProfile.getProfileTemplate());
      assertEquals(configProfile.getProfileTemplate().getName(), PROFILE_TEMPLATE_NAME_4_PROXY);

      // Delete
      bean.deleteProfileConfig(configProfile);
      configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.PROXY.ConfigProfile.Name");
      assertNull(configProfile);

      factory.commit();

    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  /*
   * Test method for 'com.npower.dm.management.ProfileConfigBean'
   */
  public void testAddEmailWithNAP() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    ProfileConfigBean bean = factory.createProfileConfigBean();
    try {
      ProfileTemplate template = templateBean.getTemplateByName(PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP);
      assertEquals(template.getName(), PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP);

      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      assertNotNull(carrier);

      factory.beginTransaction();

      ProfileConfig configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.Email.ConfigProfile.Name");
      if (configProfile != null) {
        bean.deleteProfileConfig(configProfile);
      }

      configProfile = bean.newProfileConfigInstance("Test.Email.ConfigProfile.Name", carrier, template,
          ProfileConfig.PROFILE_TYPE_APPLICATION);

      // Add
      bean.update(configProfile);
      factory.commit();

      assertTrue(configProfile.getID() > 0);

      // Setup a NAP Profile for the Profile
      factory.beginTransaction();
      ProfileConfig profileNAP = null;
      ProfileConfig profileProxy = null;
      {
        profileNAP = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.NAP.ConfigProfile.Name");
        if (profileNAP != null) {
          bean.deleteProfileConfig(profileNAP);
        }

        ProfileTemplate templateNAP = templateBean.getTemplateByName(PROFILE_TEMPLATE_NAME_4_NAP);
        assertEquals(templateNAP.getName(), PROFILE_TEMPLATE_NAME_4_NAP);
        profileNAP = bean.newProfileConfigInstance("Test.NAP.ConfigProfile.Name", carrier, templateNAP,
            ProfileConfig.PROFILE_TYPE_NAP);

        // Add NAP Profile into DM inventory
        bean.update(profileNAP);
        assertTrue(profileNAP.getID() > 0);

        profileProxy = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.PROXY.ConfigProfile.Name");
        if (profileProxy != null) {
          bean.deleteProfileConfig(profileProxy);
        }

        ProfileTemplate templateProxy = templateBean.getTemplateByName(PROFILE_TEMPLATE_NAME_4_PROXY);
        assertEquals(templateProxy.getName(), PROFILE_TEMPLATE_NAME_4_PROXY);
        profileProxy = bean.newProfileConfigInstance("Test.PROXY.ConfigProfile.Name", carrier, templateProxy,
            ProfileConfig.PROFILE_TYPE_PROXY);

        // Add Proxy Profile into DM inventory
        bean.update(profileProxy);
        assertTrue(profileProxy.getID() > 0);
      }
      // Attach the NAPProfile to Email Profile
      configProfile.setNAPProfile(profileNAP);
      configProfile.setProxyProfile(profileProxy);
      bean.update(configProfile);
      factory.commit();

      // Load
      configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.Email.ConfigProfile.Name");
      assertNotNull(configProfile);
      assertEquals(configProfile.getName(), "Test.Email.ConfigProfile.Name");
      assertNotNull(configProfile.getCarrier());
      assertEquals(configProfile.getCarrier().getExternalID(), Carrier_External_ID);
      assertNotNull(configProfile.getProfileTemplate());
      assertEquals(configProfile.getProfileTemplate().getName(), PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP);
      assertEquals(configProfile.getNAPProfile().getName(), "Test.NAP.ConfigProfile.Name");
      assertEquals(configProfile.getProxyProfile().getName(), "Test.PROXY.ConfigProfile.Name");

      // Delete
      factory.beginTransaction();
      bean.deleteProfileConfig(configProfile);
      configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.Email.ConfigProfile.Name");
      assertNull(configProfile);

      factory.commit();

    } catch (DMException e) {
      e.printStackTrace();
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  /*
   * Test method for 'com.npower.dm.management.ProfileConfigBean'
   */
  public void testAddMMSWithProxy() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    ProfileConfigBean bean = factory.createProfileConfigBean();
    try {
      ProfileTemplate template = templateBean.getTemplateByName(PROFILE_TEMPLATE_NAME_4_MMS);
      assertEquals(template.getName(), PROFILE_TEMPLATE_NAME_4_MMS);

      Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
      assertNotNull(carrier);

      factory.beginTransaction();

      ProfileConfig configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.MMS.ConfigProfile.Name");
      if (configProfile != null) {
        bean.deleteProfileConfig(configProfile);
      }

      configProfile = bean.newProfileConfigInstance("Test.MMS.ConfigProfile.Name", carrier, template,
          ProfileConfig.PROFILE_TYPE_SERVICE);

      // Add
      bean.update(configProfile);

      assertTrue(configProfile.getID() > 0);

      // Load
      configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.MMS.ConfigProfile.Name");
      assertNotNull(configProfile);
      assertEquals(configProfile.getName(), "Test.MMS.ConfigProfile.Name");
      assertNotNull(configProfile.getCarrier());
      assertEquals(configProfile.getCarrier().getExternalID(), Carrier_External_ID);
      assertNotNull(configProfile.getProfileTemplate());
      assertEquals(configProfile.getProfileTemplate().getName(), PROFILE_TEMPLATE_NAME_4_MMS);

      // Delete
      bean.deleteProfileConfig(configProfile);
      configProfile = bean.getProfileConfigByName(Carrier_External_ID, template.getProfileCategory().getName(), "Test.MMS.ConfigProfile.Name");
      assertNull(configProfile);

      factory.commit();

    } catch (DMException e) {
      e.printStackTrace();
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestProfileConfigBean.class);
  }

}
