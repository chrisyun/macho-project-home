/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/wurfl/WurflDevice.java,v 1.2 2007/11/14 06:18:55 zhao Exp $
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

import nu.xom.Element;

/**
 * @author <b>Luca Passani</b>, passani at eunet dot no <br>
 *         <br>
 *         I have been puzzled all the way about the necessity of this class.
 *         Basically, the WurflDevice class adds no information to what is
 *         contained in a nu.xom.Element that represents a device after the
 *         Wurfl.xml file has been parsed. On the other hand, I want to hide all
 *         dependencies on nu.xom.* for the user of the library, so I guess I
 *         have no other chance but define my own class to store info about a
 *         device element.<br>
 *         If you have ideas or suggestions, you are welcome to drop me an
 *         email.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/11/14 06:18:55 $
 */
public class WurflDevice {

  String  id;

  String  fall_back;

  String  user_agent;

  String  brand_name;

  String  model_name         = null;

  boolean actual_device_root = false;

  public WurflDevice(Element xom_elem) {

    // should I check that the element really represents a Wurfl device?
    // What should I do if it is not?
    id = xom_elem.getAttributeValue("id");
    fall_back = xom_elem.getAttributeValue("fall_back");
    user_agent = xom_elem.getAttributeValue("user_agent");
    if ("true".equals(xom_elem.getAttributeValue("actual_device_root"))) {
      actual_device_root = true;
    }
  }

  /**
   * Given a WurflDevice, retrieve its ID
   */
  public String getId() {
    return id;
  }

  /**
   * Given a WurflDevice, retrieve its fall_back
   */
  public String getFallBack() {
    return fall_back;
  }

  /**
   * Given a WurflDevice, retrieve its user_agent
   */
  public String getUserAgent() {
    return user_agent;
  }

  /**
   * Given a WurflDevice, retrieve its actual_device_root
   */
  public boolean getActual_device_root() {
    return actual_device_root;
  }

  /**
   * Given a WurflDevice, retrieve its brand_name
   */
  public String getBrandName() {
    return brand_name;
  }

  void setBrandName(String bn) {
    brand_name = bn;
  }

  /**
   * Given a WurflDevice, retrieve its model_name
   */
  public String getModelName() {
    return model_name;
  }

  void setModelName(String mn) {
    model_name = mn;
  }

}
