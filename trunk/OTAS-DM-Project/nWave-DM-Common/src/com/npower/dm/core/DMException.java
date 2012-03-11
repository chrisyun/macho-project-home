/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/DMException.java,v 1.7 2006/04/19 07:39:01 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2006/04/19 07:39:01 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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
 * Root Exception for DM services.
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2006/04/19 07:39:01 $
 */
public class DMException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public DMException() {
    super();
  }

  /**
   * @param arg0
   */
  public DMException(String arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   */
  public DMException(Throwable arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   * @param arg1
   */
  public DMException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
