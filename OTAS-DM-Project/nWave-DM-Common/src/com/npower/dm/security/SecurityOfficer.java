/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/security/SecurityOfficer.java,v 1.6 2008/06/16 10:11:14 zhao Exp $
  * $Revision: 1.6 $
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

import java.io.Serializable;
import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.Authentication;
import sync4j.framework.core.Constants;
import sync4j.framework.core.Cred;
import sync4j.framework.core.HMACAuthentication;
import sync4j.framework.security.Officer;
import sync4j.framework.tools.Base64;
import sync4j.framework.tools.MD5;

import com.npower.dm.core.Device;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;

/**
 * This the officer that defines how credentials are authenticated to a device
 * and how resources are authorized for a given credential based on the DM inventory.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/06/16 10:11:14 $
 */
public class SecurityOfficer implements Officer, Serializable {
  
  private static transient Log log = LogFactory.getLog(SecurityOfficer.class);

  /**
   * Has the last login failed for incorrect login/password?
   */
  private boolean loginFailed = false;

  /**
   * Has the last login failed for expired temporary login?
   */
  private boolean loginExpired = false;

  /**
   * Can use a guest credential if there is not credential in the message?
   */
  private boolean guestEnabled = false;

  /**
   * Which type of authetication does impose the server?
   */
  private String authType = "syncml:auth-md5";


  private String supportedAuthType = null;

  /**
   * 
   */
  public SecurityOfficer() {
    super();
  }

  public boolean isLoginFailed() {
    return loginFailed;
  }
  
  /**
   * Set property guestEnabled
   */
  public void setGuestEnabled(boolean enable) {
    this.guestEnabled = enable;
  }

  // Implements Officer interface *************************************************************************

  /**
   * Read property guestEnabled
   * 
   * @see sync4j.framework.security.Officer#isGuestEnabled()
   */
  public boolean isGuestEnabled() {
    return this.guestEnabled;
  }

  /**
   * @return the type of authentication set up from the server
   *  
   * @see sync4j.framework.security.Officer#getAuthType()
   */
  public String getAuthType() {
    return this.authType;
  }

  /**
   * Set the type of server authentication
   *
   * @param authType the type of server authentication
   * 
   * @see sync4j.framework.security.Officer#setAuthType(java.lang.String)
   */
  public void setAuthType(String authType) {
    this.authType = authType;

    // the authType is supported!!!!
    this.supportedAuthType = this.supportedAuthType + this.authType;
  }

  /**
   * Read property supportedAuthType
   *
   * @return the supported type of server authentication
   * 
   * @see sync4j.framework.security.Officer#getSupportedAuthType()
   */
  public String getSupportedAuthType() {
    return this.supportedAuthType;
  }

  /**
   * Set the supported type of server authentication
   *
   * @param supportedAuthType the supported type of server authentication
   * 
   * @see sync4j.framework.security.Officer#setSupportedAuthType(java.lang.String)
   */
  public void setSupportedAuthType(String supportedAuthType) {
    // the authType is supported!!!!
    this.supportedAuthType = this.authType + supportedAuthType;
  }


  /**
   * Is the account authenticated by this officer expired?
   *
   * @return true if the account is expired, false otherwise
   * 
   * @see sync4j.framework.security.Officer#isAccountExpired()
   */
  public boolean isAccountExpired() {
    return this.loginExpired;
  }

  /**
   * Authenticates a credential.
   *
   * @param credential the credential to be authenticated. You can get the deviceExternalID related with 
   *        this credential.
   *        
   *        String deviceExternalID = credential.getAuthentication().getDeviceID();
   *
   * @return true if the credential is autenticated, false otherwise
   * 
   * @see sync4j.framework.security.Officer#authenticate(sync4j.framework.core.Cred)
   */
  public boolean authenticate(Cred credential) {
    String type = credential.getType();
    
    if ((Constants.AUTH_TYPE_BASIC).equals(type)  ) {
        return authenticateBasicCredential(credential);
    } else if ((Constants.AUTH_TYPE_MD5).equals(type)  ) {
      return authenticateMD5Credential(credential);
    } else if ((Constants.AUTH_TYPE_HMAC).equals(type)  ) {
      return authenticateHMACCredential(credential);
    } else {
      return false;
    }
  }

  /**
   * 
   * Un-authenticates a credential.
   *
   * @param credential the credential to be unauthenticated
   * 
   * @see sync4j.framework.security.Officer#unAuthenticate(sync4j.framework.core.Cred)
   */
  public void unAuthenticate(Cred cred) {
    //
    // Do nothing. In the current implementation, the authentication is
    // discarde as soon as the LoginContext is garbage collected.
    //
  }

