/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/ServiceProviderBeanImpl.java,v 1.1 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/16 10:11:15 $
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

import com.npower.dm.core.DMException;
import com.npower.dm.core.ServiceProvider;
import com.npower.dm.hibernate.entity.ServiceProviderEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ServiceProviderBean;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/16 10:11:15 $
 */
public class ServiceProviderBeanImpl extends AbstractBean implements ServiceProviderBean {

  /**
   * Default Constructor
   * 
   */
  protected ServiceProviderBeanImpl() {
    super();
  }

  /**
   * Constructor
   */
  ServiceProviderBeanImpl(ManagementBeanFactory factory, Session session) {
    super(factory, session);
  }

  // Methods related with CarrierEntity management *****************************************
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.ServiceProviderBean#newServiceProviderInstance()
   */
  public ServiceProvider newServiceProviderInstance() throws DMException {
    return new ServiceProviderEntity();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ServiceProviderBean#newServiceProviderInstance(java.lang.String, java.lang.String)
   */
  public ServiceProvider newServiceProviderInstance(String externalId, String name) throws DMException {
    ServiceProvider instance = newServiceProviderInstance();
    instance.setName(name);
    instance.setExternalID(externalId);
    return instance;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ServiceProviderBean#getServiceProviderByID(java.lang.String)
   */
  public ServiceProvider getServiceProviderByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
        Query query = session.createQuery("from ServiceProviderEntity where ID='" + id + "'");
        List<ServiceProvider> list = query.list();
  
        if (list.size() == 0) {
          return null;
        }
  
        if (list.size() == 1) {
          return list.get(0);
        } else {
          throw new DMException("Result is not unique, many entries have the same ServiceProvider ID: " + id);
        }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ServiceProviderBean#getServiceProviderByExternalID(java.lang.String)
   */
  public ServiceProvider getServiceProviderByExternalID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      Query query = session.createQuery("from ServiceProviderEntity where externalID='" + id + "'");
      List<ServiceProvider> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return list.get(0);
      } else {
        throw new DMException("Result is not unique, many entries have the same ServiceProvider External ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ServiceProviderBean#update(com.npower.dm.core.ServiceProvider)
   */
  public void update(ServiceProvider sp) throws DMException {
    if (sp == null) {
      throw new NullPointerException("Could not add a null ServiceProvider into database.");
    }
    Session session = this.getHibernateSession();
    try {
      session.save(sp);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ServiceProviderBean#getAllServiceProviders()
   */
  public List<ServiceProvider> getAllServiceProviders() throws DMException {
    Session session = this.getHibernateSession();
    try {
      Query query = session.createQuery("from ServiceProviderEntity order by ID");
      List<ServiceProvider> list = query.list();

      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ServiceProviderBean#findServiceProviders(java.lang.String)
   */
  public List<ServiceProvider> findServiceProviders(String whereClause) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Query query = session.createQuery(whereClause);
      List<ServiceProvider> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ServiceProviderBean#delete(com.npower.dm.core.ServiceProvider)
   */
  public void delete(ServiceProvider sp) throws DMException {
    Session session = this.getHibernateSession();
    try {
      session.delete(sp);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

}
