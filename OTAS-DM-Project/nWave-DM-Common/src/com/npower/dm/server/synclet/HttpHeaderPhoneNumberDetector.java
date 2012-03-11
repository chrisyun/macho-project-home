/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/synclet/HttpHeaderPhoneNumberDetector.java,v 1.1 2006/12/13 07:16:41 zhao Exp $
  * $Revision: 1.1 $
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.SyncML;

/**
 * Parsing and get phone number from http header. 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2006/12/13 07:16:41 $
 */
public class HttpHeaderPhoneNumberDetector implements PhoneNumberDetector {
  
  private static Log log = LogFactory.getLog(HttpHeaderPhoneNumberDetector.class);

  /**
   * Name of Http header parameter.
   */
  private String headerName = null;
  
  /**
   * Regular express for fetch phone number from http header.
   */
  private String pattern = null;

  /**
   * 
   */
  public HttpHeaderPhoneNumberDetector() {
    super();
  }

  /**
   * @return the headerName
   */
  public String getHeaderName() {
    return headerName;
  }

  /**
   * @param headerName the headerName to set
   */
  public void setHeaderName(String headerName) {
    this.headerName = headerName;
  }

  /**
   * @return the pattern
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * @param pattern the pattern to set
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.server.synclet.PhoneNumberParser#getPhoneNumber(java.lang.String, javax.servlet.http.HttpServletRequest, sync4j.framework.core.SyncML)
   */
  public String getPhoneNumber(String deviceExternalID, HttpServletRequest httpRequest, SyncML message) {
    String name = this.getHeaderName();
    if (StringUtils.isEmpty(name)) {
       log.error("Http header name is null. please specified the name of header for HttpHeaderPhoneNumberDetector.");
       return null;
    }
    String headerValue = httpRequest.getHeader(name);
    if (StringUtils.isEmpty(headerValue)) {
       log.error("Missing value in http header: " + name);
       return null;
    }
    if (StringUtils.isEmpty(this.getPattern())) {
       return headerValue.trim();
    }
    boolean found = Pattern.compile(pattern).matcher(headerValue).find();
    if (found) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(headerValue);
        m.find();
        int start = m.start();
        int end = m.end();
        return headerValue.substring(start, end);
    } else {
        return null;
    }
  }

}
