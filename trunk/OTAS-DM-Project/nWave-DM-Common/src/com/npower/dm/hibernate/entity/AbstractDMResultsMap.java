/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDMResultsMap.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractDMResultsMap implements java.io.Serializable {

  // Fields

  private DMResultsMapID id;

  private DMCMDResponse nwDmDmCmdResponse;

  private String itemDataKind;

  private String updateId;

  private Clob rawData;

  private Blob binaryData;

  private String MFormat;

  private String MType;

  // Constructors

  /** default constructor */
  public AbstractDMResultsMap() {
  }

  /** minimal constructor */
  public AbstractDMResultsMap(DMResultsMapID id, DMCMDResponse nwDmDmCmdResponse) {
    this.id = id;
    this.nwDmDmCmdResponse = nwDmDmCmdResponse;
  }

  /** full constructor */
  public AbstractDMResultsMap(DMResultsMapID id, DMCMDResponse nwDmDmCmdResponse, String itemDataKind, String updateId,
      Clob rawData, Blob binaryData, String MFormat, String MType) {
    this.id = id;
    this.nwDmDmCmdResponse = nwDmDmCmdResponse;
    this.itemDataKind = itemDataKind;
    this.updateId = updateId;
    this.rawData = rawData;
    this.binaryData = binaryData;
    this.MFormat = MFormat;
    this.MType = MType;
  }

  // Property accessors

  public DMResultsMapID getId() {
    return this.id;
  }

  public void setId(DMResultsMapID id) {
    this.id = id;
  }

  public DMCMDResponse getNwDmDmCmdResponse() {
    return this.nwDmDmCmdResponse;
  }

  public void setNwDmDmCmdResponse(DMCMDResponse nwDmDmCmdResponse) {
    this.nwDmDmCmdResponse = nwDmDmCmdResponse;
  }

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

}