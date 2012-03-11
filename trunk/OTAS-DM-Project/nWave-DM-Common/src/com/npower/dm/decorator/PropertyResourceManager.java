/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/PropertyResourceManager.java,v 1.3 2007/09/10 02:21:14 zhao Exp $
  * $Revision: 1.3 $
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

import com.npower.dm.util.MessageResources;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/09/10 02:21:14 $
 */
public abstract class PropertyResourceManager<T> extends ResourceManager<T> {

  /**
   * 
   */
  protected PropertyResourceManager() {
    super();
  }

  /**
   * 
   */
  protected PropertyResourceManager(MessageResources resources) {
    super(resources);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.decorator.ResourceManager#getKeyPrefix(java.lang.Object)
   */
  @Override
  public abstract String getKeyPrefix(T target);


}
