/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/TestRequestHeader.java,v 1.1 2008/11/18 07:00:59 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/11/18 07:00:59 $
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

import java.io.StringReader;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/11/18 07:00:59 $
 */
public class TestRequestHeader extends TestCase {

  /**
   * @param name
   */
  public TestRequestHeader(String name) {
    super(name);
  }

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
  
  public void testParser() throws Exception {
    String headerInfo = "0001\t00000\t20081118200809\t总部终端管理平台\t20081118010203\t20081118210203\t12345\t\t";
    RequestHeader header = RequestHeader.parseHeaderInfo(new StringReader(headerInfo));
    assertNotNull(header);
    assertEquals("0001", header.getSerialNumber());
    assertEquals("00000", header.getVersion());
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    assertEquals("20081118200809", format.format(header.getGeneratedTime()));
    assertEquals("总部终端管理平台", header.getGeneratedBy());
    assertEquals("20081118010203", format.format(header.getStartTime()));
    assertEquals("20081118210203", format.format(header.getEndTime()));
    assertEquals(12345, header.getTotalRecords());
    assertEquals("", header.getMemo());
  }

}
