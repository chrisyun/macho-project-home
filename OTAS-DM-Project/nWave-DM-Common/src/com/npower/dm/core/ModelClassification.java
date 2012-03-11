/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ModelClassification.java,v 1.2 2008/09/10 09:59:42 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/09/10 09:59:42 $
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
package com.npower.dm.core;

import java.io.Serializable;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/09/10 09:59:42 $
 */
public interface ModelClassification extends Serializable {
  
  /**
   * @return
   */
  public abstract long getId();
  
  /**
   * @return Returns the externalID.
   */
  public abstract String getExternalID();

  /**
   * @param externalID The externalID to set.
   */
  public abstract void setExternalID(String externalID);
  
  /**
   * @return Returns the name.
   */
  public abstract String getName();
  
  /**
   * @param name The name to set.
   */
  public abstract void setName(String name);

  /**
   * @return Returns the description.
   */
  public abstract String getDescription();
  
  /**
   * @param description
   *        The description to set.
   */
  public abstract void setDescription(String name);
  
  /**
   * 设置ModelSelector
   * @param selector
   * @throws DMException 
   */
  public abstract void setModelSelector(ModelSelector selector) throws DMException;
  
  /**
   * 返回当前的ModelSelector
   * @return
   * @throws DMException 
   */
  public abstract ModelSelector getModelSelector() throws DMException;
  
  /**
   * 检查Model是否属于当前的ModelClass
   * @return
   * @throws DMException 
   */
  public boolean isMemeber(Model model) throws DMException;
  
}
