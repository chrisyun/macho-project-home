/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/workflow/WorkflowProcessor.java,v 1.2 2007/01/09 04:21:10 zhao Exp $
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
public interface WorkflowProcessor extends Serializable {
  
  /**
   * Return the name of processor.
   * @return
   *        Name of processor.
   */
  public String getName();

  /**
   * Sets the collection of Activities to be executed by the Workflow Process
   * 
   * @param activities ordered collection (List) of activities to be executed by the processor
   */
  public void setActivities(List<Activity> activities);

  /**
   * Set default Error handler
   * @param defaultErrorHandler
   *        Default Error Handler.
   */
  public void setDefaultErrorHandler(ErrorHandler defaultErrorHandler);
  
  /**
   * To be implemented by subclasses, ensures each Activity configured in this process
   * is supported.  This method is called by the Processor 
   * for each Activity configured.  An implementing subclass should ensure the Activity
   *  type passed in is supported.  Implementors could possibly support multiple
   * types of activities.
   * 
   * @param Activyt - activity instance of the configured activity
   * @return true if the activity is supported
   */
  public boolean supports(Activity activity);

  /**
   * Abstract method used to kickoff the processing of work flow activities.
   * Each processor implementation should implement doActivities as a means
   * of carrying out the activities wired to the workflow process.
   */
  public void doActivities();
  
  /**
   * Abstract method used to kickoff the processing of work flow activities.
   * Each processor implementation should implement doActivities as a means
   * of carrying out the activities wired to the workflow process.  This version
   * of doActivities is designed to be called from some external entity, e.g.
   * listening a JMS queue.  That external entitiy would proved the seed data.
   * 
   * @param seedData - data necessary for the workflow process to start execution
   */
  public void doActivities(Object seedData);
  
  
  public void beginSession(String sessionId, Principal principal, int type, DevInfo devInfo, DeviceDMState dmState, ManagementInitialization init);
  
  public void endSession(int completionCode);
  
  public List<ManagementOperation> getNextOperations();
  
  public void setOperationResults(List<ManagementOperationResult> results);

}
