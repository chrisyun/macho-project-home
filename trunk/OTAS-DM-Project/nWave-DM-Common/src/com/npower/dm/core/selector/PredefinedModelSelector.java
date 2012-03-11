/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/selector/PredefinedModelSelector.java,v 1.1 2008/09/04 06:01:19 zhao Exp $
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

import java.util.LinkedHashSet;
import java.util.Set;

import com.npower.dm.core.Model;
import com.npower.dm.core.ModelSelector;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/04 06:01:19 $
 */
public class PredefinedModelSelector extends AbstractModelSelector implements ModelSelector {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private Set<Model> models = new LinkedHashSet<Model>();

  /**
   * 
   */
  public PredefinedModelSelector() {
    super();
  }

  /**
   * @param models
   */
  public PredefinedModelSelector(Set<Model> models) {
    super();
    this.models = models;
  }

  /**
   * @param models the models to set
   */
  public void setModels(Set<Model> models) {
    this.models = models;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSelector#getModels()
   */
  public Set<Model> getModels() {
    return models;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSelector#isSelected(com.npower.dm.core.Model)
   */
  public boolean isSelected(Model model) {
    return this.getModels().contains(model);
  }

}
