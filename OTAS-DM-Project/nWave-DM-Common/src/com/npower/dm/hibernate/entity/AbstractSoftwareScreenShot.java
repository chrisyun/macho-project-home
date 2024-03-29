/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractSoftwareScreenShot.java,v 1.2 2008/01/28 10:30:12 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/01/28 10:30:12 $
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

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareScreenShot;

/**
 * AbstractSoftwareScreenShot generated by MyEclipse Persistence Tools
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/01/28 10:30:12 $
 */

public abstract class AbstractSoftwareScreenShot implements java.io.Serializable, SoftwareScreenShot {

  // Fields

  private long           id;

  private DMBinary       binary;

  private Software software;

  private String           description;

  // Constructors

  /** default constructor */
  public AbstractSoftwareScreenShot() {
  }

  /** minimal constructor */
  public AbstractSoftwareScreenShot(long screenShootId) {
    this.id = screenShootId;
  }

  /** full constructor */
  public AbstractSoftwareScreenShot(long screenShootId, DMBinary binary, Software software,
      String description) {
    this.id = screenShootId;
    this.binary = binary;
    this.software = software;
    this.description = description;
  }

  // Property accessors

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.BBB#getId()
   */
  public long getId() {
    return this.id;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.BBB#setId(long)
   */
  public void setId(long screenShootId) {
    this.id = screenShootId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.BBB#getBinary()
   */
  public DMBinary getBinary() {
    return this.binary;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.BBB#setBinary(com.npower.dm.core.DMBinary)
   */
  public void setBinary(DMBinary binary) {
    this.binary = binary;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.BBB#getSoftware()
   */
  public Software getSoftware() {
    return this.software;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.BBB#setSoftware(com.npower.dm.core.Software)
   */
  public void setSoftware(Software software) {
    this.software = software;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.BBB#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.BBB#setDescription(java.sql.Clob)
   */
  public void setDescription(String description) {
    this.description = description;
  }

}