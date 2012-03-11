/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractUpdate4Carrier.java,v 1.3 2006/04/25 16:31:09 zhao Exp $
 * $Revision: 1.3 $
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

import com.npower.dm.core.Carrier;
import com.npower.dm.core.ImageUpdateStatus;
import com.npower.dm.core.Update;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractUpdate4Carrier implements java.io.Serializable {

  // Fields

  private long ID;

  private Update update;

  private ImageUpdateStatus status;

  private Carrier carrier;

  private long workflowEntryId;

  private long changeVersion;

  // Constructors

  /** default constructor */
  public AbstractUpdate4Carrier() {
  }

  /** minimal constructor */
  public AbstractUpdate4Carrier(Carrier carrier, ImageUpdateStatus status, long workflowEntryId) {
    this.status = status;
    this.carrier = carrier;
    this.workflowEntryId = workflowEntryId;
  }

  /** full constructor */
  public AbstractUpdate4Carrier(Carrier carrier, Update update, ImageUpdateStatus status) {
    this.update = update;
    this.status = status;
    this.carrier = carrier;
  }

  // Property accessors

  public long getID() {
    return this.ID;
  }

  public void setID(long updateCarriersId) {
    this.ID = updateCarriersId;
  }

  public Update getUpdate() {
    return this.update;
  }

  public void setUpdate(Update nwDmUpdate) {
    this.update = nwDmUpdate;
  }

  public ImageUpdateStatus getStatus() {
    return this.status;
  }

  public void setStatus(ImageUpdateStatus nwDmImageUpdateStatus) {
    this.status = nwDmImageUpdateStatus;
  }

  public Carrier getCarrier() {
    return this.carrier;
  }

  public void setCarrier(Carrier nwDmCarrier) {
    this.carrier = nwDmCarrier;
  }

  public long getWorkflowEntryId() {
    return this.workflowEntryId;
  }

  public void setWorkflowEntryId(long workflowEntryId) {
    this.workflowEntryId = workflowEntryId;
  }

  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

}