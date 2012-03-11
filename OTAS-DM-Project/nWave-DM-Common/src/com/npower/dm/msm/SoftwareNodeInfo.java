/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/SoftwareNodeInfo.java,v 1.1 2008/11/10 14:30:38 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/11/10 14:30:38 $
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
package com.npower.dm.msm;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/11/10 14:30:38 $
 */
public class SoftwareNodeInfo {
  
  private String vendorName = null;
  private String softwareName = null;
  private String softwareVersion = null;
  private String nodePath = null;
  private boolean deletable = false;
  
  private String softwareExternalId = null;

  /**
   * 
   */
  public SoftwareNodeInfo() {
    super();
  }

  /**
   * @return the vendorName
   */
  public String getVendorName() {
    return vendorName;
  }

  /**
   * @param vendorName the vendorName to set
   */
  public void setVendorName(String vendorName) {
    this.vendorName = vendorName;
  }

  /**
   * @return the softwareName
   */
  public String getSoftwareName() {
    return softwareName;
  }

  /**
   * @param softwareName the softwareName to set
   */
  public void setSoftwareName(String softwareName) {
    this.softwareName = softwareName;
  }

  /**
   * @return the softwareVersion
   */
  public String getSoftwareVersion() {
    return softwareVersion;
  }

  /**
   * @param softwareVersion the softwareVersion to set
   */
  public void setSoftwareVersion(String softwareVersion) {
    this.softwareVersion = softwareVersion;
  }

  /**
   * @return the nodePath
   */
  public String getNodePath() {
    return nodePath;
  }

  /**
   * @param nodePath the nodePath to set
   */
  public void setNodePath(String nodePath) {
    this.nodePath = nodePath;
  }

  /**
   * @return the deletable
   */
  public boolean isDeletable() {
    return deletable;
  }

  /**
   * @param deletable the deletable to set
   */
  public void setDeletable(boolean deletable) {
    this.deletable = deletable;
  }

  /**
   * @return the softwareExternalId
   */
  public String getSoftwareExternalId() {
    return softwareExternalId;
  }

  /**
   * @param softwareExternalId the softwareExternalId to set
   */
  public void setSoftwareExternalId(String softwareExternalId) {
    this.softwareExternalId = softwareExternalId;
  }

}
