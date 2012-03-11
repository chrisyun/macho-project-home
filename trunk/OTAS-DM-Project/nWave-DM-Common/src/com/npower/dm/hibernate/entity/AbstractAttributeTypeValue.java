/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractAttributeTypeValue.java,v 1.6 2007/08/29 06:21:00 zhao Exp $
 * $Revision: 1.6 $
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

import com.npower.dm.core.AttributeTypeValue;
import com.npower.dm.core.ProfileAttributeType;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2007/08/29 06:21:00 $
 */
public abstract class AbstractAttributeTypeValue implements java.io.Serializable, AttributeTypeValue, Comparable<AttributeTypeValue> {

  // Fields

  private long ID;
  
  private String value;

  private ProfileAttributeType profileAttribType;

  // Constructors

  /** default constructor */
  public AbstractAttributeTypeValue() {
    super();
  }

  /** full constructor */
  public AbstractAttributeTypeValue(ProfileAttributeType profileAttribType, String value) {
    this.value = value;
    this.profileAttribType = profileAttribType;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.AttributeTypeValue#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long attributeTypeId) {
    this.ID = attributeTypeId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.AttributeTypeValue#getValue()
   */
  public String getValue() {
    return this.value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.AttributeTypeValue#setValue(java.lang.String)
   */
  public void setValue(String value) {
    this.value = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.AttributeTypeValue#getProfileAttribType()
   */
  public ProfileAttributeType getProfileAttribType() {
    return this.profileAttribType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.AttributeTypeValue#setProfileAttribType(com.npower.dm.hibernate.ProfileAttributeTypeEntity)
   */
  public void setProfileAttribType(ProfileAttributeType profileAttribType) {
    this.profileAttribType =profileAttribType;
  }

}