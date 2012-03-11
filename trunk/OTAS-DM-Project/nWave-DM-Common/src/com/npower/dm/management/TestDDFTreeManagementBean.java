/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestDDFTreeManagementBean.java,v 1.25 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.25 $
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
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;

/**
 * @author Zhao DongLu
 * 
 */
public class TestDDFTreeManagementBean extends TestCase {

  //private static Log log = LogFactory.getLog(TestProfileTemplateManagementBean.class);

  private static final String BASE_DIR = AllTests.BASE_DIR;

  /**
   * Profile Template XML File for Basic Testing
   */
  private static final String FILENAME_TEST_BASIC_DDF = BASE_DIR + "/metadata/ddf/test/test.basic.xml";

  private static final String FILENAME_TEST_FINDNODEPATH_DDF = BASE_DIR + "/metadata/ddf/test/test.findnodepath.xml";

  /**
   * Profile Template XML File for Nokia Testing
   */
  private static final String FILENAME_TEST_NOKIA_DDF_1 = BASE_DIR + "/metadata/ddf/test/test.nokia.1.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_2 = BASE_DIR + "/metadata/ddf/test/test.nokia.2.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_3 = BASE_DIR + "/metadata/ddf/test/test.nokia.3.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_4 = BASE_DIR + "/metadata/ddf/test/test.nokia.4.xml";

  /**
   * Profile Template XML File for Motorola Testing
   */
  private static final String FILENAME_TEST_MOTOROLA_DDF_1 = BASE_DIR + "/metadata/ddf/test/test.motorola.1.xml";

  /**
   * Profile Template XML File for Microsoft Mobile 5.0 Testing
   */
  private static final String FILENAME_TEST_MICROSOFT_DDF_1 = BASE_DIR
      + "/metadata/ddf/test/test.windows.mobile.5_0.xml";

  /**
   * Profile Template XML File for SontEricsson Testing
   */
  private static final String FILENAME_TEST_SonyEricsson_DDF_1 = BASE_DIR + "/metadata/ddf/test/test.se.1.xml";

  /**
   * 
   */
  public TestDDFTreeManagementBean() {
    super();
  }

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

