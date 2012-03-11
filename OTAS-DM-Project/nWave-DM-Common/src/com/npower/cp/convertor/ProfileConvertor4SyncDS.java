/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/ProfileConvertor4SyncDS.java,v 1.3 2007/11/22 10:10:45 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/11/22 10:10:45 $
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
package com.npower.cp.convertor;


import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.wap.nokia.NokiaOTASettings;
import com.npower.wap.omacp.OMAClientProvSettings;
import com.npower.wap.omacp.elements.ApplicationElement;
import com.npower.wap.omacp.elements.NAPDefElement;
import com.npower.wap.omacp.elements.PXLogicalElement;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/11/22 10:10:45 $
 */
public class ProfileConvertor4SyncDS extends AbstractProfileConvertor implements ProfileConvertor {
  
  /**
   * 
   */
  public ProfileConvertor4SyncDS() {
    super();
  }

  /**
   * @return the supportedCategoryName
   */
  public String getSupportedCategoryName() {
    return ProfileCategory.SYNC_DS_CATEGORY_NAME;
  }
  
  // Implements interface methods --------------------------------------------------------------------------
  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ProfileConvertor#convert(com.npower.dm.core.ProfileConfig)
   */
  public OMAClientProvSettings convertToOMAClientProv(ProfileConfig profile) throws DMException {
    ProfileTemplate template = profile.getProfileTemplate();
    ProfileCategory category = template.getProfileCategory();
    if (!this.getSupportedCategoryName().equalsIgnoreCase(category.getName())) {
       throw new DMException("Un-Supported type: " + category.getName() + " by OTA Seetings in ProfileAssignment.");
    }    
    OMAClientProvSettings settings = new OMAClientProvSettings();
    ApplicationElement syncDSElement = null;
    if (profile.getProxyProfile() != null) {
       // Linked NAP Profile
       NAPDefElement napDefElement = convertNAPProfile(profile.getProxyProfile().getNAPProfile());
       settings.addNAPDefElement(napDefElement);
       // Proxy Profile
       PXLogicalElement proxyDefElement = convertProxyProfile(profile.getProxyProfile(), napDefElement);
       settings.addPXLogicalElement(proxyDefElement);
       // SyncDS Profile
       syncDSElement = this.convertSyncDSProfile(profile, napDefElement, proxyDefElement);
    } else {
      // Linked NAP Profile
      NAPDefElement napDefElement = convertNAPProfile(profile.getNAPProfile());
      settings.addNAPDefElement(napDefElement);
      // SyncDS Profile
      syncDSElement = this.convertSyncDSProfile(profile, napDefElement, null);
    }
    
    settings.addApplicationElement(syncDSElement);
    
    return settings;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.AbstractProfileConvertor#convertToNokiaOTA(com.npower.dm.core.ProfileConfig)
   */
  @Override
  public NokiaOTASettings convertToNokiaOTA(ProfileConfig profile) throws DMException {
    throw new DMException("Un-supported convert SyncDS Profile to Nokia OTA Settings");
  }
}
