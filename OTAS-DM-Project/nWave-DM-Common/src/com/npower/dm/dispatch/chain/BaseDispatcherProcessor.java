package com.npower.dm.dispatch.chain;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.dispatch.JobNotificationSender;
import com.npower.dm.dispatch.planner.ScheduleTimePlanner;
import com.npower.dm.management.ManagementBeanFactory;

public abstract class BaseDispatcherProcessor implements DispatcherProcessor {

  private ManagementBeanFactory managementBeanFactory = null;
  private JobNotificationSender jobNotificationSender = null;
  private ScheduleTimePlanner planner = null;
  
  public BaseDispatcherProcessor() {
    super();
  }

  /**
   * @return the managementBeanFactory
   */
  public ManagementBeanFactory getManagementBeanFactory() {
    return managementBeanFactory;
  }

  /**
   * @param managementBeanFactory the managementBeanFactory to set
   */
  public void setManagementBeanFactory(ManagementBeanFactory managementBeanFactory) {
    this.managementBeanFactory = managementBeanFactory;
  }

  /**
   * @return
   */
  public JobNotificationSender getJobNotificationSender() {
    return jobNotificationSender;
  }

  /**
   * @param sender
   */
  public void setJobNotificationSender(JobNotificationSender sender) {
    this.jobNotificationSender = sender;
  }
  
  /**
   * @return the planner
   */
  public ScheduleTimePlanner getPlanner() {
    return planner;
  }

  /**
   * @param planner the planner to set
   */
  public void setPlanner(ScheduleTimePlanner planner) {
    this.planner = planner;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.DispatcherProcessor#isNeedToProcess(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  public abstract boolean isNeedToProcess(ProvisionJob job, Device device) throws DMException ;

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.DispatcherProcessor#isNeedToCancelled(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  //public abstract boolean isNeedToCancelled(ProvisionJob job, Device device) throws DMException;

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.DispatcherProcessor#process(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  public abstract void process(ProvisionJob job, Device device) throws DMException, BreakProcessorChainException;

}