/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProfileParameter.java,v 1.3 2008/12/11 05:21:29 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/12/11 05:21:29 $
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

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttribute;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/12/11 05:21:29 $
 */
public abstract class AbstractProfileParameter implements java.io.Serializable {

  // Fields

  private long profileParameterId;

  private ProfileAssignment profileAssignment;

  private DDFNode ddfNode;

  private ProfileAttribute profileAttribute;

  private String parameterType;

  private String dmTreePath;

  private long paramIndex;

  private String itemDataKind;

  private String updateId;

  private Clob rawData;

  private Blob binaryData;

  private String MFormat;

  private String MType;

  private String paramName;

  // Constructors

  /** default constructor */
  public AbstractProfileParameter() {
  }

  /** minimal constructor */
  public AbstractProfileParameter(DDFNode nwDmDdfNode, String parameterType, String dmTreePath) {
    this.ddfNode = nwDmDdfNode;
    this.parameterType = parameterType;
    this.dmTreePath = dmTreePath;
  }

  /** full constructor */
  public AbstractProfileParameter(ProfileAssignment nwDmProfileAssignment, DDFNode nwDmDdfNode,
      ProfileAttribute nwDmProfileAttribute, String parameterType, String dmTreePath, long paramIndex,
      String itemDataKind, String updateId, Clob rawData, Blob binaryData, String MFormat, String MType,
      String paramName) {
    this.profileAssignment = nwDmProfileAssignment;
    this.ddfNode = nwDmDdfNode;
    this.profileAttribute = nwDmProfileAttribute;
    this.parameterType = parameterType;
    this.dmTreePath = dmTreePath;
    this.paramIndex = paramIndex;
    this.itemDataKind = itemDataKind;
    this.updateId = updateId;
    this.rawData = rawData;
    this.binaryData = binaryData;
    this.MFormat = MFormat;
    this.MType = MType;
    this.paramName = paramName;
  }

  // Property accessors

  public long getProfileParameterId() {
    return this.profileParameterId;
  }

  public void setProfileParameterId(long profileParameterId) {
    this.profileParameterId = profileParameterId;
  }

  public ProfileAssignment getProfileAssignment() {
    return this.profileAssignment;
  }

  public void setProfileAssignment(ProfileAssignment nwDmProfileAssignment) {
    this.profileAssignment = nwDmProfileAssignment;
  }

  public DDFNode getDdfNode() {
    return this.ddfNode;
  }

  public void setDdfNode(DDFNode nwDmDdfNode) {
    this.ddfNode = nwDmDdfNode;
  }

  public ProfileAttribute getProfileAttribute() {
    return this.profileAttribute;
  }

  public void setProfileAttribute(ProfileAttribute nwDmProfileAttribute) {
    this.profileAttribute = nwDmProfileAttribute;
  }

  public String getParameterType() {
    return this.parameterType;
  }

  public void setParameterType(String parameterType) {
    this.parameterType = parameterType;
  }

  public String getDmTreePath() {
    return this.dmTreePath;
  }

  public void setDmTreePath(String dmTreePath) {
    this.dmTreePath = dmTreePath;
  }

  public long getParamIndex() {
    return this.paramIndex;
  }

  public void setParamIndex(long paramIndex) {
    this.paramIndex = paramIndex;
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

  public String getParamName() {
    return this.paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

}