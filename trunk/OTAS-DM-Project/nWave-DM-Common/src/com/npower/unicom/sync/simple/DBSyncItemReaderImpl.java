/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/simple/DBSyncItemReaderImpl.java,v 1.3 2008/11/18 11:18:28 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/18 11:18:28 $
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
package com.npower.unicom.sync.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.hibernate.Criteria;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.hibernate.entity.ModelEntity;
import com.npower.dm.management.SearchBean;
import com.npower.unicom.sync.DBSyncItemReader;
import com.npower.unicom.sync.SyncAction;
import com.npower.unicom.sync.SyncItem;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/11/18 11:18:28 $
 */
public class DBSyncItemReaderImpl extends DBSyncItemReader<Model> {

  /**
   * @param file
   */
  public DBSyncItemReaderImpl(Properties props) {
    super(props);
  }

  /**
   * @param properties
   * @param fromTime
   * @param endTime
   */
  public DBSyncItemReaderImpl(Properties properties, Date fromTime, Date toTime) {
    super(properties, fromTime, toTime);
  }
  
  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemReader#convert(com.npower.unicom.sync.SyncAction, java.lang.Object)
   */
  protected SyncItem convert(SyncAction action, Model model) {
    // ½«Model×ª»»ÎªSyncItem
    SimpleSyncItem item = new SimpleSyncItem(Long.toString(model.getID()), action);
    item.setBrand(model.getManufacturer().getExternalId());
    item.setModel(model.getManufacturerModelId());
    return item;
  }
  
  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemReader#getIterator4Delete()
   */
  protected Iterator<Model> getIterator4Delete() throws DMException {
    List<Model> entities = new ArrayList<Model>();
    return entities.iterator();
  }
  
  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemReader#getIterator4Add()
   */
  protected Iterator<Model> getIterator4Add() throws DMException {
    List<Model> entities = new ArrayList<Model>();
    /*
    SearchBean bean = this.getFactory().createSearchBean();
    Criteria criteria = bean.newCriteriaInstance(ModelEntity.class);
    if (null != this.getFromTime()) {
       criteria.add(Expression.ge("createdTime", this.getFromTime()));
    }
    if (null != this.getToTime()) {
       criteria.add(Expression.lt("createdTime", this.getToTime()));
    }
    entities = criteria.list();
    */
    return entities.iterator();
  }
  
  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemReader#getIterator4Modify()
   */
  protected Iterator<Model> getIterator4Modify() throws DMException {
    List<Model> entities = new ArrayList<Model>();
    SearchBean bean = this.getFactory().createSearchBean();
    Criteria criteria = bean.newCriteriaInstance(ModelEntity.class);
    /*
    if (null != this.getFromTime()) {
      criteria.add(Expression.ge("lastUpdatedTime", this.getFromTime()));
    }
    if (null != this.getToTime()) {
       criteria.add(Expression.lt("lastUpdatedTime", this.getToTime()));
    }
    */
    entities = criteria.list();
    return entities.iterator();
  }

}
