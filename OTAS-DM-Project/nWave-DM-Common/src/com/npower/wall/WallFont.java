/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallFont.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The wall:font tag
 */

public class WallFont extends TagSupport {

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

  // Attributes
  protected String                size             = "";

  protected String                face             = "";

  protected String                color            = "";

  // attribute Setters
  public void setSize(String s) {
    this.size = s;
  }

  public void setFace(String f) {
    this.face = f;
  }

  public void setColor(String c) {
    this.color = c;
  }

  /**
   * goStartTag will be called when we see the "font" tag
   */
  @Override
  public int doStartTag() throws JspException {

    String style = "";
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
          // build stule attribute
          if (!color.equals("")) {
            style += "color:" + color + ";";
          }
          if (!face.equals("")) {
            style += "font-family:" + face + ";";
          }
          if (size.equals("+1")) {
            style += "font-size:larger;";
          }
          if (size.equals("-1")) {
            style += "font-size:smaller;";
          }
          out.print("<span");
          if (!style.equals("")) {
            out.print(" style=\"" + style + "\"");
          }
          out.print(">");

        } catch (IOException ioe) {
          System.out.println("Error in 'font' tag: " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

      if (capability_value.startsWith("chtml")) {
        try {
          JspWriter out = pageContext.getOut();
          out.print("<font");
          if (!color.equals("")) {
            out.print(" color=\"" + color + "\"");
          }
          if (!face.equals("")) {
            out.print(" face=\"" + face + "\"");
          }
          if (!size.equals("")) {
            out.print(" size=\"" + size + "\"");
          }
          out.print(">");

        } catch (IOException ioe) {
          System.out.println("Error in 'font' tag: " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

      if (capability_value.startsWith("wml")) {
        // wml? nothing to do
        return (EVAL_BODY_INCLUDE);
      }

    } catch (Exception e) {
      System.out.println("Error in WALL tag 'font': " + e.getMessage());
      System.out.println("Error: " + e.toString());
    }
    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() {

    try {
      JspWriter out = pageContext.getOut();

      if (capability_value.startsWith("chtml")) {
        out.print("</font>");
      }
      if (capability_value.startsWith("xhtmlmp")) {
        out.print("</span>");
      }
    } catch (IOException ioe) {
      System.out.println("Error in 'font' tag: " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }

}
