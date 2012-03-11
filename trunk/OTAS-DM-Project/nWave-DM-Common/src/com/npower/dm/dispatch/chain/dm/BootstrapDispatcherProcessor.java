/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/chain/dm/BootstrapDispatcherProcessor.java,v 1.2 2008/11/26 08:42:52 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/26 08:42:52 $
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
import com.npower.dm.dispatch.JobNotificationSender;
import com.npower.dm.dispatch.chain.BaseDispatcherProcessor;
import com.npower.dm.dispatch.chain.BreakProcessorChainException;
import com.npower.dm.dispatch.chain.DispatcherProcessor;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;

/**
 * 检查设备需要预先Bootstrap, 如果需要提交Bootstrap任务.
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/11/26 08:42:52 $
 */
public class BootstrapDispatcherProcessor extends BaseDispatcherProcessor implements DispatcherProcessor {

  private static Log log = LogFactory.getLog(BootstrapDispatcherProcessor.class);
  /**
   * 
   */
  public BootstrapDispatcherProcessor() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.DispatcherProcessor#isNeedToProcess(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  public boolean isNeedToProcess(ProvisionJob job, Device device) throws DMException {
    if (!ProvisionJob.JOB_MODE_DM.equalsIgnoreCase(job.getJobMode())) {
       // 不是DM Job
       return false;
    }
    if (device.isBooted()) {
       // 已经Boot或满足取消条件
       return false;
    }

    // 还没有Boot
    long maxDuration = (job.getMaxDuration() < 1)?device.getSubscriber().getCarrier().getBootstrapTimeout():job.getMaxDuration();
    if (device.getLastBootstrapTime() != null &&
        device.getLastBootstrapTime().getTime() + maxDuration * 1000 > System.currentTimeMillis()) {
       return false; 
    }
    return true;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.DispatcherProcessor#process(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  @SuppressWarnings("finally")
  public void process(ProvisionJob job, Device device) throws DMException, BreakProcessorChainException {
    Date bootstrapTime = this.getPlanner().getNextScheduleTime(new Date(), device);
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    try {
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJobStatus deviceStatus = jobBean.getProvisionJobStatus(job, device);
        if (deviceStatus != null) {
          // Immediately Update device status
          DeviceBean deviceBean = factory.createDeviceBean();
          factory.beginTransaction();
          device.setLastBootstrapTime(bootstrapTime);
          device.setBootstrapAskCounter(device.getBootstrapAskCounter() + 1);
          deviceBean.update(device);
          factory.commit();
    
          log.info("Dispatch bootstrap (retry#" + device.getBootstrapAskCounter() + 
                   ") for deviceID: " + device.getID()  + " JobID: " + job.getID());
          // Dispatch Bootstrap message
          JobNotificationSender jobSender = this.getJobNotificationSender();
          long maxRetries = (job.getMaxRetries() < 1)?device.getSubscriber().getCarrier().getBootstrapMaxRetries():job.getMaxRetries();
          long maxDuration = (job.getMaxDuration() < 1)?device.getSubscriber().getCarrier().getBootstrapTimeout():job.getMaxDuration();
          jobSender.bootstrap(job.getID(), deviceStatus.getID(), device.getID(), bootstrapTime.getTime(), maxRetries, maxDuration * 1000);
        }
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      
      log.error("Failure to dispatch boostrap (retry#" + device.getBootstrapAskCounter() + 
                  ") for deviceID: " + device.getID()  + " JobID: " + job.getID(), ex);
    } finally {
      // Break Processor Chain
      throw new BreakProcessorChainException();
    }
  }

}
