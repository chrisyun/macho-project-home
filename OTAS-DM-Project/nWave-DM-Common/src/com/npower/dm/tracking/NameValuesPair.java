/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/NameValuesPair.java,v 1.1 2008/06/17 11:06:25 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/17 11:06:25 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.tracking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/17 11:06:25 $
 */
public class NameValuesPair implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String name = null;
  
  private List<String> values = new ArrayList<String>();

  /**
   * 
   */
  public NameValuesPair() {
    super();
  }

  public NameValuesPair(String name, List<String> values) {
    super();
    this.name = name;
    this.values = values;
  }

  public NameValuesPair(String name, String[] values) {
    super();
    this.name = name;
    if (values != null) {
       for (String v: values) {
           this.values.add(v);
       }
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getValues() {
    return values;
  }

  public void setValues(List<String> values) {
    this.values = values;
  }

}
