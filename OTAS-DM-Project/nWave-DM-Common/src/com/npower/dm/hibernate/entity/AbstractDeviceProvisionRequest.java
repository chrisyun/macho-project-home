/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDeviceProvisionRequest.java,v 1.10 2008/03/08 10:40:52 zhao Exp $
 * $Revision: 1.10 $
 * $Date: 2008/03/08 10:40:52 $
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
package com.npower.dm.hibernate.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.npower.dm.core.Device;
import com.npower.dm.core.Image;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Software;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.10 $ $Date: 2008/03/08 10:40:52 $
 */
public abstract class AbstractDeviceProvisionRequest implements java.io.Serializable, ProvisionJobStatus, Comparable<ProvisionJobStatus> {

  // Fields

  private long ID;

  private Element4Provision provisionElement = null;

  private Image toImage = null;

  private Image installingImage = null;

  private Device device = null;

  private JobState jobState = null;

  private Image oldCurrentImage = null;

  private String state = null;
  
  private String cause = null;

  private boolean readyToNotify = true;

  private long pathIndex = 0;

  private boolean askPermissionOnTrigger = true;

  private Date scheduledTime = null;

  private double notificationStartTime;

  private double notificationEndTime;

  private long rate;

  private String uiMode;
  
  private Date lastNotificationTime = null;

  private long askCount = 0;

  private long askInterval;

  private boolean askBeforeDown;

  private boolean askBeforeApply;

  private Date lastUpdatedTime = null;
  
  private long changeVersion;

  private long pendingDeviceJobIndex;

  private long pendingDeviceId;

  private long workflowEntryId;

  private long currentJobAdapterId;

  private String installationState;
  
  private Software targetSoftware;

  private Set jobExecClients = new HashSet(0);

  private Set devices = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDeviceProvisionRequest() {
  }

  /** full constructor */
  public AbstractDeviceProvisionRequest(Element4Provision element, Device device) {
    this.provisionElement = element;
    this.device = device;
  }

