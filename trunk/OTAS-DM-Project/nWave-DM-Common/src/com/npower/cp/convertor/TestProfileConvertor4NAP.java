/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/TestProfileConvertor4NAP.java,v 1.6 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.6 $
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
package com.npower.cp.convertor;



import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.wap.omacp.OMAClientProvSettings;

/**
 * TestCase: convert NAP Profile into OMAClientProvSettings
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestProfileConvertor4NAP extends BaseTestCase4ProfileConvertor {

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.BaseTestCase4ProfileConvertor#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.BaseTestCase4ProfileConvertor#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4NAP#getSupportedCategoryName()}.
   */
  public void testGetSupportedCategoryName() throws Exception {
    ProfileConvertor convertor = new ProfileConvertor4NAP();
    assertEquals(ProfileCategory.NAP_CATEGORY_NAME, convertor.getSupportedCategoryName());
  }

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4NAP#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   */
  public void testConvertSimpleProfile4OMA_CP()  throws Exception {
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileTemplate template = templateBean.getTemplateByName("NAP Default Template");
      assertNotNull(template);
      
      CarrierBean carrierBean = factory.createCarrierBean();
      Carrier carrier = carrierBean.getCarrierByExternalID(CARRIER_ID);
      
      String profileName = "NAP Profile";
      ProfileConfig profile = profileBean.newProfileConfigInstance(profileName, carrier, template, template.getProfileCategory().getName());

      factory.beginTransaction();
      profileBean.update(profile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(profile, "NAME", profileName);
      profileBean.setAttributeValue(profile, "BEARER", "GSM-GPRS");
      profileBean.setAttributeValue(profile, "NAP-ADDRTYPE", "APN");
      profileBean.setAttributeValue(profile, "NAP-ADDRESS", "cmnet");
      //profileBean.setAttributeValue(profile, "AUTHTYPE", "");
      //profileBean.setAttributeValue(profile, "AUTHNAME", "");
      //profileBean.setAttributeValue(profile, "AUTHSECRET", "");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4NAP();
      
      // Test convert for OMA CP
      OMAClientProvSettings settings = convertor.convertToOMAClientProv(profile);
      assertNotNull(settings);
      
      assertEquals(1, settings.getNapDefElements().size());
      assertNotNull(settings.getNapDefElements().get(0));
      assertEquals(profileName, 
                    settings.getNapDefElements().get(0).getName());
      assertEquals("GSM-GPRS", 
                    settings.getNapDefElements().get(0).getBearers().get(0).toString());
      assertEquals("APN", 
                    settings.getNapDefElements().get(0).getNapAddrType().toString());
      assertEquals("cmnet", 
                    settings.getNapDefElements().get(0).getNapAddress());
      assertEquals(0, settings.getNapDefElements().get(0).getNapAuthInfos().size());
      
    } catch (Exception ex) {
      throw ex;
    } finally {
      factory.release();
    }
    
  }

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4NAP#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   */
  public void testConvertComplexProfile4OMA_CP()  throws Exception {
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileTemplate template = templateBean.getTemplateByName("NAP Default Template");
      assertNotNull(template);
      
      CarrierBean carrierBean = factory.createCarrierBean();
      Carrier carrier = carrierBean.getCarrierByExternalID(CARRIER_ID);
      
      String profileName = "NAP Profile";
      ProfileConfig profile = profileBean.newProfileConfigInstance(profileName, carrier, template, template.getProfileCategory().getName());

      factory.beginTransaction();
      profileBean.update(profile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(profile, "NAME", profileName);
      profileBean.setAttributeValue(profile, "BEARER", "GSM-GPRS");
      profileBean.setAttributeValue(profile, "NAP-ADDRTYPE", "APN");
      profileBean.setAttributeValue(profile, "NAP-ADDRESS", "cmnet");
      profileBean.setAttributeValue(profile, "AUTHTYPE", "PAP");
      profileBean.setAttributeValue(profile, "AUTHNAME", "user");
      profileBean.setAttributeValue(profile, "AUTHSECRET", "password");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4NAP();
      
      // Test convert for OMA CP
      OMAClientProvSettings settings = convertor.convertToOMAClientProv(profile);
      assertNotNull(settings);
      
      assertEquals(1, settings.getNapDefElements().size());
      assertNotNull(settings.getNapDefElements().get(0));
      assertEquals(profileName, 
                    settings.getNapDefElements().get(0).getName());
      assertEquals("GSM-GPRS", 
                    settings.getNapDefElements().get(0).getBearers().get(0).toString());
      assertEquals("APN", 
                    settings.getNapDefElements().get(0).getNapAddrType().toString());
      assertEquals("cmnet", 
                    settings.getNapDefElements().get(0).getNapAddress());
      assertEquals(1, settings.getNapDefElements().get(0).getNapAuthInfos().size());
      assertEquals("PAP", settings.getNapDefElements().get(0).getNapAuthInfos().get(0).getAuthType().toString());
      assertEquals("user", settings.getNapDefElements().get(0).getNapAuthInfos().get(0).getAuthName());
      assertEquals("password", settings.getNapDefElements().get(0).getNapAuthInfos().get(0).getAuthSecret());
      
    } catch (Exception ex) {
      throw ex;
    } finally {
      factory.release();
    }
  }

}
