/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/CountryManagementBeanImpl.java,v 1.7 2008/06/05 10:34:42 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/06/05 10:34:42 $
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

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.hibernate.entity.CountryEntity;
import com.npower.dm.management.CountryBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/06/05 10:34:42 $
 */
public class CountryManagementBeanImpl extends AbstractBean implements CountryBean {

  /**
   * Default Constructor
   * 
   */
  protected CountryManagementBeanImpl() {
    super();
  }

  /**
   * Construactor with the parameter of Hibernate Session.
   */
  CountryManagementBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.CountryBean#newCountryInstance()
   */
  public Country newCountryInstance() throws DMException {
    return new CountryEntity();
  }

  public Country newCountryInstance(String isoCode, String countryCode, String countryName, String trunkCode,
      boolean displayCountryCode, boolean displayTrunkCode, boolean displayPrefix) throws DMException {
    return new CountryEntity(isoCode, countryCode, countryName, trunkCode, displayCountryCode, displayTrunkCode,
        displayPrefix);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.CountryBean#getCountryByID(long)
   */
  public Country getCountryByID(long id) throws DMException {
    if (id == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from CountryEntity where ID='" + id + "'");
      List<Country> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Country) list.get(0);
      } else {
        throw new DMException("Result is not unique, many countries have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * Get CountryEntity object by the ISO Code.
   * 
   * @see com.npower.dm.management.hb.CountryBean#getCountryByISOCode(java.lang.String)
   */
  public Country getCountryByISOCode(String isoCode) throws DMException {
    if (isoCode == null) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from CountryEntity where ISOCode='" + isoCode.toUpperCase() + "'");
      List<Country> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Country) list.get(0);
      } else {
        throw new DMException("Result is not unique, many countries have the same ISO Code: " + isoCode.toUpperCase());
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.CountryBean#getCountryByCountryCode(java.lang.String)
   */
  public Country getCountryByCountryCode(String countryCode) throws DMException {
    if (countryCode == null) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from CountryEntity where countryCode='" + countryCode + "'");
      List<Country> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Country) list.get(0);
      } else {
        throw new DMException("Result is not unique, many countries have the same CountryEntity Code: " + countryCode);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.CountryBean#add(com.npower.dm.hibernate.CountryEntity)
   */
  public void update(Country country) throws DMException {
    if (country == null) {
      throw new NullPointerException("Could not add a null country into database.");
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      //Transaction tx = session.beginTransaction();
      String isoCode = country.getISOCode();
      country.setISOCode(isoCode.toUpperCase());
      session.save(country);
      //tx.commit();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.CountryBean#getAllCountries()
   */
  public List<Country> getAllCountries() throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from CountryEntity order by ISOCode");
      List<Country> list = query.list();

      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * no public method, test purpose only.
   * 
   * @param country
   * @throws DMException
   */
  public void delete(Country country) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      // Transaction tx = session.beginTransaction();
      session.delete(country);
      // tx.commit();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

}
