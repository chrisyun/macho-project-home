/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractJobAdapter.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2006/04/25 16:31:09 $
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

import com.npower.dm.core.Device;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractJobAdapter implements java.io.Serializable {

  // Fields

  private long jobAdapterId;

  private JobExecClient nwDmDmJobExecClient;

  private Device nwDmDevice;

  private JobState nwDmJobState;

  private long changeVersion;

  private long nextProfile;

  private String jobAdapterType;

  private Set nwDmJobStates = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractJobAdapter() {
  }

  /** minimal constructor */
  public AbstractJobAdapter(long jobAdapterId) {
    this.jobAdapterId = jobAdapterId;
  }

  /** full constructor */
  public AbstractJobAdapter(long jobAdapterId, JobExecClient nwDmDmJobExecClient, Device nwDmDevice,
      JobState nwDmJobState, long changeVersion, long nextProfile, String jobAdapterType, Set nwDmJobStates) {
    this.jobAdapterId = jobAdapterId;
    this.nwDmDmJobExecClient = nwDmDmJobExecClient;
    this.nwDmDevice = nwDmDevice;
    this.nwDmJobState = nwDmJobState;
    this.changeVersion = changeVersion;
    this.nextProfile = nextProfile;
    this.jobAdapterType = jobAdapterType;
    this.nwDmJobStates = nwDmJobStates;
  }

  // Property accessors

  public long getJobAdapterId() {
    return this.jobAdapterId;
  }

  public void setJobAdapterId(long jobAdapterId) {
    this.jobAdapterId = jobAdapterId;
  }

  public JobExecClient getNwDmDmJobExecClient() {
    return this.nwDmDmJobExecClient;
  }

  public void setNwDmDmJobExecClient(JobExecClient nwDmDmJobExecClient) {
    this.nwDmDmJobExecClient = nwDmDmJobExecClient;
  }

  public Device getNwDmDevice() {
    return this.nwDmDevice;
  }

  public void setNwDmDevice(Device nwDmDevice) {
    this.nwDmDevice = nwDmDevice;
  }

  public JobState getNwDmJobState() {
    return this.nwDmJobState;
  }

  public void setNwDmJobState(JobState nwDmJobState) {
    this.nwDmJobState = nwDmJobState;
  }

  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  public long getNextProfile() {
    return this.nextProfile;
  }

  public void setNextProfile(long nextProfile) {
    this.nextProfile = nextProfile;
  }

  public String getJobAdapterType() {
    return this.jobAdapterType;
  }

  public void setJobAdapterType(String jobAdapterType) {
    this.jobAdapterType = jobAdapterType;
  }

  public Set getNwDmJobStates() {
    return this.nwDmJobStates;
  }

  public void setNwDmJobStates(Set nwDmJobStates) {
    this.nwDmJobStates = nwDmJobStates;
  }

}