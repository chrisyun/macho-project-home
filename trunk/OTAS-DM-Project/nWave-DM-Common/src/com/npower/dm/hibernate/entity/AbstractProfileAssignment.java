/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProfileAssignment.java,v 1.3 2008/12/10 05:24:20 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/12/10 05:24:20 $
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
import java.util.TreeSet;

import com.npower.dm.core.Device;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileConfig;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/12/10 05:24:20 $
 */
public abstract class AbstractProfileAssignment implements java.io.Serializable, ProfileAssignment {

  // Fields

  private long ID;

  private ProfileConfig profileConfig;

  private Device device;

  private String profileRootNodePath;

  /**
   * Init status : pending
   */
  private String status = ProfileAssignment.STATUS_PENDING;;

  /**
   * Order of assignment
   */
  private long assignmentIndex;

  private Date lastSentToDevice;

  private String lastUpdatedBy;

  private Date lastUpdatedTime;

  private long changeVersion;

  private Set<ProfileParameterEntity> profileParameters = new HashSet<ProfileParameterEntity>(0);

  private Set<ProfileAssignmentValue> profileAssignValues = new TreeSet<ProfileAssignmentValue>();

  private Set<JobAssignments> jobAssignmentses = new HashSet<JobAssignments>(0);

  // Constructors

  /** default constructor */
  public AbstractProfileAssignment() {
    super();
  }

  /** minimal constructor */
  public AbstractProfileAssignment(ProfileConfig config, Device device) {
    this.profileConfig = config;
    this.device = device;
  }

  /** full constructor */
  public AbstractProfileAssignment(ProfileConfig profileConfig, Device device, String profileRootNodePath,
      String status, Date lastSentToDevice, String lastUpdatedBy, Date lastUpdatedTime, long assignmentIndex) {
    this.profileConfig = profileConfig;
    this.device = device;
    this.profileRootNodePath = profileRootNodePath;
    this.status = status;
    this.lastSentToDevice = lastSentToDevice;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedTime = lastUpdatedTime;
    this.assignmentIndex = assignmentIndex;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long profileAssignmentId) {
    this.ID = profileAssignmentId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getProfileConfig()
   */
  public ProfileConfig getProfileConfig() {
    return this.profileConfig;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#setProfileConfig(com.npower.dm.hibernate.ProfileConfig)
   */
  public void setProfileConfig(ProfileConfig nwDmProfileConfig) {
    this.profileConfig = nwDmProfileConfig;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getDevice()
   */
  public Device getDevice() {
    return this.device;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#setDevice(com.npower.dm.hibernate.Device)
   */
  public void setDevice(Device nwDmDevice) {
    this.device = nwDmDevice;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getProfileRootNodePath()
   */
  public String getProfileRootNodePath() {
    return this.profileRootNodePath;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#setProfileRootNodePath(java.lang.String)
   */
  public void setProfileRootNodePath(String profileRootNodePath) {
    this.profileRootNodePath = profileRootNodePath;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getStatus()
   */
  public String getStatus() {
    return this.status;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#setStatus(java.lang.String)
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getLastSentToDevice()
   */
  public Date getLastSentToDevice() {
    return this.lastSentToDevice;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#setLastSentToDevice(java.util.Date)
   */
  public void setLastSentToDevice(Date lastSentToDevice) {
    this.lastSentToDevice = lastSentToDevice;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    return this.lastUpdatedTime;
  }

  public void setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getAssignmentIndex()
   */
  public long getAssignmentIndex() {
    return this.assignmentIndex;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#setAssignmentIndex(long)
   */
  public void setAssignmentIndex(long assignmentIndex) {
    this.assignmentIndex = assignmentIndex;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getProfileParameters()
   */
  public Set<ProfileParameterEntity> getProfileParameters() {
    return this.profileParameters;
  }

  public void setProfileParameters(Set<ProfileParameterEntity> parameters) {
    this.profileParameters = parameters;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getProfileAssignValues()
   */
  public Set<ProfileAssignmentValue> getProfileAssignValues() {
    return this.profileAssignValues;
  }

  public void setProfileAssignValues(Set<ProfileAssignmentValue> values) {
    this.profileAssignValues = values;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileAssignment#getJobAssignmentses()
   */
  public Set<JobAssignments> getJobAssignmentses() {
    return this.jobAssignmentses;
  }

  public void setJobAssignmentses(Set<JobAssignments> jobAssignmentses) {
    this.jobAssignmentses = jobAssignmentses;
  }

}