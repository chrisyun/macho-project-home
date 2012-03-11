/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/util/DDFTreeHelper.java,v 1.3 2007/01/10 10:30:27 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/01/10 10:30:27 $
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

import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/01/10 10:30:27 $
 */
public class DDFTreeHelper {

  public static final Pattern DynamicNamePattern = Pattern.compile("\\$\\{((NodeName)|(NodeName\\:(\\d|[a-zA-Z])*))\\}");

  /**
   * 
   */
  private DDFTreeHelper() {
    super();
  }
  
  /**
   * Split the nodePath into a List. The "./" prefix in the node path will be
   * ignored. eg: ./a/b/c/d/ results: {a, b, c, d} /a/b/c/d/ results: {a, b, c,
   * d} a/b/c/d results: {a, b, c, d}
   * 
   * @param nodePath
   * @return
   */
  public static List<String> getPathVector(String nodePath) {
    String path = nodePath;
    if (path.startsWith("./")) {
      path = nodePath.substring(2, path.length());
    }
    StringTokenizer tokenizer = new StringTokenizer(path, "/");
    List<String> pathVector = new Vector<String>();
  
    while (tokenizer.hasMoreTokens()) {
      pathVector.add(tokenizer.nextToken());
    }
    return pathVector;
  }

  /**
   * <pre>
   * Extract dynamic name.
   * eg:
   * ./a/b/c/d                   null
   * ./a/b/c/${NodeName}         null
   * ./a/b/c/${NodeName:}        null
   * ./a/b/c/${NodeName:aaaaa}   aaaaa
   * 
   * </pre>
   * @param nodePath
   * @return
   */
  public static String getDynamicName(String nodePath) {
    List<String> path = getPathVector(nodePath);
    if (path.size() == 0) {
       return null;
    }
    String name = path.get(path.size() - 1);
    Matcher matcher = DDFTreeHelper.DynamicNamePattern.matcher(name);
    boolean equals = matcher.matches();
    if (!equals) {
       return null;
    }
    int begin = name.indexOf(':');
    if (begin <= 0) {
       return null;
    }
    int end = name.lastIndexOf("}");
    if (begin + 1 < end) {
       return name.substring(begin + 1, end);
    }
    return null;
  }
  
  /**
   * <pre>
   * Extract name or dynamic name.
   * eg:
   * ./a/b/c/d                   d
   * ./a/b/c/${NodeName}         null
   * ./a/b/c/${NodeName:}        null
   * ./a/b/c/${NodeName:aaaaa}   aaaaa
   * 
   * </pre>
   * @param nodePath
   * @return
   */
  public static String getName(String nodePath) {
    List<String> path = getPathVector(nodePath);
    if (path.size() == 0) {
       return null;
    }
    String name = path.get(path.size() - 1);
    Matcher matcher = DDFTreeHelper.DynamicNamePattern.matcher(name);
    boolean equals = matcher.matches();
    if (equals) {
       return getDynamicName(nodePath);
    } else {
      return name;
    }
  }

  /**
   * <pre>
   * Return Parent Path.
   * Eg: 
   *    Input                Result
   *    ./a/b/c/             ./a/b
   *    ./a/b/c              ./a/b
   *    .                    null
   *    ./a                  .
   * </pre>
   * @param nodePath
   * @return
   */
  public static String getParentPath(String nodePath) {
    if (StringUtils.isEmpty(nodePath)) {
       return null;
    }
    
    nodePath = nodePath.trim();
    while (nodePath.endsWith("/")) {
          nodePath = nodePath.substring(0, nodePath.length() - 1);
    }
    
    if (nodePath.equalsIgnoreCase(".")) {
       return null;
    }
    
    int index = nodePath.lastIndexOf('/');
    if (index > 0) {
       return nodePath.substring(0, index);
    } else {
      return nodePath;
    }
    
  }
  
  /**
   * Concat two path.
   * @param basePath
   * @param relativePath
   * @return
   */
  public static String concat(String basePath, String relativePath) {
    
    if (StringUtils.isEmpty(basePath)) {
       return relativePath;
    }
    
    if (StringUtils.isEmpty(relativePath)) {
       return basePath;
    }
    
    basePath = basePath.trim();
    relativePath = relativePath.trim();
    
    if (relativePath.startsWith("./") || relativePath.startsWith("/")) {
       return relativePath;
    }
    
    if (!basePath.endsWith("/")) {
       basePath = basePath + "/";
    }
    
    return basePath + relativePath;
  }

}
