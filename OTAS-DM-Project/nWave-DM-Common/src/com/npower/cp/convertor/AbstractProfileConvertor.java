/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/AbstractProfileConvertor.java,v 1.15 2008/10/16 00:06:34 zhao Exp $
 * $Revision: 1.15 $
 * $Date: 2008/10/16 00:06:34 $
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.wap.nokia.NokiaOTASettings;
import com.npower.wap.nokia.NokiaOtaBookmarkSettings;
import com.npower.wap.nokia.NokiaOtaBrowserSettings;
import com.npower.wap.nokia.browser.AddressGPRS;
import com.npower.wap.nokia.browser.PPPAuthType;
import com.npower.wap.nokia.browser.Port;
import com.npower.wap.omacp.OMAClientProvSettings;
import com.npower.wap.omacp.Parameter;
import com.npower.wap.omacp.SimpleParameter;
import com.npower.wap.omacp.elements.AppAddrElement;
import com.npower.wap.omacp.elements.AppAuthElement;
import com.npower.wap.omacp.elements.ApplicationElement;
import com.npower.wap.omacp.elements.NAPAuthInfoElement;
import com.npower.wap.omacp.elements.NAPDefElement;
import com.npower.wap.omacp.elements.PXAuthInfoElement;
import com.npower.wap.omacp.elements.PXLogicalElement;
import com.npower.wap.omacp.elements.PXPhysicalElement;
import com.npower.wap.omacp.elements.PortElement;
import com.npower.wap.omacp.elements.ResourceElement;
import com.npower.wap.omacp.parameters.AppAuthLevel;
import com.npower.wap.omacp.parameters.AppID;
import com.npower.wap.omacp.parameters.AuthType;
import com.npower.wap.omacp.parameters.Bearer;
import com.npower.wap.omacp.parameters.NAPAddrType;
import com.npower.wap.omacp.parameters.PXAddrType;
import com.npower.wap.omacp.parameters.PXAuthType;
import com.npower.wap.omacp.parameters.Service;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.15 $ $Date: 2008/10/16 00:06:34 $
 */
public abstract class AbstractProfileConvertor implements ProfileConvertor {

  private ValueFetcher<ProfileCategory, String, String> valueFetcher;

  /**
   * Default Constructor
   */
  protected AbstractProfileConvertor() {
    super();
  }

  /**
   * <pre>
   * 提取参数attributeName的值.
   * 提取规则如下：
   * 1、首先从外部指定的ValueFetcher中提取值，如果提取到非空值，则返回结果；
   * 2、从Profile中提取值，并返回。
   * </pre>
   * 
   * 从ProfileConfig中提取参数attributeName的值
   * @param config
   *        ProfileConfig
   * @param attributeName
   *        Name of attribute
   * @return
   * @throws DMException
   */
  private String getAttributeValue(ProfileConfig config, String attributeName) throws DMException {
    String value = null;
    // Fetch value from fetcher chain
    if (this.valueFetcher != null) {
       value = this.valueFetcher.getValue(config.getProfileTemplate().getProfileCategory(), attributeName);
    }
    // Fetch value from inside profile
    if (value == null) {
       // Fetch from ProfileValueFetcher
       ProfileValueFetcher fetcher = new ProfileValueFetcher();
       fetcher.setProfile(config);
       value = fetcher.getValue(config.getProfileTemplate().getProfileCategory(), attributeName);
    }
    return value;
  }

