/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestSoftwareEvaluate.java,v 1.2 2008/09/02 07:14:04 chenlei Exp $
 * $Revision: 1.2 $
 * $Date: 2008/09/02 07:14:04 $
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.hibernate.entity.SoftwareEvaluateEntity;

/**
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/09/02 07:14:04 $
 */

public class TestSoftwareEvaluate extends TestCase {

  /**
   * @param name
   */
  public TestSoftwareEvaluate(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    this.cleanSimpleDate();
    super.setUp();
  }

  private void setupSimpleDate(String externalid, long grade, String userName, String userIP, String remark, String date) throws DMException, ParseException {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareEvaluateBean bean = factory.createSoftwareEvaluateBean();
    SoftwareEvaluateEntity softwareEvaluateEntity = bean.newSoftwareEvaluateInstance();
    SoftwareBean softwares = factory.createSoftwareBean();
    factory.beginTransaction();
    Software software = softwares.getSoftwareByExternalID(externalid);
    softwareEvaluateEntity.setSoftware(software);
    softwareEvaluateEntity.setRemark("this is Software Evaluate remark£¡");
    softwareEvaluateEntity.setGrade(grade);
    softwareEvaluateEntity.setUserName(userName);
    softwareEvaluateEntity.setUserIp(userIP);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date dateTime = df.parse(date);
    softwareEvaluateEntity.setCreatedTime(dateTime);
    bean.save(softwareEvaluateEntity);
    factory.commit();
  }
  
  private List<SoftwareEvaluateEntity> getAllOfSoftwareEvaluate() throws DMException {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SearchBean search = factory.createSearchBean();
    Criteria criteria = search.newCriteriaInstance(SoftwareEvaluateEntity.class);
    List<SoftwareEvaluateEntity> list = criteria.list();
    return list;
  }
  
  private void cleanSimpleDate() throws DMException {
    List<SoftwareEvaluateEntity> mylist = this.getAllOfSoftwareEvaluate();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareEvaluateBean bean = factory.createSoftwareEvaluateBean();
    factory.beginTransaction();
    for (int i = 0; i<mylist.size();i++) {
      SoftwareEvaluateEntity se = (SoftwareEvaluateEntity)mylist.get(i);
      bean.delete(se);
    }
    factory.commit();
  }
  
  public void setupInitDate() throws DMException, ParseException {
    setupSimpleDate("S1_IN_C_1", 1, "name1", "211.1.2.3", "this is Software Evaluate remark!", "2008-8-9");
    setupSimpleDate("S2_IN_C_1", 1, "name2", "211.1.2.3", "this is Software Evaluate remark!",  "2008-8-10");
    setupSimpleDate("S3_IN_C_1", 1, "name3", "211.1.2.3", "this is Software Evaluate remark!",  "2008-8-8");
    
    setupSimpleDate("S1_IN_C_1", 1, "name4", "211.1.2.3", "this is Software Evaluate remark!",  "2008-7-9");
    setupSimpleDate("S2_IN_C_1", 1, "name5", "211.1.2.3", "this is Software Evaluate remark!",  "2008-6-9");
    setupSimpleDate("S3_IN_C_1", 1, "name6", "211.1.2.3", "this is Software Evaluate remark!",  "2008-10-9");
    
    setupSimpleDate("S1_IN_C_1", 1, "name7", "211.1.2.3", "this is Software Evaluate remark!",  "2007-8-9");
    setupSimpleDate("S2_IN_C_1", 1, "name8", "211.1.2.3", "this is Software Evaluate remark!",  "2009-8-9");
    setupSimpleDate("S3_IN_C_1", 1, "name9", "211.1.2.3", "this is Software Evaluate remark!",  "2005-8-9");
    
    setupSimpleDate("S1_IN_C_1", 1, "name10", "211.1.2.3", "this is Software Evaluate remark!",  "2002-8-9");
    setupSimpleDate("S2_IN_C_1", 1, "name11", "211.1.2.3", "this is Software Evaluate remark!",  "2009-4-6");
    setupSimpleDate("S3_IN_C_1", 1, "name12", "211.1.2.3", "this is Software Evaluate remark!",  "2005-8-9");
  }
  
