/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDMPkgRespProc.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public abstract class AbstractDMPkgRespProc implements java.io.Serializable {

  // Fields

  private long dmPkgRespProcId;

  private DMPackageSender nwDmDmPkgSender;

  private Set nwDmDmPkgHandlers = new HashSet(0);

  private Set nwDmDmCmdResponses = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDMPkgRespProc() {
  }

  /** full constructor */
  public AbstractDMPkgRespProc(DMPackageSender nwDmDmPkgSender, Set nwDmDmPkgHandlers, Set nwDmDmCmdResponses) {
    this.nwDmDmPkgSender = nwDmDmPkgSender;
    this.nwDmDmPkgHandlers = nwDmDmPkgHandlers;
    this.nwDmDmCmdResponses = nwDmDmCmdResponses;
  }

  // Property accessors

  public long getDmPkgRespProcId() {
    return this.dmPkgRespProcId;
  }

  public void setDmPkgRespProcId(long dmPkgRespProcId) {
    this.dmPkgRespProcId = dmPkgRespProcId;
  }

  public DMPackageSender getNwDmDmPkgSender() {
    return this.nwDmDmPkgSender;
  }

  public void setNwDmDmPkgSender(DMPackageSender nwDmDmPkgSender) {
    this.nwDmDmPkgSender = nwDmDmPkgSender;
  }

  public Set getNwDmDmPkgHandlers() {
    return this.nwDmDmPkgHandlers;
  }

  public void setNwDmDmPkgHandlers(Set nwDmDmPkgHandlers) {
    this.nwDmDmPkgHandlers = nwDmDmPkgHandlers;
  }

  public Set getNwDmDmCmdResponses() {
    return this.nwDmDmCmdResponses;
  }

  public void setNwDmDmCmdResponses(Set nwDmDmCmdResponses) {
    this.nwDmDmCmdResponses = nwDmDmCmdResponses;
  }

}