/**
 * $Header:
 * /home/master/nWave-DM-Common/src/com/npower/dm/server/synclet/AutoDeviceExternalIDChangerSynclet.java,v
 * 1.1 2008/03/10 09:05:18 zhao Exp $ $Revision: 1.5 $ $Date: 2008/03/10
 * 09:05:18 $
 * 
 * ===============================================================================================
 * License, Version 1.1
 * 
 * Copyright (c) 1994-2006 NPower Network Software Ltd. All rights reserved.
 * 
 * This SOURCE CODE FILE, which has been provided by NPower as part of a NPower
 * product for use ONLY by licensed users of the product, includes CONFIDENTIAL
 * and PROPRIETARY information of NPower.
 * 
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS OF THE LICENSE
 * STATEMENT AND LIMITED WARRANTY FURNISHED WITH THE PRODUCT.
 * 
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED COMPANIES AND
 * ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS OR LIABILITIES ARISING
 * OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION OF YOUR PROGRAMS, INCLUDING ANY
 * CLAIMS OR LIABILITIES ARISING OUT OF OR RESULTING FROM THE USE, MODIFICATION,
 * OR DISTRIBUTION OF PROGRAMS OR FILES CREATED FROM, BASED ON, AND/OR DERIVED
 * FROM THIS SOURCE CODE FILE.
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

import com.npower.dm.bootstrap.BootstrapService;
import com.npower.dm.bootstrap.URLParameters;
import com.npower.dm.core.Device;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;

/**
 * 
 * 为自动设备Bootstrap提供服务, 解决如下问题:<br/> 如果设备在开始注册的时候不知道其IMEI信息, 并自动为其创建了任务(如:
 * 自动软件安装), 在创建任务的时候将需要首先创建设备, 但由于设备的IMEI未知, 将自动为其生成一个临时IMEI,
 * 并在发送Bootstrap信息时在ServerURL上携带这个临时的IMEI信息, 此Synclet将在实际接收到手机的请求时,
 * 自动根据DevInfo中的信息自动修正设备数据库中的实际的设备IMEI.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/06/16 10:11:15 $
 */
public class AutoDeviceExternalIDChangerSynclet implements InputMessageProcessor, Serializable {

  private transient static Log log        = LogFactory.getLog(AutoDeviceExternalIDChangerSynclet.class);

  private boolean              enable     = false;

  /**
   * IMEI or ID, IMEI means to get device from Bootstrap external ID ID means to
   * get device from device ID
   */
  private String               identifier = "IMEI";

  /**
   * 
   */
  public AutoDeviceExternalIDChangerSynclet() {
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
   * @param autoSave
   *            the autoSave to set
   */
  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  /**
   * @return Returns the identifier.
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * @param identifier
   *            The identifier to set.
   */
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  /**
   * @param message
   * @param httpRequest
   * @throws Sync4jException
   */
  private void process4IMEI(SyncML message, HttpServletRequest httpRequest) throws Sync4jException {
    // Get parameter
    String bsp = httpRequest.getParameter(BootstrapService.BOOTSTRAP_PARAMETRERS_NAME);
    URLParameters params = URLParameters.decode(bsp);
    if (params == null) {
      return;
    }
    String bootstrapDeviceExtID = params.getDeviceExtID();
    // String bootstrapDeviceID =
    // httpRequest.getParameter(BootstrapService.BOOTSTRAP_DEVICE_ID_PARAMETER_NAME);
    if (StringUtils.isEmpty(bootstrapDeviceExtID)) {
      return;
    }
    // Get Real IMEI
    String deviceExternalID = message.getSyncHdr().getSource().getLocURI();

    if (bootstrapDeviceExtID.equalsIgnoreCase(deviceExternalID)) {
      return;
    }
    ManagementBeanFactory factory = null;
    try {
      factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());

      DeviceBean deviceBean = factory.createDeviceBean();
      Device d = deviceBean.getDeviceByExternalID(deviceExternalID);
      if (d != null) {
         // Already exists, give up
         return;
      }
      
      Device device = deviceBean.getDeviceByExternalID(bootstrapDeviceExtID);
      if (device != null) {
        log.info("Change device IMEI from: " + device.getExternalId() + " to: " + deviceExternalID);
        factory.beginTransaction();
        device.setExternalId(deviceExternalID);
        deviceBean.update(device);
        factory.commit();
      }

    } catch (Exception ex) {
      if (factory != null) {
        factory.rollback();
      }
      log.error("Failure in change device IMEI from: " + bootstrapDeviceExtID + " to: "
          + deviceExternalID, ex);
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

  /**
   * @param message
   * @param httpRequest
   * @throws Sync4jException
   */
  private void process4ID(SyncML message, HttpServletRequest httpRequest) throws Sync4jException {
    // Get parameter
    String bsp = httpRequest.getParameter(BootstrapService.BOOTSTRAP_PARAMETRERS_NAME);
    URLParameters params = URLParameters.decode(bsp);
    if (params == null) {
      return;
    }
    String bootstrapDeviceID = params.getDeviceID();
    if (StringUtils.isEmpty(bootstrapDeviceID)) {
      return;
    }
    // Get Real IMEI
    String deviceExternalID = message.getSyncHdr().getSource().getLocURI();

    ManagementBeanFactory factory = null;
    try {
      factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());

      DeviceBean deviceBean = factory.createDeviceBean();
      Device device = deviceBean.getDeviceByID(bootstrapDeviceID);
      if (device != null && !device.getExternalId().equalsIgnoreCase(deviceExternalID)) {
        log.info("Change device IMEI from: " + device.getExternalId() + " to: " + deviceExternalID);
        factory.beginTransaction();
        device.setExternalId(deviceExternalID);
        deviceBean.update(device);
        factory.commit();
      }

    } catch (Exception ex) {
      if (factory != null) {
        factory.rollback();
      }
      log.error("Failure in change device ID: " + bootstrapDeviceID + " to new IMEI: "
          + deviceExternalID, ex);
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

  // Protected methods
  // ------------------------------------------------------------

  /**
   * The readObject method is responsible for reading from the stream and
   * restoring the classes fields, and restore the transient fields.
   */
  protected void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    log = LogFactory.getLog(AutoDeviceExternalIDChangerSynclet.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see sync4j.framework.engine.pipeline.InputMessageProcessor#preProcessMessage(sync4j.framework.engine.pipeline.MessageProcessingContext,
   *      sync4j.framework.core.SyncML, javax.servlet.http.HttpServletRequest)
   */
  public void preProcessMessage(MessageProcessingContext processingContext, SyncML message,
      HttpServletRequest httpRequest) throws Sync4jException {
    if (!this.isEnable()) {
      return;
    }

    if ("IMEI".equalsIgnoreCase(this.getIdentifier())) {
      this.process4IMEI(message, httpRequest);
    } else if ("ID".equalsIgnoreCase(this.getIdentifier())) {
      this.process4ID(message, httpRequest);
    }
  }

}
