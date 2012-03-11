/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/xmlinventory/OTATemplateItem.java,v 1.5 2008/10/29 03:19:41 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2008/10/29 03:19:41 $
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
package com.npower.cp.xmlinventory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.npower.cp.OTAException;
import com.npower.cp.ProvisioningDoc;
import com.npower.cp.TemplateMerger;
import com.npower.cp.TemplateMergerFactory;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.hibernate.entity.ModelClientProvMapEntity;
import com.npower.wap.nokia.NokiaOTASettings;
import com.npower.wap.omacp.OMAClientProvSettings;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/10/29 03:19:41 $
 */
public class OTATemplateItem implements ClientProvTemplate {

  private Long ID = null;
  private String externalID = null;
  private String name = null;
  private String encoder = "omacp";
  private String description = null;
  private ProfileCategory profileCategory;
  private ModelItem model;
  private String content;

  /**
   * Default constructor
   */
  public OTATemplateItem() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTATemplate#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTATemplate#getID()
   */
  public Long getID() {
    return this.ID;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTATemplate#getName()
   */
  public String getName() {
    return this.name;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTATemplate#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTATemplate#setID(java.lang.String)
   */
  public void setID(Long id) {
    this.ID = id;
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
   * @see com.npower.cp.OTATemplate#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTATemplate#getModel()
   */
  public ModelItem getModel() {
    return this.model;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTATemplate#setModel(com.npower.cp.Model)
   */
  public void setModel(ModelItem model) {
    this.model = model;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#getProfileCategory()
   */
  public ProfileCategory getProfileCategory() {
    return this.profileCategory;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#setProfileCategory(com.npower.dm.core.ProfileCategory)
   */
  public void setProfileCategory(ProfileCategory profileCategory) {
    this.profileCategory = profileCategory;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTATemplate#setCategoryByName(java.lang.String)
   */
  public void setCategoryByName(String categoryName) {
    this.profileCategory = new ProfileCategoryItem();
    this.profileCategory.setName(categoryName);
  }
  
  /* (non-Javadoc)
   * @see com.npower.cp.OTATemplate#getContent()
   */
  public String getContent() {
    return this.content;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTATemplate#setContent(java.lang.String)
   */
  public void setContent(String content) {
    this.content = content;
  }

  public void setContentString(String content) {
    if (!StringUtils.isEmpty(content)) {
       this.content = content;
    }
  }
  
  /**
   * Set the content by filename.
   * The method will be called in Common Digester
   * @param filename
   * @throws IOException
   */
  public void setFilename(String filename) throws IOException {
    if (StringUtils.isEmpty(filename)) {
       return;
    }
    String baseDir = System.getProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR);
    File file = new File(baseDir, filename);
    Reader reader = new FileReader(file);
    StringWriter writer = new StringWriter();
    int c = reader.read();
    while (c > 0) {
          writer.write(c);
          c = reader.read();
    }
    this.content = writer.toString();
    reader.close();
  }

  /*
  private String outputTmpFile(String content) throws IOException {
    File file = File.createTempFile("ota_", ".vm");
    FileWriter writer = new FileWriter(file);
    writer.write(content);
    writer.close();
    return file.getName();
  }
  */

  /* (non-Javadoc)
   * @see com.npower.cp.OTATemplate#merge(com.npower.wap.omacp.OMAClientProvSettings)
   */
  public ProvisioningDoc merge(OMAClientProvSettings settings) throws OTAException {
    TemplateMergerFactory mergerFactory = TemplateMergerFactory.newInstance();
    TemplateMerger merger = mergerFactory.getTemplateMerger();
    return merger.merge(this, settings);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#merge(com.npower.wap.nokia.NokiaOTASettings)
   */
  public ProvisioningDoc merge(NokiaOTASettings settings) throws OTAException {
    TemplateMergerFactory mergerFactory = TemplateMergerFactory.newInstance();
    TemplateMerger merger = mergerFactory.getTemplateMerger();
    return merger.merge(this, settings);
  }


  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#merge(com.npower.dm.core.ProfileConfig)
   */
  public ProvisioningDoc merge(ProfileConfig profile) throws OTAException {
    TemplateMergerFactory mergerFactory = TemplateMergerFactory.newInstance();
    TemplateMerger merger = mergerFactory.getTemplateMerger();
    return merger.merge(this, profile);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#getChangeVersion()
   */
  public Long getChangeVersion() {
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#getModelClientProvMaps()
   */
  public Set<ModelClientProvMapEntity> getModelClientProvMaps() {
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#setChangeVersion(java.lang.Long)
   */
  public void setChangeVersion(Long changeVersion) {
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#setLastUpdatedTime(java.util.Date)
   */
  public void setLastUpdatedTime(Date lastUpdatedTime) {
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#setModelClientProvMaps(java.util.Set)
   */
  public void setModelClientProvMaps(Set<ModelClientProvMapEntity> clientProvMaps) {
  }

  /**
   * @return the encoder
   */
  public String getEncoder() {
    return encoder;
  }

  /**
   * @param encoder the encoder to set
   */
  public void setEncoder(String encoder) {
    this.encoder = encoder;
  }

}
