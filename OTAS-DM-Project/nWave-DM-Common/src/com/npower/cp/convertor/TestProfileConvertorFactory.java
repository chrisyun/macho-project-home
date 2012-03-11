/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/TestProfileConvertorFactory.java,v 1.3 2007/08/30 11:30:03 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/08/30 11:30:03 $
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
package com.npower.cp.convertor;

import junit.framework.TestCase;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.hibernate.entity.ProfileCategoryEntity;
import com.npower.dm.hibernate.entity.ProfileConfigEntity;
import com.npower.dm.hibernate.entity.ProfileTemplateEntity;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/08/30 11:30:03 $
 */
public class TestProfileConvertorFactory extends TestCase {

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

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertorFactory#newInstance()}.
   */
  public void testNewInstance() throws Exception {
    assertNotNull(ProfileConvertorFactory.newInstance());
    assertTrue(ProfileConvertorFactory.newInstance() instanceof ProfileConvertorFactory);
  }

  /**
   * Test method for {@link com.npower.cp.convertor.ProfileConvertorFactory#getProfileConvertor(com.npower.dm.core.ProfileConfig)}.
   */
  public void testGetProfileConvertor() throws Exception {
    ProfileConvertorFactory factory = ProfileConvertorFactory.newInstance();
    {
      ProfileConfig profile = new ProfileConfigEntity();
      ProfileTemplate template = new ProfileTemplateEntity();
      ProfileCategory category = new ProfileCategoryEntity(ProfileCategory.NAP_CATEGORY_NAME);
      template.setProfileCategory(category);
      profile.setProfileTemplate(template);
      
      ProfileValueFetcher fetcher = new ProfileValueFetcher();
      fetcher.setProfile(profile);
      ProfileConvertor convertor = factory.getProfileConvertor(profile, fetcher);
      assertNotNull(convertor);
      assertTrue(convertor instanceof ProfileConvertor4NAP);
    }
    {
      ProfileConfig profile = new ProfileConfigEntity();
      ProfileTemplate template = new ProfileTemplateEntity();
      ProfileCategory category = new ProfileCategoryEntity(ProfileCategory.PROXY_CATEGORY_NAME);
      template.setProfileCategory(category);
      profile.setProfileTemplate(template);
      
      ProfileValueFetcher fetcher = new ProfileValueFetcher();
      fetcher.setProfile(profile);
      ProfileConvertor convertor = factory.getProfileConvertor(profile, fetcher);
      assertNotNull(convertor);
      assertTrue(convertor instanceof ProfileConvertor4Proxy);
    }
    {
      ProfileConfig profile = new ProfileConfigEntity();
      ProfileTemplate template = new ProfileTemplateEntity();
      ProfileCategory category = new ProfileCategoryEntity(ProfileCategory.MMS_CATEGORY_NAME);
      template.setProfileCategory(category);
      profile.setProfileTemplate(template);
      
      ProfileValueFetcher fetcher = new ProfileValueFetcher();
      fetcher.setProfile(profile);
      ProfileConvertor convertor = factory.getProfileConvertor(profile, fetcher);
      assertNotNull(convertor);
      assertTrue(convertor instanceof ProfileConvertor4MMS);
    }
    {
      ProfileConfig profile = new ProfileConfigEntity();
      ProfileTemplate template = new ProfileTemplateEntity();
      ProfileCategory category = new ProfileCategoryEntity(ProfileCategory.EMAIL_CATEGORY_NAME);
      template.setProfileCategory(category);
      profile.setProfileTemplate(template);
      
      ProfileValueFetcher fetcher = new ProfileValueFetcher();
      fetcher.setProfile(profile);
      ProfileConvertor convertor = factory.getProfileConvertor(profile, fetcher);
      assertNotNull(convertor);
      assertTrue(convertor instanceof ProfileConvertor4Email);
    }
    {
      ProfileConfig profile = new ProfileConfigEntity();
      ProfileTemplate template = new ProfileTemplateEntity();
      ProfileCategory category = new ProfileCategoryEntity(ProfileCategory.SYNC_DS_CATEGORY_NAME);
      template.setProfileCategory(category);
      profile.setProfileTemplate(template);
      
      ProfileValueFetcher fetcher = new ProfileValueFetcher();
      fetcher.setProfile(profile);
      ProfileConvertor convertor = factory.getProfileConvertor(profile, fetcher);
      assertNotNull(convertor);
      assertTrue(convertor instanceof ProfileConvertor4SyncDS);
    }
    {
      ProfileConfig profile = new ProfileConfigEntity();
      ProfileTemplate template = new ProfileTemplateEntity();
      ProfileCategory category = new ProfileCategoryEntity(ProfileCategory.DM_CATEGORY_NAME);
      template.setProfileCategory(category);
      profile.setProfileTemplate(template);
      
      ProfileValueFetcher fetcher = new ProfileValueFetcher();
      fetcher.setProfile(profile);
      ProfileConvertor convertor = factory.getProfileConvertor(profile, fetcher);
      assertNotNull(convertor);
      assertTrue(convertor instanceof ProfileConvertor4DM);
    }
    {
      ProfileConfig profile = new ProfileConfigEntity();
      ProfileTemplate template = new ProfileTemplateEntity();
      ProfileCategory category = new ProfileCategoryEntity(null);
      template.setProfileCategory(category);
      profile.setProfileTemplate(template);
      
      try {
          ProfileValueFetcher fetcher = new ProfileValueFetcher();
          fetcher.setProfile(profile);
          factory.getProfileConvertor(profile, fetcher);
          fail();
      } catch (DMException e) {
      }
    }
    {
      ProfileConfig profile = new ProfileConfigEntity();
      ProfileTemplate template = new ProfileTemplateEntity();
      ProfileCategory category = new ProfileCategoryEntity("UNKOWN");
      template.setProfileCategory(category);
      profile.setProfileTemplate(template);
      
      try {
          ProfileValueFetcher fetcher = new ProfileValueFetcher();
          fetcher.setProfile(profile);
          factory.getProfileConvertor(profile, fetcher);
          fail();
      } catch (DMException e) {
      }
    }
  }

}
