/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/ModelBean.java,v 1.26 2008/10/31 10:03:04 zhao Exp $
 * $Revision: 1.26 $
 * $Date: 2008/10/31 10:03:04 $
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
package com.npower.dm.management;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Criteria;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelCharacter;
import com.npower.dm.core.ModelFamily;

/**
 * @author Zhao DongLu
 * 
 */
public interface ModelBean extends BaseBean {

  /**
   * Default Model ID, this id indicate a model in DM inventory. This model is for unknown model.
   */
  //public static final String DEFAULT_MODEL_ID = "3000";

  /**
   * Default Model External ID, this id indicate default model in DM inventory. This model is for unknown model.
   */
  public static final String DEFAULT_MODEL_EXTERNAL_ID = Model.DEFAULT_MODEL_EXTERNAL_ID;

  /**
   * Default Model External ID, this id indicate default model in DM inventory. This model is for unknown model.
   */
  public static final String DEFAULT_MANUFACTURER_EXTERNAL_ID = Manufacturer.DEFAULT_MANUFACTURER_EXTERNAL_ID;

  /**
   * Create a instance of manufacturer
   * @return
   * @throws DMException
   */
  public abstract Manufacturer newManufacturerInstance() throws DMException;
  
  /**
   * Create a instance of manufacturer
   * @param name
   * @param manufacturerExternalId
   * @param description
   * @return
   * @throws DMException
   */
  public abstract Manufacturer newManufacturerInstance(String name, String manufacturerExternalId, String description)
      throws DMException;

  /**
   * Get a manufacturer by ID from DM Inventory.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract Manufacturer getManufacturerByID(String id) throws DMException;

  /**
   * Get a manufacturer by ExternalID from DM Inventory.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract Manufacturer getManufacturerByExternalID(String id) throws DMException;

  /**
   * Add a manufactuer. If the manufacturer include a set of model, the method
   * will add these models before add the manufacturer.
   * 
   * @param manufacturer
   * @throws DMException
   */
  public abstract void update(Manufacturer manufacturer) throws DMException;

  /**
   * Delete a manufacturer from DM inventory, and remove all of data related
   * with this manufacturer. Data which will be deleted are: ModelEntity
   * 
   * eg: all of models will be deleted.
   * 
   * 
   * @param manufacturer
   *          ManufacturerEntity
   * @throws DMException
   */
  public abstract void delete(Manufacturer manufacturer) throws DMException;

  /**
   * Retrieve all of Manufacturers.
   * 
   * @return List Array of ManufacturerEntity
   * @throws DMException
   */
  public abstract List<Manufacturer> getAllManufacturers() throws DMException;

  /**
   * Retrieve of Manufacturers. eg: "from ManufacturerEntity where ..."
   * 
   * @return List Array of ManufacturerEntity
   * @throws DMException
   */
  public abstract List<Manufacturer> findManufacturers(String whereClause) throws DMException;

  /**
   * Create a instance of model
   * @param manufacturer
   * @param manufacturerModelId
   * @param name
   * @param supportedDownloadMethods
   * @param isOmaEnabled
   * @param isPlainProfile
   * @param isUseEncForOmaMsg
   * @param isUseNextNoncePerPkg
   * @return
   * @throws DMException
   */
  public abstract Model newModelInstance() throws DMException;

  /**
   * Create a instance of model
   * @param manufacturer
   * @param manufacturerModelId
   * @param name
   * @param supportedDownloadMethods
   * @param isOmaEnabled
   * @param isPlainProfile
   * @param isUseEncForOmaMsg
   * @param isUseNextNoncePerPkg
   * @return
   * @throws DMException
   */
  public abstract Model newModelInstance(Manufacturer manufacturer, String manufacturerModelId, String name,
      boolean supportedDownloadMethods, boolean isOmaEnabled, boolean isPlainProfile, boolean isUseEncForOmaMsg,
      boolean isUseNextNoncePerPkg) throws DMException;

  /**
   * Find ModelEntity by ModelID from DM inventory.
   * 
   * @param id
   *          ModelID
   * @throws DMException
   */
  public abstract Model getModelByID(String id) throws DMException;

