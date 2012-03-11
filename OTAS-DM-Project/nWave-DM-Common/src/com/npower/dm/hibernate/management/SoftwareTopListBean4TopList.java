/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/SoftwareTopListBean4TopList.java,v 1.10 2008/09/10 02:46:48 chenlei Exp $
 * $Revision: 1.10 $
 * $Date: 2008/09/10 02:46:48 $
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
package com.npower.dm.hibernate.management;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.dm.core.DMException;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareTopNRate;
import com.npower.dm.hibernate.entity.VSoftwareTrackLogAll;
import com.npower.dm.hibernate.entity.VSoftwareTrackLogDaily;
import com.npower.dm.hibernate.entity.VSoftwareTrackLogMonthly;
import com.npower.dm.hibernate.entity.VSoftwareTrackLogWeekly;
import com.npower.dm.hibernate.entity.VSoftwareTrackLogYearly;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.SoftwareTrackingType;
import com.npower.dm.management.TimeRange;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.10 $ $Date: 2008/09/10 02:46:48 $
 */
public class SoftwareTopListBean4TopList extends AbstractBean {


  /**
   * 
   */
  public SoftwareTopListBean4TopList() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  public SoftwareTopListBean4TopList(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }
  
  // Private Methods ------------------------------------------------------------------------------------------

