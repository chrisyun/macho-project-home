/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/Model.java,v 1.15 2008/11/19 04:21:36 zhao Exp $
 * $Revision: 1.15 $
 * $Date: 2008/11/19 04:21:36 $
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

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.15 $ $Date: 2008/11/19 04:21:36 $
 */
public interface Model extends Comparable<Model> {
  
  /**
   * Default Model External ID, this id indicate default model in DM inventory. This model is for unknown model.
   */
  public static final String DEFAULT_MODEL_EXTERNAL_ID = "Default";

  /**
   * Default Model External ID, this id indicate default model in DM inventory. This model is for unknown model.
   */
  public static final String DEFAULT_MANUFACTURER_EXTERNAL_ID = Manufacturer.DEFAULT_MANUFACTURER_EXTERNAL_ID;

  /**
   * Default Firmware version node path
   */
  public static final String DEFAULT_FIRMWARE_VERSION_NODE_PATH = "./DevDetail/FwV";

  /**
   * Return ID
   * @return
   */
  public abstract long getID();

  /**
   * Return manufacturer
   * @return
   */
  public abstract Manufacturer getManufacturer();

  /**
   * Set manufacturer
   * @param nwDmManufacturer
   */
  public abstract void setManufacturer(Manufacturer nwDmManufacturer);

  /**
   * Return name of this model
   * @return
   */
  public abstract String getName();

  /**
   * Set a name for this model.
   * @param name
   */
  public abstract void setName(String name);

  /**
   * Return description 
   * @return
   */
  public abstract String getDescription();

  /**
   * Set description.
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * Return model's external ID of this Model.
   * @return
   */
  public abstract String getManufacturerModelId();

  /**
   * Set model's external ID for this model.
   * @param manufacturerModelId
   */
  public abstract void setManufacturerModelId(String manufacturerModelId);

  /**
   * Return family of model.
   * @return the familyExternalID
   */
  public String getFamilyExternalID();

  /**
   * Set family of model.
   * @param familyExternalID the familyExternalID to set
   */
  public void setFamilyExternalID(String familyExternalID);

  /**
   * Return true, if this model support DownloadMethos
   * @return
   */
  public abstract boolean getSupportedDownloadMethods();

  /**
   * Set true, if this model support DownloadMethos
   * @param supportedDownloadMethods
   */
  public abstract void setSupportedDownloadMethods(boolean supportedDownloadMethods);

  /**
   * Return true, if this model is OmaDmEnabled.
   * @return
   */
  public abstract boolean getIsOmaDmEnabled();

  /**
   * Set true, if this model is OMAEnable.
   * @param isOmaEnabled
   */
  public abstract void setIsOmaDmEnabled(boolean isOmaDmEnabled);

  /**
   * @return the omaDmVersion
   */
  public String getOmaDmVersion();

  /**
   * @param omaDmVersion the omaDmVersion to set
   */
  public void setOmaDmVersion(String omaDmVersion);

  /**
   * @return the isOmaCpEnabled
   */
  public boolean getIsOmaCpEnabled();

  /**
   * @param isOmaCpEnabled the isOmaCpEnabled to set
   */
  public void setIsOmaCpEnabled(boolean isOmaCpEnabled);
  
  /**
   * @return the omaCpVersion
   */
  public String getOmaCpVersion();

  /**
   * @param omaCpVersion the omaCpVersion to set
   */
  public void setOmaCpVersion(String omaCpVersion);

  /**
   * @return the isNokiaOtaEnabled
   */
  public boolean getIsNokiaOtaEnabled();

  /**
   * @param isNokiaOtaEnabled the isNokiaOtaEnabled to set
   */
  public void setIsNokiaOtaEnabled(boolean isNokiaOtaEnabled);

  /**
   * @return the nokieOtaVersion
   */
  public String getNokiaOtaVersion();

  /**
   * @param nokieOtaVersion the nokieOtaVersion to set
   */
  public void setNokiaOtaVersion(String nokiaOtaVersion);

  /**
   * @param isOmaDmEnabled the isOmaDmEnabled to set
   */
  public void setOmaDmEnabled(boolean isOmaDmEnabled);

  /**
   * Return the Authen type of this model in DM Server.
   * @return
   */
  public abstract String getServerAuthType();

  /**
   * Set the Authen type for this model in DM Server.
   * @param serverAuthType
   */
  public abstract void setServerAuthType(String serverAuthType);

  /**
   * Return true, if this model suppoer plain profle.
   * @return
   */
  public abstract boolean getIsPlainProfile();

  /**
   * Set true, if this model suppoer plain profle.
   * @param isPlainProfile
   */
  public abstract void setIsPlainProfile(boolean isPlainProfile);

  /**
   * Return true, if this model use nonce for OMA message.
   * @return
   */
  public abstract boolean getIsUseEncForOmaMsg();

  /**
   * Set true, if this model use nonce for OMA message.
   * @param isUseEncForOmaMsg
   */
  public abstract void setIsUseEncForOmaMsg(boolean isUseEncForOmaMsg);

  /**
   * return true, if this model use next nonce for per-package.
   * @return
   */
  public abstract boolean getIsUseNextNoncePerPkg();

  /**
   * Set true, if this model use next nonce for per-package.
   * @param isUseNextNoncePerPkg
   */
  public abstract void setIsUseNextNoncePerPkg(boolean isUseNextNoncePerPkg);

  /**
   * Return the node pathPath for fetching the device's firmware version.
   * See Spec OMA FUMO.
   * @return
   */
  public abstract String getFirmwareVersionNode();

