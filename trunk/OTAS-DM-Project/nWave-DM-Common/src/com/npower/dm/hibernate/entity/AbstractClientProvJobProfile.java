/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractClientProvJobProfile.java,v 1.1 2008/02/18 10:10:22 zhao Exp $
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

import java.util.HashSet;
import java.util.Set;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/02/18 10:10:22 $
 */
public abstract class AbstractClientProvJobProfile implements java.io.Serializable {

  // Fields

  private long                      id;

  private ClientProvJobTargetDeviceEntity targetDevice;

  private String                    profileExternalId;

  private Set                       parameters = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractClientProvJobProfile() {
  }

  /** minimal constructor */
  public AbstractClientProvJobProfile(String profileExternalId) {
    this.profileExternalId = profileExternalId;
  }

  /** full constructor */
  public AbstractClientProvJobProfile(ClientProvJobTargetDeviceEntity targetDevice, String profileExternalId,
      Set parameters) {
    this.targetDevice = targetDevice;
    this.profileExternalId = profileExternalId;
    this.parameters = parameters;
  }

  // Property accessors

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ClientProvJobTargetDeviceEntity getTargetDevice() {
    return this.targetDevice;
  }

  public void setTargetDevice(ClientProvJobTargetDeviceEntity targetDevice) {
    this.targetDevice = targetDevice;
  }

  public String getProfileExternalId() {
    return this.profileExternalId;
  }

  public void setProfileExternalId(String profileExternalId) {
    this.profileExternalId = profileExternalId;
  }

  public Set getParameters() {
    return this.parameters;
  }

  public void setParameters(Set parameters) {
    this.parameters = parameters;
  }

}