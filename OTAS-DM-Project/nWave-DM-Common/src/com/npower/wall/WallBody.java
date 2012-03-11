/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallBody.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The body-less wall:body
 */

public class WallBody extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest              request               = null;

  private static CapabilityMatrix cm                    = null;

  private static UAManager        uam                   = null;

  // locals
  String                          warning               = "";

  String                          UA                    = "";

  String                          device_id             = "";

  String                          capability_value      = "";

  String                          doctitle              = "";

  boolean                         enforce_title         = false;

  // Attributes
  String                          wml_back_button_label = "Back";

  String                          bgcolor               = "";

  String                          text                  = "";

  boolean                         disable_wml_template  = false;

  boolean                         newcontext            = false;

  // attribute Setters
  public void setWml_back_button_label(String wml_back_button_label) {
    this.wml_back_button_label = wml_back_button_label;
  }

  public void setBgcolor(String bg) {
    this.bgcolor = bg;
  }

  public void setText(String t) {
    this.text = t;
  }

  public void setDisable_wml_template(boolean dwt) {
    this.disable_wml_template = dwt;
  }

  public void setDisable_wml_template(String dwt) {
    if (dwt.equalsIgnoreCase("true")) {
      this.disable_wml_template = true;
    } else {
      this.disable_wml_template = false;
    }
  }

  public void setNewcontext(boolean nct) {
    this.newcontext = nct;
  }

  public void setNewcontext(String nct) {
    if (nct.equalsIgnoreCase("true")) {
      this.newcontext = true;
    } else {
      this.newcontext = false;
    }
  }

  /**
   * goStartTag will be called when we see the "body" tag
   */
  @Override
  public int doStartTag() throws JspException {

    // WallDocument document = (WallDocument) findAncestorWithClass(this,
    // WallDocument.class);
    WallDocument document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);
    if (document == null) {
      throw new JspTagException("tag 'body' must be nested inside a 'document' tag");
    }
    enforce_title = document.getEnforceTitle();
    doctitle = document.getDocumentTitle();

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

      if ((capability_value.startsWith("xhtmlmp")) || (capability_value.startsWith("chtml"))) {

        try {
          JspWriter out = pageContext.getOut();
          out.print("<body");
          if (capability_value.startsWith("xhtmlmp")) {
            String style = "";
            if (!text.equals("")) {
              style += "color:" + text;
            }
            if (!bgcolor.equals("")) {
              style += ";background-color:" + bgcolor;
            }
            if (!style.equals("")) {
              out.print(" style=\"" + style + "\"");
            }
          } else {
            // CHTML
            if (!text.equals("")) {
              out.print(" text=\"" + text + "\"");
            }
            if (!bgcolor.equals("")) {
              out.print(" bgcolor=\"" + bgcolor + "\"");
            }
          }
          out.print(">");

          if (enforce_title) {
            // do we need an extra title
            warning = TagUtil.checkCapability("xhtml_document_title_support");
            if (warning.length() > 0) {
              throw new JspException(warning);
            }
            String xhtml_extra_title_str = cm.getCapabilityForDevice(device_id, "xhtml_document_title_support");
            if (xhtml_extra_title_str.equals("false")) {
              out.print("<p>" + document.getDocumentTitle() + "</p>");
            }
          }

        } catch (IOException ioe) {
          System.out.println("Error in body tag: " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

      if (capability_value.startsWith("wml")) {

        try {
          JspWriter out = pageContext.getOut();
          // back button for WML phones which don't have it natively
          warning = TagUtil.checkCapability("built_in_back_button_support");
          if (warning.length() > 0) {
            out.println(warning);
            throw new JspException(warning);
          }
          String back_button_support = cm.getCapabilityForDevice(device_id, "built_in_back_button_support");
          if (back_button_support.equals("false") && !disable_wml_template) {
            out.println("<template>");
            out.println(" <do type=\"prev\" label=\"" + wml_back_button_label + "\">");
            out.println("  <prev/>");
            out.println(" </do>");
            out.println("</template>");
          }

          out.print("<card id=\"w\" title=\"" + doctitle + "\"");
          if (newcontext) {
            out.print(" newcontext=\"true\"");
          }
          out.print(">");
          if (enforce_title) {
            // do we need an extra title
            warning = TagUtil.checkCapability("card_title_support");
            if (warning.length() > 0) {
              throw new JspException(warning);
            }
            String wml_extra_title_str = cm.getCapabilityForDevice(device_id, "card_title_support");
            if (wml_extra_title_str.equals("false")) {
              out.println("<p>" + doctitle + "</p>");
            }
          }

        } catch (IOException ioe) {
          System.out.println("Error in head tag: " + ioe);
        }
        return (EVAL_BODY_INCLUDE);
      }

    } catch (Exception e) {
      System.out.println("Error in Wurfl: tag 'body': " + e.getMessage());
      System.out.println("Error: " + e.toString());
    }
    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() {

    try {
      JspWriter out = pageContext.getOut();

      if (capability_value.startsWith("wml")) {
        out.print("</card>");
      }
      if ((capability_value.startsWith("xhtmlmp")) || (capability_value.startsWith("chtml"))) {
        out.print("</body>");
      }
    } catch (IOException ioe) {
      System.out.println("Error in Tag body: " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }

}
