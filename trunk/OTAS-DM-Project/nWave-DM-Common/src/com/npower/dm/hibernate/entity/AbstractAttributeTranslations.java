/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractAttributeTranslations.java,v 1.4 2007/03/22 11:03:10 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2007/03/22 11:03:10 $
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

import com.npower.dm.core.ProfileNodeMapping;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/03/22 11:03:10 $
 */
public abstract class AbstractAttributeTranslations implements java.io.Serializable {

  // Fields

  private AttributeTranslationsID ID;

  private ProfileNodeMapping profileNodeMapping;

  // Constructors

  /** default constructor */
  public AbstractAttributeTranslations() {
  }

  /** full constructor */
  public AbstractAttributeTranslations(AttributeTranslationsID id, ProfileNodeMapping profileNodeMapping) {
    this.ID = id;
    this.profileNodeMapping = profileNodeMapping;
  }

  // Property accessors

  public AttributeTranslationsID getID() {
    return this.ID;
  }

  public void setID(AttributeTranslationsID id) {
    this.ID = id;
  }

  public ProfileNodeMapping getProfileNodeMapping() {
    return this.profileNodeMapping;
  }

  public void setProfileNodeMapping(ProfileNodeMapping profileNodeMapping) {
    this.profileNodeMapping = profileNodeMapping;
  }

}