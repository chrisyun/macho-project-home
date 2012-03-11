/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/selector/j2me/MidpV1Selector.java,v 1.3 2008/09/17 05:23:17 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/09/17 05:23:17 $
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
package com.npower.dm.core.selector.j2me;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.Model;
import com.npower.dm.core.selector.BaseModelBeanSelector;
import com.npower.dm.management.ModelBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/09/17 05:23:17 $
 */
public class MidpV1Selector extends BaseModelBeanSelector {

  /**
   * 
   */
  public MidpV1Selector() {
    super();
  }

  /**
   * @param modelBean
   * @param model
   * @return
   * @throws Exception
   */
  protected boolean isSelected(ModelBean modelBean, Model model) throws Exception {
    String v = modelBean.getCapability(model, "j2me_midp_1_0");
    if (StringUtils.isNotEmpty(v) && v.trim().equals("true")) {
       return true;
    }
    return false;
  }

}
