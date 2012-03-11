/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/DMTask.java,v 1.9 2008/12/16 04:19:42 chenlei Exp $
 * $Revision: 1.9 $
 * $Date: 2008/12/16 04:19:42 $
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import org.apache.commons.digester.Digester;




import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.setup.task.digester.ModelFamilyItemObjectCreationFactory;
import com.npower.dm.setup.task.digester.ModelItemObjectCreationFactory;
import com.npower.setup.core.AbstractTask;
import com.npower.setup.core.SetupException;
import com.npower.setup.core.SetupProperties;
import com.npower.setup.util.SetupPropertiesHelper;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/12/16 04:19:42 $
 */
public abstract class DMTask extends AbstractTask {

  private ManagementBeanFactory managementBeanFactory = null;
  
  /**
   * 
   */
  public DMTask() {
    super();
  }

  /**
   * @return the managementBeanFactory
   */
  ManagementBeanFactory getManagementBeanFactory() throws DMException {
    if (this.managementBeanFactory == null) {
       List<SetupProperties> setOfProperties = this.getSetOfProperties();
       Properties properties = SetupPropertiesHelper.getProperties(setOfProperties);
       return ManagementBeanFactory.newInstance(properties);
    }
    return managementBeanFactory;
  }

  /**
   * @param managementBeanFactory the managementBeanFactory to set
   */
  void setManagementBeanFactory(ManagementBeanFactory managementBeanFactory) {
    this.managementBeanFactory = managementBeanFactory;
  }

