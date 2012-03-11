/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/ManufacturerItem.java,v 1.1 2008/09/05 03:24:40 zhao Exp $
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/05 03:24:40 $
 */
public class ManufacturerItem {
  
  /**
   * Control flag: trace the filename which defined model for common digester process.
   */
  private String currentModelDefinitionFilename = null;
  
  private String externalID = null;
  private String name = null;
  private String description = null;
  
  private List<ModelItem> models = new ArrayList<ModelItem>();
  private List<String> includedFiles4Models = new ArrayList<String>();

  /**
   * 
   */
  public ManufacturerItem() {
    super();
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
   * @return the models
   */
  public List<ModelItem> getModels() {
    return models;
  }

  /**
   * @param models the models to set
   */
  public void setModels(List<ModelItem> models) {
    this.models = models;
  }
  
  public void addModel(ModelItem model) {
    this.models.add(model);
    model.setDefinedByFilename(this.getCurrentModelDefinitionFilename());
  }

  public void addModelFamily(ModelFamilyItem family) {
    ModelFamilyManager manager = ModelFamilyManager.getInstance();
    manager.addModelFamily(family);
    family.setDefinedByFilename(this.getCurrentModelDefinitionFilename());
  }

  /**
   * @return the includedFiles4Models
   */
  public List<String> getIncludedFiles4Models() {
    return includedFiles4Models;
  }

  /**
   * @param includedFiles4Models the includedFiles4Models to set
   */
  public void setIncludedFiles4Models(List<String> includedFiles4Models) {
    this.includedFiles4Models = includedFiles4Models;
  }

  public void addIncludeFile4Model(String filename) {
    if (StringUtils.isNotEmpty(filename)) {
       this.includedFiles4Models.add(filename);
    }
  }

  /**
   * @return the currentModelDefinitionFilename
   */
  public String getCurrentModelDefinitionFilename() {
    return currentModelDefinitionFilename;
  }

  /**
   * @param currentModelDefinitionFilename the currentModelDefinitionFilename to set
   */
  public void setCurrentModelDefinitionFilename(String currentModelDefinitionFilename) {
    this.currentModelDefinitionFilename = currentModelDefinitionFilename;
  }

}
