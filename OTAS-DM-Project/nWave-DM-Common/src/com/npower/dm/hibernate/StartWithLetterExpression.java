/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/StartWithLetterExpression.java,v 1.2 2008/09/16 01:55:05 zhaowx Exp $
 * $Revision: 1.2 $
 * $Date: 2008/09/16 01:55:05 $
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

package com.npower.dm.hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.TypedValue;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.2 $ $Date: 2008/09/16 01:55:05 $
 */

public class StartWithLetterExpression implements Criterion {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private final String      propertyName;

  private final String      value;

  public StartWithLetterExpression(String propertyName, String value) {
    this.propertyName = propertyName;
    this.value = value ;
  }

  /* (non-Javadoc)
   * @see org.hibernate.criterion.Criterion#getTypedValues(org.hibernate.Criteria, org.hibernate.criterion.CriteriaQuery)
   */
  public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
    return new TypedValue[] { criteriaQuery.getTypedValue(criteria, propertyName, value.toString()) };
  }

  /* (non-Javadoc)
   * @see org.hibernate.criterion.Criterion#toSqlString(org.hibernate.Criteria, org.hibernate.criterion.CriteriaQuery)
   */
  public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
    String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, propertyName);
    if (columns.length != 1)
      throw new HibernateException("ilike may only be used with single-column properties");
    return " substr(getzhongwen(" + columns[0] +"), 0, 1 )=?";
  }

  public String toString() {
    return " substr(getzhongwen(" + propertyName +"), 0, 1 )='"+value+"'";
  }

}
