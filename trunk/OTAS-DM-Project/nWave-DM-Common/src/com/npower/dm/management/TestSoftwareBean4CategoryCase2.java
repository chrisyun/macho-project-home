/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestSoftwareBean4CategoryCase2.java,v 1.3 2008/08/27 03:58:55 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/08/27 03:58:55 $
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
package com.npower.dm.management;

import java.util.Set;

import com.npower.dm.AllTests;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;




/**
 *          
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/08/27 03:58:55 $
 */
public class TestSoftwareBean4CategoryCase2 extends BaseTestSoftwareBean {

  private static final int NUMBER_OF_CHILDREN_PER_CATEGORY = 3;
  private static final int NUMBER_OF_MODELS = 2;
  private static final int NUMBER_OF_SOFTWARE_PER_CATEGORY = 10;

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    // Çå³ýÊý¾Ý
    cleanSoftwareCategories();
    clearSoftwareVendors();
    clearModels();
    
    int numberOfChildrenPerCategory = NUMBER_OF_CHILDREN_PER_CATEGORY;
    int numberOfModel = NUMBER_OF_MODELS;
    int numberOfSoftwarePerCategory = NUMBER_OF_SOFTWARE_PER_CATEGORY;
    setupSampleData(numberOfChildrenPerCategory, numberOfSoftwarePerCategory, numberOfModel);
    
  }

  /**
   * @param softwareBean
   * @param name
   * @return
   * @throws DMException
   */
  private SoftwareCategory getCategoryByName(SoftwareBean softwareBean, String name) throws DMException {
    for (SoftwareCategory c: softwareBean.getAllOfCategories()) {
        if (c.getName().equals(name)) {
           return c;
        }
    }
    return null;
  }
  

  public void testGetSoftwaresFromCategoryEntity1() throws Exception{

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    
    Set<Software> softwares = category.getSoftwares();
    assertFalse(softwares.isEmpty());
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S1_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S2_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S3_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S4_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S5_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S6_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S7_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S8_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S9_IN_C_1")));
  }

  public void testGetSoftwaresFromCategoryEntity2() throws Exception{

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    
    Set<Software> softwares = category.getAllOfSoftwares();
    assertFalse(softwares.isEmpty());
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S1_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S2_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S3_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S4_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S5_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S6_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S7_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S8_IN_C_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S9_IN_C_1")));

    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S1_IN_C_1_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S2_IN_C_1_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S3_IN_C_1_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S4_IN_C_1_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S5_IN_C_1_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S6_IN_C_1_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S7_IN_C_1_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S8_IN_C_1_1")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S9_IN_C_1_1")));

    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S1_IN_C_1_2")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S2_IN_C_1_2")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S3_IN_C_1_2")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S4_IN_C_1_2")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S5_IN_C_1_2")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S6_IN_C_1_2")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S7_IN_C_1_2")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S8_IN_C_1_2")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S9_IN_C_1_2")));

    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S1_IN_C_1_3")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S2_IN_C_1_3")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S3_IN_C_1_3")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S4_IN_C_1_3")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S5_IN_C_1_3")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S6_IN_C_1_3")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S7_IN_C_1_3")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S8_IN_C_1_3")));
    assertTrue(softwares.contains(softwareBean.getSoftwareByExternalID("S9_IN_C_1_3")));

    assertFalse(softwares.contains(softwareBean.getSoftwareByExternalID("S1_IN_C_2_3")));
    assertFalse(softwares.contains(softwareBean.getSoftwareByExternalID("S2_IN_C_2_3")));
    assertFalse(softwares.contains(softwareBean.getSoftwareByExternalID("S3_IN_C_2_3")));
    assertFalse(softwares.contains(softwareBean.getSoftwareByExternalID("S4_IN_C_2_3")));
    assertFalse(softwares.contains(softwareBean.getSoftwareByExternalID("S5_IN_C_2_3")));
    assertFalse(softwares.contains(softwareBean.getSoftwareByExternalID("S6_IN_C_2_3")));
    assertFalse(softwares.contains(softwareBean.getSoftwareByExternalID("S7_IN_C_2_3")));
    assertFalse(softwares.contains(softwareBean.getSoftwareByExternalID("S8_IN_C_2_3")));
    assertFalse(softwares.contains(softwareBean.getSoftwareByExternalID("S9_IN_C_2_3")));
  }

  public void testGetSoftwaresFromCategoryEntity3() throws Exception{

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    
    Set<SoftwareCategory> descendants = category.getDescendants();
    assertFalse(descendants.isEmpty());
    assertTrue(descendants.contains(softwareBean.getCategoryByPath("/C_1/C_1_1")));
    assertTrue(descendants.contains(softwareBean.getCategoryByPath("/C_1/C_1_2")));
    assertTrue(descendants.contains(softwareBean.getCategoryByPath("/C_1/C_1_3")));
    
    assertFalse(descendants.contains(softwareBean.getCategoryByPath("/C_1")));
    assertFalse(descendants.contains(softwareBean.getCategoryByPath("/C_2")));
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  

}