  /**
   * Create a NAP OTA Settings 
   * @param assignment
   * @return
   */
  protected NAPDefElement convertNAPProfile(ProfileConfig profile) throws DMException {
    String profileName = profile.getName();
    String napAPN = this.getAttributeValue(profile, "NAP-ADDRESS");
    String napBearer = this.getAttributeValue(profile, "BEARER");
    String id = getNapID(profile);
    
    NAPDefElement napDefElement = new NAPDefElement(id, profileName, napAPN);
    napDefElement.addBearer(Bearer.value(napBearer));
    napDefElement.setNapAddrType(NAPAddrType.APN);
    
    String authName = this.getAttributeValue(profile, "AUTHNAME");
    String authType = this.getAttributeValue(profile, "AUTHTYPE");
    String authSecret = this.getAttributeValue(profile, "AUTHSECRET");
    if (StringUtils.isNotEmpty(authName)) {
       NAPAuthInfoElement napAuthInfo = new NAPAuthInfoElement(AuthType.value(authType));
       napAuthInfo.setAuthName(authName);
       napAuthInfo.setAuthSecret(authSecret);
       napDefElement.addNapAuthInfo(napAuthInfo);
    }
    return napDefElement;
  }

  /**
   * Return ID
   * @param profile
   * @return
   */
  private String getNapID(ProfileConfig profile) {
    //String napID = profile.getName() + "ID";
    int code = Math.abs(profile.getName().hashCode());
    String id = "ap" + code;
    return id;
  }

  /**
   * Return ID
   * @param profile
   * @return
   */
  private String getProxyLogicID(ProfileConfig profile) {
    //String id = profile.getName() + "PXID";
    int code = Math.abs(profile.getName().hashCode());
    String id = "pl" + code;
    return id;
  }
  
  /**
   * Return ID
   * @param profile
   * @return
   */
  private String getProxyPhsicalID(ProfileConfig profile) {
    //String id = profile.getName() + "PXID";
    int code = Math.abs(profile.getName().hashCode());
    String id = "pp" + code;
    return id;
  }
  
  /**
   * Return ID
   * @param profile
   * @return
   */
  private String getBrowserID(ProfileConfig profile) {
    int code = Math.abs(profile.getName().hashCode());
    String id = "br" + code;
    return id;
  }

  /**
   * @param form
   * @param name
   * @param napDefElement
   * @return
   */
  protected PXLogicalElement convertProxyProfile(ProfileConfig profile, NAPDefElement napDefElement) throws DMException {
    String profileName = profile.getName();
    
    String proxyAddr = this.getAttributeValue(profile, "PXADDR");
    if (StringUtils.isEmpty(proxyAddr)) {
       return null;
    }
    
    String proxyLogicId = getProxyLogicID(profile);
    PXLogicalElement pxLogicElement = new PXLogicalElement(proxyLogicId, profileName);
    String startPage = this.getAttributeValue(profile, "STARTPAGE");
    pxLogicElement.setStartPage(startPage);
    // PXAuthInfo
    String authName = this.getAttributeValue(profile, "PXAUTH-ID");
    String authType = this.getAttributeValue(profile, "PXAUTH-TYPE");
    String authSecret = this.getAttributeValue(profile, "PXAUTH-PW");
    if (StringUtils.isNotEmpty(authName)) {
       // PXAuthInfo
       PXAuthInfoElement pxAuthInfo = new PXAuthInfoElement(PXAuthType.value(authType));
       pxAuthInfo.setPxAuthID(authName);
       pxAuthInfo.setPxAuthPW(authSecret);
       pxLogicElement.addPXAuthInfoElement(pxAuthInfo);
    }
    
    // PXPhysical # 1
    {
      String physicalID = getProxyPhsicalID(profile);
      PXPhysicalElement phyElement = new PXPhysicalElement(physicalID, 
                                                           proxyAddr, 
                                                           napDefElement.getNapID());
      phyElement.setPxAddrType(PXAddrType.IPV4);
      String portNum = this.getAttributeValue(profile, "PXPHYSICAL PORTNBR");
      PortElement port = new PortElement(portNum);
      phyElement.addPortElement(port);

      pxLogicElement.addPXPhysicalElement(phyElement);
    }
    
    return pxLogicElement;
  }

