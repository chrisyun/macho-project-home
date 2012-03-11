/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestCountryManagementBean.java,v 1.17 2008/06/16 10:11:14 zhao Exp $
 * $Revision: 1.17 $
 * $Date: 2008/06/16 10:11:14 $
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

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * 
 */
public class TestCountryManagementBean extends TestCase {

  private Country[] allOfCountries = null;

  /**
   * Constructor for TestCountryManagementBean.
   * 
   * @param arg0
   */
  public TestCountryManagementBean(String arg0) {
    super(arg0);
  }

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    CountryBean bean = beanFactory.createCountryBean();

    try {
        beanFactory.beginTransaction();
        this.allOfCountries = new Country[] {
            bean.newCountryInstance("US", "1", "United State", "1", true, true, true),
            bean.newCountryInstance("CN", "86", "China", "0", true, true, true),
            bean.newCountryInstance("HK", "852", "Hong Kong", "0", true, true, true),
            bean.newCountryInstance("GB", "44", "United Kingdom", "0", true, true, true),
            bean.newCountryInstance("FR", "33", "France", "0", true, true, true),
            bean.newCountryInstance("IT", "39", "Italy", "0", true, true, true),
            bean.newCountryInstance("DE", "49", "Germany", "0", true, true, true),
            bean.newCountryInstance("SG", "65", "Singapore", "0", true, false, true),
            bean.newCountryInstance("ES", "34", "Spain", "0", true, true, true),
            bean.newCountryInstance("TH", "66", "Thailand", "0", true, true, true) };
        beanFactory.commit();
    } catch (Exception e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /*
   * Test method for
   * 'com.npower.dm.management.CountryManagementBeanImpl.getCountryByISOCode(String)'
   */
  public void testGetCountryByISOCode() throws Exception {
    this.testAdd();
  }

  /*
   * Test method for
   * 'com.npower.dm.management.CountryManagementBeanImpl.delete(CountryEntity)'
   */
  public void testDelete() throws Exception {
    this.testAdd();
  }
  

  /*
   * Test method for
   * 'com.npower.dm.management.CountryManagementBeanImpl.add(CountryEntity)'
   */
  public void testAdd() throws Exception {
    String isoCode = "TT";
    String countryCode = "8666";
    String countryName = "China_Test";
    String trunkCode = "0";
    // long displayCountryCode = 0L;
    // long displayTrunkCode = 0L;
    // long displayPrefix = 0L;
    // long changeVersion = 0;

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    CountryBean bean = beanFactory.createCountryBean();

    try {
      
      Country country = bean.newCountryInstance(isoCode, countryCode, countryName, trunkCode, true, true, true);
      beanFactory.beginTransaction();
      bean.update(country);
      beanFactory.commit();
      
      country = bean.getCountryByISOCode(isoCode);
      assertEquals(country.getISOCode(), isoCode);
      assertEquals(country.getCountryCode(), countryCode);
      assertEquals(country.getName(), countryName);
      assertEquals(country.getTrunkCode(), trunkCode);

      beanFactory.beginTransaction();
      bean.delete(country);
      beanFactory.commit();
      
      country = bean.getCountryByISOCode(isoCode);
      assertNull(country);

    } catch (Exception e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }

  }

  /*
   * Test method for
   * 'com.npower.dm.management.CountryManagementBeanImpl.getCountryByISOCode(String)'
   */
  public void testGetAllCountries() throws Exception {

    String isoCodePrefix = "";
    String countryCodePrefix = "8668";
    String countryNamePrefix = "China_Test_";
    String trunkCodePrefix = "0";
    // long displayCountryCode = 0L;
    // long displayTrunkCode = 0L;
    // long displayPrefix = 0L;
    // long changeVersion = 0;

    int total = 20;

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    CountryBean bean = beanFactory.createCountryBean();
    try {
        // Remove all countries
        beanFactory.beginTransaction();
        deleteAllCountries(bean);
        beanFactory.commit();
  
        beanFactory.beginTransaction();
        for (int i = 0; i < total; i++) {
          Country country = bean.newCountryInstance(isoCodePrefix + i, countryCodePrefix + i, countryNamePrefix + i,
              trunkCodePrefix + i, true, true, true);
          bean.update(country);
        }
        beanFactory.commit();
  
        List<Country> countries;
        countries = bean.getAllCountries();
        assertEquals(total, countries.size());
  
        beanFactory.beginTransaction();
        for (int i = 0; i < total; i++) {
          Country country = bean.getCountryByISOCode(isoCodePrefix + i);
          bean.delete(country);
        }
        beanFactory.commit();

    } catch (Exception e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }
  }

  /**
   * Delete all of countries
   * 
   * @throws DMException
   */
  private void deleteAllCountries(CountryBean bean) throws DMException {
    List<Country> countries = bean.getAllCountries();
    for (int i = 0; countries != null && i < countries.size(); i++) {
      bean.delete((Country) countries.get(i));
    }
  }

  /**
   * Import all of countries for production enviroment.
   * 
   */
  public void testImport() throws Exception {

    ManagementBeanFactory beanFactory = AllTests.getManagementBeanFactory();
    CountryBean bean = beanFactory.createCountryBean();
    try {
      beanFactory.beginTransaction();
      // Delete all of countries
      deleteAllCountries(bean);
      beanFactory.commit();

      // Add the countries for production enviroment
      beanFactory.beginTransaction();
      for (int i = 0; i < this.allOfCountries.length; i++) {
        bean.update(this.allOfCountries[i]);
      }
      beanFactory.commit();

      Country country = bean.getCountryByCountryCode("86");
      assertEquals(country.getISOCode(), "CN");

      country = bean.getCountryByISOCode("CN");
      assertEquals(country.getCountryCode(), "86");

    } catch (Exception e) {
      beanFactory.rollback();
      throw e;
    } finally {
      beanFactory.release();
    }
  }

}
