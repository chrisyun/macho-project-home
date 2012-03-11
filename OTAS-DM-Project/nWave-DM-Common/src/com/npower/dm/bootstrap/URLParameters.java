/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/bootstrap/URLParameters.java,v 1.1 2008/03/11 03:43:32 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/03/11 03:43:32 $
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
package com.npower.dm.bootstrap;

import org.apache.commons.lang.StringUtils;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/03/11 03:43:32 $
 */
public class URLParameters {
  
  private String msisdn = null;
  private String deviceID = null;
  private String deviceExtID = null;
  
  /**
   * 
   */
  public URLParameters() {
    super();
  }

  /**
   * @return Returns the msisdn.
   */
  public String getMsisdn() {
    return msisdn;
  }

  /**
   * @param msisdn The msisdn to set.
   */
  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  /**
   * @return Returns the deviceID.
   */
  public String getDeviceID() {
    return deviceID;
  }

  /**
   * @param deviceID The deviceID to set.
   */
  public void setDeviceID(String deviceID) {
    this.deviceID = deviceID;
  }

  /**
   * @return Returns the deviceExtID.
   */
  public String getDeviceExtID() {
    return deviceExtID;
  }

  /**
   * @param deviceExtID The deviceExtID to set.
   */
  public void setDeviceExtID(String deviceExtID) {
    this.deviceExtID = deviceExtID;
  }
  
  public String encode() {
    StringBuffer result = new StringBuffer();
    result.append("p_");
    result.append((this.msisdn == null)?"":this.msisdn);
    result.append("_id_");
    result.append((this.deviceID == null)?"":this.deviceID);
    result.append("_x_");
    result.append((this.deviceExtID == null)?"":this.deviceExtID);
    return result.toString();
  }
  
  /**
   * @param s
   * @return
   */
  public static URLParameters decode(String s) {
    if (StringUtils.isEmpty(s)) {
       return null;
    }
    try {
        String msisdn = s.substring("p_".length(), s.indexOf("_id_"));
        String deviceID = s.substring(s.indexOf("_id_") + "_id_".length(),
                                      s.indexOf("_x_"));
        String deviceExtID = s.substring(s.indexOf("_x_") + "_x_".length(), s.length());
        URLParameters result = new URLParameters();
        result.setMsisdn(msisdn);
        result.setDeviceID(deviceID);
        result.setDeviceExtID(deviceExtID);
        return result;
    } catch (Exception ex) {
      return null;
    }
  }
  
}
