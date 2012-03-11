/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AttributeTranslationsID.java,v 1.3 2007/03/14 11:11:51 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2007/03/14 11:11:51 $
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
 * @version $Revision: 1.3 $ $Date: 2007/03/14 11:11:51 $
 */
public class AttributeTranslationsID implements java.io.Serializable {

  // Fields

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private long nodeMappingId;

  private String originalValue;

  private String value;

  // Constructors

  /** default constructor */
  public AttributeTranslationsID() {
  }

  // Property accessors

  public long getNodeMappingId() {
    return this.nodeMappingId;
  }

  public void setNodeMappingId(long nodeMappingId) {
    this.nodeMappingId = nodeMappingId;
  }

  public String getOriginalValue() {
    return this.originalValue;
  }

  public void setOriginalValue(String value) {
    this.originalValue = value;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String translation) {
    this.value = translation;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof AttributeTranslationsID))
      return false;
    AttributeTranslationsID castOther = (AttributeTranslationsID) other;

    return (this.getNodeMappingId() == castOther.getNodeMappingId())
        && ((this.getOriginalValue() == castOther.getOriginalValue()) || (this.getOriginalValue() != null && castOther.getOriginalValue() != null && this
            .getOriginalValue().equals(castOther.getOriginalValue())))
        && ((this.getValue() == castOther.getValue()) || (this.getValue() != null
            && castOther.getValue() != null && this.getValue().equals(castOther.getValue())));
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (int) this.getNodeMappingId();
    result = 37 * result + (getOriginalValue() == null ? 0 : this.getOriginalValue().hashCode());
    result = 37 * result + (getValue() == null ? 0 : this.getValue().hashCode());
    return result;
  }

}