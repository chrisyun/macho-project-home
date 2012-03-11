/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDDFTree.java,v 1.4 2006/06/04 14:31:57 zhao Exp $
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

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.DDFTree;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2006/06/04 14:31:57 $
 */
public abstract class AbstractDDFTree implements java.io.Serializable, Comparable, DDFTree {

  // Fields

  private long ID;

  private String specVersion;

  private String manufacturer;

  private String model;

  private Set modelDDFTrees = new HashSet(0);

  private Set ddfNodes = new TreeSet();

  // Constructors

  /** default constructor */
  public AbstractDDFTree() {
    super();
  }

  /** full constructor */
  public AbstractDDFTree(String specVersion, String manufacturer, String model, Set nwDmModelDdfTrees, Set nwDmDdfNodes) {
    this.specVersion = specVersion;
    this.manufacturer = manufacturer;
    this.model = model;
    this.modelDDFTrees = nwDmModelDdfTrees;
    this.ddfNodes = nwDmDdfNodes;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFTree#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long ddfTreeId) {
    this.ID = ddfTreeId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFTree#getSpecVersion()
   */
  public String getSpecVersion() {
    return this.specVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFTree#setSpecVersion(java.lang.String)
   */
  public void setSpecVersion(String specVersion) {
    this.specVersion = specVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFTree#getManufacturer()
   */
  public String getManufacturer() {
    return this.manufacturer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFTree#setManufacturer(java.lang.String)
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFTree#getModel()
   */
  public String getModel() {
    return this.model;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFTree#setModel(java.lang.String)
   */
  public void setModel(String model) {
    this.model = model;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFTree#getModelDDFTrees()
   */
  public Set getModelDDFTrees() {
    return this.modelDDFTrees;
  }

  public void setModelDDFTrees(Set modelDdfTrees) {
    this.modelDDFTrees = modelDdfTrees;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFTree#getDdfNodes()
   */
  public Set getDdfNodes() {
    return this.ddfNodes;
  }

  public void setDdfNodes(Set ddfNodes) {
    this.ddfNodes = ddfNodes;
  }

}