  public void testSoftwareEvaluat() throws DMException {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareEvaluateBean bean = factory.createSoftwareEvaluateBean();
    SoftwareEvaluateEntity softwareEvaluateEntity = bean.newSoftwareEvaluateInstance();
    SoftwareBean softwares = factory.createSoftwareBean();
    factory.beginTransaction();
    Software software = softwares.getSoftwareByID(3750150);
    softwareEvaluateEntity.setSoftware(software);
    softwareEvaluateEntity.setRemark( "this is Software Evaluate remark!");
    softwareEvaluateEntity.setGrade(new Long(0));
    softwareEvaluateEntity.setUserName("Rose");
    softwareEvaluateEntity.setUserIp("211.211.211.211");
    softwareEvaluateEntity.setCreatedTime(new Date());
    bean.save(softwareEvaluateEntity);
    factory.commit();
    //checking
    
    assertNotNull(softwareEvaluateEntity);
  }
  
  public void testGetAll() throws DMException, ParseException {
    this.setupInitDate();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareEvaluateBean bean = factory.createSoftwareEvaluateBean();
    SoftwareBean softwares = factory.createSoftwareBean();
    Software software = softwares.getSoftwareByID(3750150);
    Collection<SoftwareEvaluateEntity> softwareEvaluateEntity = bean.getAllOfSoftwareEvaluate(software);
    List<SoftwareEvaluateEntity> myList = new ArrayList<SoftwareEvaluateEntity>(softwareEvaluateEntity);
    
    //checking
    assertNotNull(myList);
    assertEquals("name8", myList.get(0).getUserName());
    assertEquals("name11", myList.get(1).getUserName());
    assertEquals("name2", myList.get(2).getUserName());
  }
  
  public void testGetByID() throws DMException, ParseException {
    this.setupInitDate();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareEvaluateBean bean = factory.createSoftwareEvaluateBean();
    SoftwareBean softwares = factory.createSoftwareBean();
    Software software = softwares.getSoftwareByID(3750150);
    Collection<SoftwareEvaluateEntity> softwareEvaluateEntity = bean.getAllOfSoftwareEvaluate(software);
    List<SoftwareEvaluateEntity> list = new ArrayList<SoftwareEvaluateEntity>(softwareEvaluateEntity);
    SoftwareEvaluateEntity se = bean.getSoftwareEvaluateByID(list.get(0).getId());
    
    //checking
    assertEquals("name8", se.getUserName());
  }
  
  public void testDelete() throws DMException, ParseException {
    this.setupInitDate();
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    SoftwareEvaluateBean bean = factory.createSoftwareEvaluateBean();
    SoftwareBean softwareBean = factory.createSoftwareBean();
    Software software = softwareBean.getSoftwareByExternalID("S2_IN_C_1");
    Collection<SoftwareEvaluateEntity> softwareEvaluateEntity = bean.getAllOfSoftwareEvaluate(software);
    List<SoftwareEvaluateEntity> list = new ArrayList<SoftwareEvaluateEntity>(softwareEvaluateEntity);
    factory.beginTransaction();
    for (int i = 0; i<list.size(); i++) {
      SoftwareEvaluateEntity se = (SoftwareEvaluateEntity)list.get(i);
      bean.delete(se);
    }
    factory.commit();
    
    //checking
    {
      ManagementBeanFactory factory1 = AllTests.getManagementBeanFactory();
      SoftwareEvaluateBean bean1 = factory1.createSoftwareEvaluateBean();
      SoftwareBean softwareBean1 = factory1.createSoftwareBean();
      Software software1 = softwareBean1.getSoftwareByExternalID("S2_IN_C_1");
      Collection<SoftwareEvaluateEntity> softwareEvaluate1 = bean1.getAllOfSoftwareEvaluate(software1);  
      List<SoftwareEvaluateEntity> list1 = new ArrayList<SoftwareEvaluateEntity>(softwareEvaluate1);
      assertEquals(0,list1.size());
    }
  }
  
  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

}
