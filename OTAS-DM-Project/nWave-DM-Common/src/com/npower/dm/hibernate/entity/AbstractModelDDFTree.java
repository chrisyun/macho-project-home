/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractModelDDFTree.java,v 1.4 2006/06/04 14:31:57 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2006/06/04 14:31:57 $
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
package com.npower.dm.hibernate.entity;

import com.npower.dm.core.DDFTree;
import com.npower.dm.core.Model;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2006/06/04 14:31:57 $
 */
public abstract class AbstractModelDDFTree implements java.io.Serializable, Comparable {

  // Fields

  private ModelDDFTreeID ID;

  private DDFTree ddfTree;

  private Model model;

  // Constructors

  /** default constructor */
  public AbstractModelDDFTree() {
    super();
  }

  /** full constructor */
  public AbstractModelDDFTree(ModelDDFTreeID id, DDFTree nwDmDdfTree, Model nwDmModel) {
    this.ID = id;
    this.ddfTree = nwDmDdfTree;
    this.model = nwDmModel;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ModelDDFTree#getID()
   */
  public ModelDDFTreeID getID() {
    return this.ID;
  }

  public void setID(ModelDDFTreeID id) {
    this.ID = id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ModelDDFTree#getDdfTree()
   */
  public DDFTree getDdfTree() {
    return this.ddfTree;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ModelDDFTree#setDdfTree(com.npower.dm.hibernate.DDFTree)
   */
  public void setDdfTree(DDFTree nwDmDdfTree) {
    this.ddfTree = nwDmDdfTree;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ModelDDFTree#getModel()
   */
  public Model getModel() {
    return this.model;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.ModelDDFTree#setModel(com.npower.dm.hibernate.Model)
   */
  public void setModel(Model nwDmModel) {
    this.model = nwDmModel;
  }

}