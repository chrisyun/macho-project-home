/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/decorator/DecoratorHelper.java,v 1.6 2008/01/23 02:15:18 LAH Exp $
  * $Revision: 1.6 $
  * $Date: 2008/01/23 02:15:18 $
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.npower.dm.core.Country;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.util.MessageResources;
import com.npower.dm.util.MessageResourcesFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/01/23 02:15:18 $
 */
public class DecoratorHelper {

  private static ResourceManager<Country> countryResourceManager = null;

  private static ResourceManager<Manufacturer> manufacturerResourceManager = null;

  private static ResourceManager<ProfileCategory> profileCategoryResourceManager = null;

  private static ResourceManager<ProfileAttribute> profileAttributeResourceManager = null;

  private static ResourceManager<ProfileConfig>  profileConfigResourceManager = null;
  
  static {
    MessageResourcesFactory factory = MessageResourcesFactory.createFactory();
    MessageResources resources = factory.createResources("com/npower/dm/decorator/dictionary");
    countryResourceManager = new CountryResourceManager(resources);
    manufacturerResourceManager = new ManufacturerResourceManager(resources);
    profileCategoryResourceManager = new ProfileCategoryResourceManager(resources);
    profileAttributeResourceManager = new ProfileAttributeResourceManager(resources);
    profileConfigResourceManager = new ProfileConfigResourceManager(resources);
  }
  /**
   * 
   */
  private DecoratorHelper() {
    super();
  }
  
  /**
   * Decorate Country List for I18n 
   * @param countries
   * @param locale
   * @param resources
   * @return
   */
  public static List<Country> decorateCountry(Collection<Country> countries, Locale locale) {
    List<Country> result = new ArrayList<Country>();
    for (Country country: countries) {
        Country decorator = decorate(country, locale);
        result.add(decorator);
    }
    return result;
  }

  /**
   * Decorate Country for I18n 
   * @param country
   * @param locale
   * @param resources
   * @return
   */
  public static Country decorate(Country country, Locale locale) {
    Country decorator = new CountryDecorator(locale, country, countryResourceManager);
    return decorator;
  }

  /**
   * Decorate Manufacturer List for I18n 
   * @param countries
   * @param locale
   * @param resources
   * @return
   */
  public static List<Manufacturer> decorateManufacturer(Collection<Manufacturer> collection, Locale locale) {
    List<Manufacturer> result = new ArrayList<Manufacturer>();
    for (Manufacturer object: collection) {
        Manufacturer decorator = decorate(object, locale);
        result.add(decorator);
    }
    return result;
  }

  /**
   * Decorate Manufacturer for I18n 
   * @param country
   * @param locale
   * @param resources
   * @return
   */
  public static Manufacturer decorate(Manufacturer decoratee, Locale locale) {
    Manufacturer decorator = new ManufacturerDecorator(locale, decoratee, manufacturerResourceManager);
    return decorator;
  }

  /**
   * Decorate ProfileCategory List for I18n 
   * @param countries
   * @param locale
   * @param resources
   * @return
   */
  public static List<ProfileCategory> decorateProfileCategory(Collection<ProfileCategory> collection, Locale locale) {
    List<ProfileCategory> result = new ArrayList<ProfileCategory>();
    for (ProfileCategory object: collection) {
      ProfileCategory decorator = decorate(object, locale);
        result.add(decorator);
    }
    return result;
  }

  /**
   * Decorate ProfileCategory for I18n 
   * @param country
   * @param locale
   * @param resources
   * @return
   */
  public static ProfileCategory decorate(ProfileCategory decoratee, Locale locale) {
    ProfileCategory decorator = new ProfileCategoryDecorator(locale, decoratee, profileCategoryResourceManager);
    return decorator;
  }

  /**
   * Decorate ProfileAttribute List for I18n 
   * @param countries
   * @param locale
   * @param resources
   * @return
   */
  public static List<ProfileAttribute> decorateProfileAttribute(Collection<ProfileAttribute> collection, Locale locale) {
    List<ProfileAttribute> result = new ArrayList<ProfileAttribute>();
    for (ProfileAttribute object: collection) {
        ProfileAttribute decorator = decorate(object, locale);
        result.add(decorator);
    }
    return result;
  }

  /**
   * Decorate ProfileAttribute for I18n 
   * @param country
   * @param locale
   * @param resources
   * @return
   */
  public static ProfileAttribute decorate(ProfileAttribute decoratee, Locale locale) {
    ProfileAttribute decorator = new ProfileAttributeDecorator(locale, decoratee, profileAttributeResourceManager );
    return decorator;
  }
  
  /**
   * Decorate ProfileConfig List for I18n
   * @param countries
   * @param locale
   * @param resources
   * @return
   */
  public static List<ProfileConfig> decorateProfileConfig(Collection<ProfileConfig> collection, Locale locale) {
    List<ProfileConfig> result = new ArrayList<ProfileConfig>();
    for (ProfileConfig object : collection) {
      ProfileConfig decorator = decorate(object, locale);
      result.add(decorator);
    }
    return result;
  }

  /**
   * Decorate ProfileConfig for I18n
   * @param country
   * @param locale
   * @param resources
   * @return
   */
  public static ProfileConfig decorate(ProfileConfig decoratee, Locale locale) {
    ProfileConfig decorator = new ProfileConfigDecorator(locale, decoratee, profileConfigResourceManager );
    return decorator;
  }
}
