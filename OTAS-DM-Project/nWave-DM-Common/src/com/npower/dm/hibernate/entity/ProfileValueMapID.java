/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileValueMapID.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public class ProfileValueMapID implements java.io.Serializable {

  // Fields

  private long profileId;

  private long attributeValueId;

  // Constructors

  /** default constructor */
  public ProfileValueMapID() {
  }

  // Property accessors

  public long getProfileId() {
    return this.profileId;
  }

  public void setProfileId(long profileId) {
    this.profileId = profileId;
  }

  public long getAttributeValueId() {
    return this.attributeValueId;
  }

  public void setAttributeValueId(long attributeValueId) {
    this.attributeValueId = attributeValueId;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof ProfileValueMapID))
      return false;
    ProfileValueMapID castOther = (ProfileValueMapID) other;

    return (this.getProfileId() == castOther.getProfileId())
        && (this.getAttributeValueId() == castOther.getAttributeValueId());
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (int) this.getProfileId();
    result = 37 * result + (int) this.getAttributeValueId();
    return result;
  }

}