/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/chain/dm/NotificationDispatcherProcessor.java,v 1.3 2008/11/26 08:42:52 zhao Exp $
 * $Revision: 1.3 $
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
import com.npower.dm.core.Subscriber;
import com.npower.dm.dispatch.JobNotificationSender;
import com.npower.dm.dispatch.chain.BaseDispatcherProcessor;
import com.npower.dm.dispatch.chain.BreakProcessorChainException;
import com.npower.dm.dispatch.chain.DispatcherProcessor;
import com.npower.dm.dispatch.chain.DispatcherProcessorHelper;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SubscriberBean;

/**
 * 检查设备需要预先Bootstrap, 如果需要提交Bootstrap任务.
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/11/26 08:42:52 $
 */
public class NotificationDispatcherProcessor extends BaseDispatcherProcessor implements DispatcherProcessor {

  private static Log log = LogFactory.getLog(NotificationDispatcherProcessor.class);
  /**
   * 
   */
  public NotificationDispatcherProcessor() {
    super();
  }

  // Public methods ---------------------------------------------------------------------
  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.DispatcherProcessor#isNeedToProcess(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  public boolean isNeedToProcess(ProvisionJob job, Device device) throws DMException {
    if (!ProvisionJob.JOB_MODE_DM.equalsIgnoreCase(job.getJobMode())) {
       // 不是DM Job
       return false;
    }
    if (!device.isBooted() || !job.isRequiredNotification()) {
       // 设备没有boot, 无需Notification; 
       // 或者到达最大极限，设备需要被取消
       // 或者任务不要求Notification
       return false;
    }
   
    // 设备已经Boot, 继续后续检查
    ProvisionJobBean jobBean = this.getManagementBeanFactory().createProvisionJobBean();
    ProvisionJobStatus deviceStatus = jobBean.getProvisionJobStatus(job, device);
    if (deviceStatus == null) {
       return false;
    }
    if (deviceStatus.isFinished()) {
       // 任务已完成, 无需Notification
       return false;
    }
    if (deviceStatus.isDoing()) {
       // 任务正在进行中, 无需Notification
       return false;
    }

    // 检查是否到达间隔时间
    long maxDuration = DispatcherProcessorHelper.getMaxDuration4Notify(job, deviceStatus);
    Date lastNotificationTime = device.getSubscriber().getNotificationTime();
    //lastNotificationTime = deviceStatus.getLastNotificationTime();
    if (lastNotificationTime != null &&
       lastNotificationTime.getTime() + maxDuration * 1000 > System.currentTimeMillis()) {
       // 未到达间隔时间, 不发送
       return false; 
    }
   
    return true;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.DispatcherProcessor#process(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  @SuppressWarnings("finally")
  public void process(ProvisionJob job, Device device) throws DMException, BreakProcessorChainException {
    Date notificationTime = this.getPlanner().getNextScheduleTime(new Date(), device);
    
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    ProvisionJobStatus deviceStatus = null;
    try {
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        deviceStatus = jobBean.getProvisionJobStatus(job, device);
        
        if (deviceStatus != null) {
          log.info("Dispatch notification (retry#" + deviceStatus.getAskCount() + 
                    ") for device ID: " + device.getID() + " JobID: " + job.getID());
          // Immediately Update device status
          factory.beginTransaction();
          deviceStatus.setLastNotificationTime(notificationTime);
          deviceStatus.setAskCount(deviceStatus.getAskCount() + 1);
          jobBean.update(deviceStatus);
          // 为了防止dispatchAll()对一个电话号码有多个任务的情况, 在不受间隔时间的控制下重复多次发送Notification, 特在subscriber中记录最后一次发送Notification的时间 
          Subscriber subscriber = deviceStatus.getDevice().getSubscriber();
          subscriber.setNotificationTime(notificationTime);
          subscriberBean.update(subscriber);
          factory.commit();
  
          // Dispatch Notification
          JobNotificationSender jobNotificationDispacher = this.getJobNotificationSender();
          jobNotificationDispacher.notify(job.getID(), deviceStatus.getID(), device.getID(), notificationTime.getTime());
        }
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      log.error("Failure to dispatch notification (retry#" + deviceStatus.getAskCount() + 
                ") for device ID: " + device.getID() + " JobID: " + job.getID(), ex);
    } finally {
      // Break Processor Chain
      throw new BreakProcessorChainException();
    }
  }

}
