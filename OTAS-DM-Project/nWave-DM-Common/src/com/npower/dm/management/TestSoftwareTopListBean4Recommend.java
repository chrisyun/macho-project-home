/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestSoftwareTopListBean4Recommend.java,v 1.10 2008/09/03 08:37:35 zhao Exp $
 * $Revision: 1.10 $
 * $Date: 2008/09/03 08:37:35 $
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

package com.npower.dm.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.npower.dm.AllTests;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.10 $ $Date: 2008/09/03 08:37:35 $
 */

public class TestSoftwareTopListBean4Recommend extends BaseTestSoftwareBean {
  private static final int NUMBER_OF_CHILDREN_PER_CATEGORY = 3;
  private static final int NUMBER_OF_MODELS = 2;
  private static final int NUMBER_OF_SOFTWARE_PER_CATEGORY = 10;
  
  /**
   * @param name
   */
  public TestSoftwareTopListBean4Recommend(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    // 清除数据
    cleanSoftwareCategories();
    clearSoftwareVendors();
    clearModels();
    
    int numberOfChildrenPerCategory = NUMBER_OF_CHILDREN_PER_CATEGORY;
    int numberOfModel = NUMBER_OF_MODELS;
    int numberOfSoftwarePerCategory = NUMBER_OF_SOFTWARE_PER_CATEGORY;
    setupSampleData(numberOfChildrenPerCategory, numberOfSoftwarePerCategory, numberOfModel);
   
  }

  
  protected void tearDown() throws Exception {
    super.tearDown();
  }
 
  /**
   * @param softwareBean
   * @param categoryName
   * @return
   */
  private SoftwareCategory getCategoryByName(SoftwareBean softwareBean, String categoryName) throws Exception {
    for (SoftwareCategory category: softwareBean.getAllOfCategories()) {
        if (category.getName().equals(categoryName)) {
           return category;
        }
    }
    return null;
  }
  
