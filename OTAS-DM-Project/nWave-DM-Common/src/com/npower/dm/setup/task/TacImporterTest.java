/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/TacImporterTest.java,v 1.1 2008/10/31 10:03:04 zhao Exp $
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

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/10/31 10:03:04 $
 */
public class TacImporterTest extends TestCase {

  private ManagementBeanFactory factory;

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    factory = AllTests.getManagementBeanFactory();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    this.factory.release();
  }

  /**
   * Test method for {@link com.npower.dm.setup.task.TacImporter#process()}.
   */
  public void testProcess() throws Exception {
    File file = new File(AllTests.BASE_DIR, "metadata/setup/tac/www.STADTAUS.com_TW06_IMEI_csv_v0810.csv");
    TacImporter importer = new TacImporter(file, factory);
    importer.process();
  }

}
