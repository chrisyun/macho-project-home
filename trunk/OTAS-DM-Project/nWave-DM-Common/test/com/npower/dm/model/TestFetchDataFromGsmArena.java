/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/model/TestFetchDataFromGsmArena.java,v 1.1 2007/12/14 06:30:33 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/12/14 06:30:33 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/12/14 06:30:33 $
 */
public class TestFetchDataFromGsmArena extends TestCase {

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
  
  public void testGetHtmlResult() throws Exception {
    String url = "http://www.gsmarena.com/results.php3";
    
    URL requestURL = new URL(url);
    URLConnection urlConn = requestURL.openConnection();
    urlConn.setDoInput(true);
    urlConn.setDoOutput(true);
    urlConn.setUseCaches(false);
    urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

    // Send request
    PrintWriter pw = new PrintWriter(urlConn.getOutputStream());
    pw.print("sQuickSearch=yes&sName=nokia+6120");
    pw.flush();
    pw.close();

    // Connect
    InputStream is = urlConn.getInputStream();
    BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
    StringBuffer response = new StringBuffer();
    String line = responseReader.readLine();
    while (line != null) {
          int begin = line.indexOf("<div id=\"specs-list\">");
          
          line = responseReader.readLine();
    }
    responseReader.close();
    
    System.out.println(response.toString());
    
    String content = response.toString();
    int begin = content.indexOf("<div id=\"specs-list\">");
    if (begin > 0) {
       content = content.substring(begin + "<div id=\"specs-list\">".length(), content.length());
       content = content.trim();
       
      
    }
  }

}
