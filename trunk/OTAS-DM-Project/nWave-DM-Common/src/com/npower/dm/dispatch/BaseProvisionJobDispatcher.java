package com.npower.dm.dispatch;

import com.npower.dm.core.DMException;

public abstract class BaseProvisionJobDispatcher implements ProvisionJobDispatcher {

  protected JobNotificationSender jobNotificationSender = null;

  public BaseProvisionJobDispatcher() {
    super();
  }

  public JobNotificationSender getJobNotificationSender() {
    return jobNotificationSender;
  }

  public void setJobNotificationSender(JobNotificationSender sender) {
    this.jobNotificationSender = sender;
  }

  public void dispatch(String jobID) throws DMException {
    this.dispatch(Long.parseLong(jobID));
  }

  public abstract void dispatch(long jobID) throws DMException;

  public abstract void dispatchAll() throws DMException;

}