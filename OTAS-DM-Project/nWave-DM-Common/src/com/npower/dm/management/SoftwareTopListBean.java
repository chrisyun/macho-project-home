/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/SoftwareTopListBean.java,v 1.8 2008/08/20 11:10:47 chenlei Exp $
 * $Revision: 1.8 $
 * $Date: 2008/08/20 11:10:47 $
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
package com.npower.dm.management;

import java.util.Collection;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareTopNRate;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2008/08/20 11:10:47 $
 */
public interface SoftwareTopListBean {
  
  /**
   * 返回一个软件列表, 按照 trackingEvent所指明的排序因素. 所有软件必须属于指定的category.
   * @param category
   *        为null时, 软件范围为所有类型
   * @param trackingEvent
   * @return
   * @throws DMException 
   */
  public abstract Collection<SoftwareTopNRate> getTopList(SoftwareCategory category, 
                                                  SoftwareTrackingType trackingEvent, 
                                                  TimeRange timeRange,
                                                  int maxRecords) throws DMException;

  /**
   * @param category
   * @param trackingEvent
   * @param year
   *        4-digit year
   * @param dayOfYear
   *        Day of year (1-366).
   * @param maxRecords
   * @return
   * @throws DMException 
   */
  public abstract Collection<SoftwareTopNRate> getDailyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int year, int dayOfYear, int maxRecords) throws DMException;

  /**
   * Return current top list.
   * @param category
   * @param trackingEvent
   * @param maxRecords
   * @return
   * @throws DMException 
   */
  public abstract Collection<SoftwareTopNRate> getCurrentDailyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int maxRecords) throws DMException;

  /**
   * @param category
   * @param trackingEvent
   * @param year
   *        4-digit year
   * @param weekOfYear
   *        Week of year (1-53) where week 1 starts on the first day of the year and continues to the seventh day of the year.
   * @param maxRecords
   * @return
   * @throws DMException 
   */
  public abstract Collection<SoftwareTopNRate> getWeeklyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int year, int weekOfYear, int maxRecords) throws DMException;

  /**
   * Return current top list.
   * @param category
   * @param trackingEvent
   * @param maxRecords
   * @return
   * @throws DMException 
   */
  public abstract Collection<SoftwareTopNRate> getCurrentWeeklyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int maxRecords) throws DMException;

  /**
   * @param category
   * @param trackingEvent
   * @param year
   *        4-digit year
   * @param monthOfYear
   *        Month (1-12; January = 1).
   * @param maxRecords
   * @return
   * @throws DMException 
   */
  public abstract Collection<SoftwareTopNRate> getMonthlyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int year, int monthOfYear, int maxRecords) throws DMException;

  /**
   * Return current top list.
   * @param category
   * @param trackingEvent
   * @param maxRecords
   * @return
   * @throws DMException 
   */
  public abstract Collection<SoftwareTopNRate> getCurrentMonthlyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int maxRecords) throws DMException;

  /**
   * @param category
   * @param trackingEvent
   * @param year
   *        4-digit year
   * @param maxRecords
   * @return
   * @throws DMException 
   */
  public abstract Collection<SoftwareTopNRate> getYearlyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int year, int maxRecords) throws DMException;

  /**
   * Return current top list.
   * @param category
   * @param trackingEvent
   * @param maxRecords
   * @return
   * @throws DMException 
   */
  public abstract Collection<SoftwareTopNRate> getCurrentYearlyList(SoftwareCategory category,
      SoftwareTrackingType trackingEvent, int maxRecords) throws DMException;

  /**
   * @param category
   * @param trackingEvent
   * @param maxRecords
   * @return
   * @throws DMException 
   */
  public abstract Collection<SoftwareTopNRate> getList(SoftwareCategory category, SoftwareTrackingType trackingEvent,
      int maxRecords) throws DMException;

  /**
   * <pre>
   * Set a Priority number for a recommended software.
   * 如果priority < 0, 软件将从RecommendedList中删除
   * 如果priority已经被使用, 当前设置的软件将占用此priority, 其它软件将自动延后次序. 例如:
   * 
   * 已经存在如下次序设定:
   * Priority    Software_Name
   *    0         Google Map
   *    1         MSN
   *    2         Putty
   *    3         Google Search
   *    
   *  如果设定 mTune的Priority 为2, 设定后的结果为:
   * Priority   Software_Name
   *    0         Google Map
   *    1         MSN    
   *    2         mTune
   *    3         Putty
   *    4         Google Search
   *  
   * </pre>
   * @param software
   *        Target software
   * @param category
   *        If category is null, priority will be set for root category.
   * @param priority
   *        priority, 0 is first.
   */
  public abstract void setRecommendedPriority(Software software, 
                                              SoftwareCategory category, 
                                              int priority) throws DMException;
  
  /**
   * 返回指定软件在指定分类下的推荐优先级.
   * @param software
   * @param category
   * @return
   */
  public abstract int getRecommendedPriority(Software software, SoftwareCategory category) throws DMException;
  
  /**
   * 返回指定分类下的推荐软件列表.
   * 如果指定分类下没有定义推荐软件列表, 返回空集合.
   * @param category
   * @return
   */
  public abstract Collection<Software> getRecommendedSoftwares(SoftwareCategory category) throws DMException;
}
