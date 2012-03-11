/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestProfileTemplateManagementBean.java,v 1.28 2008/06/16 10:11:14 zhao Exp $
 * $Revision: 1.28 $
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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.AllTests;
import com.npower.dm.core.AttributeTypeValue;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;

/**
 * @author Zhao DongLu
 * 
 */
public class TestProfileTemplateManagementBean extends TestCase {

  private static Log log = LogFactory.getLog(TestProfileTemplateManagementBean.class);

  private static final String BASE_DIR = AllTests.BASE_DIR;

  /**
   * Profile Template XML File for Testing
   */
  private static final String FILENAME_PROFILE_METADATA = BASE_DIR + "/metadata/profile/metadata/profile.test.xml";

  /**
   * 
   */
  private static final String CATEGORY_NAME_FOR_TEST = "TEST.NAP";

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testAdd() throws Exception {

    long now = System.currentTimeMillis();
    String categoryName = "Test_Category_" + now;
    String description = "Test Category " + now;

    // long changeVersion = 0;

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    try {
      factory.beginTransaction();
      
      ProfileCategory category = bean.getProfileCategoryByName(categoryName);
      if (category != null) {
        bean.deleteCategory(category);
      }

      category = bean.newProfileCategoryInstance(categoryName, description);
      bean.updateCategory(category);

      category = bean.getProfileCategoryByName(categoryName);
      assertEquals(category.getDescription(), description);

      bean.deleteCategory(category);
      category = bean.getProfileCategoryByName(categoryName);
      assertNull(category);

      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testImportCategory() throws Exception {

    InputStream in = null;
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    try {
      
      // URL testXML = getClass().getResource("./profile.test.xml");
      // in = testXML.openStream();
      // in = getClass().getResourceAsStream("./profile/profile.test.xml");
      in = new FileInputStream(FILENAME_PROFILE_METADATA);

      // Delete Testing Data
      factory.beginTransaction();
      List<ProfileCategory> categories = bean.findProfileCategories("from ProfileCategoryEntity where name like 'TEST.%'");
      for (int i = 0; i < categories.size(); i++) {
        bean.deleteCategory((ProfileCategory) categories.get(i));
      }
      factory.commit();

      int total = bean.importCategory(in);
      in.close();
      assertTrue(total > 0);

      // Retrieve all data
      categories = bean.findProfileCategories("from ProfileCategoryEntity where name like 'TEST.%'");
      assertTrue(total > 0);

      // If failure, please check profile.test.xml, make sure the total of
      // ProfileCategoryEntity is 5.
      assertEquals(5, total);

      ProfileCategory category = bean.getProfileCategoryByName(CATEGORY_NAME_FOR_TEST);
      assertNotNull(category);
      assertNotNull(category.getDescription());

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
      if (in != null) {
        try {
            in.close();
        } catch (IOException e) {
          e.printStackTrace();
          assertTrue(false);
        }
      }
    }
  }

  // Test methods for ProfileTemplateManagementBeanImpl
  // -----------------------------------------------------------

  public void testParsingProfileAttributeTypes() throws Exception {

    InputStream in = null;
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    try {
      // in = getClass().getResourceAsStream("profile.xml");
      in = new FileInputStream(FILENAME_PROFILE_METADATA);
      List<ProfileAttributeType> types = bean.parseProfileAttributeType(in);

      in.close();
      assertTrue(types.size() > 0);
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
          assertTrue(false);
        }
      }
    }
  }

