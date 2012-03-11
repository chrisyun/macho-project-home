/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallMarquee.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * The wall:marquee
 * 
 * @see BodyTagSupport
 */

public class WallMarquee extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest              request        = null;

  private static CapabilityMatrix cm             = null;

  private static UAManager        uam            = null;

  // locals
  String                          warning        = "";

  String                          UA             = "";

  String                          device_id      = "";

  String                          mark_up        = "";

  String                          nowrap_mode    = "";

  String                          marquee_as_css = "";

  // attributes
  String                          direction      = "";

  String                          loop           = "";

  String                          behavior       = "";

  String                          bgcolor        = "";

  // setters
  public void setDirection(String d) {
    this.direction = d;
  }

  public void setLoop(String l) {
    this.loop = l;
  }

  public void setBehavior(String b) {
    this.behavior = b;
  }

  public void setBgcolor(String bg) {
    this.bgcolor = bg;
  }

  /**
   * goStartTag will be called when we see the "marquee" tag
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
      warning += TagUtil.checkCapability("xhtml_marquee_as_css_property");
      warning += TagUtil.checkCapability("xhtml_nowrap_mode");
      if (warning.length() > 0) {
        throw new JspException(warning);
      }

      // get the user agent
      UA = TagUtil.getUA(this.request);

      device_id = uam.getDeviceIDFromUALoose(UA);
      mark_up = cm.getCapabilityForDevice(device_id, "preferred_markup");
      mark_up = TagUtil.getWallMarkup(mark_up);

      nowrap_mode = cm.getCapabilityForDevice(device_id, "xhtml_nowrap_mode");
      marquee_as_css = cm.getCapabilityForDevice(device_id, "xhtml_marquee_as_css_property");

      JspWriter out = pageContext.getOut();

      // XHTML MP
      if (mark_up.startsWith("xhtml")) {
        if (marquee_as_css.equals("true")) {
          String style = "display: -wap-marquee";
          if (!behavior.equals("")) {
            style += ";-wap-marquee-style " + behavior;
          }

          if (!direction.equals("")) {
            String dir = "ltr";
            if (direction.equals("left")) {
              dir = "rtl";
            }
            style += ";-wap-marquee-dir:" + dir;
          }

          if (!loop.equals("")) {
            style += ";-wap-marquee-loop:" + loop;
          }

          if (!bgcolor.equals("")) {
            style += ";background-color:" + bgcolor;
          }
          out.print("<div style=\"" + style + "\">");
        } else {
          // let's make an attempt to use nowrap for devices that support that
          if (nowrap_mode.equals("true")) {
            out.print("<div mode=\"nowrap\">");
          }
        }
      }

      // CHTML
      if (mark_up.startsWith("chtml")) {
        out.print("<marquee");
        if (!behavior.equals("")) {
          out.print(" behavior=\"" + behavior + "\"");
        }

        if (!direction.equals("")) {
          out.print(" direction=\"" + direction + "\"");
        }

        if (!loop.equals("")) {
          out.print(" loop=\"" + loop + "\"");
        }
        if (!bgcolor.equals("")) {
          out.print(" bgcolor=\"" + bgcolor + "\"");
        }
        out.print(">");
      }

      // WML: nothing to do
      return (EVAL_BODY_INCLUDE);

    } catch (Exception ioe) {
      System.out.println("Error in marquee tag: " + ioe);
      ioe.printStackTrace();
    }
    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() {

    try {
      JspWriter out = pageContext.getOut();

      if (mark_up.startsWith("xhtml")) {
        if (marquee_as_css.equals("true")) {
          out.print("</div>");
        } else {
          if (nowrap_mode.equals("true")) {
            out.print("</div>");
          }
        }
      }
      if (mark_up.startsWith("chtml")) {
        out.print("</marquee>");
      }
    }

    catch (IOException ioe) {
      System.out.println("Error in Tag document: " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }
}
