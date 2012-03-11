/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileAssignmentEntity.java,v 1.9 2007/04/11 10:37:58 zhao Exp $
 * $Revision: 1.9 $
 * $Date: 2007/04/11 10:37:58 $
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
package com.npower.dm.hibernate.entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.NameValuePair;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2007/04/11 10:37:58 $
 */
public class ProfileAssignmentEntity extends AbstractProfileAssignment implements java.io.Serializable, Comparable {

  // Constructors

  /** default constructor */
  public ProfileAssignmentEntity() {
    super();
  }

  /** minimal constructor */
  public ProfileAssignmentEntity(ProfileConfig profileConfig, Device device) {
    super(profileConfig, device);
  }

  /**
   * Find a ProfileAttributeValueEntity by the name from the
   * ProfileAssignmentEntity.
   * 
   * If nothing found, will return null.
   * 
   * @param attributeName
   * @return
   * @throws DMException
   */
  public ProfileAttributeValue getProfileAttributeValue(String attributeName) throws DMException {

    Set vMaps = this.getProfileAssignValues();
    for (Iterator i = vMaps.iterator(); i.hasNext();) {
      ProfileAssignmentValue vMap = (ProfileAssignmentValue) i.next();
      ProfileAttributeValue v = vMap.getProfileAttribValue();
      if (attributeName.equals(v.getProfileAttribute().getName())) {
        return v;
      }
    }
    // Not found
    return null;
  }


  /**
   * Return all of ProfileAttributeValues owened by the ProfileAssignmentValue.
   * 
   * @return ProfileAttributeValueEntity[]
   */
  public ProfileAttributeValue[] getAttributeValues() {
    Set vMaps = this.getProfileAssignValues();
    ProfileAttributeValue[] result = new ProfileAttributeValue[vMaps.size()];
    int j = 0;
    for (Iterator i = vMaps.iterator(); i.hasNext(); j++) {
      ProfileAssignmentValue vMap = (ProfileAssignmentValue) i.next();
      ProfileAttributeValue av = vMap.getProfileAttribValue();
      result[j] = av;
    }
    return result;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileAssignment#getAllAttributeValues()
   */
  public List<NameValuePair> getAllOfNameValuePairs() throws DMException {
    try {
        List<NameValuePair> result = new ArrayList<NameValuePair>();
        ProfileTemplate template = this.getProfileConfig().getProfileTemplate();
        Set<ProfileAttribute> attrs = template.getProfileAttributes();
        for (ProfileAttribute attribute: attrs) {
            String attrName = attribute.getName();
            // Load form profile assignment
            ProfileAttributeValue attrValue = this.getProfileAttributeValue(attrName);
            String value = null;
            if (attrValue == null) {
               // Load from ProfileConfig
               attrValue = this.getProfileConfig().getProfileAttributeValue(attrName);
               if (attrValue == null) {
                  // Load from ProfileTemplate
                  value = attribute.getDefaultValue();
               }
            }
            if (value == null && attrValue != null) {
               if (ProfileAttributeType.TYPE_BINARY.equalsIgnoreCase(attribute.getProfileAttribType().getName())) {
                  long length = 0;
                  if (attrValue.getBinaryData() != null) {
                     length = attrValue.getBinaryData().length();
                  }
                  value = "[Binary] " + length + " Bytes"; 
               } else {
                 value = attrValue.getStringValue();
               }
            }
            NameValuePair nameValue = new NameValuePair(attrName, value);
            result.add(nameValue);
        }
        return result;
    } catch (SQLException e) {
      throw new DMException("Could not load attribute value in profile assignment: " + this.getID(), e);
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(T)
   */
  public int compareTo(Object o) {
    if (o != null && o instanceof ProfileAssignment) {
       ProfileAssignment other = (ProfileAssignment)o;
       return (int)(this.getID() - other.getID());
    }
    return -1;
  }
  
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    try {
        buffer.append("[ ");
        List<NameValuePair> values = this.getAllOfNameValuePairs();
        for (NameValuePair nameValue: values) {
            buffer.append(nameValue.getName());
            buffer.append("=");
            buffer.append(nameValue.getValue());
            buffer.append(", ");
        }
        buffer.append(" ]");
    } catch (DMException e) {
    }
    return buffer.toString();
  }


}
