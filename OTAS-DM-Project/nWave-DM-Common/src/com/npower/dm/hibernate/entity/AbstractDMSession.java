/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDMSession.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public abstract class AbstractDMSession implements java.io.Serializable {

  // Fields

  private long dmSessionId;

  private DMPackageHandler nwDmDmPkgHandler;

  private SessionAuth nwDmSessionAuth;

  private DMCommandSession nwDmDmCmdSession;

  private String externalId;

  private long isClientAuthenticated;

  private long isServerAuthenticated;

  private long isServerUsingHmac;

  private String serverAuthType;

  private long maxMsgSize;

  private long maxObjSize;

  private long numServerAuthAttempts;

  private String deviceExternalId;

  private String curServerNonce;

  private String curClientNonce;

  private long msgIdCounter;

  private String curMsgIdProc;

  private Set nwDmSessionAuths = new HashSet(0);

  private Set nwDmDmPkgHandlers = new HashSet(0);

  private Set nwDmPrevPkgResps = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDMSession() {
  }

  /** minimal constructor */
  public AbstractDMSession(String curMsgIdProc) {
    this.curMsgIdProc = curMsgIdProc;
  }

  /** full constructor */
  public AbstractDMSession(DMPackageHandler nwDmDmPkgHandler, SessionAuth nwDmSessionAuth,
      DMCommandSession nwDmDmCmdSession, String externalId, long isClientAuthenticated, long isServerAuthenticated,
      long isServerUsingHmac, String serverAuthType, long maxMsgSize, long maxObjSize, long numServerAuthAttempts,
      String deviceExternalId, String curServerNonce, String curClientNonce, long msgIdCounter, String curMsgIdProc,
      Set nwDmSessionAuths, Set nwDmDmPkgHandlers, Set nwDmPrevPkgResps) {
    this.nwDmDmPkgHandler = nwDmDmPkgHandler;
    this.nwDmSessionAuth = nwDmSessionAuth;
    this.nwDmDmCmdSession = nwDmDmCmdSession;
    this.externalId = externalId;
    this.isClientAuthenticated = isClientAuthenticated;
    this.isServerAuthenticated = isServerAuthenticated;
    this.isServerUsingHmac = isServerUsingHmac;
    this.serverAuthType = serverAuthType;
    this.maxMsgSize = maxMsgSize;
    this.maxObjSize = maxObjSize;
    this.numServerAuthAttempts = numServerAuthAttempts;
    this.deviceExternalId = deviceExternalId;
    this.curServerNonce = curServerNonce;
    this.curClientNonce = curClientNonce;
    this.msgIdCounter = msgIdCounter;
    this.curMsgIdProc = curMsgIdProc;
    this.nwDmSessionAuths = nwDmSessionAuths;
    this.nwDmDmPkgHandlers = nwDmDmPkgHandlers;
    this.nwDmPrevPkgResps = nwDmPrevPkgResps;
  }

  // Property accessors

  public long getDmSessionId() {
    return this.dmSessionId;
  }

  public void setDmSessionId(long dmSessionId) {
    this.dmSessionId = dmSessionId;
  }

  public DMPackageHandler getNwDmDmPkgHandler() {
    return this.nwDmDmPkgHandler;
  }

  public void setNwDmDmPkgHandler(DMPackageHandler nwDmDmPkgHandler) {
    this.nwDmDmPkgHandler = nwDmDmPkgHandler;
  }

  public SessionAuth getNwDmSessionAuth() {
    return this.nwDmSessionAuth;
  }

  public void setNwDmSessionAuth(SessionAuth nwDmSessionAuth) {
    this.nwDmSessionAuth = nwDmSessionAuth;
  }

  public DMCommandSession getNwDmDmCmdSession() {
    return this.nwDmDmCmdSession;
  }

  public void setNwDmDmCmdSession(DMCommandSession nwDmDmCmdSession) {
    this.nwDmDmCmdSession = nwDmDmCmdSession;
  }

  public String getExternalId() {
    return this.externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public long getIsClientAuthenticated() {
    return this.isClientAuthenticated;
  }

  public void setIsClientAuthenticated(long isClientAuthenticated) {
    this.isClientAuthenticated = isClientAuthenticated;
  }

  public long getIsServerAuthenticated() {
    return this.isServerAuthenticated;
  }

  public void setIsServerAuthenticated(long isServerAuthenticated) {
    this.isServerAuthenticated = isServerAuthenticated;
  }

  public long getIsServerUsingHmac() {
    return this.isServerUsingHmac;
  }

  public void setIsServerUsingHmac(long isServerUsingHmac) {
    this.isServerUsingHmac = isServerUsingHmac;
  }

  public String getServerAuthType() {
    return this.serverAuthType;
  }

  public void setServerAuthType(String serverAuthType) {
    this.serverAuthType = serverAuthType;
  }

  public long getMaxMsgSize() {
    return this.maxMsgSize;
  }

  public void setMaxMsgSize(long maxMsgSize) {
    this.maxMsgSize = maxMsgSize;
  }

  public long getMaxObjSize() {
    return this.maxObjSize;
  }

  public void setMaxObjSize(long maxObjSize) {
    this.maxObjSize = maxObjSize;
  }

  public long getNumServerAuthAttempts() {
    return this.numServerAuthAttempts;
  }

  public void setNumServerAuthAttempts(long numServerAuthAttempts) {
    this.numServerAuthAttempts = numServerAuthAttempts;
  }

  public String getDeviceExternalId() {
    return this.deviceExternalId;
  }

  public void setDeviceExternalId(String deviceExternalId) {
    this.deviceExternalId = deviceExternalId;
  }

  public String getCurServerNonce() {
    return this.curServerNonce;
  }

  public void setCurServerNonce(String curServerNonce) {
    this.curServerNonce = curServerNonce;
  }

  public String getCurClientNonce() {
    return this.curClientNonce;
  }

  public void setCurClientNonce(String curClientNonce) {
    this.curClientNonce = curClientNonce;
  }

  public long getMsgIdCounter() {
    return this.msgIdCounter;
  }

  public void setMsgIdCounter(long msgIdCounter) {
    this.msgIdCounter = msgIdCounter;
  }

  public String getCurMsgIdProc() {
    return this.curMsgIdProc;
  }

  public void setCurMsgIdProc(String curMsgIdProc) {
    this.curMsgIdProc = curMsgIdProc;
  }

  public Set getNwDmSessionAuths() {
    return this.nwDmSessionAuths;
  }

  public void setNwDmSessionAuths(Set nwDmSessionAuths) {
    this.nwDmSessionAuths = nwDmSessionAuths;
  }

  public Set getNwDmDmPkgHandlers() {
    return this.nwDmDmPkgHandlers;
  }

  public void setNwDmDmPkgHandlers(Set nwDmDmPkgHandlers) {
    this.nwDmDmPkgHandlers = nwDmDmPkgHandlers;
  }

  public Set getNwDmPrevPkgResps() {
    return this.nwDmPrevPkgResps;
  }

  public void setNwDmPrevPkgResps(Set nwDmPrevPkgResps) {
    this.nwDmPrevPkgResps = nwDmPrevPkgResps;
  }

}