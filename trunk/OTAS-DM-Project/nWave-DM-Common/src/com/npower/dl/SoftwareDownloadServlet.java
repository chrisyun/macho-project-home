/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dl/SoftwareDownloadServlet.java,v 1.6 2009/02/26 10:38:06 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2009/02/26 10:38:06 $
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.Manifest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.msm.JADCreator;
import com.npower.dm.server.engine.EngineConfig;

/**
 * Service as OMA Download protocol for software download.
 * Response following URI:
 * 1. OMA Download Descriptor
 *    /{SERVLET CONTEXT}/download/software/{PACKAGE_ID}.ddml
 *    eg: /otasdm/download/software/12234451.ddml
 * 2. Download
 *    /{SERVLET CONTEXT}/download/software/{PACKAGE_ID}/filename
 *    eg: /otasdm/download/software/12234451/aaaa.sisx
 *    
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2009/02/26 10:38:06 $
 */
public class SoftwareDownloadServlet extends HttpServlet {
  public static Log log = LogFactory.getLog(SoftwareDownloadServlet.class);
  
  /**
   * Parser and extract packageID from requestURI
   * @param requestURI
   * @return
   */
  public static String parserPackageID(String requestURI) {
    int begin = requestURI.indexOf("/download/software/");
    int end = requestURI.indexOf(".ddml", begin);
    if (end <= begin) {
       end = requestURI.indexOf("/", begin + "/download/software/".length() + 1);
    }
    String packageID = requestURI.substring(begin + "/download/software/".length(), end);
    return packageID;
  }
  
  //Initialize global variables
  public void init() throws ServletException {
    super.init();
  }

  public void doDownloadDescriptor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String requestUrl = request.getRequestURL().toString();
    String requestUri = request.getRequestURI();
    log.info("Download Service: Download Descriptor: url: " + requestUrl);
    log.info("Download Service: Download Descriptor: uri: " + requestUri);

    String packageID = parserPackageID(requestUri);

    log.info("Software Download Service: Request package ID: " + packageID);
    
    DownloadDescriptor descriptor = new SoftwareDownloadDescriptor();
    descriptor.setDownloadID(packageID);
    
    try {
        String serverURL = DownloadFactory.getServerURL(requestUrl);
        String baseURL = serverURL + request.getContextPath();
        
        descriptor.setDownloadURI(baseURL + "/download/software/");
        descriptor.setInstallNotifyURI(baseURL + "/download/notification/");
      
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
    String requestUri = request.getRequestURI();
    log.info("Download Service: Download Content: uri: " + requestUri);

    String packageID = parserPackageID(requestUri);
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        SoftwareBean bean = factory.createSoftwareBean();
        SoftwarePackage pkg = bean.getPackageByID(Long.parseLong(packageID));
        if (pkg == null) {
           throw new DMException("Could not found download package by package ID: " + packageID);
        }
        InputStream ins = pkg.getBinary().getBinaryBlob().getBinaryStream();
        if (pkg.getSize() > 0) {
           response.setContentType(pkg.getMimeType());
           response.setContentLength(pkg.getSize());
           OutputStream out = response.getOutputStream();
           byte[] buf = new byte[1024];
           int len = ins.read(buf);
           while (len > 0) {
                 out.write(buf, 0, len);
                 len = ins.read(buf);
           }
           out.flush();
           //out.close();
        }
    } catch (Exception ex) {
      throw new ServletException(ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
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

  public void doDownloadJAD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String requestUri = request.getRequestURI();
    log.info("Download Service: Download Content: uri: " + requestUri);
    String requestUrl = request.getRequestURL().toString();

    String packageID = parserPackageID(requestUri);
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        SoftwareBean bean = factory.createSoftwareBean();
        SoftwarePackage pkg = bean.getPackageByID(Long.parseLong(packageID));
        if (pkg == null) {
           throw new DMException("Could not found download package by package ID: " + packageID);
        }
        if (pkg.getMimeType().equalsIgnoreCase("text/vnd.sun.j2me.app-descriptor")) {
          // Content is JAD
          InputStream ins = pkg.getBinary().getBinaryBlob().getBinaryStream();
          if (pkg.getSize() > 0) {
             response.setContentType(pkg.getMimeType());
             response.setContentLength(pkg.getSize());
             OutputStream out = response.getOutputStream();
             byte[] buf = new byte[1024];
             int len = ins.read(buf);
             while (len > 0) {
                   out.write(buf, 0, len);
                   len = ins.read(buf);
             }
             out.flush();
             //out.close();
             return;
          }
        } else if (pkg.getMimeType().equalsIgnoreCase("application/java-archive")
                  || pkg.getMimeType().equalsIgnoreCase("application/java")
                  || pkg.getMimeType().equalsIgnoreCase("application/x-java-archive")) {
          // JAR Format, generate a JAD
          File jarFile = File.createTempFile("software_tmp", "jar");
          InputStream ins = pkg.getBinary().getBinaryBlob().getBinaryStream();
          if (pkg.getSize() > 0) {
            OutputStream out = new FileOutputStream(jarFile);
            byte[] buf = new byte[1024];
            int len = ins.read(buf);
            while (len > 0) {
                  out.write(buf, 0, len);
                  len = ins.read(buf);
            }
            out.flush();
            out.close();
            
            String url4Jar = StringUtils.replace(requestUrl, ".jad", ".jar");
            JADCreator creator = new JADCreator();
            Manifest manifest = creator.getJADManufest(jarFile, url4Jar);
            response.setContentType(pkg.getMimeType());
            manifest.write(response.getOutputStream());
            
            // Clean temp file
            jarFile.delete();
            return;
          }
        }
        // Return Not Found Code.
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
    } catch (Exception ex) {
      throw new ServletException(ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
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
    String requestURI = request.getRequestURI();
    
    int begin = requestURI.indexOf("/download/software/");
    boolean omaDL = (requestURI.indexOf(".ddml", begin) > 0);
    boolean jadDL = (requestURI.indexOf(".jad", begin) > 0);
    
    this.logRequest(request);
    
    if (omaDL) {
       this.doDownloadDescriptor(request, response);
       return;
    } else if (jadDL) {
      this.doDownloadJAD(request, response);
      return;
    } else if (begin > 0) {
      this.doDownload(request, response);
      return;
    } else {
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
