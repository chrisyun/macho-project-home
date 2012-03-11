/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/util/TestWBXMLTools.java,v 1.1 2007/06/04 03:10:43 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/06/04 03:10:43 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.npower.dm.AllTests;

import junit.framework.TestCase;
import sync4j.framework.tools.WBXMLTools;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/06/04 03:10:43 $
 */
public class TestWBXMLTools extends TestCase {

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
  
  public void testWbxmlToXml() throws Exception {
    // SonyEricsson K550
    File file = new File(AllTests.BASE_DIR, "metadata/dump/sonyericsson_k550/1_req.msg");
    int length = (int)file.length();
    byte[] msg = new byte[length];
    InputStream in = new FileInputStream(file);
    in.read(msg);
    
    String xml = WBXMLTools.wbxmlToXml(msg);
    
    assertEquals("", xml);
    
  }

}
