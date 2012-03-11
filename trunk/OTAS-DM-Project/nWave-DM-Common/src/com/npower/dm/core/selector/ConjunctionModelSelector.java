/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/selector/ConjunctionModelSelector.java,v 1.3 2008/09/05 04:32:35 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/09/05 04:32:35 $
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelSelector;

/**
 * OR运算ModelSelector集合
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/09/05 04:32:35 $
 */
public class ConjunctionModelSelector extends AbstractModelSelector implements ModelSelector {
  private List<ModelSelector> selectors = new ArrayList<ModelSelector>();

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public ConjunctionModelSelector() {
    super();
  }
  
  /**
   * 添加一个ModelSelector
   * @param selector
   * @return
   */
  public ConjunctionModelSelector add(ModelSelector selector) {
    this.selectors.add(selector);
    return this;
  }

  /**
   * @return the selectors
   */
  public List<ModelSelector> getSelectors() {
    ModelSelectorHelper.decorate(this.getManagementBeanFactory(), this.getHibernateSession(), selectors);
    return selectors;
  }

  /**
   * @param selectors the selectors to set
   */
  public void setSelectors(List<ModelSelector> selectors) {
    this.selectors = selectors;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSelector#getModels()
   */
  public Set<Model> getModels() throws DMException {
    Set<Model> result = new LinkedHashSet<Model>();
    if (this.getSelectors().size() == 0) {
       return result;
    }
    // Get first selected models
    result.addAll(this.getSelectors().get(0).getModels());
    for (ModelSelector selector: this.getSelectors()) {
        result.retainAll(selector.getModels());
    }
    return result;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSelector#isSelected(com.npower.dm.core.Model)
   */
  public boolean isSelected(Model model) throws DMException {
    if (this.getSelectors().isEmpty()) {
       return false;
    }
    for (ModelSelector selector: this.getSelectors()) {
        if (!selector.isSelected(model)) {
           return false;
        }
    }
    return true;
  }
}
