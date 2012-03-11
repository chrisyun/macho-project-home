/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/device/DBSyncItemWriter4Device.java,v 1.3 2008/11/19 07:15:44 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/19 07:15:44 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.unicom.sync.device;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.tracking.DeviceChangeLogger;
import com.npower.dm.tracking.DeviceChangeLoggerFactory;
import com.npower.unicom.sync.DBSyncItemWriter;
import com.npower.unicom.sync.SyncItem;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.3 $ $Date: 2008/11/19 07:15:44 $
 */

public class DBSyncItemWriter4Device extends DBSyncItemWriter {

  private static Log log = LogFactory.getLog(DBSyncItemWriter4Device.class);

  /**
   * @param props
   */
  public DBSyncItemWriter4Device(Properties props) {
    super(props);
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemWriter#write(com.npower.unicom.sync.SyncItem)
   */
  @Override
  public void write(SyncItem item) throws IOException, DMException {
    DeviceSyncItem deviceSyncItem = null;
    if (item instanceof DeviceSyncItem) {
      deviceSyncItem = (DeviceSyncItem) item;
      
    }
    String imei = deviceSyncItem.getImei();
    String imsi = deviceSyncItem.getImsi();
    String msisdn = deviceSyncItem.getMsisdn();
    DeviceBean deviceBean =this.getFactory().createDeviceBean(); 
    ModelBean modelBean = this.getFactory().createModelBean();
    SubscriberBean subscriberBean = this.getFactory().createSubcriberBean();
    CarrierBean carrierBean = this.getFactory().createCarrierBean();
           
    try {
      // Enroll Device and subscriber
      Model model = modelBean.getModelbyTAC(imei);
      if (model == null) {
         Manufacturer manufacturer = modelBean.getManufacturerByExternalID("NOKIA");
         model = modelBean.getModelByManufacturerModelID(manufacturer, "N80");
      }
      
      Carrier carrier = carrierBean.getCarrierByExternalID("ChinaMobile");
      
      this.getFactory().beginTransaction();
      Subscriber subscriber = subscriberBean.getSubscriberByPhoneNumber(msisdn);
      if (subscriber == null) {
         subscriber = subscriberBean.newSubscriberInstance(carrier, msisdn, msisdn, msisdn);
      }
      subscriber.setIMSI(imsi);

      subscriberBean.update(subscriber);

      Device device = deviceBean.getDeviceByExternalID(imei);
      if (device == null) {
         device = deviceBean.newDeviceInstance(subscriber, model, imei);
      } else {
        device.setSubscriber(subscriber);
      }
      
      deviceBean.update(device);
      this.getFactory().commit();
      
      // 记录到设备变化日志
      this.track(imei, msisdn, imsi, model.getManufacturer().getExternalId(), model.getManufacturerModelId(), deviceSyncItem.getVersion());
      
    } catch (Exception e) {
      if (this.getFactory() != null) {
        this.getFactory().rollback();
      }
      log.error("enroll device error!, date item: " + item.toString(), e);
      throw new DMException("enroll device error",e);
    }
    
  }

  /**
   * Tracking it and save into device change log.
   * @param enrollMsg
   */
  private void track(String imei, String msisdn, String imsi, String brand, String model, String version) {
    DeviceChangeLoggerFactory logFactory = null;
    try {
      logFactory = DeviceChangeLoggerFactory.newInstance();
      DeviceChangeLogger logger = logFactory.getLogger();
      logger.log(imei, msisdn, imsi, brand, model, version);
    } catch (Exception ex) {
      log.error("Failure to write a device change log. ", ex);
    } finally {
    }
  }
}
