/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ServiceProviderEntity.java,v 1.1 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/16 10:11:15 $
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

import com.npower.dm.core.ServiceProvider;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/16 10:11:15 $
 */
public class ServiceProviderEntity extends AbstractServiceProvider implements java.io.Serializable, Comparable<ServiceProvider> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // Constructors

  /**
   * Default Constructor Set default values: ServerAuthenType: DIGEST
   * NotificationType: WAP Push Notification State Timeout: default Notification
   * Max Num of retries: Default Bootstrap Timeout: Default
   */
  public ServiceProviderEntity() {
    super();
  }

  /**
   * Implements toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("ID", this.getID()).append("externalID", this.getExternalID()).toString();
  }

  /**
   * Implements Comparable.compareTo()
   */
  public int compareTo(ServiceProvider o) {
    if (o instanceof ServiceProvider) {
      ServiceProvider other = (ServiceProvider) o;
      return this.getName().compareTo(other.getName());
    } else {
      return -1;
    }
  }

}
