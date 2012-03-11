/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/GetCommandItemID.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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
public class GetCommandItemID implements java.io.Serializable {

  // Fields

  private long getCmdId;

  private long itemIndex;

  // Constructors

  /** default constructor */
  public GetCommandItemID() {
  }

  // Property accessors

  public long getGetCmdId() {
    return this.getCmdId;
  }

  public void setGetCmdId(long getCmdId) {
    this.getCmdId = getCmdId;
  }

  public long getItemIndex() {
    return this.itemIndex;
  }

  public void setItemIndex(long itemIndex) {
    this.itemIndex = itemIndex;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof GetCommandItemID))
      return false;
    GetCommandItemID castOther = (GetCommandItemID) other;

    return (this.getGetCmdId() == castOther.getGetCmdId()) && (this.getItemIndex() == castOther.getItemIndex());
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (int) this.getGetCmdId();
    result = 37 * result + (int) this.getItemIndex();
    return result;
  }

}