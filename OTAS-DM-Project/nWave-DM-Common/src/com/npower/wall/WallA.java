/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallA.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * The body wall:a
 */

public class WallA extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest              request                  = null;

  private static CapabilityMatrix cm                       = null;

  private static UAManager        uam                      = null;

  // locals
  String                          warning                  = "";

  String                          UA                       = "";

  String                          device_id                = "";

  boolean                         fancy_ok                 = false;

  boolean                         accesskey_ok             = false;

  String                          current_bgcolor          = "bgcolor1";

  String                          mark_up                  = "";

  String                          table_ok                 = "";

  String                          css_ok                   = "";

  boolean                         table_and_css_background = false;

  boolean                         menu_css_tag             = false;

  boolean                         inside_menu              = false;

  String                          accesskey_ok_str         = "";

  String                          wml_menu_with_select     = "";

  // Attributes
  String                          href                     = "";

  String                          accesskey                = "";

  String                          title                    = "";

  String                          opwv_icon                = "";

  // attribute Setters
  public void setHref(String href) {
    this.href = href;
  }

  public void setAccesskey(String accesskey) {
    this.accesskey = accesskey;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setOpwv_icon(String oi) {
    this.opwv_icon = oi;
  }

  /**
   * goStartTag will be called when we see the "a" tag
   */
  public int doStartTag() throws JspException {

    inside_menu = false;
    // this link could be part of a menu or not
    WallMenu menu = (WallMenu) findAncestorWithClass(this, WallMenu.class);
    if (menu != null) {
      inside_menu = true;
      // System.out.println("INSIDE MENU!");
    }
    // else {
    // System.out.println("*NOT* INSIDE MENU!");
    // }
    // WallDocument document = (WallDocument) findAncestorWithClass(this,
    // WallDocument.class);
    WallDocument document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);

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

      if (href.indexOf("&") != -1) {
        // WML and XHTML require '&amp;' CHTML requires '&'
        href = TagUtil.normalizeHref(href, mark_up);
      }

      if (inside_menu) {
        // XHTML first
        if (mark_up.startsWith("xhtmlmp")) {

          fancy_ok = menu.colorize && menu.menu_css_tag && menu.table_and_css_background;

          // if the user wants to do it fancy and the device allows for it
          if (fancy_ok) {
            try {
              JspWriter out = pageContext.getOut();
              String bgcolorstyle = menu.getBGColor();
              out.print("<tr>");
              out.print(" <td class=\"" + bgcolorstyle + "\">");

              String an = ""; // autonumber
              if (menu.getAutonumber()) {
                an = menu.getAutonumber_index();
                out.print(" " + an);

                // generate icon?
                if (document != null && !opwv_icon.equals("")) {
                  if (document.getUseXHTMLExtensions()) {
                    out.print(" <img localsrc=\"" + opwv_icon + "\" src=\"\" alt=\"\"/>");
                  }
                }
                out.print(" <a accesskey=\"" + an + "\"");

              } else {
                // maybe mark-up defines it's own
                if (!accesskey.equals("")) {
                  out.print(" " + accesskey + " <a accesskey=\"" + accesskey + "\"");
                } else {
                  out.print("<a");
                }
              }
              out.print(" href=\"" + href + "\"");
              // title is optional
              if (!title.equals("")) {
                out.print(" title=\"" + title + "\"");
              }
              out.print(">");
            } catch (IOException ioe) {
              // System.out.println("Error in a tag <a>: " + ioe);
              System.out.println("Error in a tag <a>" + ioe);
            }
            return (EVAL_BODY_INCLUDE);
          } else { // for whatever reason, no fancy XHTML menus

            try {
              JspWriter out = pageContext.getOut();
              out.print("<li>");

              // generate icon?
              if (document != null && !opwv_icon.equals("")) {
                if (document.getUseXHTMLExtensions()) {
                  out.print("<img localsrc=\"" + opwv_icon + "\" src=\"\" alt=\"\"/> ");
                }
              }

              String an = ""; // autonumber
              if (menu.getAutonumber()) {
                an = menu.getAutonumber_index();
                out.print("<a accesskey=\"" + an + "\"");
              } else {
                // maybe mark-up defines it's own
                if (!accesskey.equals("")) {
                  out.print("<a accesskey=\"" + accesskey + "\"");
                } else {
                  out.print("<a");
                }
              }
              out.print(" href=\"" + href + "\"");
              // title is optional
              if (!title.equals("")) {
                out.print(" title=\"" + title + "\"");
              }
              out.print(">");

            } catch (IOException ioe) {
              System.out.println("Error in a tag <a>: " + ioe);
            }
            return (EVAL_BODY_INCLUDE);
          }
        }

        // CHTML
        if (mark_up.startsWith("chtml")) {

          try {
            JspWriter out = pageContext.getOut();
            String an = ""; // autonumber

            warning = TagUtil.checkCapability("imode_region");
            if (warning.length() > 0) {
              throw new JspException(warning);
            }

            String region = cm.getCapabilityForDevice(device_id, "imode_region");

            if (menu.getAutonumber()) {
              an = menu.getAutonumber_index();
              String emoji = getEmoji(an, region);
              out.print(emoji + "&nbsp;<a accesskey=\"" + an + "\"");
            } else {
              // maybe mark-up defines it's own
              if (!accesskey.equals("")) {
                String emoji = getEmoji(accesskey, region);
                out.print(emoji + "&nbsp;<a accesskey=\"" + accesskey + "\"");
              } else {
                out.print("<a");
              }
            }
            out.print(" href=\"" + href + "\"");
            out.print(">");

          } catch (IOException ioe) {
            System.out.println("Error in a tag <a>: " + ioe);
          }
          return (EVAL_BODY_INCLUDE);
        }

        // WML
        if (mark_up.startsWith("wml")) {
          try {
            JspWriter out = pageContext.getOut();

            // If possible, do menus with select/option/onpick
            warning = TagUtil.checkCapability("menu_with_select_element_recommended");
            if (warning.length() > 0) {
              throw new JspException(warning);
            }
            wml_menu_with_select = cm.getCapabilityForDevice(device_id, "menu_with_select_element_recommended");

            if (wml_menu_with_select.equals("true")) {
              out.print("<option onpick=\"" + href + "\"");
              // title is optional
              if (!title.equals("")) {
                out.print(" title=\"" + title + "\"");
              }
              out.print(">");

              // generate icon?
              if (document != null && !opwv_icon.equals("")) {
                if (document.getUseWMLExtensions()) {
                  out.print("<img localsrc=\"" + opwv_icon + "\" src=\"\" alt=\"\"/>");
                }
              }

            } else {
              // Render with Hyperlinks. extra check for WML. accesskey support
              warning = TagUtil.checkCapability("access_key_support");
              if (warning.length() > 0) {
                throw new JspException(warning);
              }
              accesskey_ok_str = cm.getCapabilityForDevice(device_id, "access_key_support");
              if (accesskey_ok_str.equals("true")) {
                accesskey_ok = true;
              }

              @SuppressWarnings("unused")
              String an = ""; // autonumber
              // accesskey for WML is tricky: extra check accesskey support by
              // WML browser
              // SECOND THOUGHT: I get problems with GATEWAY. removing accesskey
              // for WML for the time being
              if (menu.getAutonumber() && accesskey_ok) {
                an = menu.getAutonumber_index();
                out.print("<a");
                // out.print("<a accesskey=\""+an+"\"");
              }
              if (!menu.getAutonumber() && accesskey_ok) {
                // maybe mark-up defines it's own
                if (!accesskey.equals("")) {
                  out.print("<a");
                  // out.print("<a accesskey=\""+accesskey+"\"");
                } else {
                  out.print("<a");
                }
              }
              if (!accesskey_ok) {
                out.print("<a");
              }

              out.print(" href=\"" + href + "\"");
              // title is optional
              if (!title.equals("")) {
                out.print(" title=\"" + title + "\"");
              }
              out.print(">");
            }

          } catch (IOException ioe) {
            System.out.println("Error in a tag <a>: " + ioe);
          }
          return (EVAL_BODY_INCLUDE);

        }

      } else {
        // ************ NOT inside a menu! PLAIN LINK!********************

        if (mark_up.startsWith("xhtmlmp")) {

          try {
            JspWriter out = pageContext.getOut();
            // accesskey?
            if (!accesskey.equals("")) {
              out.print("<a accesskey=\"" + accesskey + "\"");
            } else {
              out.print("<a");
            }

            out.print(" href=\"" + href + "\"");
            // title is optional
            if (!title.equals("")) {
              out.print(" title=\"" + title + "\"");
            }
            out.print(">");

          } catch (IOException ioe) {
            System.out.println("Error in a tag <a>: " + ioe);
          }
          return (EVAL_BODY_INCLUDE);
        } // end XHTML

        // CHTML
        if (mark_up.startsWith("chtml")) {

          try {
            JspWriter out = pageContext.getOut();
            if (!accesskey.equals("")) {
              out.print("<a accesskey=\"" + accesskey + "\"");
            } else {
              out.print("<a");
            }
            out.print(" href=\"" + href + "\"");
            out.print(">");

          } catch (IOException ioe) {
            System.out.println("Error in a tag <a>: " + ioe);
          }
          return (EVAL_BODY_INCLUDE);
        }

        // WML

        if (mark_up.startsWith("wml")) {

          try {
            JspWriter out = pageContext.getOut();

            if (accesskey_ok) {
              // maybe mark-up defines it's own
              if (!accesskey.equals("")) {
                out.print("<a");
                // Accesskey interferes with gateways and WML 1.1 DTD
                // out.print("<a accesskey=\""+accesskey+"\"");
              } else {
                out.print("<a");
              }
            }
            if (!accesskey_ok) {
              out.print("<a");
            }

            out.print(" href=\"" + href + "\"");
            // title is optional
            if (!title.equals("")) {
              out.print(" title=\"" + title + "\"");
            }
            out.print(">");
          } catch (IOException ioe) {
            System.out.println("Error in a tag <a>: " + ioe);
          }
          return (EVAL_BODY_INCLUDE);
        }
      }
    } catch (Exception e) {
      System.out.println("Error in WALL tag 'a': " + e.getMessage() + "Error: " + e.toString());
    }

    return (SKIP_BODY);

  }

  public int doEndTag() throws JspException {

    if (inside_menu) {
      try {
        JspWriter out = pageContext.getOut();
        // XHTML first
        if (mark_up.startsWith("xhtmlmp")) {
          if (fancy_ok) {
            out.println("</a></td>");
            out.print("</tr>");
          } else {
            out.print("</a></li>");
          }
        }
        // CHTML first
        if (mark_up.startsWith("chtml")) {
          out.print("</a><br>");
        }
        // WML first
        if (mark_up.startsWith("wml")) {
          if (wml_menu_with_select.equals("true")) {
            out.print("</option>");
          } else {
            out.print("</a><br/>");
          }
        }

      } catch (IOException ioe) {
        System.out.println("Error while closing tag a: " + ioe);
      }

    } else { // NOT INSIDE A MENU
      try {
        JspWriter out = pageContext.getOut();
        out.print("</a>");
      } catch (IOException ioe) {
        System.out.println("Error while closing tag a: " + ioe);
      }
    }

    return (EVAL_PAGE); // Continue with rest of JSP page
  }

  // for CHTML, to derive the right Emojy code for accesskeys
  public String getEmoji(String ak, String region) {

    if (region.equals("eu")) {

      if (ak.equals("0"))
        return "&#59115;";
      if (ak.equals("1"))
        return "&#59106;";
      if (ak.equals("2"))
        return "&#59107;";
      if (ak.equals("3"))
        return "&#59108;";
      if (ak.equals("4"))
        return "&#59109;";
      if (ak.equals("5"))
        return "&#59110;";
      if (ak.equals("6"))
        return "&#59111;";
      if (ak.equals("7"))
        return "&#59112;";
      if (ak.equals("8"))
        return "&#59113;";
      if (ak.equals("9"))
        return "&#59114;";
    }

    if (region.equals("ja")) {

      if (ak.equals("1"))
        return "&#63879;";
      if (ak.equals("2"))
        return "&#63880;";
      if (ak.equals("3"))
        return "&#63881;";
      if (ak.equals("4"))
        return "&#63882;";
      if (ak.equals("5"))
        return "&#63883;";
      if (ak.equals("6"))
        return "&#63884;";
      if (ak.equals("7"))
        return "&#63885;";
      if (ak.equals("8"))
        return "&#63886;";
      if (ak.equals("9"))
        return "&#63887;";
      if (ak.equals("0"))
        return "&#63888;";
    }

    // unrecognized CHTML/Imode devices get the number
    return ak;
  }

}
