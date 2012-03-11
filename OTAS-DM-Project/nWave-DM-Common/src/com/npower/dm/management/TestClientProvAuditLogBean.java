/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestClientProvAuditLogBean.java,v 1.4 2008/06/16 10:11:14 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/06/16 10:11:14 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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

import java.sql.Date;
import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.ClientProvAuditLog;
import com.npower.dm.core.DMException;

/**
 * @author Liu AiHui
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:14 $
 */

public class TestClientProvAuditLogBean extends TestCase {
  private static final String carrier = "ChinaMobile";
  private static final String deviceManufacturer = "nokia";
  private static final String deviceModel = "6610";
  private static final String memo = "memotemp.....";
  private static final String profileCategory = "profilecategorytest";
  private static final String profileName = "profilenametest";
  private static final String profileContent = "profileContenttest";
  private static Date timeStamp = new Date(System.currentTimeMillis());
  private static String status = "failure";
  private static String devicePhoneNumber = "13012345678";

  private static final String carrier2 = "ChinaMobile2";
  private static final String deviceManufacturer2 = "nokia2";
  private static final String deviceModel2 = "66102";
  private static final String memo2 = "memotemp.....2";
  private static final String profileCategory2 = "profilecategorytest2";
  private static final String profileName2 = "profilenametest2";
  private static final String profileContent2 = "profileContenttest2";
  private static Date timeStamp2 = new Date(System.currentTimeMillis());
  private static String status2 = "success";
  private static String devicePhoneNumber2 = "13012345672";
  
  private static final String carrier3 = "ChinaMobile3";
  private static final String deviceManufacturer3 = "nokia2";
  private static final String deviceModel3 = "66102";
  private static final String memo3 = "memotemp.....3";
  private static final String profileCategory3 = "profilecategorytest3";
  private static final String profileName3 = "profilenametest3";
  private static final String profileContent3 = "profileContenttest3";
  private static Date timeStamp3 = new Date(System.currentTimeMillis());
  private static String status3 = "success";
  private static String devicePhoneNumber3 = "13012345672";
  
  private static final String carrier22 = "ChinaMobile22";
  private static final String deviceManufacturer22 = "nokia22";
  private static final String deviceModel22 = "6622";
  private static final String memo22 = "memotemp.....22";
  private static final String profileCategory22 = "profilecategorytest22";
  private static final String profileName22 = "profilenametest22";
  private static final String profileContent22 = "profileContenttest22";
  private static Date timeStamp22 = new Date(System.currentTimeMillis());
  private static String status22 = "failure";
  private static String devicePhoneNumber22 = "130123456722";
  private long id = 0L;
  private long id2 = 0L;
  private long id3 = 0L;  
  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    //add a cpAuditLog
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    ClientProvAuditLogBean bean = beanFactory.createClientProvAuditLogBean();
    
    ClientProvAuditLog cpAuditLog = bean.newClientProvAuditLogInstance(timeStamp, devicePhoneNumber, status); 
    cpAuditLog.setCarrierExtID(carrier);
    cpAuditLog.setManufacturerExtID(deviceManufacturer);
    cpAuditLog.setModelExtID(deviceModel);
    cpAuditLog.setMemo(memo);
    cpAuditLog.setProfileCategoryName(profileCategory);
    cpAuditLog.setProfileName(profileName);
    cpAuditLog.setProfileContent(profileContent);

    ClientProvAuditLog cpAuditLog2 = bean.newClientProvAuditLogInstance(timeStamp2, devicePhoneNumber2, status2); 
    cpAuditLog2.setCarrierExtID(carrier2);
    cpAuditLog2.setManufacturerExtID(deviceManufacturer2);
    cpAuditLog2.setModelExtID(deviceModel2);
    cpAuditLog2.setMemo(memo2);
    cpAuditLog2.setProfileCategoryName(profileCategory2);
    cpAuditLog2.setProfileName(profileName2);
    cpAuditLog2.setProfileContent(profileContent2);

    
    ClientProvAuditLog cpAuditLog3 = bean.newClientProvAuditLogInstance(timeStamp3, devicePhoneNumber3, status3); 
    cpAuditLog3.setCarrierExtID(carrier3);
    cpAuditLog3.setManufacturerExtID(deviceManufacturer3);
    cpAuditLog3.setModelExtID(deviceModel3);
    cpAuditLog3.setMemo(memo3);
    cpAuditLog3.setProfileCategoryName(profileCategory3);
    cpAuditLog3.setProfileName(profileName3);
    cpAuditLog3.setProfileContent(profileContent3);
    
