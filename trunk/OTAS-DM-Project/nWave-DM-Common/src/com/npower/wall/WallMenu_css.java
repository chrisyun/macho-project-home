/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallMenu_css.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * The body-less wall:menu_css
 * 
 * @see BodyTagSupport
 */

public class WallMenu_css extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest              request            = null;

  private static CapabilityMatrix cm                 = null;

  private static UAManager        uam                = null;

  // locals
  String                          warning            = "";

  String                          UA                 = "";

  String                          device_id          = "";

  String                          mark_up            = "";

  String                          css_table_coloring = "";

  String                          table_for_layout   = "";

  // attributes
  String                          bgcolor1           = "";

  String                          bgcolor2           = "";

  // accessors
  public void setBgcolor1(String bgc1) {
    bgcolor1 = bgc1;
  }

  public void setBgcolor2(String bgc2) {
    bgcolor2 = bgc2;
  }

  /**
   * goStartTag will be called when we see the "menu_css" tag
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
      warning += TagUtil.checkCapability("xhtml_supports_css_cell_table_coloring");
      warning += TagUtil.checkCapability("xhtml_supports_table_for_layout");
      if (warning.length() > 0) {
        throw new JspException(warning);
      }

      // get the user agent
      UA = TagUtil.getUA(this.request);

      device_id = uam.getDeviceIDFromUALoose(UA);

      mark_up = cm.getCapabilityForDevice(device_id, "preferred_markup");
      mark_up = TagUtil.getWallMarkup(mark_up);
      css_table_coloring = cm.getCapabilityForDevice(device_id, "xhtml_supports_css_cell_table_coloring");
      table_for_layout = cm.getCapabilityForDevice(device_id, "xhtml_supports_table_for_layout");

      // only makes sense for good CSS implementations
      if ((mark_up.startsWith("xhtmlmp")) && css_table_coloring.equals("true") && table_for_layout.equals("true")) {
        try {
          warning = TagUtil.checkCapability("xhtml_readable_background_color1");
          warning += TagUtil.checkCapability("xhtml_readable_background_color2");
          if (warning.length() > 0) {
            throw new JspException(warning);
          }
          // let the WURFL do it, unless different values are specified in the
          // tag
          if (bgcolor1.equals("")) {
            bgcolor1 = cm.getCapabilityForDevice(device_id, "xhtml_readable_background_color1");
          }
          if (bgcolor2.equals("")) {
            bgcolor2 = cm.getCapabilityForDevice(device_id, "xhtml_readable_background_color2");
          }
          JspWriter out = pageContext.getOut();
          out.println("<style>");
          out.println("  .bgcolor1 { background-color:" + bgcolor1 + ";}");
          out.println("  .bgcolor2 { background-color:" + bgcolor2 + ";}");
          out.println("</style>");

          // look-back: let the <document> tag know that the style for the menu
          // has been created
          // WallDocument document = (WallDocument) findAncestorWithClass(this,
          // WallDocument.class);
          WallDocument document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);
          WallHead head = (WallHead) findAncestorWithClass(this, WallHead.class);
          if (document == null || head == null) {
            throw new JspTagException("tag 'menu_css' must be nested inside 'head' and 'document' tags");
          }
          document.setCSSMenuRequested();

        } catch (IOException ioe) {
          System.out.println("Error in menu_css tag: " + ioe);
        }
        return (SKIP_BODY);
      }

      // WML, CHTML and bad XHTML implementations get no CSS

      return (SKIP_BODY);

    } catch (Exception e) {
      System.out.println("Error in Wurfl: tag 'menu_css': " + e.getMessage());
      System.out.println("Error: " + e.toString());
    }
    return (SKIP_BODY);
  }
}
