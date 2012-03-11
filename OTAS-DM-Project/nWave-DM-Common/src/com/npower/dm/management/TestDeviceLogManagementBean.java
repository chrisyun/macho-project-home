/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestDeviceLogManagementBean.java,v 1.18 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.18 $
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
package com.npower.dm.management;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceLog;
import com.npower.dm.core.DeviceLogAction;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Subscriber;
import com.npower.dm.hibernate.entity.DeviceLogEntity;

/**
 * <p>
 * </p>
 * 
 * @author Zhao Yang
 * @version $Revision: 1.18 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestDeviceLogManagementBean extends TestCase {

  protected void setUp() throws Exception {
    this.setupEntities();
  }

  private void setupEntities() throws Exception {

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DeviceBean devicebean = beanFactory.createDeviceBean();
    CarrierBean carrierBean = beanFactory.createCarrierBean();
    ModelBean modelBean = beanFactory.createModelBean();
    SubscriberBean subscriberBean = beanFactory.createSubcriberBean();
    CountryBean countryBean = beanFactory.createCountryBean();

    try {
        beanFactory.beginTransaction();
  
        Country country = countryBean.getCountryByISOCode(DeviceLog.Count_Name);
        assertNotNull(country);
        // create carrier
        String externalID = DeviceLog.CARRIER_EXTERNAL_ID;
        String carrierName = DeviceLog.CARRIER_NAME;
        Carrier carrier = carrierBean.getCarrierByExternalID(externalID);
        if (carrier == null) {
          carrier = carrierBean.newCarrierInstance(country, externalID, carrierName);
          carrierBean.update(carrier);
        }
        assertNotNull(carrier);
        assertEquals(carrier.getName(), DeviceLog.CARRIER_NAME);
  
        // create subscriber
        List<Subscriber> mans1 = subscriberBean.findSubscriber(" from SubscriberEntity where externalId = '" + DeviceLog.subExternalID + "'");
        Subscriber subscriber = null;
        if (mans1.size() == 0) {
  
          subscriber = subscriberBean.newSubscriberInstance(carrier, DeviceLog.subExternalID, DeviceLog.subExternalID, DeviceLog.subExternalID);
          subscriber.setCarrier(carrier);
          subscriber.setExternalId(DeviceLog.subExternalID);
          subscriber.setPhoneNumber(DeviceLog.subExternalID);
          subscriber.setPassword(DeviceLog.subExternalID);
          subscriberBean.update(subscriber);
        } else {
          subscriber = (Subscriber) mans1.get(0);
        }
        assertNotNull(subscriber);
  
        // create manufacturer
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(DeviceLog.MANUFACTURER_EXTERNAL_ID);
        if (manufacturer == null) {
          manufacturer = modelBean.newManufacturerInstance(DeviceLog.MANUFACTURER_EXTERNAL_ID, DeviceLog.MANUFACTURER_EXTERNAL_ID,
              DeviceLog.MANUFACTURER_EXTERNAL_ID);
          modelBean.update(manufacturer);
        }
        assertNotNull(manufacturer);
  
        // create model
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, DeviceLog.MODEL_EXTERNAL_ID);
        if (model == null) {
          model = modelBean.newModelInstance(manufacturer, DeviceLog.MODEL_EXTERNAL_ID, DeviceLog.MODEL_EXTERNAL_ID, true, true, true, true, true);
          modelBean.update(model);
        }
        assertNotNull(model);
  
        // create device
        Device device = devicebean.getDeviceByExternalID(DeviceLog.DEVICE_EXTERNAL_ID);
        if (device == null) {
          device = devicebean.newDeviceInstance(subscriber, model, DeviceLog.DEVICE_EXTERNAL_ID);
          devicebean.update(device);
        }
        assertNotNull(device);
        
        beanFactory.commit();
        
    } catch (RuntimeException e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }

  public void add() throws Exception {

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DeviceLogBean logbean = beanFactory.createDeviceLogBean();
    DeviceBean devicebean = beanFactory.createDeviceBean();
    CarrierBean carrierBean = beanFactory.createCarrierBean();
    ModelBean modelBean = beanFactory.createModelBean();
    SubscriberBean subscriberBean = beanFactory.createSubcriberBean();
    CountryBean countryBean = beanFactory.createCountryBean();
    try {
      Country country = countryBean.getCountryByISOCode(DeviceLog.Count_Name);
      assertNotNull(country);

      // load carrier
      Carrier carrier = carrierBean.getCarrierByExternalID(DeviceLog.CARRIER_EXTERNAL_ID);
      assertNotNull(carrier);

      // laod subscriber
      List<Subscriber> subscribers = subscriberBean.findSubscriber(" from SubscriberEntity where externalId = '" + DeviceLog.subExternalID + "'");
      assertTrue(subscribers.size() > 0);
      Subscriber subscriber = (Subscriber) subscribers.get(0);
      assertNotNull(subscriber);

      // load manufacturer
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(DeviceLog.MANUFACTURER_EXTERNAL_ID);
      assertNotNull(manufacturer);

      // load model
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, DeviceLog.MODEL_EXTERNAL_ID);
      assertNotNull(model);

      // load device
      Device device = devicebean.getDeviceByExternalID(DeviceLog.DEVICE_EXTERNAL_ID);
      assertNotNull(device);

      beanFactory.beginTransaction();
      // create devicelog
      assertEquals(DeviceLog.actionName, DeviceLog.actionName);

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      int YEAR = calendar.get(Calendar.YEAR);

      for (int y = YEAR - 1; y < YEAR + 2; y++) {

        for (int month = 0; month < 12; month = month + 2) {

          for (int d = 1; d < 31; d = d + 5) {

            for (int h = 0; h < 12; h = h + 3) {

              for (int min = 10; min < 60; min = min + 10) {

                for (int s = 30; s < 60; s = s + 30) {
                  DeviceLog devicelog = logbean.newDeviceLogInstance(DeviceLog.actionName, device, "Assign Profile", "test message" + "#" + y + month + d
                      + h + min + s);

                  DeviceLogEntity entity = (DeviceLogEntity) devicelog;
                  SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                  String year = (y < 10) ? "0" + y : "" + y;

                  String mon = (month < 10) ? "0" + month : "" + month;

                  String day = (d < 10) ? "0" + d : "" + d;

                  String hour = (h < 10) ? "0" + h : "" + h;

                  String minute = (min < 10) ? "0" + min : "" + min;

                  String sec = (s < 10) ? "0" + s : "" + s;

                  String dateStringToParse = "" + year + "-" + mon + "-" + day + " " + hour + ":" + minute + ":" + sec + "";

                  Date date = bartDateFormat.parse(dateStringToParse);
                  entity.setCreationDate(date);
                  // System.out.println(entity.getCreationDate());

                  logbean.update(entity);
                }

              }

            }

          }

        }
      }

      beanFactory.commit();

    } catch (RuntimeException e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }


  }

  public void testLogAdd() throws Exception {

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DeviceLogBean logbean = beanFactory.createDeviceLogBean();
    DeviceBean devicebean = beanFactory.createDeviceBean();
    CarrierBean carrierBean = beanFactory.createCarrierBean();
    ModelBean modelBean = beanFactory.createModelBean();
    SubscriberBean subscriberBean = beanFactory.createSubcriberBean();
    CountryBean countryBean = beanFactory.createCountryBean();
    try {
      
      //没有运行完JUnit就终止的情况
      beanFactory.beginTransaction();
      logbean.clearAll();
      beanFactory.commit();
      
      Country country = countryBean.getCountryByISOCode(DeviceLog.Count_Name);
      assertNotNull(country);

      // load carrier
      Carrier carrier = carrierBean.getCarrierByExternalID(DeviceLog.CARRIER_EXTERNAL_ID);
      assertNotNull(carrier);

      // laod subscriber
      List<Subscriber> subscribers = subscriberBean.findSubscriber(" from SubscriberEntity where externalId = '" + DeviceLog.subExternalID + "'");
      assertTrue(subscribers.size() > 0);
      Subscriber subscriber = (Subscriber) subscribers.get(0);
      assertNotNull(subscriber);

      // load manufacturer
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(DeviceLog.MANUFACTURER_EXTERNAL_ID);
      assertNotNull(manufacturer);

      // load model
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, DeviceLog.MODEL_EXTERNAL_ID);
      assertNotNull(model);

      // load device
      Device device = devicebean.getDeviceByExternalID(DeviceLog.DEVICE_EXTERNAL_ID);
      assertNotNull(device);

      beanFactory.beginTransaction();
      // create devicelog
      assertEquals(DeviceLog.actionName, DeviceLog.actionName);
      for (int i = 0; i < 100; i++) {
        DeviceLog devicelog = logbean.newDeviceLogInstance(DeviceLog.actionName, device, "Assign Profile" + "@" + i, "test message" + "#" + i);
        logbean.update(devicelog);
      }
      beanFactory.commit();

      List<DeviceLog> logsbyaction = logbean.findDeviceLogsByAction(DeviceLog.actionName);
      
      assertEquals(100, logsbyaction.size());

      for (int i = 0; i < logsbyaction.size(); i++) {
        DeviceLog logbyaction = (DeviceLog) logsbyaction.get(i);
        assertNotNull(logbyaction);
        assertEquals(logbyaction.getMessage(), "test message" + "#" + (logsbyaction.size() - 1 - i));
        assertEquals(logbyaction.getJobType(), "Assign Profile" + "@" + (logsbyaction.size() - 1 - i));
      }

      beanFactory.beginTransaction();
      logbean.clearAll();
 
      beanFactory.commit();

    } catch (RuntimeException e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }

  public void testFindBytime() throws Exception {

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DeviceLogBean logbean = beanFactory.createDeviceLogBean();
    try {
      
      //没有运行完JUnit就终止的情况
      beanFactory.beginTransaction();
      logbean.clearAll();
      beanFactory.commit();

      add();

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      //Now Year
      int YEAR = 2007;
      int MONTH = 4;
      int DAY = 11;

      List<DeviceLog> year = logbean.findDeviceLogsByOneTime(YEAR);
      assertNotNull(year);
      assertEquals(720, year.size());

      List<DeviceLog> yearmonth = logbean.findDeviceLogsByOneTime(YEAR, MONTH);
      assertNotNull(yearmonth);
      assertEquals(120, yearmonth.size());

      List<DeviceLog> yearmonthday = logbean.findDeviceLogsByOneTime(YEAR, MONTH, DAY);
      assertNotNull(yearmonthday);
      assertEquals(20, yearmonthday.size());

      List<DeviceLog> yearmonthdayhour1 = logbean.findDeviceLogsByOneTime(YEAR, MONTH, DAY, 3);
      assertNotNull(yearmonthdayhour1);
      assertEquals(5, yearmonthdayhour1.size());

      List<DeviceLog> yearmonthdayhour2 = logbean.findDeviceLogsByOneTime(YEAR, MONTH, DAY, 6);
      assertNotNull(yearmonthdayhour2);

      assertEquals(5, yearmonthdayhour2.size());

      List<DeviceLog> yearmonthdayhourmin1 = logbean.findDeviceLogsByOneTime(YEAR, MONTH, DAY, 3, 10);
      assertNotNull(yearmonthdayhourmin1);
      assertEquals(1, yearmonthdayhourmin1.size());

      List<DeviceLog> yearmonthdayhourmin2 = logbean.findDeviceLogsByOneTime(YEAR, MONTH, DAY, 3, 20);
      assertNotNull(yearmonthdayhourmin2);
      assertEquals(1, yearmonthdayhourmin2.size());

      List<DeviceLog> yearmonthdayhourminsec1 = logbean.findDeviceLogsByOneTime(YEAR, MONTH, DAY, 3, 20, 30);
      assertNotNull(yearmonthdayhourminsec1);
      assertEquals(1, yearmonthdayhourminsec1.size());

      List<DeviceLog> yearmonthdayhourminsec2 = logbean.findDeviceLogsByOneTime(YEAR, MONTH, DAY, 3, 20, 30);
      assertNotNull(yearmonthdayhourminsec2);
      assertEquals(1, yearmonthdayhourminsec2.size());

      List<DeviceLog> betweentime1 = logbean.findDeviceLogsBetweenTwoTime(YEAR - 1, 1, 1, YEAR, 1, 1);
      assertNotNull(betweentime1);
      
      assertEquals(720, betweentime1.size());

      List<DeviceLog> betweentime2 = logbean.findDeviceLogsBetweenTwoTime(YEAR, 1, DAY, YEAR, 3, DAY);
      assertNotNull(betweentime2);
      assertEquals(120, betweentime2.size());

      List<DeviceLog> betweentime3 = logbean.findDeviceLogsBetweenTwoTime(YEAR, 4, 1, YEAR, 4, 6);
      assertNotNull(betweentime3);
      assertEquals(40, betweentime3.size());

      List<DeviceLog> betweentime4 = logbean.findDeviceLogsBetweenTwoTime(YEAR, 4, 1, 3, YEAR, 4, 6, 6);
      assertNotNull(betweentime4);
      assertEquals(30, betweentime4.size());

      List<DeviceLog> betweentime5 = logbean.findDeviceLogsBetweenTwoTime(YEAR, 4, 1, 3, YEAR, 4, 6, 9);
      assertNotNull(betweentime5);
      assertEquals(35, betweentime5.size());

      beanFactory.beginTransaction();
      logbean.clearAll();
      beanFactory.commit();

    } catch (RuntimeException e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }

  public void testFindByattribute() throws Exception {

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DeviceLogBean logbean = beanFactory.createDeviceLogBean();
    try {

      
      //没有运行完JUnit就终止的情况
      beanFactory.beginTransaction();
      logbean.clearAll();
      beanFactory.commit();

      add();

      List<DeviceLog> logsbyaction = logbean.findDeviceLogsByAction(DeviceLog.actionName);
      assertTrue(logsbyaction.size() == 2160);
      DeviceLog logbyaction1 = (DeviceLog) logsbyaction.get(0);
      assertNotNull(logbyaction1);
      //Now Year
      assertEquals(logbyaction1.getMessage(), "test message#2008102695030");
      assertEquals(logbyaction1.getJobType(), "Assign Profile");

      DeviceLog logbyaction2 = (DeviceLog) logsbyaction.get(logsbyaction.size() - 1);
      assertNotNull(logbyaction2);
      //Now Year-2      
      assertEquals(logbyaction2.getMessage(), "test message#20060101030");
      assertEquals(logbyaction2.getJobType(), "Assign Profile");

      String jobtype = "Assign Profile";
      List<DeviceLog> logsbyjobtype = logbean.findDeviceLogsByJobType(jobtype);
      assertTrue(logsbyjobtype.size() == 2160);
      DeviceLog logbyjobtype1 = (DeviceLog) logsbyjobtype.get(0);
      assertNotNull(logbyjobtype1);
      //Now Year
      assertEquals(logbyjobtype1.getMessage(), "test message#2008102695030");
      assertEquals(logbyjobtype1.getJobType(), "Assign Profile");
      //Now Year-2
      DeviceLog logbyjobtype2 = (DeviceLog) logsbyjobtype.get(logsbyaction.size() - 1);
      assertNotNull(logbyjobtype2);
      assertEquals(logbyjobtype2.getMessage(), "test message#20060101030");
      assertEquals(logbyjobtype2.getJobType(), "Assign Profile");

      beanFactory.beginTransaction();
      logbean.clearAll();
      beanFactory.commit();

    } catch (RuntimeException e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }

  public void testSelectattribute() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DeviceLogBean logbean = beanFactory.createDeviceLogBean();
    try {
      List<DeviceLogAction> actionlist = logbean.getDeviceLogActions();
      assertEquals(actionlist.size(), 36);

    } catch (RuntimeException e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }

  public void testPagination() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DeviceLogBean logbean = beanFactory.createDeviceLogBean();
    try {

      add();
      logbean.setOrderBy("ID desc");

      logbean.setNumberOfLogsPerPage(10);
      logbean.setCurrentPageNumber(1);

      List<DeviceLog> yearmonthday = logbean.findDeviceLogsByOneTime(2006, 4);
      assertEquals(logbean.getNumberOfTotalLogs(), 120);
      assertEquals(logbean.getNumberOfTotalPages(), 12);

      assertNotNull(yearmonthday);
      assertEquals(yearmonthday.size(), 10);

      for (int i = 0; i < 9; i++) {

        DeviceLog testlog1 = (DeviceLog) yearmonthday.get(i);
        DeviceLog testlog2 = (DeviceLog) yearmonthday.get(i + 1);

        assertTrue(testlog1.getCreationDate().compareTo(testlog2.getCreationDate()) == 1);

      }

      logbean.setNumberOfLogsPerPage(33);
      logbean.setCurrentPageNumber(1);

      List<DeviceLog> yearmonthday2 = logbean.findDeviceLogsByOneTime(2006, 4);
      assertEquals(logbean.getNumberOfTotalLogs(), 120);
      assertEquals(logbean.getNumberOfTotalPages(), 4);

      assertNotNull(yearmonthday2);
      assertEquals(yearmonthday2.size(), 33);

      for (int i = 0; i < 31; i++) {

        DeviceLog testlog1 = (DeviceLog) yearmonthday2.get(i);
        DeviceLog testlog2 = (DeviceLog) yearmonthday2.get(i + 1);

        assertTrue(testlog1.getCreationDate().compareTo(testlog2.getCreationDate()) == 1);

      }

      logbean.setNumberOfLogsPerPage(10);
      int totalpages = logbean.getNumberOfTotalPages();
      for (int i = 1; i < totalpages; i++) {
        logbean.setCurrentPageNumber(i);
        List<DeviceLog> testlist1 = logbean.findDeviceLogsByOneTime(2006, 4);
        logbean.setCurrentPageNumber(i + 1);
        List<DeviceLog> testlist2 = logbean.findDeviceLogsByOneTime(2006, 4);

        assertEquals(testlist1.size(), 10);
        assertEquals(testlist2.size(), 10);

        DeviceLog testlog1 = (DeviceLog) testlist1.get(0);
        DeviceLog testlog2 = (DeviceLog) testlist2.get(0);

        assertTrue(testlog1.getCreationDate().compareTo(testlog2.getCreationDate()) == 1);

      }

      beanFactory.beginTransaction();
      logbean.clearAll();
      beanFactory.commit();

    } catch (RuntimeException e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }

  protected void tearDown() throws Exception {

    tearDownEntites();

  }

  private void tearDownEntites() throws Exception {

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DeviceBean devicebean = beanFactory.createDeviceBean();
    CarrierBean carrierBean = beanFactory.createCarrierBean();
    ModelBean modelBean = beanFactory.createModelBean();

    try {

        Device device = devicebean.getDeviceByExternalID(DeviceLog.DEVICE_EXTERNAL_ID);
  
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(DeviceLog.MANUFACTURER_EXTERNAL_ID);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, DeviceLog.MODEL_EXTERNAL_ID);
  
        String externalID = DeviceLog.CARRIER_EXTERNAL_ID;
        Carrier carrier = carrierBean.getCarrierByExternalID(externalID);
  
        beanFactory.beginTransaction();
        devicebean.delete(device);
        modelBean.delete(model);
        modelBean.delete(manufacturer);
        //subscriberBean.delete(subscriber);
        carrierBean.delete(carrier);
        beanFactory.commit();

    } catch (RuntimeException e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }

}
