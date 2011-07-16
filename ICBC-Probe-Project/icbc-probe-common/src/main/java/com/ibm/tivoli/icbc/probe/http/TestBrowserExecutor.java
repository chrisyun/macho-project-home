/**
 * 
 */
package com.ibm.tivoli.icbc.probe.http;

import java.io.File;

import junit.framework.TestCase;

import com.ibm.tivoli.icbc.probe.http.BrowserExecutor.BrowserResult;

/**
 * @author zhaodonglu
 * 
 */
public class TestBrowserExecutor extends TestCase {

  /**
   * @param name
   */
  public TestBrowserExecutor(String name) {
    super(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testCase1() throws Exception {
    JxBrowserExecutorImpl executor = new JxBrowserExecutorImpl();
    BrowserResult result = executor.navigate("http://www.icbc.com.cn/abcdef");
    System.out.println(result);
    (new File(result.getImageFile())).delete();

    result = executor.navigate("http://www.google.com");
    System.out.println(result);
    (new File(result.getImageFile())).delete();

    //result = executor.navigate("http://aaa.cbcbcbcbcbcbcb.com/icbc/aaaaaaa");
    //System.out.println(result);
    //(new File(result.getImageFile())).delete();
  }

}
