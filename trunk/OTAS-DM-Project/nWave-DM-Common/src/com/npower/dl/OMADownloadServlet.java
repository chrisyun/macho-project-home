/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dl/OMADownloadServlet.java,v 1.5 2007/08/29 06:21:00 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2007/08/29 06:21:00 $
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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/08/29 06:21:00 $
 */
public class OMADownloadServlet extends HttpServlet {
  public static Log log = LogFactory.getLog(OMADownloadServlet.class);
  
  //Initialize global variables
  public void init() throws ServletException {
    super.init();
  }

  public void doDownloadDescriptor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String requestUrl = request.getRequestURL().toString();
    String requestUri = request.getRequestURI();
    log.info("Download Service: Download Descriptor: url: " + requestUrl);
    log.info("Download Service: Download Descriptor: uri: " + requestUri);

    String downloadID = DownloadFactory.parserDownloadID(requestUri);

    log.info("Download Service: Request download ID: " + downloadID);
    
    DownloadFactory ddFactory = DownloadFactory.newInstance();
    DownloadDescriptor descriptor = ddFactory.getDownloadDescriptor(downloadID);
    
    try {
        String serverURL = DownloadFactory.getServerURL(requestUrl);
        String baseURL = serverURL + request.getContextPath() + "/dl";
        
        //String baseURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
        //                + request.getContextPath() + "/dl";
        
        descriptor.setDownloadURI(baseURL + "/" + DownloadFactory.OPERATION_DOWNLOAD);
        descriptor.setInstallNotifyURI(baseURL + "/" + DownloadFactory.OPERARION_INSTALL_NOTIFICATION);
      
        String content = descriptor.getContent();
        response.setContentType(descriptor.getContentType());
        response.setContentLength(content.length());
        
        OutputStream out = response.getOutputStream();
        out.write(content.getBytes("UTF-8"));
        out.flush();
        out.close();
        
        log.debug("Download Descriptor Content: " + content);
    } catch (Exception ex) {
      throw new ServletException(ex);
    } finally {
    }
  }
  
  public void doDownload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String uri = request.getRequestURI();
    log.info("Download Service: Download Content: uri: " + uri);

    String downloadID = DownloadFactory.parserDownloadID(uri);

    FumoDownloadUpdate download = new FumoDownloadUpdate();
    download.setDownloadID(downloadID);
    try {
        byte[] content = download.getContent();
        if (content != null && content.length > 0) {
           log.info("Download Service: Request download ID: " + downloadID + ",Content size: " + content.length);
           response.setContentType(download.getContentType());
           response.setContentLength(content.length);
           OutputStream out = response.getOutputStream();
           out.write(content);
           out.flush();
           //out.close();
        }
    } catch (Exception ex) {
      throw new ServletException(ex);
    } finally {
    }
  }
  
  public void doInstallNotification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String uri = request.getRequestURI();
    log.info("Download Service: Install Notification: uri: " + uri);
    //String downloadID = DownloadDescriptorFactory.parserDownloadID(uri);

    try {
        log.info("Receive Install Notification: " + uri);
    } catch (Exception ex) {
      throw new ServletException(ex);
    } finally {
    }
  }

  /**
   * @param request
   */
  private void logRequest(HttpServletRequest request) {
    Enumeration<String> names = request.getHeaderNames();
    while (names.hasMoreElements()) {
          String name = names.nextElement();
          String value = request.getHeader(name);
          log.info("Http header: " + name + "=" + value);
    }
    names = request.getParameterNames();
    while (names.hasMoreElements()) {
          String name = (String)names.nextElement();
          String value = request.getHeader(name);
          log.info("Http param: " + name + "=" + value);
    }
  }
  
  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String uri = request.getRequestURI();
    
    String operatoin = DownloadFactory.parserOperation(uri);
    
    this.logRequest(request);
    
    if (StringUtils.isEmpty(operatoin)) {
       throw new ServletException("Unknown Download Operation!");
    }
    
    if (operatoin.equals(DownloadFactory.OPERATION_DOWNLOAD_DESCRIPTOR)) {
       this.doDownloadDescriptor(request, response);
       return;
    } else if (operatoin.equals(DownloadFactory.OPERATION_DOWNLOAD)) {
      this.doDownload(request, response);
      return;
    } else if (operatoin.equals(DownloadFactory.OPERARION_INSTALL_NOTIFICATION)) {
      this.doInstallNotification(request, response);
      return;
    }
  }

  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    doGet(request, response);
  }

  //Clean up resources
  public void destroy() {
  }

}
