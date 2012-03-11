/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractServiceProvider.java,v 1.1 2008/06/16 10:11:15 zhao Exp $
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

import java.util.HashSet;
import java.util.Set;

import com.npower.dm.core.ServiceProvider;
import com.npower.dm.core.Subscriber;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/16 10:11:15 $
 */
public abstract class AbstractServiceProvider implements java.io.Serializable, ServiceProvider {

  // Fields
  /**
   * ID of CarrierEntity
   */
  private long ID;

  /**
   * EnternalID of CarrierEntity
   */
  private String externalID;

  /**
   * Name of CarrierEntity
   */
  private String name;
  
  /**
   * Description of the carrier.
   */
  private String description;

  /**
   * Set of the subscribers for this carrier.
   */
  private Set<Subscriber> subscribers = new HashSet<Subscriber>(0);

  // Constructors

  /** default constructor */
  public AbstractServiceProvider() {
    super();
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long carrierId) {
    this.ID = carrierId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getExternalID()
   */
  public String getExternalID() {
    return this.externalID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setExternalID(java.lang.String)
   */
  public void setExternalID(String carrierExternalId) {
    this.externalID = carrierExternalId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Carrier#getSubscribersForCarrierId()
   */
  public Set<Subscriber> getSubscribers() {
    return this.subscribers;
  }

  public void setSubscribers(Set<Subscriber> s) {
    this.subscribers = s;
  }

}