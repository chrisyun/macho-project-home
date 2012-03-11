/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/synclet/CarrierDetector.java,v 1.2 2007/05/16 07:17:57 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/05/16 07:17:57 $
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

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import sync4j.framework.core.SyncML;

import com.npower.dm.core.DMException;

/**
 * Detect carrier from HttpRequest or message.
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/05/16 07:17:57 $
 */
public interface CarrierDetector extends Serializable {

  /**
   * Return Carrier ID detected from DM Session.
   * @param deviceExternalID
   *        Device's IMEI
   * @param phoneNumber
   *        Device's phone number
   * @param httpRequest
   *        DM Http request
   * @param message
   *        DM SyncML message.
   * @return
   *        carrier ID.
   */
  public abstract String getCarrierID(String deviceExternalID, String phoneNumber, HttpServletRequest httpRequest, SyncML message) throws DMException;

}
