/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/security/TestSecurityOfficer.java,v 1.4 2008/06/16 10:11:14 zhao Exp $
  * $Revision: 1.4 $
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
package com.npower.dm.security;

import junit.framework.TestCase;
import sync4j.framework.core.Authentication;
import sync4j.framework.core.Cred;
import sync4j.framework.core.HMACAuthentication;
import sync4j.framework.core.NextNonce;
import sync4j.framework.tools.Base64;
import sync4j.framework.tools.MD5;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.CountryBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SubscriberBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:14 $
 */
public class TestSecurityOfficer extends TestCase {

  /**
   * 
   */
  private static final byte[] nextNonceBytes = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
  /**
   * 
   */
  private static final String CLIENT_PASSWORD = "client.password";
  /**
   * 
   */
  private static final String CLIENT_USERNAME = "client.username";
  
  private String manufacturerExternalID = "W32 Device Manufacturer";
  private String modelExternalID = "W32 Device Model";
  private String deviceExternalID = "w32dm";
  private String carrierExternalID = "W32 Carrier";

  
  private String serverPassword = "srvpwd";
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    try {
        // clear all
        this.tearDownEntities();
    
        setupEntities();
        
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * @throws DMException
   */
  private void setupEntities() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, null);
        factory.beginTransaction();
        modelBean.update(manufacturer);
        factory.commit();
        
        factory.beginTransaction();
        Model model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
        modelBean.update(model);
        factory.commit();
        
