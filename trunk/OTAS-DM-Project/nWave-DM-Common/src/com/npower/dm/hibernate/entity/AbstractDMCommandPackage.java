/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDMCommandPackage.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractDMCommandPackage implements java.io.Serializable {

  // Fields

  private long dmCmdPkgId;

  private String state;

  private Set nwDmJobStates = new HashSet(0);

  private Set nwDmDmPkgHandlers = new HashSet(0);

  private Set nwDmDmPkgSenders = new HashSet(0);

  private Set nwDmDmCmds = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDMCommandPackage() {
  }

  /** full constructor */
  public AbstractDMCommandPackage(String state, Set nwDmJobStates, Set nwDmDmPkgHandlers, Set nwDmDmPkgSenders,
      Set nwDmDmCmds) {
    this.state = state;
    this.nwDmJobStates = nwDmJobStates;
    this.nwDmDmPkgHandlers = nwDmDmPkgHandlers;
    this.nwDmDmPkgSenders = nwDmDmPkgSenders;
    this.nwDmDmCmds = nwDmDmCmds;
  }

  // Property accessors

  public long getDmCmdPkgId() {
    return this.dmCmdPkgId;
  }

  public void setDmCmdPkgId(long dmCmdPkgId) {
    this.dmCmdPkgId = dmCmdPkgId;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Set getNwDmJobStates() {
    return this.nwDmJobStates;
  }

  public void setNwDmJobStates(Set nwDmJobStates) {
    this.nwDmJobStates = nwDmJobStates;
  }

  public Set getNwDmDmPkgHandlers() {
    return this.nwDmDmPkgHandlers;
  }

  public void setNwDmDmPkgHandlers(Set nwDmDmPkgHandlers) {
    this.nwDmDmPkgHandlers = nwDmDmPkgHandlers;
  }

  public Set getNwDmDmPkgSenders() {
    return this.nwDmDmPkgSenders;
  }

  public void setNwDmDmPkgSenders(Set nwDmDmPkgSenders) {
    this.nwDmDmPkgSenders = nwDmDmPkgSenders;
  }

  public Set getNwDmDmCmds() {
    return this.nwDmDmCmds;
  }

  public void setNwDmDmCmds(Set nwDmDmCmds) {
    this.nwDmDmCmds = nwDmDmCmds;
  }

}