/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ProfileConfig.java,v 1.8 2008/01/12 02:47:45 zhao Exp $
 * $Revision: 1.8 $
 * $Date: 2008/01/12 02:47:45 $
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
 * <p>Represent a ProfileConfig.</p>
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2008/01/12 02:47:45 $
 * 
 */
public interface ProfileConfig {

  /**
   * Profile Type: NAP
   */
  public static final String PROFILE_TYPE_NAP = "NAP";

  /**
   * Profile Type: Proxy
   */
  public static final String PROFILE_TYPE_PROXY = "Proxy";

  /**
   * Profile Type: Service
   */
  public static final String PROFILE_TYPE_SERVICE = "Service";

  /**
   * Profile Type: Application
   */
  public static final String PROFILE_TYPE_APPLICATION = "Application";

  /**
   * Return the ID.
   * @return
   */
  public abstract long getID();
  //public abstract void setID(long profileId);

  /**
   * Return the NAP ProfileConfig for this ProfileConfig.
   * @return Return NAP ProfileConfig.
   */
  public abstract ProfileConfig getNAPProfile();

  /**
   * Set a NAP ProfileCOnfig for this ProfileConfig.
   * @param napProfileConfig   NAP ProfileConfig
   * @throws DMException
   */
  public abstract void setNAPProfile(ProfileConfig napProfileConfig) throws DMException;

  /**
   * Return the Proxy ProfileConfig for this ProfileConfig.
   * 
   * @return Proxy ProfileConfig.
   */
  public abstract ProfileConfig getProxyProfile();

  /**
   * Set a Proxy ProfileConfig for this ProfileConfig.
   * 
   * @param proxyProfileConfig
   * @throws DMException
   */
  public abstract void setProxyProfile(ProfileConfig proxyProfileConfig) throws DMException;

  /**
   * Return the ProfileTemplate of this ProfileConfig.
   * 
   * @return Return the ProfieTemplateof this ProfileConfig.
   */
  public abstract ProfileTemplate getProfileTemplate();

  /**
   * Set a ProfileTemplate for this ProfileConfig.
   * 
   * @param profileTemplate
   */
  public abstract void setProfileTemplate(ProfileTemplate profileTemplate);

  /**
   * Return the carrier of this ProfileConfig.<br>
   * A ProfileConfig will be belongs to a Carrier.
   * 
   * @return
   */
  public abstract Carrier getCarrier();

  /**
   * Set a carrier for this ProfileConfig.
   * 
   * @param carrier
   */
  public abstract void setCarrier(Carrier carrier);

  /**
   * Return the type of ProfileConfig.
   * @return 
   */
  public abstract String getProfileType();

  /**
   * Set a type for this ProfileConfig.
   * 
   * @param profileType
   */
  public abstract void setProfileType(String profileType);

  /**
   * Return ExternalID of ProfileConfig.
   * @return the familyExternalID
   */
  public String getExternalID();

  /**
   * Set ExternalID of ProfileConfig.
   * @param ExternalID
   */
  public void setExternalID(String externalID);

  /**
   * Return the name of this ProfileConfig.
   * 
   * @return
   */
  public abstract String getName();

  /**
   * Set a name for this ProfileConfig.
   * 
   * @param name
   */
  public abstract void setName(String name);

  /**
   * @return the description
   */
  public abstract String getDescription();

  /**
   * @param description the description to set
   */
  public abstract void setDescription(String description);

  /**
   * Indicate the profile will be presented to ent user.
   * @return the isUserProfile
   */
  public boolean getIsUserProfile();

  /**
   * Set value of isUserProfile
   * @param isUserProfile the isUserProfile to set
   */
  public void setIsUserProfile(boolean isUserProfile);

  /**
   * Return a set of assignments for this ProfileConfig.
   * @return  Return a <code>Set</code> of {@link com.npower.dm.core.ProfileAssignment} objects.
   */
  //public abstract Set getProfileAssignments();

  /**
   * Return a set of ProfileConfigs which reference this ProfileConfig as NAP.
   * 
   * @return Return a <code>Set</code> of {@link com.npower.dm.core.ProfileConfig} objects.
   */
  public abstract Set<ProfileConfig> getProfileConfigsReferencedAsNAP();

  /**
   * Return a set of ProfileConfigs which reference this ProfileConfig as Proxy.
   * 
   * @return Return a <code>Set</code> of {@link com.npower.dm.core.ProfileConfig} objects.
   */
  public abstract Set<ProfileConfig> getProfileConfigsReferencedAsProxy();

  /**
   * Return a set of ProfileValueMap included by this ProfileConfig.
   */
  //public abstract Set getProfileValueMaps();

  /**
   * Return a set of carriers which reference this ProfileConfig.
   * @return Return a <code>Set</code> of {@link com.npower.dm.core.Carrier} objects.
   */
  public abstract Set<Carrier> getCarriers();


  /**
   * Find a ProfileAttributeValue by the name from the
   * ProfileConfigEntity.
   * 
   * If no element found, will return a null.
   * 
   * @param attributeName
   * @return
   * @throws DMException
   */
  public ProfileAttributeValue getProfileAttributeValue(String attributeName) throws DMException;

  /**
   * Return all of ProfileAttributeValues owned by the ProfileConfigEntity.
   * 
   * @return ProfileAttributeValue[]
   */
  public ProfileAttributeValue[] getAttributeValues();

  /**
   * Getter of lastUpdatedBy
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * Setter of lastUpdatedBy
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Getter of lastUpdatedTime
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * Getter of changeVersion
   * @return
   */
  public abstract long getChangeVersion();

}