/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/selector/TestCriteriaSelector.java,v 1.1 2008/09/04 06:01:19 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/09/04 06:01:19 $
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

import junit.framework.TestCase;

import org.hibernate.criterion.Expression;

import com.npower.dm.AllTests;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.hibernate.management.AbstractBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * 要求安装型号数据库后, 才能运行本测试用例
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/04 06:01:19 $
 */
public class TestCriteriaSelector extends TestCase {

  /**
   * @param name
   */
  public TestCriteriaSelector(String name) {
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
  
  public void testCase1() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        // get a model
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID("NOKIA");
        Model model1 = modelBean.getModelByManufacturerModelID(manufacturer, "6120c");
        Model model2 = modelBean.getModelByManufacturerModelID(manufacturer, "6681");
        
        String os = "S60 3rd Edition, Feature Pack 1"; // S60 3rd edition (Symbian OS , Series 60 UI)
        {
          // Test Model Selector
          CriteriaModelSelector selector = new CriteriaModelSelector();
          selector.addExpression(Expression.or(CharacterExpression.eq("general.developer.platform", os), 
                                               CharacterExpression.eq("features.os", os)));
          selector.setHibernateSession(((AbstractBean)modelBean).getHibernateSession());
          
          // Testing & checking
          assertTrue(selector.isSelected(model1));
          assertTrue(!selector.isSelected(model2));
        }
        
        // Get ModelClassificationEntity Bean
        //ModelClassificationBean bean = (ModelClassificationBean)factory.createBean(ModelClassificationBean.class);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
