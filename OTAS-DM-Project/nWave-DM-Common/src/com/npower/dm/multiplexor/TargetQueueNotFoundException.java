/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/multiplexor/TargetQueueNotFoundException.java,v 1.1 2008/12/25 02:32:31 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/25 02:32:31 $
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
package com.npower.dm.multiplexor;

import com.npower.util.HelperUtil;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/12/25 02:32:31 $
 */
public class TargetQueueNotFoundException extends Exception {

  private String from;
  private String to;
  private byte[] msgBytes;
  private String msgText;

  /**
   * 
   */
  public TargetQueueNotFoundException() {
    super();
  }

  /**
   * @param message
   */
  public TargetQueueNotFoundException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public TargetQueueNotFoundException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public TargetQueueNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Throwable#getMessage()
   */
  public TargetQueueNotFoundException(String from, String to, byte[] msgBytes, String msgText) {
    this.from = from;
    this.to = to;
    this.msgBytes = msgBytes;
    this.msgText = msgText;
  }

  /* (non-Javadoc)
   * @see java.lang.Throwable#getMessage()
   */
  public String getMessage() {
    return "Could not found target queue for msg[from: " + this.from + 
            ", to: " + this.to + 
            ", msg: " + this.msgText + 
            ", hex: " + HelperUtil.bytesToHexString(this.msgBytes) + "]";
  }

}