  /**
   * Set the node pathPath for fetching the device's firmware version.
   * See Spec OMA FUMO.
   * @param firmwareNode
   */
  public abstract void setFirmwareVersionNode(String firmwareNode);

  /**
   * Return the node path for update for the device's firmware.
   * See Spec OMA FUMO.
   * @return
   */
  public abstract String getFirmwareUpdateNode();

  /**
   * Return the node path for update for the device's firmware.
   * See Spec OMA FUMO.
   * @param firmwareUpdateNode
   */
  public abstract void setFirmwareUpdateNode(String firmwareUpdateNode);

  /**
   * Return the node path for download for the device's firmware.
   * See Spec OMA FUMO.
   * @return
   */
  public abstract String getFirmwareDownloadNode();

  /**
   * Set the node path for download for the device's firmware.
   * See Spec OMA FUMO.
   * @param firmwareDownloadNode
   */
  public abstract void setFirmwareDownloadNode(String firmwareDownloadNode);

  /**
   * Return the node path for download and update for the device's firmware.
   * See Spec OMA FUMO.
   * @return
   */
  public abstract String getFirmwareDownloadAndUpdateNode();

  /**
   * Set the node path for download and update for the device's firmware.
   * See Spec OMA FUMO.
   * @param firmwareDownloadAndUpdateNode
   */
  public abstract void setFirmwareDownloadAndUpdateNode(String firmwareDownloadAndUpdateNode);

  /**
   * Return the Node Path for checking state for the device's firmware.
   * See Spec OMA FUMO.
   * @return
   */
  public abstract String getFirmwareStatusNode();

  /**
   * Set the Node Path for checking state for the device's firmware.
   * See Spec OMA FUMO.
   * @param firmwareStatusNode
   */
  public abstract void setFirmwareStatusNode(String firmwareStatusNode);

  /**
   * Return the Bootstrap properties related with the model.
   * 
   * @return
   * @throws DMException
   */
  public Properties getDMBootstrapProperties() throws DMException;

  /**
   * return the DM properties related with the model.
   * 
   * @return
   * @throws DMException
   */
  public Properties getDMProperties() throws DMException;

  /**
   * Return a set of ModelTAC which belong to this model.
   * @return Return a <code>Set</code> of {@java.lang.String} objects.
   */
  public abstract Set<String> getModelTAC();

  /**
   * Return a set of images related with this model.
   * @return Return a <code>Set</code> of {@com.npower.dm.core.Image} objects.
   */
  public abstract Set<Image> getImages();

  //public abstract Set getModelDMBootProps();

  //public abstract Set getModelDMProps();

  //public abstract Set getModelProfileMaps();

  /**
   * Return a list of ProfileMaps which belong to this model.
   * @return  Return a <code>List</code> of {@com.npower.dm.core.ProfileMapping} objects.
   */
  public abstract List<ProfileMapping> getProfileMappings();
  
  /**
   * Return a ProfileMapping which match the template.
   * @param template
   * @return
   */
  public ProfileMapping getProfileMap(ProfileTemplate template);

  /**
   * Return all of ClientProvTemplates boundled with this model
   * @return
   */
  public Set<ClientProvTemplate> getClientProvTemplates();

  /**
   * Return a set of devices which belong to this model.
   * @return  Return a <code>Set</code> of {@com.npower.dm.core.Device} objects.
   */
  public abstract Set<Device> getDevices();

  /**
   * Return a set of ModelDDFTree which belong to this model.
   * @return Return a <code>Set</code> of {@com.npower.dm.core.ModelDDFTree} objects.
   */
  //public abstract Set getModelDDFTrees();

  /**
   * Return a set of DDFTree which belong to this model.
   * @return Return a <code>Set</code> of {@com.npower.dm.core.DDFTree} objects.
   */
  public abstract Set<DDFTree> getDDFTrees();

  /**
   * Assign InputStream as icon content of Binary.
   * 
   * @param in
   * @throws IOException
   */
  public void setIconBinary(InputStream in) throws IOException;

  /**
   * Assign bytes as icon content of Binary.
   * 
   * @param bytes
   */
  public void setIconBinary(byte[] bytes);

  /**
   * @return
   * @throws DMException
   */
  public InputStream getIconInputStream() throws DMException;

  /**
   * @return
   * @throws DMException
   */
  public byte[] getIconBinary() throws DMException;

  /**
   * @return
   */
  public abstract Blob getIcon();

  /**
   * @param icon
   */
  public abstract void setIcon(Blob icon);

  /**
   * @return the operatingSystem
   */
  public String getOperatingSystem();

  /**
   * @param operatingSystem the operatingSystem to set
   */
  public void setOperatingSystem(String operatingSystem);

  /**
   * @return the announcedDate
   */
  public Date getAnnouncedDate();

  /**
   * @param announcedDate the announcedDate to set
   */
  public void setAnnouncedDate(Date announcedDate);

  /**
   * @return the characters
   */
  public Set<ModelCharacter> getCharacters();

  /**
   * @param characters the characters to set
   */
  public void setCharacters(Set<ModelCharacter> characters);
  
  /**
   * Return a character
   * @param category
   * @param name
   * @return
   */
  public ModelCharacter getCharacter(String category, String name);
  
  /**
   * Return value of character
   * @param category
   * @param name
   * @return
   */
  public String getCharacterValue(String category, String name);

  /**
   * Getter LastUpdatedBy
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * Setter LastUpdatedBy
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Getter LastUpdatedTime
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * Getter ChangeVersion
   * @return
   */
  public abstract long getChangeVersion();

  /**
   * @return the createdTime
   */
  public Date getCreatedTime();
}