/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProvisionRequest.java,v 1.25 2009/02/17 03:38:59 zhao Exp $
 * $Revision: 1.25 $
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
package com.npower.dm.hibernate.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.ClientProvJobTargetDevice;
import com.npower.dm.core.Image;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Software;
import com.npower.wap.omacp.notification.UIMode;

/**
 *
 * @author Zhao DongLu
 * @version $Revision: 1.25 $ $Date: 2009/02/17 03:38:59 $
 */
public abstract class AbstractProvisionRequest implements java.io.Serializable, ProvisionJob, Comparable<ProvisionJob> {

  // Fields

  private long ID;

  private UpdateWorkflow updateWorkflow = null;

  private Image targetImage = null;

  private String targetImageDescription = null;

  private String targetSoftwareDescription = null;

  private String targetType = ProvisionJob.JOB_ELEMENT_TYPE_SINGLE;

  private String description = null;
  
  private String name = null;

  private boolean running = true;

  private String state = ProvisionJob.JOB_STATE_APPLIED;

  private Date scheduledTime = new Date();
  
  /**
   * Default value
   */
  private long concurrentSize = 10;
  
  /**
   * Default Interval
   */
  private long concurrentInterval = 100;

  private String criteria = null;

  private int priority = 5;

  private boolean askPermissionOnTrigger = false;

  private double notificationStartTime = 0;

  /**
   * Hour after scheduledTime, Default 1 day
   */
  private double notificationEndTime = 24;

  private String uiMode = "" + UIMode.USER_INTERACTION.getValue();

  private long askCount = 0;

  private long askInterval = 0;

  private boolean askBeforeDown = false;

  private boolean askBeforeApply = false;

  private long changeVersion = 0;

  private String jobType = null;

  private String jobMode = ProvisionJob.JOB_MODE_DM;

  private String jobTypeForDisplay = null;

  private long commandPackageId = 0;

  private String script = null;

  private String appLanguage = Locale.getDefault().toString();

  private String workflowName = null;
  
  /**
   * Default is true
   */
  private boolean requiredNotification = true;

  /**
   * Default Max Retries is 0
   */
  private long   maxRetries = 0;

  /**
   * Default Max Duration is 0 minutes
   */
  private long   maxDuration = 0;

  private Date   createdTime = new Date();

  private String createdBy;

  private Date   lastUpdatedTime = new Date();

  private String lastUpdatedBy;

  private Software targetSoftware;
  
  private ProvisionJob parent;

  private Set    otaTargetDevices = new HashSet(0);

  private Set jobStates = new HashSet(0);

  private Set deviceLogs = new HashSet(0);

  private Set discoveryJobNodes = new TreeSet();

  private Set provisionPhoneNumbers = new HashSet(0);

  private Set provisionElements = new HashSet(0);
  
  private Set children = new TreeSet();
  
  private boolean prompt4Beginning;
  
  private String promptType4Beginning;
  
  private String promptText4Beginning;
  
  private boolean prompt4Finished;
  
  private String promptType4Finished;
  
  private String promptText4Finished;

  private Date expiredTime = null;

  // Constructors



  /** default constructor */
  public AbstractProvisionRequest() {
    super();
  }

