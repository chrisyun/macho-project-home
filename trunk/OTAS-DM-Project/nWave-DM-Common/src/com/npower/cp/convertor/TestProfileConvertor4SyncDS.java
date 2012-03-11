/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/TestProfileConvertor4SyncDS.java,v 1.5 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.5 $
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
import com.npower.wap.omacp.parameters.AppID;

/**
 * TestCase: convert NAP Profile into OMAClientProvSettings
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestProfileConvertor4SyncDS extends BaseTestCase4ProfileConvertor {

  /**
   * 
   */
  private static final String SYNC_DS_DEFAULT_TEMPLATE_NAME = "SyncDS Default Template";

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
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4SyncDS#getSupportedCategoryName()}.
   */
  public void testGetSupportedCategoryName() throws Exception {
    ProfileConvertor convertor = new ProfileConvertor4SyncDS();
    assertEquals(ProfileCategory.SYNC_DS_CATEGORY_NAME, convertor.getSupportedCategoryName());
  }

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4SyncDS#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   * Test: SyncDS Profile referenced a Proxy Profile
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
      ProfileTemplate syncdsTemplate = templateBean.getTemplateByName(TestProfileConvertor4SyncDS.SYNC_DS_DEFAULT_TEMPLATE_NAME);
      assertNotNull(syncdsTemplate);
      
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
      
      // Create a SyncDS Profile
      String syncdsProfileName = "SyncDS Profile";
      ProfileConfig syncdsProfile = profileBean.newProfileConfigInstance(syncdsProfileName, carrier, syncdsTemplate, syncdsTemplate.getProfileCategory().getName());
      syncdsProfile.setNAPProfile(napProfile);
      
      factory.beginTransaction();
      profileBean.update(syncdsProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(syncdsProfile, "Display Name", syncdsProfileName);
      profileBean.setAttributeValue(syncdsProfile, "Server Address", "http://ds.otas.mobi:80/otas-ds/ds");
      profileBean.setAttributeValue(syncdsProfile, "Username", "abc");
      profileBean.setAttributeValue(syncdsProfile, "Password", "pwd");
      profileBean.setAttributeValue(syncdsProfile, "Contacts DB", "./cards");
      profileBean.setAttributeValue(syncdsProfile, "Contacts DB ContentType", "text/x-vcard");
      profileBean.setAttributeValue(syncdsProfile, "Calendar DB", "./cal");
      profileBean.setAttributeValue(syncdsProfile, "Calendar DB ContentType", "text/x-vcalendar");
      profileBean.setAttributeValue(syncdsProfile, "Notes DB", "./notes");
      profileBean.setAttributeValue(syncdsProfile, "Notes DB ContentType", "text/plain");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4SyncDS();
      OMAClientProvSettings settings = convertor.convertToOMAClientProv(syncdsProfile);
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
      
      // Assert SyncDS Definition
      assertEquals(1, settings.getApplicationElements().size());
      assertNotNull(settings.getApplicationElements().get(0));
      assertEquals(syncdsProfileName, settings.getApplicationElements().get(0).getName());
      assertEquals(AppID.W5_SyncML_PUSH_Application.toString(), settings.getApplicationElements().get(0).getAppID());
      assertEquals(1, settings.getApplicationElements().get(0).getAddrs().size());
      assertEquals("http://ds.otas.mobi:80/otas-ds/ds", settings.getApplicationElements().get(0).getAddrs().get(0));
      assertEquals(1, settings.getApplicationElements().get(0).getAppAuthElements().size());
      assertEquals("abc", settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthName());
      assertEquals("pwd", settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthSecret());
      assertEquals(3, settings.getApplicationElements().get(0).getResourceElements().size());
      assertEquals("./cards", settings.getApplicationElements().get(0).getResourceElements().get(0).getUri());
      assertEquals("text/x-vcard", settings.getApplicationElements().get(0).getResourceElements().get(0).getAppAccept());
      assertEquals("./cal", settings.getApplicationElements().get(0).getResourceElements().get(1).getUri());
      assertEquals("text/x-vcalendar", settings.getApplicationElements().get(0).getResourceElements().get(1).getAppAccept());
      assertEquals("./notes", settings.getApplicationElements().get(0).getResourceElements().get(2).getUri());
      assertEquals("text/plain", settings.getApplicationElements().get(0).getResourceElements().get(2).getAppAccept());
      
      // Test linked relation between syncds and nap
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
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4SyncDS#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   * Test: SyncDS Profile referenced a Proxy Profile
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
      ProfileTemplate syncdsTemplate = templateBean.getTemplateByName(TestProfileConvertor4SyncDS.SYNC_DS_DEFAULT_TEMPLATE_NAME);
      assertNotNull(syncdsTemplate);
      
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
      
      // Create a SyncDS Profile
      String syncdsProfileName = "SyncDS Profile";
      ProfileConfig syncdsProfile = profileBean.newProfileConfigInstance(syncdsProfileName, carrier, syncdsTemplate, syncdsTemplate.getProfileCategory().getName());
      syncdsProfile.setProxyProfile(proxyProfile);
      
      factory.beginTransaction();
      profileBean.update(syncdsProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(syncdsProfile, "Display Name", syncdsProfileName);
      profileBean.setAttributeValue(syncdsProfile, "Server Address", "http://ds.otas.mobi:80/otas-ds/ds");
      profileBean.setAttributeValue(syncdsProfile, "Username", "abc");
      profileBean.setAttributeValue(syncdsProfile, "Password", "pwd");
      profileBean.setAttributeValue(syncdsProfile, "Contacts DB", "./cards");
      profileBean.setAttributeValue(syncdsProfile, "Contacts DB ContentType", "text/x-vcard");
      profileBean.setAttributeValue(syncdsProfile, "Calendar DB", "./cal");
      profileBean.setAttributeValue(syncdsProfile, "Calendar DB ContentType", "text/x-vcalendar");
      profileBean.setAttributeValue(syncdsProfile, "Notes DB", "./notes");
      profileBean.setAttributeValue(syncdsProfile, "Notes DB ContentType", "text/plain");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4SyncDS();
      OMAClientProvSettings settings = convertor.convertToOMAClientProv(syncdsProfile);
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
      
      // Assert SyncDS Definition
      // Assert SyncDS Definition
      assertEquals(1, settings.getApplicationElements().size());
      assertNotNull(settings.getApplicationElements().get(0));
      assertEquals(syncdsProfileName, settings.getApplicationElements().get(0).getName());
      assertEquals(AppID.W5_SyncML_PUSH_Application.toString(), settings.getApplicationElements().get(0).getAppID());
      assertEquals(1, settings.getApplicationElements().get(0).getAddrs().size());
      assertEquals("http://ds.otas.mobi:80/otas-ds/ds", settings.getApplicationElements().get(0).getAddrs().get(0));
      assertEquals(1, settings.getApplicationElements().get(0).getAppAuthElements().size());
      assertEquals("abc", settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthName());
      assertEquals("pwd", settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthSecret());
      assertEquals(3, settings.getApplicationElements().get(0).getResourceElements().size());
      assertEquals("./cards", settings.getApplicationElements().get(0).getResourceElements().get(0).getUri());
      assertEquals("text/x-vcard", settings.getApplicationElements().get(0).getResourceElements().get(0).getAppAccept());
      assertEquals("./cal", settings.getApplicationElements().get(0).getResourceElements().get(1).getUri());
      assertEquals("text/x-vcalendar", settings.getApplicationElements().get(0).getResourceElements().get(1).getAppAccept());
      assertEquals("./notes", settings.getApplicationElements().get(0).getResourceElements().get(2).getUri());
      assertEquals("text/plain", settings.getApplicationElements().get(0).getResourceElements().get(2).getAppAccept());
      
      // Test linked relation between nap and proxy
      assertEquals(1,
                   settings.getPXLogicalElements().get(0).getPxPhysicals().get(0).getToNAPIDs().size());
      assertEquals(settings.getNapDefElements().get(0).getNapID(),
                   settings.getPXLogicalElements().get(0).getPxPhysicals().get(0).getToNAPIDs().get(0));
      // Test linked relation between syncds and proxy
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
