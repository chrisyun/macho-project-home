/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/BaseTestCase4ProfileConvertor.java,v 1.3 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/06/16 10:11:15 $
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

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/06/16 10:11:15 $
 */
public abstract class BaseTestCase4ProfileConvertor extends TestCase {

  private static final String BASE_DIR = AllTests.BASE_DIR;

  /**
   * Profile Template XML File for Testing
   */
  private static final String[] FILENAMES_PROFILE_METADATA = {
                  BASE_DIR + "/metadata/setup/profiles/profile.meta.install.xml",
                  BASE_DIR + "/metadata/setup/profiles/profile.template.NAP.install.xml",
                  BASE_DIR + "/metadata/setup/profiles/profile.template.PROXY.install.xml",
                  BASE_DIR + "/metadata/setup/profiles/profile.template.MMS.install.xml",
                  BASE_DIR + "/metadata/setup/profiles/profile.template.EMAIL.install.xml",
                  BASE_DIR + "/metadata/setup/profiles/profile.template.SyncDS.install.xml",
                  BASE_DIR + "/metadata/setup/profiles/profile.template.DM.install.xml",
                  BASE_DIR + "/metadata/setup/profiles/profile.template.POC.install.xml",
                  BASE_DIR + "/metadata/setup/profiles/profile.template.IMPS.install.xml",
    };
  protected static final String CARRIER_ID = "CARRIER";

  /**
   * 
   */
  protected BaseTestCase4ProfileConvertor() {
    super();
  }

  /**
   * @param name
   */
  public BaseTestCase4ProfileConvertor(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
    this.setUpProfileMetaData();
    this.setUpCarrier(CARRIER_ID);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Clean all of profile meta data, and import all of profile meta data
   * @throws Exception
   */
  private void setUpProfileMetaData() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
        List<ProfileCategory> categories = templateBean.findProfileCategories("from ProfileCategoryEntity");
        factory.beginTransaction();
        for (ProfileCategory category: categories) {
            templateBean.deleteCategory(category);
        }
        factory.commit();
        // Import Profile Categories
        for (String filename: FILENAMES_PROFILE_METADATA) {
            InputStream in = new FileInputStream(filename);
            templateBean.importCategory(in);
            in.close();
        }
        // Import Profile Attribute Types
        for (String filename: FILENAMES_PROFILE_METADATA) {
            InputStream in = new FileInputStream(filename);
            templateBean.importProfileAttributeType(in);
            in.close();
        }
        // Import Profile Templates
        for (String filename: FILENAMES_PROFILE_METADATA) {
            InputStream in = new FileInputStream(filename);
            templateBean.importProfileTemplates(in);
            in.close();
        }
    } catch (Exception ex) {
      throw ex;
    } finally {
      factory.release();
    }
    
  }

  /**
   * Create a carrier for testing
   * @param carrierID
   * @throws Exception
   */
  public void setUpCarrier(String carrierID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    CarrierBean carrierBean = factory.createCarrierBean();
    try {
      Country country = factory.createCountryBean().getCountryByISOCode("CN");
      assertNotNull(country);
      assertEquals(country.getCountryCode(), "86");
      Carrier carrier = carrierBean.getCarrierByExternalID(carrierID);
      if (carrier == null) {
        carrier = carrierBean.newCarrierInstance(country, carrierID, carrierID);
        factory.beginTransaction();
        carrierBean.update(carrier);
        factory.commit();
      }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

}