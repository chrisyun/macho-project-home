/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/setup/task/TestModelFamilyManager.java,v 1.1 2008/09/05 03:24:40 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/09/05 03:24:40 $
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
package com.npower.dm.setup.task;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/05 03:24:40 $
 */
public class TestModelFamilyManager extends TestCase {

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
  
  public void testMerge() throws Exception {
    ModelFamilyManager manager = ModelFamilyManager.getInstance();
 
    {
      ModelFamilyItem family = new ModelFamilyItem();
      family.setExternalID("S60.3rd");
      family.setOmaDmVersion("1.1");
      family.setNokiaOtaVersion("7.0");
      family.addDdfFile("ddf.1.S60.3rd.xml");
      family.addDdfFile("ddf.2.S60.3rd.xml");
      
      manager.addModelFamily(family);
    }
    
    {
      ModelFamilyItem family = new ModelFamilyItem();
      family.setExternalID("S60.3rd.fp1");
      family.setParentID("S60.3rd");
      family.setOmaDmVersion("1.1.2");
      family.setNokiaOtaVersion("7.0");
      family.addDdfFile("ddf.1.S60.3rd.fp1.xml");
      family.addDdfFile("ddf.2.S60.3rd.fp1.xml");
      
      manager.addModelFamily(family);
    }
    
    {
      ModelItem item = new ModelItem();
      item.setFamilyID("S60.3rd");
      item.setName("6681");
      item.addTac("12345678");
      item.addTac("22345678");
      
      manager.merge(item);
      
      assertEquals("6681", item.getName());
      assertEquals("1.1", item.getOmaDmVersion());
      assertEquals("7.0", item.getNokiaOtaVersion());
      assertEquals(2, item.getDdfFiles().size());
      assertEquals("ddf.1.S60.3rd.xml", item.getDdfFiles().get(0));
      assertEquals("ddf.2.S60.3rd.xml", item.getDdfFiles().get(1));
      assertEquals(2, item.getTacs().size());
      assertEquals("12345678", item.getTacs().get(0));
      assertEquals("22345678", item.getTacs().get(1));
    }
    
    {
      ModelItem item = new ModelItem();
      item.setFamilyID("S60.3rd.fp1");
      item.setName("6681");
      item.addTac("32345678");
      item.addTac("42345678");
      
      manager.merge(item);
      
      assertEquals("6681", item.getName());
      assertEquals("1.1.2", item.getOmaDmVersion());
      assertEquals("7.0", item.getNokiaOtaVersion());
      assertEquals(2, item.getDdfFiles().size());
      assertEquals("ddf.1.S60.3rd.fp1.xml", item.getDdfFiles().get(0));
      assertEquals("ddf.2.S60.3rd.fp1.xml", item.getDdfFiles().get(1));
      assertEquals(2, item.getTacs().size());
      assertEquals("32345678", item.getTacs().get(0));
      assertEquals("42345678", item.getTacs().get(1));
    }
  }
}
