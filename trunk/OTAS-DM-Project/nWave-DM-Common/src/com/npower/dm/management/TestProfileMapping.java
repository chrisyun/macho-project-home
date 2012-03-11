/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestProfileMapping.java,v 1.31 2008/06/16 10:11:14 zhao Exp $
 * $Revision: 1.31 $
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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.AllTests;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.hibernate.entity.ProfileNodeMappingEntity;

/**
 * @author Zhao DongLu
 * 
 */
public class TestProfileMapping extends TestCase {

  private static Log log = LogFactory.getLog(TestProfileMapping.class);

  private static final String BASE_DIR = AllTests.BASE_DIR;

  private static final String MAPPING_TEST_FILE = BASE_DIR + "/metadata/profile/mapping/test/mapping.test.1.xml";

  /**
   * Profile Template XML File for Testing
   */
  private static final String FILENAME_PROFILE_METADATA = BASE_DIR + "/metadata/profile/metadata/profile.test.xml";

  /**
   * Profile Template XML File for Nokia Testing
   */
  private static final String FILENAME_TEST_NOKIA_DDF_1 = BASE_DIR + "/metadata/ddf/test/test.nokia.1.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_2 = BASE_DIR + "/metadata/ddf/test/test.nokia.2.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_3 = BASE_DIR + "/metadata/ddf/test/test.nokia.3.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_4 = BASE_DIR + "/metadata/ddf/test/test.nokia.4.xml";

  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER";

  private static final String MODEL_External_ID = "TEST.MODEL";

  /**
   * Constructor for TestProfileMapping.
   * 
   * @param arg0
   */
  public TestProfileMapping(String arg0) {
    super(arg0);
  }

  // private Session hsession = null;
  // private Transaction tx = null;

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
    DDFTreeBean treeBean = factory.createDDFTreeBean();
    ModelBean modelBean = factory.createModelBean();

    /*
     * this.hsession = HibernateSessionFactory.currentSession(); this.tx =
     * this.hsession.beginTransaction(); this.bean = new
     * ProfileMappingBeanImpl(this.hsession); this.templateBean = new
     * ProfileTemplateManagementBeanImpl(this.hsession); this.treeBean = new
     * DDFTreeManagementBeanImpl(this.hsession); this.modelBean = new
     * ModelManagementBeanImpl(this.hsession);
     */

    try {
        this.setupTemplates(factory, templateBean);
        this.setupModel(factory, modelBean);
        this.setupDDFTree(factory, modelBean, treeBean);
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

  }