  /**
   * Authorizes a resource.
   *
   * @param principal the requesting entity
   * @param resource the name (or the identifier) of the resource to be authorized
   *
   * @return true if the credential is authorized to access the resource, false
   *         otherwise
   *         
   * @see sync4j.framework.security.Officer#authorize(java.security.Principal, java.lang.String)
   */
  public boolean authorize(Principal principal, String resource) {
    return true;
  }
  
  // private methods **************************************************************************************
  
  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    in.defaultReadObject();
    log = LogFactory.getLog(SecurityOfficer.class);
  }

  /**
   * Verify the credential.
   * 
   * @param credential
   * @return
   */
  private boolean authenticateBasicCredential(Cred credential) {
    Authentication auth   = credential.getAuthentication();
    String deviceExternalID = auth.getDeviceId();

    String username = null;
    String password = null;

    String s = new String(Base64.decode(credential.getData()));

    int p = s.indexOf(':');

    if (p == -1) {
        username = s;
        password = "";
    } else {
        username = (p>0) ? s.substring(0, p) : "";
        password = (p == (s.length()-1)) ? "" : s.substring(p+1);
    }

    if (log.isDebugEnabled()) {
        log.debug("Username: " + username + "; password: " + password);
    }

    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        DeviceBean deviceBean = factory.createDeviceBean();
        
        Device device = (Device)deviceBean.getDeviceByExternalID(deviceExternalID);
        
        if (device == null) {
           if (log.isDebugEnabled()) {
              log.debug("Authentication failure: Device: " + deviceExternalID + " not found!");
           }
           return false;
        }
        if (!device.getIsActivated()) {
           if (log.isDebugEnabled()) {
              log.debug("Authentication failure: Device: " + deviceExternalID + " had been disabled!");
           }
           return false;
        }
        return (username.equals(device.getOMAClientUsername()) && password.equals(device.getOMAClientPassword()));
    } catch (Exception e) {
      log.error("authenticateBasicCredential failure!", e);
      return false;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }


  private boolean authenticateMD5Credential(Cred credential) {

    Authentication auth   = credential.getAuthentication();
    String deviceExternalID = auth.getDeviceId();
    
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        DeviceBean deviceBean = factory.createDeviceBean();
        
        Device device = (Device)deviceBean.getDeviceByExternalID(deviceExternalID);
        
        if (device == null) {
           if (log.isDebugEnabled()) {
              log.debug("Authentication failure: Device: " + deviceExternalID + " not found!");
           }
           return false;
        }
        if (!device.getIsActivated()) {
           if (log.isDebugEnabled()) {
              log.debug("Authentication failure: Device: " + deviceExternalID + " had been disabled!");
           }
           return false;
        }
        String userDigest = new String(Base64.encode(MD5.digest((device.getOMAClientUsername() + ":" + device.getOMAClientPassword()).getBytes())));
        byte[] clientNonce = auth.getNextNonce().getValue();
        if (log.isDebugEnabled()) {
            log.debug("userDigest: " + userDigest);
        }

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

        //
        // digest sent by client in format b64
        //
        String msgDigestNonceB64 = auth.getData();

        if (log.isDebugEnabled()) {
            log.debug("serverDigestNonceB64: " + serverDigestNonceB64);
            log.debug("msgDigestNonceB64: "    + msgDigestNonceB64   );
        }

        if (!msgDigestNonceB64.equals(serverDigestNonceB64)) {
            return false;
        }

        auth.setUsername(userDigest);

        return true;
    } catch (Exception e) {
        log.error("Error load device from the persistent store: " + e);
        log.error("authenticateMD5Credential", e);
        return false;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  private boolean authenticateHMACCredential(Cred credential) {
    Authentication auth   = credential.getAuthentication();

    if ( !(auth instanceof HMACAuthentication) ) {
        throw new IllegalStateException("Authentication not valid!");
    }

    String deviceExternalID = auth.getDeviceId();
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        DeviceBean deviceBean = factory.createDeviceBean();
        
        Device device = (Device)deviceBean.getDeviceByExternalID(deviceExternalID);
        
        if (device == null) {
           if (log.isDebugEnabled()) {
              log.debug("Authentication failure: Device: " + deviceExternalID + " not found!");
           }
           return false;
        }
        if (!device.getIsActivated()) {
           if (log.isDebugEnabled()) {
              log.debug("Authentication failure: Device: " + deviceExternalID + " had been disabled!");
           }
           return false;
        }
        
        String userMac       = ((HMACAuthentication)auth).getUserMac()      ;
        String calculatedMac = ((HMACAuthentication)auth).getCalculatedMac();

        if ((userMac != null) && (userMac.equals(calculatedMac))) {
            String digest = new String(Base64.encode(MD5.digest( (device.getOMAClientUsername() + ":" + device.getOMAClientPassword()).getBytes())));;
            auth.setUsername(digest);
            return true;
        }

        return false;
    } catch (Exception e) {
      log.error("Error load device from the persistent store: " + e);
      log.error("authenticateMD5Credential", e);
      return false;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

}

  
}
