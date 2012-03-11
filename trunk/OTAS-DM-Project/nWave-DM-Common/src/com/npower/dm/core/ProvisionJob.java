/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ProvisionJob.java,v 1.29 2009/02/17 03:38:59 zhao Exp $
 * $Revision: 1.29 $
 * $Date: 2009/02/17 03:38:59 $
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
import java.util.List;
import java.util.Set;


/**
 * Represent a provision job.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.29 $ $Date: 2009/02/17 03:38:59 $
 */
public interface ProvisionJob {
  
  /**
   * Constance: JOB_STATE
   */
  public final static String JOB_STATE_APPLIED      = "Applied";
  public final static String JOB_STATE_DISABLE      = "Disable";
  public final static String JOB_STATE_CANCELLED    = "Cancelled";
  public final static String JOB_STATE_FINISHED     = "Finished";
  
  /**
   * Constance: JOB_ELEMENT_TYPE
   */
  public final static String JOB_ELEMENT_TYPE_SINGLE    = "Single";
  public final static String JOB_ELEMENT_TYPE_MULTIPLE  = "Multiple";
  
  /**
   * Constance: JOB_TYPE
   */
  public final static String JOB_TYPE_DISCOVERY             = "Discovery";
  public final static String JOB_TYPE_ASSIGN_PROFILE        = "Assign";
  public final static String JOB_TYPE_RE_ASSIGN_PROFILE     = "Re-Send";
  public final static String JOB_TYPE_DELETE_PROFILE        = "DeleteProfile";
  public final static String JOB_TYPE_FIRMWARE              = "Firmware";
  public final static String JOB_TYPE_SCRIPT                = "Script";
  public final static String JOB_TYPE_WORKFLOW              = "Workflow";
  public final static String JOB_TYPE_SOFTWARE_INSTALL      = "Software-Install";
  public final static String JOB_TYPE_SOFTWARE_UN_INSTALL   = "Software-Uninstall";
  public final static String JOB_TYPE_SOFTWARE_ACTIVE       = "Software-Active";
  public final static String JOB_TYPE_SOFTWARE_DEACTIVE     = "Software-Deactive";
  public final static String JOB_TYPE_SOFTWARE_UPGRADE      = "Software-Upgrade";
  public final static String JOB_TYPE_SOFTWARE_DISCOVERY    = "Software-Discovery";
  public final static String JOB_TYPE_SOFTWARE_INSTALL_STAGE_2      = "Software-Install-Stage2";
  
  /**
   * Constance: JOB_MODE
   */
  // DM mode 
  public final static String JOB_MODE_DM                    = "dm";
  // OTA CP mode
  public final static String JOB_MODE_CP                    = "cp";
  
  /**
   * Return ID.
   * @return
   */
  public abstract long getID();

  /**
   * Return the job's state:
   * Applied, Disable, Cancelled
   * @return
   */
  public abstract String getState();

  /**
   * Set a state for this job
   * @param state
   */
  public abstract void setState(String state);

  /**
   * Return Job's mode
   * @return
   */
  public abstract String getJobMode();

  /**
   * Set a job's mode
   * @param jobMode
   */
  public abstract void setJobMode(String jobMode);
  /**
   * Return Job's type
   * @return
   */
  public abstract String getJobType();

  /**
   * Set a job's type
   * @param jobType
   */
  public abstract void setJobType(String jobType);

  /**
   * Return the display information for this job's type
   * @return
   */
  public abstract String getJobTypeForDisplay();

  /**
   * Set a display information for this job's type
   * @param jobTypeForDisplay
   */
  public abstract void setJobTypeForDisplay(String jobTypeForDisplay);

  /**
   * Return type of Job, values include:<br/>
   * single<br/>
   * multiple<br/>
   * 
   * @return
   */
  public abstract String getTargetType();
  
  /**
   * Set a type of job, value include:<br/>
   * single<br/>
   * multiple<br/>
   * 
   * @param type
   */
  public abstract void setTargetType(String type);

  /**
   * @return the name
   */
  public abstract String getName();

  /**
   * @param name the name to set
   */
  public abstract void setName(String name);

  /**
   * Return the description of this job.
   * 
   * @return
   */
  public abstract String getDescription();

  /**
   * Set a description of this job.
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * Return true, if this Job is running.
   * @return
   */
  public abstract boolean isRunning();

  /**
   * Set true, if this job is running and session is actived, and untill end of session in DMProcessor.
   * @param running
   */
  public abstract void setRunning(boolean running);

  /**
   * Return true, if this Job is finished.
   * @return
   */
  public abstract boolean isFinished() throws DMException;

  /**
   * Return the time of schedule.
   * Untill perior this time, otherwise the job could not been run.
   * @return
   */
  public abstract Date getScheduledTime();

  /**
   * Set a schedule for this Job.
   * @param scheduledTime
   */
  public abstract void setScheduledTime(Date scheduledTime);
  
  /**
   * Return the time of expired time.
   * 到达此时间后, 如果任务没有运行完成, 任务将被撤销
   * @return
   */
  public abstract Date getExpiredTime();

  /**
   * Set expired time for this Job.
   * @param scheduledTime
   */
  public abstract void setExpiredTime(Date time);
  
  /**
   * If the job's type is discovery, return a array of nodes which will be a root node for discovery job.
   * 
   * @return
   */
  public abstract String[] getNodes4Discovery();
  
