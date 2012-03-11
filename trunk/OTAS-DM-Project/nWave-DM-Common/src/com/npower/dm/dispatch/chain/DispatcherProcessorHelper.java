/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/chain/DispatcherProcessorHelper.java,v 1.2 2008/08/01 06:45:39 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/01 06:45:39 $
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
package com.npower.dm.dispatch.chain;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/08/01 06:45:39 $
 */
public abstract class DispatcherProcessorHelper {

  /**
   * 
   */
  public DispatcherProcessorHelper() {
    super();
  }

  // public methods ---------------------------------------------------------------------
  /**
   * In seconds
   * @param job
   * @param carrier
   * @return
   */
  public static long getMaxDuration4Notify(ProvisionJob job, ProvisionJobStatus deviceStatus) {
    Carrier carrier = deviceStatus.getDevice().getSubscriber().getCarrier();
    long maxDuration = job.getMaxDuration();
    if (maxDuration <= 0) {
       maxDuration = carrier.getNotificationStateTimeout();
    }
    return maxDuration;
  }

  /**
   * @param job
   * @param carrier
   * @return
   */
  public static long getMaxRetries4Notify(ProvisionJob job, ProvisionJobStatus deviceStatus) {
    Carrier carrier = deviceStatus.getDevice().getSubscriber().getCarrier();
    long maxRetries = job.getMaxRetries();
    if (maxRetries <= 0) {
       maxRetries = carrier.getNotificationMaxNumRetries();
    }
    return maxRetries;
  }

}
