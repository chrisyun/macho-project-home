/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/xmlinventory/OTAInventoryImpl.java,v 1.12 2007/06/04 09:11:01 zhao Exp $
  * $Revision: 1.12 $
  * $Date: 2007/06/04 09:11:01 $
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.npower.cp.OTAException;
import com.npower.cp.OTAInventory;
import com.npower.dm.core.ClientProvTemplate;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.12 $ $Date: 2007/06/04 09:11:01 $
 */
public class OTAInventoryImpl extends OTAInventory {
  
  private static Log log = LogFactory.getLog(OTAInventoryImpl.class);
  /**
   * Constance: Default template filename
   */
  public static final String DEFAULT_TEMPLATE_FILENAME = "otatemplate.xml";

  /**
   * Constance: property name of template directory
   */
  public static final String CP_TEMPLATE_RESOURCE_DIR = "npower.cp.template.dir";

  /**
   * Constance: property name of template filename
   */
  public static final String CP_TEMPLATE_RESOURCE_FILENAME = "npower.cp.template.filename";

  /**
   * Singleton pattern: database instance.
   */
  private static List<ManufacturerItem> manufacturers = new ArrayList<ManufacturerItem>();
  
  private Properties properties = new Properties();

  /**
   * Default constructor.
   */
  public OTAInventoryImpl() throws OTAException {
    super();
  }
  
  /**
   * Must defined the following properties:
   * npower.cp.template.dir       Template Resource directory
   * npower.cp.template.filename  
   */
  public OTAInventoryImpl(Properties props) throws OTAException {
    super();
    this.properties  = props;
  }
  
  // Private methods -------------------------------------------------------------------------------------------
  /**
   * Load all of templates from inventory database.
   * @throws OTAException
   */
  private void load() throws OTAException {
    String resourcePath = getResourceBaseDir();
    String resourceFilename = this.properties.getProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_FILENAME, OTAInventoryImpl.DEFAULT_TEMPLATE_FILENAME);
    
