/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProfileConfig.java,v 1.5 2008/01/12 02:47:45 zhao Exp $
 * $Revision: 1.5 $
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
package com.npower.dm.hibernate.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/01/12 02:47:45 $
 */
public abstract class AbstractProfileConfig implements java.io.Serializable, ProfileConfig {

  // Fields

  private long ID;
  
  private String externalID;

  private ProfileConfig NAPProfileConfig;

  private ProfileConfig proxyProfileConfig;

  private ProfileTemplate profileTemplate;

  private Carrier carrier;

  private String profileType;

  private String name;
  
  private boolean isUserProfile  = true;
  
  private String description;

  private String lastUpdatedBy;

  private Date lastUpdatedTime;

  private long changeVersion;

  private Set<ProfileAssignment> profileAssignments = new HashSet<ProfileAssignment>(0);

  private Set<ProfileConfig> profileConfigsReferencedAsNAP = new HashSet<ProfileConfig>(0);

  private Set<ProfileConfig> profileConfigsReferencedAsProxy = new HashSet<ProfileConfig>(0);

  private Set profileValueMaps = new TreeSet();

  private Set<Carrier> carriers = new HashSet<Carrier>(0);

  // Constructors

  /** default constructor */
  public AbstractProfileConfig() {
    super();
  }

  /** minimal constructor */
  public AbstractProfileConfig(String externalID, String name, Carrier carrier, ProfileTemplate template, String profileType) {
    this.externalID = externalID;
    this.profileTemplate = template;
    this.profileType = profileType;
    this.carrier = carrier;
    this.name = name;
  }

  /** full constructor */
  public AbstractProfileConfig(String name, Carrier carrier, ProfileTemplate template, String profileType,
      ProfileConfig napProfile, ProfileConfig proxyProfile) {
    this.NAPProfileConfig = napProfile;
    this.proxyProfileConfig = proxyProfile;
    this.profileTemplate = template;
    this.carrier = carrier;
    this.profileType = profileType;
    this.name = name;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getID()
   */
  public long getID() {
    return this.ID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#setID(long)
   */
  public void setID(long profileId) {
    this.ID = profileId;
  }

  public ProfileConfig getNAPProfileConfig() {
    return this.NAPProfileConfig;
  }

  public void setNAPProfileConfig(ProfileConfig nwDmProfileConfigByNapId) throws DMException {
    this.NAPProfileConfig = nwDmProfileConfigByNapId;
  }

  public ProfileConfig getProxyProfileConfig() {
    return this.proxyProfileConfig;
  }

  public void setProxyProfileConfig(ProfileConfig nwDmProfileConfigByProxyId) throws DMException {
    this.proxyProfileConfig = nwDmProfileConfigByProxyId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getProfileTemplate()
   */
  public ProfileTemplate getProfileTemplate() {
    return this.profileTemplate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#setProfileTemplate(com.npower.dm.hibernate.ProfileTemplate)
   */
  public void setProfileTemplate(ProfileTemplate nwDmProfileTemplate) {
    this.profileTemplate = nwDmProfileTemplate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getCarrier()
   */
  public Carrier getCarrier() {
    return this.carrier;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#setCarrier(com.npower.dm.hibernate.Carrier)
   */
  public void setCarrier(Carrier nwDmCarrier) {
    this.carrier = nwDmCarrier;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getProfileType()
   */
  public String getProfileType() {
    return this.profileType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#setProfileType(java.lang.String)
   */
  public void setProfileType(String profileType) {
    this.profileType = profileType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    return this.lastUpdatedTime;
  }

  public void setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getProfileAssignments()
   */
  public Set<ProfileAssignment> getProfileAssignments() {
    return this.profileAssignments;
  }

  public void setProfileAssignments(Set<ProfileAssignment> nwDmProfileAssignments) {
    this.profileAssignments = nwDmProfileAssignments;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getProfileConfigsReferencedAsNAP()
   */
  public Set<ProfileConfig> getProfileConfigsReferencedAsNAP() {
    return this.profileConfigsReferencedAsNAP;
  }

  public void setProfileConfigsReferencedAsNAP(Set<ProfileConfig> nwDmProfileConfigsForNapId) {
    this.profileConfigsReferencedAsNAP = nwDmProfileConfigsForNapId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getProfileConfigsReferencedAsProxy()
   */
  public Set<ProfileConfig> getProfileConfigsReferencedAsProxy() {
    return this.profileConfigsReferencedAsProxy;
  }

  public void setProfileConfigsReferencedAsProxy(Set<ProfileConfig> nwDmProfileConfigsForProxyId) {
    this.profileConfigsReferencedAsProxy = nwDmProfileConfigsForProxyId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getProfileValueMaps()
   */
  public Set getProfileValueMaps() {
    return this.profileValueMaps;
  }

  public void setProfileValueMaps(Set nwDmProfileValueMaps) {
    this.profileValueMaps = nwDmProfileValueMaps;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileConfig#getCarriers()
   */
  public Set<Carrier> getCarriers() {
    return this.carriers;
  }

  /**
   * @param nwDmCarriers
   */
  public void setCarriers(Set<Carrier> carriers) {
    this.carriers = carriers;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileConfig#getDescription()
   */
  public String getDescription() {
    return description;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileConfig#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileConfig#isUserProfile()
   */
  public boolean getIsUserProfile() {
    return isUserProfile;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileConfig#setUserProfile(boolean)
   */
  public void setIsUserProfile(boolean isUserProfile) {
    this.isUserProfile = isUserProfile;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileConfig#getExternalID()
   */
  public String getExternalID() {
    return externalID;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileConfig#setExternalID(java.lang.String)
   */
  public void setExternalID(String externalID) {
    this.externalID = externalID;
  }

}