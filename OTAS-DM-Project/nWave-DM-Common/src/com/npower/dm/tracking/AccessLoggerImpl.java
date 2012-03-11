/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/AccessLoggerImpl.java,v 1.2 2008/06/18 04:52:03 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/06/18 04:52:03 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.tracking;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.npower.dm.tracking.writer.AccessLogWriter;
import com.npower.dm.tracking.writer.SimpleAccessLogWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/18 04:52:03 $
 */
public class AccessLoggerImpl implements AccessLogger {
  
  /**
   * Writer to ouput log information.
   */
  private AccessLogWriter writer = new SimpleAccessLogWriter();

  /**
   * Default constructor.
   */
  public AccessLoggerImpl() {
    super();
  }

  /**
   * Constructor with an AccessLogWriter.
   * @param writer
   */
  public AccessLoggerImpl(AccessLogWriter writer) {
    super();
    this.writer = writer;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.AccessLogger#getWriter()
   */
  public AccessLogWriter getWriter() {
    return writer;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.AccessLogger#setWriter(com.npower.dm.tracking.AccessLogWriter)
   */
  public void setWriter(AccessLogWriter writer) {
    this.writer = writer;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.tracking.AccessLogger#log(javax.servlet.http.HttpServletRequest)
   */
  @SuppressWarnings("unchecked")
  public void log(HttpServletRequest req) throws IOException {
    AccessLogItem item = new AccessLogItem();
    item.setUrl(req.getRequestURL().toString());
    item.setQuery(req.getQueryString());
    
    item.setClientIP(req.getRemoteAddr());
    
    HttpSession session = req.getSession();
    if (session != null) {
       item.setSessionID(session.getId());
    }
    
    String userAgent = req.getHeader("user-agent");
    item.setUserAgent(userAgent);
    
    Enumeration<String> headers = req.getHeaderNames();
    while (headers.hasMoreElements()) {
          String name = headers.nextElement();
          String value = req.getHeader(name);
          NameValuePair pair = new NameValuePair(name, value);
          item.getHeaders().add(pair);
    }
    
    Enumeration<String> parameters = req.getParameterNames();
    while (parameters.hasMoreElements()) {
          String name = parameters.nextElement();
          String[] values = req.getParameterValues(name);
          NameValuesPair pair = new NameValuesPair(name, values);
          item.getParameters().add(pair);
    }
    
    this.writer.write(item);
  }

}
