/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/selector/TestSelector.java,v 1.7 2008/09/19 02:46:40 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/09/19 02:46:40 $
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
package com.npower.dm.core.selector;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

import com.npower.dm.AllTests;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelSelector;
import com.npower.dm.core.selector.common.ScreenResolutionSelector;
import com.npower.dm.core.selector.j2me.MidpV1Selector;
import com.npower.dm.core.selector.j2me.MidpV2Selector;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * 需预先运行DMSetup程序, 创建型号数据库
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/09/19 02:46:40 $
 */
public class TestSelector extends TestCase {

  /**
   * @param name
   */
  public TestSelector(String name) {
    super(name);
  }

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
   * 打印所有型号的屏幕信息.
   * @throws Exception
   */
  public void testPrintScreenInfo() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean bean = factory.createModelBean();
        for (Model m: bean.getAllModel()) {
          String h = bean.getCapability(m, "resolution_height");
          String w = bean.getCapability(m, "resolution_width");
          System.out.println(m.getManufacturer().getExternalId() + " " + m.getManufacturerModelId() + ": " + h + ", " + w);
        }
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  /**
   * 测试MIDP V1 Selector选择的结果
   * @throws Exception
   */
  /**
   * @throws Exception
   */
  public void testMidpV1Selector() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelSelector selector = (ModelSelector)factory.createBean(MidpV1Selector.class);
        Set<Model> result = selector.getModels();
        
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getMidpV1(bean);
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(631, result.size());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试MIDP V1 Selector选择的结果
   * @throws Exception
   */
  /**
   * @throws Exception
   */
  public void testMidpV2Selector() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelSelector selector = (ModelSelector)factory.createBean(MidpV2Selector.class);
        Set<Model> result = selector.getModels();
        
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getMidpV2(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(409, result.size());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试Symbian S60 V3 Selector选择的结果
   * @throws Exception
   */
  /**
   * @throws Exception
   */
  public void testS60V3Selector() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
        selector.addExpression(Expression.like("familyExternalID", "family_nokia_s60_3rd", MatchMode.START));
        /*
        String os = "S60 3rd Edition"; // S60 3rd edition (Symbian OS , Series 60 UI)
        selector.addExpression(Expression.or(CharacterExpression.ilike("general.developer.platform", os, MatchMode.ANYWHERE), 
                                             CharacterExpression.ilike("features.os", os, MatchMode.ANYWHERE)));
        */
        Set<Model> result = selector.getModels();
        
        for (Model m: result) {
            System.out.println("expected.add(getModel(bean, \"" + m.getManufacturer().getExternalId() + "\", \"" + m.getManufacturerModelId() +"\"));");
        }
    
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getS60V3(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(39, result.size());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试Symbian S60 V2 Selector选择的结果
   * @throws Exception
   */
  /**
   * @throws Exception
   */
  public void testS60V2Selector() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
        selector.addExpression(Expression.like("familyExternalID", "family_nokia_s60_2nd", MatchMode.START));
        /*
        String os = "S60 2nd Edition"; 
        selector.addExpression(Expression.or(CharacterExpression.ilike("general.developer.platform", os, MatchMode.ANYWHERE), 
                                             CharacterExpression.ilike("features.os", os, MatchMode.ANYWHERE)));
        */
        Set<Model> result = selector.getModels();
        
        for (Model m: result) {
            System.out.println("expected.add(getModel(bean, \"" + m.getManufacturer().getExternalId() + "\", \"" + m.getManufacturerModelId() +"\"));");
        }
    
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getS60V2(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(16, result.size());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试Symbian S60 V3 Selector选择的结果
   * @throws Exception
   */
  /**
   * @throws Exception
   */
  public void testWindowsMobile_5_0_PPC() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        String os = "Microsoft Windows Mobile 5.0 PocketPC"; 
        CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
        selector.addExpression(CharacterExpression.ilike("general.operating.system", os, MatchMode.START));
        Set<Model> result = selector.getModels();
        
        for (Model m: result) {
            System.out.println("expected.add(getModel(bean, \"" + m.getManufacturer().getExternalId() + "\", \"" + m.getManufacturerModelId() +"\"));");
        }
    
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getWindowsMobile_5_0_PPC(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(73, result.size());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试Symbian S60 V3 Selector选择的结果
   * @throws Exception
   */
  /**
   * @throws Exception
   */
  public void testWindowsMobile_5_0_SmartPhone() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        String os = "Microsoft Windows Mobile 5.0 Smartphone"; 
        CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
        selector.addExpression(CharacterExpression.ilike("general.operating.system", os, MatchMode.START));
        Set<Model> result = selector.getModels();
        
        for (Model m: result) {
            System.out.println("expected.add(getModel(bean, \"" + m.getManufacturer().getExternalId() + "\", \"" + m.getManufacturerModelId() +"\"));");
        }
    
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getWindowsMobile_5_0_SmartPhone(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(29, result.size());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试Symbian S60 V3 Selector选择的结果
   * @throws Exception
   */
  /**
   * @throws Exception
   */
  public void testWindowsMobile_6_0_Professional() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        String os = "Microsoft Windows Mobile 6.0 Prof"; 
        CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
        selector.addExpression(CharacterExpression.ilike("general.operating.system", os, MatchMode.START));
        Set<Model> result = selector.getModels();
        
        for (Model m: result) {
            System.out.println("expected.add(getModel(bean, \"" + m.getManufacturer().getExternalId() + "\", \"" + m.getManufacturerModelId() +"\"));");
        }
    
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getWindowsMobile_6_0_Professional(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(29, result.size());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试Symbian S60 V3 Selector选择的结果
   * @throws Exception
   */
  /**
   * @throws Exception
   */
  public void testWindowsMobile_6_0_Standard() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        String os = "Microsoft Windows Mobile 6.0 Standard"; 
        CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
        selector.addExpression(CharacterExpression.ilike("general.operating.system", os, MatchMode.START));
        Set<Model> result = selector.getModels();
        
        for (Model m: result) {
            System.out.println("expected.add(getModel(bean, \"" + m.getManufacturer().getExternalId() + "\", \"" + m.getManufacturerModelId() +"\"));");
        }
    
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getWindowsMobile_6_0_Standard(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(13, result.size());
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试Screen Selector选择的结果
   * @throws Exception
   */
  /**
   * @throws Exception
   */
  public void testScreenSelector_320_240() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ScreenResolutionSelector selector = (ScreenResolutionSelector)factory.createBean(ScreenResolutionSelector.class);
        selector.setHeight(320);
        selector.setWidth(240);
        
        Set<Model> result = selector.getModels();
        
        for (Model m: result) {
            System.out.println("expected.add(getModel(bean, \"" + m.getManufacturer().getExternalId() + "\", \"" + m.getManufacturerModelId() +"\"));");
        }
      
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getScreen_320_240(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(143, result.size());

    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试Screen Selector选择的结果
   * @throws Exception
   */
  public void testScreenSelector_208_176() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ScreenResolutionSelector selector = (ScreenResolutionSelector)factory.createBean(ScreenResolutionSelector.class);
        selector.setHeight(208);
        selector.setWidth(176);
        
        Set<Model> result = selector.getModels();
        
        for (Model m: result) {
            System.out.println("expected.add(getModel(bean, \"" + m.getManufacturer().getExternalId() + "\", \"" + m.getManufacturerModelId() +"\"));");
        }
      
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getScreen_208_176(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(26, result.size());

    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试Screen Selector选择的结果
   * @throws Exception
   */
  public void testScreenSelector_208_208() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ScreenResolutionSelector selector = (ScreenResolutionSelector)factory.createBean(ScreenResolutionSelector.class);
        selector.setHeight(208);
        selector.setWidth(208);
        
        Set<Model> result = selector.getModels();
        
        for (Model m: result) {
            System.out.println("expected.add(getModel(bean, \"" + m.getManufacturer().getExternalId() + "\", \"" + m.getManufacturerModelId() +"\"));");
        }
      
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getScreen_208_208(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(3, result.size());

    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试Screen Selector选择的结果
   * @throws Exception
   */
  public void testScreenSelector_416_352() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ScreenResolutionSelector selector = (ScreenResolutionSelector)factory.createBean(ScreenResolutionSelector.class);
        selector.setHeight(416);
        selector.setWidth(352);
        
        Set<Model> result = selector.getModels();
        
        for (Model m: result) {
            System.out.println("expected.add(getModel(bean, \"" + m.getManufacturer().getExternalId() + "\", \"" + m.getManufacturerModelId() +"\"));");
        }
      
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getScreen_416_352(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(5, result.size());

    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 测试MIDP V1 Selector 和Screen Selector选择的结果
   * @throws Exception
   */
  /**
   * @throws Exception
   */
  public void testMidpV2SelectorAndScreenSelector_320_240() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ConjunctionModelSelector selector = (ConjunctionModelSelector)factory.createBean(ConjunctionModelSelector.class);
        ModelSelector selector1 = (ModelSelector)factory.createBean(MidpV2Selector.class);
        ScreenResolutionSelector selector2 = (ScreenResolutionSelector)factory.createBean(ScreenResolutionSelector.class);
        selector2.setHeight(320);
        selector2.setWidth(240);
        
        selector.add(selector1);
        selector.add(selector2);
        
        Set<Model> result = selector.getModels();
        
        for (Model m: result) {
            System.out.println("expected.add(getModel(bean, \"" + m.getManufacturer().getExternalId() + "\", \"" + m.getManufacturerModelId() +"\"));");
        }
      
        ModelBean bean = factory.createModelBean();
        Set<Model> expected = getMidpV2_Screen_320_240(bean);
        
        // 检查是否选择器选中了某些不被期望的型号
        checkUnexpectedModel(result, expected);
        // 检查是否选择器遗漏了某些型号
        checkMissingModel(result, expected);
      
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
        
        assertEquals(102, result.size());

    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 检查是否选择器选中了某些不被期望的型号
   * @param result
   * @param expected
   */
  private void checkUnexpectedModel(Set<Model> result, Set<Model> expected) {
    for (Model m: result) {
        assertTrue("Unexpected Model: " + m.getManufacturer().getExternalId() + " " + m.getManufacturerModelId(), 
                   expected.contains(m));
    }
  }

  /**
   * 检查是否选择器遗漏了某些型号.
   * @param result
   * @param expected
   */
  private void checkMissingModel(Set<Model> result, Set<Model> expected) {
    for (Model m: expected) {
        assertTrue("Missing Model: " + m.getManufacturer().getExternalId() + " " + m.getManufacturerModelId(), 
                    result.contains(m));
    }
  }

  /**
   * Expected Java MIDP V1 Models
   * @param bean
   * @return
   * @throws DMException
   */
  private Set<Model> getMidpV1(ModelBean bean) throws DMException {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "Alcatel", "OTC550"));
    expected.add(getModel(bean, "Alcatel", "OTS853"));
    expected.add(getModel(bean, "BenQ-Siemens", "EF81"));
    expected.add(getModel(bean, "BenQ-Siemens", "EL71"));
    expected.add(getModel(bean, "BenQ-Siemens", "S68"));
    expected.add(getModel(bean, "HP", "iPAQ hw6915"));
    expected.add(getModel(bean, "HP", "iPAQ rw6815"));
    expected.add(getModel(bean, "HTC", "2125"));
    expected.add(getModel(bean, "HTC", "3100 (Star Trek)"));
    expected.add(getModel(bean, "HTC", "9090"));
    expected.add(getModel(bean, "HTC", "Apache, PPC 6700"));
    expected.add(getModel(bean, "HTC", "Elf, P3450"));
    expected.add(getModel(bean, "HTC", "Hermes"));
    expected.add(getModel(bean, "HTC", "MDA Compact"));
    expected.add(getModel(bean, "HTC", "Prophet"));
    expected.add(getModel(bean, "HTC", "S621"));
    expected.add(getModel(bean, "HTC", "S710"));
    expected.add(getModel(bean, "HTC", "SDA"));
    expected.add(getModel(bean, "HTC", "Wizard"));
    expected.add(getModel(bean, "HTC", "XDA Trion"));
    expected.add(getModel(bean, "Kyocera", "KX1"));
    expected.add(getModel(bean, "LG", "A7150"));
    expected.add(getModel(bean, "LG", "B2000"));
    expected.add(getModel(bean, "LG", "B2050"));
    expected.add(getModel(bean, "LG", "C1100"));
    expected.add(getModel(bean, "LG", "C1150"));
    expected.add(getModel(bean, "LG", "C1200"));
    expected.add(getModel(bean, "LG", "C1300"));
    expected.add(getModel(bean, "LG", "C3300"));
    expected.add(getModel(bean, "LG", "C3310"));
    expected.add(getModel(bean, "LG", "C3320"));
    expected.add(getModel(bean, "LG", "C3380"));
    expected.add(getModel(bean, "LG", "CU320"));
    expected.add(getModel(bean, "LG", "CU500"));
    expected.add(getModel(bean, "LG", "F2100"));
    expected.add(getModel(bean, "LG", "F2300"));
    expected.add(getModel(bean, "LG", "F7250"));
    expected.add(getModel(bean, "LG", "G5300i"));
    expected.add(getModel(bean, "LG", "G5400"));
    expected.add(getModel(bean, "LG", "G7050"));
    expected.add(getModel(bean, "LG", "G7100"));
    expected.add(getModel(bean, "LG", "KG220"));
    expected.add(getModel(bean, "LG", "KS10"));
    expected.add(getModel(bean, "LG", "KU800"));
    expected.add(getModel(bean, "LG", "L1100"));
    expected.add(getModel(bean, "LG", "L1400"));
    expected.add(getModel(bean, "LG", "L3100"));
    expected.add(getModel(bean, "LG", "L600v"));
    expected.add(getModel(bean, "LG", "LX550"));
    expected.add(getModel(bean, "LG", "MM535"));
    expected.add(getModel(bean, "LG", "PM225"));
    expected.add(getModel(bean, "LG", "T5100"));
    expected.add(getModel(bean, "LG", "U400"));
    expected.add(getModel(bean, "LG", "U8100"));
    expected.add(getModel(bean, "LG", "U8138"));
    expected.add(getModel(bean, "LG", "U8150"));
    expected.add(getModel(bean, "LG", "U8180"));
    expected.add(getModel(bean, "LG", "U8210"));
    expected.add(getModel(bean, "LG", "U8290"));
    expected.add(getModel(bean, "LG", "U890"));
    expected.add(getModel(bean, "Motorola", "A1000"));
    expected.add(getModel(bean, "Motorola", "A388"));
    expected.add(getModel(bean, "Motorola", "A630"));
    expected.add(getModel(bean, "Motorola", "A668"));
    expected.add(getModel(bean, "Motorola", "A728"));
    expected.add(getModel(bean, "Motorola", "A760"));
    expected.add(getModel(bean, "Motorola", "A768"));
    expected.add(getModel(bean, "Motorola", "A780"));
    expected.add(getModel(bean, "Motorola", "A830"));
    expected.add(getModel(bean, "Motorola", "A835"));
    expected.add(getModel(bean, "Motorola", "A845"));
    expected.add(getModel(bean, "Motorola", "A920"));
    expected.add(getModel(bean, "Motorola", "A925"));
    expected.add(getModel(bean, "Motorola", "C350"));
    expected.add(getModel(bean, "Motorola", "C350i"));
    expected.add(getModel(bean, "Motorola", "C380"));
    expected.add(getModel(bean, "Motorola", "C381"));
    expected.add(getModel(bean, "Motorola", "C385"));
    expected.add(getModel(bean, "Motorola", "C390"));
    expected.add(getModel(bean, "Motorola", "C450"));
    expected.add(getModel(bean, "Motorola", "C550"));
    expected.add(getModel(bean, "Motorola", "C650"));
    expected.add(getModel(bean, "Motorola", "C651"));
    expected.add(getModel(bean, "Motorola", "C980"));
    expected.add(getModel(bean, "Motorola", "E1000"));
    expected.add(getModel(bean, "Motorola", "E1070"));
    expected.add(getModel(bean, "Motorola", "E375"));
    expected.add(getModel(bean, "Motorola", "E398"));
    expected.add(getModel(bean, "Motorola", "E550"));
    expected.add(getModel(bean, "Motorola", "E680"));
    expected.add(getModel(bean, "Motorola", "E680i"));
    expected.add(getModel(bean, "Motorola", "E770v"));
    expected.add(getModel(bean, "Motorola", "L2"));
    expected.add(getModel(bean, "Motorola", "L6"));
    expected.add(getModel(bean, "Motorola", "MPX100"));
    expected.add(getModel(bean, "Motorola", "MPX200"));
    expected.add(getModel(bean, "Motorola", "MPX220"));
    expected.add(getModel(bean, "Motorola", "RAZRV6"));
    expected.add(getModel(bean, "Motorola", "SLVRL7"));
    expected.add(getModel(bean, "Motorola", "SLVRL72"));
    expected.add(getModel(bean, "Motorola", "SLVRL7e"));
    expected.add(getModel(bean, "Motorola", "SLVRL7i"));
    expected.add(getModel(bean, "Motorola", "V1050"));
    expected.add(getModel(bean, "Motorola", "V1075"));
    expected.add(getModel(bean, "Motorola", "V1100"));
    expected.add(getModel(bean, "Motorola", "V180"));
    expected.add(getModel(bean, "Motorola", "V190"));
    expected.add(getModel(bean, "Motorola", "V191"));
    expected.add(getModel(bean, "Motorola", "V220"));
    expected.add(getModel(bean, "Motorola", "V226"));
    expected.add(getModel(bean, "Motorola", "V280"));
    expected.add(getModel(bean, "Motorola", "V3"));
    expected.add(getModel(bean, "Motorola", "V300"));
    expected.add(getModel(bean, "Motorola", "V303"));
    expected.add(getModel(bean, "Motorola", "V330"));
    expected.add(getModel(bean, "Motorola", "V360"));
    expected.add(getModel(bean, "Motorola", "V360v"));
    expected.add(getModel(bean, "Motorola", "V361"));
    expected.add(getModel(bean, "Motorola", "V365"));
    expected.add(getModel(bean, "Motorola", "V3c"));
    expected.add(getModel(bean, "Motorola", "V3e"));
    expected.add(getModel(bean, "Motorola", "V3i"));
    expected.add(getModel(bean, "Motorola", "V3m"));
    expected.add(getModel(bean, "Motorola", "V3t"));
    expected.add(getModel(bean, "Motorola", "V3x"));
    expected.add(getModel(bean, "Motorola", "V3xx"));
    expected.add(getModel(bean, "Motorola", "V400"));
    expected.add(getModel(bean, "Motorola", "V400p"));
    expected.add(getModel(bean, "Motorola", "V500"));
    expected.add(getModel(bean, "Motorola", "V501"));
    expected.add(getModel(bean, "Motorola", "V505"));
    expected.add(getModel(bean, "Motorola", "V525"));
    expected.add(getModel(bean, "Motorola", "V535"));
    expected.add(getModel(bean, "Motorola", "V545"));
    expected.add(getModel(bean, "Motorola", "V547"));
    expected.add(getModel(bean, "Motorola", "V550"));
    expected.add(getModel(bean, "Motorola", "V557"));
    expected.add(getModel(bean, "Motorola", "V557p"));
    expected.add(getModel(bean, "Motorola", "V600"));
    expected.add(getModel(bean, "Motorola", "V600i"));
    expected.add(getModel(bean, "Motorola", "V620"));
    expected.add(getModel(bean, "Motorola", "V690"));
    expected.add(getModel(bean, "Motorola", "V6PEBL"));
    expected.add(getModel(bean, "Motorola", "V6V"));
    expected.add(getModel(bean, "Motorola", "V8"));
    expected.add(getModel(bean, "Motorola", "V8 iTunes"));
    expected.add(getModel(bean, "Motorola", "V80"));
    expected.add(getModel(bean, "Motorola", "V872"));
    expected.add(getModel(bean, "Motorola", "V878"));
    expected.add(getModel(bean, "Motorola", "V8v"));
    expected.add(getModel(bean, "Motorola", "V975"));
    expected.add(getModel(bean, "Motorola", "V980"));
    expected.add(getModel(bean, "NOKIA", "2600"));
    expected.add(getModel(bean, "NOKIA", "2610"));
    expected.add(getModel(bean, "NOKIA", "2626"));
    expected.add(getModel(bean, "NOKIA", "2630"));
    expected.add(getModel(bean, "NOKIA", "2650"));
    expected.add(getModel(bean, "NOKIA", "2652"));
    expected.add(getModel(bean, "NOKIA", "2855"));
    expected.add(getModel(bean, "NOKIA", "2865"));
    expected.add(getModel(bean, "NOKIA", "2865i"));
    expected.add(getModel(bean, "NOKIA", "3100"));
    expected.add(getModel(bean, "NOKIA", "3108"));
    expected.add(getModel(bean, "NOKIA", "3109c"));
    expected.add(getModel(bean, "NOKIA", "3120"));
    expected.add(getModel(bean, "NOKIA", "3128"));
    expected.add(getModel(bean, "NOKIA", "3200"));
    expected.add(getModel(bean, "NOKIA", "3220"));
    expected.add(getModel(bean, "NOKIA", "3230"));
    expected.add(getModel(bean, "NOKIA", "3250"));
    expected.add(getModel(bean, "NOKIA", "3300"));
    expected.add(getModel(bean, "NOKIA", "3410"));
    expected.add(getModel(bean, "NOKIA", "3510i"));
    expected.add(getModel(bean, "NOKIA", "3530"));
    expected.add(getModel(bean, "NOKIA", "3595"));
    expected.add(getModel(bean, "NOKIA", "3600"));
    expected.add(getModel(bean, "NOKIA", "3620"));
    expected.add(getModel(bean, "NOKIA", "3650"));
    expected.add(getModel(bean, "NOKIA", "3660"));
    expected.add(getModel(bean, "NOKIA", "5070"));
    expected.add(getModel(bean, "NOKIA", "5100"));
    expected.add(getModel(bean, "NOKIA", "5140"));
    expected.add(getModel(bean, "NOKIA", "5140i"));
    expected.add(getModel(bean, "NOKIA", "5200"));
    expected.add(getModel(bean, "NOKIA", "5300"));
    expected.add(getModel(bean, "NOKIA", "5500"));
    expected.add(getModel(bean, "NOKIA", "5500d"));
    expected.add(getModel(bean, "NOKIA", "5610"));
    expected.add(getModel(bean, "NOKIA", "5700"));
    expected.add(getModel(bean, "NOKIA", "6010"));
    expected.add(getModel(bean, "NOKIA", "6020"));
    expected.add(getModel(bean, "NOKIA", "6020b"));
    expected.add(getModel(bean, "NOKIA", "6021"));
    expected.add(getModel(bean, "NOKIA", "6030"));
    expected.add(getModel(bean, "NOKIA", "6060"));
    expected.add(getModel(bean, "NOKIA", "6070"));
    expected.add(getModel(bean, "NOKIA", "6080"));
    expected.add(getModel(bean, "NOKIA", "6085"));
    expected.add(getModel(bean, "NOKIA", "6086"));
    expected.add(getModel(bean, "NOKIA", "6100"));
    expected.add(getModel(bean, "NOKIA", "6101"));
    expected.add(getModel(bean, "NOKIA", "6102"));
    expected.add(getModel(bean, "NOKIA", "6102i"));
    expected.add(getModel(bean, "NOKIA", "6103"));
    expected.add(getModel(bean, "NOKIA", "6111"));
    expected.add(getModel(bean, "NOKIA", "6120c"));
    expected.add(getModel(bean, "NOKIA", "6121c"));
    expected.add(getModel(bean, "NOKIA", "6125"));
    expected.add(getModel(bean, "NOKIA", "6126"));
    expected.add(getModel(bean, "NOKIA", "6133"));
    expected.add(getModel(bean, "NOKIA", "6136"));
    expected.add(getModel(bean, "NOKIA", "6151"));
    expected.add(getModel(bean, "NOKIA", "6165"));
    expected.add(getModel(bean, "NOKIA", "6170"));
    expected.add(getModel(bean, "NOKIA", "6170b"));
    expected.add(getModel(bean, "NOKIA", "6200"));
    expected.add(getModel(bean, "NOKIA", "6200IM"));
    expected.add(getModel(bean, "NOKIA", "6230"));
    expected.add(getModel(bean, "NOKIA", "6230i"));
    expected.add(getModel(bean, "NOKIA", "6233"));
    expected.add(getModel(bean, "NOKIA", "6234"));
    expected.add(getModel(bean, "NOKIA", "6260"));
    expected.add(getModel(bean, "NOKIA", "6265"));
    expected.add(getModel(bean, "NOKIA", "6265i"));
    expected.add(getModel(bean, "NOKIA", "6267"));
    expected.add(getModel(bean, "NOKIA", "6270"));
    expected.add(getModel(bean, "NOKIA", "6275"));
    expected.add(getModel(bean, "NOKIA", "6275i"));
    expected.add(getModel(bean, "NOKIA", "6280"));
    expected.add(getModel(bean, "NOKIA", "6282"));
    expected.add(getModel(bean, "NOKIA", "6288"));
    expected.add(getModel(bean, "NOKIA", "6290"));
    expected.add(getModel(bean, "NOKIA", "6300"));
    expected.add(getModel(bean, "NOKIA", "6301"));
    expected.add(getModel(bean, "NOKIA", "6310i"));
    expected.add(getModel(bean, "NOKIA", "6500"));
    expected.add(getModel(bean, "NOKIA", "6500c"));
    expected.add(getModel(bean, "NOKIA", "6500s"));
    expected.add(getModel(bean, "NOKIA", "6555"));
    expected.add(getModel(bean, "NOKIA", "6600"));
    expected.add(getModel(bean, "NOKIA", "6610"));
    expected.add(getModel(bean, "NOKIA", "6610i"));
    expected.add(getModel(bean, "NOKIA", "6620"));
    expected.add(getModel(bean, "NOKIA", "6630"));
    expected.add(getModel(bean, "NOKIA", "6650"));
    expected.add(getModel(bean, "NOKIA", "6651"));
    expected.add(getModel(bean, "NOKIA", "6670"));
    expected.add(getModel(bean, "NOKIA", "6670b"));
    expected.add(getModel(bean, "NOKIA", "6680"));
    expected.add(getModel(bean, "NOKIA", "6681"));
    expected.add(getModel(bean, "NOKIA", "6682"));
    expected.add(getModel(bean, "NOKIA", "6708"));
    expected.add(getModel(bean, "NOKIA", "6800"));
    expected.add(getModel(bean, "NOKIA", "6810"));
    expected.add(getModel(bean, "NOKIA", "6820"));
    expected.add(getModel(bean, "NOKIA", "6820a"));
    expected.add(getModel(bean, "NOKIA", "6822"));
    expected.add(getModel(bean, "NOKIA", "6822a"));
    expected.add(getModel(bean, "NOKIA", "6822b"));
    expected.add(getModel(bean, "NOKIA", "7200"));
    expected.add(getModel(bean, "NOKIA", "7210"));
    expected.add(getModel(bean, "NOKIA", "7250"));
    expected.add(getModel(bean, "NOKIA", "7250i"));
    expected.add(getModel(bean, "NOKIA", "7260"));
    expected.add(getModel(bean, "NOKIA", "7270"));
    expected.add(getModel(bean, "NOKIA", "7360"));
    expected.add(getModel(bean, "NOKIA", "7370"));
    expected.add(getModel(bean, "NOKIA", "7373"));
    expected.add(getModel(bean, "NOKIA", "7380"));
    expected.add(getModel(bean, "NOKIA", "7390"));
    expected.add(getModel(bean, "NOKIA", "7500"));
    expected.add(getModel(bean, "NOKIA", "7600"));
    expected.add(getModel(bean, "NOKIA", "7610"));
    expected.add(getModel(bean, "NOKIA", "7650"));
    expected.add(getModel(bean, "NOKIA", "7710"));
    expected.add(getModel(bean, "NOKIA", "8801"));
    expected.add(getModel(bean, "NOKIA", "8910i"));
    expected.add(getModel(bean, "NOKIA", "9110i"));
    expected.add(getModel(bean, "NOKIA", "9210"));
    expected.add(getModel(bean, "NOKIA", "9210i"));
    expected.add(getModel(bean, "NOKIA", "9300"));
    expected.add(getModel(bean, "NOKIA", "9300i"));
    expected.add(getModel(bean, "NOKIA", "9500"));
    expected.add(getModel(bean, "NOKIA", "E50"));
    expected.add(getModel(bean, "NOKIA", "E60"));
    expected.add(getModel(bean, "NOKIA", "E61"));
    expected.add(getModel(bean, "NOKIA", "E61i"));
    expected.add(getModel(bean, "NOKIA", "E62"));
    expected.add(getModel(bean, "NOKIA", "E65"));
    expected.add(getModel(bean, "NOKIA", "E70"));
    expected.add(getModel(bean, "NOKIA", "N-Gage"));
    expected.add(getModel(bean, "NOKIA", "N-Gage QD"));
    expected.add(getModel(bean, "NOKIA", "N70"));
    expected.add(getModel(bean, "NOKIA", "N71"));
    expected.add(getModel(bean, "NOKIA", "N72"));
    expected.add(getModel(bean, "NOKIA", "N73"));
    expected.add(getModel(bean, "NOKIA", "N75"));
    expected.add(getModel(bean, "NOKIA", "N76"));
    expected.add(getModel(bean, "NOKIA", "N77"));
    expected.add(getModel(bean, "NOKIA", "N80"));
    expected.add(getModel(bean, "NOKIA", "N81"));
    expected.add(getModel(bean, "NOKIA", "N90"));
    expected.add(getModel(bean, "NOKIA", "N91"));
    expected.add(getModel(bean, "NOKIA", "N92"));
    expected.add(getModel(bean, "NOKIA", "N93"));
    expected.add(getModel(bean, "NOKIA", "N93i"));
    expected.add(getModel(bean, "NOKIA", "N95"));
    expected.add(getModel(bean, "Panasonic", "VS2"));
    expected.add(getModel(bean, "Panasonic", "VS6"));
    expected.add(getModel(bean, "Panasonic", "X200"));
    expected.add(getModel(bean, "Panasonic", "X400"));
    expected.add(getModel(bean, "Panasonic", "X500"));
    expected.add(getModel(bean, "Panasonic", "X700"));
    expected.add(getModel(bean, "Panasonic", "X701"));
    expected.add(getModel(bean, "Panasonic", "X800"));
    expected.add(getModel(bean, "Philips", "350"));
    expected.add(getModel(bean, "Philips", "568"));
    expected.add(getModel(bean, "Philips", "650"));
    expected.add(getModel(bean, "Philips", "755"));
    expected.add(getModel(bean, "Qtek", "8010"));
    expected.add(getModel(bean, "Qtek", "8080"));
    expected.add(getModel(bean, "Qtek", "9000"));
    expected.add(getModel(bean, "Qtek", "9090"));
    expected.add(getModel(bean, "Qtek", "9100"));
    expected.add(getModel(bean, "RIM", "BlackBerry 7100g"));
    expected.add(getModel(bean, "RIM", "BlackBerry 7130c"));
    expected.add(getModel(bean, "RIM", "BlackBerry 8700c"));
    expected.add(getModel(bean, "RIM", "BlackBerry 8700r"));
    expected.add(getModel(bean, "Sagem", "MY400V"));
    expected.add(getModel(bean, "Sagem", "MY401C"));
    expected.add(getModel(bean, "Sagem", "MY401V"));
    expected.add(getModel(bean, "Sagem", "MY411V"));
    expected.add(getModel(bean, "Sagem", "MYX52"));
    expected.add(getModel(bean, "Sagem", "myC5-2"));
    expected.add(getModel(bean, "Sagem", "myC5-2 Vodafone"));
    expected.add(getModel(bean, "Sagem", "myC5-3"));
    expected.add(getModel(bean, "Sagem", "myV-55"));
    expected.add(getModel(bean, "Sagem", "myX-6-2"));
    expected.add(getModel(bean, "Sagem", "myX-7"));
    expected.add(getModel(bean, "Sagem", "myX-8"));
    expected.add(getModel(bean, "Sagem", "myZ-5"));
    expected.add(getModel(bean, "Samsung", "C130"));
    expected.add(getModel(bean, "Samsung", "C200"));
    expected.add(getModel(bean, "Samsung", "C200C"));
    expected.add(getModel(bean, "Samsung", "C200N"));
    expected.add(getModel(bean, "Samsung", "C200S"));
    expected.add(getModel(bean, "Samsung", "C300"));
    expected.add(getModel(bean, "Samsung", "D500"));
    expected.add(getModel(bean, "Samsung", "D500C"));
    expected.add(getModel(bean, "Samsung", "D500E"));
    expected.add(getModel(bean, "Samsung", "D508"));
    expected.add(getModel(bean, "Samsung", "D520"));
    expected.add(getModel(bean, "Samsung", "D600"));
    expected.add(getModel(bean, "Samsung", "D608"));
    expected.add(getModel(bean, "Samsung", "D800"));
    expected.add(getModel(bean, "Samsung", "D820"));
    expected.add(getModel(bean, "Samsung", "D830"));
    expected.add(getModel(bean, "Samsung", "D840"));
    expected.add(getModel(bean, "Samsung", "D900"));
    expected.add(getModel(bean, "Samsung", "E100"));
    expected.add(getModel(bean, "Samsung", "E100A"));
    expected.add(getModel(bean, "Samsung", "E105"));
    expected.add(getModel(bean, "Samsung", "E250"));
    expected.add(getModel(bean, "Samsung", "E300"));
    expected.add(getModel(bean, "Samsung", "E310"));
    expected.add(getModel(bean, "Samsung", "E315"));
    expected.add(getModel(bean, "Samsung", "E317"));
    expected.add(getModel(bean, "Samsung", "E318"));
    expected.add(getModel(bean, "Samsung", "E320"));
    expected.add(getModel(bean, "Samsung", "E330"));
    expected.add(getModel(bean, "Samsung", "E330C"));
    expected.add(getModel(bean, "Samsung", "E330N"));
    expected.add(getModel(bean, "Samsung", "E335"));
    expected.add(getModel(bean, "Samsung", "E338"));
    expected.add(getModel(bean, "Samsung", "E350"));
    expected.add(getModel(bean, "Samsung", "E360"));
    expected.add(getModel(bean, "Samsung", "E370"));
    expected.add(getModel(bean, "Samsung", "E400"));
    expected.add(getModel(bean, "Samsung", "E530"));
    expected.add(getModel(bean, "Samsung", "E600"));
    expected.add(getModel(bean, "Samsung", "E600C"));
    expected.add(getModel(bean, "Samsung", "E610"));
    expected.add(getModel(bean, "Samsung", "E610C"));
    expected.add(getModel(bean, "Samsung", "E618"));
    expected.add(getModel(bean, "Samsung", "E620"));
    expected.add(getModel(bean, "Samsung", "E630"));
    expected.add(getModel(bean, "Samsung", "E630C"));
    expected.add(getModel(bean, "Samsung", "E638"));
    expected.add(getModel(bean, "Samsung", "E700"));
    expected.add(getModel(bean, "Samsung", "E700A"));
    expected.add(getModel(bean, "Samsung", "E708"));
    expected.add(getModel(bean, "Samsung", "E710"));
    expected.add(getModel(bean, "Samsung", "E720"));
    expected.add(getModel(bean, "Samsung", "E720C"));
    expected.add(getModel(bean, "Samsung", "E760"));
    expected.add(getModel(bean, "Samsung", "E800"));
    expected.add(getModel(bean, "Samsung", "E800C"));
    expected.add(getModel(bean, "Samsung", "E800N"));
    expected.add(getModel(bean, "Samsung", "E808"));
    expected.add(getModel(bean, "Samsung", "E810"));
    expected.add(getModel(bean, "Samsung", "E810C"));
    expected.add(getModel(bean, "Samsung", "E820"));
    expected.add(getModel(bean, "Samsung", "E820N"));
    expected.add(getModel(bean, "Samsung", "E820P"));
    expected.add(getModel(bean, "Samsung", "E820T"));
    expected.add(getModel(bean, "Samsung", "E900"));
    expected.add(getModel(bean, "Samsung", "I310"));
    expected.add(getModel(bean, "Samsung", "I320"));
    expected.add(getModel(bean, "Samsung", "I320N"));
    expected.add(getModel(bean, "Samsung", "I710"));
    expected.add(getModel(bean, "Samsung", "I750"));
    expected.add(getModel(bean, "Samsung", "IP-A790"));
    expected.add(getModel(bean, "Samsung", "MM-A700"));
    expected.add(getModel(bean, "Samsung", "P408"));
    expected.add(getModel(bean, "Samsung", "P510"));
    expected.add(getModel(bean, "Samsung", "P518"));
    expected.add(getModel(bean, "Samsung", "P700"));
    expected.add(getModel(bean, "Samsung", "P716"));
    expected.add(getModel(bean, "Samsung", "P730"));
    expected.add(getModel(bean, "Samsung", "P730C"));
    expected.add(getModel(bean, "Samsung", "P735"));
    expected.add(getModel(bean, "Samsung", "P738"));
    expected.add(getModel(bean, "Samsung", "PM-A740"));
    expected.add(getModel(bean, "Samsung", "S300M"));
    expected.add(getModel(bean, "Samsung", "SGH-T519"));
    expected.add(getModel(bean, "Samsung", "SGH-X496"));
    expected.add(getModel(bean, "Samsung", "T609"));
    expected.add(getModel(bean, "Samsung", "T619"));
    expected.add(getModel(bean, "Samsung", "T629"));
    expected.add(getModel(bean, "Samsung", "U600"));
    expected.add(getModel(bean, "Samsung", "U700"));
    expected.add(getModel(bean, "Samsung", "X100"));
    expected.add(getModel(bean, "Samsung", "X100A"));
    expected.add(getModel(bean, "Samsung", "X105"));
    expected.add(getModel(bean, "Samsung", "X108"));
    expected.add(getModel(bean, "Samsung", "X120"));
    expected.add(getModel(bean, "Samsung", "X160"));
    expected.add(getModel(bean, "Samsung", "X200"));
    expected.add(getModel(bean, "Samsung", "X400"));
    expected.add(getModel(bean, "Samsung", "X427"));
    expected.add(getModel(bean, "Samsung", "X430"));
    expected.add(getModel(bean, "Samsung", "X450"));
    expected.add(getModel(bean, "Samsung", "X458"));
    expected.add(getModel(bean, "Samsung", "X460"));
    expected.add(getModel(bean, "Samsung", "X460C"));
    expected.add(getModel(bean, "Samsung", "X468"));
    expected.add(getModel(bean, "Samsung", "X480"));
    expected.add(getModel(bean, "Samsung", "X480C"));
    expected.add(getModel(bean, "Samsung", "X510"));
    expected.add(getModel(bean, "Samsung", "X600"));
    expected.add(getModel(bean, "Samsung", "X600A"));
    expected.add(getModel(bean, "Samsung", "X608"));
    expected.add(getModel(bean, "Samsung", "X610"));
    expected.add(getModel(bean, "Samsung", "X640"));
    expected.add(getModel(bean, "Samsung", "X640C"));
    expected.add(getModel(bean, "Samsung", "X640S"));
    expected.add(getModel(bean, "Samsung", "X650"));
    expected.add(getModel(bean, "Samsung", "X660"));
    expected.add(getModel(bean, "Samsung", "X660V"));
    expected.add(getModel(bean, "Samsung", "X820"));
    expected.add(getModel(bean, "Samsung", "Z100"));
    expected.add(getModel(bean, "Samsung", "Z105"));
    expected.add(getModel(bean, "Samsung", "Z105U"));
    expected.add(getModel(bean, "Samsung", "Z107"));
    expected.add(getModel(bean, "Samsung", "Z110"));
    expected.add(getModel(bean, "Samsung", "Z130"));
    expected.add(getModel(bean, "Samsung", "Z140"));
    expected.add(getModel(bean, "Samsung", "Z140M"));
    expected.add(getModel(bean, "Samsung", "Z140N"));
    expected.add(getModel(bean, "Samsung", "Z140V"));
    expected.add(getModel(bean, "Samsung", "Z150"));
    expected.add(getModel(bean, "Samsung", "Z400"));
    expected.add(getModel(bean, "Samsung", "Z400V"));
    expected.add(getModel(bean, "Samsung", "Z500"));
    expected.add(getModel(bean, "Samsung", "Z500V"));
    expected.add(getModel(bean, "Samsung", "Z510"));
    expected.add(getModel(bean, "Samsung", "Z520V"));
    expected.add(getModel(bean, "Samsung", "Z540"));
    expected.add(getModel(bean, "Samsung", "Z560"));
    expected.add(getModel(bean, "Samsung", "Z720"));
    expected.add(getModel(bean, "Samsung", "ZV10"));
    expected.add(getModel(bean, "Samsung", "ZV30"));
    expected.add(getModel(bean, "Samsung", "ZV40"));
    expected.add(getModel(bean, "Samsung", "i520"));
    expected.add(getModel(bean, "Samsung", "i600"));
    expected.add(getModel(bean, "Samsung", "i620v"));
    expected.add(getModel(bean, "Sendo", "P600"));
    expected.add(getModel(bean, "Sendo", "S600"));
    expected.add(getModel(bean, "Sendo", "X"));
    expected.add(getModel(bean, "Sharp", "770SH"));
    expected.add(getModel(bean, "Sharp", "802SH"));
    expected.add(getModel(bean, "Sharp", "903SH"));
    expected.add(getModel(bean, "Sharp", "TM-100"));
    expected.add(getModel(bean, "Sharp", "TM-200"));
    expected.add(getModel(bean, "Siemens", "2128"));
    expected.add(getModel(bean, "Siemens", "A60"));
    expected.add(getModel(bean, "Siemens", "C55"));
    expected.add(getModel(bean, "Siemens", "C62"));
    expected.add(getModel(bean, "Siemens", "C65"));
    expected.add(getModel(bean, "Siemens", "C75"));
    expected.add(getModel(bean, "Siemens", "CF110"));
    expected.add(getModel(bean, "Siemens", "CF62T"));
    expected.add(getModel(bean, "Siemens", "CFX65"));
    expected.add(getModel(bean, "Siemens", "CT65"));
    expected.add(getModel(bean, "Siemens", "CV65"));
    expected.add(getModel(bean, "Siemens", "CX65"));
    expected.add(getModel(bean, "Siemens", "CX70"));
    expected.add(getModel(bean, "Siemens", "CX75"));
    expected.add(getModel(bean, "Siemens", "CXT65"));
    expected.add(getModel(bean, "Siemens", "CXT70"));
    expected.add(getModel(bean, "Siemens", "M50"));
    expected.add(getModel(bean, "Siemens", "M65"));
    expected.add(getModel(bean, "Siemens", "M65v"));
    expected.add(getModel(bean, "Siemens", "M75"));
    expected.add(getModel(bean, "Siemens", "MC60"));
    expected.add(getModel(bean, "Siemens", "ME75"));
    expected.add(getModel(bean, "Siemens", "MT50"));
    expected.add(getModel(bean, "Siemens", "S65"));
    expected.add(getModel(bean, "Siemens", "S65V"));
    expected.add(getModel(bean, "Siemens", "SF65"));
    expected.add(getModel(bean, "Siemens", "SK65"));
    expected.add(getModel(bean, "Siemens", "SL45i"));
    expected.add(getModel(bean, "Siemens", "SL65"));
    expected.add(getModel(bean, "Siemens", "SL65Escada"));
    expected.add(getModel(bean, "Siemens", "ST60"));
    expected.add(getModel(bean, "Siemens", "SX1"));
    expected.add(getModel(bean, "Siemens", "SX1McLaren"));
    expected.add(getModel(bean, "SonyEricsson", "D750i"));
    expected.add(getModel(bean, "SonyEricsson", "F500i"));
    expected.add(getModel(bean, "SonyEricsson", "J300a"));
    expected.add(getModel(bean, "SonyEricsson", "J300c"));
    expected.add(getModel(bean, "SonyEricsson", "J300i"));
    expected.add(getModel(bean, "SonyEricsson", "K310a"));
    expected.add(getModel(bean, "SonyEricsson", "K310c"));
    expected.add(getModel(bean, "SonyEricsson", "K500c"));
    expected.add(getModel(bean, "SonyEricsson", "K500i"));
    expected.add(getModel(bean, "SonyEricsson", "K506c"));
    expected.add(getModel(bean, "SonyEricsson", "K508c"));
    expected.add(getModel(bean, "SonyEricsson", "K510c"));
    expected.add(getModel(bean, "SonyEricsson", "K510i"));
    expected.add(getModel(bean, "SonyEricsson", "K600i"));
    expected.add(getModel(bean, "SonyEricsson", "K608i"));
    expected.add(getModel(bean, "SonyEricsson", "K610c"));
    expected.add(getModel(bean, "SonyEricsson", "K610i"));
    expected.add(getModel(bean, "SonyEricsson", "K700c"));
    expected.add(getModel(bean, "SonyEricsson", "K700i"));
    expected.add(getModel(bean, "SonyEricsson", "K750a"));
    expected.add(getModel(bean, "SonyEricsson", "K750c"));
    expected.add(getModel(bean, "SonyEricsson", "K750i"));
    expected.add(getModel(bean, "SonyEricsson", "K790a"));
    expected.add(getModel(bean, "SonyEricsson", "K790c"));
    expected.add(getModel(bean, "SonyEricsson", "K790i"));
    expected.add(getModel(bean, "SonyEricsson", "K800c"));
    expected.add(getModel(bean, "SonyEricsson", "K800i"));
    expected.add(getModel(bean, "SonyEricsson", "M600c"));
    expected.add(getModel(bean, "SonyEricsson", "M600i"));
    expected.add(getModel(bean, "SonyEricsson", "P800"));
    expected.add(getModel(bean, "SonyEricsson", "P802"));
    expected.add(getModel(bean, "SonyEricsson", "P900"));
    expected.add(getModel(bean, "SonyEricsson", "P908"));
    expected.add(getModel(bean, "SonyEricsson", "P910"));
    expected.add(getModel(bean, "SonyEricsson", "P910a"));
    expected.add(getModel(bean, "SonyEricsson", "P910c"));
    expected.add(getModel(bean, "SonyEricsson", "P910i"));
    expected.add(getModel(bean, "SonyEricsson", "P990c"));
    expected.add(getModel(bean, "SonyEricsson", "P990i"));
    expected.add(getModel(bean, "SonyEricsson", "S700a"));
    expected.add(getModel(bean, "SonyEricsson", "S700c"));
    expected.add(getModel(bean, "SonyEricsson", "S700i"));
    expected.add(getModel(bean, "SonyEricsson", "T237"));
    expected.add(getModel(bean, "SonyEricsson", "T610"));
    expected.add(getModel(bean, "SonyEricsson", "T616"));
    expected.add(getModel(bean, "SonyEricsson", "T618"));
    expected.add(getModel(bean, "SonyEricsson", "T628"));
    expected.add(getModel(bean, "SonyEricsson", "T630"));
    expected.add(getModel(bean, "SonyEricsson", "T637"));
    expected.add(getModel(bean, "SonyEricsson", "T650i"));
    expected.add(getModel(bean, "SonyEricsson", "V600i"));
    expected.add(getModel(bean, "SonyEricsson", "V630i"));
    expected.add(getModel(bean, "SonyEricsson", "V800"));
    expected.add(getModel(bean, "SonyEricsson", "V800i"));
    expected.add(getModel(bean, "SonyEricsson", "W200i"));
    expected.add(getModel(bean, "SonyEricsson", "W300c"));
    expected.add(getModel(bean, "SonyEricsson", "W300i"));
    expected.add(getModel(bean, "SonyEricsson", "W550c"));
    expected.add(getModel(bean, "SonyEricsson", "W550i"));
    expected.add(getModel(bean, "SonyEricsson", "W580i"));
    expected.add(getModel(bean, "SonyEricsson", "W600i"));
    expected.add(getModel(bean, "SonyEricsson", "W610c"));
    expected.add(getModel(bean, "SonyEricsson", "W610i"));
    expected.add(getModel(bean, "SonyEricsson", "W660i"));
    expected.add(getModel(bean, "SonyEricsson", "W700c"));
    expected.add(getModel(bean, "SonyEricsson", "W700i"));
    expected.add(getModel(bean, "SonyEricsson", "W710a"));
    expected.add(getModel(bean, "SonyEricsson", "W710c"));
    expected.add(getModel(bean, "SonyEricsson", "W710i"));
    expected.add(getModel(bean, "SonyEricsson", "W810a"));
    expected.add(getModel(bean, "SonyEricsson", "W810c"));
    expected.add(getModel(bean, "SonyEricsson", "W810i"));
    expected.add(getModel(bean, "SonyEricsson", "W850a"));
    expected.add(getModel(bean, "SonyEricsson", "W850c"));
    expected.add(getModel(bean, "SonyEricsson", "W850i"));
    expected.add(getModel(bean, "SonyEricsson", "W880c"));
    expected.add(getModel(bean, "SonyEricsson", "W880i"));
    expected.add(getModel(bean, "SonyEricsson", "W900c"));
    expected.add(getModel(bean, "SonyEricsson", "W900i"));
    expected.add(getModel(bean, "SonyEricsson", "W910i"));
    expected.add(getModel(bean, "SonyEricsson", "W950c"));
    expected.add(getModel(bean, "SonyEricsson", "W950i"));
    expected.add(getModel(bean, "SonyEricsson", "Z1010"));
    expected.add(getModel(bean, "SonyEricsson", "Z310c"));
    expected.add(getModel(bean, "SonyEricsson", "Z310i"));
    expected.add(getModel(bean, "SonyEricsson", "Z500a"));
    expected.add(getModel(bean, "SonyEricsson", "Z500c"));
    expected.add(getModel(bean, "SonyEricsson", "Z500i"));
    expected.add(getModel(bean, "SonyEricsson", "Z520a"));
    expected.add(getModel(bean, "SonyEricsson", "Z520c"));
    expected.add(getModel(bean, "SonyEricsson", "Z520i"));
    expected.add(getModel(bean, "SonyEricsson", "Z525a"));
    expected.add(getModel(bean, "SonyEricsson", "Z530c"));
    expected.add(getModel(bean, "SonyEricsson", "Z530i"));
    expected.add(getModel(bean, "SonyEricsson", "Z550a"));
    expected.add(getModel(bean, "SonyEricsson", "Z550c"));
    expected.add(getModel(bean, "SonyEricsson", "Z550i"));
    expected.add(getModel(bean, "SonyEricsson", "Z600"));
    expected.add(getModel(bean, "SonyEricsson", "Z608"));
    expected.add(getModel(bean, "SonyEricsson", "Z710c"));
    expected.add(getModel(bean, "SonyEricsson", "Z710i"));
    expected.add(getModel(bean, "SonyEricsson", "Z800i"));
    expected.add(getModel(bean, "Toshiba", "803"));
    expected.add(getModel(bean, "i-mate", "JAM"));
    return expected;
  }

  /**
   * Expected Java MIDP V1 Models
   * @param bean
   * @return
   * @throws DMException
   */
  private Set<Model> getMidpV2(ModelBean bean) throws DMException {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "Alcatel", "OTC550"));
    expected.add(getModel(bean, "BenQ-Siemens", "EL71"));
    expected.add(getModel(bean, "HP", "iPAQ hw6915"));
    expected.add(getModel(bean, "HTC", "9090"));
    expected.add(getModel(bean, "HTC", "SDA"));
    expected.add(getModel(bean, "LG", "A7150"));
    expected.add(getModel(bean, "LG", "C1100"));
    expected.add(getModel(bean, "LG", "C1150"));
    expected.add(getModel(bean, "LG", "C1200"));
    expected.add(getModel(bean, "LG", "C3380"));
    expected.add(getModel(bean, "LG", "CU320"));
    expected.add(getModel(bean, "LG", "F2100"));
    expected.add(getModel(bean, "LG", "KG220"));
    expected.add(getModel(bean, "LG", "KS10"));
    expected.add(getModel(bean, "LG", "KU800"));
    expected.add(getModel(bean, "LG", "T5100"));
    expected.add(getModel(bean, "LG", "U400"));
    expected.add(getModel(bean, "LG", "U8138"));
    expected.add(getModel(bean, "LG", "U8150"));
    expected.add(getModel(bean, "LG", "U8180"));
    expected.add(getModel(bean, "LG", "U8210"));
    expected.add(getModel(bean, "LG", "U8290"));
    expected.add(getModel(bean, "LG", "U890"));
    expected.add(getModel(bean, "Motorola", "A1000"));
    expected.add(getModel(bean, "Motorola", "A630"));
    expected.add(getModel(bean, "Motorola", "A668"));
    expected.add(getModel(bean, "Motorola", "A728"));
    expected.add(getModel(bean, "Motorola", "A760"));
    expected.add(getModel(bean, "Motorola", "A780"));
    expected.add(getModel(bean, "Motorola", "A845"));
    expected.add(getModel(bean, "Motorola", "C380"));
    expected.add(getModel(bean, "Motorola", "C381"));
    expected.add(getModel(bean, "Motorola", "C385"));
    expected.add(getModel(bean, "Motorola", "C650"));
    expected.add(getModel(bean, "Motorola", "C651"));
    expected.add(getModel(bean, "Motorola", "C980"));
    expected.add(getModel(bean, "Motorola", "E1000"));
    expected.add(getModel(bean, "Motorola", "E1070"));
    expected.add(getModel(bean, "Motorola", "E375"));
    expected.add(getModel(bean, "Motorola", "E398"));
    expected.add(getModel(bean, "Motorola", "E550"));
    expected.add(getModel(bean, "Motorola", "E680"));
    expected.add(getModel(bean, "Motorola", "E770v"));
    expected.add(getModel(bean, "Motorola", "L6"));
    expected.add(getModel(bean, "Motorola", "MPX200"));
    expected.add(getModel(bean, "Motorola", "MPX220"));
    expected.add(getModel(bean, "Motorola", "RAZRV6"));
    expected.add(getModel(bean, "Motorola", "SLVRL7"));
    expected.add(getModel(bean, "Motorola", "SLVRL72"));
    expected.add(getModel(bean, "Motorola", "SLVRL7e"));
    expected.add(getModel(bean, "Motorola", "SLVRL7i"));
    expected.add(getModel(bean, "Motorola", "V1050"));
    expected.add(getModel(bean, "Motorola", "V1075"));
    expected.add(getModel(bean, "Motorola", "V180"));
    expected.add(getModel(bean, "Motorola", "V191"));
    expected.add(getModel(bean, "Motorola", "V220"));
    expected.add(getModel(bean, "Motorola", "V226"));
    expected.add(getModel(bean, "Motorola", "V280"));
    expected.add(getModel(bean, "Motorola", "V3"));
    expected.add(getModel(bean, "Motorola", "V300"));
    expected.add(getModel(bean, "Motorola", "V303"));
    expected.add(getModel(bean, "Motorola", "V330"));
    expected.add(getModel(bean, "Motorola", "V360"));
    expected.add(getModel(bean, "Motorola", "V360v"));
    expected.add(getModel(bean, "Motorola", "V361"));
    expected.add(getModel(bean, "Motorola", "V365"));
    expected.add(getModel(bean, "Motorola", "V3c"));
    expected.add(getModel(bean, "Motorola", "V3e"));
    expected.add(getModel(bean, "Motorola", "V3i"));
    expected.add(getModel(bean, "Motorola", "V3m"));
    expected.add(getModel(bean, "Motorola", "V3t"));
    expected.add(getModel(bean, "Motorola", "V3x"));
    expected.add(getModel(bean, "Motorola", "V3xx"));
    expected.add(getModel(bean, "Motorola", "V400"));
    expected.add(getModel(bean, "Motorola", "V400p"));
    expected.add(getModel(bean, "Motorola", "V500"));
    expected.add(getModel(bean, "Motorola", "V505"));
    expected.add(getModel(bean, "Motorola", "V525"));
    expected.add(getModel(bean, "Motorola", "V535"));
    expected.add(getModel(bean, "Motorola", "V547"));
    expected.add(getModel(bean, "Motorola", "V550"));
    expected.add(getModel(bean, "Motorola", "V557"));
    expected.add(getModel(bean, "Motorola", "V557p"));
    expected.add(getModel(bean, "Motorola", "V600"));
    expected.add(getModel(bean, "Motorola", "V600i"));
    expected.add(getModel(bean, "Motorola", "V620"));
    expected.add(getModel(bean, "Motorola", "V690"));
    expected.add(getModel(bean, "Motorola", "V6PEBL"));
    expected.add(getModel(bean, "Motorola", "V6V"));
    expected.add(getModel(bean, "Motorola", "V8"));
    expected.add(getModel(bean, "Motorola", "V8 iTunes"));
    expected.add(getModel(bean, "Motorola", "V80"));
    expected.add(getModel(bean, "Motorola", "V872"));
    expected.add(getModel(bean, "Motorola", "V878"));
    expected.add(getModel(bean, "Motorola", "V8v"));
    expected.add(getModel(bean, "Motorola", "V975"));
    expected.add(getModel(bean, "Motorola", "V980"));
    expected.add(getModel(bean, "NOKIA", "2610"));
    expected.add(getModel(bean, "NOKIA", "2626"));
    expected.add(getModel(bean, "NOKIA", "2630"));
    expected.add(getModel(bean, "NOKIA", "3109c"));
    expected.add(getModel(bean, "NOKIA", "3220"));
    expected.add(getModel(bean, "NOKIA", "3230"));
    expected.add(getModel(bean, "NOKIA", "3250"));
    expected.add(getModel(bean, "NOKIA", "5070"));
    expected.add(getModel(bean, "NOKIA", "5140"));
    expected.add(getModel(bean, "NOKIA", "5140i"));
    expected.add(getModel(bean, "NOKIA", "5200"));
    expected.add(getModel(bean, "NOKIA", "5300"));
    expected.add(getModel(bean, "NOKIA", "5500"));
    expected.add(getModel(bean, "NOKIA", "5500d"));
    expected.add(getModel(bean, "NOKIA", "5610"));
    expected.add(getModel(bean, "NOKIA", "5700"));
    expected.add(getModel(bean, "NOKIA", "6020"));
    expected.add(getModel(bean, "NOKIA", "6020b"));
    expected.add(getModel(bean, "NOKIA", "6021"));
    expected.add(getModel(bean, "NOKIA", "6030"));
    expected.add(getModel(bean, "NOKIA", "6060"));
    expected.add(getModel(bean, "NOKIA", "6070"));
    expected.add(getModel(bean, "NOKIA", "6080"));
    expected.add(getModel(bean, "NOKIA", "6085"));
    expected.add(getModel(bean, "NOKIA", "6086"));
    expected.add(getModel(bean, "NOKIA", "6101"));
    expected.add(getModel(bean, "NOKIA", "6102"));
    expected.add(getModel(bean, "NOKIA", "6102i"));
    expected.add(getModel(bean, "NOKIA", "6103"));
    expected.add(getModel(bean, "NOKIA", "6111"));
    expected.add(getModel(bean, "NOKIA", "6120c"));
    expected.add(getModel(bean, "NOKIA", "6121c"));
    expected.add(getModel(bean, "NOKIA", "6125"));
    expected.add(getModel(bean, "NOKIA", "6126"));
    expected.add(getModel(bean, "NOKIA", "6133"));
    expected.add(getModel(bean, "NOKIA", "6136"));
    expected.add(getModel(bean, "NOKIA", "6151"));
    expected.add(getModel(bean, "NOKIA", "6165"));
    expected.add(getModel(bean, "NOKIA", "6170"));
    expected.add(getModel(bean, "NOKIA", "6170b"));
    expected.add(getModel(bean, "NOKIA", "6230"));
    expected.add(getModel(bean, "NOKIA", "6230i"));
    expected.add(getModel(bean, "NOKIA", "6233"));
    expected.add(getModel(bean, "NOKIA", "6234"));
    expected.add(getModel(bean, "NOKIA", "6260"));
    expected.add(getModel(bean, "NOKIA", "6265"));
    expected.add(getModel(bean, "NOKIA", "6265i"));
    expected.add(getModel(bean, "NOKIA", "6267"));
    expected.add(getModel(bean, "NOKIA", "6270"));
    expected.add(getModel(bean, "NOKIA", "6275"));
    expected.add(getModel(bean, "NOKIA", "6275i"));
    expected.add(getModel(bean, "NOKIA", "6280"));
    expected.add(getModel(bean, "NOKIA", "6282"));
    expected.add(getModel(bean, "NOKIA", "6288"));
    expected.add(getModel(bean, "NOKIA", "6290"));
    expected.add(getModel(bean, "NOKIA", "6300"));
    expected.add(getModel(bean, "NOKIA", "6301"));
    expected.add(getModel(bean, "NOKIA", "6500"));
    expected.add(getModel(bean, "NOKIA", "6500c"));
    expected.add(getModel(bean, "NOKIA", "6500s"));
    expected.add(getModel(bean, "NOKIA", "6555"));
    expected.add(getModel(bean, "NOKIA", "6600"));
    expected.add(getModel(bean, "NOKIA", "6620"));
    expected.add(getModel(bean, "NOKIA", "6630"));
    expected.add(getModel(bean, "NOKIA", "6670"));
    expected.add(getModel(bean, "NOKIA", "6670b"));
    expected.add(getModel(bean, "NOKIA", "6680"));
    expected.add(getModel(bean, "NOKIA", "6681"));
    expected.add(getModel(bean, "NOKIA", "6682"));
    expected.add(getModel(bean, "NOKIA", "6822"));
    expected.add(getModel(bean, "NOKIA", "6822a"));
    expected.add(getModel(bean, "NOKIA", "6822b"));
    expected.add(getModel(bean, "NOKIA", "7260"));
    expected.add(getModel(bean, "NOKIA", "7270"));
    expected.add(getModel(bean, "NOKIA", "7360"));
    expected.add(getModel(bean, "NOKIA", "7370"));
    expected.add(getModel(bean, "NOKIA", "7373"));
    expected.add(getModel(bean, "NOKIA", "7380"));
    expected.add(getModel(bean, "NOKIA", "7390"));
    expected.add(getModel(bean, "NOKIA", "7500"));
    expected.add(getModel(bean, "NOKIA", "7610"));
    expected.add(getModel(bean, "NOKIA", "7710"));
    expected.add(getModel(bean, "NOKIA", "8801"));
    expected.add(getModel(bean, "NOKIA", "9300"));
    expected.add(getModel(bean, "NOKIA", "9500"));
    expected.add(getModel(bean, "NOKIA", "E50"));
    expected.add(getModel(bean, "NOKIA", "E60"));
    expected.add(getModel(bean, "NOKIA", "E61"));
    expected.add(getModel(bean, "NOKIA", "E61i"));
    expected.add(getModel(bean, "NOKIA", "E62"));
    expected.add(getModel(bean, "NOKIA", "E65"));
    expected.add(getModel(bean, "NOKIA", "E70"));
    expected.add(getModel(bean, "NOKIA", "N-Gage QD"));
    expected.add(getModel(bean, "NOKIA", "N70"));
    expected.add(getModel(bean, "NOKIA", "N71"));
    expected.add(getModel(bean, "NOKIA", "N72"));
    expected.add(getModel(bean, "NOKIA", "N73"));
    expected.add(getModel(bean, "NOKIA", "N75"));
    expected.add(getModel(bean, "NOKIA", "N76"));
    expected.add(getModel(bean, "NOKIA", "N77"));
    expected.add(getModel(bean, "NOKIA", "N80"));
    expected.add(getModel(bean, "NOKIA", "N81"));
    expected.add(getModel(bean, "NOKIA", "N90"));
    expected.add(getModel(bean, "NOKIA", "N91"));
    expected.add(getModel(bean, "NOKIA", "N92"));
    expected.add(getModel(bean, "NOKIA", "N93"));
    expected.add(getModel(bean, "NOKIA", "N93i"));
    expected.add(getModel(bean, "NOKIA", "N95"));
    expected.add(getModel(bean, "Panasonic", "X500"));
    expected.add(getModel(bean, "Panasonic", "X800"));
    expected.add(getModel(bean, "Qtek", "9000"));
    expected.add(getModel(bean, "Qtek", "9090"));
    expected.add(getModel(bean, "Qtek", "9100"));
    expected.add(getModel(bean, "RIM", "BlackBerry 8700c"));
    expected.add(getModel(bean, "RIM", "BlackBerry 8700r"));
    expected.add(getModel(bean, "Sagem", "MYX52"));
    expected.add(getModel(bean, "Sagem", "myV-55"));
    expected.add(getModel(bean, "Sagem", "myX-6-2"));
    expected.add(getModel(bean, "Sagem", "myX-7"));
    expected.add(getModel(bean, "Sagem", "myX-8"));
    expected.add(getModel(bean, "Sagem", "myZ-5"));
    expected.add(getModel(bean, "Samsung", "C200"));
    expected.add(getModel(bean, "Samsung", "C200C"));
    expected.add(getModel(bean, "Samsung", "C200N"));
    expected.add(getModel(bean, "Samsung", "C200S"));
    expected.add(getModel(bean, "Samsung", "C300"));
    expected.add(getModel(bean, "Samsung", "D500"));
    expected.add(getModel(bean, "Samsung", "D500C"));
    expected.add(getModel(bean, "Samsung", "D500E"));
    expected.add(getModel(bean, "Samsung", "D520"));
    expected.add(getModel(bean, "Samsung", "D600"));
    expected.add(getModel(bean, "Samsung", "D608"));
    expected.add(getModel(bean, "Samsung", "D800"));
    expected.add(getModel(bean, "Samsung", "D900"));
    expected.add(getModel(bean, "Samsung", "E105"));
    expected.add(getModel(bean, "Samsung", "E250"));
    expected.add(getModel(bean, "Samsung", "E300"));
    expected.add(getModel(bean, "Samsung", "E310"));
    expected.add(getModel(bean, "Samsung", "E350"));
    expected.add(getModel(bean, "Samsung", "E360"));
    expected.add(getModel(bean, "Samsung", "E370"));
    expected.add(getModel(bean, "Samsung", "E530"));
    expected.add(getModel(bean, "Samsung", "E600"));
    expected.add(getModel(bean, "Samsung", "E600C"));
    expected.add(getModel(bean, "Samsung", "E620"));
    expected.add(getModel(bean, "Samsung", "E710"));
    expected.add(getModel(bean, "Samsung", "E720"));
    expected.add(getModel(bean, "Samsung", "E720C"));
    expected.add(getModel(bean, "Samsung", "E760"));
    expected.add(getModel(bean, "Samsung", "E810"));
    expected.add(getModel(bean, "Samsung", "E810C"));
    expected.add(getModel(bean, "Samsung", "E900"));
    expected.add(getModel(bean, "Samsung", "I310"));
    expected.add(getModel(bean, "Samsung", "MM-A700"));
    expected.add(getModel(bean, "Samsung", "P408"));
    expected.add(getModel(bean, "Samsung", "P510"));
    expected.add(getModel(bean, "Samsung", "P518"));
    expected.add(getModel(bean, "Samsung", "P730"));
    expected.add(getModel(bean, "Samsung", "P730C"));
    expected.add(getModel(bean, "Samsung", "P735"));
    expected.add(getModel(bean, "Samsung", "P738"));
    expected.add(getModel(bean, "Samsung", "SGH-T519"));
    expected.add(getModel(bean, "Samsung", "T619"));
    expected.add(getModel(bean, "Samsung", "T629"));
    expected.add(getModel(bean, "Samsung", "U600"));
    expected.add(getModel(bean, "Samsung", "U700"));
    expected.add(getModel(bean, "Samsung", "X105"));
    expected.add(getModel(bean, "Samsung", "X120"));
    expected.add(getModel(bean, "Samsung", "X430"));
    expected.add(getModel(bean, "Samsung", "X450"));
    expected.add(getModel(bean, "Samsung", "X458"));
    expected.add(getModel(bean, "Samsung", "X480C"));
    expected.add(getModel(bean, "Samsung", "X640"));
    expected.add(getModel(bean, "Samsung", "X640C"));
    expected.add(getModel(bean, "Samsung", "X640S"));
    expected.add(getModel(bean, "Samsung", "X650"));
    expected.add(getModel(bean, "Samsung", "X660"));
    expected.add(getModel(bean, "Samsung", "X660V"));
    expected.add(getModel(bean, "Samsung", "Z100"));
    expected.add(getModel(bean, "Samsung", "Z105"));
    expected.add(getModel(bean, "Samsung", "Z105U"));
    expected.add(getModel(bean, "Samsung", "Z107"));
    expected.add(getModel(bean, "Samsung", "Z130"));
    expected.add(getModel(bean, "Samsung", "Z140"));
    expected.add(getModel(bean, "Samsung", "Z140M"));
    expected.add(getModel(bean, "Samsung", "Z140N"));
    expected.add(getModel(bean, "Samsung", "Z140V"));
    expected.add(getModel(bean, "Samsung", "Z150"));
    expected.add(getModel(bean, "Samsung", "Z400"));
    expected.add(getModel(bean, "Samsung", "Z400V"));
    expected.add(getModel(bean, "Samsung", "Z500"));
    expected.add(getModel(bean, "Samsung", "Z500V"));
    expected.add(getModel(bean, "Samsung", "Z510"));
    expected.add(getModel(bean, "Samsung", "Z520V"));
    expected.add(getModel(bean, "Samsung", "Z540"));
    expected.add(getModel(bean, "Samsung", "Z560"));
    expected.add(getModel(bean, "Samsung", "Z720"));
    expected.add(getModel(bean, "Samsung", "ZV10"));
    expected.add(getModel(bean, "Samsung", "ZV40"));
    expected.add(getModel(bean, "Sharp", "770SH"));
    expected.add(getModel(bean, "Sharp", "802SH"));
    expected.add(getModel(bean, "Sharp", "TM-200"));
    expected.add(getModel(bean, "Siemens", "C62"));
    expected.add(getModel(bean, "Siemens", "C65"));
    expected.add(getModel(bean, "Siemens", "C75"));
    expected.add(getModel(bean, "Siemens", "CFX65"));
    expected.add(getModel(bean, "Siemens", "CT65"));
    expected.add(getModel(bean, "Siemens", "CV65"));
    expected.add(getModel(bean, "Siemens", "CX65"));
    expected.add(getModel(bean, "Siemens", "CX70"));
    expected.add(getModel(bean, "Siemens", "CX75"));
    expected.add(getModel(bean, "Siemens", "CXT65"));
    expected.add(getModel(bean, "Siemens", "CXT70"));
    expected.add(getModel(bean, "Siemens", "M65"));
    expected.add(getModel(bean, "Siemens", "M65v"));
    expected.add(getModel(bean, "Siemens", "M75"));
    expected.add(getModel(bean, "Siemens", "ME75"));
    expected.add(getModel(bean, "Siemens", "S65"));
    expected.add(getModel(bean, "Siemens", "S65V"));
    expected.add(getModel(bean, "Siemens", "SK65"));
    expected.add(getModel(bean, "Siemens", "SL65"));
    expected.add(getModel(bean, "Siemens", "SL65Escada"));
    expected.add(getModel(bean, "Siemens", "ST60"));
    expected.add(getModel(bean, "SonyEricsson", "D750i"));
    expected.add(getModel(bean, "SonyEricsson", "F500i"));
    expected.add(getModel(bean, "SonyEricsson", "J300a"));
    expected.add(getModel(bean, "SonyEricsson", "J300c"));
    expected.add(getModel(bean, "SonyEricsson", "J300i"));
    expected.add(getModel(bean, "SonyEricsson", "K310a"));
    expected.add(getModel(bean, "SonyEricsson", "K310c"));
    expected.add(getModel(bean, "SonyEricsson", "K500c"));
    expected.add(getModel(bean, "SonyEricsson", "K500i"));
    expected.add(getModel(bean, "SonyEricsson", "K506c"));
    expected.add(getModel(bean, "SonyEricsson", "K508c"));
    expected.add(getModel(bean, "SonyEricsson", "K510c"));
    expected.add(getModel(bean, "SonyEricsson", "K510i"));
    expected.add(getModel(bean, "SonyEricsson", "K600i"));
    expected.add(getModel(bean, "SonyEricsson", "K608i"));
    expected.add(getModel(bean, "SonyEricsson", "K610c"));
    expected.add(getModel(bean, "SonyEricsson", "K610i"));
    expected.add(getModel(bean, "SonyEricsson", "K700c"));
    expected.add(getModel(bean, "SonyEricsson", "K700i"));
    expected.add(getModel(bean, "SonyEricsson", "K750a"));
    expected.add(getModel(bean, "SonyEricsson", "K750c"));
    expected.add(getModel(bean, "SonyEricsson", "K750i"));
    expected.add(getModel(bean, "SonyEricsson", "K790a"));
    expected.add(getModel(bean, "SonyEricsson", "K790c"));
    expected.add(getModel(bean, "SonyEricsson", "K790i"));
    expected.add(getModel(bean, "SonyEricsson", "K800c"));
    expected.add(getModel(bean, "SonyEricsson", "K800i"));
    expected.add(getModel(bean, "SonyEricsson", "M600c"));
    expected.add(getModel(bean, "SonyEricsson", "M600i"));
    expected.add(getModel(bean, "SonyEricsson", "P900"));
    expected.add(getModel(bean, "SonyEricsson", "P908"));
    expected.add(getModel(bean, "SonyEricsson", "P910"));
    expected.add(getModel(bean, "SonyEricsson", "P910a"));
    expected.add(getModel(bean, "SonyEricsson", "P910c"));
    expected.add(getModel(bean, "SonyEricsson", "P910i"));
    expected.add(getModel(bean, "SonyEricsson", "P990c"));
    expected.add(getModel(bean, "SonyEricsson", "P990i"));
    expected.add(getModel(bean, "SonyEricsson", "S700a"));
    expected.add(getModel(bean, "SonyEricsson", "S700c"));
    expected.add(getModel(bean, "SonyEricsson", "S700i"));
    expected.add(getModel(bean, "SonyEricsson", "T650i"));
    expected.add(getModel(bean, "SonyEricsson", "V600i"));
    expected.add(getModel(bean, "SonyEricsson", "V630i"));
    expected.add(getModel(bean, "SonyEricsson", "V800"));
    expected.add(getModel(bean, "SonyEricsson", "V800i"));
    expected.add(getModel(bean, "SonyEricsson", "W300c"));
    expected.add(getModel(bean, "SonyEricsson", "W300i"));
    expected.add(getModel(bean, "SonyEricsson", "W550c"));
    expected.add(getModel(bean, "SonyEricsson", "W550i"));
    expected.add(getModel(bean, "SonyEricsson", "W600i"));
    expected.add(getModel(bean, "SonyEricsson", "W610c"));
    expected.add(getModel(bean, "SonyEricsson", "W610i"));
    expected.add(getModel(bean, "SonyEricsson", "W660i"));
    expected.add(getModel(bean, "SonyEricsson", "W700c"));
    expected.add(getModel(bean, "SonyEricsson", "W700i"));
    expected.add(getModel(bean, "SonyEricsson", "W710a"));
    expected.add(getModel(bean, "SonyEricsson", "W710c"));
    expected.add(getModel(bean, "SonyEricsson", "W710i"));
    expected.add(getModel(bean, "SonyEricsson", "W810a"));
    expected.add(getModel(bean, "SonyEricsson", "W810c"));
    expected.add(getModel(bean, "SonyEricsson", "W810i"));
    expected.add(getModel(bean, "SonyEricsson", "W850a"));
    expected.add(getModel(bean, "SonyEricsson", "W850c"));
    expected.add(getModel(bean, "SonyEricsson", "W850i"));
    expected.add(getModel(bean, "SonyEricsson", "W880c"));
    expected.add(getModel(bean, "SonyEricsson", "W880i"));
    expected.add(getModel(bean, "SonyEricsson", "W900c"));
    expected.add(getModel(bean, "SonyEricsson", "W900i"));
    expected.add(getModel(bean, "SonyEricsson", "W950c"));
    expected.add(getModel(bean, "SonyEricsson", "W950i"));
    expected.add(getModel(bean, "SonyEricsson", "Z1010"));
    expected.add(getModel(bean, "SonyEricsson", "Z500a"));
    expected.add(getModel(bean, "SonyEricsson", "Z500c"));
    expected.add(getModel(bean, "SonyEricsson", "Z500i"));
    expected.add(getModel(bean, "SonyEricsson", "Z520a"));
    expected.add(getModel(bean, "SonyEricsson", "Z520c"));
    expected.add(getModel(bean, "SonyEricsson", "Z520i"));
    expected.add(getModel(bean, "SonyEricsson", "Z530c"));
    expected.add(getModel(bean, "SonyEricsson", "Z530i"));
    expected.add(getModel(bean, "SonyEricsson", "Z550a"));
    expected.add(getModel(bean, "SonyEricsson", "Z550c"));
    expected.add(getModel(bean, "SonyEricsson", "Z550i"));
    expected.add(getModel(bean, "SonyEricsson", "Z610c"));
    expected.add(getModel(bean, "SonyEricsson", "Z610i"));
    expected.add(getModel(bean, "SonyEricsson", "Z710c"));
    expected.add(getModel(bean, "SonyEricsson", "Z710i"));
    expected.add(getModel(bean, "SonyEricsson", "Z800i"));
    expected.add(getModel(bean, "Toshiba", "803"));
    expected.add(getModel(bean, "i-mate", "JAM"));
    
    return expected;
  }
  

  /**
   * Symbian S60 3rd 所有终端
   * @param bean
   * @return
   * @throws Exception
   */
  private Set<Model> getS60V3(ModelBean bean) throws Exception {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "LG", "KS10"));
    expected.add(getModel(bean, "LG", "KS10 JOY"));
    expected.add(getModel(bean, "Motorola", "RIZR Z10"));
    expected.add(getModel(bean, "NOKIA", "N82"));
    expected.add(getModel(bean, "NOKIA", "N95-3"));
    expected.add(getModel(bean, "NOKIA", "E51"));
    expected.add(getModel(bean, "NOKIA", "N81"));
    expected.add(getModel(bean, "NOKIA", "N81-1"));
    expected.add(getModel(bean, "NOKIA", "N95_8GB-1"));
    expected.add(getModel(bean, "NOKIA", "6121c"));
    expected.add(getModel(bean, "NOKIA", "6120c"));
    expected.add(getModel(bean, "NOKIA", "5700"));
    expected.add(getModel(bean, "NOKIA", "N77"));
    expected.add(getModel(bean, "NOKIA", "E61i"));
    expected.add(getModel(bean, "NOKIA", "E65"));
    expected.add(getModel(bean, "NOKIA", "6110"));
    expected.add(getModel(bean, "NOKIA", "E90"));
    expected.add(getModel(bean, "NOKIA", "N76"));
    expected.add(getModel(bean, "NOKIA", "N93i"));
    expected.add(getModel(bean, "NOKIA", "6290"));
    expected.add(getModel(bean, "NOKIA", "N95"));
    expected.add(getModel(bean, "NOKIA", "N75"));
    expected.add(getModel(bean, "NOKIA", "N91-1"));
    expected.add(getModel(bean, "NOKIA", "E62"));
    expected.add(getModel(bean, "NOKIA", "E50"));
    expected.add(getModel(bean, "NOKIA", "5500"));
    expected.add(getModel(bean, "NOKIA", "N93"));
    expected.add(getModel(bean, "NOKIA", "N73"));
    expected.add(getModel(bean, "NOKIA", "N71"));
    expected.add(getModel(bean, "NOKIA", "N80"));
    expected.add(getModel(bean, "NOKIA", "N92"));
    expected.add(getModel(bean, "NOKIA", "E70"));
    expected.add(getModel(bean, "NOKIA", "E60"));
    expected.add(getModel(bean, "NOKIA", "E61"));
    expected.add(getModel(bean, "NOKIA", "3250"));
    expected.add(getModel(bean, "NOKIA", "N91"));
    expected.add(getModel(bean, "Samsung", "i520"));
    expected.add(getModel(bean, "Samsung", "i550v"));
    expected.add(getModel(bean, "Samsung", "i560v"));
    return expected;
  }

  /**
   * Symbian S60 3rd 所有终端
   * @param bean
   * @return
   * @throws Exception
   */
  private Set<Model> getS60V2(ModelBean bean) throws Exception {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "NOKIA", "3230"));
    expected.add(getModel(bean, "NOKIA", "6670"));
    expected.add(getModel(bean, "NOKIA", "6260"));
    expected.add(getModel(bean, "NOKIA", "7610"));
    expected.add(getModel(bean, "NOKIA", "6620"));
    expected.add(getModel(bean, "NOKIA", "6681"));
    expected.add(getModel(bean, "NOKIA", "6680"));
    expected.add(getModel(bean, "NOKIA", "6682"));
    expected.add(getModel(bean, "NOKIA", "6630"));
    expected.add(getModel(bean, "NOKIA", "N72"));
    expected.add(getModel(bean, "NOKIA", "N70"));
    expected.add(getModel(bean, "NOKIA", "N90"));
    expected.add(getModel(bean, "NOKIA", "6600"));
    expected.add(getModel(bean, "Panasonic", "X700"));
    expected.add(getModel(bean, "Panasonic", "X701"));
    expected.add(getModel(bean, "Panasonic", "X800"));
    return expected;
  }

  /**
   * Windows Mobile 2005 PocketPC
   * @param bean
   * @return
   */
  private Set<Model> getWindowsMobile_5_0_PPC(ModelBean bean) throws Exception {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "Asus", "A632 MyPal"));
    expected.add(getModel(bean, "Asus", "A636 MyPal"));
    expected.add(getModel(bean, "Asus", "A639 MyPal"));
    expected.add(getModel(bean, "Asus", "P305"));
    expected.add(getModel(bean, "Asus", "P525"));
    expected.add(getModel(bean, "Asus", "P535"));
    expected.add(getModel(bean, "Asus", "P735"));
    expected.add(getModel(bean, "BenQ-Siemens", "P51"));
    expected.add(getModel(bean, "Dopod", "818 Pro"));
    expected.add(getModel(bean, "Dopod", "838"));
    expected.add(getModel(bean, "Dopod", "838 Pro"));
    expected.add(getModel(bean, "Dopod", "900"));
    expected.add(getModel(bean, "Dopod", "C800"));
    expected.add(getModel(bean, "Dopod", "C858"));
    expected.add(getModel(bean, "Dopod", "CHT 9000"));
    expected.add(getModel(bean, "Dopod", "CHT 9100"));
    expected.add(getModel(bean, "Dopod", "D600"));
    expected.add(getModel(bean, "Dopod", "D810"));
    expected.add(getModel(bean, "Dopod", "M700"));
    expected.add(getModel(bean, "Dopod", "P800W"));
    expected.add(getModel(bean, "HTC", "TyTN"));
    expected.add(getModel(bean, "i-mate", "JAMA"));
    expected.add(getModel(bean, "i-mate", "JAMin"));
    expected.add(getModel(bean, "i-mate", "JAQ"));
    expected.add(getModel(bean, "i-mate", "JAQ3"));
    expected.add(getModel(bean, "i-mate", "JASJAM"));
    expected.add(getModel(bean, "i-mate", "JASJAR"));
    expected.add(getModel(bean, "i-mate", "K-JAM"));
    expected.add(getModel(bean, "i-mate", "PDAL"));
    expected.add(getModel(bean, "Dopod", "U1000"));
    expected.add(getModel(bean, "ETEN", "G500"));
    expected.add(getModel(bean, "ETEN", "G500+"));
    expected.add(getModel(bean, "ETEN", "M550"));
    expected.add(getModel(bean, "ETEN", "M600"));
    expected.add(getModel(bean, "ETEN", "M600+"));
    expected.add(getModel(bean, "ETEN", "M700"));
    expected.add(getModel(bean, "ETEN", "X500"));
    expected.add(getModel(bean, "HP", "iPAQ hw6900"));
    expected.add(getModel(bean, "HP", "iPAQ hw6910"));
    expected.add(getModel(bean, "HP", "iPAQ hw6915"));
    expected.add(getModel(bean, "HP", "iPAQ hx2190"));
    expected.add(getModel(bean, "HP", "iPAQ hx2490"));
    expected.add(getModel(bean, "HP", "iPAQ rw6815"));
    expected.add(getModel(bean, "HP", "iPAQ rw6828"));
    expected.add(getModel(bean, "HP", "iPAQ rx5700"));
    expected.add(getModel(bean, "HP", "iPAQ rx5900"));
    expected.add(getModel(bean, "HTC", "Advantage X7500"));
    expected.add(getModel(bean, "HTC", "P3300"));
    expected.add(getModel(bean, "HTC", "P3350"));
    expected.add(getModel(bean, "HTC", "P3400"));
    expected.add(getModel(bean, "HTC", "P3600"));
    expected.add(getModel(bean, "HTC", "P4350"));
    expected.add(getModel(bean, "HTC", "P6300"));
    expected.add(getModel(bean, "O2", "Xda Atom"));
    expected.add(getModel(bean, "O2", "Xda Atom Exec"));
    expected.add(getModel(bean, "O2", "Xda Atom Life"));
    expected.add(getModel(bean, "O2", "Xda Zinc"));
    expected.add(getModel(bean, "O2", "Xda Orbit"));
    expected.add(getModel(bean, "O2", "Xda Terra"));
    expected.add(getModel(bean, "O2", "Xda Argon"));
    expected.add(getModel(bean, "O2", "Xda Flame"));
    expected.add(getModel(bean, "O2", "Xda Stealth"));
    expected.add(getModel(bean, "O2", "Xda Trion"));
    expected.add(getModel(bean, "O2", "Xda Neo"));
    expected.add(getModel(bean, "O2", "Xda Exec"));
    expected.add(getModel(bean, "O2", "Xda mini S"));
    expected.add(getModel(bean, "Qtek", "9000"));
    expected.add(getModel(bean, "Qtek", "9100"));
    expected.add(getModel(bean, "Qtek", "9600"));
    expected.add(getModel(bean, "Qtek", "S200"));
    expected.add(getModel(bean, "Samsung", "I710"));
    expected.add(getModel(bean, "Samsung", "I718"));
    expected.add(getModel(bean, "Toshiba", "G500"));
    return expected;
  }

  /**
   * Windows Mobile 2005 SmartPhone
   * @param bean
   * @return
   */
  private Set<Model> getWindowsMobile_5_0_SmartPhone(ModelBean bean) throws Exception {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "Dopod", "310"));
    expected.add(getModel(bean, "Dopod", "566"));
    expected.add(getModel(bean, "Dopod", "577W"));
    expected.add(getModel(bean, "Dopod", "586"));
    expected.add(getModel(bean, "Dopod", "586W"));
    expected.add(getModel(bean, "Dopod", "595"));
    expected.add(getModel(bean, "Dopod", "C720W"));
    expected.add(getModel(bean, "Dopod", "s300"));
    expected.add(getModel(bean, "i-mate", "Smartflip"));
    expected.add(getModel(bean, "i-mate", "SP4m"));
    expected.add(getModel(bean, "i-mate", "SP5"));
    expected.add(getModel(bean, "i-mate", "SP5m"));
    expected.add(getModel(bean, "i-mate", "SPJAS"));
    expected.add(getModel(bean, "i-mate", "SPL"));
    expected.add(getModel(bean, "HTC", "MTeoR"));
    expected.add(getModel(bean, "O2", "Cosmo"));
    expected.add(getModel(bean, "O2", "Xda phone"));
    expected.add(getModel(bean, "O2", "Xda Graphite"));
    expected.add(getModel(bean, "O2", "Xda Orion"));
    expected.add(getModel(bean, "Qtek", "8300"));
    expected.add(getModel(bean, "Qtek", "8310"));
    expected.add(getModel(bean, "Qtek", "8500"));
    expected.add(getModel(bean, "Qtek", "8600"));
    expected.add(getModel(bean, "Samsung", "I310"));
    expected.add(getModel(bean, "Samsung", "I320"));
    expected.add(getModel(bean, "Samsung", "I320N"));
    expected.add(getModel(bean, "Samsung", "SCH-i600"));
    expected.add(getModel(bean, "Samsung", "i600"));
    expected.add(getModel(bean, "Samsung", "i607"));
    return expected;
  }

  private Set<Model> getWindowsMobile_6_0_Professional(ModelBean bean) throws Exception {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "Asus", "P526"));
    expected.add(getModel(bean, "Asus", "P527"));
    expected.add(getModel(bean, "Asus", "P750"));
    expected.add(getModel(bean, "HTC", "Touch Cruise"));
    expected.add(getModel(bean, "HTC", "Touch Dual"));
    expected.add(getModel(bean, "HTC", "TyTN II"));
    expected.add(getModel(bean, "i-mate", "JAMA 101"));
    expected.add(getModel(bean, "i-mate", "JAMA 201"));
    expected.add(getModel(bean, "i-mate", "JAQ4"));
    expected.add(getModel(bean, "i-mate", "Ultimate 5150"));
    expected.add(getModel(bean, "i-mate", "Ultimate 6150"));
    expected.add(getModel(bean, "i-mate", "Ultimate 7150"));
    expected.add(getModel(bean, "i-mate", "Ultimate 8150"));
    expected.add(getModel(bean, "i-mate", "Ultimate 8502"));
    expected.add(getModel(bean, "i-mate", "Ultimate 9150"));
    expected.add(getModel(bean, "i-mate", "Ultimate 9502"));
    expected.add(getModel(bean, "ETEN", "M800"));
    expected.add(getModel(bean, "ETEN", "X500+"));
    expected.add(getModel(bean, "ETEN", "X600"));
    expected.add(getModel(bean, "ETEN", "X800"));
    expected.add(getModel(bean, "HTC", "P3600i"));
    expected.add(getModel(bean, "HTC", "P6500"));
    expected.add(getModel(bean, "HTC", "Touch"));
    expected.add(getModel(bean, "O2", "Xda Stellar"));
    expected.add(getModel(bean, "O2", "Xda Orbit II"));
    expected.add(getModel(bean, "O2", "Xda Comet"));
    expected.add(getModel(bean, "O2", "Xda Nova"));
    expected.add(getModel(bean, "O2", "Xda Star"));
    expected.add(getModel(bean, "Samsung", "I780"));
    return expected;
  }

  private Set<Model> getWindowsMobile_6_0_Standard(ModelBean bean) throws Exception {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "Asus", "M530w"));
    expected.add(getModel(bean, "Dopod", "C500"));
    expected.add(getModel(bean, "Motorola", "Q8"));
    expected.add(getModel(bean, "Motorola", "Q9"));
    expected.add(getModel(bean, "Motorola", "Q9h"));
    expected.add(getModel(bean, "Motorola", "Q9q"));
    expected.add(getModel(bean, "HP", "iPAQ-500"));
    expected.add(getModel(bean, "HTC", "S630"));
    expected.add(getModel(bean, "HTC", "S710"));
    expected.add(getModel(bean, "HTC", "S730"));
    expected.add(getModel(bean, "Samsung", "i620"));
    expected.add(getModel(bean, "Samsung", "i620v"));
    expected.add(getModel(bean, "Samsung", "i640v"));
    return expected;
  }

  /**
   * 屏幕Height x Width 为 320x240
   * @param bean
   * @return
   * @throws Exception
   */
  private Set<Model> getScreen_320_240(ModelBean bean) throws Exception {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "Asus", "P505"));
    expected.add(getModel(bean, "BenQ-Siemens", "EF81"));
    expected.add(getModel(bean, "BenQ-Siemens", "EL71"));
    expected.add(getModel(bean, "Dopod", "595"));
    expected.add(getModel(bean, "HP", "iPAQ rw6815"));
    expected.add(getModel(bean, "HTC", "3100 (Star Trek)"));
    expected.add(getModel(bean, "HTC", "8500"));
    expected.add(getModel(bean, "HTC", "9090"));
    expected.add(getModel(bean, "HTC", "Apache, PPC 6700"));
    expected.add(getModel(bean, "HTC", "Artist"));
    expected.add(getModel(bean, "HTC", "Cingular 8125"));
    expected.add(getModel(bean, "HTC", "Elf, P3450"));
    expected.add(getModel(bean, "HTC", "Gemini"));
    expected.add(getModel(bean, "HTC", "Hermes"));
    expected.add(getModel(bean, "HTC", "MDA Compact"));
    expected.add(getModel(bean, "HTC", "P4550"));
    expected.add(getModel(bean, "HTC", "Prophet"));
    expected.add(getModel(bean, "HTC", "S710"));
    expected.add(getModel(bean, "HTC", "Wizard"));
    expected.add(getModel(bean, "HTC", "XDA Trion"));
    expected.add(getModel(bean, "HTC", "v1605"));
    expected.add(getModel(bean, "LG", "KS10"));
    expected.add(getModel(bean, "LG", "KU800"));
    expected.add(getModel(bean, "LG", "U400"));
    expected.add(getModel(bean, "Motorola", "A388"));
    expected.add(getModel(bean, "Motorola", "A780"));
    expected.add(getModel(bean, "Motorola", "E1000"));
    expected.add(getModel(bean, "Motorola", "E1070"));
    expected.add(getModel(bean, "Motorola", "E680"));
    expected.add(getModel(bean, "Motorola", "RAZRV6"));
    expected.add(getModel(bean, "Motorola", "V1050"));
    expected.add(getModel(bean, "Motorola", "V1075"));
    expected.add(getModel(bean, "Motorola", "V1100"));
    expected.add(getModel(bean, "Motorola", "V600"));
    expected.add(getModel(bean, "Motorola", "V600i"));
    expected.add(getModel(bean, "Motorola", "V620"));
    expected.add(getModel(bean, "Motorola", "V690"));
    expected.add(getModel(bean, "Motorola", "V6PEBL"));
    expected.add(getModel(bean, "Motorola", "V6V"));
    expected.add(getModel(bean, "NOKIA", "5300"));
    expected.add(getModel(bean, "NOKIA", "5610"));
    expected.add(getModel(bean, "NOKIA", "5700"));
    expected.add(getModel(bean, "NOKIA", "6120c"));
    expected.add(getModel(bean, "NOKIA", "6121c"));
    expected.add(getModel(bean, "NOKIA", "6126"));
    expected.add(getModel(bean, "NOKIA", "6133"));
    expected.add(getModel(bean, "NOKIA", "6233"));
    expected.add(getModel(bean, "NOKIA", "6234"));
    expected.add(getModel(bean, "NOKIA", "6265"));
    expected.add(getModel(bean, "NOKIA", "6265i"));
    expected.add(getModel(bean, "NOKIA", "6267"));
    expected.add(getModel(bean, "NOKIA", "6270"));
    expected.add(getModel(bean, "NOKIA", "6275"));
    expected.add(getModel(bean, "NOKIA", "6275i"));
    expected.add(getModel(bean, "NOKIA", "6280"));
    expected.add(getModel(bean, "NOKIA", "6282"));
    expected.add(getModel(bean, "NOKIA", "6288"));
    expected.add(getModel(bean, "NOKIA", "6290"));
    expected.add(getModel(bean, "NOKIA", "6300"));
    expected.add(getModel(bean, "NOKIA", "6301"));
    expected.add(getModel(bean, "NOKIA", "6500"));
    expected.add(getModel(bean, "NOKIA", "6500c"));
    expected.add(getModel(bean, "NOKIA", "6500s"));
    expected.add(getModel(bean, "NOKIA", "6555"));
    expected.add(getModel(bean, "NOKIA", "7370"));
    expected.add(getModel(bean, "NOKIA", "7373"));
    expected.add(getModel(bean, "NOKIA", "7390"));
    expected.add(getModel(bean, "NOKIA", "7500"));
    expected.add(getModel(bean, "NOKIA", "E50"));
    expected.add(getModel(bean, "NOKIA", "E65"));
    expected.add(getModel(bean, "NOKIA", "N71"));
    expected.add(getModel(bean, "NOKIA", "N73"));
    expected.add(getModel(bean, "NOKIA", "N75"));
    expected.add(getModel(bean, "NOKIA", "N76"));
    expected.add(getModel(bean, "NOKIA", "N77"));
    expected.add(getModel(bean, "NOKIA", "N81"));
    expected.add(getModel(bean, "NOKIA", "N93"));
    expected.add(getModel(bean, "NOKIA", "N93i"));
    expected.add(getModel(bean, "NOKIA", "N95"));
    expected.add(getModel(bean, "O2", "Xda Atom"));
    expected.add(getModel(bean, "O2", "Xda Graphite"));
    expected.add(getModel(bean, "O2", "Xda II"));
    expected.add(getModel(bean, "Panasonic", "VS2"));
    expected.add(getModel(bean, "Panasonic", "VS6"));
    expected.add(getModel(bean, "Qtek", "9090"));
    expected.add(getModel(bean, "Qtek", "9100"));
    expected.add(getModel(bean, "Qtek", "S100"));
    expected.add(getModel(bean, "Qtek", "S200"));
    expected.add(getModel(bean, "Sagem", "myX-8"));
    expected.add(getModel(bean, "Samsung", "D600"));
    expected.add(getModel(bean, "Samsung", "D608"));
    expected.add(getModel(bean, "Samsung", "D800"));
    expected.add(getModel(bean, "Samsung", "D820"));
    expected.add(getModel(bean, "Samsung", "D830"));
    expected.add(getModel(bean, "Samsung", "D840"));
    expected.add(getModel(bean, "Samsung", "D900"));
    expected.add(getModel(bean, "Samsung", "E900"));
    expected.add(getModel(bean, "Samsung", "I300"));
    expected.add(getModel(bean, "Samsung", "U600"));
    expected.add(getModel(bean, "Samsung", "U700"));
    expected.add(getModel(bean, "Samsung", "Z110"));
    expected.add(getModel(bean, "Samsung", "Z400"));
    expected.add(getModel(bean, "Samsung", "Z400V"));
    expected.add(getModel(bean, "Samsung", "Z510"));
    expected.add(getModel(bean, "Samsung", "Z520V"));
    expected.add(getModel(bean, "Samsung", "Z540"));
    expected.add(getModel(bean, "Samsung", "Z560"));
    expected.add(getModel(bean, "Samsung", "Z720"));
    expected.add(getModel(bean, "Samsung", "ZV50"));
    expected.add(getModel(bean, "Sharp", "550SH"));
    expected.add(getModel(bean, "Sharp", "770SH"));
    expected.add(getModel(bean, "Sharp", "802SH"));
    expected.add(getModel(bean, "Sharp", "903SH"));
    expected.add(getModel(bean, "Sharp", "TM-100"));
    expected.add(getModel(bean, "Sharp", "TM-200"));
    expected.add(getModel(bean, "SonyEricsson", "K790a"));
    expected.add(getModel(bean, "SonyEricsson", "K790c"));
    expected.add(getModel(bean, "SonyEricsson", "K790i"));
    expected.add(getModel(bean, "SonyEricsson", "K800c"));
    expected.add(getModel(bean, "SonyEricsson", "K800i"));
    expected.add(getModel(bean, "SonyEricsson", "K810c"));
    expected.add(getModel(bean, "SonyEricsson", "K810i"));
    expected.add(getModel(bean, "SonyEricsson", "M600c"));
    expected.add(getModel(bean, "SonyEricsson", "M600i"));
    expected.add(getModel(bean, "SonyEricsson", "P990c"));
    expected.add(getModel(bean, "SonyEricsson", "P990i"));
    expected.add(getModel(bean, "SonyEricsson", "S700a"));
    expected.add(getModel(bean, "SonyEricsson", "S700c"));
    expected.add(getModel(bean, "SonyEricsson", "S700i"));
    expected.add(getModel(bean, "SonyEricsson", "T650i"));
    expected.add(getModel(bean, "SonyEricsson", "W580i"));
    expected.add(getModel(bean, "SonyEricsson", "W850a"));
    expected.add(getModel(bean, "SonyEricsson", "W850c"));
    expected.add(getModel(bean, "SonyEricsson", "W850i"));
    expected.add(getModel(bean, "SonyEricsson", "W880c"));
    expected.add(getModel(bean, "SonyEricsson", "W880i"));
    expected.add(getModel(bean, "SonyEricsson", "W900c"));
    expected.add(getModel(bean, "SonyEricsson", "W900i"));
    expected.add(getModel(bean, "SonyEricsson", "W910i"));
    expected.add(getModel(bean, "SonyEricsson", "W950c"));
    expected.add(getModel(bean, "SonyEricsson", "W950i"));
    expected.add(getModel(bean, "Toshiba", "803"));
    expected.add(getModel(bean, "i-mate", "JAM"));
    return expected;
  }

  /**
   * 屏幕Height x Width 为 208x176
   * @param bean
   * @return
   * @throws Exception
   */
  private Set<Model> getScreen_208_176(ModelBean bean) throws Exception {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "NOKIA", "3230"));
    expected.add(getModel(bean, "NOKIA", "3250"));
    expected.add(getModel(bean, "NOKIA", "3600"));
    expected.add(getModel(bean, "NOKIA", "3620"));
    expected.add(getModel(bean, "NOKIA", "3650"));
    expected.add(getModel(bean, "NOKIA", "3660"));
    expected.add(getModel(bean, "NOKIA", "6260"));
    expected.add(getModel(bean, "NOKIA", "6600"));
    expected.add(getModel(bean, "NOKIA", "6620"));
    expected.add(getModel(bean, "NOKIA", "6630"));
    expected.add(getModel(bean, "NOKIA", "6670"));
    expected.add(getModel(bean, "NOKIA", "6670b"));
    expected.add(getModel(bean, "NOKIA", "6680"));
    expected.add(getModel(bean, "NOKIA", "6681"));
    expected.add(getModel(bean, "NOKIA", "6682"));
    expected.add(getModel(bean, "NOKIA", "7610"));
    expected.add(getModel(bean, "NOKIA", "7650"));
    expected.add(getModel(bean, "NOKIA", "N-Gage"));
    expected.add(getModel(bean, "NOKIA", "N-Gage QD"));
    expected.add(getModel(bean, "NOKIA", "N70"));
    expected.add(getModel(bean, "NOKIA", "N72"));
    expected.add(getModel(bean, "NOKIA", "N91"));
    expected.add(getModel(bean, "Panasonic", "X700"));
    expected.add(getModel(bean, "Panasonic", "X701"));
    expected.add(getModel(bean, "Panasonic", "X800"));
    expected.add(getModel(bean, "Sendo", "X"));
    return expected;
  }

  /**
   * 屏幕Height x Width 为 208x208
   * @param bean
   * @return
   * @throws Exception
   */
  private Set<Model> getScreen_208_208(ModelBean bean) throws Exception {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "NOKIA", "5500"));
    expected.add(getModel(bean, "NOKIA", "5500d"));
    expected.add(getModel(bean, "NOKIA", "6230i"));
    return expected;
  }

  /**
   * 屏幕Height x Width 为 208x208
   * @param bean
   * @return
   * @throws Exception
   */
  private Set<Model> getScreen_416_352(ModelBean bean) throws Exception {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "NOKIA", "E60"));
    expected.add(getModel(bean, "NOKIA", "E70"));
    expected.add(getModel(bean, "NOKIA", "N80"));
    expected.add(getModel(bean, "NOKIA", "N90"));
    expected.add(getModel(bean, "NOKIA", "N92"));
    return expected;
  }

  /**
   * 屏幕Height x Width 为 320x240, 支持 Java MIDP 2.0
   * @param bean
   * @return
   * @throws Exception
   */
  private Set<Model> getMidpV2_Screen_320_240(ModelBean bean) throws Exception {
    Set<Model> expected = new HashSet<Model>();
    expected.add(getModel(bean, "BenQ-Siemens", "EL71"));
    expected.add(getModel(bean, "HTC", "9090"));
    expected.add(getModel(bean, "LG", "KS10"));
    expected.add(getModel(bean, "LG", "KU800"));
    expected.add(getModel(bean, "LG", "U400"));
    expected.add(getModel(bean, "Motorola", "A780"));
    expected.add(getModel(bean, "Motorola", "E1000"));
    expected.add(getModel(bean, "Motorola", "E1070"));
    expected.add(getModel(bean, "Motorola", "E680"));
    expected.add(getModel(bean, "Motorola", "RAZRV6"));
    expected.add(getModel(bean, "Motorola", "V1050"));
    expected.add(getModel(bean, "Motorola", "V1075"));
    expected.add(getModel(bean, "Motorola", "V600"));
    expected.add(getModel(bean, "Motorola", "V600i"));
    expected.add(getModel(bean, "Motorola", "V620"));
    expected.add(getModel(bean, "Motorola", "V690"));
    expected.add(getModel(bean, "Motorola", "V6PEBL"));
    expected.add(getModel(bean, "Motorola", "V6V"));
    expected.add(getModel(bean, "NOKIA", "5300"));
    expected.add(getModel(bean, "NOKIA", "5610"));
    expected.add(getModel(bean, "NOKIA", "5700"));
    expected.add(getModel(bean, "NOKIA", "6120c"));
    expected.add(getModel(bean, "NOKIA", "6121c"));
    expected.add(getModel(bean, "NOKIA", "6126"));
    expected.add(getModel(bean, "NOKIA", "6133"));
    expected.add(getModel(bean, "NOKIA", "6233"));
    expected.add(getModel(bean, "NOKIA", "6234"));
    expected.add(getModel(bean, "NOKIA", "6265"));
    expected.add(getModel(bean, "NOKIA", "6265i"));
    expected.add(getModel(bean, "NOKIA", "6267"));
    expected.add(getModel(bean, "NOKIA", "6270"));
    expected.add(getModel(bean, "NOKIA", "6275"));
    expected.add(getModel(bean, "NOKIA", "6275i"));
    expected.add(getModel(bean, "NOKIA", "6280"));
    expected.add(getModel(bean, "NOKIA", "6282"));
    expected.add(getModel(bean, "NOKIA", "6288"));
    expected.add(getModel(bean, "NOKIA", "6290"));
    expected.add(getModel(bean, "NOKIA", "6300"));
    expected.add(getModel(bean, "NOKIA", "6301"));
    expected.add(getModel(bean, "NOKIA", "6500"));
    expected.add(getModel(bean, "NOKIA", "6500c"));
    expected.add(getModel(bean, "NOKIA", "6500s"));
    expected.add(getModel(bean, "NOKIA", "6555"));
    expected.add(getModel(bean, "NOKIA", "7370"));
    expected.add(getModel(bean, "NOKIA", "7373"));
    expected.add(getModel(bean, "NOKIA", "7390"));
    expected.add(getModel(bean, "NOKIA", "7500"));
    expected.add(getModel(bean, "NOKIA", "E50"));
    expected.add(getModel(bean, "NOKIA", "E65"));
    expected.add(getModel(bean, "NOKIA", "N71"));
    expected.add(getModel(bean, "NOKIA", "N73"));
    expected.add(getModel(bean, "NOKIA", "N75"));
    expected.add(getModel(bean, "NOKIA", "N76"));
    expected.add(getModel(bean, "NOKIA", "N77"));
    expected.add(getModel(bean, "NOKIA", "N81"));
    expected.add(getModel(bean, "NOKIA", "N93"));
    expected.add(getModel(bean, "NOKIA", "N93i"));
    expected.add(getModel(bean, "NOKIA", "N95"));
    expected.add(getModel(bean, "Qtek", "9090"));
    expected.add(getModel(bean, "Qtek", "9100"));
    expected.add(getModel(bean, "Sagem", "myX-8"));
    expected.add(getModel(bean, "Samsung", "D600"));
    expected.add(getModel(bean, "Samsung", "D608"));
    expected.add(getModel(bean, "Samsung", "D800"));
    expected.add(getModel(bean, "Samsung", "D900"));
    expected.add(getModel(bean, "Samsung", "E900"));
    expected.add(getModel(bean, "Samsung", "U600"));
    expected.add(getModel(bean, "Samsung", "U700"));
    expected.add(getModel(bean, "Samsung", "Z400"));
    expected.add(getModel(bean, "Samsung", "Z400V"));
    expected.add(getModel(bean, "Samsung", "Z510"));
    expected.add(getModel(bean, "Samsung", "Z520V"));
    expected.add(getModel(bean, "Samsung", "Z540"));
    expected.add(getModel(bean, "Samsung", "Z560"));
    expected.add(getModel(bean, "Samsung", "Z720"));
    expected.add(getModel(bean, "Sharp", "770SH"));
    expected.add(getModel(bean, "Sharp", "802SH"));
    expected.add(getModel(bean, "Sharp", "TM-200"));
    expected.add(getModel(bean, "SonyEricsson", "K790a"));
    expected.add(getModel(bean, "SonyEricsson", "K790c"));
    expected.add(getModel(bean, "SonyEricsson", "K790i"));
    expected.add(getModel(bean, "SonyEricsson", "K800c"));
    expected.add(getModel(bean, "SonyEricsson", "K800i"));
    expected.add(getModel(bean, "SonyEricsson", "M600c"));
    expected.add(getModel(bean, "SonyEricsson", "M600i"));
    expected.add(getModel(bean, "SonyEricsson", "P990c"));
    expected.add(getModel(bean, "SonyEricsson", "P990i"));
    expected.add(getModel(bean, "SonyEricsson", "S700a"));
    expected.add(getModel(bean, "SonyEricsson", "S700c"));
    expected.add(getModel(bean, "SonyEricsson", "S700i"));
    expected.add(getModel(bean, "SonyEricsson", "T650i"));
    expected.add(getModel(bean, "SonyEricsson", "W850a"));
    expected.add(getModel(bean, "SonyEricsson", "W850c"));
    expected.add(getModel(bean, "SonyEricsson", "W850i"));
    expected.add(getModel(bean, "SonyEricsson", "W880c"));
    expected.add(getModel(bean, "SonyEricsson", "W880i"));
    expected.add(getModel(bean, "SonyEricsson", "W900c"));
    expected.add(getModel(bean, "SonyEricsson", "W900i"));
    expected.add(getModel(bean, "SonyEricsson", "W950c"));
    expected.add(getModel(bean, "SonyEricsson", "W950i"));
    expected.add(getModel(bean, "Toshiba", "803"));
    expected.add(getModel(bean, "i-mate", "JAM"));
    return expected;
  }

  /**
   * @param bean
   * @param manufacturerExtID
   * @param modelExternalID
   * @return
   * @throws DMException
   */
  private Model getModel(ModelBean bean, String manufacturerExtID, String modelExternalID) throws DMException {
    return bean.getModelByManufacturerModelID(bean.getManufacturerByExternalID(manufacturerExtID), modelExternalID);
  }

}
