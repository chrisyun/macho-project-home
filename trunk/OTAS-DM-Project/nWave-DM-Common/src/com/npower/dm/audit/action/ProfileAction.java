/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/action/ProfileAction.java,v 1.3 2009/02/13 10:49:06 zhaowx Exp $
  * $Revision: 1.3 $
  * $Date: 2009/02/13 10:49:06 $
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
package com.npower.dm.audit.action;

/**
 * Device Audit Log Action Type.
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2009/02/13 10:49:06 $
 */
public enum ProfileAction {
  //Type: Profile
  CREATE("createProfile"),
  DELETE("deleteProfile"),
  UPDATE("updateProfile"),  
  View("viewProfile"),
  ;
  
  
  private String value = null;
  
  /**
   * Constractor
   * @param value
   *        Value of action
   */
  private ProfileAction(String value) {
    this.value = value;
  }
  
  /**
   * Return the value
   * @return
   */
  public String getValue() {
    return this.value;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  public String toString() {
    return this.value;
  }
}
