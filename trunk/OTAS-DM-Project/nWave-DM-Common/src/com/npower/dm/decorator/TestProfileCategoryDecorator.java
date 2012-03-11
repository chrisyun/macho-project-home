/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/TestProfileCategoryDecorator.java,v 1.2 2007/09/10 02:21:14 zhao Exp $
  * $Revision: 1.2 $
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

import junit.framework.TestCase;

import com.npower.dm.core.ProfileCategory;
import com.npower.dm.hibernate.entity.ProfileCategoryEntity;
import com.npower.dm.util.MessageResources;
import com.npower.dm.util.MessageResourcesFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/09/10 02:21:14 $
 */
public class TestProfileCategoryDecorator extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testProfileCategoryName() throws Exception {
    MessageResourcesFactory factory = MessageResourcesFactory.createFactory();
    MessageResources messageResources = factory.createResources("com/npower/dm/decorator/dictionary");

    ResourceManager<ProfileCategory> resources = new ProfileCategoryResourceManager(messageResources);
    
    {
      ProfileCategory category = new ProfileCategoryEntity();
      category.setName("NAP");
      category.setDescription("description");
      
      ProfileCategoryDecorator decorator = new ProfileCategoryDecorator(Locale.CHINA, category, resources);
      assertEquals("»¥ÁªÍø½ÓÈë", decorator.getName());
    }
  }

}
