/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractJobState.java,v 1.4 2006/06/23 11:34:53 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2006/06/23 11:34:53 $
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

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.Device;
import com.npower.dm.core.Image;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2006/06/23 11:34:53 $
 */
public abstract class AbstractJobState implements java.io.Serializable {

  // Fields

  private long ID;

  private Image toImage;

  private Image installingImage;

  private JobAdapter jobAdapter;

  private DMCommandPackage commandPackage;

  private ProvisionRequest provisionRequest;

  private Device device;

  private Image oldCurrentImage;

  private String jobStateType;

  private long workflowEntryId;

  private long askInterval;

  private long askCount;

  private boolean askBeforeApply;

  private boolean askBeforeDown;

  private long pathIndex;

  private String installationState;

  private String workflowState;

  private String uiMode;

  private boolean askUser;

  private Set deviceProvisionRequests = new HashSet(0);

  private Set nodesToDiscovers = new HashSet(0);

  private Set jobAssignmentses = new TreeSet();

  private Set jobAdapters = new HashSet(0);

  private Set jobExecClients = new HashSet(0);

  private Set jobUpdatePaths = new HashSet(0);

  private Set discJobStNodes = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractJobState() {
  }

  /** minimal constructor */
  public AbstractJobState(String jobStateType) {
    this.jobStateType = jobStateType;
  }

  /** full constructor */
  public AbstractJobState(Image nwDmImageByToImageId, Image nwDmImageByInstallingImage, JobAdapter nwDmDmJobAdapter,
      DMCommandPackage nwDmDmCmdPkg, ProvisionRequest nwDmProvReq, Device nwDmDevice,
      Image nwDmImageByOldCurrentImageId, String jobStateType, long workflowEntryId, long askInterval, long askCount,
      boolean askBeforeApply, boolean askBeforeDown, long pathIndex, String installationState, String workflowState,
      String uiMode, boolean askUser, Set nwDmDeviceProvReqs, Set nwDmNodesToDiscovers, Set nwDmJobAssignmentses,
      Set nwDmDmJobAdapters, Set nwDmDmJobExecClients, Set nwDmFwJobUpdatePaths, Set nwDmDiscJobStNodes) {
    this.toImage = nwDmImageByToImageId;
    this.installingImage = nwDmImageByInstallingImage;
    this.jobAdapter = nwDmDmJobAdapter;
    this.commandPackage = nwDmDmCmdPkg;
    this.provisionRequest = nwDmProvReq;
    this.device = nwDmDevice;
    this.oldCurrentImage = nwDmImageByOldCurrentImageId;
    this.jobStateType = jobStateType;
    this.workflowEntryId = workflowEntryId;
    this.askInterval = askInterval;
    this.askCount = askCount;
    this.askBeforeApply = askBeforeApply;
    this.askBeforeDown = askBeforeDown;
    this.pathIndex = pathIndex;
    this.installationState = installationState;
    this.workflowState = workflowState;
    this.uiMode = uiMode;
    this.askUser = askUser;
    this.deviceProvisionRequests = nwDmDeviceProvReqs;
    this.nodesToDiscovers = nwDmNodesToDiscovers;
    this.jobAssignmentses = nwDmJobAssignmentses;
    this.jobAdapters = nwDmDmJobAdapters;
    this.jobExecClients = nwDmDmJobExecClients;
    this.jobUpdatePaths = nwDmFwJobUpdatePaths;
    this.discJobStNodes = nwDmDiscJobStNodes;
  }

  // Property accessors

  public long getID() {
    return this.ID;
  }

  public void setID(long jobStateId) {
    this.ID = jobStateId;
  }

  public Image getToImage() {
    return this.toImage;
  }

  public void setToImage(Image nwDmImageByToImageId) {
    this.toImage = nwDmImageByToImageId;
  }

  public Image getInstallingImage() {
    return this.installingImage;
  }

  public void setInstallingImage(Image nwDmImageByInstallingImage) {
    this.installingImage = nwDmImageByInstallingImage;
  }

  public JobAdapter getJobAdapter() {
    return this.jobAdapter;
  }

  public void setJobAdapter(JobAdapter nwDmDmJobAdapter) {
    this.jobAdapter = nwDmDmJobAdapter;
  }

  public DMCommandPackage getCommandPackage() {
    return this.commandPackage;
  }

