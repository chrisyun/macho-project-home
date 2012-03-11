/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestSoftwareBean4Vendor.java,v 1.2 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2008/06/16 10:11:15 $
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

import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.SoftwareVendor;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestSoftwareBean4Vendor extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    // Clean all of categories.
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    List<SoftwareVendor> all = bean.getAllOfVendors();
    factory.beginTransaction();
    for (SoftwareVendor vendor: all) {
        bean.delete(vendor);
    }
    factory.commit();
    factory.release();
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
    SoftwareVendor c1 = bean.newVendorInstance("Vendor#1", "Mobile software vendor#1");
    bean.update(c1);
    factory.commit();
    
    // Testing & checking
    assertTrue(c1.getId()> 0);
    SoftwareVendor c = bean.getVendorByID(c1.getId());
    assertNotNull(c);
    assertEquals("Vendor#1", c.getName());
    assertEquals("Mobile software vendor#1", c.getDescription());
    
    // Delete it
    factory.beginTransaction();
    bean.delete(c);
    factory.commit();
    
    // Testing & checking
    assertTrue(c.getId()> 0);
    SoftwareVendor another = bean.getVendorByID(c.getId());
    assertNull(another);
    
    factory.release();
  }

  public void testUpdate() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    SoftwareVendor c1 = bean.newVendorInstance("Vendor#1", "Mobile software vendor#1");
    bean.update(c1);
    factory.commit();
    
    // Testing & checking
    assertTrue(c1.getId()> 0);
    SoftwareVendor c = bean.getVendorByID(c1.getId());
    assertNotNull(c);
    assertEquals("Vendor#1", c.getName());
    assertEquals("Mobile software vendor#1", c.getDescription());
    
    // Modify it
    factory.beginTransaction();
    c.setName("Vendor#2");
    c.setDescription("Mobile software vendor#2");
    bean.update(c);
    factory.commit();
    
    // Testing & checking
    SoftwareVendor c2 = bean.getVendorByID(c.getId());
    assertNotNull(c2);
    assertEquals("Vendor#2", c2.getName());
    assertEquals("Mobile software vendor#2", c2.getDescription());

    // Delete it
    factory.beginTransaction();
    bean.delete(c);
    factory.commit();

    // Testing & checking
    assertTrue(c.getId()> 0);
    SoftwareVendor another = bean.getVendorByID(c.getId());
    assertNull(another);
    
    factory.release();
  }

  public void testDelete() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    SoftwareVendor c1 = bean.newVendorInstance("Vendor#1", "Mobile software vendor#1");
    bean.update(c1);
    factory.commit();
    
    // Testing & checking
    assertTrue(c1.getId()> 0);
    SoftwareVendor c = bean.getVendorByID(c1.getId());
    assertNotNull(c);
    assertEquals("Vendor#1", c.getName());
    assertEquals("Mobile software vendor#1", c.getDescription());
    
    // Delete it
    factory.beginTransaction();
    bean.delete(c);
    factory.commit();
    
    // Testing & checking
    assertTrue(c.getId()> 0);
    SoftwareVendor another = bean.getVendorByID(c.getId());
    assertNull(another);
    
    factory.release();
  }
  
  /**
   * @throws Exception
   */
  public void testGetAllOfVendors() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    // Create root categories
    factory.beginTransaction();
    SoftwareVendor c1 = bean.newVendorInstance("Vendor#1", "Mobile software vendor#1");
    bean.update(c1);
    
    SoftwareVendor c2 = bean.newVendorInstance("Vendor#2", "Mobile software vendor#2");
    bean.update(c2);
    
    SoftwareVendor c3 = bean.newVendorInstance("Vendor#2", "Mobile software vendor#2");
    bean.update(c3);
    factory.commit();
    
    // Testing Root categories
    List<SoftwareVendor> roots = bean.getAllOfVendors();
    assertEquals(3, roots.size());
    
    factory.release();
  }

}
