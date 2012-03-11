/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/Country.java,v 1.4 2007/01/17 04:31:00 zhao Exp $
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

import java.util.Set;

/**
 * <p>Represent a Country.</p>
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/01/17 04:31:00 $
 */
public interface Country {

  /**
   * Getter of the ID.
   * @return
   */
  public abstract long getID();

  /**
   * Getter of the ISOCode, eg: "CN", "US", ...
   * @return
   */
  public abstract String getISOCode();

  /**
   * Setter of the ISOCode
   * @param isoCode
   */
  public abstract void setISOCode(String isoCode);

  /**
   * Getter of the telephone area code for this Country.
   * @return
   */
  public abstract String getCountryCode();

  /**
   * Setter of the telephone area-code for this country.
   * @param countryCode
   */
  public abstract void setCountryCode(String countryCode);

  /**
   * Getter of the country's name
   * @return
   */
  public abstract String getName();

  /**
   * Setter of the country's name
   * @param countryName
   */
  public abstract void setName(String countryName);

  /**
   * Getter of the country's trunk code.
   * @return
   */
  public abstract String getTrunkCode();

  /**
   * Setter of the country's trunk code.
   * @param trunkCode
   */
  public abstract void setTrunkCode(String trunkCode);

  /**
   * Flag to indicate prefix a country code on the telephonenumber.<br>
   * Eg: true  -> (86)13801356729
   * 
   * @return
   */
  public abstract boolean getDisplayCountryCode();

  /**
   * Setter of the display_country_code flag.
   * 
   * @param displayCountryCode
   */
  public abstract void setDisplayCountryCode(boolean displayCountryCode);

  /**
   * Getter of the display_trunk_code flag.
   * 
   * @return
   */
  public abstract boolean getDisplayTrunkCode();

  /**
   * Setter of the display_trunk_code flag.
   * 
   * @param displayTrunkCode
   */
  public abstract void setDisplayTrunkCode(boolean displayTrunkCode);

  /**
   * Getter of the display_prefix flag.
   * @return
   */
  public abstract boolean getDisplayPrefix();

  /**
   * Setter of the display_prefix flag.
   * @param displayPrefix
   */
  public abstract void setDisplayPrefix(boolean displayPrefix);

  /**
   * Return the changeVersion
   * @return
   */
  public abstract long getChangeVersion();

  /**
   * Return all of carriers which belongs to this country.<br>
   * The elements will be ordered by carrier's name in ASC.
   * 
   * @return Return a <code>Set</code> of the {@link com.npower.dm.core.Carrier} objects
   */
  public abstract Set<Carrier> getCarriers();

}