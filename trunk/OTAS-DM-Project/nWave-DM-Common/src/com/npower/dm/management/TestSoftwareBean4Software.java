/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestSoftwareBean4Software.java,v 1.6 2008/08/29 02:55:10 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2008/08/29 02:55:10 $
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

import com.npower.dm.AllTests;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareLicenseType;
import com.npower.dm.core.SoftwareVendor;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/08/29 02:55:10 $
 */
public class TestSoftwareBean4Software extends BaseTestSoftwareBean {

  private SoftwareVendor vendor;
  private SoftwareCategory category1, category2, category3;

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    cleanSoftwareCategories();
    clearSoftwareVendors();
    
    {
      String name = "Vendor#1";
      String description = "Mobile software vendor#1";
      vendor = setupSoftwareVendor(name, description);
    }
    
    {
      String name = "Category#1";
      String description = "Mobile software category1#1";
      category1 = setupSoftwareCategory(null, name, description);
    }
    
    {
      String name = "Category#2";
      String description = "Mobile software category1#2";
      category2 = setupSoftwareCategory(null, name, description);
    }
    
    {
      String name = "Category#3";
      String description = "Mobile software category1#3";
      category3 = setupSoftwareCategory(null, name, description);
    }
    
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
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category1, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    factory.commit();
    
    // Testing & checking
    assertTrue(s1.getId()> 0);
    Software s = bean.getSoftwareByID(s1.getId());
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(this.vendor.getId(), s.getVendor().getId());
    assertEquals(this.category1.getId(), s.getCategory().getId());
    
    // Delete it
    factory.beginTransaction();
    bean.delete(s);
    factory.commit();
    
    // Testing & checking
    assertTrue(s.getId()> 0);
    Software another = bean.getSoftwareByID(s.getId());
    assertNull(another);
    
