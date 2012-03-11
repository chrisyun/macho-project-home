/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/ProfileConfigDecorator.java,v 1.1 2008/01/22 06:52:43 LAH Exp $
 * $Revision: 1.1 $
 * $Date: 2008/01/22 06:52:43 $
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

import java.util.Date;
import java.util.Locale;
import java.util.Set;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;

/**
 * @author Liu AiHui
 * @version $Revision: 1.1 $ $Date: 2008/01/22 06:52:43 $
 */

public class ProfileConfigDecorator extends Decorator<ProfileConfig> implements ProfileConfig {

  /**
   * Default
   */
  public ProfileConfigDecorator(){
    super();
  }
  
  /**
   * Constructor
   */
  public ProfileConfigDecorator(Locale locale, ProfileConfig decoratee, ResourceManager<ProfileConfig> resources) {
    super(decoratee, locale, resources);
  }
  
  public ProfileAttributeValue[] getAttributeValues() {
    return this.getDecoratee().getAttributeValues();
  }

  public Carrier getCarrier() {
    return this.getDecoratee().getCarrier();
  }

  public Set<Carrier> getCarriers() {
    return this.getDecoratee().getCarriers();
  }

  public long getChangeVersion() {
    return this.getDecoratee().getChangeVersion();
  }

  public String getDescription() {
    String index = getDecoratee().getName();
    String orginalValue = getDecoratee().getDescription();
    String value = this.getResourceManager().getResource(this.getLocale(), this, index, "description", orginalValue);
    return value;
  }

  public String getExternalID() {
    return this.getDecoratee().getExternalID();
  }

  public long getID() {
    return this.getDecoratee().getID();
  }

  public boolean getIsUserProfile() {
    return this.getDecoratee().getIsUserProfile();
  }

  public String getLastUpdatedBy() {
    return this.getDecoratee().getLastUpdatedBy();
  }

  public Date getLastUpdatedTime() {
    return this.getDecoratee().getLastUpdatedTime();
  }

  public ProfileConfig getNAPProfile() {
    return this.getDecoratee().getNAPProfile();
  }

  public String getName() {
    String index = getDecoratee().getName();
    String orginalValue = getDecoratee().getName();
    String value = this.getResourceManager().getResource(this.getLocale(), this, index, "name", orginalValue);
    return value;
  }

  public ProfileAttributeValue getProfileAttributeValue(String attributeName) throws DMException {
    return this.getDecoratee().getProfileAttributeValue(attributeName);
  }

  public Set<ProfileConfig> getProfileConfigsReferencedAsNAP() {
    return this.getDecoratee().getProfileConfigsReferencedAsNAP();
  }

  public Set<ProfileConfig> getProfileConfigsReferencedAsProxy() {
    return this.getDecoratee().getProfileConfigsReferencedAsProxy();
  }

  public ProfileTemplate getProfileTemplate() {
    return this.getDecoratee().getProfileTemplate();
  }

  public String getProfileType() {
    return this.getDecoratee().getProfileType();
  }

  public ProfileConfig getProxyProfile() {
    return this.getDecoratee().getProxyProfile();
  }

  public void setCarrier(Carrier carrier) {
    this.getDecoratee().setCarrier(carrier);    
  }

  public void setDescription(String description) {
    this.getDecoratee().setDescription(description);    
  }

  public void setExternalID(String externalID) {
    this.getDecoratee().setExternalID(externalID);
  }

  public void setIsUserProfile(boolean isUserProfile) {
    this.getDecoratee().setIsUserProfile(isUserProfile);
  }

  public void setLastUpdatedBy(String lastUpdatedBy) {    
    this.getDecoratee().setLastUpdatedBy(lastUpdatedBy);
  }

  public void setNAPProfile(ProfileConfig napProfileConfig) throws DMException {
    this.getDecoratee().setNAPProfile(napProfileConfig);
  }

  public void setName(String name) {
    this.getDecoratee().setName(name);    
  }

  public void setProfileTemplate(ProfileTemplate profileTemplate) {
    this.getDecoratee().setProfileTemplate(profileTemplate);
  }

  public void setProfileType(String profileType) {
    this.getDecoratee().setProfileType(profileType);
  }

  public void setProxyProfile(ProfileConfig proxyProfileConfig) throws DMException {
    this.getDecoratee().setProxyProfile(proxyProfileConfig);
  }

}
