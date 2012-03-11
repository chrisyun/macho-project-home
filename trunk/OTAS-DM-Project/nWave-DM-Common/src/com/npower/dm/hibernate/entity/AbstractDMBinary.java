/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDMBinary.java,v 1.4 2008/04/02 06:24:24 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/04/02 06:24:24 $
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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/04/02 06:24:24 $
 */
public abstract class AbstractDMBinary implements java.io.Serializable {

  // Fields

  private long ID;
  
  private String filename = null;
  
  private String mimeType = null;

  private Blob binary;

  private Date creationDate = new Date();

  private String status;

  private String lastUpdatedBy;

  private Date lastUpdatedTime = new Date();

  private Set updates = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDMBinary() {
    super();
  }

  /** minimal constructor */
  public AbstractDMBinary(Blob bits) {
    this.binary = bits;
  }

  // Property accessors

  public long getID() {
    return this.ID;
  }

  public void setID(long blobId) {
    this.ID = blobId;
  }

  public Blob getBinaryBlob() {
    return this.binary;
  }

  public void setBinaryBlob(Blob bits) {
    this.binary = bits;
  }

  public Date getCreationDate() {
    return this.creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public Date getLastUpdatedTime() {
    return this.lastUpdatedTime;
  }

  public void setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

  public Set getUpdates() {
    return this.updates;
  }

  public void setUpdates(Set nwDmUpdates) {
    this.updates = nwDmUpdates;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

}