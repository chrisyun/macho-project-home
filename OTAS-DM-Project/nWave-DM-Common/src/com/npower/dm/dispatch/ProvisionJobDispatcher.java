/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/ProvisionJobDispatcher.java,v 1.3 2008/07/25 11:10:45 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/07/25 11:10:45 $
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
package com.npower.dm.dispatch;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/07/25 11:10:45 $
 */
public interface ProvisionJobDispatcher {
  
  /**
   * @return Returns the JobNotificationSender.
   */
  public JobNotificationSender getJobNotificationSender();

  /**
   * @param jobNotificationSender The jobNotificationSender to set.
   */
  public void setJobNotificationSender(JobNotificationSender jobNotificationSender);

  /**
   * Dispatch all of Provision Job
   * @throws DMException
   */
  public void dispatchAll() throws DMException;
  
  /**
   * Dispatch a provision job.
   * @param job
   * @throws DMExceptio
   */
  public void dispatch(String jobID) throws DMException;
  
  /**
   * Dispatch a provision job.
   * @param job
   * @throws DMExceptio
   */
  public void dispatch(long jobID) throws DMException;
  
}
