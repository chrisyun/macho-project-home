/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallMenu.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * The body wall:menu
 */

public class WallMenu extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest              request                  = null;

  private static CapabilityMatrix cm                       = null;

  private static UAManager        uam                      = null;

  // locals
  String                          warning                  = "";

  String                          UA                       = "";

  String                          device_id                = "";

  String                          current_bgcolor          = "bgcolor2";

  boolean                         autonumber               = false;

  int                             autonumber_index         = 0;

  String                          mark_up                  = "";

  String                          table_ok                 = "";

  String                          css_ok                   = "";

  String                          wml_menu_with_select     = "";

  boolean                         table_and_css_background = false;

  boolean                         menu_css_tag             = false;

  // Attributes
  protected boolean               colorize                 = false;

  // attribute Setters
  public void setColorize(boolean colorize) {
    this.colorize = colorize;
  }

  public void setColorize(String cl) {
    if (cl.equalsIgnoreCase("true")) {
      colorize = true;
    }
  }

  public void setAutonumber(boolean an) {
    this.autonumber = an;
  }

  public void setAutonumber(String an) {
    if (an.equalsIgnoreCase("true")) {
      this.autonumber = true;
    } else {
      this.autonumber = false;
    }
  }

  // look_back: used for status info when generating menu items.
  // boolean getColorize() { return colorize; }
  boolean getUseCssAndTableForMenu() {
    return table_and_css_background;
  }

  boolean getCSSMenuRequested() {
    return menu_css_tag;
  }

  boolean getAutonumber() {
    return autonumber;
  }

  String getAutonumber_index() {
    autonumber_index++;
    return autonumber_index + "";
  }

  String getBGColor() {
    if (current_bgcolor.equals("bgcolor1")) {
      current_bgcolor = "bgcolor2";
    } else {
      current_bgcolor = "bgcolor1";
    }
    return current_bgcolor;
  }

  /**
   * goStartTag will be called when we see the "menu" tag
   */
  @Override
  public int doStartTag() throws JspException {

    current_bgcolor = "bgcolor2";
    autonumber_index = 0;
    mark_up = "";
    table_ok = "";
    css_ok = "";
    table_and_css_background = false;
    menu_css_tag = false;

    WallBlock wblock = (WallBlock) findAncestorWithClass(this, WallBlock.class);

    if (wblock != null) {
      throw new JspException(
          "'menu' tag cannot be nested inside a 'block' tag (breaks XHTML validity and will produce an error on some browsers).\n Close or remove the containing 'block' tag.");
    }

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
      mark_up = cm.getCapabilityForDevice(device_id, "preferred_markup");
      mark_up = TagUtil.getWallMarkup(mark_up);

      // XHTML first
      if (mark_up.startsWith("xhtmlmp")) {

        // do we need to use colorized menus?
        // check that colorized=true and that the 'menu_css' tag has been
        // produced
        // WallDocument document = (WallDocument) findAncestorWithClass(this,
        // WallDocument.class);
        WallDocument document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);
        if (document == null) {
          throw new JspTagException("tag 'menu' must be nested inside a 'document' tag");
        }
        menu_css_tag = document.getCSSMenuRequested();

        warning = TagUtil.checkCapability("xhtml_supports_table_for_layout");
        warning += TagUtil.checkCapability("xhtml_supports_css_cell_table_coloring");
        if (warning.length() > 0) {
          throw new JspException(warning);
        }

        table_ok = cm.getCapabilityForDevice(device_id, "xhtml_supports_table_for_layout");
        css_ok = cm.getCapabilityForDevice(device_id, "xhtml_supports_css_cell_table_coloring");
        table_and_css_background = (table_ok.equals("true") && css_ok.equals("true"));

        // if the user wants to do it fancy and the device allows for it
        if (menu_css_tag && colorize && table_and_css_background) {
          try {
            JspWriter out = pageContext.getOut();
            out.println("<table>");
          } catch (IOException ioe) {
            System.out.println("Error in menu tag: " + ioe);
          }
          return (EVAL_BODY_INCLUDE);
        } else {
          // OK, just default plain menus
          try {
            JspWriter out = pageContext.getOut();
            out.print("<ol>");
          } catch (IOException ioe) {
            System.out.println("Error in menu tag: " + ioe);
          }
          return (EVAL_BODY_INCLUDE);
        }
      }

      // CHTML
      if (mark_up.startsWith("chtml")) {
        try {
          JspWriter out = pageContext.getOut();
          out.println("<br clear=\"all\">");
        } catch (IOException ioe) {
          System.out.println("Error in menu tag: " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

      // WML
      if (mark_up.startsWith("wml")) {

        warning = TagUtil.checkCapability("menu_with_select_element_recommended");
        if (warning.length() > 0) {
          throw new JspException(warning);
        }

        wml_menu_with_select = cm.getCapabilityForDevice(device_id, "menu_with_select_element_recommended");

        try {
          JspWriter out = pageContext.getOut();
          if (wml_menu_with_select.equals("true")) {
            out.println("<p align=\"left\" mode=\"nowrap\">");
            out.print("<select>");
          } else {
            out.print("<p>");
          }
        } catch (IOException ioe) {
          System.out.println("Error in menu tag: " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

    } catch (Exception e) {
      System.out.println("Error in Wurfl: tag 'menu': " + e.getMessage());
      System.out.println("Error: " + e.toString());
    }
    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() {

    // only need to close menus (table or 'ol') for XHTML
    if (mark_up.startsWith("xhtmlmp")) {
      // if the user wants to do it fancy and the device allows for it
      if (menu_css_tag && colorize && table_and_css_background) {
        try {
          JspWriter out = pageContext.getOut();
          out.println("</table>");
        } catch (IOException ioe) {
          System.out.println("Error in menu tag: " + ioe);
        }

      } else {
        // OK, just default plain menus
        try {
          JspWriter out = pageContext.getOut();
          out.println("</ol>");
        } catch (IOException ioe) {
          System.out.println("Error in menu tag: " + ioe);
        }
      }
    }

    if (mark_up.startsWith("wml")) {
      try {
        JspWriter out = pageContext.getOut();
        if (wml_menu_with_select.equals("true")) {
          out.println("</select>");
          out.print("</p>");
        } else {
          out.print("</p>");
        }
      } catch (IOException ioe) {
        System.out.println("Error in menu tag: " + ioe);
      }
    }

    return (EVAL_PAGE); // Continue with rest of JSP page

  }
}
