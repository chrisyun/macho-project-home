/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractClientProvTemplate.java,v 1.4 2007/11/12 05:17:36 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2007/11/12 05:17:36 $
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
package com.npower.dm.hibernate.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.ProfileCategory;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/11/12 05:17:36 $
 */
public abstract class AbstractClientProvTemplate implements java.io.Serializable, ClientProvTemplate {

  // Fields

  private Long            templateId;
  
  private String          externalID;

  private ProfileCategory profileCategory;

  private String          name;

  private String          description;
  
  private String          encoder       =   "omacp";

  private String          content;

  private String          lastUpdatedBy;

  private Date            lastUpdatedTime;

  private Long            changeVersion;

  private Set<ModelClientProvMapEntity>             modelClientProvMaps = new HashSet<ModelClientProvMapEntity>(0);

  // Constructors

  /** default constructor */
  public AbstractClientProvTemplate() {
  }

  /** minimal constructor */
  public AbstractClientProvTemplate(Long templateId, String externalID, String content) {
    this.templateId = templateId;
    this.content = content;
    this.externalID = externalID;
  }

  /** full constructor */
  public AbstractClientProvTemplate(Long templateId, ProfileCategory profileCategory, String name, String description,
      String content, String lastUpdatedBy, Date lastUpdatedTime, Long changeVersion, Set nwDmModelClientProvMaps) {
    this.templateId = templateId;
    this.profileCategory = profileCategory;
    this.name = name;
    this.description = description;
    this.content = content;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedTime = lastUpdatedTime;
    this.changeVersion = changeVersion;
    this.modelClientProvMaps = nwDmModelClientProvMaps;
  }

  // Property accessors

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#getTemplateId()
   */
  public Long getID() {
    return this.templateId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#setTemplateId(java.lang.Long)
   */
  public void setID(Long templateId) {
    this.templateId = templateId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#getExternalID()
   */
  public String getExternalID() {
    return externalID;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#setExternalID(java.lang.String)
   */
  public void setExternalID(String externalID) {
    this.externalID = externalID;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#getProfileCategory()
   */
  public ProfileCategory getProfileCategory() {
    return this.profileCategory;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#setProfileCategory(com.npower.dm.core.ProfileCategory)
   */
  public void setProfileCategory(ProfileCategory profileCategory) {
    this.profileCategory = profileCategory;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#getName()
   */
  public String getName() {
    return this.name;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#getContent()
   */
  public String getContent() {
    return this.content;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#setContent(java.lang.String)
   */
  public void setContent(String content) {
    this.content = content;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#getEncoder()
   */
  public String getEncoder() {
    return encoder;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#setEncoder(java.lang.String)
   */
  public void setEncoder(String encoder) {
    this.encoder = encoder;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    return this.lastUpdatedTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#setLastUpdatedTime(java.util.Date)
   */
  public void setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#getChangeVersion()
   */
  public Long getChangeVersion() {
    return this.changeVersion;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#setChangeVersion(java.lang.Long)
   */
  public void setChangeVersion(Long changeVersion) {
    this.changeVersion = changeVersion;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#getModelClientProvMaps()
   */
  public Set<ModelClientProvMapEntity> getModelClientProvMaps() {
    return this.modelClientProvMaps;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ClientProvTemplate#setModelClientProvMaps(java.util.Set)
   */
  public void setModelClientProvMaps(Set<ModelClientProvMapEntity> clientProvMaps) {
    this.modelClientProvMaps = clientProvMaps;
  }

}