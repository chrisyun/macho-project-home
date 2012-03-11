/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/action/ClientProvTemplateAction.java,v 1.1 2009/02/19 10:18:10 zhaowx Exp $
 * $Revision: 1.1 $
 * $Date: 2009/02/19 10:18:10 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2009 NPower Network Software Ltd.  All rights reserved.
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
 * @author Zhao wanxiang
 * @version $Revision: 1.1 $ $Date: 2009/02/19 10:18:10 $
 */

public enum ClientProvTemplateAction {

  //Type: ClientProvTemplate
  CREATE("createClientProvTemplate"),
  DELETE("deleteClientProvTemplate"),
  UPDATE("updateClientProvTemplate");
    
  
  private String value = null;
  
  /**
   * Constractor
   * @param value
   *        Value of action
   */
  private ClientProvTemplateAction(String value) {
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
