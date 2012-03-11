/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/NameValuePair.java,v 1.1 2006/06/26 03:48:13 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2006/06/26 03:48:13 $
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
package com.npower.dm.core;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2006/06/26 03:48:13 $
 */
public class NameValuePair {
  
  private String name = null;
  
  private Object value = null;

  /**
   * 
   */
  public NameValuePair() {
    super();
  }

  /**
   * @param name
   * @param value
   */
  public NameValuePair(String name, Object value) {
    super();
    this.name = name;
    this.value = value;
  }

  /**
   * @return Returns the name.
   */
  public String getName() {
    return name;
  }

  /**
   * @param name The name to set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return Returns the value.
   */
  public Object getValue() {
    return value;
  }

  /**
   * @param value The value to set.
   */
  public void setValue(Object value) {
    this.value = value;
  }

}
