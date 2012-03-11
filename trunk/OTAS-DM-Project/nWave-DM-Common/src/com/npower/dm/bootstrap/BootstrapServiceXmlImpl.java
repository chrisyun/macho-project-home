/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/bootstrap/BootstrapServiceXmlImpl.java,v 1.12 2008/07/29 07:17:40 zhao Exp $
  * $Revision: 1.12 $
  * $Date: 2008/07/29 07:17:40 $
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

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.cp.OTAException;
import com.npower.cp.ProvisioningDoc;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.management.DeviceBean;
import com.npower.sms.SmsException;
import com.npower.wap.omacp.OMACPSecurityMethod;
import com.npower.wap.omacp.OMAClientProvDoc;
import com.npower.wap.omacp.OMAClientProvSettings;
import com.npower.wap.omacp.elements.ApplicationElement;
import com.npower.wap.omacp.elements.NAPDefElement;
import com.npower.wap.omacp.elements.PXLogicalElement;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.12 $ $Date: 2008/07/29 07:17:40 $
 */
/**
 * @author Zhao DongLu
 * @version $Revision: 1.12 $ $Date: 2008/07/29 07:17:40 $
 */
public class BootstrapServiceXmlImpl extends BaseBootstrapService implements BootstrapService {
  
  private static Log log = LogFactory.getLog(BootstrapServiceXmlImpl.class);
  
  /**
   * Default constructor.
   */
  public BootstrapServiceXmlImpl() {
    super();
  }
  
  // Private methods ----------------------------------------------------------------
  /**
   * @param device
   * @return
   * @throws OTAException
   * @throws DMException
   */
  private ClientProvTemplate findOTATemplate(Device device) throws OTAException, DMException {

    Model model = device.getModel();
    Manufacturer manufacturer = model.getManufacturer();
    String manuExternalID = manufacturer.getExternalId();
    String modelExternalID = model.getManufacturerModelId();
    
    return findOTATemplate(manuExternalID, modelExternalID);
  }

  /**
   * @param manuExternalID
   * @param modelExternalID
   * @return
   * @throws OTAException
   * @throws DMException
   */
  private ClientProvTemplate findOTATemplate(String manuExternalID, String modelExternalID) throws OTAException, DMException {
    ClientProvTemplate template = this.getOtaInventory().findTemplate(manuExternalID, modelExternalID, ProfileCategory.DM_CATEGORY_NAME);
    return template;
  }

  // Public methods ---------------------------------------------------------------------
  
  // Implements public methods -----------------------------------------------------

  /* (non-Javadoc)
   * @see com.npower.dm.bootstrap.AbstractBootstrapService#bootstrap(java.lang.String, com.npower.wap.omacp.OMACPSecurityMethod, java.lang.String, long)
   */
  public long bootstrap(String deviceExternalID, OMACPSecurityMethod pinType, String pin, long scheduleTime, int maxRetry, long maxDuration) throws DMException, SmsException {
    DeviceBean deviceBean = this.getBeanFactory().createDeviceBean();
    Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
    if (device == null) {
       throw new DMException("Bootstrap device not not found, externalID: " + deviceExternalID);
    }
    String msisdn = AbstractBootstrapService.caculatePhoneNumber(device);
    
    
    try {
        ClientProvTemplate template = findOTATemplate(device);
        
        // Prepare profile for OTA Template.
        Properties profile = prepareProfile(device);
        
        // Build template parameters
        OMAClientProvSettings settings = new OMAClientProvSettings();
        // Build NAP or Proxy
        String napDefNapID = this.getProperty(profile, "napdef_napid");
        if (StringUtils.isNotEmpty(napDefNapID)) {
           // NAP
           NAPDefElement nap = this.createNAPDef(profile);
           settings.addNAPDefElement(nap);
        }
        String pxLogicID = this.getProperty(profile, "pxlogic_id");
        if (StringUtils.isNotEmpty(pxLogicID)) {
          // NAP with Proxy
          PXLogicalElement proxy = this.createProxyDef(profile, napDefNapID);
          settings.addPXLogicalElement(proxy);
        }

        // Build and add DM document.
        ApplicationElement app = buildDMApplication(profile, napDefNapID, pxLogicID, msisdn);
        settings.addApplicationElement(app);

        // Merge Profile template with profile.
        ProvisioningDoc doc = template.merge(settings);
        OMAClientProvDoc provDoc = new OMAClientProvDoc(doc.getContent());
        if (pinType == null) {
           pinType = OMACPSecurityMethod.USERPIN;
        }
        provDoc.setSecurityMethod(pinType);
        provDoc.setPIN(pin);
        
        log.info("Bootstrap, device msisdn: " + msisdn + ", Content: " + doc.getContent());
        String msgID = this.getSmsSender().send(provDoc.getSmsWapPushMessage(), msisdn, msisdn, scheduleTime, maxRetry, maxDuration);
        return msgID.hashCode();
    } catch (OTAException e) {
      throw new DMException("Error in find OTA Template.", e);
    } catch (SmsException e) {
      throw e;
    } catch (IOException e) {
      throw new DMException("Error in Send SMS for bootstrap.", e);
    } catch (Exception e) {
      throw new DMException("Error in Send SMS for bootstrap.", e);
    }
    
  }

