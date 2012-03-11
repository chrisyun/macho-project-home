/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractPathElement.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

import com.npower.dm.core.Update;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractPathElement implements java.io.Serializable {

  // Fields

  private long pathElementId;

  private Update nwDmUpdate;

  private Element4Provision nwDmPrElement;

  private long sequenceNumber;

  private long changeVersion;

  // Constructors

  /** default constructor */
  public AbstractPathElement() {
  }

  /** minimal constructor */
  public AbstractPathElement(long sequenceNumber, long changeVersion) {
    this.sequenceNumber = sequenceNumber;
    this.changeVersion = changeVersion;
  }

  /** full constructor */
  public AbstractPathElement(Update nwDmUpdate, Element4Provision nwDmPrElement, long sequenceNumber, long changeVersion) {
    this.nwDmUpdate = nwDmUpdate;
    this.nwDmPrElement = nwDmPrElement;
    this.sequenceNumber = sequenceNumber;
    this.changeVersion = changeVersion;
  }

  // Property accessors

  public long getPathElementId() {
    return this.pathElementId;
  }

  public void setPathElementId(long pathElementId) {
    this.pathElementId = pathElementId;
  }

  public Update getNwDmUpdate() {
    return this.nwDmUpdate;
  }

  public void setNwDmUpdate(Update nwDmUpdate) {
    this.nwDmUpdate = nwDmUpdate;
  }

  public Element4Provision getNwDmPrElement() {
    return this.nwDmPrElement;
  }

  public void setNwDmPrElement(Element4Provision nwDmPrElement) {
    this.nwDmPrElement = nwDmPrElement;
  }

  public long getSequenceNumber() {
    return this.sequenceNumber;
  }

  public void setSequenceNumber(long sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
  }

  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

}