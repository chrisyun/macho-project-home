/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/TestCountry.java,v 1.4 2007/08/29 06:20:59 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2007/08/29 06:20:59 $
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

package com.npower.dm.hibernate.management;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;

import com.npower.dm.core.Country;
import com.npower.dm.hibernate.entity.CountryEntity;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/08/29 06:20:59 $
 */
public class TestCountry extends TestCase {
  
  Session session = null;

  protected void setUp() throws Exception {
    super.setUp();
    // Open a hibernate session
    HibernateSessionFactory sessionFactory = HibernateSessionFactory.newInstance();
    this.session = sessionFactory.currentSession();
  }

  /**
   * Test case: basiclly DB operation, include: search, add, load
   * 
   * @throws Exception
   */
  public void testDBOperation() throws Exception {

    String isoCode = "tt";
    String countryCode = "8666";
    String countryName = "China_Test";
    String trunkCode = "0";
    boolean displayCountryCode = false;
    boolean displayTrunkCode = false;
    boolean displayPrefix = false;
    Transaction tx = null;

    Query query = this.session.createQuery("from CountryEntity where isoCode='" + isoCode + "'");
    List<Country> list = query.list();
    if (list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        Country cc = (Country) list.get(i);
        tx = this.session.beginTransaction();
        this.session.delete(cc);
        tx.commit();
      }
    }

    tx = this.session.beginTransaction();
    Country country = new CountryEntity(isoCode, countryCode, countryName, trunkCode, displayCountryCode,
        displayTrunkCode, displayPrefix);
    this.session.save(country);
    tx.commit();

    long countryID = country.getID();

    country = (Country) this.session.load(Country.class, new Long(countryID));
    assertEquals(isoCode, country.getISOCode());

    tx = this.session.beginTransaction();
    this.session.delete(country);
    tx.commit();
  }

  /**
   * Test case: iso_code and countrycode is unique, test it.
   * 
   * @throws Exception
   */
  public void testISOCodeUniue() throws Exception {
    String isoCode1 = "t1";
    String countryCode1 = "8661";
    String countryName1 = "China_Test_1";
    String trunkCode1 = "0";

    String isoCode2 = "t2";
    String countryCode2 = "8662";
    String countryName2 = "China_Test_2";
    String trunkCode2 = "1";

    boolean displayCountryCode = false;
    boolean displayTrunkCode = false;
    boolean displayPrefix = false;
    Transaction tx = null;
    // Clear records which isoCode is "tt"
    Query query = this.session.createQuery("from CountryEntity where isoCode='" + isoCode1 + "'");
    List<Country> list = query.list();
    if (list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        Country cc = (Country) list.get(i);
        tx = this.session.beginTransaction();
        this.session.delete(cc);
        tx.commit();
      }
    }

    // Add the first record which isoCode is "tt"
    tx = this.session.beginTransaction();
    Country country1 = new CountryEntity(isoCode1, countryCode1, countryName1, trunkCode1, displayCountryCode,
        displayTrunkCode, displayPrefix);
    this.session.save(country1);
    tx.commit();

    // Add again the record which isoCode is "tt" too.
    try {
      tx = this.session.beginTransaction();
      Country country2 = new CountryEntity(isoCode1, countryCode2, countryName2, trunkCode2, displayCountryCode,
          displayTrunkCode, displayPrefix);
      this.session.save(country2);
      tx.commit();
      assertTrue(false);
    } catch (TransactionException e) {
      // with the unique constraint, the second adding will be failure!
      assertTrue(true);
    }

    // Add again the record which country code is "tt" too.
    try {
      tx = this.session.beginTransaction();
      Country country2 = new CountryEntity(isoCode2, countryCode2, countryName1, trunkCode2, displayCountryCode,
          displayTrunkCode, displayPrefix);
      this.session.save(country2);
      tx.commit();
      assertTrue(false);
    } catch (TransactionException e) {
      // with the unique constraint, the second adding will be failure!
      assertTrue(true);
    }

    // Clear the country1
    tx = this.session.beginTransaction();
    this.session.delete(country1);
    tx.commit();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    if (this.session != null) {
       this.session.close();
    }
  }

}
