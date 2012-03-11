/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ProfileCategory.java,v 1.11 2008/12/12 10:37:17 zhao Exp $
 * $Revision: 1.11 $
 * $Date: 2008/12/12 10:37:17 $
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

/**
 * Represent a ProfileCategory.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.11 $ $Date: 2008/12/12 10:37:17 $
 */
public interface ProfileCategory {

  /**
   * Category Name: Proxy
   */
  public static final String PROXY_CATEGORY_NAME = "Proxy";
  
  /**
   * Category Name: NAP
   */
  public static final String NAP_CATEGORY_NAME = "NAP";
  
  /**
   * Category Name: Email
   */
  public static final String EMAIL_CATEGORY_NAME = "Email";
  
  /**
   * Category Name: MMS
   */
  public static final String MMS_CATEGORY_NAME = "MMS";
  
  /**
   * Category Name: SyncDS
   */
  public static final String SYNC_DS_CATEGORY_NAME = "SyncDS";
  
  /**
   * Category Name: DM
   */
  public static final String DM_CATEGORY_NAME = "DM";
  
  /**
   * Category Name: POC
   */
  public static final String POC_CATEGORY_NAME = "POC";
  
  /**
   * Category Name: IMPS
   */
  public static final String IMPSP_CATEGORY_NAME = "IMPS";
  
  /**
   * Category Name: PushMail
   */
  public static final String PUSH_MAIL_CATEGORY_NAME = "PushMail";

  /**
   * Category Name: Mobile Software Management
   */
  public static final String MSM_CATEGORY_NAME = "MSM";
  
  /**
   * Category Name: bookmark
   */
  public static final String BOOKMARK_CATEGORY_NAME = "Bookmark";

  /**
   * Category Name: 3GPP Streaming
   */
  public static final String STREAMING_3GPP_CATEGORY_NAME = "Streaming";

  /**
   * Category Name: Policy Management
   */
  public static final String POLICY_MGMT_NAME = "PolicyMgmt";

  /**
   * Category Name: LookAndFeel
   */
  public static final String LOOK_AND_FEEL_NAME = "LookAndFeel";
  
  /**
   * Return ID of this ProfileCategory.
   * 
   * @return
   */
  public abstract long getID();

  /**
   * Return name of the ProfileCatefory
   * @return
   */
  public abstract String getName();

  /**
   * Setter of the name.
   * 
   * @param name
   */
  public abstract void setName(String name);

  /**
   * Return description.
   * 
   * @return
   */
  public abstract String getDescription();

  /**
   * Setter of the name.
   * 
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * Return the LastUpdateBy
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * Setter of the lastUpdateBy
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Return the time of lastUpdated.
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * Return the changeVersion.
   * @return
   */
  public abstract long getChangeVersion();

  /**
   * Return all of ProfileTemplate which belongs to this ProfileCategory.
   * 
   * @return Return a <code>Set</code> of the {$link com.npower.dm.ProfileTemplate} objects.
   */
  public abstract Set<ProfileTemplate> getProfileTemplates();

}