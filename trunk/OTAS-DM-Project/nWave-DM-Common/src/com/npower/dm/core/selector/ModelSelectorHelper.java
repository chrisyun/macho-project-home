/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/selector/ModelSelectorHelper.java,v 1.1 2008/09/05 02:12:42 zhao Exp $
 * $Revision: 1.1 $
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
package com.npower.dm.core.selector;

import java.util.Collection;

import org.hibernate.Session;

import com.npower.dm.core.ManagementBeanFactoryAware;
import com.npower.dm.core.ModelSelector;
import com.npower.dm.hibernate.HibernateSessionAware;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/05 02:12:42 $
 */
public class ModelSelectorHelper {

  /**
   * 
   */
  public ModelSelectorHelper() {
    super();
  }

  /**
   * Decorate Hibernate Session
   * @param selector
   */
  public static void decorate(ManagementBeanFactory factory, Session session, ModelSelector selector) {
    if (selector instanceof HibernateSessionAware) {
       ((HibernateSessionAware)selector).setHibernateSession(session);
    }
    if (selector instanceof ManagementBeanFactoryAware) {
       ((ManagementBeanFactoryAware)selector).setManagementBeanFactory(factory);
    }
  }

  /**
   * Decorate Hibernate Session
   * @param selector
   */
  public static void decorate(ManagementBeanFactory factory, Session session, Collection<ModelSelector> selectors) {
    for (ModelSelector selector: selectors) {
        decorate(factory, session, selector);
    }
  }

}
