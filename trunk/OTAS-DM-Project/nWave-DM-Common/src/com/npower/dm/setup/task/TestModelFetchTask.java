/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/TestModelFetchTask.java,v 1.1 2008/09/05 03:24:40 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/09/05 03:24:40 $
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

import java.util.List;

import junit.framework.TestCase;

/**
 * @author Liu AiHui
 * @version $Revision: 1.1 $ $Date: 2008/09/05 03:24:40 $
 */

public class TestModelFetchTask extends TestCase {

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
  
  public void testLoadModelFetchItems() throws Exception{
    ModelFetchTask task = new ModelFetchTask();
    List<ModelFetchItem> modelfetchs = task.loadModelFetchItems("D:/Zhao/MyWorkspace/OTAS-DM-Tools/test/fetch.xml");
//    List<ModelFetchItem> modelfetchs = task.loadModelFetchItems("d:/tmp/alcatel.xml");
    assertNotNull(modelfetchs);   
  }
  
}

