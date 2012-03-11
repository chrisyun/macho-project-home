/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/SyncItem.java,v 1.2 2008/11/18 04:25:28 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/18 04:25:28 $
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
package com.npower.unicom.sync;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/11/18 04:25:28 $
 */
public abstract class SyncItem {
  
  /**
   * 流水号
   */
  private String id = null;
  
  /**
   * UpdateType
   */
  private SyncAction action = null;

  /**
   * 
   */
  public SyncItem() {
    super();
  }

  /**
   * @param id
   * @param action
   */
  public SyncItem(String id, SyncAction action) {
    super();
    this.id = id;
    this.action = action;
  }

  /**
   * 流水号
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * 流水号
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 表示当前同步项的同步类型: 添加、修改、删除
   * @return the action
   */
  public SyncAction getAction() {
    return action;
  }

  /**
   * @param action the action to set
   */
  public void setAction(SyncAction action) {
    this.action = action;
  }

  /**
   * @param actionString
   */
  public void setActionAsString(String actionString) {
    this.action = SyncAction.valueOfByString(actionString);
  }

}
