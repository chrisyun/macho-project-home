/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dao/DaoFactory.java,v 1.1 2008/06/17 11:06:25 zhao Exp $
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
package com.npower.dm.dao;

import java.util.Properties;

import javax.security.auth.Subject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.dm.hibernate.management.DaoFactoryImpl;

/**
 * 
 * Factory of ManagementBean. All of instances of Management Bean will be
 * created by this factory.
 * 
 * Factory lifecycle must be manully handled. The lifecycle includes the
 * following states: 1. begineTransaction(); 2. commit() or rollback(); 3.
 * release()
 * 
 * more information, please sess the methods related with these states.
 * 
 * For Example:
 * 
 * DaoFactory factory = DaoFactory.newInstance();
 * CountryBean bean = factory.createCountryBean(); CountryEntity country = new
 * CountryEntity(....); factory.beginTransaction(); bean.add(country);
 * factory.commit(); factory.release();
 * 
 * 
 * 
 * @author Zhao DongLu
 * @param <T>
 * 
 */
public abstract class DaoFactory {

  private static Log   log                       = LogFactory.getLog(DaoFactory.class);
  
  /**
   * Default construct
   */
  protected DaoFactory() {
    super();
  }

  /**
   * Return Security Subject boudled with this factory.
   * @return the subject
   */
  public abstract Subject getSubject();

  /**
   * Set Security Subject boulded with this factory.
   * @param subject 
   *        the subject to set
   */
  public abstract void setSubject(Subject subject);

  // static methods -------------------------------------------------------------
  /**
   * Return an instance of ManagementBeanFactory.
   * This constructor will create an instance of HibernateSessionFactory using properties as Hibernate Properties.
   * 
   * @return instance of ManagementBeanFactory
   */
  public static DaoFactory newInstance(Properties properties) throws DMException {
    if (log.isDebugEnabled()) {
      log.debug("new a instance of ManagementBeanFactory");
    }

    DaoFactory instance = new DaoFactoryImpl(properties);
    return instance;
  }
  
  /**
   * Factory lifecycle method. Tell the instance of factory release all of
   * resource.
   * 
   * Please call this method to release all of resource after factory is end of
   * lifecyle.
   * 
   */
  protected abstract void releaseResource();

  // public methods -------------------------------------------------------------
  /**
   * Factory lifecycle method. Tell the instance of factory release all of
   * resource.
   * 
   * Please call this method to release all of resource after factory is end of
   * lifecyle.
   * 
   */
  public void release() {
    this.releaseResource();
  }

  /**
   * Return the active status of factory.
   * @return
   * @throws DMException
   */
  public abstract boolean isOpen() throws DMException;

  /**
   * Factory lifecycle method. Begin a transaction, start-up a checkpoint.
   * If last transaction has not be commited of rollbacked, this method will automaticlly 
   * commited all of transaction before the calling and begin a new checkpoint.
   * 
   */
  public abstract void beginTransaction() throws DMException;

  /**
   * Factory lifecycle method. Commit all of transaction.
   * If the transaction has not been created, will throw DMException.
   * 
   * Commit all of transaction before this method after laster checkpoint..
   * 
   */
  public abstract void commit();

  /**
   * Factory lifecycle method.
   * Factory lifecycle method. Rollback all of transaction before this methods after last checkpoint.
   * If the transaction has not been created, will throw DMException.
   * 
   */
  public abstract void rollback();
  
  /**
   * Create a instance of DAO, and bundle with database connection.
   * @param clazz
   * @return
   * @throws DMException
   */
  public abstract Object newDaoInstance(Class<?> clazz) throws DMException;

}
