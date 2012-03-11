/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/tracking/TestAccessLogger.java,v 1.3 2008/08/04 10:36:21 zhaowx Exp $
 * $Revision: 1.3 $
 * $Date: 2008/08/04 10:36:21 $
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.npower.dm.tracking.writer.AccessLogWriterChain;
import com.npower.dm.tracking.writer.DaoAccessLogWriter;
import com.npower.dm.tracking.writer.SimpleAccessLogWriter;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/08/04 10:36:21 $
 */
public class TestAccessLogger extends TestCase {
  
  private HttpServletRequest request = null;

  /**
   * @param name
   */
  public TestAccessLogger(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("user-agent", "Firfox");
    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("modelID", "aaaaaa");
    this.request = new HttpServletRequest4Test(headers, parameters);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testWrite() throws Exception {
    AccessLogger logger = new AccessLoggerImpl(new SimpleAccessLogWriter(System.err));
    logger.log(this.request);
  }

  public void testChain() throws Exception {
    AccessLogWriterChain chain = new AccessLogWriterChain();
    chain.add(new SimpleAccessLogWriter(System.err));
    chain.add(new DaoAccessLogWriter());
    
    AccessLogger logger = new AccessLoggerImpl(chain);
    logger.log(this.request);
  }

}
