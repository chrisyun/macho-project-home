/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallAlternate_img.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflServletInit;

/**
 * The body-less wall:alternate_img
 */

public class WallAlternate_img extends TagSupport {

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

  String                          mark_up          = "";

  // Attributes
  boolean                         test             = false;

  boolean                         nopicture        = false;

  String                          src              = "";

  String                          opwv_icon        = "";

  String                          eu_imode_icon    = "";

  String                          ja_imode_icon    = "";

  // accessors

  public void setTest(boolean t) {
    this.test = t;
  }

  public void setTest(String t) {
    if (t.equalsIgnoreCase("true")) {
      this.test = true;
    } else {
      this.test = false;
    }
  }

  public void setNopicture(boolean np) {
    this.nopicture = np;
  }

  public void setNopicture(String np) {
    if (np.equalsIgnoreCase("true")) {
      this.nopicture = true;
    } else {
      this.nopicture = false;
    }
  }

  public void setSrc(String s) {
    this.src = s;
  }

  public void setOpwv_icon(String oi) {
    this.opwv_icon = oi;
  }

  public void setEu_imode_icon(String eii) {
    this.eu_imode_icon = eii;
  }

  public void setJa_imode_icon(String jii) {
    this.ja_imode_icon = jii;
  }

  /**
   * goStartTag will be called when we see the "alternate_img" tag
   */
  @Override
  public int doStartTag() throws JspException {

    try {
      ObjectsManager objectsManager = WurflServletInit.getObjectsManager();
      cm = objectsManager.getCapabilityMatrixInstance();
      uam = objectsManager.getUAManagerInstance();
    } catch (Exception e) {
      throw new JspException(e.getMessage(), e);
    }
    request = (HttpServletRequest) pageContext.getRequest();

    // one and only one attribute at the time (apart from 'test' which is
    // compulsory)
    int c = 0;
    if (!"".equals(src) && src != null)
      c++;
    if (!"".equals(eu_imode_icon) && eu_imode_icon != null)
      c++;
    if (!"".equals(ja_imode_icon) && ja_imode_icon != null)
      c++;
    if (!"".equals(opwv_icon) && opwv_icon != null)
      c++;
    if (nopicture)
      c++;

    if (c == 0) {
      throw new JspTagException(
          "tag 'alternate_img' must use one of the following attributes:\nsrc,opwv_icon,eu_imode_icon,ja_imode_ico or nopicture");
    }
    if (c > 1) {
      throw new JspTagException(
          "tag 'alternate_img' must use one and only one of the following attributes:\nsrc,opwv_icon,eu_imode_icon,ja_imode_ico or nopicture");
    }

    // let's go for the root img. We'll need it later
    WallImg img = (WallImg) findAncestorWithClass(this, WallImg.class);
    if (img == null) {
      throw new JspTagException("tag 'alternate_img' must be nested inside an 'img' tag");
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
    mark_up = TagUtil.getWallMarkup(capability_value);

    if (test && nopicture) {
      img.setRender_as("nothing");
      return (SKIP_BODY);
    }

    if (test && !src.equals("")) {
      img.setSrc2(src);
      return (SKIP_BODY);
    }

    if (mark_up.startsWith("chtml")) {

      String region = cm.getCapabilityForDevice(device_id, "imode_region");
      if (test && region.equals("ja") && !ja_imode_icon.equals("")) {
        img.setRender_as("icon");
        img.setImode_ja_icon(ja_imode_icon);
        return (SKIP_BODY);
      }
      if (test && region.equals("eu") && !eu_imode_icon.equals("")) {
        img.setRender_as("icon");
        img.setImode_eu_icon(eu_imode_icon);
        return (SKIP_BODY);
      }
    }

    if (mark_up.startsWith("xhtmlmp") || mark_up.startsWith("wml")) {

      if (test && !opwv_icon.equals("")) {
        img.setRender_as("icon");
        img.setOpwv_icon_localsrc(opwv_icon);
      }
      return (SKIP_BODY);
    }

    return (SKIP_BODY);
  }

  @Override
  public void release() {

    this.test = false;
    this.nopicture = false;

    this.src = null;
    this.opwv_icon = null;
    this.eu_imode_icon = null;
    this.ja_imode_icon = null;
  }
}
