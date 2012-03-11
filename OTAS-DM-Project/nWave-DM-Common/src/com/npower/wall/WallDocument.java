/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallDocument.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * wall:document this tag is useful to store info global to the document
 */

public class WallDocument extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest              request                         = null;

  HttpServletResponse             response                        = null;

  private static CapabilityMatrix cm                              = null;

  private static UAManager        uam                             = null;

  // private static ListManager lm = null;

  // locals
  String                          warning                         = "";

  String                          UA                              = "";

  String                          device_id                       = "";

  String                          capability_value                = "";

  String                          title                           = "";

  boolean                         enforce_title                   = false;

  boolean                         uplink                          = false;

  boolean                         use_wml_extensions              = false;

  boolean                         use_xhtml_extensions            = false;

  // HashMap allCapas = null;

  // attributes
  protected boolean               disable_wml_extensions          = false;

  protected boolean               disable_xhtml_extensions        = false;

  protected boolean               disable_cache                   = false;

  protected boolean               disable_content_type_generation = false;

  // storage for look-back. Tags store info in the variables below
  boolean                         css_menu_requested              = false;

  String                          formAction                      = "";

  String                          formMethod                      = "";

  ArrayList<Object>                       formFields                      = new ArrayList<Object>();

  HashMap<Object, Object>                         hiddenFields                    = new HashMap<Object, Object>();

  // look-back related methods
  void setCSSMenuRequested() {
    css_menu_requested = true;
  }

  boolean getCSSMenuRequested() {
    return css_menu_requested;
  }

  void setDocumentTitle(String tl) {
    this.title = tl;
  }

  String getDocumentTitle() {
    return this.title;
  }

  void setEnforceTitle(boolean et) {
    this.enforce_title = et;
  }

  boolean getEnforceTitle() {
    return this.enforce_title;
  }

  void setFormAction(String a) {
    formAction = a;
  }

  String getFormAction() {
    return formAction;
  }

  void setFormMethod(String m) {
    formMethod = m;
  }

  String getFormMethod() {
    return formMethod;
  }

  void addFieldName(String ff) {
    formFields.add(ff);
  }

  ArrayList<Object> getFormFields() {
    return formFields;
  }

  void addHiddenField(String name, String value) {
    hiddenFields.put(name, value);
  }

  HashMap<Object, Object> getHiddenFields() {
    return hiddenFields;
  }

  boolean getUseWMLExtensions() {
    return use_wml_extensions;
  }

  boolean getUseXHTMLExtensions() {
    return use_xhtml_extensions;
  }

  boolean getDisable_cache() {
    return disable_cache;
  }

  // attribute Setters
  // wml_extensions
  public void setDisable_wml_extensions(boolean dwe) {
    this.disable_wml_extensions = dwe;
  }

  public void setDisable_wml_extensions(String dwe) {
    if (dwe.equalsIgnoreCase("true")) {
      this.disable_wml_extensions = true;
    } else {
      this.disable_wml_extensions = false;
    }
  }

  // xhtml_extensions
  public void setDisable_xhtml_extensions(boolean dwe) {
    this.disable_xhtml_extensions = dwe;
  }

  public void setDisable_xhtml_extensions(String dwe) {
    if (dwe.equalsIgnoreCase("true")) {
      this.disable_xhtml_extensions = true;
    } else {
      this.disable_xhtml_extensions = false;
    }
  }

  // cache
  public void setDisable_cache(boolean dc) {
    this.disable_cache = dc;
  }

  public void setDisable_cache(String dc) {
    if (dc.equalsIgnoreCase("true")) {
      this.disable_cache = true;
    } else {
      this.disable_cache = false;
    }
  }

  // content type
  public void setDisable_content_type_generation(boolean dctg) {
    this.disable_content_type_generation = dctg;
  }

  public void setDisable_content_type_generation(String dctg) {
    if (dctg.equalsIgnoreCase("true")) {
      this.disable_content_type_generation = true;
    } else {
      this.disable_content_type_generation = false;
    }
  }

  /**
   * goStartTag will be called when we see the "document" tag
   */
  @Override
  public int doStartTag() throws JspException {

    // resetting tag state
    css_menu_requested = false;
    title = "";
    enforce_title = false;
    formAction = "";
    formMethod = "";
    formFields.clear();
    hiddenFields.clear();
    uplink = false;

    // register WallDocument class with request to make it
    // easy for other objects to find (and to integrate with Struts tiles)
    pageContext.setAttribute("wall-document", this, PageContext.REQUEST_SCOPE);

    try {
      ObjectsManager objectsManager = WurflServletInit.getObjectsManager();
      cm = objectsManager.getCapabilityMatrixInstance();
      uam = objectsManager.getUAManagerInstance();
      // lm = ObjectsManager.getListManagerInstance();

      request = (HttpServletRequest) pageContext.getRequest();
      response = (HttpServletResponse) pageContext.getResponse();

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

      // let's make that all capabilities are available to the JSTL as
      // ${capabilities.capability_name}
      // allCapas = lm.getCapabilitiesForDeviceID(device_id);
      // pageContext.setAttribute("capabilities",allCapas);

      // let's do some checking that everything is fine
      if (!capability_value.startsWith("xhtmlmp") && !capability_value.startsWith("chtml")
          && !capability_value.startsWith("wml")) {
        throw new JspException("No Valid mark up found for device id =" + device_id + ". 'preferred_markup'="
            + capability_value);
      }

      // figure out if extensions can be used or not
      uplink = TagUtil.isUplink(this.request);

      // WAP 1.X requires a different mime type
      if (capability_value.startsWith("xhtmlmp")) {

        warning = TagUtil.checkCapability("opwv_xhtml_extensions_support");
        if (warning.length() > 0) {
          throw new JspException(warning);
        }
        String opwv_ext_string = cm.getCapabilityForDevice(device_id, "opwv_xhtml_extensions_support");
        boolean opwv_xhtml_extensions_supported = opwv_ext_string.equals("true");

        use_xhtml_extensions = !disable_xhtml_extensions && uplink && opwv_xhtml_extensions_supported;

      }

      // WAP 1.X requires a different mime type
      if (capability_value.startsWith("wml")) {
        if (!disable_content_type_generation) {
          response.setContentType("text/vnd.wap.wml");
        }
        warning = TagUtil.checkCapability("opwv_wml_extensions_support");
        if (warning.length() > 0) {
          throw new JspException(warning);
        }
        String opwv_ext_string = cm.getCapabilityForDevice(device_id, "opwv_wml_extensions_support");
        boolean opwv_wml_extensions_supported = opwv_ext_string.equals("true");

        use_wml_extensions = !disable_wml_extensions && uplink && opwv_wml_extensions_supported;

      }

      // XHTML MP may require a different mime type (different from 'text/html'
      if (capability_value.startsWith("xhtmlmp")) {

        if (!disable_content_type_generation) {
          warning = TagUtil.checkCapability("xhtmlmp_preferred_mime_type");
          if (warning.length() > 0) {
            throw new JspException(warning);
          }
          String mime_type = cm.getCapabilityForDevice(device_id, "xhtmlmp_preferred_mime_type");
          response.setContentType(mime_type);
        }
      }
      // let's add headers to disable cache if requested
      if (disable_cache) {
        response.addHeader("Cache-Control", "no-cache, must-revalidate");
        response.addHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Sun, 12 Jul 1970 2:00:00 GMT");
      }

      return (EVAL_BODY_INCLUDE);

    } catch (Exception e) {
      throw new JspException("Error in WALL tag 'document': " + e.getMessage());
    }
  }

  @Override
  public int doEndTag() {

    try {
      JspWriter out = pageContext.getOut();

      if (capability_value.startsWith("wml")) {
        out.print("</wml>");
      }
      if ((capability_value.startsWith("xhtmlmp")) || (capability_value.startsWith("chtml"))) {
        out.print("</html>");
      }
    } catch (IOException ioe) {
      System.out.println("Error in Tag document: " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }

}
