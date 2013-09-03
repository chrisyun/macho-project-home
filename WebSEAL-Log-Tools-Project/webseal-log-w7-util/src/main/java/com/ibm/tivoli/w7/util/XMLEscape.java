/**
 * 
 */
package com.ibm.tivoli.w7.util;

/**
 * @author zhaodonglu
 *
 */
public class XMLEscape {

  public static String escape(String s) {
    String result = s;
    if (s == null) {
       return result;
    }
    result = result.replaceAll("&", "&amp;");
    result = result.replaceAll("\"", "&quot;");
    result = result.replaceAll("'", "&apos;");
    result = result.replaceAll("<", "&lt;");
    result = result.replaceAll(">", "&gt;");
    return result;
  }
}
