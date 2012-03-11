package com.npower.dm.management;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.AllTests;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareLicenseType;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareVendor;

public abstract class BaseTestSoftwareBean extends TestCase {

  public BaseTestSoftwareBean() {
    super();
  }

  public BaseTestSoftwareBean(String name) {
    super(name);
  }

  public static void cleanSoftwareCategories() throws DMException {
    // Clean all of categories.
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    List<SoftwareCategory> all = bean.getAllOfRootCategories();
    for (SoftwareCategory category: all) {
        factory.beginTransaction();
        bean.delete(category);
        factory.commit();
    }
    factory.release();
  }

  public static void clearSoftwareVendors() throws DMException {
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

  public static void clearModels() throws DMException {
    // Clean all of Models and Manufacturers.
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    List<Manufacturer> all = bean.getAllManufacturers();
    factory.beginTransaction();
    for (Manufacturer m: all) {
        bean.delete(m);
    }
    factory.commit();
    factory.release();
  }

  /**
   * Create a vendor
   * @param name
   * @param description
   * @return
   * @throws Exception
   */
  public static SoftwareVendor setupSoftwareVendor(String name, String description) throws Exception {

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    SoftwareVendor vendor = bean.newVendorInstance(name, description);
    bean.update(vendor);
    factory.commit();
    
    // Testing & checking
    assertTrue(vendor.getId()> 0);
    SoftwareVendor c = bean.getVendorByID(vendor.getId());
    assertNotNull(c);
    assertEquals(name, c.getName());
    assertEquals(description, c.getDescription());
    
    factory.release();
    
    return vendor;
  }

  public static SoftwareCategory setupSoftwareCategory(SoftwareCategory parent, String name, String description) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareBean bean = factory.createSoftwareBean();
    
    factory.beginTransaction();
    SoftwareCategory category = setupSoftwareCategory(bean, parent, name, description);
    factory.commit();
    
    // Testing & checking
    assertTrue(category.getId()> 0);
    SoftwareCategory c = bean.getCategoryByID(category.getId());
    assertNotNull(c);
    assertEquals(name, c.getName());
    assertEquals(description, c.getDescription());
    assertEquals(null, c.getParent());
    
    factory.release();
    return category;
    
  }

  /**
   * @param bean
   * @param parent
   * @param name
   * @param description
   * @return
   * @throws DMException
   */
  public static SoftwareCategory setupSoftwareCategory(SoftwareBean bean, SoftwareCategory parent, String name,
      String description) throws DMException {
    SoftwareCategory category = bean.newCategoryInstance(parent, name, description);
    bean.update(category);
    return category;
  }

