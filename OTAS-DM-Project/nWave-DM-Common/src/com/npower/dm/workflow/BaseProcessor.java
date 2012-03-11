/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/workflow/BaseProcessor.java,v 1.2 2007/08/29 06:21:00 zhao Exp $
  * $Revision: 1.2 $
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

import java.util.List;

/**
 * Base class for all Workflow Processors.  
 * Responsible of keeping track of an ordered collection
 *  
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/08/29 06:21:00 $
 */
public abstract class BaseProcessor implements WorkflowProcessor {

  private String name = BaseProcessor.class.getName();
  
  private ProcessContext processContext = null;
  
  private List<Activity> activities;
  private ErrorHandler defaultErrorHandler;

  /**
   * Default constrctuor.
   */
  public BaseProcessor() {
    super();
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the processContext
   */
  public ProcessContext getProcessContext() {
    return processContext;
  }

  /**
   * @param processContext the processContext to set
   */
  public void setProcessContext(ProcessContext processContext) {
    this.processContext = processContext;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.workflow.WorkflowProcessor#setActivities(java.util.List)
   */
  public void setActivities(List<Activity> activities) {
    this.activities = activities;

  }

  /* (non-Javadoc)
   * @see com.npower.dm.workflow.WorkflowProcessor#setDefaultErrorHandler(com.npower.dm.workflow.ErrorHandler)
   */
  public void setDefaultErrorHandler(ErrorHandler defaultErrorHandler) {
    this.defaultErrorHandler = defaultErrorHandler;
  }

  /**
   * Return Activities.
   * @return
   */
  public List<Activity> getActivities() {
    return activities;
  }
  
  /**
   * Return default error handler.
   * @return
   */
  public ErrorHandler getDefaultErrorHandler() {
    return defaultErrorHandler;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.workflow.WorkflowProcessor#doActivities()
   */
  public abstract void doActivities();

  /* (non-Javadoc)
   * @see com.npower.dm.workflow.WorkflowProcessor#doActivities(java.lang.Object)
   */
  public abstract void doActivities(Object seedData);

  /* (non-Javadoc)
   * @see com.npower.dm.workflow.WorkflowProcessor#supports(com.npower.dm.workflow.Activity)
   */
  public abstract boolean supports(Activity activity);


}
