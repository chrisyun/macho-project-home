/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallBlock.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The wall:block tag
 */

public class WallBlock extends TagSupport {

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
  String                          align            = "";

  // attribute Setters
  public void setAlign(String a) {
    this.align = a;
  }

  /**
   * goStartTag will be called when we see the "block" tag
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
          out.print("<p");
          if (!align.equals("")) {
            if (align.equals("middle")) {
              out.print(" style=\"text-align:center\"");
            } else {
              out.print(" style=\"text-align:" + align + "\"");
            }
          }
          out.print(">");
        } catch (IOException ioe) {
          System.out.println("Error in 'block' tag: " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

      if (capability_value.startsWith("chtml")) {
        try {
          JspWriter out = pageContext.getOut();
          if (!align.equals("")) {
            if (align.equals("middle")) {
              out.print("<p align=\"center\">");
            } else {
              out.print("<p align=\"" + align + "\">");
            }
          }
        } catch (IOException ioe) {
          System.out.println("Error in 'block' tag: " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

      if (capability_value.startsWith("wml")) {

        try {
          JspWriter out = pageContext.getOut();
          out.print("<p");
          if (!align.equals("")) {
            if (align.equals("middle")) {
              out.print(" align=\"center\"");
            } else {
              out.print(" align=\"" + align + "\"");
            }

          }
          out.print(">");
        } catch (IOException ioe) {
          System.out.println("Error in head tag: " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

    } catch (Exception e) {
      System.out.println("Error in WALL tag 'block': " + e.getMessage());
      System.out.println("Error: " + e.toString());
    }
    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() throws JspException {

    try {
      JspWriter out = pageContext.getOut();

      if (capability_value.startsWith("wml")) {
        out.print("</p>");
      }
      if (capability_value.startsWith("xhtmlmp")) {
        out.print("</p>");
      }
      if (capability_value.startsWith("chtml")) {
        if (!align.equals("")) {
          out.print("</p>");
        }
      }
    } catch (IOException ioe) {
      throw new JspException("Error in Tag block: " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }

}
