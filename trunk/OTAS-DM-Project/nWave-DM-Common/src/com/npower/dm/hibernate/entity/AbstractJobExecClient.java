/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractJobExecClient.java,v 1.4 2006/04/30 10:52:38 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2006/04/30 10:52:38 $
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

import com.npower.dm.core.ProvisionJobStatus;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2006/04/30 10:52:38 $
 */
public abstract class AbstractJobExecClient implements java.io.Serializable {

  // Fields

  private long jobExecClientId;

  private JobState nwDmJobState;

  private ProvisionJobStatus nwDmDeviceProvReq;

  private String jobClientType;

  private String actionId;

  private String state;

  private Set nwDmDmJobAdapters = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractJobExecClient() {
  }

  /** minimal constructor */
  public AbstractJobExecClient(String jobClientType) {
    this.jobClientType = jobClientType;
  }

  /** full constructor */
  public AbstractJobExecClient(JobState nwDmJobState, ProvisionJobStatus nwDmDeviceProvReq, String jobClientType,
      String actionId, String state, Set nwDmDmJobAdapters) {
    this.nwDmJobState = nwDmJobState;
    this.nwDmDeviceProvReq = nwDmDeviceProvReq;
    this.jobClientType = jobClientType;
    this.actionId = actionId;
    this.state = state;
    this.nwDmDmJobAdapters = nwDmDmJobAdapters;
  }

  // Property accessors

  public long getJobExecClientId() {
    return this.jobExecClientId;
  }

  public void setJobExecClientId(long jobExecClientId) {
    this.jobExecClientId = jobExecClientId;
  }

  public JobState getNwDmJobState() {
    return this.nwDmJobState;
  }

  public void setNwDmJobState(JobState nwDmJobState) {
    this.nwDmJobState = nwDmJobState;
  }

  public ProvisionJobStatus getNwDmDeviceProvReq() {
    return this.nwDmDeviceProvReq;
  }

  public void setNwDmDeviceProvReq(ProvisionJobStatus nwDmDeviceProvReq) {
    this.nwDmDeviceProvReq = nwDmDeviceProvReq;
  }

  public String getJobClientType() {
    return this.jobClientType;
  }

  public void setJobClientType(String jobClientType) {
    this.jobClientType = jobClientType;
  }

  public String getActionId() {
    return this.actionId;
  }

  public void setActionId(String actionId) {
    this.actionId = actionId;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Set getNwDmDmJobAdapters() {
    return this.nwDmDmJobAdapters;
  }

  public void setNwDmDmJobAdapters(Set nwDmDmJobAdapters) {
    this.nwDmDmJobAdapters = nwDmDmJobAdapters;
  }

}