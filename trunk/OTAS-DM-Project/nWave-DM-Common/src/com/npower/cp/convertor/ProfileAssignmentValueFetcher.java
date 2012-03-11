/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/ProfileAssignmentValueFetcher.java,v 1.1 2008/07/22 16:13:16 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/07/22 16:13:16 $
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
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileCategory;

/**
 * Fetch value from a profileAssignment
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/07/22 16:13:16 $
 */
public class ProfileAssignmentValueFetcher implements ValueFetcher<ProfileCategory, String, String> {
  
  private ProfileAssignment profileAssignment = null;

  /**
   * Default constructor
   */
  public ProfileAssignmentValueFetcher() {
    super();
  }

  /**
   * Constructor
   * @param profileAssignment
   */
  public ProfileAssignmentValueFetcher(ProfileAssignment profileAssignment) {
    super();
    this.profileAssignment = profileAssignment;
  }

  /**
   * Return profileAssignment
   * @return the profileAssignment
   */
  public ProfileAssignment getProfileAssignment() {
    return profileAssignment;
  }

  /**
   * Set profileAssignment
   * @param profileAssignment the profileAssignment to set
   */
  public void setProfile(ProfileAssignment profile) {
    this.profileAssignment = profile;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ValueFetcher#getValue(java.lang.Object, java.lang.Object)
   */
  public String getValue(ProfileCategory category, String key) throws DMException {
    ProfileAssignment config = this.getProfileAssignment();
    if (!config.getProfileConfig().getProfileTemplate().getProfileCategory().getName().equals(category.getName())) {
       throw new DMException("Mis-matched category of current profileAssignment with category of key, category=" + category.getName() + ", key=" + key);
    }
    
    ProfileAttributeValue attriValue = null;
    //attriValue = assignment.getProfileAttributeValue(attributeName);
    if (attriValue == null) {
       // Get value from profileAssignment config
       attriValue = config.getProfileAttributeValue(key);
    }
    if (attriValue == null) {
       return null;
       //throw new NullPointerException("Missing value of ProfileConfig, ID: " + config.getID() + ", attribute name: " + attributeName);
    }
    ProfileAttribute attribute = attriValue.getProfileAttribute();
    ProfileAttributeType type = attribute.getProfileAttribType();
    if (type != null 
        && ProfileAttributeType.TYPE_BINARY.equals(type.getName())) {
      // Get binary value
      // value = attriValue.getBytes();
      throw new DMException("Un-Supported attribute type: " + type + " by OTA Seetings in ProfileAssignment.");
    }
    // Get text value
    String value = attriValue.getStringValue();
    return value;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ValueFetcher#getValue(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  public String getValue(ProfileCategory category, String key, String defaultValue) throws DMException {
    String value = this.getValue(category, key);
    return (value == null)?defaultValue:value;
  }

}
