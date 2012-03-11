/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestSoftwareBean4ScreenShot.java,v 1.4 2008/06/16 10:11:14 zhao Exp $
  * $Revision: 1.4 $
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

import java.io.InputStream;
import java.util.List;

import com.npower.dm.AllTests;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareLicenseType;
import com.npower.dm.core.SoftwareScreenShot;
import com.npower.dm.core.SoftwareVendor;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:14 $
 */
public class TestSoftwareBean4ScreenShot extends BaseTestSoftwareBean {

  private SoftwareVendor vendor;
  private SoftwareCategory category;

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
      String description = "Mobile software category#1";
      category = setupSoftwareCategory(null, name, description);
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
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    InputStream bytes = new java.io.ByteArrayInputStream(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    SoftwareScreenShot p1 = bean.newScreenShotInstance(s1, bytes, "Screen shot");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwareScreenShot p = bean.getScreenShotByID(p1.getId());
    assertNotNull(p);
    assertEquals("Screen shot", p.getDescription());
    assertNotNull(p.getBinary());
    assertEquals(8, p.getBinary().getBinaryBlob().length());
    
    Software s = p.getSoftware();
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(this.vendor.getId(), s.getVendor().getId());
    assertEquals(this.category.getId(), s.getCategory().getId());
    
    // Delete it
    factory.beginTransaction();
    bean.delete(p);
    factory.commit();
    
    // Testing & checking
    assertTrue(p.getId()> 0);
    SoftwareScreenShot another = bean.getScreenShotByID(p.getId());
    assertNull(another);
    
    factory.release();
    
  }

  public void testUpdate() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    InputStream bytes = new java.io.ByteArrayInputStream(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    SoftwareScreenShot p1 = bean.newScreenShotInstance(s1, bytes, "Screen shot");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwareScreenShot p = bean.getScreenShotByID(p1.getId());
    assertNotNull(p);
    assertEquals("Screen shot", p.getDescription());
    assertNotNull(p.getBinary());
    assertEquals(8, p.getBinary().getBinaryBlob().length());
    
    // Update it
    factory.beginTransaction();
    p.setDescription("Screen shot updated");
    bean.update(p);
    factory.commit();
    
    // Testing & checking
    assertTrue(p.getId()> 0);
    SoftwareScreenShot another = bean.getScreenShotByID(p.getId());
    assertNotNull(another);
    assertEquals("Screen shot updated", another.getDescription());
    
    factory.release();
    
  }

  public void testDelete() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    InputStream bytes = new java.io.ByteArrayInputStream(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    SoftwareScreenShot p1 = bean.newScreenShotInstance(s1, bytes, "Screen shot");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwareScreenShot p = bean.getScreenShotByID(p1.getId());
    assertNotNull(p);
    assertEquals("Screen shot", p.getDescription());
    assertNotNull(p.getBinary());
    assertEquals(8, p.getBinary().getBinaryBlob().length());
    
    Software s = p.getSoftware();
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(this.vendor.getId(), s.getVendor().getId());
    assertEquals(this.category.getId(), s.getCategory().getId());
    
    // Delete it
    factory.beginTransaction();
    bean.delete(p);
    factory.commit();
    
    // Testing & checking
    assertTrue(p.getId()> 0);
    SoftwareScreenShot another = bean.getScreenShotByID(p.getId());
    assertNull(another);
    
    factory.release();
  }
  
  /**
   * @throws Exception
   */
  public void testGetAll() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    // Create root categories
    factory.beginTransaction();
    SoftwareVendor v1 = bean.newVendorInstance("Vendor#1", "Mobile software vendor#1");
    bean.update(v1);
    
    Software s1 = bean.newSoftwareInstance(v1, this.category, "SFT1", "Software#1", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Vendor#1 Software#1");
    bean.update(s1);
    {
      InputStream bytes = new java.io.ByteArrayInputStream(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
      SoftwareScreenShot p = bean.newScreenShotInstance(s1, bytes, "Screen shot");
      bean.update(p);
    }

    SoftwareVendor v2 = bean.newVendorInstance("Vendor#2", "Mobile software vendor#2");
    bean.update(v2);
    
    Software s2 = bean.newSoftwareInstance(v1, this.category, "SFT2", "Software#2", "2.0", SoftwareLicenseType.SHARE);
    s2.setDescription("Vendor#2 Software#2");
    bean.update(s2);
    {
      InputStream bytes = new java.io.ByteArrayInputStream(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
      SoftwareScreenShot p = bean.newScreenShotInstance(s1, bytes, "Screen shot");
      bean.update(p);
    }

    SoftwareVendor v3 = bean.newVendorInstance("Vendor#3", "Mobile software vendor#3");
    bean.update(v3);
    
    Software s3 = bean.newSoftwareInstance(v1, this.category, "SFT3", "Software#3", "3.0", SoftwareLicenseType.FREE);
    s3.setDescription("Vendor#3 Software#3");
    bean.update(s3);
    {
      InputStream bytes = new java.io.ByteArrayInputStream(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
      SoftwareScreenShot p = bean.newScreenShotInstance(s1, bytes, "Screen shot");
      bean.update(p);
    }

    factory.commit();
    
    // Testing Root categories
    List<SoftwareScreenShot> pkgs = bean.getAllOfScreenShots();
    assertEquals(3, pkgs.size());
    
    factory.release();
  }

}
