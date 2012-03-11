/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/ModelFamilyItem.java,v 1.1 2008/09/05 03:24:40 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/09/05 03:24:40 $
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
package com.npower.dm.setup.task;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/05 03:24:40 $
 */
public class ModelFamilyItem {
  
  /**
   * Control flag: trace the filename which defined the model.
   */
  private String definedByFilename = null;
  
  /**
   * Family properties
   */
  private String externalID = null;
  private String parentID = null;

  private String name = null;
  private String description = null;
  
  private boolean isOmaDmEnabled = true;
  private String omaDmVersion = null;
  
  private boolean isOmaCpEnabled = true;
  private String omaCpVersion = null;
  
  private boolean isNokiaOtaEnabled = true;
  private String nokiaOtaVersion = null;
  
  private boolean supportedDownloadMethods = true;
  private String firmwareVersionNode = null;
  private String firmwareUpdateNode = null;
  private String firmwareDownloadNode = null;
  private String firmwareDownloadAndUpdateNode = null;
  private String firmwareStatusNode = null;
  
  private String iconFile = null;
  
  private List<FirmwareItem> firmwares = new ArrayList<FirmwareItem>();
  
  private List<String> ddfFiles = new ArrayList<String>();

  private List<String> tacs = new ArrayList<String>();
  private List<String> profileMappingFiles = new ArrayList<String>();
  private List<String> cpTemplatesFiles = new ArrayList<String>();
  
  private Map<String, String> specifications = new LinkedHashMap<String, String>();

  /**
   * Default constructor.
   */
  public ModelFamilyItem() {
    super();
  }

  /**
   * @return the ddfFiles
   */
  public List<String> getDdfFiles() {
    return ddfFiles;
  }

  /**
   * @param ddfFiles the ddfFiles to set
   */
  public void setDdfFiles(List<String> ddfFiles) {
    this.ddfFiles = ddfFiles;
  }
  