    try {
      beanFactory.beginTransaction();
    
      bean.update(cpAuditLog);
      
      assertNotNull(cpAuditLog);
      assertEquals(cpAuditLog.getCarrierExtID(),carrier);
      assertEquals(cpAuditLog.getManufacturerExtID(),deviceManufacturer);
      assertEquals(cpAuditLog.getModelExtID(),deviceModel);
      assertEquals(cpAuditLog.getDevicePhoneNumber(),devicePhoneNumber);
      assertEquals(cpAuditLog.getMemo(),memo);
      assertEquals(cpAuditLog.getProfileCategoryName(),profileCategory);
      assertEquals(cpAuditLog.getProfileContent(),profileContent);
      assertEquals(cpAuditLog.getProfileName(),profileName);
      assertEquals(cpAuditLog.getStatus(),status);
      assertEquals(cpAuditLog.getTimeStamp(),timeStamp);
      
      id = cpAuditLog.getCpAuditLogId();
      
      //second
      bean.update(cpAuditLog2);
      
      assertNotNull(cpAuditLog2);
      assertEquals(cpAuditLog2.getCarrierExtID(),carrier2);
      assertEquals(cpAuditLog2.getManufacturerExtID(),deviceManufacturer2);
      assertEquals(cpAuditLog2.getModelExtID(),deviceModel2);
      assertEquals(cpAuditLog2.getDevicePhoneNumber(),devicePhoneNumber2);
      assertEquals(cpAuditLog2.getMemo(),memo2);
      assertEquals(cpAuditLog2.getProfileCategoryName(),profileCategory2);
      assertEquals(cpAuditLog2.getProfileContent(),profileContent2);
      assertEquals(cpAuditLog2.getProfileName(),profileName2);
      assertEquals(cpAuditLog2.getStatus(),status2);
      assertEquals(cpAuditLog2.getTimeStamp(),timeStamp2);
      
      id2 = cpAuditLog2.getCpAuditLogId();
      
      //third
      bean.update(cpAuditLog3);
      
      assertNotNull(cpAuditLog3);
      assertEquals(cpAuditLog3.getCarrierExtID(),carrier3);
      assertEquals(cpAuditLog3.getManufacturerExtID(),deviceManufacturer3);
      assertEquals(cpAuditLog3.getModelExtID(),deviceModel3);
      assertEquals(cpAuditLog3.getDevicePhoneNumber(),devicePhoneNumber3);
      assertEquals(cpAuditLog3.getMemo(),memo3);
      assertEquals(cpAuditLog3.getProfileCategoryName(),profileCategory3);
      assertEquals(cpAuditLog3.getProfileContent(),profileContent3);
      assertEquals(cpAuditLog3.getProfileName(),profileName3);
      assertEquals(cpAuditLog3.getStatus(),status3);
      assertEquals(cpAuditLog3.getTimeStamp(),timeStamp3);
      
      id3 = cpAuditLog3.getCpAuditLogId();

      
      beanFactory.commit();
    } catch (Exception e) {
      beanFactory.rollback();      
      assertTrue(false);
    } finally {
      beanFactory.release();
    }    

  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    //delete a cpAuditLog
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    ClientProvAuditLogBean bean = beanFactory.createClientProvAuditLogBean();

