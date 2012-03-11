/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/TestSoftwareCategoryTask.java,v 1.3 2008/11/28 10:26:17 chenlei Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/28 10:26:17 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.dm.setup.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.setup.console.ConsoleImpl;
import com.npower.setup.core.Console;
import com.npower.setup.core.Setup;
import com.npower.setup.core.SetupException;
import com.npower.setup.core.SetupImpl;

/**
 * @author chenlei
 * @version $Revision: 1.3 $ $Date: 2008/11/28 10:26:17 $
 */

public class TestSoftwareCategoryTask extends TestCase {
  private  ManagementBeanFactory factory = null;
  
  private SoftwareBean softwareBean = null;
  private static String softwareCategoryFile = "D:/Zhao/MyWorkspace/OTAS-Setup-Common/metadata/test/dm/Software/SoftwareCategory.default.xml";
  
  private String cName_1 = "网络工具";
  private String cDescription_1 = "网络工具所有软件";
  
  private String cName_1_child_Name = "网络加速";
  private String cDescription_child_1 = "网络加速的所有软件";
  
  private String cName_2 = "应用软件";
  private String cDescription_2 = "工应用软件的所有软件";
  
  private String cName_2_child_Name = "文档管理";
  private String cDescription_child_2 = "文档管理的所有软件";
  
  private String cName_3 = "图文处理";
  private String cDescription_3 = "图文处理的所有软件";
  
  private String cName_3_child_Name = "图片压缩";
  private String cDescription_child_3 = "图片压缩的所有软件";
  
  /**
   * @param name
   */
  public TestSoftwareCategoryTask(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    System.setProperty("otas.dm.home", "D:/OTASDM");
    factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    softwareBean = factory.createSoftwareBean();
  }

  public void testLoadSoftwareCategoryItems() throws SetupException {
    SoftwareCategoryTask task = new SoftwareCategoryTask();
    List<SoftwareCategoryItem> categories = task.loadSoftwareCategoryItems(softwareCategoryFile);
    assertNotNull(categories);
    assertTrue(categories.size() == 3);
    
    SoftwareCategoryItem s = categories.get(0);
    assertEquals(s.getName(), this.cName_1);
    assertEquals(s.getDescription(), this.cDescription_1);
    assertEquals(s.getSoftwareCategory().get(0).getName(), this.cName_1_child_Name);
    assertEquals(s.getSoftwareCategory().get(0).getDescription(), this.cDescription_child_1);
    
    SoftwareCategoryItem s1 = categories.get(1);
    assertEquals(s1.getName(), this.cName_2);
    assertEquals(s1.getDescription(), this.cDescription_2);
    assertEquals(s1.getSoftwareCategory().get(0).getName(), this.cName_2_child_Name);
    assertEquals(s1.getSoftwareCategory().get(0).getDescription(), this.cDescription_child_2);
    
    
    SoftwareCategoryItem s2 = categories.get(2);
    assertEquals(s2.getName(), this.cName_3);
    assertEquals(s2.getDescription(), this.cDescription_3);
    assertEquals(s2.getSoftwareCategory().get(0).getName(), this.cName_3_child_Name);
    assertEquals(s2.getSoftwareCategory().get(0).getDescription(), this.cDescription_child_3);
  }
  
