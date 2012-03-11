/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/CarrierBean.java,v 1.11 2006/12/13 07:15:37 zhao Exp $
 * $Revision: 1.11 $
 * $Date: 2006/12/13 07:15:37 $
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

import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;

/**
 * All operations against carriers will be conducted through this CarrierBean. 
 * @author Zhao DongLu
 * 
 */
public interface CarrierBean extends BaseBean {
  
  /**
   * Default Model ID, this id indicate a model in DM inventory. This model is for unknown model.
   */
  public static final String DEFAULT_CARRIER_ID = "1000";
  
  /**
   * Create a instance of carrier
   * @return
   * @throws DMException
   */
  public abstract Carrier newCarrierInstance() throws DMException;

  /**
   * Create a instance of carrier
   * @param country
   * @param carrierExternalId
   * @param name
   * @return
   * @throws DMException
   */
  public abstract Carrier newCarrierInstance(Country country, String carrierExternalId, String name) throws DMException;

  /**
   * Find a CarrierEntity by CarrierEntity ID
   * 
   * @param id
   * @return CarrierEntity
   * @throws DMException
   */
  public abstract Carrier getCarrierByID(String id) throws DMException;

  /**
   * Fing a carrier by carrier external ID.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract Carrier getCarrierByExternalID(String id) throws DMException;

  /**
   * Add or update a CarrierEntity into DM inventory.
   * 
   * @param country
   * @throws DMException
   */
  public abstract void update(Carrier carrier) throws DMException;

  /**
   * Retrieve all of Carrieres.
   * 
   * @return List Array of CarrierEntity
   * @throws DMException
   */
  public abstract List<Carrier> getAllCarriers() throws DMException;

  /**
   * Retrieve all of Carrieres. 
   * For example: from CarrierEntity where ...
   * 
   * 
   * @return List Array of CarrierEntity
   * @throws DMException
   */
  public abstract List<Carrier> findCarriers(String whereClause) throws DMException;

  /**
   * Delete a carrier. it will remove all of related with carrier, except audit
   * log.
   * 
   * @param country
   * @throws DMException
   */
  public abstract void delete(Carrier carrier) throws DMException;

  /**
   * Find Carrier by phone number policies.
   * If none exists carrier match the phone number, will return null.
   * @param string
   * @return
   */
  public abstract Carrier findCarrierByPhoneNumberPolicy(String phoneNumber) throws DMException;

}