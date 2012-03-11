/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDMCommandSession.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public abstract class AbstractDMCommandSession implements java.io.Serializable {

  // Fields

  private long dmCmdSessionId;

  private String cmdSessionType;

  private String manExtId;

  private String manModId;

  private String deviceId;

  private long jobAdapterId;

  private Set nwDmDmSessions = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDMCommandSession() {
  }

  /** minimal constructor */
  public AbstractDMCommandSession(String cmdSessionType) {
    this.cmdSessionType = cmdSessionType;
  }

  /** full constructor */
  public AbstractDMCommandSession(String cmdSessionType, String manExtId, String manModId, String deviceId,
      long jobAdapterId, Set nwDmDmSessions) {
    this.cmdSessionType = cmdSessionType;
    this.manExtId = manExtId;
    this.manModId = manModId;
    this.deviceId = deviceId;
    this.jobAdapterId = jobAdapterId;
    this.nwDmDmSessions = nwDmDmSessions;
  }

  // Property accessors

  public long getDmCmdSessionId() {
    return this.dmCmdSessionId;
  }

  public void setDmCmdSessionId(long dmCmdSessionId) {
    this.dmCmdSessionId = dmCmdSessionId;
  }

  public String getCmdSessionType() {
    return this.cmdSessionType;
  }

  public void setCmdSessionType(String cmdSessionType) {
    this.cmdSessionType = cmdSessionType;
  }

  public String getManExtId() {
    return this.manExtId;
  }

  public void setManExtId(String manExtId) {
    this.manExtId = manExtId;
  }

  public String getManModId() {
    return this.manModId;
  }

  public void setManModId(String manModId) {
    this.manModId = manModId;
  }

  public String getDeviceId() {
    return this.deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public long getJobAdapterId() {
    return this.jobAdapterId;
  }

  public void setJobAdapterId(long jobAdapterId) {
    this.jobAdapterId = jobAdapterId;
  }

  public Set getNwDmDmSessions() {
    return this.nwDmDmSessions;
  }

  public void setNwDmDmSessions(Set nwDmDmSessions) {
    this.nwDmDmSessions = nwDmDmSessions;
  }

}