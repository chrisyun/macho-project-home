/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/cp/template/TestDMTemplate.java,v 1.2 2007/10/09 02:09:31 LAH Exp $
  * $Revision: 1.2 $
  * $Date: 2007/10/09 02:09:31 $
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
package com.npower.cp.template;



import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;

import com.npower.cp.OTAInventory;
import com.npower.cp.ProvisioningDoc;
import com.npower.cp.xmlinventory.OTAInventoryImpl;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.ProfileCategory;
import com.npower.wap.omacp.OMAClientProvSettings;
import com.npower.wap.omacp.SimpleParameter;
import com.npower.wap.omacp.elements.AppAuthElement;
import com.npower.wap.omacp.elements.ApplicationElement;
import com.npower.wap.omacp.elements.NAPAuthInfoElement;
import com.npower.wap.omacp.elements.NAPDefElement;
import com.npower.wap.omacp.elements.PXAuthInfoElement;
import com.npower.wap.omacp.elements.PXLogicalElement;
import com.npower.wap.omacp.elements.PXPhysicalElement;
import com.npower.wap.omacp.elements.PortElement;
import com.npower.wap.omacp.parameters.AppAuthLevel;
import com.npower.wap.omacp.parameters.AppAuthType;
import com.npower.wap.omacp.parameters.AppID;
import com.npower.wap.omacp.parameters.AuthType;
import com.npower.wap.omacp.parameters.Bearer;
import com.npower.wap.omacp.parameters.NAPAddrType;
import com.npower.wap.omacp.parameters.PXAddrType;
import com.npower.wap.omacp.parameters.PXAuthType;
import com.npower.wap.omacp.parameters.Service;

/**
 * 本测试用例用于测试所有型号的DM模板。
 * <pre>
 * </pre>
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/10/09 02:09:31 $
 */
public class TestDMTemplate extends BaseTestCase {

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
   */
  private OMAClientProvSettings getDMSettings() {
    OMAClientProvSettings settings = new OMAClientProvSettings();
    // PXLogic Element
    PXLogicalElement pxLogicElement = new PXLogicalElement("pxlogicID", "PXLogicName");
    pxLogicElement.setStartPage("http://start.a.com");
    PXAuthInfoElement pxAuthInfo = new PXAuthInfoElement(PXAuthType.HTTP_BASIC);
    pxAuthInfo.setPxAuthID("pxAuthID");
    pxAuthInfo.setPxAuthPW("pxAuthPW");
    pxLogicElement.addPXAuthInfoElement(pxAuthInfo );
    settings.addPXLogicalElement(pxLogicElement);
    PXPhysicalElement pxPhysicalElement = new PXPhysicalElement("pxPhysical", "pxPhysicalAddr", "NAPID");
    pxPhysicalElement.setPxAddrType(PXAddrType.IPV4);
    PortElement port = new PortElement("8080");
    port.addService(Service.CL_SEC_WSP);
    port.addService(Service.OTA_HTTP_PO);
    pxPhysicalElement.addPortElement(port);
    pxLogicElement.addPXPhysicalElement(pxPhysicalElement);
    
    // NAPDefElement Element
    NAPDefElement napElement = new NAPDefElement("NAPID", "NAPName", "NAPAddr");
    napElement.addBearer(Bearer.GSM_GPRS);
    napElement.setNapAddrType(NAPAddrType.APN);
    settings.addNAPDefElement(napElement);
    NAPAuthInfoElement napAuth = new NAPAuthInfoElement(AuthType.PAP);
    napAuth.setAuthName("authName");
    napAuth.setAuthSecret("authSecret");
    napElement.addNapAuthInfo(napAuth);
    
    ApplicationElement application = new ApplicationElement(AppID.W7_SyncML_Device_Management);
    settings.addApplicationElement(application);  
    application.addAddr("http://app.a.com");
    application.addToProxy("PXLogicName");
    application.setName("Sync DM");
    
    SimpleParameter parameter = new SimpleParameter();
    parameter.setName("PROVIDER-ID");
    parameter.setValue("MyMail");
    application.addParameter(parameter);  
    
    {
      // Client Authentication parameters
      AppAuthElement appAuth = new AppAuthElement();
      appAuth.setAppAuthType(AppAuthType.HTTP_DIGEST);      
      appAuth.setAppAuthName("appAuthName");
      appAuth.setAppAuthSecret("appAuthSecret");
      application.addAppAuthElement(appAuth);
    }
    {
      // Client Authentication parameters
      AppAuthElement appClientAuth = new AppAuthElement();
      appClientAuth.setAppAuthLevel(AppAuthLevel.APPSRV);
      appClientAuth.setAppAuthType(AppAuthType.DIGEST);
      appClientAuth.setAppAuthType(AppAuthType.BASIC);
      appClientAuth.setAppAuthSecret("appAuthSecret");
      appClientAuth.setAppAuthData("2007-09-09");
      application.addAppAuthElement(appClientAuth);
    }
    {
      // Server Authentication parameters, used by OMA DS 1.2 Notification
      AppAuthElement appServerAuth = new AppAuthElement();
      appServerAuth.setAppAuthLevel(AppAuthLevel.CLIENT);
      appServerAuth.setAppAuthName("otasds");
      appServerAuth.setAppAuthSecret("otasds");
      appServerAuth.setAppAuthData("2007-09-09");
      application.addAppAuthElement(appServerAuth);
    }

    return settings;
  }

