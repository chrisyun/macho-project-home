/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/server/synclet/TestHttpHeaderPhoneNumberParser.java,v 1.3 2006/12/13 07:16:41 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2006/12/13 07:16:41 $
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
package com.npower.dm.server.synclet;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2006/12/13 07:16:41 $
 */
public class TestHttpHeaderPhoneNumberParser extends TestCase {
  
  

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

  public void testParserNull() throws Exception {
    SimpleHttpRequest request = new SimpleHttpRequest();
    HttpHeaderPhoneNumberDetector parser = new HttpHeaderPhoneNumberDetector();
    assertNull(parser.getPhoneNumber(null, request, null));
    
  }

  public void testParserNotNull() throws Exception {
    SimpleHttpRequest request = new SimpleHttpRequest();
    request.setHeader("x-up-calling-line-id", "8629241062097");
    
    assertEquals("8629241062097", request.getHeader("x-up-calling-line-id"));
    
    HttpHeaderPhoneNumberDetector parser = new HttpHeaderPhoneNumberDetector();
    parser.setHeaderName("x-up-calling-line-id");
    
    assertEquals("8629241062097", parser.getPhoneNumber(null, request, null));
    
  }
  
  /**
   * 提取第一个[]中数值,并且清除+860|860
   * @throws Exception
   */
  public void testParserPattern1() throws Exception {
    
    HttpHeaderPhoneNumberDetector parser = new HttpHeaderPhoneNumberDetector();
    String headerName = "x-up-calling-line-id";
    parser.setHeaderName(headerName);
    parser.setPattern("(?!8)(?!6)(?!0)((?!\\[)([0-9]*)(?=\\]))");
    
    {
      String value = "Nokia 7710 10.1.1.2 [13240025585] [1234567890]";
      SimpleHttpRequest request = new SimpleHttpRequest();
      request.setHeader(headerName, value);
      assertEquals(value, request.getHeader(headerName));
      assertEquals("13240025585", parser.getPhoneNumber(null, request, null));
    }
    {
      String value = "Nokia 7710 10.2.2.3 [+86013240025585] [1234567890]";
      SimpleHttpRequest request = new SimpleHttpRequest();
      request.setHeader(headerName, value);
      assertEquals(value, request.getHeader(headerName));
      assertEquals("13240025585", parser.getPhoneNumber(null, request, null));
    }
    {
      String value = "Nokia 7710 10.2.2.3 [86013240025585] [1234567890]";
      SimpleHttpRequest request = new SimpleHttpRequest();
      request.setHeader(headerName, value);
      assertEquals(value, request.getHeader(headerName));
      assertEquals("13240025585", parser.getPhoneNumber(null, request, null));
    }
  }

  /**
   * 清除+86|86|0
   * @throws Exception
   */
  public void testParserPattern2() throws Exception {
    
    HttpHeaderPhoneNumberDetector parser = new HttpHeaderPhoneNumberDetector();
    String headerName = "x-up-calling-line-id";
    parser.setHeaderName(headerName);
    parser.setPattern("(?!\\+)(?!8)(?!6)(?!0)\\d*");
    
    {
      String value = "+8613801356920";
      SimpleHttpRequest request = new SimpleHttpRequest();
      request.setHeader(headerName, value);
      assertEquals(value, request.getHeader(headerName));
      assertEquals("13801356920", parser.getPhoneNumber(null, request, null));
    }
    {
      String value = "8613801356920";
      SimpleHttpRequest request = new SimpleHttpRequest();
      request.setHeader(headerName, value);
      assertEquals(value, request.getHeader(headerName));
      assertEquals("13801356920", parser.getPhoneNumber(null, request, null));
    }
    {
      String value = "013801356920";
      SimpleHttpRequest request = new SimpleHttpRequest();
      request.setHeader(headerName, value);
      assertEquals(value, request.getHeader(headerName));
      assertEquals("13801356920", parser.getPhoneNumber(null, request, null));
    }
    {
      String value = "13801356920";
      SimpleHttpRequest request = new SimpleHttpRequest();
      request.setHeader(headerName, value);
      assertEquals(value, request.getHeader(headerName));
      assertEquals("13801356920", parser.getPhoneNumber(null, request, null));
    }
  }

}
