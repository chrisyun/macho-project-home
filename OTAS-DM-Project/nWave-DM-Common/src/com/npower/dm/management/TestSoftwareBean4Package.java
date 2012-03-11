/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestSoftwareBean4Package.java,v 1.7 2008/09/10 09:59:42 zhao Exp $
  * $Revision: 1.7 $
  * $Date: 2008/09/10 09:59:42 $
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.npower.dm.AllTests;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelFamily;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareLicenseType;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareVendor;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/09/10 09:59:42 $
 */
public class TestSoftwareBean4Package extends BaseTestSoftwareBean {

  private SoftwareVendor vendor;
  private SoftwareCategory category;
  private Model model_1_family_1;
  private Model model_2_family_2;
  private Model model_3_family_3;
  private Model model_4_family_3;
  private Model model_5_family_3;
  private Model model_6_family_4;

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
    
    {
      String manufacturerExternalID = "Manufacturer#1";
      String modelExternalID = "Model#1";
      model_1_family_1 = setupModel(manufacturerExternalID, modelExternalID, "model.family.1");
    }
    
    {
      String manufacturerExternalID = "Manufacturer#1";
      String modelExternalID = "Model#2";
      model_2_family_2 = setupModel(manufacturerExternalID, modelExternalID, "model.family.2");
    }
    
    {
      String manufacturerExternalID = "Manufacturer#1";
      String modelExternalID = "Model#3";
      model_3_family_3 = setupModel(manufacturerExternalID, modelExternalID, "model.family.3");
    }
    
    {
      String manufacturerExternalID = "Manufacturer#1";
      String modelExternalID = "Model#4";
      model_4_family_3 = setupModel(manufacturerExternalID, modelExternalID, "model.family.3");
    }
    
    {
      String manufacturerExternalID = "Manufacturer#1";
      String modelExternalID = "Model#5";
      model_5_family_3 = setupModel(manufacturerExternalID, modelExternalID, "model.family.3");
    }
    
    {
      String manufacturerExternalID = "Manufacturer#1";
      String modelExternalID = "Model#6";
      model_6_family_4 = setupModel(manufacturerExternalID, modelExternalID, "model.family.4");
    }
    
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    try {
        List<Manufacturer> all = bean.getAllManufacturers();
        factory.beginTransaction();
        for (Manufacturer manufacturer: all) {
            bean.delete(manufacturer);
        }
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  public void testAdd1() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    Set<Model> setOfModel = new HashSet<Model>();
    setOfModel.add(model_1_family_1);
    SoftwarePackage p1 = bean.newPackageInstance(s1, null, null, setOfModel, "http://www.download.com/aaa/1.sisx");
    p1.setLanguage("zh");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwarePackage p = bean.getPackageByID(p1.getId());
    assertNotNull(p);
    assertEquals("zh", p.getLanguage());
    assertEquals("http://www.download.com/aaa/1.sisx", p.getUrl());
    Set<Model> targetModels = bean.getTargetModels(p);
    assertEquals(1, targetModels.size());
    assertEquals(model_1_family_1.getID(), targetModels.iterator().next().getID());
    
    Software s = p.getSoftware();
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(this.vendor.getId(), s.getVendor().getId());
    assertEquals(this.category.getId(), s.getCategory().getId());
    
    // Test Get by model and software
    SoftwarePackage p2 = bean.getPackage(s, model_1_family_1);
    assertNotNull(p2);
    assertEquals(p1.getId(), p2.getId());
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
    SoftwarePackage another = bean.getPackageByID(p.getId());
    assertNull(another);
    
    factory.release();
    
  }

