/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/audit/TestDeviceLogger.java,v 1.2 2007/02/08 07:11:59 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/02/08 07:11:59 $
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
package com.npower.dm.audit;

import junit.framework.TestCase;

import com.npower.dm.audit.action.DeviceAction;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/02/08 07:11:59 $
 */
public class TestDeviceLogger extends TestCase {

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
  
  public void testLog() throws Exception {
    AuditLoggerFactory factory = AuditLoggerFactory.newInstance();
    DeviceAuditLogger logger = factory.getDeviceLog();
    String username = "usr_" + System.currentTimeMillis() + "_";
    String deviceExtID = "IMEI:" + System.currentTimeMillis();
    for (int i = 0; i < 100; i++) {
        logger.log(DeviceAction.Activate_Device, username + i, "127.0.0.1", System.currentTimeMillis(), deviceExtID + i, null);
    }
    for (int i = 0; i < 100; i++) {
      logger.log(DeviceAction.Deactivate_Device, username + i, "127.0.0.1", System.currentTimeMillis(), deviceExtID + i, null);
  }
  }

}
