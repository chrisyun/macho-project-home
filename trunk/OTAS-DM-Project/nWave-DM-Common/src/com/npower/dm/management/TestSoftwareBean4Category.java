/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestSoftwareBean4Category.java,v 1.2 2008/06/16 10:11:14 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2008/06/16 10:11:14 $
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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.npower.dm.AllTests;
import com.npower.dm.core.SoftwareCategory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/16 10:11:14 $
 */
public class TestSoftwareBean4Category extends BaseTestSoftwareBean {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    cleanSoftwareCategories();
    
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testAdd() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    SoftwareCategory c1 = bean.newCategoryInstance(null, "Category#1", "Mobile software category#1");
    bean.update(c1);
    factory.commit();
    
    // Testing & checking
    assertTrue(c1.getId()> 0);
    SoftwareCategory c = bean.getCategoryByID(c1.getId());
    assertNotNull(c);
    assertEquals("Category#1", c.getName());
    assertEquals("Mobile software category#1", c.getDescription());
    assertEquals(null, c.getParent());
    
    // Delete it
    factory.beginTransaction();
    bean.delete(c);
    factory.commit();
    
    // Testing & checking
    assertTrue(c.getId()> 0);
    SoftwareCategory another = bean.getCategoryByID(c.getId());
    assertNull(another);
    
