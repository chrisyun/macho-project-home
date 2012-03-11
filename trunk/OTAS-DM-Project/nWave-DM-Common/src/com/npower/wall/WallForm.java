/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallForm.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;
import java.util.ArrayList;

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
 * wall:form Forms. Comes in two flavours. XHTML/XHTML or WML/CHTML/XHTML
 */

public class WallForm extends TagSupport {

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

  WallDocument                    document         = null;

  // Attributes
  protected boolean               enable_wml       = false;

  protected String                action           = "";

  protected String                method           = "get";

  // attribute Setters
  public void setAction(String action) {
    this.action = action;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setEnable_wml(boolean ew) {
    this.enable_wml = ew;
  }

  public void setEnable_wml(String ew) {
    this.enable_wml = false;
    if (ew.equals("true")) {
      this.enable_wml = true;
    }
  }

  /**
   * goStartTag will be called when we see the "form" tag
   */
  @Override
  public int doStartTag() throws JspException {

    // resetting tag state

    WallBlock wblock = (WallBlock) findAncestorWithClass(this, WallBlock.class);

    if (wblock != null) {
      throw new JspException(
          "'form' tag cannot be nested inside a 'block' tag (breaks XHTML validity and will produce an error on some browsers).\n Close or remove the containing 'block' tag.");
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

      if (capability_value.startsWith("xhtml")) {
        JspWriter out = pageContext.getOut();
        out.print("<form action=\"" + action + "\"");
        if (!method.equals("")) {
          out.print(" method=\"" + method + "\"");
        }
        out.println(">");
        out.print(" <p>");
        return (EVAL_BODY_INCLUDE);
      }
      if (capability_value.startsWith("chtml")) {
        JspWriter out = pageContext.getOut();
        out.print("<form action=\"" + action + "\"");
        if (!method.equals("")) {
          out.print(" method=\"" + method + "\"");
        }
        out.print(">");
        return (EVAL_BODY_INCLUDE);
      }

      // WAP 1.X not admitted unless explicitly enabled by the programmer
      if (!enable_wml && (capability_value.startsWith("wml"))) {
        String err_msg = "This page is not available to WAP 1.X devices.\n"
            + "If you think this is an error, please contact your service provider.";
        JspWriter out = pageContext.getOut();
        out.println("<p>" + err_msg + "</p>");
        // throw new JspException(err_msg);
      }
      // support WML.
      if (enable_wml && (capability_value.startsWith("wml"))) {
        // document = (WallDocument) findAncestorWithClass(this,
        // WallDocument.class);
        document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);
        if (document == null) {
          throw new JspTagException("tag 'form' (wml enabled) must be nested inside a 'document' tag");
        }
        document.setFormAction(action);
        document.setFormMethod(method);
        JspWriter out = pageContext.getOut();
        out.println("<p>");
        return (EVAL_BODY_INCLUDE);
      }

    } catch (Exception e) {
      System.out.println("Error in WALL tag 'form': " + e.getMessage());
      System.out.println("Error: " + e.toString());
    }
    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() {

    try {
      JspWriter out = pageContext.getOut();

      if (capability_value.startsWith("xhtmlmp")) {
        out.println(" </p>");
        out.print("</form>");
      }
      if (capability_value.startsWith("chtml")) {
        out.print("</form>");
      }

      if (enable_wml && (capability_value.startsWith("wml"))) {

        @SuppressWarnings("unused")
        String action = document.getFormAction();
        @SuppressWarnings("unused")
        String method = document.getFormMethod();
        @SuppressWarnings("unused")
        ArrayList<?> al = document.getFormFields();
        out.println("</p>");
      }

    } catch (IOException ioe) {
      System.out.println("Error in 'form' tag: " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }

}
