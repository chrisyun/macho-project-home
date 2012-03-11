/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ManufacturerEntity.java,v 1.4 2007/02/10 03:47:51 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2007/02/10 03:47:51 $
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

import com.npower.dm.core.Manufacturer;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/02/10 03:47:51 $
 */
public class ManufacturerEntity extends AbstractManufacturer {

  // Constructors

  /** default constructor */
  public ManufacturerEntity() {
    super();
  }

  /** minimal constructor */
  public ManufacturerEntity(String manufacturerExternalId) {
    super(manufacturerExternalId);
  }

  /** full constructor */
  public ManufacturerEntity(String name, String manufacturerExternalId, String description) {
    super(name, description, manufacturerExternalId);
  }

  public String toString() {
    return new ToStringBuilder(this).append("name", this.getName()).append("externalID", this.getExternalId()).append(
        "description", this.getDescription()).toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(T)
   */
  public int compareTo(Manufacturer o) {
    if (o == null) {
       return 1;
    }
    return this.getExternalId().compareTo(o.getExternalId());    
  }

}
