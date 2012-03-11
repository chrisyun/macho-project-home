/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractImage.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

import com.npower.dm.core.Image;
import com.npower.dm.core.ImageUpdateStatus;
import com.npower.dm.core.Model;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractImage implements java.io.Serializable, Image {

  private long ID;

  private ImageUpdateStatus status;

  private Model model;

  private String versionId;

  private boolean applicableToAllCarriers = true;

  private String description;

  private long changeVersion;

  private Set devices = new HashSet(0);

  private Set updatesForToImageId = new HashSet(0);

  private Set imageCarrierses = new HashSet(0);

  private Set provReqs = new HashSet(0);

  private Set jobStatesForInstallingImage = new HashSet(0);

  private Set prElements = new HashSet(0);

  private Set jobStatesForToImageId = new HashSet(0);

  private Set deviceProvReqsForOldCurrentImageId = new HashSet(0);

  private Set updatesForFromImageId = new HashSet(0);

  private Set jobStatesForOldCurrentImageId = new HashSet(0);

  private Set deviceProvReqsForToImageId = new HashSet(0);

  private Set deviceProvReqsForInstallingImage = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractImage() {
  }

  /** minimal constructor */
  public AbstractImage(Model nwDmModel, String imageExternalId, boolean applicableToAllCarriers) {

    this.model = nwDmModel;
    this.versionId = imageExternalId;
    this.applicableToAllCarriers = applicableToAllCarriers;

  }

  /** full constructor */
  public AbstractImage(Model nwDmModel, String imageExternalId, boolean applicableToAllCarriers, String description) {

    this.model = nwDmModel;
    this.versionId = imageExternalId;
    this.applicableToAllCarriers = applicableToAllCarriers;
    this.description = description;

  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long imageId) {
    this.ID = imageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getStatus()
   */
  public ImageUpdateStatus getStatus() {
    return this.status;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#setStatus(com.npower.dm.hibernate.ImageUpdateStatusEntity)
   */
  public void setStatus(ImageUpdateStatus nwDmImageUpdateStatus) {
    this.status = nwDmImageUpdateStatus;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getModel()
   */
  public Model getModel() {
    return this.model;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#setModel(com.npower.dm.hibernate.Model)
   */
  public void setModel(Model nwDmModel) {
    this.model = nwDmModel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getVersionId()
   */
  public String getVersionId() {
    return this.versionId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#setVersionId(java.lang.String)
   */
  public void setVersionId(String imageExternalId) {
    this.versionId = imageExternalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getApplicableToAllCarriers()
   */
  public boolean getApplicableToAllCarriers() {
    return this.applicableToAllCarriers;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#setApplicableToAllCarriers(boolean)
   */
  public void setApplicableToAllCarriers(boolean applicableToAllCarriers) {
    this.applicableToAllCarriers = applicableToAllCarriers;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getChangeVersion()
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
   * @see com.npower.dm.hibernate.Image#getDevices()
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
   * @see com.npower.dm.hibernate.Image#getUpdatesForToImageId()
   */
  public Set getUpdatesForToImageId() {
    return this.updatesForToImageId;
  }

  public void setUpdatesForToImageId(Set nwDmUpdatesForToImageId) {
    this.updatesForToImageId = nwDmUpdatesForToImageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getImageCarrierses()
   */
  public Set getImageCarrierses() {
    return this.imageCarrierses;
  }

  public void setImageCarrierses(Set nwDmImageCarrierses) {
    this.imageCarrierses = nwDmImageCarrierses;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getProvReqs()
   */
  public Set getProvReqs() {
    return this.provReqs;
  }

  public void setProvReqs(Set nwDmProvReqs) {
    this.provReqs = nwDmProvReqs;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getJobStatesForInstallingImage()
   */
  public Set getJobStatesForInstallingImage() {
    return this.jobStatesForInstallingImage;
  }

  public void setJobStatesForInstallingImage(Set nwDmJobStatesForInstallingImage) {
    this.jobStatesForInstallingImage = nwDmJobStatesForInstallingImage;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getPrElements()
   */
  public Set getPrElements() {
    return this.prElements;
  }

  public void setPrElements(Set nwDmPrElements) {
    this.prElements = nwDmPrElements;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getJobStatesForToImageId()
   */
  public Set getJobStatesForToImageId() {
    return this.jobStatesForToImageId;
  }

  public void setJobStatesForToImageId(Set nwDmJobStatesForToImageId) {
    this.jobStatesForToImageId = nwDmJobStatesForToImageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getDeviceProvReqsForOldCurrentImageId()
   */
  public Set getDeviceProvReqsForOldCurrentImageId() {
    return this.deviceProvReqsForOldCurrentImageId;
  }

  public void setDeviceProvReqsForOldCurrentImageId(Set nwDmDeviceProvReqsForOldCurrentImageId) {
    this.deviceProvReqsForOldCurrentImageId = nwDmDeviceProvReqsForOldCurrentImageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getUpdatesForFromImageId()
   */
  public Set getUpdatesForFromImageId() {
    return this.updatesForFromImageId;
  }

  public void setUpdatesForFromImageId(Set nwDmUpdatesForFromImageId) {
    this.updatesForFromImageId = nwDmUpdatesForFromImageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getJobStatesForOldCurrentImageId()
   */
  public Set getJobStatesForOldCurrentImageId() {
    return this.jobStatesForOldCurrentImageId;
  }

  public void setJobStatesForOldCurrentImageId(Set nwDmJobStatesForOldCurrentImageId) {
    this.jobStatesForOldCurrentImageId = nwDmJobStatesForOldCurrentImageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getDeviceProvReqsForToImageId()
   */
  public Set getDeviceProvReqsForToImageId() {
    return this.deviceProvReqsForToImageId;
  }

  public void setDeviceProvReqsForToImageId(Set nwDmDeviceProvReqsForToImageId) {
    this.deviceProvReqsForToImageId = nwDmDeviceProvReqsForToImageId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Image#getDeviceProvReqsForInstallingImage()
   */
  public Set getDeviceProvReqsForInstallingImage() {
    return this.deviceProvReqsForInstallingImage;
  }

  public void setDeviceProvReqsForInstallingImage(Set nwDmDeviceProvReqsForInstallingImage) {
    this.deviceProvReqsForInstallingImage = nwDmDeviceProvReqsForInstallingImage;
  }

}