        CountryBean countryBean = factory.createCountryBean();
        Country country = countryBean.getCountryByISOCode("CN");
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.newCarrierInstance(country, carrierExternalID, carrierExternalID);
        factory.beginTransaction();
        carrierBean.update(carrier);
        factory.commit();
        
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        Subscriber subscriber = subscriberBean.newSubscriberInstance(carrier, deviceExternalID, "13801356799", "password");
        factory.beginTransaction();
        subscriberBean.update(subscriber);
        factory.commit();
        
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.newDeviceInstance(subscriber, model, deviceExternalID);
        device.setOMAClientUsername(CLIENT_USERNAME);
        device.setOMAClientPassword(CLIENT_PASSWORD);
        device.setOMAServerPassword(serverPassword);
        factory.beginTransaction();
        deviceBean.update(device);
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
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
    this.tearDownEntities();
  }
  
  private void tearDownEntities() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        if (device != null) {
          factory.beginTransaction();
          deviceBean.delete(device);
          factory.commit();
        }
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
        if (carrier != null) {
          factory.beginTransaction();
          carrierBean.delete(carrier);
          factory.commit();
        }
        
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer != null) {
          factory.beginTransaction();
          modelBean.delete(manufacturer);
          factory.commit();
        }
    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

  /*
   * Test method for 'com.npower.dm.security.SecurityOfficer.authenticate(Cred)'
   */
  public void testBasicAuthenticate() throws Exception {
    
    String authType = "syncml:auth-basic";

    SecurityOfficer provider = new SecurityOfficer();
    provider.setAuthType(authType);
    provider.setSupportedAuthType(authType);
    
    assertFalse(provider.isGuestEnabled());
    
    // Test Case # 1: Login success.
    {
      String data = new String(Base64.encode((CLIENT_USERNAME + ":" + CLIENT_PASSWORD).getBytes()));;
      String type = authType;
      Authentication authentication = Cred.createAuthentication(data, type);
      authentication.setDeviceId(this.deviceExternalID);
      Cred credential = new Cred(authentication);
      
      assertTrue(provider.authenticate(credential));
    }
    
    // Test Case # 2: Login failure, error username and password
    {
      String data = new String(Base64.encode(("error_username:error_password").getBytes()));;
      String type = authType;
      Authentication authentication = Cred.createAuthentication(data, type);
      authentication.setDeviceId(this.deviceExternalID);
      Cred credential = new Cred(authentication);
      
      assertFalse(provider.authenticate(credential));
    }
    
    // Test Case # 3: Login failure, error in deviceExternalID
    {
      String data = new String(Base64.encode((CLIENT_USERNAME + ":" + CLIENT_PASSWORD).getBytes()));;
      String type = authType;
      Authentication authentication = Cred.createAuthentication(data, type);
      authentication.setDeviceId("error.device.externalID");
      Cred credential = new Cred(authentication);
      
      assertFalse(provider.authenticate(credential));
    }
  }

  /*
   * Test method for 'com.npower.dm.security.SecurityOfficer.authenticate(Cred)'
   */
  public void testMD5Authenticate() throws Exception {
    
    String authType = "syncml:auth-md5";

    SecurityOfficer provider = new SecurityOfficer();
    provider.setAuthType(authType);
    provider.setSupportedAuthType(authType);
    
    assertFalse(provider.isGuestEnabled());
    
    // Test Case # 1: Login success.
    {
      // b64(H(name:password)
      String digest = this.getDigest(CLIENT_USERNAME, CLIENT_PASSWORD);
      
      String type = authType;
      Authentication authentication = Cred.createAuthentication(digest, type);
      authentication.setDeviceId(this.deviceExternalID);
      NextNonce nextNonce = new NextNonce(TestSecurityOfficer.nextNonceBytes);
      authentication.setNextNonce(nextNonce);
      Cred credential = new Cred(authentication);
      
      assertTrue(provider.authenticate(credential));
    }
    
    // Test Case # 2: Login failure, error username and password
    {
      String digest = this.getDigest("error_username", "error_password");
      
      String type = authType;
      Authentication authentication = Cred.createAuthentication(digest, type);
      authentication.setDeviceId(this.deviceExternalID);
      NextNonce nextNonce = new NextNonce(TestSecurityOfficer.nextNonceBytes);
      authentication.setNextNonce(nextNonce);
      Cred credential = new Cred(authentication);
      
      assertFalse(provider.authenticate(credential));
    }
    
    // Test Case # 3: Login failure, error in deviceExternalID
    {
      // b64(H(name:password)
      String digest = this.getDigest(CLIENT_USERNAME, CLIENT_PASSWORD);
      
      String type = authType;
      Authentication authentication = Cred.createAuthentication(digest, type);
      authentication.setDeviceId("error.device.externalID");
      NextNonce nextNonce = new NextNonce(TestSecurityOfficer.nextNonceBytes);
      authentication.setNextNonce(nextNonce);
      Cred credential = new Cred(authentication);

      assertFalse(provider.authenticate(credential));
    }
  }
  
  private String getDigest(String username, String password) {
    String userDigest = new String(Base64.encode(MD5.digest((username + ":" + password).getBytes())));
    byte[] clientNonce = TestSecurityOfficer.nextNonceBytes;

    byte[] userDigestBytes = userDigest.getBytes();

    //
    // computation of the MD5 digest
    //
    // Creates a unique buffer containing the bytes to digest
    //
    byte[] buf = new byte[userDigestBytes.length + 1 + clientNonce.length];

    System.arraycopy(userDigestBytes, 0, buf, 0, userDigestBytes.length);
    buf[userDigestBytes.length] = (byte)':';
    System.arraycopy(clientNonce, 0, buf, userDigestBytes.length+1, clientNonce.length);

    byte[] digest = MD5.digest(buf);

    //
    // encoding digest in Base64 for comparation with digest sent by client
    //
    String serverDigestNonceB64 = new String(Base64.encode(digest));
    return serverDigestNonceB64;
  }

  /*
   * Test method for 'com.npower.dm.security.SecurityOfficer.authenticate(Cred)'
   */
  public void testHMACAuthenticate() throws Exception {
    
    String authType = "syncml:auth-MAC";
    String hmacData = "algorithm=MD5, username=\"Robert Jordan\", mac=Dgpoz8Pbs4XC0ecp6kLw4Q==";

    SecurityOfficer provider = new SecurityOfficer();
    provider.setAuthType(authType);
    provider.setSupportedAuthType(authType);
    
    assertFalse(provider.isGuestEnabled());
    
    // Test Case # 1: Login success.
    {
      
      HMACAuthentication authentication = new HMACAuthentication(hmacData);
      authentication.setDeviceId(this.deviceExternalID);
      Cred credential = new Cred(authentication);
      
      authentication.setCalculatedMac(authentication.getUserMac());
      
      assertTrue(provider.authenticate(credential));
    }
    
    // Test Case # 2: Login failure, error username and password
    {
      HMACAuthentication authentication = new HMACAuthentication(hmacData);
      authentication.setDeviceId(this.deviceExternalID);
      Cred credential = new Cred(authentication);
      
      authentication.setCalculatedMac("dadjksjalkdjeiuo");
      
      assertFalse(provider.authenticate(credential));
    }
    
    // Test Case # 3: Login failure, error in deviceExternalID
    {
      HMACAuthentication authentication = new HMACAuthentication(hmacData);
      authentication.setDeviceId("error.device.externalID");
      Cred credential = new Cred(authentication);
      
      authentication.setCalculatedMac(authentication.getUserMac());

      assertFalse(provider.authenticate(credential));
    }
  }
  
  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestSecurityOfficer.class);
  }

}