  private Criteria prepareCriteria(Class<?> clazz, SoftwareCategory category, SoftwareTrackingType trackingEvent, int maxRecords) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    SearchBean bean = factory.createSearchBean();
    Criteria criteria = bean.newCriteriaInstance(clazz);
    String trackingType = trackingEvent.toString();
    criteria.add(Expression.eq("trackingType", trackingType));
    criteria.setMaxResults(maxRecords);
    criteria.addOrder(Order.desc("count"));
    Criteria criteria4Software = criteria.createCriteria("software");
    Criteria criteria4Categories = criteria4Software.createCriteria("softwareCategoriesSet");
    if (category != null) {
      Set<SoftwareCategory> targetCategories = this.getTargetCategories(category);
      criteria4Categories.add(Expression.in("id.category", targetCategories));
    }
    return criteria;
  }

  private Set<SoftwareCategory> getTargetCategories(SoftwareCategory category) {
    Set<SoftwareCategory> result = new HashSet<SoftwareCategory>();
    getTargetCategories(result, category);
    return result;
  }

  private void getTargetCategories(Set<SoftwareCategory> result, SoftwareCategory category) {
    result.add(category);
    result.addAll(category.getChildren());
    for (SoftwareCategory c: category.getChildren()) {
        this.getTargetCategories(result, c);
    }
  }
  // Public methods -------------------------------------------------------------------------------------------
  

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.management.AAA#getDailyList(com.npower.dm.core.SoftwareCategory, com.npower.dm.management.SoftwareTrackingType, int, int, int)
   */
  public Collection<SoftwareTopNRate> getDailyList(SoftwareCategory category, 
                                           SoftwareTrackingType trackingEvent, 
                                           int year, 
                                           int dayOfYear,
                                           int maxRecords) throws DMException {
    Criteria criteria = prepareCriteria(VSoftwareTrackLogDaily.class, category, trackingEvent, maxRecords);
    String day = Integer.toString(dayOfYear);
    if (day.length() == 1) {
      day = "00" + day;
    }
    criteria.add(Expression.eq("dayOfYear", Integer.toString(year) + "-" + day));
    Collection<SoftwareTopNRate> items = (Collection<SoftwareTopNRate>)criteria.list();
    return items;

  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.management.AAA#getCurrentDailyList(com.npower.dm.core.SoftwareCategory, com.npower.dm.management.SoftwareTrackingType, int)
   */
  public Collection<SoftwareTopNRate> getCurrentDailyList(SoftwareCategory category, 
                                           SoftwareTrackingType trackingEvent,
                                           int maxRecords) throws DMException {
    Calendar gregoriandate = Calendar.getInstance();
    int year = gregoriandate.get(Calendar.YEAR);
    int dayOfyear = gregoriandate.get(Calendar.DAY_OF_YEAR);
    
    return getDailyList(category, trackingEvent, year, dayOfyear, maxRecords);
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.management.AAA#getWeeklyList(com.npower.dm.core.SoftwareCategory, com.npower.dm.management.SoftwareTrackingType, int, int, int)
   */
  public Collection<SoftwareTopNRate> getWeeklyList(SoftwareCategory category, 
                                            SoftwareTrackingType trackingEvent, 
                                            int year, 
                                            int weekOfYear,
                                            int maxRecords) throws DMException {
    Criteria criteria = prepareCriteria(VSoftwareTrackLogWeekly.class, category, trackingEvent, maxRecords);
    String weekresult = Integer.toString(weekOfYear);
    if (weekresult.length() == 1) {
      weekresult = "0" + weekresult;
    }
    criteria.add(Expression.eq("weekOfYear", Integer.toString(year)+"-"+ weekresult));
    Collection<SoftwareTopNRate> items = (Collection<SoftwareTopNRate>)criteria.list();
    return items;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.management.AAA#getCurrentWeeklyList(com.npower.dm.core.SoftwareCategory, com.npower.dm.management.SoftwareTrackingType, int)
   */
  public Collection<SoftwareTopNRate> getCurrentWeeklyList(SoftwareCategory category, 
                                            SoftwareTrackingType trackingEvent,
                                            int maxRecords) throws DMException {
    GregorianCalendar gregoriandate = new GregorianCalendar();
    int year = gregoriandate.get(Calendar.YEAR);
    int weekOfYear = gregoriandate.get(Calendar.WEEK_OF_YEAR);

    return getWeeklyList(category, trackingEvent, year, weekOfYear, maxRecords);
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.management.AAA#getMonthlyList(com.npower.dm.core.SoftwareCategory, com.npower.dm.management.SoftwareTrackingType, int, int, int)
   */
  public Collection<SoftwareTopNRate> getMonthlyList(SoftwareCategory category, 
                                             SoftwareTrackingType trackingEvent, 
                                             int year, 
                                             int monthOfYear,
                                             int maxRecords) throws DMException {
    Criteria criteria = prepareCriteria(VSoftwareTrackLogMonthly.class,category,trackingEvent,maxRecords);
    String month = Integer.toString(monthOfYear);
    if (month.length()==1) {
      month = "0" + month;
    }
    criteria.add(Expression.eq("monthOfYear", Integer.toString(year)+"-"+month));
    Collection<SoftwareTopNRate> items = (Collection<SoftwareTopNRate>)criteria.list();
    return items;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.management.AAA#getCurrentMonthlyList(com.npower.dm.core.SoftwareCategory, com.npower.dm.management.SoftwareTrackingType, int)
   */
  public Collection<SoftwareTopNRate> getCurrentMonthlyList(SoftwareCategory category, 
                                             SoftwareTrackingType trackingEvent,
                                             int maxRecords) throws DMException {
    GregorianCalendar gregoriandate = new GregorianCalendar();
    int year = gregoriandate.get(Calendar.YEAR);
    int monthOfYear = gregoriandate.get(Calendar.MONTH)+1;
    return getMonthlyList(category, trackingEvent, year, monthOfYear, maxRecords);
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.management.AAA#getYearlyList(com.npower.dm.core.SoftwareCategory, com.npower.dm.management.SoftwareTrackingType, int, int)
   */
  public Collection<SoftwareTopNRate> getYearlyList(SoftwareCategory category, 
                                            SoftwareTrackingType trackingEvent, 
                                            int year,
                                            int maxRecords) throws DMException {
    Criteria criteria = prepareCriteria(VSoftwareTrackLogYearly.class,category,trackingEvent,maxRecords);
    criteria.add(Expression.eq("year", Integer.toString(year)));
    Collection<SoftwareTopNRate> items = (Collection<SoftwareTopNRate>)criteria.list();
    return items;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.management.AAA#getCurrentYearlyList(com.npower.dm.core.SoftwareCategory, com.npower.dm.management.SoftwareTrackingType, int)
   */
  public Collection<SoftwareTopNRate> getCurrentYearlyList(SoftwareCategory category, 
                                            SoftwareTrackingType trackingEvent,
                                            int maxRecords) throws DMException {
    GregorianCalendar gregoriandate = new GregorianCalendar();
    int year = gregoriandate.get(Calendar.YEAR);
    return getYearlyList(category, trackingEvent, year, maxRecords);
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.management.AAA#getList(com.npower.dm.core.SoftwareCategory, com.npower.dm.management.SoftwareTrackingType, int)
   */
  public Collection<SoftwareTopNRate> getList(SoftwareCategory category, 
                                            SoftwareTrackingType trackingEvent, 
                                            int maxRecords) throws DMException {
    Criteria criteria = prepareCriteria(VSoftwareTrackLogAll.class,category,trackingEvent,maxRecords);
    Collection<SoftwareTopNRate> items = (Collection<SoftwareTopNRate>)criteria.list();
    return items;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.management.AAA#getTopList(com.npower.dm.core.SoftwareCategory, com.npower.dm.management.SoftwareTrackingType, com.npower.dm.management.TimeRange, int)
   */
  public Collection<SoftwareTopNRate> getTopList(SoftwareCategory category, SoftwareTrackingType trackingEvent,
      TimeRange timeRange, int maxRecords) throws DMException {
    if(timeRange.equals(TimeRange.DAILY)) {
      return getCurrentDailyList(category, trackingEvent, maxRecords);
    } else if (timeRange.equals(TimeRange.WEEKLY)) {
      return getCurrentWeeklyList(category, trackingEvent, maxRecords);
    } else if (timeRange.equals(TimeRange.MONTHLY)) {
      return getCurrentMonthlyList(category, trackingEvent, maxRecords);
    } else if (timeRange.equals(TimeRange.YEARLY)) {
      return getCurrentYearlyList(category, trackingEvent, maxRecords);
    } else if (timeRange.equals(TimeRange.ALL)) {
      return getList(category, trackingEvent, maxRecords);
    } else {
      return null;
    }
  }
}
