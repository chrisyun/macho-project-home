/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/ModelClassificationBean.java,v 1.1 2008/09/04 06:01:20 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/09/04 06:01:20 $
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
package com.npower.dm.management;

import java.util.List;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.ModelSelector;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/04 06:01:20 $
 */
public interface ModelClassificationBean {
  
  /**
   * 返回系统定义的所有的ModelClassification
   * @return
   * @throws DMException
   */
  public abstract List<ModelClassification> getAllOfModelClassifications() throws DMException;

  /**
   * 返回Model所属的ModelClass
   * @param model
   * @return
   * @throws DMException
   */
  public abstract List<ModelClassification> getModelClassByModel(Model model) throws DMException;
  
  /**
   * 检查model是否属于modelClass.
   * @param modelClassification
   * @param model
   * @return
   * @throws DMException
   */
  public abstract boolean isMemeber(ModelClassification modelClassification, Model model) throws DMException;
  
  /**
   * @param extID
   * @param name
   * @param selector
   * @return
   * @throws DMException
   */
  public abstract ModelClassification newInstance(String extID, String name, ModelSelector selector) throws DMException;
  
  /**
   * @param extID
   * @param name
   * @param models
   * @return
   * @throws DMException
   */
  public abstract ModelClassification newInstance(String extID, String name, List<Model> models) throws DMException;
  
  /**
   * @param extID
   * @param name
   * @param selector
   * @param models
   * @return
   * @throws DMException
   */
  public abstract ModelClassification newInstance(String extID, String name, ModelSelector selector, List<Model> models) throws DMException;
  
  /**
   * Update and save
   * @param modelClassification
   * @throws DMException
   */
  public abstract void update(ModelClassification modelClassification) throws DMException;
  
  /**
   * Update and save
   * @param modelClassification
   * @param models
   * @throws DMException
   */
  public abstract void update(ModelClassification modelClassification, List<Model> models) throws DMException;
  
  /**
   * Delete it.
   * @param modelClassification
   * @throws DMException
   */
  public abstract void delete(ModelClassification modelClassification) throws DMException;
  
  /**
   * @param id
   * @return
   * @throws DMException
   */
  public abstract ModelClassification getModelClassificationByID(long id) throws DMException;
  
  /**
   * @param extID
   * @return
   * @throws DMException
   */
  public abstract ModelClassification getModelClassificationByExtID(String extID) throws DMException;
  
}
