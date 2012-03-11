/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/interceptor/EntityAwareInterceptor.java,v 1.2 2008/09/05 02:12:42 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/09/05 02:12:42 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.hibernate.interceptor;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import com.npower.dm.core.ManagementBeanFactoryAware;
import com.npower.dm.hibernate.HibernateSessionAware;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/09/05 02:12:42 $
 */
public class EntityAwareInterceptor implements Interceptor, HibernateSessionAware, ManagementBeanFactoryAware {

  private Session hibernateSession = null;
  private ManagementBeanFactory managementBeanFactory;

  /**
   * 
   */
  public EntityAwareInterceptor() {
    super();
  }

  /**
   * @param hibernateSession
   */
  public EntityAwareInterceptor(ManagementBeanFactory factory, Session hibernateSession) {
    super();
    this.hibernateSession = hibernateSession;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.HibernateSessionAware#getHibernateSession()
   */
  public Session getHibernateSession() {
    return hibernateSession;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.HibernateSessionAware#setHibernateSession(org.hibernate.Session)
   */
  public void setHibernateSession(Session hibernateSession) {
    this.hibernateSession = hibernateSession;
  }

  /**
   * @return the managementBeanFactory
   */
  public ManagementBeanFactory getManagementBeanFactory() {
    return managementBeanFactory;
  }

  /**
   * @param managementBeanFactory the managementBeanFactory to set
   */
  public void setManagementBeanFactory(ManagementBeanFactory managementBeanFactory) {
    this.managementBeanFactory = managementBeanFactory;
  }

  /* (non-Javadoc)
   * @see org.hibernate.Interceptor#afterTransactionBegin(org.hibernate.Transaction)
   */
  public void afterTransactionBegin(Transaction tx) {
  }

  /* (non-Javadoc)
   * @see org.hibernate.Interceptor#afterTransactionCompletion(org.hibernate.Transaction)
   */
  public void afterTransactionCompletion(Transaction tx) {
  }

  /* (non-Javadoc)
   * @see org.hibernate.Interceptor#beforeTransactionCompletion(org.hibernate.Transaction)
   */
  public void beforeTransactionCompletion(Transaction tx) {
  }

  public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
      String[] propertyNames, Type[] types) {
    return null;
  }

  public Object getEntity(String entityName, Serializable id) throws CallbackException {
    return null;
  }

  public String getEntityName(Object object) throws CallbackException {
    return null;
  }

  public Object instantiate(String entityName, EntityMode entityMode, Serializable id) throws CallbackException {
    return null;
  }

  public Boolean isTransient(Object entity) {
    return null;
  }

  public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
  }

  public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {
  }

  public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
  }

  public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
      throws CallbackException {
  }

  public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
      String[] propertyNames, Type[] types) throws CallbackException {
    return false;
  }

  /* (non-Javadoc)
   * @see org.hibernate.Interceptor#onLoad(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
   */
  public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
      throws CallbackException {
    if (entity != null && entity instanceof HibernateSessionAware) {
       ((HibernateSessionAware)entity).setHibernateSession(this.getHibernateSession());
    }
    if (entity != null && entity instanceof ManagementBeanFactoryAware) {
      ((ManagementBeanFactoryAware)entity).setManagementBeanFactory(this.getManagementBeanFactory());
    }
    return false;
  }

  public String onPrepareStatement(String sql) {
    return sql;
  }

  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
      throws CallbackException {
    return false;
  }

  public void postFlush(Iterator entities) throws CallbackException {
  }

  public void preFlush(Iterator entities) throws CallbackException {
  }

}
