/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractModel.java,v 1.13 2008/11/19 04:27:49 zhao Exp $
 * $Revision: 1.13 $
 * $Date: 2008/11/19 04:27:49 $
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

import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelCharacter;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.13 $ $Date: 2008/11/19 04:27:49 $
 */
public abstract class AbstractModel implements java.io.Serializable, Comparable<Model>, Model {

  // Fields

  private long ID;

  private Manufacturer manufacturer;

  private String name;

  private String description;
  
  private String familyExternalID;

  private String manufacturerModelId;

  private boolean supportedDownloadMethods;

  private String lastUpdatedBy;

  private Date lastUpdatedTime = new Date();

  private boolean isOmaDmEnabled = false;
  
  private String OmaDmVersion;
  
  private boolean isOmaCpEnabled = false;
  
  private String OmaCpVersion;
  
  private boolean isNokiaOtaEnabled = false;
  
  private String nokiaOtaVersion;
  
  private String serverAuthType;

  private boolean isPlainProfile;

  private boolean isUseEncForOmaMsg;

  private boolean isUseNextNoncePerPkg;

  private String firmwareNode;

  private String firmwareUpdateNode;

  private String firmwareUpdateReplaceNode;

  private String firmwareUpdateExecNode;

  private String firmwareUpdateStatusNode;
  
  private long changeVersion;

  private Blob      icon;
  
  private String operatingSystem;
  
  private Date announcedDate;

  private Date createdTime = new Date();

  private Set<ModelCharacter> characters = new TreeSet<ModelCharacter>();

  private Set modelProfileMaps = new HashSet(0);

  private Set modelDMProps = new HashSet(0);

  private Set images = new HashSet(0);

  private Set modelDMBootProps = new HashSet(0);

  private Set devices = new HashSet(0);

  private Set modelTACs = new HashSet(0);

  private Set modelDDFTrees = new TreeSet();

  private Set<ModelClientProvMapEntity>         modelClientProvMaps = new HashSet<ModelClientProvMapEntity>(0);
  // Constructors

  /** default constructor */
  public AbstractModel() {
    super();
  }

  /** minimal constructor */
  public AbstractModel(Manufacturer nwDmManufacturer, String name, String manufacturerModelId,
      boolean supportedDownloadMethods, boolean isOmaEnabled, boolean isPlainProfile, boolean isUseEncForOmaMsg,
      boolean isUseNextNoncePerPkg) {
    this.manufacturer = nwDmManufacturer;
    this.name = name;
    this.manufacturerModelId = manufacturerModelId;
    this.supportedDownloadMethods = supportedDownloadMethods;
    this.isOmaDmEnabled = isOmaEnabled;
    this.isPlainProfile = isPlainProfile;
    this.isUseEncForOmaMsg = isUseEncForOmaMsg;
    this.isUseNextNoncePerPkg = isUseNextNoncePerPkg;
  }

