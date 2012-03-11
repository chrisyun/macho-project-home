/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/WallInput.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
 * The body-less wall:input
 */

public class WallInput extends TagSupport {

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

  WallDocument                    document;

  // Attributes
  String                          accesskey        = "";

  String                          format           = "";

  String                          checked          = "";

  String                          disabled         = "";

  String                          emptyok          = "";

  String                          maxlength        = "";

  String                          name             = "";

  String                          size             = "";

  String                          title            = "";

  String                          type             = "";

  String                          value            = "";

  // accessors
  public void setAccesskey(String a) {
    this.accesskey = a;
  }

  public String getAccesskey() {
    return accesskey;
  }

  public void setFormat(String f) {
    this.format = f;
  }

  public String getFormat() {
    return format;
  }

  public void setChecked(String c) {
    this.checked = c;
  }

  public String getChecked() {
    return checked;
  }

  public void setDisabled(String d) {
    this.disabled = d;
  }

  public String getDisabled() {
    return disabled;
  }

  public void setEmptyok(String eo) {
    this.emptyok = eo;
  }

  public String getEmptyok() {
    return emptyok;
  }

  public void setMaxlength(String ml) {
    this.maxlength = ml;
  }

  public String getMaxlength() {
    return maxlength;
  }

  public void setName(String n) {
    this.name = n;
  }

  public String getName() {
    return name;
  }

  public void setSize(String s) {
    this.size = s;
  }

  public String getSize() {
    return size;
  }

  public void setTitle(String t) {
    this.title = t;
  }

  public String getTitle() {
    return title;
  }

  public void setType(String xt) {
    this.type = xt;
  }

  public String getType() {
    return type;
  }

  public void setValue(String v) {
    this.value = v;
  }

  public String getValue() {
    return value;
  }

