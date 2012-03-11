/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/ModelFetchTask.java,v 1.1 2008/09/05 03:24:40 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/09/05 03:24:40 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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
import java.util.Map;

import org.apache.commons.digester.Digester;

import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelCharacter;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.setup.core.SetupException;

/**
 * @author Liu AiHui
 * @version $Revision: 1.1 $ $Date: 2008/09/05 03:24:40 $
 */

public class ModelFetchTask extends DMTask {
  /**
   * 
   */
  public ModelFetchTask() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.setup.core.AbstractTask#process()
   */
  @Override
  protected void process() throws SetupException {
    List<String> filenames = getFilenames();
    ManagementBeanFactory factory = null;
    try {
      factory = this.getManagementBeanFactory();
      ModelBean modelBean = factory.createModelBean();
      factory.beginTransaction();
      for (String filename : filenames) {
        File file = new File(filename);
        if (!file.isAbsolute()) {
          file = new File(this.getSetup().getWorkDir(), filename);
        }
        this.getSetup().getConsole().println("         Loading file [ " + file.getAbsolutePath() + " ]");
        List<ModelFetchItem> items = this.loadModelFetchItems(file.getAbsolutePath());
        for (ModelFetchItem modelFetchItem : items) {
          Manufacturer manufacturer = modelBean.getManufacturerByExternalID(modelFetchItem.getManufacturerExternalID());
          if(manufacturer == null){
            continue;
          }
          Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelFetchItem.getExternalID());
          if(model == null){
            continue;
          }
          this.getSetup().getConsole().println("         Importing Model [ " + manufacturer.getExternalId() +" " + model.getManufacturerModelId() + " ]");
          Map<String, String> specMap = modelFetchItem.getSpecifications();
          for (String specName : specMap.keySet()) {
            String category = null;
            String name = specName;
            int index = specName.indexOf(".");
            if (index >= 0) {
              category = specName.substring(0, index);
              name = specName.substring(index + 1, specName.length());
            }
            String value = specMap.get(specName);           
            ModelCharacter character = model.getCharacter(category, name);
            if(character == null){
              character = modelBean.newModelCharacterInstance(model, category, name);              
            }
            character.setValue(value);
            modelBean.update(character);
          }
        }
      }
      factory.commit();
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new SetupException("Error in import model specifications.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  private List<String> getFilenames() {
    return this.getPropertyValues("import.files");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.setup.core.AbstractTask#rollback()
   */
  @Override
  protected void rollback() throws SetupException {
  }

  protected Digester createManufacturerDigester() {
    // Initialize the digester
    Digester digester = new Digester();
    digester.setValidating(false);

    // Model stage
    digester.addObjectCreate("*/Models/Model", ModelFetchItem.class);
    digester.addSetNext("*/Models/Model", "add");

    // Basic information
    digester.addBeanPropertySetter("*/Models/Model/ExternalID", "externalID");
    digester.addBeanPropertySetter("*/Models/Model/Manufacturer", "manufacturerExternalID");
    // Specifications
    digester.addCallMethod("*/Models/Model/Specifications/Specification", "setSpecification", 3);
    digester.addCallParam("*/Models/Model/Specifications/Specification", 0, "category");
    digester.addCallParam("*/Models/Model/Specifications/Specification", 1, "name");
    digester.addCallParam("*/Models/Model/Specifications/Specification", 2, "value");

    return (digester);
  }

  /**
   * @param filename
   * @return
   * @throws SetupException
   */
  protected List<ModelFetchItem> loadModelFetchItems(String filename) throws SetupException {
    List<ModelFetchItem> result = new ArrayList<ModelFetchItem>();
    try {
      Digester digester = this.createManufacturerDigester();
      digester.push(result);
      digester.parse(new FileInputStream(filename));
      return result;
    } catch (Exception e) {
      throw new SetupException(e);
    }
  }
}
