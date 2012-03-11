/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/util/TestIMEIUtil.java,v 1.2 2007/11/20 09:51:53 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/11/20 09:51:53 $
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
package com.npower.dm.util;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/11/20 09:51:53 $
 */
public class TestIMEIUtil extends TestCase {

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
  
  public void testCalculateIMEIBy14Digital() throws Exception {
    byte[] input = {(byte)0x26, (byte)0x05, (byte)0x31, (byte)0x79, (byte)0x31, (byte)0x13, (byte)0x83};
    String imei = IMEIUtil.calculateIMEIBy14Digital(input);
    // Check code is 7
    assertEquals("260531793113837", imei);
  }

  public void testCalculate1() throws Exception {
    String imei = IMEIUtil.calculateIMEI("260531793113837");
    // Check code is 7
    assertEquals("260531793113837", imei);
  }

  public void testCalculate2() throws Exception {
    String imei = IMEIUtil.calculateIMEI("26053179311383");
    // Check code is 7
    assertEquals("260531793113837", imei);
  }

  public void testCalculate3() throws Exception {
    String imei = IMEIUtil.calculateIMEI("35434900712013");
    // Check code is 5
    assertEquals("354349007120135", imei);
  }

  public void testCalculate4() throws Exception {
    String imei = IMEIUtil.calculateIMEI("00440074176761");
    // Check code is 8
    assertEquals("004400741767618", imei);
  }

  public void testCalculate5() throws Exception {
    String imei = IMEIUtil.calculateIMEI("004400741767610");
    // Check code is 8
    assertEquals("004400741767618", imei);
  }

  public void testCalculate6() throws Exception {
    String imei = IMEIUtil.calculateIMEI("354349007120130");
    // Check code is 5
    assertEquals("354349007120135", imei);
  }
  
  public void testLong() throws Exception {
    assertEquals(15, IMEIUtil.generateIMEI().length());
  }

}
