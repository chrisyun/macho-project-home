/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/device/DBSyncItemReader4Device.java,v 1.2 2008/11/20 09:17:03 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/20 09:17:03 $
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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Subscriber;
import com.npower.dm.hibernate.entity.DeviceEntity;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.SearchBean;
import com.npower.unicom.sync.DBSyncItemReader;
import com.npower.unicom.sync.SyncAction;
import com.npower.unicom.sync.SyncItem;


/**
 * @author Zhao wanxiang
 * @version $Revision: 1.2 $ $Date: 2008/11/20 09:17:03 $
 */

public class DBSyncItemReader4Device extends DBSyncItemReader<Device> {

  private static Log log = LogFactory.getLog(DBSyncItemReader4Device.class);
  /**
   * @param props
   */
  public DBSyncItemReader4Device(Properties props) {
    super(props);
  }

  /**
   * @param properties
   * @param fromTime
   * @param toTime
   */
  public DBSyncItemReader4Device(Properties properties, Date fromTime, Date toTime) {
    super(properties, fromTime, toTime);
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemReader#getIterator4Add()
   */
  @Override
  protected Iterator<Device> getIterator4Add() throws DMException {
    List<Device> entities = new ArrayList<Device>();
    SearchBean bean = this.getFactory().createSearchBean();
    Criteria criteria = bean.newCriteriaInstance(DeviceEntity.class);
    if (null != this.getFromTime()) {
       criteria.add(Expression.ge("createdTime", this.getFromTime()));
    }
    if (null != this.getToTime()) {
       criteria.add(Expression.lt("createdTime", this.getToTime()));
    }
    entities = criteria.list();
    log.info("[" + this.getClass().getSimpleName() + "] total items: " + entities.size() + " need to add");
    return entities.iterator();
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemReader#getIterator4Delete()
   */
  @Override
  protected Iterator<Device> getIterator4Delete() throws DMException {
    List<Device> entities = new ArrayList<Device>();
    log.info("[" + this.getClass().getSimpleName() + "] total items: " + entities.size() + " need to delete");
    return entities.iterator();
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemReader#getIterator4Modify()
   */
  @Override
  protected Iterator<Device> getIterator4Modify() throws DMException {
    List<Device> entities = new ArrayList<Device>();
    SearchBean bean = this.getFactory().createSearchBean();
    Criteria criteria = bean.newCriteriaInstance(DeviceEntity.class);
    if (null != this.getFromTime()) {
      criteria.add(Expression.ge("lastUpdatedTime", this.getFromTime()));
    }
    if (null != this.getToTime()) {
       criteria.add(Expression.lt("lastUpdatedTime", this.getToTime()));
    }
    entities = criteria.list();
    log.info("[" + this.getClass().getSimpleName() + "] total items: " + entities.size() + " need to modify");
    return entities.iterator();
  }

  @Override
  protected SyncItem convert(SyncAction action, Device device) {
    // 将Device转换为SyncItem
    DeviceSyncItem item = new DeviceSyncItem(Long.toString(device.getID()), action);
    item.setImei(device.getExternalId());
    Subscriber subscriber = device.getSubscriber();
    item.setImsi(subscriber.getIMSI());
    item.setMsisdn(subscriber.getPhoneNumber());
    item.setCompanyName(device.getModel().getManufacturer().getExternalId());
    item.setTerminalModel(device.getModel().getManufacturerModelId());
    
    // 提取终端软件版本
    try {
      DeviceBean deviceBean = this.getFactory().createDeviceBean();
      String currentFirmwareVersion = deviceBean.getCurrentFirmwareVersionId(device.getID());
      currentFirmwareVersion = (currentFirmwareVersion == null)?"":currentFirmwareVersion;
      item.setVersion(currentFirmwareVersion);
    } catch (DMException e) {
      log.error("failure to get current firmware version for device: " + device.getExternalId());
    }
    return item;
  }

}
