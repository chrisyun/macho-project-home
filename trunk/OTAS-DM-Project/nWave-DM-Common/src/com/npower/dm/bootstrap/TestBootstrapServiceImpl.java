/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/bootstrap/TestBootstrapServiceImpl.java,v 1.2 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2008/06/16 10:11:15 $
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
package com.npower.dm.bootstrap;

import junit.framework.TestCase;

import com.npower.cp.OTAInventory;
import com.npower.cp.db.OTAInventoryImpl;
import com.npower.dm.AllTests;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.sms.client.NoopSmsSender;
import com.npower.sms.client.SmsSender;
import com.npower.wap.omacp.OMACPSecurityMethod;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestBootstrapServiceImpl extends TestCase {

  /**
   * @param name
   */
  public TestBootstrapServiceImpl(String name) {
    super(name);
  }
  
  private SmsSender getSmsSender() {
    return new NoopSmsSender();
  }
  
  private OTAInventory getOTAInventory() {
    return new OTAInventoryImpl();
  }

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

  /**
   * Test method for {@link com.npower.dm.bootstrap.BootstrapServiceImpl#bootstrap(java.lang.String, com.npower.wap.omacp.OMACPSecurityMethod, java.lang.String, long, int, long)}.
   */
  public final void testBootstrap1() {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link com.npower.dm.bootstrap.BootstrapServiceImpl#bootstrap(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.npower.wap.omacp.OMACPSecurityMethod, java.lang.String, long, int, long)}.
   */
  public final void testBootstrap2() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        BootstrapService service = new BootstrapServiceImpl();
        factory = AllTests.getManagementBeanFactory();
        service.setBeanFactory(factory);
        
        service.setSmsSender(this.getSmsSender());
        service.setOtaInventory(this.getOTAInventory());
        
        String serverPassword = "server_password";
        String serverNonce = null;
        OMACPSecurityMethod pinType = OMACPSecurityMethod.USERPIN;
        String msisdn = "13801234567";
        int maxRetry = 3;
        long scheduleTime = System.currentTimeMillis();
        long maxDuration = 10 * 1000;
        String clientNonce = null;
        String clientUsername = "username";
        String clientPassword = "password";
        String pin = "1234";
        service.bootstrap(msisdn, serverPassword, serverNonce, clientUsername, clientPassword, clientNonce, pinType, pin, scheduleTime, maxRetry, maxDuration);
    } catch (Exception e) {
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