  public void testAdd2() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    InputStream bytes = new java.io.ByteArrayInputStream(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    Set<Model> setOfModel = new HashSet<Model>();
    setOfModel.add(model_1_family_1);
    SoftwarePackage p1 = bean.newPackageInstance(s1, null, null, setOfModel, "x-epoc/x-sisx-app", bytes, "1.sisx");
    p1.setLanguage("zh");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwarePackage p = bean.getPackageByID(p1.getId());
    assertNotNull(p);
    assertEquals("zh", p.getLanguage());
    assertEquals("x-epoc/x-sisx-app", p.getMimeType());
    assertEquals("1.sisx", p.getBlobFilename());
    assertNotNull(p.getBinary());
    assertEquals(8, p.getBinary().getBinaryBlob().length());
    assertEquals(null, p.getUrl());
    
    Set<Model> targetModels = bean.getTargetModels(p);
    assertEquals(1, targetModels.size());
    assertEquals(model_1_family_1.getID(), targetModels.iterator().next().getID());
    
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
    SoftwarePackage another = bean.getPackageByID(p.getId());
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
    
    Set<Model> setOfModel = new HashSet<Model>();
    setOfModel.add(model_1_family_1);
    SoftwarePackage p1 = bean.newPackageInstance(s1, null, null, setOfModel, "http://www.download.com/aaa/1.sisx");
    p1.setLanguage("zh");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwarePackage p = bean.getPackageByID(p1.getId());
    assertNotNull(p);
    assertEquals("zh", p.getLanguage());
    assertEquals("http://www.download.com/aaa/1.sisx", p.getUrl());

    Set<Model> targetModels = bean.getTargetModels(p);
    assertEquals(1, targetModels.size());
    assertEquals(model_1_family_1.getID(), targetModels.iterator().next().getID());
    
    // Update it
    factory.beginTransaction();
    p.setLanguage("en");
    bean.update(p);
    factory.commit();
    
    // Testing & checking
    assertTrue(p.getId()> 0);
    SoftwarePackage another = bean.getPackageByID(p.getId());
    assertNotNull(another);
    assertEquals("en", another.getLanguage());
    
    factory.release();
    
  }

  public void testDelete() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    Set<Model> setOfModel = new HashSet<Model>();
    setOfModel.add(model_1_family_1);
    SoftwarePackage p1 = bean.newPackageInstance(s1, null, null, setOfModel, "http://www.download.com/aaa/1.sisx");
    p1.setLanguage("zh");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwarePackage p = bean.getPackageByID(p1.getId());
    assertNotNull(p);
    assertEquals("zh", p.getLanguage());
    assertEquals("http://www.download.com/aaa/1.sisx", p.getUrl());

    Set<Model> targetModels = bean.getTargetModels(p);
    assertEquals(1, targetModels.size());
    assertEquals(model_1_family_1.getID(), targetModels.iterator().next().getID());
    
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
    SoftwarePackage another = bean.getPackageByID(p.getId());
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
    
    Software s1 = bean.newSoftwareInstance(v1, this.category, "SFT1", "Software#1", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Vendor#1 Software#1");
    bean.update(s1);
    {
      Set<Model> setOfModel = new HashSet<Model>();
      setOfModel.add(model_1_family_1);
      SoftwarePackage p = bean.newPackageInstance(s1, null, null, setOfModel, "http://www.download.com/aaa/1.sisx");
      p.setLanguage("zh");
      bean.update(p);
    }

    SoftwareVendor v2 = bean.newVendorInstance("Vendor#2", "Mobile software vendor#2");
    bean.update(v2);
    
    Software s2 = bean.newSoftwareInstance(v1, this.category, "SFT2", "Software#2", "2.0", SoftwareLicenseType.SHARE);
    s2.setDescription("Vendor#2 Software#2");
    bean.update(s2);
    {
      Set<Model> setOfModel = new HashSet<Model>();
      setOfModel.add(model_1_family_1);
      SoftwarePackage p = bean.newPackageInstance(s2, null, null, setOfModel, "http://www.download.com/aaa/1.sisx");
      p.setLanguage("zh");
      bean.update(p);
    }

    SoftwareVendor v3 = bean.newVendorInstance("Vendor#3", "Mobile software vendor#3");
    bean.update(v3);
    
    Software s3 = bean.newSoftwareInstance(v1, this.category, "SFT3", "Software#3", "3.0", SoftwareLicenseType.FREE);
    s3.setDescription("Vendor#3 Software#3");
    bean.update(s3);
    {
      Set<Model> setOfModel = new HashSet<Model>();
      setOfModel.add(model_1_family_1);
      SoftwarePackage p = bean.newPackageInstance(s3, null, null, setOfModel, "http://www.download.com/aaa/1.sisx");
      p.setLanguage("zh");
      bean.update(p);
    }

    factory.commit();
    
    // Testing Root categories
    List<SoftwarePackage> pkgs = bean.getAllOfPackages();
    assertEquals(3, pkgs.size());
    
    factory.release();
  }