  /**
   * 从NAP Profile中提取start page, 创建Bookmark application
   * @param profile
   * @return
   * @throws DMException
   */
  protected ApplicationElement convertNAPProfile4Bookmark(ProfileConfig profile, NAPDefElement napDefElement) throws DMException {
    String profileName = profile.getName();
    String id = napDefElement.getNapID();
    
    String startPage = this.getAttributeValue(profile, "STARTPAGE");
    if (StringUtils.isEmpty(startPage)) {
       return null;
    }
    ApplicationElement app = new ApplicationElement(AppID.W2_WML_User_Agent);
    app.setName(profileName);
    List<String> napIDs = new ArrayList<String>();
    napIDs.add(id);
    app.setToNAPIDs(napIDs);
    
    ResourceElement res = new ResourceElement(startPage);
    res.setName(profileName);
    res.setStartPage(startPage);
    app.addResourceElement(res);
    
    return app;
  }

  /**
   * 从Proxy Profile中提取start page, 创建Bookmark application
   * @param profile
   * @param proxyDefElement
   * @return
   * @throws DMException
   */
  protected ApplicationElement convertProxyProfile4Bookmark(ProfileConfig profile, PXLogicalElement proxyDefElement) throws DMException {
    String profileName = profile.getName();
    String proxyID = proxyDefElement.getProxyID();
    
    String startPage = this.getAttributeValue(profile, "STARTPAGE");
    if (StringUtils.isEmpty(startPage)) {
       return null;
    }
    ApplicationElement app = new ApplicationElement(AppID.W2_WML_User_Agent);
    app.setName(profileName);
    List<String> proxies = new ArrayList<String>();
    proxies.add(proxyID);
    app.setToProxies(proxies);
    
    ResourceElement res = new ResourceElement(startPage);
    res.setName(profileName);
    res.setStartPage(startPage);
    app.addResourceElement(res);
    
    return app;
  }
  
  

  /**
   * @param form
   * @param name
   * @param napDefElement
   * @return
   */
  protected NokiaOtaBrowserSettings convertProxyProfile4NokiaOTA(ProfileConfig proxyProfile) throws DMException {
    NokiaOtaBrowserSettings settings = new NokiaOtaBrowserSettings();
    String profileName = proxyProfile.getName();
    String proxyID = this.getBrowserID(proxyProfile);
    settings.setId(proxyID);
    String proxyAddr = this.getAttributeValue(proxyProfile, "PXADDR");
    if (StringUtils.isEmpty(proxyAddr)) {
       return null;
    }

    ProfileConfig napProfile = proxyProfile.getNAPProfile();
    String napAPN = this.getAttributeValue(napProfile, "NAP-ADDRESS");
    String napBearer = this.getAttributeValue(napProfile, "BEARER");
    if (napBearer.equalsIgnoreCase("GSM-GPRS")) {
       AddressGPRS address = new AddressGPRS(proxyAddr);
       address.setGprsAccessPointName(napAPN);
       settings.addAddress(address);
       
       settings.setName(profileName);
       String startPage = this.getAttributeValue(proxyProfile, "STARTPAGE");
       settings.setUrl(startPage);
       settings.addBookmark(profileName, startPage);
       
       String authName = this.getAttributeValue(napProfile, "AUTHNAME");
       String authType = this.getAttributeValue(napProfile, "AUTHTYPE");
       String authSecret = this.getAttributeValue(napProfile, "AUTHSECRET");
       if (StringUtils.isNotEmpty(authName)) {
          if ("CHAP".equalsIgnoreCase(authType)) {
             address.setPppAuthType(PPPAuthType.CHAP);
          } else if ("PAP".equalsIgnoreCase(authType)) {
            address.setPppAuthType(PPPAuthType.PAP);
          } else if ("MS_CHAP".equalsIgnoreCase(authType)) {
            address.setPppAuthType(PPPAuthType.MS_CHAP);
          } else {
            address.setPppAuthType(PPPAuthType.PAP);
          }
          address.setPppAuthName(authName);
          address.setPppAuthSecret(authSecret);
       }

       // PXAuthInfo
       String pxAuthName = this.getAttributeValue(proxyProfile, "PXAUTH-ID");
       //String pxAuthType = this.getAttributeValue(profile, "PXAUTH-TYPE");
       String pxAuthSecret = this.getAttributeValue(proxyProfile, "PXAUTH-PW");
       if (StringUtils.isNotEmpty(pxAuthName)) {
          address.setProxyAuthName(pxAuthName);
          address.setProxyAuthSecret(pxAuthSecret);
       }
       
       String portNum = this.getAttributeValue(proxyProfile, "PXPHYSICAL PORTNBR");
       address.setPort(Port.value(portNum));
    }
    return settings;
  }

