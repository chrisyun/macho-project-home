/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallXmlpidtd.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The body-less wall:xmlpidtd
 * 
 * @see BodyTagSupport
 */

public class WallXmlpidtd extends TagSupport {

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

  // attributes
  protected String                encoding         = null;

  // setters and getters for 'encoding' attribute
  public void setEncoding(String e) {
    this.encoding = e;
  }

  public String getEncoding() {
    return this.encoding;
  }

  /**
   * goStartTag will be called when we see the "xmlpidtd" tag
   */
  @Override
  public int doStartTag() throws JspException {

    // check if we can use extensions
    // WallDocument document = (WallDocument) findAncestorWithClass(this,
    // WallDocument.class);
    WallDocument document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);

    if (document == null) {
      throw new JspTagException("tag 'xmlpidtd' must be nested inside a 'document' tag");
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
          out.print("<?xml version=\"1.0\"");
          if (encoding != null)
            out.print(" encoding=\"" + encoding + "\"");
          out.println("?>");
          boolean use_opwv_dtd = document.getUseXHTMLExtensions();
          if (use_opwv_dtd) {
            out.print("<!DOCTYPE html PUBLIC \"-//OPENWAVE//DTD XHTML Mobile 1.0//EN\"");
            out.println(" \"http://www.openwave.com/DTD/xhtml-mobile10.dtd\">");
          } else {
            out.print("<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.0//EN\"");
            out.println(" \"http://www.wapforum.org/DTD/xhtml-mobile10.dtd\">");
          }
        } catch (IOException ioe) {
          System.out.println("Error in xmlpidtd tag: " + ioe);
        }
        return (SKIP_BODY);
      }
      if (capability_value.startsWith("wml")) {
        try {
          JspWriter out = pageContext.getOut();
          out.print("<?xml version=\"1.0\"");
          if (encoding != null)
            out.print(" encoding=\"" + encoding + "\"");
          out.println("?>");
          boolean use_opwv_dtd = document.getUseWMLExtensions();
          if (use_opwv_dtd) {
            out.print("<!DOCTYPE wml PUBLIC \"-//PHONE.COM//DTD WML 1.1//EN\" ");
            out.print("\"http://www.phone.com/dtd/wml11.dtd\" >");
          } else {
            out.print("<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//EN\" ");
            out.print("\"http://www.wapforum.org/DTD/wml_1.1.xml\">");
          }

        } catch (IOException ioe) {
          System.out.println("Error in xmlpidtd tag: " + ioe);
        }
        return (SKIP_BODY);

      }

      // CHTML = Nothing to DO! (same goes for unrecognized)

      return (SKIP_BODY);

    } catch (Exception e) {
      System.out.println("Error in Wurfl: tag 'wmlpidtd': " + e.getMessage());
      System.out.println("Error: " + e.toString());
    }
    return (SKIP_BODY);
  }
}
