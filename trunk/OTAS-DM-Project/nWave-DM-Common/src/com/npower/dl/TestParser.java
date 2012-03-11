/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dl/TestParser.java,v 1.4 2007/03/12 14:47:47 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2007/03/12 14:47:47 $
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
package com.npower.dl;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/03/12 14:47:47 $
 */
public class TestParser extends TestCase {
  
  static {
    System.setProperty("otas.dm.home", "D:/OTASDM");
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
  
  public void testParserDownloadID1() throws Exception {
    String uri = "http://127.0.0.1:8080/omadm/dl/download/367263871";
    String downloadID = DownloadFactory.parserDownloadID(uri);
    assertEquals("367263871", downloadID);
  }

  public void testParserOperation1() throws Exception {
    String uri = "http://127.0.0.1:8080/omadm/dl/download/367263871";
    String operation = DownloadFactory.parserOperation(uri);
    assertEquals("download", operation);
  }

  public void testParserDownloadID2() throws Exception {
    String uri = "http://127.0.0.1:8080/omadm/dl/download/367263871.swupd";
    String downloadID = DownloadFactory.parserDownloadID(uri);
    assertEquals("367263871", downloadID);
  }

  public void testParserOperation3() throws Exception {
    String uri = "http://127.0.0.1:8080/omadm/dl/download/367263871.swupd";
    String operation = DownloadFactory.parserOperation(uri);
    assertEquals("download", operation);
  }

  public void testMimeTypeHelper() throws Exception {
    String man = "nokia";
    assertEquals("application/vnd.nokia.swupd.dp2", FirmwareMimeTypeHelper.getMimeType(man, null));

    man = "Nokia";
    assertEquals("application/vnd.nokia.swupd.dp2", FirmwareMimeTypeHelper.getMimeType(man, null));

    man = " Nokia ";
    assertEquals("application/vnd.nokia.swupd.dp2", FirmwareMimeTypeHelper.getMimeType(man, null));
  
    man = " NOKIA ";
    assertEquals("application/vnd.nokia.swupd.dp2", FirmwareMimeTypeHelper.getMimeType(man, null));

    man = "  ";
    assertEquals("application/octet-stream", FirmwareMimeTypeHelper.getMimeType(man, null));
  }

  public void testFileSuffix() throws Exception {
    String man = "nokia";
    assertEquals(".swupd", FirmwareMimeTypeHelper.getFileSuffix(man, null));

    man = "Nokia";
    assertEquals(".swupd", FirmwareMimeTypeHelper.getFileSuffix(man, null));

    man = " Nokia ";
    assertEquals(".swupd", FirmwareMimeTypeHelper.getFileSuffix(man, null));
  
    man = " NOKIA ";
    assertEquals(".swupd", FirmwareMimeTypeHelper.getFileSuffix(man, null));

    man = "  ";
    assertEquals(".bin", FirmwareMimeTypeHelper.getFileSuffix(man, null));
  }
  
  public void testMimiTypeHelper() throws Exception {
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType(null, null);
      assertEquals("application/octet-stream", mimeType.getMimeType());
      assertEquals(".bin", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("", null);
      assertEquals("application/octet-stream", mimeType.getMimeType());
      assertEquals(".bin", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("", "");
      assertEquals("application/octet-stream", mimeType.getMimeType());
      assertEquals(".bin", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("default", null);
      assertEquals("application/octet-stream", mimeType.getMimeType());
      assertEquals(".bin", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("Default", null);
      assertEquals("application/octet-stream", mimeType.getMimeType());
      assertEquals(".bin", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("Default", null);
      assertEquals("application/octet-stream", mimeType.getMimeType());
      assertEquals(".bin", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("Default", "Default");
      assertEquals("application/octet-stream", mimeType.getMimeType());
      assertEquals(".bin", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("Default", "Unknown");
      assertEquals("application/octet-stream", mimeType.getMimeType());
      assertEquals(".bin", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("Nokia", null);
      assertEquals("application/vnd.nokia.swupd.dp2", mimeType.getMimeType());
      assertEquals(".swupd", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("Nokia", "Unknown");
      assertEquals("application/vnd.nokia.swupd.dp2", mimeType.getMimeType());
      assertEquals(".swupd", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("Test", null);
      assertEquals("application/test", mimeType.getMimeType());
      assertEquals(".test", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("Test", "Test1");
      assertEquals("application/test1", mimeType.getMimeType());
      assertEquals(".test1", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("Test", "Test2");
      assertEquals("application/test2", mimeType.getMimeType());
      assertEquals(".test2", mimeType.getSuffix());
    }
    {
      DownloadMimeType mimeType = FirmwareMimeTypeHelper.findMimeType("Test", "Test3");
      assertEquals("application/test", mimeType.getMimeType());
      assertEquals(".test", mimeType.getSuffix());
    }
  }
  
  public void testGetServerURL() throws Exception {
    assertEquals("http://www.a.com:80",
        DownloadFactory.getServerURL("http://www.a.com"));
    assertEquals("http://www.a.com:80",
        DownloadFactory.getServerURL("HTTP://www.a.com"));
    assertEquals("http://www.a.com:80",
        DownloadFactory.getServerURL("http://www.a.com:80"));
    assertEquals("http://www.a.com:80",
        DownloadFactory.getServerURL("HTTP://www.a.com:80"));
    assertEquals("http://www.a.com:80",
        DownloadFactory.getServerURL("http://www.a.com:80/aaa/aaa/"));
    assertEquals("http://www.a.com:8080",
        DownloadFactory.getServerURL("http://www.a.com:8080/aaa/aaa/"));
    
    assertEquals("https://www.a.com:443",
        DownloadFactory.getServerURL("https://www.a.com/aaa/aaa/"));
    assertEquals("https://www.a.com:8443",
        DownloadFactory.getServerURL("https://www.a.com:8443/aaa/aaa/"));
    assertEquals("https://www.a.com:8443",
        DownloadFactory.getServerURL("Https://www.a.com:8443/aaa/aaa/"));
    
  }

}