  /**
   * 测试缺省型号的DM模板的合并结果.
   * @throws Exception
   */
  public void testMergeOMACP() throws Exception {
    OTAInventory inventory = getOTAInvetory();
    assertTrue(inventory instanceof OTAInventoryImpl);
    
    ClientProvTemplate template = inventory.findTemplate(null, null, ProfileCategory.DM_CATEGORY_NAME);
    assertNotNull(template);
    
    OMAClientProvSettings settings = getDMSettings();
    
    ProvisioningDoc doc = template.merge(settings );
    String content = doc.getContent();
    
    String expected = "<?xml version='1.0' encoding='UTF-8'?>\n" +
    "<!-- \n" +
    " -->\n" +
    "<wap-provisioningdoc version=\"1.1\">\n" +
    "    <characteristic type=\"PXLOGICAL\">\n" +
    "        <parm name=\"PROXY-ID\" value=\"pxlogicID\"/>\n" +
    "        <parm name=\"NAME\" value=\"PXLogicName\"/>\n" +
    "        <parm name=\"STARTPAGE\" value=\"http://start.a.com\"/>\n" +
    "        <characteristic type=\"PXAUTHINFO\">\n" +
    "            <parm name=\"PXAUTH-TYPE\" value=\"HTTP-BASIC\"/>\n" +
    "            <parm name=\"PXAUTH-ID\" value=\"pxAuthID\"/>\n" +
    "            <parm name=\"PXAUTH-PW\" value=\"pxAuthPW\"/>\n" +
    "        </characteristic>\n" +
    "                \n" +
    "        <characteristic type=\"PXPHYSICAL\">\n" +
    "            <parm name=\"PHYSICAL-PROXY-ID\" value=\"pxPhysical\"/>\n" +
    "            <parm name=\"PXADDR\" value=\"pxPhysicalAddr\"/>\n" +
    "            <parm name=\"PXADDRTYPE\" value=\"IPV4\"/>\n" +
    "            <parm name=\"TO-NAPID\" value=\"NAPID\"/>\n" +
    "            <characteristic type=\"PORT\">\n" +
    "                <parm name=\"PORTNBR\" value=\"8080\"/>\n" +
    "                <parm name=\"SERVICE\" value=\"CL-SEC-WSP\"/>" +
    "                <parm name=\"SERVICE\" value=\"OTA-HTTP-PO\"/>" +
    "            </characteristic>" +
    "        </characteristic>" +
    "    </characteristic>" +
    "" +
        "<characteristic type=\"NAPDEF\">" +
    "        <parm name=\"NAPID\" value=\"NAPID\"/>" +
    "        <parm name=\"BEARER\" value=\"GSM-GPRS\"/>" +
    "        <parm name=\"NAME\" value=\"NAPName\"/>" +
    "        <parm name=\"NAP-ADDRESS\" value=\"NAPAddr\"/>" +
    "        <parm name=\"NAP-ADDRTYPE\" value=\"APN\"/>" +
    "        <characteristic type=\"NAPAUTHINFO\">" +
    "            <parm name=\"AUTHTYPE\" value=\"PAP\"/>" +
    "            <parm name=\"AUTHNAME\" value=\"authName\"/>" +
    "            <parm name=\"AUTHSECRET\" value=\"authSecret\"/>" +
    "        </characteristic>" +
    "    </characteristic>" +
    "" +
    "  <characteristic type=\"APPLICATION\"> "+
    "    <parm name=\"APPID\" value=\"w7\"/> "+
    "    <parm name=\"NAME\" value=\"Sync DM\"/>"+  
    "    <parm name=\"TO-PROXY\" value=\"PXLogicName\"/>"+  
    "    <parm name=\"ADDR\" value=\"http://app.a.com\"/> "+ 
    "    <parm name=\"PROVIDER-ID\" value=\"MyMail\"/>  "+
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHTYPE\" value=\"HTTP-DIGEST\"/>"+  
    "      <parm name=\"AAUTHNAME\" value=\"appAuthName\"/>"+  
    "      <parm name=\"AAUTHSECRET\" value=\"appAuthSecret\"/>"+ 
    "    </characteristic>"+  
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHLEVEL\" value=\"APPSRV\"/>"+  
    "      <parm name=\"AAUTHTYPE\" value=\"BASIC\"/>  "+
    "      <parm name=\"AAUTHSECRET\" value=\"appAuthSecret\"/> "+ 
    "      <parm name=\"AAUTHDATA\" value=\"2007-09-09\"/> "+
    "    </characteristic>"+  
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHLEVEL\" value=\"CLIENT\"/>"+  
    "      <parm name=\"AAUTHNAME\" value=\"otasds\"/> "+ 
    "      <parm name=\"AAUTHSECRET\" value=\"otasds\"/>"+  
    "      <parm name=\"AAUTHDATA\" value=\"2007-09-09\"/>"+ 
    "    </characteristic>"+ 
    "  </characteristic> "+
    "" +
    "</wap-provisioningdoc>";
    
    Diff diff = new Diff(expected, content);
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
    
  }