  /**
   * @param form
   * @param name
   * @return
   */
  protected ApplicationElement convertMMSProfile(ProfileConfig profile, NAPDefElement napElement, PXLogicalElement pxElement) throws DMException {
    String profileName = profile.getName();
    ApplicationElement app = new ApplicationElement(AppID.W4_MMS_User_Agent);
    app.setName(profileName);
    if (pxElement != null) {
       app.addToProxy(pxElement.getProxyID());
    } else if (napElement != null) {
      app.addToNAPID(napElement.getNapID());
    }
    String mmsURL = this.getAttributeValue(profile, "MMSC Server");
    app.addAddr(mmsURL);
    
    // Keep NAP and Proxy name same with MMS profile
    /*
    if (pxElement != null) {
       pxElement.setName(profileName);
    }
    if (napElement != null) {
      napElement.setName(profileName);
    }
    */
    return app;
  }

  /**
   * @param form
   * @param name
   * @return
   */
  protected NokiaOtaBrowserSettings convertMMSProfile4NokiaOTA(ProfileConfig mmsProfile) throws DMException {
    String profileName = mmsProfile.getName();
    ProfileConfig proxyProfile = mmsProfile.getProxyProfile();
    
    NokiaOtaBrowserSettings settings = this.convertProxyProfile4NokiaOTA(proxyProfile);
    String mmsURL = this.getAttributeValue(mmsProfile, "MMSC Server");
    settings.setName(profileName);
    settings.setMmsurl(mmsURL);
    settings.setId(settings.getId());
    return settings;
  }

  /**
   * @param form
   * @param napName
   * @param napDefElement
   * @return
   */
  protected ApplicationElement convertIncommingEmailProfile(ProfileConfig profile, NAPDefElement napDefElement) throws DMException {
    String protocol = this.getAttributeValue(profile, "Mailbox Protocol");
    AppID appID = (protocol == null || protocol.toLowerCase().indexOf("pop") >= 0)?AppID.Email_POP3:AppID.Email_IMAP4;
    ApplicationElement app = new ApplicationElement(appID);
    String mailFrom = this.getAttributeValue(profile, "Email Address");
    app.setProviderID(mailFrom);
    app.setName(profile.getName());
    app.addToNAPID(napDefElement.getNapID());
    
    String server = this.getAttributeValue(profile, "Receiving Server Address");
    String serverPort = this.getAttributeValue(profile, "Receiving Server Port");
    AppAddrElement appAddr = new AppAddrElement(server);
    String useSSL = this.getAttributeValue(profile, "Use SSL Receiving Service");
    PortElement portElement = new PortElement(serverPort);
    if (useSSL != null && useSSL.equalsIgnoreCase("true")) {
       portElement.addService(Service.STARTTLS);
    }
    appAddr.addPort(portElement);
    app.addAppAddrElement(appAddr);
    

    AppAuthElement auth = new AppAuthElement();
    String authName = this.getAttributeValue(profile, "Username");
    String authPassword = this.getAttributeValue(profile, "Password");
    auth.setAppAuthName(authName);
    auth.setAppAuthSecret(authPassword);
    app.addAppAuthElement(auth);
    return app;
  }

