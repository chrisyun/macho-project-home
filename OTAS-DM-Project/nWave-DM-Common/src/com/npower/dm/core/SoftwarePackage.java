/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/SoftwarePackage.java,v 1.12 2008/09/10 09:59:42 zhao Exp $
 * $Revision: 1.12 $
 * $Date: 2008/09/10 09:59:42 $
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

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.npower.dm.hibernate.entity.DMBinary;
import com.npower.dm.hibernate.entity.VSoftwareTrackLogAll;
import com.npower.dm.management.SoftwareTrackingType;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.12 $ $Date: 2008/09/10 09:59:42 $
 */
public interface SoftwarePackage {

  /**
   * @return
   */
  public abstract long getId();

  /**
   * @param packageId
   */
  public abstract void setId(long packageId);

  /**
   * @return
   */
  public abstract DMBinary getBinary();

  /**
   * @param binary
   */
  public abstract void setBinary(DMBinary binary);

  /**
   * @return
   */
  public abstract Software getSoftware();

  /**
   * @param software
   */
  public abstract void setSoftware(Software software);

  /**
   * @return
   */
  public abstract String getLanguage();

  /**
   * @param language
   */
  public abstract void setLanguage(String language);

  /**
   * @return
   */
  public abstract String getMimeType();

  /**
   * @param mimeType
   */
  public abstract void setMimeType(String mimeType);

  /**
   * @return
   */
  public abstract String getUrl();

  /**
   * @param url
   */
  public abstract void setUrl(String url);

  /**
   * @return
   */
  public abstract String getBlobFilename();

  /**
   * @param blobFilename
   */
  public abstract void setBlobFilename(String blobFilename);

  /**
   * @return Returns the installationOptions.
   */
  public String getInstallationOptions();

  /**
   * @param installationOptions The installationOptions to set.
   */
  public void setInstallationOptions(String installationOptions);

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
  public String getName();

  /**
   * @param name
   */
  public void setName(String name);

  /**
   * @return
   */
  public String getDescription();

  /**
   * @param description
   */
  public void setDescription(String description);

  /**
   * @return
   */
  public abstract Date getPublishedTime();

  /**
   * @param publichedTime
   */
  public abstract void setPublishedTime(Date publichedTime);

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
  public abstract Set<? extends VSoftwareTrackLogAll> getRatings();

  /**
   * @param ratings
   */
  public abstract void setRatings(Set<? extends VSoftwareTrackLogAll> ratings);
  
  /**
   * @return
   */
  public abstract Collection<ModelClassification> getModelClassifications();
  
  /**
   * Return target Model family external ID
   * @return
   */
  public abstract Set<String> getModelFamilyExternalIDs();

  /**
   * Return target Models
   * @return
   */
  public abstract Set<Model> getModels();
  
  /**
   * Size of software package in bytes.
   * @return
   * @throws Exception
   */
  public abstract int getSize() throws Exception;
  
  /**
   * Return value of rating 
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