/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/Software.java,v 1.9 2008/09/02 03:31:41 zhao Exp $
 * $Revision: 1.9 $
 * $Date: 2008/09/02 03:31:41 $
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
package com.npower.dm.core;

import java.util.Date;
import java.util.Set;

import com.npower.dm.management.SoftwareTrackingType;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/09/02 03:31:41 $
 */
public interface Software {
  
  public static final String STATUS_TEST = "test";
  public static final String STATUS_RELEASE = "release";
  
  public static final String LICENSE_TYPE_FREE = "free";
  
  public static final String LICENSE_TYPE_TRILA = "trial";

  public abstract long getId();

  /**
   * @param softwareId
   */
  public abstract void setId(long softwareId);

  /**
   * Return primary category
   * @return
   */
  public abstract SoftwareCategory getCategory();
  
  /**
   * Return all of categories
   * @return
   */
  public abstract Set<SoftwareCategory> getCategoryies();

  /**
   * @return
   */
  public abstract SoftwareVendor getVendor();

  /**
   * @param vendor
   */
  public abstract void setVendor(SoftwareVendor vendor);

  /**
   * @return
   */
  public abstract String getExternalId();

  /**
   * @param externalId
   */
  public abstract void setExternalId(String externalId);

  /**
   * @return
   */
  public abstract String getName();

  /**
   * @param name
   */
  public abstract void setName(String name);

  /**
   * @return
   */
  public abstract String getVersion();

  /**
   * @param version
   */
  public abstract void setVersion(String version);

  /**
   * @return
   */
  public abstract String getLicenseType();

  /**
   * @param licenseType
   */
  public abstract void setLicenseType(String licenseType);

  /**
   * Return status of package
   * @return
   */
  public String getStatus();
  
  /**
   * Set status package
   * @param status
   */
  public void setStatus(String status);

  /**
   * @return
   */
  public abstract String getDescription();

  /**
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * @return
   */
  public Date getCreatedTime();

  /**
   * @return
   */
  public Date getLastUpdatedTime();

  /**
   * @return
   */
  public String getCreatedBy();

  /**
   * @return
   */
  public String getLastUpdatedBy();

  /**
   * @return
   */
  public long getChangeVersion();
  
  /**
   * @return
   */
  public abstract Set<SoftwarePackage> getPackages();

  /**
   * @param packages
   */
  public abstract void setPackages(Set<SoftwarePackage> packages);

  /**
   * @return
   */
  public abstract Set<? extends SoftwareScreenShot> getScreenShoots();

  /**
   * @param screenShoots
   */
  public abstract void setScreenShoots(Set<? extends SoftwareScreenShot> screenShoots);

  /**
   * 返回所有的Rating值 
   * @return
   */
  public abstract long getRating(SoftwareTrackingType type);
  
  /**
   * Return number of view 
   * @return
   */
  public abstract long getRatingView();
  
  /**
   * Return number of download 
   * @return
   */
  public abstract long getRatingDownload();
  
  /**
   * Return number of installed.
   * @return
   */
  public abstract long getRatingInstall();
  
}