  public void addDdfFile(String filename) {
    this.ddfFiles.add(filename);
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the externalID
   */
  public String getExternalID() {
    return externalID;
  }

  /**
   * @param externalID the externalID to set
   */
  public void setExternalID(String externalID) {
    this.externalID = externalID;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the profileMappingFiles
   */
  public List<String> getProfileMappingFiles() {
    return profileMappingFiles;
  }

  /**
   * @param profileMappingFiles the profileMappingFiles to set
   */
  public void setProfileMappingFiles(List<String> profileMappingFiles) {
    this.profileMappingFiles = profileMappingFiles;
  }

  public void addProfileMappingFile(String filename) {
    this.profileMappingFiles.add(filename);
  }

  /**
   * @return the firmwareDownloadAndUpdateNode
   */
  public String getFirmwareDownloadAndUpdateNode() {
    return firmwareDownloadAndUpdateNode;
  }

  /**
   * @param firmwareDownloadAndUpdateNode the firmwareDownloadAndUpdateNode to set
   */
  public void setFirmwareDownloadAndUpdateNode(String firmwareDownloadAndUpdateNode) {
    this.firmwareDownloadAndUpdateNode = firmwareDownloadAndUpdateNode;
  }

  /**
   * @return the firmwareDownloadNode
   */
  public String getFirmwareDownloadNode() {
    return firmwareDownloadNode;
  }

  /**
   * @param firmwareDownloadNode the firmwareDownloadNode to set
   */
  public void setFirmwareDownloadNode(String firmwareDownloadNode) {
    this.firmwareDownloadNode = firmwareDownloadNode;
  }

  /**
   * @return the firmwareStatusNode
   */
  public String getFirmwareStatusNode() {
    return firmwareStatusNode;
  }

  /**
   * @param firmwareStatusNode the firmwareStatusNode to set
   */
  public void setFirmwareStatusNode(String firmwareStatusNode) {
    this.firmwareStatusNode = firmwareStatusNode;
  }

  /**
   * @return the firmwareUpdateNode
   */
  public String getFirmwareUpdateNode() {
    return firmwareUpdateNode;
  }

  /**
   * @param firmwareUpdateNode the firmwareUpdateNode to set
   */
  public void setFirmwareUpdateNode(String firmwareUpdateNode) {
    this.firmwareUpdateNode = firmwareUpdateNode;
  }

  /**
   * @return the firmwareVersionNode
   */
  public String getFirmwareVersionNode() {
    return firmwareVersionNode;
  }

  /**
   * @param firmwareVersionNode the firmwareVersionNode to set
   */
  public void setFirmwareVersionNode(String firmwareVersionNode) {
    this.firmwareVersionNode = firmwareVersionNode;
  }

  /**
   * @return the supportedDownloadMethods
   */
  public boolean getSupportedDownloadMethods() {
    return supportedDownloadMethods;
  }

  /**
   * @param supportedDownloadMethods the supportedDownloadMethods to set
   */
  public void setSupportedDownloadMethods(String supportedDownloadMethods) {
    this.supportedDownloadMethods = Boolean.parseBoolean(supportedDownloadMethods);
  }

  /**
   * @param supportedDownloadMethods the supportedDownloadMethods to set
   */
  public void setSupportedDownloadMethods(boolean supportedDownloadMethods) {
    this.supportedDownloadMethods = supportedDownloadMethods;
  }

  /**
   * @return the tacs
   */
  public List<String> getTacs() {
    return tacs;
  }

  /**
   * @param tacs the tacs to set
   */
  public void setTacs(List<String> tacs) {
    this.tacs = tacs;
  }

  public void addTac(String tac) {
    this.tacs.add(tac);
  }

  /**
   * @return the firmwares
   */
  public List<FirmwareItem> getFirmwares() {
    return firmwares;
  }

  /**
   * @param firmwares the firmwares to set
   */
  public void setFirmwares(List<FirmwareItem> firmwares) {
    this.firmwares = firmwares;
  }

  /**
   * Add a FirmwareItem
   * @param firmware
   */
  public void addFirmware(FirmwareItem firmware) {
    this.firmwares.add(firmware);
  }

  /**
   * @return
   */
  public List<String> getCpTemplatesFiles() {
    return cpTemplatesFiles;
  }

  /**
   * @param cpTemplatesFiles
   */
  public void setCpTemplatesFiles(List<String> cpTemplatesFiles) {
    this.cpTemplatesFiles = cpTemplatesFiles;
  }

  /**
   * @param filename
   */
  public void addCpTemplatesFiles(String filename) {
    this.cpTemplatesFiles.add(filename);
  }

  /**
   * @return the iconFile
   */
  public String getIconFile() {
    return iconFile;
  }

  /**
   * @param iconFile the iconFile to set
   */
  public void setIconFile(String iconFile) {
    this.iconFile = iconFile;
  }

  /**
   * @return the isOMAEnabled
   */
  public boolean getIsOmaDmEnabled() {
    return isOmaDmEnabled;
  }

  /**
   * @param isOmaDmEnabled the isOmaDmEnabled to set
   */
  public void setIsOmaDmEnabled(String isOmaDmEnabled) {
    isOmaDmEnabled = (StringUtils.isEmpty(isOmaDmEnabled))?"false":isOmaDmEnabled;
    this.isOmaDmEnabled = Boolean.parseBoolean(isOmaDmEnabled);
  }

  /**
   * @param isOmaDmEnabled the isOmaDmEnabled to set
   */
  public void setIsOmaDmEnabled(boolean isOmaDmEnabled) {
    this.isOmaDmEnabled = isOmaDmEnabled;
  }

  /**
   * @return the omaDmVersion
   */
  public String getOmaDmVersion() {
    return omaDmVersion;
  }

  /**
   * @param omaDmVersion the omaDmVersion to set
   */
  public void setOmaDmVersion(String omaDmVersion) {
    this.omaDmVersion = omaDmVersion;
  }

  /**
   * @return the isOmaCpEnabled
   */
  public boolean isOmaCpEnabled() {
    return isOmaCpEnabled;
  }

  /**
   * @param isOmaDmEnabled the isOmaDmEnabled to set
   */
  public void setIsOmaCpEnabled(String isOmaCpEnabled) {
    isOmaCpEnabled = (StringUtils.isEmpty(isOmaCpEnabled))?"false":isOmaCpEnabled;
    this.isOmaCpEnabled = Boolean.parseBoolean(isOmaCpEnabled);
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
    return omaCpVersion;
  }

  /**
   * @param omaCpVersion the omaCpVersion to set
   */
  public void setOmaCpVersion(String omaCpVersion) {
    this.omaCpVersion = omaCpVersion;
  }

  /**
   * @return the isNokiaOtaEnabled
   */
  public boolean isNokiaOtaEnabled() {
    return isNokiaOtaEnabled;
  }

  /**
   * @param isOmaDmEnabled the isOmaDmEnabled to set
   */
  public void setIsNokiaOtaEnabled(String isNokiaOtaEnabled) {
    isNokiaOtaEnabled = (StringUtils.isEmpty(isNokiaOtaEnabled))?"false":isNokiaOtaEnabled;
    this.isNokiaOtaEnabled = Boolean.parseBoolean(isNokiaOtaEnabled);
  }

  /**
   * @param isNokiaOtaEnabled the isNokiaOtaEnabled to set
   */
  public void setIsNokiaOtaEnabled(boolean isNokiaOtaEnabled) {
    this.isNokiaOtaEnabled = isNokiaOtaEnabled;
  }

  /**
   * @return the nokiaOtaVersion
   */
  public String getNokiaOtaVersion() {
    return nokiaOtaVersion;
  }

  /**
   * @param nokiaOtaVersion the nokiaOtaVersion to set
   */
  public void setNokiaOtaVersion(String nokiaOtaVersion) {
    this.nokiaOtaVersion = nokiaOtaVersion;
  }

  /**
   * @return the definedByFilename
   */
  public String getDefinedByFilename() {
    return definedByFilename;
  }

  /**
   * @param definedByFilename the definedByFilename to set
   */
  public void setDefinedByFilename(String definedByFilename) {
    this.definedByFilename = definedByFilename;
  }

  /**
   * @return the parentID
   */
  public String getParentID() {
    return parentID;
  }

  /**
   * @param parentID the parentID to set
   * @throws NoSuchMethodException 
   * @throws InvocationTargetException 
   * @throws IllegalAccessException 
   */
  public void setParentID(String parentID) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    /*
    ModelFamilyManager manager = ModelFamilyManager.getInstance();
    ModelFamilyItem family = manager.findModelFamily(parentID);
    if (family != null) {
       PropertyUtils.copyProperties(this, family);
    }
    */
    this.parentID = parentID;
  }

  /**
   * @return the specifications
   */
  public Map<String, String> getSpecifications() {
    return specifications;
  }

  /**
   * @param specifications the specifications to set
   */
  public void setSpecifications(Map<String, String> specifications) {
    this.specifications = specifications;
  }
  
  public void setSpecification(String category, String name, String value) {
    this.specifications.put((category + "." + name).toLowerCase(), value);
  }
  
  public String getSpecification(String category, String name) {
    return this.specifications.get((category + "." + name).toLowerCase());
  }

}