  public void testCopy() throws Exception {
    SoftwareCategoryTask task = new SoftwareCategoryTask();
    List<SoftwareCategoryItem> items = task.loadSoftwareCategoryItems(softwareCategoryFile);
    assertNotNull(items);
    assertTrue(items.size() == 3);
    List<SoftwareCategory> resultList = new ArrayList<SoftwareCategory>();
    
    for (SoftwareCategoryItem item: items) {
      SoftwareCategory category = softwareBean.newCategoryInstance();
      SoftwareCategory category1 = softwareBean.newCategoryInstance();
      task.copy(item, category, category1);
      resultList.add(category);
    }
    

    
    assertNotNull(resultList);
    assertTrue(resultList.size() == 3);
    SoftwareCategory s = (SoftwareCategory)resultList.get(0);
    assertEquals(s.getName(), this.cName_1);
    assertEquals(s.getDescription(), this.cDescription_1);
    Set<SoftwareCategory> set = s.getChildren();
    Iterator<SoftwareCategory> it = set.iterator();
    while (it.hasNext()) {
      SoftwareCategory ss = it.next();
      assertEquals(ss.getName(), this.cName_1_child_Name);
      assertEquals(ss.getDescription(), this.cDescription_child_1);
    }

    
    SoftwareCategory s1 = (SoftwareCategory)resultList.get(1);
    assertEquals(s1.getName(), this.cName_2);
    assertEquals(s1.getDescription(), this.cDescription_2);
    Set<SoftwareCategory> set1 = s1.getChildren();
    Iterator<SoftwareCategory> it1 = set1.iterator();
    while (it1.hasNext()) {
      SoftwareCategory ss = it1.next();
      assertEquals(ss.getName(), this.cName_2_child_Name);
      assertEquals(ss.getDescription(), this.cDescription_child_2);
    }
    
    
    SoftwareCategory s2 = (SoftwareCategory)resultList.get(2);
    assertEquals(s2.getName(), this.cName_3);
    assertEquals(s2.getDescription(), this.cDescription_3);
    Set<SoftwareCategory> set2 = s2.getChildren();
    Iterator<SoftwareCategory> it2 = set2.iterator();
    while (it2.hasNext()) {
      SoftwareCategory ss = it2.next();
      assertEquals(ss.getName(), this.cName_3_child_Name);
      assertEquals(ss.getDescription(), this.cDescription_child_3);
    }
  }
  
  public void testprocessSoftwareCategory() throws Exception{
    Setup setup = new SetupImpl();
    Console console = new ConsoleImpl();
    setup.setConsole(console);
    
    SoftwareCategoryTask task = new SoftwareCategoryTask();
    task.setSetup(setup);
    
    List<String> filenames = new ArrayList<String>();
    filenames.add(softwareCategoryFile);
    task.setFilenames(filenames);
    task.setManagementBeanFactory(factory);  
    task.process();
    
    factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    softwareBean = factory.createSoftwareBean();
    List<SoftwareCategory> categories = softwareBean.getAllOfRootCategories();
    
    assertNotNull(categories);
    assertTrue(categories.size() == 3);
    
    SoftwareCategory s = categories.get(0);
    assertEquals(s.getName(), this.cName_1);
    assertEquals(s.getDescription(), this.cDescription_1);
    Set<SoftwareCategory> set = s.getChildren();
    Iterator<SoftwareCategory> it = set.iterator();
    while (it.hasNext()) {
      SoftwareCategory ss = it.next();
      assertEquals(ss.getName(), this.cName_1_child_Name);
      assertEquals(ss.getDescription(), this.cDescription_child_1);
    }

    
    SoftwareCategory s1 = categories.get(1);
    assertEquals(s1.getName(), this.cName_2);
    assertEquals(s1.getDescription(), this.cDescription_2);
    Set<SoftwareCategory> set1 = s1.getChildren();
    Iterator<SoftwareCategory> it1 = set1.iterator();
    while (it1.hasNext()) {
      SoftwareCategory ss1 = it1.next();
      assertEquals(ss1.getName(), this.cName_2_child_Name);
      assertEquals(ss1.getDescription(), this.cDescription_child_2);
    }
    
    
    SoftwareCategory s2 = categories.get(2);
    assertEquals(s2.getName(), this.cName_3);
    assertEquals(s2.getDescription(), this.cDescription_3);
    Set<SoftwareCategory> set2 = s2.getChildren();
    Iterator<SoftwareCategory> it2 = set2.iterator();
    while (it2.hasNext()) {
      SoftwareCategory ss2 = it2.next();
      assertEquals(ss2.getName(), this.cName_3_child_Name);
      assertEquals(ss2.getDescription(), this.cDescription_child_3);
    }
  }
  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

}
