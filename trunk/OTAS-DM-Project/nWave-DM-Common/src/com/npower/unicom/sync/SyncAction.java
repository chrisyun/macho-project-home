/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/SyncAction.java,v 1.4 2008/11/19 04:30:10 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/11/19 04:30:10 $
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
 * @version $Revision: 1.4 $ $Date: 2008/11/19 04:30:10 $
 */
public enum SyncAction {
  Add(1),
  Modify(2),
  Delete(3)
  ;
  
  private int value;

  private SyncAction(int s) {
    this.value = s;
  }
  
  /**
   * @return the value
   */
  public int getValue() {
    return value;
  }

  /**
   * @param value the value to set
   */
  public void setValue(int value) {
    this.value = value;
  }

  /**
   * 根据字符串返回对应的Enum
   * @param v
   * @return
   */
  public static SyncAction valueOfByString(String v) {
    for (SyncAction action: SyncAction.values()) {
        if (action.getValue() == Integer.parseInt(v)) {
           return action;
        }
    }
    return null;
  }
}
