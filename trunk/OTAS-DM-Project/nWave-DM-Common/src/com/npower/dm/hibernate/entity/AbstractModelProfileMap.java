/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractModelProfileMap.java,v 1.4 2006/06/22 03:20:01 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2006/06/22 03:20:01 $
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileMapping;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2006/06/22 03:20:01 $
 */
public abstract class AbstractModelProfileMap implements java.io.Serializable {

  // Fields

  private ModelProfileMapID ID;

  private ProfileMapping profileMapping;

  private Model model;

  // Constructors

  /** default constructor */
  public AbstractModelProfileMap() {
    super();
  }

  /** full constructor */
  public AbstractModelProfileMap(ModelProfileMapID id, ProfileMapping nwDmProfileMapping, Model nwDmModel) {
    this.ID = id;
    this.profileMapping = nwDmProfileMapping;
    this.model = nwDmModel;
  }

  // Property accessors

  public ModelProfileMapID getID() {
    return this.ID;
  }

  public void setID(ModelProfileMapID id) {
    this.ID = id;
  }

  public ProfileMapping getProfileMapping() {
    return this.profileMapping;
  }

  public void setProfileMapping(ProfileMapping nwDmProfileMapping) {
    this.profileMapping = nwDmProfileMapping;
  }

  public Model getModel() {
    return this.model;
  }

  public void setModel(Model nwDmModel) {
    this.model = nwDmModel;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof AbstractModelProfileMap))
      return false;
    AbstractModelProfileMap castOther = (AbstractModelProfileMap) other;
    return new EqualsBuilder().append(ID, castOther.ID).isEquals();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(ID).toHashCode();
  }

}