  private void setupTemplates(ManagementBeanFactory factory, ProfileTemplateBean templateBean) throws Exception {
    InputStream in = null;
    try {
      // in = getClass().getResourceAsStream("profile.xml");
      in = new FileInputStream(FILENAME_PROFILE_METADATA);

      String clause4AllTestTemplates = "from ProfileTemplateEntity where name like 'Test.%' or name like 'TEST.%'";

      List<ProfileTemplate> templates = templateBean.findTemplates(clause4AllTestTemplates);
      for (int i = 0; i < templates.size(); i++) {
        ProfileTemplate template = (ProfileTemplate) templates.get(i);
        log.info("delete:" + template);
        factory.beginTransaction();
        templateBean.delete(template);
        factory.commit();
      }

      templates = templateBean.findTemplates(clause4AllTestTemplates);
      assertEquals(templates.size(), 0);

      int total = templateBean.importProfileTemplates(in);
      in.close();
      assertTrue(total > 0);

      templates = templateBean.findTemplates(clause4AllTestTemplates);
      assertTrue(templates.size() > 0);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  private void setupModel(ManagementBeanFactory factory, ModelBean modelBean) throws Exception {
    try {
      factory.beginTransaction();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      if (manufacturer == null) {
        manufacturer = modelBean.newManufacturerInstance(MANUFACTURER_External_ID, MANUFACTURER_External_ID, MANUFACTURER_External_ID);
        modelBean.update(manufacturer);
      }

      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
      if (model == null) {
        model = modelBean.newModelInstance(manufacturer, MODEL_External_ID, MODEL_External_ID, true, true, true, true, true);

        modelBean.update(model);
      }
      factory.commit();
    } catch (DMException e) {
      e.printStackTrace();
      throw e;
    }
  }

  private void setupDDFTree(ManagementBeanFactory factory, ModelBean modelBean, DDFTreeBean treeBean) throws Exception {
    try {
      factory.beginTransaction();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);

      {
        InputStream in = new FileInputStream(new File(FILENAME_TEST_NOKIA_DDF_1));
        DDFTree tree = treeBean.parseDDFTree(in);
        assertEquals(tree.getManufacturer(), "--Nokia--");
        assertEquals(tree.getModel(), "--The device model--");
        assertEquals(tree.getSpecVersion(), "1.1");

        Set<DDFNode> nodes = tree.getRootDDFNodes();
        assertEquals(nodes.size(), 1);

        DDFNode node = (DDFNode) nodes.iterator().next();
        assertEquals(node.getName(), ".");
        assertNull(node.getParentDDFNode());

        node = (DDFNode) node.getChildren().iterator().next();
        assertEquals(nodes.size(), 1);
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

        treeBean.addDDFTree(tree);
        modelBean.attachDDFTree(model, tree.getID());
      }

      {
        InputStream in = new FileInputStream(new File(FILENAME_TEST_NOKIA_DDF_2));
        DDFTree tree = treeBean.parseDDFTree(in);
        assertEquals(tree.getManufacturer(), "--Nokia--");
        assertEquals(tree.getModel(), "--The device model--");
        assertEquals(tree.getSpecVersion(), "1.1");

        Set<DDFNode> nodes = tree.getRootDDFNodes();
        assertEquals(nodes.size(), 1);

        DDFNode node = (DDFNode) nodes.iterator().next();
        assertEquals(node.getName(), ".");
        assertNull(node.getParentDDFNode());

        node = (DDFNode) node.getChildren().iterator().next();
        assertEquals(nodes.size(), 1);
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

        treeBean.addDDFTree(tree);
        modelBean.attachDDFTree(model, tree.getID());

      }

      {
        InputStream in = new FileInputStream(new File(FILENAME_TEST_NOKIA_DDF_3));
        DDFTree tree = treeBean.parseDDFTree(in);
        assertEquals(tree.getManufacturer(), "--Nokia--");
        assertEquals(tree.getModel(), "--The device model--");
        assertEquals(tree.getSpecVersion(), "1.1");

        Set<DDFNode> nodes = tree.getRootDDFNodes();
        assertEquals(nodes.size(), 1);

        DDFNode node = (DDFNode) nodes.iterator().next();
        assertEquals(node.getName(), ".");
        assertNull(node.getParentDDFNode());

        node = (DDFNode) node.getChildren().iterator().next();
        assertEquals(nodes.size(), 1);
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

        treeBean.addDDFTree(tree);
        modelBean.attachDDFTree(model, tree.getID());
      }

      {
        InputStream in = new FileInputStream(new File(FILENAME_TEST_NOKIA_DDF_4));
        DDFTree tree = treeBean.parseDDFTree(in);
        assertEquals(tree.getManufacturer(), "--Nokia--");
        assertEquals(tree.getModel(), "--The device model--");
        assertEquals(tree.getSpecVersion(), "1.1");

        Set<DDFNode> nodes = tree.getRootDDFNodes();
        assertEquals(nodes.size(), 1);

        DDFNode node = (DDFNode) nodes.iterator().next();
        assertEquals(node.getName(), ".");
        assertNull(node.getParentDDFNode());

        node = (DDFNode) node.getChildren().iterator().next();
        assertEquals(nodes.size(), 1);
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

        treeBean.addDDFTree(tree);
        modelBean.attachDDFTree(model, tree.getID());
      }
      
      factory.commit();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /*
   * private void teardownModelAndDDFTree() { }
   */
  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();

  }

  /**
   * @param mappings
   */
  private void assertResult(List<ProfileMapping> mappings) {
    // TestCase #1 in mapping.test.1.xml
    // *************************************************
    {
      ProfileMapping mapping1 = (ProfileMapping) mappings.get(0);
      assertEquals(mapping1.getAssignToDevice(), true);
      assertEquals(mapping1.getShareRootNode(), true);
      assertEquals(mapping1.getLinkedProfileType(), "NAP");
      assertEquals(mapping1.getModelExternalID(), MODEL_External_ID);
      assertEquals(mapping1.getManufacturerExternalID(), MANUFACTURER_External_ID);

      assertFalse(mapping1.getProfileTemplate() == null);
      ProfileTemplate template1 = mapping1.getProfileTemplate();
      assertEquals(template1.getName(), "Test.Proxy Template");
      assertEquals(template1.getProfileCategory().getName(), "TEST.Proxy");
      assertEquals(template1.getNeedsNap(), true);
      assertEquals(template1.getNeedsProxy(), false);
      assertEquals(template1.getProfileAttributes().size(), 7);

      DDFNode rootNode = mapping1.getRootDDFNode();
      assertNotNull(rootNode);
      assertEquals(null, rootNode.getName());
      assertEquals("AP", rootNode.getParentDDFNode().getName());

      Set<ProfileNodeMapping> nodeMappings = mapping1.getProfileNodeMappings();
      assertEquals(nodeMappings.size(), 5);
      int total = 0;
      for (Iterator<ProfileNodeMapping> i = nodeMappings.iterator(); i.hasNext();) {
        ProfileNodeMappingEntity mapping = (ProfileNodeMappingEntity) i.next();
        if (mapping.getAttributeNameString().equals("Display Name")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "Px/${NodeName}/Name");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("Name", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("Px", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertEquals(attr.getName(), "Display Name");
          assertNotNull(attr);
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("Proxy Address")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "Px/${NodeName}/PxAddr");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("PxAddr", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("Px", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertEquals(attr.getName(), "Proxy Address");
          assertNotNull(attr);
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("Test Start Page")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "Px/${NodeName}/Startpg");
          assertEquals(mapping.getDisplayName(), "Test Start Page");
          assertEquals(mapping.getValue(), "http://www.otas.com");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("Startpg", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("Px", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);

        } else if (mapping.getAttributeNameString().equals("Test Port Number")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "Px/${NodeName}/Port/${NodeName}/PortNbr");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("PortNbr", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("Port", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);
          assertEquals(attr.getName(), "Test Port Number");
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("Test Domain")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "Px/${NodeName}/Domain");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("Domain", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("Px", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);
          assertEquals(attr.getName(), "Test Domain");
          assertTrue(attr.getID() > 0);

        }
      }
      assertEquals(total, 5);
    }

    // TestCase #2 in mapping.test.1.xml
    // *************************************************
    {
      ProfileMapping mapping2 = (ProfileMapping) mappings.get(1);
      assertEquals(mapping2.getAssignToDevice(), false);
      assertEquals(mapping2.getShareRootNode(), false);
      assertEquals(mapping2.getLinkedProfileType(), null);
      assertEquals(mapping2.getModelExternalID(), MODEL_External_ID);
      assertEquals(mapping2.getManufacturerExternalID(), MANUFACTURER_External_ID);

      assertFalse(mapping2.getProfileTemplate() == null);
      ProfileTemplate template1 = mapping2.getProfileTemplate();
      assertEquals(template1.getName(), "TEST.Profile Template#1");
      assertEquals(template1.getProfileCategory().getName(), "TEST.NAP");
      assertEquals(template1.getNeedsNap(), false);
      assertEquals(template1.getNeedsProxy(), false);
      assertEquals(template1.getProfileAttributes().size(), 7);

      DDFNode rootNode = mapping2.getRootDDFNode();
      assertNotNull(rootNode);
      assertEquals(null, rootNode.getName());
      assertEquals("AP", rootNode.getParentDDFNode().getName());

      Set<ProfileNodeMapping> nodeMappings = mapping2.getProfileNodeMappings();
      assertEquals(nodeMappings.size(), 8);
      int total = 0;
      for (Iterator<ProfileNodeMapping> i = nodeMappings.iterator(); i.hasNext();) {
        ProfileNodeMappingEntity mapping = (ProfileNodeMappingEntity) i.next();
        if (mapping.getAttributeNameString().equals("TEST NAP Address Type")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/NAPAddrTy");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("NAPAddrTy", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("NAPDef", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertEquals(attr.getName(), "TEST NAP Address Type");
          assertNotNull(attr);
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("TEST Display Name")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/Name");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("Name", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("NAPDef", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertEquals(attr.getName(), "TEST Display Name");
          assertNotNull(attr);
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("Bearer Type")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/Bearer/${NodeName}/BearerL");
          assertEquals(mapping.getDisplayName(), "Bearer Type");
          assertEquals(mapping.getValue(), "GSM-GPRS");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("BearerL", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("Bearer", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNull(attr);

        } else if (mapping.getAttributeNameString().equals("TEST Bearer Direction")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/Bearer/${NodeName}/Direction");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("Direction", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("Bearer", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);
          assertEquals(attr.getName(), "TEST Bearer Direction");
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("TEST NAP Address")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/NAPAddr");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("NAPAddr", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("NAPDef", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);
          assertEquals(attr.getName(), "TEST NAP Address");
          assertTrue(attr.getID() > 0);

        }
      }
      assertEquals(total, 5);
    }

    // TestCase #3 in mapping.test.1.xml
    // *************************************************
    {
      ProfileMapping mapping2 = (ProfileMapping) mappings.get(2);
      assertEquals(mapping2.getAssignToDevice(), true);
      assertEquals(mapping2.getShareRootNode(), false);
      assertEquals(mapping2.getLinkedProfileType(), null);
      assertEquals(mapping2.getModelExternalID(), MODEL_External_ID);
      assertEquals(mapping2.getManufacturerExternalID(), MANUFACTURER_External_ID);

      assertFalse(mapping2.getProfileTemplate() == null);
      ProfileTemplate template1 = mapping2.getProfileTemplate();
      assertEquals(template1.getName(), "TEST.CSD NAP Template");
      assertEquals(template1.getProfileCategory().getName(), "TEST.NAP");
      assertEquals(template1.getNeedsNap(), false);
      assertEquals(template1.getNeedsProxy(), false);
      assertEquals(template1.getProfileAttributes().size(), 9);

      DDFNode rootNode = mapping2.getRootDDFNode();
      assertNotNull(rootNode);
      assertEquals(null, rootNode.getName());
      assertEquals("AP", rootNode.getParentDDFNode().getName());

      Set<ProfileNodeMapping> nodeMappings = mapping2.getProfileNodeMappings();
      assertEquals(nodeMappings.size(), 9);
      int total = 0;
      for (Iterator<ProfileNodeMapping> i = nodeMappings.iterator(); i.hasNext();) {
        ProfileNodeMappingEntity mapping = (ProfileNodeMappingEntity) i.next();
        if (mapping.getAttributeNameString().equals("TEST Display Name")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/Name");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("Name", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("NAPDef", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertEquals(attr.getName(), "TEST Display Name");
          assertNotNull(attr);
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("Bearer Type")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/Bearer/${NodeName:Bearer01}/BearerL");
          assertEquals(mapping.getDisplayName(), "Bearer Type");
          assertEquals(mapping.getValue(), "GSM-CSD");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("BearerL", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("Bearer", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNull(attr);

        } else if (mapping.getAttributeNameString().equals("TEST Bearer Direction")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/Bearer/${NodeName:Bearer01}/Direction");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("Direction", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("Bearer", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);

        } else if (mapping.getAttributeNameString().equals("TEST NAP Address")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/NAPAddr");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("NAPAddr", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("NAPDef", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);
          assertEquals(attr.getName(), "TEST NAP Address");
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("TEST NAP Address Type")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/NAPAddrTy");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("NAPAddrTy", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("NAPDef", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);
          assertEquals(attr.getName(), "TEST NAP Address Type");
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("TEST Link Speed")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/LnkSpeed");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("LnkSpeed", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("NAPDef", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);
          assertEquals(attr.getName(), "TEST Link Speed");
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("TEST DNS Address")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/DNSAddr");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("DNSAddr", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("NAPDef", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);
          assertEquals(attr.getName(), "TEST DNS Address");
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("TEST Authentication Username")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/NAPAuthInf/${NodeName:Auth01}/AuthName");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("AuthName", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("NAPAuthInf", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);
          assertEquals(attr.getName(), "TEST Authentication Username");
          assertTrue(attr.getID() > 0);

        } else if (mapping.getAttributeNameString().equals("TEST Authentication Password")) {
          total++;
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, mapping.getMappingType());
          assertEquals(mapping.getNodeRelativePath(), "NAPDef/${NodeName}/NAPAuthInf/${NodeName:Auth01}/AuthSecr");
          DDFNode node = mapping.getDdfNode();
          assertNotNull(node);
          assertEquals("AuthSecr", node.getName());

          assertEquals(null, node.getParentDDFNode().getName());
          assertEquals("NAPAuthInf", node.getParentDDFNode().getParentDDFNode().getName());

          ProfileAttribute attr = mapping.getProfileAttribute();
          assertNotNull(attr);
          assertEquals(attr.getName(), "TEST Authentication Password");
          assertTrue(attr.getID() > 0);

        }
      }
      assertEquals(total, 9);
    }
  }

  public void testParsingProfileMapping() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileMappingBean bean = factory.createProfileMappingBean();
    try {
      InputStream in = new FileInputStream(new File(MAPPING_TEST_FILE));
      List<ProfileMapping> mappings = bean.parsingProfileMapping(in);
      in.close();

      for (Iterator<ProfileMapping> it = mappings.iterator(); it.hasNext();) {
        ProfileMapping map = (ProfileMapping) it.next();        
        assertNotNull(map.getProfileTemplate() != null);
        assertNotNull(map.getProfileTemplate().getName() != null);
      }

      assertEquals(mappings.size(), 5);

      // Checking result
      assertResult(mappings);

    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

  }

  // Test case: import profile mapping
  public void testImportProfileMapping() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileMappingBean bean = factory.createProfileMappingBean();
    ModelBean modelBean = factory.createModelBean();
    try {
      InputStream in = new FileInputStream(new File(MAPPING_TEST_FILE));
      List<ProfileMapping> mappings = bean.importProfileMapping(in);
      in.close();

      // Checking result
      assertResult(mappings);

      assertEquals(mappings.size(), 5);
      // Attach the profileMapping to Model
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, MODEL_External_ID);
      ProfileMapping testCase1 = null;
      ProfileMapping testCase2 = null;
      ProfileMapping testCase3 = null;
      ProfileMapping testCase4 = null;
      ProfileMapping testCase5 = null;
      for (int i = 0; i < mappings.size(); i++) {
        ProfileMapping map = (ProfileMapping) mappings.get(i);

        modelBean.attachProfileMapping(model, map.getID());

        ProfileTemplate template = map.getProfileTemplate();
        ProfileMapping map2 = model.getProfileMap(template);
        assertEquals(map, map2);
        
        String templateName = template.getName();
        assertNotNull(templateName);
        
        if (templateName.equals("Test.Proxy Template")) {
          testCase1 = map;
        } else if (templateName.equals("TEST.Profile Template#1")) {
          testCase2 = map;
        } else if (templateName.equals("TEST.CSD NAP Template")) {
          testCase3 = map;
        } else if (templateName.equals("TEST.Email Template")) {
          testCase4 = map;
        } else if (templateName.equals("TEST.MMS Template")) {
          testCase5 = map;
        }
      }
      
      // Checking Node Mapping for each mapping
      assertNotNull(testCase1);
      assertNotNull(testCase2);
      assertNotNull(testCase3);
      assertNotNull(testCase4);
      
      {
        // Asset Test Case#1
        Set<ProfileNodeMapping> nodeMappings = testCase1.getProfileNodeMappings();
        assertEquals(5, nodeMappings.size());
        List<ProfileNodeMapping> list = new ArrayList<ProfileNodeMapping>(nodeMappings);
        assertEquals(true, testCase1.getAssignToDevice());
        assertEquals(true, testCase1.getShareRootNode());
        assertEquals("NAP", testCase1.getLinkedProfileType());
        assertEquals("TEST.MANUFACTUER", testCase1.getManufacturerExternalID());
        assertEquals("TEST.MODEL", testCase1.getModelExternalID());
        assertEquals("./AP/${NodeName}", testCase1.getRootDDFNode().getNodePath());
        assertEquals("./AP/${NodeName}", testCase1.getRootNodePath());
        
        {
          ProfileNodeMapping node = list.get(0);
          assertEquals("Display Name", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/Px/${NodeName}/Name", node.getDdfNode().getNodePath());
          assertEquals("Px/${NodeName}/Name", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(1);
          assertEquals("Proxy Address", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/Px/${NodeName}/PxAddr", node.getDdfNode().getNodePath());
          assertEquals("Px/${NodeName}/PxAddr", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(2);
          assertEquals("Test Start Page", node.getDisplayName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE, node.getMappingType());
          assertEquals("http://www.otas.com", node.getValue());
          assertEquals("./AP/${NodeName}/Px/${NodeName}/Startpg", node.getDdfNode().getNodePath());
          assertEquals("Px/${NodeName}/Startpg", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(3);
          assertEquals("Test Port Number", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/Px/${NodeName}/Port/${NodeName}/PortNbr", node.getDdfNode().getNodePath());
          assertEquals("Px/${NodeName}/Port/${NodeName}/PortNbr", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(4);
          assertEquals("Test Domain", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/Px/${NodeName}/Domain", node.getDdfNode().getNodePath());
          assertEquals("Px/${NodeName}/Domain", node.getNodeRelativePath());
        }
      }
      
      {
        // Asset Test Case#2
        Set<ProfileNodeMapping> nodeMappings = testCase2.getProfileNodeMappings();
        assertEquals(8, nodeMappings.size());
        List<ProfileNodeMapping> list = new ArrayList<ProfileNodeMapping>(nodeMappings);
        assertEquals(false, testCase2.getAssignToDevice());
        assertEquals(false, testCase2.getShareRootNode());
        assertNull(testCase2.getLinkedProfileType());
        assertEquals("TEST.MANUFACTUER", testCase2.getManufacturerExternalID());
        assertEquals("TEST.MODEL", testCase2.getModelExternalID());
        assertEquals("./AP/${NodeName}", testCase2.getRootDDFNode().getNodePath());
        assertEquals("./AP/${NodeName}", testCase2.getRootNodePath());
        
        {
          ProfileNodeMapping node = list.get(0);
          assertEquals("TEST NAP Address Type", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/NAPAddrTy", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/NAPAddrTy", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(1);
          assertEquals("TEST Display Name", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/Name", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/Name", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(2);
          assertEquals("Bearer Type", node.getDisplayName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE, node.getMappingType());
          assertEquals("GSM-GPRS", node.getValue());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/Bearer/${NodeName}/BearerL", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/Bearer/${NodeName}/BearerL", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(3);
          assertEquals("TEST Bearer Direction", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/Bearer/${NodeName}/Direction", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/Bearer/${NodeName}/Direction", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(4);
          assertEquals("TEST NAP Address", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/NAPAddr", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/NAPAddr", node.getNodeRelativePath());
        }

        {
          ProfileNodeMapping node = list.get(5);
          assertEquals("TEST DNS Address", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/DNSAddr", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/DNSAddr", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(6);
          assertEquals("TEST Authentication Username", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/NAPAuthInf/${NodeName}/AuthName", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/NAPAuthInf/${NodeName}/AuthName", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(7);
          assertEquals("TEST Authentication Password", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/NAPAuthInf/${NodeName}/AuthSecr", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/NAPAuthInf/${NodeName}/AuthSecr", node.getNodeRelativePath());
        }
      }

      {
        // Asset Test Case#3
        Set<ProfileNodeMapping> nodeMappings = testCase3.getProfileNodeMappings();
        assertEquals(9, nodeMappings.size());
        List<ProfileNodeMapping> list = new ArrayList<ProfileNodeMapping>(nodeMappings);
        assertEquals(true, testCase3.getAssignToDevice());
        assertEquals(false, testCase3.getShareRootNode());
        assertNull(testCase3.getLinkedProfileType());
        assertEquals("TEST.MANUFACTUER", testCase3.getManufacturerExternalID());
        assertEquals("TEST.MODEL", testCase3.getModelExternalID());
        assertEquals("./AP/${NodeName}", testCase3.getRootDDFNode().getNodePath());
        assertEquals("./AP/${NodeName}", testCase3.getRootNodePath());
        
        {
          ProfileNodeMapping node = list.get(0);
          assertEquals("TEST Display Name", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/Name", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/Name", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(1);
          assertEquals("Bearer Type", node.getDisplayName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE, node.getMappingType());
          assertEquals("GSM-CSD", node.getValue());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/Bearer/${NodeName}/BearerL", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/Bearer/${NodeName:Bearer01}/BearerL", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(2);
          assertEquals("TEST Bearer Direction", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/Bearer/${NodeName}/Direction", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/Bearer/${NodeName:Bearer01}/Direction", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(3);
          assertEquals("TEST NAP Address", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/NAPAddr", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/NAPAddr", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(4);
          assertEquals("TEST NAP Address Type", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/NAPAddrTy", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/NAPAddrTy", node.getNodeRelativePath());
        }

        {
          ProfileNodeMapping node = list.get(5);
          assertEquals("TEST Link Speed", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/LnkSpeed", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/LnkSpeed", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(6);
          assertEquals("TEST DNS Address", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/DNSAddr", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/DNSAddr", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(7);
          assertEquals("TEST Authentication Username", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/NAPAuthInf/${NodeName}/AuthName", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/NAPAuthInf/${NodeName:Auth01}/AuthName", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(8);
          assertEquals("TEST Authentication Password", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./AP/${NodeName}/NAPDef/${NodeName}/NAPAuthInf/${NodeName}/AuthSecr", node.getDdfNode().getNodePath());
          assertEquals("NAPDef/${NodeName}/NAPAuthInf/${NodeName:Auth01}/AuthSecr", node.getNodeRelativePath());
        }
      }

      {
        // Asset Test Case#4
        Set<ProfileNodeMapping> nodeMappings = testCase4.getProfileNodeMappings();
        assertEquals(9, nodeMappings.size());
        List<ProfileNodeMapping> list = new ArrayList<ProfileNodeMapping>(nodeMappings);
        assertEquals(true, testCase4.getAssignToDevice());
        assertEquals(false, testCase4.getShareRootNode());
        assertEquals("NAP", testCase4.getLinkedProfileType());
        assertEquals("TEST.MANUFACTUER", testCase4.getManufacturerExternalID());
        assertEquals("TEST.MODEL", testCase4.getModelExternalID());
        assertEquals("./Email/${NodeName}", testCase4.getRootDDFNode().getNodePath());
        assertEquals("./Email/${NodeName}", testCase4.getRootNodePath());
        
        {
          ProfileNodeMapping node = list.get(0);
          assertEquals("Display Name", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./Email/${NodeName}/Name", node.getDdfNode().getNodePath());
          assertEquals("Name", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(1);
          assertEquals("Username", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./Email/${NodeName}/UID", node.getDdfNode().getNodePath());
          assertEquals("UID", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(2);
          assertEquals("Password", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./Email/${NodeName}/PW", node.getDdfNode().getNodePath());
          assertEquals("PW", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(3);
          assertEquals("Email Address", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./Email/${NodeName}/UAddr", node.getDdfNode().getNodePath());
          assertEquals("UAddr", node.getNodeRelativePath());
        }

        {
          ProfileNodeMapping node = list.get(4);
          assertEquals("User Display Name", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./Email/${NodeName}/UName", node.getDdfNode().getNodePath());
          assertEquals("UName", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(5);
          assertEquals("Receiving Server Address", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./Email/${NodeName}/Mrcv", node.getDdfNode().getNodePath());
          assertEquals("Mrcv", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(6);
          assertEquals("Sending Server Address", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./Email/${NodeName}/Msnd", node.getDdfNode().getNodePath());
          assertEquals("Msnd", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(7);
          assertEquals("Mailbox Protocol", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./Email/${NodeName}/Mpro", node.getDdfNode().getNodePath());
          assertEquals("Mpro", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(8);
          assertEquals("APLink", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./Email/${NodeName}/ToNapID", node.getDdfNode().getNodePath());
          assertEquals("ToNapID", node.getNodeRelativePath());
        }
      }

      {
        // Asset Test Case#5: MMS
        Set<ProfileNodeMapping> nodeMappings = testCase5.getProfileNodeMappings();
        assertEquals(3, nodeMappings.size());
        List<ProfileNodeMapping> list = new ArrayList<ProfileNodeMapping>(nodeMappings);
        assertEquals(true, testCase5.getAssignToDevice());
        assertEquals(false, testCase5.getShareRootNode());
        assertEquals("Proxy", testCase5.getLinkedProfileType());
        assertEquals("TEST.MANUFACTUER", testCase5.getManufacturerExternalID());
        assertEquals("TEST.MODEL", testCase5.getModelExternalID());
        assertEquals("./MMS/MMSAcc", testCase5.getRootDDFNode().getNodePath());
        assertEquals("./MMS/MMSAcc", testCase5.getRootNodePath());
        
        {
          ProfileNodeMapping node = list.get(0);
          assertEquals("Display Name", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./MMS/MMSAcc/Name", node.getDdfNode().getNodePath());
          assertEquals("Name", node.getNodeRelativePath());
          assertNull(node.getCategoryName());
          
          // Test Value Translation
          assertEquals(null, node.translateValue(null));
          assertEquals("", node.translateValue(""));
          assertEquals("No translation value found", node.translateValue("No translation value found"));
          assertEquals("Modem", node.translateValue("ANALOG-MODEM"));
          assertEquals("ISDN", node.translateValue("ISDN V.110"));
          assertEquals("ISDN", node.translateValue("ISDN V.120"));
        }
        {
          ProfileNodeMapping node = list.get(1);
          assertEquals("Start Page", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./MMS/MMSAcc/Con/DCon/MMSSAddr", node.getDdfNode().getNodePath());
          assertEquals("Con/DCon/MMSSAddr", node.getNodeRelativePath());
        }
        {
          ProfileNodeMapping node = list.get(2);
          assertEquals("APLink", node.getProfileAttribute().getName());
          assertEquals(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE, node.getMappingType());
          assertEquals("./MMS/MMSAcc/Con/DCon/MToNapID/Primary/MToNapIDL", node.getDdfNode().getNodePath());
          assertEquals("Con/DCon/MToNapID/Primary/MToNapIDL", node.getNodeRelativePath());
          assertNull(node.getCategoryName());
        }
      }
      
      assertEquals(5, model.getProfileMappings().size());

      // Dettach the profileMapping from Model
      for (int i = 0; i < mappings.size(); i++) {
        ProfileMapping map = (ProfileMapping) mappings.get(i);

        modelBean.dettachProfileMapping(model, map.getID());

        ProfileTemplate template = map.getProfileTemplate();
        ProfileMapping map2 = model.getProfileMap(template);
        assertNull(map2);

        bean.delete((ProfileMapping) mappings.get(i));
      }

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
}