  /**
   * @param manufacturerExternalID
   * @param modelExternalID
   * @return
   * @throws Exception
   */
  public static Model setupModel(String manufacturerExternalID, String modelExternalID, String familyExtID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    ModelBean modelBean = factory.createModelBean();

    try {
        factory.beginTransaction();
        
        Model model = setupModel(modelBean, manufacturerExternalID, modelExternalID, familyExtID);
        factory.commit();
        return model;
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * @param modelBean
   * @param manufacturerExternalID
   * @param modelExternalID
   * @param familyExtID
   * @return
   * @throws DMException
   */
  public static Model setupModel(ModelBean modelBean, String manufacturerExternalID, String modelExternalID,
      String familyExtID) throws DMException {
    Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
    if (manufacturer == null) {
       manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, manufacturerExternalID);
       modelBean.update(manufacturer);
    }
 
    Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
    if (model == null) {
       model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
       model.setFamilyExternalID(familyExtID);
       modelBean.update(model);
    }
    return model;
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    if (StringUtils.isEmpty(System.getProperty("otas.dm.home"))) {
       System.setProperty("otas.dm.home", "D:/OTASDM");
    }
    
    cleanSoftwareCategories();
    clearSoftwareVendors();
    clearModels();
    
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * <pre>
   * 1. 创建多个型号(Device Model), 属于同一个Manufacturer
   *    M_1
   *    M_2
   * 2. 创建多个分类(Software Category)
   *    C_1 (C_1_1, C_1_2, ..., C_1_n)
   *    C_2 (C_2_1, C_2_2, ..., C_2_n)
   * 3. 为每个分类下创建多个软件
   *    S1_IN_[Name of Category] 和
   *    S2_IN_[Name of Category]
   *    例如:
   *        S1_IN_C_1, S1_IN_C_1_1, S1_IN_C_1_2, ...
   *        S1_IN_C_2, S1_IN_C_2_1, S1_IN_C_2_2, ...
   *        S2_IN_C_1, S2_IN_C_1_1, S2_IN_C_1_2, ...
   *        S2_IN_C_2, S2_IN_C_2_1, S2_IN_C_2_2, ...
   * 4. 为每个软件和每个不同的型号创建软件包(Software Package)
   *    P_S(Name of Software)_M(Name of Model), 例如:
   *    P_(S1_IN_C_1)_M(M_1),        
   *    P_(S1_IN_C_1)_M(M_2),        
   *    P_(S2_IN_C_1)_M(M_1),        
   *    P_(S2_IN_C_1)_M(M_2),  
   *    ...
   * </pre>
   * @param numberOfChildrenPerCategory
   * @param numberOfSoftwarePerCategory
   * @param numberOfModel
   * @throws Exception
   */
  public static void setupSampleData(int numberOfChildrenPerCategory, int numberOfSoftwarePerCategory, int numberOfModel)
      throws Exception {
    // 清除数据
    cleanSoftwareCategories();
    clearSoftwareVendors();
    clearModels();
  
    // 1. Setup Models
    {
      ManagementBeanFactory factory = null;
      try {
          factory = AllTests.getManagementBeanFactory();
          ModelBean bean = factory.createModelBean();
          factory.beginTransaction();
          for (int i = 1; i <= numberOfModel; i++) {
              String manufacturerExternalID = "Manufacturer";
              String modelExternalID = "M_" + i;
              String modelFamilyID = "model.family." + modelExternalID;
              setupModel(bean, manufacturerExternalID, modelExternalID, modelFamilyID);
          }
          factory.commit();
      } catch (Exception ex) {
        if (factory != null) {
           factory.rollback();
        }
        throw ex;
      } finally {
        if (factory != null) { 
           factory.release();
        }
      }
    }
  
   //  2.Setup Categories
    {
      ManagementBeanFactory factory = null;
      try {
          factory = AllTests.getManagementBeanFactory();
          SoftwareBean softwareBean = factory.createSoftwareBean();
          factory.beginTransaction();
          for (int i = 1; i <= numberOfChildrenPerCategory; i++) {
              String name = "C_" + i;
              String description = name;
              SoftwareCategory category = setupSoftwareCategory(softwareBean, null, name, description);
              for (int j = 1; j <= numberOfChildrenPerCategory; j++) {
                  String childName = name + "_" + j;
                  String childDescription = childName;
                  setupSoftwareCategory(softwareBean, category, childName, childDescription);
              }
          }
          factory.commit();
      } catch (Exception ex) {
        if (factory != null) {
           factory.rollback();
        }
        throw ex;
      } finally {
        if (factory != null) { 
           factory.release();
        }
      }
    }
    
    
    // 3.Setup Softwares
    {
      ManagementBeanFactory factory = null;
      try {
          factory = AllTests.getManagementBeanFactory();
          SoftwareBean softwareBean = factory.createSoftwareBean();
          factory.beginTransaction();
          
          for (SoftwareCategory category: softwareBean.getAllOfCategories()) {
              for (int i = 1; i < numberOfSoftwarePerCategory; i++) {
                  String softwareExtID = "S" + i + "_IN_" + category.getName();
                  String softwareName = softwareExtID;
                  String softwareVersion = "1.0";
                  
                  // Set Vendor
                  String nameOfVendor = softwareExtID;
                  String descriptionOfVendor = nameOfVendor;
                  SoftwareVendor vendor = softwareBean.newVendorInstance(nameOfVendor, descriptionOfVendor);
                  softwareBean.update(vendor);
                  
                  // Setup Software
                  Software s1 = softwareBean.newSoftwareInstance(vendor, category, softwareExtID, softwareName, softwareVersion, SoftwareLicenseType.FREE);
                  softwareBean.update(s1);
                  
              }
          }
          factory.commit();
      } catch (Exception ex) {
        if (factory != null) {
           factory.rollback();
        }
        throw ex;
      } finally {
        if (factory != null) { 
           factory.release();
        }
      }
    }
    
    // 4.Setup Software Packages
    {
      ManagementBeanFactory factory = null;
      try {
          factory = AllTests.getManagementBeanFactory();
          SoftwareBean softwareBean = factory.createSoftwareBean();
          ModelBean modelBean = factory.createModelBean();
          factory.beginTransaction();
          
          for (Software software: softwareBean.getAllOfSoftwares()) {
              for (Model model: modelBean.getAllModel()) {
                  Set<Model> setOfModel = new HashSet<Model>();
                  setOfModel.add(model);
                  
                  String urlOfPackage = "http://www.a.com/download";
                  SoftwarePackage p1 = softwareBean.newPackageInstance(software, null, null, setOfModel, urlOfPackage );
                  String langOfPackage1 = "zh";
                  p1.setLanguage(langOfPackage1);
                  softwareBean.update(p1);
              }
          }
          factory.commit();
      } catch (Exception ex) {
        if (factory != null) {
           factory.rollback();
        }
        throw ex;
      } finally {
        if (factory != null) { 
           factory.release();
        }
      }
    }
  }

}