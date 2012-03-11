/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/planner/SimplePlanner.java,v 1.1 2008/07/26 05:03:51 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/07/26 05:03:51 $
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
package com.npower.dm.dispatch.planner;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/07/26 05:03:51 $
 */
public class SimplePlanner implements ScheduleTimePlanner {
  
  private static Log log = LogFactory.getLog(SimplePlanner.class);

  private long currentNumberOfDevices = 0;
  private ProvisionJob provisionJob = null; 
  /**
   * 
   */
  public SimplePlanner() {
    super();
  }

  public SimplePlanner(ProvisionJob job) {
    super();
    this.provisionJob = job;
  }

  /**
   * @return the provisionJob
   */
  public ProvisionJob getProvisionJob() {
    return provisionJob;
  }

  /**
   * @param provisionJob the provisionJob to set
   */
  public void setProvisionJob(ProvisionJob job) {
    this.provisionJob = job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.planner.ScheduleTimePlanner#getNextScheduleTime(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  public Date getNextScheduleTime(Date startTime, Device device) {
    this.currentNumberOfDevices++;
    long concurrentSize = provisionJob.getConcurrentSize();
    long concurrentInterval = provisionJob.getConcurrentInterval();
    
    long delay = (this.currentNumberOfDevices) / concurrentSize * concurrentInterval;
    if (log.isDebugEnabled()) {
       log.debug("Planner:delay: " + delay + "ms, jobid: " + this.provisionJob.getID() + ", device#" + this.currentNumberOfDevices + ": " + device.getExternalId());
    }
    return new Date(startTime.getTime() + delay);
  }

}
