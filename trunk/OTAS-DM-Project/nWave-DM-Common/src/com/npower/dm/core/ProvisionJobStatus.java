/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ProvisionJobStatus.java,v 1.14 2008/03/12 05:15:23 zhao Exp $
 * $Revision: 1.14 $
 * $Date: 2008/03/12 05:15:23 $
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
package com.npower.dm.core;

import java.util.Date;
import java.util.Set;

import com.npower.dm.hibernate.entity.Element4Provision;
import com.npower.dm.hibernate.entity.JobExecClient;
import com.npower.dm.hibernate.entity.JobState;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.14 $ $Date: 2008/03/12 05:15:23 $
 */
public interface ProvisionJobStatus {

  /**
   * Constance: DEVICE_JOB_STATE
   */
  public final static String DEVICE_JOB_STATE_READY         = "READY";
  public final static String DEVICE_JOB_STATE_DOING         = "DOING";
  public final static String DEVICE_JOB_STATE_WAITING_CLIENT_INITIALIZED_SESSION         = "WAITING";
  public final static String DEVICE_JOB_STATE_DONE          = "DONE";
  public final static String DEVICE_JOB_STATE_CANCELLED     = "CANCELLED";
  public final static String DEVICE_JOB_STATE_UNKNOWN       = "UNKNOWN";
  public final static String DEVICE_JOB_STATE_ERROR         = "ERROR";
  //public final static String DEVICE_JOB_STATE_MANAGEABLE    = "MANAGEABLE";
  //public final static String DEVICE_JOB_STATE_UNMANAGEABLE  = "UNMANAGEABLE";
  public final static String DEVICE_JOB_STATE_NOTIFIED      = "NOTIFIED";
  
  public abstract long getID();

  public abstract Element4Provision getProvisionElement();

  public abstract void setProvisionElement(Element4Provision nwDmPrElement);

  public abstract Image getToImage();

  public abstract void setToImage(Image image);

  /**
   * @return Returns the targetSoftware.
   */
  public Software getTargetSoftware();

  /**
   * @param targetSoftware The targetSoftware to set.
   */
  public void setTargetSoftware(Software targetSoftware);

  public abstract Image getInstallingImage();

  public abstract void setInstallingImage(Image image);

  public abstract Device getDevice();

  public abstract void setDevice(Device nwDmDevice);

  public abstract JobState getJobState();

  public abstract void setJobState(JobState nwDmJobState);

  public abstract Image getOldCurrentImage();

  public abstract void setOldCurrentImage(Image image);

  public abstract String getState();

  /**
   * @return the cause information
   */
  public abstract String getCause();
  
  /**
   * @param cause 
   *       the cause information to set
   */
  public abstract void setCause(String cause);
  
  public abstract void setState(String state);

  public abstract boolean getReadyToNotify();

  public abstract void setReadyToNotify(boolean readyToNotify);

  public abstract long getPathIndex();

  public abstract void setPathIndex(long pathIndex);

  public abstract boolean getAskPermissionOnTrigger();

  public abstract void setAskPermissionOnTrigger(boolean askPermissionOnTrigger);

  public abstract Date getScheduledTime();

  public abstract void setScheduledTime(Date scheduledTime);

  public abstract double getNotificationStartTime();

  public abstract void setNotificationStartTime(double notificationStartTime);

  public abstract double getNotificationEndTime();

  public abstract void setNotificationEndTime(double notificationEndTime);

  public abstract long getRate();

  public abstract void setRate(long rate);

  public abstract String getUiMode();

  public abstract void setUiMode(String uiMode);

  /**
   * @return Returns the lastNotificationTime.
   */
  public Date getLastNotificationTime();

  /**
   * @param lastNotificationTime The lastNotificationTime to set.
   */
  public void setLastNotificationTime(Date lastNotificationTime);

  public abstract long getAskCount();

  public abstract void setAskCount(long askCount);

  public abstract long getAskInterval();

  public abstract void setAskInterval(long askInterval);

  public abstract boolean getAskBeforeDown();

  public abstract void setAskBeforeDown(boolean askBeforeDown);

  public abstract boolean getAskBeforeApply();

  public abstract void setAskBeforeApply(boolean askBeforeApply);

  public abstract long getPendingDeviceJobIndex();

  public abstract void setPendingDeviceJobIndex(long pendingDeviceJobIndex);

  public abstract long getPendingDeviceId();

  public abstract void setPendingDeviceId(long pendingDeviceId);

  public abstract long getWorkflowEntryId();

  public abstract void setWorkflowEntryId(long workflowEntryId);

  public abstract long getCurrentJobAdapterId();

  public abstract void setCurrentJobAdapterId(long currentJobAdapterId);

  public abstract String getInstallationState();

  public abstract void setInstallationState(String installationState);

  public abstract Set<JobExecClient> getJobExecClients();

  public abstract void setJobExecClients(Set<JobExecClient> jobExecClients);

  public abstract Set<Device> getDevices();

  public abstract void setDevices(Set<Device> devices);
  
  public abstract long getChangeVersion();

  public abstract void setChangeVersion(long changeVersion);

  /**
   * @return the lastUpdatedTime
   */
  public Date getLastUpdatedTime();

  /**
   * @param lastUpdatedTime the lastUpdatedTime to set
   */
  public void setLastUpdatedTime(Date lastUpdatedTime);

  /**
   * The job is finished.
   * @return
   */
  public abstract boolean isFinished();
  
  /**
   * 检测当前任务是否正在运行中. 即设备正在与服务器间发生DM Session
   * @return
   */
  public abstract boolean isDoing();
}