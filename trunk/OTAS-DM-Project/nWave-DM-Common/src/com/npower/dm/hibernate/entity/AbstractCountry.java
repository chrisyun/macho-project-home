/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractCountry.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2006/04/25 16:31:09 $
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
package com.npower.dm.hibernate.entity;

import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.Country;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractCountry implements java.io.Serializable, Country {

  // Fields
  /**
   * ID of country
   */
  private long ID;

  /**
   * ISO CountryEntity Name, eg: CN, US, ..
   */
  private String ISOCode;

  /**
   * Telecom area code, eg: "86", "01", ...
   */
  private String countryCode;

  /**
   * Name of country, eg" "China", ...
   */
  private String name;

  /**
   * Trunk code, Eg: Trunk code for China is "0", for US is "".
   */
  private String trunkCode;

  /**
   * Flag for display country code, If true, the telephone number will be
   * display : (86)13801356729
   */
  private boolean displayCountryCode;

  /**
   * Flag for display trunk code, If true, the telephone number will be display :
   * (086)13801356729
   */
  private boolean displayTrunkCode;

  private boolean displayPrefix;

  private long changeVersion;

  /**
   * Set of carriers, order by CarrierEntity's name.
   */
  private Set carriers = new TreeSet();

  // Constructors

  /** default constructor */
  public AbstractCountry() {
    super();
  }

  /**
   * Constructor
   * 
   * @param isoCode
   * @param countryCode
   * @param countryName
   * @param displayCountryCode
   * @param displayTrunkCode
   * @param displayPrefix
   * @param changeVersion
   */
  public AbstractCountry(String isoCode, String countryCode, String countryName, boolean displayCountryCode,
      boolean displayTrunkCode, boolean displayPrefix) {

    this.ISOCode = isoCode;
    this.countryCode = countryCode;
    this.name = countryName;
    this.displayCountryCode = displayCountryCode;
    this.displayTrunkCode = displayTrunkCode;
    this.displayPrefix = displayPrefix;
  }

  /**
   * Constructor
   * 
   * @param isoCode
   * @param countryCode
   * @param countryName
   * @param trunkCode
   * @param displayCountryCode
   * @param displayTrunkCode
   * @param displayPrefix
   * @param changeVersion
   */
  public AbstractCountry(String isoCode, String countryCode, String countryName, String trunkCode,
      boolean displayCountryCode, boolean displayTrunkCode, boolean displayPrefix) {

    this.ISOCode = isoCode;
    this.countryCode = countryCode;
    this.name = countryName;
    this.trunkCode = trunkCode;
    this.displayCountryCode = displayCountryCode;
    this.displayTrunkCode = displayTrunkCode;
    this.displayPrefix = displayPrefix;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long countryId) {
    this.ID = countryId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#getISOCode()
   */
  public String getISOCode() {
    return this.ISOCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#setISOCode(java.lang.String)
   */
  public void setISOCode(String isoCode) {
    this.ISOCode = isoCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#getCountryCode()
   */
  public String getCountryCode() {
    return this.countryCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#setCountryCode(java.lang.String)
   */
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#setName(java.lang.String)
   */
  public void setName(String countryName) {
    this.name = countryName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#getTrunkCode()
   */
  public String getTrunkCode() {
    return this.trunkCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#setTrunkCode(java.lang.String)
   */
  public void setTrunkCode(String trunkCode) {
    this.trunkCode = trunkCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#getDisplayCountryCode()
   */
  public boolean getDisplayCountryCode() {
    return this.displayCountryCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#setDisplayCountryCode(boolean)
   */
  public void setDisplayCountryCode(boolean displayCountryCode) {
    this.displayCountryCode = displayCountryCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#getDisplayTrunkCode()
   */
  public boolean getDisplayTrunkCode() {
    return this.displayTrunkCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#setDisplayTrunkCode(boolean)
   */
  public void setDisplayTrunkCode(boolean displayTrunkCode) {
    this.displayTrunkCode = displayTrunkCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#getDisplayPrefix()
   */
  public boolean getDisplayPrefix() {
    return this.displayPrefix;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#setDisplayPrefix(boolean)
   */
  public void setDisplayPrefix(boolean displayPrefix) {
    this.displayPrefix = displayPrefix;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.Country#getCarriers()
   */
  public Set getCarriers() {
    return this.carriers;
  }

  public void setCarriers(Set carriers) {
    this.carriers = carriers;
  }

}