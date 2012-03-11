/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallI.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The wall:i (italics in WML compatibility mode)
 */

public class WallI extends TagSupport {

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

  // Attributes
  protected String                markup           = "";

  // attribute Setters
  public void setMarkup(String markup) {
    this.markup = markup;
  }

  /**
   * goStartTag will be called when we see the "i" tag
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
        JspWriter out = pageContext.getOut();
        out.print("<i>");
      }
      return (EVAL_BODY_INCLUDE);

    } catch (Exception e) {
      System.out.println("Error in Wurfl: tag 'i': " + e.getMessage());
      System.out.println("Error: " + e.toString());
    }
    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() {

    if (!((capability_value.startsWith("wml")) && anchor != null)) {
      try {
        JspWriter out = pageContext.getOut();
        out.print("</i>");
      } catch (IOException ioe) {
        System.out.println("Error in Tag 'i': " + ioe);
      }
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }
}
