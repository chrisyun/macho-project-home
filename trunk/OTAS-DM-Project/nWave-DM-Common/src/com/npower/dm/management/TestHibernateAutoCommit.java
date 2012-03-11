/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestHibernateAutoCommit.java,v 1.4 2008/06/16 10:11:14 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/06/16 10:11:14 $
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

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Country;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:14 $
 */
public class TestHibernateAutoCommit extends TestCase {

  public void testAutoCommit() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        CountryBean bean = factory.createCountryBean();
        
        Country country = bean.getCountryByISOCode("AA");
        if (country != null) {
           factory.beginTransaction();
           bean.delete(country);
           factory.commit();
        }
        country = bean.newCountryInstance("AA", "99", "OLD Name", "1", true, true, true);
        factory.beginTransaction();
        bean.update(country);
        factory.commit();
        
        
        country = bean.getCountryByISOCode("AA");
        assertEquals("99", country.getCountryCode());
        factory.beginTransaction();
        country.setName("NEW Name");
        //bean.update(country);
        factory.commit();
        
        country = bean.getCountryByISOCode("AA");
        Country country1 = bean.getCountryByISOCode("AA");
        assertNotNull(country1);
        assertEquals("NEW Name", country1.getName());
    } catch (RuntimeException e) {
      throw e;
    } finally {
      factory.release();
    }
    
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestHibernateAutoCommit.class);
  }

}
