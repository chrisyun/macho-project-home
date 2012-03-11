/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/ServiceProviderBean.java,v 1.1 2008/06/16 10:11:15 zhao Exp $
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
package com.npower.dm.management;

import java.util.List;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ServiceProvider;

/**
 * All operations against ServiceProviders will be conducted through this ServiceProviderBean. 
 * @author Zhao DongLu
 * 
 */
public interface ServiceProviderBean extends BaseBean {
  
  /**
   * Create a instance of ServiceProvider
   * @return
   * @throws DMException
   */
  public abstract ServiceProvider newServiceProviderInstance() throws DMException;

  /**
   * Create a instance of ServiceProvider
   * @param externalId
   * @param name
   * @return
   * @throws DMException
   */
  public abstract ServiceProvider newServiceProviderInstance(String externalId, String name) throws DMException;

  /**
   * Find a ServiceProvider by ID
   * 
   * @param id
   * @return CarrierEntity
   * @throws DMException
   */
  public abstract ServiceProvider getServiceProviderByID(String id) throws DMException;

  /**
   * Find a ServiceProvider by external ID.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract ServiceProvider getServiceProviderByExternalID(String id) throws DMException;

  /**
   * Add or update a ServiceProvider into DM inventory.
   * 
   * @param country
   * @throws DMException
   */
  public abstract void update(ServiceProvider sp) throws DMException;

  /**
   * Retrieve all of ServiceProviders.
   * 
   * @return List Array of ServiceProvider
   * @throws DMException
   */
  public abstract List<ServiceProvider> getAllServiceProviders() throws DMException;

  /**
   * Retrieve all of ServiceProviders. 
   * For example: from ServiceProviderEntity where ...
   * 
   * 
   * @return List Array ofServiceProviderEntity
   * @throws DMException
   */
  public abstract List<ServiceProvider> findServiceProviders(String whereClause) throws DMException;

  /**
   * Delete a ServiceProvider. 
   * 
   * @param country
   * @throws DMException
   */
  public abstract void delete(ServiceProvider sp) throws DMException;

}