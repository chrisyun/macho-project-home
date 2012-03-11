/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ServiceProvider.java,v 1.1 2008/06/16 10:11:15 zhao Exp $
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
package com.npower.dm.core;

import java.util.Set;

/**
 * <p>Represnet a Carrier.</p>
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/16 10:11:15 $
 */
public interface ServiceProvider {

  /**
   * Return the ID.
   * @return
   */
  public abstract long getID();

  /**
   * Return the alias name of the carrier. <br>
   * Eg: China Mobile's externalID is "CMCC".
   * @return
   */
  public abstract String getExternalID();

  /**
   * Setup the externalID for the carrier.
   * 
   * @param carrierExternalId
   */
  public abstract void setExternalID(String carrierExternalId);

  /**
   * Return the name of carrier.
   * @return
   */
  public abstract String getName();

  /**
   * Set the name of carrier.
   * @param name
   */
  public abstract void setName(String name);

  /**
   * Return the description.
   * 
   * @return
   */
  public abstract String getDescription();

  /**
   * Set the description.
   * 
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * Return all of subscribers which belong to this carrier.
   * 
   * @return Return a <code>Set</code> of the {$@link com.npower.dm.core.Subscriber} objects.
   */
  public abstract Set<Subscriber> getSubscribers();

}