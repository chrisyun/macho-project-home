/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestClientProvTemplateBean.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/06/16 10:11:15 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestClientProvTemplateBean extends TestCase {

  private static final String MANUFACTURER_External_ID_1 = "Test.Manu.1." + System.currentTimeMillis();
  private static final String MODEL_External_ID_1 = "Test.Model.1." + System.currentTimeMillis();

  private static final String MANUFACTURER_External_ID_2 = "Test.Manu.2." + System.currentTimeMillis();
  private static final String MODEL_External_ID_2 = "Test.Model.2." + System.currentTimeMillis();

  /**
   * @param name
   */
  public TestClientProvTemplateBean(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    //ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    ModelBean modelBean = factory.createModelBean();

    try {
        factory.beginTransaction();
  
        this.setupModel(modelBean);
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  private void setupModel(ModelBean modelBean) throws Exception {
    try {
      String manufacturer_ext_id = MANUFACTURER_External_ID_1;
      String model_ext_id = MODEL_External_ID_1;
      createModel(modelBean, manufacturer_ext_id, model_ext_id);

      manufacturer_ext_id = MANUFACTURER_External_ID_2;
      model_ext_id = MODEL_External_ID_2;
      createModel(modelBean, manufacturer_ext_id, model_ext_id);

    } catch (DMException e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * @param modelBean
   * @param manufacturer_ext_id
   * @param model_ext_id
   * @throws DMException
   */
  private void createModel(ModelBean modelBean, String manufacturer_ext_id, String model_ext_id) throws DMException {
    Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturer_ext_id);
    if (manufacturer == null) {
      manufacturer = modelBean.newManufacturerInstance(manufacturer_ext_id, manufacturer_ext_id, manufacturer_ext_id);
      modelBean.update(manufacturer);
    }

    Model model = modelBean.getModelByManufacturerModelID(manufacturer, model_ext_id);
    if (model == null) {
      model = modelBean.newModelInstance(manufacturer, model_ext_id, model_ext_id, true, true, true, true, true);

      modelBean.update(model);
    }
  }

  public void testAddUpdateDeleteGet() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean modelBean = factory.createModelBean();
    ProfileTemplateBean profileTemplateBean = factory.createProfileTemplateBean();
    
    ClientProvTemplateBean bean = factory.createClientProvTemplateBean();
    try {
        ClientProvTemplate template = bean.newClientProvTemplateInstance();
        
        String content = "content";
        String description = "description";
        String name = "name";
        String manufacturerExtID = MANUFACTURER_External_ID_1;
        String modelExtID = MODEL_External_ID_1;
        String categoryName = ProfileCategory.NAP_CATEGORY_NAME;

        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExtID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer , modelExtID);
        assertNotNull(model);
        ProfileCategory category = profileTemplateBean.getProfileCategoryByName(categoryName);
        assertNotNull(category);
        
        template.setExternalID(name);
        template.setContent(content);
        template.setName(name);
        template.setDescription(description);
        template.setProfileCategory(category);
        
        // Add
        factory.beginTransaction();
        bean.update(template);
        factory.commit();
        
        ClientProvTemplate other = bean.getTemplate(template.getID().toString());
        assertNotNull(other);
        
        assertEquals(template.getContent(), other.getContent());
        assertEquals(template.getDescription(), other.getDescription());
        assertEquals(template.getName(), other.getName());
        assertEquals(template.getProfileCategory().getID(), other.getProfileCategory().getID());
        assertEquals(categoryName, other.getProfileCategory().getName());
        
        // Update
        String newContent = "content";
        String newDescription = "description";
        String newName = "name";
        String newCategoryName = ProfileCategory.SYNC_DS_CATEGORY_NAME;
        ProfileCategory newCategory = profileTemplateBean.getProfileCategoryByName(newCategoryName);
        assertNotNull(newCategory);
        other.setExternalID(newName);
        other.setName(newName);
        other.setContent(newContent);
        other.setDescription(newDescription);
        other.setProfileCategory(newCategory);
        
        factory.beginTransaction();
        bean.update(other);
        factory.commit();
        
        ClientProvTemplate another = bean.getTemplate(template.getID().toString());
        assertNotNull(another);
        
        assertEquals(other.getContent(), another.getContent());
        assertEquals(other.getDescription(), another.getDescription());
        assertEquals(other.getName(), another.getName());
        assertEquals(other.getProfileCategory().getID(), another.getProfileCategory().getID());
        assertEquals(newCategoryName, another.getProfileCategory().getName());
        
        // Delete
        factory.beginTransaction();
        bean.delete(another);
        factory.commit();
        another = bean.getTemplate(template.getID().toString());
        assertNull(another);

        
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
  
  public void testAttach() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean modelBean = factory.createModelBean();
    ProfileTemplateBean profileTemplateBean = factory.createProfileTemplateBean();
    
    ClientProvTemplateBean bean = factory.createClientProvTemplateBean();
    try {
        ClientProvTemplate template = bean.newClientProvTemplateInstance();
        
        String content = "content";
        String description = "description";
        String name = "name";
        String manufacturerExtID = MANUFACTURER_External_ID_2;
        String modelExtID = MODEL_External_ID_2;
        String categoryName = ProfileCategory.NAP_CATEGORY_NAME;

        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExtID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer , modelExtID);
        assertNotNull(model);
        ProfileCategory category = profileTemplateBean.getProfileCategoryByName(categoryName);
        assertNotNull(category);
        
        template.setExternalID(name);
        template.setContent(content);
        template.setName(name);
        template.setDescription(description);
        template.setProfileCategory(category);
        
        // Add
        factory.beginTransaction();
        bean.update(template);
        factory.commit();
        
        ClientProvTemplate other = bean.getTemplate(template.getID().toString());
        assertNotNull(other);
        
        // Attach
        factory.beginTransaction();
        bean.attach(model, other);
        factory.commit();
        
        assertFalse(model.getClientProvTemplates().isEmpty());
        assertTrue(model.getClientProvTemplates().size() == 1);
        assertTrue(model.getClientProvTemplates().contains(other));
        
        // Test findTemplate
        ClientProvTemplate another = bean.findTemplate(model, category);
        assertNotNull(another);
        
        assertEquals(template.getContent(), another.getContent());
        assertEquals(template.getDescription(), another.getDescription());
        assertEquals(template.getName(), another.getName());
        assertEquals(template.getProfileCategory().getID(), another.getProfileCategory().getID());
        assertEquals(categoryName, another.getProfileCategory().getName());
        
        // Test findTemplates
        List<ClientProvTemplate> templates = bean.findTemplates(model);
        assertNotNull(templates);
        assertTrue(templates.size() > 0);
        
        // Dettach
        factory.beginTransaction();
        bean.dettach(model, other);
        factory.commit();
        // Test findTemplate
        another = bean.findTemplate(model, category);
        assertNull(another);
        
        assertTrue(model.getClientProvTemplates().isEmpty());
        assertTrue(model.getClientProvTemplates().size() == 0);
        assertFalse(model.getClientProvTemplates().contains(other));
        
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
