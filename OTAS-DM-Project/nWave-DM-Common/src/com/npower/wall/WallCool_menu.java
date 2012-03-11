/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallCool_menu.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The body wall:cool_menu
 */

public class WallCool_menu extends TagSupport {

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

  String                          xhtml_table_support    = "";

  boolean                         perform_tabularization = false;

  // Cell end counter
  int                             cellnumber             = 1;

  // Attributes
  String                          colnum                 = "2";

  int                             colnum_int             = 2;

  boolean                         tabularize             = true;

  // attribute Setters
  public void setColnum(String cn) {
    this.colnum = cn;
    try {
      colnum_int = Integer.parseInt(cn);
    } catch (NumberFormatException e) {
      System.out.println("'cool_menu' tag, 'colnum' attribute. Value is NOT an integer. Default 2.");
      colnum_int = 2;
    }
  }

  public void setColnum(int cn) {
    colnum_int = cn;
    try {
      colnum = Integer.toString(cn);
    } catch (NumberFormatException e) {
      System.out.println("'cool_menu' tag, 'colnum' attribute. Value is NOT an integer. Default 2.");
      colnum = "2";
    }
  }

  public void setTabularize(boolean tblrz) {
    this.tabularize = tblrz;
  }

  public void setTabularize(String tblrz) {
    if (tblrz.equalsIgnoreCase("true")) {
      this.tabularize = true;
    } else {
      this.tabularize = false;
    }
  }

  // utility methods (to be called by 'cell' tag to decide when
  // table row needs to be opened/closed)

  public boolean isFirstRowCell() {
    if ((cellnumber % colnum_int) == 1) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isLastRowCell() {
    if ((cellnumber % colnum_int) == 0) {
      cellnumber = 1;
      return true;
    } else {
      cellnumber++;
      return false;
    }
  }

  /**
   * goStartTag will be called when we see the "cool_menu" tag
   */
  @Override
  public int doStartTag() throws JspException {

    cellnumber = 1;
    perform_tabularization = false;

    WallBlock wblock = (WallBlock) findAncestorWithClass(this, WallBlock.class);

    if (wblock != null) {
      throw new JspException(
          "'cool_menu' tag cannot be nested inside a 'block' tag (breaks XHTML validity and will produce an error on some browsers).\n Close or remove the containing 'block' tag.");
    }

    try {
      ObjectsManager objectsManager = WurflServletInit.getObjectsManager();
      cm = objectsManager.getCapabilityMatrixInstance();
      uam = objectsManager.getUAManagerInstance();
    } catch (Exception e) {
      throw new JspException(e.getMessage(), e);
    }
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

    try {
      JspWriter out = pageContext.getOut();

      // tabularize or not? look at attribute 'tabularize' and device
      // capabilities
      if (tabularize) {
        if (mark_up.startsWith("chtml")) {
          warning = TagUtil.checkCapability("chtml_table_support");
          if (warning.length() > 0) {
            throw new JspException(warning);
          }
          imode_table_support = cm.getCapabilityForDevice(device_id, "chtml_table_support");
          perform_tabularization = imode_table_support.equals("true");
        }

        if (mark_up.startsWith("xhtmlmp")) {
          warning = TagUtil.checkCapability("xhtml_table_support");
          if (warning.length() > 0) {
            throw new JspException(warning);
          }
          xhtml_table_support = cm.getCapabilityForDevice(device_id, "xhtml_table_support");
          perform_tabularization = xhtml_table_support.equals("true");
        }
      }

      if (perform_tabularization) {
        // cool_menus for XHTML
        if (mark_up.startsWith("xhtmlmp")) {
          out.print("<table class=\"coolmenu\">");
        }

        // cool_menus for some CHTML devices that support tables
        if (mark_up.startsWith("chtml")) {
          out.print("<table>");
        }
      } else {
        // fix well-formdness for XHTML
        if (mark_up.startsWith("xhtmlmp")) {
          out.print("<p>");
        }
      }

      // WML is never tabularized, no matter what. Add <p> for well-formedness
      if (mark_up.startsWith("wml")) {
        out.println("<p align=\"left\" mode=\"nowrap\">");
      }

      return (EVAL_BODY_INCLUDE);

    } catch (IOException ioe) {
      System.out.println("Error in 'cool_menu' tag: " + ioe);
    }

    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() throws JspException {

    try {
      JspWriter out = pageContext.getOut();

      if (mark_up.startsWith("wml")) {
        out.println("</p>");
      } else {
        if (perform_tabularization) {
          // need to check if tables must be closed
          // out.print(cellnumber +"-"+ colnum_int);

          // add css hook for XHTML but not CHTML
          String css_for_xhtml = "";
          if (mark_up.startsWith("xhtmlmp")) {
            css_for_xhtml = " class=\"coolmenu\"";
          }

          int start = cellnumber % colnum_int;
          if (start != 1) {
            if (start == 0) {
              out.print("<td" + css_for_xhtml + "></td>");
            } else {
              for (int i = start; i <= colnum_int; i++) {
                out.print("<td" + css_for_xhtml + "></td>");
              }
            }
            out.print("</tr>");
          }
          out.print("</table>");
        } else {
          // fix well-formdness for XHTML
          if (mark_up.startsWith("xhtmlmp")) {
            out.print("</p>");
          }
        }
      }

    } catch (IOException ioe) {
      throw new JspException("Error in 'cool_menu' tag: " + ioe);
    }

    return (EVAL_PAGE); // Continue with rest of JSP page

  }

  @Override
  public void release() {

    // Cell end counter
    cellnumber = 1;
  }

}
