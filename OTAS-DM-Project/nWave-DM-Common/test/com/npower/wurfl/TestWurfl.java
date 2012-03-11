/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/wurfl/TestWurfl.java,v 1.3 2008/01/11 04:24:01 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/01/11 04:24:01 $
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
package com.npower.wurfl;


import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * @author Luca Passani Some Junit tests to make sure we do not break anything
 *         when refactoring
 * 
 * I declared this as part of the package to gain access to the package internal
 * methods
 * 
 * @version $Revision: 1.3 $ $Date: 2008/01/11 04:24:01 $
 */
public class TestWurfl extends TestCase {

  Wurfl wu = null;

  public static void main(String[] args) throws IOException {
    junit.textui.TestRunner.run(TestWurfl.class);
  }

  public void setUp() throws IOException {
    WurflSource source = new FileWurflSource(new File("D:\\zhao\\MyWorkspace\\nWave-DM-Common\\metadata\\wurfl\\wurfl.xml"));
    ObjectsManager om = ObjectsManager.newInstance(source);
    wu = om.getWurflInstance();
  }

  public void testWurflMethods() {

    System.out.println("Testing WURFL methods");
    System.out.println("1 -> wurfl.isCapabilityIn(\"colors\");");
    assertEquals(true, wu.isCapabilityIn("colors"));
    System.out.println("2 -> wurfl.isDeviceIn(\"mot_t720_ver1_sub050841r\");");
    assertEquals(true, wu.isDeviceIn("mot_t720_ver1_sub050841r"));
    assertEquals("generic", wu.getDeviceWhereCapabilityIsDefined("nokia_3650_ver1_subsemicolon",
        "empty_option_value_support"));
  }

}
