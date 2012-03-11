/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractSessionAuth.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

import com.npower.dm.core.Device;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractSessionAuth implements java.io.Serializable {

  // Fields

  private long sessionAuthId;

  private DMSession nwDmDmSession;

  private Device nwDmDevice;

  private String sessionAuthType;

  private String clientNonce;

  private Set nwDmDmSessions = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractSessionAuth() {
  }

  /** full constructor */
  public AbstractSessionAuth(DMSession nwDmDmSession, Device nwDmDevice, String sessionAuthType, String clientNonce,
      Set nwDmDmSessions) {
    this.nwDmDmSession = nwDmDmSession;
    this.nwDmDevice = nwDmDevice;
    this.sessionAuthType = sessionAuthType;
    this.clientNonce = clientNonce;
    this.nwDmDmSessions = nwDmDmSessions;
  }

  // Property accessors

  public long getSessionAuthId() {
    return this.sessionAuthId;
  }

  public void setSessionAuthId(long sessionAuthId) {
    this.sessionAuthId = sessionAuthId;
  }

  public DMSession getNwDmDmSession() {
    return this.nwDmDmSession;
  }

  public void setNwDmDmSession(DMSession nwDmDmSession) {
    this.nwDmDmSession = nwDmDmSession;
  }

  public Device getNwDmDevice() {
    return this.nwDmDevice;
  }

  public void setNwDmDevice(Device nwDmDevice) {
    this.nwDmDevice = nwDmDevice;
  }

  public String getSessionAuthType() {
    return this.sessionAuthType;
  }

  public void setSessionAuthType(String sessionAuthType) {
    this.sessionAuthType = sessionAuthType;
  }

  public String getClientNonce() {
    return this.clientNonce;
  }

  public void setClientNonce(String clientNonce) {
    this.clientNonce = clientNonce;
  }

  public Set getNwDmDmSessions() {
    return this.nwDmDmSessions;
  }

  public void setNwDmDmSessions(Set nwDmDmSessions) {
    this.nwDmDmSessions = nwDmDmSessions;
  }

}