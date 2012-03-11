/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/synclet/AutoRegDeviceSynclet.java,v 1.7 2008/06/16 10:11:15 zhao Exp $
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
package com.npower.dm.server.synclet;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.Sync4jException;
import sync4j.framework.core.SyncML;
import sync4j.framework.engine.pipeline.InputMessageProcessor;
import sync4j.framework.engine.pipeline.MessageProcessingContext;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/06/16 10:11:15 $
 */
public class AutoRegDeviceSynclet implements InputMessageProcessor, Serializable {

  private transient static Log log = LogFactory.getLog(AutoRegDeviceSynclet.class);
  
  /**
   * Inidicate: Auto Registration a unkonown device.
   */
  private boolean enable = true;
  
  /**
   * This flag is true will automaticlly boundle and update deviceID and phonenumber
   */
  private boolean autoBoundle = false;
  
  private String clientUsername = null;
  
  private String clientPassword = null;
  
  private String serverPassword = null;
  
  private PhoneNumberDetector phoneNumberDetector = null;
  
  private CarrierDetector carrierDetector = null;
  
  /**
   * 
   */
  public AutoRegDeviceSynclet() {
    super();
  }

  // Setter and Getter -------------------------------------------------------
  /**
   * @return the autoSave
   */
  public boolean isEnable() {
    return enable;
  }

  /**
   * @param autoSave the autoSave to set
   */
  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  /**
   * @return the autoBoundle
   */
  public boolean isAutoBoundle() {
    return autoBoundle;
  }

  /**
   * @param autoBoundle the autoBoundle to set
   */
  public void setAutoBoundle(boolean autoBoundle) {
    this.autoBoundle = autoBoundle;
  }

  /**
   * @return the carrierDetector
   */
  public CarrierDetector getCarrierDetector() {
    return carrierDetector;
  }

  /**
   * @param carrierDetector the carrierDetector to set
   */
  public void setCarrierDetector(CarrierDetector carrierDetector) {
    this.carrierDetector = carrierDetector;
  }

  /**
   * @return the clientPassword
   */
  public String getClientPassword() {
    return clientPassword;
  }

  /**
   * @param clientPassword the clientPassword to set
   */
  public void setClientPassword(String clientPassword) {
    this.clientPassword = clientPassword;
  }

  /**
   * @return the clientUsername
   */
  public String getClientUsername() {
    return clientUsername;
  }

  /**
   * @param clientUsername the clientUsername to set
   */
  public void setClientUsername(String clientUsername) {
    this.clientUsername = clientUsername;
  }

  /**
   * @return the serverPassword
   */
  public String getServerPassword() {
    return serverPassword;
  }

  /**
   * @param serverPassword the serverPassword to set
   */
  public void setServerPassword(String serverPassword) {
    this.serverPassword = serverPassword;
  }


  /**
   * @return the phoneNumberParser
   */
  public PhoneNumberDetector getPhoneNumberDetector() {
    return phoneNumberDetector;
  }

  /**
   * @param phoneNumberParser the phoneNumberParser to set
   */
  public void setPhoneNumberDetector(PhoneNumberDetector phoneNumberParser) {
    this.phoneNumberDetector = phoneNumberParser;
  }

  // Protected methods ------------------------------------------------------------ 

