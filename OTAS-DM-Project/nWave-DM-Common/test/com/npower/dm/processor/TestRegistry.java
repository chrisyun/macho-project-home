/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/processor/TestRegistry.java,v 1.3 2007/01/11 05:21:00 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/01/11 05:21:00 $
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
package com.npower.dm.processor;

import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.hibernate.entity.DDFNodeEntity;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/01/11 05:21:00 $
 */
public class TestRegistry extends TestCase {

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
  
  
  /**
   * <pre>
   *   A -- B -- ${NodeName} -- D1 -- E1
   *                         |
   *                         |- D2 -- E2
   *                         
   * </pre>
   * @throws Exception
   */
  public void testAddItem() throws Exception {
    // Set up DDF tree
    DDFNodeEntity a = new DDFNodeEntity();
    a.setID(1L);
    a.setName("A");
    
    DDFNodeEntity b = new DDFNodeEntity();
    b.setID(11L);
    b.setName("B");
    a.add(b);
    
    DDFNodeEntity c1 = new DDFNodeEntity();
    c1.setID(111L);
    c1.setName(null);
    b.add(c1);
    
    DDFNodeEntity d1 = new DDFNodeEntity();
    d1.setID(1111L);
    d1.setName("D1");
    c1.add(d1);
    DDFNodeEntity d2 = new DDFNodeEntity();
    d2.setID(1112L);
    d2.setName("D2");
    c1.add(d2);
    
    DDFNodeEntity e1 = new DDFNodeEntity();
    e1.setID(11111L);
    e1.setName(null);
    d1.add(e1);
    
    DDFNodeEntity e2 = new DDFNodeEntity();
    e2.setID(11112L);
    e2.setName("E2");
    d1.add(e2);
    
    DDFNodeEntity e3 = new DDFNodeEntity();
    e3.setID(11113L);
    e3.setName("E3");
    d1.add(e3);
    
    DDFNodeEntity f1 = new DDFNodeEntity();
    f1.setID(111111L);
    f1.setName("F1");
    e1.add(f1);
    
    DDFNodeEntity k = new DDFNodeEntity();
    k.setID(12L);
    k.setName(null);
    a.add(k);
    
    DDFNodeEntity m = new DDFNodeEntity();
    m.setID(612L);
    m.setName("M");
    k.add(m);
    
    DDFNodeEntity kc1 = new DDFNodeEntity();
    kc1.setID(6111L);
    kc1.setName(null);
    m.add(kc1);
    
    DDFNodeEntity kd1 = new DDFNodeEntity();
    kd1.setID(61111L);
    kd1.setName("KD1");
    kc1.add(kd1);
    DDFNodeEntity kd2 = new DDFNodeEntity();
    kd2.setID(61112L);
    kd2.setName("KD2");
    kc1.add(kd2);
    
    DDFNodeEntity ke1 = new DDFNodeEntity();
    ke1.setID(611111L);
    ke1.setName(null);
    kd1.add(ke1);
    
    DDFNodeEntity ke2 = new DDFNodeEntity();
    ke2.setID(611112L);
    ke2.setName("KE2");
    kd1.add(ke2);
    
    DDFNodeEntity ke3 = new DDFNodeEntity();
    ke3.setID(611113L);
    ke3.setName("KE3");
    kd1.add(ke3);
    
    DDFNodeEntity kf1 = new DDFNodeEntity();
    kf1.setID(6111111L);
    kf1.setName("KF1");
    ke1.add(kf1);

    assertEquals("./A", a.getNodePath());
    assertEquals("./A/B", b.getNodePath());
    assertEquals("./A/B/${NodeName}", c1.getNodePath());
    assertEquals("./A/B/${NodeName}/D1", d1.getNodePath());
    assertEquals("./A/B/${NodeName}/D2", d2.getNodePath());
    assertEquals("./A/B/${NodeName}/D1/${NodeName}", e1.getNodePath());
    assertEquals("./A/B/${NodeName}/D1/E2", e2.getNodePath());
    assertEquals("./A/B/${NodeName}/D1/E3", e3.getNodePath());
    assertEquals("./A/B/${NodeName}/D1/${NodeName}/F1", f1.getNodePath());
    
    assertEquals("./A/${NodeName}", k.getNodePath());
    assertEquals("./A/${NodeName}/M", m.getNodePath());
    assertEquals("./A/${NodeName}/M/${NodeName}", kc1.getNodePath());
    assertEquals("./A/${NodeName}/M/${NodeName}/KD1", kd1.getNodePath());
    assertEquals("./A/${NodeName}/M/${NodeName}/KD2", kd2.getNodePath());
    assertEquals("./A/${NodeName}/M/${NodeName}/KD1/${NodeName}", ke1.getNodePath());
    assertEquals("./A/${NodeName}/M/${NodeName}/KD1/KE2", ke2.getNodePath());
    assertEquals("./A/${NodeName}/M/${NodeName}/KD1/KE3", ke3.getNodePath());
    assertEquals("./A/${NodeName}/M/${NodeName}/KD1/${NodeName}/KF1", kf1.getNodePath());
    
    
    Registry registry = new Registry();
    
    // Test Add
    RegistryItem item = registry.addRegistryItem("./A/B", b);
    assertNotNull(item);
    assertEquals("B", item.getName());
    assertNotNull(item.getParent());
    assertEquals("A", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals(".", item.getParent().getParent().getName());
    
    item = registry.addRegistryItem("./A/${NodeName}/M/${NodeName:KC2}/KD1/${NodeName}/KF1", kf1);
    assertNotNull(item);
    assertEquals("KF1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("OTAS2", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("KD1", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("KC2", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals("M", item.getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent());
    assertEquals("OTAS1", item.getParent().getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getName());
    
    item = registry.addRegistryItem("./A/${NodeName}/M/${NodeName:KC2}/KD1/KE2", ke2);
    assertNotNull(item);
    assertEquals("KE2", item.getName());
    assertNotNull(item.getParent());
    assertEquals("KD1", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("KC2", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("M", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals("OTAS1", item.getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getParent().getParent().getName());
    
    item = registry.addRegistryItem("./A/B/${NodeName:C2}/D1/${NodeName}/F1", f1);
    assertNotNull(item);
    assertEquals("F1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("OTAS3", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("D1", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("C2", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals("B", item.getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getParent().getParent().getName());
    
    item = registry.addRegistryItem("./A/B/${NodeName:C2}/D1", d1);
    assertNotNull(item);
    assertEquals("D1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("C2", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("B", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getName());
    
    item = registry.addRegistryItem("./A/B/${NodeName:C1}", c1);
    assertNotNull(item);
    assertEquals("C1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("B", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("A", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getName());

    item = registry.addRegistryItem("./A/B/${NodeName:C1}/D1", d1);
    assertNotNull(item);
    assertEquals("D1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("C1", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("B", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getName());
    
    // Test find
    item = registry.find("./A/B/C1/D1");
    assertNotNull(item);
    assertEquals("D1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("C1", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("B", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getName());

    item = registry.find("./A/B/C2/D1");
    assertNotNull(item);
    assertEquals("D1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("C2", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("B", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getName());

    item = registry.find("./A/B/${NodeName:C2}/D1");
    assertNotNull(item);
    assertEquals("D1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("C2", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("B", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getName());
    
    item = registry.find("./A/B/${NodeName:C2}/D1/${NodeName}/F1");
    assertNotNull(item);
    assertEquals("F1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("OTAS3", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("D1", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("C2", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals("B", item.getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getParent().getParent().getName());
    
    item = registry.find("./A/B/${NodeName:C2}/D1/${NodeName:OTAS3}/F1");
    assertNotNull(item);
    assertEquals("F1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("OTAS3", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("D1", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("C2", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals("B", item.getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getParent().getParent().getName());
    
    item = registry.find("./A/B/C2/D1/${NodeName:OTAS3}/F1");
    assertNotNull(item);
    assertEquals("F1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("OTAS3", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("D1", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("C2", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals("B", item.getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getParent().getParent().getName());
    
    item = registry.find("./A/B/${NodeName:C2}/D1/OTAS3/F1");
    assertNotNull(item);
    assertEquals("F1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("OTAS3", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("D1", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("C2", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals("B", item.getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getParent().getParent().getName());
    
    item = registry.find("./A/B/C2/D1/OTAS3/F1");
    assertNotNull(item);
    assertEquals("F1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("OTAS3", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("D1", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("C2", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals("B", item.getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getParent().getParent().getName());
    
    item = registry.find("./A/B/C2/D1/OTAS4444");
    assertNull(item);

    item = registry.find("./A/B/C2/D1/OTAS4444/F1");
    assertNull(item);

    item = registry.find("./A/${NodeName}/M/${NodeName:KC2}/KD1/${NodeName}/KF1");
    assertNotNull(item);
    assertEquals("KF1", item.getName());
    assertNotNull(item.getParent());
    assertEquals("OTAS2", item.getParent().getName());
    assertNotNull(item.getParent().getParent());
    assertEquals("KD1", item.getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent());
    assertEquals("KC2", item.getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent());
    assertEquals("M", item.getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent());
    assertEquals("OTAS1", item.getParent().getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent().getParent());
    assertEquals("A", item.getParent().getParent().getParent().getParent().getParent().getParent().getName());
    assertNotNull(item.getParent().getParent().getParent().getParent().getParent().getParent().getParent());
    assertEquals(".", item.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getName());

    
    // Test Children
    item = registry.find("./A");
    assertNotNull(item);
    assertEquals(2, item.getChildren().size());
  
    item = registry.find("./A/B");
    assertNotNull(item);
    assertEquals(2, item.getChildren().size());
  
    item = registry.find("./A/B/${NodeName:C1}");
    assertNotNull(item);
    assertEquals(1, item.getChildren().size());
  
    item = registry.find("./A/B/${NodeName:C2}");
    assertNotNull(item);
    assertEquals(1, item.getChildren().size());
  
    item = registry.find("./A/B/${NodeName:C1}/D1");
    assertNotNull(item);
    assertEquals(0, item.getChildren().size());
  
    item = registry.find("./A/B/${NodeName:C2}/D1");
    assertNotNull(item);
    assertEquals(1, item.getChildren().size());
  
    item = registry.find("./A/B/${NodeName:C2}/D1/${NodeName}");
    assertNotNull(item);
    assertEquals(1, item.getChildren().size());
  
    item = registry.find("./A/B/${NodeName:C2}/D1/${NodeName}/F1");
    assertNotNull(item);
    assertEquals(0, item.getChildren().size());
  
    // Test Deepth
    item = registry.find("./A");
    assertNotNull(item);
    assertEquals(6, item.getDeepth());
  
    item = registry.find("./A/B");
    assertNotNull(item);
    assertEquals(4, item.getDeepth());
  
    item = registry.find("./A/B/${NodeName:C1}");
    assertNotNull(item);
    assertEquals(1, item.getDeepth());
  
    item = registry.find("./A/B/${NodeName:C1}/D1");
    assertNotNull(item);
    assertEquals(0, item.getDeepth());
  
    item = registry.find("./A/B/${NodeName:C2}");
    assertNotNull(item);
    assertEquals(3, item.getDeepth());
  
    item = registry.find("./A/B/${NodeName:C2}/D1");
    assertNotNull(item);
    assertEquals(2, item.getDeepth());
  
    item = registry.find("./A/B/${NodeName:C2}/D1/${NodeName}");
    assertNotNull(item);
    assertEquals(1, item.getDeepth());
  
    item = registry.find("./A/B/${NodeName:C2}/D1/${NodeName}/F1");
    assertNotNull(item);
    assertEquals(0, item.getDeepth());
  
    // Test Position
    item = registry.find("./A");
    assertNotNull(item);
    assertEquals(1, item.getPosition());
  
    item = registry.find("./A/B");
    assertNotNull(item);
    assertEquals(2, item.getPosition());
  
    item = registry.find("./A/B/${NodeName:C1}");
    assertNotNull(item);
    assertEquals(3, item.getPosition());
  
    item = registry.find("./A/B/${NodeName:C1}/D1");
    assertNotNull(item);
    assertEquals(4, item.getPosition());
  
    item = registry.find("./A/B/${NodeName:C2}");
    assertNotNull(item);
    assertEquals(3, item.getPosition());
  
    item = registry.find("./A/B/${NodeName:C2}/D1");
    assertNotNull(item);
    assertEquals(4, item.getPosition());
  
    item = registry.find("./A/B/${NodeName:C2}/D1/${NodeName}");
    assertNotNull(item);
    assertEquals(5, item.getPosition());
  
    item = registry.find("./A/B/${NodeName:C2}/D1/${NodeName}/F1");
    assertNotNull(item);
    assertEquals(6, item.getPosition());
  
    // Test Sorted Children
    item = registry.find("./A");
    assertNotNull(item);
    assertEquals(2, item.getChildren().size());
  
    item = registry.find("./A/B");
    assertNotNull(item);
    assertEquals(2, item.getChildren().size());
    assertEquals("C1", item.getChildren().get(0).getName());
    assertEquals("C2", item.getChildren().get(1).getName());
  
    item = registry.find("./A/B/${NodeName:C1}");
    assertNotNull(item);
    assertEquals(1, item.getChildren().size());
  
    item = registry.find("./A/B/${NodeName:C2}");
    assertNotNull(item);
    assertEquals(1, item.getChildren().size());
  
    item = registry.find("./A/B/${NodeName:C1}/D1");
    assertNotNull(item);
    assertEquals(0, item.getChildren().size());
  
    item = registry.find("./A/B/${NodeName:C2}/D1");
    assertNotNull(item);
    assertEquals(1, item.getChildren().size());
  
    item = registry.find("./A/B/${NodeName:C2}/D1/${NodeName}");
    assertNotNull(item);
    assertEquals(1, item.getChildren().size());
  
    item = registry.find("./A/B/${NodeName:C2}/D1/${NodeName}/F1");
    assertNotNull(item);
    assertEquals(0, item.getChildren().size());
  
    // Test getAncestor()
    item = registry.find("./A/B/${NodeName:C2}/D1/${NodeName}/F1");
    assertNotNull(item);
    List<RegistryItem> ancestor = item.getAncestor();
    assertEquals(5, ancestor.size());
    assertEquals("A", ancestor.get(0).getName());
    assertEquals("B", ancestor.get(1).getName());
    assertEquals("C2", ancestor.get(2).getName());
    assertEquals("D1", ancestor.get(3).getName());
    assertEquals("OTAS3", ancestor.get(4).getName());
    
    // Test getAllItems()
    List<RegistryItem> items = registry.getItems();
    assertEquals(15, items.size());
    assertEquals("A", items.get(0).getName());
    assertEquals("./A", items.get(0).getPath());
    assertEquals("B", items.get(1).getName());
    assertEquals("./A/B", items.get(1).getPath());
    assertEquals("C1", items.get(2).getName());
    assertEquals("./A/B/C1", items.get(2).getPath());
    assertEquals("D1", items.get(3).getName());
    assertEquals("./A/B/C1/D1", items.get(3).getPath());
    assertEquals("C2", items.get(4).getName());
    assertEquals("./A/B/C2", items.get(4).getPath());
    assertEquals("D1", items.get(5).getName());
    assertEquals("./A/B/C2/D1", items.get(5).getPath());
    assertEquals("OTAS3", items.get(6).getName());
    assertEquals("./A/B/C2/D1/OTAS3", items.get(6).getPath());
    assertEquals("F1", items.get(7).getName());
    assertEquals("./A/B/C2/D1/OTAS3/F1", items.get(7).getPath());
    assertEquals("OTAS1", items.get(8).getName());
    assertEquals("./A/OTAS1", items.get(8).getPath());
    assertEquals("M", items.get(9).getName());
    assertEquals("./A/OTAS1/M", items.get(9).getPath());
    assertEquals("KC2", items.get(10).getName());
    assertEquals("./A/OTAS1/M/KC2", items.get(10).getPath());
    assertEquals("KD1", items.get(11).getName());
    assertEquals("./A/OTAS1/M/KC2/KD1", items.get(11).getPath());
    assertEquals("KE2", items.get(12).getName());
    assertEquals("./A/OTAS1/M/KC2/KD1/KE2", items.get(12).getPath());
    assertEquals("OTAS2", items.get(13).getName());
    assertEquals("./A/OTAS1/M/KC2/KD1/OTAS2", items.get(13).getPath());
    assertEquals("KF1", items.get(14).getName());
    assertEquals("./A/OTAS1/M/KC2/KD1/OTAS2/KF1", items.get(14).getPath());
    
    // Test get dynamic items
    items = registry.getDynamicItems();
    assertEquals(6, items.size());
    assertEquals("C1", items.get(0).getName());
    assertEquals("./A/B/C1", items.get(0).getPath());
    assertEquals("C2", items.get(1).getName());
    assertEquals("./A/B/C2", items.get(1).getPath());
    assertEquals("OTAS3", items.get(2).getName());
    assertEquals("./A/B/C2/D1/OTAS3", items.get(2).getPath());
    assertEquals("OTAS1", items.get(3).getName());
    assertEquals("./A/OTAS1", items.get(3).getPath());
    assertEquals("KC2", items.get(4).getName());
    assertEquals("./A/OTAS1/M/KC2", items.get(4).getPath());
    assertEquals("OTAS2", items.get(5).getName());
    assertEquals("./A/OTAS1/M/KC2/KD1/OTAS2", items.get(5).getPath());
    
    // Test toString()
    System.out.println(registry.toString());
  }

}
