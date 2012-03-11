/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/firmware/FirmwareDBSyncItemReader.java,v 1.6 2008/11/20 09:17:03 zhao Exp $
 * $Revision: 1.6 $
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

package com.npower.unicom.sync.firmware;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Update;
import com.npower.dm.hibernate.entity.UpdateEntity;
import com.npower.dm.management.SearchBean;
import com.npower.unicom.sync.DBSyncItemReader;
import com.npower.unicom.sync.SyncAction;
import com.npower.unicom.sync.SyncItem;

/**
 * @author chenlei
 * @version $Revision: 1.6 $ $Date: 2008/11/20 09:17:03 $
 */

public class FirmwareDBSyncItemReader extends DBSyncItemReader<Update>{
  private static  Log log = LogFactory.getLog(FirmwareDBSyncItemReader.class);
  
  public FirmwareDBSyncItemReader(Properties props) {
    super(props);
  }
  @Override
  protected Iterator<Update> getIterator4Add() throws DMException {
    List<Update> entities = new ArrayList<Update>();
    SearchBean bean = this.getFactory().createSearchBean();
    Criteria criteria = bean.newCriteriaInstance(UpdateEntity.class);
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

  @Override
  protected Iterator<Update> getIterator4Delete() throws DMException {
    List<Update> entities = new ArrayList<Update>();
    log.info("[" + this.getClass().getSimpleName() + "] total items: " + entities.size() + " need to delete");
    return entities.iterator();
  }

  @Override
  protected Iterator<Update> getIterator4Modify() throws DMException {
    List<Update> entities = new ArrayList<Update>();
    SearchBean bean = this.getFactory().createSearchBean();
    Criteria criteria = bean.newCriteriaInstance(UpdateEntity.class);
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
  protected SyncItem convert(SyncAction action, Update entity) {
    // ½«Update×ª»»ÎªSyncItem
    FirmwareSyncItem item = new FirmwareSyncItem(Long.toString(entity.getID()), action);
    item.setManufacturer(entity.getFromImage().getModel().getManufacturer().getExternalId());
    item.setModel(entity.getFromImage().getModel().getManufacturerModelId());
    item.setFromVersion(entity.getFromImage().getVersionId());
    item.setToVersion(entity.getToImage().getVersionId());
    item.setFirmwareName(Long.toString(entity.getID()));
    try {
        item.setBytes(entity.getBytes());
    } catch (DMException e) {
      log.error(e.getMessage(), e);
    }
    return item;
  }
}
