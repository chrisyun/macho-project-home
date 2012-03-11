/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/SoftwareTopListBeanImpl.java,v 1.9 2008/08/20 11:11:22 chenlei Exp $
 * $Revision: 1.9 $
 * $Date: 2008/08/20 11:11:22 $
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

import java.util.Collection;

import org.hibernate.Session;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareTopNRate;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareTopListBean;
import com.npower.dm.management.SoftwareTrackingType;
import com.npower.dm.management.TimeRange;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/08/20 11:11:22 $
 */
public class SoftwareTopListBeanImpl extends AbstractBean implements SoftwareTopListBean {

  /**
   * 
   */
  protected SoftwareTopListBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  public SoftwareTopListBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareTopListBean#getRecommendedPriority(com.npower.dm.core.Software, com.npower.dm.core.SoftwareCategory)
   */
  public int getRecommendedPriority(Software software, SoftwareCategory category) throws DMException {
    SoftwareTopListBean4Recommend helper = new SoftwareTopListBean4Recommend(this.getManagementBeanFactory(), this.getHibernateSession());
    return helper.getRecommendedPriority(software, category);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareTopListBean#getRecommendedSoftwares(com.npower.dm.core.SoftwareCategory)
   */
  public Collection<Software> getRecommendedSoftwares(SoftwareCategory category) throws DMException {
    SoftwareTopListBean4Recommend helper = new SoftwareTopListBean4Recommend(this.getManagementBeanFactory(), this.getHibernateSession());
    return helper.getRecommendedSoftwares(category);
    
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareTopListBean#setRecommendedPriority(com.npower.dm.core.Software, com.npower.dm.core.SoftwareCategory, int)
   */
  public void setRecommendedPriority(Software software, SoftwareCategory category, int priority) throws DMException {
    SoftwareTopListBean4Recommend helper = new SoftwareTopListBean4Recommend(this.getManagementBeanFactory(), this.getHibernateSession());
    helper.setRecommendedPriority(software, category, priority);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareTopListBean#getTopList(com.npower.dm.core.SoftwareCategory, com.npower.dm.management.SoftwareTrackingType, com.npower.dm.management.TimeRange, int)
   */
  public Collection<SoftwareTopNRate> getTopList(SoftwareCategory category, SoftwareTrackingType trackingEvent,
      TimeRange timeRange, int maxRecords) throws DMException {
    SoftwareTopListBean4TopList helper = new SoftwareTopListBean4TopList(this.getManagementBeanFactory(),this.getHibernateSession());
    return helper.getTopList(category, trackingEvent, timeRange, maxRecords);
  }

  public Collection<SoftwareTopNRate> getCurrentDailyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int maxRecords) throws DMException {
    SoftwareTopListBean4TopList helper = new SoftwareTopListBean4TopList(this.getManagementBeanFactory(),this.getHibernateSession());
    return helper.getCurrentDailyList(category, trackingEvent, maxRecords);
  }

  public Collection<SoftwareTopNRate> getCurrentMonthlyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int maxRecords) throws DMException {
    SoftwareTopListBean4TopList helper = new SoftwareTopListBean4TopList(this.getManagementBeanFactory(),this.getHibernateSession());
    return helper.getCurrentMonthlyList(category, trackingEvent, maxRecords);
  }

  public Collection<SoftwareTopNRate> getCurrentWeeklyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int maxRecords) throws DMException {
    SoftwareTopListBean4TopList helper = new SoftwareTopListBean4TopList(this.getManagementBeanFactory(),this.getHibernateSession());
    return helper.getCurrentWeeklyList(category, trackingEvent, maxRecords);
  }

  public Collection<SoftwareTopNRate> getCurrentYearlyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int maxRecords) throws DMException {
    SoftwareTopListBean4TopList helper = new SoftwareTopListBean4TopList(this.getManagementBeanFactory(),this.getHibernateSession());
    return helper.getCurrentYearlyList(category, trackingEvent, maxRecords);
  }

  public Collection<SoftwareTopNRate> getDailyList(SoftwareCategory category, SoftwareTrackingType trackingEvent,
      int year, int dayOfYear, int maxRecords) throws DMException {
    SoftwareTopListBean4TopList helper = new SoftwareTopListBean4TopList(this.getManagementBeanFactory(),this.getHibernateSession());
    return helper.getDailyList(category, trackingEvent, year, dayOfYear, maxRecords);
  }

  public Collection<SoftwareTopNRate> getList(SoftwareCategory category, SoftwareTrackingType trackingEvent,
      int maxRecords) throws DMException {
    SoftwareTopListBean4TopList helper = new SoftwareTopListBean4TopList(this.getManagementBeanFactory(),this.getHibernateSession());
    return helper.getList(category, trackingEvent, maxRecords);
  }

  public Collection<SoftwareTopNRate> getMonthlyList(SoftwareCategory category, SoftwareTrackingType trackingEvent,
      int year, int monthOfYear, int maxRecords) throws DMException {
    SoftwareTopListBean4TopList helper = new SoftwareTopListBean4TopList(this.getManagementBeanFactory(),this.getHibernateSession());
    return helper.getMonthlyList(category, trackingEvent, year, monthOfYear, maxRecords);
  }

  public Collection<SoftwareTopNRate> getWeeklyList(SoftwareCategory category, SoftwareTrackingType trackingEvent,
      int year, int weekOfYear, int maxRecords) throws DMException {
    SoftwareTopListBean4TopList helper = new SoftwareTopListBean4TopList(this.getManagementBeanFactory(),this.getHibernateSession());
    return helper.getWeeklyList(category, trackingEvent, year, weekOfYear, maxRecords);
  }

  public Collection<SoftwareTopNRate> getYearlyList(SoftwareCategory category, SoftwareTrackingType trackingEvent,
      int year, int maxRecords) throws DMException {
    SoftwareTopListBean4TopList helper = new SoftwareTopListBean4TopList(this.getManagementBeanFactory(),this.getHibernateSession());
    return helper.getYearlyList(category, trackingEvent, year, maxRecords);
  }

}
