/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/DeviceLogBean.java,v 1.8 2007/08/29 06:20:59 zhao Exp $
 * $Revision: 1.8 $
 * $Date: 2007/08/29 06:20:59 $
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
package com.npower.dm.management;

import java.util.List;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceLog;
import com.npower.dm.core.DeviceLogAction;

/**
 * <p>
 * </p>
 * 
 * @author Zhao Yang
 * @version $Revision: 1.8 $ $Date: 2007/08/29 06:20:59 $
 */
public interface DeviceLogBean extends BaseBean {

  /**
   * create device log
   * 
   * @param actionName
   * @param device
   * @param jobType
   * @param message
   * @return
   * @throws DMException
   */
  public abstract DeviceLog newDeviceLogInstance(String actionName, Device device, String jobType, String message) throws DMException;

  /**
   * update device log
   * 
   * @param devicelog
   * @throws DMException
   */
  public abstract void update(DeviceLog devicelog) throws DMException;

  /**
   * delete all of the device logs
   * 
   * @throws DMException
   */
  public abstract void clearAll() throws DMException;

  /**
   * Find device logs by action
   * 
   * @param action
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLog> findDeviceLogsByAction(String action) throws DMException;

  /**
   * Find device logs by jobtype
   * 
   * @param jobtype
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLog> findDeviceLogsByJobType(String jobtype) throws DMException;

  /**
   * Get actions of device logs
   * 
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLogAction> getDeviceLogActions() throws DMException;

  /**
   * Find device logs by year to_char(CREATION_DATE,'yyyy-mm-dd hh:mm:ss')
   * 
   * @param year
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLog> findDeviceLogsByOneTime(int year) throws DMException;

  /**
   * Find device logs by year and month to_char(CREATION_DATE,'yyyy-mm-dd
   * hh:mm:ss')
   * 
   * @param year
   * @param month
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLog> findDeviceLogsByOneTime(int year, int month) throws DMException;

  /**
   * Find device logs by year, month and day to_char(CREATION_DATE,'yyyy-mm-dd
   * hh:mm:ss')
   * 
   * @param year
   * @param month
   * @param day
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLog> findDeviceLogsByOneTime(int year, int month, int day) throws DMException;

  /**
   * Find device logs by year, month, day and hour
   * to_char(CREATION_DATE,'yyyy-mm-dd hh:mm:ss')
   * 
   * @param year
   * @param month
   * @param day
   * @param hour
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLog> findDeviceLogsByOneTime(int year, int month, int day, int hour) throws DMException;

  /**
   * Find device logs by year, month, day, hour and minite
   * to_char(CREATION_DATE,'yyyy-mm-dd hh:mm:ss')
   * 
   * @param year
   * @param month
   * @param day
   * @param hour
   * @param minite
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLog> findDeviceLogsByOneTime(int year, int month, int day, int hour, int minite) throws DMException;

  /**
   * Find device logs by year, month, day, hour, minite and second
   * to_char(CREATION_DATE,'yyyy-mm-dd hh:mm:ss')
   * 
   * @param year
   * @param month
   * @param day
   * @param hour
   * @param minite
   * @param second
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLog> findDeviceLogsByOneTime(int year, int month, int day, int hour, int minite, int second) throws DMException;

  /**
   * Find device logs between two times by year, month and day
   * 
   * @param yearstart
   * @param monthstart
   * @param daystart
   * @param yearend
   * @param monthend
   * @param dayend
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLog> findDeviceLogsBetweenTwoTime(int yearbegin, int monthbegin, int daybegin, int yearend, int monthend, int dayend) throws DMException;

  /**
   * Find device logs between two times by year, month, day and hour
   * 
   * @param yearbegin
   * @param monthbegin
   * @param daybegin
   * @param hourbegin
   * @param yearend
   * @param monthend
   * @param dayend
   * @param hourend
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLog> findDeviceLogsBetweenTwoTime(int yearbegin, int monthbegin, int daybegin, int hourbegin, int yearend, int monthend, int dayend,
      int hourend) throws DMException;

  /**
   * Set the number of logs per page.
   * @param numberOfLogsPerPage
   */
  public abstract void setNumberOfLogsPerPage(int numberOfLogsPerPage);
  
  /**
   * Return the number of logs per page.
   * @return
   */
  public abstract int getNumberOfLogsPerPage();
  
  /**
   * Set the current page number used for findXXXX().
   * The first oage number is 1
   * @param numberOfLogsPerPage
   */
  public abstract void setCurrentPageNumber(int numberOfLogsPerPage);
  
  /**
   * Return current page number.
   * @return
   */
  public abstract int getCurrentPageNumber();
  
  /**
   * Set the "order by" for query.
   * For example: "creationDate desc"
   * @param orderBy
   */
  public void setOrderBy(String orderBy);
  
  public String getOrderBy();
  
  /**
   * Number of total logs
   * 
   * @return
   * @throws DMException
   */
  public abstract int getNumberOfTotalLogs() throws DMException;

  /**
   * Number of total pages.
   * 
   * @return
   * @throws DMException
   */
  public abstract int getNumberOfTotalPages() throws DMException;

  /**
   * Return ths list which belongs to the specified page.
   * 
   * @return
   * @throws DMException
   */
  public abstract List<DeviceLog> findDeviceLogs() throws DMException;

}
