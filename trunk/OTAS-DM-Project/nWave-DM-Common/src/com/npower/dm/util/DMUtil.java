/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/util/DMUtil.java,v 1.6 2007/08/29 06:21:00 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2007/08/29 06:21:00 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLEncoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.sql.Clob;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2007/08/29 06:21:00 $
 */
public class DMUtil {

  /**
   * 
   */
  public static final String NAME_PREFIX_FOR_NO_NAME_NODE = "OTAS";

  /**
   * 
   */
  public DMUtil() {
    super();
  }

  public static void print(ClassLoader loader) {
    System.out.println("Dump classload: " + loader.toString());
    URL[] urls = getClassLoaderURLs(loader);
    for (int i = 0; urls != null && i < urls.length; i++) {
        System.out.println(urls[i].toString());
    }
    System.out.println("End of classload: " + loader.toString());
  }

  /**
   * Format a string buffer containing the Class, Interfaces, CodeSource, and
   * ClassLoader information for the given object clazz.
   * 
   * @param clazz
   *          the Class
   * @param results,
   *          the buffer to write the info to
   */
  public static void displayClassInfo(Class<?> clazz, StringBuffer results) {
    // Print out some codebase info for the ProbeHome
    ClassLoader cl = clazz.getClassLoader();
    results.append("\n" + clazz.getName() + ".ClassLoader=" + cl);
    ClassLoader parent = cl;
    while (parent != null) {
      results.append("\n.." + parent);
      URL[] urls = getClassLoaderURLs(parent);
      int length = urls != null ? urls.length : 0;
      for (int u = 0; u < length; u++) {
        results.append("\n...." + urls[u]);
      }
      if (parent != null)
        parent = parent.getParent();
    }
    CodeSource clazzCS = clazz.getProtectionDomain().getCodeSource();
    if (clazzCS != null)
      results.append("\n++++CodeSource: " + clazzCS);
    else
      results.append("\n++++Null CodeSource");
    results.append("\nImplemented Interfaces:");
    Class<?>[] ifaces = clazz.getInterfaces();
    for (int i = 0; i < ifaces.length; i++) {
      results.append("\n++" + ifaces[i]);
      ClassLoader loader = ifaces[i].getClassLoader();
      results.append("\n++++ClassLoader: " + loader);
      ProtectionDomain pd = ifaces[i].getProtectionDomain();
      CodeSource cs = pd.getCodeSource();
      if (cs != null)
        results.append("\n++++CodeSource: " + cs);
      else
        results.append("\n++++Null CodeSource");
    }
  }

  /**
   * Use reflection to access a URL[] getURLs or ULR[] getAllURLs method so that
   * non-URLClassLoader class loaders, or class loaders that override getURLs to
   * return null or empty, can provide the true classpath info.
   */
  public static URL[] getClassLoaderURLs(ClassLoader cl) {
    URL[] urls = {};
    try {
      Class<?> returnType = urls.getClass();
      Class<?>[] parameterTypes = {};
      Method getURLs = cl.getClass().getMethod("getURLs", parameterTypes);
      if (returnType.isAssignableFrom(getURLs.getReturnType())) {
        Object[] args = {};
        urls = (URL[]) getURLs.invoke(cl, args);
      }
    } catch (Exception ignore) {
    }
    return urls;
  }

  /**
   * Convert a Clob into String.
   * 
   * @param clob
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static String convertClob2String(Clob clob) throws SQLException, IOException {
    if (clob != null) {
      BufferedReader in = new BufferedReader(clob.getCharacterStream());
      String line = in.readLine();
      StringBuffer result = new StringBuffer();
      while (line != null) {
        result.append(line);
        line = in.readLine();
      }
  
      // Could not close the Reader, which will cause
      // a SQLException(Could not reset the Reader) when next time to call this
      // method.
      // in.close();
      return result.toString();
    }
    return null;
  }
  
  /**
   * Append a parameter into URL
   * @param url
   * @param parameterName
   * @param parameterValue
   * @return
   */
  public static String appendParameter(String url, String parameterName, String parameterValue) {
    StringBuffer result = new StringBuffer(url);
    if (StringUtils.isEmpty(parameterName)) {
       return result.toString();
    }
    if (url.indexOf('?') > 0) {
       result.append('&');
    } else {
      result.append('?');
    }
    result.append(parameterName);
    result.append('=');
    try {
      result.append(URLEncoder.encode(parameterValue, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    return result.toString();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    print(Thread.currentThread().getContextClassLoader());
  }

}
