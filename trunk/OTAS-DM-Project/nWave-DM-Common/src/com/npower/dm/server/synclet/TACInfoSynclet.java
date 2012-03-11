/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/synclet/TACInfoSynclet.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.4 $
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
import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.pipeline.InputMessageProcessor;
import sync4j.framework.engine.pipeline.MessageProcessingContext;
import sync4j.framework.protocol.ManagementInitialization;
import sync4j.framework.protocol.ProtocolException;

import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * Auto tracking tac info from device's IMEI.
 * If flag autoSave is true, will automaticlly add the tac infomation into model's repository.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public class TACInfoSynclet implements Serializable, InputMessageProcessor {

  private transient static Log log = LogFactory.getLog(TACInfoSynclet.class);
  
  private boolean enable = true;
  
  /**
   * Constructor
   */
  public TACInfoSynclet() {
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

  // Protected methods ------------------------------------------------------------ 

  /**
   * The readObject method is responsible for reading from the stream and restoring the classes fields,
   * and restore the transient fields.
   */
  protected void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    log = LogFactory.getLog(TACInfoSynclet.class);
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
    log.info("processing TAC information");
    ManagementBeanFactory factory = null;
    try {
        ManagementInitialization init = new ManagementInitialization(message.getSyncHdr(), message.getSyncBody());
        DevInfo devInfo = init.getDevInfo();
        if (devInfo == null) {
           return ;
        }
        String imei = devInfo.getDevId();
        if (StringUtils.isEmpty(imei)) {
           return;
        }
        String manufacturerName = devInfo.getMan();
        if (StringUtils.isEmpty(manufacturerName)) {
           return;
        }
        String modelName = devInfo.getMod();
        if (StringUtils.isEmpty(modelName)) {
           return;
        }
        
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ModelBean modelBean = factory.createModelBean();
        Model model = modelBean.getModelbyTAC(imei);
        if (model == null) {
           // Unknown TAC
           factory.beginTransaction();
           Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerName);
           if (manufacturer != null) {
              model =  modelBean.getModelByManufacturerModelID(manufacturer, modelName);
           } else {
             // Manufactuer non-exists, create manufacturer.
             manufacturer = modelBean.newManufacturerInstance(manufacturerName, manufacturerName, manufacturerName);
             modelBean.update(manufacturer);
           }
           if (model == null) {
              // Unknown Model, create model.
              model = modelBean.newModelInstance(manufacturer, modelName, modelName, false, true, false, false, false);
              modelBean.update(model);
           }
           // Registe the TAC Info.
           log.info("Registerate TAC info: " + imei + " with model: " + manufacturerName + " " + modelName);
           modelBean.addTACInfo(model, imei);
           factory.commit();
        } else {
          // Checking
          if (!model.getManufacturerModelId().equals(modelName) ||
              !model.getManufacturer().getExternalId().equals(manufacturerName)) {
             log.warn("Un-matached TAC info: " + imei + " with model: " + manufacturerName + " " + modelName);
          }
        }
        
    } catch (ProtocolException e) {
      // Ignore the protocol exception
      if (factory != null) {
         factory.rollback();
      }
    } catch (Exception e) {
      if (factory != null) {
         factory.rollback();
      }
      throw new Sync4jException("Failure in registrate TAC Info.", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }


}
