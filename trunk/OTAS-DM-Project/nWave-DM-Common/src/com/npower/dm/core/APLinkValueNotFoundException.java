/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/APLinkValueNotFoundException.java,v 1.1 2007/01/12 03:34:44 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/01/12 03:34:44 $
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
package com.npower.dm.core;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/01/12 03:34:44 $
 */
public class APLinkValueNotFoundException extends DMException {

  /**
   * 
   */
  public APLinkValueNotFoundException() {
    super();
  }

  /**
   * @param arg0
   */
  public APLinkValueNotFoundException(String arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   */
  public APLinkValueNotFoundException(Throwable arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   * @param arg1
   */
  public APLinkValueNotFoundException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
