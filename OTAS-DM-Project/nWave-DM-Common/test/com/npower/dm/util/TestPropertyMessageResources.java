/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/util/TestPropertyMessageResources.java,v 1.2 2008/01/11 04:23:47 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2008/01/11 04:23:47 $
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

import java.util.Locale;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/01/11 04:23:47 $
 */
public class TestPropertyMessageResources extends TestCase {

  /**
   * @param name
   */
  public TestPropertyMessageResources(String name) {
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
    MessageResourcesFactory factory = MessageResourcesFactory.createFactory();
    assertEquals(PropertyMessageResourcesFactory.class.getName(), factory.getClass().getName());
    MessageResources resources = factory.createResources("com/npower/dm/decorator/test");
    
    {
      String v = resources.getMessage(Locale.ENGLISH, "meta.key1.name.label");
      assertEquals("Default_en", v);
    }
    {
      String v = resources.getMessage(Locale.CHINA, "meta.key1.name.label");
      assertEquals("Default_zh_CN", v);
    }
    {
      String v = resources.getMessage(Locale.CHINA, "meta.key1.NO_EXISTS.label");
      assertNull(v);
    }
  }

}
