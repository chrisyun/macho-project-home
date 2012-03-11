/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/chain/DispatcherProcessor.java,v 1.2 2008/07/26 05:03:51 zhao Exp $
 * $Revision: 1.2 $
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
package com.npower.dm.dispatch.chain;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.dispatch.JobNotificationSender;
import com.npower.dm.dispatch.planner.ScheduleTimePlanner;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/07/26 05:03:51 $
 */
public interface DispatcherProcessor {
  
  /**
   * @param managementBeanFactory
   */
  public abstract void setManagementBeanFactory(ManagementBeanFactory managementBeanFactory);
  
  /**
   * @param sender
   */
  public abstract void setJobNotificationSender(JobNotificationSender sender);
  
  /**
   * 是否需要被当前的处理器处理.
   * @param job
   * @param device
   * @return
   * @throws DMException
   */
  public abstract boolean isNeedToProcess(ProvisionJob job, Device device) throws DMException;
  
  /**
   * 是否需要被取消.
   * @param job
   * @param device
   * @return
   * @throws DMException
   */
  //public abstract boolean isNeedToCancelled(ProvisionJob job, Device device) throws DMException;
  
  /**
   * 处理任务
   * @param job
   * @param device
   * @throws BreakProcessorChainException
   *         抛出后表示不需要ProcessorChain中后续的Processor处理
   * @throws DMException
   */
  public abstract void process(ProvisionJob job, Device device) throws BreakProcessorChainException, DMException;

  /**
   * @param planner
   */
  public abstract void setPlanner(ScheduleTimePlanner planner);

}
