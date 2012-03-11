/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/TestTacItemParser.java,v 1.1 2008/10/31 10:03:04 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/10/31 10:03:04 $
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
package com.npower.dm.setup.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.AllTests;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/10/31 10:03:04 $
 */
public class TestTacItemParser extends TestCase {

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
    List<TacItem> items = new ArrayList<TacItem>();
    
    TacItemParser parser = new TacItemParser(new File(AllTests.BASE_DIR, "metadata/TAC/testcase1.csv"));
    parser.open();
    TacItem item = parser.getNext();
    while (item != null) {
          items.add(item);
          item = parser.getNext();
    }
    parser.close();
    
    assertEquals(1, items.size());
    
  }
  
  
  public void testCase2() throws Exception {
    List<TacItem> items = new ArrayList<TacItem>();
    
    TacItemParser parser = new TacItemParser(new File(AllTests.BASE_DIR, "metadata/setup/tac/www.STADTAUS.com_TW06_IMEI_csv_v0810.csv"));
    parser.open();
    TacItem item = parser.getNext();
    while (item != null) {
          items.add(item);
          item = parser.getNext();
    }
    parser.close();
    
    assertEquals(9997, items.size());
    
  }

}
