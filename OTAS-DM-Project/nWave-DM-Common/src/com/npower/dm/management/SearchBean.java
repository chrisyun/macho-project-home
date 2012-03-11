/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/SearchBean.java,v 1.5 2007/08/30 09:25:18 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2007/08/30 09:25:18 $
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
package com.npower.dm.management;

import java.util.List;

import org.hibernate.Criteria;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/08/30 09:25:18 $
 */
public interface SearchBean extends BaseBean {
  
  /**
   * Create a Criteria.
   * @param entityClass
   * @return
   * @throws DMException
   */
  public abstract Criteria newCriteriaInstance(Class<?> entityClass) throws DMException;
  
  /**
   * Find Entity by criteria
   * @param criteria
   * @return
   * @throws DMException
   */
  public abstract List find(Criteria criteria) throws DMException;
  
  /**
   * Return a paginated list for pagination
   * @param criteria
   * @param pageNumber
   *        Number of page
   * @param objectsPerPage
   *        number of objects per page
   * @return
   * @throws DMException
   */
  public abstract PaginatedResult getPaginatedList(Criteria criteria, int pageNumber, int objectsPerPage) throws DMException;

}