/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractElement4Provision.java,v 1.6 2006/04/27 08:08:55 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2006/04/27 08:08:55 $
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

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.Image;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2006/04/27 08:08:55 $
 */
public abstract class AbstractElement4Provision implements java.io.Serializable {

  // Fields

  private long ID;

  private DeviceGroup deviceGroup = null;

  private Image sourceImage = null;

  private ProvisionRequest provisionRequest = null;

  private Carrier carrier = null;

  private String state = "PENDING";

  private long changeVersion = 0;

  private String jobElementType = null;

  private Set pathElements = new HashSet(0);

  private Set deviceProvReqs = new HashSet(0);

  private Set devices = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractElement4Provision() {
  }

  /** full constructor */
  public AbstractElement4Provision(DeviceGroup deviceGroup, ProvisionRequest provReq) {
    this.deviceGroup = deviceGroup;
    this.provisionRequest = provReq;
    this.setJobElementType(provReq.getJobType());
  }

  /** full constructor */
  public AbstractElement4Provision(Carrier carrier, DeviceGroup deviceGroup, ProvisionRequest provReq) {
    this.deviceGroup = deviceGroup;
    this.provisionRequest = provReq;
    this.carrier = carrier;
    this.setJobElementType(provReq.getJobType());
  }

  // Property accessors

  public long getID() {
    return this.ID;
  }

  public void setID(long prElementId) {
    this.ID = prElementId;
  }

  public DeviceGroup getDeviceGroup() {
    return this.deviceGroup;
  }

  public void setDeviceGroup(DeviceGroup nwDmDeviceGroup) {
    this.deviceGroup = nwDmDeviceGroup;
  }

  public Image getSourceImage() {
    return this.sourceImage;
  }

  public void setSourceImage(Image nwDmImage) {
    this.sourceImage = nwDmImage;
  }

  public ProvisionRequest getProvisionRequest() {
    return this.provisionRequest;
  }

  public void setProvisionRequest(ProvisionRequest nwDmProvReq) {
    this.provisionRequest = nwDmProvReq;
  }

  public Carrier getCarrier() {
    return this.carrier;
  }

  public void setCarrier(Carrier nwDmCarrier) {
    this.carrier = nwDmCarrier;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  public String getJobElementType() {
    return this.jobElementType;
  }

  public void setJobElementType(String jobElementType) {
    this.jobElementType = jobElementType;
  }

  public Set getPathElements() {
    return this.pathElements;
  }

  public void setPathElements(Set nwDmPathElements) {
    this.pathElements = nwDmPathElements;
  }

  public Set getDeviceProvReqs() {
    return this.deviceProvReqs;
  }

  public void setDeviceProvReqs(Set nwDmDeviceProvReqs) {
    this.deviceProvReqs = nwDmDeviceProvReqs;
  }

  public Set getDevices() {
    return this.devices;
  }

  public void setDevices(Set nwDmDevices) {
    this.devices = nwDmDevices;
  }

}