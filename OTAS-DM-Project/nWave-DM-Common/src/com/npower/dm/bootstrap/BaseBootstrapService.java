/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/bootstrap/BaseBootstrapService.java,v 1.3 2008/03/11 03:43:32 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/03/11 03:43:32 $
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

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.Device;
import com.npower.dm.util.DMUtil;
import com.npower.wap.omacp.elements.AppAuthElement;
import com.npower.wap.omacp.elements.ApplicationElement;
import com.npower.wap.omacp.elements.NAPAuthInfoElement;
import com.npower.wap.omacp.elements.NAPDefElement;
import com.npower.wap.omacp.elements.PXLogicalElement;
import com.npower.wap.omacp.elements.PXPhysicalElement;
import com.npower.wap.omacp.elements.PortElement;
import com.npower.wap.omacp.parameters.AppAuthLevel;
import com.npower.wap.omacp.parameters.AppID;
import com.npower.wap.omacp.parameters.AuthType;
import com.npower.wap.omacp.parameters.Bearer;
import com.npower.wap.omacp.parameters.NAPAddrType;
import com.npower.wap.omacp.parameters.PXAddrType;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/03/11 03:43:32 $
 */
public abstract class BaseBootstrapService extends AbstractBootstrapService implements BootstrapService {
  
  private Properties profileProperties = new Properties();
  
  /**
   * 
   */
  protected BaseBootstrapService() {
    super();
  }
  
  // Protected methods ------------------------------------------------------------------------
  /**
   * Prepare OTA profile for OTA template.
   * @return OTA Profile.
   */
  protected Properties prepareProfile(Device device) {
    String serverPassword = device.getOMAServerPassword();
    String serverNonce = device.getOMAServerNonce();
    String clientUsername = device.getOMAClientUsername();
    String clientPassword = device.getOMAClientPassword();
    String clientNonce = device.getOMAClientNonce();

    return prepareProfile(serverPassword, serverNonce, clientUsername, clientPassword, clientNonce);
  }

  /**
   * @param serverPassword
   * @param serverNonce
   * @param clientUsername
   * @param clientPassword
   * @param clientNonce
   * @return
   */
  protected Properties prepareProfile(String serverPassword, String serverNonce, String clientUsername, String clientPassword, String clientNonce) {
    Properties profileProperties = this.getProfileProperties();
    profileProperties.setProperty("dm_service_srv_pwd", serverPassword);
    serverNonce = (serverNonce == null)?"":serverNonce;
    profileProperties.setProperty("dm_service_srv_nonce", serverNonce);
    profileProperties.setProperty("dm_service_cli_uid", clientUsername);
    profileProperties.setProperty("dm_service_cli_pwd", clientPassword);
    clientNonce = (clientNonce == null)?"":clientNonce;
    profileProperties.setProperty("dm_service_cli_nonce", clientNonce);
    return profileProperties;
  }

  protected String getProperty(Properties props, String name) {
    String s = props.getProperty(name);
    return s;
  }

  /**
   * @param profile
   * @return
   */
  protected ApplicationElement buildDMApplication(Properties profile, String napID, String proxyID, String targetMsisdn) {
    ApplicationElement app = new ApplicationElement(AppID.W7_SyncML_Device_Management);
    
    // Add nap id or Proxy ID
    if (StringUtils.isNotEmpty(napID) || StringUtils.isNotEmpty(proxyID)) {
       if ( StringUtils.isNotEmpty(proxyID)) {
          // Add NAP with Proxy
          app.addToProxy(proxyID);
       } else {
         // Add NAP without proxy
         app.addToNAPID(napID);
       }
    }
    
    // Set DM parameters
    app.setName(this.getProperty(profile, "dm_service_name"));
    String dmServerURL = this.getProperty(profile, "dm_service_url");
    // Append target msisdn into DM URL
    URLParameters params = new URLParameters();
    params.setMsisdn(targetMsisdn);
    String paramValue = params.encode();
    dmServerURL = DMUtil.appendParameter(dmServerURL, BootstrapService.BOOTSTRAP_PARAMETRERS_NAME, paramValue);        
    app.addAddr(dmServerURL);
    
    app.setProviderID(this.getProperty(profile, "dm_service_id"));
    
    // DM Server Auth
    AppAuthElement auth1 = new AppAuthElement();
    auth1.setAppAuthLevel(AppAuthLevel.APPSRV);
    auth1.setAppAuthName(this.getProperty(profile, "dm_service_srv_uid"));
    auth1.setAppAuthSecret(this.getProperty(profile, "dm_service_srv_pwd"));
    app.addAppAuthElement(auth1);
    
    // DM Client Auth
    AppAuthElement auth2 = new AppAuthElement();
    auth2.setAppAuthLevel(AppAuthLevel.CLIENT);
    auth2.setAppAuthName(this.getProperty(profile, "dm_service_cli_uid"));
    auth2.setAppAuthSecret(this.getProperty(profile, "dm_service_cli_pwd"));
    app.addAppAuthElement(auth2);
    
    return app;
  }

