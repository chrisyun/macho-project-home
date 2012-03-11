/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/management/TestJob4Firmware.java,v 1.2 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.2 $
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

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Update;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestJob4Firmware extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testCreateFotaJob() throws Exception {
    String deviceExternalID = "IMEI:004400741767618";
    String updateID = "159600";
    
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        DeviceBean deviceBean = factory.createDeviceBean();
        UpdateImageBean updateBean = factory.createUpdateImageBean();
        
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        assertNotNull(device);
        Update update = updateBean.getUpdateByID(updateID);
        assertNotNull(update);
        
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        factory.beginTransaction();
        ProvisionJob job = jobBean.newJob4FirmwareUpdate(device, update);
        factory.commit();
        
        assertNotNull(job);
        
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

}
