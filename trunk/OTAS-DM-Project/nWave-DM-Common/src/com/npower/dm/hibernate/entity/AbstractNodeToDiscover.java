/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractNodeToDiscover.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public abstract class AbstractNodeToDiscover implements java.io.Serializable {

  // Fields

  private NodeToDiscoverID id;

  private JobState nwDmJobState;

  private String nodeName;

  // Constructors

  /** default constructor */
  public AbstractNodeToDiscover() {
  }

  /** minimal constructor */
  public AbstractNodeToDiscover(NodeToDiscoverID id, JobState nwDmJobState) {
    this.id = id;
    this.nwDmJobState = nwDmJobState;
  }

  /** full constructor */
  public AbstractNodeToDiscover(NodeToDiscoverID id, JobState nwDmJobState, String nodeName) {
    this.id = id;
    this.nwDmJobState = nwDmJobState;
    this.nodeName = nodeName;
  }

  // Property accessors

  public NodeToDiscoverID getId() {
    return this.id;
  }

  public void setId(NodeToDiscoverID id) {
    this.id = id;
  }

  public JobState getNwDmJobState() {
    return this.nwDmJobState;
  }

  public void setNwDmJobState(JobState nwDmJobState) {
    this.nwDmJobState = nwDmJobState;
  }

  public String getNodeName() {
    return this.nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

}