  /**
   * Create NAP Def setting
   * @return setting
   */
  protected NAPDefElement createNAPDef(Properties profile) {
    String napDefNapID = this.getProperty(profile, "napdef_napid");
    String napDefName = this.getProperty(profile, "napdef_name");
    String napDefAddr = this.getProperty(profile, "napdef_nap_addr");
    if (StringUtils.isEmpty(napDefNapID) 
        || StringUtils.isEmpty(napDefName)
        || StringUtils.isEmpty(napDefAddr)) {
       return null;
    }
    NAPDefElement napDefElement = new NAPDefElement(napDefNapID, napDefName, napDefAddr);
    napDefElement.addBearer(Bearer.value(this.getProperty(profile, "napdef_bearer")));
    napDefElement.setNapAddrType(NAPAddrType.value(this.getProperty(profile, "napdef_apn")));
    
    String authType = this.getProperty(profile, "napdef_nap_auth_type");
    String authName = this.getProperty(profile, "napdef_nap_auth_name");
    String authSecret = this.getProperty(profile, "napdef_nap_auth_secret");
    if (!StringUtils.isEmpty(authType) && !StringUtils.isEmpty(authName) ) {
       NAPAuthInfoElement napAuthInfo = new NAPAuthInfoElement(AuthType.value(authType));
       napAuthInfo.setAuthName(authName);
       napAuthInfo.setAuthSecret(authSecret);
       napDefElement.addNapAuthInfo(napAuthInfo);
    }
    return napDefElement;
  }

  /**
   * Create NAP Def setting
   * @return setting
   */
  protected PXLogicalElement createProxyDef(Properties profile, String napID) {
    
    String pxLogicID = this.getProperty(profile, "pxlogic_id");
    String pxLogicName = this.getProperty(profile, "pxlogic_name");
    if (StringUtils.isEmpty(pxLogicID) 
        || StringUtils.isEmpty(pxLogicName)) {
       return null;
    }
    PXLogicalElement pxLogicElement = new PXLogicalElement(pxLogicID, pxLogicName);
    pxLogicElement.setStartPage(this.getProperty(profile, "pxlogic_startpage"));

    String pxPhyID = pxLogicID;
    String pxAddress = this.getProperty(profile, "pxlogic_phy_proxy_addr");
    PXPhysicalElement phyElement = new PXPhysicalElement(pxPhyID, pxAddress, napID);
    phyElement.setPxAddrType(PXAddrType.value(this.getProperty(profile, "pxlogic_phy_proxy_addr_type")));
    String portStr = this.getProperty(profile, "pxlogic_phy_proxy_addr_port");
    if (StringUtils.isNotEmpty(portStr)) {
       PortElement port = new PortElement(portStr);
       phyElement.addPortElement(port);
    }
    pxLogicElement.addPXPhysicalElement(phyElement);

    return pxLogicElement;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.bootstrap.BootstrapService#getProfileProperties()
   */
  public Properties getProfileProperties() {
    return profileProperties;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.bootstrap.BootstrapService#setProfileProperties(java.util.Properties)
   */
  public void setProfileProperties(Properties profile) {
    this.profileProperties = profile;
  }
}
