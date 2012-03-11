/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/ProfileConvertorFactory.java,v 1.6 2008/10/29 03:19:41 zhao Exp $
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
package com.npower.cp.convertor;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/10/29 03:19:41 $
 */
public class ProfileConvertorFactory {

  /**
   * Private constructor
   */
  private ProfileConvertorFactory() {
    super();
  }
  
  /**
   * Return an instance of ProfileConvertorFactory
   * @return
   */
  public static ProfileConvertorFactory newInstance() {
    return new ProfileConvertorFactory();
  }
  
  /**
   * Return a convertor for the profile.
   * @param profile
   * @return
   */
  public ProfileConvertor getProfileConvertor(ProfileConfig profile, ValueFetcher<ProfileCategory, String, String> valueFetcher) throws DMException {
    if (profile == null) {
      throw new NullPointerException("ProfileConfig is null, could not convert NULL ProfileConfig into OMAClientProvSettings.");
   }
   ProfileTemplate template = profile.getProfileTemplate();
   ProfileCategory category = template.getProfileCategory();
   
   if (ProfileCategory.NAP_CATEGORY_NAME.equalsIgnoreCase(category.getName())) {
      ProfileConvertor convertor = new ProfileConvertor4NAP();
      convertor.setValueFetcher(valueFetcher);
      return convertor;
   } else if (ProfileCategory.PROXY_CATEGORY_NAME.equalsIgnoreCase(category.getName())) {
     ProfileConvertor convertor = new ProfileConvertor4Proxy();
     convertor.setValueFetcher(valueFetcher);
     return convertor;
   } else if (ProfileCategory.MMS_CATEGORY_NAME.equalsIgnoreCase(category.getName())) {
     ProfileConvertor convertor = new ProfileConvertor4MMS();
     convertor.setValueFetcher(valueFetcher);
     return convertor;
   } else if (ProfileCategory.EMAIL_CATEGORY_NAME.equalsIgnoreCase(category.getName())) {
     ProfileConvertor convertor = new ProfileConvertor4Email();
     convertor.setValueFetcher(valueFetcher);
     return convertor;
   } else if (ProfileCategory.DM_CATEGORY_NAME.equalsIgnoreCase(category.getName())) {
     ProfileConvertor convertor = new ProfileConvertor4DM();
     convertor.setValueFetcher(valueFetcher);
     return convertor;
   } else if (ProfileCategory.SYNC_DS_CATEGORY_NAME.equalsIgnoreCase(category.getName())) {
     ProfileConvertor convertor = new ProfileConvertor4SyncDS();
     convertor.setValueFetcher(valueFetcher);
     return convertor;
   } else if (ProfileCategory.BOOKMARK_CATEGORY_NAME.equalsIgnoreCase(category.getName())) {
     ProfileConvertor convertor = new ProfileConvertor4Bookmark();
     convertor.setValueFetcher(valueFetcher);
     return convertor;
   } else {
     return null;
     //throw new DMException("Un-supported profile category: " + category.getName() + " by OTA Settings");
   }
  }

}
