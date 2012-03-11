/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/bootstrap/BootstrapServiceImpl.java,v 1.11 2008/07/29 07:17:40 zhao Exp $
  * $Revision: 1.11 $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.cp.convertor.PropertiesValueFetcher;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.OTAClientProvJobBean;
import com.npower.dm.util.DMUtil;
import com.npower.sms.SmsException;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * 实现BootstrapService, 使用Carrier中定义的DM Bootstrap Profile, 并基于ClientProv模式发送报文.
 * @author Zhao DongLu
 * @version $Revision: 1.11 $ $Date: 2008/07/29 07:17:40 $
 */
public class BootstrapServiceImpl extends AbstractBootstrapService implements BootstrapService {

  private static Log log = LogFactory.getLog(BootstrapServiceImpl.class);
  /**
   * 
   */
  public BootstrapServiceImpl() {
    super();
  }

  /**
   * @param dmBootProf
   * @return
   * @throws DMException
   */
  private String getOriginalDMServerURL(ProfileConfig dmBootProf) throws DMException {
    ProfileAttributeValue serverURLAttrValue = dmBootProf.getProfileAttributeValue("ServerAddr");
    String dmServerURL = serverURLAttrValue.getStringValue();
    return dmServerURL;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.bootstrap.AbstractBootstrapService#bootstrap(java.lang.String, com.npower.wap.omacp.OMACPSecurityMethod, java.lang.String, long, int, long)
   */
  @Override
  public long bootstrap(String deviceExternalID, OMACPSecurityMethod securityMethod, String pin, long scheduleTime,
      int maxRetry, long maxDuration) throws DMException, SmsException {
    DeviceBean deviceBean = this.getBeanFactory().createDeviceBean();
    Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
    if (device == null) {
       throw new DMException("Bootstrap device not not found, externalID: " + deviceExternalID);
    }
    String msisdn = caculatePhoneNumber(device);
    
    try {
        Carrier carrier = device.getSubscriber().getCarrier();
        ProfileConfig dmBootProf = carrier.getBootstrapDmProfile();
        if (dmBootProf == null) {
           throw new DMException("Bootstrap failured for device: " + deviceExternalID + ", cause: Could not found dm bootstrap profile from carrier: " + carrier.getExternalID());
        }
        OTAClientProvJobBean bean = this.getBeanFactory().createOTAClientProvJobBean(this.getOtaInventory(), this.getSmsSender());
        log.info("Bootstrap, device id: " + deviceExternalID + " , msisdn: " + msisdn + ", PinType: " + securityMethod + ", Pin: " + pin);

        // 创建Bootstrap URL, 追加参数
        PropertiesValueFetcher valueFetcher = new PropertiesValueFetcher();
        String dmServerURL = getOriginalDMServerURL(dmBootProf);
        URLParameters params = new URLParameters();
        params.setMsisdn(msisdn);
        params.setDeviceID("" + device.getID());
        params.setDeviceExtID(device.getExternalId());
        String paramValue = params.encode();
        dmServerURL = DMUtil.appendParameter(dmServerURL, BootstrapService.BOOTSTRAP_PARAMETRERS_NAME, paramValue);        
        valueFetcher.setValue(ProfileCategory.DM_CATEGORY_NAME, "ServerAddr", dmServerURL);

        ProvisionJob job = bean.provision(device, dmBootProf, valueFetcher, securityMethod, pin, scheduleTime, maxRetry, maxDuration/1000);
        log.info("Bootstrap Job ID: " + job.getID());
        return job.getID();
    } catch (Exception e) {
      throw new DMException("Error in bootstrap device: " + deviceExternalID, e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.bootstrap.AbstractBootstrapService#bootstrap(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.npower.wap.omacp.OMACPSecurityMethod, java.lang.String, long, int, long)
   */
  @Override
  public long bootstrap(String msisdn, String serverPassword, String serverNonce, String clientUsername,
      String clientPassword, String clientNonce, OMACPSecurityMethod securityMethod, String pin, long scheduleTime,
      int maxRetry, long maxDuration) throws DMException, SmsException {
    
    try {
        CarrierBean carrierBean = this.getBeanFactory().createCarrierBean();
        Carrier carrier = carrierBean.findCarrierByPhoneNumberPolicy(msisdn);
        if (carrier == null) {
           throw new DMException("Bootstrap failured, could not found matched carrier by phone number: " + msisdn);
        }
      
        ProfileConfig dmBootProf = carrier.getBootstrapDmProfile();
        if (dmBootProf == null) {
           throw new DMException("Bootstrap failured for device msisdn: " + msisdn + ", cause: Could not found dm bootstrap profile from carrier: " + carrier.getExternalID());
        }
        OTAClientProvJobBean bean = this.getBeanFactory().createOTAClientProvJobBean(this.getOtaInventory(), this.getSmsSender());
        log.info("Bootstrap, device msisdn: " + msisdn + ", PinType: " + securityMethod + ", Pin: " + pin);

        PropertiesValueFetcher valueFetcher = new PropertiesValueFetcher();
        valueFetcher.setValue(ProfileCategory.DM_CATEGORY_NAME, "ServerPW", serverPassword);
        valueFetcher.setValue(ProfileCategory.DM_CATEGORY_NAME, "ServerNonce", serverNonce);
        valueFetcher.setValue(ProfileCategory.DM_CATEGORY_NAME, "ClientId", clientUsername);
        valueFetcher.setValue(ProfileCategory.DM_CATEGORY_NAME, "ClientPW", clientPassword);
        valueFetcher.setValue(ProfileCategory.DM_CATEGORY_NAME, "ClientNonce", clientNonce);
        
        // 创建Bootstrap URL, 追加参数
        String dmServerURL = getOriginalDMServerURL(dmBootProf);
        URLParameters params = new URLParameters();
        params.setMsisdn(msisdn);
        String paramValue = params.encode();
        dmServerURL = DMUtil.appendParameter(dmServerURL, BootstrapService.BOOTSTRAP_PARAMETRERS_NAME, paramValue);        
        valueFetcher.setValue(ProfileCategory.DM_CATEGORY_NAME, "ServerAddr", dmServerURL);

        ModelBean modelBean = this.getBeanFactory().createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(Model.DEFAULT_MANUFACTURER_EXTERNAL_ID);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, Model.DEFAULT_MANUFACTURER_EXTERNAL_ID);
        ProvisionJob job = bean.provision(msisdn, model, carrier, dmBootProf, valueFetcher , securityMethod, pin, scheduleTime, maxRetry, maxDuration/1000);
        log.info("Bootstrap Job ID: " + job.getID());
        return job.getID();
    } catch (Exception e) {
      throw new DMException("Error in bootstrap device msisdn: " + msisdn, e);
    }
  }

}