  // Property accessors

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long provReqId) {
    this.ID = provReqId;
  }

  public UpdateWorkflow getUpdateWorkflow() {
    return this.updateWorkflow;
  }

  public void setUpdateWorkflow(UpdateWorkflow nwDmUpdateWorkflow) {
    this.updateWorkflow = nwDmUpdateWorkflow;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getTargetImage()
   */
  public Image getTargetImage() {
    return this.targetImage;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setTargetImage(com.npower.dm.core.Image)
   */
  public void setTargetImage(Image nwDmImage) {
    this.targetImage = nwDmImage;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getType()
   */
  public String getTargetType() {
    return this.targetType;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setType(java.lang.String)
   */
  public void setTargetType(String type) {
    this.targetType = type;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getDescription()
   */
  public String getDescription() {
    /*
    if (this.description == null) {
       this.description = this.appLanguage;
    }
    */
    return this.description;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getRunning()
   */
  public boolean getRunning() {
    return this.running;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setRunning(boolean)
   */
  public void setRunning(boolean running) {
    this.running = running;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getTargetImageDescription()
   */
  public String getTargetImageDescription() {
    return this.targetImageDescription;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setTargetImageDescription(java.lang.String)
   */
  public void setTargetImageDescription(String targetImageDescription) {
    this.targetImageDescription = targetImageDescription;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getState()
   */
  public String getState() {
    return this.state;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setState(java.lang.String)
   */
  public void setState(String state) {
    this.state = state;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getScheduledTime()
   */
  public Date getScheduledTime() {
    return this.scheduledTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setScheduledTime(java.util.Date)
   */
  public void setScheduledTime(Date scheduledTime) {
    this.scheduledTime = scheduledTime;
  }

  public String getCriteria() {
    return this.criteria;
  }

  public void setCriteria(String criteria) {
    this.criteria = criteria;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getPriority()
   */
  public int getPriority() {
    return this.priority;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setPriority(long)
   */
  public void setPriority(int priority) {
    this.priority = priority;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getAskPermissionOnTrigger()
   */
  public boolean getAskPermissionOnTrigger() {
    return this.askPermissionOnTrigger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setAskPermissionOnTrigger(boolean)
   */
  public void setAskPermissionOnTrigger(boolean askPermissionOnTrigger) {
    this.askPermissionOnTrigger = askPermissionOnTrigger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getNotificationStartTime()
   */
  public double getNotificationStartTime() {
    return this.notificationStartTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setNotificationStartTime(double)
   */
  public void setNotificationStartTime(double notificationStartTime) {
    this.notificationStartTime = notificationStartTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getNotificationEndTime()
   */
  public double getNotificationEndTime() {
    return this.notificationEndTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setNotificationEndTime(double)
   */
  public void setNotificationEndTime(double notificationEndTime) {
    this.notificationEndTime = notificationEndTime;
  }

  public String getUiMode() {
    return this.uiMode;
  }

  public void setUiMode(String uiMode) {
    this.uiMode = uiMode;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getAskCount()
   */
  public long getAskCount() {
    return this.askCount;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setAskCount(long)
   */
  public void setAskCount(long askCount) {
    this.askCount = askCount;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getAskInterval()
   */
  public long getAskInterval() {
    return this.askInterval;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setAskInterval(long)
   */
  public void setAskInterval(long askInterval) {
    this.askInterval = askInterval;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getAskBeforeDown()
   */
  public boolean getAskBeforeDown() {
    return this.askBeforeDown;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setAskBeforeDown(boolean)
   */
  public void setAskBeforeDown(boolean askBeforeDown) {
    this.askBeforeDown = askBeforeDown;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getAskBeforeApply()
   */
  public boolean getAskBeforeApply() {
    return this.askBeforeApply;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setAskBeforeApply(boolean)
   */
  public void setAskBeforeApply(boolean askBeforeApply) {
    this.askBeforeApply = askBeforeApply;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getJobType()
   */
  public String getJobType() {
    return this.jobType;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setJobType(java.lang.String)
   */
  public void setJobType(String jobType) {
    this.jobType = jobType;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getJobTypeForDisplay()
   */
  public String getJobTypeForDisplay() {
    return (this.jobTypeForDisplay == null)?this.getJobType():this.jobTypeForDisplay;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setJobTypeForDisplay(java.lang.String)
   */
  public void setJobTypeForDisplay(String jobTypeForDisplay) {
    this.jobTypeForDisplay = jobTypeForDisplay;
  }

  public long getCommandPackageId() {
    return this.commandPackageId;
  }

  public void setCommandPackageId(long commandPackageId) {
    this.commandPackageId = commandPackageId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getScript()
   */
  public String getScript() {
    return this.script;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setScript(java.sql.Clob)
   */
  public void setScript(String script) {
    this.script = script;
  }

  public String getAppLanguage() {
    return this.appLanguage;
  }

  public void setAppLanguage(String appLanguage) {
    this.appLanguage = appLanguage;
  }

  public String getWorkflowName() {
    return this.workflowName;
  }

  public void setWorkflowName(String workflowName) {
    this.workflowName = workflowName;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getJobStates()
   */
  public Set getJobStates() {
    return this.jobStates;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setJobStates(java.util.Set)
   */
  public void setJobStates(Set nwDmJobStates) {
    this.jobStates = nwDmJobStates;
  }

  public Set getDeviceLogs() {
    return this.deviceLogs;
  }

  public void setDeviceLogs(Set nwDmDeviceLogs) {
    this.deviceLogs = nwDmDeviceLogs;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getDiscoveryJobNodes()
   */
  public Set getDiscoveryJobNodes() {
    return this.discoveryJobNodes;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setDiscoveryJobNodes(java.util.Set)
   */
  public void setDiscoveryJobNodes(Set nwDmDiscoveryJobNodes) {
    this.discoveryJobNodes = nwDmDiscoveryJobNodes;
  }

  public Set getProvisionPhoneNumbers() {
    return this.provisionPhoneNumbers;
  }

  public void setProvisionPhoneNumbers(Set nwDmPrPhoneNumbers) {
    this.provisionPhoneNumbers = nwDmPrPhoneNumbers;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#getProvisionElements()
   */
  public Set getProvisionElements() {
    return this.provisionElements;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ProvisionJob#setProvisionElements(java.util.Set)
   */
  public void setProvisionElements(Set nwDmPrElements) {
    this.provisionElements = nwDmPrElements;
  }

  // Implements ProvisionJob methods ***********************************************************************

  /**
   * If the job's type is discovery, return a array of nodes which will be a root node for discovery job.
   *
   * @return
   */
  public String[] getNodes4Discovery() {
    if (!this.getJobType().equalsIgnoreCase(ProvisionJob.JOB_TYPE_DISCOVERY)) {
       return null;
    }
    Set nodeSet = this.getDiscoveryJobNodes();
    String[] result = new String[nodeSet.size()];
    int j = 0;
    for (Iterator i = nodeSet.iterator(); i.hasNext(); ) {
        Node4DiscoveryJob node = (Node4DiscoveryJob)i.next();
        String rootNode = node.getID().getRootNode();
        result[j++] = rootNode;
    }
    return result;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getJobMode()
   */
  public String getJobMode() {
    return jobMode;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#setJobMode(java.lang.String)
   */
  public void setJobMode(String jobMode) {
    this.jobMode = jobMode;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getMaxRetries()
   */
  public long getMaxRetries() {
    return maxRetries;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#setMaxRetries(long)
   */
  public void setMaxRetries(long maxRetries) {
    this.maxRetries = maxRetries;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getMaxDuration()
   */
  public long getMaxDuration() {
    return maxDuration;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#setMaxDuration(long)
   */
  public void setMaxDuration(long maxDuration) {
    this.maxDuration = maxDuration;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getCreatedTime()
   */
  public Date getCreatedTime() {
    return createdTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#setCreatedTime(java.util.Date)
   */
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getCreatedBy()
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#setCreatedBy(java.lang.String)
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    return lastUpdatedTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#setLastUpdatedTime(java.util.Date)
   */
  public void setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getOtaTargetDevices()
   */
  public Set<ClientProvJobTargetDevice> getOtaTargetDevices() {
    return this.otaTargetDevices;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#setOtaTargetDevices(java.util.Set)
   */
  public void setOtaTargetDevices(Set<ClientProvJobTargetDevice> otaTargetDevices) {
    this.otaTargetDevices = otaTargetDevices;
  }

  /**
   * @return Returns the targetSoftwareDescription.
   */
  public String getTargetSoftwareDescription() {
    return targetSoftwareDescription;
  }

  /**
   * @param targetSoftwareDescription The targetSoftwareDescription to set.
   */
  public void setTargetSoftwareDescription(String targetSoftwareDescription) {
    this.targetSoftwareDescription = targetSoftwareDescription;
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
   * @see com.npower.dm.core.ProvisionJob#isRequiredNotification()
   */
  public boolean isRequiredNotification() {
    return requiredNotification;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#setRequiredNotification(boolean)
   */
  public void setRequiredNotification(boolean requiredNotification) {
    this.requiredNotification = requiredNotification;
  }

  /**
   * @return the concurrentSize
   */
  public long getConcurrentSize() {
    return concurrentSize;
  }

  /**
   * @param concurrentSize the concurrentSize to set
   */
  public void setConcurrentSize(long concurrentSize) {
    this.concurrentSize = concurrentSize;
  }

  /**
   * @return the concurrentInterval
   */
  public long getConcurrentInterval() {
    return concurrentInterval;
  }

  /**
   * @param concurrentInterval the concurrentInterval to set
   */
  public void setConcurrentInterval(long concurrentInterval) {
    this.concurrentInterval = concurrentInterval;
  }

  /**
   * @return the parent
   */
  public ProvisionJob getParent() {
    return parent;
  }

  /**
   * @param parent the parent to set
   */
  public void setParent(ProvisionJob parent) {
    this.parent = parent;
  }

  /**
   * @return the children
   */
  public Set getChildren() {
    return children;
  }

  /**
   * @param children the children to set
   */
  public void setChildren(Set children) {
    this.children = children;
  }

  public boolean isPrompt4Beginning() {
    return prompt4Beginning;
  }

  public void setPrompt4Beginning(boolean prompt4Beginning) {
    this.prompt4Beginning = prompt4Beginning;
  }

  public String getPromptType4Beginning() {
    return promptType4Beginning;
  }

  public void setPromptType4Beginning(String promptType4Beginning) {
    this.promptType4Beginning = promptType4Beginning;
  }

  public String getPromptText4Beginning() {
    return promptText4Beginning;
  }

  public void setPromptText4Beginning(String promptText4Beginning) {
    this.promptText4Beginning = promptText4Beginning;
  }

  public boolean isPrompt4Finished() {
    return prompt4Finished;
  }

  public void setPrompt4Finished(boolean prompt4Finished) {
    this.prompt4Finished = prompt4Finished;
  }

  public String getPromptType4Finished() {
    return promptType4Finished;
  }

  public void setPromptType4Finished(String promptType4Finished) {
    this.promptType4Finished = promptType4Finished;
  }

  public String getPromptText4Finished() {
    return promptText4Finished;
  }

  public void setPromptText4Finished(String promptText4Finished) {
    this.promptText4Finished = promptText4Finished;
  }

  /**
   * Return the time of expired time.
   * 到达此时间后, 如果任务没有运行完成, 任务将被撤销
   * @return
   */
  public Date getExpiredTime() {
    return this.expiredTime ;
  }

  /**
   * Set expired time for this Job.
   * @param scheduledTime
   */
  public void setExpiredTime(Date time) {
    this.expiredTime = time;
  }
}
