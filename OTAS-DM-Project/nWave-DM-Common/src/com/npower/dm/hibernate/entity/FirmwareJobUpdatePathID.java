/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/FirmwareJobUpdatePathID.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public class FirmwareJobUpdatePathID implements java.io.Serializable {

  // Fields

  private long fwJobStateId;

  private long updateId;

  private long pathIndex;

  // Constructors

  /** default constructor */
  public FirmwareJobUpdatePathID() {
  }

  // Property accessors

  public long getFwJobStateId() {
    return this.fwJobStateId;
  }

  public void setFwJobStateId(long fwJobStateId) {
    this.fwJobStateId = fwJobStateId;
  }

  public long getUpdateId() {
    return this.updateId;
  }

  public void setUpdateId(long updateId) {
    this.updateId = updateId;
  }

  public long getPathIndex() {
    return this.pathIndex;
  }

  public void setPathIndex(long pathIndex) {
    this.pathIndex = pathIndex;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof FirmwareJobUpdatePathID))
      return false;
    FirmwareJobUpdatePathID castOther = (FirmwareJobUpdatePathID) other;

    return (this.getFwJobStateId() == castOther.getFwJobStateId()) && (this.getUpdateId() == castOther.getUpdateId())
        && (this.getPathIndex() == castOther.getPathIndex());
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (int) this.getFwJobStateId();
    result = 37 * result + (int) this.getUpdateId();
    result = 37 * result + (int) this.getPathIndex();
    return result;
  }

}