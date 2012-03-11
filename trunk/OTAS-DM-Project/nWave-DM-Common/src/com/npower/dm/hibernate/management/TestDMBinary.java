/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/TestDMBinary.java,v 1.1 2006/12/06 11:24:44 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2006/12/06 11:24:44 $
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
package com.npower.dm.hibernate.management;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.npower.dm.hibernate.entity.DMBinary;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2006/12/06 11:24:44 $
 */
public class TestDMBinary extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testDMBinary() throws Exception {
    DMBinary bin = new DMBinary(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05});
    bin.setCreationDate(new Date());
    bin.setLastUpdatedBy("aaa");
    bin.setLastUpdatedTime(new Date());
    bin.setStatus("aaa");
    
    HibernateSessionFactory factory = HibernateSessionFactory.newInstance();
    Session session = null;
    try {
        session = factory.currentSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(bin);
        tx.commit();
        long id = bin.getID();
        assertTrue(id > 0);
        DMBinary b = (DMBinary)session.load(DMBinary.class, new Long(id));
        assertNotNull(b);
    } catch (Exception e) {
      if (session != null) {
         session.close();
      }
    }
    
  }

}
