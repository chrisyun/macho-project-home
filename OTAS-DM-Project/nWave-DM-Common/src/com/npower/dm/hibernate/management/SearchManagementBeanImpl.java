/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/SearchManagementBeanImpl.java,v 1.7 2008/08/27 04:25:35 zhao Exp $
  * $Revision: 1.7 $
  * $Date: 2008/08/27 04:25:35 $
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

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.SearchBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/08/27 04:25:35 $
 */
public class SearchManagementBeanImpl extends AbstractBean implements SearchBean {

  protected SearchManagementBeanImpl() {
    super();
  }

  SearchManagementBeanImpl(ManagementBeanFactory factory, Session session) {
    super(factory, session);
  }
  
  /** Interface methods ***********************************************************************/
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.SearchManagementBean#find(org.hibernate.Criteria)
   */
  public List find(Criteria criteria) throws DMException {
    try {
        return criteria.list();
  } catch (HibernateException ex) {
    throw new DMException(ex);
  }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SearchManagementBean#newCriteriaInstance(java.lang.Class)
   */
  public Criteria newCriteriaInstance(Class<?> entityClass) throws DMException {
    try {
        Session session = this.getHibernateSession();
        Criteria criteria = session.createCriteria(entityClass);
        return criteria;
  } catch (HibernateException ex) {
    throw new DMException(ex);
  }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SearchBean#getPaginatedList(org.hibernate.Criteria, int, int)
   */
  public PaginatedResult getPaginatedList(Criteria criteria, int pageNumber, int objectsPerPage) throws DMException {
    PaginatedResultImpl4Criteria list = new PaginatedResultImpl4Criteria(criteria, pageNumber, objectsPerPage);
    return list;
  }
  
}
