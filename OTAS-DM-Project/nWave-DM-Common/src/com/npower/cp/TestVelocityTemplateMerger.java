/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/TestVelocityTemplateMerger.java,v 1.3 2008/10/29 04:26:17 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/10/29 04:26:17 $
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
package com.npower.cp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;

import com.npower.dm.AllTests;
import com.npower.dm.core.ProfileConfig;
import com.npower.wap.nokia.NokiaOtaBrowserSettings;
import com.npower.wap.nokia.SyncMLDSSettings;
import com.npower.wap.nokia.browser.AddressGPRS;
import com.npower.wap.nokia.browser.PPPAuthType;
import com.npower.wap.nokia.browser.Port;
import com.npower.wap.nokia.syncmlds.AddrType;
import com.npower.wap.nokia.syncmlds.Auth;
import com.npower.wap.nokia.syncmlds.AuthLevel;
import com.npower.wap.nokia.syncmlds.AuthScheme;
import com.npower.wap.nokia.syncmlds.Bearer;
import com.npower.wap.nokia.syncmlds.ConRef;
import com.npower.wap.nokia.syncmlds.ConType;
import com.npower.wap.nokia.syncmlds.RemoteDB;
import com.npower.wap.nokia.syncmlds.RemoteDBContentType;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/10/29 04:26:17 $
 */
