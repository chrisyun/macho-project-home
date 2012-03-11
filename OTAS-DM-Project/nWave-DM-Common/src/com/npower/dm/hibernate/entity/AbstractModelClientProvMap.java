/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractModelClientProvMap.java,v 1.3 2007/05/29 07:30:26 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/05/29 07:30:26 $
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

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.Model;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/05/29 07:30:26 $
 */
public abstract class AbstractModelClientProvMap implements java.io.Serializable, ModelClientProvMap {

  // Fields

  private ModelClientProvMapId id;

  private ClientProvTemplate   clientProvTemplateEntity;

  private Model                model;

  // Constructors

  /** default constructor */
  public AbstractModelClientProvMap() {
  }

  /** minimal constructor */
  public AbstractModelClientProvMap(ModelClientProvMapId id) {
    this.id = id;
  }

  /** full constructor */
  public AbstractModelClientProvMap(ModelClientProvMapId id, ClientProvTemplate clientProvTemplateEntity, Model model) {
    this.id = id;
    this.clientProvTemplateEntity = clientProvTemplateEntity;
    this.model = model;
  }

  // Property accessors

  public ModelClientProvMapId getId() {
    return this.id;
  }

  public void setId(ModelClientProvMapId id) {
    this.id = id;
  }

  public ClientProvTemplate getClientProvTemplate() {
    return this.clientProvTemplateEntity;
  }

  public void setClientProvTemplate(ClientProvTemplate clientProvTemplateEntity) {
    this.clientProvTemplateEntity = clientProvTemplateEntity;
  }

  public Model getModel() {
    return this.model;
  }

  public void setModel(Model model) {
    this.model = model;
  }

}