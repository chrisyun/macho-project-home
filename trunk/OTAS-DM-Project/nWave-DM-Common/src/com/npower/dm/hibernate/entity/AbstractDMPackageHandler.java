/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDMPackageHandler.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public abstract class AbstractDMPackageHandler implements java.io.Serializable {

  // Fields

  private long dmPkgHandlerId;

  private DMPackageSender nwDmDmPkgSender;

  private DMPkgRespProc nwDmDmPkgRespProc;

  private DMCommandPackage nwDmDmCmdPkg;

  private DMSession nwDmDmSession;

  private String handlerType;

  private Set nwDmDmSessions = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDMPackageHandler() {
  }

  /** minimal constructor */
  public AbstractDMPackageHandler(String handlerType) {
    this.handlerType = handlerType;
  }

  /** full constructor */
  public AbstractDMPackageHandler(DMPackageSender nwDmDmPkgSender, DMPkgRespProc nwDmDmPkgRespProc,
      DMCommandPackage nwDmDmCmdPkg, DMSession nwDmDmSession, String handlerType, Set nwDmDmSessions) {
    this.nwDmDmPkgSender = nwDmDmPkgSender;
    this.nwDmDmPkgRespProc = nwDmDmPkgRespProc;
    this.nwDmDmCmdPkg = nwDmDmCmdPkg;
    this.nwDmDmSession = nwDmDmSession;
    this.handlerType = handlerType;
    this.nwDmDmSessions = nwDmDmSessions;
  }

  // Property accessors

  public long getDmPkgHandlerId() {
    return this.dmPkgHandlerId;
  }

  public void setDmPkgHandlerId(long dmPkgHandlerId) {
    this.dmPkgHandlerId = dmPkgHandlerId;
  }

  public DMPackageSender getNwDmDmPkgSender() {
    return this.nwDmDmPkgSender;
  }

  public void setNwDmDmPkgSender(DMPackageSender nwDmDmPkgSender) {
    this.nwDmDmPkgSender = nwDmDmPkgSender;
  }

  public DMPkgRespProc getNwDmDmPkgRespProc() {
    return this.nwDmDmPkgRespProc;
  }

  public void setNwDmDmPkgRespProc(DMPkgRespProc nwDmDmPkgRespProc) {
    this.nwDmDmPkgRespProc = nwDmDmPkgRespProc;
  }

  public DMCommandPackage getNwDmDmCmdPkg() {
    return this.nwDmDmCmdPkg;
  }

  public void setNwDmDmCmdPkg(DMCommandPackage nwDmDmCmdPkg) {
    this.nwDmDmCmdPkg = nwDmDmCmdPkg;
  }

  public DMSession getNwDmDmSession() {
    return this.nwDmDmSession;
  }

  public void setNwDmDmSession(DMSession nwDmDmSession) {
    this.nwDmDmSession = nwDmDmSession;
  }

  public String getHandlerType() {
    return this.handlerType;
  }

  public void setHandlerType(String handlerType) {
    this.handlerType = handlerType;
  }

  public Set getNwDmDmSessions() {
    return this.nwDmDmSessions;
  }

  public void setNwDmDmSessions(Set nwDmDmSessions) {
    this.nwDmDmSessions = nwDmDmSessions;
  }

}