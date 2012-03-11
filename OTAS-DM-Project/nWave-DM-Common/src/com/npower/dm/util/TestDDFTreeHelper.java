/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/util/TestDDFTreeHelper.java,v 1.5 2007/08/29 06:21:00 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2007/08/29 06:21:00 $
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

import java.util.List;

import junit.framework.TestCase;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/08/29 06:21:00 $
 */
public class TestDDFTreeHelper extends TestCase {

  /**
   * Constructor for TestDDFTreeManagementImpl.
   * 
   * @param arg0
   */
  public TestDDFTreeHelper(String arg0) {
    super(arg0);
  }

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testGetPathVector() {
    String nodePath1 = "./a/b/c/d/";
    List<String> path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 4);
    assertEquals(path.get(0), "a");
    assertEquals(path.get(1), "b");
    assertEquals(path.get(2), "c");
    assertEquals(path.get(3), "d");

    nodePath1 = "/a/b/c/d/";
    path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 4);
    assertEquals(path.get(0), "a");
    assertEquals(path.get(1), "b");
    assertEquals(path.get(2), "c");
    assertEquals(path.get(3), "d");

    nodePath1 = "a/b/c/d";
    path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 4);
    assertEquals(path.get(0), "a");
    assertEquals(path.get(1), "b");
    assertEquals(path.get(2), "c");
    assertEquals(path.get(3), "d");

    nodePath1 = "a/b/c/d/${NodeName}";
    path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 5);
    assertEquals(path.get(0), "a");
    assertEquals(path.get(1), "b");
    assertEquals(path.get(2), "c");
    assertEquals(path.get(3), "d");
    assertEquals(path.get(4), "${NodeName}");

    nodePath1 = "a/b/c/d/${NodeName}/";
    path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 5);
    assertEquals(path.get(0), "a");
    assertEquals(path.get(1), "b");
    assertEquals(path.get(2), "c");
    assertEquals(path.get(3), "d");
    assertEquals(path.get(4), "${NodeName}");

    nodePath1 = "a/b/c/d/${NodeName:}/";
    path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 5);
    assertEquals(path.get(0), "a");
    assertEquals(path.get(1), "b");
    assertEquals(path.get(2), "c");
    assertEquals(path.get(3), "d");
    assertEquals(path.get(4), "${NodeName:}");

    nodePath1 = "a/b/c/d/${NodeName:aaaaa}/";
    path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 5);
    assertEquals(path.get(0), "a");
    assertEquals(path.get(1), "b");
    assertEquals(path.get(2), "c");
    assertEquals(path.get(3), "d");
    assertEquals(path.get(4), "${NodeName:aaaaa}");

    nodePath1 = "a/b/c/d/${NodeName:aaaaa}/bbbb";
    path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 6);
    assertEquals(path.get(0), "a");
    assertEquals(path.get(1), "b");
    assertEquals(path.get(2), "c");
    assertEquals(path.get(3), "d");
    assertEquals(path.get(4), "${NodeName:aaaaa}");
    assertEquals(path.get(5), "bbbb");

    nodePath1 = "a";
    path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 1);
    assertEquals(path.get(0), "a");

    nodePath1 = "${NodeName}";
    path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 1);
    assertEquals(path.get(0), "${NodeName}");

    nodePath1 = "${NodeName:}";
    path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 1);
    assertEquals(path.get(0), "${NodeName:}");

    nodePath1 = "${NodeName:aaaaa}";
    path = DDFTreeHelper.getPathVector(nodePath1);
    assertEquals(path.size(), 1);
    assertEquals(path.get(0), "${NodeName:aaaaa}");

  }

  public void testDynamicName() {
    String nodePath1 = "./a/b/c/d/";
    String name = DDFTreeHelper.getDynamicName(nodePath1);
    assertNull(name);

    nodePath1 = "/a/b/c/d/";
    name = DDFTreeHelper.getDynamicName(nodePath1);
    assertNull(name);

    nodePath1 = "a/b/c/d";
    name = DDFTreeHelper.getDynamicName(nodePath1);
    assertNull(name);

    nodePath1 = "a/b/c/d/${NodeName}";
    name = DDFTreeHelper.getDynamicName(nodePath1);
    assertNull(name);

    nodePath1 = "a/b/c/d/${NodeName}/";
    name = DDFTreeHelper.getDynamicName(nodePath1);
    assertNull(name);

    nodePath1 = "a/b/c/d/${NodeName:}/";
    name = DDFTreeHelper.getDynamicName(nodePath1);
    assertNull(name);

    nodePath1 = "a/b/c/d/${NodeName:aaaaa}/";
    name = DDFTreeHelper.getDynamicName(nodePath1);
    assertEquals("aaaaa", name);

    nodePath1 = "a/b/c/d/${NodeName:aaaaa}/bbbb";
    name = DDFTreeHelper.getDynamicName(nodePath1);
    assertNull(name);

    nodePath1 = "a";
    name = DDFTreeHelper.getDynamicName(nodePath1);
    assertNull(name);

    nodePath1 = "${NodeName}";
    name = DDFTreeHelper.getDynamicName(nodePath1);
    assertNull(name);

    nodePath1 = "${NodeName:}";
    name = DDFTreeHelper.getDynamicName(nodePath1);
    assertNull(name);

    nodePath1 = "${NodeName:bbbbb}";
    name = DDFTreeHelper.getDynamicName(nodePath1);
    assertEquals("bbbbb", name);

  }

  public void testName() {
    String nodePath1 = "./a/b/c/d/";
    String name = DDFTreeHelper.getName(nodePath1);
    assertEquals("d", name);

    nodePath1 = "/a/b/c/d/";
    name = DDFTreeHelper.getName(nodePath1);
    assertEquals("d", name);

    nodePath1 = "a/b/c/d";
    name = DDFTreeHelper.getName(nodePath1);
    assertEquals("d", name);

    nodePath1 = "a/b/c/d/${NodeName}";
    name = DDFTreeHelper.getName(nodePath1);
    assertNull(name);

    nodePath1 = "a/b/c/d/${NodeName}/";
    name = DDFTreeHelper.getName(nodePath1);
    assertNull(name);

    nodePath1 = "a/b/c/d/${NodeName:}/";
    name = DDFTreeHelper.getName(nodePath1);
    assertNull(name);

    nodePath1 = "a/b/c/d/${NodeName:aaaaa}/";
    name = DDFTreeHelper.getName(nodePath1);
    assertEquals("aaaaa", name);

    nodePath1 = "a/b/c/d/${NodeName:aaaaa}/bbbb";
    name = DDFTreeHelper.getName(nodePath1);
    assertEquals("bbbb", name);

    nodePath1 = "a";
    name = DDFTreeHelper.getName(nodePath1);
    assertEquals("a", name);

    nodePath1 = "${NodeName}";
    name = DDFTreeHelper.getName(nodePath1);
    assertNull(name);

    nodePath1 = "${NodeName:}";
    name = DDFTreeHelper.getName(nodePath1);
    assertNull(name);

    nodePath1 = "${NodeName:bbbbb}";
    name = DDFTreeHelper.getName(nodePath1);
    assertEquals("bbbbb", name);

  }

  public void testParentName() {
    String nodePath1 = ".";
    String path = DDFTreeHelper.getParentPath(nodePath1);
    assertEquals(null, path);

    nodePath1 = "./";
    path = DDFTreeHelper.getParentPath(nodePath1);
    assertEquals(null, path);

    nodePath1 = "./a/";
    path = DDFTreeHelper.getParentPath(nodePath1);
    assertEquals(".", path);

    nodePath1 = "./a";
    path = DDFTreeHelper.getParentPath(nodePath1);
    assertEquals(".", path);

    nodePath1 = "./a/b/c/d/";
    path = DDFTreeHelper.getParentPath(nodePath1);
    assertEquals("./a/b/c", path);

    nodePath1 = "./a/b/c/d";
    path = DDFTreeHelper.getParentPath(nodePath1);
    assertEquals("./a/b/c", path);

    nodePath1 = "./aaaa/bbbb/cccc/dddd/";
    path = DDFTreeHelper.getParentPath(nodePath1);
    assertEquals("./aaaa/bbbb/cccc", path);

    nodePath1 = "./aaaa/bbbb/cccc/dddd";
    path = DDFTreeHelper.getParentPath(nodePath1);
    assertEquals("./aaaa/bbbb/cccc", path);

  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestDDFTreeHelper.class);
  }

}
