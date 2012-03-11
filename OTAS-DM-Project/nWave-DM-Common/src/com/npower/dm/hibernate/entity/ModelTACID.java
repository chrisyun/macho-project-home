/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ModelTACID.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public class ModelTACID implements java.io.Serializable {

  // Fields

  private long modelId;

  private String TAC;

  // Constructors

  /** default constructor */
  public ModelTACID() {
  }

  // Property accessors

  public long getModelId() {
    return this.modelId;
  }

  public void setModelId(long modelId) {
    this.modelId = modelId;
  }

  public String getTAC() {
    return this.TAC;
  }

  public void setTAC(String tac) {
    this.TAC = tac;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof ModelTACID))
      return false;
    ModelTACID castOther = (ModelTACID) other;

    return (this.getModelId() == castOther.getModelId())
        && ((this.getTAC() == castOther.getTAC()) || (this.getTAC() != null && castOther.getTAC() != null && this
            .getTAC().equals(castOther.getTAC())));
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (int) this.getModelId();
    result = 37 * result + (getTAC() == null ? 0 : this.getTAC().hashCode());
    return result;
  }

}