    factory.release();
    
  }

  public void testUpdate() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    SoftwareCategory c1 = bean.newCategoryInstance(null, "Category#1", "Mobile software category#1");
    bean.update(c1);
    factory.commit();
    
    // Testing & checking
    assertTrue(c1.getId()> 0);
    SoftwareCategory c = bean.getCategoryByID(c1.getId());
    assertNotNull(c);
    assertEquals("Category#1", c.getName());
    assertEquals("Mobile software category#1", c.getDescription());
    assertEquals(null, c.getParent());
    
    // Modify it
    factory.beginTransaction();
    c.setName("Category#2");
    c.setDescription("Mobile software category#2");
    bean.update(c);
    factory.commit();
    
    // Testing & checking
    SoftwareCategory c2 = bean.getCategoryByID(c.getId());
    assertNotNull(c2);
    assertEquals("Category#2", c2.getName());
    assertEquals("Mobile software category#2", c2.getDescription());
    assertEquals(null, c2.getParent());

    // Delete it
    factory.beginTransaction();
    bean.delete(c);
    factory.commit();

    // Testing & checking
    assertTrue(c.getId()> 0);
    SoftwareCategory another = bean.getCategoryByID(c.getId());
    assertNull(another);
    
    factory.release();
  }

  public void testDelete() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    SoftwareCategory c1 = bean.newCategoryInstance(null, "Category#1", "Mobile software category#1");
    bean.update(c1);
    factory.commit();
    
    // Testing & checking
    assertTrue(c1.getId()> 0);
    SoftwareCategory c = bean.getCategoryByID(c1.getId());
    assertNotNull(c);
    assertEquals("Category#1", c.getName());
    assertEquals("Mobile software category#1", c.getDescription());
    assertEquals(null, c.getParent());
    
    // Delete it
    factory.beginTransaction();
    bean.delete(c);
    factory.commit();
    
    // Testing & checking
    assertTrue(c.getId()> 0);
    SoftwareCategory another = bean.getCategoryByID(c.getId());
    assertNull(another);
    
    factory.release();
  }
  
  /**
   * @throws Exception
   */
  public void testGetAllOfRootCategories() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    // Create root categories
    factory.beginTransaction();
    SoftwareCategory c1 = bean.newCategoryInstance(null, "Category#1", "Mobile software category#1");
    bean.update(c1);
    
    SoftwareCategory c2 = bean.newCategoryInstance(null, "Category#2", "Mobile software category#2");
    bean.update(c2);
    
    SoftwareCategory c3 = bean.newCategoryInstance(null, "Category#2", "Mobile software category#2");
    bean.update(c3);
    factory.commit();
    
    // Testing Root categories
    List<SoftwareCategory> roots = bean.getAllOfRootCategories();
    assertEquals(3, roots.size());
    
    factory.release();
  }

  /**
   * @throws Exception
   */
  public void testGetAllOfCategories() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    // Create root categories
    factory.beginTransaction();
    SoftwareCategory c1 = bean.newCategoryInstance(null, "Category#1", "Mobile software category#1");
    bean.update(c1);
    
    SoftwareCategory s11 = bean.newCategoryInstance(c1, "Sub Category#1-1", "Mobile software sub category#1-1");
    bean.update(s11);
    
    SoftwareCategory s12 = bean.newCategoryInstance(c1, "Sub Category#1-2", "Mobile software sub category#1-2");
    bean.update(s12);
    
    SoftwareCategory s13 = bean.newCategoryInstance(c1, "Sub Category#1-3", "Mobile software sub category#1-3");
    bean.update(s13);
    
    SoftwareCategory c2 = bean.newCategoryInstance(null, "Category#2", "Mobile software category#2");
    bean.update(c2);
    
    SoftwareCategory s21 = bean.newCategoryInstance(c2, "Sub Category#2-1", "Mobile software sub category#2-1");
    bean.update(s21);
    
    SoftwareCategory s22 = bean.newCategoryInstance(c2, "Sub Category#2-2", "Mobile software sub category#2-2");
    bean.update(s22);
    
    SoftwareCategory c3 = bean.newCategoryInstance(null, "Category#2", "Mobile software category#2");
    bean.update(c3);
    
    SoftwareCategory s31 = bean.newCategoryInstance(c3, "Sub Category#3-1", "Mobile software sub category#3-1");
    bean.update(s31);
    
    factory.commit();
    
    // Testing All of categories
    List<SoftwareCategory> all = bean.getAllOfCategories();
    assertEquals(9, all.size());
    
    // Testing Root categories
    List<SoftwareCategory> roots = bean.getAllOfRootCategories();
    assertEquals(3, roots.size());
    
    factory.release();
  }

  /**
   * @throws Exception
   */
  public void testRelations() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    // Create root categories
    factory.beginTransaction();
    SoftwareCategory c1 = bean.newCategoryInstance(null, "Category#1", "Mobile software category#1");
    bean.update(c1);
    
    SoftwareCategory s11 = bean.newCategoryInstance(c1, "Sub Category#1-1", "Mobile software sub category#1-1");
    bean.update(s11);
    
    SoftwareCategory s12 = bean.newCategoryInstance(c1, "Sub Category#1-2", "Mobile software sub category#1-2");
    bean.update(s12);
    
    SoftwareCategory s13 = bean.newCategoryInstance(c1, "Sub Category#1-3", "Mobile software sub category#1-3");
    bean.update(s13);
    
    SoftwareCategory c2 = bean.newCategoryInstance(null, "Category#2", "Mobile software category#2");
    bean.update(c2);
    
    SoftwareCategory s21 = bean.newCategoryInstance(c2, "Sub Category#2-1", "Mobile software sub category#2-1");
    bean.update(s21);
    
    SoftwareCategory s22 = bean.newCategoryInstance(c2, "Sub Category#2-2", "Mobile software sub category#2-2");
    bean.update(s22);
    
    SoftwareCategory c3 = bean.newCategoryInstance(null, "Category#3", "Mobile software category#3");
    bean.update(c3);
    
    SoftwareCategory s31 = bean.newCategoryInstance(c3, "Sub Category#3-1", "Mobile software sub category#3-1");
    bean.update(s31);
    
    factory.commit();
    
    // Testing
    List<SoftwareCategory> roots = bean.getAllOfRootCategories();
    assertEquals(3, roots.size());
    assertEquals(null, roots.get(0).getParent());
    assertEquals("Category#1", roots.get(0).getName());
    assertEquals("Mobile software category#1", roots.get(0).getDescription());
    List<SoftwareCategory> children = new ArrayList<SoftwareCategory>(new TreeSet<SoftwareCategory>(roots.get(0).getChildren()));
    assertEquals(3, children.size());
    assertEquals(roots.get(0).getId(), children.get(0).getParent().getId());
    assertEquals(roots.get(0).getId(), children.get(1).getParent().getId());
    assertEquals(roots.get(0).getId(), children.get(2).getParent().getId());
    assertEquals("Sub Category#1-1", children.get(0).getName());
    assertEquals("Sub Category#1-2", children.get(1).getName());
    assertEquals("Sub Category#1-3", children.get(2).getName());
    assertEquals("Mobile software sub category#1-1", children.get(0).getDescription());
    assertEquals("Mobile software sub category#1-2", children.get(1).getDescription());
    assertEquals("Mobile software sub category#1-3", children.get(2).getDescription());
    
    assertEquals(null, roots.get(1).getParent());
    assertEquals("Category#2", roots.get(1).getName());
    assertEquals("Mobile software category#2", roots.get(1).getDescription());
    children = new ArrayList<SoftwareCategory>(new TreeSet<SoftwareCategory>(roots.get(1).getChildren()));
    assertEquals(2, children.size());
    assertEquals(roots.get(1).getId(), children.get(0).getParent().getId());
    assertEquals(roots.get(1).getId(), children.get(1).getParent().getId());
    assertEquals("Sub Category#2-1", children.get(0).getName());
    assertEquals("Sub Category#2-2", children.get(1).getName());
    assertEquals("Mobile software sub category#2-1", children.get(0).getDescription());
    assertEquals("Mobile software sub category#2-2", children.get(1).getDescription());
    
    assertEquals(null, roots.get(2).getParent());
    assertEquals("Category#3", roots.get(2).getName());
    assertEquals("Mobile software category#3", roots.get(2).getDescription());
    children = new ArrayList<SoftwareCategory>(new TreeSet<SoftwareCategory>(roots.get(2).getChildren()));
    assertEquals(1, children.size());
    assertEquals(roots.get(2).getId(), children.get(0).getParent().getId());
    assertEquals("Sub Category#3-1", children.get(0).getName());
    assertEquals("Mobile software sub category#3-1", children.get(0).getDescription());
    
    factory.release();
  }

}
