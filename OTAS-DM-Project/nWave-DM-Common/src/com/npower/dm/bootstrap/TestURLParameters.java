/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/bootstrap/TestURLParameters.java,v 1.1 2008/03/11 03:43:32 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/03/11 03:43:32 $
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
package com.npower.dm.bootstrap;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/03/11 03:43:32 $
 */
public class TestURLParameters extends TestCase {

  /**
   * @param name
   */
  public TestURLParameters(String name) {
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
  
  public void testCase1() throws Exception {
    {
      URLParameters params = new URLParameters();
      params.setMsisdn("12801256729");
      params.setDeviceID("12345670");
      params.setDeviceExtID("IMEI:000000001117886");
      
      String result = params.encode();
      assertEquals("p_12801256729_id_12345670_x_IMEI:000000001117886", result);
    }
    {
      URLParameters params = URLParameters.decode("p_12801256729_id_12345670_x_IMEI:000000001117886");
      assertEquals("12801256729", params.getMsisdn());
      assertEquals("12345670", params.getDeviceID());
      assertEquals("IMEI:000000001117886", params.getDeviceExtID());
    }
  }

  public void testCase2() throws Exception {
    {
      URLParameters params = new URLParameters();
      params.setMsisdn("12801256729");
      params.setDeviceID("12345670");
      params.setDeviceExtID(null);
      
      String result = params.encode();
      assertEquals("p_12801256729_id_12345670_x_", result);
    }
    {
      URLParameters params = URLParameters.decode("p_12801256729_id_12345670_x_");
      assertEquals("12801256729", params.getMsisdn());
      assertEquals("12345670", params.getDeviceID());
      assertEquals("", params.getDeviceExtID());
    }
  }

}