    factory.release();
    
  }

  public void testUpdate() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category1, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    factory.commit();
    
    // Testing & checking
    assertTrue(s1.getId()> 0);
    Software s = bean.getSoftwareByID(s1.getId());
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(this.vendor.getId(), s.getVendor().getId());
    assertEquals(this.category1.getId(), s.getCategory().getId());
    
    // Update it
    factory.beginTransaction();
    s.setVersion("2.0");
    factory.commit();
    
    // Testing & checking
    assertTrue(s.getId()> 0);
    Software another = bean.getSoftwareByID(s.getId());
    assertNotNull(another);
    assertEquals("2.0", another.getVersion());
    
    factory.release();
    
  }

  public void testCase4Bug373() throws Exception {
    long softwareId = 0;
    {
      ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
      SoftwareBean bean = factory.createSoftwareBean();
      
      factory.beginTransaction();
      Software s1 = bean.newSoftwareInstance(this.vendor, this.category1, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
      s1.setDescription("Microsoft Winodws Mobile Messager");
      bean.update(s1);
      factory.commit();
      
      softwareId = s1.getId();
      
      // Testing & checking
      assertTrue(s1.getId()> 0);
      Software s = bean.getSoftwareByID(s1.getId());
      assertNotNull(s);
      assertEquals("MSN", s.getExternalId());
      assertEquals("Windows Mobile Messager", s.getName());
      assertEquals("1.0", s.getVersion());
      assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
      assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
      assertEquals(this.vendor.getId(), s.getVendor().getId());
      assertEquals(this.category1.getId(), s.getCategory().getId());
      
      factory.release();
    }
    {
      ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
      SoftwareBean bean = factory.createSoftwareBean();
      Software s = bean.getSoftwareByID(softwareId);
      SoftwareCategory category = bean.getCategoryByID(s.getCategory().getId());
      assertNotNull(s);
      
      factory.beginTransaction();
      s.setExternalId("New ID for MSN");
      s.setName("New Name for MSN");
      s.setDescription("New Description for Microsoft Winodws Mobile Messager");
      bean.update(s);
      bean.update(s, category);
      factory.commit();
      
      // Testing & checking
      Software s1 = bean.getSoftwareByID(s.getId());
      assertNotNull(s1);
      assertEquals("New ID for MSN", s1.getExternalId());
      assertEquals("New Name for MSN", s1.getName());
      assertEquals("1.0", s1.getVersion());
      assertEquals(SoftwareLicenseType.FREE.toString(), s1.getLicenseType());
      assertEquals("New Description for Microsoft Winodws Mobile Messager", s1.getDescription());
      assertEquals(this.vendor.getId(), s1.getVendor().getId());
      assertEquals(this.category1.getId(), s1.getCategory().getId());
      
      factory.release();
    }
    
  }

  public void testDelete() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category1, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    factory.commit();
    
    // Testing & checking
    assertTrue(s1.getId()> 0);
    Software s = bean.getSoftwareByID(s1.getId());
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(this.vendor.getId(), s.getVendor().getId());
    assertEquals(this.category1.getId(), s.getCategory().getId());
    
    // Delete it
    factory.beginTransaction();
    bean.delete(s);
    factory.commit();
    
    // Testing & checking
    assertTrue(s.getId()> 0);
    Software another = bean.getSoftwareByID(s.getId());
    assertNull(another);
    
    factory.release();
    
  }
  
  /**
   * @throws Exception
   */
  public void testGetAllOfSoftwares() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    // Create root categories
    factory.beginTransaction();
    SoftwareVendor v1 = bean.newVendorInstance("Vendor#1", "Mobile software vendor#1");
    bean.update(v1);
    
    Software s1 = bean.newSoftwareInstance(v1, this.category1, "SFT1", "Software#1", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Vendor#1 Software#1");
    bean.update(s1);

    SoftwareVendor v2 = bean.newVendorInstance("Vendor#2", "Mobile software vendor#2");
    bean.update(v2);
    
    Software s2 = bean.newSoftwareInstance(v1, this.category1, "SFT2", "Software#2", "2.0", SoftwareLicenseType.SHARE);
    s2.setDescription("Vendor#2 Software#2");
    bean.update(s2);

    SoftwareVendor v3 = bean.newVendorInstance("Vendor#3", "Mobile software vendor#3");
    bean.update(v3);
    
    Software s3 = bean.newSoftwareInstance(v1, this.category1, "SFT3", "Software#3", "3.0", SoftwareLicenseType.FREE);
    s3.setDescription("Vendor#3 Software#3");
    bean.update(s3);

    factory.commit();
    
    // Testing Root categories
    List<Software> roots = bean.getAllOfSoftwares();
    assertEquals(3, roots.size());
    
    factory.release();
  }

  public void testUpdateCategoriesCase1() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    // Create software
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category1, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    factory.commit();
    
    // Testing & checking
    assertTrue(s1.getId()> 0);
    Software s = bean.getSoftwareByID(s1.getId());
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(this.vendor.getId(), s.getVendor().getId());
    assertEquals(this.category1.getId(), s.getCategory().getId());
    assertEquals(1, s.getCategoryies().size());
    assertTrue(s.getCategoryies().contains(this.category1));

    // Update it
    factory.beginTransaction();
    bean.update(s1, this.category2);
    factory.commit();
    
    // Testing & checking
    assertTrue(s.getId()> 0);
    Software another = bean.getSoftwareByID(s.getId());
    assertNotNull(another);
    assertEquals(this.category2.getId(), s.getCategory().getId());
    assertEquals(1, s.getCategoryies().size());
    assertTrue(s.getCategoryies().contains(this.category2));
    
    factory.release();
    
  }

  public void testUpdateCategoriesCase2() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    // Create software
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category2, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    factory.commit();
    
    // Testing & checking
    assertTrue(s1.getId()> 0);
    Software s = bean.getSoftwareByID(s1.getId());
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(this.vendor.getId(), s.getVendor().getId());
    assertEquals(this.category2.getId(), s.getCategory().getId());
    
    // Update it
    factory.beginTransaction();
    List<SoftwareCategory> categories = new ArrayList<SoftwareCategory>();
    categories.add(this.category3);
    categories.add(this.category2);
    categories.add(this.category1);
    bean.update(s1, categories);
    factory.commit();
    
    // Testing & checking
    assertTrue(s.getId()> 0);
    Software another = bean.getSoftwareByID(s.getId());
    assertNotNull(another);
    assertEquals(this.category3.getId(), s.getCategory().getId());
    assertEquals(3, s.getCategoryies().size());
    assertTrue(s.getCategoryies().contains(this.category1));
    assertTrue(s.getCategoryies().contains(this.category2));
    assertTrue(s.getCategoryies().contains(this.category3));
    
    factory.release();
    
  }

  public void testUpdateCategoriesCase3() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    List<SoftwareCategory> categories = new ArrayList<SoftwareCategory>();
    categories.add(this.category3);
    categories.add(this.category2);
    categories.add(this.category1);

    // Create software
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, categories, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    factory.commit();
    
    // Testing & checking
    assertTrue(s1.getId()> 0);
    Software s = bean.getSoftwareByID(s1.getId());
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(this.vendor.getId(), s.getVendor().getId());
    assertEquals(this.category3.getId(), s.getCategory().getId());
    assertEquals(3, s.getCategoryies().size());
    assertTrue(s.getCategoryies().contains(this.category1));
    assertTrue(s.getCategoryies().contains(this.category2));
    assertTrue(s.getCategoryies().contains(this.category3));
    
    // Update it
    factory.beginTransaction();
    categories.add(this.category1);
    bean.update(s1, this.category1);
    factory.commit();
    
    // Testing & checking
    assertTrue(s.getId()> 0);
    Software another = bean.getSoftwareByID(s.getId());
    assertNotNull(another);
    assertEquals(this.category1.getId(), s.getCategory().getId());
    assertEquals(1, s.getCategoryies().size());
    assertTrue(s.getCategoryies().contains(this.category1));
    
    factory.release();
    
  }

}