  /** full constructor */
  public AbstractModel(Manufacturer nwDmManufacturer, String name, String description, String manufacturerModelId,
      boolean supportedDownloadMethods, String lastUpdatedBy, Date lastUpdatedTime, boolean isOmaEnabled,
      String serverAuthType, boolean isPlainProfile, boolean isUseEncForOmaMsg, boolean isUseNextNoncePerPkg,
      String firmwareNode, String firmwareUpdateNode, String firmwareUpdateReplaceNode, String firmwareUpdateExecNode,
      String firmwareUpdateStatusNode) {
    this.manufacturer = nwDmManufacturer;
    this.name = name;
    this.description = description;
    this.manufacturerModelId = manufacturerModelId;
    this.supportedDownloadMethods = supportedDownloadMethods;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedTime = lastUpdatedTime;
    this.isOmaDmEnabled = isOmaEnabled;
    this.serverAuthType = serverAuthType;
    this.isPlainProfile = isPlainProfile;
    this.isUseEncForOmaMsg = isUseEncForOmaMsg;
    this.isUseNextNoncePerPkg = isUseNextNoncePerPkg;
    this.firmwareNode = firmwareNode;
    this.firmwareUpdateNode = firmwareUpdateNode;
    this.firmwareUpdateReplaceNode = firmwareUpdateReplaceNode;
    this.firmwareUpdateExecNode = firmwareUpdateExecNode;
    this.firmwareUpdateStatusNode = firmwareUpdateStatusNode;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long modelId) {
    this.ID = modelId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getManufacturer()
   */
  public Manufacturer getManufacturer() {
    return this.manufacturer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setManufacturer(com.npower.dm.hibernate.Manufacturer)
   */
  public void setManufacturer(Manufacturer nwDmManufacturer) {
    this.manufacturer = nwDmManufacturer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getManufacturerModelId()
   */
  public String getManufacturerModelId() {
    return this.manufacturerModelId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setManufacturerModelId(java.lang.String)
   */
  public void setManufacturerModelId(String manufacturerModelId) {
    this.manufacturerModelId = manufacturerModelId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Model#getFamilyExternalID()
   */
  public String getFamilyExternalID() {
    return familyExternalID;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Model#setFamilyExternalID(java.lang.String)
   */
  public void setFamilyExternalID(String familyExternalID) {
    this.familyExternalID = familyExternalID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getSupportedDownloadMethods()
   */
  public boolean getSupportedDownloadMethods() {
    return this.supportedDownloadMethods;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setSupportedDownloadMethods(boolean)
   */
  public void setSupportedDownloadMethods(boolean supportedDownloadMethods) {
    this.supportedDownloadMethods = supportedDownloadMethods;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getLastUpdatedTime()
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
   * @see com.npower.dm.hibernate.Model#getIsOmaDmEnabled()
   */
  public boolean getIsOmaDmEnabled() {
    return this.isOmaDmEnabled;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setIsOMAEnabled(boolean)
   */
  public void setIsOmaDmEnabled(boolean isOmaDmEnabled) {
    this.isOmaDmEnabled = isOmaDmEnabled;
  }

  /**
   * @return the omaDmVersion
   */
  public String getOmaDmVersion() {
    return OmaDmVersion;
  }

  /**
   * @param omaDmVersion the omaDmVersion to set
   */
  public void setOmaDmVersion(String omaDmVersion) {
    OmaDmVersion = omaDmVersion;
  }

  /**
   * @return the isOmaCpEnabled
   */
  public boolean getIsOmaCpEnabled() {
    return isOmaCpEnabled;
  }

  /**
   * @param isOmaCpEnabled the isOmaCpEnabled to set
   */
  public void setIsOmaCpEnabled(boolean isOmaCpEnabled) {
    this.isOmaCpEnabled = isOmaCpEnabled;
  }

  /**
   * @return the omaCpVersion
   */
  public String getOmaCpVersion() {
    return OmaCpVersion;
  }

  /**
   * @param omaCpVersion the omaCpVersion to set
   */
  public void setOmaCpVersion(String omaCpVersion) {
    OmaCpVersion = omaCpVersion;
  }

  /**
   * @return the isNokiaOtaEnabled
   */
  public boolean getIsNokiaOtaEnabled() {
    return isNokiaOtaEnabled;
  }

  /**
   * @param isNokiaOtaEnabled the isNokiaOtaEnabled to set
   */
  public void setIsNokiaOtaEnabled(boolean isNokiaOtaEnabled) {
    this.isNokiaOtaEnabled = isNokiaOtaEnabled;
  }

  /**
   * @return the nokieOtaVersion
   */
  public String getNokiaOtaVersion() {
    return nokiaOtaVersion;
  }

  /**
   * @param nokieOtaVersion the nokieOtaVersion to set
   */
  public void setNokiaOtaVersion(String nokiaOtaVersion) {
    this.nokiaOtaVersion = nokiaOtaVersion;
  }

  /**
   * @param isOmaDmEnabled the isOmaDmEnabled to set
   */
  public void setOmaDmEnabled(boolean isOmaDmEnabled) {
    this.isOmaDmEnabled = isOmaDmEnabled;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getServerAuthType()
   */
  public String getServerAuthType() {
    return this.serverAuthType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setServerAuthType(java.lang.String)
   */
  public void setServerAuthType(String serverAuthType) {
    this.serverAuthType = serverAuthType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getIsPlainProfile()
   */
  public boolean getIsPlainProfile() {
    return this.isPlainProfile;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setIsPlainProfile(boolean)
   */
  public void setIsPlainProfile(boolean isPlainProfile) {
    this.isPlainProfile = isPlainProfile;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getIsUseEncForOmaMsg()
   */
  public boolean getIsUseEncForOmaMsg() {
    return this.isUseEncForOmaMsg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setIsUseEncForOmaMsg(boolean)
   */
  public void setIsUseEncForOmaMsg(boolean isUseEncForOmaMsg) {
    this.isUseEncForOmaMsg = isUseEncForOmaMsg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getIsUseNextNoncePerPkg()
   */
  public boolean getIsUseNextNoncePerPkg() {
    return this.isUseNextNoncePerPkg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setIsUseNextNoncePerPkg(boolean)
   */
  public void setIsUseNextNoncePerPkg(boolean isUseNextNoncePerPkg) {
    this.isUseNextNoncePerPkg = isUseNextNoncePerPkg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getFirmwareNode()
   */
  public String getFirmwareVersionNode() {
    return this.firmwareNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setFirmwareNode(java.lang.String)
   */
  public void setFirmwareVersionNode(String firmwareNode) {
    this.firmwareNode = firmwareNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getFirmwareUpdateNode()
   */
  public String getFirmwareUpdateNode() {
    return this.firmwareUpdateNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setFirmwareUpdateNode(java.lang.String)
   */
  public void setFirmwareUpdateNode(String firmwareUpdateNode) {
    this.firmwareUpdateNode = firmwareUpdateNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getFirmwareUpdateReplaceNode()
   */
  public String getFirmwareDownloadNode() {
    return this.firmwareUpdateReplaceNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setFirmwareUpdateReplaceNode(java.lang.String)
   */
  public void setFirmwareDownloadNode(String firmwareUpdateReplaceNode) {
    this.firmwareUpdateReplaceNode = firmwareUpdateReplaceNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getFirmwareUpdateExecNode()
   */
  public String getFirmwareDownloadAndUpdateNode() {
    return this.firmwareUpdateExecNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setFirmwareUpdateExecNode(java.lang.String)
   */
  public void setFirmwareDownloadAndUpdateNode(String firmwareUpdateExecNode) {
    this.firmwareUpdateExecNode = firmwareUpdateExecNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getFirmwareUpdateStatusNode()
   */
  public String getFirmwareStatusNode() {
    return this.firmwareUpdateStatusNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#setFirmwareUpdateStatusNode(java.lang.String)
   */
  public void setFirmwareStatusNode(String firmwareUpdateStatusNode) {
    this.firmwareUpdateStatusNode = firmwareUpdateStatusNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getChangeVersion()
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
   * @see com.npower.dm.hibernate.Model#getModelProfileMaps()
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
   * @see com.npower.dm.hibernate.Model#getModelDMProps()
   */
  public Set getModelDMProps() {
    return this.modelDMProps;
  }

  public void setModelDMProps(Set nwDmModelDmProps) {
    this.modelDMProps = nwDmModelDmProps;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getImages()
   */
  public Set getImages() {
    return this.images;
  }

  public void setImages(Set nwDmImages) {
    this.images = nwDmImages;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getModelDMBootProps()
   */
  public Set getModelDMBootProps() {
    return this.modelDMBootProps;
  }

  public void setModelDMBootProps(Set nwDmModelDmBootProps) {
    this.modelDMBootProps = nwDmModelDmBootProps;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getDevices()
   */
  public Set getDevices() {
    return this.devices;
  }

  public void setDevices(Set nwDmDevices) {
    this.devices = nwDmDevices;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getModelTACs()
   */
  public Set getModelTACs() {
    return this.modelTACs;
  }

  public void setModelTACs(Set nwDmModelTacs) {
    this.modelTACs = nwDmModelTacs;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Model#getModelDDFTrees()
   */
  public Set getModelDDFTrees() {
    return this.modelDDFTrees;
  }

  public void setModelDDFTrees(Set nwDmModelDdfTrees) {
    this.modelDDFTrees = nwDmModelDdfTrees;
  }

  public Set<ModelClientProvMapEntity> getModelClientProvMaps() {
    return modelClientProvMaps;
  }

  public void setModelClientProvMaps(Set<ModelClientProvMapEntity> modelClientProvMaps) {
    this.modelClientProvMaps = modelClientProvMaps;
  }

  // -----------------------------------------------------------------------------
  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelSpec#getIcon()
   */
  public Blob getIcon() {
    return this.icon;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelSpec#setIcon(java.sql.Blob)
   */
  public void setIcon(Blob icon) {
    this.icon = icon;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#getOperatingSystem()
   */
  public String getOperatingSystem() {
    return operatingSystem;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#setOperatingSystem(java.lang.String)
   */
  public void setOperatingSystem(String operatingSystem) {
    this.operatingSystem = operatingSystem;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#getAnnouncedDate()
   */
  public Date getAnnouncedDate() {
    return announcedDate;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#setAnnouncedDate(java.sql.Date)
   */
  public void setAnnouncedDate(Date announcedDate) {
    this.announcedDate = announcedDate;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#getCharacters()
   */
  public Set<ModelCharacter> getCharacters() {
    return characters;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#setCharacters(java.util.Set)
   */
  public void setCharacters(Set<ModelCharacter> characters) {
    this.characters = characters;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#getCharacter(java.lang.String, java.lang.String)
   */
  public ModelCharacter getCharacter(String category, String name) {
    for (ModelCharacter character: this.getCharacters()) {
        if (character.getCategory() == category || character.getCategory().equalsIgnoreCase(category)) {
           if (name.equalsIgnoreCase(character.getName())) {
              return character;
           }
        }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#getCharacterValue(java.lang.String, java.lang.String)
   */
  public String getCharacterValue(String category, String name) {
    ModelCharacter character = this.getCharacter(category, name);
    if (character != null) {
       return character.getValue();
    }
    return null;
  }

  /**
   * @return the createdTime
   */
  public Date getCreatedTime() {
    return createdTime;
  }

  /**
   * @param createdTime the createdTime to set
   */
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }
  

}