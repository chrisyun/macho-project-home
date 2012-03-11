/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/bootstrap/TestBootstrapService.java,v 1.7 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.7 $
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
package com.npower.dm.bootstrap;

import java.util.Properties;

import junit.framework.TestCase;

import com.npower.cp.OTAException;
import com.npower.cp.OTAInventory;
import com.npower.cp.xmlinventory.OTAInventoryImpl;
import com.npower.dm.AllTests;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.sms.client.SmsSender;
import com.npower.sms.client.SmsSenderFactory;
import com.npower.sms.client.SoapSmsSender;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestBootstrapService extends TestCase {


  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * 
   */
  private Properties setupProfile() {
    Properties profile = new Properties();
    profile = new Properties();
    profile.setProperty("napdef_napid", "TEST_NAP_ID");
    profile.setProperty("napdef_name", "TEST_NAP_NAME");
    profile.setProperty("napdef_nap_addr", "cmnet");
    profile.setProperty("napdef_bearer", "GSM-GPRS");
    profile.setProperty("napdef_apn", "APN");

    /*
    profile.setProperty("pxlogic_id", "TEST_PROXY_ID");
    profile.setProperty("pxlogic_name", "TEST_PROXY_NAME");
    profile.setProperty("pxlogic_startpage", "http://proxy.otas.cn");
    profile.setProperty("pxlogic_phy_proxy_addr", "10.0.0.172");
    profile.setProperty("pxlogic_phy_proxy_addr_type", "IPV4");
    profile.setProperty("pxlogic_phy_proxy_addr_port", "8080");
    */
    profile.setProperty("dm_service_name", "OTAS DM TST");
    profile.setProperty("dm_service_id", "otasdm.test");
    profile.setProperty("dm_service_url", "http://dm.otas.cn/otasdm/dm");
    profile.setProperty("dm_service_srv_uid", "otasdm");
    return profile;
  }

  /**
   * @return
   * @throws OTAException
   */
  private OTAInventory setupOTAInventory() throws Exception {
    // Initialize OTAInventory.
    Properties props = new Properties();
    props.setProperty(OTAInventory.PROPERTY_FACTORY, OTAInventoryImpl.class.getName());
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR, AllTests.BASE_DIR + "/metadata/cp/inventory");
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_FILENAME, "main.xml");
    OTAInventory otaInventory = OTAInventory.newInstance(props);
    return otaInventory;
  }

  /**
   * @return
   * @throws OTAException
   */
  private SmsSender setupSmsSender() throws Exception {
    Properties props = new Properties();
    props.setProperty(SmsSenderFactory.PROP_SMS_SENDER_CLASS, com.npower.sms.client.SoapSmsSender.class.getName());
    props.setProperty(SoapSmsSender.PROP_SOAP_URL, "http://localhost:9999/OTAS-SMS-Gateway/services/SMSSenderService");
    SmsSenderFactory factory = SmsSenderFactory.newInstance(props);
    SmsSender sender = factory.getSmsSender();
    return sender;
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testBootstrapWithNAP() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    try {
        Properties profile = setupProfile();
        OTAInventory otaInventory = setupOTAInventory();
        SmsSender smsSender = setupSmsSender();
        
        BaseBootstrapService service = new BootstrapServiceSettingsImpl();
        service.setBeanFactory(beanFactory);
        service.setOtaInventory(otaInventory);
        service.setSmsSender(smsSender);
        service.setProfileProperties(profile);
        
        String pin = "1234";
        String deviceExternalID = "IMEI:351886011493928";
        service.bootstrap(deviceExternalID, OMACPSecurityMethod.USERPIN, pin);
    } catch (Exception e) {
      throw e;
    } finally {
      beanFactory.release();
    }
  }


}
