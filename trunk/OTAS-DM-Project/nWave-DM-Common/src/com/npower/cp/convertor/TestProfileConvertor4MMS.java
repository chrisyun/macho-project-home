/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/TestProfileConvertor4MMS.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
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
import com.npower.wap.nokia.NokiaOTASettings;
import com.npower.wap.nokia.NokiaOtaBrowserSettings;
import com.npower.wap.nokia.browser.AddressGPRS;
import com.npower.wap.nokia.browser.Port;
import com.npower.wap.omacp.OMAClientProvSettings;
import com.npower.wap.omacp.parameters.AppID;

/**
 * TestCase: convert NAP Profile into OMAClientProvSettings
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestProfileConvertor4MMS extends BaseTestCase4ProfileConvertor {

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
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4MMS#getSupportedCategoryName()}.
   */
  public void testGetSupportedCategoryName() throws Exception {
    ProfileConvertor convertor = new ProfileConvertor4MMS();
    assertEquals(ProfileCategory.MMS_CATEGORY_NAME, convertor.getSupportedCategoryName());
  }

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4MMS#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   * Test: MMS Profile referenced a Proxy Profile
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
      ProfileTemplate mmsTemplate = templateBean.getTemplateByName("MMS Default Template");
      assertNotNull(mmsTemplate);
      
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
      
      // Create a MMS Profile
      String mmsProfileName = "MMS Profile";
      ProfileConfig mmsProfile = profileBean.newProfileConfigInstance(mmsProfileName, carrier, mmsTemplate, mmsTemplate.getProfileCategory().getName());
      mmsProfile.setNAPProfile(napProfile);
      
      factory.beginTransaction();
      profileBean.update(mmsProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(mmsProfile, "Display Name", mmsProfileName);
      profileBean.setAttributeValue(mmsProfile, "MMSC Server", "http://mmsc.monternet.com");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4MMS();
      OMAClientProvSettings settings = convertor.convertToOMAClientProv(mmsProfile);
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
      
      // Assert MMS Definition
      assertEquals(1, settings.getApplicationElements().size());
      assertNotNull(settings.getApplicationElements().get(0));
      assertEquals(mmsProfileName, settings.getApplicationElements().get(0).getName());
      assertEquals(AppID.W4_MMS_User_Agent.toString(), settings.getApplicationElements().get(0).getAppID());
      assertEquals(1, settings.getApplicationElements().get(0).getAddrs().size());
      assertEquals("http://mmsc.monternet.com", settings.getApplicationElements().get(0).getAddrs().get(0));
      
      // Test linked relation between mms and nap
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
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4MMS#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   * Test: MMS Profile referenced a Proxy Profile
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
      ProfileTemplate mmsTemplate = templateBean.getTemplateByName("MMS Default Template");
      assertNotNull(mmsTemplate);
      
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
      
      // Create a MMS Profile
      String mmsProfileName = "MMS Profile";
      ProfileConfig mmsProfile = profileBean.newProfileConfigInstance(mmsProfileName, carrier, mmsTemplate, mmsTemplate.getProfileCategory().getName());
      mmsProfile.setProxyProfile(proxyProfile);
      
      factory.beginTransaction();
      profileBean.update(mmsProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(mmsProfile, "Display Name", mmsProfileName);
      profileBean.setAttributeValue(mmsProfile, "MMSC Server", "http://mmsc.monternet.com");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4MMS();
      OMAClientProvSettings settings = convertor.convertToOMAClientProv(mmsProfile);
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
      
      // Assert MMS Definition
      assertEquals(1, settings.getApplicationElements().size());
      assertNotNull(settings.getApplicationElements().get(0));
      assertEquals(mmsProfileName, settings.getApplicationElements().get(0).getName());
      assertEquals(AppID.W4_MMS_User_Agent.toString(), settings.getApplicationElements().get(0).getAppID());
      assertEquals(1, settings.getApplicationElements().get(0).getAddrs().size());
      assertEquals("http://mmsc.monternet.com", settings.getApplicationElements().get(0).getAddrs().get(0));
      
      // Test linked relation between nap and proxy
      assertEquals(1,
                   settings.getPXLogicalElements().get(0).getPxPhysicals().get(0).getToNAPIDs().size());
      assertEquals(settings.getNapDefElements().get(0).getNapID(),
                   settings.getPXLogicalElements().get(0).getPxPhysicals().get(0).getToNAPIDs().get(0));
      // Test linked relation between mms and proxy
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

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4MMS#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   * Test: MMS Profile referenced a Proxy Profile
   */
  public void testConvertComplexProfile4Nokia_OTA()  throws Exception {
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileTemplate napTemplate = templateBean.getTemplateByName("NAP Default Template");
      assertNotNull(napTemplate);
      ProfileTemplate proxyTemplate = templateBean.getTemplateByName("Proxy Default Template");
      assertNotNull(proxyTemplate);
      ProfileTemplate mmsTemplate = templateBean.getTemplateByName("MMS Default Template");
      assertNotNull(mmsTemplate);
      
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
      profileBean.setAttributeValue(proxyProfile, "PXPHYSICAL PORTNBR", "9201");
      factory.commit();
      
      // Create a MMS Profile
      String mmsProfileName = "MMS Profile";
      ProfileConfig mmsProfile = profileBean.newProfileConfigInstance(mmsProfileName, carrier, mmsTemplate, mmsTemplate.getProfileCategory().getName());
      mmsProfile.setProxyProfile(proxyProfile);
      
      factory.beginTransaction();
      profileBean.update(mmsProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(mmsProfile, "Display Name", mmsProfileName);
      profileBean.setAttributeValue(mmsProfile, "MMSC Server", "http://mmsc.monternet.com");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4MMS();
      
      // Test convert for OMA CP
      NokiaOTASettings result = convertor.convertToNokiaOTA(mmsProfile);
      assertNotNull(result);
      assertTrue(result instanceof NokiaOtaBrowserSettings);
      
      NokiaOtaBrowserSettings settings = (NokiaOtaBrowserSettings)result;
      
      assertEquals(mmsProfileName, settings.getName());
      assertEquals("http://www.monternet.com", settings.getUrl());
      
      assertEquals(1, settings.getAddresses().size());
      assertTrue(settings.getAddresses().get(0) instanceof AddressGPRS);
      //assertEquals(AddressBearer.GPRS, ((AddressGPRS)(settings.getAddresses().get(0))));
      assertEquals("cmwap", ((AddressGPRS)(settings.getAddresses().get(0))).getGprsAccessPointName());
      assertEquals("10.0.0.172", ((AddressGPRS)(settings.getAddresses().get(0))).getProxy());
      assertEquals(Port.PORT_9201, ((AddressGPRS)(settings.getAddresses().get(0))).getPort());
      
      assertEquals(1, settings.getBookmarks().size());
      assertEquals(proxyProfileName, settings.getBookmarks().get(0).getName());
      assertEquals("http://www.monternet.com", settings.getBookmarks().get(0).getUrl());
      
      assertEquals("http://mmsc.monternet.com", settings.getMmsurl());
    } catch (Exception ex) {
      throw ex;
    } finally {
      factory.release();
    }
    
  }
}
