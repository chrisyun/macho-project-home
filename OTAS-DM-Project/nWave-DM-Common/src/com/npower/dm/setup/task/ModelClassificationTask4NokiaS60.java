/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/ModelClassificationTask4NokiaS60.java,v 1.3 2008/09/18 10:22:45 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/09/18 10:22:45 $
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
package com.npower.dm.setup.task;

import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

import com.npower.dm.core.selector.CriteriaModelSelector;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/09/18 10:22:45 $
 */
public class ModelClassificationTask4NokiaS60 extends BaseModelClassificationTask {

  /**
   * Default constructor
   */
  public ModelClassificationTask4NokiaS60() {
    super();
  }
  
  @Override
  protected void createAll(ManagementBeanFactory factory) throws Exception {
    {
      // S60 2nd All
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(Expression.like("familyExternalID", "family_nokia_s60_2nd", MatchMode.START));
      this.updateModelClassification(factory, selector, 
          "Nokia_S60_2nd_All", 
          "Nokia S60 2nd All", 
          "All of Nokia S60 2nd, included FP1, FP2, FP3, ...");
    }
    {
      // S60 2nd fp1
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(Expression.eq("familyExternalID", "family_nokia_s60_2nd_fp1"));
      this.updateModelClassification(factory, selector, 
          "Nokia_S60_2nd_FP1_All", 
          "Nokia S60 2nd FP1 ALL", 
          "All of Nokia S60 2nd FP1");
    }
    {
      // S60 2nd fp2
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(Expression.eq("familyExternalID", "family_nokia_s60_2nd_fp2"));
      this.updateModelClassification(factory, selector, 
          "Nokia_S60_2nd_FP2_All", 
          "Nokia S60 2nd FP2 ALL", 
          "All of Nokia S60 2nd FP2");
    }
    {
      // S60 2nd fp3
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(Expression.eq("familyExternalID", "family_nokia_s60_2nd_fp3"));
      this.updateModelClassification(factory, selector, 
          "Nokia_S60_2nd_FP3_All", 
          "Nokia S60 2nd FP3 ALL", 
          "All of Nokia S60 2nd FP3");
    }
    {
      // S60 3rd All
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(Expression.like("familyExternalID", "family_nokia_s60_3rd", MatchMode.START));
      this.updateModelClassification(factory, selector, 
          "Nokia_S60_3rd_All", 
          "Nokia S60 3rd All", 
          "All of Nokia S60 3rd, included 'Initialize Release, FP1, ...'");
    }
    {
      // S60 3rd Initialize Release
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(Expression.eq("familyExternalID", "family_nokia_s60_3rd"));
      this.updateModelClassification(factory, selector, 
          "Nokia_S60_3rd_Init_All", 
          "Nokia S60 3rd Init All", 
          "All of Nokia S60 3rd Initialize Release");
    }
    {
      // S60 3rd FP1
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(Expression.eq("familyExternalID", "family_nokia_s60_3rd"));
      this.updateModelClassification(factory, selector, 
          "Nokia_S60_3rd_FP1_All", 
          "Nokia S60 3rd FP1 All", 
          "All of Nokia S60 3rd FP1");
    }
  }

}
