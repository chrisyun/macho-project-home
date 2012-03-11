/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/FirmwareItem.java,v 1.1 2008/09/05 03:24:40 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/09/05 03:24:40 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.setup.task;

import java.io.Serializable;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/05 03:24:40 $
 */
public class FirmwareItem implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String fromVersion = null;
  private String toVersion = null;
  private String filename = null;
  private String status = null;

  /**
   * 
   */
  public FirmwareItem() {
    super();
  }
  
  /**
   * @return the filename
   */
  public String getFilename() {
    return filename;
  }

  /**
   * @param filename the filename to set
   */
  public void setFilename(String filename) {
    this.filename = filename;
  }

  /**
   * @return the fromVersion
   */
  public String getFromVersion() {
    return fromVersion;
  }

  /**
   * @param fromVersion the fromVersion to set
   */
  public void setFromVersion(String fromVersion) {
    this.fromVersion = fromVersion;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return the toVersion
   */
  public String getToVersion() {
    return toVersion;
  }

  /**
   * @param toVersion the toVersion to set
   */
  public void setToVersion(String toVersion) {
    this.toVersion = toVersion;
  }

  

}
