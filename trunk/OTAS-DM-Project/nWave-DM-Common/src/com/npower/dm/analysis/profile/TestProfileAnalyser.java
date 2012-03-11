/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/analysis/profile/TestProfileAnalyser.java,v 1.2 2009/01/21 07:53:10 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2009/01/21 07:53:10 $
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
package com.npower.dm.analysis.profile;

import java.sql.Clob;
import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.hibernate.entity.ProfileParameterEntity;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2009/01/21 07:53:10 $
 */
public class TestProfileAnalyser extends TestCase {

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
  
  /**
   * @throws Exception
   */
  public void testCase1() throws Exception {
    String deviceExtId = "IMEI:004400701707588";
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileAnalyser analyser = new ProfileAnalyser(factory);
    try {
        List<ProfileAssignment> configs = analyser.analyse(null, deviceExtId);
        
        // ¼ì²é½á¹û
        assertEquals(40, configs.size());
        for (ProfileAssignment assignment: configs) {
            System.out.println("==================================================");
            System.out.println(assignment.getProfileConfig().getName() + "[" + assignment.getProfileConfig().getProfileTemplate().getName() + "]");
            System.out.println("--------------------------------------------------");
            System.out.println("Node Path: " + assignment.getProfileRootNodePath());
            for (ProfileParameterEntity parameter: assignment.getProfileParameters()) {
                String value = null;
                Clob clob = parameter.getRawData();
                if (clob != null) {
                   //value = clob.getSubString(0, (int) clob.length());
                }
                System.out.println(parameter.getDmTreePath() + ": " + value);
            }
            System.out.println("==================================================");
        }
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

}
