/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/workflow/SequenceProcessor.java,v 1.3 2007/08/29 06:21:00 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2007/08/29 06:21:00 $
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

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.protocol.ManagementInitialization;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/08/29 06:21:00 $
 */
public class SequenceProcessor extends BaseProcessor {

  private Log logger = LogFactory.getLog(SequenceProcessor.class);

  /**
   * 
   */
  public SequenceProcessor() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.workflow.BaseProcessor#supports(com.npower.dm.workflow.Activity)
   */
  @Override
  public boolean supports(Activity activity) {
    return (activity instanceof BaseActivity);
  }

  /**
   * Determine if the process should stop
   * 
   * @param context
   *          the current process context
   * @param activity
   *          the current activity in the iteration
   */
  private boolean processShouldStop(ProcessContext context, Activity activity) {
    if (context != null && context.stopProcess()) {
      logger.info("Interrupted workflow as requested by:" + activity.getName());
      return true;
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.workflow.BaseProcessor#doActivities()
   */
  @Override
  public void doActivities() {
    this.doActivities(null);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.workflow.BaseProcessor#doActivities(java.lang.Object)
   */
  @Override
  public void doActivities(Object seedData) {

    if (logger.isDebugEnabled())
      logger.debug(getName() + " processor is running..");

    // retrieve
    List<Activity> activities = getActivities();

    // retrieve the Workflow ProcessContext
    ProcessContext context = this.getProcessContext();

    if (seedData != null)
      context.setSeedData(seedData);

    for (Iterator<Activity> it = activities.iterator(); it.hasNext();) {
        Activity activity = (Activity) it.next();
  
        if (logger.isDebugEnabled()) {
          logger.debug("running activity:" + activity.getName() + " using arguments:" + context);
        }
        
        try {
            context = activity.execute(context);
  
        } catch (Throwable th) {
          ErrorHandler errorHandler = activity.getErrorHandler();
          if (errorHandler == null) {
            logger.info("no error handler for this action, run default error handler and abort processing ");
            getDefaultErrorHandler().handleError(context, th);
            break;
          } else {
            logger.info("run error handler and continue");
            errorHandler.handleError(context, th);
          }
        }
  
        // ensure its ok to continue the process
        if (processShouldStop(context, activity)) {
           break;
        }
    }
    logger.debug(getName() + " processor is done.");
  }

  /* (non-Javadoc)
   * @see com.npower.dm.workflow.WorkflowProcessor#beginSession(java.lang.String, java.security.Principal, int, sync4j.framework.core.dm.ddf.DevInfo, sync4j.framework.engine.dm.DeviceDMState, sync4j.framework.protocol.ManagementInitialization)
   */
  public void beginSession(String sessionId, Principal principal, int type, DevInfo devInfo, DeviceDMState dmState, ManagementInitialization init) {
    
  }

  /* (non-Javadoc)
   * @see com.npower.dm.workflow.WorkflowProcessor#endSession(int)
   */
  public void endSession(int completionCode) {
    // TODO Auto-generated method stub
    
  }

  /* (non-Javadoc)
   * @see com.npower.dm.workflow.WorkflowProcessor#getNextOperations()
   */
  public List<ManagementOperation> getNextOperations() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.workflow.WorkflowProcessor#setOperationResults(java.util.List)
   */
  public void setOperationResults(List<ManagementOperationResult> results) {
    // TODO Auto-generated method stub
    
  }

}
