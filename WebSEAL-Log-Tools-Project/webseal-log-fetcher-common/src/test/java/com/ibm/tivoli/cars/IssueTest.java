package com.ibm.tivoli.cars;

import junit.framework.TestCase;

import com.ibm.tivoli.cars.handler.ConsoleEventHandlerImpl;

public class IssueTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testTimeZone() throws Exception {
    String line = "10.200.35.148 - skwaid [14/ÎåÔÂ/2011:22:57:26 +0800] \"GET /universe/wps/themes/html/SGMUniverseSpecNav/heading_bg02.gif HTTP/1.1\" 304 0";
    LogParser parser = new LogParser();
    WebSEALRequestLogEvent evt = parser.parseWebSEALRequestLogEvent(line);
    ConsoleEventHandlerImpl handler = new ConsoleEventHandlerImpl();
    handler.handle(line, evt);
  }

}