  /**
   * @param form
   * @param napName
   * @param napDefElement
   * @return
   */
  protected ApplicationElement convertSmtpProfile(ProfileConfig profile, NAPDefElement napDefElement) throws DMException {
    ApplicationElement app = new ApplicationElement(AppID.Email_SMTP);
    String mailFrom = this.getAttributeValue(profile, "Email Address");
    app.setProviderID(mailFrom);
    app.setName(profile.getName());
    app.addToNAPID(napDefElement.getNapID());
    
    Parameter<String> fromParam = new SimpleParameter();
    fromParam.setName("FROM");
    String replyEmail = this.getAttributeValue(profile, "Reply To Address");
    if (StringUtils.isEmpty(replyEmail)) {
       replyEmail = mailFrom;
    }
    fromParam.setValue(replyEmail);
    app.addParameter(fromParam);
    
    String smtpServer = this.getAttributeValue(profile, "Sending Server Address");
    String smtpServerPort = this.getAttributeValue(profile, "Sending Server Port");
    AppAddrElement appAddr = new AppAddrElement(smtpServer);
    PortElement portElement = new PortElement(smtpServerPort);
    String useSSL = this.getAttributeValue(profile, "Use SSL Sending Service");
    if (useSSL != null && useSSL.equalsIgnoreCase("true")) {
       portElement.addService(Service.STARTTLS);
    }
    appAddr.addPort(portElement);
    app.addAppAddrElement(appAddr);
    
    String smtpAuth = this.getAttributeValue(profile, "Use SMTP authentication");
    String authName = this.getAttributeValue(profile, "SMTP authentication ID");
    if (StringUtils.isEmpty(authName)) {
       authName = this.getAttributeValue(profile, "Username");
    }
    String authPassword = this.getAttributeValue(profile, "SMTP authentication PW");
    if (StringUtils.isEmpty(authPassword)) {
       authPassword = this.getAttributeValue(profile, "Password");
    }
    if (smtpAuth != null && smtpAuth.equalsIgnoreCase("true")) {
       AppAuthElement auth = new AppAuthElement();
       auth.setAppAuthName(authName);
       auth.setAppAuthSecret(authPassword);
       app.addAppAuthElement(auth);
    }
    return app;
  }
  
  /**
   * @param form
   * @param name
   * @return
   */
  protected ApplicationElement convertSyncDSProfile(ProfileConfig profile, NAPDefElement napElement, PXLogicalElement pxElement) throws DMException {
    String profileName = profile.getName();
    ApplicationElement app = new ApplicationElement(AppID.W5_SyncML_PUSH_Application);
    app.setName(profileName);
    if (pxElement != null) {
       app.addToProxy(pxElement.getProxyID());
    } else if (napElement != null) {
      app.addToNAPID(napElement.getNapID());
    }
 
    String host = this.getAttributeValue(profile, "Server Address");
    app.addAddr(host);
    
    String authName = this.getAttributeValue(profile, "Username");
    String authSecret = this.getAttributeValue(profile, "Password");
    if (StringUtils.isNotEmpty(authName)) {
      AppAuthElement auth = new AppAuthElement();
      if (StringUtils.isNotEmpty(authName)) {
         auth.setAppAuthName(authName);
      }
      if (StringUtils.isNotEmpty(authSecret)) {
         auth.setAppAuthSecret(authSecret);
      }
      app.addAppAuthElement(auth);
    }

    String contactURI = this.getAttributeValue(profile, "Contacts DB");
    if (StringUtils.isNotEmpty(contactURI)) {
       ResourceElement res = new ResourceElement(contactURI);
       String contactContentType = this.getAttributeValue(profile, "Contacts DB ContentType");
       if (StringUtils.isEmpty(contactContentType)) {
         contactContentType = "text/x-vcard";
       }
       res.setAppAccept(contactContentType);
       app.addResourceElement(res);
    }
    
    String calendarURI = this.getAttributeValue(profile, "Calendar DB");
    if (StringUtils.isNotEmpty(calendarURI)) {
       ResourceElement res = new ResourceElement(calendarURI);
       String calendarContentType = this.getAttributeValue(profile, "Calendar DB ContentType");
       if (StringUtils.isEmpty(calendarContentType)) {
         calendarContentType = "text/x-vcalendar";
       }
       res.setAppAccept(calendarContentType);
       app.addResourceElement(res);
    }
    
    String noteURI = this.getAttributeValue(profile, "Notes DB");
    if (StringUtils.isNotEmpty(noteURI)) {
       ResourceElement res = new ResourceElement(noteURI);
       String noteContentType = this.getAttributeValue(profile, "Notes DB ContentType");
       if (StringUtils.isEmpty(noteContentType)) {
         noteContentType = "text/plain";
       }
       res.setAppAccept(noteContentType);
       app.addResourceElement(res);
    }
    return app;
  }

