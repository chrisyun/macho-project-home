/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/TestCountryDecorator.java,v 1.2 2007/09/10 02:21:14 zhao Exp $
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

import com.npower.dm.core.Country;
import com.npower.dm.hibernate.entity.CountryEntity;
import com.npower.dm.util.MessageResources;
import com.npower.dm.util.MessageResourcesFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/09/10 02:21:14 $
 */
public class TestCountryDecorator extends TestCase {

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
  
  public void testCountryName() throws Exception {
    MessageResourcesFactory factory = MessageResourcesFactory.createFactory();
    MessageResources messageResources = factory.createResources("com/npower/dm/decorator/dictionary");

    ResourceManager<Country> resources = new CountryResourceManager(messageResources);
    
    {
      Country country = new CountryEntity();
      country.setName("China");
      country.setISOCode("cn");
      
      CountryDecorator decorator = new CountryDecorator(Locale.CHINA, country, resources);
      assertEquals("中国", decorator.getName());
    }
    {
      Country country = new CountryEntity();
      country.setName("china");
      country.setISOCode("cn");
      
      CountryDecorator decorator = new CountryDecorator(Locale.CHINA, country, resources);
      assertEquals("中国", decorator.getName());
    }
    {
      Country country = new CountryEntity();
      country.setName("CHINA");
      country.setISOCode("cn");
      
      CountryDecorator decorator = new CountryDecorator(Locale.CHINA, country, resources);
      assertEquals("中国", decorator.getName());
    }
    {
      Country country = new CountryEntity();
      country.setName("CHINA");
      country.setISOCode("cn");
      
      CountryDecorator decorator = new CountryDecorator(Locale.ENGLISH, country, resources);
      assertEquals("CHINA", decorator.getName());
    }
    {
      Country country = new CountryEntity();
      country.setName("Hong Kong");
      country.setISOCode("HK");
      
      CountryDecorator decorator = new CountryDecorator(Locale.ENGLISH, country, resources);
      assertEquals("Hong Kong", decorator.getName());
    }
    {
      Country country = new CountryEntity();
      country.setName("Hong Kong");
      country.setISOCode("HK");
      
      CountryDecorator decorator = new CountryDecorator(Locale.CHINA, country, resources);
      assertEquals("中国香港", decorator.getName());
    }
    {
      Country country = new CountryEntity();
      country.setName("Unknown");
      country.setISOCode("Unknown");
      
      CountryDecorator decorator = new CountryDecorator(Locale.CHINA, country, resources);
      assertEquals("Unknown", decorator.getName());
    }
  }

}
