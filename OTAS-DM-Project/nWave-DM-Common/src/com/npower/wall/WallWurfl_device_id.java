/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallWurfl_device_id.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * The body-less wall:wurfl_device_id
 */

public class WallWurfl_device_id extends TagSupport {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  HttpServletRequest       request          = null;

  private static UAManager uam              = null;

  // locals
  String                   warning          = "";

  String                   UA               = "";

  String                   device_id        = "";

  String                   capability_value = "";

  // Attributes
  String                   ua               = "";

  // setters
  public void setUa(String u) {
    this.ua = u;
  }

  /**
   * goStartTag will be called when we see the "load_capabilities" tag
   */
  @Override
  public int doStartTag() throws JspException {

    try {
      ObjectsManager objectsManager = WurflServletInit.getObjectsManager();
      uam = objectsManager.getUAManagerInstance();

      request = (HttpServletRequest) pageContext.getRequest();

      // get the user agent
      UA = TagUtil.getUA(this.request);
      device_id = uam.getDeviceIDFromUALoose(UA);
      try {
        JspWriter out = pageContext.getOut();
        if (!ua.equals("")) {
          // if ua is requested, print out a small report
          out.print("Device ID: " + device_id + "<br/>");
          out.print("User Agent: " + request.getHeader("User-Agent") + "<br/>");
        } else {
          out.print(device_id);
        }

      } catch (IOException ioe) {
        System.out.println("Error in wurfl_device_id tag: " + ioe);
      }

    } catch (Exception e) {
      System.out.println("Error in WALL: tag 'wurfl_device_id': " + e.getMessage());
      System.out.println("Error: " + e.toString());
    }
    return (SKIP_BODY);
  }
}