  /**
   * @param form
   * @param name
   * @return
   */
  protected ApplicationElement convertDMProfile(ProfileConfig profile, NAPDefElement napElement, PXLogicalElement pxElement) throws DMException {
    String profileName = profile.getName();
    ApplicationElement app = new ApplicationElement(AppID.W7_SyncML_Device_Management);
    app.setName(profileName);
    if (pxElement != null) {
       app.addToProxy(pxElement.getProxyID());
    } else if (napElement != null) {
      app.addToNAPID(napElement.getNapID());
    }
 
    // Set DM parameters
    String dmServerURL = this.getAttributeValue(profile, "ServerAddr");
    app.addAddr(dmServerURL);
    // Implements Server Initiated Session when bootstrap.
    SimpleParameter initParm = new SimpleParameter();
    initParm.setName("INIT");
    initParm.setValue("1");
    app.addParameter(initParm);
    
    // DM Server Auth
    AppAuthElement serverAuth = new AppAuthElement();
    serverAuth.setAppAuthLevel(AppAuthLevel.APPSRV);
    serverAuth.setAppAuthName(this.getAttributeValue(profile, "ServerId"));
    serverAuth.setAppAuthSecret(this.getAttributeValue(profile, "ServerPW"));
    app.addAppAuthElement(serverAuth);
    
    // DM Client Auth
    AppAuthElement clientAuth = new AppAuthElement();
    clientAuth.setAppAuthLevel(AppAuthLevel.CLIENT);
    clientAuth.setAppAuthName(this.getAttributeValue(profile, "ClientId"));
    clientAuth.setAppAuthSecret(this.getAttributeValue(profile, "ClientPW"));
    app.addAppAuthElement(clientAuth);
    return app;
  }

  /**
   * @param form
   * @param name
   * @return
   */
  protected NokiaOtaBookmarkSettings convertBookmarkProfile4NokiaOTA(ProfileConfig profile) throws DMException {
    String name = this.getAttributeValue(profile, "NAME");
    String url = this.getAttributeValue(profile, "URL");
    NokiaOtaBookmarkSettings setting = new NokiaOtaBookmarkSettings();

    setting.addBookmark(name, url);
    return setting;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ProfileConvertor#setValueFetcher(com.npower.cp.convertor.ValueFetcher)
   */
  public void setValueFetcher(ValueFetcher<ProfileCategory, String, String> fetcher) {
    this.valueFetcher = fetcher;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ProfileConvertor#getSupportedCategoryName()
   */
  public abstract String getSupportedCategoryName();
  
  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ProfileConvertor#convertToOMAClientProv(com.npower.dm.core.ProfileConfig)
   */
  public abstract OMAClientProvSettings convertToOMAClientProv(ProfileConfig profile) throws DMException;
  
  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ProfileConvertor#convertToNokiaOTA(com.npower.dm.core.ProfileConfig)
   */
  public abstract NokiaOTASettings convertToNokiaOTA(ProfileConfig profile) throws DMException;
}