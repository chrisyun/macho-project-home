/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/AccessLogItem.java,v 1.1 2008/06/17 11:06:25 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/17 11:06:25 $
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/17 11:06:25 $
 */
public class AccessLogItem implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private Date timeStamp = new Date();
  
  private String url = null;
  
  private String query = null;
  
  private String clientIP = null;
  
  private String sessionID = null;
  
  private String userAgent = null;
  
  private List<NameValuePair> headers = new ArrayList<NameValuePair>();

  private List<NameValuesPair> parameters = new ArrayList<NameValuesPair>();

  /**
   * 
   */
  public AccessLogItem() {
    super();
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public String getClientIP() {
    return clientIP;
  }

  public void setClientIP(String clientIP) {
    this.clientIP = clientIP;
  }

  public String getSessionID() {
    return sessionID;
  }

  public void setSessionID(String sessionID) {
    this.sessionID = sessionID;
  }

  public List<NameValuePair> getHeaders() {
    return headers;
  }

  public void setHeaders(List<NameValuePair> headers) {
    this.headers = headers;
  }

  public List<NameValuesPair> getParameters() {
    return parameters;
  }

  public void setParameters(List<NameValuesPair> parameters) {
    this.parameters = parameters;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  
}