  // Property accessors

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long devProvReqId) {
    this.ID = devProvReqId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getProvisionElement()
   */
  public Element4Provision getProvisionElement() {
    return this.provisionElement;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setProvisionElement(com.npower.dm.hibernate.entity.Element4Provision)
   */
  public void setProvisionElement(Element4Provision nwDmPrElement) {
    this.provisionElement = nwDmPrElement;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getToImage()
   */
  public Image getToImage() {
    return this.toImage;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setToImage(com.npower.dm.core.Image)
   */
  public void setToImage(Image nwDmImageByToImageId) {
    this.toImage = nwDmImageByToImageId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getInstallingImage()
   */
  public Image getInstallingImage() {
    return this.installingImage;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setInstallingImage(com.npower.dm.core.Image)
   */
  public void setInstallingImage(Image nwDmImageByInstallingImage) {
    this.installingImage = nwDmImageByInstallingImage;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getDevice()
   */
  public Device getDevice() {
    return this.device;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setDevice(com.npower.dm.core.Device)
   */
  public void setDevice(Device nwDmDevice) {
    this.device = nwDmDevice;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getJobState()
   */
  public JobState getJobState() {
    return this.jobState;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setJobState(com.npower.dm.hibernate.entity.JobState)
   */
  public void setJobState(JobState nwDmJobState) {
    this.jobState = nwDmJobState;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getOldCurrentImage()
   */
  public Image getOldCurrentImage() {
    return this.oldCurrentImage;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setOldCurrentImage(com.npower.dm.core.Image)
   */
  public void setOldCurrentImage(Image nwDmImageByOldCurrentImageId) {
    this.oldCurrentImage = nwDmImageByOldCurrentImageId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getState()
   */
  public String getState() {
    return this.state;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setState(java.lang.String)
   */
  public void setState(String state) {
    this.state = state;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getReadyToNotify()
   */
  public boolean getReadyToNotify() {
    return this.readyToNotify;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setReadyToNotify(boolean)
   */
  public void setReadyToNotify(boolean readyToNotify) {
    this.readyToNotify = readyToNotify;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getPathIndex()
   */
  public long getPathIndex() {
    return this.pathIndex;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setPathIndex(long)
   */
  public void setPathIndex(long pathIndex) {
    this.pathIndex = pathIndex;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getAskPermissionOnTrigger()
   */
  public boolean getAskPermissionOnTrigger() {
    return this.askPermissionOnTrigger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setAskPermissionOnTrigger(boolean)
   */
  public void setAskPermissionOnTrigger(boolean askPermissionOnTrigger) {
    this.askPermissionOnTrigger = askPermissionOnTrigger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getScheduledTime()
   */
  public Date getScheduledTime() {
    return this.scheduledTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setScheduledTime(java.util.Date)
   */
  public void setScheduledTime(Date scheduledTime) {
    this.scheduledTime = scheduledTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getNotificationStartTime()
   */
  public double getNotificationStartTime() {
    return this.notificationStartTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setNotificationStartTime(double)
   */
  public void setNotificationStartTime(double notificationStartTime) {
    this.notificationStartTime = notificationStartTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getNotificationEndTime()
   */
  public double getNotificationEndTime() {
    return this.notificationEndTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setNotificationEndTime(double)
   */
  public void setNotificationEndTime(double notificationEndTime) {
    this.notificationEndTime = notificationEndTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getRate()
   */
  public long getRate() {
    return this.rate;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setRate(long)
   */
  public void setRate(long rate) {
    this.rate = rate;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getUiMode()
   */
  public String getUiMode() {
    return this.uiMode;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setUiMode(java.lang.String)
   */
  public void setUiMode(String uiMode) {
    this.uiMode = uiMode;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getAskCount()
   */
  public long getAskCount() {
    return this.askCount;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setAskCount(long)
   */
  public void setAskCount(long askCount) {
    this.askCount = askCount;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getAskInterval()
   */
  public long getAskInterval() {
    return this.askInterval;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setAskInterval(long)
   */
  public void setAskInterval(long askInterval) {
    this.askInterval = askInterval;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getAskBeforeDown()
   */
  public boolean getAskBeforeDown() {
    return this.askBeforeDown;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setAskBeforeDown(boolean)
   */
  public void setAskBeforeDown(boolean askBeforeDown) {
    this.askBeforeDown = askBeforeDown;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getAskBeforeApply()
   */
  public boolean getAskBeforeApply() {
    return this.askBeforeApply;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setAskBeforeApply(boolean)
   */
  public void setAskBeforeApply(boolean askBeforeApply) {
    this.askBeforeApply = askBeforeApply;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.changeVersion;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setChangeVersion(long)
   */
  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getPendingDeviceJobIndex()
   */
  public long getPendingDeviceJobIndex() {
    return this.pendingDeviceJobIndex;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setPendingDeviceJobIndex(long)
   */
  public void setPendingDeviceJobIndex(long pendingDeviceJobIndex) {
    this.pendingDeviceJobIndex = pendingDeviceJobIndex;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getPendingDeviceId()
   */
  public long getPendingDeviceId() {
    return this.pendingDeviceId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setPendingDeviceId(long)
   */
  public void setPendingDeviceId(long pendingDeviceId) {
    this.pendingDeviceId = pendingDeviceId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getWorkflowEntryId()
   */
  public long getWorkflowEntryId() {
    return this.workflowEntryId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setWorkflowEntryId(long)
   */
  public void setWorkflowEntryId(long workflowEntryId) {
    this.workflowEntryId = workflowEntryId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getCurrentJobAdapterId()
   */
  public long getCurrentJobAdapterId() {
    return this.currentJobAdapterId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setCurrentJobAdapterId(long)
   */
  public void setCurrentJobAdapterId(long currentJobAdapterId) {
    this.currentJobAdapterId = currentJobAdapterId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getInstallationState()
   */
  public String getInstallationState() {
    return this.installationState;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setInstallationState(java.lang.String)
   */
  public void setInstallationState(String installationState) {
    this.installationState = installationState;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getJobExecClients()
   */
  public Set getJobExecClients() {
    return this.jobExecClients;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setJobExecClients(java.util.Set)
   */
  public void setJobExecClients(Set nwDmDmJobExecClients) {
    this.jobExecClients = nwDmDmJobExecClients;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#getDevices()
   */
  public Set getDevices() {
    return this.devices;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceProvisionRequest#setDevices(java.util.Set)
   */
  public void setDevices(Set nwDmDevices) {
    this.devices = nwDmDevices;
  }

  /**
   * @return the cause information
   */
  public String getCause() {
    return cause;
  }

  /**
   * @param cause 
   *       the cause information to set
   */
  public void setCause(String cause) {
    this.cause = cause;
  }

  /**
   * @return the lastUpdatedTime
   */
  public Date getLastUpdatedTime() {
    return lastUpdatedTime;
  }

  /**
   * @param lastUpdatedTime the lastUpdatedTime to set
   */
  public void setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

  /**
   * @return Returns the targetSoftware.
   */
  public Software getTargetSoftware() {
    return targetSoftware;
  }

  /**
   * @param targetSoftware The targetSoftware to set.
   */
  public void setTargetSoftware(Software targetSoftware) {
    this.targetSoftware = targetSoftware;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJobStatus#getLastNotificationTime()
   */
  public Date getLastNotificationTime() {
    return lastNotificationTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJobStatus#setLastNotificationTime(java.util.Date)
   */
  public void setLastNotificationTime(Date lastNotificationTime) {
    this.lastNotificationTime = lastNotificationTime;
  }

}