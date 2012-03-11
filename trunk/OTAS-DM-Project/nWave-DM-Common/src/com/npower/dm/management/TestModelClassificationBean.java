/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestModelClassificationBean.java,v 1.5 2008/09/05 05:56:33 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2008/09/05 05:56:33 $
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
package com.npower.dm.management;

import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.criterion.Expression;

import com.npower.dm.AllTests;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.ModelSelector;
import com.npower.dm.core.selector.CharacterExpression;
import com.npower.dm.core.selector.CriteriaModelSelector;
import com.npower.dm.core.selector.j2me.MidpV1Selector;
import com.npower.dm.core.selector.j2me.MidpV2Selector;
import com.npower.dm.hibernate.management.ModelClassificationBeanImpl;

/**
 * 本测试用例需要预先安装型号数据库.
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/09/05 05:56:33 $
 */
public class TestModelClassificationBean extends TestCase {

  /**
   * @param name
   */
  public TestModelClassificationBean(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ManagementBeanFactory factory = null;
    // Create an instance
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelClassificationBean bean = (ModelClassificationBean)factory.createBean(ModelClassificationBeanImpl.class);
        factory.beginTransaction();
        for (ModelClassification mc: bean.getAllOfModelClassifications()) {
            bean.delete(mc);
        }
        factory.commit();
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
      factory.release();
    }
  }
  
  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
  }
  
  /**
   * @throws Exception
   */
  public void testAdd() throws Exception {

    String extID = "MC1";
    String name = "MC1";

    ManagementBeanFactory factory = null;
    // Create an instance
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelClassificationBean bean = (ModelClassificationBean)factory.createBean(ModelClassificationBeanImpl.class);
        
        String os = "S60 3rd Edition, Feature Pack 1"; // S60 3rd edition (Symbian OS , Series 60 UI)
        CriteriaModelSelector selector = new CriteriaModelSelector();
        selector.addExpression(Expression.or(CharacterExpression.eq("general.developer.platform", os), 
                                             CharacterExpression.eq("features.os", os)));

        ModelClassification mc = bean.newInstance(extID, name, selector);
        factory.beginTransaction();
        bean.update(mc);
        factory.commit();
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
      factory.release();
    }

    // Load an instance
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID("NOKIA");
        Model model1 = modelBean.getModelByManufacturerModelID(manufacturer, "6120c");
        Model model2 = modelBean.getModelByManufacturerModelID(manufacturer, "6681");

        ModelClassificationBean bean = (ModelClassificationBean)factory.createBean(ModelClassificationBeanImpl.class);
        ModelClassification mc = bean.getModelClassificationByExtID(extID);
        assertNotNull(mc);
        assertTrue(mc.isMemeber(model1));
        assertFalse(mc.isMemeber(model2));
        
        assertTrue(mc.getModelSelector().getModels().contains(model1));
        assertFalse(mc.getModelSelector().getModels().contains(model2));
    } catch (Exception ex) {
      throw ex;
    } finally {
      factory.release();
    }
  }
  
  public void testCase2() throws Exception {
    ManagementBeanFactory factory = null;
    // Create an instance
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelClassificationBean bean = (ModelClassificationBean)factory.createBean(ModelClassificationBeanImpl.class);
        
        CriteriaModelSelector selector = new CriteriaModelSelector();
        selector.addExpression(Expression.eq("familyExternalID", "family_nokia_s40_3rd"));

        ModelClassification mc = bean.newInstance("Nokia S40 3rd", "Nokia S40 3rd", selector);
        mc.setDescription("Nokia S40 3rd");
        factory.beginTransaction();
        bean.update(mc);
        factory.commit();
        
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
      factory.release();
    }
    
    // Create an instance
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelClassificationBean bean = (ModelClassificationBean)factory.createBean(ModelClassificationBeanImpl.class);
        
        ModelClassification mc = bean.getModelClassificationByExtID("Nokia S40 3rd");
        Set<Model> models = mc.getModelSelector().getModels();
        for (Model m: models) {
            System.out.println(m.getManufacturer().getName() + " " + m.getName());
        }
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
      factory.release();
    }
  }

  /**
   * @param factory
   * @param extID
   * @param name
   * @param description
   * @throws DMException
   */
  private ModelClassification createModelClassByFamilyID(ManagementBeanFactory factory, String extID, String name, String description)
      throws DMException {
    CriteriaModelSelector selector = new CriteriaModelSelector();
    selector.addExpression(Expression.eq("familyExternalID", extID));

    return createModelClassification(factory, selector, extID, name, description);
  }

  /**
   * @param factory
   * @param extID
   * @param name
   * @param description
   * @param selector
   * @return
   * @throws DMException
   */
  private ModelClassification createModelClassification(ManagementBeanFactory factory, ModelSelector selector, String extID, String name,
      String description) throws DMException {
    ModelClassificationBean bean = factory.createModelClassificationBean();
    
    ModelClassification mc = bean.newInstance(extID, name, selector);
    mc.setDescription(description);
    
    factory.beginTransaction();
    bean.update(mc);
    factory.commit();
    return mc;
  }

  public void testSetupClassifications() throws Exception {
    ManagementBeanFactory factory = null;
    // Create an instance
    try {
        factory = AllTests.getManagementBeanFactory();
        // Create ModelClassification based ont model families
        createModelClassByFamilyID(factory, "family_microsoft_windows_mobile_2003", "Microsoft Windows Mobile 2003", "Microsoft Windows Mobile 2003");
        createModelClassByFamilyID(factory, "family_microsoft_windows_mobile_5.0", "Microsoft Windows Mobile 5.0", "Microsoft Windows Mobile 5.0");
        createModelClassByFamilyID(factory, "family_microsoft_windows_mobile_6.0", "Microsoft Windows Mobile 6.0", "Microsoft Windows Mobile 6.0");
        createModelClassByFamilyID(factory, "family_motorola_java_platform_p2k_p044", "Motorola Java Platform P2K p044", "Motorola Java Platform P2K p044");
        createModelClassByFamilyID(factory, "family_motorola_linux_ezx_1st", "Motorola Linux ezx 1st", "Motorola Linux ezx 1st");
        createModelClassByFamilyID(factory, "family_motorola_linux_ezx_2nd", "Motorola Linux ezx 2nd", "Motorola Linux ezx 2nd");
        createModelClassByFamilyID(factory, "family_motorola_linux_ezx_3rd", "Motorola Linux ezx 3rd", "Motorola Linux ezx 3rd");
        createModelClassByFamilyID(factory, "family_motorola_p2k_1st_c650", "Motorola p2k 1st c650", "Motorola p2k 1st c650");
        createModelClassByFamilyID(factory, "family_motorola_p2k_1st_e398", "Motorola p2k 1st e398", "Motorola p2k 1st e398");
        createModelClassByFamilyID(factory, "family_motorola_p2k_1st_v600", "Motorola p2k 1st v600", "Motorola p2k 1st v600");
        createModelClassByFamilyID(factory, "family_motorola_p2k_2nd_l6", "Motorola p2k 2nd l6", "Motorola p2k 2nd l6");
        createModelClassByFamilyID(factory, "family_motorola_p2k_2nd_l7", "Motorola p2k 2nd l7", "Motorola p2k 2nd l7");
        createModelClassByFamilyID(factory, "family_motorola_p2k_3rd", "Motorola p2k 3rd", "Motorola p2k 3rd");
        createModelClassByFamilyID(factory, "family_motorola_p2k_unkown", "Motorola p2k Unkown", "Motorola p2k Unkown");
        createModelClassByFamilyID(factory, "family_nokia_s40_1st", "Nokia S40 1st", "Nokia S40 1st");
        createModelClassByFamilyID(factory, "family_nokia_s40_2nd", "Nokia S40 2nd", "Nokia S40 2nd");
        createModelClassByFamilyID(factory, "family_nokia_s40_3rd", "Nokia S40 3rd", "Nokia S40 3rd");
        createModelClassByFamilyID(factory, "family_nokia_s40_5th", "Nokia S40 5th", "Nokia S40 5th");
        createModelClassByFamilyID(factory, "family_nokia_s60_2nd", "Nokia S60 2nd", "Nokia S60 2nd");
        createModelClassByFamilyID(factory, "family_nokia_s60_2nd_fp1", "Nokia S60 2nd fp1", "Nokia S60 2nd fp1");
        createModelClassByFamilyID(factory, "family_nokia_s60_2nd_fp2", "Nokia S60 2nd fp2", "Nokia S60 2nd fp2");
        createModelClassByFamilyID(factory, "family_nokia_s60_2nd_fp3", "Nokia S60 2nd fp3", "Nokia S60 2nd fp3");
        createModelClassByFamilyID(factory, "family_nokia_s60_3rd", "Nokia S60 3rd", "Nokia S60 3rd");
        createModelClassByFamilyID(factory, "family_nokia_s60_3rd_fp1", "Nokia S60 3rd fp1", "Nokia S60 3rd fp1");
        createModelClassByFamilyID(factory, "family_nokia_s80_2nd", "Nokia S80 2nd", "Nokia S80 2nd");
        createModelClassByFamilyID(factory, "family_oma_cp_1_1_default", "oma cp 1.1 Default", "oma cp 1.1 Default");
        createModelClassByFamilyID(factory, "family_samsung_agere_platform_U_serial", "Samsung Agere Platform U serial", "Samsung Agere Platform U serial");
        createModelClassByFamilyID(factory, "family_samsung_agere_platform_basic", "Samsung Agere Platform Basic", "Samsung Agere Platform Basic");
        createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_1.0", "SonyEricsson DM v1.0", "SonyEricsson DM v1.0");
        createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_2.0", "SonyEricsson DM v2.0", "SonyEricsson DM v2.0");
        createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_3.0", "SonyEricsson DM v3.0", "SonyEricsson DM v3.0");
        createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_3.1", "SonyEricsson DM v3.1", "SonyEricsson DM v3.1");
        createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_3.2", "SonyEricsson DM v3.2", "SonyEricsson DM v3.2");
        createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_3.3", "SonyEricsson DM v3.3", "SonyEricsson DM v3.3");
        createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_4.0", "SonyEricsson DM v4.0", "SonyEricsson DM v4.0");
        createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_4.1", "SonyEricsson DM v4.1", "SonyEricsson DM v4.1");
        createModelClassByFamilyID(factory, "family_symbian_uiq_2.1", "Symbian UIQ 2.1", "Symbian UIQ 2.1");
        createModelClassByFamilyID(factory, "family_symbian_uiq_3.0", "Symbian UIQ 3.0", "Symbian UIQ 3.0");
        createModelClassByFamilyID(factory, "family_symbian_uiq_3.1", "Symbian UIQ 3.1", "Symbian UIQ 3.1");
        
        // Create ModelClassifications
        this.createModelClassification(factory, new MidpV1Selector(), "Java MIDP 1.0 Platform", "Java MIDP 1.0 Platform", "Java MIDP 1.0 Platform");
        this.createModelClassification(factory, new MidpV2Selector(), "Java MIDP 2.0 Platform", "Java MIDP 2.0 Platform", "Java MIDP 2.0 Platform");
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
      factory.release();
    }
    
    // Load and display
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelClassificationBean bean = (ModelClassificationBean)factory.createBean(ModelClassificationBeanImpl.class);
        for (ModelClassification mc: bean.getAllOfModelClassifications()) {
            Set<Model> models = mc.getModelSelector().getModels();
            for (Model m: models) {
                System.out.println(mc.getName() + ":" + m.getManufacturer().getName() + " " + m.getName());
            }
        }
    } catch (Exception ex) {
      factory.rollback();
      throw ex;
    } finally {
      factory.release();
    }
  }

}