  /* (non-Javadoc)
   * @see com.npower.dm.bootstrap.AbstractBootstrapService#bootstrap(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.npower.wap.omacp.OMACPSecurityMethod, java.lang.String, long)
   */
  public long bootstrap(String msisdn, String serverPassword, String serverNonce, String clientUsername, String clientPassword, String clientNonce, OMACPSecurityMethod pinType, String pin, long scheduleTime, int maxRetry, long maxDuration) throws DMException, SmsException {
    try {
        ClientProvTemplate template = findOTATemplate(null, null);
        
        // Prepare profile for OTA Template.
        Properties profile = prepareProfile(serverPassword, serverNonce, clientUsername, clientPassword, clientNonce);
        
        // Build template parameters
        OMAClientProvSettings settings = new OMAClientProvSettings();
        // Build NAP or Proxy
        String napDefNapID = this.getProperty(profile, "napdef_napid");
        if (StringUtils.isNotEmpty(napDefNapID)) {
           // NAP
           NAPDefElement nap = this.createNAPDef(profile);
           settings.addNAPDefElement(nap);
        }
        String pxLogicID = this.getProperty(profile, "pxlogic_id");
        if (StringUtils.isNotEmpty(pxLogicID)) {
          // NAP with Proxy
          PXLogicalElement proxy = this.createProxyDef(profile, napDefNapID);
          settings.addPXLogicalElement(proxy);
        }
  
        // Build and add DM document.
        ApplicationElement app = buildDMApplication(profile, napDefNapID, pxLogicID, msisdn);
        settings.addApplicationElement(app);
  
        // Merge Profile template with profile.
        ProvisioningDoc doc = template.merge(settings);
        OMAClientProvDoc provDoc = new OMAClientProvDoc(doc.getContent());
        if (pinType == null) {
           pinType = OMACPSecurityMethod.USERPIN;
        }
        provDoc.setSecurityMethod(pinType);
        provDoc.setPIN(pin);
        
        log.info("Bootstrap, device msisdn: " + msisdn + ", Content: " + doc.getContent());
        String msgID = this.getSmsSender().send(provDoc.getSmsWapPushMessage(), msisdn, msisdn, scheduleTime, maxRetry, maxDuration);
        return msgID.hashCode();
  } catch (OTAException e) {
    throw new DMException("Error in find OTA Template.", e);
  } catch (SmsException e) {
    throw e;
  } catch (IOException e) {
    throw new DMException("Error in Send SMS for bootstrap.", e);
  } catch (Exception e) {
    throw new DMException("Error in Send SMS for bootstrap.", e);
  }
  }

}
