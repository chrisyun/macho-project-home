/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProvisionsPhoneNumber.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public abstract class AbstractProvisionsPhoneNumber implements java.io.Serializable {

  // Fields

  private long prPnId;

  private ProvisionRequest nwDmProvReq;

  private String phoneNumber;

  private long changeVersion;

  // Constructors

  /** default constructor */
  public AbstractProvisionsPhoneNumber() {
  }

  /** minimal constructor */
  public AbstractProvisionsPhoneNumber(String phoneNumber, long changeVersion) {
    this.phoneNumber = phoneNumber;
    this.changeVersion = changeVersion;
  }

  /** full constructor */
  public AbstractProvisionsPhoneNumber(ProvisionRequest nwDmProvReq, String phoneNumber, long changeVersion) {
    this.nwDmProvReq = nwDmProvReq;
    this.phoneNumber = phoneNumber;
    this.changeVersion = changeVersion;
  }

  // Property accessors

  public long getPrPnId() {
    return this.prPnId;
  }

  public void setPrPnId(long prPnId) {
    this.prPnId = prPnId;
  }

  public ProvisionRequest getNwDmProvReq() {
    return this.nwDmProvReq;
  }

  public void setNwDmProvReq(ProvisionRequest nwDmProvReq) {
    this.nwDmProvReq = nwDmProvReq;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

}