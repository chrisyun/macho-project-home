/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallImg.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
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
 * The body-less wall:img
 */

public class WallImg extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest              request                      = null;

  private static CapabilityMatrix cm                           = null;

  private static UAManager        uam                          = null;

  // locals
  WallDocument                    document                     = null;

  String                          warning                      = "";

  String                          UA                           = "";

  String                          device_id                    = "";

  String                          capability_value             = "";

  String                          mark_up                      = "";

  String                          render_as                    = "";   // valid
                                                                        // values:
                                                                        // "nothing","icon","image"

  boolean                         inside_cool_menu             = false;

  boolean                         cool_menu_perform_tabularize = false;

  String                          imode_table_support          = "";

  String                          xhtml_br                     = "";

  String                          css_hook                     = "";

  String                          chtml_br                     = "";

  String                          opwv_icon_space              = "";

  String                          imode_eu_icon                = "";

  String                          imode_ja_icon                = "";

  String                          opwv_icon_localsrc           = "";

  // cooperation methods (just a tiny bit of encapsulation)
  protected void setRender_as(String ra) {
    render_as = ra;
  }

  protected void setImode_eu_icon(String iei) {
    imode_eu_icon = iei;
  }

  protected void setImode_ja_icon(String iji) {
    imode_ja_icon = iji;
  }

  protected void setOpwv_icon_localsrc(String oil) {
    opwv_icon_localsrc = oil;
  }

  // maybe one day I'll understand why setSrc was not good enough for doing the
  // same thing...
  protected void setSrc2(String s) {
    this.src = s;
  }

  // Attributes
  String src = "";

  String alt = "";

  // accessors
  public void setSrc(String s) {
    this.src = s;
  }

  public void setAlt(String a) {
    this.alt = a;
  }

  /*
   * goStartTag will be called when we see the "img" tag there is one major
   * difference between this tag and most other tags. The rendering is done by
   * doEndTag(), since the 'alernate_img' tag nested inside the image tag can
   * actually modify how the tag is rendered.
   */
  @Override
  public int doStartTag() throws JspException {

    render_as = "image";
    xhtml_br = "";
    css_hook = "";
    chtml_br = "";
    imode_eu_icon = "";
    imode_ja_icon = "";
    opwv_icon_localsrc = "";
    opwv_icon_space = "";

    WallCool_menu coolmenu = (WallCool_menu) findAncestorWithClass(this, WallCool_menu.class);
    if (coolmenu != null) {
      inside_cool_menu = true;
      cool_menu_perform_tabularize = coolmenu.perform_tabularization;
      if (cool_menu_perform_tabularize) {
        xhtml_br = "<br/>";
        chtml_br = "<br>";
      } else {
        xhtml_br = " ";
        chtml_br = "&nbsp;";
      }
      css_hook = " class=\"coolmenu\"";
      opwv_icon_space = " ";
    }

    // document = (WallDocument) findAncestorWithClass(this,
    // WallDocument.class);
    document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);
    if (document == null) {
      throw new JspTagException("Error in 'img' tag. No top 'document' tag found!");
    }
    try {
      ObjectsManager objectsManager = WurflServletInit.getObjectsManager();
      cm = objectsManager.getCapabilityMatrixInstance();
      uam = objectsManager.getUAManagerInstance();
    } catch (Exception e) {
      throw new JspException(e.getMessage(), e);
    }
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
    mark_up = TagUtil.getWallMarkup(capability_value);

    return (EVAL_BODY_INCLUDE);

  }

  @Override
  public int doEndTag() throws JspException {

    try {
      JspWriter out = pageContext.getOut();

      // XHTML first
      // out.println(device_id+" "+mark_up+" "+render_as);
      if (mark_up.startsWith("xhtmlmp")) {
        if (render_as.equals("image")) {
          out.print("<img" + css_hook + " src=\"" + src + "\" alt=\"" + alt + "\" />" + xhtml_br);
        }
        if (render_as.equals("icon") && document.getUseXHTMLExtensions()) {
          // only do something if device supports OPWV buil-in icons
          out.print("<img src=\"\" alt=\"" + alt + "\"");
          out.print(" localsrc=\"" + opwv_icon_localsrc + "\"/>" + xhtml_br);
        }
        // if render_as == nothing, do nothing ;)
      }

      // WML
      if (mark_up.startsWith("wml")) {
        if (render_as.equals("image")) {
          out.print(opwv_icon_space + "<img src=\"" + src + "\" alt=\"" + alt + "\" /> ");
        }
        if (render_as.equals("icon") && document.getUseWMLExtensions()) {
          // only do something if device supports OPWV buil-in icons
          out.print(opwv_icon_space + "<img src=\"\" alt=\"" + alt + "\"");
          out.print(" localsrc=\"" + opwv_icon_localsrc + "\"/> ");
        }
        // if render_as == nothing, do nothing ;)
      }
      // CHTML
      if (mark_up.startsWith("chtml")) {
        if (render_as.equals("image")) {
          out.print("<img src=\"" + src + "\" alt=\"" + alt + "\">" + chtml_br);

        }
        if (render_as.equals("icon")) {
          warning = TagUtil.checkCapability("imode_region");
          if (warning.length() > 0) {
            throw new JspException(warning);
          }
          String region = cm.getCapabilityForDevice(device_id, "imode_region");
          if (region.equals("ja")) {
            out.print(imode_ja_icon + chtml_br);
          }
          if (region.equals("eu")) {
            out.print(imode_eu_icon + chtml_br);
          }

        }
        // if render_as == nothing, do nothing ;)
      }
    } catch (IOException ioe) {
      throw new JspException("Error in 'img' tag: " + ioe);
    }
    return (EVAL_PAGE); // Continue with rest of JSP page
  }

  @Override
  public void release() {

    render_as = null;

    inside_cool_menu = false;
    cool_menu_perform_tabularize = false;
    imode_table_support = null;
    xhtml_br = null;
    css_hook = null;
    chtml_br = null;
    opwv_icon_space = null;

    imode_eu_icon = null;
    imode_ja_icon = null;
    opwv_icon_localsrc = null;
  }
}
