/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/client/TestDMClientEmulatorImpl.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
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
package com.npower.dm.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import sync4j.framework.engine.dm.AddManagementOperation;
import sync4j.framework.engine.dm.DeleteManagementOperation;
import sync4j.framework.engine.dm.GetManagementOperation;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ReplaceManagementOperation;
import sync4j.framework.engine.dm.TreeNode;

import com.npower.dm.AllTests;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestDMClientEmulatorImpl extends TestCase {

  private static final String BASE_DIR = AllTests.BASE_DIR;

  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER";

  private static final String MODEL_External_ID = "TEST.MODEL";

  /**
   * DDF files
   */
  private static final String FILENAME_TEST_NOKIA_DDF_1 = BASE_DIR + "/metadata/ddf/test/test.nokia.1.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_2 = BASE_DIR + "/metadata/ddf/test/test.nokia.2.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_3 = BASE_DIR + "/metadata/ddf/test/test.nokia.3.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_4 = BASE_DIR + "/metadata/ddf/test/test.nokia.4.xml";

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

    this.tearDown();

    this.setupManufacturer(MANUFACTURER_External_ID);
    this.setupModel(MANUFACTURER_External_ID, MODEL_External_ID);

    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_1);
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_2);
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_3);
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_4);

  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    this.tearDownManufacturer(MANUFACTURER_External_ID);
  }

  private void setupManufacturer(String manufacturerExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer == null) {
          manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, manufacturerExternalID);
          factory.beginTransaction();
          modelBean.update(manufacturer);
          factory.commit();
        }
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

  }

  private void setupModel(String manufacturerExternalID, String modelExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        if (model == null) {
          model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
          factory.beginTransaction();
          modelBean.update(model);
          factory.commit();
        }
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

  }

  private void setupDDFTree(String manufacturerExternalID, String modelExternalID, String DDFFilename) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
      DDFTreeBean treeBean = factory.createDDFTreeBean();

      InputStream in = new FileInputStream(new File(DDFFilename));

      factory.beginTransaction();
      DDFTree tree = treeBean.parseDDFTree(in);
      Set<DDFNode> nodes = tree.getRootDDFNodes();
      assertEquals(nodes.size(), 1);
      in.close();

      treeBean.addDDFTree(tree);
      modelBean.attachDDFTree(model, tree.getID());

      factory.commit();
    } catch (Exception e) {
      factory.rollback();
      e.printStackTrace();
      throw e;
    } finally {
      factory.release();
    }
  }

  private void tearDownManufacturer(String manufacturerExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer != null) {
          factory.beginTransaction();
          modelBean.delete(manufacturer);
          factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      e.printStackTrace();
      throw e;
    } finally {
      factory.release();
    }

  }

  /**
   * testBuildDefaultRegistry
   * 
   * @throws Exception
   */
  public void testBuildDefaultRegistry() throws Exception {

    assertTrue(true);

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
  
        DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
        emulator.initialize();
  
        assertTrue(emulator.exists("./Email"));
        assertFalse(emulator.exists("./Email/MOTA1"));
        assertTrue(emulator.exists("./SyncML"));
        assertTrue(emulator.exists("./MMS/MMSAcc/Con"));
  
        assertFalse(emulator.exists("./Email/NO_EXISTS"));
        assertFalse(emulator.exists("./SyncML/NO_EXISTS"));
        assertFalse(emulator.exists("./SyncML/MMSAcc/NO_EXISTS"));
  
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * test delete
   * 
   * @throws Exception
   */
  public void testProcessorCaseDelete_200_404() throws Exception {

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {

      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);

      DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
      emulator.initialize();

      {
        List<ManagementOperation> addOperations = new ArrayList<ManagementOperation>();
        AddManagementOperation oper1 = new AddManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1", null));
        addOperations.add(oper1);
  
        // Add an item
        ManagementOperation[] operations = (ManagementOperation[]) addOperations.toArray(new ManagementOperation[0]);
        ManagementOperationResult[] results = emulator.process(operations);
        // Test results of add operation
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals(200, results[0].getStatusCode());
      }
      
      // Delete
      {
        List<ManagementOperation> deleteOperations = new ArrayList<ManagementOperation>();
        DeleteManagementOperation oper = new DeleteManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1", null));
        deleteOperations.add(oper);
        
        // Delete an item
        ManagementOperation[] operations = (ManagementOperation[]) deleteOperations.toArray(new ManagementOperation[0]);
        ManagementOperationResult[] results = emulator.process(operations);
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals(200, results[0].getStatusCode());
      }
      
      // Delete again
      {
        List<ManagementOperation> deleteOperations = new ArrayList<ManagementOperation>();
        DeleteManagementOperation oper = new DeleteManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1", null));
        deleteOperations.add(oper);
        
        // Delete an item
        ManagementOperation[] operations = (ManagementOperation[]) deleteOperations.toArray(new ManagementOperation[0]);
        ManagementOperationResult[] results = emulator.process(operations);
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals(404, results[0].getStatusCode());
      }
      
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * test delete
   * 
   * @throws Exception
   */
  public void testProcessorCaseDelete_425() throws Exception {

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {

      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);

      DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
      emulator.initialize();

      // Delete
      {
        List<ManagementOperation> deleteOperations = new ArrayList<ManagementOperation>();
        DeleteManagementOperation oper = new DeleteManagementOperation();
        oper.addTreeNode(new TreeNode("./Email", null));
        deleteOperations.add(oper);
        
        // Delete an item
        ManagementOperation[] operations = (ManagementOperation[]) deleteOperations.toArray(new ManagementOperation[0]);
        ManagementOperationResult[] results = emulator.process(operations);
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals(425, results[0].getStatusCode());
      }
            
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * test replace
   * 
   * @throws Exception
   */
  public void testProcessorCaseReplace_200() throws Exception {

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {

      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);

      DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
      emulator.initialize();

      {
        List<ManagementOperation> addOperations = new ArrayList<ManagementOperation>();
        AddManagementOperation oper1 = new AddManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1", null));
        addOperations.add(oper1);
  
        AddManagementOperation oper2 = new AddManagementOperation();
        oper2.addTreeNode(new TreeNode("./Email/MOTA1/PW", "aaaa"));
        addOperations.add(oper2);

        AddManagementOperation oper3 = new AddManagementOperation();
        oper3.addTreeNode(new TreeNode("./Email/MOTA1/UID", "bbbb"));
        addOperations.add(oper3);

        // Add an item
        ManagementOperation[] operations = (ManagementOperation[]) addOperations.toArray(new ManagementOperation[0]);
        ManagementOperationResult[] results = emulator.process(operations);
        // Test results of add operation
        assertNotNull(results);
        assertEquals(3, results.length);
        assertEquals(200, results[0].getStatusCode());
        assertEquals(200, results[1].getStatusCode());
        assertEquals(200, results[2].getStatusCode());
      }
      
      // Replace
      {
        List<ManagementOperation> operatoins = new ArrayList<ManagementOperation>();
        
        ReplaceManagementOperation oper1 = new ReplaceManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1/PW", "password"));
        operatoins.add(oper1);
        
        ReplaceManagementOperation oper2 = new ReplaceManagementOperation();
        oper2.addTreeNode(new TreeNode("./Email/MOTA1/UID", "username"));
        operatoins.add(oper2);
        
        // Delete an item
        ManagementOperationResult[] results = emulator.process((ManagementOperation[]) operatoins.toArray(new ManagementOperation[0]));
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(2, results.length);
        assertEquals(200, results[0].getStatusCode());
        assertEquals(200, results[1].getStatusCode());
        
        // Checking registry
        assertEquals("password", emulator.getValue("./Email/MOTA1/PW"));
        assertEquals("username", emulator.getValue("./Email/MOTA1/UID"));
      }
      
      // Replace again
      {
        List<ManagementOperation> operatoins = new ArrayList<ManagementOperation>();
        
        ReplaceManagementOperation oper1 = new ReplaceManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1/PW", "passwordNew"));
        operatoins.add(oper1);
        
        ReplaceManagementOperation oper2 = new ReplaceManagementOperation();
        oper2.addTreeNode(new TreeNode("./Email/MOTA1/UID", "usernameNew"));
        operatoins.add(oper2);
        
        // Delete an item
        ManagementOperationResult[] results = emulator.process((ManagementOperation[]) operatoins.toArray(new ManagementOperation[0]));
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(2, results.length);
        assertEquals(200, results[0].getStatusCode());
        assertEquals(200, results[1].getStatusCode());
        
        // Checking registry
        assertEquals("passwordNew", emulator.getValue("./Email/MOTA1/PW"));
        assertEquals("usernameNew", emulator.getValue("./Email/MOTA1/UID"));
      }
      
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * test replace
   * 
   * @throws Exception
   */
  public void testProcessorCaseReplace_404() throws Exception {

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {

      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);

      DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
      emulator.initialize();

      {
        List<ManagementOperation> addOperations = new ArrayList<ManagementOperation>();
        AddManagementOperation oper1 = new AddManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1", null));
        addOperations.add(oper1);
  
        AddManagementOperation oper2 = new AddManagementOperation();
        oper2.addTreeNode(new TreeNode("./Email/MOTA1/PW", "aaaa"));
        addOperations.add(oper2);

        AddManagementOperation oper3 = new AddManagementOperation();
        oper3.addTreeNode(new TreeNode("./Email/MOTA1/UID", "bbbb"));
        addOperations.add(oper3);

        // Add items
        ManagementOperation[] operations = (ManagementOperation[]) addOperations.toArray(new ManagementOperation[0]);
        ManagementOperationResult[] results = emulator.process(operations);
        // Test results of add operation
        assertNotNull(results);
        assertEquals(3, results.length);
        assertEquals(200, results[0].getStatusCode());
        assertEquals(200, results[1].getStatusCode());
        assertEquals(200, results[2].getStatusCode());
      }
      
      // Replace
      {
        List<ManagementOperation> operatoins = new ArrayList<ManagementOperation>();
        
        ReplaceManagementOperation oper1 = new ReplaceManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1/PW", "password"));
        operatoins.add(oper1);
        
        ReplaceManagementOperation oper2 = new ReplaceManagementOperation();
        oper2.addTreeNode(new TreeNode("./Email/MOTA1/UID", "username"));
        operatoins.add(oper2);
        
        // Delete an item
        ManagementOperationResult[] results = emulator.process((ManagementOperation[]) operatoins.toArray(new ManagementOperation[0]));
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(2, results.length);
        assertEquals(200, results[0].getStatusCode());
        assertEquals(200, results[1].getStatusCode());
        
        // Checking registry
        assertEquals("password", emulator.getValue("./Email/MOTA1/PW"));
        assertEquals("username", emulator.getValue("./Email/MOTA1/UID"));
      }
      
      // Replace again
      {
        List<ManagementOperation> operatoins = new ArrayList<ManagementOperation>();
        
        ReplaceManagementOperation oper1 = new ReplaceManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1/PW_NO_EXISTS", "passwordNew"));
        operatoins.add(oper1);
        
        ReplaceManagementOperation oper2 = new ReplaceManagementOperation();
        oper2.addTreeNode(new TreeNode("./Email/MOTA1/UID_NO_EXISTS", "usernameNew"));
        operatoins.add(oper2);
        
        // Delete an item
        ManagementOperationResult[] results = emulator.process((ManagementOperation[]) operatoins.toArray(new ManagementOperation[0]));
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(2, results.length);
        assertEquals(404, results[0].getStatusCode());
        assertEquals(404, results[1].getStatusCode());
        
        // Checking registry
        assertEquals("password", emulator.getValue("./Email/MOTA1/PW"));
        assertEquals("username", emulator.getValue("./Email/MOTA1/UID"));
      }
      
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * test get
   * 
   * @throws Exception
   */
  public void testProcessorCaseGet_Leaf() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {

      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);

      DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
      emulator.initialize();

      {
        List<ManagementOperation> addOperations = new ArrayList<ManagementOperation>();
        AddManagementOperation oper1 = new AddManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1", null));
        addOperations.add(oper1);
  
        AddManagementOperation oper2 = new AddManagementOperation();
        oper2.addTreeNode(new TreeNode("./Email/MOTA1/PW", "aaaa"));
        addOperations.add(oper2);

        AddManagementOperation oper3 = new AddManagementOperation();
        oper3.addTreeNode(new TreeNode("./Email/MOTA1/UID", "bbbb"));
        addOperations.add(oper3);

        // Add items
        ManagementOperation[] operations = (ManagementOperation[]) addOperations.toArray(new ManagementOperation[0]);
        ManagementOperationResult[] results = emulator.process(operations);
        // Test results of add operation
        assertNotNull(results);
        assertEquals(3, results.length);
        assertEquals(200, results[0].getStatusCode());
        assertEquals(200, results[1].getStatusCode());
        assertEquals(200, results[2].getStatusCode());
      }
      
      // Replace
      {
        List<ManagementOperation> operatoins = new ArrayList<ManagementOperation>();
        
        ReplaceManagementOperation oper1 = new ReplaceManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1/PW", "password"));
        operatoins.add(oper1);
        
        ReplaceManagementOperation oper2 = new ReplaceManagementOperation();
        oper2.addTreeNode(new TreeNode("./Email/MOTA1/UID", "username"));
        operatoins.add(oper2);
        
        // Delete an item
        ManagementOperationResult[] results = emulator.process((ManagementOperation[]) operatoins.toArray(new ManagementOperation[0]));
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(2, results.length);
        assertEquals(200, results[0].getStatusCode());
        assertEquals(200, results[1].getStatusCode());
        
        // Checking registry
        assertEquals("password", emulator.getValue("./Email/MOTA1/PW"));
        assertEquals("username", emulator.getValue("./Email/MOTA1/UID"));
      }
      
      // Get
      {
        List<ManagementOperation> operatoins = new ArrayList<ManagementOperation>();
        
        GetManagementOperation oper1 = new GetManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1/UID"));
        operatoins.add(oper1);
        
        ManagementOperationResult[] results = emulator.process((ManagementOperation[]) operatoins.toArray(new ManagementOperation[0]));
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals(200, results[0].getStatusCode());
        
        Map<String, Object> nodes = results[0].getNodes();
        TreeNode node = (TreeNode)nodes.get("./Email/MOTA1/UID");
        assertNotNull(node);
        assertEquals(DDFNode.DDF_FORMAT_CHR, node.getFormat());
        assertEquals("username", node.getValue());
        
        // Checking registry
        assertEquals("password", emulator.getValue("./Email/MOTA1/PW"));
        assertEquals("username", emulator.getValue("./Email/MOTA1/UID"));
      }
      
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

  }

  /**
   * test get
   * 
   * @throws Exception
   */
  public void testProcessorCaseGet_Node() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {

      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);

      DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
      emulator.initialize();

      {
        List<ManagementOperation> addOperations = new ArrayList<ManagementOperation>();
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/PtxtSAuth", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/UName", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/PW", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/UseSauth", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/UID", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/SauthPW", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/Name", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/Mrcv", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/SauthUID", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/Mpro", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/UseSecCon", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/UAddr", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/ToNapID", null));
          addOperations.add(oper);
        }
        {
          AddManagementOperation oper = new AddManagementOperation();
          oper.addTreeNode(new TreeNode("./Email/MOTA1/Msnd", null));
          addOperations.add(oper);
        }
  
        // Add items
        ManagementOperation[] operations = (ManagementOperation[]) addOperations.toArray(new ManagementOperation[0]);
        ManagementOperationResult[] results = emulator.process(operations);
        // Test results of add operation
        assertNotNull(results);
        assertEquals(15, results.length);
        assertEquals(200, results[0].getStatusCode());
        assertEquals(200, results[1].getStatusCode());
        assertEquals(200, results[2].getStatusCode());
        assertEquals(200, results[3].getStatusCode());
        assertEquals(200, results[4].getStatusCode());
        assertEquals(200, results[5].getStatusCode());
        assertEquals(200, results[6].getStatusCode());
        assertEquals(200, results[7].getStatusCode());
        assertEquals(200, results[8].getStatusCode());
        assertEquals(200, results[9].getStatusCode());
        assertEquals(200, results[10].getStatusCode());
        assertEquals(200, results[11].getStatusCode());
        assertEquals(200, results[12].getStatusCode());
        assertEquals(200, results[13].getStatusCode());
        assertEquals(200, results[14].getStatusCode());
      }
      
      // Replace
      {
        List<ManagementOperation> operatoins = new ArrayList<ManagementOperation>();
        
        ReplaceManagementOperation oper1 = new ReplaceManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1/PW", "password"));
        operatoins.add(oper1);
        
        ReplaceManagementOperation oper2 = new ReplaceManagementOperation();
        oper2.addTreeNode(new TreeNode("./Email/MOTA1/UID", "username"));
        operatoins.add(oper2);
        
        // Delete an item
        ManagementOperationResult[] results = emulator.process((ManagementOperation[]) operatoins.toArray(new ManagementOperation[0]));
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(2, results.length);
        assertEquals(200, results[0].getStatusCode());
        assertEquals(200, results[1].getStatusCode());
        
        // Checking registry
        assertEquals("password", emulator.getValue("./Email/MOTA1/PW"));
        assertEquals("username", emulator.getValue("./Email/MOTA1/UID"));
      }
      
      // Get
      {
        List<ManagementOperation> operatoins = new ArrayList<ManagementOperation>();
        
        GetManagementOperation oper1 = new GetManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1"));
        operatoins.add(oper1);
        
        ManagementOperationResult[] results = emulator.process((ManagementOperation[]) operatoins.toArray(new ManagementOperation[0]));
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals(200, results[0].getStatusCode());
        
        Map<String, Object> nodes = results[0].getNodes();
        TreeNode node = (TreeNode)nodes.get("./Email/MOTA1");
        assertNotNull(node);
        assertEquals(DDFNode.DDF_FORMAT_NODE, node.getFormat());
        Set<String> expected = new HashSet<String>();
        expected.addAll(Arrays.asList("PtxtSAuth/UName/PW/UseSauth/UID/SauthPW/Name/Mrcv/SauthUID/Mpro/UseSecCon/UAddr/ToNapID/Msnd".split("/")));
        Set<String> real = new HashSet<String>();
        real.addAll(Arrays.asList(node.getValue().toString().split("/")));
        
        assertTrue(expected.containsAll(real));
        assertTrue(real.containsAll(expected));
        
        // Checking registry
        assertEquals("password", emulator.getValue("./Email/MOTA1/PW"));
        assertEquals("username", emulator.getValue("./Email/MOTA1/UID"));
      }
      
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

  }
  /**
   * test get
   * 
   * @throws Exception
   */
  public void testProcessorCaseGet_425() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {

      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);

      DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
      emulator.initialize();

      {
        List<ManagementOperation> addOperations = new ArrayList<ManagementOperation>();
        AddManagementOperation oper1 = new AddManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1", null));
        addOperations.add(oper1);
  
        AddManagementOperation oper2 = new AddManagementOperation();
        oper2.addTreeNode(new TreeNode("./Email/MOTA1/PW", "aaaa"));
        addOperations.add(oper2);

        AddManagementOperation oper3 = new AddManagementOperation();
        oper3.addTreeNode(new TreeNode("./Email/MOTA1/UID", "bbbb"));
        addOperations.add(oper3);

        // Add items
        ManagementOperation[] operations = (ManagementOperation[]) addOperations.toArray(new ManagementOperation[0]);
        ManagementOperationResult[] results = emulator.process(operations);
        // Test results of add operation
        assertNotNull(results);
        assertEquals(3, results.length);
        assertEquals(200, results[0].getStatusCode());
        assertEquals(200, results[1].getStatusCode());
        assertEquals(200, results[2].getStatusCode());
      }
      
      // Replace
      {
        List<ManagementOperation> operatoins = new ArrayList<ManagementOperation>();
        
        ReplaceManagementOperation oper1 = new ReplaceManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1/PW", "password"));
        operatoins.add(oper1);
        
        ReplaceManagementOperation oper2 = new ReplaceManagementOperation();
        oper2.addTreeNode(new TreeNode("./Email/MOTA1/UID", "username"));
        operatoins.add(oper2);
        
        // Delete an item
        ManagementOperationResult[] results = emulator.process((ManagementOperation[]) operatoins.toArray(new ManagementOperation[0]));
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(2, results.length);
        assertEquals(200, results[0].getStatusCode());
        assertEquals(200, results[1].getStatusCode());
        
        // Checking registry
        assertEquals("password", emulator.getValue("./Email/MOTA1/PW"));
        assertEquals("username", emulator.getValue("./Email/MOTA1/UID"));
      }
      
      // Get
      {
        List<ManagementOperation> operatoins = new ArrayList<ManagementOperation>();
        
        GetManagementOperation oper1 = new GetManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1/PW", "passwordNew"));
        operatoins.add(oper1);
        
        ManagementOperationResult[] results = emulator.process((ManagementOperation[]) operatoins.toArray(new ManagementOperation[0]));
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals(425, results[0].getStatusCode());
        
        Map<String, Object> nodes = results[0].getNodes();
        TreeNode node = (TreeNode)nodes.get("./Email/MOTA1/PW");
        assertNotNull(node);

        // Checking registry
        assertEquals("password", emulator.getValue("./Email/MOTA1/PW"));
        assertEquals("username", emulator.getValue("./Email/MOTA1/UID"));
      }
      
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

  }

  /**
   * test get
   * 
   * @throws Exception
   */
  public void testProcessorCaseGet_404() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {

      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);

      DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
      emulator.initialize();

      
      // Get
      {
        List<ManagementOperation> operatoins = new ArrayList<ManagementOperation>();
        
        GetManagementOperation oper1 = new GetManagementOperation();
        oper1.addTreeNode(new TreeNode("./Email/MOTA1/PW_NO_EXISTS"));
        operatoins.add(oper1);
        
        ManagementOperationResult[] results = emulator.process((ManagementOperation[]) operatoins.toArray(new ManagementOperation[0]));
        // Test results of delete operation
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals(404, results[0].getStatusCode());
        
        Map<String, Object> nodes = results[0].getNodes();
        TreeNode node = (TreeNode)nodes.get("./Email/MOTA1/PW_NO_EXISTS");
        assertNotNull(node);
      }
      
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

  }

  /**
   * test add
   * 
   * @throws Exception
   */
  public void testProcessorCaseAdd_200_418() throws Exception {

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);

      DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
      emulator.initialize();

      List<ManagementOperation> addOperations = new ArrayList<ManagementOperation>();
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/PtxtSAuth", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/UName", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/PW", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/UseSauth", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/UID", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/SauthPW", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/Name", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/Mrcv", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/SauthUID", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/Mpro", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/UseSecCon", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/UAddr", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/ToNapID", null));
        addOperations.add(oper);
      }
      {
        AddManagementOperation oper = new AddManagementOperation();
        oper.addTreeNode(new TreeNode("./Email/MOTA1/Msnd", null));
        addOperations.add(oper);
      }

      // Add items
      ManagementOperation[] operations = (ManagementOperation[]) addOperations.toArray(new ManagementOperation[0]);
      ManagementOperationResult[] results = emulator.process(operations);
      // Test results of add operation
      assertNotNull(results);
      assertEquals(15, results.length);
      assertEquals(200, results[0].getStatusCode());
      assertEquals(200, results[1].getStatusCode());
      assertEquals(200, results[2].getStatusCode());
      assertEquals(200, results[3].getStatusCode());
      assertEquals(200, results[4].getStatusCode());
      assertEquals(200, results[5].getStatusCode());
      assertEquals(200, results[6].getStatusCode());
      assertEquals(200, results[7].getStatusCode());
      assertEquals(200, results[8].getStatusCode());
      assertEquals(200, results[9].getStatusCode());
      assertEquals(200, results[10].getStatusCode());
      assertEquals(200, results[11].getStatusCode());
      assertEquals(200, results[12].getStatusCode());
      assertEquals(200, results[13].getStatusCode());
      assertEquals(200, results[14].getStatusCode());

      // Test Emulator registry
      assertTrue(emulator.exists("./Email/MOTA1"));
      assertTrue(emulator.exists("./Email/MOTA1/PW"));
      assertTrue(emulator.exists("./Email/MOTA1/UID"));
      assertTrue(emulator.exists("./Email/MOTA1/Name"));
      assertTrue(emulator.exists("./Email/MOTA1/SauthUID"));
      assertTrue(emulator.exists("./Email/MOTA1/ToNapID"));
      assertTrue(emulator.exists("./Email/MOTA1/SauthPW"));
      assertTrue(emulator.exists("./Email/MOTA1/UName"));
      assertTrue(emulator.exists("./Email/MOTA1/UseSauth"));
      assertTrue(emulator.exists("./Email/MOTA1/UseSecCon"));
      assertTrue(emulator.exists("./Email/MOTA1/Msnd"));
      assertTrue(emulator.exists("./Email/MOTA1/UAddr"));
      assertTrue(emulator.exists("./Email/MOTA1/Mrcv"));
      assertTrue(emulator.exists("./Email/MOTA1/PtxtSAuth"));
      assertTrue(emulator.exists("./Email/MOTA1/Mpro"));
      
      // Test: 418 Exists
      results = emulator.process(operations);
      // Test results of operation
      assertNotNull(results);
      assertEquals(15, results.length);
      assertEquals(418, results[0].getStatusCode());
      assertEquals(418, results[1].getStatusCode());
      assertEquals(418, results[2].getStatusCode());
      assertEquals(418, results[3].getStatusCode());
      assertEquals(418, results[4].getStatusCode());
      assertEquals(418, results[5].getStatusCode());
      assertEquals(418, results[6].getStatusCode());
      assertEquals(418, results[7].getStatusCode());
      assertEquals(418, results[8].getStatusCode());
      assertEquals(418, results[9].getStatusCode());
      assertEquals(418, results[10].getStatusCode());
      assertEquals(418, results[11].getStatusCode());
      assertEquals(418, results[12].getStatusCode());
      assertEquals(418, results[13].getStatusCode());
      assertEquals(418, results[14].getStatusCode());
      
      
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * test add
   * 
   * @throws Exception
   */
  public void testProcessorCaseAdd_404() throws Exception {

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      ModelBean modelBean = factory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);

      DMClientEmulatorImpl emulator = new DMClientEmulatorImpl(model, new RegistryImpl());
      emulator.initialize();

      List<ManagementOperation> list = new ArrayList<ManagementOperation>();
      AddManagementOperation oper1 = new AddManagementOperation();
      oper1.addTreeNode(new TreeNode("./NoExists/MOTA1", null));
      list.add(oper1);

      // Test: 404 status
      ManagementOperation[] operations = (ManagementOperation[]) list.toArray(new ManagementOperation[0]);
      ManagementOperationResult[] results = emulator.process(operations);
      
      // Test results of operation
      assertNotNull(results);
      assertEquals(1, results.length);
      assertEquals(404, results[0].getStatusCode());
      
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestDMClientEmulatorImpl.class);
  }

}