/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/selector/NullModelSelector.java,v 1.1 2008/09/09 01:59:07 hcp Exp $
 * $Revision: 1.1 $
 * $Date: 2008/09/09 01:59:07 $
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

import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelSelector;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.1 $ ${date}6:13:38 PM$
 * com.npower.dm.core.selector
 * nWave-DM-Common
 * NullModelSelector.java
 */
public class NullModelSelector extends AbstractModelSelector implements ModelSelector {

  /**
   * 
   */
  public NullModelSelector() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.selector.AbstractModelSelector#getModels()
   */
  @Override
  public Set<Model> getModels() throws DMException {
    Set<Model> set = new TreeSet<Model>();
    return set;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.selector.AbstractModelSelector#isSelected(com.npower.dm.core.Model)
   */
  @Override
  public boolean isSelected(Model model) throws DMException {
    return false;
  }

}
