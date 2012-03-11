/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/tracking/TestDMTrackingLogJob.java,v 1.1 2008/08/04 10:21:31 chenlei Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/04 10:21:31 $
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
import java.util.Date;

import junit.framework.TestCase;

import com.npower.dm.tracking.writer.DMJobLogWriterChain;
import com.npower.dm.tracking.writer.DaoDMJobLogWriter;

/**
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2008/08/04 10:21:31 $
 */

public class TestDMTrackingLogJob extends TestCase {



  /**
   * @param name
   */
  public TestDMTrackingLogJob(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testWrite() throws IOException {
    DMJobLogger logger = new DMJobLoggerImpl();
    logger.setWriter(new DaoDMJobLogWriter());
    long now = System.currentTimeMillis();
    Date beginDate = new Date(now);
    Date endDate = new Date(now + 10 * 1000);
    //Calendar cal = Calendar.getInstance();
    //cal.set(Calendar.YEAR, value);
    logger.log("1fgd", 2, "aaaaaaaa", new SimpleManagementProcessor(), beginDate, endDate, "ok");

  }
  
  public void testChain() throws IOException {
    DMJobLogWriterChain chain = new DMJobLogWriterChain();
    long now = System.currentTimeMillis();
    Date beginDate = new Date(now);
    Date endDate = new Date(now + 10 * 1000);
    chain.add(new DaoDMJobLogWriter());
    
    DMJobLogger logger = new DMJobLoggerImpl();
    logger.log("1", 2, "aaaaaaaa", new SimpleManagementProcessor(), beginDate, endDate, "ok");
  }
  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

}
