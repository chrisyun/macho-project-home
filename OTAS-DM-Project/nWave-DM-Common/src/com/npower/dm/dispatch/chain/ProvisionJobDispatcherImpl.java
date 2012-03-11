/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/chain/ProvisionJobDispatcherImpl.java,v 1.4 2009/02/17 03:38:59 zhao Exp $
 * $Revision: 1.4 $
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
package com.npower.dm.dispatch.chain;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.dm.core.ClientProvJobTargetDevice;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.dispatch.BaseProvisionJobDispatcher;
import com.npower.dm.dispatch.planner.SimplePlanner;
import com.npower.dm.hibernate.entity.ProvisionRequest;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SearchBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2009/02/17 03:38:59 $
 */
public class ProvisionJobDispatcherImpl extends BaseProvisionJobDispatcher {

  private static Log log = LogFactory.getLog(ProvisionJobDispatcherImpl.class);

  private DispatcherProcessor dmProcessor = null;

  private DispatcherProcessor cpProcessor = null;

  /**
   * 
   */
  public ProvisionJobDispatcherImpl() {
    super();
    
  }

  /**
   * @return the dmProcessor
   */
  public DispatcherProcessor getDmProcessor() {
    return dmProcessor;
  }

  /**
   * @param dmProcessor the dmProcessor to set
   */
  public void setDmProcessor(DispatcherProcessor processor) {
    this.dmProcessor = processor;
  }
  
  /**
   * @return the cpProcessor
   */
  public DispatcherProcessor getCpProcessor() {
    return cpProcessor;
  }

  /**
   * @param cpProcessor the cpProcessor to set
   */
  public void setCpProcessor(DispatcherProcessor cpProcessor) {
    this.cpProcessor = cpProcessor;
  }

  /**
   * <pre>
   * 处理流程如下：
   * 1、检查是否需要Bootstrap
   * 2、如果需要Bootstrap, 发送Bootstrap报文
   *    a、检查Bootstrap是否已经正常发送, 并达到滞后发送Notification的时间
   *    b、如果满足在Bootstrap之后滞后发送Notification的时间条件, 发送Notification
   * 5、如果无需发送Bootstrap, 检查是否需要发送Notification
   *    a、满足条件, 发送Notification
   * </pre>
   * Dispatch a provision job
   * @param factory
   * @param jobBean
   * @param job
   * @throws DMException
   */
  private void dispatch(ManagementBeanFactory factory, ProvisionJobBean jobBean, ProvisionJob job) throws DMException {
    if (job.getScheduledTime().getTime() > System.currentTimeMillis()) {
      // 未到达调度时间
      return;
    }
    if (ProvisionJob.JOB_MODE_DM.equalsIgnoreCase(job.getJobMode())) {
      DispatcherProcessor currentProcessor = this.dmProcessor;
      // 处理DM模式的任务
      currentProcessor.setJobNotificationSender(this.getJobNotificationSender());
      currentProcessor.setManagementBeanFactory(factory);
      currentProcessor.setPlanner(new SimplePlanner(job));
      for (ProvisionJobStatus deviceStatus: job.getAllOfProvisionJobStatus()) {
          if (currentProcessor.isNeedToProcess(job, deviceStatus.getDevice())) {
             try {
               currentProcessor.process(job, deviceStatus.getDevice());
             } catch (BreakProcessorChainException ex) {
               log.error("Unkown error when dispatch job: " + job.getID() + ", device: " + deviceStatus.getDevice().getExternalId(), ex);
             }
          }
      }
    } else if (ProvisionJob.JOB_MODE_CP.equalsIgnoreCase(job.getJobMode())) {
      DispatcherProcessor currentProcessor = this.cpProcessor;
      // 处理CP模式的任务
      currentProcessor.setJobNotificationSender(this.getJobNotificationSender());
      currentProcessor.setManagementBeanFactory(factory);
      currentProcessor.setPlanner(new SimplePlanner(job));
      for (ClientProvJobTargetDevice deviceStatus: job.getOtaTargetDevices()) {
          DeviceBean deviceBean = factory.createDeviceBean();
          Device device = deviceBean.getDeviceByID(deviceStatus.getDeviceId());
          if (currentProcessor.isNeedToProcess(job, device)) {
             try {
               currentProcessor.process(job, device);
             } catch (BreakProcessorChainException ex) {
               log.error("Unkown error when dispatch job: " + job.getID() + ", device: " + device.getExternalId(), ex);
             }
          }
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.BaseProvisionJobDispatcher#dispatchAll()
   */
  @Override
  public void dispatchAll() throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        
        // All of DM jobs
        {
          List<ProvisionJob> jobs = jobBean.findJobsQueued(true);
          for (ProvisionJob job: jobs) {
              dispatch(factory, jobBean, job);
          }
        }
        // All of CP jobs
        {
          SearchBean searchBean = factory.createSearchBean();
          Criteria mainCrt = searchBean.newCriteriaInstance(ProvisionRequest.class);
          mainCrt.addOrder(Order.desc("scheduledTime"));
          mainCrt.add(Expression.eq("jobMode", ProvisionJob.JOB_MODE_CP));
          mainCrt.add(Expression.eq("state", ProvisionJob.JOB_STATE_APPLIED));
          List<ProvisionJob> jobs = mainCrt.list();
          for (ProvisionJob job: jobs) {
              dispatch(factory, jobBean, job);
          }
        }
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new DMException(ex.getMessage(), ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.BaseProvisionJobDispatcher#dispatch(long)
   */
  @Override
  public void dispatch(long jobID) throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        
        ProvisionJob job = jobBean.loadJobByID(jobID);
        dispatch(factory, jobBean, job);
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new DMException(ex.getMessage(), ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
