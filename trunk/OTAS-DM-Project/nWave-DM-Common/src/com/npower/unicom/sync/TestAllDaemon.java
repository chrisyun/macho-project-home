/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/TestAllDaemon.java,v 1.1 2008/11/19 07:00:00 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/11/19 07:00:00 $
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

import java.io.File;

import junit.framework.TestCase;

import com.npower.unicom.sync.device.DeviceExportDaemonPlugIn;
import com.npower.unicom.sync.firmware.FirmwareExportDaemonPlugIn;
import com.npower.unicom.sync.model.ModelExportDaemonPlugIn;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/11/19 07:00:00 $
 */
public class TestAllDaemon extends TestCase {

  private AbstractExportDaemonPlugIn deviceExportDaemon;
  private AbstractExportDaemonPlugIn firmwareExportDaemon;
  private AbstractExportDaemonPlugIn modelExportDaemon;

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

    System.setProperty("otas.dm.home", "D:/OTASDM");
    String home = System.getProperty("otas.dm.home");
    
    deviceExportDaemon = new DeviceExportDaemonPlugIn();
    deviceExportDaemon.setDirectory((new File(home, "output/device").getAbsolutePath()));
    deviceExportDaemon.setTimePattern("*-*-* *:*:00");
    deviceExportDaemon.init(null, null);
    
    firmwareExportDaemon = new FirmwareExportDaemonPlugIn();
    firmwareExportDaemon.setDirectory((new File(home, "output/firmware").getAbsolutePath()));
    firmwareExportDaemon.setTimePattern("*-*-* *:*:00");
    firmwareExportDaemon.init(null, null);
    
    modelExportDaemon = new ModelExportDaemonPlugIn();
    modelExportDaemon.setDirectory((new File(home, "output/model").getAbsolutePath()));
    modelExportDaemon.setTimePattern("*-*-* *:*:00");
    modelExportDaemon.init(null, null);
}

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    this.deviceExportDaemon.destroy();
    this.firmwareExportDaemon.destroy();
    this.modelExportDaemon.destroy();
  }

  public void testCase1() throws Exception {
    Thread.sleep(1000 * 1000);
  }
}
