/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDDFNodeMIMEType.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2006/04/25 16:31:09 $
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

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFNodeMIMEType;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractDDFNodeMIMEType implements java.io.Serializable, DDFNodeMIMEType {

  // Fields

  private DDFNodeMIMETypeID ID;

  private DDFNode ddfNode;

  // Constructors

  /** default constructor */
  public AbstractDDFNodeMIMEType() {
    super();
  }

  /** full constructor */
  public AbstractDDFNodeMIMEType(DDFNodeMIMETypeID id, DDFNode nwDmDdfNode) {
    this.ID = id;
    this.ddfNode = nwDmDdfNode;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNodeMIMEType#getID()
   */
  public DDFNodeMIMETypeID getID() {
    return this.ID;
  }

  public void setID(DDFNodeMIMETypeID id) {
    this.ID = id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNodeMIMEType#getDdfNode()
   */
  public DDFNode getDdfNode() {
    return this.ddfNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNodeMIMEType#setDdfNode(com.npower.dm.hibernate.DDFNode)
   */
  public void setDdfNode(DDFNode nwDmDdfNode) {
    this.ddfNode = nwDmDdfNode;
  }

}