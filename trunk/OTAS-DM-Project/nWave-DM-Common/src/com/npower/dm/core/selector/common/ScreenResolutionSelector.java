/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/selector/common/ScreenResolutionSelector.java,v 1.2 2008/09/17 09:25:16 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/09/17 09:25:16 $
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
package com.npower.dm.core.selector.common;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.Model;
import com.npower.dm.core.selector.BaseModelBeanSelector;
import com.npower.dm.management.ModelBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/09/17 09:25:16 $
 */
public class ScreenResolutionSelector extends BaseModelBeanSelector {
  
  private int width = 0;
  private int height = 0;

  /**
   * 
   */
  public ScreenResolutionSelector() {
    super();
  }

  /**
   * @param height
   * @param width
   */
  public ScreenResolutionSelector(int height, int width) {
    super();
    this.height = height;
    this.width = width;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * @param modelBean
   * @param model
   * @return
   * @throws Exception
   */
  protected boolean isSelected(ModelBean modelBean, Model model) throws Exception {
    String h = modelBean.getCapability(model, "resolution_height");
    if (StringUtils.isEmpty(h) || Long.parseLong(h) != this.getHeight()) {
       return false;
    }
    String w = modelBean.getCapability(model, "resolution_width");
    if (StringUtils.isEmpty(w) || Long.parseLong(w) != this.getWidth()) {
       return false;
    }
    return true;
  }

}
