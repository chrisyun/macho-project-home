/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/workflow/Activity.java,v 1.2 2007/01/09 04:21:10 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/01/09 04:21:10 $
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
package com.npower.dm.workflow;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.protocol.ManagementInitialization;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/01/09 04:21:10 $
 */
public interface Activity extends Serializable {

  /**
   * Return name of activity.
   * @return
   *        Return name of activity.
   */
  public String getName();
  
  /**
   * Get the fine-grained error handler wired up for this Activity
   * @return
   */
  public ErrorHandler getErrorHandler();
  
  /**
   * Called by the encompassing processor to activate
   * the execution of the Activity
   * 
   * @param context
   *        Process context for this workflow
   * @return 
   *        Resulting process context
   * @throws Exception
   */
  public ProcessContext execute(ProcessContext context) throws Exception;
  
  public void beginSession(String sessionId, Principal principal, int type, DevInfo devInfo, DeviceDMState dmState, ManagementInitialization init);
  
  public void endSession(int completionCode);
  
  public List<ManagementOperation> getNextOperations();
  
  public void setOperationResults(List<ManagementOperationResult> results);

}
