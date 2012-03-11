/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileConfigEntity.java,v 1.8 2008/12/09 04:34:48 zhao Exp $
 * $Revision: 1.8 $
 * $Date: 2008/12/09 04:34:48 $
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

import java.util.Iterator;
import java.util.Set;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2008/12/09 04:34:48 $
 */
public class ProfileConfigEntity extends AbstractProfileConfig implements java.io.Serializable, Comparable<ProfileConfig> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // Constructors

  /** default constructor */
  public ProfileConfigEntity() {
  }

  /** minimal constructor */
  public ProfileConfigEntity(String externalID, String name, Carrier carrier, ProfileTemplate profileTemplate, String profileType) {
    super(externalID, name, carrier, profileTemplate, profileType);
  }

  /** full constructor */
  public ProfileConfigEntity(String name, Carrier carrier, ProfileTemplate profileTemplate, String profileType,
      ProfileConfig napProfile, ProfileConfig proxyProflile) {
    super(name, carrier, profileTemplate, profileType, napProfile, proxyProflile);
  }

  // public methods
  // ***************************************************************************************

  /**
   * Setter of CarrierEntity of the ProfileConfigEntity
   * 
   * @param carrier
   *          CarrierEntity
   */
  public void setCarrier(Carrier carrier) {
    super.setCarrier(carrier);

    // carrier.getProfileConfigs().add(this);
  }

  /**
   * Setter of the ProfileTemplateEntity of the ProfileConfigEntity
   */
  public void setProfileTemplate(ProfileTemplate template) {
    super.setProfileTemplate(template);

    // template.getProfileConfigs().add(this);
  }

  /**
   * Attach a NAP Profile into this Profile. If the ProfileType of
   * NAProfileConfig is NOT NAP, will throw a DMException.
   * 
   * @param NAPProfileConfig
   *          ProfileConfigEntity
   * @throws DMException
   */
  public void setNAPProfile(ProfileConfig NAPProfileConfig) throws DMException {
    if (NAPProfileConfig == null || NAPProfileConfig != null && NAPProfileConfig.getProfileType() != null
        && NAPProfileConfig.getProfileType().equalsIgnoreCase(ProfileConfig.PROFILE_TYPE_NAP)) {
      super.setNAPProfileConfig(NAPProfileConfig);
    } else {
      throw new DMException("The ProfileConfigEntity Type is not a NAP Type.");
    }
  }

  /**
   * Attach a Proxy Profile into this Profile. If the ProfileType of
   * NAProfileConfig is NOT Proxy, will throw a DMException.
   * 
   * @param proxyProfileConfig
   *          ProfileConfigEntity
   * @throws DMException
   */
  public void setProxyProfile(ProfileConfig proxyProfileConfig) throws DMException {
    if (proxyProfileConfig == null || proxyProfileConfig != null
        && proxyProfileConfig.getProfileType().equalsIgnoreCase(ProfileConfig.PROFILE_TYPE_PROXY)) {
      super.setProxyProfileConfig(proxyProfileConfig);
    } else {
      throw new DMException("The ProfileConfigEntity Type is not a Proxy Type.");
    }
  }


  /**
   * Find a ProfileAttributeValueEntity by the name from the
   * ProfileConfigEntity.
   * 
   * If nothing found, will return null.
   * 
   * @param attributeName
   * @return
   * @throws DMException
   */
  public ProfileAttributeValue getProfileAttributeValue(String attributeName) throws DMException {

    Set<ProfileValueMap> vMaps = this.getProfileValueMaps();
    for (Iterator<ProfileValueMap> i = vMaps.iterator(); i.hasNext();) {
      ProfileValueMap vMap = (ProfileValueMap) i.next();
      ProfileAttributeValue v = vMap.getProfileAttribValue();
      if (attributeName.equals(v.getProfileAttribute().getName())) {
        return v;
      }
    }
    // Fix Bug#416
    // Profile Config中提取不到参数时，从Template中提取缺省值
    ProfileTemplate template = this.getProfileTemplate();
    ProfileAttribute attribute = template.getAttributeByAttributeName(attributeName);
    if (attribute != null) {
       Set<ProfileAttributeValue> values = attribute.getProfileAttribValues();
       if (values != null && !values.isEmpty()) {
          // 提取第一个参数值
          return values.iterator().next();
       }
    }
    // Not found
    return null;
  }

  /**
   * Return all of ProfileAttributeValues owened by the ProfileConfigEntity.
   * 
   * @return ProfileAttributeValueEntity[]
   */
  public ProfileAttributeValue[] getAttributeValues() {
    Set<ProfileValueMap> vMaps = this.getProfileValueMaps();
    ProfileAttributeValue[] result = new ProfileAttributeValue[vMaps.size()];
    int j = 0;
    for (Iterator<ProfileValueMap> i = vMaps.iterator(); i.hasNext(); j++) {
      ProfileValueMap vMap = (ProfileValueMap) i.next();
      ProfileAttributeValue av = vMap.getProfileAttribValue();
      result[j] = av;
    }
    return result;
  }

  /**
   * Implements Comparable.compareTo()
   */
  public int compareTo(ProfileConfig o) {
    if (o instanceof ProfileConfig) {
      ProfileConfig other = (ProfileConfig) o;
      return this.getName().compareTo(other.getName());
    } else {
      return -1;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileConfig#getNAPProfile()
   */
  public ProfileConfig getNAPProfile() {
    return super.getNAPProfileConfig();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileConfig#getProxyProfile()
   */
  public ProfileConfig getProxyProfile() {
    return super.getProxyProfileConfig();
  }

}