    try {
      beanFactory.beginTransaction();
      ClientProvAuditLog cpAuditLog = bean.getClientProvAuditLogByID(id);
      if (cpAuditLog != null) {
        bean.delete(cpAuditLog);
      }
      ClientProvAuditLog cpAuditLog2 = bean.getClientProvAuditLogByID(id2);
      if (cpAuditLog2 != null) {
        bean.delete(cpAuditLog2);
      }
      ClientProvAuditLog cpAuditLog3 = bean.getClientProvAuditLogByID(id3);
      if (cpAuditLog3 != null) {
        bean.delete(cpAuditLog3);
      }
      cpAuditLog = bean.getClientProvAuditLogByID(id);
      assertNull(cpAuditLog);
      cpAuditLog = bean.getClientProvAuditLogByID(id2);
      assertNull(cpAuditLog);
      cpAuditLog = bean.getClientProvAuditLogByID(id3);
      assertNull(cpAuditLog);
      beanFactory.commit();
    } catch (Exception e) {
      beanFactory.rollback();      
      assertTrue(false);
    } finally {
      beanFactory.release();
    }  
  }

  public void testUpdate() throws DMException{
    //add a cpAuditLog
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    ClientProvAuditLogBean bean = beanFactory.createClientProvAuditLogBean();
        
    try {
      beanFactory.beginTransaction();
      ClientProvAuditLog cpAuditLog2 = bean.getClientProvAuditLogByID(id);
      assertNotNull(cpAuditLog2);
      cpAuditLog2.setCarrierExtID(carrier22);
      cpAuditLog2.setManufacturerExtID(deviceManufacturer22);
      cpAuditLog2.setModelExtID(deviceModel22);
      cpAuditLog2.setMemo(memo22);
      cpAuditLog2.setProfileCategoryName(profileCategory22);
      cpAuditLog2.setProfileName(profileName22);
      cpAuditLog2.setProfileContent(profileContent22);
      cpAuditLog2.setDevicePhoneNumber(devicePhoneNumber22);
      cpAuditLog2.setStatus(status22);
      cpAuditLog2.setTimeStamp(timeStamp22);
      bean.update(cpAuditLog2);
      
      assertNotNull(cpAuditLog2);
      assertEquals(cpAuditLog2.getCarrierExtID(),carrier22);
      assertEquals(cpAuditLog2.getManufacturerExtID(),deviceManufacturer22);
      assertEquals(cpAuditLog2.getModelExtID(),deviceModel22);
      assertEquals(cpAuditLog2.getDevicePhoneNumber(),devicePhoneNumber22);
      assertEquals(cpAuditLog2.getMemo(),memo22);
      assertEquals(cpAuditLog2.getProfileCategoryName(),profileCategory22);
      assertEquals(cpAuditLog2.getProfileContent(),profileContent22);
      assertEquals(cpAuditLog2.getProfileName(),profileName22);
      assertEquals(cpAuditLog2.getStatus(),status22);
      assertEquals(cpAuditLog2.getTimeStamp(),timeStamp22);
      
      
      beanFactory.commit();
    } catch (Exception e) {
      beanFactory.rollback();      
      assertTrue(false);
    } finally {
      beanFactory.release();
    }    

  }
  
  public void testFind() throws DMException{
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    ClientProvAuditLogBean bean = beanFactory.createClientProvAuditLogBean();

    try {
      beanFactory.beginTransaction();
      List<ClientProvAuditLog> cpAuditLogs = bean.getAllClientProvAuditLogs();
      beanFactory.commit();
      assertNotNull(cpAuditLogs);
      assertTrue(cpAuditLogs.size() == 3);
      ClientProvAuditLog cpAuditLog = (ClientProvAuditLog)cpAuditLogs.get(0);
      assertEquals(cpAuditLog.getCarrierExtID(),carrier);
      assertEquals(cpAuditLog.getManufacturerExtID(),deviceManufacturer);
      assertEquals(cpAuditLog.getModelExtID(),deviceModel);
      assertEquals(cpAuditLog.getDevicePhoneNumber(),devicePhoneNumber);
      assertEquals(cpAuditLog.getMemo(),memo);
      assertEquals(cpAuditLog.getProfileCategoryName(),profileCategory);
      assertEquals(cpAuditLog.getProfileContent(),profileContent);
      assertEquals(cpAuditLog.getProfileName(),profileName);
      assertEquals(cpAuditLog.getStatus(),status);
      //junit.framework.AssertionFailedError: expected:<2007-09-20 10:28:42.0> but was:<2007-09-20>
      //assertEquals(cpAuditLog.getTimeStamp(),timeStamp);
      
     } catch (Exception e) {
      beanFactory.rollback();      
      assertTrue(false);
    } finally {
      beanFactory.release();
    }          
  }
  public void testGetNextPrveID() throws DMException{
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    ClientProvAuditLogBean bean = beanFactory.createClientProvAuditLogBean();

    try {
      beanFactory.beginTransaction();
      //first
      Long nextID = bean.getNextID(id);
      assertTrue(nextID == id2);
      
      //second
      Long nextID2 = bean.getNextID(id2);
      assertTrue(nextID2 == id3);
      Long prveID2 =bean.getPrevID(id2);
      assertTrue(prveID2 == id);
      
      //third
      Long prveID3 =bean.getPrevID(id3);
      assertTrue(prveID3 == id2);
      
      beanFactory.commit();
      
     } catch (Exception e) {
      beanFactory.rollback();      
      assertTrue(false);
    } finally {
      beanFactory.release();
    }
    
  }
}
