/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/management/TestSearchBean.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/06/16 10:11:15 $
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
package com.npower.dm.management;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.dm.AllTests;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.hibernate.entity.DeviceEntity;
import com.npower.dm.hibernate.entity.ModelEntity;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestSearchBean extends TestCase {

  /**
   * @param name
   */
  public TestSearchBean(String name) {
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
  
  public void testPaginatedList4Models() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        SearchBean bean = factory.createSearchBean();
      
        // Get Page#1
        {
          Criteria criteria = getModelCriteria(factory, bean);
          PaginatedResult list = bean.getPaginatedList(criteria, 1, 20);
          assertEquals(113, list.getFullListSize());
          List<?> result = list.getList();
          assertNotNull(result);
          assertEquals(20, result.size());
          
        }
        
        // Get Page#2
        {
          Criteria criteria = getModelCriteria(factory, bean);
          PaginatedResult list = bean.getPaginatedList(criteria, 2, 20);
          assertEquals(113, list.getFullListSize());
          List<?> result = list.getList();
          assertNotNull(result);
          assertEquals(20, result.size());
          
          assertEquals(113, list.getFullListSize());
          result = list.getList();
          assertNotNull(result);
          assertEquals(20, result.size());
          
          assertEquals(113, list.getFullListSize());
          result = list.getList();
          assertNotNull(result);
          assertEquals(20, result.size());
        }
        
        // Get Page#3
        {
          Criteria criteria = getModelCriteria(factory, bean);
          PaginatedResult list = bean.getPaginatedList(criteria, 3, 20);
          assertEquals(113, list.getFullListSize());
          List<?> result = list.getList();
          assertNotNull(result);
          assertEquals(20, result.size());
        }
        
        // Get Page#4
        {
          Criteria criteria = getModelCriteria(factory, bean);
          PaginatedResult list = bean.getPaginatedList(criteria, 4, 20);
          assertEquals(113, list.getFullListSize());
          List<?> result = list.getList();
          assertNotNull(result);
          assertEquals(20, result.size());
        }
        
        // Get Page#5
        {
          Criteria criteria = getModelCriteria(factory, bean);
          PaginatedResult list = bean.getPaginatedList(criteria, 5, 20);
          assertEquals(113, list.getFullListSize());
          List<?> result = list.getList();
          assertNotNull(result);
          assertEquals(20, result.size());
        }
        
        // Get Page#6
        {
          Criteria criteria = getModelCriteria(factory, bean);
          PaginatedResult list = bean.getPaginatedList(criteria, 6, 20);
          assertEquals(113, list.getFullListSize());
          List<?> result = list.getList();
          assertNotNull(result);
          assertEquals(13, result.size());
        }
        
    } catch (DMException e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * @param bean
   * @return
   * @throws DMException
   */
  private Criteria getModelCriteria(ManagementBeanFactory factory, SearchBean bean) throws DMException {
    Criteria criteria = bean.newCriteriaInstance(ModelEntity.class);
    return criteria;
  }
  
  public void testPaginatedList4Device() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        SearchBean bean = factory.createSearchBean();
        String searchManufacturerID = "23617630";
        String searchMadelID = null;
        Criteria criteria = this.getDeviceCriteria(factory, bean, searchManufacturerID, searchMadelID);
        
        {
          // Page #1
          PaginatedResult list = bean.getPaginatedList(criteria, 1, 2000);
          assertEquals(2140, list.getFullListSize());
          List<?> result = list.getList();
          assertNotNull(result);
          assertEquals(2000, result.size());
        }
        
        {
          // Page #2
          PaginatedResult list = bean.getPaginatedList(criteria, 2, 2000);
          assertEquals(2140, list.getFullListSize());
          List<?> result = list.getList();
          assertNotNull(result);
          assertEquals(140, result.size());
        }
        
    } catch (DMException e) {
      throw e;
    } finally {
      factory.release();
    }
    
  }

  /**
   * @param bean
   * @return
   * @throws DMException
   */
  private Criteria getDeviceCriteria(ManagementBeanFactory factory, SearchBean bean, String searchManufacturerID, String searchMadelID) throws DMException {
    SearchBean searchBean = factory.createSearchBean();
    ModelBean modelBean = factory.createModelBean();
    Criteria criteria = searchBean.newCriteriaInstance(DeviceEntity.class);
    criteria.addOrder(Order.asc("externalId"));
    if (StringUtils.isNotEmpty(searchManufacturerID)) {
       Manufacturer manufactuer = modelBean.getManufacturerByID(searchManufacturerID);
       if (manufactuer != null) {
          Criteria subCriteria = criteria.createCriteria("model");
          subCriteria.add(Expression.eq("manufacturer", manufactuer));
       }
    }
   
    if (StringUtils.isNotEmpty(searchMadelID)) {
       Model model = modelBean.getModelByID(searchMadelID);
       if (model != null) {
          criteria.add(Expression.eq("model", model));
       }
    }
    return criteria;
  }

}