  /**
   * Create Digester rules for manufacturers and models
   * @return
   */
  protected Digester createManufacturerDigester() {
    // Initialize the digester
    Digester digester = new Digester();
    digester.setValidating(false);
    // Manufacturer stage
    digester.addObjectCreate("*/Manufacturer", ManufacturerItem.class);
    digester.addBeanPropertySetter("*/Manufacturer/ExternalID", "externalID");
    digester.addBeanPropertySetter("*/Manufacturer/Name", "name");
    digester.addBeanPropertySetter("*/Manufacturer/Description", "description");
    digester.addSetNext("*/Manufacturer", "add");
    
    digester.addCallMethod("*/Models", "addIncludeFile4Model", 1);
    digester.addCallParam("*/Models", 0, "filename");

    // Model stage
    digester.addFactoryCreate("*/Models/Model", ModelItemObjectCreationFactory.class);
    //digester.addObjectCreate("*/Models/Model", ModelItem.class);
    digester.addSetNext("*/Models/Model", "addModel");
    
    // Basic information
    //digester.addCallMethod("*/Models/Model", "setFamilyID", 1);
    //digester.addCallParam("*/Models/Model", 0, "family");
    digester.addBeanPropertySetter("*/Models/Model/ExternalID", "externalID");
    digester.addBeanPropertySetter("*/Models/Model/Name", "name");
    digester.addBeanPropertySetter("*/Models/Model/Description", "description");
    //digester.addBeanPropertySetter("*/Models/Model/FamilyID", "familyID");
    digester.addBeanPropertySetter("*/Models/Model/IconFile", "iconFile");
    digester.addBeanPropertySetter("*/Models/Model/IsOmaDmEnabled", "isOmaDmEnabled");
    digester.addBeanPropertySetter("*/Models/Model/OmaDmVersion", "omaDmVersion");
    digester.addBeanPropertySetter("*/Models/Model/IsOmaCpEnabled", "isOmaCpEnabled");
    digester.addBeanPropertySetter("*/Models/Model/OmaCpVersion", "omaCpVersion");
    digester.addBeanPropertySetter("*/Models/Model/IsNokiaOtaEnabled", "isNokiaOtaEnabled");
    digester.addBeanPropertySetter("*/Models/Model/NokiaOtaVersion", "nokiaOtaVersion");
    digester.addBeanPropertySetter("*/Models/Model/SupportedDownloadMethods", "supportedDownloadMethods");
    digester.addBeanPropertySetter("*/Models/Model/FirmwareVersionNode", "firmwareVersionNode");
    digester.addBeanPropertySetter("*/Models/Model/FirmwareUpdateNode", "firmwareUpdateNode");
    digester.addBeanPropertySetter("*/Models/Model/FirmwareDownloadNode", "firmwareDownloadNode");
    digester.addBeanPropertySetter("*/Models/Model/FirmwareDownloadAndUpdateNode", "firmwareDownloadAndUpdateNode");
    digester.addBeanPropertySetter("*/Models/Model/FirmwareStatusNode", "firmwareStatusNode");
    // DDF Information
    digester.addCallMethod("*/Models/Model/DDFs/DDF", "addDdfFile", 1);
    digester.addCallParam("*/Models/Model/DDFs/DDF", 0, "filename");
    // DM Profile Mappings
    digester.addCallMethod("*/Models/Model/ProfileMappings/ProfileMapping", "addProfileMappingFile", 1);
    digester.addCallParam("*/Models/Model/ProfileMappings/ProfileMapping", 0, "filename");
    // CP Templates
    digester.addCallMethod("*/Models/Model/CPTemplates", "addCpTemplatesFiles", 1);
    digester.addCallParam("*/Models/Model/CPTemplates", 0, "filename");
    // TAC Informations
    digester.addCallMethod("*/Models/Model/TACs/TAC", "addTac", 1);
    digester.addCallParam("*/Models/Model/TACs/TAC", 0);
    // Firmwares
    digester.addObjectCreate("*/Models/Model/Firmwares/Firmware", FirmwareItem.class);
    digester.addBeanPropertySetter("*/Models/Model/Firmwares/Firmware/FromVersion", "fromVersion");
    digester.addBeanPropertySetter("*/Models/Model/Firmwares/Firmware/ToVersion", "toVersion");
    digester.addBeanPropertySetter("*/Models/Model/Firmwares/Firmware/Status", "status");
    digester.addBeanPropertySetter("*/Models/Model/Firmwares/Firmware/Filename", "filename");
    digester.addSetNext("*/Models/Model/Firmwares/Firmware", "addFirmware");
    // Specifications
    digester.addCallMethod("*/Models/Model/Specifications/Specification", "setSpecification", 3);
    digester.addCallParam("*/Models/Model/Specifications/Specification", 0, "category");
    digester.addCallParam("*/Models/Model/Specifications/Specification", 1, "name");
    digester.addCallParam("*/Models/Model/Specifications/Specification", 2, "value");
    
    // Model Family stage
    digester.addFactoryCreate("*/Models/Family", ModelFamilyItemObjectCreationFactory.class);
    //digester.addObjectCreate("*/Models/Family", ModelFamilyItem.class);
    digester.addSetNext("*/Models/Family", "addModelFamily");
    // Basic information
    //digester.addCallMethod("*/Models/Family", "setParentID", 1);
    //digester.addCallParam("*/Models/Family", 0, "parent");
    //digester.addSetNestedProperties("*/Models/Family", "parent", "parentID");
    digester.addBeanPropertySetter("*/Models/Family/ExternalID", "externalID");
    digester.addBeanPropertySetter("*/Models/Family/Name", "name");
    digester.addBeanPropertySetter("*/Models/Family/Description", "description");
    //digester.addBeanPropertySetter("*/Models/Family/ParentID", "parentID");
    digester.addBeanPropertySetter("*/Models/Family/IconFile", "iconFile");
    digester.addBeanPropertySetter("*/Models/Family/IsOmaDmEnabled", "isOmaDmEnabled");
    digester.addBeanPropertySetter("*/Models/Family/OmaDmVersion", "omaDmVersion");
    digester.addBeanPropertySetter("*/Models/Family/IsOmaCpEnabled", "isOmaCpEnabled");
    digester.addBeanPropertySetter("*/Models/Family/OmaCpVersion", "omaCpVersion");
    digester.addBeanPropertySetter("*/Models/Family/IsNokiaOtaEnabled", "isNokiaOtaEnabled");
    digester.addBeanPropertySetter("*/Models/Family/NokiaOtaVersion", "nokiaOtaVersion");
    digester.addBeanPropertySetter("*/Models/Family/SupportedDownloadMethods", "supportedDownloadMethods");
    digester.addBeanPropertySetter("*/Models/Family/FirmwareVersionNode", "firmwareVersionNode");
    digester.addBeanPropertySetter("*/Models/Family/FirmwareUpdateNode", "firmwareUpdateNode");
    digester.addBeanPropertySetter("*/Models/Family/FirmwareDownloadNode", "firmwareDownloadNode");
    digester.addBeanPropertySetter("*/Models/Family/FirmwareDownloadAndUpdateNode", "firmwareDownloadAndUpdateNode");
    digester.addBeanPropertySetter("*/Models/Family/FirmwareStatusNode", "firmwareStatusNode");
    // DDF Information
    digester.addCallMethod("*/Models/Family/DDFs/DDF", "addDdfFile", 1);
    digester.addCallParam("*/Models/Family/DDFs/DDF", 0, "filename");
    // DM Profile Mappings
    digester.addCallMethod("*/Models/Family/ProfileMappings/ProfileMapping", "addProfileMappingFile", 1);
    digester.addCallParam("*/Models/Family/ProfileMappings/ProfileMapping", 0, "filename");
    // CP Templates
    digester.addCallMethod("*/Models/Family/CPTemplates", "addCpTemplatesFiles", 1);
    digester.addCallParam("*/Models/Family/CPTemplates", 0, "filename");
    // Firmwares
    digester.addObjectCreate("*/Models/Family/Firmwares/Firmware", FirmwareItem.class);
    digester.addBeanPropertySetter("*/Models/Family/Firmwares/Firmware/FromVersion", "fromVersion");
    digester.addBeanPropertySetter("*/Models/Family/Firmwares/Firmware/ToVersion", "toVersion");
    digester.addBeanPropertySetter("*/Models/Family/Firmwares/Firmware/Status", "status");
    digester.addBeanPropertySetter("*/Models/Family/Firmwares/Firmware/Filename", "filename");
    digester.addSetNext("*/Models/Family/Firmwares/Firmware", "addFirmware");
    // Specifications
    digester.addCallMethod("*/Models/Family/Specifications/Specification", "setSpecification", 3);
    digester.addCallParam("*/Models/Family/Specifications/Specification", 0, "category");
    digester.addCallParam("*/Models/Family/Specifications/Specification", 1, "name");
    digester.addCallParam("*/Models/Family/Specifications/Specification", 2, "value");

    return (digester);
  }

