/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/cp/TestTemplateMerge.java,v 1.6 2007/08/28 09:27:16 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2007/08/28 09:27:16 $
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
package com.npower.cp;

import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;

import com.npower.cp.xmlinventory.OTAInventoryImpl;
import com.npower.dm.AllTests;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.wap.omacp.OMAClientProvSettings;
import com.npower.wap.omacp.elements.AppAuthElement;
import com.npower.wap.omacp.elements.ApplicationElement;
import com.npower.wap.omacp.elements.NAPAuthInfoElement;
import com.npower.wap.omacp.elements.NAPDefElement;
import com.npower.wap.omacp.elements.PXAuthInfoElement;
import com.npower.wap.omacp.elements.PXLogicalElement;
import com.npower.wap.omacp.elements.PXPhysicalElement;
import com.npower.wap.omacp.elements.PortElement;
import com.npower.wap.omacp.elements.ResourceElement;
import com.npower.wap.omacp.parameters.AppID;
import com.npower.wap.omacp.parameters.AuthType;
import com.npower.wap.omacp.parameters.Bearer;
import com.npower.wap.omacp.parameters.NAPAddrType;
import com.npower.wap.omacp.parameters.PXAddrType;
import com.npower.wap.omacp.parameters.PXAuthType;
import com.npower.wap.omacp.parameters.Service;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2007/08/28 09:27:16 $
 */
public class TestTemplateMerge extends TestCase {

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
   * @throws OTAException
   */
  private OTAInventory getOTAInvetory() throws OTAException {
    Properties props = new Properties();
    props.setProperty(OTAInventory.PROPERTY_FACTORY, OTAInventoryImpl.class.getName());
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR, AllTests.BASE_DIR + "/metadata/cp/test");
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_FILENAME, "test.3.inventory.xml");
    
    OTAInventory inventory = OTAInventory.newInstance(props);
    return inventory;
  }
  
  /**
   * @return
   */
  private OMAClientProvSettings getSettings() {
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
    
    ApplicationElement application = new ApplicationElement(AppID.W5_SyncML_PUSH_Application);
    settings.addApplicationElement(application);
    application.addAddr("http://app.a.com");
    application.setName("OTAS SyncDS");
    application.addToNAPID("NAPID");
    AppAuthElement appAuth = new AppAuthElement();
    appAuth.setAppAuthName("appAuthName");
    appAuth.setAppAuthSecret("appAuthSecret");
    application.addAppAuthElement(appAuth);
    
    ResourceElement resource1 = new ResourceElement("./card");
    resource1.setName("Resource1Name");
    resource1.setAppAccept("text/x-vcard,text/vcard");
    application.addResourceElement(resource1);
    
    ResourceElement resource2 = new ResourceElement("./cal");
    resource2.setName("Resource2Name");
    resource2.setAppAccept("text/x-vcalendar");
    application.addResourceElement(resource2);
    return settings;
  }

  public void testMergeBasic() throws Exception {
    OTAInventory inventory = getOTAInvetory();
    assertTrue(inventory instanceof OTAInventoryImpl);
    
    List<ClientProvTemplate> templates = inventory.getTemplates("nokia", "6620", "SyncDS");
    assertNotNull(templates);
    assertEquals(1, templates.size());
    
    ClientProvTemplate template = templates.get(0);
    
    OMAClientProvSettings settings = getSettings();
    
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
        "<characteristic type=\"APPLICATION\">" +
    "        <parm name=\"APPID\" value=\"w5\"/>" +
    "        <parm name=\"NAME\" value=\"OTAS SyncDS\"/>" +
    "        <characteristic type=\"APPADDR\">" +
    "            <parm name=\"ADDR\" value=\"http://app.a.com\"/>" +
    "        </characteristic>" +
    "        <characteristic type=\"APPAUTH\">" +
    "            <parm name=\"AAUTHNAME\" value=\"appAuthName\"/>" +
    "            <parm name=\"AAUTHSECRET\" value=\"appAuthSecret\"/>" +
    "        </characteristic>" +
    "        <characteristic type=\"RESOURCE\">" +
    "            <parm name=\"URI\" value=\"./card\"/>" +
    "            <parm name=\"NAME\" value=\"Resource1Name\"/>" +
    "            <parm name=\"AACCEPT\" value=\"text/x-vcard,text/vcard\"/>" +
    "        </characteristic>" +
    "        <characteristic type=\"RESOURCE\">" +
    "            <parm name=\"URI\" value=\"./cal\"/>" +
    "            <parm name=\"NAME\" value=\"Resource2Name\"/>" +
    "            <parm name=\"AACCEPT\" value=\"text/x-vcalendar\"/>" +
    "        </characteristic>" +
    "    </characteristic>" +
    "" +
    "</wap-provisioningdoc>";
    
    Diff diff = new Diff(expected, content);
    
    assertTrue("XML Similar " + diff.toString(), diff.similar());
    assertTrue("XML Identical " + diff.toString(), diff.identical());
    
  }


}
