/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/wurfl/UAManager.java,v 1.2 2007/11/14 06:18:55 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/11/14 06:18:55 $
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <b>Luca Passani</b>, passani at eunet dot no <br>
 *         <br>
 * 
 * Similarly to CapabilityMatrix, this object screens the rest of the app from
 * WURFL parsing and XOM. UAManager implements a caching mechanism to minimize
 * expensive analisys of UA strings to identify the device ID <br>
 * <br>
 * There are actually two separate chaches. One for storing queries for strict
 * matching and one for queries for loose matching (the latter are heavy to
 * compute, so one definitely wants to cache that)
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/14 06:18:55 $
 */
public class UAManager {

  private Map<String, String> ht_strict = Collections.synchronizedMap(new HashMap<String, String>(2053, 0.75f));

  private Map<String, String> ht_loose  = Collections.synchronizedMap(new HashMap<String, String>(2053, 0.75f));

  private Wurfl               wu        = null;

  public UAManager(Wurfl _wu) {
    wu = _wu;
  }

  /**
   * Given the UA of a device, find the device ID of the associated device.
   * Match must be perfect.
   */

  public String getDeviceIDFromUA(String ua) {

    Object obj = ht_strict.get(ua);
    if (obj != null) { // UA already cached?
      return (String) obj;
    } else { // let's find it, return it and put it in cache
      String devID = wu.getDeviceIDFromUA(ua);
      ht_strict.put(ua, devID); // cache for next time
      return devID;
    }
  }

  /**
   * Given the UA of a device, find the device ID of the associated device.
   * Match may be loose, meaning that <code>MOT-T720/G_05.01.43R</code> will
   * match
   * <code>MOT-T720/05.08.41R MIB/2.0 Profile/MIDP-1.0 Configuration/CLDC-1.0</code>.
   * (assuming no better match is found). Observe that this is more powerful
   * than a simple substring match. Matching UAs loosely is an expensive
   * operation, but UAManager implements a cache that helps a lot.
   */

  public String getDeviceIDFromUALoose(String ua) {

    Object obj = ht_loose.get(ua);
    if (obj != null) { // UA already cached?
      return (String) obj;

    } else { // let's find it, return it and put it in cache
      String devID = wu.getDeviceIDFromUALoose(ua);
      ht_loose.put(ua, devID); // cache for next time
      return devID;
    }
  }
}