  protected Digester createSoftwareCategoryDigester() {
    Digester digester = new Digester();
    digester.addObjectCreate("*/SoftwareCategory", SoftwareCategoryItem.class);    
    digester.addBeanPropertySetter("*/SoftwareCategory/Name", "name");
    digester.addBeanPropertySetter("*/SoftwareCategory/Description", "description");
    digester.addSetNext("*/SoftwareCategory", "add");
    digester.addObjectCreate("*/SoftwareCategory/SoftwareCategory", SoftwareCategoryItem.class);
    digester.addBeanPropertySetter("*/SoftwareCategory/SoftwareCategory/Name", "name");
    digester.addBeanPropertySetter("*/SoftwareCategory/SoftwareCategory/Description", "description");    
    digester.addSetNext("*/SoftwareCategory/SoftwareCategory", "addSoftwareCategory");
    return digester;
  }
  
  protected Digester createSoftwareDigester() {
    Digester digester = new Digester();
    digester.setValidating(false);
    digester.addObjectCreate("*/Software", SoftwareItem.class);
    digester.addBeanPropertySetter("*/Software/ExternalId", "externalId");
    digester.addBeanPropertySetter("*/Software/Vendor", "vendor");
    digester.addBeanPropertySetter("*/Software/Category", "category");
    digester.addBeanPropertySetter("*/Software/Name", "name");
    digester.addBeanPropertySetter("*/Software/Version","version");
    digester.addBeanPropertySetter("*/Software/Status", "status");
    digester.addBeanPropertySetter("*/Software/LicenseType", "licenseType");
    digester.addBeanPropertySetter("*/Software/Description", "description");
    digester.addObjectCreate("*/Software/Packages/Package", SoftwarePackageItem.class);
    digester.addBeanPropertySetter("*/Software/Packages/Package/Name", "name");
    
    digester.addObjectCreate("*/Software/Packages/Package/Remote", SoftwarePackage4RemoteItem.class);
    digester.addBeanPropertySetter("*/Software/Packages/Package/Remote/Url", "url");
    digester.addSetNext("*/Software/Packages/Package/Remote", "setRemote");

    digester.addObjectCreate("*/Software/Packages/Package/Local", SoftwarePackage4LocalItem.class);
    digester.addBeanPropertySetter("*/Software/Packages/Package/Local/MimeType", "mimeType");
    digester.addBeanPropertySetter("*/Software/Packages/Package/Local/File", "file");
    digester.addSetNext("*/Software/Packages/Package/Local", "setLocal");    
    
   
    
    digester.addObjectCreate("*/Software/Packages/Package/InstallOptions/InstOpts", SoftwarePackage4InstallOptionsItem.class);
    digester.addObjectCreate("*/Software/Packages/Package/InstallOptions/InstOpts/StdOpt", SoftwarePackage4StdOptItem.class);
    digester.addSetProperties("*/Software/Packages/Package/InstallOptions/InstOpts/StdOpt");
    digester.addSetNext("*/Software/Packages/Package/InstallOptions/InstOpts/StdOpt", "addStdOpt");
    digester.addObjectCreate("*/Software/Packages/Package/InstallOptions/InstOpts/StdSymOpt", SoftwarePackage4StdSymOptItem.class);
    digester.addSetProperties("*/Software/Packages/Package/InstallOptions/InstOpts/StdSymOpt");
    digester.addSetNext("*/Software/Packages/Package/InstallOptions/InstOpts/StdSymOpt", "addStdSymOpt");
    digester.addSetNext("*/Software/Packages/Package/InstallOptions/InstOpts", "addInstallOptions");
    
    
    digester.addObjectCreate("*/Software/Packages/Package/TargetModels/Model", SoftwarePackage4TargetModelsItem.class);
    digester.addBeanPropertySetter("*/Software/Packages/Package/TargetModels/Model/Vendor", "vendor");
    digester.addBeanPropertySetter("*/Software/Packages/Package/TargetModels/Model/Name", "name");
    digester.addSetNext("*/Software/Packages/Package/TargetModels/Model", "addModels");
    digester.addSetNext("*/Software/Packages/Package", "addSoftwarePackage");
    digester.addSetNext("*/Software", "add");
    
    return digester;
  }
  
