/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/SubscriberBean.java,v 1.9 2008/09/19 10:37:56 zhaowx Exp $
 * $Revision: 1.9 $
 * $Date: 2008/09/19 10:37:56 $
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
import com.npower.dm.core.DMException;
import com.npower.dm.core.Subscriber;

/**
 * All operations against subscribers will be conducted through this Bean.
 * @author Zhao DongLu
 * 
 */
public interface SubscriberBean extends BaseBean {

  // Public Methods
  // ******************************************************************************
  /**
   * Create a instance of subscriber.
   * @return
   * @throws DMException
   */
  public abstract Subscriber newSubscriberInstance() throws DMException;

  /**
   * Create a instance of subscriber.
   * @param carrier
   * @param externalID
   * @param phoneNumber
   * @param password
   * @return
   * @throws DMException
   */
  public abstract Subscriber newSubscriberInstance(Carrier carrier, String externalID, String phoneNumber, String password) throws DMException;

  /**
   * Add or update a subscriber into DM inventory. If the subscriber exists,
   * will update into DM inventory.
   * 
   * @param subscriber
   *          Subcriber
   * @throws DMException
   */
  public abstract void update(Subscriber subscriber) throws DMException;

  /**
   * Load a Subecriber by id from DM inventory. If no subscriber found, will
   * return a null.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract Subscriber getSubscriberByID(String id) throws DMException;

  /**
   * Load a Subecriber by externalID from DM inventory. If no subscriber found, will
   * return a null.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract Subscriber getSubscriberByExternalID(String externalID) throws DMException;

  /**
   * Load a Subecriber by phoneNumber from DM inventory. If no subscriber found, will
   * return a null.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract Subscriber getSubscriberByPhoneNumber(String phoneNumber) throws DMException;

  /**
   * Delete a subscriber from DM inventory. All entity, such as Devices , will
   * be deleted!!!
   * 
   * @param subscriber
   * @throws DMException
   */
  public abstract void delete(Subscriber subscriber) throws DMException;

  /**
   * Find subscribers from DM inventory by the clause. For exmaple: " from
   * SubscriberEntity where ....";
   * 
   * @param whereClause
   * @return List array of subscribers.
   * @throws DMException
   */
  public abstract List<Subscriber> findSubscriber(String whereClause) throws DMException;

  public abstract Subscriber getSubscriberByEmail(String email) throws DMException;

}