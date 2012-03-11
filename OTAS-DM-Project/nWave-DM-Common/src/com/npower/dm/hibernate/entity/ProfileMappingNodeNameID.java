/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileMappingNodeNameID.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public class ProfileMappingNodeNameID implements java.io.Serializable {

  // Fields

  private long profileMappingId;

  private long ddfNodeId;

  private String nodeName;

  // Constructors

  /** default constructor */
  public ProfileMappingNodeNameID() {
  }

  // Property accessors

  public long getProfileMappingId() {
    return this.profileMappingId;
  }

  public void setProfileMappingId(long profileMappingId) {
    this.profileMappingId = profileMappingId;
  }

  public long getDdfNodeId() {
    return this.ddfNodeId;
  }

  public void setDdfNodeId(long ddfNodeId) {
    this.ddfNodeId = ddfNodeId;
  }

  public String getNodeName() {
    return this.nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof ProfileMappingNodeNameID))
      return false;
    ProfileMappingNodeNameID castOther = (ProfileMappingNodeNameID) other;

    return (this.getProfileMappingId() == castOther.getProfileMappingId())
        && (this.getDdfNodeId() == castOther.getDdfNodeId())
        && ((this.getNodeName() == castOther.getNodeName()) || (this.getNodeName() != null
            && castOther.getNodeName() != null && this.getNodeName().equals(castOther.getNodeName())));
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (int) this.getProfileMappingId();
    result = 37 * result + (int) this.getDdfNodeId();
    result = 37 * result + (getNodeName() == null ? 0 : this.getNodeName().hashCode());
    return result;
  }

}