  /**
   * 创建数据:
   * 增加SoftwareCategory: C_1的软件, 模拟设置得优先级:
   * ----------------------------------------------
   * 软件分类  软件名称        优先级              
   * -------------------------------
   * C_1     S1_IN_C_1        0             
   * C_1     S2_IN_C_1        1             
   * C_1     S3_IN_C_1        2              
   * C_1     S4_IN_C_1        3              
   * C_1     S5_IN_C_1        4        
   * -------------------------------
   * 
   * </pre>
   * 
   * @throws Exception
   */
  
  
  public void testSetRecommendedPriority1() throws Exception {
    //checking
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();    
    SoftwareTopListBean bean = factory.createSoftwareTopListBean();    
    SoftwareBean softwareBean = factory.createSoftwareBean();
    
    // Create Original Data
    {
      Software software = softwareBean.getSoftwareByExternalID("S1_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 0);
      factory.commit();
      assertEquals(0, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S2_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 1);
      factory.commit();
      assertEquals(1, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S3_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 2);
      factory.commit();
      assertEquals(2, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S4_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 3);
      factory.commit();
      assertEquals(3, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S5_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 4);
      factory.commit();
      assertEquals(4, bean.getRecommendedPriority(software, category));
    }
    
    // Modify priority
    {
      Software software = softwareBean.getSoftwareByExternalID("S5_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 0);
      factory.commit();
      int priority = bean.getRecommendedPriority(software, category);
      assertEquals(0, priority);
      assertTrue(bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S5_IN_C_1"), getCategoryByName(softwareBean, "C_1"))
          <
          bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S1_IN_C_1"), getCategoryByName(softwareBean, "C_1")));
      assertTrue(bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S1_IN_C_1"), getCategoryByName(softwareBean, "C_1"))
          <
          bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S2_IN_C_1"), getCategoryByName(softwareBean, "C_1")));
      assertTrue(bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S2_IN_C_1"), getCategoryByName(softwareBean, "C_1"))
          <
          bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S3_IN_C_1"), getCategoryByName(softwareBean, "C_1")));
      assertTrue(bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S3_IN_C_1"), getCategoryByName(softwareBean, "C_1"))
          <
          bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S4_IN_C_1"), getCategoryByName(softwareBean, "C_1")));
    }
    
  }

  public void testSetRecommendedPriority2() throws Exception {
    //checking
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean bean = factory.createSoftwareTopListBean();
    SoftwareBean softwareBean = factory.createSoftwareBean();
    
    // Create Original Data
    {
      Software software = softwareBean.getSoftwareByExternalID("S1_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 0);
      factory.commit();
      assertEquals(0, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S2_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 1);
      factory.commit();
      assertEquals(1, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S3_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 2);
      factory.commit();
      assertEquals(2, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S4_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 3);
      factory.commit();
      assertEquals(3, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S5_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 4);
      factory.commit();
      assertEquals(4, bean.getRecommendedPriority(software, category));
    }
    
    // Modify priority
    {
      Software software = softwareBean.getSoftwareByExternalID("S5_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 2);
      factory.commit();
      int priority = bean.getRecommendedPriority(software, category);
      assertEquals(2, priority);
      int priority_S_1 = bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S1_IN_C_1"), getCategoryByName(softwareBean, "C_1"));
      int priority_S_2 = bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S2_IN_C_1"), getCategoryByName(softwareBean, "C_1"));
      int priority_S_3 = bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S3_IN_C_1"), getCategoryByName(softwareBean, "C_1"));
      int priority_S_4 = bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S4_IN_C_1"), getCategoryByName(softwareBean, "C_1"));
      int priority_S_5 = bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S5_IN_C_1"), getCategoryByName(softwareBean, "C_1"));
      assertTrue(priority_S_1 < priority_S_2);
      assertTrue(priority_S_2 < priority_S_5);
      assertTrue(priority_S_5 < priority_S_3);
      assertTrue(priority_S_3 < priority_S_4);
    }
    
  }

  public void testSetRecommendedPriority3() throws Exception {
    //checking
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();    
    SoftwareTopListBean bean = factory.createSoftwareTopListBean();    
    SoftwareBean softwareBean = factory.createSoftwareBean();
    
    // Create Original Data
    {
      Software software = softwareBean.getSoftwareByExternalID("S1_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 0);
      factory.commit();
      assertEquals(0, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S2_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 1);
      factory.commit();
      assertEquals(1, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S3_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 2);
      factory.commit();
      assertEquals(2, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S4_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 3);
      factory.commit();
      assertEquals(3, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S5_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 4);
      factory.commit();
      assertEquals(4, bean.getRecommendedPriority(software, category));
    }
    
    // Modify priority
    {
      Software software = softwareBean.getSoftwareByExternalID("S5_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, -1);
      factory.commit();
      int priority = bean.getRecommendedPriority(software, category);
      assertEquals(-1, priority);
      
      assertTrue(bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S1_IN_C_1"), getCategoryByName(softwareBean, "C_1"))
          <
          bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S2_IN_C_1"), getCategoryByName(softwareBean, "C_1")));
      assertTrue(bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S2_IN_C_1"), getCategoryByName(softwareBean, "C_1"))
          <
          bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S3_IN_C_1"), getCategoryByName(softwareBean, "C_1")));
      assertTrue(bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S3_IN_C_1"), getCategoryByName(softwareBean, "C_1"))
          <
          bean.getRecommendedPriority(softwareBean.getSoftwareByExternalID("S4_IN_C_1"), getCategoryByName(softwareBean, "C_1")));
    }
    
    {
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      Collection<Software> result = bean.getRecommendedSoftwares(category);
      List<Software> items = new ArrayList<Software>(result);
      assertEquals(4, items.size());
      assertEquals("S1_IN_C_1", items.get(0).getName());
    }
    
  }

  
  public void testGetRecommendedSoftwares() throws Exception {
    //checking
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean bean = factory.createSoftwareTopListBean();
    SoftwareBean softwareBean = factory.createSoftwareBean();
    
    // Create Original Data
    {
      Software software = softwareBean.getSoftwareByExternalID("S1_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 0);
      factory.commit();
      assertEquals(0, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S2_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 1);
      factory.commit();
      assertEquals(1, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S3_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 2);
      factory.commit();
      assertEquals(2, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S4_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 3);
      factory.commit();
      assertEquals(3, bean.getRecommendedPriority(software, category));
    }
    {
      Software software = softwareBean.getSoftwareByExternalID("S5_IN_C_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 4);
      factory.commit();
      assertEquals(4, bean.getRecommendedPriority(software, category));
    }
    
    {
      Software software = softwareBean.getSoftwareByExternalID("S1_IN_C_1_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 0);
      factory.commit();
      assertEquals(0, bean.getRecommendedPriority(software, category));
    }
    
    {
      Software software = softwareBean.getSoftwareByExternalID("S2_IN_C_1_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 1);
      factory.commit();
      assertEquals(1, bean.getRecommendedPriority(software, category));
    }
    
    {
      Software software = softwareBean.getSoftwareByExternalID("S3_IN_C_1_1");
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1_1");
      factory.beginTransaction();
      bean.setRecommendedPriority(software, category, 2);
      factory.commit();
      assertEquals(2, bean.getRecommendedPriority(software, category));
    }
    
    {
      Software software = softwareBean.getSoftwareByExternalID("S1_IN_C_3");      
      factory.beginTransaction();
      bean.setRecommendedPriority(software, null, 0);
      factory.commit();
      assertEquals(0, bean.getRecommendedPriority(software, null));
    }
    
    {
      Software software = softwareBean.getSoftwareByExternalID("S2_IN_C_2");      
      factory.beginTransaction();
      bean.setRecommendedPriority(software, null, 1);
      factory.commit();
      assertEquals(1, bean.getRecommendedPriority(software, null));
    }
    
    {
      SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
      Collection<Software> result = bean.getRecommendedSoftwares(category);
      List<Software> items = new ArrayList<Software>(result);
      assertEquals(5, items.size());
      assertEquals("S1_IN_C_1", items.get(0).getName());
    }
    
    {
      SoftwareCategory category = getCategoryByName(softwareBean, "C_2");
      Collection<Software> result = bean.getRecommendedSoftwares(category);
      List<Software> items = new ArrayList<Software>(result);
      assertEquals(0, items.size());
    }
    
  }
  
  /**
   * Test Case for SoftwareBean.getCategoryByPath()
   * @throws Exception
   */
  public void testGetCategoryByPath() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean softwareBean = factory.createSoftwareBean();
        
    
    {
      SoftwareCategory category = softwareBean.getCategoryByPath("/C_1");
      assertNotNull(category);
      assertEquals("C_1", category.getName());
    }
    {
      SoftwareCategory category = softwareBean.getCategoryByPath("/C_1/");
      assertNotNull(category);
      assertEquals("C_1", category.getName());
    }
    {
      SoftwareCategory category = softwareBean.getCategoryByPath("/C_1/C_1_1");
      assertNotNull(category);
      assertEquals("C_1_1", category.getName());
    }
    {
      SoftwareCategory category = softwareBean.getCategoryByPath("/C_1/C_1_1/");
      assertNotNull(category);
      assertEquals("C_1_1", category.getName());
    }
    {
      SoftwareCategory category = softwareBean.getCategoryByPath("/C_1/NONE/");
      assertNull(category);
    }
   
    {
      SoftwareCategory category = softwareBean.getCategoryByPath("/C_1/C_1_1/NONE/");
      assertNull(category);
    }
    {
      SoftwareCategory category = softwareBean.getCategoryByPath("/");
      assertNull(category);
    }
    {
      SoftwareCategory category = softwareBean.getCategoryByPath("");
      assertNull(category);
    }
    {
      SoftwareCategory category = softwareBean.getCategoryByPath(null);
      assertNull(category);
    }
    {
      SoftwareCategory category = softwareBean.getCategoryByPath("/NONE");
      assertNull(category);
    }
    {
      SoftwareCategory category = softwareBean.getCategoryByPath("NONE");
      assertNull(category);
    }
    {
      SoftwareCategory category = softwareBean.getCategoryByPath("C_1/");
      assertNotNull(category);
    }
  }
  

}
