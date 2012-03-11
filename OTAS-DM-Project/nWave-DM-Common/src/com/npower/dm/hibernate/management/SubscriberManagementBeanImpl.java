/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/SubscriberManagementBeanImpl.java,v 1.12 2008/09/19 10:38:04 zhaowx Exp $
 * $Revision: 1.12 $
 * $Date: 2008/09/19 10:38:04 $
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

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Subscriber;
import com.npower.dm.hibernate.entity.SubscriberEntity;
import com.npower.dm.management.BaseBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SubscriberBean;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.12 $ $Date: 2008/09/19 10:38:04 $
 */
public class SubscriberManagementBeanImpl extends AbstractBean implements BaseBean, SubscriberBean {

  /**
   * 
   */
  protected SubscriberManagementBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  SubscriberManagementBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }

  // Public Methods
  // ******************************************************************************

  /* (non-Javadoc)
   * @see com.npower.dm.management.SubscriberBean#newSubscriberInstance()
   */
  public Subscriber newSubscriberInstance() throws DMException {
    return new SubscriberEntity();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SubscriberBean#newSubscriberInstance(com.npower.dm.core.Carrier, java.lang.String, java.lang.String, java.lang.String)
   */
  public Subscriber newSubscriberInstance(Carrier carrier, String externalID, String phoneNumber, String password) throws DMException {
    return new SubscriberEntity(externalID, carrier, phoneNumber, password);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.SubscriberBean#update(com.npower.dm.hibernate.SubscriberEntity)
   */
  public void update(Subscriber subscriber) throws DMException {
    if (subscriber == null) {
       throw new NullPointerException("Could not add or update a null subscriber into DM inventory.");
    }
    Session session = this.getHibernateSession();
    try {
        ((SubscriberEntity)subscriber).setLastUpdatedTime(new Date());
        session.saveOrUpdate(subscriber);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.SubscriberBean#getSubscriberByID(java.lang.String)
   */
  public Subscriber getSubscriberByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }

    Session session = this.getHibernateSession();
    try {
      return (Subscriber) session.get(SubscriberEntity.class, new Long(id));
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
      if (session != null) {
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SubscriberBean#getSubscriberByExternalID(java.lang.String)
   */
  public Subscriber getSubscriberByExternalID(String externalID) throws DMException {
    if (externalID == null || externalID.trim().length() == 0) {
      return null;
    }

    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(Subscriber.class);
      criteria.add(Expression.eq("externalId", externalID));
      List<Subscriber> list = criteria.list();
      if (list.size() > 0) {
        return (Subscriber) list.get(0);
      } else {
        return null;
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SubscriberBean#getSubscriberByPhoneNumber(java.lang.String)
   */
  public Subscriber getSubscriberByPhoneNumber(String phoneNumber) throws DMException {
    String clause = "from SubscriberEntity where phoneNumber='" + phoneNumber + "'";
    List<Subscriber> list = this.findSubscriber(clause);
    if (list.size() > 0) {
       return (Subscriber)list.get(0);
    } else {
      return null;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.SubscriberBean#delete(com.npower.dm.hibernate.SubscriberEntity)
   */
  public void delete(Subscriber subscriber) throws DMException {
    Session session = this.getHibernateSession();
    try {
      session.delete(subscriber);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.SubscriberBean#findSubscriber(java.lang.String)
   */
  public List<Subscriber> findSubscriber(String whereClause) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery(whereClause);
      List<Subscriber> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }

  }

  public Subscriber getSubscriberByEmail(String email) throws DMException {
    String clause = "from SubscriberEntity where email='" + email + "'";
    List<Subscriber> list = this.findSubscriber(clause);
    if (list.size() > 0) {
       return (Subscriber)list.get(0);
    } else {
      return null;
    }
  }


}
