/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/wurfl/WurflException.java,v 1.2 2007/11/14 06:18:55 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/11/14 06:18:55 $
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

package com.npower.wurfl;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/11/14 06:18:55 $
 */
public class WurflException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 5929252362430112358L;

  public WurflException() {
  }

  public WurflException(String msg) {
    super(msg);
  }

}