  /**
   * goStartTag will be called when we see the "input" tag
   */
  public int doStartTag() throws JspException {

    // needed to make sure that only one style attribute is generated
    String style_attr_value_format = "";
    boolean style_is_set = false;

    try {
      ObjectsManager objectsManager = WurflServletInit.getObjectsManager();
      cm = objectsManager.getCapabilityMatrixInstance();
      uam = objectsManager.getUAManagerInstance();
      request = (HttpServletRequest) pageContext.getRequest();

      // let's go for the root document. We'll need it later
      // document = (WallDocument) findAncestorWithClass(this,
      // WallDocument.class);
      document = (WallDocument) pageContext.getAttribute("wall-document", PageContext.REQUEST_SCOPE);
      if (document == null) {
        throw new JspTagException("tag 'input'(wml) must be nested inside a 'document' tag");
      }
      // check that emptyok is wither left empty or has the emptyok="emptyok"
      // syntax
      if (!"".equals(emptyok) && !"emptyok".equals(emptyok)) {
        throw new JspTagException("Only admitted value for emptyok attribute (<input> tag) is 'emptyok'");
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
      capability_value = TagUtil.getWallMarkup(capability_value);

      // submit has different semantics from the other input types
      if (!type.equals("submit")) {

        // only submit can do without a 'name' attribute
        if (name.equals("")) {
          String err_msg = "input type='" + type + "' requires a name attribute";
          JspWriter out = pageContext.getOut();
          out.println(err_msg);
          throw new JspException(err_msg);
        }

        /*
         * Still haven't decided if this is a good idea! //XHTML device that
         * don't support disabled forms and WML1.X devices if
         * (disabled.equals("disabled")) { //let's check that disables is
         * supported. If not, we may avoid displaying the //control completelly
         * warning +=
         * TagUtil.checkCapability("xhtml_allows_disabled_form_elements"); if
         * (warning.length() > 0) { throw new JspException(warning); } String
         * empty_form_element = cm.getCapabilityForDevice(device_id,
         * "xhtml_allows_disabled_form_elements"); if (
         * empty_form_element.equals("false") ||
         * capability_value.startsWith("wml") ) { return(SKIP_BODY); } }
         */

        if (capability_value.startsWith("xhtmlmp")) {
          try {
            JspWriter out = pageContext.getOut();
            out.print("<input");

            // required attributes
            out.print(" type=\"" + type + "\"");
            out.print(" name=\"" + name + "\"");
            out.print(" value=\"" + value + "\"");
            if (!title.equals("")) {
              out.print(" title=\"" + title + "\"");
            }

            // format is tricky
            if (!format.equals("")) {
              // Check that the format capabilities are in the database
              warning = TagUtil.checkCapability("xhtml_format_as_css_property");
              warning += TagUtil.checkCapability("xhtml_format_as_attribute");
              if (warning.length() > 0) {
                throw new JspException(warning);
              }
              String cssformat = cm.getCapabilityForDevice(device_id, "xhtml_format_as_css_property");
              String attributeformat = cm.getCapabilityForDevice(device_id, "xhtml_format_as_attribute");
              if (cssformat.equals("true")) {
                // pitfall here. emptyok can also generate a style attribute
                // but we can have only one
                // out.print(" style=\"-wap-input-format:
                // &apos;"+format+"&apos;\"");
                style_is_set = true;
                style_attr_value_format = "-wap-input-format: &apos;" + format + "&apos;";
              }
              if (cssformat.equals("false") && attributeformat.equals("true")) {
                out.print(" format=\"" + format + "\"");
              }
            }
            // emptyok is also tricky
            if (!emptyok.equals("")) {
              // emptyok, use the same WURFL capabilities used for format
              warning = TagUtil.checkCapability("xhtml_format_as_css_property");
              warning += TagUtil.checkCapability("xhtml_format_as_attribute");
              if (warning.length() > 0) {
                throw new JspException(warning);
              }
              String cssformat = cm.getCapabilityForDevice(device_id, "xhtml_format_as_css_property");
              String attributeformat = cm.getCapabilityForDevice(device_id, "xhtml_format_as_attribute");
              if (cssformat.equals("true")) {
                if (style_is_set) {
                  out.print(" style=\"-wap-input-required;" + style_attr_value_format + "\"");
                } else {
                  out.print(" style=\"-wap-input-required\"");
                }
              }
              if (cssformat.equals("false") && attributeformat.equals("true")) {
                out.print(" emptyok=\"emptyok\"");
              }
            } else {
              // emptyok does not require rendering, but we need
              // to check that we don't have a format style attribute to render
              if (style_is_set) {
                out.print(" style=\"" + style_attr_value_format + "\"");
              }
            }

            // optional attributes
            if (!accesskey.equals("")) {
              out.print(" acceskey=\"" + accesskey + "\"");
            }
            if (!checked.equals("")) {
              out.print(" checked=\"" + checked + "\"");
            }
            if (!disabled.equals("")) {
              out.print(" disabled=\"" + disabled + "\"");
            }
            if (!maxlength.equals("")) {
              out.print(" maxlength=\"" + maxlength + "\"");
            }
            if (!size.equals("")) {
              out.print(" size =\"" + size + "\"");
            }

            out.print("/>");
          } catch (IOException ioe) {
            System.out.println("Error in input tag: " + ioe);
          }
          return (SKIP_BODY);
        }

        // CHTML <input> without trailing slash
        if (capability_value.startsWith("chtml")) {
          try {
            JspWriter out = pageContext.getOut();
            out.print("<input");

            // required attributes
            out.print(" type=\"" + type + "\"");
            out.print(" name=\"" + name + "\"");
            out.print(" value=\"" + value + "\"");

            // optional attributes
            if (!accesskey.equals("")) {
              out.print(" acceskey=\"" + accesskey + "\"");
            }
            if (!checked.equals("")) {
              out.print(" checked");
            }
            if (!maxlength.equals("")) {
              out.print(" maxlength=\"" + maxlength + "\"");
            }
            if (!size.equals("")) {
              out.print(" size =\"" + size + "\"");
            }

            // If format specifies numbers only, we can do something with istyle
            if (!format.equals("") || (type.equals("text"))) {
              String istyle = " istyle=\"4\"";
              if (format.equals("N*") || format.matches("N{1,}")) {
                out.print(istyle);
              }
            }
            out.print(">");
          } catch (IOException ioe) {
            System.out.println("Error in input tag: " + ioe);
          }
          return (SKIP_BODY);
        }

        if (capability_value.startsWith("wml")) {
          try {

            if (!type.equals("hidden")) {

              // tell the document that this element exists
              document.addFieldName(name);

              JspWriter out = pageContext.getOut();
              out.print("<input");

              // required attributes
              out.print(" type=\"" + type + "\"");
              out.print(" name=\"" + name + "\"");
              out.print(" value=\"" + value + "\"");

              if (!title.equals("")) {
                out.print(" title=\"" + title + "\"");
              }

              // emptyok is also tricky
              // optional attributes
              if (!format.equals("")) {
                out.print(" format=\"" + format + "\"");
              }
              if (!emptyok.equals("")) {
                out.print(" emptyok=\"" + false + "\"");
              }
              if (!accesskey.equals("")) {
                out.print(" acceskey=\"" + accesskey + "\"");
              }
              if (!maxlength.equals("")) {
                out.print(" maxlength=\"" + maxlength + "\"");
              }
              if (!size.equals("")) {
                out.print(" size =\"" + size + "\"");
              }

              out.print("/>");
            } else {
              // need to manage the hidden field (with the help of document
              document.addHiddenField(name, value);
            }
          } catch (IOException ioe) {
            System.out.println("Error in input tag: " + ioe);
          }
          return (SKIP_BODY);
        }
      } else { // type=submit

        if ((capability_value.startsWith("xhtmlmp")) || (capability_value.startsWith("chtml"))) {
          try {
            JspWriter out = pageContext.getOut();
            out.print("<input type=\"submit\" value=\"" + value + "\" />");
          } catch (IOException ioe) {
            System.out.println("Error in input tag (submit): " + ioe);
          }
        }
        // WML is trickier, we need to retrieve all the fields
        if (capability_value.startsWith("wml")) {
          try {
            JspWriter out = pageContext.getOut();
            ArrayList<?> fields = document.getFormFields();
            HashMap<Object, Object> hiddenFields = document.getHiddenFields();
            String key = "";

            // softkey support and button
            warning = TagUtil.checkCapability("softkey_support");
            warning += TagUtil.checkCapability("wml_1_3");
            if (warning.length() > 0) {
              throw new JspException(warning);
            }
            String softkey_support = cm.getCapabilityForDevice(device_id, "softkey_support");
            String wml_1_3 = cm.getCapabilityForDevice(device_id, "wml_1_3");

            // if the browser supports softkeys and it's not a WML13 device, use
            // softkeys
            if (wml_1_3.equals("false") && softkey_support.equals("true")) {
              out.println("\n<do type=\"accept\" label=\"" + value + "\">");
              out.print(" <go href=\"" + document.getFormAction() + "\"");
              if (!document.getFormMethod().equals("")) {
                out.print(" method=\"" + document.getFormMethod() + "\"");
              }
              out.println(">");

              // hidden fields have a different tratment
              Iterator<Object> keys = hiddenFields.keySet().iterator();
              while (keys.hasNext()) {
                key = (String) keys.next();
                out.println("   <postfield name=\"" + key + "\" value=\"" + hiddenFields.get(key) + "\" />");
              }

              // fields
              int nr_fields = fields.size();
              String str = "";
              for (int j = 0; j < nr_fields; j++) {
                str = (String) fields.get(j);
                out.println("   <postfield name=\"" + str + "\" value=\"$" + str + "\" />");
              }
              out.println(" </go>");
              out.println("</do>");

            } else { // use softkeys
              out.println("\n<anchor>" + value);
              out.print(" <go href=\"" + document.getFormAction() + "\"");
              if (!document.getFormMethod().equals("")) {
                out.print(" method=\"" + document.getFormMethod() + "\"");
              }
              out.println(">");

              // hidden fields require a different treatment
              Iterator<Object> keys = hiddenFields.keySet().iterator();
              while (keys.hasNext()) {
                key = (String) keys.next();
                out.println("   <postfield name=\"" + key + "\" value=\"" + hiddenFields.get(key) + "\" />");
              }

              int nr_fields = fields.size();
              String str = "";
              for (int j = 0; j < nr_fields; j++) {
                str = (String) fields.get(j);
                out.println("   <postfield name=\"" + str + "\" value=\"$" + str + "\" />");
              }
              out.println(" </go>");
              out.println("</anchor>");

            }
          } catch (IOException ioe) {
            System.out.println("Error in input tag (submit): " + ioe);
          }
        }
      }

      return (SKIP_BODY);

    } catch (Exception e) {
      System.out.println("Error in Wurfl: tag 'input': " + e.getMessage());
      System.out.println("Error: " + e.toString());
    }
    return (SKIP_BODY);
  }
}
