/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/selector/AbstractModelSelector.java,v 1.4 2008/09/18 10:22:45 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/09/18 10:22:45 $
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
package com.npower.dm.core.selector;

import java.util.Set;

import org.hibernate.Session;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ManagementBeanFactoryAware;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelSelector;
import com.npower.dm.hibernate.HibernateSessionAware;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/09/18 10:22:45 $
 */
public abstract class AbstractModelSelector implements ModelSelector, HibernateSessionAware, ManagementBeanFactoryAware {
  
  transient private Session hibernateSession;
  transient private ManagementBeanFactory managementBeanFactory;

  /**
   * 
   */
  protected AbstractModelSelector() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.HibernateSessionAware#setHibernateSession(org.hibernate.Session)
   */
  public void setHibernateSession(Session session) {
    this.hibernateSession = session;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.HibernateSessionAware#getHibernateSession()
   */
  public Session getHibernateSession() {
    return this.hibernateSession;
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
   * @see com.npower.dm.core.ModelSelector#getModels()
   */
  public abstract Set<Model> getModels() throws DMException;

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSelector#isSelected(com.npower.dm.core.Model)
   */
  public abstract boolean isSelected(Model model) throws DMException;

}