  /**
   * 测试Nokia缺省型号的DM模板的合并结果.
   * @throws Exception
   */
  public void testMergeNokia() throws Exception {
    OTAInventory inventory = getOTAInvetory();
    assertTrue(inventory instanceof OTAInventoryImpl);
    
    ClientProvTemplate template = inventory.findTemplate("nokia", "6681", ProfileCategory.DM_CATEGORY_NAME);
    assertNotNull(template);
    
    OMAClientProvSettings settings = getDMSettings();
    
    ProvisioningDoc doc = template.merge(settings );
    String content = doc.getContent();
    
    String expected = "<?xml version='1.0' encoding='UTF-8'?>\n" +
    "<!-- \n" +
    " -->\n" +
    "<wap-provisioningdoc version=\"1.1\">\n" +
    "    <characteristic type=\"PXLOGICAL\">\n" +
    "        <parm name=\"PROXY-ID\" value=\"pxlogicID\"/>\n" +
    "        <parm name=\"NAME\" value=\"PXLogicName\"/>\n" +
    "        <parm name=\"STARTPAGE\" value=\"http://start.a.com\"/>\n" +
    "        <characteristic type=\"PXAUTHINFO\">\n" +
    "            <parm name=\"PXAUTH-TYPE\" value=\"HTTP-BASIC\"/>\n" +
    "            <parm name=\"PXAUTH-ID\" value=\"pxAuthID\"/>\n" +
    "            <parm name=\"PXAUTH-PW\" value=\"pxAuthPW\"/>\n" +
    "        </characteristic>\n" +
    "                \n" +
    "        <characteristic type=\"PXPHYSICAL\">\n" +
    "            <parm name=\"PHYSICAL-PROXY-ID\" value=\"pxPhysical\"/>\n" +
    "            <parm name=\"PXADDR\" value=\"pxPhysicalAddr\"/>\n" +
    "            <parm name=\"PXADDRTYPE\" value=\"IPV4\"/>\n" +
    "            <parm name=\"TO-NAPID\" value=\"NAPID\"/>\n" +
    "            <characteristic type=\"PORT\">\n" +
    "                <parm name=\"PORTNBR\" value=\"8080\"/>\n" +
    "                <parm name=\"SERVICE\" value=\"CL-SEC-WSP\"/>" +
    "                <parm name=\"SERVICE\" value=\"OTA-HTTP-PO\"/>" +
    "            </characteristic>" +
    "        </characteristic>" +
    "    </characteristic>" +
    "" +
        "<characteristic type=\"NAPDEF\">" +
    "        <parm name=\"NAPID\" value=\"NAPID\"/>" +
    "        <parm name=\"BEARER\" value=\"GSM-GPRS\"/>" +
    "        <parm name=\"NAME\" value=\"NAPName\"/>" +
    "        <parm name=\"NAP-ADDRESS\" value=\"NAPAddr\"/>" +
    "        <parm name=\"NAP-ADDRTYPE\" value=\"APN\"/>" +
    "        <characteristic type=\"NAPAUTHINFO\">" +
    "            <parm name=\"AUTHTYPE\" value=\"PAP\"/>" +
    "            <parm name=\"AUTHNAME\" value=\"authName\"/>" +
    "            <parm name=\"AUTHSECRET\" value=\"authSecret\"/>" +
    "        </characteristic>" +
    "    </characteristic>" +
    "" +
    "  <characteristic type=\"APPLICATION\"> "+
    "    <parm name=\"APPID\" value=\"w7\"/> "+
    "    <parm name=\"NAME\" value=\"Sync DM\"/>"+  
    "    <parm name=\"TO-PROXY\" value=\"PXLogicName\"/>"+  
    "    <parm name=\"ADDR\" value=\"http://app.a.com\"/> "+ 
    "    <parm name=\"PROVIDER-ID\" value=\"MyMail\"/>  "+
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHTYPE\" value=\"HTTP-DIGEST\"/>"+  
    "      <parm name=\"AAUTHNAME\" value=\"appAuthName\"/>"+  
    "      <parm name=\"AAUTHSECRET\" value=\"appAuthSecret\"/>"+ 
    "    </characteristic>"+  
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHLEVEL\" value=\"APPSRV\"/>"+  
    "      <parm name=\"AAUTHTYPE\" value=\"BASIC\"/>  "+
    "      <parm name=\"AAUTHSECRET\" value=\"appAuthSecret\"/> "+ 
    "      <parm name=\"AAUTHDATA\" value=\"2007-09-09\"/> "+
    "    </characteristic>"+  
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHLEVEL\" value=\"CLIENT\"/>"+  
    "      <parm name=\"AAUTHNAME\" value=\"otasds\"/> "+ 
    "      <parm name=\"AAUTHSECRET\" value=\"otasds\"/>"+  
    "      <parm name=\"AAUTHDATA\" value=\"2007-09-09\"/>"+ 
    "    </characteristic>"+ 
    "  </characteristic> "+
    "" +
    "</wap-provisioningdoc>";
    
    Diff diff = new Diff(expected, content);
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
    
  }

