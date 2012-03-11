/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/Decorator.java,v 1.1 2007/09/05 11:24:25 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/09/05 11:24:25 $
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

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/09/05 11:24:25 $
 */
public abstract class Decorator<T> {
  
  private T decoratee = null;
  
  private Locale locale = Locale.getDefault();
  
  private ResourceManager<T> resourceManager = null;


  /**
   * Default constructor
   */
  public Decorator() {
    super();
  }
  

  /**
   * @param decoratee
   * @param locale
   */
  public Decorator(T decoratee, Locale locale, ResourceManager<T> resources) {
    super();
    this.decoratee = decoratee;
    this.locale = locale;
    this.resourceManager = resources;
  }


  /**
   * @return
   */
  public T getDecoratee() {
    return this.decoratee;
  }

  /**
   * @param decoratee
   */
  public void setDecoratee(T decoratee) {
    this.setDecoratee(decoratee);
  }

  /**
   * @return
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * @param locale
   */
  public void setLocale(Locale locale) {
    this.locale = locale;
  }


  /**
   * @return the resourceManager
   */
  public ResourceManager<T> getResourceManager() {
    return resourceManager;
  }


  /**
   * @param resourceManager the resourceManager to set
   */
  public void setResourceManager(ResourceManager<T> resources) {
    this.resourceManager = resources;
  }

}
