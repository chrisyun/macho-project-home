package com.npower.unicom.sync;

import java.util.Date;

import junit.framework.TestCase;

public class TestOnTime extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testCase1() throws Exception {
    Date d = new Date(108, 10, 20, 16, 54, 00);
    assertTrue(AbstractExportDaemonPlugIn.onTime(d, "*-*-* 16:54:00"));
    
    d = new Date(108, 10, 20, 16, 50, 00);
    assertTrue(AbstractExportDaemonPlugIn.onTime(d, "*-*-* *:*0:00,*-*-* *:*5:00"));
    for (int i = 1; i < 59; i++) {
        d = new Date(108, 10, 20, 16, 50, i);
        assertFalse(AbstractExportDaemonPlugIn.onTime(d, "*-*-* *:*0:00,*-*-* *:*5:00"));
    }
  }

}
