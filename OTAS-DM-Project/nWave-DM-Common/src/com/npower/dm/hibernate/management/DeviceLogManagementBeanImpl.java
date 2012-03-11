/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/DeviceLogManagementBeanImpl.java,v 1.11 2008/04/02 06:24:24 zhao Exp $
 * $Revision: 1.11 $
 * $Date: 2008/04/02 06:24:24 $
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
package com.npower.dm.hibernate.management;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceLog;
import com.npower.dm.core.DeviceLogAction;
import com.npower.dm.hibernate.entity.DeviceLogEntity;
import com.npower.dm.management.DeviceLogBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * <p>
 * </p>
 * 
 * @author Zhao Yang
 * @version $Revision: 1.11 $ $Date: 2008/04/02 06:24:24 $
 */
public class DeviceLogManagementBeanImpl extends AbstractBean implements DeviceLogBean {

  // the numbers of paginal logs
  private int numberOfLogsPerPage = Integer.MAX_VALUE;

  // the page number of current
  private int currentPageNumber = 1;

  // for example: select....where...order by ID desc
  private String orderBy = "ID desc";

  // the number of total records
  private int totalRecords = 0;

  protected DeviceLogManagementBeanImpl() {
    super();
  }

  DeviceLogManagementBeanImpl(ManagementBeanFactory factory, Session session) {
    super(factory, session);
  }

  // Setter and Getter methods
  // *********************************************************************
  public int getNumberOfLogsPerPage() {
    return numberOfLogsPerPage;
  }

  public void setNumberOfLogsPerPage(int numberOfLogsPerPage) {
    this.numberOfLogsPerPage = numberOfLogsPerPage;
  }

  public int getCurrentPageNumber() {
    return currentPageNumber;
  }

