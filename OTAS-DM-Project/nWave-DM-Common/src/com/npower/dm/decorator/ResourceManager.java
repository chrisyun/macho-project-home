/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/ResourceManager.java,v 1.4 2007/09/10 02:21:14 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2007/09/10 02:21:14 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.decorator;

import java.util.Locale;

import com.npower.dm.util.MessageResources;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/09/10 02:21:14 $
 */
public abstract class ResourceManager<T> {
  
  private MessageResources messageResources = null;

  /**
   * Default constructor
   */
  protected ResourceManager() {
    super();
  }
  
  /**
   * Default constructor
   */
  protected ResourceManager(MessageResources messageResources) {
    super();
    this.setMessageResources(messageResources);
  }
  
  private String formatKey(String key) {
    String result = key.replace(' ', '_');
    result = result.toLowerCase();
    return result;
  }
  
  /**
   * @param target
   * @param propertyName
   * @return
   */
  private String caculateKey(T target, String index, String propertyName) {
    String prefix = this.getKeyPrefix(target);
    StringBuffer result = new StringBuffer();
    result.append(prefix);
    result.append('.');
    result.append(index.toLowerCase());
    result.append('.');
    result.append(propertyName.toLowerCase());
    result.append(".label");
    return formatKey(result.toString());
  }
  
  /**
   * @param locale
   * @param target
   * @param propertyName
   * @return
   */
  public String getResource(Locale locale, T target, String index, String propertyName, String defaultValue) {
    String value = this.getMessageResources().getMessage(locale, this.caculateKey(target, index, propertyName));
    if (value == null) {
       value = defaultValue;
    }
    return value;
  }

  /**
   * @param messageResources the messageResources to set
   */
  public void setMessageResources(MessageResources messageResources) {
    this.messageResources = messageResources;
  }
  /**
   * @return the resources
   */
  protected MessageResources getMessageResources() {
    return this.messageResources;
  }
  
  /**
   * @param target
   * @return
   */
  public abstract String getKeyPrefix(T target);

}
