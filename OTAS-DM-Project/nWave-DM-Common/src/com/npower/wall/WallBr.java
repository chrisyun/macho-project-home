/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallBr.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * The body-less wall:br
 */

public class WallBr extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest              request          = null;

  private static CapabilityMatrix cm               = null;

  private static UAManager        uam              = null;

  // locals
  String                          warning          = "";

  String                          UA               = "";

  String                          device_id        = "";

  String                          capability_value = "";

  /**
   * goStartTag will be called when we see the "br" tag
   */
  @Override
  public int doStartTag() throws JspException {

    try {
      ObjectsManager objectsManager = WurflServletInit.getObjectsManager();
      cm = objectsManager.getCapabilityMatrixInstance();
      uam = objectsManager.getUAManagerInstance();
      request = (HttpServletRequest) pageContext.getRequest();

      // Check the capability string
      warning = TagUtil.checkCapability("preferred_markup");
      if (warning.length() > 0) {
        throw new JspException(warning);
      }

      // get the user agent
      UA = TagUtil.getUA(this.request);

      device_id = uam.getDeviceIDFromUALoose(UA);

      capability_value = cm.getCapabilityForDevice(device_id, "preferred_markup");
      capability_value = TagUtil.getWallMarkup(capability_value);

      if (capability_value.startsWith("xhtmlmp")) {
        try {
          JspWriter out = pageContext.getOut();
          out.print("<br/>");
        } catch (IOException ioe) {
          throw new JspException("Error in WALL tag 'br': " + ioe.getMessage());
        }
      }

      if (capability_value.startsWith("wml")) {
        try {
          JspWriter out = pageContext.getOut();
          out.print("<br/>");
        } catch (IOException ioe) {
          throw new JspException("Error in WALL tag 'br': " + ioe.getMessage());
        }
      }

      // CHTML <br> without trailing slash
      if (capability_value.startsWith("chtml")) {
        try {
          JspWriter out = pageContext.getOut();
          out.print("<br>");
        } catch (IOException ioe) {
          throw new JspException("Error in WALL tag 'br': " + ioe.getMessage());
        }

      }

      return (SKIP_BODY);

    } catch (Exception e) {
      throw new JspException("Error in WALL tag 'br': " + e.getMessage());
    }

  }
}
