/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractClientProvJob.java,v 1.1 2008/02/18 10:10:22 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/02/18 10:10:22 $
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

import com.npower.dm.core.ClientProvJob;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/02/18 10:10:22 $
 */
public abstract class AbstractClientProvJob implements java.io.Serializable, ClientProvJob {

  // Fields

  private long   id;

  private String name;

  private String type;

  private String description;

  private String state;

  private Date   scheduleTime;

  private long   maxRetries;

  private long   maxDuration;

  private Date   createdTime = new Date();

  private String createdBy;

  private Date   lastUpdatedTime = new Date();

  private String lastUpdatedBy;

  private Set<?>    targetDevices = new HashSet<Object>(0);

  // Constructors

  /** default constructor */
  public AbstractClientProvJob() {
  }

  /** minimal constructor */
  public AbstractClientProvJob(String state, Date createdTime) {
    this.state = state;
    this.createdTime = createdTime;
  }

  /** full constructor */
  public AbstractClientProvJob(String name, String type, String description, String state, Date scheduleTime,
      long maxRetries, long maxDuration, Date createdTime, String createdBy, Date lastUpdatedTime,
      String lastUpdatedBy, Set targetDevices) {
    this.name = name;
    this.type = type;
    this.description = description;
    this.state = state;
    this.scheduleTime = scheduleTime;
    this.maxRetries = maxRetries;
    this.maxDuration = maxDuration;
    this.createdTime = createdTime;
    this.createdBy = createdBy;
    this.lastUpdatedTime = lastUpdatedTime;
    this.lastUpdatedBy = lastUpdatedBy;
    this.targetDevices = targetDevices;
  }

  // Property accessors

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getJobId()
   */
  public long getId() {
    return this.id;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setJobId(long)
   */
  public void setId(long jobId) {
    this.id = jobId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getName()
   */
  public String getName() {
    return this.name;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getType()
   */
  public String getType() {
    return this.type;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setType(java.lang.String)
   */
  public void setType(String type) {
    this.type = type;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getState()
   */
  public String getState() {
    return this.state;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setState(java.lang.String)
   */
  public void setState(String state) {
    this.state = state;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getScheduleTime()
   */
  public Date getScheduleTime() {
    return this.scheduleTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setScheduleTime(java.util.Date)
   */
  public void setScheduleTime(Date scheduleTime) {
    this.scheduleTime = scheduleTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getMaxRetries()
   */
  public long getMaxRetries() {
    return this.maxRetries;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setMaxRetries(long)
   */
  public void setMaxRetries(long maxRetries) {
    this.maxRetries = maxRetries;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getMaxDuration()
   */
  public long getMaxDuration() {
    return this.maxDuration;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setMaxDuration(long)
   */
  public void setMaxDuration(long maxDuration) {
    this.maxDuration = maxDuration;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getCreatedTime()
   */
  public Date getCreatedTime() {
    return this.createdTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setCreatedTime(java.util.Date)
   */
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getCreatedBy()
   */
  public String getCreatedBy() {
    return this.createdBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setCreatedBy(java.lang.String)
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    return this.lastUpdatedTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setLastUpdatedTime(java.util.Date)
   */
  public void setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#getTargetDevices()
   */
  public Set getTargetDevices() {
    return this.targetDevices;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClienProvJob#setTargetDevices(java.util.Set)
   */
  public void setTargetDevices(Set targetDevices) {
    this.targetDevices = targetDevices;
  }

}