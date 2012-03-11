/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ClientProvTemplate.java,v 1.6 2008/10/29 03:19:41 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2008/10/29 03:19:41 $
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
package com.npower.dm.core;

import java.util.Date;

import com.npower.cp.OTAException;
import com.npower.cp.ProvisioningDoc;
import com.npower.wap.nokia.NokiaOTASettings;
import com.npower.wap.omacp.OMAClientProvSettings;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/10/29 03:19:41 $
 */
public interface ClientProvTemplate {
  
  public static final String OMA_CP_1_1_ENCODER = "omacp";
  public static final String NOKIA_OTA_7_0_ENCODER = "nokia_ota";
  

  /**
   * Return internal ID
   * @return
   */
  public abstract Long getID();

  /**
   * Set internal ID
   * @param templateId
   */
  public abstract void setID(Long templateId);

  /**
   * Return external ID
   * @return
   */
  public abstract String getExternalID();

  /**
   * Set external ID
   * @param externalID
   */
  public abstract void setExternalID(String externalID);

  /**
   * Return Category of this template
   * @return
   */
  public abstract ProfileCategory getProfileCategory();

  /**
   * Set category for this template
   * @param profileCategory
   */
  public abstract void setProfileCategory(ProfileCategory profileCategory);

  /**
   * Return name of template
   * @return
   */
  public abstract String getName();

  /**
   * Set name of template
   * @param name
   */
  public abstract void setName(String name);

  /**
   * Return description of this template
   * @return
   */
  public abstract String getDescription();

  /**
   * Set description of this template
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * Return content of this template
   * @return
   */
  public abstract String getContent();

  /**
   * Set content of this template
   * @param content
   */
  public abstract void setContent(String content);

  /**
   * @return the encoder
   */
  public String getEncoder();

  /**
   * @param encoder the encoder to set
   */
  public void setEncoder(String encoder);

  /**
   * return last updated by
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * Set last updated by
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Return last updated time
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * Set last updated time
   * @param lastUpdatedTime
   */
  public abstract void setLastUpdatedTime(Date lastUpdatedTime);

  /**
   * Return change version
   * @return
   */
  public abstract Long getChangeVersion();

  /**
   * Set change version
   * @param changeVersion
   */
  public abstract void setChangeVersion(Long changeVersion);

  /**
   * @return
   */
  //public abstract Set<ModelClientProvMapEntity> getModelClientProvMaps();

  /**
   * @param clientProvMaps
   */
  //public abstract void setModelClientProvMaps(Set<ModelClientProvMapEntity> clientProvMaps);

  /**
   * Merge this template with the settings.
   * @param settings
   * @return
   * @throws OTAException
   */
  public ProvisioningDoc merge(OMAClientProvSettings settings) throws OTAException;

  /**
   * Merge this template with the settings.
   * @param profile
   * @return
   * @throws OTAException
   */
  public abstract ProvisioningDoc merge(ProfileConfig profile) throws OTAException;
  
  /**
   * Merge this template with the settings.
   * @param settings
   * @return
   * @throws OTAException
   */
  public ProvisioningDoc merge(NokiaOTASettings settings) throws OTAException;


}