/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ModelProfileMapID.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public class ModelProfileMapID implements java.io.Serializable {

  // Fields

  private long deviceModelId;

  private long profileMappingId;

  // Constructors

  /** default constructor */
  public ModelProfileMapID() {
  }

  // Property accessors

  public long getDeviceModelId() {
    return this.deviceModelId;
  }

  public void setDeviceModelId(long deviceModelId) {
    this.deviceModelId = deviceModelId;
  }

  public long getProfileMappingId() {
    return this.profileMappingId;
  }

  public void setProfileMappingId(long profileMappingId) {
    this.profileMappingId = profileMappingId;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof ModelProfileMapID))
      return false;
    ModelProfileMapID castOther = (ModelProfileMapID) other;

    return (this.getDeviceModelId() == castOther.getDeviceModelId())
        && (this.getProfileMappingId() == castOther.getProfileMappingId());
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (int) this.getDeviceModelId();
    result = 37 * result + (int) this.getProfileMappingId();
    return result;
  }

}