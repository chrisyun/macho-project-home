/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/tools/TestPackageCheckerImpl.java,v 1.3 2008/05/08 10:07:39 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/05/08 10:07:39 $
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
package com.npower.dm.msm.tools;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/05/08 10:07:39 $
 */
public class TestPackageCheckerImpl extends TestCase {

  /**
   * @param name
   */
  public TestPackageCheckerImpl(String name) {
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
  
  public void testSimple() throws Exception {
    PackageChecker checker = new PackageCheckerImpl();
    {
      PackageMetaInfo metaInfo = checker.getPackageMetaInfo("http://www.mbook.com.cn/down/mbook_3.20_www_s60_3rd.sis");
      assertNotNull(metaInfo);
    }
    {
      PackageMetaInfo metaInfo = checker.getPackageMetaInfo("http://www.otas.mobi:8080/download/google_map_2008416114254135586.sisx");
      assertNotNull(metaInfo);
    }
    {
      PackageMetaInfo metaInfo = checker.getPackageMetaInfo("http://wap.mapabc.com/wap/dls.jsp");
      assertNotNull(metaInfo);
    }
  }

}
