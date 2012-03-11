/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractProfileValueMap.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractProfileValueMap implements java.io.Serializable {

  // Fields

  private ProfileValueMapID ID;

  private ProfileAttributeValue profileAttribValue;

  private ProfileConfig profileConfig;

  private long valueIndex;

  // Constructors

  /** default constructor */
  public AbstractProfileValueMap() {
    super();
  }

  /** minimal constructor */
  public AbstractProfileValueMap(ProfileValueMapID id, ProfileAttributeValue nwDmProfileAttribValue,
      ProfileConfig nwDmProfileConfig) {
    this.ID = id;
    this.profileAttribValue = nwDmProfileAttribValue;
    this.profileConfig = nwDmProfileConfig;
  }

  /** full constructor */
  public AbstractProfileValueMap(ProfileValueMapID id, ProfileAttributeValue nwDmProfileAttribValue,
      ProfileConfig nwDmProfileConfig, long valueIndex) {
    this.ID = id;
    this.profileAttribValue = nwDmProfileAttribValue;
    this.profileConfig = nwDmProfileConfig;
    this.valueIndex = valueIndex;
  }

  // Property accessors

  public ProfileValueMapID getID() {
    return this.ID;
  }

  public void setID(ProfileValueMapID id) {
    this.ID = id;
  }

  public ProfileAttributeValue getProfileAttribValue() {
    return this.profileAttribValue;
  }

  public void setProfileAttribValue(ProfileAttributeValue nwDmProfileAttribValue) {
    this.profileAttribValue = nwDmProfileAttribValue;
  }

  public ProfileConfig getProfileConfig() {
    return this.profileConfig;
  }

  public void setProfileConfig(ProfileConfig nwDmProfileConfig) {
    this.profileConfig = nwDmProfileConfig;
  }

  public long getValueIndex() {
    return this.valueIndex;
  }

  public void setValueIndex(long valueIndex) {
    this.valueIndex = valueIndex;
  }

}