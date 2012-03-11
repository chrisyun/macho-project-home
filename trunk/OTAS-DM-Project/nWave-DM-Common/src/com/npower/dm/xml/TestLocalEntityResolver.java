/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/xml/TestLocalEntityResolver.java,v 1.1 2008/12/15 03:24:14 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/15 03:24:14 $
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
package com.npower.dm.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.npower.dm.AllTests;
import com.npower.dm.core.DDFTree;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.ManagementBeanFactory;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/12/15 03:24:14 $
 */
public class TestLocalEntityResolver extends TestCase {

  private static final String BASE_DIR = AllTests.BASE_DIR;
  /**
   * Profile Template XML File for Motorola Testing
   */
  private static final String FILENAME_BAD_DOC_TYPE = BASE_DIR + "/metadata/ddf/test/test.motorola.bad.doctype.xml";

  /**
   * @param name
   */
  public TestLocalEntityResolver(String name) {
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
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      InputStream in = new FileInputStream(new File(FILENAME_BAD_DOC_TYPE));
      DDFTree tree = bean.parseDDFTree(in);
      in.close();
      assertNotNull(tree);
    } catch (Exception e) {
      throw e;
    } finally {
      beanFactory.release();
    }
  }

}
