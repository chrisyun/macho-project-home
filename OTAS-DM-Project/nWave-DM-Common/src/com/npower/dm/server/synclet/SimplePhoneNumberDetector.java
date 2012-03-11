/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/synclet/SimplePhoneNumberDetector.java,v 1.1 2006/12/13 07:16:41 zhao Exp $
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

import javax.servlet.http.HttpServletRequest;

import sync4j.framework.core.SyncML;

/**
 * Default phoneNumber parser.
 * This parser will always return imei as phone number.
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2006/12/13 07:16:41 $
 */
public class SimplePhoneNumberDetector implements PhoneNumberDetector {

  /**
   * Default constructor.
   */
  public SimplePhoneNumberDetector() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.server.synclet.PhoneNumberParser#getPhoneNumber(java.lang.String, javax.servlet.http.HttpServletRequest, sync4j.framework.core.SyncML)
   */
  public String getPhoneNumber(String deviceExternalID, HttpServletRequest httpRequest, SyncML message) {
    String msisdn = deviceExternalID;
    if (msisdn.toUpperCase().startsWith("IMEI:")) {
       msisdn = msisdn.substring(5, msisdn.length());
    }
    return msisdn;
  }

}
