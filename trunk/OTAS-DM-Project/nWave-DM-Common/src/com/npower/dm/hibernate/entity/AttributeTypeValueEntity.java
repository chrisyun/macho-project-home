/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AttributeTypeValueEntity.java,v 1.5 2007/08/29 06:21:00 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2007/08/29 06:21:00 $
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

import org.apache.commons.lang.builder.ToStringBuilder;

import com.npower.dm.core.AttributeTypeValue;
import com.npower.dm.core.ProfileAttributeType;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/08/29 06:21:00 $
 */
public class AttributeTypeValueEntity extends AbstractAttributeTypeValue implements java.io.Serializable {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public AttributeTypeValueEntity() {
    super();
  }

  /** full constructor */
  public AttributeTypeValueEntity(ProfileAttributeType profileAttribType, String value) {
    super(profileAttribType, value);
  }

  public String toString() {
    return new ToStringBuilder(this).append("ID", this.getID()).append("value", this.getValue()).toString();

  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(T)
   */
  public int compareTo(AttributeTypeValue o) {
    if (o != null && o instanceof AttributeTypeValue) {
       return (int)(this.getID() - ((AttributeTypeValue)o).getID());
    } else {
      return 1;
    }
  }

}
