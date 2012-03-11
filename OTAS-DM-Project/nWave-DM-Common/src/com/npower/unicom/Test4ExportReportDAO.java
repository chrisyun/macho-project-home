/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/Test4ExportReportDAO.java,v 1.2 2008/11/04 09:32:51 chenlei Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/04 09:32:51 $
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

package com.npower.unicom;

import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/11/04 09:32:51 $
 */

public class Test4ExportReportDAO extends TestCase {

  /**
   * @param name
   */
  public Test4ExportReportDAO(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  
  public void testCase1() throws DMException {
    System.setProperty("otas.dm.home", "D:/OTASDM");
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ExportReportDAO dao = new ExportReportDAO(factory,null);
    List<VCPCDRLog> myList = dao.getReport4Time("2008-10-31", "2008-10-31");
    
    VCPCDRLog a = myList.get(0);
    assertEquals("13060136955", a.getPhonenumber());  
    assertEquals("NOKIA", a.getManufacturer());
    assertEquals("N80", a.getModel());
  }
  
  
  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

}
