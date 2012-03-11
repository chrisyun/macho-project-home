/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/DBSyncItemReader.java,v 1.4 2008/11/18 10:00:07 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/11/18 10:00:07 $
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
package com.npower.unicom.sync;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/11/18 10:00:07 $
 */
public abstract class DBSyncItemReader<T> implements SyncItemReader {

  private Properties properties = new Properties();
  private ManagementBeanFactory factory;
  
  private Date fromTime = null;
  private Date toTime = null;

  /**
   * 
   */
  public DBSyncItemReader(Properties props) {
    super();
    this.properties  = props;
  }

  /**
   * @param properties
   * @param fromTime
   *        同步时间范围
   * @param toTime
   *        同步时间范围
   */
  public DBSyncItemReader(Properties properties, Date fromTime, Date toTime) {
    super();
    this.properties = properties;
    this.fromTime = fromTime;
    this.toTime = toTime;
  }

  /**
   * @return the fromTime
   */
  public Date getFromTime() {
    return fromTime;
  }

  /**
   * @param fromTime the fromTime to set
   */
  public void setFromTime(Date fromTime) {
    this.fromTime = fromTime;
  }

  /**
   * @return the endTime
   */
  public Date getToTime() {
    return toTime;
  }

  /**
   * @param endTime the endTime to set
   */
  public void setToTime(Date endTime) {
    this.toTime = endTime;
  }

  /**
   * @return
   */
  public ManagementBeanFactory getFactory() {
    return factory;
  }

  /**
   * @param factory
   */
  public void setFactory(ManagementBeanFactory factory) {
    this.factory = factory;
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.SyncItemReader#close()
   */
  public void close() throws IOException {
    this.factory.release();
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.SyncItemReader#open()
   */
  public void open() throws IOException, DMException {
    this.factory = ManagementBeanFactory.newInstance(properties);
    // 提取数据
    this.fetchData();
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.SyncItemReader#read()
   */
  public SyncItem read() throws IOException, ParseException {
    if (iterator4Add != null && iterator4Add.hasNext()) {
       return this.convert(SyncAction.Add, (T)iterator4Add.next());
    } else if (iterator4Modify != null && iterator4Modify.hasNext()) {
      return this.convert(SyncAction.Modify, (T)iterator4Modify.next());
    } else if (iterator4Delete != null && iterator4Delete.hasNext()) {
      return this.convert(SyncAction.Delete, (T)iterator4Delete.next());
    } else {
      return null;
    }
  }

  private Iterator<T> iterator4Add = null;
  private Iterator<T> iterator4Modify = null;
  private Iterator<T> iterator4Delete = null;
  /**
   * 初始化, 装载数据
   * @throws DMException
   */
  private void fetchData() throws DMException {
    try {
        this.iterator4Add = this.getIterator4Add();
        this.iterator4Modify = this.getIterator4Modify();
        this.iterator4Delete = this.getIterator4Delete();
    } catch (DMException e) {
      throw new DMException("Failure to fetch data: " + e.getMessage(), e);
    }
  }
  /**
   * 将Model转换为SyncItem
   * @param entity
   *        Database Entity
   * @return
   */
  protected abstract SyncItem convert(SyncAction action, T entity);
  
  /**
   * 返回删除数据的Iterator
   * @return
   * @throws DMException
   */
  protected abstract Iterator<T> getIterator4Delete() throws DMException;
  
  /**
   * 返回添加数据的Iterator
   * @return
   * @throws DMException
   */
  protected abstract Iterator<T> getIterator4Add() throws DMException;
  
  /**
   * 返回修改数据的Iterator
   * @return
   * @throws DMException
   */
  protected abstract Iterator<T> getIterator4Modify() throws DMException ;
  
}
