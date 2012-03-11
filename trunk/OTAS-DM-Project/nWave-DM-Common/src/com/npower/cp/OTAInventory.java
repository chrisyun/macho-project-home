/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/OTAInventory.java,v 1.9 2007/08/29 06:21:00 zhao Exp $
  * $Revision: 1.9 $
  * $Date: 2007/08/29 06:21:00 $
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
package com.npower.cp;

import java.util.List;
import java.util.Properties;

import com.npower.dm.core.ClientProvTemplate;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2007/08/29 06:21:00 $
 */
public abstract class OTAInventory {
  
  public static final String PROPERTY_FACTORY = "npower.cp.inventory.factory";

  /**
   * Constance: Default model ID
   */
  protected static final String DEFAULT_MODEL_ID = "Default";

  /**
   * Constance: default manufactuer ID
   */
  protected static String DEFAULT_MANUFACTURER_ID = "Default";
  
  /**
   * Constructor
   */
  protected OTAInventory() {
    super();
  }
  
  /**
   * Create a instance based on properties.
   * @param props
   * @return
   */
  public static OTAInventory newInstance(Properties props) throws OTAException {
    String factoryClassname = props.getProperty(PROPERTY_FACTORY);
    try {
        Class<?> clazz = Class.forName(factoryClassname);
        OTAInventory inventory = (OTAInventory)clazz.newInstance();
        inventory.initialize(props);
        return inventory;
    } catch (ClassNotFoundException e) {
      throw new OTAException("Instance factory failured!", e);
    } catch (InstantiationException e) {
      throw new OTAException("Instance factory failured!", e);
    } catch (IllegalAccessException e) {
      throw new OTAException("Instance factory failured!", e);
    }
  }
  
  /**
   * Initialize inventory
   * @param props
   * @throws OTAException
   */
  public abstract void initialize(Properties props) throws OTAException;
  
  /**
   * Return the OTA template by it's ID
   * @param templateID
   * @return
   * @throws OTAException
   */
  public abstract ClientProvTemplate getTemplate(String templateID) throws OTAException;
  
  /**
   * Find OTATemplates which belongs to this model.
   * @param manufacturerID
   * @param modelID
   * @return list of OTATemplate
   * @throws OTAException
   */
  public abstract List<ClientProvTemplate> getTemplates(String manufacturerID, String modelID) throws OTAException;
  
  /**
   * Return all of OTA Templates by category and model.
   * This method always return exactlly matched templates.
   * @param manufacturerID
   * @param modelID
   * @param category
   * @return
   * @throws OTAException
   */
  public abstract List<ClientProvTemplate> getTemplates(String manufacturerID, String modelID, String categoryName) throws OTAException;
  
  /**
   * Find compatiable OTATemplate
   * @param manufacturerID 
   *        Manufacturer ID
   * @param modelID
   *        Model ID
   * @param category
   *        Temaplte Category
   * @return
   * @throws OTAException
   */
  public abstract ClientProvTemplate findTemplate(String manufacturerID, String modelID, String categoryName) throws OTAException;
  
  /**
   * Return all of manufacturers
   * @return
   * @throws OTAException
   */
  //public abstract List<Manufacturer> getSupportedManufacturers() throws OTAException;
  
  /**
   * Find all of models by manufacturer
   * @param manufacturer
   * @return
   * @throws OTAException
   */
  //public abstract List<Model> getSupportedModels(Manufacturer manufacturer) throws OTAException;
  
}
