/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDMCommand.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractDMCommand implements java.io.Serializable {

  // Fields

  private long dmCmdId;

  private DMCommandPackage nwDmDmCmdPkg;

  private DMCommand nwDmDmCmd;

  private String cmdType;

  private String name;

  private String target;

  private String MFormat;

  private String MType;

  private String itemDataKind;

  private String updateId;

  private String rawData;

  private Blob binaryData;

  private String alertCode;

  private long cmdIndex;

  private Set nwDmReplCmdItems = new HashSet(0);

  private Set nwDmDmCmds = new HashSet(0);

  private Set nwDmGetCmdItems = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDMCommand() {
  }

  /** minimal constructor */
  public AbstractDMCommand(String cmdType) {
    this.cmdType = cmdType;
  }

  /** full constructor */
  public AbstractDMCommand(DMCommandPackage nwDmDmCmdPkg, DMCommand nwDmDmCmd, String cmdType, String name,
      String target, String MFormat, String MType, String itemDataKind, String updateId, String rawData,
      Blob binaryData, String alertCode, long cmdIndex, Set nwDmReplCmdItems, Set nwDmDmCmds, Set nwDmGetCmdItems) {
    this.nwDmDmCmdPkg = nwDmDmCmdPkg;
    this.nwDmDmCmd = nwDmDmCmd;
    this.cmdType = cmdType;
    this.name = name;
    this.target = target;
    this.MFormat = MFormat;
    this.MType = MType;
    this.itemDataKind = itemDataKind;
    this.updateId = updateId;
    this.rawData = rawData;
    this.binaryData = binaryData;
    this.alertCode = alertCode;
    this.cmdIndex = cmdIndex;
    this.nwDmReplCmdItems = nwDmReplCmdItems;
    this.nwDmDmCmds = nwDmDmCmds;
    this.nwDmGetCmdItems = nwDmGetCmdItems;
  }

  // Property accessors

  public long getDmCmdId() {
    return this.dmCmdId;
  }

  public void setDmCmdId(long dmCmdId) {
    this.dmCmdId = dmCmdId;
  }

  public DMCommandPackage getNwDmDmCmdPkg() {
    return this.nwDmDmCmdPkg;
  }

  public void setNwDmDmCmdPkg(DMCommandPackage nwDmDmCmdPkg) {
    this.nwDmDmCmdPkg = nwDmDmCmdPkg;
  }

  public DMCommand getNwDmDmCmd() {
    return this.nwDmDmCmd;
  }

  public void setNwDmDmCmd(DMCommand nwDmDmCmd) {
    this.nwDmDmCmd = nwDmDmCmd;
  }

  public String getCmdType() {
    return this.cmdType;
  }

  public void setCmdType(String cmdType) {
    this.cmdType = cmdType;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTarget() {
    return this.target;
  }

  public void setTarget(String target) {
    this.target = target;
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

  public String getRawData() {
    return this.rawData;
  }

  public void setRawData(String rawData) {
    this.rawData = rawData;
  }

  public Blob getBinaryData() {
    return this.binaryData;
  }

  public void setBinaryData(Blob binaryData) {
    this.binaryData = binaryData;
  }

  public String getAlertCode() {
    return this.alertCode;
  }

  public void setAlertCode(String alertCode) {
    this.alertCode = alertCode;
  }

  public long getCmdIndex() {
    return this.cmdIndex;
  }

  public void setCmdIndex(long cmdIndex) {
    this.cmdIndex = cmdIndex;
  }

  public Set getNwDmReplCmdItems() {
    return this.nwDmReplCmdItems;
  }

  public void setNwDmReplCmdItems(Set nwDmReplCmdItems) {
    this.nwDmReplCmdItems = nwDmReplCmdItems;
  }

  public Set getNwDmDmCmds() {
    return this.nwDmDmCmds;
  }

  public void setNwDmDmCmds(Set nwDmDmCmds) {
    this.nwDmDmCmds = nwDmDmCmds;
  }

  public Set getNwDmGetCmdItems() {
    return this.nwDmGetCmdItems;
  }

  public void setNwDmGetCmdItems(Set nwDmGetCmdItems) {
    this.nwDmGetCmdItems = nwDmGetCmdItems;
  }

}