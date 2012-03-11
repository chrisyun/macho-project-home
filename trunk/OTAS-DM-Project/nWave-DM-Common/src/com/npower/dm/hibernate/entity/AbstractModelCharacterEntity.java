/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractModelCharacterEntity.java,v 1.2 2008/06/05 10:34:41 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2008/06/05 10:34:41 $
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
package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Model;
import com.npower.dm.core.ModelCharacter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/05 10:34:41 $
 */
public abstract class AbstractModelCharacterEntity implements java.io.Serializable, ModelCharacter {
  
  private long ID = 0;
  
  private String category = null;
  
  private String name = null;
  
  private String value = null;
  
  private Model model = null;
  
  /**
   * 
   */
  public AbstractModelCharacterEntity() {
    super();
  }

  /**
   * @param category
   * @param name
   * @param value
   * @param modelSpec
   */
  public AbstractModelCharacterEntity(String category, String name, String value, Model model) {
    super();
    this.category = category;
    this.name = name;
    this.value = value;
    this.model = model;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelCharacter#getID()
   */
  public long getID() {
    return ID;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelCharacter#setID(long)
   */
  public void setID(long id) {
    ID = id;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelCharacter#getCategory()
   */
  public String getCategory() {
    return category;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelCharacter#setCategory(java.lang.String)
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelCharacter#getName()
   */
  public String getName() {
    return name;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelCharacter#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelCharacter#getValue()
   */
  public String getValue() {
    return value;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelCharacter#setValue(java.lang.String)
   */
  public void setValue(String value) {
    this.value = value;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelCharacter#getModel()
   */
  public Model getModel() {
    return model;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelCharacter#setModelSpec(com.npower.dm.core.ModelSpec)
   */
  public void setModel(Model model) {
    this.model = model;
  }

}
