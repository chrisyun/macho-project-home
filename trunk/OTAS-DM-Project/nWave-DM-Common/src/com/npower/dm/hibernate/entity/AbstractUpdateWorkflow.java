/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractUpdateWorkflow.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

import java.sql.Clob;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractUpdateWorkflow implements java.io.Serializable {

  // Fields

  private long workflowId;

  private String name;

  private String internalName;

  private long isCurrent;

  private Clob data;

  private String type;

  private long changeVersion;

  private Set nwDmStepses = new HashSet(0);

  private Set nwDmProvReqs = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractUpdateWorkflow() {
  }

  /** minimal constructor */
  public AbstractUpdateWorkflow(String name, String internalName, long isCurrent, Clob data, String type,
      long changeVersion) {
    this.name = name;
    this.internalName = internalName;
    this.isCurrent = isCurrent;
    this.data = data;
    this.type = type;
    this.changeVersion = changeVersion;
  }

  /** full constructor */
  public AbstractUpdateWorkflow(String name, String internalName, long isCurrent, Clob data, String type,
      long changeVersion, Set nwDmStepses, Set nwDmProvReqs) {
    this.name = name;
    this.internalName = internalName;
    this.isCurrent = isCurrent;
    this.data = data;
    this.type = type;
    this.changeVersion = changeVersion;
    this.nwDmStepses = nwDmStepses;
    this.nwDmProvReqs = nwDmProvReqs;
  }

  // Property accessors

  public long getWorkflowId() {
    return this.workflowId;
  }

  public void setWorkflowId(long workflowId) {
    this.workflowId = workflowId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getInternalName() {
    return this.internalName;
  }

  public void setInternalName(String internalName) {
    this.internalName = internalName;
  }

  public long getIsCurrent() {
    return this.isCurrent;
  }

  public void setIsCurrent(long isCurrent) {
    this.isCurrent = isCurrent;
  }

  public Clob getData() {
    return this.data;
  }

  public void setData(Clob data) {
    this.data = data;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  public Set getNwDmStepses() {
    return this.nwDmStepses;
  }

  public void setNwDmStepses(Set nwDmStepses) {
    this.nwDmStepses = nwDmStepses;
  }

  public Set getNwDmProvReqs() {
    return this.nwDmProvReqs;
  }

  public void setNwDmProvReqs(Set nwDmProvReqs) {
    this.nwDmProvReqs = nwDmProvReqs;
  }

}