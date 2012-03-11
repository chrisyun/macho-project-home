/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/PropertiesValueFetcher.java,v 1.1 2008/02/11 10:52:45 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/02/11 10:52:45 $
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
package com.npower.cp.convertor;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileCategory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/02/11 10:52:45 $
 */
public class PropertiesValueFetcher implements ValueFetcher<ProfileCategory, String, String> {

  private Map<String, Properties> map = new HashMap<String, Properties>();
  /**
   * 
   */
  public PropertiesValueFetcher() {
    super();
  }
  
  public void setValue(String categoryName, String key, String value) {
    //String categoryName = (category != null)?category.getName():"___DEFAULT__";
    if (!this.map.containsKey(categoryName)) {
      this.map.put(categoryName, new Properties());
    }
    Properties props = this.map.get(categoryName);
    if (value == null) {
       props.remove(key);
    } else {
      props.setProperty(key, value);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ValueFetcher#getValue(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  public String getValue(ProfileCategory category, String key, String defaultValue) throws DMException {
    String categoryName = (category != null)?category.getName():"___DEFAULT__";
    if (!this.map.containsKey(categoryName)) {
       return defaultValue;
    } else {
      Properties props = this.map.get(categoryName);
      return props.getProperty(key, defaultValue);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ValueFetcher#getValue(java.lang.Object, java.lang.Object)
   */
  public String getValue(ProfileCategory category, String key) throws DMException {
    return this.getValue(category, key, null);
  }

}
