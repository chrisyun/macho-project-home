/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProfileValueItem.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
import java.sql.Clob;

import com.npower.dm.core.ProfileAttributeValue;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractProfileValueItem implements java.io.Serializable {

  // Fields
  private long ID;

  private String itemDataKind;

  private String updateId;

  private Clob rawData;

  private Blob binaryData;

  private String MFormat;

  private String MType;

  private ProfileAttributeValue profileAttribValue;

  // Constructors

  /** default constructor */
  public AbstractProfileValueItem() {
    super();
  }

  /** full constructor */
  public AbstractProfileValueItem(ProfileAttributeValue profileAttribValue) {
    this.profileAttribValue = profileAttribValue;
  }

  // Property accessors

  public long getID() {
    return this.ID;
  }

  public void setID(long id) {
    this.ID = id;
  }

  public ProfileAttributeValue getProfileAttribValue() {
    return this.profileAttribValue;
  }

  public void setProfileAttribValue(ProfileAttributeValue nwDmProfileAttribValue) {
    this.profileAttribValue = nwDmProfileAttribValue;
  }

  // Property accessors

  public String getItemDataKind() {
    return this.itemDataKind;
  }

  public void setItemDataKind(String itemDataKind) {
    this.itemDataKind = itemDataKind;
  }

  public String getUpdateId() {
    return this.updateId;
  }

  public void setUpdateId(String updateId) {
    this.updateId = updateId;
  }

  public Clob getRawData() {
    return this.rawData;
  }

  public void setRawData(Clob rawData) {
    this.rawData = rawData;
  }

  public Blob getBinaryData() {
    return this.binaryData;
  }

  public void setBinaryData(Blob binaryData) {
    this.binaryData = binaryData;
  }

  public String getMFormat() {
    return this.MFormat;
  }

  public void setMFormat(String MFormat) {
    this.MFormat = MFormat;
  }

  public String getMType() {
    return this.MType;
  }

  public void setMType(String MType) {
    this.MType = MType;
  }

  /*
   * 
   * public boolean equals(Object other) { if ( (this == other ) ) return true;
   * if ( (other == null ) ) return false; if ( !(other instanceof
   * ProfileValueItemId) ) return false; ProfileValueItemId castOther = (
   * ProfileValueItemId ) other;
   * 
   * return
   * (this.getProfileAttributeValueId()==castOther.getProfileAttributeValueId()) && (
   * (this.getItemDataKind()==castOther.getItemDataKind()) || (
   * this.getItemDataKind()!=null && castOther.getItemDataKind()!=null &&
   * this.getItemDataKind().equals(castOther.getItemDataKind()) ) ) && (
   * (this.getUpdateId()==castOther.getUpdateId()) || ( this.getUpdateId()!=null &&
   * castOther.getUpdateId()!=null &&
   * this.getUpdateId().equals(castOther.getUpdateId()) ) ) && (
   * (this.getRawData()==castOther.getRawData()) || ( this.getRawData()!=null &&
   * castOther.getRawData()!=null &&
   * this.getRawData().equals(castOther.getRawData()) ) ) && (
   * (this.getBinaryData()==castOther.getBinaryData()) || (
   * this.getBinaryData()!=null && castOther.getBinaryData()!=null &&
   * this.getBinaryData().equals(castOther.getBinaryData()) ) ) && (
   * (this.getMFormat()==castOther.getMFormat()) || ( this.getMFormat()!=null &&
   * castOther.getMFormat()!=null &&
   * this.getMFormat().equals(castOther.getMFormat()) ) ) && (
   * (this.getMType()==castOther.getMType()) || ( this.getMType()!=null &&
   * castOther.getMType()!=null && this.getMType().equals(castOther.getMType()) ) ); }
   * 
   * public int hashCode() { int result = 17;
   * 
   * result = 37 * result + (int) this.getProfileAttributeValueId(); result = 37 *
   * result + ( getItemDataKind() == null ? 0 :
   * this.getItemDataKind().hashCode() ); result = 37 * result + ( getUpdateId() ==
   * null ? 0 : this.getUpdateId().hashCode() ); result = 37 * result + (
   * getRawData() == null ? 0 : this.getRawData().hashCode() ); result = 37 *
   * result + ( getBinaryData() == null ? 0 : this.getBinaryData().hashCode() );
   * result = 37 * result + ( getMFormat() == null ? 0 :
   * this.getMFormat().hashCode() ); result = 37 * result + ( getMType() == null ?
   * 0 : this.getMType().hashCode() ); return result; }
   * 
   */

}