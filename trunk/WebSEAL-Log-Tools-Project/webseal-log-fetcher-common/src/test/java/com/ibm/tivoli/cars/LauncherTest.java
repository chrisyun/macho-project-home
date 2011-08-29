package com.ibm.tivoli.cars;

import junit.framework.TestCase;

import com.ibm.tivoli.cars.fetcher.Launcher;

public class LauncherTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInternal() throws Exception {
    Launcher.main(new String[]{"-c", "Z:/SGM/InternalWebseal227/wblog.config.properties", "-d", "Z:/SGM/InternalWebseal227/logs", "request.log.*"});
  }

  public void testExternal() throws Exception {
    Launcher.main(new String[]{"-c", "Z:/SGM/ExternalWebseal149/wblog.config.properties", "-d", "Z:/SGM/ExternalWebseal149/logs", "request.log.*"});
  }
}
