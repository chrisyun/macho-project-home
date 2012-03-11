/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/ProfileCategoryDecorator.java,v 1.1 2007/09/06 09:45:13 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/09/06 09:45:13 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.decorator;

import java.util.Date;
import java.util.Locale;
import java.util.Set;

import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/09/06 09:45:13 $
 */
public class ProfileCategoryDecorator extends Decorator<ProfileCategory> implements ProfileCategory {

  /**
   * Default Constructor
   */
  public ProfileCategoryDecorator() {
    super();
  }

  /**
   * Constructor
   */
  public ProfileCategoryDecorator(Locale locale, ProfileCategory decoratee, ResourceManager<ProfileCategory> resources) {
    super(decoratee, locale, resources);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.getDecoratee().getChangeVersion();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getDescription()
   */
  public String getDescription() {
    String index = getDecoratee().getName();
    String orginalValue = getDecoratee().getDescription();
    String value = this.getResourceManager().getResource(this.getLocale(), this, index, "description", orginalValue);
    return value;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getID()
   */
  public long getID() {
    return this.getDecoratee().getID();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.getDecoratee().getLastUpdatedBy();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    return this.getDecoratee().getLastUpdatedTime();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getName()
   */
  public String getName() {
    String index = getDecoratee().getName();
    String orginalValue = getDecoratee().getName();
    String value = this.getResourceManager().getResource(this.getLocale(), this, index, "name", orginalValue);
    return value;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getProfileTemplates()
   */
  public Set<ProfileTemplate> getProfileTemplates() {
    return this.getDecoratee().getProfileTemplates();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.getDecoratee().setDescription(description);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.getDecoratee().setLastUpdatedBy(lastUpdatedBy);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#setName(java.lang.String)
   */
  public void setName(String name) {
    this.getDecoratee().setName(name);
  }

}