  /**
   * Test Parsing DDF Tree from basic DDF
   * 
   */
  public void testParsingDDFTreeBasic() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      InputStream in = new FileInputStream(new File(FILENAME_TEST_BASIC_DDF));
      DDFTree tree = bean.parseDDFTree(in);
      in.close();
      myAssertSets4Basic(tree);

    } catch (Exception e) {
      throw e;
    } finally {
      beanFactory.release();
    }
  }

  /**
   * assertions for basic DDF Testcases
   * 
   * @param tree
   */
  private void myAssertSets4Basic(DDFTree tree) throws Exception {

    assertEquals(tree.getManufacturer(), "--Nokia--");
    assertEquals(tree.getModel(), "--The device model--");
    assertEquals(tree.getSpecVersion(), "1.1");

    Set<DDFNode> nodes = tree.getRootDDFNodes();
    assertEquals(nodes.size(), 1);

    DDFNode node = nodes.iterator().next();
    assertEquals(node.getName(), ".");
    assertNull(node.getParentDDFNode());
    
    node = (DDFNode) node.getChildren().iterator().next();
    assertEquals(node.getName(), "AP");
    assertEquals(node.getTitle(), "Access Point object");
    assertEquals(node.getDescription(), "Access Point settings");
    assertEquals(node.getDefaultValue(), "80");
    assertEquals(node.getOccurrence(), "One");
    assertEquals(node.getIsDynamic(), false);
    assertEquals(node.getIsLeafNode(), false);
    assertEquals(node.getFormat(), "node");
    assertNotNull(node.getParentDDFNode());
    assertEquals(node.getDdfTree(), tree);
    assertTrue(node.imply(DDFNode.ACCESS_TYPE_GET));
    assertTrue(node.imply(DDFNode.ACCESS_TYPE_REPLACE));
    assertFalse(node.imply(DDFNode.ACCESS_TYPE_ADD));
    assertFalse(node.imply(DDFNode.ACCESS_TYPE_EXEC));
    assertFalse(node.imply(DDFNode.ACCESS_TYPE_DELETE));
    assertFalse(node.imply(DDFNode.ACCESS_TYPE_COPY));
    assertNull(node.getMIMETypeString());

    // Test Case #3
    Iterator<DDFNode> children = node.getChildren().iterator();
    DDFNode child = (DDFNode) children.next();
    assertEquals(child.getName(), "NAPID_CHR");
    assertEquals(child.getTitle(), "Network Access Point ID");
    assertEquals(child.getDescription(), null);
    assertEquals(child.getOccurrence(), "One");
    assertEquals(child.getIsDynamic(), true);
    assertEquals(child.getIsLeafNode(), false);
    assertEquals(child.getFormat(), "chr");
    assertEquals(child.getParentDDFNode(), node);
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_GET));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_REPLACE));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_ADD));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_EXEC));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_DELETE));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_COPY));
    assertEquals(child.getMIMETypeString(), "text/plain");

    // Test Case #4
    node = child;
    child = (DDFNode) node.getChildren().iterator().next();
    assertEquals(child.getName(), "NAPID_B64");
    assertEquals(child.getTitle(), "Network Access Point ID");
    assertEquals(child.getDescription(), null);
    assertEquals(child.getOccurrence(), "ZeroOrOne");
    assertEquals(child.getIsDynamic(), true);
    assertEquals(child.getIsLeafNode(), false);
    assertEquals(child.getFormat(), "b64");
    assertEquals(child.getParentDDFNode(), node);
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_GET));
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_EXEC));
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_DELETE));
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_COPY));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_REPLACE));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_ADD));
    assertEquals(child.getMIMETypeString(), "text/plain");

    // Test Case #5
    node = child;
    child = (DDFNode) node.getChildren().iterator().next();
    assertEquals(child.getName(), "NAPID_BIN");
    assertEquals(child.getTitle(), "Network Access Point ID");
    assertEquals(child.getDescription(), null);
    assertEquals(child.getOccurrence(), "ZeroOrMore");
    assertEquals(child.getIsDynamic(), true);
    assertEquals(child.getIsLeafNode(), false);
    assertEquals(child.getFormat(), "bin");
    assertEquals(child.getParentDDFNode(), node);
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_GET));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_REPLACE));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_ADD));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_EXEC));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_DELETE));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_COPY));
    assertEquals(child.getMIMETypeString(), "text/plain");

    // Test Case #6
    node = child;
    child = (DDFNode) node.getChildren().iterator().next();
    assertEquals(child.getName(), "NAPID_BOOL");
    assertEquals(child.getTitle(), "Network Access Point ID");
    assertEquals(child.getDescription(), null);
    assertEquals(child.getOccurrence(), "OneOrMore");
    assertEquals(child.getIsDynamic(), true);
    assertEquals(child.getIsLeafNode(), false);
    assertEquals(child.getFormat(), "bool");
    assertEquals(child.getParentDDFNode(), node);
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_GET));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_REPLACE));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_ADD));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_EXEC));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_DELETE));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_COPY));
    assertEquals(child.getMIMETypeString(), "text/plain");

    // Test Case #7
    node = child;
    child = (DDFNode) node.getChildren().iterator().next();
    assertEquals(child.getName(), "NAPID_INT");
    assertEquals(child.getTitle(), "Network Access Point ID");
    assertEquals(child.getDescription(), null);
    assertEquals(child.getOccurrence(), "ZeroOrN");
    assertEquals(child.getIsDynamic(), true);
    assertEquals(child.getIsLeafNode(), false);
    assertEquals(child.getFormat(), "int");
    assertEquals(child.getParentDDFNode(), node);
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_GET));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_REPLACE));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_ADD));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_EXEC));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_DELETE));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_COPY));
    assertEquals(child.getMIMETypeString(), "text/plain");

    // Test Case #8
    node = child;
    child = (DDFNode) node.getChildren().iterator().next();
    assertEquals(child.getName(), "NAPID_XML");
    assertEquals(child.getTitle(), "Network Access Point ID");
    assertEquals(child.getDescription(), null);
    assertEquals(child.getOccurrence(), "OneOrN");
    assertEquals(child.getIsDynamic(), true);
    assertEquals(child.getIsLeafNode(), false);
    assertEquals(child.getFormat(), "xml");
    assertEquals(child.getParentDDFNode(), node);
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_GET));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_REPLACE));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_ADD));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_EXEC));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_DELETE));
    assertFalse(child.imply(DDFNode.ACCESS_TYPE_COPY));
    assertEquals(child.getMIMETypeString(), "text/plain");

    // Test Case #9
    node = child;
    child = (DDFNode) node.getChildren().iterator().next();
    assertEquals(child.getName(), "NAPID_NULL");
    assertEquals(child.getTitle(), "Network Access Point ID");
    assertEquals(child.getDescription(), null);
    assertEquals(child.getOccurrence(), "One");
    assertEquals(child.getIsDynamic(), true);
    assertEquals(child.getIsLeafNode(), true);
    assertEquals(child.getFormat(), null);
    assertEquals(child.getParentDDFNode(), node);
    assertEquals(child.getChildren().size(), 0);
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_GET));
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_REPLACE));
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_ADD));
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_EXEC));
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_DELETE));
    assertTrue(child.imply(DDFNode.ACCESS_TYPE_COPY));
    assertEquals(child.getMIMETypeString(), "text/xml");
  }

  /**
   * TestCase for addDDFTree() method
   * 
   */
  public void testAddDDFTree() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      InputStream in = new FileInputStream(new File(FILENAME_TEST_BASIC_DDF));
      DDFTree tree = bean.parseDDFTree(in);
      in.close();

      this.myAssertSets4Basic(tree);

      // Add a DDFTreeEntity into DM Inventory
      beanFactory.beginTransaction();
      bean.addDDFTree(tree);
      beanFactory.commit();

      // Load the DDF tree from DM Inventory
      tree = bean.getDDFTreeByID(tree.getID() + "");
      // Checking it
      this.myAssertSets4Basic(tree);

      // Delete the DDFtree from DM inventory
      beanFactory.beginTransaction();
      bean.delete(tree);
      beanFactory.commit();

    } catch (Exception e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }

  /**
   * Testcase: Compliance with Nokia DDF files: DDF.1
   * 
   */
  public void testParsingDDFTree_Nokia_1() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      InputStream in = new FileInputStream(new File(FILENAME_TEST_NOKIA_DDF_1));
      DDFTree tree = bean.parseDDFTree(in);
      assertEquals(tree.getManufacturer(), "--Nokia--");
      assertEquals(tree.getModel(), "--The device model--");
      assertEquals(tree.getSpecVersion(), "1.1");

      Set<DDFNode> nodes = tree.getRootDDFNodes();
      assertEquals(nodes.size(), 1);

      DDFNode node = (DDFNode) nodes.iterator().next();
      assertEquals(node.getName(), ".");
      assertNull(node.getParentDDFNode());
      
      node = (DDFNode) node.getChildren().iterator().next();
      assertEquals(node.getName(), "Email");
      assertEquals(node.getTitle(), "Email object");
      assertEquals(node.getDescription(), "Email settings");
      assertEquals(node.getOccurrence(), "One");
      assertEquals(node.getIsDynamic(), false);
      assertEquals(node.getIsLeafNode(), false);
      assertEquals(node.getFormat(), "node");
      assertNotNull(node.getParentDDFNode());
      assertEquals(node.getDdfTree(), tree);

      in.close();
    } catch (Exception e) {
      throw e;
    } finally {
      beanFactory.release();
    }
  }

  /**
   * Testcase: Compliance with Nokia DDF files: DDF.1
   * 
   */
  public void testParsingDDFTree_Nokia_2() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      InputStream in = new FileInputStream(new File(FILENAME_TEST_NOKIA_DDF_2));
      DDFTree tree = bean.parseDDFTree(in);
      assertEquals(tree.getManufacturer(), "--Nokia--");
      assertEquals(tree.getModel(), "--The device model--");
      assertEquals(tree.getSpecVersion(), "1.1");

      Set<DDFNode> nodes = tree.getRootDDFNodes();
      assertEquals(nodes.size(), 1);

      DDFNode node = (DDFNode) nodes.iterator().next();
      assertEquals(node.getName(), ".");
      assertNull(node.getParentDDFNode());
      
      node = (DDFNode) node.getChildren().iterator().next();
      assertEquals(node.getName(), "MMS");
      assertEquals(node.getTitle(), "MMS object");
      assertEquals(node.getDescription(), "MMS settings");
      assertEquals(node.getOccurrence(), "One");
      assertEquals(node.getIsDynamic(), false);
      assertEquals(node.getIsLeafNode(), false);
      assertEquals(node.getFormat(), "node");
      assertNotNull(node.getParentDDFNode());
      assertEquals(node.getDdfTree(), tree);

      in.close();
    } catch (Exception e) {
      throw e;
    } finally {
      beanFactory.release();
    }
  }

  /**
   * Testcase: Compliance with Nokia DDF files: DDF.1
   * 
   */
  public void testParsingDDFTree_Nokia_3() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      InputStream in = new FileInputStream(new File(FILENAME_TEST_NOKIA_DDF_3));
      DDFTree tree = bean.parseDDFTree(in);
      assertEquals(tree.getManufacturer(), "--Nokia--");
      assertEquals(tree.getModel(), "--The device model--");
      assertEquals(tree.getSpecVersion(), "1.1");

      Set<DDFNode> nodes = tree.getRootDDFNodes();
      assertEquals(nodes.size(), 1);

      DDFNode node = (DDFNode) nodes.iterator().next();
      assertEquals(node.getName(), ".");
      assertNull(node.getParentDDFNode());
      
      node = (DDFNode) node.getChildren().iterator().next();
      assertEquals(node.getName(), "AP");
      assertEquals(node.getTitle(), "Access Point object");
      assertEquals(node.getDescription(), "Access Point settings");
      assertEquals(node.getOccurrence(), "One");
      assertEquals(node.getIsDynamic(), false);
      assertEquals(node.getIsLeafNode(), false);
      assertEquals(node.getFormat(), "node");
      assertNotNull(node.getParentDDFNode());
      assertEquals(node.getDdfTree(), tree);

      in.close();
    } catch (Exception e) {
      throw e;
    } finally {
      beanFactory.release();
    }
  }

  /**
   * Testcase: Compliance with Nokia DDF files: DDF.1
   * 
   */
  public void testParsingDDFTree_Nokia_4() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      InputStream in = new FileInputStream(new File(FILENAME_TEST_NOKIA_DDF_4));
      DDFTree tree = bean.parseDDFTree(in);
      assertEquals(tree.getManufacturer(), "--Nokia--");
      assertEquals(tree.getModel(), "--The device model--");
      assertEquals(tree.getSpecVersion(), "1.1");

      Set<DDFNode> nodes = tree.getRootDDFNodes();
      assertEquals(nodes.size(), 1);

      DDFNode node = (DDFNode) nodes.iterator().next();
      assertEquals(node.getName(), ".");
      assertNull(node.getParentDDFNode());
      
      node = (DDFNode) node.getChildren().iterator().next();
      assertEquals(node.getName(), "SyncML");
      assertEquals(node.getTitle(), "SyncML object");
      assertEquals(node.getDescription(), "SyncML settings");
      assertEquals(node.getOccurrence(), "One");
      assertEquals(node.getIsDynamic(), false);
      assertEquals(node.getIsLeafNode(), false);
      assertEquals(node.getFormat(), "node");
      assertNotNull(node.getParentDDFNode());
      assertEquals(node.getDdfTree(), tree);

      in.close();
    } catch (Exception e) {
      throw e;
   } finally {
      beanFactory.release();
    }
  }

  /**
   * Testcase: Compliance with Motorola DDF files: DDF.1
   * 
   */
  public void testParsingDDFTree_Motorola_1() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      InputStream in = new FileInputStream(new File(FILENAME_TEST_MOTOROLA_DDF_1));
      DDFTree tree = bean.parseDDFTree(in);
      assertEquals(tree.getManufacturer(), "--Motorola--");
      assertEquals(tree.getModel(), "--V600,V500,V400,V300--");
      assertEquals(tree.getSpecVersion(), "1.1.2");

      Set<DDFNode> nodes = tree.getRootDDFNodes();
      assertEquals(nodes.size(), 1);

      DDFNode rootNode = (DDFNode) nodes.iterator().next();
      assertEquals(rootNode.getName(), ".");
      assertNull(rootNode.getParentDDFNode());
      assertEquals(rootNode.getName(), ".");
      assertNull(rootNode.getParentDDFNode());
      
      nodes = rootNode.getChildren();
      assertEquals(nodes.size(), 4);

      int total = 0;
      for (Iterator<DDFNode> i = nodes.iterator(); i.hasNext();) {
        DDFNode node = (DDFNode) i.next();
        String nodeName = node.getName();
        if (nodeName.equals("SyncML")) {
          assertEquals(node.getName(), "SyncML");
          assertEquals(node.getTitle(), "SyncML node");
          assertEquals(node.getDescription(), "SyncML settings");
          assertEquals(node.getOccurrence(), "One");
          assertEquals(node.getIsDynamic(), false);
          assertEquals(node.getIsLeafNode(), false);
          assertEquals(node.getFormat(), "node");
          assertNotNull(node.getParentDDFNode());
          assertEquals(node.getDdfTree(), tree);
          assertTrue(node.imply(DDFNode.ACCESS_TYPE_GET));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_REPLACE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_ADD));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_EXEC));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_DELETE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_COPY));

          total++;
        } else if (nodeName.equals("DevInfo")) {
          assertEquals(node.getName(), "DevInfo");
          assertEquals(node.getTitle(), "The interior node holding all devinfo objects");
          assertEquals(node.getDescription(), null);
          assertEquals(node.getOccurrence(), null);
          assertEquals(node.getIsDynamic(), false);
          assertEquals(node.getIsLeafNode(), false);
          assertEquals(node.getFormat(), "node");
          assertNotNull(node.getParentDDFNode());
          assertEquals(node.getDdfTree(), tree);
          assertTrue(node.imply(DDFNode.ACCESS_TYPE_GET));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_REPLACE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_ADD));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_EXEC));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_DELETE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_COPY));

          total++;
        } else if (nodeName.equals("DevDetail")) {
          assertEquals(node.getName(), "DevDetail");
          assertEquals(node.getTitle(), "The interior node holding all DevDetail nodes");
          assertEquals(node.getDescription(), null);
          assertEquals(node.getOccurrence(), null);
          assertEquals(node.getIsDynamic(), false);
          assertEquals(node.getIsLeafNode(), false);
          assertEquals(node.getFormat(), "node");
          assertNotNull(node.getParentDDFNode());
          assertEquals(node.getDdfTree(), tree);
          assertTrue(node.imply(DDFNode.ACCESS_TYPE_GET));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_REPLACE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_ADD));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_EXEC));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_DELETE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_COPY));

          total++;
        } else if (nodeName.equals("Application")) {
          assertEquals(node.getName(), "Application");
          assertEquals(node.getDescription(), null);
          assertEquals(node.getOccurrence(), "One");
          assertEquals(node.getIsDynamic(), false);
          assertEquals(node.getIsLeafNode(), false);
          assertEquals(node.getFormat(), "node");
          assertNotNull(node.getParentDDFNode());
          assertEquals(node.getDdfTree(), tree);
          assertTrue(node.imply(DDFNode.ACCESS_TYPE_GET));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_REPLACE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_ADD));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_EXEC));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_DELETE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_COPY));

          total++;
        }
      }
      assertEquals(total, 4);

      in.close();
    } catch (Exception e) {
      throw e;
    } finally {
      beanFactory.release();
    }
  }

  /**
   * Testcase: Compliance with Microsoft DDF files: DDF.1
   * 
   */
  public void testParsingDDFTree_Microsoft_1() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      InputStream in = new FileInputStream(new File(FILENAME_TEST_MICROSOFT_DDF_1));
      DDFTree tree = bean.parseDDFTree(in);
      assertEquals(tree.getManufacturer(), "--Microsoft--");
      assertEquals(tree.getModel(), "--Windows Mobile 5.0--");
      assertEquals(tree.getSpecVersion(), "1.1.2");

      Set<DDFNode> nodes = tree.getRootDDFNodes();
      assertEquals(nodes.size(), 1);

      DDFNode node = (DDFNode) nodes.iterator().next();
      assertEquals(node.getName(), ".");
      assertNull(node.getParentDDFNode());
      
      node = (DDFNode) node.getChildren().iterator().next();
      assertEquals(node.getName(), "DevDetail");
      assertEquals(node.getTitle(), null);
      assertEquals(node.getDescription(), "");
      assertEquals(node.getOccurrence(), "One");
      assertEquals(node.getIsDynamic(), false);
      assertEquals(node.getIsLeafNode(), false);
      assertEquals(node.getFormat(), "node");
      assertNotNull(node.getParentDDFNode());
      assertEquals(node.getDdfTree(), tree);
      assertTrue(node.imply(DDFNode.ACCESS_TYPE_GET));
      assertFalse(node.imply(DDFNode.ACCESS_TYPE_REPLACE));
      assertFalse(node.imply(DDFNode.ACCESS_TYPE_ADD));
      assertFalse(node.imply(DDFNode.ACCESS_TYPE_EXEC));
      assertFalse(node.imply(DDFNode.ACCESS_TYPE_DELETE));
      assertFalse(node.imply(DDFNode.ACCESS_TYPE_COPY));

      in.close();
    } catch (Exception e) {
      throw e;
    } finally {
      beanFactory.release();
    }
  }

  /**
   * Testcase: Compliance with Motorola DDF files: DDF.1
   * 
   */
  public void testParsingDDFTree_SonyEricsson_1() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      InputStream in = new FileInputStream(new File(FILENAME_TEST_SonyEricsson_DDF_1));
      DDFTree tree = bean.parseDDFTree(in);
      assertEquals(tree.getManufacturer(), "SonyEricsson");
      assertEquals(tree.getModel(), "W550i");
      assertEquals(tree.getSpecVersion(), "1.1.2");

      Set<DDFNode> nodes = tree.getRootDDFNodes();
      assertEquals(nodes.size(), 1);

      DDFNode rootNode = (DDFNode) nodes.iterator().next();
      assertEquals(rootNode.getName(), ".");
      assertNull(rootNode.getParentDDFNode());
      
      nodes = rootNode.getChildren();
      assertEquals(nodes.size(), 6);

      int total = 0;
      for (Iterator<DDFNode> i = nodes.iterator(); i.hasNext();) {
        DDFNode node = (DDFNode) i.next();
        String nodeName = node.getName();
        if (nodeName.equals("Com.SonyEricsson")) {
          assertEquals(node.getName(), "Com.SonyEricsson");
          assertEquals(node.getTitle(), "");
          assertEquals(node.getDescription(), "Container for SonyEricsson proprietary management objects.");
          assertEquals(node.getOccurrence(), "One");
          assertEquals(node.getIsDynamic(), false);
          assertEquals(node.getIsLeafNode(), false);
          assertEquals(node.getFormat(), "node");
          assertNotNull(node.getParentDDFNode());
          assertEquals(node.getDdfTree(), tree);
          assertTrue(node.imply(DDFNode.ACCESS_TYPE_GET));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_REPLACE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_ADD));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_EXEC));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_DELETE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_COPY));

          total++;
        } else if (nodeName.equals("DevInfo")) {
          assertEquals(node.getName(), "DevInfo");
          assertEquals(node.getTitle(), "");
          assertEquals(node.getDescription(), "Standardized Object for device information.");
          assertEquals(node.getOccurrence(), "One");
          assertEquals(node.getIsDynamic(), false);
          assertEquals(node.getIsLeafNode(), false);
          assertEquals(node.getFormat(), "node");
          assertNotNull(node.getParentDDFNode());
          assertEquals(node.getDdfTree(), tree);
          assertTrue(node.imply(DDFNode.ACCESS_TYPE_GET));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_REPLACE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_ADD));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_EXEC));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_DELETE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_COPY));

          total++;
        } else if (nodeName.equals("DevDetail")) {
          assertEquals(node.getName(), "DevDetail");
          assertEquals(node.getTitle(), "");
          assertEquals(node.getDescription(), "Standardized Object for detailed device information.");
          assertEquals(node.getOccurrence(), "One");
          assertEquals(node.getIsDynamic(), false);
          assertEquals(node.getIsLeafNode(), false);
          assertEquals(node.getFormat(), "node");
          assertNotNull(node.getParentDDFNode());
          assertEquals(node.getDdfTree(), tree);
          assertTrue(node.imply(DDFNode.ACCESS_TYPE_GET));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_REPLACE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_ADD));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_EXEC));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_DELETE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_COPY));

          total++;
        } else if (nodeName.equals("AP")) {
          assertEquals(node.getName(), "AP");
          assertEquals(node.getDescription(),
              "Container for Access Point settings, i.e. data accounts and internet profiles (proxy).");
          assertEquals(node.getOccurrence(), "One");
          assertEquals(node.getIsDynamic(), false);
          assertEquals(node.getIsLeafNode(), false);
          assertEquals(node.getFormat(), "node");
          assertNotNull(node.getParentDDFNode());
          assertEquals(node.getDdfTree(), tree);
          assertTrue(node.imply(DDFNode.ACCESS_TYPE_GET));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_REPLACE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_ADD));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_EXEC));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_DELETE));
          assertFalse(node.imply(DDFNode.ACCESS_TYPE_COPY));

          total++;
        }
      }
      assertEquals(total, 4);

      in.close();
    } catch (Exception e) {
      throw e;
    } finally {
      beanFactory.release();
    }
  }

  public void testModelDDFTreeAttach() throws Exception {
    long now = System.currentTimeMillis();

    String manuName = "TestManu4DDF" + now;
    String manuExternalID = "TestManu4DDF" + now;

    String name = "test_model_ddf_" + now;
    String manuModelID = "test_ddf_" + now;
    boolean supportedDownloadMethods = true;
    boolean omaEnable = true;
    boolean plainProfile = true;
    boolean useEncForOmaMsg = true;
    boolean useNextNoncePerPkg = true;

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      beanFactory.beginTransaction();
      
      // Create ManufacturerEntity
      ModelBean modelBean = beanFactory.createModelBean();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manuExternalID);
      if (manufacturer != null) {
        modelBean.delete(manufacturer);
      }

      manufacturer = modelBean.newManufacturerInstance(manuName, manuExternalID, manuName);
      modelBean.update(manufacturer);

      Model model = modelBean.newModelInstance(manufacturer, manuModelID, name, supportedDownloadMethods, omaEnable,
          plainProfile, useEncForOmaMsg, useNextNoncePerPkg);

      modelBean.update(model);

      // Import tree#1
      InputStream in = new FileInputStream(new File(FILENAME_TEST_NOKIA_DDF_1));
      DDFTree tree1 = bean.parseDDFTree(in);
      in.close();
      assertEquals(tree1.getManufacturer(), "--Nokia--");
      assertEquals(tree1.getModel(), "--The device model--");
      assertEquals(tree1.getSpecVersion(), "1.1");
      Set<DDFNode> nodes = tree1.getRootDDFNodes();
      assertEquals(nodes.size(), 1);
      bean.addDDFTree(tree1);

      // Import tree#2
      in = new FileInputStream(new File(FILENAME_TEST_NOKIA_DDF_2));
      DDFTree tree2 = bean.parseDDFTree(in);
      assertEquals(tree2.getManufacturer(), "--Nokia--");
      assertEquals(tree2.getModel(), "--The device model--");
      assertEquals(tree2.getSpecVersion(), "1.1");
      nodes = tree2.getRootDDFNodes();
      assertEquals(nodes.size(), 1);
      bean.addDDFTree(tree2);

      // Import tree#3
      in = new FileInputStream(new File(FILENAME_TEST_NOKIA_DDF_3));
      DDFTree tree3 = bean.parseDDFTree(in);
      assertEquals(tree3.getManufacturer(), "--Nokia--");
      assertEquals(tree3.getModel(), "--The device model--");
      assertEquals(tree3.getSpecVersion(), "1.1");
      bean.addDDFTree(tree3);

      nodes = tree3.getRootDDFNodes();
      assertEquals(nodes.size(), 1);

      // Assertion ...
      modelBean.attachDDFTree(model, tree1.getID());
      assertEquals(model.getDDFTrees().size(), 1);

      modelBean.attachDDFTree(model, tree1.getID());
      assertEquals(model.getDDFTrees().size(), 1);

      modelBean.attachDDFTree(model, tree2.getID());
      assertEquals(model.getDDFTrees().size(), 2);

      modelBean.attachDDFTree(model, tree2.getID());
      assertEquals(model.getDDFTrees().size(), 2);

      modelBean.attachDDFTree(model, tree3.getID());
      assertEquals(model.getDDFTrees().size(), 3);

      modelBean.attachDDFTree(model, tree3.getID());
      assertEquals(model.getDDFTrees().size(), 3);

      // Test cases for findDDFNodeByPath(ModelEntity, nodePath)
      {
        String nodePath = "MMS/MMSAcc/Name";
        DDFNode node = bean.findDDFNodeByNodePath(model, nodePath);
        assertEquals(node.getName(), "Name");
        assertEquals(node.getTitle(), "Dispalyable name for settings set");
      }
      {
        String nodePath = "Email/${NodeName}/Name";
        DDFNode node = bean.findDDFNodeByNodePath(model, nodePath);
        assertEquals(node.getName(), "Name");
        assertEquals(node.getTitle(), "Account Name");
      }
      {
        String nodePath = "AP/${NodeName}/Px";
        DDFNode node = bean.findDDFNodeByNodePath(model, nodePath);
        assertEquals(node.getName(), "Px");
        assertEquals(node.getTitle(), "A collection of all Proxy objects");
      }
      {
        String nodePath = "AP/${NodeName}/Px/${NodeName}";
        DDFNode node = bean.findDDFNodeByNodePath(model, nodePath);
        assertEquals(node.getName(), null);
        assertEquals(node.getTitle(), "The \"name \" node for a Px object");
      }
      {
        String nodePath = "AP/${NodeName}/Px/${NodeName}/Name";
        DDFNode node = bean.findDDFNodeByNodePath(model, nodePath);
        assertEquals(node.getName(), "Name");
        assertEquals(node.getTitle(), "User displayable name for the Proxy");
      }
      {
        String nodePath = "AP/${NodeName}/Px/${NodeName}/PxID";
        DDFNode node = bean.findDDFNodeByNodePath(model, nodePath);
        assertEquals(node.getName(), "PxID");
        assertEquals(node.getTitle(), "Defines Logical Proxy entity");
      }
      {
        String nodePath = "AP/${NodeName}/Px/${NodeName}/PxID/AAAA";
        DDFNode node = bean.findDDFNodeByNodePath(model, nodePath);
        assertNull(node);
      }

      modelBean.dettachDDFTree(model, tree1.getID());
      assertEquals(model.getDDFTrees().size(), 2);

      // modelBean.dettachDDFTree(model, tree1.getDdfTreeId());
      // assertEquals(model.getModelDDFTrees().size(), 2);

      modelBean.dettachDDFTree(model, tree2.getID());
      assertEquals(model.getDDFTrees().size(), 1);

      // modelBean.dettachDDFTree(model, tree2.getDdfTreeId());
      // assertEquals(model.getModelDDFTrees().size(), 1);

      modelBean.dettachDDFTree(model, tree3.getID());
      assertEquals(model.getDDFTrees().size(), 0);

      // modelBean.dettachDDFTree(model, tree3.getDdfTreeId());
      // assertEquals(model.getModelDDFTrees().size(), 0);

      // Clean up
      bean.delete(tree1);
      bean.delete(tree2);
      bean.delete(tree3);

      modelBean.delete(model);
      modelBean.delete(manufacturer);
      
      beanFactory.commit();

    } catch (Exception e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }
  }

  /**
   * Test findDDFNodeByNodePath() in DDFTreeManagementBeanImpl
   * 
   */
  public void testFindDDFNodeByNodePath() throws Exception {
    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    DDFTreeBean bean = beanFactory.createDDFTreeBean();
    try {
      InputStream in = new FileInputStream(new File(FILENAME_TEST_FINDNODEPATH_DDF));
      DDFTree tree = bean.parseDDFTree(in);
      in.close();

      beanFactory.beginTransaction();
      bean.addDDFTree(tree);
      {
        String nodePath = "./AP";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "AP");
      }

      {
        String nodePath = "/AP";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "AP");
      }

      {
        String nodePath = "AP";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "AP");
      }

      {
        String nodePath = "./AP/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "AP");
      }

      {
        String nodePath = "/AP/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "AP");
      }

      {
        String nodePath = "AP/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "AP");
      }

      {
        String nodePath = "APPPP";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }

      {
        String nodePath = "/APPPP";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }

      {
        String nodePath = "./APPPP";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }

      {
        String nodePath = "APPPP/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }

      {
        String nodePath = "/APPPP/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }

      {
        String nodePath = "./APPPP/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }

      {
        String nodePath = "AP/NAPID_CHR";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_CHR");
      }

      {
        String nodePath = "AP/NAPID_CHR/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_CHR");
      }

      {
        String nodePath = "AP/NAPID_CHR/NAPID_B64";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_B64");
      }

      {
        String nodePath = "./AP/NAPID_CHR/NAPID_B64/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_B64");
      }

      {
        String nodePath = "./AP/NAPID_CHR/${NodeName}";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), null);
        assertEquals(node.getTitle(), "${NodeName} Title");
      }

      {
        String nodePath = "./AP/NAPID_CHR/${NodeName}/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), null);
        assertEquals(node.getTitle(), "${NodeName} Title");
      }

      {
        String nodePath = "AP/NAPID_CHR/${NodeName}";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), null);
        assertEquals(node.getTitle(), "${NodeName} Title");
      }

      {
        String nodePath = "AP/NAPID_CHR/${NodeName}/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), null);
        assertEquals(node.getTitle(), "${NodeName} Title");
      }

      {
        String nodePath = "/AP/NAPID_CHR/${NodeName}";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), null);
        assertEquals(node.getTitle(), "${NodeName} Title");
      }

      {
        String nodePath = "/AP/NAPID_CHR/${NodeName}/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), null);
        assertEquals(node.getTitle(), "${NodeName} Title");
      }

      {
        String nodePath = "./AP/NAPID_CHR/${NodeName}/NAPID_NULL";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_NULL");
        assertEquals(node.getTitle(), "Network Access Point ID");
      }

      {
        String nodePath = "./AP/NAPID_CHR/${NodeName}/NAPID_NULL/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_NULL");
        assertEquals(node.getTitle(), "Network Access Point ID");
      }

      {
        String nodePath = "/AP/NAPID_CHR/${NodeName}/NAPID_NULL";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_NULL");
        assertEquals(node.getTitle(), "Network Access Point ID");
      }

      {
        String nodePath = "/AP/NAPID_CHR/${NodeName}/NAPID_NULL/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_NULL");
        assertEquals(node.getTitle(), "Network Access Point ID");
      }

      {
        String nodePath = "AP/NAPID_CHR/${NodeName}/NAPID_NULL";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_NULL");
        assertEquals(node.getTitle(), "Network Access Point ID");
      }

      {
        String nodePath = "AP/NAPID_CHR/${NodeName}/NAPID_NULL/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_NULL");
        assertEquals(node.getTitle(), "Network Access Point ID");
      }

      {
        String nodePath = "./AP/NAPID_CHR/${NodeName}/NAPID_NULL/ABCD/EFGH";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }
      
      {
        String nodePath = "./AP/NAPID_CHR/${NodeName}/NAPID_NULL/ABCD/EFGH/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }
      
      {
        String nodePath = "/AP/NAPID_CHR/${NodeName}/NAPID_NULL/ABCD/EFGH";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }
      
      {
        String nodePath = "/AP/NAPID_CHR/${NodeName}/NAPID_NULL/ABCD/EFGH/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }
      
      {
        String nodePath = "AP/NAPID_CHR/${NodeName}/NAPID_NULL/ABCD/EFGH";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }
      
      {
        String nodePath = "AP/NAPID_CHR/${NodeName}/NAPID_NULL/ABCD/EFGH/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }
      
      // Constance name mode
      
      {
        String nodePath = "AP/NAPID_CHR/${NodeName:AAAAA}/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), null);
        assertEquals(node.getTitle(), "${NodeName} Title");
      }
      
      {
        String nodePath = "AP/NAPID_CHR/${NodeName:}/";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), null);
        assertEquals(node.getTitle(), "${NodeName} Title");
      }
      
      {
        String nodePath = "AP/NAPID_CHR/${NodeName:BND6473826}/NAPID_NULL";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_NULL");
        assertEquals(node.getTitle(), "Network Access Point ID");
      }

      {
        String nodePath = "AP/NAPID_CHR/${NodeName:46387267}/NAPID_NULL";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_NULL");
        assertEquals(node.getTitle(), "Network Access Point ID");
      }

      {
        String nodePath = "AP/NAPID_CHR/${NodeName:}/NAPID_NULL";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertEquals(node.getName(), "NAPID_NULL");
        assertEquals(node.getTitle(), "Network Access Point ID");
      }

      {
        String nodePath = "AP/NAPID_CHR/${NodeName:BBBBB}/NAPID_NULL/ABCD/EFGH";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }
      
      {
        String nodePath = "AP/NAPID_CHR/${NodeName:}/NAPID_NULL/ABCD/EFGH";
        DDFNode node = bean.findDDFNodeByNodePath(tree, nodePath);
        assertNull(node);
      }
      

      bean.delete(tree);
      beanFactory.commit();
      
    } catch (Exception e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }
  }

 }
