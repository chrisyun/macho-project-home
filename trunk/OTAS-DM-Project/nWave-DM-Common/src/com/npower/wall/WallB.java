/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallB.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The wall:b (bold in WML compatibility mode)
 */

public class WallB extends TagSupport {

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

  WallA                           anchor           = null;

  /**
   * goStartTag will be called when we see the "b" tag
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

      // this link could be part of a menu or not
      anchor = (WallA) findAncestorWithClass(this, WallA.class);

      // if wml and inside an anchor 'a' tag, don't render (would give a parsing
      // error)
      if (!((capability_value.startsWith("wml")) && (anchor != null))) {
        if (!capability_value.startsWith("chtml")) {
          JspWriter out = pageContext.getOut();
          out.print("<b>");
        }
      }
      return (EVAL_BODY_INCLUDE);

    } catch (Exception e) {
      throw new JspException("Error in WALL tag 'b': " + e.getMessage() + "\n" + "Error: " + e.toString());
    }
  }

  @Override
  public int doEndTag() throws JspException {

    try {
      if (!((capability_value.startsWith("wml")) && anchor != null)) {
        if (!capability_value.startsWith("chtml")) {
          JspWriter out = pageContext.getOut();
          out.print("</b>");
        }
      }
    } catch (IOException ioe) {
      throw new JspException("Error in Tag 'b': " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }
}
