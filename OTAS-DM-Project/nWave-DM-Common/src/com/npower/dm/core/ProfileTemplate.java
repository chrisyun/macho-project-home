/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ProfileTemplate.java,v 1.4 2007/01/17 04:31:00 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2007/01/17 04:31:00 $
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
 * <p>Represent a ProfileTemplate.</p>
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/01/17 04:31:00 $
 */
public interface ProfileTemplate {
  
  /**
   * Return the ID
   * @return
   */
  public abstract long getID();

  /**
   * Return the ProfileCategory of this template.
   * @return
   */
  public abstract ProfileCategory getProfileCategory();

  /**
   * Set the ProfileCategory for this template.
   * @param nwDmProfileCategory
   */
  public abstract void setProfileCategory(ProfileCategory nwDmProfileCategory);

  /**
   * Return the name of the template.
   * @return
   */
  public abstract String getName();

  /**
   * Set the name of the template.
   * @param name
   */
  public abstract void setName(String name);

  /**
   * Return true, if this template need a NAP Profile.
   * @return
   */
  public abstract boolean getNeedsNap();

  /**
   * Set <code>true</code>, if this template need a NAP Profile.
   * @param needsNap
   */
  public abstract void setNeedsNap(boolean needsNap);

  /**
   * Return <code>true</code>, if this template need a Proxy Profile.
   * 
   * @return
   */
  public abstract boolean getNeedsProxy();

  /**
   * Set <code>true</code>, if this template need a Proxy profile.
   * @param needsProxy
   */
  public abstract void setNeedsProxy(boolean needsProxy);

  /**
   * Return LastUpdateBy
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * Set LastUpdateBy
   * 
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Return the lastUpdatedTime
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * Return the changeVersion.
   * @return
   */
  public abstract long getChangeVersion();

  /**
   * Return a <code>Set</code> of the {@link com.npower.dm.core.ProfileMapping} objects.
   * @return
   */
  public abstract Set<ProfileMapping> getProfileMappings();

  /**
   * Return the models which has been assign a mapping between this template and its DDF.
   * @return
   */
  public abstract Set<Model> getModels();
  
  /**
   * A set of attributes for this ProfileTemplate.<br>
   * 
   * @return Return a <code>Set</code> of the {@link com.npower.dm.core.ProfileAttribute} objects.
   */
  public abstract Set<ProfileAttribute> getProfileAttributes();

  /**
   * A set of ProfileConfigs which reference this ProfileTemplate.
   * 
   * @return Return a <code>Set</code> of the {@link com.npower.dm.core.ProfileConfig} objects.
   */
  public abstract Set<ProfileConfig> getProfileConfigs();

  /**
   * Find a attribute by name include by the ProfileTemplate.
   * <br>
   * Caution: Attribute's name is case-sensetive.
   * <br>
   * @param name   Attribute's name
   * @return null If no attribute match the name.
   */
  public abstract ProfileAttribute getAttributeByAttributeName(String name);
}