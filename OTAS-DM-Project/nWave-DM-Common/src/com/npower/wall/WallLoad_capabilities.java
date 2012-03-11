/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallLoad_capabilities.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.npower.wurfl.ListManager;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * The body-less wall:load_capabilities
 */

public class WallLoad_capabilities extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest         request          = null;

  private static UAManager   uam              = null;

  private static ListManager lm               = null;

  // locals
  String                     warning          = "";

  String                     UA               = "";

  String                     device_id        = "";

  String                     capability_value = "";

  Map<String, String>                    allCapas         = null;

  /**
   * goStartTag will be called when we see the "load_capabilities" tag
   */
  @Override
  public int doStartTag() throws JspException {

    try {
      ObjectsManager objectsManager = WurflServletInit.getObjectsManager();
      uam = objectsManager.getUAManagerInstance();
      lm = objectsManager.getListManagerInstance();

      request = (HttpServletRequest) pageContext.getRequest();

      // get the user agent
      UA = TagUtil.getUA(this.request);
      device_id = uam.getDeviceIDFromUALoose(UA);

      // let's make that all capabilities are available to the JSTL
      // as ${capabilities.capability_name}
      allCapas = lm.getCapabilitiesForDeviceID(device_id);
      String wall_markup = (String) allCapas.get("preferred_markup");
      wall_markup = TagUtil.getWallMarkup(wall_markup);
      allCapas.put("wall_markup", wall_markup);
      pageContext.setAttribute("capabilities", allCapas);

      // add device brands
      pageContext.setAttribute("brands", lm.getDeviceBrandList());
      pageContext.setAttribute("devices", lm.getDeviceGroupedByBrand());

      // add device_id
      pageContext.setAttribute("device_id", device_id);

    } catch (Exception e) {
      System.out.println("Error in Wurfl: tag 'load_capabilities': " + e.getMessage());
      System.out.println("Error: " + e.toString());
    }
    return (SKIP_BODY);
  }
}
