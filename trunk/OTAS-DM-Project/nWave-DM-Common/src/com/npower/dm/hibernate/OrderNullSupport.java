/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/OrderNullSupport.java,v 1.2 2008/08/26 08:45:21 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/26 08:45:21 $
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
import org.hibernate.criterion.Order;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/08/26 08:45:21 $
 */
public class OrderNullSupport extends Order {
  
  private boolean nullLast = false;
  private String propertyName = null;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * @param propertyName
   * @param ascending
   */
  protected OrderNullSupport(String propertyName, boolean ascending) {
    super(propertyName, ascending);
    this.propertyName = propertyName;
  }

  public String toString() {
      return super.toString();
  }
  
  /**
   * Render the SQL fragment
   *
   */
  public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) 
  throws HibernateException {
    String result = super.toSqlString(criteria, criteriaQuery);
    if (this.nullLast) {
       return result + " nulls last";
    } else {
      return result;
    }
  }

  public Order ignoreCase() {
    return super.ignoreCase();
  }

  /**
   * Ascending order
   *
   * @param propertyName
   * @return Order
   */
  public static Order asc(String propertyName) {
      return new OrderNullSupport(propertyName, true);
  }

  /**
   * Descending order
   *
   * @param propertyName
   * @return Order
   */
  public static Order desc(String propertyName) {
      return new OrderNullSupport(propertyName, false);
  }

  /**
   * <pre>
   * Descending order.
   * By default, Null value is higher than other no-null value.
   * See also: http://opensource.atlassian.com/projects/hibernate/browse/HHH-465.
   * 
   * This method will order nulls to last position.
   * </pre>
   * @param propertyName
   * @return Order
   */
  public static Order descNullLast(String propertyName) {
    OrderNullSupport order = new OrderNullSupport(propertyName, false);
    order.nullLast = true;
    return order;
  }

  /**
   * @return the propertyName
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * @param propertyName the propertyName to set
   */
  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

}
