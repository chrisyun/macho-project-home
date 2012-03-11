/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/DaoFactoryImpl.java,v 1.1 2008/06/17 11:06:25 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/17 11:06:25 $
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

import java.util.Properties;

import javax.security.auth.Subject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.npower.dm.core.DMException;
import com.npower.dm.dao.DaoFactory;
import com.npower.dm.hibernate.dao.IBaseHibernateDAO;

/**
 * Implements the ManagementBeanFactory based on the Hibernate
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/17 11:06:25 $
 */
public final class DaoFactoryImpl extends DaoFactory {

  private static Log  log         = LogFactory.getLog(DaoFactoryImpl.class);

  /**
   * Trnasaction Handler boundled with the factory instance.
   */
  private Transaction transaction = null;

  /**
   * Hibernate Session handler boudled with the factory instance.
   */
  private Session     session     = null;
  
  /**
   * Security subject boundled with the factory instance.
   */
  private Subject subject = null;
  
  /**
   * If true, will trace transaction
   */
  private boolean traceTransaction = false;
  
  /**
   * Counstructor for the ManagementBeanFactory
   * This constructor will create an instance of HibernateSessionFactory.
   * The initialize process will load hibernate properties by the following steps:
   * 1. Load from ${otas.dm.home}/config/otasdm/otasdm.properties"
   * 2. If not found, load hibernate.properties file from classpath
   * 3. If not found, will use System.getProperties()
   */
  public DaoFactoryImpl() throws DMException {
    super();
    initilize(null);
  }

  /**
   * Counstructor for the ManagementBeanFactory
   * This constructor will create an instance of HibernateSessionFactory using properties as Hibernate Properties.
   */
  public DaoFactoryImpl(Properties properties) throws DMException {
    super();
    initilize(properties);
  }

  /**
   * 
   */
  private void initilize(Properties properties) {
    if (log.isDebugEnabled()) {
       log.debug("Initializing ManagementBeanFactory, and initializing HibernateSessionFactory");
    }
    HibernateSessionFactory sessionFactory = null;
    if (properties == null) {
       sessionFactory = HibernateSessionFactory.newInstance();
    } else {
      sessionFactory = HibernateSessionFactory.newInstance(properties);
    }
    this.session = sessionFactory.currentSession();
    if (log.isDebugEnabled()) {
       log.debug("Initializing ManagementBeanFactory, the current Hibernate session boundled with factory is " + this.session);
    }
    
  }

  // private methods.
  // *****************************************************************

  /**
   * Package-friendly method. Getter of HibernateSession related with the
   * factory instance.
   * 
   * @return org.hibernate.Session return the session related with the Factory.
   */
  private synchronized Session getHibernateSession() {
    return this.session;
  }

  // public methods
  // ****************************************************************************
  /**
   * Implements Transaction Management based on the Hibernate.
   * 
   * @see com.npower.dm.management.ManagementBeanFactory#beginTransaction()
   */
  public synchronized void beginTransaction() {
    if (this.transaction == null) {
      this.transaction = this.getHibernateSession().beginTransaction();
      if (log.isDebugEnabled()) {
         log.debug("Begin transaction ...");
      }
    } else {
      // Transaction already open
      if (this.transaction.isActive()) {
         this.transaction.commit();
         if (log.isDebugEnabled()) {
           log.debug("Transaction is actived, auto committed.");
        }
      }
      this.transaction = this.getHibernateSession().beginTransaction();
      if (log.isDebugEnabled()) {
         log.debug("Begin transaction ...");
      }
      if (traceTransaction) {
         log.error("Another transaction has not been commited or rollbacked, could not begin a new Transaction.");
         throw new RuntimeException("Another transaction has not been commited or rollbacked, could not begin a new Transaction.");
      }
    }
  }

  /**
   * Implements commit() method based on the Hibernate.
   * 
   * @see com.npower.dm.management.ManagementBeanFactory#commit()
   */
  public synchronized void commit() {
    if (this.transaction != null) {
      if (log.isDebugEnabled()) {
        log.debug("Commit transaction ...");
      }
      this.transaction.commit();
      this.transaction = null;
    } else {
      log.warn("The current transaction has not been created, could not commit the Transaction.");
      //throw new RuntimeException("The current transaction has not been created, could not commit the Transaction.");
    }
  }

  /**
   * Implements releaseResource() methods. Release the session of hibernate.
   */
  public synchronized void releaseResource() {
    if (log.isDebugEnabled()) {
      log.debug("Release ManagementBeanFactoryImpl ...");
    }
    if (this.session != null && this.session.isOpen()) {
       if (log.isDebugEnabled()) {
          log.debug("Close HibernateSession ...");
       }
       this.session.close();
    } else {
      log.warn("Could not release the ManagementBeanFactory, error in state of the current HibernateSession boundled with the ManagementBeanFactory");
      //throw new RuntimeException("Could not release the ManagementBeanFactory, error in state of the current HibernateSession boundled with the ManagementBeanFactory");
    }
  }

  /**
   * implements rollback() methods based on Hibernate.
   * 
   * @see com.npower.dm.management.ManagementBeanFactory#rollback()
   */
  public synchronized void rollback() {
    if (this.transaction != null) {
       if (log.isDebugEnabled()) {
          log.debug("Rollback transaction ...");
       }
       this.transaction.rollback();
       this.transaction = null;
    } else {
      log.warn("The current transaction has not been created, could not rollback the Transaction.");
      //throw new RuntimeException("The current transaction has not been created, could not rollback the Transaction.");
    }
  }

  /**
   * @return the subject
   */
  public Subject getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#isOpen()
   */
  @Override
  public boolean isOpen() throws DMException {
    if (this.session == null) {
       return false;
    } else {
      return this.session.isOpen();
    }
  }

  @Override
  public Object newDaoInstance(Class<?> clazz) throws DMException {
    try {
      Object instance = clazz.newInstance();
      if (instance instanceof IBaseHibernateDAO) {
         ((IBaseHibernateDAO)instance).setSession(this.getHibernateSession());
      }
      return instance;
    } catch (InstantiationException e) {
      throw new DMException("Could not create an instance for class: " + clazz.getName(), e);
    } catch (IllegalAccessException e) {
      throw new DMException("Failre to create an instance for class: " + clazz.getName(), e);
    }
  }

}
