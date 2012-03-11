/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/workflow/BaseActivity.java,v 1.1 2007/01/08 07:22:08 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/01/08 07:22:08 $
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

/**
 * Abstract implemention of activity designed for re-use by business 
 * purpose specific Activities.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/01/08 07:22:08 $
 */
public abstract class BaseActivity implements Activity {

  private ErrorHandler errorHandler;
  
  private String name = BaseActivity.class.getName();
  
  /**
   * Default constrctuor.
   */
  public BaseActivity() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.workflow.Activity#execute(com.npower.dm.workflow.ProcessContext)
   */
  public abstract ProcessContext execute(ProcessContext context) throws Exception;

  /* (non-Javadoc)
   * @see com.npower.dm.workflow.Activity#getErrorHandler()
   */
  public ErrorHandler getErrorHandler() {
    return this.errorHandler;
  }

  /**
   * Set the fine grained error handler
   * @param errorHandler
   */
  public void setErrorHandler(ErrorHandler errorHandler) {
      this.errorHandler = errorHandler;
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
}