public class TestVelocityTemplateMerger extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    XMLUnit.setIgnoreWhitespace(true);   
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  /**
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  private StringBuffer getBrowserTemplate() throws FileNotFoundException, IOException {
    String browserTemplateFilename = "metadata/cp/inventory/nokia/nokia_ericsson_ota_7_0_browser.vm";
    return loadTemplate(browserTemplateFilename);
  }

  /**
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  private StringBuffer getStreamingTemplate() throws FileNotFoundException, IOException {
    String filename = "metadata/cp/test/sony_ericsson_w800_3gpp_streaming.vm";
    return loadTemplate(filename);
  }

  /**
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  private StringBuffer getSyncMLTemplate() throws FileNotFoundException, IOException {
    String browserTemplateFilename = "metadata/cp/inventory/nokia/nokia_ericsson_ota_7_0_syncml.vm";
    return loadTemplate(browserTemplateFilename);
  }

  /**
   * @param browserTemplateFilename
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  private StringBuffer loadTemplate(String browserTemplateFilename) throws FileNotFoundException, IOException {
    FileReader reader = new FileReader(new File(AllTests.BASE_DIR, browserTemplateFilename));
    
    StringBuffer template = new StringBuffer();
    char[] buf = new char[512];
    int len = reader.read(buf);
    while (len > 0) {
          template.append(buf, 0, len);
          len = reader.read(buf);
    }
    reader.close();
    return template;
  }

  public void testMergeNokiaOTA_Browser_1() throws Exception {
    VelocityTemplateMerger merger = new VelocityTemplateMerger();
    
    StringBuffer template = getBrowserTemplate();
    //assertEquals("", template.toString());

    AddressGPRS address = new AddressGPRS("10.0.0.172");
    address.setGprsAccessPointName("cmwap");
    NokiaOtaBrowserSettings settings = new NokiaOtaBrowserSettings();
    settings.setName("CMWAP");
    settings.setUrl("http://wap.monternet.com");
    settings.addBookmark("CMWap", "http://wap.monternet.com");
    settings.addAddress(address);
    
    ProvisioningDoc result = merger.merge(template.toString(), settings);
    assertNotNull(result);
    
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
    "\n" +
    "<!-- \n" +
    "  Nokia OTA 7.0 Browser Configuration Compatiable Profile\n" +
    " -->\n" +
    "<CHARACTERISTIC-LIST> \n" +
    "  <CHARACTERISTIC TYPE=\"ADDRESS\"> \n" +
    "    <!-- Values: GSM/CSD | GSM/SMS | GSM/USSD | IS-136/CSD | GPRS  -->  \n" +
    "    <PARM NAME=\"BEARER\" VALUE=\"GPRS\"/>  \n" +
    "    <PARM NAME=\"PROXY\" VALUE=\"10.0.0.172\"/>  \n" +
    "    <!-- Values: MSISDN_NO | IPV4 -->  \n" +
    "    <PARM NAME=\"PROXY_TYPE\" VALUE=\"IPV4\"/>  \n" +
    "    <PARM NAME=\"GPRS_ACCESSPOINTNAME\" VALUE=\"cmwap\"/> \n" +
    "  </CHARACTERISTIC>  \n" +
    "  <CHARACTERISTIC TYPE=\"URL\" VALUE=\"http://wap.monternet.com\"/>  \n" +
    "  <CHARACTERISTIC TYPE=\"NAME\"> \n" +
    "    <PARM NAME=\"NAME\" VALUE=\"CMWAP\"/> \n" +
    "  </CHARACTERISTIC>  \n" +
    "  <CHARACTERISTIC TYPE=\"BOOKMARK\"> \n" +
    "    <PARM NAME=\"NAME\" VALUE=\"CMWap\"/>  \n" +
    "    <PARM NAME=\"URL\" VALUE=\"http://wap.monternet.com\"/> \n" +
    "  </CHARACTERISTIC> \n" +
    "</CHARACTERISTIC-LIST>\n";
    Diff diff = new Diff(expected, result.getContent());
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
  }

  public void testMergeNokiaOTA_Browser_2() throws Exception {
    VelocityTemplateMerger merger = new VelocityTemplateMerger();
    
    StringBuffer template = getBrowserTemplate();
    //assertEquals("", template.toString());

    AddressGPRS address = new AddressGPRS("10.0.0.172");
    address.setGprsAccessPointName("cmwap");
    address.setPppAuthType(PPPAuthType.PAP);
    NokiaOtaBrowserSettings settings = new NokiaOtaBrowserSettings();
    settings.setName("CMWAP");
    settings.setUrl("http://wap.monternet.com");
    settings.addBookmark("CMWap", "http://wap.monternet.com");
    settings.addAddress(address);
    
    ProvisioningDoc result = merger.merge(template.toString(), settings);
    assertNotNull(result);
    
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
    "\n" +
    "<!-- \n" +
    "  Nokia OTA 7.0 Browser Configuration Compatiable Profile\n" +
    " -->\n" +
    "<CHARACTERISTIC-LIST> \n" +
    "  <CHARACTERISTIC TYPE=\"ADDRESS\"> \n" +
    "    <!-- Values: GSM/CSD | GSM/SMS | GSM/USSD | IS-136/CSD | GPRS  -->  \n" +
    "    <PARM NAME=\"BEARER\" VALUE=\"GPRS\"/>  \n" +
    "    <PARM NAME=\"PROXY\" VALUE=\"10.0.0.172\"/>  \n" +
    "    <!-- Values: MSISDN_NO | IPV4 -->  \n" +
    "    <PARM NAME=\"PROXY_TYPE\" VALUE=\"IPV4\"/>  \n" +
    "    <PARM NAME=\"GPRS_ACCESSPOINTNAME\" VALUE=\"cmwap\"/> \n" +
    "  </CHARACTERISTIC>  \n" +
    "  <CHARACTERISTIC TYPE=\"URL\" VALUE=\"http://wap.monternet.com\"/>  \n" +
    "  <CHARACTERISTIC TYPE=\"NAME\"> \n" +
    "    <PARM NAME=\"NAME\" VALUE=\"CMWAP\"/> \n" +
    "  </CHARACTERISTIC>  \n" +
    "  <CHARACTERISTIC TYPE=\"BOOKMARK\"> \n" +
    "    <PARM NAME=\"NAME\" VALUE=\"CMWap\"/>  \n" +
    "    <PARM NAME=\"URL\" VALUE=\"http://wap.monternet.com\"/> \n" +
    "  </CHARACTERISTIC> \n" +
    "</CHARACTERISTIC-LIST>\n";
    Diff diff = new Diff(expected, result.getContent());
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
  }

  public void testMergeNokiaOTA_Browser_3() throws Exception {
    VelocityTemplateMerger merger = new VelocityTemplateMerger();
    
    StringBuffer template = getBrowserTemplate();
    //assertEquals("", template.toString());

    AddressGPRS address = new AddressGPRS("10.0.0.172");
    address.setGprsAccessPointName("cmwap");
    address.setPppAuthType(PPPAuthType.PAP);
    address.setPppAuthName("ppp_username");
    address.setPppAuthSecret("ppp_password");
    address.setProxyAuthName("proxy_user");
    address.setProxyAuthSecret("proxy_password");
    address.setPort(Port.PORT_9201);
    NokiaOtaBrowserSettings settings = new NokiaOtaBrowserSettings();
    settings.setName("CMWAP");
    settings.setUrl("http://wap.monternet.com");
    settings.addBookmark("CMWap", "http://wap.monternet.com");
    settings.addAddress(address);
    
    ProvisioningDoc result = merger.merge(template.toString(), settings);
    assertNotNull(result);
    
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                      "\n" +
                      "<!-- \n" +
                      "  Nokia OTA 7.0 Browser Configuration Compatiable Profile\n" +
                      " -->\n" +
                      "<CHARACTERISTIC-LIST> \n" +
                      "  <CHARACTERISTIC TYPE=\"ADDRESS\"> \n" +
                      "    <!-- Values: GSM/CSD | GSM/SMS | GSM/USSD | IS-136/CSD | GPRS  -->  \n" +
                      "    <PARM NAME=\"BEARER\" VALUE=\"GPRS\"/>  \n" +
                      "    <!-- Values: PAP | CHAP | MS_CHAP -->  \n" +
                      "    <PARM NAME=\"PPP_AUTHTYPE\" VALUE=\"PAP\"/>  \n" +
                      "    <PARM NAME=\"PPP_AUTHNAME\" VALUE=\"ppp_username\"/>  \n" +
                      "    <PARM NAME=\"PPP_AUTHSECRET\" VALUE=\"ppp_password\"/>  \n" +
                      "    <!-- Values: AUTOMATIC | MANUAL -->  \n" +
                      "    <PARM NAME=\"PPP_LOGINTYPE\" VALUE=\"AUTOMATIC\"/>  \n" +
                      "    <PARM NAME=\"PROXY\" VALUE=\"10.0.0.172\"/>  \n" +
                      "    <!-- Values: MSISDN_NO | IPV4 -->  \n" +
                      "    <PARM NAME=\"PROXY_TYPE\" VALUE=\"IPV4\"/>  \n" +
                      "    <PARM NAME=\"PROXY_AUTHNAME\" VALUE=\"proxy_user\"/>  \n" +
                      "    <PARM NAME=\"PROXY_AUTHSERCRET\" VALUE=\"proxy_password\"/>  \n" +
                      "    <!-- Values: AUTOMATIC | MANUAL -->  \n" +
                      "    <PARM NAME=\"PROXY_LOGINTYPE\" VALUE=\"AUTOMATIC\"/>  \n" +
                      "    <!-- Values: 9200 | 9201 | 9202 | 9203 -->  \n" +
                      "    <PARM NAME=\"PORT\" VALUE=\"9201\"/>  \n" +
                      "    <PARM NAME=\"GPRS_ACCESSPOINTNAME\" VALUE=\"cmwap\"/> \n" +
                      "  </CHARACTERISTIC>  \n" +
                      "  <CHARACTERISTIC TYPE=\"URL\" VALUE=\"http://wap.monternet.com\"/>  \n" +
                      "  <CHARACTERISTIC TYPE=\"NAME\"> \n" +
                      "    <PARM NAME=\"NAME\" VALUE=\"CMWAP\"/> \n" +
                      "  </CHARACTERISTIC>  \n" +
                      "  <CHARACTERISTIC TYPE=\"BOOKMARK\"> \n" +
                      "    <PARM NAME=\"NAME\" VALUE=\"CMWap\"/>  \n" +
                      "    <PARM NAME=\"URL\" VALUE=\"http://wap.monternet.com\"/> \n" +
                      "  </CHARACTERISTIC> \n" +
                      "</CHARACTERISTIC-LIST>\n";
    Diff diff = new Diff(expected, result.getContent());
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
  }

  public void testMergeNokiaOTAMMS() throws Exception {
    VelocityTemplateMerger merger = new VelocityTemplateMerger();
    
    StringBuffer template = getBrowserTemplate();
    //assertEquals("", template.toString());

    AddressGPRS address = new AddressGPRS("10.0.0.172");
    address.setGprsAccessPointName("cmwap");
    NokiaOtaBrowserSettings settings = new NokiaOtaBrowserSettings();
    settings.setName("CMWAP");
    settings.setUrl("http://wap.monternet.com");
    settings.addAddress(address);
    settings.setMmsurl("http://mmsc.monternet.com");
    
    ProvisioningDoc result = merger.merge(template.toString(), settings);
    assertNotNull(result);
    
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
    "\n" +
    "<!-- \n" +
    "  Nokia OTA 7.0 Browser Configuration Compatiable Profile\n" +
    " -->\n" +
    "<CHARACTERISTIC-LIST> \n" +
    "  <CHARACTERISTIC TYPE=\"ADDRESS\"> \n" +
    "    <!-- Values: GSM/CSD | GSM/SMS | GSM/USSD | IS-136/CSD | GPRS  -->  \n" +
    "    <PARM NAME=\"BEARER\" VALUE=\"GPRS\"/>  \n" +
    "    <PARM NAME=\"PROXY\" VALUE=\"10.0.0.172\"/>  \n" +
    "    <!-- Values: MSISDN_NO | IPV4 -->  \n" +
    "    <PARM NAME=\"PROXY_TYPE\" VALUE=\"IPV4\"/>  \n" +
    "    <PARM NAME=\"GPRS_ACCESSPOINTNAME\" VALUE=\"cmwap\"/> \n" +
    "  </CHARACTERISTIC>  \n" +
    "  <CHARACTERISTIC TYPE=\"URL\" VALUE=\"http://wap.monternet.com\"/>  \n" +
    "  <CHARACTERISTIC TYPE=\"MMSURL\" VALUE=\"http://mmsc.monternet.com\"/>  \n" +
    "  <CHARACTERISTIC TYPE=\"NAME\"> \n" +
    "    <PARM NAME=\"NAME\" VALUE=\"CMWAP\"/> \n" +
    "  </CHARACTERISTIC> \n" +
    "</CHARACTERISTIC-LIST>\n";
    Diff diff = new Diff(expected, result.getContent());
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
  }

  public void testMergeNokiaOTA_SyncML_1() throws Exception {
    VelocityTemplateMerger merger = new VelocityTemplateMerger();
    
    StringBuffer template = this.getSyncMLTemplate();
    //assertEquals("", template.toString());

    RemoteDBContentType ct1 = new RemoteDBContentType("text/x-vcard");
    ct1.addCtVersion("2.1");
    RemoteDB db1 = new RemoteDB("./card", ct1);
    db1.setName("个人地址簿");
    
    SyncMLDSSettings settings = new SyncMLDSSettings("http://m.otas.cn/omads/ds", new RemoteDB[]{db1});
    settings.setPort("80");
    settings.setName("OTAS SyncDS Service");
    Auth auth1 = new Auth(AuthScheme.BASIC, "james", "password");
    auth1.setAuthLevel(AuthLevel.SyncML_Server);
    settings.addAuth(auth1);
    
    ProvisioningDoc result = merger.merge(template.toString(), settings);
    assertNotNull(result);
    
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
    "\n" +
    "<!-- \n" +
    "  Nokia OTA 7.0 Browser Configuration Compatiable Profile\n" +
    " -->\n" +
    "<SyncSettings> \n" +
    "  <Version>1.0</Version>  \n" +
    "  <HostAddr>http://m.otas.cn/omads/ds</HostAddr>  \n" +
    "  <Port>80</Port>  \n" +
    "  <RemoteDB> \n" +
    "    <CTType>text/x-vcard</CTType>  \n" +
    "    <CTVer>2.1</CTVer>  \n" +
    "    <URI>./card</URI>  \n" +
    "    <Name>个人地址簿</Name> \n" +
    "  </RemoteDB>  \n" +
    "  <Name>OTAS SyncDS Service</Name>  \n" +
    "  <Auth> \n" +
    "    <AuthLevel>1</AuthLevel>  \n" +
    "    <AuthScheme>1</AuthScheme>  \n" +
    "    <Username>james</Username>  \n" +
    "    <!-- Base64 coded password -->  \n" +
    "    <Cred>cGFzc3dvcmQ=</Cred> \n" +
    "  </Auth> \n" +
    "</SyncSettings>\n";
    Diff diff = new Diff(expected, result.getContent());
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
  }

  public void testMergeNokiaOTA_SyncML_2() throws Exception {
    VelocityTemplateMerger merger = new VelocityTemplateMerger();
    
    StringBuffer template = this.getSyncMLTemplate();
    //assertEquals("", template.toString());

    RemoteDBContentType ct1 = new RemoteDBContentType("text/x-vcard");
    ct1.addCtVersion("2.1");
    RemoteDB db1 = new RemoteDB("./card", ct1);
    db1.setName("个人地址簿");
    
    RemoteDBContentType ct2 = new RemoteDBContentType("text/x-vcalendar");
    ct2.addCtVersion("1.0");
    ct2.addCtVersion("2.0");
    ct2.addCtVersion("3.0");
    RemoteDB db2 = new RemoteDB("./cal", ct2);
    db2.setName("日程表");
    Auth auth = new Auth(AuthScheme.DIGEST_MD5, "myusername", "mypassword");
    db2.setAuth(auth);

    SyncMLDSSettings settings = new SyncMLDSSettings("http://m.otas.cn/omads/ds", new RemoteDB[]{db1, db2});
    settings.setPort("80");
    settings.setName("OTAS SyncDS Service");
    Auth auth1 = new Auth(AuthScheme.BASIC, "james", "password");
    auth1.setAuthLevel(AuthLevel.SyncML_Server);
    settings.addAuth(auth1);
    
    settings.setConnectoinReference(new ConRef(ConType.PHYSICAL_AP, AddrType.APN, "cmnet"));
    
    ProvisioningDoc result = merger.merge(template.toString(), settings);
    assertNotNull(result);
    
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
    "\n" +
    "<!-- \n" +
    "  Nokia OTA 7.0 Browser Configuration Compatiable Profile\n" +
    " -->\n" +
    "<SyncSettings> \n" +
    "  <Version>1.0</Version>  \n" +
    "  <HostAddr>http://m.otas.cn/omads/ds</HostAddr>  \n" +
    "  <Port>80</Port>  \n" +
    "  <RemoteDB> \n" +
    "    <CTType>text/x-vcard</CTType>  \n" +
    "    <CTVer>2.1</CTVer>  \n" +
    "    <URI>./card</URI>  \n" +
    "    <Name>个人地址簿</Name> \n" +
    "  </RemoteDB>  \n" +
    "  <RemoteDB> \n" +
    "    <CTType>text/x-vcalendar</CTType>  \n" +
    "    <CTVer>1.0</CTVer>  \n" +
    "    <CTVer>2.0</CTVer>  \n" +
    "    <CTVer>3.0</CTVer>  \n" +
    "    <URI>./cal</URI>  \n" +
    "    <Name>日程表</Name>  \n" +
    "    <Auth> \n" +
    "      <AuthLevel/>  \n" +
    "      <AuthScheme>2</AuthScheme>  \n" +
    "      <Username>myusername</Username>  \n" +
    "      <!-- Base64 coded password -->  \n" +
    "      <Cred>bXlwYXNzd29yZA==</Cred> \n" +
    "    </Auth> \n" +
    "  </RemoteDB>  \n" +
    "  <Name>OTAS SyncDS Service</Name>  \n" +
    "  <Auth> \n" +
    "    <AuthLevel>1</AuthLevel>  \n" +
    "    <AuthScheme>1</AuthScheme>  \n" +
    "    <Username>james</Username>  \n" +
    "    <!-- Base64 coded password -->  \n" +
    "    <Cred>cGFzc3dvcmQ=</Cred> \n" +
    "  </Auth>  \n" +
    "  <ConRef> \n" +
    "    <!-- Values: 1 | 2 | 3 | 4 -->  \n" +
    "    <ConType>4</ConType>  \n" +
    "    <!-- Values: 1 - IPV4 | 2 - IPV6 | 3 - E164 | 4 - ALPHA | 5 - APN -->  \n" +
    "    <AddrType>5</AddrType>  \n" +
    "    <Addr>cmnet</Addr> \n" +
    "  </ConRef> \n" +
    "</SyncSettings>\n";
    Diff diff = new Diff(expected, result.getContent());
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
  }

  public void testMergeNokiaOTA_SyncML_3() throws Exception {
    VelocityTemplateMerger merger = new VelocityTemplateMerger();
    
    StringBuffer template = this.getSyncMLTemplate();
    //assertEquals("", template.toString());

    RemoteDBContentType ct1 = new RemoteDBContentType("text/x-vcard");
    ct1.addCtVersion("2.1");
    RemoteDB db1 = new RemoteDB("./card", ct1);
    db1.setName("个人地址簿");
    
    RemoteDBContentType ct2 = new RemoteDBContentType("text/x-vcalendar");
    ct2.addCtVersion("1.0");
    ct2.addCtVersion("2.0");
    ct2.addCtVersion("3.0");
    RemoteDB db2 = new RemoteDB("./cal", ct2);
    db2.setName("日程表");
    Auth auth = new Auth(AuthScheme.DIGEST_MD5, "myusername", "mypassword");
    db2.setAuth(auth);

    SyncMLDSSettings settings = new SyncMLDSSettings("http://m.otas.cn/omads/ds", new RemoteDB[]{db1, db2});
    settings.setPort("80");
    settings.setName("OTAS SyncDS Service");
    Auth auth1 = new Auth(AuthScheme.BASIC, "james", "password");
    auth1.setAuthLevel(AuthLevel.SyncML_Server);
    settings.addAuth(auth1);
    
    ConRef conRef = new ConRef(ConType.PHYSICAL_AP, AddrType.APN, "cmnet");
    conRef.setBearer(Bearer.GSM_GPRS);
    conRef.setRefID("ref001");
    settings.setConnectoinReference(conRef);
    
    ProvisioningDoc result = merger.merge(template.toString(), settings);
    assertNotNull(result);
    
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
    "\n" +
    "<!-- \n" +
    "  Nokia OTA 7.0 Browser Configuration Compatiable Profile\n" +
    " -->\n" +
    "<SyncSettings> \n" +
    "  <Version>1.0</Version>  \n" +
    "  <HostAddr>http://m.otas.cn/omads/ds</HostAddr>  \n" +
    "  <Port>80</Port>  \n" +
    "  <RemoteDB> \n" +
    "    <CTType>text/x-vcard</CTType>  \n" +
    "    <CTVer>2.1</CTVer>  \n" +
    "    <URI>./card</URI>  \n" +
    "    <Name>个人地址簿</Name> \n" +
    "  </RemoteDB>  \n" +
    "  <RemoteDB> \n" +
    "    <CTType>text/x-vcalendar</CTType>  \n" +
    "    <CTVer>1.0</CTVer>  \n" +
    "    <CTVer>2.0</CTVer>  \n" +
    "    <CTVer>3.0</CTVer>  \n" +
    "    <URI>./cal</URI>  \n" +
    "    <Name>日程表</Name>  \n" +
    "    <Auth> \n" +
    "      <AuthLevel/>  \n" +
    "      <AuthScheme>2</AuthScheme>  \n" +
    "      <Username>myusername</Username>  \n" +
    "      <!-- Base64 coded password -->  \n" +
    "      <Cred>bXlwYXNzd29yZA==</Cred> \n" +
    "    </Auth> \n" +
    "  </RemoteDB>  \n" +
    "  <Name>OTAS SyncDS Service</Name>  \n" +
    "  <Auth> \n" +
    "    <AuthLevel>1</AuthLevel>  \n" +
    "    <AuthScheme>1</AuthScheme>  \n" +
    "    <Username>james</Username>  \n" +
    "    <!-- Base64 coded password -->  \n" +
    "    <Cred>cGFzc3dvcmQ=</Cred> \n" +
    "  </Auth>  \n" +
    "  <ConRef> \n" +
    "    <!-- Values: 1 | 2 | 3 | 4 -->  \n" +
    "    <ConType>4</ConType>  \n" +
    "    <!-- Values: 1 | 2 | ... | 24 -->  \n" +
    "    <Bearer>11</Bearer>  \n" +
    "    <!-- Values: 1 - IPV4 | 2 - IPV6 | 3 - E164 | 4 - ALPHA | 5 - APN -->  \n" +
    "    <AddrType>5</AddrType>  \n" +
    "    <Addr>cmnet</Addr>  \n" +
    "    <RefID>ref001</RefID> \n" +
    "  </ConRef> \n" +
    "</SyncSettings>\n";
    Diff diff = new Diff(expected, result.getContent());
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
  }

  public void testMerge3GPPStreaming() throws Exception {
    VelocityTemplateMerger merger = new VelocityTemplateMerger();
    
    StringBuffer template = getStreamingTemplate();
    
    ProvisioningDoc result = merger.merge(template.toString(), (ProfileConfig)null);
    assertNotNull(result);
    
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
    "\n" +
    "<!-- \n" +
    "  Nokia OTA 7.0 Browser Configuration Compatiable Profile\n" +
    " -->\n" +
    "<CHARACTERISTIC-LIST> \n" +
    "  <CHARACTERISTIC TYPE=\"ADDRESS\"> \n" +
    "    <!-- Values: GSM/CSD | GSM/SMS | GSM/USSD | IS-136/CSD | GPRS  -->  \n" +
    "    <PARM NAME=\"BEARER\" VALUE=\"GPRS\"/>  \n" +
    "    <PARM NAME=\"PROXY\" VALUE=\"10.0.0.172\"/>  \n" +
    "    <!-- Values: MSISDN_NO | IPV4 -->  \n" +
    "    <PARM NAME=\"PROXY_TYPE\" VALUE=\"IPV4\"/>  \n" +
    "    <PARM NAME=\"GPRS_ACCESSPOINTNAME\" VALUE=\"cmwap\"/> \n" +
    "  </CHARACTERISTIC>  \n" +
    "  <CHARACTERISTIC TYPE=\"URL\" VALUE=\"http://wap.monternet.com\"/>  \n" +
    "  <CHARACTERISTIC TYPE=\"NAME\"> \n" +
    "    <PARM NAME=\"NAME\" VALUE=\"CMWAP\"/> \n" +
    "  </CHARACTERISTIC>  \n" +
    "  <CHARACTERISTIC TYPE=\"BOOKMARK\"> \n" +
    "    <PARM NAME=\"NAME\" VALUE=\"CMWap\"/>  \n" +
    "    <PARM NAME=\"URL\" VALUE=\"http://wap.monternet.com\"/> \n" +
    "  </CHARACTERISTIC> \n" +
    "</CHARACTERISTIC-LIST>\n";
    Diff diff = new Diff(expected, result.getContent());
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
  }

}
