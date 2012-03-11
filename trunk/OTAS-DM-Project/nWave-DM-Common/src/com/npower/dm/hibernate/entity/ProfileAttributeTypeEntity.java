/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileAttributeTypeEntity.java,v 1.5 2006/06/06 03:07:07 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2006/06/06 03:07:07 $
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

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2006/06/06 03:07:07 $
 */
public class ProfileAttributeTypeEntity extends AbstractProfileAttributeType implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public ProfileAttributeTypeEntity() {
    super();
  }

  /** minimal constructor */
  public ProfileAttributeTypeEntity(String name, long changeVersion) {
    super(name, changeVersion);
  }

  /** full constructor */
  public ProfileAttributeTypeEntity(String name, String description, String lastUpdatedBy, Date lastUpdatedTime,
      long changeVersion, Set nwDmProfileAttributes, Set nwDmAttribTypeValueses) {
    super(name, description, lastUpdatedBy, lastUpdatedTime, changeVersion, nwDmProfileAttributes,
        nwDmAttribTypeValueses);
  }

  public void setAttribTypeValues(Set attribTypeValues) {
    //long id = this.getID();
    //if (id == 0) {
    //   Session session = HibernateSessionFactory.currentSession();
    //   session.saveOrUpdate(this);
    //}
    // Link the AttributeTypeValueEntity to owner (ProfileAttributeTypeEntity)
    if (!attribTypeValues.isEmpty()) {
       for (Iterator i = attribTypeValues.iterator(); i.hasNext();) {
           AttributeTypeValueEntity v = (AttributeTypeValueEntity) i.next();
           v.setProfileAttribType(this);
       }
    }
    super.setAttribTypeValues(attribTypeValues);
  }

  public String toString() {
    return new ToStringBuilder(this).append("name", this.getName()).append("changeVersion", this.getChangeVersion())
        .toString();

  }

}
