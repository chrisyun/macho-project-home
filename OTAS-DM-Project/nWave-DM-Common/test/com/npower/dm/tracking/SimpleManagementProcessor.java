/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/tracking/SimpleManagementProcessor.java,v 1.1 2008/08/04 10:21:31 chenlei Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/04 10:21:31 $
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

package com.npower.dm.tracking;

import sync4j.framework.core.Alert;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;

/**
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2008/08/04 10:21:31 $
 */

public class SimpleManagementProcessor implements ManagementProcessor {

  /**
   * 
   */
  public SimpleManagementProcessor() {
    // TODO Auto-generated constructor stub
  }

  /* (non-Javadoc)
   * @see sync4j.framework.engine.dm.ManagementProcessor#beginSession(sync4j.framework.engine.dm.SessionContext)
   */
  public void beginSession(SessionContext arg0) throws ManagementException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see sync4j.framework.engine.dm.ManagementProcessor#endSession(int)
   */
  public void endSession(int arg0) throws ManagementException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see sync4j.framework.engine.dm.ManagementProcessor#getName()
   */
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see sync4j.framework.engine.dm.ManagementProcessor#getNextOperations()
   */
  public ManagementOperation[] getNextOperations() throws ManagementException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see sync4j.framework.engine.dm.ManagementProcessor#setGenericAlert(sync4j.framework.core.Alert[])
   */
  public void setGenericAlert(Alert[] arg0) throws ManagementException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see sync4j.framework.engine.dm.ManagementProcessor#setOperationResults(sync4j.framework.engine.dm.ManagementOperationResult[])
   */
  public void setOperationResults(ManagementOperationResult[] arg0) throws ManagementException {
    // TODO Auto-generated method stub

  }

}