  /**
   * The readObject method is responsible for reading from the stream and restoring the classes fields,
   * and restore the transient fields.
   */
  protected void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    log = LogFactory.getLog(AutoRegDeviceSynclet.class);
  }  
  
  /**
   * @param factory
   * @param externalID
   * @param bean
   * @return
   * @throws DMException
   */
  private Device createDevice(ManagementBeanFactory factory, String externalID, String subecriberPhone, SyncML message, HttpServletRequest httpRequest) throws DMException {
    log.info("Auto registrate a unkonown device, device enteral ID: " + externalID);

    ModelBean modelBean = factory.createModelBean();
    DeviceBean bean = factory.createDeviceBean();
    SubscriberBean subBean = factory.createSubcriberBean();
    CarrierBean carrierBean = factory.createCarrierBean();
    
    String imei = externalID;
    if (imei.toUpperCase().startsWith("IMEI:")) {
       imei = imei.substring(5, imei.length());
    }
    Model model = modelBean.getModelbyTAC(imei);
    if (model == null) {
       // Using default model.
       Manufacturer defaultManufacturer = modelBean.getManufacturerByExternalID(ModelBean.DEFAULT_MANUFACTURER_EXTERNAL_ID);
       if (defaultManufacturer == null) {
          throw new DMException("Could not load Default manufacturer by external ID: " + ModelBean.DEFAULT_MANUFACTURER_EXTERNAL_ID);
       }
       model = modelBean.getModelByManufacturerModelID(defaultManufacturer, ModelBean.DEFAULT_MODEL_EXTERNAL_ID);
    }
    if (model == null) {
       throw new DMException("Could not found model for Auto Registration by IMEI: " + imei);
    }
    
    String carrierID = this.carrierDetector.getCarrierID(externalID, subecriberPhone, httpRequest, message);
    Carrier carrier = null;
    if (StringUtils.isNotEmpty(carrierID)) {
       carrier = carrierBean.getCarrierByID(carrierID);
    }
    if (carrier == null) {
       log.error("Could not find carrier for auto reg device: " + externalID + ", " + subecriberPhone);
       throw new DMException("Could not find Carrier for auto registration service, device: " + externalID + ", " + subecriberPhone);
    }
    log.info("Found carrier: " + carrier.getExternalID() + " for auto reg device: " + externalID + ", " + subecriberPhone);
    
    try {
        // Create a subscriber
        Subscriber subscriber = subBean.getSubscriberByPhoneNumber(subecriberPhone);
        if (subscriber == null) {
           subscriber = subBean.newSubscriberInstance(carrier, externalID, subecriberPhone, this.clientPassword);
           subBean.update(subscriber);
        } else {
          subscriber.setCarrier(carrier);
          subscriber.setNewPassword(this.clientPassword);
          subBean.update(subscriber);
        }
        
        // Create a device.
        Device device = bean.newDeviceInstance(subscriber, model, externalID);
        device.setOMAClientUsername(this.clientUsername);
        device.setOMAClientPassword(this.clientPassword);
        device.setOMAServerPassword(this.serverPassword);
        device.setIsActivated(true);
        bean.update(device);
        return device;
    } catch (Exception e) {
      factory.rollback();
      throw new DMException("Failure in create a device for Auto Registration service.", e);
    } finally {
    }
  }

  // Public methods --------------------------------------------------------------
  /* (non-Javadoc)
   * @see sync4j.framework.engine.pipeline.InputMessageProcessor#preProcessMessage(sync4j.framework.engine.pipeline.MessageProcessingContext, sync4j.framework.core.SyncML, javax.servlet.http.HttpServletRequest)
   */
  public void preProcessMessage(MessageProcessingContext processingContext, SyncML message,
      HttpServletRequest httpRequest) throws Sync4jException {
    
    if (!this.isEnable()) {
       return;
    }
    String deviceExternalID = message.getSyncHdr().getSource().getLocURI();
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        // Get PhoneNumber
        PhoneNumberDetector parser = this.getPhoneNumberDetector();
        String subecriberPhone = parser.getPhoneNumber(deviceExternalID, httpRequest, message);
        
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = null;
        
        factory.beginTransaction();
        if (this.isAutoBoundle()) {
           device = deviceBean.bind(deviceExternalID, subecriberPhone);
        } else {
          device = deviceBean.getDeviceByExternalID(deviceExternalID);
        }
        if (device == null) {
           device = this.createDevice(factory, deviceExternalID, subecriberPhone, message, httpRequest);
           log.info("Registerated an unkown device: " + device.getExternalId());
        }
        factory.commit();
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new Sync4jException("Failure in registerate an unkonw device.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
