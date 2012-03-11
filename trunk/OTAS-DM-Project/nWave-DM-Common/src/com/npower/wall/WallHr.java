/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallHr.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The body-less wall:hr
 */

public class WallHr extends TagSupport {

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

  // TO-DO: add support for size, width and color attribute;
  // Attributes
  protected String                color            = "";

  // attribute setters
  public void setColor(String cl) {
    color = cl;
  }

  /**
   * goStartTag will be called when we see the "hr" tag
   */
  @Override
  public int doStartTag() throws JspException {

    WallBlock wblock = (WallBlock) findAncestorWithClass(this, WallBlock.class);
    WallMenu wmenu = (WallMenu) findAncestorWithClass(this, WallMenu.class);
    WallForm wform = (WallForm) findAncestorWithClass(this, WallForm.class);

    if (wblock != null || wform != null || wmenu != null) {
      throw new JspException(
          "'hr' tag cannot be used inside a block, form or cool_menu tag (breaks XHTML validity and will produce an error on some browsers)");
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

      capability_value = cm.getCapabilityForDevice(device_id, "preferred_markup");
      capability_value = TagUtil.getWallMarkup(capability_value);

      if (capability_value.startsWith("xhtmlmp")) {

        try {
          JspWriter out = pageContext.getOut();
          out.print("<hr");
          if (!color.equals("")) {
            out.print(" color=\"" + color + "\" style=\"border-color:" + color + ";background-color:" + color + "\"");
          }
          out.print("/>");
        } catch (IOException ioe) {
          throw new JspException("Error in 'hr' tag: " + ioe);
        }
      }

      if (capability_value.startsWith("wml")) {
        try {
          JspWriter out = pageContext.getOut();
          out.print("<p>---</p>");
        } catch (IOException ioe) {
          throw new JspException("Error in 'hr' tag: " + ioe);
        }
      }

      // CHTML <hr> without trailing slash
      if (capability_value.startsWith("chtml")) {
        try {
          JspWriter out = pageContext.getOut();
          out.print("<hr");
          if (!color.equals("")) {
            out.print(" color=\"" + color + "\"");
          }
          out.print("/>");
        } catch (IOException ioe) {
          throw new JspException("Error in 'hr' tag: " + ioe);
        }

      }

      return (SKIP_BODY);

    } catch (Exception e) {
      throw new JspException("Error in WALL tag 'hr': " + e.getMessage());
    }

  }
}
