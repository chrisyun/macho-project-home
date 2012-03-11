/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/device/TestDeviceImportDaemonPlugIn.java,v 1.1 2008/11/19 05:58:51 zhaowx Exp $
 * $Revision: 1.1 $
 * $Date: 2008/11/19 05:58:51 $
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

import junit.framework.TestCase;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.1 $ $Date: 2008/11/19 05:58:51 $
 */

public class TestDeviceImportDaemonPlugIn extends TestCase {

  private DeviceImportDaemonPlugIn daemon;
  /**
   * @param name
   */
  public TestDeviceImportDaemonPlugIn(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    System.setProperty("otas.dm.home", "D:/OTASDM");
    daemon = new DeviceImportDaemonPlugIn();
    daemon.setDirectory("D:/OTASDM/output/device/request");
    daemon.setIntervalInSeconds(2);
    daemon.init(null, null);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    this.daemon.destroy();
  }

  public void testCase1() throws Exception {
    Thread.sleep(2000 * 1000);
  }
  
}
