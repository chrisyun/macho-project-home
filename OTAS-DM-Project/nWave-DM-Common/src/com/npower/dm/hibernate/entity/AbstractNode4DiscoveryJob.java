/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractNode4DiscoveryJob.java,v 1.4 2006/04/26 14:55:55 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2006/04/26 14:55:55 $
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
 * @version $Revision: 1.4 $ $Date: 2006/04/26 14:55:55 $
 */
public abstract class AbstractNode4DiscoveryJob implements java.io.Serializable {

  // Fields

  private Node4DiscoveryJobID ID;

  private ProvisionRequest provisionRequest;

  // Constructors

  /** default constructor */
  public AbstractNode4DiscoveryJob() {
    super();
  }

  /** full constructor */
  public AbstractNode4DiscoveryJob(ProvisionRequest provReq, String nodePath) {
    this.provisionRequest = provReq;
    Node4DiscoveryJobID id = new Node4DiscoveryJobID();
    id.setDiscoveryJobId(provReq.getID());
    id.setRootNode(nodePath);
    this.setID(id);
  }

  // Property accessors

  public Node4DiscoveryJobID getID() {
    return this.ID;
  }

  public void setID(Node4DiscoveryJobID id) {
    this.ID = id;
  }

  public ProvisionRequest getProvisionRequest() {
    return this.provisionRequest;
  }

  public void setProvisionRequest(ProvisionRequest provReq) {
    this.provisionRequest = provReq;
  }

}