  /**
   * Get a ModelEntity by ManufacturerEntity and ModelEntity's manufacturer
   * modelID
   * 
   * @param manufacturer
   * @param id
   * @return
   * @throws DMException
   */
  public abstract Model getModelByManufacturerModelID(Manufacturer manufacturer, String id) throws DMException;

  /**
   * Get a ModelEntity by mode name
   * @param manufacturer
   * @param id
   * @return
   * @throws DMException
   */
  public Model getModelByName(Manufacturer manufacturer, String id) throws DMException;
  
  /**
   * Get a ModelEntity by mode name
   * @param manufacturerExtId
   * @param modelExtId
   * @return
   * @throws DMException
   */
  public Model getModelByExternalId(String manufacturerExtId, String modelExtId) throws DMException;
  
  /**
   * Add or update a model into DM inventory.
   * 
   * @param model
   * @throws DMException
   */
  // public abstract void add(ModelEntity model) throws DMException;
  /**
   * Get all of model in DM inventory.
   * 
   * @return
   * @throws DMException
   */
  public abstract List<Model> getAllModel() throws DMException;

  /**
   * Return all of model belong to the manufacturer.
   * 
   * @param manufacturer
   * @return
   * @throws DMException
   */
  public abstract List<Model> getAllModel(Manufacturer manufacturer) throws DMException;

  /**
   * Find model from DM inventory by the criterira.
   * 
   * @param criteria
   * @return
   * @throws DMException
   */
  public abstract List<Model> findModel(Criteria criteria) throws DMException;
  
  /**
   * Return a model family
   * @param extID
   * @return
   * @throws DMException
   */
  public abstract ModelFamily getModelFamilyByExtID(String extID) throws DMException;

  /**
   * 返回所有属于familyExtID的型号
   * @param familyExtID
   * @return
   */
  public abstract List<Model> findModelsByFamilyExtID(String familyExtID) throws DMException;

  /**
   * Find a ModelEntity by TAC Info from DM Inventory.
   * 
   * @param tacInfo
   * @return
   * @throws DMException
   */
  public abstract Model getModelbyTAC(String tacInfo) throws DMException;

  /**
   * UpdateEntity the model into DM inventory.
   * 
   * @param model
   * @throws DMException
   */
  public abstract void update(Model model) throws DMException;

  /**
   * Delete model from DM inventory, and remove all of data related with the
   * model, include: ModelDDFTree (only delete reference to DDFTreeEntity,
   * the DDFTreeEntity will not be deletd),
   * 
   * 
   * @param nodel
   * @throws DMException
   */
  public abstract void delete(Model model) throws DMException;

  /**
   * Add a tac string into the model's TAC library.
   * This method will check length of TacInfo.
   * If len > 8, will be substring to 8 chars.
   * 
   * If tac exists, will be ignored.
   * 
   * @param model
   * @param TACInfo
   * @throws DMException
   */
  public abstract void addTACInfo(Model model, String TACInfo) throws DMException;

  /**
   * Return all of family
   * @return
   * @throws DMException
   */
  public abstract List<ModelFamily> getAllModelFamily() throws DMException;
  
  /**
   * Set tacs into Model, all of old tac will be clean and overwrite.
   * 
   * @param model
   * @param TACInfo
   * @throws DMException
   */
  public abstract void setTACInfos(Model model, List<String> TACInfos) throws DMException;

  /**
   * Store the properties into ModelEntity's DMBootstrap Properties. Old
   * properties will be replaced by the props.
   * 
   * @param props
   *          Properties；
   * @throws DMException
   */
  public void setDMBootstrapProperties(Model model, Properties props) throws DMException;

  /**
   * Store the properties into ModelEntity's DM Properties. Old properties will
   * be replaced by the props.
   * 
   * @param props
   *          Properties；
   * @throws DMException
   */
  public void setDMProperties(Model model, Properties props) throws DMException;

  /**
   * Attach a DDFTreeEntity to ModelEntity. The DDFTreeEntity must save into
   * DMInventory before call the method.
   * 
   * @param model
   *          ModelEntity
   * @param ddfTreeID
   *          long ID of DDFTreeEntity in DM inventory which will be attached to
   *          ModelEntity
   * 
   * @throws DMException
   */
  public abstract void attachDDFTree(Model model, long ddfTreeID) throws DMException;

