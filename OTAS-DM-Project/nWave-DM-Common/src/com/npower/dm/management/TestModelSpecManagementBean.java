/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestModelSpecManagementBean.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.4 $
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

import java.io.InputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Hibernate;

import com.npower.dm.AllTests;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelCharacter;

/**
 * @author Zhao DongLu
 * 
 */
public class TestModelSpecManagementBean extends TestCase {

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


  public void testModelSpecMethods() throws Exception {

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

    ManagementBeanFactory factory = null;
    long id = 0;
    try {
      factory = AllTests.getManagementBeanFactory();
      ModelBean modelBean = factory.createModelBean();

      // Create a manufacturer
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manuExternalID);
      if (manufacturer != null) {
        factory.beginTransaction();
        modelBean.delete(manufacturer);
        factory.commit();
      }
      manufacturer = modelBean.newManufacturerInstance(manuName, manuExternalID, manuName);
      factory.beginTransaction();
      modelBean.update(manufacturer);
      factory.commit();

      // Create a model
      Model model = modelBean.newModelInstance(manufacturer, manuModelID, name, supportedDownloadMethods, omaEnable,
          plainProfile, useEncForOmaMsg, useNextNoncePerPkg);

      factory.beginTransaction();
      byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
      InputStream in = new java.io.ByteArrayInputStream(bytes);
      Blob icon = Hibernate.createBlob(in);
      model.setIcon(icon);
      modelBean.update(model);
      factory.commit();
      
      id = model.getID();
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
      
    assertTrue(id > 0);
    try {
      factory = AllTests.getManagementBeanFactory();
      ModelBean bean = factory.createModelBean();
      
      // Load
      {
        Model model = bean.getModelByID("" + id);
        assertNotNull(model);
        
        Blob blob = model.getIcon();
        long len = blob.length();
        assertEquals(8, len);
        InputStream in = blob.getBinaryStream();
        byte[] icon = new byte[8];
        in.read(icon, 0, (int)len);
        assertEquals(1, icon[0]);
        assertEquals(2, icon[1]);
        assertEquals(3, icon[2]);
        assertEquals(4, icon[3]);
        assertEquals(5, icon[4]);
        assertEquals(6, icon[5]);
        assertEquals(7, icon[6]);
        assertEquals(8, icon[7]);
      }

      // Load
      {
        Model model = bean.getModelByID("" + id);
        assertNotNull(model);
        
        byte[] icon = model.getIconBinary();
        assertEquals(8, icon.length);
        assertEquals(1, icon[0]);
        assertEquals(2, icon[1]);
        assertEquals(3, icon[2]);
        assertEquals(4, icon[3]);
        assertEquals(5, icon[4]);
        assertEquals(6, icon[5]);
        assertEquals(7, icon[6]);
        assertEquals(8, icon[7]);
      }

      // Delete
      {
        Model model = bean.getModelByID("" + id);
        assertNotNull(model);
        
        factory.beginTransaction();
        bean.delete(model);
        factory.commit();
        
        assertNull(bean.getModelByID("" + id));
      }

    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testModelCharacterMethods() throws Exception {

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

    ManagementBeanFactory factory = null;
    long id = 0;
    try {
      factory = AllTests.getManagementBeanFactory();
      ModelBean bean = factory.createModelBean();

      // Create a manufacturer
      Manufacturer manufacturer = bean.getManufacturerByExternalID(manuExternalID);
      if (manufacturer != null) {
        factory.beginTransaction();
        bean.delete(manufacturer);
        factory.commit();
      }
      manufacturer = bean.newManufacturerInstance(manuName, manuExternalID, manuName);
      factory.beginTransaction();
      bean.update(manufacturer);
      factory.commit();

      // Create a model
      Model model = bean.newModelInstance(manufacturer, manuModelID, name, supportedDownloadMethods, omaEnable,
          plainProfile, useEncForOmaMsg, useNextNoncePerPkg);

      byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
      InputStream in = new java.io.ByteArrayInputStream(bytes);
      Blob icon = Hibernate.createBlob(in);
      model.setIcon(icon);

      factory.beginTransaction();
      bean.update(model);
      factory.commit();
      
      id = model.getID();
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
      
    assertTrue(id > 0);
    try {
      factory = AllTests.getManagementBeanFactory();
      ModelBean bean = factory.createModelBean();
      
      {
        Model model = bean.getModelByID("" + id);
        
        factory.beginTransaction();
        ModelCharacter c1 = bean.newModelCharacterInstance(model, "general", "os");
        c1.setValue("Symbian V9.1");
        bean.update(c1);
        ModelCharacter c2 = bean.newModelCharacterInstance(model, null, "name1");
        c2.setValue("value1");
        bean.update(c2);
        bean.update(c2);
        factory.commit();
      }

      // Load
      {
        Model model = bean.getModelByID("" + id);
        assertNotNull(model);
        
        Set<ModelCharacter> characters = model.getCharacters();
        assertNotNull(characters);
        assertEquals(2, characters.size());
        
        List<ModelCharacter> result1 = bean.findModelCharacters(model, "general", "os");
        assertEquals(1, result1.size());
        ModelCharacter c1 = result1.get(0);
        assertEquals("general", c1.getCategory());
        assertEquals("os", c1.getName());
        assertEquals("Symbian V9.1", c1.getValue());
        
        List<ModelCharacter> result2 = bean.findModelCharacters(model, null, "name1");
        assertEquals(1, result2.size());
        ModelCharacter c2 = result2.get(0);
        assertEquals(null, c2.getCategory());
        assertEquals("name1", c2.getName());
        assertEquals("value1", c2.getValue());
        
        List<ModelCharacter> result3 = bean.findModelCharacters(model, null, "name2");
        assertEquals(0, result3.size());
      }

      // Delete
      {
        Model model = bean.getModelByID("" + id);
        assertNotNull(model);
        
        factory.beginTransaction();
        ModelCharacter[] set = model.getCharacters().toArray(new ModelCharacter[0]);
        for (ModelCharacter character: set) {
            bean.delete(character);
        }
        factory.commit();
      }

      // Load
      {
        Model model = bean.getModelByID("" + id);
        assertNotNull(model);
        
        Set<ModelCharacter> characters = model.getCharacters();
        assertNotNull(characters);
        assertEquals(0, characters.size());
        
        List<ModelCharacter> result1 = bean.findModelCharacters(model, "general", "os");
        assertEquals(0, result1.size());
        
        List<ModelCharacter> result2 = bean.findModelCharacters(model, null, "name1");
        assertEquals(0, result2.size());
        
        List<ModelCharacter> result3 = bean.findModelCharacters(model, null, "name2");
        assertEquals(0, result3.size());
        
      }

    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

  }

}
