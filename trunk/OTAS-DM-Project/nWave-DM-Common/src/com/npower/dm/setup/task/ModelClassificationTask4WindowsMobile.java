/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/ModelClassificationTask4WindowsMobile.java,v 1.1 2008/09/19 10:05:50 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/09/19 10:05:50 $
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

import org.hibernate.criterion.MatchMode;

import com.npower.dm.core.selector.CharacterExpression;
import com.npower.dm.core.selector.CriteriaModelSelector;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/19 10:05:50 $
 */
public class ModelClassificationTask4WindowsMobile extends BaseModelClassificationTask {

  /**
   * Default constructor
   */
  public ModelClassificationTask4WindowsMobile() {
    super();
  }
  
  @Override
  protected void createAll(ManagementBeanFactory factory) throws Exception {
    {
      // Microsoft Windows Mobile 5.0
      String os = "Microsoft Windows Mobile 5.0"; 
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(CharacterExpression.ilike("general.operating.system", os, MatchMode.START));

      this.updateModelClassification(factory, selector, 
          "wm_5.0", 
          "Windows Mobile 5.0", 
          "All of 5.0");
    }
    {
      // Microsoft Windows Mobile 5.0 Smartphone
      String os = "Microsoft Windows Mobile 5.0 Smartphone"; 
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(CharacterExpression.ilike("general.operating.system", os, MatchMode.START));

      this.updateModelClassification(factory, selector, 
          "wm_5.0_sp", 
          "Windows Mobile 5.0 Smartphone", 
          "All of 5.0 Smartphone.");
    }
    {
      // Microsoft Windows Mobile 5.0 PocketPC
      String os = "Microsoft Windows Mobile 5.0 PocketPC"; 
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(CharacterExpression.ilike("general.operating.system", os, MatchMode.START));

      this.updateModelClassification(factory, selector, 
          "wm_5.0_ppc", 
          "Windows Mobile 5.0 PocketPC", 
          "All of 5.0 PocketPC");
    }
    {
      // Microsoft Windows Mobile 6.0
      String os = "Microsoft Windows Mobile 6.0"; 
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(CharacterExpression.ilike("general.operating.system", os, MatchMode.START));

      this.updateModelClassification(factory, selector, 
          "wm_6.0", 
          "Windows Mobile 6.0", 
          "All of Windows Mobile 6.0");
    }
    {
      // Microsoft Windows Mobile 6.0 Standard
      String os = "Microsoft Windows Mobile 6.0 Standard"; 
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(CharacterExpression.ilike("general.operating.system", os, MatchMode.START));

      this.updateModelClassification(factory, selector, 
          "wm_6.0_s", 
          "Windows Mobile 6.0 Standard", 
          "All of Windows Mobile 6.0 Standard.");
    }
    {
      // Microsoft Windows Mobile 6.0 Professional
      String os = "Microsoft Windows Mobile 6.0 Prof"; 
      CriteriaModelSelector selector = (CriteriaModelSelector)factory.createBean(CriteriaModelSelector.class);
      selector.addExpression(CharacterExpression.ilike("general.operating.system", os, MatchMode.START));

      this.updateModelClassification(factory, selector, 
          "wm_6.0_pro", 
          "Windows Mobile 6.0 Professional", 
          "All of Windows Mobile 6.0 Professional");
    }
  }

}