  /**
   * De-attache a DDFTreeEntity from ModelEntity. The method will also delete
   * the DDFTreeEntity from DM inventory.
   * 
   * @param model
   * @param ddfTreeID
   * @throws DMException
   */
  public abstract void dettachDDFTree(Model model, long ddfTreeID) throws DMException;

  /**
   * <pre>
   * Attache a ProfileMappingEntity to ModelEntity. 
   * Notes:
   * 1. This method will dettach all of old profile mapping and delete all of old profile mapping.
   * 2. The ProfileMappingEntity must save into DM inventory before call this method.
   * 
   * </pre>
   * @param model
   * @param profileMappingID
   * @throws DMException
   */
  public abstract void attachProfileMapping(Model model, long profileMappingID) throws DMException;

  /**
   * <pre>
   * Detach a ProfileMappingEntity from ModelEntity. 
   * The method doesn't delete the ProfileMappingEntity from DM inventory.
   * </pre>
   * 
   * @param model
   * @param profileMappingID
   * @throws DMException
   */
  public abstract void dettachProfileMapping(Model model, long profileMappingID) throws DMException;
  
  /**
   * 
   * <pre>
   * Import the TAC list of models form file.
   * <pre>
   * 
   * @param xmlFile
   * @throws DMException
   */
  public abstract void importModelTAC(File xmlFile) throws DMException;
  
  /**
   * 
   * <pre>
   * Export the TAC form database by Model.
   * <pre>
   * 
   * @param model
   * @return
   * @throws DMException
   */
  public abstract void exportModelTAC(Model model, String outFile) throws DMException;
  
  /**
   * 
   * <pre>
   * Export the TAC form database by Manufacturer.
   * <pre>
   * 
   * @param manufacturer
   * @return
   * @throws DMException
   */
  public abstract void exportModelTAC(Manufacturer manufacturer, String outFile) throws DMException;

  /**
   * Return Capability Matrix
   * @param model
   * @return
   */
  public Map<String, String> getCapabilityMatrix(Model model) throws Exception;

  /**
   * Return Capability Matrix.
   * String v = modelBean.getCapability(model, "j2me_midp_1_0")
   * @param model
   * @param capability
   * @return
   * @throws Exception
   */
  public String getCapability(Model model, String capability) throws Exception;
  
  /**
   * Create a instance of ModelCharacter.
   * 
   * @param model
   * @param category
   * @param name
   * @return
   * @throws DMException
   */
  public abstract ModelCharacter newModelCharacterInstance(Model model, String category, String name) throws DMException;

  /**
   * Update the ModelCharacter into DM inventory.
   * 
   * @param model
   * @throws DMException
   */
  public abstract void update(ModelCharacter character) throws DMException;

  /**
   * Delete ModelCharacter from DM inventory
   * 
   * 
   * @param nodel
   * @throws DMException
   */
  public abstract void delete(ModelCharacter character) throws DMException;

  /**
   * Find ModelCharacter by ID from DM inventory.
   * 
   * @param id
   *          ID
   * @throws DMException
   */
  public abstract ModelCharacter getModelCharacterByID(long id) throws DMException;

  /**
   * Find characters.
   * 
   * @param model
   * @param category
   * @param name
   * @return
   * @throws DMException
   */
  public List<ModelCharacter> findModelCharacters(Model model, String category, String name) throws DMException;

  /**
   * Given the UA of a device, find the device ID of the associated device.
   * Match may be loose, meaning that <code>MOT-T720/G_05.01.43R</code> will
   * match
   * <code>MOT-T720/05.08.41R MIB/2.0 Profile/MIDP-1.0 Configuration/CLDC-1.0</code>.
   * (assuming no better match is found). Observe that this is more powerful
   * than a simple substring match. Matching UAs loosely is an expensive
   * operation, but UAManager implements a cache that helps a lot.
   * 
   * @param ua
   * @return
   * @throws DMException
   */
  public Model getModelFromUA(String ua) throws DMException;
  
}