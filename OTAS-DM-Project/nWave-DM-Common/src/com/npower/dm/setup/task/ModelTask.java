/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/ModelTask.java,v 1.3 2008/12/14 11:06:09 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/12/14 11:06:09 $
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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Image;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelCharacter;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.Update;
import com.npower.dm.management.ClientProvTemplateBean;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileMappingBean;
import com.npower.dm.management.UpdateImageBean;
import com.npower.setup.core.SetupException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/12/14 11:06:09 $
 */
public class ModelTask extends DMTask {
  
  private static Log log              = LogFactory.getLog(ModelTask.class);

  /**
   * Default constructor
   */
  public ModelTask() {
    super();
  }
  
  private List<String> getFilenames() {
    return this.getPropertyValues("import.files");
  }
  
  /**
   * Assign value into modelItem which are empty
   * @param modelItem
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   */
  private void copyFromFamily(ModelItem item) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    // Get and extend information from model family
    //ModelFamilyManager manager = ModelFamilyManager.getInstance();
    //manager.merge(item);
  }

  /**
   * @param item
   * @param model
   * @throws InvocationTargetException 
   * @throws IllegalAccessException 
   * @throws NoSuchMethodException 
   */
  private void copy(ModelItem item, Model model) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    // Copy ModelItem into Model
    model.setManufacturerModelId(item.getExternalID());
    model.setName(item.getName());
    model.setDescription(item.getDescription());
    model.setFamilyExternalID(item.getFamilyID());
    
    model.setIsOmaDmEnabled(item.getIsOmaDmEnabled());
    model.setOmaDmVersion(item.getOmaDmVersion());
    model.setIsOmaCpEnabled(item.isOmaCpEnabled());
    model.setOmaCpVersion(item.getOmaCpVersion());
    model.setIsNokiaOtaEnabled(item.isNokiaOtaEnabled());
    model.setNokiaOtaVersion(item.getNokiaOtaVersion());
    
    model.setSupportedDownloadMethods(item.getSupportedDownloadMethods());
    model.setFirmwareVersionNode(item.getFirmwareVersionNode());
    model.setFirmwareDownloadNode(item.getFirmwareDownloadNode());
    model.setFirmwareUpdateNode(item.getFirmwareUpdateNode());
    model.setFirmwareDownloadAndUpdateNode(item.getFirmwareDownloadAndUpdateNode());
    model.setFirmwareStatusNode(item.getFirmwareStatusNode());
  }


  public void importFirmware(String manExtID, String modelExtID, String fromVersion, String toVersion,
                           String filename) throws Exception {
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        UpdateImageBean bean = factory.createUpdateImageBean();
  
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manExtID);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExtID);
  
        Image image_1 = bean.getImageByVersionID(model, fromVersion);
        if (image_1 == null) {
           image_1 = bean.newImageInstance(model, fromVersion, true, fromVersion);
        }
        
        Image image_2 = bean.getImageByVersionID(model, toVersion);
        if (image_2 == null) {
           image_2 = bean.newImageInstance(model, toVersion, true, toVersion);
        }
  
        File file_1_to_2 = new File(filename);
        InputStream in_1_to_2 = new FileInputStream(file_1_to_2);
  
        // Test case #1
        Update update_1_to_2 = bean.getUpdate(model, fromVersion, toVersion);
        if (update_1_to_2 != null) {
           update_1_to_2.setBinary(in_1_to_2);
        } else {
          update_1_to_2 = bean.newUpdateInstance(image_1, image_2, in_1_to_2);
        }
        update_1_to_2.setStatus(bean.getImageUpdateStatus(Image.STATUS_RELEASED));
        factory.beginTransaction();
        bean.update(update_1_to_2);
        factory.commit();
  
        this.getSetup().getConsole().println("           [" + 
            fromVersion + " -> " + toVersion + "]");                  

        //
        // Caution: must release the Hibernate Session, otherwise will throw the
        // floowing exception:
        // java.lang.UnsupportedOperationException: Blob may not be manipulated
        // from creating session
        //
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  protected void processModelBasicInformation() throws SetupException {
    List<String> filenames = this.getFilenames();
    
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        
        if (System.getProperty("otas.dm.home") == null) {
        log.error("ModelTask: " + "Could not found 'otas.dm.home' from system properties, please set 'otas.dm.home'.");
        }
        
        DDFTreeBean ddfBean = factory.createDDFTreeBean();
        
        // Import Model, DDF, TACs
        for (String filename: filenames) {
            // Process the file, and import data into database.
            File file = new File(filename);
            if (!file.isAbsolute()) {
               file = new File(this.getSetup().getWorkDir(), filename);
            }

            this.getSetup().getConsole().println("         Loading file [ " + file.getAbsolutePath() + " ]");
            List<ManufacturerItem> items = this.loadManufacturerItems(file.getAbsolutePath());
            for (ManufacturerItem manufacturerItem: items) {
                List<ModelItem> modelItems = manufacturerItem.getModels();
                Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerItem.getExternalID());
                for (ModelItem modelItem: modelItems) {
                    Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelItem.getExternalID());
                    if (model == null) {
                       log.info("Create a new model: " + manufacturer.getExternalId() + " " + modelItem.getExternalID());
                       model = modelBean.newModelInstance();
                       model.setManufacturer(manufacturer);
                    } else {
                      log.info("Modify a model: " + manufacturer.getExternalId() + " " + modelItem.getExternalID());
                    }
                    
                    // Copy information from family
                    this.copyFromFamily(modelItem);
                    // Copy item into model entity
                    this.copy(modelItem, model);
                    
                    this.getSetup().getConsole().println("         * Importing [" + manufacturer.getExternalId() + " " + model.getManufacturerModelId() + "] from [ " + modelItem.getDefinedByFilename() + " ]");
                    factory.beginTransaction();
                    modelBean.update(model);
                    this.getSetup().getConsole().println("           * Basic information processed");
                    
                    // ModelSpec: Icon, ...
                    String iconFilename = modelItem.getIconFile();
                    if (StringUtils.isEmpty(iconFilename)) {
                       iconFilename = this.getSetup().getPropertyValue("model.default.icon.file");
                    }
                    File iconFile = new File(iconFilename);
                    if (!iconFile.isAbsolute()) {
                       iconFile = new File(this.getSetup().getWorkDir(), iconFilename);
                    }
                    if (iconFile.exists()) {
                       model.setIconBinary(new FileInputStream(iconFile));
                    } else {
                      this.getSetup().getConsole().println("           Could not found icon file: " + iconFile.getAbsolutePath());
                    }
                    modelBean.update(model);
                    
                    Map<String, String> specMap = modelItem.getSpecifications();
                    for (String specName: specMap.keySet()) {
                        String category = null;
                        String name = specName;
                        int index = specName.indexOf(".");
                        if (index >= 0) {
                           category = specName.substring(0, index);
                           name = specName.substring(index + 1, specName.length());
                        }
                        String value = specMap.get(specName);
                        List<ModelCharacter> characters = modelBean.findModelCharacters(model, category, name);
                        ModelCharacter character = null;
                        if (characters != null && characters.size() > 0) {
                           // Update exists character
                           character = characters.get(0);
                        } else {
                          // Create a new character
                          character = modelBean.newModelCharacterInstance(model, category, name);
                        }
                        character.setValue(value);
                        modelBean.update(character);
                    }
                    this.getSetup().getConsole().println("           * Specifications processed");
                    
                    // TACs
                    for (String tac: modelItem.getTacs()) {
                        if (StringUtils.isNotEmpty(tac)) {
                           modelBean.addTACInfo(model, tac);
                           this.getSetup().getConsole().println("           * TAC information processed");
                        }
                    }

                    // DDF Files
                    for (String ddfFilename: modelItem.getDdfFiles()) {
                        File ddfFile = new File(ddfFilename);
                        if (!ddfFile.isAbsolute()) {
                           ddfFile = new File(this.getSetup().getWorkDir(), ddfFilename);
                        }
                        DDFTree ddfTree = null;
                        try {
                          ddfTree = ddfBean.parseDDFTree(new FileInputStream(ddfFile));
                        } catch (Exception e) {
                          throw new DMException("Error to parse DDF File: " + ddfFile.getAbsolutePath(), e);
                        }
                        ddfBean.addDDFTree(ddfTree);
                        modelBean.attachDDFTree(model, ddfTree.getID());
                        this.getSetup().getConsole().println("           * DDF information [ " + ddfFile + " ] processed");
                    }
                    factory.commit();
                }
            }
        }
        
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new SetupException("Error in import models.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  protected void processProfileMappings() throws SetupException {
    List<String> filenames = this.getFilenames();
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        ProfileMappingBean mappingBean = factory.createProfileMappingBean();
        
        // Import Profile Mappings
        for (String filename: filenames) {
            // Process the file, and import data into database.
            File file = new File(filename);
            if (!file.isAbsolute()) {
               file = new File(this.getSetup().getWorkDir(), filename);
            }
            
            this.getSetup().getConsole().println("         Loading file [ " + file.getAbsolutePath() + " ]");
            List<ManufacturerItem> items = this.loadManufacturerItems(file.getAbsolutePath());
            for (ManufacturerItem manufacturerItem: items) {
                List<ModelItem> modelItems = manufacturerItem.getModels();
                Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerItem.getExternalID());
                for (ModelItem modelItem: modelItems) {
                    // Copy information from family
                    copyFromFamily(modelItem);
                    
                    Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelItem.getExternalID());
                    
                    if (!modelItem.getProfileMappingFiles().isEmpty()) {
                      this.getSetup().getConsole().println("         * Importings DM Mapping for [ " + manufacturer.getExternalId() + " " + model.getManufacturerModelId() + " ]");
                   }
                    for (String mappingFilename: modelItem.getProfileMappingFiles()) {
                        File mappingFile = new File(mappingFilename);
                        if (!mappingFile.isAbsolute()) {
                           mappingFile = new File(this.getSetup().getWorkDir(), mappingFilename);
                        }
                        
                        List<ProfileMapping> mappings = mappingBean.importProfileMapping(new FileInputStream(mappingFile), 
                                                                                manufacturer.getExternalId(),
                                                                                model.getManufacturerModelId());
                        factory.beginTransaction();
                        for (ProfileMapping mapping: mappings) {
                            modelBean.attachProfileMapping(model, mapping.getID());
                        }
                        factory.commit();
                        this.getSetup().getConsole().println("           [ " + mappingFile.getAbsolutePath() + " ] imported!");                  
                    }
                }
            }
        }
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new SetupException("Error in import models.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  protected void processCPTemplates() throws SetupException {
    List<String> filenames = this.getFilenames();
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        ClientProvTemplateBean cpBean = factory.createClientProvTemplateBean();
        
        // Import Profile Mappings
        for (String filename: filenames) {
            // Process the file, and import data into database.
            File file = new File(filename);
            if (!file.isAbsolute()) {
               file = new File(this.getSetup().getWorkDir(), filename);
            }
            
            this.getSetup().getConsole().println("         Loading file [ " + file.getAbsolutePath() + " ]");
            List<ManufacturerItem> items = this.loadManufacturerItems(file.getAbsolutePath());
            for (ManufacturerItem manufacturerItem: items) {
                List<ModelItem> modelItems = manufacturerItem.getModels();
                Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerItem.getExternalID());
                for (ModelItem modelItem: modelItems) {
                    // Copy information from family
                    this.copyFromFamily(modelItem);
                    
                    Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelItem.getExternalID());
                    
                    if (!modelItem.getCpTemplatesFiles().isEmpty()) {
                       this.getSetup().getConsole().println("         * Importing CP Templates for [ " + manufacturer.getExternalId() + " " + model.getManufacturerModelId() + " ]");
                    }
                    for (String templateFilename: modelItem.getCpTemplatesFiles()) {
                        File templateFile = new File(templateFilename);
                        if (!templateFile.isAbsolute()) {
                          templateFile = new File(this.getSetup().getWorkDir(), templateFilename);
                        }
                        InputStream in = new FileInputStream(templateFile);
                        List<ClientProvTemplate> templates = cpBean.importClientProvTemplates(in, this.getSetup().getWorkDir());
                        factory.beginTransaction();
                        for (ClientProvTemplate template: templates) {
                            cpBean.attach(model, template);
                        }
                        factory.commit();
                        this.getSetup().getConsole().println("           [ " + templateFile + " ] imported!");                  
                    }
                }
            }
        }
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new SetupException("Error in import models.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  protected void processFirmwares() throws SetupException {
    List<String> filenames = this.getFilenames();
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        
        // Import Profile Mappings
        for (String filename: filenames) {
            // Process the file, and import data into database.
            File file = new File(filename);
            if (!file.isAbsolute()) {
               file = new File(this.getSetup().getWorkDir(), filename);
            }
            
            this.getSetup().getConsole().println("         Loading file [ " + file.getAbsolutePath() + " ]");
            List<ManufacturerItem> items = this.loadManufacturerItems(file.getAbsolutePath());
            for (ManufacturerItem manufacturerItem: items) {
                List<ModelItem> modelItems = manufacturerItem.getModels();
                Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerItem.getExternalID());
                for (ModelItem modelItem: modelItems) {
                    // Copy information from family
                    this.copyFromFamily(modelItem);
                    
                    Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelItem.getExternalID());
                    
                    if (!modelItem.getFirmwares().isEmpty()) {
                       this.getSetup().getConsole().println("         * Importing Firmwares for [ " + manufacturer.getExternalId() + " " + model.getManufacturerModelId() + " ]");
                    }
                    for (FirmwareItem firmwareItem: modelItem.getFirmwares()) {
                        File firmwareFile = new File(firmwareItem.getFilename());
                        if (!firmwareFile.isAbsolute()) {
                           firmwareFile = new File(this.getSetup().getWorkDir(), firmwareItem.getFilename());
                        }
                        if (!firmwareFile.exists()) {
                           this.getSetup().getConsole().println("Could not load firmware from: " + firmwareFile.getAbsolutePath());
                           continue;
                        }
                        String fromVersion = firmwareItem.getFromVersion();
                        String toVersion = firmwareItem.getToVersion();
                        this.importFirmware(manufacturer.getExternalId(), model.getManufacturerModelId(), fromVersion, toVersion, firmwareFile.getAbsolutePath());
                    }
                    
                }
            }
        }
    } catch (Exception ex) {
      throw new SetupException("Error in import models.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.setup.core.AbstractTask#process()
   */
  @Override
  protected void process() throws SetupException {
    
    // Import models
    this.getSetup().getConsole().println("    [1]. Process and import model definitions");
    this.processModelBasicInformation();
    
    // Import Firmwares
    this.getSetup().getConsole().println("    [2]. Process and import model firmwares");
    this.processFirmwares();
    
    // Import profile mappings
    this.getSetup().getConsole().println("    [3]. Process and import model DM profile mappings");
    this.processProfileMappings();
    
    // Import CP Templates
    this.getSetup().getConsole().println("    [4]. Process and import model OMA Client Prov templates");
    this.processCPTemplates();
  }

  /* (non-Javadoc)
   * @see com.npower.setup.core.AbstractTask#rollback()
   */
  @Override
  protected void rollback() throws SetupException {
  }

}
