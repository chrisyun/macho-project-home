/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/Test11111.java,v 1.1 2008/11/28 10:26:17 chenlei Exp $
 * $Revision: 1.1 $
 * $Date: 2008/11/28 10:26:17 $
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



import com.npower.setup.core.SetupBootstrap;
import com.npower.setup.core.SetupException;

import junit.framework.TestCase;

/**
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2008/11/28 10:26:17 $
 */

public class Test11111 extends TestCase {
  SetupBootstrap setupBootstrap = null;
  /**
   * @param name
   */
  public Test11111(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    System.setProperty("otas.dm.home", "D:/OTASDM");
    setupBootstrap = new SetupBootstrap();
    setupBootstrap.setFilename("D:/OTASDM/install/dmsetup.xml");
  }

  public void testSoftwareCategoryTask() throws SetupException {
    setupBootstrap.execute();
    
  }
  
  
  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

}
