/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/device/DeviceSyncItem.java,v 1.1 2008/11/18 09:24:52 zhaowx Exp $
 * $Revision: 1.1 $
 * $Date: 2008/11/18 09:24:52 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.unicom.sync.device;

import com.npower.unicom.sync.SyncAction;
import com.npower.unicom.sync.SyncItem;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.1 $ $Date: 2008/11/18 09:24:52 $
 */

public class DeviceSyncItem extends SyncItem {

  private String imei = null;
  private String imsi = null;
  private String msisdn = null;
  private String companyName = null;
  private String terminalModel = null;
  private String version = null;
  
  /**
   * 
   */
  public DeviceSyncItem() {
    super();
  }

  /**
   * @param id
   * @param action
   */
  public DeviceSyncItem(String id, SyncAction action) {
    super(id, action);   
  }

  public String getImei() {
    return imei;
  }

  public void setImei(String imei) {
    this.imei = imei;
  }

  public String getImsi() {
    return imsi;
  }

  public void setImsi(String imsi) {
    this.imsi = imsi;
  }

  public String getMsisdn() {
    return msisdn;
  }

  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getTerminalModel() {
    return terminalModel;
  }

  public void setTerminalModel(String terminalModel) {
    this.terminalModel = terminalModel;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  
}
