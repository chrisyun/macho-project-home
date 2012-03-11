/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallCool_menu_css.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The body-less wall:cool_menu_css
 * 
 * @see BodyTagSupport
 */

public class WallCool_menu_css extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest              request             = null;

  private static CapabilityMatrix cm                  = null;

  private static UAManager        uam                 = null;

  // locals
  String                          warning             = "";

  String                          UA                  = "";

  String                          device_id           = "";

  String                          mark_up             = "";

  String                          css_table_coloring  = "";

  String                          table_for_layout    = "";

  String                          width               = "50%";

  String                          xhtml_table_support = "";

  boolean                         create_table_css    = false;

  // Attributes
  String                          colnum              = "2";

  int                             colnum_int          = 2;

  boolean                         tabularize          = true;

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

  /**
   * goStartTag will be called when we see the "cool_menu_css" tag
   */
  @Override
  public int doStartTag() throws JspException {

    xhtml_table_support = "";
    create_table_css = false;

    // let's check that the tag is being used properly (inside head and
    // document)
    // WallDocument document = (WallDocument) findAncestorWithClass(this,
    // WallDocument.class);
    WallDocument document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);

    WallHead head = (WallHead) findAncestorWithClass(this, WallHead.class);
    if (document == null || head == null) {
      throw new JspTagException("tag 'cool_menu_css' must be nested inside 'head' and 'document' tags");
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

      // only makes sense for XHTML
      if ((mark_up.startsWith("xhtmlmp"))) {
        // compute right CSS based on number of cols
        width = "50%";
        if (colnum_int != 2) {
          int width_int = 100 / colnum_int;
          width = width_int + "%";
        }

        warning = TagUtil.checkCapability("xhtml_table_support");
        if (warning.length() > 0) {
          throw new JspException(warning);
        }
        xhtml_table_support = cm.getCapabilityForDevice(device_id, "xhtml_table_support");
        create_table_css = tabularize && xhtml_table_support.equals("true");

        try {
          JspWriter out = pageContext.getOut();
          out.println("<style>");

          if (create_table_css) {
            // this CSS only makes sense if we are using tables for formatting
            // and the XHTML device supports table
            out.println(" table.coolmenu {width:100%}");
            out.println(" td.coolmenu {text-align:center;font-size:smaller;width:" + width + ";vertical-align:top;}");
            out.println(" img.coolmenu {vertical-align:top;}");
          } else {
            // we need one dummy entry to avoid potentially empty 'style' tags
            out.println(" .noneedtomatchanything {font-size:100%}");
          }

        } catch (IOException ioe) {
          System.out.println("Error in cool_menu_css tag: " + ioe);
        }
        // why not allow people to add extra CSS info in the body of the tag for
        // XHTML
        // such as: table { color:blue }; tr { background-color: red };
        return (EVAL_BODY_INCLUDE);

      }

      // WML, CHTML and bad XHTML implementations get no CSS

      return (SKIP_BODY);

    } catch (Exception e) {
      throw new JspException("Error in Wurfl: tag 'cool_menu_css': " + e.getMessage() + "Error: " + e.toString());
    }
  }

  @Override
  public int doEndTag() throws JspException {

    try {
      JspWriter out = pageContext.getOut();

      // XHTML-MP
      if (mark_up.startsWith("xhtmlmp")) {
        out.print("</style>");
      }
    } catch (IOException ioe) {
      throw new JspException("Error in Tag cell: " + ioe);
    }

    return (EVAL_PAGE); // Continue with rest of JSP page
  }

}
