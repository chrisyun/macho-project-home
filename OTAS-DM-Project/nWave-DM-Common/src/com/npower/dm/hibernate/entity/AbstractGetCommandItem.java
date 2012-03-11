/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractGetCommandItem.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractGetCommandItem implements java.io.Serializable {

  // Fields

  private GetCommandItemID id;

  private DMCommand nwDmDmCmd;

  private String elt;

  // Constructors

  /** default constructor */
  public AbstractGetCommandItem() {
  }

  /** minimal constructor */
  public AbstractGetCommandItem(DMCommand nwDmDmCmd) {
    this.nwDmDmCmd = nwDmDmCmd;
  }

  /** full constructor */
  public AbstractGetCommandItem(DMCommand nwDmDmCmd, String elt) {
    this.nwDmDmCmd = nwDmDmCmd;
    this.elt = elt;
  }

  // Property accessors

  public GetCommandItemID getId() {
    return this.id;
  }

  public void setId(GetCommandItemID id) {
    this.id = id;
  }

  public DMCommand getNwDmDmCmd() {
    return this.nwDmDmCmd;
  }

  public void setNwDmDmCmd(DMCommand nwDmDmCmd) {
    this.nwDmDmCmd = nwDmDmCmd;
  }

  public String getElt() {
    return this.elt;
  }

  public void setElt(String elt) {
    this.elt = elt;
  }

}