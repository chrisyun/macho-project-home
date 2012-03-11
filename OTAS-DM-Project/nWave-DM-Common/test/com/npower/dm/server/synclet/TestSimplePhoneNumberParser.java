/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/server/synclet/TestSimplePhoneNumberParser.java,v 1.2 2006/12/13 07:16:41 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2006/12/13 07:16:41 $
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
package com.npower.dm.server.synclet;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/12/13 07:16:41 $
 */
public class TestSimplePhoneNumberParser extends TestCase {

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
  
  public void testParser1() throws Exception {
    PhoneNumberDetector parser = new SimplePhoneNumberDetector();
    String deviceExternalID = "IMEI:351886011493928";
    assertEquals("351886011493928", parser.getPhoneNumber(deviceExternalID, null, null));
  }

  public void testParser2() throws Exception {
    PhoneNumberDetector parser = new SimplePhoneNumberDetector();
    String deviceExternalID = "351886011493928";
    assertEquals("351886011493928", parser.getPhoneNumber(deviceExternalID, null, null));
  }

}
