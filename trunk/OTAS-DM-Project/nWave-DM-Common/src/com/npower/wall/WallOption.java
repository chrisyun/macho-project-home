/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallOption.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * The wall:option
 * 
 * @see BodyTagSupport
 */

public class WallOption extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest              request   = null;

  private static CapabilityMatrix cm        = null;

  private static UAManager        uam       = null;

  // locals
  String                          warning   = "";

  String                          UA        = "";

  String                          device_id = "";

  String                          mark_up   = "";

  // attributes
  String                          value     = "";

  String                          selected  = "";

  // accessors
  public void setValue(String v) {
    this.value = v;
  }

  public void setSelected(String s) {
    selected = s;
  }

  /**
   * goStartTag will be called when we see the "option" tag
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
      mark_up = cm.getCapabilityForDevice(device_id, "preferred_markup");
      mark_up = TagUtil.getWallMarkup(mark_up);

      WallSelect select = (WallSelect) findAncestorWithClass(this, WallSelect.class);
      if (select == null) {
        throw new JspTagException("tag 'option' must be nested inside a 'select' tag");
      }

      JspWriter out = pageContext.getOut();
      out.print("  <option value=\"" + value + "\"");
      if (selected.equals("selected")) {

        if (mark_up.startsWith("xhtmlmp")) {
          out.print(" selected=\"selected\"");
        }
        if (mark_up.startsWith("chtml")) {
          out.print(" selected");
        }
      }
      out.print(">");

      return (EVAL_BODY_INCLUDE);

    } catch (Exception ioe) {
      System.out.println("Error in option tag: " + ioe);
      ioe.printStackTrace();
    }
    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() {

    try {
      JspWriter out = pageContext.getOut();
      out.print("</option>");
    }

    catch (IOException ioe) {
      System.out.println("Error in Tag document: " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }
}
