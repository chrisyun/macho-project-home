/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/AbstractBean.java,v 1.4 2008/08/18 09:42:54 chenlei Exp $
 * $Revision: 1.4 $
 * $Date: 2008/08/18 09:42:54 $
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

import org.hibernate.Session;

import com.npower.dm.management.BaseBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/08/18 09:42:54 $
 */
public abstract class AbstractBean implements BaseBean {

  private Session hibernateSession = null;

  private ManagementBeanFactory managementBeanFactory = null;

  /**
   * 
   */
  protected AbstractBean() {
    super();
  }

  /**
   * 
   */
  public AbstractBean(ManagementBeanFactory factory, Session hsession) {
    super();
    this.managementBeanFactory = factory;
    this.hibernateSession = hsession;
  }

  /**
   * Return the HibernateSession
   * 
   * @return
   */
  public Session getHibernateSession() {
    return this.hibernateSession;
  }

  /**
   * Implements getMangementBeanFactory()
   * 
   * @return
   */
  public ManagementBeanFactory getManagementBeanFactory() {
    return this.managementBeanFactory;
  }

  /**
   * @param hibernateSession
   */
  public void setHibernateSession(Session hibernateSession) {
    this.hibernateSession = hibernateSession;
  }

  /**
   * @param managementBeanFactory
   */
  public void setManagementBeanFactory(ManagementBeanFactory managementBeanFactory) {
    this.managementBeanFactory = managementBeanFactory;
  }

}
