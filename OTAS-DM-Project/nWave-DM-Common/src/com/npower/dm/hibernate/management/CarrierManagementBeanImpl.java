/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/CarrierManagementBeanImpl.java,v 1.8 2007/08/27 11:32:31 zhao Exp $
 * $Revision: 1.8 $
 * $Date: 2007/08/27 11:32:31 $
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.hibernate.entity.CarrierEntity;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2007/08/27 11:32:31 $
 */
public class CarrierManagementBeanImpl extends AbstractBean implements CarrierBean {

  /**
   * Default Constructor
   * 
   */
  protected CarrierManagementBeanImpl() {
    super();
  }

  /**
   * Constructor
   */
  CarrierManagementBeanImpl(ManagementBeanFactory factory, Session session) {
    super(factory, session);
  }

  // Methods related with CarrierEntity management *****************************************
  
  public Carrier newCarrierInstance() throws DMException {
    return new CarrierEntity();
  }

  public Carrier newCarrierInstance(Country country, String carrierExternalId, String name) {
    return new CarrierEntity(country, carrierExternalId, name, Carrier.SERVER_AUTH_TYPE_DIGEST,
        Carrier.NOTIFICATION_TYPE_WAP_PUSH, Carrier.DEFAULT_NOTIFICATION_STATE_TIMEOUT,
        Carrier.DEFAULT_BOOTSTRAP_TIMEOUT, Carrier.DEFAULT_NOTIFICATION_MAX_RETRIES);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.CarrierBean#getCarrierByID(java.lang.String)
   */
  public Carrier getCarrierByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
        // session = HibernateSessionFactory.currentSession();
        Query query = session.createQuery("from CarrierEntity where ID='" + id + "'");
        List<Carrier> list = query.list();
  
        if (list.size() == 0) {
          return null;
        }
  
        if (list.size() == 1) {
          return list.get(0);
        } else {
          throw new DMException("Result is not unique, many countries have the same CarrierEntity ID: " + id);
        }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.CarrierBean#getCarrierByExternalID(java.lang.String)
   */
  public Carrier getCarrierByExternalID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from CarrierEntity where externalID='" + id + "'");
      List<Carrier> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return list.get(0);
      } else {
        throw new DMException("Result is not unique, many countries have the same CarrierEntity External ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.CarrierBean#add(com.npower.dm.hibernate.CarrierEntity)
   */
  public void update(Carrier carrier) throws DMException {
    if (carrier == null) {
      throw new NullPointerException("Could not add a null carrier into database.");
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      // Transaction tx = session.beginTransaction();
      session.save(carrier);
      // tx.commit();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.CarrierBean#getAllCountries()
   */
  public List<Carrier> getAllCarriers() throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from CarrierEntity order by ID");
      List<Carrier> list = query.list();

      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.CarrierBean#findCountries(java.lang.String)
   */
  public List<Carrier> findCarriers(String whereClause) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery(whereClause);
      List<Carrier> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.CarrierBean#delete(com.npower.dm.hibernate.CarrierEntity)
   */
  public void delete(Carrier carrier) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      // Transaction tx = session.beginTransaction();
      session.delete(carrier);
      // tx.commit();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.CarrierBean#findCarrierByPhoneNumberPolicy(java.lang.String)
   */
  public Carrier findCarrierByPhoneNumberPolicy(String phoneNumber) throws DMException {
    String clause = "from CarrierEntity where CARRIER_ID <> '" + CarrierBean.DEFAULT_CARRIER_ID + "' and length(PHONE_NUMBER_POLICY) > 0";
    List<Carrier> carriers = this.findCarriers(clause);
    for (Carrier carrier: carriers) {
        String policy = carrier.getPhoneNumberPolicy();
        if (StringUtils.isEmpty(policy)) {
           continue;
        }
        String[] patternStrings = policy.split(",");
        for (String patternString: patternStrings) {
            if (phoneNumber.trim().startsWith(patternString.trim())) {
               return carrier;
            }
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(phoneNumber);
            boolean matched = matcher.matches();
            if (matched) {
               return carrier;
            }
        }
    }
    return null;
  }
  
}
