/*
 ***************************************************************************
 * Copyright 2004 Luca Passani                                             *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallHead.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The body wall:head
 */

public class WallHead extends TagSupport {

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

  /**
   * goStartTag will be called when we see the "head" tag
   */
  @Override
  public int doStartTag() throws JspException {

    try {
      ObjectsManager objectsManager = WurflServletInit.getObjectsManager();
      cm = objectsManager.getCapabilityMatrixInstance();
      uam = objectsManager.getUAManagerInstance();
      request = (HttpServletRequest) pageContext.getRequest();

      // WallDocument document = (WallDocument) findAncestorWithClass(this,
      // WallDocument.class);
      WallDocument document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);
      if (document == null) {
        throw new JspTagException("tag 'head' must be nested inside a 'document' tag");
      }

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

      // title tag works for HTML and XHTML
      if ((capability_value.startsWith("xhtmlmp")) || (capability_value.startsWith("chtml"))) {
        try {
          JspWriter out = pageContext.getOut();

          if (capability_value.startsWith("xhtmlmp")) {
            out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">");
            out.print("<head>");
            if (document.getDisable_cache()) {
              out.print("\n <meta http-equiv=\"Cache-Control\" ");
              out.println("content=\"max-age=0\"/>");
              out.print(" <meta http-equiv=\"Cache-Control\" ");
              out.println("content=\"no-store\"/>");
            }
          } else {
            out.println("<html>");
            out.print("<head>");
          }

        } catch (IOException ioe) {
          throw new JspTagException("Error in head tag: " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

      if (capability_value.startsWith("wml")) {

        try {
          JspWriter out = pageContext.getOut();
          out.println("<wml>");
          out.print("<head>");
          if (document.getDisable_cache()) {
            out.print("\n <meta forua=\"true\" http-equiv=\"Cache-Control\" ");
            out.println("content=\"max-age=0\"/>");
          }
        } catch (IOException ioe) {
          System.out.println("Error in head tag: " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

    } catch (Exception e) {
      throw new JspTagException("Error in Wurfl: tag 'head': " + e.getMessage() + "\n" + "Error: " + e.toString());
    }
    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() throws JspTagException {
    // easy method. just close <head> regardless of the mark-up
    try {
      JspWriter out = pageContext.getOut();
      out.print("</head>");
    } catch (IOException ioe) {
      throw new JspTagException("Error in Tag head: " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }

}
