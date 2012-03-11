/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/tracking/HttpServletRequest4Test.java,v 1.1 2008/06/18 04:52:03 zhao Exp $
 * $Revision: 1.1 $
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/18 04:52:03 $
 */
public class HttpServletRequest4Test implements HttpServletRequest {
  
  private Map<String, String> headers = new HashMap<String, String>();
  private Map<String, String> parameters = new HashMap<String, String>();

  public class Enumerator implements Enumeration<String> {
    
    private Iterator<String> iterator = null;

    public Enumerator(Set<String> set) {
      super();
      this.iterator = set.iterator();
    }

    public boolean hasMoreElements() {
      return this.iterator.hasNext();
    }

    public String nextElement() {
      return this.iterator.next();
    }
  }
  /**
   * 
   */
  public HttpServletRequest4Test() {
    super();
  }

  public HttpServletRequest4Test(Map<String, String> headers, Map<String, String> parameters) {
    super();
    this.headers = headers;
    this.parameters = parameters;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getAuthType()
   */
  public String getAuthType() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getContextPath()
   */
  public String getContextPath() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getCookies()
   */
  public Cookie[] getCookies() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getDateHeader(java.lang.String)
   */
  public long getDateHeader(String name) {
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getHeader(java.lang.String)
   */
  public String getHeader(String name) {
    return this.headers.get(name);
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
   */
  public Enumeration getHeaderNames() {
    return new Enumerator(this.headers.keySet());
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getHeaders(java.lang.String)
   */
  public Enumeration getHeaders(String name) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getIntHeader(java.lang.String)
   */
  public int getIntHeader(String name) {
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getMethod()
   */
  public String getMethod() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getPathInfo()
   */
  public String getPathInfo() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getPathTranslated()
   */
  public String getPathTranslated() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getQueryString()
   */
  public String getQueryString() {
    return "softwareID=MSN&imei=IMEI:357674014003983";
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getRemoteUser()
   */
  public String getRemoteUser() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getRequestURI()
   */
  public String getRequestURI() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getRequestURL()
   */
  public StringBuffer getRequestURL() {
    return new StringBuffer("http://127.0.0.1:8080/OTAS-DM-MyPortal/wap/software/task.do?softwareID=MSN&imei=IMEI:357674014003983");
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
   */
  public String getRequestedSessionId() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getServletPath()
   */
  public String getServletPath() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getSession()
   */
  public HttpSession getSession() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
   */
  public HttpSession getSession(boolean create) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
   */
  public Principal getUserPrincipal() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromCookie()
   */
  public boolean isRequestedSessionIdFromCookie() {
    return false;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromURL()
   */
  public boolean isRequestedSessionIdFromURL() {
    return false;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
   */
  public boolean isRequestedSessionIdFromUrl() {
    return false;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdValid()
   */
  public boolean isRequestedSessionIdValid() {
    return false;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#isUserInRole(java.lang.String)
   */
  public boolean isUserInRole(String role) {
    return false;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getAttribute(java.lang.String)
   */
  public Object getAttribute(String arg0) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getAttributeNames()
   */
  public Enumeration getAttributeNames() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getCharacterEncoding()
   */
  public String getCharacterEncoding() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getContentLength()
   */
  public int getContentLength() {
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getContentType()
   */
  public String getContentType() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getInputStream()
   */
  public ServletInputStream getInputStream() throws IOException {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getLocalAddr()
   */
  public String getLocalAddr() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getLocalName()
   */
  public String getLocalName() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getLocalPort()
   */
  public int getLocalPort() {
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getLocale()
   */
  public Locale getLocale() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getLocales()
   */
  public Enumeration getLocales() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
   */
  public String getParameter(String arg0) {
    return this.parameters.get(arg0);
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getParameterMap()
   */
  public Map getParameterMap() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getParameterNames()
   */
  public Enumeration getParameterNames() {
    return new Enumerator(this.parameters.keySet());
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
   */
  public String[] getParameterValues(String arg0) {
    String[] values = new String[1];
    values[0] = this.getParameter(arg0);
    return values;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getProtocol()
   */
  public String getProtocol() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getReader()
   */
  public BufferedReader getReader() throws IOException {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getRealPath(java.lang.String)
   */
  public String getRealPath(String arg0) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getRemoteAddr()
   */
  public String getRemoteAddr() {
    return "10.1.1.1";
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getRemoteHost()
   */
  public String getRemoteHost() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getRemotePort()
   */
  public int getRemotePort() {
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getRequestDispatcher(java.lang.String)
   */
  public RequestDispatcher getRequestDispatcher(String arg0) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getScheme()
   */
  public String getScheme() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getServerName()
   */
  public String getServerName() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getServerPort()
   */
  public int getServerPort() {
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#isSecure()
   */
  public boolean isSecure() {
    return false;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#removeAttribute(java.lang.String)
   */
  public void removeAttribute(String arg0) {

  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#setAttribute(java.lang.String, java.lang.Object)
   */
  public void setAttribute(String arg0, Object arg1) {

  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#setCharacterEncoding(java.lang.String)
   */
  public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {

  }

}
