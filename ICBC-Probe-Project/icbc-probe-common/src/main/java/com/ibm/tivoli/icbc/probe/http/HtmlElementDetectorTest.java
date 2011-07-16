package com.ibm.tivoli.icbc.probe.http;

import java.net.URL;

import junit.framework.TestCase;

import com.ibm.tivoli.icbc.result.http.PageElementResult;

public class HtmlElementDetectorTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testDetectStringString() throws Exception {
  }

  public void testDetectURLInt() throws Exception {
    HtmlElementDetectorImpl detector = new HtmlElementDetectorImpl();
    PageElementResult result = detector.detect(new URL("http://www.icbc.com.cn/icbc"), 5000);
    assertNotNull(result);
  }

}
