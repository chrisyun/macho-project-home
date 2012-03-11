/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestSoftwareTrackingBean.java,v 1.14 2008/08/27 03:53:33 chenlei Exp $
  * $Revision: 1.14 $
  * $Date: 2008/08/27 03:53:33 $
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.npower.dm.AllTests;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareTopNRate;
import com.npower.dm.hibernate.management.SoftwareTopListBean4TopList;




/**
 *          
 * @author Zhao DongLu
 * @version $Revision: 1.14 $ $Date: 2008/08/27 03:53:33 $
 */
public class TestSoftwareTrackingBean extends BaseTestSoftwareBean {

  private static final int NUMBER_OF_CHILDREN_PER_CATEGORY = 3;
  private static final int NUMBER_OF_MODELS = 2;
  private static final int NUMBER_OF_SOFTWARE_PER_CATEGORY = 10;

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    // 清除数据
    cleanSoftwareCategories();
    clearSoftwareVendors();
    clearModels();
    
    int numberOfChildrenPerCategory = NUMBER_OF_CHILDREN_PER_CATEGORY;
    int numberOfModel = NUMBER_OF_MODELS;
    int numberOfSoftwarePerCategory = NUMBER_OF_SOFTWARE_PER_CATEGORY;
    setupSampleData(numberOfChildrenPerCategory, numberOfSoftwarePerCategory, numberOfModel);
    
  }

  /**
   * <pre>
   * 测试SoftwareTrackingBean如下方法:
   * public void track(Software software, SoftwareTrackingEvent event)
   * 
   * 创建数据:
   * 增加SoftwareCategory: C_1的软件, 模拟下载次数:
   * ------------------------------------------------------------------
   *  软件名称        View次数
   *                2008-01-01     2008-02-10   2008-08-08    合计
   * ------------------------------------------------------------------
   * S1_IN_C_1        10              20         30           60
   * S2_IN_C_1        20              10          5           35
   * S3_IN_C_1        30              20         15           65
   * S4_IN_C_1        40              15         20           75
   * S5_IN_C_1        50               0          5           55
   * ------------------------------------------------------------------
   *   当天的数据
   * ------------------------------------------------------------------
   * 软件名称          view次数         合计
   *                 2008-8-20
   * S4_IN_C_1          75            150
   * S5_IN_C_1          55            110
   * S6_IN_C_1          102           102
   * S7_IN_C_1          206           206  
   * ------------------------------------------------------------------
   * </pre>
   * @throws Exception
   */
  private void setupSampleTrackData1() throws Exception {
    // Create Sample Data  动态得到时间
    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int month1 = now.get(Calendar.MONTH);
    int month2 = month1+1;
    int day1 = now.get(Calendar.DAY_OF_MONTH);
    int month = month2 + 3;
    int day = day1 + 10;
    //判断得到的月得年是否超过12和32如果超过，就等于第二年或下一个月的第一天。
    if (month > 12) {
      month = 1;
    }
    if (day  >32) {
      day = 1;
    }
    mockTrackingEventInDay("S1_IN_C_1", year+3, month,  day, SoftwareTrackingType.VIEW, 10);
    mockTrackingEventInDay("S1_IN_C_1", year+9, month, day, SoftwareTrackingType.VIEW, 20);
    mockTrackingEventInDay("S1_IN_C_1", year, month2,  day1, SoftwareTrackingType.VIEW, 30);

    mockTrackingEventInDay("S2_IN_C_1", year+3, month,  day, SoftwareTrackingType.VIEW, 20);
    mockTrackingEventInDay("S2_IN_C_1", year+9, month, day, SoftwareTrackingType.VIEW, 10);
    mockTrackingEventInDay("S2_IN_C_1", year, month2,  day1, SoftwareTrackingType.VIEW,  5);

    mockTrackingEventInDay("S3_IN_C_1", year+3, month,  day, SoftwareTrackingType.VIEW, 30);
    mockTrackingEventInDay("S3_IN_C_1", year+9, month, day, SoftwareTrackingType.VIEW, 20);
    mockTrackingEventInDay("S3_IN_C_1", year, month2,  day1, SoftwareTrackingType.VIEW, 15);

    mockTrackingEventInDay("S4_IN_C_1", year+3, month,  day, SoftwareTrackingType.VIEW, 40);
    mockTrackingEventInDay("S4_IN_C_1", year+9, month, day, SoftwareTrackingType.VIEW, 15);
    mockTrackingEventInDay("S4_IN_C_1", year, month2,  day1, SoftwareTrackingType.VIEW, 20);

    mockTrackingEventInDay("S5_IN_C_1", year+3, month,  day, SoftwareTrackingType.VIEW, 50);
    mockTrackingEventInDay("S5_IN_C_1", year+9, month, day, SoftwareTrackingType.VIEW,  0);
    mockTrackingEventInDay("S5_IN_C_1", year, month2,  day1, SoftwareTrackingType.VIEW,  5);
    
  }

  /**
   * <pre>
   * 测试SoftwareTrackingBean如下方法:
   * public void track(Software software, SoftwareTrackingEvent event)
   * 
   * 创建数据:
   * 增加SoftwareCategory: C_1_1的软件, 模拟下载次数:
   * ------------------------------------------------------------------
   *  软件名称          View次数
   *                 2008-01-01     2008-02-10   2008-08-08    合计
   * ------------------------------------------------------------------
   * S1_IN_C_1_1        10              20         30           60
   * S2_IN_C_1_1        20              10          5           35
   * S3_IN_C_1_1        30              20         15           65
   * S4_IN_C_1_1        40              15         20           75
   * S5_IN_C_1_1        50               0          5           55
   * ------------------------------------------------------------------

   * </pre>
   * @throws Exception
   */
  private void setupSampleTrackData2() throws Exception {
    
    //Create Complex Date
    
    // Create Sample Data  动态得到时间
    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int month1 = now.get(Calendar.MONTH);
    int month2 = month1+1;
    int day1 = now.get(Calendar.DAY_OF_MONTH);
    int month = month2 + 3;
    int day = day1 + 10;
    //判断得到的月得年是否超过12和32如果超过，就等于第二年或下一个月的第一天。
    if (month > 12) {
      month = 1;
    }
    if (day  >32) {
      day = 1;
    }
    mockTrackingEventInDay("S1_IN_C_1_1", year+6, month,  day, SoftwareTrackingType.DOWNLOAD, 10);
    mockTrackingEventInDay("S1_IN_C_1_1", year+8, month, day, SoftwareTrackingType.DOWNLOAD, 20);
    mockTrackingEventInDay("S1_IN_C_1_1", year, month2,  day1, SoftwareTrackingType.DOWNLOAD, 30);

    mockTrackingEventInDay("S2_IN_C_1_1", year+6, month,  day, SoftwareTrackingType.DOWNLOAD, 20);
    mockTrackingEventInDay("S2_IN_C_1_1", year+8, month, day, SoftwareTrackingType.DOWNLOAD, 10);
    mockTrackingEventInDay("S2_IN_C_1_1", year, month2,  day1, SoftwareTrackingType.DOWNLOAD,  5);

    mockTrackingEventInDay("S3_IN_C_1_1", year+6, month,  day, SoftwareTrackingType.DOWNLOAD, 30);
    mockTrackingEventInDay("S3_IN_C_1_1", year+8, month, day, SoftwareTrackingType.DOWNLOAD, 20);
    mockTrackingEventInDay("S3_IN_C_1_1", year, month2,  day1, SoftwareTrackingType.DOWNLOAD, 15);

    mockTrackingEventInDay("S4_IN_C_1_1", year+6, month,  day, SoftwareTrackingType.DOWNLOAD, 40);
    mockTrackingEventInDay("S4_IN_C_1_1", year+8, month, day, SoftwareTrackingType.DOWNLOAD, 15);
    mockTrackingEventInDay("S4_IN_C_1_1", year, month2,  day1, SoftwareTrackingType.DOWNLOAD, 20);

    mockTrackingEventInDay("S5_IN_C_1_1", year+6, month,  day, SoftwareTrackingType.DOWNLOAD, 50);
    mockTrackingEventInDay("S5_IN_C_1_1", year+8, month, day, SoftwareTrackingType.DOWNLOAD,  0);
    mockTrackingEventInDay("S5_IN_C_1_1", year, month2,  day1, SoftwareTrackingType.DOWNLOAD,  5);
  }

  private void setupSampleTrackData3() throws Exception {
    /*            当天数据                                */
    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int month1 = now.get(Calendar.MONTH);
    int month = month1+1;
    int day = now.get(Calendar.DAY_OF_MONTH);
    mockTrackingEventInDay("S4_IN_C_1", year, month,  day, SoftwareTrackingType.VIEW, 40);
    mockTrackingEventInDay("S4_IN_C_1", year, month, day, SoftwareTrackingType.VIEW, 15);
    mockTrackingEventInDay("S4_IN_C_1", year, month,  day, SoftwareTrackingType.VIEW, 20);

    mockTrackingEventInDay("S5_IN_C_1", year, month,  day, SoftwareTrackingType.VIEW, 50);
    mockTrackingEventInDay("S5_IN_C_1", year, month, day, SoftwareTrackingType.VIEW,  0);
    mockTrackingEventInDay("S5_IN_C_1", year, month,  day, SoftwareTrackingType.VIEW,  5);
    
    mockTrackingEventInDay("S6_IN_C_1", year, month,  day, SoftwareTrackingType.VIEW, 18);
    mockTrackingEventInDay("S6_IN_C_1", year, month, day, SoftwareTrackingType.VIEW, 26);
    mockTrackingEventInDay("S6_IN_C_1", year, month,  day, SoftwareTrackingType.VIEW, 58);

    mockTrackingEventInDay("S7_IN_C_1", year, month,  day, SoftwareTrackingType.VIEW, 96);
    mockTrackingEventInDay("S7_IN_C_1", year, month, day, SoftwareTrackingType.VIEW,  85);
    mockTrackingEventInDay("S7_IN_C_1", year, month,  day, SoftwareTrackingType.VIEW,  25);
    
  }
  /**
   * 为软件创建Tracking Log, 日志将创建在指定的日期内, 平均分布于当天的各个时段.
   * 本调用将自动提交事务.
   * @param softwareExtID
   * @param year
   * @param month
   * @param day
   * @param eventType
   *        事件类型
   * @param totalEvent
   *        总日志数量
   * @throws Exception
   */
  private void mockTrackingEventInDay(String softwareExtID, int year, int month, int day, SoftwareTrackingType eventType, int totalEvent) throws Exception {
    ManagementBeanFactory factory = null;
    try {
      factory = AllTests.getManagementBeanFactory();
      factory.beginTransaction();
      mockTrackingEventInDay(factory, softwareExtID, year, month, day, eventType, totalEvent);
      factory.commit();
    } catch (Exception e) {
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * @param factory
   * @param softwareExtID
   * @param year
   * @param month
   * @param day
   * @param eventType
   * @param totalEvent
   * @throws Exception
   */
  private void mockTrackingEventInDay(ManagementBeanFactory factory, String softwareExtID, int year, int month, int day, SoftwareTrackingType eventType, int totalEvent) throws Exception {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    
    long interval = 24 * 3600 * 1000 / (totalEvent + 1);
    for (int i = 0; i < totalEvent; i++) {
        Date timeStamp = new Date(calendar.getTime().getTime() + (i + 1) * interval);
        this.mockTrackingEventInDay(factory, softwareExtID, timeStamp, eventType, totalEvent);
    }
  }

  /**
   * @param factory
   * @param softwareExtID
   * @param timeStamp
   * @param eventType
   * @param totalEvent
   * @throws Exception
   */
  private void mockTrackingEventInDay(ManagementBeanFactory factory, String softwareExtID, Date timeStamp, SoftwareTrackingType eventType, int totalEvent) throws Exception {
    SoftwareTrackingBean trackingBean = factory.createSoftwareTrackingBean();
    SoftwareBean softwareBean = factory.createSoftwareBean();
    Software software = softwareBean.getSoftwareByExternalID(softwareExtID);
    SoftwareTrackingEvent event = new SoftwareTrackingEvent(eventType);
    event.setTimeStamp(timeStamp);
    trackingBean.track(software, event);
  }

  /**
   * @param softwareBean
   * @param name
   * @return
   * @throws DMException
   */
  private SoftwareCategory getCategoryByName(SoftwareBean softwareBean, String name) throws DMException {
    for (SoftwareCategory c: softwareBean.getAllOfCategories()) {
        if (c.getName().equals(name)) {
           return c;
        }
    }
    return null;
  }
  
  
  public void testTopList4GetDailyList() throws Exception{
    // Create Sample Data
    this.setupSampleTrackData1();

    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int day = now.get(Calendar.DAY_OF_YEAR);
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);

    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    Collection<SoftwareTopNRate> softwares = topBean.getDailyList(category, SoftwareTrackingType.VIEW, year, day, 3);
    List<SoftwareTopNRate> result = new ArrayList<SoftwareTopNRate>(softwares);

    //checking software
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("S1_IN_C_1", result.get(0).getSoftware().getName());
    assertEquals("S4_IN_C_1", result.get(1).getSoftware().getName());
    assertEquals("S3_IN_C_1", result.get(2).getSoftware().getName());

    //checking count
    assertEquals(30, result.get(0).getCount());
    assertEquals(20, result.get(1).getCount());
    assertEquals(15, result.get(2).getCount());
  }

  public void testTopList4GetWeeklyList() throws Exception {
    //Create Sample Data
    this.setupSampleTrackData1();
    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int week = now.get(Calendar.WEEK_OF_YEAR);
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);
    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    Collection<SoftwareTopNRate> softwares = topBean.getWeeklyList(category, SoftwareTrackingType.VIEW, year, week, 3);
    List<SoftwareTopNRate> result = new ArrayList<SoftwareTopNRate>(softwares);
    
    //checking software
    assertEquals(3, result.size());
    assertEquals("S1_IN_C_1", result.get(0).getSoftware().getName());
    assertEquals("S4_IN_C_1", result.get(1).getSoftware().getName());
    assertEquals("S3_IN_C_1", result.get(2).getSoftware().getName());
    assertNotNull(result);
    
    //checking count
    assertEquals(30,result.get(0).getCount());
    assertEquals(20,result.get(1).getCount());
    assertEquals(15,result.get(2).getCount());
  }
  
  public void testTopList4GetMonthlyList() throws Exception{
    //Create Sample Data
    this.setupSampleTrackData1();
    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int month = now.get(Calendar.MONTH);
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);
    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    Collection<SoftwareTopNRate> softwares = topBean.getMonthlyList(category, SoftwareTrackingType.VIEW, year, month+1, 3);
    List<SoftwareTopNRate> result = new ArrayList<SoftwareTopNRate>(softwares);
    
    //checking software
    assertEquals(3, result.size());
    assertEquals("S1_IN_C_1", result.get(0).getSoftware().getName());
    assertEquals("S4_IN_C_1", result.get(1).getSoftware().getName());
    assertEquals("S3_IN_C_1", result.get(2).getSoftware().getName());
    assertNotNull(result);

    //checking count
    assertEquals(30,result.get(0).getCount());
    assertEquals(20,result.get(1).getCount());
    assertEquals(15,result.get(2).getCount());
  }
  
  public void testTopList4GetYearlyList() throws Exception{
    //Create Sample Data
    this.setupSampleTrackData1();
    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);
    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    Collection<SoftwareTopNRate> softwares = topBean.getYearlyList(category, SoftwareTrackingType.VIEW, year, 3);
    List<SoftwareTopNRate> result = new ArrayList<SoftwareTopNRate>(softwares);
    
    //checking software
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("S1_IN_C_1", result.get(0).getSoftware().getName());
    assertEquals("S4_IN_C_1", result.get(1).getSoftware().getName());
    assertEquals("S3_IN_C_1", result.get(2).getSoftware().getName());
    
    //checking count
    assertEquals(30,result.get(0).getCount());
    assertEquals(20,result.get(1).getCount());
    assertEquals(15,result.get(2).getCount());
  }
  
  public void testTopList4GetCurrentDailyList() throws Exception{
 // Create Sample Data
    this.setupSampleTrackData3();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);
    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    Collection<SoftwareTopNRate> softwares = topBean.getCurrentDailyList(category, SoftwareTrackingType.VIEW,3);
    List<SoftwareTopNRate> result = new ArrayList<SoftwareTopNRate>(softwares);
    
    //checking software
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("S7_IN_C_1", result.get(0).getSoftware().getName());
    assertEquals("S6_IN_C_1", result.get(1).getSoftware().getName());
    assertEquals("S4_IN_C_1", result.get(2).getSoftware().getName());
    
    //checking count
    assertEquals(206,result.get(0).getCount());
    assertEquals(102,result.get(1).getCount());
    assertEquals(75,result.get(2).getCount());
    
  }
  
  public void testTopList4GetCurrentWeeklyList() throws Exception{
    //Create Sample Data
    this.setupSampleTrackData3();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);
    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    Collection<SoftwareTopNRate> softwares = topBean.getCurrentWeeklyList(category, SoftwareTrackingType.VIEW,3);
    List<SoftwareTopNRate> result = new ArrayList<SoftwareTopNRate>(softwares);
    
    //checking software
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("S7_IN_C_1", result.get(0).getSoftware().getName());
    assertEquals("S6_IN_C_1", result.get(1).getSoftware().getName());
    assertEquals("S4_IN_C_1", result.get(2).getSoftware().getName());
    
    //checking count
    assertEquals(206,result.get(0).getCount());
    assertEquals(102,result.get(1).getCount());
    assertEquals(75,result.get(2).getCount());
  }
  
  public void testTopList4GetCurrentMonthlyList() throws Exception{
    //Create Sample Data
    this.setupSampleTrackData3();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);
    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    Collection<SoftwareTopNRate> softwares = topBean.getCurrentMonthlyList(category, SoftwareTrackingType.VIEW,3);
    List<SoftwareTopNRate> result = new ArrayList<SoftwareTopNRate>(softwares);
   
    //checking software
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("S7_IN_C_1", result.get(0).getSoftware().getName());
    assertEquals("S6_IN_C_1", result.get(1).getSoftware().getName());
    assertEquals("S4_IN_C_1", result.get(2).getSoftware().getName());
    
    //checking count
    assertEquals(206,result.get(0).getCount());
    assertEquals(102,result.get(1).getCount());
    assertEquals(75,result.get(2).getCount());
  }
  
  public void testTopList4GetCurrentYearlyList() throws Exception{
    //Create Sample Data
    this.setupSampleTrackData3();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);
    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    Collection<SoftwareTopNRate> softwares = topBean.getCurrentYearlyList(category, SoftwareTrackingType.VIEW,3);
    List<SoftwareTopNRate> result = new ArrayList<SoftwareTopNRate>(softwares);
   
    //checking software
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("S7_IN_C_1", result.get(0).getSoftware().getName());
    assertEquals("S6_IN_C_1", result.get(1).getSoftware().getName());
    assertEquals("S4_IN_C_1", result.get(2).getSoftware().getName());
    
    //checking count
    assertEquals(206,result.get(0).getCount());
    assertEquals(102,result.get(1).getCount());
    assertEquals(75,result.get(2).getCount());
  }
  
  public void testTopList4GetList() throws Exception{
    //Create Complex Data
    this.setupSampleTrackData2();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);
    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1_1");
    assertNotNull(category);
    Collection<SoftwareTopNRate> softwares = topBean.getList(category, SoftwareTrackingType.DOWNLOAD,3);
    List<SoftwareTopNRate> result = new ArrayList<SoftwareTopNRate>(softwares);
    
    //checking software
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("S4_IN_C_1_1", result.get(0).getSoftware().getName());
    assertEquals("S3_IN_C_1_1", result.get(1).getSoftware().getName());
    assertEquals("S1_IN_C_1_1", result.get(2).getSoftware().getName());
    
    //checking count
    assertEquals(75,result.get(0).getCount());
    assertEquals(65,result.get(1).getCount());
    assertEquals(60,result.get(2).getCount());
  }
  
  public void testTopList4GetList2() throws Exception{
    //Create Complex Data
    this.setupSampleTrackData2();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);
    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    Collection<SoftwareTopNRate> softwares = topBean.getList(category, SoftwareTrackingType.DOWNLOAD,3);
    List<SoftwareTopNRate> result = new ArrayList<SoftwareTopNRate>(softwares);
    
    //checking software
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("S4_IN_C_1_1", result.get(0).getSoftware().getName());
    assertEquals("S3_IN_C_1_1", result.get(1).getSoftware().getName());
    assertEquals("S1_IN_C_1_1", result.get(2).getSoftware().getName());
    
    //checking count
    assertEquals(75,result.get(0).getCount());
    assertEquals(65,result.get(1).getCount());
    assertEquals(60,result.get(2).getCount());
  }
  
  public void testTopList() throws Exception {
    //Create Sample Data
    this.setupSampleTrackData1();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);

    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    List<SoftwareTopNRate> result = (List<SoftwareTopNRate>) topBean.getTopList(category, SoftwareTrackingType.VIEW,TimeRange.DAILY,3);
    
    //checking software
    assertEquals("S1_IN_C_1", result.get(0).getSoftware().getName());
    assertEquals("S4_IN_C_1", result.get(1).getSoftware().getName());
    assertEquals("S3_IN_C_1", result.get(2).getSoftware().getName());
    assertNotNull(result);
    assertEquals(3, result.size());

    //checking count
    assertEquals(30,result.get(0).getCount());
    assertEquals(20,result.get(1).getCount());
    assertEquals(15,result.get(2).getCount());
  }

  public void testGetRateFromSoftwareEntity() throws Exception{
    // Create Sample Data
    this.setupSampleTrackData1();

    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareTopListBean4TopList topBean = (SoftwareTopListBean4TopList)factory.createBean(SoftwareTopListBean4TopList.class);

    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int day = now.get(Calendar.DAY_OF_YEAR);
    SoftwareBean  softwareBean = factory.createSoftwareBean();
    SoftwareCategory category = getCategoryByName(softwareBean, "C_1");
    assertNotNull(category);
    Collection<SoftwareTopNRate> softwares = topBean.getDailyList(category, SoftwareTrackingType.VIEW, year, day, 3);
    List<SoftwareTopNRate> result = new ArrayList<SoftwareTopNRate>(softwares);

    //checking software
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("S1_IN_C_1", result.get(0).getSoftware().getName());
    assertEquals(60, result.get(0).getSoftware().getRatingView());
    assertEquals(0, result.get(0).getSoftware().getRatingDownload());
    assertEquals(0, result.get(0).getSoftware().getRatingInstall());
    assertEquals("S4_IN_C_1", result.get(1).getSoftware().getName());
    assertEquals(150, result.get(1).getSoftware().getRatingView());
    assertEquals(0, result.get(1).getSoftware().getRatingDownload());
    assertEquals(0, result.get(1).getSoftware().getRatingInstall());
    assertEquals("S3_IN_C_1", result.get(2).getSoftware().getName());
    assertEquals(65, result.get(2).getSoftware().getRatingView());
    assertEquals(0, result.get(2).getSoftware().getRatingDownload());
    assertEquals(0, result.get(2).getSoftware().getRatingInstall());

  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  

}