    synchronized (manufacturers) {
      try {
          File resourceFile = new File(resourcePath, resourceFilename);
          if (!resourceFile.exists()) {
             throw new OTAException("Could not open the template file: " + resourceFilename);
          }
          // Clear up
          Digester digester = this.createDigester();
          manufacturers.clear();
          digester.push(this);
          digester.parse(resourceFile);
      } catch (Exception e) {
        throw new OTAException(e);
      }
    }
  }

  /**
   * Return base directory of XML inventory.
   * @return
   * @throws OTAException
   */
  private String getResourceBaseDir() throws OTAException {
    String resourcePath = this.properties.getProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR);
    if (StringUtils.isNotEmpty(resourcePath)) {
       File resourceDir = new File(resourcePath);
       if (!resourceDir.isAbsolute()) {
          resourceDir = new File(System.getProperty("otas.dm.home"), resourcePath);
       }
       resourcePath = resourceDir.getAbsolutePath();
    }
    
    if (StringUtils.isEmpty(resourcePath)) {
       throw new OTAException("CP Template Path must be specified in property: " + OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR);
    }
    return resourcePath;
  }
  
  /**
   * Create Digester to parsing Template index file.
   * @return
   * @throws SAXException 
   * @throws ParserConfigurationException 
   */
  private Digester createDigester() throws ParserConfigurationException, SAXException {
    SAXParserFactory spf = SAXParserFactory.newInstance();
    //spf.setNamespaceAware(true);
    //spf.setXIncludeAware(true);
    
    // Initialize the digester
    Digester currentDigester = new Digester(spf.newSAXParser());
    currentDigester.setValidating(false);

    // Parsing template
    currentDigester.addSetProperties("ota-templates/import", "filename", "include");

    // Parsing manufacturer
    currentDigester.addObjectCreate("ota-templates/manufacturer", ManufacturerItem.class);
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/id", "ID");
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/name", "name");
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/description", "description");
    currentDigester.addSetNext("ota-templates/manufacturer", "add");

    // Parsing model
    currentDigester.addObjectCreate("ota-templates/manufacturer/model", ModelItem.class);
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/model/id", "ID");
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/model/name", "name");
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/model/description", "description");
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/model/compatible", "compatible");
    currentDigester.addSetNext("ota-templates/manufacturer/model", "addModel");

    // Parsing template
    currentDigester.addObjectCreate("ota-templates/manufacturer/model/template", OTATemplateItem.class);
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/model/template/id", "externalID");
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/model/template/name", "name");
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/model/template/description", "description");
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/model/template/content", "contentString");
    currentDigester.addBeanPropertySetter("ota-templates/manufacturer/model/template/category", "categoryByName");
    currentDigester.addSetProperties("ota-templates/manufacturer/model/template/content", "filename", "filename");
    currentDigester.addSetNext("ota-templates/manufacturer/model/template", "addTemplate");
    
    return currentDigester;
  }
  
  /**
   * Add a manufacturer
   * @param man
   */
  public void add(ManufacturerItem man) {
    manufacturers.add(man);
  }
  
  /**
   * Set include file
   * @param filename 
   * @throws OTAException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  public void setInclude(String filename) throws OTAException, ParserConfigurationException, SAXException, IOException {
    File file = new File(this.getResourceBaseDir(), filename);
    if (!file.exists()) {
       throw new OTAException("Could not found file: " + file.getAbsolutePath());
    }
    Digester digester = this.createDigester();
    digester.push(this);
    digester.parse(file);
    
  }
  
  // Implements abstract methods -------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#initialize(java.util.Properties)
   */
  public void initialize(Properties props) throws OTAException {
    log.info("Initializing OTASInventory: " + this.getClass().getName());
    this.properties = props;
    // Set CP template resource directory into System properties.
    System.setProperty(OTAInventoryImpl.CP_TEMPLATE_RESOURCE_DIR, 
                       this.getResourceBaseDir());
    log.info("Loading CP Templates from : " + this.getResourceBaseDir());
    this.load();
  }
  
  /**
   * Return all of OTATemplate defined.
   * @return
   * @throws OTAException
   */
  protected List<ClientProvTemplate> findAll() throws OTAException {
    List<ClientProvTemplate> result = new ArrayList<ClientProvTemplate>();
    for (ManufacturerItem man: manufacturers) {
        for (ModelItem model: man.getModels()) {
            for (ClientProvTemplate template: model.getTemplates()) {
                result.add(template);
            }
        }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#getSupportedManufacturers()
   */
  //@Override
  public List<ManufacturerItem> getSupportedManufacturers() throws OTAException {
    return manufacturers;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#getSupportedModels(com.npower.cp.Manufacturer)
   */
  //@Override
  public List<ModelItem> getSupportedModels(ManufacturerItem manufacturer) throws OTAException {
    return manufacturer.getModels();
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#getTemplate(java.lang.String)
   */
  @Override
  public ClientProvTemplate getTemplate(String templateID) throws OTAException {
    if (templateID == null) {
       return null;
    }
    for (ManufacturerItem man: manufacturers) {
        for (ModelItem model: man.getModels()) {
            for (ClientProvTemplate template: model.getTemplates()) {
                if (templateID.equals(template.getID())) {
                   return template;
                }
            }
        }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#getTemplates(java.lang.String, java.lang.String)
   */
  @Override
  public List<ClientProvTemplate> getTemplates(String manufacturerID, String modelID) throws OTAException {
    if (manufacturerID == null || modelID == null) {
       return new ArrayList<ClientProvTemplate>();
    }
    for (ManufacturerItem man: manufacturers) {
        if (!manufacturerID.equalsIgnoreCase(man.getID())) {
           continue;
        }
        for (ModelItem model: man.getModels()) {
            if (model.matched(modelID)) {
               return model.getTemplates();
            }
        }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#getTemplates(java.lang.String, java.lang.String, com.npower.cp.OTAProfileCategory)
   */
  @Override
  public List<ClientProvTemplate> getTemplates(String manufacturerID, String modelID, String categoryName) throws OTAException {
    List<ClientProvTemplate> result = new ArrayList<ClientProvTemplate>();
    if (manufacturerID == null || modelID == null || categoryName == null) {
        return result;
    }
    List<ClientProvTemplate> templates = this.getTemplates(manufacturerID, modelID);
    if (templates == null) {
       return result;
    }
    for (ClientProvTemplate template: templates) {
        if (template.getProfileCategory().getName().equalsIgnoreCase(categoryName)) {
           result.add(template);
        }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#findTemplate(java.lang.String, java.lang.String, com.npower.cp.OTAProfileCategory)
   */
  @Override
  public ClientProvTemplate findTemplate(String manufacturerID, String modelID, String categoryName) throws OTAException {
    
    log.info("Finding CP Template for Manufacturer: " + manufacturerID + ", modelID: " + modelID + ", category: " + categoryName);
    List<ClientProvTemplate> templates = this.getTemplates(manufacturerID, modelID, categoryName);
    if (templates != null && templates.size() > 0) {
       ClientProvTemplate template = templates.get(0);
       log.info("CP Template Found, exactlly matched! template ID: " + template.getExternalID());
       return template;
    }
    
    // Find Default Model from manufacturer
    templates = this.getTemplates(manufacturerID, DEFAULT_MODEL_ID, categoryName);
    if (templates != null && templates.size() > 0) {
       ClientProvTemplate template = templates.get(0);
       log.info("CP Template Found, manufacturer default matched! template ID: " + template.getExternalID());
       return template;
    }
    
    // Find Default Model from default manufacturer
    templates = this.getTemplates(DEFAULT_MANUFACTURER_ID, DEFAULT_MODEL_ID, categoryName);
    if (templates != null && templates.size() > 0) {
       ClientProvTemplate template = templates.get(0);
       log.info("CP Template Found, global default matched! template ID: " + template.getExternalID());
       return template;
    }
    
    return null;
  }

}
