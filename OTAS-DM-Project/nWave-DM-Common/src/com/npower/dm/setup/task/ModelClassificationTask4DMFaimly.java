/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/ModelClassificationTask4DMFaimly.java,v 1.2 2008/09/18 05:50:46 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/09/18 05:50:46 $
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
package com.npower.dm.setup.task;

import org.hibernate.criterion.Expression;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.selector.CriteriaModelSelector;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/09/18 05:50:46 $
 */
public class ModelClassificationTask4DMFaimly extends BaseModelClassificationTask {

  /**
   * Default constructor
   */
  public ModelClassificationTask4DMFaimly() {
    super();
  }

  /**
   * @param factory
   * @param extID
   * @param name
   * @param description
   * @throws DMException
   */
  private ModelClassification createModelClassByFamilyID(ManagementBeanFactory factory, String extID, String name,
      String description) throws DMException {
    CriteriaModelSelector selector = new CriteriaModelSelector();
    selector.addExpression(Expression.eq("familyExternalID", extID));

    return updateModelClassification(factory, selector, extID, name, description);
  }

  /**
   * 为每一个DM Model Family创建与之对应的ModelClassifications.
   * 
   * @param factory
   * @throws DMException
   */
  private void createAllForFamilyID(ManagementBeanFactory factory) throws DMException {
    createModelClassByFamilyID(factory, "family_microsoft_windows_mobile_2003", "Microsoft Windows Mobile 2003",
        "Microsoft Windows Mobile 2003");
    createModelClassByFamilyID(factory, "family_microsoft_windows_mobile_5.0", "Microsoft Windows Mobile 5.0",
        "Microsoft Windows Mobile 5.0");
    createModelClassByFamilyID(factory, "family_microsoft_windows_mobile_6.0", "Microsoft Windows Mobile 6.0",
        "Microsoft Windows Mobile 6.0");
    createModelClassByFamilyID(factory, "family_motorola_java_platform_p2k_p044", "Motorola Java Platform P2K p044",
        "Motorola Java Platform P2K p044");
    createModelClassByFamilyID(factory, "family_motorola_linux_ezx_1st", "Motorola Linux ezx 1st",
        "Motorola Linux ezx 1st");
    createModelClassByFamilyID(factory, "family_motorola_linux_ezx_2nd", "Motorola Linux ezx 2nd",
        "Motorola Linux ezx 2nd");
    createModelClassByFamilyID(factory, "family_motorola_linux_ezx_3rd", "Motorola Linux ezx 3rd",
        "Motorola Linux ezx 3rd");
    createModelClassByFamilyID(factory, "family_motorola_p2k_1st_c650", "Motorola p2k 1st c650",
        "Motorola p2k 1st c650");
    createModelClassByFamilyID(factory, "family_motorola_p2k_1st_e398", "Motorola p2k 1st e398",
        "Motorola p2k 1st e398");
    createModelClassByFamilyID(factory, "family_motorola_p2k_1st_v600", "Motorola p2k 1st v600",
        "Motorola p2k 1st v600");
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
    createModelClassByFamilyID(factory, "family_samsung_agere_platform_U_serial", "Samsung Agere Platform U serial",
        "Samsung Agere Platform U serial");
    createModelClassByFamilyID(factory, "family_samsung_agere_platform_basic", "Samsung Agere Platform Basic",
        "Samsung Agere Platform Basic");
    createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_1.0", "SonyEricsson DM v1.0",
        "SonyEricsson DM v1.0");
    createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_2.0", "SonyEricsson DM v2.0",
        "SonyEricsson DM v2.0");
    createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_3.0", "SonyEricsson DM v3.0",
        "SonyEricsson DM v3.0");
    createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_3.1", "SonyEricsson DM v3.1",
        "SonyEricsson DM v3.1");
    createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_3.2", "SonyEricsson DM v3.2",
        "SonyEricsson DM v3.2");
    createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_3.3", "SonyEricsson DM v3.3",
        "SonyEricsson DM v3.3");
    createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_4.0", "SonyEricsson DM v4.0",
        "SonyEricsson DM v4.0");
    createModelClassByFamilyID(factory, "family_sonyericsson_dm_ver_4.1", "SonyEricsson DM v4.1",
        "SonyEricsson DM v4.1");
    createModelClassByFamilyID(factory, "family_symbian_uiq_2.1", "Symbian UIQ 2.1", "Symbian UIQ 2.1");
    createModelClassByFamilyID(factory, "family_symbian_uiq_3.0", "Symbian UIQ 3.0", "Symbian UIQ 3.0");
    createModelClassByFamilyID(factory, "family_symbian_uiq_3.1", "Symbian UIQ 3.1", "Symbian UIQ 3.1");
  }

  @Override
  protected void createAll(ManagementBeanFactory factory) throws Exception {
    this.createAllForFamilyID(factory);
  }

}