  /**
   * 测试SonyEricsson W800的DM模板的合并结果.
   * @throws Exception
   */
  public void testMergeSonyEricssonW800() throws Exception {
    OTAInventory inventory = getOTAInvetory();
    assertTrue(inventory instanceof OTAInventoryImpl);
    
    ClientProvTemplate template = inventory.findTemplate("SonyEricsson", "W800", ProfileCategory.DM_CATEGORY_NAME);
    assertNotNull(template);
    
    OMAClientProvSettings settings = getDMSettings();
    
    ProvisioningDoc doc = template.merge(settings );
    String content = doc.getContent();
    
    String expected = "<?xml version='1.0' encoding='UTF-8'?>\n" +
    "<!-- \n" +
    " -->\n" +
    "<wap-provisioningdoc version=\"1.1\">\n" +
    "    <characteristic type=\"PXLOGICAL\">\n" +
    "        <parm name=\"PROXY-ID\" value=\"pxlogicID\"/>\n" +
    "        <parm name=\"NAME\" value=\"PXLogicName\"/>\n" +
    "        <parm name=\"STARTPAGE\" value=\"http://start.a.com\"/>\n" +
    "        <characteristic type=\"PXAUTHINFO\">\n" +
    "            <parm name=\"PXAUTH-TYPE\" value=\"HTTP-BASIC\"/>\n" +
    "            <parm name=\"PXAUTH-ID\" value=\"pxAuthID\"/>\n" +
    "            <parm name=\"PXAUTH-PW\" value=\"pxAuthPW\"/>\n" +
    "        </characteristic>\n" +
    "                \n" +
    "        <characteristic type=\"PXPHYSICAL\">\n" +
    "            <parm name=\"PHYSICAL-PROXY-ID\" value=\"pxPhysical\"/>\n" +
    "            <parm name=\"PXADDR\" value=\"pxPhysicalAddr\"/>\n" +
    "            <parm name=\"PXADDRTYPE\" value=\"IPV4\"/>\n" +
    "            <parm name=\"TO-NAPID\" value=\"NAPID\"/>\n" +
    "            <characteristic type=\"PORT\">\n" +
    "                <parm name=\"PORTNBR\" value=\"8080\"/>\n" +
    "                <parm name=\"SERVICE\" value=\"CL-SEC-WSP\"/>" +
    "                <parm name=\"SERVICE\" value=\"OTA-HTTP-PO\"/>" +
    "            </characteristic>" +
    "        </characteristic>" +
    "    </characteristic>" +
    "" +
        "<characteristic type=\"NAPDEF\">" +
    "        <parm name=\"NAPID\" value=\"NAPID\"/>" +
    "        <parm name=\"BEARER\" value=\"GSM-GPRS\"/>" +
    "        <parm name=\"NAME\" value=\"NAPName\"/>" +
    "        <parm name=\"NAP-ADDRESS\" value=\"NAPAddr\"/>" +
    "        <parm name=\"NAP-ADDRTYPE\" value=\"APN\"/>" +
    "        <characteristic type=\"NAPAUTHINFO\">" +
    "            <parm name=\"AUTHTYPE\" value=\"PAP\"/>" +
    "            <parm name=\"AUTHNAME\" value=\"authName\"/>" +
    "            <parm name=\"AUTHSECRET\" value=\"authSecret\"/>" +
    "        </characteristic>" +
    "    </characteristic>" +
    "" +
    "  <characteristic type=\"APPLICATION\"> "+
    "    <parm name=\"APPID\" value=\"w7\"/> "+
    "    <parm name=\"NAME\" value=\"Sync DM\"/>"+  
    "    <parm name=\"TO-PROXY\" value=\"PXLogicName\"/>"+  
    "    <parm name=\"ADDR\" value=\"http://app.a.com\"/> "+ 
    "    <parm name=\"PROVIDER-ID\" value=\"MyMail\"/>  "+
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHTYPE\" value=\"HTTP-DIGEST\"/>"+  
    "      <parm name=\"AAUTHNAME\" value=\"appAuthName\"/>"+  
    "      <parm name=\"AAUTHSECRET\" value=\"appAuthSecret\"/>"+ 
    "    </characteristic>"+  
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHLEVEL\" value=\"APPSRV\"/>"+  
    "      <parm name=\"AAUTHTYPE\" value=\"BASIC\"/>  "+
    "      <parm name=\"AAUTHSECRET\" value=\"appAuthSecret\"/> "+ 
    "      <parm name=\"AAUTHDATA\" value=\"2007-09-09\"/> "+
    "    </characteristic>"+  
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHLEVEL\" value=\"CLIENT\"/>"+  
    "      <parm name=\"AAUTHNAME\" value=\"otasds\"/> "+ 
    "      <parm name=\"AAUTHSECRET\" value=\"otasds\"/>"+  
    "      <parm name=\"AAUTHDATA\" value=\"2007-09-09\"/>"+ 
    "    </characteristic>"+ 
    "  </characteristic> "+
    "" +
    "</wap-provisioningdoc>";
    
    Diff diff = new Diff(expected, content);
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
    
  }