  /**
   * 创建目标型号为model1, model2, model3的软件包
   * @throws Exception
   */
  public void testAdd3() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    Set<Model> setOfModel = new HashSet<Model>();
    setOfModel.add(model_1_family_1);
    setOfModel.add(model_2_family_2);
    setOfModel.add(model_3_family_3);
    SoftwarePackage p1 = bean.newPackageInstance(s1, null, null, setOfModel, "http://www.download.com/aaa/1.sisx");
    p1.setLanguage("zh");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwarePackage p = bean.getPackageByID(p1.getId());
    assertNotNull(p);
    assertEquals("zh", p.getLanguage());
    assertEquals("http://www.download.com/aaa/1.sisx", p.getUrl());
    Set<Model> targetModels = bean.getTargetModels(p);
    assertEquals(3, targetModels.size());
    assertTrue(targetModels.contains(model_1_family_1));
    assertTrue(targetModels.contains(model_2_family_2));
    assertTrue(targetModels.contains(model_3_family_3));
    
    Software s = p.getSoftware();
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(this.vendor.getId(), s.getVendor().getId());
    assertEquals(this.category.getId(), s.getCategory().getId());
    
    // Test Get by model and software
    SoftwarePackage p2 = bean.getPackage(s, model_1_family_1);
    assertNotNull(p2);
    assertEquals(p1.getId(), p2.getId());
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
    SoftwarePackage another = bean.getPackageByID(p.getId());
    assertNull(another);
    
