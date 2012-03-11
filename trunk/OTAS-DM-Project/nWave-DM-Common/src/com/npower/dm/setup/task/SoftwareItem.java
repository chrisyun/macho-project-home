/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/SoftwareItem.java,v 1.2 2008/12/16 04:19:41 chenlei Exp $
 * $Revision: 1.2 $
 * $Date: 2008/12/16 04:19:41 $
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

package com.npower.dm.setup.task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/12/16 04:19:41 $
 */

public class SoftwareItem {

  private String externalId;
  private String vendor;
  private String category;
  private String name;
  private String version;
  private String status;
  private String licenseType;
  private String description;

  private List<SoftwarePackageItem> softwarePackageItem = new ArrayList<SoftwarePackageItem>();

  public String getVendor() {
    return vendor;
  }
  public void setVendor(String vendor) {
    this.vendor = vendor;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  public String getExternalId() {
    return externalId;
  }
  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }
  public String getLicenseType() {
    return licenseType;
  }
  public void setLicenseType(String licenseType) {
    this.licenseType = licenseType;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public List<SoftwarePackageItem> getSoftwarePackage() {
    return softwarePackageItem;
  }
  public void addSoftwarePackage(SoftwarePackageItem softwarePackageItem) {
    this.softwarePackageItem.add(softwarePackageItem);
  }
  public String getCategory() {
    return category;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  
}
