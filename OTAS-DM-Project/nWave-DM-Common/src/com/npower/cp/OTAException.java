/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/OTAException.java,v 1.2 2007/05/17 06:51:46 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/05/17 06:51:46 $
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
package com.npower.cp;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/05/17 06:51:46 $
 */
public class OTAException extends Exception {

  /**
   * Default constructor
   */
  public OTAException() {
    super();
  }

  /**
   * @param message
   */
  public OTAException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public OTAException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public OTAException(String message, Throwable cause) {
    super(message, cause);
  }

}
