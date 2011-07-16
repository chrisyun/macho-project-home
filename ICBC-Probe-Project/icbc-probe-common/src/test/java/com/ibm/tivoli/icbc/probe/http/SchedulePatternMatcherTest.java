/**
 * 
 */
package com.ibm.tivoli.icbc.probe.http;

import junit.framework.TestCase;

/**
 * @author zhaodonglu
 *
 */
public class SchedulePatternMatcherTest extends TestCase {

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
  
  public void testCase() throws Exception {
    SchedulePatternMatcher m = new SchedulePatternMatcher("schedule:[*:00]");
    assertEquals("SchedulePatternMatcher [times=[00:00, 01:00, 02:00, 03:00, 04:00, 05:00, 06:00, 07:00, 08:00, 09:00, 10:00, 11:00, 12:00, 13:00, 14:00, 15:00, 16:00, 17:00, 18:00, 19:00, 20:00, 21:00, 22:00, 23:00]]", 
                   m.toString());
    
    m = new SchedulePatternMatcher("schedule:[9:00, 11:30, 14:00, 18:00, 23:00]");
    assertEquals("SchedulePatternMatcher [times=[11:30, 14:00, 18:00, 23:00, 9:00]]", m.toString());
    
    m = new SchedulePatternMatcher("schedule:[*:00, *:30]");
    assertEquals("SchedulePatternMatcher [times=[11:30, 14:00, 18:00, 23:00, 9:00]]", m.toString());
  }

}
