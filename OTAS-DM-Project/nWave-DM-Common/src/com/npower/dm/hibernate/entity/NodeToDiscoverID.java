/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/NodeToDiscoverID.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public class NodeToDiscoverID implements java.io.Serializable {

  // Fields

  private long devProvReqId;

  private long nodeIndex;

  // Constructors

  /** default constructor */
  public NodeToDiscoverID() {
  }

  // Property accessors

  public long getDevProvReqId() {
    return this.devProvReqId;
  }

  public void setDevProvReqId(long devProvReqId) {
    this.devProvReqId = devProvReqId;
  }

  public long getNodeIndex() {
    return this.nodeIndex;
  }

  public void setNodeIndex(long nodeIndex) {
    this.nodeIndex = nodeIndex;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof NodeToDiscoverID))
      return false;
    NodeToDiscoverID castOther = (NodeToDiscoverID) other;

    return (this.getDevProvReqId() == castOther.getDevProvReqId()) && (this.getNodeIndex() == castOther.getNodeIndex());
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (int) this.getDevProvReqId();
    result = 37 * result + (int) this.getNodeIndex();
    return result;
  }

}