  public void setCommandPackage(DMCommandPackage nwDmDmCmdPkg) {
    this.commandPackage = nwDmDmCmdPkg;
  }

  public ProvisionRequest getProvisionRequest() {
    return this.provisionRequest;
  }

  public void setProvisionRequest(ProvisionRequest nwDmProvReq) {
    this.provisionRequest = nwDmProvReq;
  }

  public Device getDevice() {
    return this.device;
  }

  public void setDevice(Device nwDmDevice) {
    this.device = nwDmDevice;
  }

  public Image getOldCurrentImage() {
    return this.oldCurrentImage;
  }

  public void setOldCurrentImage(Image nwDmImageByOldCurrentImageId) {
    this.oldCurrentImage = nwDmImageByOldCurrentImageId;
  }

  public String getJobStateType() {
    return this.jobStateType;
  }

  public void setJobStateType(String jobStateType) {
    this.jobStateType = jobStateType;
  }

  public long getWorkflowEntryId() {
    return this.workflowEntryId;
  }

  public void setWorkflowEntryId(long workflowEntryId) {
    this.workflowEntryId = workflowEntryId;
  }

  public long getAskInterval() {
    return this.askInterval;
  }

  public void setAskInterval(long askInterval) {
    this.askInterval = askInterval;
  }

  public long getAskCount() {
    return this.askCount;
  }

  public void setAskCount(long askCount) {
    this.askCount = askCount;
  }

  public boolean getAskBeforeApply() {
    return this.askBeforeApply;
  }

  public void setAskBeforeApply(boolean askBeforeApply) {
    this.askBeforeApply = askBeforeApply;
  }

  public boolean getAskBeforeDown() {
    return this.askBeforeDown;
  }

  public void setAskBeforeDown(boolean askBeforeDown) {
    this.askBeforeDown = askBeforeDown;
  }

  public long getPathIndex() {
    return this.pathIndex;
  }

  public void setPathIndex(long pathIndex) {
    this.pathIndex = pathIndex;
  }

  public String getInstallationState() {
    return this.installationState;
  }

  public void setInstallationState(String installationState) {
    this.installationState = installationState;
  }

  public String getWorkflowState() {
    return this.workflowState;
  }

  public void setWorkflowState(String workflowState) {
    this.workflowState = workflowState;
  }

  public String getUiMode() {
    return this.uiMode;
  }

  public void setUiMode(String uiMode) {
    this.uiMode = uiMode;
  }

  public boolean getAskUser() {
    return this.askUser;
  }

  public void setAskUser(boolean askUser) {
    this.askUser = askUser;
  }

  public Set getDeviceProvisionRequests() {
    return this.deviceProvisionRequests;
  }

  public void setDeviceProvisionRequests(Set nwDmDeviceProvReqs) {
    this.deviceProvisionRequests = nwDmDeviceProvReqs;
  }

  public Set getNodesToDiscovers() {
    return this.nodesToDiscovers;
  }

  public void setNodesToDiscovers(Set nwDmNodesToDiscovers) {
    this.nodesToDiscovers = nwDmNodesToDiscovers;
  }

  public Set getJobAssignmentses() {
    return this.jobAssignmentses;
  }

  public void setJobAssignmentses(Set nwDmJobAssignmentses) {
    this.jobAssignmentses = nwDmJobAssignmentses;
  }

  public Set getJobAdapters() {
    return this.jobAdapters;
  }

  public void setJobAdapters(Set nwDmDmJobAdapters) {
    this.jobAdapters = nwDmDmJobAdapters;
  }

  public Set getJobExecClients() {
    return this.jobExecClients;
  }

  public void setJobExecClients(Set nwDmDmJobExecClients) {
    this.jobExecClients = nwDmDmJobExecClients;
  }

  public Set getJobUpdatePaths() {
    return this.jobUpdatePaths;
  }

  public void setJobUpdatePaths(Set nwDmFwJobUpdatePaths) {
    this.jobUpdatePaths = nwDmFwJobUpdatePaths;
  }

  public Set getDiscJobStNodes() {
    return this.discJobStNodes;
  }

  public void setDiscJobStNodes(Set nwDmDiscJobStNodes) {
    this.discJobStNodes = nwDmDiscJobStNodes;
  }

}