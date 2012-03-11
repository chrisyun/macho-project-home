/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/Node4DiscoveryJobID.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public class Node4DiscoveryJobID implements java.io.Serializable {

  // Fields

  private long discoveryJobId;

  private String rootNode;

  // Constructors

  /** default constructor */
  public Node4DiscoveryJobID() {
  }

  // Property accessors

  public long getDiscoveryJobId() {
    return this.discoveryJobId;
  }

  public void setDiscoveryJobId(long discoveryJobId) {
    this.discoveryJobId = discoveryJobId;
  }

  public String getRootNode() {
    return this.rootNode;
  }

  public void setRootNode(String rootNode) {
    this.rootNode = rootNode;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof Node4DiscoveryJobID))
      return false;
    Node4DiscoveryJobID castOther = (Node4DiscoveryJobID) other;

    return (this.getDiscoveryJobId() == castOther.getDiscoveryJobId())
        && ((this.getRootNode() == castOther.getRootNode()) || (this.getRootNode() != null
            && castOther.getRootNode() != null && this.getRootNode().equals(castOther.getRootNode())));
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (int) this.getDiscoveryJobId();
    result = 37 * result + (getRootNode() == null ? 0 : this.getRootNode().hashCode());
    return result;
  }

}