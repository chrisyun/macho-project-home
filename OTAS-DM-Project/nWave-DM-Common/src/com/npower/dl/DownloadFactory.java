/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dl/DownloadFactory.java,v 1.3 2007/03/12 14:47:47 zhao Exp $
  * $Revision: 1.3 $
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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/03/12 14:47:47 $
 */
public class DownloadFactory {

  /**
   * 
   */
  public static final String OPERARION_INSTALL_NOTIFICATION = "InstallNotification";
  /**
   * 
   */
  public static final String OPERATION_DOWNLOAD = "Download";
  /**
   * 
   */
  public static final String OPERATION_DOWNLOAD_DESCRIPTOR = "DownloadDescriptor";

  private DownloadFactory() {
    super();
  }
  
  public static DownloadFactory newInstance() {
    return new DownloadFactory();
  }
  
  /**
   * Extract Download ID From URL
   * @param uri
   * @return
   */
  public static String parserDownloadID(String uri) {
    if (StringUtils.isEmpty(uri)) {
       return null;
    }
    String result = uri.trim();
    if (result.lastIndexOf('.') > 0 && result.lastIndexOf('.') > result.lastIndexOf('/')) {
       result = result.substring(0, result.lastIndexOf('.'));
    }
    while (result.endsWith("/")) {
       result = result.substring(0, result.length() - 1);
    }
    result = result.substring(result.lastIndexOf('/') + 1, result.length());
    return result;
  }
  
  /**
   * Extract Download operation from URL
   * @param uri
   * @return
   */
  public static String parserOperation(String uri) {
    if (StringUtils.isEmpty(uri)) {
       return null;
    }
    String result = uri.trim();
    while (result.endsWith("/")) {
       result = result.substring(0, result.length() - 1);
    }
    result = result.substring(0, result.lastIndexOf('/'));
    
    result = result.substring(result.lastIndexOf('/') + 1, result.length());
    return result;
  }
  
  /**
   * Extract Server URL: 
   * http(s)://server:port
   * 
   * @param requestURL
   * @return
   * @throws MalformedURLException
   */
  public static String getServerURL(String requestURL) throws MalformedURLException {
    URL url = new URL(requestURL);
    String protocol = url.getProtocol();
    String server = url.getHost();
    int port = url.getPort();
    if (port <= 0) {
       if (protocol.equalsIgnoreCase("https")) {
          port = 443;
       } else {
         port = 80;
       }
    }
    String serverURL = protocol + "://" + server + ":" + port;
    return serverURL;
  }
  
  /**
   * Return a instance of Download Descriptior.
   * @param downloadID
   * @return
   */
  public DownloadDescriptor getDownloadDescriptor(String downloadID) {
    DownloadDescriptor dd = new FumoDownloadDescriptor();
    dd.setDownloadID(downloadID);
    return dd;
  }
}
