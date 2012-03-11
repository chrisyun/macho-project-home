/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDMPackageSender.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public abstract class AbstractDMPackageSender implements java.io.Serializable {

  // Fields

  private long dmPkgSenderId;

  private DMCommandPackage nwDmDmCmdPkg;

  private long nextCommandIndex;

  private long nextItemIndex;

  private long chunkIndex;

  private Set nwDmDmPkgSenderCmdIdses = new HashSet(0);

  private Set nwDmDmPkgHandlers = new HashSet(0);

  private Set nwDmDmPkgRespProcs = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDMPackageSender() {
  }

  /** full constructor */
  public AbstractDMPackageSender(DMCommandPackage nwDmDmCmdPkg, long nextCommandIndex, long nextItemIndex,
      long chunkIndex, Set nwDmDmPkgSenderCmdIdses, Set nwDmDmPkgHandlers, Set nwDmDmPkgRespProcs) {
    this.nwDmDmCmdPkg = nwDmDmCmdPkg;
    this.nextCommandIndex = nextCommandIndex;
    this.nextItemIndex = nextItemIndex;
    this.chunkIndex = chunkIndex;
    this.nwDmDmPkgSenderCmdIdses = nwDmDmPkgSenderCmdIdses;
    this.nwDmDmPkgHandlers = nwDmDmPkgHandlers;
    this.nwDmDmPkgRespProcs = nwDmDmPkgRespProcs;
  }

  // Property accessors

  public long getDmPkgSenderId() {
    return this.dmPkgSenderId;
  }

  public void setDmPkgSenderId(long dmPkgSenderId) {
    this.dmPkgSenderId = dmPkgSenderId;
  }

  public DMCommandPackage getNwDmDmCmdPkg() {
    return this.nwDmDmCmdPkg;
  }

  public void setNwDmDmCmdPkg(DMCommandPackage nwDmDmCmdPkg) {
    this.nwDmDmCmdPkg = nwDmDmCmdPkg;
  }

  public long getNextCommandIndex() {
    return this.nextCommandIndex;
  }

  public void setNextCommandIndex(long nextCommandIndex) {
    this.nextCommandIndex = nextCommandIndex;
  }

  public long getNextItemIndex() {
    return this.nextItemIndex;
  }

  public void setNextItemIndex(long nextItemIndex) {
    this.nextItemIndex = nextItemIndex;
  }

  public long getChunkIndex() {
    return this.chunkIndex;
  }

  public void setChunkIndex(long chunkIndex) {
    this.chunkIndex = chunkIndex;
  }

  public Set getNwDmDmPkgSenderCmdIdses() {
    return this.nwDmDmPkgSenderCmdIdses;
  }

  public void setNwDmDmPkgSenderCmdIdses(Set nwDmDmPkgSenderCmdIdses) {
    this.nwDmDmPkgSenderCmdIdses = nwDmDmPkgSenderCmdIdses;
  }

  public Set getNwDmDmPkgHandlers() {
    return this.nwDmDmPkgHandlers;
  }

  public void setNwDmDmPkgHandlers(Set nwDmDmPkgHandlers) {
    this.nwDmDmPkgHandlers = nwDmDmPkgHandlers;
  }

  public Set getNwDmDmPkgRespProcs() {
    return this.nwDmDmPkgRespProcs;
  }

  public void setNwDmDmPkgRespProcs(Set nwDmDmPkgRespProcs) {
    this.nwDmDmPkgRespProcs = nwDmDmPkgRespProcs;
  }

}