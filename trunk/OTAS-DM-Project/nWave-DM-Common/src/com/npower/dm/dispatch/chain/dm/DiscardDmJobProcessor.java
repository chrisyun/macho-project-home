/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/chain/dm/DiscardDmJobProcessor.java,v 1.3 2009/02/17 03:38:59 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2009/02/17 03:38:59 $
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
package com.npower.dm.dispatch.chain.dm;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.dispatch.chain.BaseDispatcherProcessor;
import com.npower.dm.dispatch.chain.BreakProcessorChainException;
import com.npower.dm.dispatch.chain.DispatcherProcessor;
import com.npower.dm.dispatch.chain.DispatcherProcessorHelper;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;

/**
 * 检查设备需要预先Bootstrap, 如果需要提交Bootstrap任务.
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2009/02/17 03:38:59 $
 */
public class DiscardDmJobProcessor extends BaseDispatcherProcessor implements DispatcherProcessor {

  private static Log log = LogFactory.getLog(DiscardDmJobProcessor.class);
  
  /**
   * 
   */
  public DiscardDmJobProcessor() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.DispatcherProcessor#isNeedToProcess(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  /**
   * 返回true, 表示该任务需要被撤销
   */
  public boolean isNeedToProcess(ProvisionJob job, Device device) throws DMException {
    if (!ProvisionJob.JOB_MODE_DM.equalsIgnoreCase(job.getJobMode())) {
       // 不是DM Job
       return false;
    }
    
    // 检查是否到达超期时间
    Date expiredTime = job.getExpiredTime();
    if (expiredTime == null) {
       expiredTime = new Date(job.getScheduledTime().getTime() + 1000 * device.getSubscriber().getCarrier().getDefaultJobExpiredTimeInSeconds());
    }
    if (expiredTime != null && expiredTime.getTime() < System.currentTimeMillis()) {
       // Reach expire time
       log.info("job[" + job.getID() + "] reach expire time, discarded!");
       return true;
    }
    
    long maxRetries4Boot = (job.getMaxRetries() < 1)?device.getSubscriber().getCarrier().getBootstrapMaxRetries():job.getMaxRetries();
    if (device.getBootstrapAskCounter() >= maxRetries4Boot) {
       // 到达最大Bootstrap次数, 放弃Bootstrap
       return true;
    }

    ProvisionJobBean jobBean = this.getManagementBeanFactory().createProvisionJobBean();
    ProvisionJobStatus deviceStatus = jobBean.getProvisionJobStatus(job, device);
    if (deviceStatus != null) {
      long maxRetries4Notify = DispatcherProcessorHelper.getMaxRetries4Notify(job, deviceStatus);
      if (deviceStatus.getAskCount() >= maxRetries4Notify) {
         // 到达最大次数，不发送Notification
         return true;
      }
    }
    return false;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.DispatcherProcessor#process(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  @SuppressWarnings("finally")
  public void process(ProvisionJob job, Device device) throws DMException, BreakProcessorChainException {
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    try {
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJobStatus deviceStatus = jobBean.getProvisionJobStatus(job, device);
        if (deviceStatus != null) {
          log.info("The JobID: " + job.getID() + " for device: " + + deviceStatus.getDevice().getID() + " has been cancelled");
          factory.beginTransaction();
          deviceStatus.setState(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED);
          deviceStatus.setCause("Cancelled by job dispatcher, reach maxium retries!");
          jobBean.update(deviceStatus);
          factory.commit();
        }
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      
      log.error("Failure to discard job:" + job.getID() + " for deviceID: " + device.getID(), ex);
      throw new DMException("Failure to discard job:" + job.getID() + " for deviceID: " + device.getID(), ex);
    } finally {
      // Break Processor Chain
      // Job was canceled, break processor chain.
      throw new BreakProcessorChainException();
    }
  }

}
