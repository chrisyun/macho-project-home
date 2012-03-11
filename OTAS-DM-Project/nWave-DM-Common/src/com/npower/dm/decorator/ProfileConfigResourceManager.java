/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/ProfileConfigResourceManager.java,v 1.1 2008/01/22 06:52:42 LAH Exp $
 * $Revision: 1.1 $
 * $Date: 2008/01/22 06:52:42 $
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

package com.npower.dm.decorator;

import com.npower.dm.core.ProfileConfig;
import com.npower.dm.util.MessageResources;

/**
 * @author Liu AiHui
 * @version $Revision: 1.1 $ $Date: 2008/01/22 06:52:42 $
 */

public class ProfileConfigResourceManager extends PropertyResourceManager<ProfileConfig> {

  /**
   * 
   */
  public ProfileConfigResourceManager() {
    super();
  }

  /**
   * 
   */
  public ProfileConfigResourceManager(MessageResources resources) {
    super(resources);
  }
  
  @Override
  public String getKeyPrefix(ProfileConfig target) {
    return "meta.profile_config";
  }

}
