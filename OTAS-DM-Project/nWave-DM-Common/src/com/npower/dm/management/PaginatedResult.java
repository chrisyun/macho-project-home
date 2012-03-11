/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/PaginatedResult.java,v 1.3 2008/08/26 07:59:02 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/08/26 07:59:02 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/08/26 07:59:02 $
 */
public interface PaginatedResult {

  // Publie methods
  // -------------------------------------------------------------------------------------
  /**
   * @return the criteria
   */
  //public abstract Criteria getCriteria();

  /*
   * (non-Javadoc)
   * 
   * @see org.displaytag.pagination.PaginatedList#getFullListSize()
   */
  public abstract int getFullListSize();

  /*
   * (non-Javadoc)
   * 
   * @see org.displaytag.pagination.PaginatedList#getList()
   */
  public abstract List<?> getList();

  /*
   * (non-Javadoc)
   * 
   * @see org.displaytag.pagination.PaginatedList#getObjectsPerPage()
   */
  public abstract int getObjectsPerPage();

  /*
   * (non-Javadoc)
   * 
   * @see org.displaytag.pagination.PaginatedList#getPageNumber()
   */
  public abstract int getPageNumber();

  /*
   * (non-Javadoc)
   * 
   * @see org.displaytag.pagination.PaginatedList#getSearchId()
   */
  public abstract String getSearchId();

  /*
   * (non-Javadoc)
   * 
   * @see org.displaytag.pagination.PaginatedList#getSortCriterion()
   */
  public abstract String getSortCriterion();

}