  public void testImportProfileAttributeTypes() throws Exception {

    InputStream in = null;
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    try {
        // in = getClass().getResourceAsStream("profile.xml");
        in = new FileInputStream(FILENAME_PROFILE_METADATA);
  
        String clause4AllTestTypes = "from ProfileAttributeTypeEntity where name like 'TEST %'";
  
        List<ProfileAttributeType> types = bean.findProfileAttributeTypes(clause4AllTestTypes);
        for (int i = 0; i < types.size(); i++) {
            ProfileAttributeType type = (ProfileAttributeType) types.get(i);
            log.info("delete:" + type);
            factory.beginTransaction();
            bean.deleteAttributeType(type);
            factory.commit();
        }
  
        types = bean.findProfileAttributeTypes(clause4AllTestTypes);
        assertEquals(types.size(), 0);
  
        int total = bean.importProfileAttributeType(in);
        in.close();
  
        types = bean.findProfileAttributeTypes(clause4AllTestTypes);
        assertTrue(types.size() > 0);
  
        ProfileAttributeType type = bean.getProfileAttributeTypeByName("TEST Bearer Link Speed");
        assertEquals(type.getDescription(), "Speed on the channel for CSD bearers");
        assertEquals(type.getAttribTypeValues().size(), 4);
        Set<AttributeTypeValue> values = type.getAttribTypeValues();
        for (Iterator<AttributeTypeValue> i = values.iterator(); i.hasNext();) {
            AttributeTypeValue value = (AttributeTypeValue) i.next();
            assertEquals(value.getProfileAttribType().getID(), type.getID());
            String v = value.getValue();
            assertTrue(v.equals("2400") || v.equals("4800") || v.equals("9600") || v.equals("14400"));
        }
  
        assertTrue(total > 0);
        
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
          assertTrue(false);
        }
      }
    }
  }

  public void testGetProfileAttributeTypeByName() throws Exception {
    testImportProfileAttributeTypes();
  }

  public void testFindProfileAttributeTypes() throws Exception {
    testImportProfileAttributeTypes();
  }

  // Test methods for ProfileTemplateManagementBeanImpl ProfileAttributeEntity
  // -------------------------------------------------

  public void testParsingProfileTemplates() throws Exception {
    InputStream in = null;
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    try {
      // in = getClass().getResourceAsStream("profile.xml");
      in = new FileInputStream(FILENAME_PROFILE_METADATA);
      List<ProfileTemplate> templates = bean.parseProfileTemplate(in);
      in.close();
      log.debug(templates);
      assertTrue(templates.size() > 0);
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
          assertTrue(false);
        }
      }
    }
  }

  public void testImportProfileTemplates() throws Exception {

    InputStream in = null;
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileTemplateBean bean = factory.createProfileTemplateBean();
    try {
        // in = getClass().getResourceAsStream("profile.xml");
        in = new FileInputStream(FILENAME_PROFILE_METADATA);
  
        String clause4AllTestTemplates = "from ProfileTemplateEntity where name like 'TEST.%'";
  
        factory.beginTransaction();
        List<ProfileTemplate> templates = bean.findTemplates(clause4AllTestTemplates);
        for (int i = 0; i < templates.size(); i++) {
          ProfileTemplate template = (ProfileTemplate) templates.get(i);
          log.info("delete:" + template);
          bean.delete(template);
        }
        factory.commit();
  
        templates = bean.findTemplates(clause4AllTestTemplates);
        assertEquals(templates.size(), 0);
  
        int total = bean.importProfileTemplates(in);
        in.close();
        assertTrue(total > 0);
  
        templates = bean.findTemplates(clause4AllTestTemplates);
        assertTrue(templates.size() > 0);
  
        {
          ProfileTemplate template = bean.getTemplateByName("TEST.Profile Template#1");
          assertEquals(template.getNeedsNap(), false);
          assertEquals(template.getNeedsProxy(), false);
          assertEquals(template.getProfileCategory().getName(), "TEST.NAP");
          assertEquals(template.getProfileAttributes().size(), 7);
  
          ProfileAttribute attr = bean.getProfileAttributeByName(template.getName(), "TEST Display Name");
          assertEquals(attr.getName(), "TEST Display Name");
          assertEquals(attr.getProfileAttribType().getName(), "TEST String");
          assertEquals(attr.getDisplayValue(), true);
          assertEquals(attr.getIsMultivalued(), true);
          assertEquals(attr.getIsRequired(), true);
          assertEquals(attr.getIsUserAttribute(), true);
          assertEquals(attr.getProfileTemplate().getName(), template.getName());
  
          attr = bean.getProfileAttributeByName(template.getName(), "TEST Bearer Direction");
          assertEquals(attr.getName(), "TEST Bearer Direction");
          assertEquals(attr.getProfileAttribType().getName(), "TEST Bearer Direction");
          assertEquals(attr.getDisplayValue(), true);
          assertEquals(attr.getIsMultivalued(), false);
          assertEquals(attr.getIsRequired(), true);
          assertEquals(attr.getIsUserAttribute(), false);
          assertEquals(attr.getDefaultValue(), "Outgoing");
          assertEquals(attr.getProfileTemplate().getName(), template.getName());
        }
  
        {
          /*
           * ProfileTemplateEntity template =
           * bean.getTemplateByName("TEST.CSD NAP Template");
           * assertEquals(template.getNeedsNap(), 0L);
           * assertEquals(template.getNeedsProxy(), 0L);
           * assertEquals(template.getProfileCategory().getName(), "TEST.NAP");
           * assertEquals(template.getProfileAttributes().size(), 9);
           * 
           * ProfileAttributeEntity attr =
           * bean.getProfileAttributeByName(template.getName(), "TEST Display
           * Name"); assertEquals(attr.getName(), "TEST Display Name");
           * assertEquals(attr.getProfileAttribType().getName(), "TEST String");
           * assertEquals(attr.getDisplayValue(), 1L);
           * assertEquals(attr.getIsMultivalued(), 1L);
           * assertEquals(attr.getIsRequired(), 1L);
           * assertEquals(attr.getIsUserAttribute(), 1L);
           * assertEquals(attr.getProfileTemplate().getName(),
           * template.getName());
           * 
           * attr = bean.getProfileAttributeByName(template.getName(), "TEST
           * Bearer Direction"); assertEquals(attr.getName(), "TEST Bearer
           * Direction"); assertEquals(attr.getProfileAttribType().getName(),
           * "TEST Bearer Direction"); assertEquals(attr.getDisplayValue(), 1L);
           * assertEquals(attr.getIsMultivalued(), 0L);
           * assertEquals(attr.getIsRequired(), 1L);
           * assertEquals(attr.getIsUserAttribute(), 0L);
           * assertEquals(attr.getDefaultValue(), "Outgoing");
           * assertEquals(attr.getProfileTemplate().getName(),
           * template.getName());
           */
        }

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
          assertTrue(false);
        }
      }
    }
  }

}