  public void setCurrentPageNumber(int currentPageNumber) {
    this.currentPageNumber = currentPageNumber;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  // Privates methods
  // *********************************************************************
  /**
   * return DeviceLogAction by DeviceLogAction.value
   * 
   * @param value
   * @return DeviceLogAction
   * @throws DMException
   */
  private DeviceLogAction findDeviceLogActionByName(String value) throws DMException {
    String hsql = " from DeviceLogActionEntity where value= '" + value + "' order by value desc";
    Session session = null;
    try {
      session = this.getHibernateSession();
      Query query = session.createQuery(hsql);
      List<DeviceLogAction> list = query.list();
      if (list.size() == 0) {
        throw new DMException("Could not found the value of log action:" + value);
      }
      DeviceLogAction redevicelogaction = null;
      for (int i = 0; i < list.size(); i++) {
        redevicelogaction = (DeviceLogAction) list.get(i);
        return redevicelogaction;
      }

      return null;

    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /**
   * return List by String for example: DeviceLog test = (DeviceLog)list.get(0)
   * 
   * @param hsql
   * @return List
   * @throws DMException
   */
  private List<DeviceLog> loadLogsByHSQL(String hsql) throws DMException {
    Session session = null;
    try {
      session = this.getHibernateSession();
      Query query = session.createQuery(hsql + " order by " + this.getOrderBy());

      int firstResult = this.getNumberOfLogsPerPage() * (this.getCurrentPageNumber() - 1);
      query.setFirstResult(firstResult);
      query.setMaxResults(this.getNumberOfLogsPerPage());

      List<DeviceLog> list = query.list();

      Object result = session.createQuery("select count(*) " + hsql).uniqueResult();
      if (result instanceof Integer) {
        this.totalRecords = ((Integer) session.createQuery("select count(*) " + hsql).uniqueResult()).intValue();
      } else {
        // Upgrade to Hibernate 3.2.x
        this.totalRecords = ((Long) session.createQuery("select count(*) " + hsql).uniqueResult()).intValue();
      }

      return list;

    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  // Implements DeviceLogBean methods
  // *********************************************************************
  /**
   * create devicelog
   */
  public DeviceLog newDeviceLogInstance(String actionName, Device device, String jobType, String message) throws DMException {

    DeviceLogAction action = this.findDeviceLogActionByName(actionName);
    if (action == null || action.getValue() == null || action.getValue().trim().length() == 0) {
      throw new DMException("Could not found the log action:" + actionName);
    }
    DeviceLog deviceLog = new DeviceLogEntity(action, device, jobType, message);
    deviceLog.setCreationDate(new Date());
    return deviceLog;
  }

  /**
   * update devcielog
   */
  public void update(DeviceLog devicelog) throws DMException {
    Session session = null;
    try {
      session = this.getHibernateSession();
      DeviceLogEntity savelog = (DeviceLogEntity) devicelog;
      session.save(savelog);

    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /**
   * delete all the test logs
   */
  public void clearAll() throws DMException {
    Session session = null;
    try {
      session = this.getHibernateSession();
      Query query = session.createQuery(" from DeviceLogEntity");
      List<DeviceLog> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        session.delete(list.get(i));
      }
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /**
   * find all the deviceactions
   * For Example: (DeviceLogAction)List
   * @return (DeviceLogAction)List
   */
  public List<DeviceLogAction> getDeviceLogActions() throws DMException {
    Session session = null;
    try {
      session = this.getHibernateSession();
      Query query = session.createQuery(" from DeviceLogActionEntity");
      List<DeviceLogAction> list = query.list();
      return list;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /**
   * find all the devicelogs
   * For Example: (DeviceLog)List
   * @return (DeviceLog)List
   */
  public List<DeviceLog> findDeviceLogs() throws DMException {
    String hsql = " from DeviceLogEntity";
    return loadLogsByHSQL(hsql);
  }

  /**
   * Find device logs by action 
   * For Example: (DeviceLog)List
   * @param action
   * @return (DeviceLog)List
   * @throws DMException
   */
  public List<DeviceLog> findDeviceLogsByAction(String action) throws DMException {
    String hsql = " from DeviceLogEntity where action= '" + action + "'";
    return loadLogsByHSQL(hsql);
  }

  /**
   * Find device logs by jobtype
   * For Example: (DeviceLog)List
   * @param jobtype
   * @return (DeviceLog)List
   * @throws DMException
   */
  public List<DeviceLog> findDeviceLogsByJobType(String jobtype) throws DMException {
    String hsql = " from DeviceLogEntity where jobType= '" + jobtype + "'";
    return loadLogsByHSQL(hsql);
  }

  /**
   * Find device logs by year to_char(CREATION_DATE,'yyyy-mm-dd hh:mm:ss')
   * For Example: (DeviceLog)List
   * @param year
   * @return (DeviceLog)List
   * @throws DMException
   */
  public List<DeviceLog> findDeviceLogsByOneTime(int year) throws DMException {
    String hsql = " from DeviceLogEntity where to_char(creationDate,'yyyy')='" + year + "'";
    return loadLogsByHSQL(hsql);
  }

  /**
   * Find device logs by year and month to_char(CREATION_DATE,'yyyy-mm-dd
   * hh:mm:ss')
   * For Example: (DeviceLog)List
   * @param year
   * @param month
   * @return (DeviceLog)List
   * @throws DMException
   */

  public List<DeviceLog> findDeviceLogsByOneTime(int year, int month) throws DMException {
    String monthstr = (month < 10) ? "0" + month : "" + month;
    String hsql = " from DeviceLogEntity where to_char(creationDate,'yyyy-mm')='" + year + "-" + monthstr + "'";
    return loadLogsByHSQL(hsql);
  }

  /**
   * Find device logs by year, month and day to_char(CREATION_DATE,'yyyy-mm-dd
   * hh:mm:ss') 
   * For Example: (DeviceLog)List
   * @param year
   * @param month
   * @param day
   * @return (DeviceLog)List
   * @throws DMException
   */

  public List<DeviceLog> findDeviceLogsByOneTime(int year, int month, int day) throws DMException {
    String monthstr = (month < 10) ? "0" + month : "" + month;

    String daystr = (day < 10) ? "0" + day : "" + day;

    String hsql = " from DeviceLogEntity where to_char(creationDate,'yyyy-mm-dd')='" + year + "-" + monthstr + "-" + daystr + "'";
    return loadLogsByHSQL(hsql);
  }

  /**
   * Find device logs by year, month, day and hour
   * to_char(CREATION_DATE,'yyyy-mm-dd hh:mm:ss') 
   * For Example: (DeviceLog)List
   * @param year
   * @param month
   * @param day
   * @param hour
   * @return (DeviceLog)List
   * @throws DMException
   */

  public List<DeviceLog> findDeviceLogsByOneTime(int year, int month, int day, int hour) throws DMException {
    String monthstr = (month < 10) ? "0" + month : "" + month;

    String daystr = (day < 10) ? "0" + day : "" + day;

    String hourstr = (hour < 10) ? "0" + hour : "" + hour;

    String hsql = " from DeviceLogEntity where to_char(creationDate,'yyyy-mm-dd hh24')='" + year + "-" + monthstr + "-" + daystr + " " + hourstr + "'";
    return loadLogsByHSQL(hsql);
  }

  /**
   * Find device logs by year, month, day, hour and minite
   * to_char(CREATION_DATE,'yyyy-mm-dd hh:mm:ss')
   * For Example: (DeviceLog)List
   * @param year
   * @param month
   * @param day
   * @param hour
   * @param minite
   * @return (DeviceLog)List
   * @throws DMException
   */

  public List<DeviceLog> findDeviceLogsByOneTime(int year, int month, int day, int hour, int minite) throws DMException {
    String monthstr = (month < 10) ? "0" + month : "" + month;

    String daystr = (day < 10) ? "0" + day : "" + day;

    String hourstr = (hour < 10) ? "0" + hour : "" + hour;

    String minitestr = (minite < 10) ? "0" + minite : "" + minite;

    String hsql = " from DeviceLogEntity where to_char(creationDate,'yyyy-mm-dd hh24:mi')='" + year + "-" + monthstr + "-" + daystr + " " + hourstr + ":"
        + minitestr + "'";

    System.out.println(hsql);

    return loadLogsByHSQL(hsql);
  }

  /**
   * Find device logs by year, month, day, hour, minite and second
   * to_char(CREATION_DATE,'yyyy-mm-dd hh:mm:ss') 
   * For Example: (DeviceLog)List
   * @param year
   * @param month
   * @param day
   * @param hour
   * @param minite
   * @param second
   * @return (DeviceLog)List
   * @throws DMException
   */

  public List<DeviceLog> findDeviceLogsByOneTime(int year, int month, int day, int hour, int minite, int second) throws DMException {
    String monthstr = (month < 10) ? "0" + month : "" + month;

    String daystr = (day < 10) ? "0" + day : "" + day;

    String hourstr = (hour < 10) ? "0" + hour : "" + hour;

    String minitestr = (minite < 10) ? "0" + minite : "" + minite;

    String secondstr = (second < 10) ? "0" + second : "" + second;

    String hsql = " from DeviceLogEntity where to_char(creationDate,'yyyy-mm-dd hh24:mi:ss')='" + year + "-" + monthstr + "-" + daystr + " " + hourstr + ":"
        + minitestr + ":" + secondstr + "'";

    return loadLogsByHSQL(hsql);
  }

  /**
   * Find device logs between two times by year, month and day 
   * For Example: (DeviceLog)List
   * @param yearstart
   * @param monthstart
   * @param daystart
   * @param yearend
   * @param monthend
   * @param dayend
   * @return (DeviceLog)List
   * @throws DMException
   */

  public List<DeviceLog> findDeviceLogsBetweenTwoTime(int yearbegin, int monthbegin, int daybegin, int yearend, int monthend, int dayend) throws DMException {

    String monthbeginstr = (monthbegin < 10) ? "0" + monthbegin : "" + monthbegin;

    String daybeginstr = (daybegin < 10) ? "0" + daybegin : "" + daybegin;

    String monthendstr = (monthend < 10) ? "0" + monthend : "" + monthend;

    String dayendstr = (dayend < 10) ? "0" + dayend : "" + dayend;

    String hsql = " from DeviceLogEntity where to_char(creationDate,'yyyy-mm-dd')>='" + yearbegin + "-" + monthbeginstr + "-" + daybeginstr
        + "' and to_char(creationDate,'yyyy-mm-dd')<='" + yearend + "-" + monthendstr + "-" + dayendstr + "'";

    System.out.println(hsql);

    return loadLogsByHSQL(hsql);
  }

  /**
   * Find device logs between two times by year, month, day and hour
   * For Example: (DeviceLog)List
   * 
   * @param yearbegin
   * @param monthbegin
   * @param daybegin
   * @param hourbegin
   * @param yearend
   * @param monthend
   * @param dayend
   * @param hourend
   * @return (DeviceLog)List
   * @throws DMException
   */

  public List<DeviceLog> findDeviceLogsBetweenTwoTime(int yearbegin, int monthbegin, int daybegin, int hourbegin, int yearend, int monthend, int dayend, int hourend)
      throws DMException {
    String monthbeginstr = (monthbegin < 10) ? "0" + monthbegin : "" + monthbegin;

    String daybeginstr = (daybegin < 10) ? "0" + daybegin : "" + daybegin;

    String hourbeginstr = (hourbegin < 10) ? "0" + hourbegin : "" + hourbegin;

    String monthendstr = (monthend < 10) ? "0" + monthend : "" + monthend;

    String dayendstr = (dayend < 10) ? "0" + dayend : "" + dayend;

    String hourendstr = (hourend < 10) ? "0" + hourend : "" + hourend;

    String hsql = " from DeviceLogEntity where to_char(creationDate,'yyyy-mm-dd hh24')>='" + yearbegin + "-" + monthbeginstr + "-" + daybeginstr + " "
        + hourbeginstr + "' and to_char(creationDate,'yyyy-mm-dd hh24')<='" + yearend + "-" + monthendstr + "-" + dayendstr + " " + hourendstr + "'";
    return loadLogsByHSQL(hsql);
  }

  /**
   * get numbers of total logs
   * 
   * @return int
   */
  public int getNumberOfTotalLogs() throws DMException {
    return this.totalRecords;
  }

  /**
   * get numbers of total pages
   * 
   * @return int
   */
  public int getNumberOfTotalPages() throws DMException {
    try {
      int totalRecords = this.getNumberOfTotalLogs();
      int totalPage = 0;
      if ((totalRecords % this.getNumberOfLogsPerPage()) == 0) {
        totalPage = totalRecords / this.getNumberOfLogsPerPage();
      } else {
        totalPage = totalRecords / this.getNumberOfLogsPerPage() + 1;
      }

      return totalPage;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

}
