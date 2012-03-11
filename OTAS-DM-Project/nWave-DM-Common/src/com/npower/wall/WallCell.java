/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallCell.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * The wall:cell (requires nesting inside coolmenu)
 */

public class WallCell extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest              request                = null;

  private static CapabilityMatrix cm                     = null;

  private static UAManager        uam                    = null;

  // locals
  String                          warning                = "";

  String                          UA                     = "";

  String                          device_id              = "";

  String                          capability_value       = "";

  String                          mark_up                = "";

  String                          imode_table_support    = "";

  boolean                         perform_tabularization = false;

  WallCool_menu                   cool_menu              = null;

  /**
   * goStartTag will be called when we see the "cell" tag
   */
  @Override
  public int doStartTag() throws JspException {

    cool_menu = (WallCool_menu) findAncestorWithClass(this, WallCool_menu.class);
    if (cool_menu == null) {
      throw new JspTagException("tag 'cell' must be nested inside a 'cool_menu' tag");
    }
    // asl the containing cool_menu tag if tabularization must be performed
    perform_tabularization = cool_menu.perform_tabularization;

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
      mark_up = TagUtil.getWallMarkup(capability_value);

      JspWriter out = pageContext.getOut();

      // XHTML-MP and CHTML
      if (!mark_up.startsWith("wml")) {
        if (perform_tabularization) {
          if (cool_menu.isFirstRowCell()) {
            out.print("<tr>");
          }
          // add css hook for XHTML but not CHTML
          String css_for_xhtml = "";
          if (mark_up.startsWith("xhtmlmp")) {
            css_for_xhtml = " class=\"coolmenu\"";
          }
          out.print("<td" + css_for_xhtml + ">");
        }
      }

      return (EVAL_BODY_INCLUDE);

    } catch (Exception ioe) {
      throw new JspTagException("Error in 'cell' tag: " + ioe, ioe);
    }
  }

  @Override
  public int doEndTag() throws JspException {

    try {
      JspWriter out = pageContext.getOut();

      // WML
      if (mark_up.startsWith("wml")) {
        out.print("<br/>");
      } else {

        // XHTML and CHTML
        if (perform_tabularization) {
          out.print("</td>");
          if (cool_menu.isLastRowCell()) {
            out.print("</tr>");
          }
        } else {
          if (mark_up.startsWith("chtml")) {
            out.print("<br>");
          } else {
            out.print("<br/>");
          }
        }
      }
    } catch (IOException ioe) {
      throw new JspException("Error in Tag 'cell': " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }
}
