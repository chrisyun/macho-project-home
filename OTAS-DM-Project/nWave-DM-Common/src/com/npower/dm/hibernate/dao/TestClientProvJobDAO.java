/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/dao/TestClientProvJobDAO.java,v 1.1 2008/02/18 10:10:22 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/02/18 10:10:22 $
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
package com.npower.dm.hibernate.dao;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Transaction;

import com.npower.dm.hibernate.entity.ClientProvJobEntity;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/02/18 10:10:22 $
 */
public class TestClientProvJobDAO extends TestCase {

  public TestClientProvJobDAO(String name) {
    super(name);
  }

  /**
   * @throws java.lang.Exception
   */
  protected void setUp() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  protected void tearDown() throws Exception {
  }
  
  public void testAdd() throws Exception {
    ClientProvJobDAO dao = new ClientProvJobDAO();
    ClientProvJobEntity job = new ClientProvJobEntity();
    String name = "Job Name";
    job.setName(name);
    String description = "Job Description";
    job.setDescription(description);
    long maxRetries = 3;
    job.setMaxRetries(maxRetries);
    long maxDuration = 600;
    job.setMaxDuration(maxDuration);
    Date scheduleTime = new Date();
    job.setScheduleTime(scheduleTime);
    String type = "OTA";
    job.setType(type);
    String state = "Ready";
    job.setState(state);
    
    Transaction tx = dao.getSession().beginTransaction();
    dao.save(job );
    tx.commit();
    
    assertTrue(job.getId() > 0);
    assertEquals(name, job.getName());
    assertEquals(description, job.getDescription());
    assertEquals(maxRetries, job.getMaxRetries());
    assertEquals(maxDuration, job.getMaxDuration());
    assertEquals(scheduleTime, job.getScheduleTime());
    assertEquals(type, job.getType());
    assertEquals(state, job.getState());
  }

}
