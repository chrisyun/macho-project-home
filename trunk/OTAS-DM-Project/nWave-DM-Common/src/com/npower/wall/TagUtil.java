/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/TagUtil.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * This is a utility class hopefully with reusable methods for all kind of
 * different wall tags
 */

public class TagUtil {

  private static CapabilityMatrix cm  = null;

  private static UAManager        uam = null;

  static {
    try {
      cm  = WurflServletInit.getObjectsManager().getCapabilityMatrixInstance();
      uam = WurflServletInit.getObjectsManager().getUAManagerInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * Get the UA from request if any otherwise use the parameter passed
   */

  public static String getUA(HttpServletRequest request) {
    String UA = "";
    if (request.getParameter("UA") != null) {
      UA = request.getParameter("UA");
    } else {
      UA = request.getHeader("User-Agent");
    }
    if (StringUtils.isEmpty(UA)) {
       // Default UA
       UA = "Nokia6681";
    }
    return UA;
  }

  /**
   * Get preferred MIME type from request and WURFL
   */

  public static String getPreferred_mime_type(HttpServletRequest request) {
    String UA = getUA(request);
    String device_id = uam.getDeviceIDFromUALoose(UA);
    String capability_value = cm.getCapabilityForDevice(device_id, "preferred_markup");
    capability_value = getWallMarkup(capability_value);
    String mime_type = "text/vnd.wap.wml";
    if (capability_value.equals("xhtmlmp")) {
      mime_type = cm.getCapabilityForDevice(device_id, "xhtmlmp_preferred_mime_type");
    }
    if (capability_value.equals("chtml")) {
      mime_type = "text/html";
    }
    // if (capability_value.equals("wml")) {
    // mime_type = "text/vnd.wap.wml";
    // }
    return mime_type;
  }

  /**
   * Figure out if the request is coming in through an UP.Link (AKA MAG) WAP
   * Gateway from Openwave (this info is necessary to undestand if certain WML
   * extensions (useful for usability) can be utilized.
   */

  public static boolean isUplink(HttpServletRequest request) {
    String UA = "";
    if (request.getParameter("UA") != null) {
      UA = "" + request.getParameter("UA");
    } else {
      UA = "" + request.getHeader("User-Agent");
    }
    if (UA.indexOf("UP.Link") != -1) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check if it is a 'real' capability
   */
  public static String checkCapability(String capability) {

    if (!cm.isCapabilityIn(capability)) {
      String strMsg = "Invalid capability name:  " + capability;
      return strMsg;
    }
    return "";
  }

  /**
   * isolate the library from future changes in WURFL definitions. Given a
   * mark_up string, return xhtml,wml or chtml
   */
  public static String getWallMarkup(String prefmarkup) {
    if (prefmarkup.startsWith("html_wi_oma_xhtmlmp") || prefmarkup.startsWith("html_wi_w3_xhtmlbasic")) {
      return "xhtmlmp";
    }

    if (prefmarkup.startsWith("html_wi_imode") || prefmarkup.startsWith("html_web")) {
      return "chtml";
    }

    if (prefmarkup.startsWith("wml")) {
      return "wml";
    }

    System.out.println("Mark up '" + prefmarkup + "' NOT recognized!");
    System.out.println("Returning 'wml' as mark-up of choice for this device" + " but you'd better check");
    return "wml";
  }

  /**
   * WML and XHTML require &amp; to separate query parameters CHTML is ok with
   * '&' This method guarantees that the URL is normalized for the requesting
   * device, no matter which of the two formats has been chosen
   */
  public static String normalizeHref(String href, String markup) {

    // System.out.println("href: "+href+" ("+markup+")");

    if (markup.equals("chtml")) {
      // System.out.println("CHTML");
      if (href.indexOf("&amp;") != -1) {
        href = href.replaceAll("&amp;", "&");
        // System.out.println("'href' changed:"+href);
      }
    } else {
      // WML or XHTML MP
      if ((href.indexOf("&amp;") == -1) && (href.indexOf("&") != -1)) {
        href = href.replaceAll("&", "&amp;");
        // System.out.println("'href' changed:"+href);
      }
    }
    return href;
  }
}