  /**
   * 测试SonyEricsson W810的DM模板的合并结果.
   * @throws Exception
   */
  public void testMergeSonyEricsson810() throws Exception {
    OTAInventory inventory = getOTAInvetory();
    assertTrue(inventory instanceof OTAInventoryImpl);
    
    ClientProvTemplate template = inventory.findTemplate("SonyEricsson", "W810", ProfileCategory.DM_CATEGORY_NAME);
    assertNotNull(template);
    
    OMAClientProvSettings settings = getDMSettings();
    
    ProvisioningDoc doc = template.merge(settings );
    String content = doc.getContent();
    
    String expected = "<?xml version='1.0' encoding='UTF-8'?>\n" +
    "<!-- \n" +
    " -->\n" +
    "<wap-provisioningdoc version=\"1.1\">\n" +
    "    <characteristic type=\"PXLOGICAL\">\n" +
    "        <parm name=\"PROXY-ID\" value=\"pxlogicID\"/>\n" +
    "        <parm name=\"NAME\" value=\"PXLogicName\"/>\n" +
    "        <parm name=\"STARTPAGE\" value=\"http://start.a.com\"/>\n" +
    "        <characteristic type=\"PXAUTHINFO\">\n" +
    "            <parm name=\"PXAUTH-TYPE\" value=\"HTTP-BASIC\"/>\n" +
    "            <parm name=\"PXAUTH-ID\" value=\"pxAuthID\"/>\n" +
    "            <parm name=\"PXAUTH-PW\" value=\"pxAuthPW\"/>\n" +
    "        </characteristic>\n" +
    "                \n" +
    "        <characteristic type=\"PXPHYSICAL\">\n" +
    "            <parm name=\"PHYSICAL-PROXY-ID\" value=\"pxPhysical\"/>\n" +
    "            <parm name=\"PXADDR\" value=\"pxPhysicalAddr\"/>\n" +
    "            <parm name=\"PXADDRTYPE\" value=\"IPV4\"/>\n" +
    "            <parm name=\"TO-NAPID\" value=\"NAPID\"/>\n" +
    "            <characteristic type=\"PORT\">\n" +
    "                <parm name=\"PORTNBR\" value=\"8080\"/>\n" +
    "                <parm name=\"SERVICE\" value=\"CL-SEC-WSP\"/>" +
    "                <parm name=\"SERVICE\" value=\"OTA-HTTP-PO\"/>" +
    "            </characteristic>" +
    "        </characteristic>" +
    "    </characteristic>" +
    "" +
        "<characteristic type=\"NAPDEF\">" +
    "        <parm name=\"NAPID\" value=\"NAPID\"/>" +
    "        <parm name=\"BEARER\" value=\"GSM-GPRS\"/>" +
    "        <parm name=\"NAME\" value=\"NAPName\"/>" +
    "        <parm name=\"NAP-ADDRESS\" value=\"NAPAddr\"/>" +
    "        <parm name=\"NAP-ADDRTYPE\" value=\"APN\"/>" +
    "        <characteristic type=\"NAPAUTHINFO\">" +
    "            <parm name=\"AUTHTYPE\" value=\"PAP\"/>" +
    "            <parm name=\"AUTHNAME\" value=\"authName\"/>" +
    "            <parm name=\"AUTHSECRET\" value=\"authSecret\"/>" +
    "        </characteristic>" +
    "    </characteristic>" +
    "" +
    "  <characteristic type=\"APPLICATION\"> "+
    "    <parm name=\"APPID\" value=\"w7\"/> "+
    "    <parm name=\"NAME\" value=\"Sync DM\"/>"+  
    "    <parm name=\"TO-PROXY\" value=\"PXLogicName\"/>"+  
    "    <parm name=\"ADDR\" value=\"http://app.a.com\"/> "+ 
    "    <parm name=\"PROVIDER-ID\" value=\"MyMail\"/>  "+
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHTYPE\" value=\"HTTP-DIGEST\"/>"+  
    "      <parm name=\"AAUTHNAME\" value=\"appAuthName\"/>"+  
    "      <parm name=\"AAUTHSECRET\" value=\"appAuthSecret\"/>"+ 
    "    </characteristic>"+  
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHLEVEL\" value=\"APPSRV\"/>"+  
    "      <parm name=\"AAUTHTYPE\" value=\"BASIC\"/>  "+
    "      <parm name=\"AAUTHSECRET\" value=\"appAuthSecret\"/> "+ 
    "      <parm name=\"AAUTHDATA\" value=\"2007-09-09\"/> "+
    "    </characteristic>"+  
    "    <characteristic type=\"APPAUTH\">"+ 
    "      <parm name=\"AAUTHLEVEL\" value=\"CLIENT\"/>"+  
    "      <parm name=\"AAUTHNAME\" value=\"otasds\"/> "+ 
    "      <parm name=\"AAUTHSECRET\" value=\"otasds\"/>"+  
    "      <parm name=\"AAUTHDATA\" value=\"2007-09-09\"/>"+ 
    "    </characteristic>"+ 
    "  </characteristic> "+
    "" +
    "</wap-provisioningdoc>";
    
    Diff diff = new Diff(expected, content);
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
    
  }

}
