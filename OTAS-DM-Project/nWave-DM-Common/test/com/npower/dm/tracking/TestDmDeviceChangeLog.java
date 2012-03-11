/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/tracking/TestDmDeviceChangeLog.java,v 1.2 2008/08/06 08:02:19 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/06 08:02:19 $
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

package com.npower.dm.tracking;

import java.io.IOException;

import junit.framework.TestCase;

import com.npower.dm.tracking.writer.DaoDMDeviceChangeLogWriter;
import com.npower.dm.tracking.writer.DeviceChangeLogWriterChain;

/**
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/08/06 08:02:19 $
 */

public class TestDmDeviceChangeLog extends TestCase {

  /**
   * @param name
   */
  public TestDmDeviceChangeLog(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testWrite() throws IOException {
    DeviceChangeLogger logger = new DeviceChangeLoggerImpl();
    logger.setWriter(new DaoDMDeviceChangeLogWriter());
    logger.log("357674015889737", "13946016587", "2356874", "Moto", "E1", "V1");
  }
  
  public void testChain() throws IOException {
    DeviceChangeLogWriterChain chain = new DeviceChangeLogWriterChain();
    chain.add(new DaoDMDeviceChangeLogWriter());
    
    DeviceChangeLogger logger = new DeviceChangeLoggerImpl();
    logger.log("357674015889737", "13946016587", "2356874", "Moto", "E1", "V1");
  }
  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

}
