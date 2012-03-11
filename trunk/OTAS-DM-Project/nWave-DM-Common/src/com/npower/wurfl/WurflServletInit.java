/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/wurfl/WurflServletInit.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/05/12 09:38:17 $
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
package com.npower.wurfl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/05/12 09:38:17 $
 */
public class WurflServletInit extends HttpServlet {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private static Log log = LogFactory.getLog(WurflServletInit.class);
  
  private static String wurflSourceClassName;

  private static ObjectsManager objectManager = null;
  
  public static ObjectsManager getObjectsManager() throws Exception {
    synchronized (WurflServletInit.class) {
      if (objectManager == null) {
        // Initialize WURFL with '/WEB-INF/wurfl.xml'
        log.info("Initialize WURFL ...");
        try {
          WurflSource source = (WurflSource)Class.forName(wurflSourceClassName).newInstance();
          objectManager = ObjectsManager.newInstance(source);
        } catch (Exception e) {
          log.fatal(e.getMessage(), e);
          throw e;
        }
      }
      return objectManager;
    }
  }

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    wurflSourceClassName = config.getInitParameter("WurflSourceClass");
    if (wurflSourceClassName == null || wurflSourceClassName.trim().length() == 0) {
       throw new ServletException("Missing WURFLSourceClass parameter in web.xml for WurflServletInit servlet.");
    }
    // To fire up WURFL initialize
    try {
        getObjectsManager();
    } catch (Exception e) {
      log.fatal(e.getMessage(), e);
      throw new ServletException(e.getMessage(), e);
    }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    out.println("Init servlet (Loaded on startup for WURFL initialization)");
  }

  /**
   * We are going to perform the same operations for POST requests as for GET
   * methods, so this method just sends the request to the doGet method.
   */

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    doGet(request, response);
  }

}
