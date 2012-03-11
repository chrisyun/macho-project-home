/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/cp/TestXmlOtaInventory.java,v 1.6 2007/08/28 09:27:16 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2007/08/28 09:27:16 $
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
package com.npower.cp;

import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import com.npower.cp.xmlinventory.ManufacturerItem;
import com.npower.cp.xmlinventory.ModelItem;
import com.npower.cp.xmlinventory.OTAInventoryImpl;
import com.npower.cp.xmlinventory.OTATemplateItem;
import com.npower.dm.AllTests;
import com.npower.dm.core.ClientProvTemplate;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2007/08/28 09:27:16 $
 */
public class TestXmlOtaInventory extends TestCase {

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
  
  public void testOtaInventoryFactory() throws Exception {
    Properties props = new Properties();
    props.setProperty(OTAInventory.PROPERTY_FACTORY, OTAInventoryImpl.class.getName());
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR, AllTests.BASE_DIR + "/metadata/cp/test");
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_FILENAME, "test.1.otatemplate.inventory.xml");
    
    OTAInventory instance = OTAInventory.newInstance(props);
    assertTrue(instance instanceof OTAInventoryImpl);
    OTAInventoryImpl inventory = (OTAInventoryImpl)instance;
    List<ManufacturerItem> mans = inventory.getSupportedManufacturers();
    assertEquals(1, mans.size());
    
    ManufacturerItem man = mans.get(0);
    assertNotNull(man);
    assertEquals("nokia", man.getID());
    assertEquals("Nokia", man.getName());
    assertEquals("Nokia Manufacturer", man.getDescription());
    
    assertEquals(2, man.getModels().size());
    {
      ModelItem model = man.getModels().get(0);
      assertNotNull(model);
      assertEquals("6620", model.getID());
      assertEquals("6620C", model.getName());
      assertEquals("Nokia 6620", model.getDescription());
      assertEquals(man, model.getManufacturer());
      
      assertEquals(1, model.getTemplates().size());
      ClientProvTemplate template = model.getTemplates().get(0);
      assertNotNull(template);
      assertEquals("SyncDS_OMA_CP", template.getExternalID());
      assertEquals("OMA CP SyncDS Settings", template.getName());
      assertEquals("This template based on OMA CP used for SyncDS settings.", template.getDescription());
      assertEquals("content", template.getContent());
      assertEquals("OMA_SyncDS", template.getProfileCategory().getName());
      //assertEquals(model, template.getModel());
      
    }
    {
      ModelItem model = man.getModels().get(1);
      assertNotNull(model);
      assertEquals("6681", model.getID());
      assertEquals("6681C", model.getName());
      assertEquals("Nokia 6681", model.getDescription());
      assertEquals(man, model.getManufacturer());
      
      assertEquals(1, model.getTemplates().size());
      ClientProvTemplate template = model.getTemplates().get(0);
      assertNotNull(template);
      assertEquals("SyncDS_NOKIA_CP", template.getExternalID());
      assertEquals("Nokia OTA SyncDS Settings", template.getName());
      assertEquals("NOKIA 7.0 OTA SyncDS", template.getProfileCategory().getName());
      assertEquals("This template based on Nokia OTA 7.0 used for SyncDS settings.", template.getDescription());
      assertEquals("<?xml version='1.0' encoding='UTF-8'?>\r\n<wap-provisioningdoc version=\"1.1\"/>", template.getContent());
      //assertEquals(model, template.getModel());
    }
  }
  
  public void testOtaInventoryFactoryWithInclude() throws Exception {
    Properties props = new Properties();
    props.setProperty(OTAInventory.PROPERTY_FACTORY, OTAInventoryImpl.class.getName());
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR, AllTests.BASE_DIR + "/metadata/cp/test");
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_FILENAME, "test.2.otatemplate.inventory.xml");
    
    OTAInventory instance = OTAInventory.newInstance(props);
    assertTrue(instance instanceof OTAInventoryImpl);
    OTAInventoryImpl inventory = (OTAInventoryImpl)instance;
    
    List<ManufacturerItem> mans = inventory.getSupportedManufacturers();
    assertEquals(2, mans.size());
    
    ManufacturerItem man = mans.get(0);
    assertNotNull(man);
    assertEquals("nokia", man.getID());
    assertEquals("Nokia", man.getName());
    assertEquals("Nokia Manufacturer", man.getDescription());
    
    assertEquals(2, man.getModels().size());
    {
      ModelItem model = man.getModels().get(0);
      assertNotNull(model);
      assertEquals("6620", model.getID());
      assertEquals("6620C", model.getName());
      assertEquals("Nokia 6620", model.getDescription());
      assertEquals(man, model.getManufacturer());
      
      assertEquals(1, model.getTemplates().size());
      ClientProvTemplate template = model.getTemplates().get(0);
      assertNotNull(template);
      assertEquals("SyncDS_OMA_CP", template.getExternalID());
      assertEquals("OMA CP SyncDS Settings", template.getName());
      assertEquals("OMA_SyncDS", template.getProfileCategory().getName());
      assertEquals("This template based on OMA CP used for SyncDS settings.", template.getDescription());
      assertEquals("content", template.getContent());
      assertEquals(model, ((OTATemplateItem)template).getModel());
      
    }
    {
      ModelItem model = man.getModels().get(1);
      assertNotNull(model);
      assertEquals("6681", model.getID());
      assertEquals("6681C", model.getName());
      assertEquals("Nokia 6681", model.getDescription());
      assertEquals(man, model.getManufacturer());
      
      assertEquals(1, model.getTemplates().size());
      ClientProvTemplate template = model.getTemplates().get(0);
      assertNotNull(template);
      assertEquals("SyncDS_NOKIA_CP", template.getExternalID());
      assertEquals("Nokia OTA SyncDS Settings", template.getName());
      assertEquals("NOKIA 7.0 OTA SyncDS", template.getProfileCategory().getName());
      assertEquals("This template based on Nokia OTA 7.0 used for SyncDS settings.", template.getDescription());
      assertEquals("<?xml version='1.0' encoding='UTF-8'?>\r\n<wap-provisioningdoc version=\"1.1\"/>", template.getContent());
      assertEquals(model, ((OTATemplateItem)template).getModel());
    }
  }
  
  public void testFindTemplateByCategory() throws Exception {
    Properties props = new Properties();
    props.setProperty(OTAInventory.PROPERTY_FACTORY, OTAInventoryImpl.class.getName());
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR, AllTests.BASE_DIR + "/metadata/cp/test");
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_FILENAME, "test.1.otatemplate.inventory.xml");
    
    OTAInventory inventory = OTAInventory.newInstance(props);
    assertTrue(inventory instanceof OTAInventoryImpl);
    
    List<ClientProvTemplate> templates = inventory.getTemplates("nokia", "6681", "NOKIA 7.0 OTA SyncDS");
    assertNotNull(templates);
    assertEquals(1, templates.size());
  }
  
  public void testFindTemplateWithCompatiable() throws Exception {
    Properties props = new Properties();
    props.setProperty(OTAInventory.PROPERTY_FACTORY, OTAInventoryImpl.class.getName());
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR, AllTests.BASE_DIR + "/metadata/cp/test");
    props.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_FILENAME, "test.3.inventory.xml");
    
    OTAInventory inventory = OTAInventory.newInstance(props);
    assertTrue(inventory instanceof OTAInventoryImpl);
    {
      List<ClientProvTemplate> templates = inventory.getTemplates("nokia", "6620", "SyncDS");
      assertNotNull(templates);
      assertEquals(1, templates.size());
    }
    
    {
      List<ClientProvTemplate> templates = inventory.getTemplates("nokia", "6621", "SyncDS");
      assertNotNull(templates);
      assertEquals(1, templates.size());
    }

    {
      List<ClientProvTemplate> templates = inventory.getTemplates("nokia", "6622", "SyncDS");
      assertNotNull(templates);
      assertEquals(1, templates.size());
    }
    
    {
      List<ClientProvTemplate> templates = inventory.getTemplates("nokia", "6623", "SyncDS");
      assertNotNull(templates);
      assertEquals(1, templates.size());
    }
    
    {
      List<ClientProvTemplate> templates = inventory.getTemplates("nokia", "6624", "SyncDS");
      assertNotNull(templates);
      assertEquals(1, templates.size());
    }
  }
  
  public void testManufacturer() throws Exception {
    
  }

}
