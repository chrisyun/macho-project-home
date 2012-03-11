/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/Manufacturer.java,v 1.5 2007/05/28 05:42:56 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2007/05/28 05:42:56 $
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

import java.util.Date;
import java.util.Set;

/**
 * Represent a manufacturer.
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/05/28 05:42:56 $
 */
public interface Manufacturer extends Comparable<Manufacturer> {

  /**
   * Default Model External ID, this id indicate default model in DM inventory. This model is for unknown model.
   */
  public static final String DEFAULT_MANUFACTURER_EXTERNAL_ID = "Default";

  /**
   * Default Model External ID, this id indicate default model in DM inventory. This model is for unknown model.
   */
  public static final String DEFAULT_MODEL_EXTERNAL_ID = Model.DEFAULT_MODEL_EXTERNAL_ID;

  /**
   * Return the ID.
   * @return
   */
  public abstract long getID();

  /**
   * Return the name.
   * @return
   */
  public abstract String getName();

  /**
   * Set a name for this manufacturer.
   * @param name
   */
  public abstract void setName(String name);

  /**
   * Return the description.
   * @return
   */
  public abstract String getDescription();

  /**
   * Set the description.
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * Return the externalID of this manufacturer.
   * @return
   */
  public abstract String getExternalId();

  /**
   * Set the external ID for this manufacturer. 
   * @param manufacturerExternalId
   */
  public abstract void setExternalId(String manufacturerExternalId);

  /**
   * Return a set of models which belongs to this manufacturer.
   * @return Return a <code>Set</code> of {@com.npower.dm.core.Model} objects.
   */
  public abstract Set<Model> getModels();

  /**
   * Getter of the LastUpdatedBy
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * Setter of the LastUpdatedBy
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Getter of the LastUpdatedTime
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * Getter of the ChangeVersion
   * @return
   */
  public abstract long getChangeVersion();

}