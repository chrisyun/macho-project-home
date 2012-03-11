/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestModelManagementBean.java,v 1.28 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.28 $
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

import java.io.File;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelFamily;

/**
 * @author Zhao DongLu
 * 
 */
public class TestModelManagementBean extends TestCase {

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    // Clean all model
    this.tearDown();
  }

  /*
   * @see TestCase#tearDown()
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

  public void testManufacturerMethods() throws Exception {

    long now = System.currentTimeMillis();
    String name = "test1_manufacturer_name_" + now;
    String externalID = "test1_manufacturer_" + now;
    String description = "Test1 ManufacturerEntity " + now;

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    try {
      factory.beginTransaction();

      Manufacturer manufacturer = bean.getManufacturerByExternalID(externalID);
      if (manufacturer != null) {
        bean.delete(manufacturer);
      }

      manufacturer = bean.newManufacturerInstance(name, externalID, description);
      bean.update(manufacturer);

      manufacturer = bean.getManufacturerByExternalID(externalID);
      assertEquals(manufacturer.getExternalId(), externalID);
      assertEquals(manufacturer.getName(), name);
      assertEquals(manufacturer.getDescription(), description);

      long id = manufacturer.getID();

      manufacturer = bean.getManufacturerByID("" + id);
      assertEquals(manufacturer.getExternalId(), externalID);
      assertEquals(manufacturer.getName(), name);
      assertEquals(manufacturer.getDescription(), description);

      bean.delete(manufacturer);
      manufacturer = bean.getManufacturerByExternalID(externalID);
      assertNull(manufacturer);

      factory.commit();

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

  }

  /**
   * TODO Test failure! occurs: java.util.ConcurrentModificationException at
   * java.util.HashMap$HashIterator.nextEntry(Unknown Source) at
   * java.util.HashMap$KeyIterator.next(Unknown Source) at
   * org.hibernate.collection.AbstractPersistentCollection$IteratorProxy.next(AbstractPersistentCollection.java:366)
   * at
   * com.npower.dm.management.ModelManagementBeanImpl.delete(ModelManagementBeanImpl.java:439)
   * at
   * com.npower.dm.management.ModelManagementBeanImpl.delete(ModelManagementBeanImpl.java:150)
   * at
   * com.npower.dm.management.TestModelManagementBean.testGetAllManufacturers(TestModelManagementBean.java:104)
   * at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) at
   * sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source) at
   * sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source) at
   * java.lang.reflect.Method.invoke(Unknown Source) at
   * junit.framework.TestCase.runTest(TestCase.java:154) at
   * junit.framework.TestCase.runBare(TestCase.java:127) at
   * junit.framework.TestResult$1.protect(TestResult.java:106) at
   * junit.framework.TestResult.runProtected(TestResult.java:124) at
   * junit.framework.TestResult.run(TestResult.java:109) at
   * junit.framework.TestCase.run(TestCase.java:118) at
   * junit.framework.TestSuite.runTest(TestSuite.java:208) at
   * junit.framework.TestSuite.run(TestSuite.java:203) at
   * junit.framework.TestSuite.runTest(TestSuite.java:208) at
   * junit.framework.TestSuite.run(TestSuite.java:203) at
   * org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:478)
   * at
   * org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:344)
   * at
   * org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:196)
   * 
   * 
   * 
   */
  public void disable_testGetAllManufacturers() throws Exception {

    long now = System.currentTimeMillis();
    String name = "test1_manufacturer_name_" + now;
    String externalID = "test1_manufacturer_" + now;
    String description = "Test1 ManufacturerEntity " + now;

    int total = 20;
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    try {
      factory.beginTransaction();
      // Remove all Manufacturers for Testing
      List<Manufacturer> mans = bean.getAllManufacturers();
      for (int i = 0; i < mans.size(); i++) {
        Manufacturer man = (Manufacturer) mans.get(i);
        bean.delete(man);
      }

      for (int i = 0; i < total; i++) {
        Manufacturer manufacturer = bean.newManufacturerInstance(name + "_" + i, externalID + "_" + i, description
            + "_" + i);
        bean.update(manufacturer);
      }

      mans = bean.getAllManufacturers();
      assertEquals(total, mans.size());

      for (int i = 0; i < total; i++) {
        Manufacturer manufacturer = bean.getManufacturerByExternalID(externalID + "_" + i);
        assertEquals(manufacturer.getName(), name + "_" + i);
        assertEquals(manufacturer.getDescription(), description + "_" + i);
        assertEquals(manufacturer.getExternalId(), externalID + "_" + i);
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

  public void testModelMethods() throws Exception {

    long now = System.currentTimeMillis();

    String manuName = "TestManu4Model";
    String manuExternalID = "TestManu4Model";

    String name = "test_model_name_" + now;
    String manuModelID = "test_manufacturer_" + now;
    boolean supportedDownloadMethods = true;
    boolean omaEnable = true;
    boolean plainProfile = true;
    boolean useEncForOmaMsg = true;
    boolean useNextNoncePerPkg = true;

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    try {
      factory.beginTransaction();

      Manufacturer manufacturer = bean.getManufacturerByExternalID(manuExternalID);
      if (manufacturer != null) {
        bean.delete(manufacturer);
        factory.commit();
        factory.beginTransaction();
      }

      manufacturer = bean.newManufacturerInstance(manuName, manuExternalID, manuName);
      bean.update(manufacturer);

      Model model = bean.newModelInstance(manufacturer, manuModelID, name, supportedDownloadMethods, omaEnable,
          plainProfile, useEncForOmaMsg, useNextNoncePerPkg);

      bean.update(model);

      model = bean.getModelByID(new Long(model.getID()).toString());
      assertEquals(model.getName(), name);
      assertEquals(model.getManufacturerModelId(), manuModelID);
      assertEquals(model.getIsOmaDmEnabled(), omaEnable);
      assertEquals(model.getIsPlainProfile(), plainProfile);
      assertEquals(model.getIsUseEncForOmaMsg(), useEncForOmaMsg);
      assertEquals(model.getIsUseNextNoncePerPkg(), useNextNoncePerPkg);
      assertEquals(model.getSupportedDownloadMethods(), supportedDownloadMethods);
      assertEquals(model.getManufacturer().getName(), manuName);

      assertTrue(model.getManufacturer().getModels().size() > 0);

      model = bean.getModelByManufacturerModelID(manufacturer, manuModelID);
      assertEquals(model.getName(), name);
      assertEquals(model.getManufacturerModelId(), manuModelID);
      assertEquals(model.getIsOmaDmEnabled(), omaEnable);
      assertEquals(model.getIsPlainProfile(), plainProfile);
      assertEquals(model.getIsUseEncForOmaMsg(), useEncForOmaMsg);
      assertEquals(model.getIsUseNextNoncePerPkg(), useNextNoncePerPkg);
      assertEquals(model.getSupportedDownloadMethods(), supportedDownloadMethods);
      assertEquals(model.getManufacturer().getName(), manuName);
      assertTrue(model.getManufacturer().getModels().size() > 0);

      bean.delete(model);
      model = bean.getModelByID(new Long(model.getID()).toString());
      assertNull(model);
      model = bean.getModelByManufacturerModelID(manufacturer, manuExternalID);
      assertNull(model);

      bean.delete(manufacturer);
      manufacturer = bean.getManufacturerByExternalID(manuExternalID);
      assertNull(manufacturer);

      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

  }

  public void testModelTAC() throws Exception {

    long now = System.currentTimeMillis();

    String manuName = "TestManu4Model_" + now;
    String manuExternalID = "TestManu4Model_" + now;

    String name = "test_model_name_" + now;
    String manuModelID = "test_manufacturer_" + now;
    boolean supportedDownloadMethods = true;
    boolean omaEnable = true;
    boolean plainProfile = true;
    boolean useEncForOmaMsg = true;
    boolean useNextNoncePerPkg = true;

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    try {
      factory.beginTransaction();

      Manufacturer manufacturer = bean.newManufacturerInstance(manuName, manuExternalID, manuName);
      bean.update(manufacturer);

      Model model = bean.newModelInstance(manufacturer, manuModelID, name, supportedDownloadMethods, omaEnable,
          plainProfile, useEncForOmaMsg, useNextNoncePerPkg);

      bean.update(model);

      String TACInfo = now + "1";
      bean.addTACInfo(model, TACInfo);

      Model model1 = bean.getModelbyTAC(TACInfo);
      assertEquals(model.getID(), model1.getID());

      Model model2 = bean.getModelbyTAC("1209079886");
      assertNull(model2);

      bean.delete(model);
      bean.delete(manufacturer);

      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

  }

  public void testModelBootstrapProps() throws Exception {

    long now = System.currentTimeMillis();

    String manuName = "TestManu4Model_" + now;
    String manuExternalID = "TestManu4Model_" + now;

    String name = "test2_model_name_" + now;
    String manuModelID = "test2_manufacturer_" + now;
    boolean supportedDownloadMethods = true;
    boolean omaEnable = true;
    boolean plainProfile = true;
    boolean useEncForOmaMsg = true;
    boolean useNextNoncePerPkg = true;

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    try {
      factory.beginTransaction();

      Manufacturer manufacturer = bean.newManufacturerInstance(manuName, manuExternalID, manuName);
      bean.update(manufacturer);

      Model model = bean.newModelInstance(manufacturer, manuModelID, name, supportedDownloadMethods, omaEnable,
          plainProfile, useEncForOmaMsg, useNextNoncePerPkg);

      bean.update(model);

      // Test case # 1
      Properties props = new Properties();
      props.setProperty("prop1", "value1");
      props.setProperty("prop2", "value2");
      props.setProperty("prop3", "value3");

      bean.setDMBootstrapProperties(model, props);
      bean.update(model);

      Model model1 = bean.getModelByID(model.getID() + "");
      Properties props1 = model1.getDMBootstrapProperties();
      assertEquals(props1.getProperty("prop1"), "value1");
      assertEquals(props1.getProperty("prop2"), "value2");
      assertEquals(props1.getProperty("prop3"), "value3");

      // TestCase # 2
      props = new Properties();
      bean.setDMBootstrapProperties(model, props);
      bean.update(model);
      model1 = bean.getModelByID(model.getID() + "");
      props1 = model.getDMBootstrapProperties();
      assertEquals(props1.getProperty("prop1"), null);
      assertEquals(props1.getProperty("prop2"), null);
      assertEquals(props1.getProperty("prop3"), null);

      // Test case # 3
      props = new Properties();
      props.setProperty("prop1", "value1");
      props.setProperty("prop2", "value2");
      props.setProperty("prop3", "value3");
      props.setProperty("prop4", "value4");
      props.setProperty("prop5", "value5");
      props.setProperty("prop6", "value6");

      bean.setDMBootstrapProperties(model, props);
      bean.update(model);

      model1 = bean.getModelByID(model.getID() + "");
      props1 = model.getDMBootstrapProperties();
      assertEquals(props1.getProperty("prop1"), "value1");
      assertEquals(props1.getProperty("prop2"), "value2");
      assertEquals(props1.getProperty("prop3"), "value3");
      assertEquals(props1.getProperty("prop4"), "value4");
      assertEquals(props1.getProperty("prop5"), "value5");
      assertEquals(props1.getProperty("prop6"), "value6");

      bean.delete(model);
      bean.delete(manufacturer);

      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

  }

  public void testModelDMProps() throws Exception {

    long now = System.currentTimeMillis();

    String manuName = "Test3Manu4Model_" + now;
    String manuExternalID = "Test3Manu4Model_" + now;

    String name = "test3_model_name_" + now;
    String manuModelID = "test3_manufacturer_" + now;
    boolean supportedDownloadMethods = true;
    boolean omaEnable = true;
    boolean plainProfile = true;
    boolean useEncForOmaMsg = true;
    boolean useNextNoncePerPkg = true;

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    try {
      factory.beginTransaction();

      Manufacturer manufacturer = bean.newManufacturerInstance(manuName, manuExternalID, manuName);
      bean.update(manufacturer);

      Model model = bean.newModelInstance(manufacturer, manuModelID, name, supportedDownloadMethods, omaEnable,
          plainProfile, useEncForOmaMsg, useNextNoncePerPkg);

      bean.update(model);

      // Test case # 1
      Properties props = new Properties();
      props.setProperty("prop1", "value1");
      props.setProperty("prop2", "value2");
      props.setProperty("prop3", "value3");

      bean.setDMProperties(model, props);
      bean.update(model);

      Model model1 = bean.getModelByID(model.getID() + "");
      Properties props1 = model1.getDMProperties();
      assertEquals(props1.getProperty("prop1"), "value1");
      assertEquals(props1.getProperty("prop2"), "value2");
      assertEquals(props1.getProperty("prop3"), "value3");

      // TestCase # 2
      props = new Properties();
      bean.setDMProperties(model, props);
      bean.update(model);
      model1 = bean.getModelByID(model.getID() + "");
      props1 = model.getDMProperties();
      assertEquals(props1.getProperty("prop1"), null);
      assertEquals(props1.getProperty("prop2"), null);
      assertEquals(props1.getProperty("prop3"), null);

      // Test case # 3
      props = new Properties();
      props.setProperty("prop1", "value1");
      props.setProperty("prop2", "value2");
      props.setProperty("prop3", "value3");
      props.setProperty("prop4", "value4");
      props.setProperty("prop5", "value5");
      props.setProperty("prop6", "value6");

      bean.setDMProperties(model, props);
      bean.update(model);

      model1 = bean.getModelByID(model.getID() + "");
      props1 = model.getDMProperties();
      assertEquals(props1.getProperty("prop1"), "value1");
      assertEquals(props1.getProperty("prop2"), "value2");
      assertEquals(props1.getProperty("prop3"), "value3");
      assertEquals(props1.getProperty("prop4"), "value4");
      assertEquals(props1.getProperty("prop5"), "value5");
      assertEquals(props1.getProperty("prop6"), "value6");

      bean.delete(model);
      bean.delete(manufacturer);

      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testimportAndexportModelTAC() throws Exception {

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();

    File importTAC = new File("D:/zhao/myworkspace/nWave-DM-Common/metadata/TAC/test/importTAC.xml");
    bean.importModelTAC(importTAC);

    try {

      factory.beginTransaction();

      Model model3230 = bean.getModelbyTAC("11111100");
      assertTrue(model3230 != null);
      assertEquals(model3230.getManufacturer().getName(), "NOKIA");
      assertEquals(model3230.getName(), "3230");

      model3230 = bean.getModelbyTAC("22222200");
      assertEquals(model3230.getManufacturer().getName(), "NOKIA");
      assertEquals(model3230.getName(), "3230");

      model3230 = bean.getModelbyTAC("33333300");
      assertEquals(model3230.getManufacturer().getName(), "NOKIA");
      assertEquals(model3230.getName(), "3230");
      
      Model model6670 = bean.getModelbyTAC("44444400");
      assertTrue(model6670 != null);
      assertEquals(model6670.getManufacturer().getName(), "NOKIA");
      assertEquals(model6670.getName(), "6670");

      model6670 = bean.getModelbyTAC("55555500");
      assertEquals(model6670.getManufacturer().getName(), "NOKIA");
      assertEquals(model6670.getName(), "6670");

      Model model6260 = bean.getModelbyTAC("66666600");
      assertTrue(model6260 != null);
      assertEquals(model6260.getManufacturer().getName(), "NOKIA");
      assertEquals(model6260.getName(), "6260");

      Model model7610 = bean.getModelbyTAC("77777700");
      assertTrue(model7610 != null);
      assertEquals(model7610.getManufacturer().getName(), "NOKIA");
      assertEquals(model7610.getName(), "7610");

      model7610 = bean.getModelbyTAC("88888800");
      assertTrue(model7610 != null);
      assertEquals(model7610.getManufacturer().getName(), "NOKIA");
      assertEquals(model7610.getName(), "7610");

      model7610 = bean.getModelbyTAC("99999900");
      assertTrue(model7610 != null);
      assertEquals(model7610.getManufacturer().getName(), "NOKIA");
      assertEquals(model7610.getName(), "7610");

      model7610 = bean.getModelbyTAC("00000000");
      assertTrue(model7610 != null);
      assertEquals(model7610.getManufacturer().getName(), "NOKIA");
      assertEquals(model7610.getName(), "7610");

      Model modelV270 = bean.getModelbyTAC("11122200");
      assertTrue(modelV270 != null);
      assertEquals(modelV270.getManufacturer().getName(), "Motorola");
      assertEquals(modelV270.getName(), "V270");

      modelV270 = bean.getModelbyTAC("11133300");
      assertTrue(modelV270 != null);
      assertEquals(modelV270.getManufacturer().getName(), "Motorola");
      assertEquals(modelV270.getName(), "V270");

      Model modelV280 = bean.getModelbyTAC("11144400");
      assertTrue(modelV280 != null);
      assertEquals(modelV280.getManufacturer().getName(), "Motorola");
      assertEquals(modelV280.getName(), "V280");

      Model modelP910a = bean.getModelbyTAC("00011100");
      assertTrue(modelP910a != null);
      assertEquals(modelP910a.getManufacturer().getName(), "SonyEricsson");
      assertEquals(modelP910a.getName(), "P910a");

      Model modelP900 = bean.getModelbyTAC("00033300");
      assertTrue(modelP900 != null);
      assertEquals(modelP900.getManufacturer().getName(), "SonyEricsson");
      assertEquals(modelP900.getName(), "P900");

      //export TAC by model
      bean.exportModelTAC(model3230, "D:/zhao/myworkspace/nWave-DM-Common/metadata/TAC/test/model3230TAC.xml");
      bean.exportModelTAC(model6670, "D:/zhao/myworkspace/nWave-DM-Common/metadata/TAC/test/model6670TAC.xml");
      bean.exportModelTAC(modelV270, "D:/zhao/myworkspace/nWave-DM-Common/metadata/TAC/test/modelV270TAC.xml");
      bean.exportModelTAC(modelV280, "D:/zhao/myworkspace/nWave-DM-Common/metadata/TAC/test/modelV280TAC.xml");
      bean.exportModelTAC(modelP910a, "D:/zhao/myworkspace/nWave-DM-Common/metadata/TAC/test/modelP910aTAC.xml");
      
      //export TAC by manufacturer
      Manufacturer manufacturer = bean.getManufacturerByExternalID("NOKIA");
      bean.exportModelTAC(manufacturer, "D:/zhao/myworkspace/nWave-DM-Common/metadata/TAC/test/nokia.xml");
      
      model3230.getModelTAC().remove("11111100");
      model3230.getModelTAC().remove("22222200");
      model3230.getModelTAC().remove("33333300");
      model6670.getModelTAC().remove("44444400");
      model6670.getModelTAC().remove("55555500");
      model6260.getModelTAC().remove("66666600");
      model7610.getModelTAC().remove("77777700");
      model7610.getModelTAC().remove("88888800");
      model7610.getModelTAC().remove("99999900");
      model7610.getModelTAC().remove("00000000");
      modelV270.getModelTAC().remove("11122200");
      modelV270.getModelTAC().remove("11133300");
      modelV280.getModelTAC().remove("11144400");
      modelP910a.getModelTAC().remove("00011100");
      modelP900.getModelTAC().remove("00033300");

      factory.commit();

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  public void testModelFamilyMethods() throws Exception {

    long now = System.currentTimeMillis();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    
    String manuName = "TestManu4Model";
    String manuExternalID = "TestManu4Model";

    try {
        factory.beginTransaction();
  
        Manufacturer manufacturer = bean.getManufacturerByExternalID(manuExternalID);
        if (manufacturer != null) {
          bean.delete(manufacturer);
          factory.commit();
          factory.beginTransaction();
        }
        manufacturer = bean.newManufacturerInstance(manuName, manuExternalID, manuName);
        bean.update(manufacturer);
        factory.commit();

        factory.beginTransaction();
        for (int k = 0; k < 10; k++ ) {
            for (int i = 0; i < 10; i++) {
                Model model = bean.newModelInstance();
                model.setManufacturer(manufacturer);
                model.setName("MODEL_" + k + "_" + i + "_" + now);
                model.setManufacturerModelId("MODEL_" + k + "_"  + i + "_" + now);
                String familyExternalID = "model.family." + k;
                model.setFamilyExternalID(familyExternalID );
                bean.update(model);
            }
        }
        factory.commit();
        
        List<ModelFamily> families = bean.getAllModelFamily();
        assertEquals(10, families.size());
        assertEquals("model.family.0", families.get(0).getExternalID());
        assertEquals("model.family.1", families.get(1).getExternalID());
        assertEquals("model.family.2", families.get(2).getExternalID());
        assertEquals("model.family.3", families.get(3).getExternalID());
        assertEquals("model.family.4", families.get(4).getExternalID());
        assertEquals("model.family.5", families.get(5).getExternalID());
        assertEquals("model.family.6", families.get(6).getExternalID());
        assertEquals("model.family.7", families.get(7).getExternalID());
        assertEquals("model.family.8", families.get(8).getExternalID());
        assertEquals("model.family.9", families.get(9).getExternalID());
        
        assertEquals(bean.getModelFamilyByExtID("model.family.0"), families.get(0));
        assertEquals(bean.getModelFamilyByExtID("model.family.1"), families.get(1));
        assertEquals(bean.getModelFamilyByExtID("model.family.2"), families.get(2));
        assertEquals(bean.getModelFamilyByExtID("model.family.3"), families.get(3));
        assertEquals(bean.getModelFamilyByExtID("model.family.4"), families.get(4));
        assertEquals(bean.getModelFamilyByExtID("model.family.5"), families.get(5));
        assertEquals(bean.getModelFamilyByExtID("model.family.6"), families.get(6));
        assertEquals(bean.getModelFamilyByExtID("model.family.7"), families.get(7));
        assertEquals(bean.getModelFamilyByExtID("model.family.8"), families.get(8));
        assertEquals(bean.getModelFamilyByExtID("model.family.9"), families.get(9));
        
        {
          List<Model> models = bean.findModelsByFamilyExtID("model.family.0");
          assertEquals(10, models.size());
          assertTrue(models.get(0).getName().startsWith("MODEL_0_"));
          assertTrue(models.get(1).getName().startsWith("MODEL_0_"));
          assertTrue(models.get(2).getName().startsWith("MODEL_0_"));
          assertTrue(models.get(3).getName().startsWith("MODEL_0_"));
          assertTrue(models.get(4).getName().startsWith("MODEL_0_"));
          assertTrue(models.get(5).getName().startsWith("MODEL_0_"));
          assertTrue(models.get(6).getName().startsWith("MODEL_0_"));
          assertTrue(models.get(7).getName().startsWith("MODEL_0_"));
          assertTrue(models.get(8).getName().startsWith("MODEL_0_"));
          assertTrue(models.get(9).getName().startsWith("MODEL_0_"));
        }

        {
          List<Model> models = bean.findModelsByFamilyExtID("model.family.1");
          assertEquals(10, models.size());
          assertTrue(models.get(0).getName().startsWith("MODEL_1_"));
          assertTrue(models.get(1).getName().startsWith("MODEL_1_"));
          assertTrue(models.get(2).getName().startsWith("MODEL_1_"));
          assertTrue(models.get(3).getName().startsWith("MODEL_1_"));
          assertTrue(models.get(4).getName().startsWith("MODEL_1_"));
          assertTrue(models.get(5).getName().startsWith("MODEL_1_"));
          assertTrue(models.get(6).getName().startsWith("MODEL_1_"));
          assertTrue(models.get(7).getName().startsWith("MODEL_1_"));
          assertTrue(models.get(8).getName().startsWith("MODEL_1_"));
          assertTrue(models.get(9).getName().startsWith("MODEL_1_"));
        }

        {
          List<Model> models = bean.findModelsByFamilyExtID("model.family.2");
          assertEquals(10, models.size());
          assertTrue(models.get(0).getName().startsWith("MODEL_2_"));
          assertTrue(models.get(1).getName().startsWith("MODEL_2_"));
          assertTrue(models.get(2).getName().startsWith("MODEL_2_"));
          assertTrue(models.get(3).getName().startsWith("MODEL_2_"));
          assertTrue(models.get(4).getName().startsWith("MODEL_2_"));
          assertTrue(models.get(5).getName().startsWith("MODEL_2_"));
          assertTrue(models.get(6).getName().startsWith("MODEL_2_"));
          assertTrue(models.get(7).getName().startsWith("MODEL_2_"));
          assertTrue(models.get(8).getName().startsWith("MODEL_2_"));
          assertTrue(models.get(9).getName().startsWith("MODEL_2_"));
        }

        {
          List<Model> models = bean.findModelsByFamilyExtID("model.family.3");
          assertEquals(10, models.size());
          assertTrue(models.get(0).getName().startsWith("MODEL_3_"));
          assertTrue(models.get(1).getName().startsWith("MODEL_3_"));
          assertTrue(models.get(2).getName().startsWith("MODEL_3_"));
          assertTrue(models.get(3).getName().startsWith("MODEL_3_"));
          assertTrue(models.get(4).getName().startsWith("MODEL_3_"));
          assertTrue(models.get(5).getName().startsWith("MODEL_3_"));
          assertTrue(models.get(6).getName().startsWith("MODEL_3_"));
          assertTrue(models.get(7).getName().startsWith("MODEL_3_"));
          assertTrue(models.get(8).getName().startsWith("MODEL_3_"));
          assertTrue(models.get(9).getName().startsWith("MODEL_3_"));
        }

        {
          List<Model> models = bean.findModelsByFamilyExtID("model.family.4");
          assertEquals(10, models.size());
          assertTrue(models.get(0).getName().startsWith("MODEL_4_"));
          assertTrue(models.get(1).getName().startsWith("MODEL_4_"));
          assertTrue(models.get(2).getName().startsWith("MODEL_4_"));
          assertTrue(models.get(3).getName().startsWith("MODEL_4_"));
          assertTrue(models.get(4).getName().startsWith("MODEL_4_"));
          assertTrue(models.get(5).getName().startsWith("MODEL_4_"));
          assertTrue(models.get(6).getName().startsWith("MODEL_4_"));
          assertTrue(models.get(7).getName().startsWith("MODEL_4_"));
          assertTrue(models.get(8).getName().startsWith("MODEL_4_"));
          assertTrue(models.get(9).getName().startsWith("MODEL_4_"));
        }

        {
          List<Model> models = bean.findModelsByFamilyExtID("model.family.5");
          assertEquals(10, models.size());
          assertTrue(models.get(0).getName().startsWith("MODEL_5_"));
          assertTrue(models.get(1).getName().startsWith("MODEL_5_"));
          assertTrue(models.get(2).getName().startsWith("MODEL_5_"));
          assertTrue(models.get(3).getName().startsWith("MODEL_5_"));
          assertTrue(models.get(4).getName().startsWith("MODEL_5_"));
          assertTrue(models.get(5).getName().startsWith("MODEL_5_"));
          assertTrue(models.get(6).getName().startsWith("MODEL_5_"));
          assertTrue(models.get(7).getName().startsWith("MODEL_5_"));
          assertTrue(models.get(8).getName().startsWith("MODEL_5_"));
          assertTrue(models.get(9).getName().startsWith("MODEL_5_"));
        }

        {
          List<Model> models = bean.findModelsByFamilyExtID("model.family.6");
          assertEquals(10, models.size());
          assertTrue(models.get(0).getName().startsWith("MODEL_6_"));
          assertTrue(models.get(1).getName().startsWith("MODEL_6_"));
          assertTrue(models.get(2).getName().startsWith("MODEL_6_"));
          assertTrue(models.get(3).getName().startsWith("MODEL_6_"));
          assertTrue(models.get(4).getName().startsWith("MODEL_6_"));
          assertTrue(models.get(5).getName().startsWith("MODEL_6_"));
          assertTrue(models.get(6).getName().startsWith("MODEL_6_"));
          assertTrue(models.get(7).getName().startsWith("MODEL_6_"));
          assertTrue(models.get(8).getName().startsWith("MODEL_6_"));
          assertTrue(models.get(9).getName().startsWith("MODEL_6_"));
        }

        {
          List<Model> models = bean.findModelsByFamilyExtID("model.family.7");
          assertEquals(10, models.size());
          assertTrue(models.get(0).getName().startsWith("MODEL_7_"));
          assertTrue(models.get(1).getName().startsWith("MODEL_7_"));
          assertTrue(models.get(2).getName().startsWith("MODEL_7_"));
          assertTrue(models.get(3).getName().startsWith("MODEL_7_"));
          assertTrue(models.get(4).getName().startsWith("MODEL_7_"));
          assertTrue(models.get(5).getName().startsWith("MODEL_7_"));
          assertTrue(models.get(6).getName().startsWith("MODEL_7_"));
          assertTrue(models.get(7).getName().startsWith("MODEL_7_"));
          assertTrue(models.get(8).getName().startsWith("MODEL_7_"));
          assertTrue(models.get(9).getName().startsWith("MODEL_7_"));
        }

        {
          List<Model> models = bean.findModelsByFamilyExtID("model.family.8");
          assertEquals(10, models.size());
          assertTrue(models.get(0).getName().startsWith("MODEL_8_"));
          assertTrue(models.get(1).getName().startsWith("MODEL_8_"));
          assertTrue(models.get(2).getName().startsWith("MODEL_8_"));
          assertTrue(models.get(3).getName().startsWith("MODEL_8_"));
          assertTrue(models.get(4).getName().startsWith("MODEL_8_"));
          assertTrue(models.get(5).getName().startsWith("MODEL_8_"));
          assertTrue(models.get(6).getName().startsWith("MODEL_8_"));
          assertTrue(models.get(7).getName().startsWith("MODEL_8_"));
          assertTrue(models.get(8).getName().startsWith("MODEL_8_"));
          assertTrue(models.get(9).getName().startsWith("MODEL_8_"));
        }

        {
          List<Model> models = bean.findModelsByFamilyExtID("model.family.9");
          assertEquals(10, models.size());
          assertTrue(models.get(0).getName().startsWith("MODEL_9_"));
          assertTrue(models.get(1).getName().startsWith("MODEL_9_"));
          assertTrue(models.get(2).getName().startsWith("MODEL_9_"));
          assertTrue(models.get(3).getName().startsWith("MODEL_9_"));
          assertTrue(models.get(4).getName().startsWith("MODEL_9_"));
          assertTrue(models.get(5).getName().startsWith("MODEL_9_"));
          assertTrue(models.get(6).getName().startsWith("MODEL_9_"));
          assertTrue(models.get(7).getName().startsWith("MODEL_9_"));
          assertTrue(models.get(8).getName().startsWith("MODEL_9_"));
          assertTrue(models.get(9).getName().startsWith("MODEL_9_"));
        }

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

  }

}
