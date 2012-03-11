/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/server/synclet/SimpleHttpRequest.java,v 1.2 2007/08/29 06:21:00 zhao Exp $
  * $Revision: 1.2 $
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
package com.npower.dm.server.synclet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/08/29 06:21:00 $
 */
public class SimpleHttpRequest implements HttpServletRequest {
  
  private Map<String, String> headers = new HashMap<String, String>();

  /**
   * @param arg0
   */
  public SimpleHttpRequest() {
    super();
  }
  
  public void setHeader(String name, String value) {
    this.headers.put(name, value);
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getHeader(java.lang.String)
   */
  public String getHeader(String name) {
    return this.headers.get(name);
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
  public long getDateHeader(String arg0) {
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
   */
  public Enumeration<?> getHeaderNames() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getHeaders(java.lang.String)
   */
  public Enumeration<?> getHeaders(String arg0) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServletRequest#getIntHeader(java.lang.String)
   */
  public int getIntHeader(String arg0) {
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
    return null;
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
    return null;
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
  public HttpSession getSession(boolean arg0) {
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
  public boolean isUserInRole(String arg0) {
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
  public Enumeration<?> getAttributeNames() {
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
  public Enumeration<?> getLocales() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
   */
  public String getParameter(String arg0) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getParameterMap()
   */
  public Map<?, ?> getParameterMap() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getParameterNames()
   */
  public Enumeration<?> getParameterNames() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
   */
  public String[] getParameterValues(String arg0) {
    return null;
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
    return null;
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