  /**
   * Load ManufacturerItems from DM setup data files.
   * @param filename
   * @return
   */
  protected List<ManufacturerItem> loadManufacturerItems(String filename) throws SetupException {
    List<ManufacturerItem> result = new ArrayList<ManufacturerItem>();
    try {
        Digester digester = this.createManufacturerDigester();
        digester.push(result);
        digester.parse(new FileInputStream(filename));
        
        for (ManufacturerItem item: result) {
            List<String> includeFilenames4Model = item.getIncludedFiles4Models();
            for (String includeFilename: includeFilenames4Model) {
                digester.push(item);
                File file = new File(includeFilename);
                if (!file.isAbsolute()) {
                   file = new File(this.getSetup().getWorkDir(), includeFilename);
                }
                // Trace definition file
                item.setCurrentModelDefinitionFilename(file.getAbsolutePath());
                
                digester.parse(new FileInputStream(file));
            }
        }
        
        return result;
    } catch (Exception e) {
      throw new SetupException(e);
    }
  }
  
  protected List<SoftwareVenderItem> loadSoftwareVenderImporter(String filename) throws SetupException {
    List<SoftwareVenderItem> result = new ArrayList<SoftwareVenderItem>();
    try {
        Digester digester = this.createSoftwareVenderDigester();
        digester.push(result);
        digester.parse(new FileInputStream(filename));                
        return result;
    } catch (Exception e) {
      throw new SetupException(e);
    }
  }

  protected Digester createSoftwareVenderDigester() {
    // Initialize the digester
    Digester digester = new Digester();
    digester.setValidating(false);
    
    digester.addObjectCreate("*/SoftwareVendor", SoftwareVenderItem.class);
    digester.addBeanPropertySetter("*/SoftwareVendor/Name", "name");
    digester.addBeanPropertySetter("*/SoftwareVendor/Description", "description");
    digester.addSetNext("*/SoftwareVendor", "add");
    return digester;
  }
 
  
  protected List<SoftwareCategoryItem> loadSoftwareCategoryItems(String filename) throws SetupException {
    List<SoftwareCategoryItem> result = new ArrayList<SoftwareCategoryItem>();
    try {
        Digester digester = this.createSoftwareCategoryDigester();
        digester.push(result);
        digester.parse(new FileInputStream(filename));       
        return result;
    } catch (Exception e) {
      throw new SetupException(e);
    }
  }
  
  protected List<SoftwareItem> loadSoftwareItems(String filename) throws SetupException {
    List<SoftwareItem> result = new ArrayList<SoftwareItem>();
    try {
        Digester digester = this.createSoftwareDigester();
        digester.push(result);
        digester.parse(new FileInputStream(filename));       
        return result;
    } catch (Exception e) {
      throw new SetupException(e);
    }
  }
  
}