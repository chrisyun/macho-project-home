/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/TestProfileConvertor4Email.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
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
import com.npower.wap.omacp.parameters.AppID;

/**
 * TestCase: convert NAP Profile into OMAClientProvSettings
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestProfileConvertor4Email extends BaseTestCase4ProfileConvertor {

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
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4Email#getSupportedCategoryName()}.
   */
  public void testGetSupportedCategoryName() throws Exception {
    ProfileConvertor convertor = new ProfileConvertor4Email();
    assertEquals(ProfileCategory.EMAIL_CATEGORY_NAME, convertor.getSupportedCategoryName());
  }

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4Email#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   * Test: Email profile based on POP3 protocol
   */
  public void testConvertSimpleProfileBasedPOP3()  throws Exception {
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileTemplate napTemplate = templateBean.getTemplateByName("NAP Default Template");
      assertNotNull(napTemplate);
      ProfileTemplate proxyTemplate = templateBean.getTemplateByName("Proxy Default Template");
      assertNotNull(proxyTemplate);
      ProfileTemplate emailTemplate = templateBean.getTemplateByName("Email Default Template");
      assertNotNull(emailTemplate);
      
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
      profileBean.setAttributeValue(napProfile, "NAP-ADDRESS", "cmnet");
      factory.commit();
      
      // Create a MMS Profile
      String emailProfileName = "Email Profile";
      ProfileConfig emailProfile = profileBean.newProfileConfigInstance(emailProfileName, carrier, emailTemplate, emailTemplate.getProfileCategory().getName());
      emailProfile.setNAPProfile(napProfile);
      
      factory.beginTransaction();
      profileBean.update(emailProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(emailProfile, "Display Name", emailProfileName);
      profileBean.setAttributeValue(emailProfile, "Username", "abc");
      profileBean.setAttributeValue(emailProfile, "Password", "pwd");
      profileBean.setAttributeValue(emailProfile, "Email Address", "abc@gmail.com");
      profileBean.setAttributeValue(emailProfile, "Mailbox Protocol", "POP3");
      profileBean.setAttributeValue(emailProfile, "Receiving Server Address", "pop.gmail.com");
      profileBean.setAttributeValue(emailProfile, "Receiving Server Port", "110");
      profileBean.setAttributeValue(emailProfile, "Sending Server Address", "smtp.gmail.com");
      profileBean.setAttributeValue(emailProfile, "Sending Server Port", "25");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4Email();
      OMAClientProvSettings settings = convertor.convertToOMAClientProv(emailProfile);
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
      assertEquals("cmnet", 
                    settings.getNapDefElements().get(0).getNapAddress());
      assertEquals(0, settings.getNapDefElements().get(0).getNapAuthInfos().size());
      
      // Assert Email Definition
      assertEquals(0, settings.getPXLogicalElements().size());
      
      // Assert Email Definition
      assertEquals(2, settings.getApplicationElements().size());
      // SMTP Definition
      assertNotNull(settings.getApplicationElements().get(0));
      assertEquals(emailProfileName, settings.getApplicationElements().get(0).getName());
      assertEquals(AppID.Email_SMTP.toString(), settings.getApplicationElements().get(0).getAppID());
      assertEquals("abc@gmail.com", settings.getApplicationElements().get(0).getProviderID());
      assertEquals(1, settings.getApplicationElements().get(0).getAppAddrElements().size());
      assertEquals("smtp.gmail.com", settings.getApplicationElements().get(0).getAppAddrElements().get(0).getAddr());
      assertEquals("25", settings.getApplicationElements().get(0).getAppAddrElements().get(0).getPorts().get(0).getPortNBR());
      assertNotNull(settings.getApplicationElements().get(0).getParameter("FROM"));
      assertEquals("abc@gmail.com",  settings.getApplicationElements().get(0).getParameter("FROM").getValue());
      assertEquals(0, settings.getApplicationElements().get(0).getAppAuthElements().size());    
      // POP3 Definition
      assertNotNull(settings.getApplicationElements().get(1));
      assertEquals(emailProfileName, settings.getApplicationElements().get(1).getName());
      assertEquals(AppID.Email_POP3.toString(), settings.getApplicationElements().get(1).getAppID());
      assertEquals("abc@gmail.com", settings.getApplicationElements().get(1).getProviderID());
      assertEquals(1, settings.getApplicationElements().get(1).getAppAddrElements().size());
      assertEquals("pop.gmail.com", settings.getApplicationElements().get(1).getAppAddrElements().get(0).getAddr());
      assertEquals("110", settings.getApplicationElements().get(1).getAppAddrElements().get(0).getPorts().get(0).getPortNBR());
      assertEquals(1, settings.getApplicationElements().get(1).getAppAuthElements().size());    
      assertEquals("abc", settings.getApplicationElements().get(1).getAppAuthElements().get(0).getAppAuthName());    
      assertEquals("pwd", settings.getApplicationElements().get(1).getAppAuthElements().get(0).getAppAuthSecret());    
      
      // Test linked relation between email and nap
      assertEquals(0,
                   settings.getApplicationElements().get(0).getToProxies().size());
      assertEquals(1,
                   settings.getApplicationElements().get(0).getToNAPIDs().size());
      assertEquals( settings.getNapDefElements().get(0).getNapID(),
                   settings.getApplicationElements().get(0).getToNAPIDs().get(0));
      assertEquals(1,
                   settings.getApplicationElements().get(1).getToNAPIDs().size());
      assertEquals(settings.getNapDefElements().get(0).getNapID(),
                   settings.getApplicationElements().get(1).getToNAPIDs().get(0));
      
    } catch (Exception ex) {
      throw ex;
    } finally {
      factory.release();
    }
    
  }

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4Email#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   * Test: Email profile based on IMAP4 protocol
   */
  public void testConvertSimpleProfileBasedIMAP()  throws Exception {
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileTemplate napTemplate = templateBean.getTemplateByName("NAP Default Template");
      assertNotNull(napTemplate);
      ProfileTemplate proxyTemplate = templateBean.getTemplateByName("Proxy Default Template");
      assertNotNull(proxyTemplate);
      ProfileTemplate emailTemplate = templateBean.getTemplateByName("Email Default Template");
      assertNotNull(emailTemplate);
      
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
      profileBean.setAttributeValue(napProfile, "NAP-ADDRESS", "cmnet");
      factory.commit();
      
      // Create a MMS Profile
      String emailProfileName = "Email Profile";
      ProfileConfig emailProfile = profileBean.newProfileConfigInstance(emailProfileName, carrier, emailTemplate, emailTemplate.getProfileCategory().getName());
      emailProfile.setNAPProfile(napProfile);
      
      factory.beginTransaction();
      profileBean.update(emailProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(emailProfile, "Display Name", emailProfileName);
      profileBean.setAttributeValue(emailProfile, "Username", "abc");
      profileBean.setAttributeValue(emailProfile, "Password", "pwd");
      profileBean.setAttributeValue(emailProfile, "Email Address", "abc@gmail.com");
      profileBean.setAttributeValue(emailProfile, "Mailbox Protocol", "IMAP4");
      profileBean.setAttributeValue(emailProfile, "Receiving Server Address", "imap.gmail.com");
      profileBean.setAttributeValue(emailProfile, "Receiving Server Port", "143");
      profileBean.setAttributeValue(emailProfile, "Sending Server Address", "smtp.gmail.com");
      profileBean.setAttributeValue(emailProfile, "Sending Server Port", "25");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4Email();
      OMAClientProvSettings settings = convertor.convertToOMAClientProv(emailProfile);
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
      assertEquals("cmnet", 
                    settings.getNapDefElements().get(0).getNapAddress());
      assertEquals(0, settings.getNapDefElements().get(0).getNapAuthInfos().size());
      
      // Assert Email Definition
      assertEquals(0, settings.getPXLogicalElements().size());
      
      // Assert Email Definition
      assertEquals(2, settings.getApplicationElements().size());
      // SMTP Definition
      assertNotNull(settings.getApplicationElements().get(0));
      assertEquals(emailProfileName, settings.getApplicationElements().get(0).getName());
      assertEquals(AppID.Email_SMTP.toString(), settings.getApplicationElements().get(0).getAppID());
      assertEquals("abc@gmail.com", settings.getApplicationElements().get(0).getProviderID());
      assertEquals(1, settings.getApplicationElements().get(0).getAppAddrElements().size());
      assertEquals("smtp.gmail.com", settings.getApplicationElements().get(0).getAppAddrElements().get(0).getAddr());
      assertEquals("25", settings.getApplicationElements().get(0).getAppAddrElements().get(0).getPorts().get(0).getPortNBR());
      assertNotNull(settings.getApplicationElements().get(0).getParameter("FROM"));
      assertEquals("abc@gmail.com",  settings.getApplicationElements().get(0).getParameter("FROM").getValue());
      assertEquals(0, settings.getApplicationElements().get(0).getAppAuthElements().size());    
      // IMAP4 Definition
      assertNotNull(settings.getApplicationElements().get(1));
      assertEquals(emailProfileName, settings.getApplicationElements().get(1).getName());
      assertEquals(AppID.Email_IMAP4.toString(), settings.getApplicationElements().get(1).getAppID());
      assertEquals("abc@gmail.com", settings.getApplicationElements().get(1).getProviderID());
      assertEquals(1, settings.getApplicationElements().get(1).getAppAddrElements().size());
      assertEquals("imap.gmail.com", settings.getApplicationElements().get(1).getAppAddrElements().get(0).getAddr());
      assertEquals("143", settings.getApplicationElements().get(1).getAppAddrElements().get(0).getPorts().get(0).getPortNBR());
      assertEquals(1, settings.getApplicationElements().get(1).getAppAuthElements().size());    
      assertEquals("abc", settings.getApplicationElements().get(1).getAppAuthElements().get(0).getAppAuthName());    
      assertEquals("pwd", settings.getApplicationElements().get(1).getAppAuthElements().get(0).getAppAuthSecret());    
      
      // Test linked relation between email and nap
      assertEquals(0,
                   settings.getApplicationElements().get(0).getToProxies().size());
      assertEquals(1,
                   settings.getApplicationElements().get(0).getToNAPIDs().size());
      assertEquals( settings.getNapDefElements().get(0).getNapID(),
                   settings.getApplicationElements().get(0).getToNAPIDs().get(0));
      assertEquals(1,
                   settings.getApplicationElements().get(1).getToNAPIDs().size());
      assertEquals(settings.getNapDefElements().get(0).getNapID(),
                   settings.getApplicationElements().get(1).getToNAPIDs().get(0));
      
    } catch (Exception ex) {
      throw ex;
    } finally {
      factory.release();
    }
    
  }

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertor4Email#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)}.
   * Test: Email profile based on Authentication-SMTP protocol
   */
  public void testConvertSimpleProfileBasedAuthSMTP()  throws Exception {
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      ProfileTemplate napTemplate = templateBean.getTemplateByName("NAP Default Template");
      assertNotNull(napTemplate);
      ProfileTemplate proxyTemplate = templateBean.getTemplateByName("Proxy Default Template");
      assertNotNull(proxyTemplate);
      ProfileTemplate emailTemplate = templateBean.getTemplateByName("Email Default Template");
      assertNotNull(emailTemplate);
      
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
      profileBean.setAttributeValue(napProfile, "NAP-ADDRESS", "cmnet");
      factory.commit();
      
      // Create a MMS Profile
      String emailProfileName = "Email Profile";
      ProfileConfig emailProfile = profileBean.newProfileConfigInstance(emailProfileName, carrier, emailTemplate, emailTemplate.getProfileCategory().getName());
      emailProfile.setNAPProfile(napProfile);
      
      factory.beginTransaction();
      profileBean.update(emailProfile);
      factory.commit();

      factory.beginTransaction();
      profileBean.setAttributeValue(emailProfile, "Display Name", emailProfileName);
      profileBean.setAttributeValue(emailProfile, "Username", "abc");
      profileBean.setAttributeValue(emailProfile, "Password", "pwd");
      profileBean.setAttributeValue(emailProfile, "Email Address", "abc@gmail.com");
      profileBean.setAttributeValue(emailProfile, "Mailbox Protocol", "IMAP4");
      profileBean.setAttributeValue(emailProfile, "Receiving Server Address", "imap.gmail.com");
      profileBean.setAttributeValue(emailProfile, "Receiving Server Port", "143");
      profileBean.setAttributeValue(emailProfile, "Sending Server Address", "smtp.gmail.com");
      profileBean.setAttributeValue(emailProfile, "Sending Server Port", "25");
      profileBean.setAttributeValue(emailProfile, "Use SMTP authentication", "true");
      profileBean.setAttributeValue(emailProfile, "SMTP authentication ID", "abc4smtp");
      profileBean.setAttributeValue(emailProfile, "SMTP authentication PW", "pwd4smtp");
      factory.commit();
      
      
      // TestCase for convert
      ProfileConvertor convertor = new ProfileConvertor4Email();
      OMAClientProvSettings settings = convertor.convertToOMAClientProv(emailProfile);
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
      assertEquals("cmnet", 
                    settings.getNapDefElements().get(0).getNapAddress());
      assertEquals(0, settings.getNapDefElements().get(0).getNapAuthInfos().size());
      
      // Assert Email Definition
      assertEquals(0, settings.getPXLogicalElements().size());
      
      // Assert Email Definition
      assertEquals(2, settings.getApplicationElements().size());
      // SMTP Definition
      assertNotNull(settings.getApplicationElements().get(0));
      assertEquals(emailProfileName, settings.getApplicationElements().get(0).getName());
      assertEquals(AppID.Email_SMTP.toString(), settings.getApplicationElements().get(0).getAppID());
      assertEquals("abc@gmail.com", settings.getApplicationElements().get(0).getProviderID());
      assertEquals(1, settings.getApplicationElements().get(0).getAppAddrElements().size());
      assertEquals("smtp.gmail.com", settings.getApplicationElements().get(0).getAppAddrElements().get(0).getAddr());
      assertEquals("25", settings.getApplicationElements().get(0).getAppAddrElements().get(0).getPorts().get(0).getPortNBR());
      assertNotNull(settings.getApplicationElements().get(0).getParameter("FROM"));
      assertEquals("abc@gmail.com",  settings.getApplicationElements().get(0).getParameter("FROM").getValue());
      assertEquals(1, settings.getApplicationElements().get(0).getAppAuthElements().size());    
      assertEquals("abc4smtp", settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthName());    
      assertEquals("pwd4smtp", settings.getApplicationElements().get(0).getAppAuthElements().get(0).getAppAuthSecret());    
      // IMAP4 Definition
      assertNotNull(settings.getApplicationElements().get(1));
      assertEquals(emailProfileName, settings.getApplicationElements().get(1).getName());
      assertEquals(AppID.Email_IMAP4.toString(), settings.getApplicationElements().get(1).getAppID());
      assertEquals("abc@gmail.com", settings.getApplicationElements().get(1).getProviderID());
      assertEquals(1, settings.getApplicationElements().get(1).getAppAddrElements().size());
      assertEquals("imap.gmail.com", settings.getApplicationElements().get(1).getAppAddrElements().get(0).getAddr());
      assertEquals("143", settings.getApplicationElements().get(1).getAppAddrElements().get(0).getPorts().get(0).getPortNBR());
      assertEquals(1, settings.getApplicationElements().get(1).getAppAuthElements().size());    
      assertEquals("abc", settings.getApplicationElements().get(1).getAppAuthElements().get(0).getAppAuthName());    
      assertEquals("pwd", settings.getApplicationElements().get(1).getAppAuthElements().get(0).getAppAuthSecret());    
      
      // Test linked relation between email and nap
      assertEquals(0,
                   settings.getApplicationElements().get(0).getToProxies().size());
      assertEquals(1,
                   settings.getApplicationElements().get(0).getToNAPIDs().size());
      assertEquals( settings.getNapDefElements().get(0).getNapID(),
                   settings.getApplicationElements().get(0).getToNAPIDs().get(0));
      assertEquals(1,
                   settings.getApplicationElements().get(1).getToNAPIDs().size());
      assertEquals(settings.getNapDefElements().get(0).getNapID(),
                   settings.getApplicationElements().get(1).getToNAPIDs().get(0));
      
    } catch (Exception ex) {
      throw ex;
    } finally {
      factory.release();
    }
    
  }

}
