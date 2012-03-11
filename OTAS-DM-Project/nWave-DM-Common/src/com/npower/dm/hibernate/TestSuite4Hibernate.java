/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/TestSuite4Hibernate.java,v 1.6 2007/05/16 07:17:57 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2007/05/16 07:17:57 $
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

package com.npower.dm.hibernate;


import junit.framework.Test;
import junit.framework.TestSuite;

import com.npower.dm.hibernate.management.TestCountry;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2007/05/16 07:17:57 $
 */
public class TestSuite4Hibernate {

  public static Test suite() {
    TestSuite suite = new TestSuite("TestSuite for com.npower.dm.hibernate");
    // $JUnit-BEGIN$
    suite.addTestSuite(TestCountry.class);
    // $JUnit-END$
    return suite;
  }

}