    factory.release();
    
  }

  /**
   * 创建目标型号为model1, model2, model3和model.family.3的软件包
   * @throws Exception
   */
  public void testAdd4() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    ModelBean modelBean = factory.createModelBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    Set<Model> setOfModel = new HashSet<Model>();
    setOfModel.add(model_1_family_1);
    setOfModel.add(model_2_family_2);
    setOfModel.add(model_3_family_3);
    
    Set<ModelFamily> setOfFamily = new HashSet<ModelFamily>();
    setOfFamily.add(modelBean.getModelFamilyByExtID("model.family.3"));
    
    SoftwarePackage p1 = bean.newPackageInstance(s1, null, setOfFamily, setOfModel, "http://www.download.com/aaa/1.sisx");
    p1.setLanguage("zh");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwarePackage p = bean.getPackageByID(p1.getId());
    assertNotNull(p);
    assertEquals("zh", p.getLanguage());
    assertEquals("http://www.download.com/aaa/1.sisx", p.getUrl());
    Set<Model> targetModels = bean.getTargetModels(p);
    assertEquals(5, targetModels.size());
    Set<Long> modelIDSet = new HashSet<Long>();
    for (Model m: targetModels) {
      modelIDSet.add(new Long(m.getID()));
    }
    assertTrue(modelIDSet.contains(new Long(model_1_family_1.getID())));
    assertTrue(modelIDSet.contains(new Long(model_2_family_2.getID())));
    assertTrue(modelIDSet.contains(new Long(model_3_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_4_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_5_family_3.getID())));
    
    Software s = p.getSoftware();
    assertNotNull(s);
    assertEquals("MSN", s.getExternalId());
    assertEquals("Windows Mobile Messager", s.getName());
    assertEquals("1.0", s.getVersion());
    assertEquals(SoftwareLicenseType.FREE.toString(), s.getLicenseType());
    assertEquals("Microsoft Winodws Mobile Messager", s.getDescription());
    assertEquals(this.vendor.getId(), s.getVendor().getId());
    assertEquals(this.category.getId(), s.getCategory().getId());
    
    // Test Get by model and software
    SoftwarePackage p2 = bean.getPackage(s, model_1_family_1);
    assertNotNull(p2);
    assertEquals(p1.getId(), p2.getId());
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
    SoftwarePackage another = bean.getPackageByID(p.getId());
    assertNull(another);
    
    factory.release();
    
  }

  /**
   * Test assign(...)
   * @throws Exception
   */
  public void testAssign1() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    ModelBean modelBean = factory.createModelBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    Set<Model> setOfModel = new HashSet<Model>();
    setOfModel.add(model_1_family_1);
    setOfModel.add(model_2_family_2);
    setOfModel.add(model_3_family_3);
    
    SoftwarePackage p1 = bean.newPackageInstance(s1, null, null, setOfModel, "http://www.download.com/aaa/1.sisx");
    p1.setLanguage("zh");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwarePackage p = bean.getPackageByID(p1.getId());
    assertNotNull(p);
    assertEquals("zh", p.getLanguage());
    assertEquals("http://www.download.com/aaa/1.sisx", p.getUrl());
    Set<Model> targetModels = bean.getTargetModels(p);
    assertEquals(3, targetModels.size());
    Set<Long> modelIDSet = new HashSet<Long>();
    for (Model m: targetModels) {
      modelIDSet.add(new Long(m.getID()));
    }
    assertTrue(modelIDSet.contains(new Long(model_1_family_1.getID())));
    assertTrue(modelIDSet.contains(new Long(model_2_family_2.getID())));
    assertTrue(modelIDSet.contains(new Long(model_3_family_3.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_4_family_3.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_5_family_3.getID())));
    
    // Assign model family 3
    factory.beginTransaction();
    Set<ModelFamily> setOfFamily = new HashSet<ModelFamily>();
    setOfFamily.add(modelBean.getModelFamilyByExtID("model.family.3"));
    bean.assign(null, null, setOfFamily, p1);
    factory.commit();
    
    // Testing and checking
    targetModels = bean.getTargetModels(p);
    assertEquals(5, targetModels.size());
    modelIDSet = new HashSet<Long>();
    for (Model m: targetModels) {
      modelIDSet.add(new Long(m.getID()));
    }
    assertTrue(modelIDSet.contains(new Long(model_1_family_1.getID())));
    assertTrue(modelIDSet.contains(new Long(model_2_family_2.getID())));
    assertTrue(modelIDSet.contains(new Long(model_3_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_4_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_5_family_3.getID())));
    
    // Test 
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_1_family_1);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_2_family_2);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_3_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_4_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_5_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_6_family_4);
      assertNull(p2);
    }
    
    
    // Delete it
    factory.beginTransaction();
    bean.delete(p);
    factory.commit();
    
    // Testing & checking
    assertTrue(p.getId()> 0);
    SoftwarePackage another = bean.getPackageByID(p.getId());
    assertNull(another);
    
    factory.release();
    
  }

  /**
   * Test assign(...)
   * @throws Exception
   */
  public void testAssign2() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    ModelBean modelBean = factory.createModelBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    Set<Model> setOfModel = new HashSet<Model>();
    setOfModel.add(model_1_family_1);
    setOfModel.add(model_2_family_2);
    setOfModel.add(model_3_family_3);
    
    SoftwarePackage p1 = bean.newPackageInstance(s1, null, null, setOfModel, "http://www.download.com/aaa/1.sisx");
    p1.setLanguage("zh");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwarePackage p = bean.getPackageByID(p1.getId());
    assertNotNull(p);
    assertEquals("zh", p.getLanguage());
    assertEquals("http://www.download.com/aaa/1.sisx", p.getUrl());
    Set<Model> targetModels = bean.getTargetModels(p);
    assertEquals(3, targetModels.size());
    Set<Long> modelIDSet = new HashSet<Long>();
    for (Model m: targetModels) {
      modelIDSet.add(new Long(m.getID()));
    }
    assertTrue(modelIDSet.contains(new Long(model_1_family_1.getID())));
    assertTrue(modelIDSet.contains(new Long(model_2_family_2.getID())));
    assertTrue(modelIDSet.contains(new Long(model_3_family_3.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_4_family_3.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_5_family_3.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_6_family_4.getID())));
    
    // Test 
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_1_family_1);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_2_family_2);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_3_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_4_family_3);
      assertNull(p2);
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_5_family_3);
      assertNull(p2);
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_6_family_4);
      assertNull(p2);
    }
    
    // Assign model family 3
    factory.beginTransaction();
    Set<ModelFamily> setOfFamily = new HashSet<ModelFamily>();
    setOfFamily.add(modelBean.getModelFamilyByExtID("model.family.3"));
    setOfModel = new HashSet<Model>();
    setOfModel.add(model_6_family_4);
    bean.assign(null, setOfModel, setOfFamily, p1);
    factory.commit();
    
    // Testing and checking
    targetModels = bean.getTargetModels(p);
    assertEquals(6, targetModels.size());
    modelIDSet = new HashSet<Long>();
    for (Model m: targetModels) {
      modelIDSet.add(new Long(m.getID()));
    }
    assertTrue(modelIDSet.contains(new Long(model_1_family_1.getID())));
    assertTrue(modelIDSet.contains(new Long(model_2_family_2.getID())));
    assertTrue(modelIDSet.contains(new Long(model_3_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_4_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_5_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_6_family_4.getID())));
    
    // Test 
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_1_family_1);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_2_family_2);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_3_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_4_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_5_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_6_family_4);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    
    // Delete it
    factory.beginTransaction();
    bean.delete(p);
    factory.commit();
    
    // Testing & checking
    assertTrue(p.getId()> 0);
    SoftwarePackage another = bean.getPackageByID(p.getId());
    assertNull(another);
    
    factory.release();
    
  }

  /**
   * Test reassign(...)
   * @throws Exception
   */
  public void testReAssign1() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    ModelBean modelBean = factory.createModelBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    Set<Model> setOfModel = new HashSet<Model>();
    setOfModel.add(model_1_family_1);
    setOfModel.add(model_2_family_2);
    setOfModel.add(model_3_family_3);
    
    SoftwarePackage p1 = bean.newPackageInstance(s1, null, null, setOfModel, "http://www.download.com/aaa/1.sisx");
    p1.setLanguage("zh");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwarePackage p = bean.getPackageByID(p1.getId());
    assertNotNull(p);
    assertEquals("zh", p.getLanguage());
    assertEquals("http://www.download.com/aaa/1.sisx", p.getUrl());
    Set<Model> targetModels = bean.getTargetModels(p);
    assertEquals(3, targetModels.size());
    Set<Long> modelIDSet = new HashSet<Long>();
    for (Model m: targetModels) {
      modelIDSet.add(new Long(m.getID()));
    }
    assertTrue(modelIDSet.contains(new Long(model_1_family_1.getID())));
    assertTrue(modelIDSet.contains(new Long(model_2_family_2.getID())));
    assertTrue(modelIDSet.contains(new Long(model_3_family_3.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_4_family_3.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_5_family_3.getID())));
    
    // Test 
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_1_family_1);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_2_family_2);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_3_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_4_family_3);
      assertNull(p2);
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_5_family_3);
      assertNull(p2);
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_6_family_4);
      assertNull(p2);
    }

    // ReAssign model family 3
    factory.beginTransaction();
    Set<ModelFamily> setOfFamily = new HashSet<ModelFamily>();
    setOfFamily.add(modelBean.getModelFamilyByExtID("model.family.3"));
    bean.reassign(null, null, setOfFamily, p1);
    factory.commit();
    
    // Testing and checking
    targetModels = bean.getTargetModels(p);
    assertEquals(3, targetModels.size());
    modelIDSet = new HashSet<Long>();
    for (Model m: targetModels) {
      modelIDSet.add(new Long(m.getID()));
    }
    assertTrue(!modelIDSet.contains(new Long(model_1_family_1.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_2_family_2.getID())));
    assertTrue(modelIDSet.contains(new Long(model_3_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_4_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_5_family_3.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_6_family_4.getID())));
    
    // Test 
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_1_family_1);
      assertNull(p2);
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_2_family_2);
      assertNull(p2);
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_3_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_4_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_5_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_6_family_4);
      assertNull(p2);
    }

    // Delete it
    factory.beginTransaction();
    bean.delete(p);
    factory.commit();
    
    // Testing & checking
    assertTrue(p.getId()> 0);
    SoftwarePackage another = bean.getPackageByID(p.getId());
    assertNull(another);
    
    factory.release();
    
  }
  
  /**
   * Test reassign(...)
   * @throws Exception
   */
  public void testReAssign2() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    ModelBean modelBean = factory.createModelBean();
    
    factory.beginTransaction();
    Software s1 = bean.newSoftwareInstance(this.vendor, this.category, "MSN", "Windows Mobile Messager", "1.0", SoftwareLicenseType.FREE);
    s1.setDescription("Microsoft Winodws Mobile Messager");
    bean.update(s1);
    
    Set<Model> setOfModel = new HashSet<Model>();
    setOfModel.add(model_1_family_1);
    setOfModel.add(model_2_family_2);
    setOfModel.add(model_3_family_3);
    
    SoftwarePackage p1 = bean.newPackageInstance(s1, null, null, setOfModel, "http://www.download.com/aaa/1.sisx");
    p1.setLanguage("zh");
    bean.update(p1);
    factory.commit();
    
    // Testing & checking
    assertTrue(p1.getId()> 0);
    SoftwarePackage p = bean.getPackageByID(p1.getId());
    assertNotNull(p);
    assertEquals("zh", p.getLanguage());
    assertEquals("http://www.download.com/aaa/1.sisx", p.getUrl());
    Set<Model> targetModels = bean.getTargetModels(p);
    assertEquals(3, targetModels.size());
    Set<Long> modelIDSet = new HashSet<Long>();
    for (Model m: targetModels) {
      modelIDSet.add(new Long(m.getID()));
    }
    assertTrue(modelIDSet.contains(new Long(model_1_family_1.getID())));
    assertTrue(modelIDSet.contains(new Long(model_2_family_2.getID())));
    assertTrue(modelIDSet.contains(new Long(model_3_family_3.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_4_family_3.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_5_family_3.getID())));
    
    // Test 
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_1_family_1);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_2_family_2);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_3_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_4_family_3);
      assertNull(p2);
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_5_family_3);
      assertNull(p2);
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_6_family_4);
      assertNull(p2);
    }

    // ReAssign model family 3
    factory.beginTransaction();
    Set<ModelFamily> setOfFamily = new HashSet<ModelFamily>();
    setOfFamily.add(modelBean.getModelFamilyByExtID("model.family.3"));
    setOfModel = new HashSet<Model>();
    setOfModel.add(model_6_family_4);
    bean.reassign(null, setOfModel, setOfFamily, p1);
    factory.commit();
    
    // Testing and checking
    targetModels = bean.getTargetModels(p);
    assertEquals(4, targetModels.size());
    modelIDSet = new HashSet<Long>();
    for (Model m: targetModels) {
      modelIDSet.add(new Long(m.getID()));
    }
    assertTrue(!modelIDSet.contains(new Long(model_1_family_1.getID())));
    assertTrue(!modelIDSet.contains(new Long(model_2_family_2.getID())));
    assertTrue(modelIDSet.contains(new Long(model_3_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_4_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_5_family_3.getID())));
    assertTrue(modelIDSet.contains(new Long(model_6_family_4.getID())));
    
    // Test 
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_1_family_1);
      assertNull(p2);
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_2_family_2);
      assertNull(p2);
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_3_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_4_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_5_family_3);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }
    {
      SoftwarePackage p2 = bean.getPackage(p1.getSoftware(), model_6_family_4);
      assertNotNull(p2);
      assertEquals(p1.getId(), p2.getId());
    }

    // Delete it
    factory.beginTransaction();
    bean.delete(p);
    factory.commit();
    
    // Testing & checking
    assertTrue(p.getId()> 0);
    SoftwarePackage another = bean.getPackageByID(p.getId());
    assertNull(another);
    
    factory.release();
    
  }
  
}
