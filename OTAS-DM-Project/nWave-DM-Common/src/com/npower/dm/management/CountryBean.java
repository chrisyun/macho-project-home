/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/CountryBean.java,v 1.8 2006/11/27 15:50:09 zhao Exp $
 * $Revision: 1.8 $
 * $Date: 2006/11/27 15:50:09 $
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

import org.hibernate.Session;

import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;

/**
 * All operations against countries will be conducted through CountryBean.
 * @author Zhao DongLu
 * 
 */
public interface CountryBean extends BaseBean {

  public abstract Session getHibernateSession();

  /**
   * Create a instance of Country
   * @return
   * @throws DMException
   */
  public abstract Country newCountryInstance() throws DMException;
  
  /**
   * Create a instance of country
   * @param isoCode
   * @param countryCode
   * @param countryName
   * @param trunkCode
   * @param displayCountryCode
   * @param displayTrunkCode
   * @param displayPrefix
   * @return
   * @throws DMException
   */
  public abstract Country newCountryInstance(String isoCode, String countryCode, String countryName, String trunkCode,
      boolean displayCountryCode, boolean displayTrunkCode, boolean displayPrefix) throws DMException;

  /**
   * Find country by id. 
   * 
   * @param id
   * @return CountryEntity return null, if ths id no-exists.
   */
  public abstract Country getCountryByID(long id) throws DMException;

  /**
   * Find country by iso_code. Notice: ISO code is upper-case
   * 
   * @param isoCode
   * @return CountryEntity return null, if ths ISO code no-exists.
   */
  public abstract Country getCountryByISOCode(String isoCode) throws DMException;

  /**
   * Find country by country_code.
   * 
   * @param countryCode
   * @return CountryEntity return null, if ths ISO code no-exists.
   */
  public abstract Country getCountryByCountryCode(String countryCode) throws DMException;

  /**
   * Add or update a country into DM inventory.
   * 
   * @param country
   * @throws DMException
   */
  public abstract void update(Country country) throws DMException;

  /**
   * Retrieve all of countries.
   * 
   * @return List Array of CountryEntity
   * @throws DMException
   */
  public abstract List<Country> getAllCountries() throws DMException;

  /**
   * no public method, test purpose only.
   * 
   * @param country
   * @throws DMException
   */
  public abstract void delete(Country country) throws DMException;

}