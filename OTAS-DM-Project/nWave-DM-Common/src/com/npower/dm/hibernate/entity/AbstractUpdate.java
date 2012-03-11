/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractUpdate.java,v 1.4 2008/11/24 10:46:56 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/11/24 10:46:56 $
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

import com.npower.dm.core.Image;
import com.npower.dm.core.ImageUpdateStatus;
import com.npower.dm.core.Update;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/11/24 10:46:56 $
 */
public abstract class AbstractUpdate implements java.io.Serializable, Update {

  // Fields

  private long ID;

  private Image fromImage;

  private DMBinary DMBlob;

  private ImageUpdateStatus status;

  private Image toImage;

  private String lastUpdatedBy;

  private Date lastUpdatedTime = new Date();

  private String description;

  private long workflowEntryId;

  private long workflowId;

  private String workflowSteps;

  private long changeVersion;

  private Date createdTime = new Date();

  private Set updateCarrierses = new HashSet(0);

  private Set devices = new HashSet(0);

  private Set pathElements = new HashSet(0);

  private Set jobUpdatePaths = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractUpdate() {
    super();
  }

  /** minimal constructor */
  public AbstractUpdate(Image from, Image to) {

    this.fromImage = from;
    this.toImage = to;
  }

  /** full constructor */
  public AbstractUpdate(Image from, Image to, DMBinary binary, String description) {

    this.fromImage = from;
    this.DMBlob = binary;
    this.toImage = to;
    this.description = description;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long updateId) {
    this.ID = updateId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getFromImage()
   */
  public Image getFromImage() {
    return this.fromImage;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#setFromImage(com.npower.dm.hibernate.ImageEntity)
   */
  public void setFromImage(Image nwDmImageByFromImageId) {
    this.fromImage = nwDmImageByFromImageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getDMBlob()
   */
  public DMBinary getDMBlob() {
    return this.DMBlob;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#setDMBlob(com.npower.dm.hibernate.DMBinary)
   */
  public void setDMBlob(DMBinary binary) {
    this.DMBlob = binary;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getStatus()
   */
  public ImageUpdateStatus getStatus() {
    return this.status;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#setStatus(com.npower.dm.hibernate.ImageUpdateStatusEntity)
   */
  public void setStatus(ImageUpdateStatus nwDmImageUpdateStatus) {
    this.status = nwDmImageUpdateStatus;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getToImage()
   */
  public Image getToImage() {
    return this.toImage;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#setToImage(com.npower.dm.hibernate.ImageEntity)
   */
  public void setToImage(Image nwDmImageByToImageId) {
    this.toImage = nwDmImageByToImageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getLastUpdatedTime()
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
   * @see com.npower.dm.hibernate.Update#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getWorkflowEntryId()
   */
  public long getWorkflowEntryId() {
    return this.workflowEntryId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#setWorkflowEntryId(long)
   */
  public void setWorkflowEntryId(long workflowEntryId) {
    this.workflowEntryId = workflowEntryId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getWorkflowId()
   */
  public long getWorkflowId() {
    return this.workflowId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#setWorkflowId(long)
   */
  public void setWorkflowId(long workflowId) {
    this.workflowId = workflowId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getWorkflowSteps()
   */
  public String getWorkflowSteps() {
    return this.workflowSteps;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#setWorkflowSteps(java.lang.String)
   */
  public void setWorkflowSteps(String workflowSteps) {
    this.workflowSteps = workflowSteps;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getChangeVersion()
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
   * @see com.npower.dm.hibernate.Update#getUpdateCarrierses()
   */
  public Set getUpdateCarrierses() {
    return this.updateCarrierses;
  }

  public void setUpdateCarrierses(Set nwDmUpdateCarrierses) {
    this.updateCarrierses = nwDmUpdateCarrierses;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getDevices()
   */
  public Set getDevices() {
    return this.devices;
  }

  public void setDevices(Set nwDmDevices) {
    this.devices = nwDmDevices;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getPathElements()
   */
  public Set getPathElements() {
    return this.pathElements;
  }

  public void setPathElements(Set nwDmPathElements) {
    this.pathElements = nwDmPathElements;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Update#getJobUpdatePaths()
   */
  public Set getJobUpdatePaths() {
    return this.jobUpdatePaths;
  }

  public void setJobUpdatePaths(Set nwDmFwJobUpdatePaths) {
    this.jobUpdatePaths = nwDmFwJobUpdatePaths;
  }

  /**
   * @return the createdTime
   */
  public Date getCreatedTime() {
    return createdTime;
  }

  /**
   * @param createdTime the createdTime to set
   */
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

}