  /**
   * When the Job's type is Command, return the script.
   * @return
   */
  public abstract String getScriptString() throws DMException;
  
  /**
   * When the job's type is assignment, return the ProfileAssignments
   * @return
   * @throws DMException
   */
  public abstract List<ProfileAssignment> getProfileAssignments() throws DMException;

  /**
   * Get the all of DeviceStatus from this job.
   * @return
   * @throws DMException
   */
  public abstract List<ProvisionJobStatus> getAllOfProvisionJobStatus() throws DMException;
  
  
  public abstract Image getTargetImage();

  public abstract void setTargetImage(Image image);

  public abstract String getTargetImageDescription();

  public abstract void setTargetImageDescription(String targetImageDescription);

  /**
   * @return Returns the targetSoftwareDescription.
   */
  public String getTargetSoftwareDescription();

  /**
   * @param targetSoftwareDescription The targetSoftwareDescription to set.
   */
  public void setTargetSoftwareDescription(String targetSoftwareDescription);

  /**
   * @return Returns the targetSoftware.
   */
  public Software getTargetSoftware();

  /**
   * @param targetSoftware The targetSoftware to set.
   */
  public void setTargetSoftware(Software targetSoftware);

  /**
   * Return UI mode of notification
   * @return
   */
  public abstract String getUiMode();

  /**
   * Set UI mode of notification
   * @param uiMode
   */
  public abstract void setUiMode(String uiMode);

  /**
   * @return Returns the requiredNotification.
   */
  public boolean isRequiredNotification();

  /**
   * @param requiredNotification The requiredNotification to set.
   */
  public void setRequiredNotification(boolean requiredNotification);

  /**
   * @return
   */
  public abstract long getMaxRetries();

  /**
   * @param maxRetries
   */
  public abstract void setMaxRetries(long maxRetries);

  /**
   * @return
   */
  public abstract long getMaxDuration();

  /**
   * @param maxDuration
   */
  public abstract void setMaxDuration(long maxDuration);

  /**
   * @return
   */
  public abstract long getConcurrentSize();

  /**
   * @param maxDuration
   */
  public abstract void setConcurrentSize(long concurrentSize);

  /**
   * In milliseconds
   * @return
   */
  public abstract long getConcurrentInterval();

  /**
   * In milliseconds
   * @param concurrentInterval
   */
  public abstract void setConcurrentInterval(long concurrentInterval);

  /**
   * @return
   */
  public abstract Date getCreatedTime();

  /**
   * @param createdTime
   */
  public abstract void setCreatedTime(Date createdTime);

  /**
   * @return
   */
  public abstract String getCreatedBy();

  /**
   * @param createdBy
   */
  public abstract void setCreatedBy(String createdBy);

  /**
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * @param lastUpdatedTime
   */
  public abstract void setLastUpdatedTime(Date lastUpdatedTime);

  /**
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * @return
   */
  public abstract Set<ClientProvJobTargetDevice> getOtaTargetDevices();

  /**
   * @param targetDevices
   */
  public abstract void setOtaTargetDevices(Set<ClientProvJobTargetDevice> targetDevices);

  public abstract int getPriority();

  public abstract void setPriority(int priority);

  /**
   * @return the parent
   */
  public ProvisionJob getParent();

  /**
   * @param parent the parent to set
   */
  public void setParent(ProvisionJob parent);

  /**
   * @return the children
   */
  public Set<ProvisionJob> getChildren();

  /**
   * @param children the children to set
   */
  public void setChildren(Set<ProvisionJob> children);

  /*
  public abstract boolean getAskPermissionOnTrigger();

  public abstract void setAskPermissionOnTrigger(boolean askPermissionOnTrigger);

  public abstract double getNotificationStartTime();

  public abstract void setNotificationStartTime(double notificationStartTime);

  public abstract double getNotificationEndTime();

  public abstract void setNotificationEndTime(double notificationEndTime);

  public abstract long getAskCount();

  public abstract void setAskCount(long askCount);

  public abstract long getAskInterval();

  public abstract void setAskInterval(long askInterval);

  public abstract boolean getAskBeforeDown();

  public abstract void setAskBeforeDown(boolean askBeforeDown);

  public abstract boolean getAskBeforeApply();

  public abstract void setAskBeforeApply(boolean askBeforeApply);

  public abstract void setScript(Clob script);

  public abstract Set getJobStates();

  public abstract void setJobStates(Set nwDmJobStates);

  public abstract Set getDiscoveryJobNodes();

  public abstract void setDiscoveryJobNodes(Set nwDmDiscoveryJobNodes);

  public abstract void setProvisionElements(Set nwDmPrElements);
  */

  public abstract long getChangeVersion();
  
  public abstract boolean isPrompt4Beginning();
  
  public abstract void setPrompt4Beginning(boolean isPrompt4Beginning);
  
  public abstract String getPromptType4Beginning();
  
  public abstract void setPromptType4Beginning(String promptType4Beginning);
  
  public abstract String getPromptText4Beginning();
  
  public abstract void setPromptText4Beginning(String promptText4Beginning);
  
  public abstract boolean isPrompt4Finished();
  
  public abstract void setPrompt4Finished(boolean isPrompt4Finished);
  
  public abstract String getPromptType4Finished();
  
  public abstract void setPromptType4Finished(String promptType4Finished);
  
  public abstract String getPromptText4Finished();
  
  public abstract void setPromptText4Finished(String promptText4Finished);

}