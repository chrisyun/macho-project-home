/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/xmlinventory/ModelItem.java,v 1.2 2007/06/25 02:00:55 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/06/25 02:00:55 $
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.ClientProvTemplate;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/06/25 02:00:55 $
 */
public class ModelItem {

  private String ID = null;
  private String name = null;
  private String description = null;
  private String compatible = null;
  private ManufacturerItem manufacturer = null;
  private List<ClientProvTemplate> templates = new ArrayList<ClientProvTemplate>();
  
  /**
   * Default constructor
   */
  public ModelItem() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#getID()
   */
  public String getID() {
    return this.ID;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#getManufacturer()
   */
  public ManufacturerItem getManufacturer() {
    return this.manufacturer;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#getName()
   */
  public String getName() {
    return this.name;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#setID(java.lang.String)
   */
  public void setID(String id) {
    this.ID = id;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#setManufacturer(com.npower.cp.Manufacturer)
   */
  public void setManufacturer(ManufacturerItem manufacturer) {
    this.manufacturer  = manufacturer;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#addTemplate(com.npower.cp.OTATemplate)
   */
  public void addTemplate(OTATemplateItem template) {
    this.templates.add(template);
    template.setModel(this);
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#getTemplates()
   */
  public List<ClientProvTemplate> getTemplates() {
    return this.templates;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#setTemplates(java.util.List)
   */
  public void setTemplates(List<ClientProvTemplate> templates) {
    this.templates = templates;
  }

  /**
   * Return the compatiable models in CSV format(separated by ',')
   * @return
   */
  public String getCompatible() {
    return compatible;
  }

  /**
   * Set compatiable models in CSV format(separated by ',')
   * @param compatible
   */
  public void setCompatible(String compatible) {
    this.compatible = compatible;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Model#matched(java.lang.String)
   */
  public boolean matched(String modelID) {
    if (this.getID().equalsIgnoreCase(modelID)) {
       return true;
    }
    if (StringUtils.isEmpty(this.getCompatible())) {
       return false;
    }
    String[] compatiableModels = StringUtils.split(this.getCompatible(), ',');
    if (compatiableModels == null) {
       return false;
    }
    for (String comptiable: compatiableModels) {
        if (comptiable.trim().equalsIgnoreCase(modelID)) {
           return true;
        }
    }
    return false;
  }

}
