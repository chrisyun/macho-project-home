/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/client/DMClientEmulator.java,v 1.1 2006/12/27 03:46:28 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2006/12/27 03:46:28 $
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
package com.npower.dm.client;

import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;

import com.npower.dm.core.DMException;

/**
 * OMA client Emulator.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2006/12/27 03:46:28 $
 */
public interface DMClientEmulator {
  
  /**
   * Initialize the emulator.
   */
  public void initialize();
  
  /**
   * Process the operations and return the result.
   * @param operations
   * @return
   * @throws DMException
   */
  public abstract ManagementOperationResult[] process(ManagementOperation[] operations) throws DMException;
  
  /**
   * Reset the device.
   *
   */
  public abstract void reset();

}
