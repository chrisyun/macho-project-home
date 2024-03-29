/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/SoftwareVendorEntity.java,v 1.3 2008/08/28 14:58:16 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/08/28 14:58:16 $
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

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.SoftwareVendor;

/**
 * SoftwareVendorEntity generated by MyEclipse Persistence Tools
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/08/28 14:58:16 $
 */
public class SoftwareVendorEntity extends AbstractSoftwareVendor implements java.io.Serializable, SoftwareVendor {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = -6339433522903630762L;

  /** default constructor */
  public SoftwareVendorEntity() {
  }

  /** minimal constructor */
  public SoftwareVendorEntity(long vendorId, String name) {
    super(vendorId, name);
  }

  /** full constructor */
  public SoftwareVendorEntity(long vendorId, String name, String description, String webSite, Set<SoftwareEntity> softwares) {
    super(vendorId, name, description, webSite, softwares);
  }

  /* (non-Javadoc)
 * @see java.lang.Comparable#compareTo(java.lang.Object)
 */
public int compareTo(SoftwareVendor o) {
    if (o == null) {
        return 1;
     }
     if (StringUtils.isEmpty(this.getName()) && StringUtils.isEmpty(this.getName())) {
        return 0;
     } else if (StringUtils.isEmpty(this.getName())) {
       return -1;
     } else {
       return this.getName().compareTo(o.getName());
     }
  }

}
