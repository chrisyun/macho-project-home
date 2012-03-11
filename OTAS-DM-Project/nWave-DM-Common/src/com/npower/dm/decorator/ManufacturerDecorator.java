/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/ManufacturerDecorator.java,v 1.1 2007/09/06 09:45:13 zhao Exp $
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

import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/09/06 09:45:13 $
 */
public class ManufacturerDecorator extends Decorator<Manufacturer> implements Manufacturer {

  /**
   * Default Constructor
   */
  public ManufacturerDecorator() {
    super();
  }

  /**
   * Constructor
   */
  public ManufacturerDecorator(Locale locale, Manufacturer decoratee, ResourceManager<Manufacturer> resources) {
    super(decoratee, locale, resources);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.getDecoratee().getChangeVersion();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#getDescription()
   */
  public String getDescription() {
    return this.getDecoratee().getDescription();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#getExternalId()
   */
  public String getExternalId() {
    return this.getDecoratee().getExternalId();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#getID()
   */
  public long getID() {
    return this.getDecoratee().getID();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    return this.getDecoratee().getLastUpdatedBy();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    return this.getDecoratee().getLastUpdatedTime();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#getModels()
   */
  public Set<Model> getModels() {
    return this.getDecoratee().getModels();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#getName()
   */
  public String getName() {
    String index = getDecoratee().getExternalId();
    String orginalValue = getDecoratee().getName();
    String value = this.getResourceManager().getResource(this.getLocale(), this, index, "name", orginalValue);
    return value;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.getDecoratee().setDescription(description);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#setExternalId(java.lang.String)
   */
  public void setExternalId(String manufacturerExternalId) {
    this.getDecoratee().setExternalId(manufacturerExternalId);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.getDecoratee().setLastUpdatedBy(lastUpdatedBy);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Manufacturer#setName(java.lang.String)
   */
  public void setName(String name) {
    this.getDecoratee().setName(name);
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Manufacturer o) {
    return this.getDecoratee().compareTo(o);
  }


}
