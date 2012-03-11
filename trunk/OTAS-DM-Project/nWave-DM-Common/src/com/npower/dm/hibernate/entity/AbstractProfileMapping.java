/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProfileMapping.java,v 1.7 2008/12/12 04:16:10 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/12/12 04:16:10 $
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

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileTemplate;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/12/12 04:16:10 $
 */
public abstract class AbstractProfileMapping implements java.io.Serializable, ProfileMapping {

  // Fields

  private long ID;

  private DDFNode rootDDFNode;
  
  private String rootNodePath;

  private ProfileTemplate profileTemplate;

  private boolean shareRootNode;

  private boolean assignToDevice;

  private String linkedProfileType;

  private String lastUpdatedBy;

  private Date lastUpdatedTime;

  private long changeVersion;

  private Set modelProfileMaps = new HashSet(0);

  private Set profileNodeMappings = new TreeSet();

  private Set mappingNodeNames = new HashSet(0);

  private boolean needToDiscovery = true;
  
  private String discoveryNodePaths = null;

  // Constructors

  /** default constructor */
  public AbstractProfileMapping() {
    super();
  }

  /** minimal constructor */
  public AbstractProfileMapping(DDFNode nwDmDdfNode, ProfileTemplate nwDmProfileTemplate, long changeVersion) {
    this.rootDDFNode = nwDmDdfNode;
    this.profileTemplate = nwDmProfileTemplate;
    this.changeVersion = changeVersion;
  }

  /** full constructor */
  public AbstractProfileMapping(DDFNode nwDmDdfNode, ProfileTemplate nwDmProfileTemplate, boolean shareRootNode,
      boolean assignToDevice, String linkedProfileType, String lastUpdatedBy, Date lastUpdatedTime, long changeVersion,
      Set nwDmModelProfileMaps, Set nwDmProfileNodeMappings, Set nwDmMappingNodeNames) {
    this.rootDDFNode = nwDmDdfNode;
    this.profileTemplate = nwDmProfileTemplate;
    this.shareRootNode = shareRootNode;
    this.assignToDevice = assignToDevice;
    this.linkedProfileType = linkedProfileType;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedTime = lastUpdatedTime;
    this.changeVersion = changeVersion;
    this.modelProfileMaps = nwDmModelProfileMaps;
    this.profileNodeMappings = nwDmProfileNodeMappings;
    this.mappingNodeNames = nwDmMappingNodeNames;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long profileMappingId) {
    this.ID = profileMappingId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#getDdfNode()
   */
  public DDFNode getRootDDFNode() {
    return this.rootDDFNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#setDdfNode(com.npower.dm.hibernate.DDFNode)
   */
  public void setRootDDFNode(DDFNode nwDmDdfNode) {
    this.rootDDFNode = nwDmDdfNode;
  }

  /**
   * @return the rootNodePath
   */
  public String getRootNodePath() {
    if (StringUtils.isEmpty(this.rootNodePath)) {
       DDFNode rootNode = this.getRootDDFNode();
       if (rootNode != null) {
          this.rootNodePath = rootNode.getNodePath();
       }
    }
    return this.rootNodePath;
  }

  /**
   * @param rootNodePath the rootNodePath to set
   */
  public void setRootNodePath(String rootNodePath) {
    this.rootNodePath = rootNodePath;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#getProfileTemplate()
   */
  public ProfileTemplate getProfileTemplate() {
    return this.profileTemplate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#setProfileTemplate(com.npower.dm.hibernate.ProfileTemplate)
   */
  public void setProfileTemplate(ProfileTemplate nwDmProfileTemplate) {
    this.profileTemplate = nwDmProfileTemplate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#getShareRootNode()
   */
  public boolean getShareRootNode() {
    return this.shareRootNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#setShareRootNode(boolean)
   */
  public void setShareRootNode(boolean shareRootNode) {
    this.shareRootNode = shareRootNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#getAssignToDevice()
   */
  public boolean getAssignToDevice() {
    return this.assignToDevice;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#setAssignToDevice(boolean)
   */
  public void setAssignToDevice(boolean assignToDevice) {
    this.assignToDevice = assignToDevice;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#getLinkedProfileType()
   */
  public String getLinkedProfileType() {
    return this.linkedProfileType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#setLinkedProfileType(java.lang.String)
   */
  public void setLinkedProfileType(String linkedProfileType) {
    this.linkedProfileType = linkedProfileType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#getLastUpdatedTime()
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
   * @see com.npower.dm.hibernate.ProfileMapping#getChangeVersion()
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
   * @see com.npower.dm.hibernate.ProfileMapping#getModelProfileMaps()
   */
  public Set getModelProfileMaps() {
    return this.modelProfileMaps;
  }

  public void setModelProfileMaps(Set nwDmModelProfileMaps) {
    this.modelProfileMaps = nwDmModelProfileMaps;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#getProfileNodeMappings()
   */
  public Set getProfileNodeMappings() {
    return this.profileNodeMappings;
  }

  public void setProfileNodeMappings(Set nwDmProfileNodeMappings) {
    this.profileNodeMappings = nwDmProfileNodeMappings;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ProfileMapping#getMappingNodeNames()
   */
  public Set getMappingNodeNames() {
    return this.mappingNodeNames;
  }

  public void setMappingNodeNames(Set nwDmMappingNodeNames) {
    this.mappingNodeNames = nwDmMappingNodeNames;
  }

  /**
   * @return the needToDiscovery
   */
  public boolean isNeedToDiscovery() {
    return needToDiscovery;
  }

  /**
   * @param needToDiscovery the needToDiscovery to set
   */
  public void setNeedToDiscovery(boolean needToDiscovery) {
    this.needToDiscovery = needToDiscovery;
  }

  /**
   * @return the discoveryNodePaths
   */
  public String getDiscoveryNodePaths() {
    return discoveryNodePaths;
  }

  /**
   * @param discoveryNodePaths the discoveryNodePaths to set
   */
  public void setDiscoveryNodePaths(String discoveryNodePaths) {
    this.discoveryNodePaths = discoveryNodePaths;
  }

}