/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/TestProfileConvertor4DM.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.4 $
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
import com.npower.wap.omacp.parameters.AppAuthLevel;
import com.npower.wap.omacp.parameters.AppID;

/**
 * TestCase: convert NAP Profile into OMAClientProvSettings
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestProfileConvertor4DM extends BaseTestCase4ProfileConvertor {

  /**
   * 
   */
  private static final String DM_DEFAULT_TEMPLATE_NAME = "DM Default Template";

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
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4DM#getSupportedCategoryName()}.
   */
  public void testGetSupportedCategoryName() throws Exception {
    ProfileConvertor convertor = new ProfileConvertor4DM();
    assertEquals(ProfileCategory.DM_CATEGORY_NAME, convertor.getSupportedCategoryName());
  }

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4DM#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   * Test: DM Profile referenced a Proxy Profile
   */
  public void testConvertSimpleProfile()  throws Exception {
    
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileTemplate napTemplate = templateBean.getTemplateByName("NAP Default Template");
      assertNotNull(napTemplate);
      ProfileTemplate proxyTemplate = templateBean.getTemplateByName("Proxy Default Template");
      assertNotNull(proxyTemplate);
      ProfileTemplate dmTemplate = templateBean.getTemplateByName(TestProfileConvertor4DM.DM_DEFAULT_TEMPLATE_NAME);
      assertNotNull(dmTemplate);
      
      CarrierBean carrierBean = factory.createCarrierBean();
      Carrier carrier = carrierBean.getCarrierByExternalID(CARRIER_ID);
      
      // Create a NAP Profile
      String napProfileName = "NAP Profile";
      ProfileConfig napProfile = profileBean.newProfileConfigInstance(napProfileName, carrier, napTemplate, napTemplate.getProfileCategory().getName());

      factory.beginTransaction();
      profileBean.update(napProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(napProfile, "NAME", napProfileName);
      profileBean.setAttributeValue(napProfile, "BEARER", "GSM-GPRS");
      profileBean.setAttributeValue(napProfile, "NAP-ADDRTYPE", "APN");
      profileBean.setAttributeValue(napProfile, "NAP-ADDRESS", "cmwap");
      factory.commit();
      
      // Create a DM Profile
      String dmProfileName = "DM Profile";
      ProfileConfig dmProfile = profileBean.newProfileConfigInstance(dmProfileName, carrier, dmTemplate, dmTemplate.getProfileCategory().getName());
      dmProfile.setNAPProfile(napProfile);
      
      factory.beginTransaction();
      profileBean.update(dmProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(dmProfile, "Name", dmProfileName);
      profileBean.setAttributeValue(dmProfile, "ServerAddr", "http://ds.otas.mobi:8080/otas-dm/dm");
      profileBean.setAttributeValue(dmProfile, "ServerId", "otasdm");
      profileBean.setAttributeValue(dmProfile, "ServerPW", "otasdm_pwd");
      profileBean.setAttributeValue(dmProfile, "ClientId", "user");
      profileBean.setAttributeValue(dmProfile, "ClientPW", "pwd");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4DM();
      OMAClientProvSettings settings = convertor.convertToOMAClientProv(dmProfile);
      assertNotNull(settings);
      
      // Assert Nap Definition
      assertEquals(1, settings.getNapDefElements().size());
      assertNotNull(settings.getNapDefElements().get(0));
      assertEquals(napProfileName, 
                    settings.getNapDefElements().get(0).getName());
      assertEquals("GSM-GPRS", 
                    settings.getNapDefElements().get(0).getBearers().get(0).toString());
      assertEquals("APN", 
                    settings.getNapDefElements().get(0).getNapAddrType().toString());
      assertEquals("cmwap", 
                    settings.getNapDefElements().get(0).getNapAddress());
      assertEquals(0, settings.getNapDefElements().get(0).getNapAuthInfos().size());
      
      // Assert Proxy Definition
      assertEquals(0, settings.getPXLogicalElements().size());
      
      // Assert DM Definition
      assertEquals(1, settings.getApplicationElements().size());
      assertNotNull(settings.getApplicationElements().get(0));
      assertEquals(dmProfileName, settings.getApplicationElements().get(0).getName());
      assertEquals(AppID.W7_SyncML_Device_Management.toString(), settings.getApplicationElements().get(0).getAppID());
      assertEquals(1, settings.getApplicationElements().get(0).getAddrs().size());
      assertEquals("http://ds.otas.mobi:8080/otas-dm/dm", settings.getApplicationElements().get(0).getAddrs().get(0));
      assertEquals(2, settings.getApplicationElements().get(0).getAppAuthElements().size());
      assertEquals(AppAuthLevel.APPSRV, settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthLevel());
      assertEquals("otasdm", settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthName());
      assertEquals("otasdm_pwd", settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthSecret());
      assertEquals(AppAuthLevel.CLIENT, settings.getApplicationElements().get(0).getAppAuthElements().get(1).getAppAuthLevel());
      assertEquals("user", settings.getApplicationElements().get(0).getAppAuthElements().get(1).getAppAuthName());
      assertEquals("pwd", settings.getApplicationElements().get(0).getAppAuthElements().get(1).getAppAuthSecret());
      
      // Test linked relation between dm and nap
      assertEquals(0,
                   settings.getApplicationElements().get(0).getToProxies().size());
      assertEquals(1,
                   settings.getApplicationElements().get(0).getToNAPIDs().size());
      assertEquals( settings.getNapDefElements().get(0).getNapID(),
                   settings.getApplicationElements().get(0).getToNAPIDs().get(0));
      
    } catch (Exception ex) {
      throw ex;
    } finally {
      factory.release();
    }
    
  }

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4DM#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   * Test: DM Profile referenced a Proxy Profile
   */
  public void testConvertComplexProfile()  throws Exception {
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileTemplate napTemplate = templateBean.getTemplateByName("NAP Default Template");
      assertNotNull(napTemplate);
      ProfileTemplate proxyTemplate = templateBean.getTemplateByName("Proxy Default Template");
      assertNotNull(proxyTemplate);
      ProfileTemplate dmTemplate = templateBean.getTemplateByName(TestProfileConvertor4DM.DM_DEFAULT_TEMPLATE_NAME);
      assertNotNull(dmTemplate);
      
      CarrierBean carrierBean = factory.createCarrierBean();
      Carrier carrier = carrierBean.getCarrierByExternalID(CARRIER_ID);
      
      // Create a NAP Profile
      String napProfileName = "NAP Profile";
      ProfileConfig napProfile = profileBean.newProfileConfigInstance(napProfileName, carrier, napTemplate, napTemplate.getProfileCategory().getName());

      factory.beginTransaction();
      profileBean.update(napProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(napProfile, "NAME", napProfileName);
      profileBean.setAttributeValue(napProfile, "BEARER", "GSM-GPRS");
      profileBean.setAttributeValue(napProfile, "NAP-ADDRTYPE", "APN");
      profileBean.setAttributeValue(napProfile, "NAP-ADDRESS", "cmwap");
      factory.commit();
      
      // Create a Proxy Profile
      String proxyProfileName = "Proxy Profile";
      ProfileConfig proxyProfile = profileBean.newProfileConfigInstance(proxyProfileName, carrier, proxyTemplate, proxyTemplate.getProfileCategory().getName());
      proxyProfile.setNAPProfile(napProfile);
      
      factory.beginTransaction();
      profileBean.update(proxyProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(proxyProfile, "NAME", proxyProfileName);
      profileBean.setAttributeValue(proxyProfile, "STARTPAGE", "http://www.monternet.com");
      profileBean.setAttributeValue(proxyProfile, "PXADDRTYPE", "IPV4");
      profileBean.setAttributeValue(proxyProfile, "PXADDR", "10.0.0.172");
      profileBean.setAttributeValue(proxyProfile, "PXPHYSICAL PORTNBR", "8080");
      factory.commit();
      
      // Create a DM Profile
      String dmProfileName = "DM Profile";
      ProfileConfig dmProfile = profileBean.newProfileConfigInstance(dmProfileName, carrier, dmTemplate, dmTemplate.getProfileCategory().getName());
      dmProfile.setProxyProfile(proxyProfile);
      
      factory.beginTransaction();
      profileBean.update(dmProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(dmProfile, "Name", dmProfileName);
      profileBean.setAttributeValue(dmProfile, "ServerAddr", "http://ds.otas.mobi:8080/otas-dm/dm");
      profileBean.setAttributeValue(dmProfile, "ServerId", "otasdm");
      profileBean.setAttributeValue(dmProfile, "ServerPW", "otasdm_pwd");
      profileBean.setAttributeValue(dmProfile, "ClientId", "user");
      profileBean.setAttributeValue(dmProfile, "ClientPW", "pwd");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4DM();
      OMAClientProvSettings settings = convertor.convertToOMAClientProv(dmProfile);
      assertNotNull(settings);
      
      // Assert Nap Definition
      assertEquals(1, settings.getNapDefElements().size());
      assertNotNull(settings.getNapDefElements().get(0));
      assertEquals(napProfileName, 
                    settings.getNapDefElements().get(0).getName());
      assertEquals("GSM-GPRS", 
                    settings.getNapDefElements().get(0).getBearers().get(0).toString());
      assertEquals("APN", 
                    settings.getNapDefElements().get(0).getNapAddrType().toString());
      assertEquals("cmwap", 
                    settings.getNapDefElements().get(0).getNapAddress());
      assertEquals(0, settings.getNapDefElements().get(0).getNapAuthInfos().size());
      
      // Assert Proxy Definition
      assertEquals(1, settings.getPXLogicalElements().size());
      assertNotNull(settings.getPXLogicalElements().get(0));
      assertEquals(proxyProfileName, 
                    settings.getPXLogicalElements().get(0).getName());
      assertEquals("http://www.monternet.com", 
                    settings.getPXLogicalElements().get(0).getStartPage());
      assertEquals(1, 
                    settings.getPXLogicalElements().get(0).getPxPhysicals().size());
      assertEquals("IPV4", 
                   settings.getPXLogicalElements().get(0).getPxPhysicals().get(0).getPxAddrType().toString());
      assertEquals("10.0.0.172", 
                   settings.getPXLogicalElements().get(0).getPxPhysicals().get(0).getPxAddr());
      assertEquals(1, 
                   settings.getPXLogicalElements().get(0).getPxPhysicals().get(0).getPorts().size());
      assertEquals("8080", 
                   settings.getPXLogicalElements().get(0).getPxPhysicals().get(0).getPorts().get(0).getPortNBR());
      assertEquals(0, 
                   settings.getPXLogicalElements().get(0).getPxAuthInfos().size());
      
      // Assert DM Definition
      assertEquals(1, settings.getApplicationElements().size());
      assertNotNull(settings.getApplicationElements().get(0));
      assertEquals(dmProfileName, settings.getApplicationElements().get(0).getName());
      assertEquals(AppID.W7_SyncML_Device_Management.toString(), settings.getApplicationElements().get(0).getAppID());
      assertEquals(1, settings.getApplicationElements().get(0).getAddrs().size());
      assertEquals("http://ds.otas.mobi:8080/otas-dm/dm", settings.getApplicationElements().get(0).getAddrs().get(0));
      assertEquals(2, settings.getApplicationElements().get(0).getAppAuthElements().size());
      assertEquals(AppAuthLevel.APPSRV, settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthLevel());
      assertEquals("otasdm", settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthName());
      assertEquals("otasdm_pwd", settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthSecret());
      assertEquals(AppAuthLevel.CLIENT, settings.getApplicationElements().get(0).getAppAuthElements().get(1).getAppAuthLevel());
      assertEquals("user", settings.getApplicationElements().get(0).getAppAuthElements().get(1).getAppAuthName());
      assertEquals("pwd", settings.getApplicationElements().get(0).getAppAuthElements().get(1).getAppAuthSecret());
      
      // Test linked relation between nap and proxy
      assertEquals(1,
                   settings.getPXLogicalElements().get(0).getPxPhysicals().get(0).getToNAPIDs().size());
      assertEquals(settings.getNapDefElements().get(0).getNapID(),
                   settings.getPXLogicalElements().get(0).getPxPhysicals().get(0).getToNAPIDs().get(0));
      // Test linked relation between dm and proxy
      assertEquals(1,
                   settings.getApplicationElements().get(0).getToProxies().size());
      assertEquals(settings.getPXLogicalElements().get(0).getProxyID(),
                   settings.getApplicationElements().get(0).getToProxies().get(0));
      
    } catch (Exception ex) {
      throw ex;
    } finally {
      factory.release();
    }
    
  }

}
