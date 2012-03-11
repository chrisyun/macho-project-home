/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractPrevPkgResp.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

import java.sql.Blob;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractPrevPkgResp implements java.io.Serializable {

  // Fields

  private long prevPkgRespId;

  private DMSession nwDmDmSession;

  private Blob responseData;

  private String hmacHeader;

  // Constructors

  /** default constructor */
  public AbstractPrevPkgResp() {
  }

  /** minimal constructor */
  public AbstractPrevPkgResp(DMSession nwDmDmSession, Blob responseData) {
    this.nwDmDmSession = nwDmDmSession;
    this.responseData = responseData;
  }

  /** full constructor */
  public AbstractPrevPkgResp(DMSession nwDmDmSession, Blob responseData, String hmacHeader) {
    this.nwDmDmSession = nwDmDmSession;
    this.responseData = responseData;
    this.hmacHeader = hmacHeader;
  }

  // Property accessors

  public long getPrevPkgRespId() {
    return this.prevPkgRespId;
  }

  public void setPrevPkgRespId(long prevPkgRespId) {
    this.prevPkgRespId = prevPkgRespId;
  }

  public DMSession getNwDmDmSession() {
    return this.nwDmDmSession;
  }

  public void setNwDmDmSession(DMSession nwDmDmSession) {
    this.nwDmDmSession = nwDmDmSession;
  }

  public Blob getResponseData() {
    return this.responseData;
  }

  public void setResponseData(Blob responseData) {
    this.responseData = responseData;
  }

  public String getHmacHeader() {
    return this.hmacHeader;
  }

  public void setHmacHeader(String hmacHeader) {
    this.hmacHeader = hmacHeader;
  }

}