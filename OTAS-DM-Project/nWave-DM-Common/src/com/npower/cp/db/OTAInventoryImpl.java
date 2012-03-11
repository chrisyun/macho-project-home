/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/db/OTAInventoryImpl.java,v 1.5 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2008/06/16 10:11:15 $
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
package com.npower.cp.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.cp.OTAException;
import com.npower.cp.OTAInventory;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.management.ClientProvTemplateBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/06/16 10:11:15 $
 */
public class OTAInventoryImpl extends OTAInventory {
  private static Log log = LogFactory.getLog(OTAInventoryImpl.class);

  /**
   * Default constructor
   */
  public OTAInventoryImpl() {
    super();
  }

  /**
   * @param factory
   * @param manufacturerID
   * @param modelID
   * @return
   * @throws OTAException
   * @throws DMException
   */
  private List<ClientProvTemplate> getTemplates(ManagementBeanFactory factory, String manufacturerID, String modelID) throws OTAException, DMException {
    if (manufacturerID == null) {
       throw new OTAException("Manufacturer external ID is null");
    }
    if (modelID == null) {
       throw new OTAException("Model external ID is null");
    }
    ModelBean bean = factory.createModelBean();
    
    List<ClientProvTemplate> result = new ArrayList<ClientProvTemplate>();

    // Todo Improvement performance
    List<Manufacturer> manufacturers = bean.getAllManufacturers();
    for (Manufacturer manufacturer: manufacturers) {
        if (manufacturer.getExternalId().equalsIgnoreCase(manufacturerID)
            || manufacturer.getName().equalsIgnoreCase(manufacturerID)) {
           for (Model model: manufacturer.getModels()) {
               if (model.getManufacturerModelId().equalsIgnoreCase(modelID) 
                   || model.getName().equalsIgnoreCase(modelID)) {
                 if (model.getClientProvTemplates() != null) {
                    result.addAll(model.getClientProvTemplates());
                 }
                 break;
               }
           }
        }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#initialize(java.util.Properties)
   */
  @Override
  public void initialize(Properties props) throws OTAException {
    log.info("Initializing OTASInventory: " + this.getClass().getName());
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#findTemplate(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public ClientProvTemplate findTemplate(String manufacturerID, String modelID, String categoryName)
      throws OTAException {
    log.info("Finding CP Template for Manufacturer: " + manufacturerID + ", modelID: " + modelID + ", category: " + categoryName);
    List<ClientProvTemplate> templates = this.getTemplates(manufacturerID, modelID, categoryName);
    if (templates != null && templates.size() > 0) {
       log.info("Exactly matched CP Template ID: " + templates.get(0).getID() + " of category: " + categoryName + " for Model: " + manufacturerID + " " + modelID);
       return templates.get(0);
    }
    
    // Find Default Model from manufacturer
    templates = this.getTemplates(manufacturerID, DEFAULT_MODEL_ID, categoryName);
    if (templates != null && templates.size() > 0) {
       log.info("Manufacturer default matched CP Template ID: " + templates.get(0).getID() + " of category: " + categoryName + " for Model: " + manufacturerID + " " + modelID);
       return templates.get(0);
    }
    
    // Find Default Model from default manufacturer
    templates = this.getTemplates(DEFAULT_MANUFACTURER_ID, DEFAULT_MODEL_ID, categoryName);
    if (templates != null && templates.size() > 0) {
       log.info("Global default matched CP Template ID: " + templates.get(0).getID() + " of category: " + categoryName + " for Model: " + manufacturerID + " " + modelID);
       return templates.get(0);
    }
    
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#getTemplate(java.lang.String)
   */
  @Override
  public ClientProvTemplate getTemplate(String templateID) throws OTAException {
    if (templateID == null) {
       throw new NullPointerException("template ID is null");
    }
    Long id = null;
    try {
        id = Long.parseLong(templateID);
    } catch (NumberFormatException e) {
      throw new OTAException("Template id is not in number format", e);
    }
    
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ClientProvTemplateBean bean = factory.createClientProvTemplateBean();
        
        ClientProvTemplate template = bean.getTemplate(id.toString());
        return template;
    } catch (DMException ex) {
      throw new OTAException("Failure in loading OTAS Template ID: " + templateID, ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#getTemplates(java.lang.String, java.lang.String)
   */
  @Override
  public List<ClientProvTemplate> getTemplates(String manufacturerID, String modelID) throws OTAException {
    if (manufacturerID == null) {
       throw new OTAException("Manufacturer external ID is null");
    }
    if (modelID == null) {
       throw new OTAException("Model external ID is null");
    }
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        return this.getTemplates(factory, manufacturerID, modelID);
    } catch (DMException ex) {
      throw new OTAException("Failure in retrieve OTAS Templates for manufacturer external ID: " + manufacturerID + " and model external ID: " + modelID, ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAInventory#getTemplates(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public List<ClientProvTemplate> getTemplates(String manufacturerID, String modelID, String categoryName)
      throws OTAException {
    List<ClientProvTemplate> result = new ArrayList<ClientProvTemplate>();
    if (manufacturerID == null || modelID == null || categoryName == null) {
        return result;
    }
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        List<ClientProvTemplate> templates = this.getTemplates(factory, manufacturerID, modelID);
        if (templates == null) {
           return result;
        }
        for (ClientProvTemplate template: templates) {
            if (template.getProfileCategory().getName().equalsIgnoreCase(categoryName)) {
               result.add(template);
            }
        }
        return result;
    } catch (DMException ex) {
      throw new OTAException("Failure in retrieve OTAS Templates for manufacturer external ID: " + manufacturerID + " and model external ID: " + modelID, ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
