/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallSelect.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The wall:select
 */

public class WallSelect extends TagSupport {

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

  String                          doctitle         = "";

  boolean                         enforce_title    = false;

  // Attributes
  String                          disabled         = "";

  String                          name             = "";

  String                          size             = "";

  String                          title            = "";

  String                          multiple         = "";

  // accessors
  public void setDisabled(String d) {
    this.disabled = d;
  }

  public String getDisabled() {
    return disabled;
  }

  public void setName(String n) {
    this.name = n;
  }

  public String getName() {
    return name;
  }

  public void setSize(String s) {
    this.size = s;
  }

  public String getSize() {
    return size;
  }

  public void setTitle(String t) {
    this.title = t;
  }

  public String getTitle() {
    return title;
  }

  public void setMultiple(String m) {
    this.multiple = m;
  }

  public String getMultiple() {
    return multiple;
  }

  /**
   * goStartTag will be called when we see the "select" tag
   */
  @Override
  public int doStartTag() throws JspException {

    // WallDocument document = (WallDocument) findAncestorWithClass(this,
    // WallDocument.class);
    WallDocument document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);
    if (document == null) {
      throw new JspTagException("tag 'select' must be nested inside a 'document' tag");
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

      JspWriter out = pageContext.getOut();
      out.print("<select name=\"" + name + "\"");
      if (!title.equals("")) {
        out.print(" title=\"" + title + "\"");
      }
      // wml
      if (capability_value.startsWith("wml")) {
        document.addFieldName(name);
        if (multiple.equals("multiple")) {
          out.print(" multiple=\"true\"");
        }
      } else { // XHTML/CHTML
        if (multiple.equals("multiple")) {
          out.print(" multiple=\"multiple\"");
        }
        if (!size.equals("")) {
          out.print(" size=\"" + size + "\"");
        }
      }
      out.print(">");

      return (EVAL_BODY_INCLUDE);

    } catch (Exception ioe) {
      System.out.println("Error in 'select' tag: " + ioe);
      ioe.printStackTrace();
    }

    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() {

    try {
      JspWriter out = pageContext.getOut();
      out.print("</select>");
    } catch (IOException ioe) {
      System.out.println("Error in Tag select: " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }

}
