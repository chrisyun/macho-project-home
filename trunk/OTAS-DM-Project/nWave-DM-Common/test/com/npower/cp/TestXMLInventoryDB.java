/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/cp/TestXMLInventoryDB.java,v 1.7 2007/08/28 09:27:16 zhao Exp $
  * $Revision: 1.7 $
  * $Date: 2007/08/28 09:27:16 $
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
package com.npower.cp;

import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import com.npower.cp.xmlinventory.OTAInventoryImpl;
import com.npower.cp.xmlinventory.OTATemplateItem;
import com.npower.dm.AllTests;
import com.npower.dm.core.ClientProvTemplate;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2007/08/28 09:27:16 $
 */
public class TestXMLInventoryDB extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testGetTemplates() throws Exception {
    Properties props = new Properties();
    props.setProperty(OTAInventory.PROPERTY_FACTORY, OTAInventoryImpl.class.getName());
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR, AllTests.BASE_DIR + "/metadata/cp/inventory");
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_FILENAME, "main.xml");
    
    OTAInventory inventory = OTAInventory.newInstance(props);
    assertTrue(inventory instanceof OTAInventoryImpl);
    
    {
      // Test getTemplates
      List<ClientProvTemplate> templates = inventory.getTemplates("nokia", "6681", "SyncDS");
      assertNotNull(templates);
      assertEquals(1, templates.size());
      ClientProvTemplate template = templates.get(0);
      assertEquals("SyncDS", template.getProfileCategory().getName(), "SyncDS");
      assertEquals("default", ((OTATemplateItem)template).getModel().getID());
      assertEquals("default", ((OTATemplateItem)template).getModel().getName());
      assertEquals("nokia", ((OTATemplateItem)template).getModel().getManufacturer().getID());
      assertEquals("Nokia", ((OTATemplateItem)template).getModel().getManufacturer().getName());
    }
    
    {
      // Test getTemplates
      List<ClientProvTemplate> templates = inventory.getTemplates("nokia", "Un-Exists-Model", "SyncDS");
      assertNotNull(templates);
      assertEquals(0, templates.size());
    }
    
    {
      // Test getTemplates
      List<ClientProvTemplate> templates = inventory.getTemplates("nokia", "6681", "Unknown_Cateory");
      assertNotNull(templates);
      assertEquals(0, templates.size());
    }
    
  }

  public void testFindTemplate() throws Exception {
    Properties props = new Properties();
    props.setProperty(OTAInventory.PROPERTY_FACTORY, OTAInventoryImpl.class.getName());
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR, AllTests.BASE_DIR + "/metadata/cp/inventory");
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_FILENAME, "main.xml");
    
    OTAInventory inventory = OTAInventory.newInstance(props);
    assertTrue(inventory instanceof OTAInventoryImpl);
    
    {
      // Test findTemplate
      ClientProvTemplate template = inventory.findTemplate("nokia", "6681", "SyncDS");
      assertNotNull(template);
      assertEquals("SyncDS", template.getProfileCategory().getName(), "SyncDS");
      assertEquals("default", ((OTATemplateItem)template).getModel().getID());
      assertEquals("default", ((OTATemplateItem)template).getModel().getName());
      assertEquals("nokia", ((OTATemplateItem)template).getModel().getManufacturer().getID());
      assertEquals("Nokia", ((OTATemplateItem)template).getModel().getManufacturer().getName());
    }
    
    {
      // Test findTemplate
      ClientProvTemplate template = inventory.findTemplate("nokia", "Unknown-Model", "SyncDS");
      assertNotNull(template);
      assertEquals("SyncDS", template.getProfileCategory().getName(), "SyncDS");
      assertEquals("default", ((OTATemplateItem)template).getModel().getID());
      assertEquals("default", ((OTATemplateItem)template).getModel().getName());
      assertEquals("nokia", ((OTATemplateItem)template).getModel().getManufacturer().getID());
      assertEquals("Nokia", ((OTATemplateItem)template).getModel().getManufacturer().getName());
    }
    
    {
      // Test findTemplate
      ClientProvTemplate template = inventory.findTemplate("Unknown-Manufacturer", "Unknown-Model", "SyncDS");
      assertNotNull(template);
      assertEquals("SyncDS", template.getProfileCategory().getName(), "SyncDS");
      assertEquals("Default", ((OTATemplateItem)template).getModel().getID());
      assertEquals("Default", ((OTATemplateItem)template).getModel().getName());
      assertEquals("Default", ((OTATemplateItem)template).getModel().getManufacturer().getID());
      assertEquals("Generic Manufacturer", ((OTATemplateItem)template).getModel().getManufacturer().getName());
    }
    
    {
      // Test findTemplate
      ClientProvTemplate template = inventory.findTemplate(null, null, "SyncDS");
      assertNotNull(template);
      assertEquals("SyncDS", template.getProfileCategory().getName(), "SyncDS");
      assertEquals("Default", ((OTATemplateItem)template).getModel().getID());
      assertEquals("Default", ((OTATemplateItem)template).getModel().getName());
      assertEquals("Default", ((OTATemplateItem)template).getModel().getManufacturer().getID());
      assertEquals("Generic Manufacturer", ((OTATemplateItem)template).getModel().getManufacturer().getName());
    }
    
    {
      // Test findTemplate
      ClientProvTemplate template = inventory.findTemplate("nokia", "6681", "Unknown_Cateory");
      assertNull(template);
    }
    
    {
      // Test findTemplate
      ClientProvTemplate template = inventory.findTemplate("SonyEricsson", "W800", "SyncDS");
      assertNotNull(template);
      assertEquals("SyncDS", template.getProfileCategory().getName(), "SyncDS");
      assertEquals("default", ((OTATemplateItem)template).getModel().getID());
      assertEquals("default", ((OTATemplateItem)template).getModel().getName());
      assertEquals("SonyEricsson", ((OTATemplateItem)template).getModel().getManufacturer().getID());
      assertEquals("SonyEricsson", ((OTATemplateItem)template).getModel().getManufacturer().getName());
    }
    
    {
      // Test findTemplate
      ClientProvTemplate template = inventory.findTemplate("SonyEricsson", "w800", "SyncDS");
      assertNotNull(template);
      assertEquals("SyncDS", template.getProfileCategory().getName(), "SyncDS");
      assertEquals("default", ((OTATemplateItem)template).getModel().getID());
      assertEquals("default", ((OTATemplateItem)template).getModel().getName());
      assertEquals("SonyEricsson", ((OTATemplateItem)template).getModel().getManufacturer().getID());
      assertEquals("SonyEricsson", ((OTATemplateItem)template).getModel().getManufacturer().getName());
    }
    
    {
      // Test findTemplate
      ClientProvTemplate template = inventory.findTemplate("SonyEricsson", "W810", "SyncDS");
      assertNotNull(template);
      assertEquals("SyncDS", template.getProfileCategory().getName(), "SyncDS");
      assertEquals("W810", ((OTATemplateItem)template).getModel().getID());
      assertEquals("W810", ((OTATemplateItem)template).getModel().getName());
      assertEquals("SonyEricsson", ((OTATemplateItem)template).getModel().getManufacturer().getID());
      assertEquals("SonyEricsson", ((OTATemplateItem)template).getModel().getManufacturer().getName());
    }
    
    {
      // Test findTemplate
      ClientProvTemplate template = inventory.findTemplate("SonyEricsson", "w810c", "SyncDS");
      assertNotNull(template);
      assertEquals("SyncDS", template.getProfileCategory().getName(), "SyncDS");
      assertEquals("W810", ((OTATemplateItem)template).getModel().getID());
      assertEquals("W810", ((OTATemplateItem)template).getModel().getName());
      assertEquals("SonyEricsson", ((OTATemplateItem)template).getModel().getManufacturer().getID());
      assertEquals("SonyEricsson", ((OTATemplateItem)template).getModel().getManufacturer().getName());
    }
    
  }

}
