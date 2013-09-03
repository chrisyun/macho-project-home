/**
 * 
 */
package com.ibm.tivoli.w7.util;

import junit.framework.TestCase;

/**
 * @author zhaodonglu
 *
 */
public class XMLEscapeTest extends TestCase {

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
   * Test method for {@link com.ibm.tivoli.w7.util.XMLEscape#escape(java.lang.String)}.
   */
  public void testEscape() {
    assertEquals(null, XMLEscape.escape(null));
    assertEquals("a&quot;b&apos;c&lt;d&gt;e&amp;f", XMLEscape.escape("a\"b'c<d>e&f"));
  }
  
  public void testFormat() {
    assertEquals("abcdef", String.format("%.6s", "abcdefghijklmn"));
    assertEquals("我爱北京", String.format("%.4s", "我爱北京天安门"));
  }

}
