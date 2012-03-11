/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/xmlinventory/ManufacturerItem.java,v 1.1 2007/05/24 08:48:48 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/05/24 08:48:48 $
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


/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/05/24 08:48:48 $
 */
public class ManufacturerItem {
  
  private String ID = null;
  private String name = null;
  private String description = null;
  private List<ModelItem> models = new ArrayList<ModelItem>();

  /**
   * Default constructor
   */
  public ManufacturerItem() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Manufacturer#addModel(com.npower.cp.Model)
   */
  public void addModel(ModelItem model) {
    this.models.add(model);
    model.setManufacturer(this);
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Manufacturer#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Manufacturer#getID()
   */
  public String getID() {
    return this.ID;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Manufacturer#getModels()
   */
  public List<ModelItem> getModels() {
    return this.models;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Manufacturer#getName()
   */
  public String getName() {
    return this.name;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Manufacturer#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Manufacturer#setID(java.lang.String)
   */
  public void setID(String id) {
    this.ID = id;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.Manufacturer#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

}
