/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallTitle.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * The body wall:title
 */

public class WallTitle extends BodyTagSupport {

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

  String                          title            = "";

  WallDocument                    document         = null;

  WallHead                        head             = null;

  // Attributes
  protected boolean               enforce_title    = false;

  // attribute Setters
  public void setEnforce_title(String et) {
    enforce_title = true;
    if (et.equalsIgnoreCase("false")) {
      enforce_title = false;
    }
  }

  public void setEnforce_title(boolean et) {
    enforce_title = et;
  }

  /**
   * goStartTag will be called when we see the "title" tag
   */
  @Override
  public int doStartTag() throws JspException {

    // document = (WallDocument) findAncestorWithClass(this,
    // WallDocument.class);
    document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);
    if (document == null) {
      throw new JspTagException("tag 'title' must be nested inside a 'document' tag");
    }
    head = (WallHead) findAncestorWithClass(this, WallHead.class);
    if (document == null) {
      throw new JspTagException("tag 'title' must be nested inside a 'head' tag");
    }
    document.setEnforceTitle(enforce_title);

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

      // title tag works for HTML and XHTML
      if ((capability_value.startsWith("xhtmlmp")) || (capability_value.startsWith("chtml"))) {
        try {
          JspWriter out = pageContext.getOut();
          out.print("<title>");
        } catch (IOException ioe) {
          System.out.println("Error in 'title' tag (xhtml|chtml): " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

      if (capability_value.startsWith("wml")) {
        try {
          // we need to make sure that no empty <head> tag is produced for WML
          JspWriter out = pageContext.getOut();
          out.print("<meta name=\"taglib\" content=\"WALL\" />");

        } catch (IOException ioe) {
          System.out.println("Error in 'title' tag (wml): " + ioe);
        }
        return (EVAL_BODY_BUFFERED);
      }

    } catch (Exception e) {
      throw new JspException("Error in Wurfl: tag 'title': " + e.getMessage());
    }
    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() {

    if ((capability_value.startsWith("xhtmlmp")) || (capability_value.startsWith("chtml"))) {
      try {
        JspWriter out = pageContext.getOut();
        out.print("</title>");
      } catch (IOException ioe) {
        System.out.println("Error in Tag title: " + ioe);
      }
    }

    if (capability_value.startsWith("wml")) {

      // for WML we need to tell the document about the title for later
      // rendering
      BodyContent body = getBodyContent();
      title = body.getString();
      document.setDocumentTitle(title);

    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }

}
