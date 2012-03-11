/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/CountryDecorator.java,v 1.4 2007/09/06 09:45:13 zhao Exp $
  * $Revision: 1.4 $
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

import java.util.Locale;
import java.util.Set;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/09/06 09:45:13 $
 */
public class CountryDecorator extends Decorator<Country> implements Country {

  /**
   * Default Constructor
   */
  public CountryDecorator() {
    super();
  }

  /**
   * Constructor
   */
  public CountryDecorator(Locale locale, Country decoratee, ResourceManager<Country> resources) {
    super(decoratee, locale, resources);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#getCarriers()
   */
  public Set<Carrier> getCarriers() {
    return getDecoratee().getCarriers();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#getChangeVersion()
   */
  public long getChangeVersion() {
    return getDecoratee().getChangeVersion();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#getCountryCode()
   */
  public String getCountryCode() {
    return getDecoratee().getCountryCode();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#getDisplayCountryCode()
   */
  public boolean getDisplayCountryCode() {
    return getDecoratee().getDisplayCountryCode();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#getDisplayPrefix()
   */
  public boolean getDisplayPrefix() {
    return getDecoratee().getDisplayPrefix();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#getDisplayTrunkCode()
   */
  public boolean getDisplayTrunkCode() {
    return getDecoratee().getDisplayTrunkCode();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#getID()
   */
  public long getID() {
    return getDecoratee().getID();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#getISOCode()
   */
  public String getISOCode() {
    return getDecoratee().getISOCode();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#getName()
   */
  public String getName() {
    String index = getDecoratee().getISOCode();
    String orginalValue = getDecoratee().getName();
    String value = this.getResourceManager().getResource(this.getLocale(), this, index, "name", orginalValue);
    return value;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#getTrunkCode()
   */
  public String getTrunkCode() {
    return getDecoratee().getTrunkCode();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#setCountryCode(java.lang.String)
   */
  public void setCountryCode(String countryCode) {
    getDecoratee().setCountryCode(countryCode);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#setDisplayCountryCode(boolean)
   */
  public void setDisplayCountryCode(boolean displayCountryCode) {
    getDecoratee().setDisplayCountryCode(displayCountryCode);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#setDisplayPrefix(boolean)
   */
  public void setDisplayPrefix(boolean displayPrefix) {
    getDecoratee().setDisplayPrefix(displayPrefix);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#setDisplayTrunkCode(boolean)
   */
  public void setDisplayTrunkCode(boolean displayTrunkCode) {
    getDecoratee().setDisplayTrunkCode(displayTrunkCode);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#setISOCode(java.lang.String)
   */
  public void setISOCode(String isoCode) {
    getDecoratee().setISOCode(isoCode);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#setName(java.lang.String)
   */
  public void setName(String countryName) {
    getDecoratee().setName(countryName);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Country#setTrunkCode(java.lang.String)
   */
  public void setTrunkCode(String trunkCode) {
    getDecoratee().setTrunkCode(trunkCode);
  }


}
