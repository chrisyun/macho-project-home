/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/ProfileConfigBean.java,v 1.12 2008/01/12 11:17:17 zhao Exp $
 * $Revision: 1.12 $
 * $Date: 2008/01/12 11:17:17 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileTemplate;

/**
 * All of operations against ProfileConfig will be conducted through this ProfileConfigBean.
 * ProfileConfigEntity Management Bean.
 * 
 * @author Zhao DongLu
 * 
 */
public interface ProfileConfigBean extends BaseBean {

  /**
   * Create an instance of ProfileConfig
   * @param name
   *        Name of ProfileConfig
   * @param carrier
   *        Carrier
   * @param template
   *        ProfileTemplate
   * @param profileType
   * @return
   * @throws DMException
   */
  public ProfileConfig newProfileConfigInstance(String name, Carrier carrier, ProfileTemplate template,
      String profileType) throws DMException;

  /**
   * Create an instance of ProfileConfig
   * @param externalID
   * @param name
   * @param carrier
   * @param template
   * @param profileType
   * @return
   * @throws DMException
   */
  public ProfileConfig newProfileConfigInstance(String externalID, String name, Carrier carrier, ProfileTemplate template,
      String profileType) throws DMException;
  
  /**
   * Add a ProfileConfigEntity into DM inventory.
   * 
   * @param profile
   *          ProfileConfigEntity
   * @throws DMException
   */
  public void update(ProfileConfig profile) throws DMException;

  /**
   * Get and find a ProfileConfigEntity by ID
   * 
   * @param id
   *          String ID of the ProfileConfigEntity
   */
  public ProfileConfig getProfileConfigByID(String id) throws DMException;

  /**
   * Get and find a ProfileConfigEntity by External ID
   * @param externalID
   * @return
   * @throws DMException
   */
  public ProfileConfig getProfileConfigByExternalID(String externalID) throws DMException;

  /**
   * Find a ProfileConfigEntity By it's name.
   * 
   * @param carrierExtID
   *        External ID of carrier
   * @param categoryName
   *        Name of category
   * @param name
   *        Name of the ProfileConfig
   * @return
   * @throws DMException
   */
  public ProfileConfig getProfileConfigByName(String carrierExtID, String categoryName, String name) throws DMException;

  /**
   * Fina and list all of the ProfileConfigs by the HibernateSQL. For Exmaple:
   * "from ProfileConfigEntity where ...."
   * 
   * @param whereClause
   *          String, whereClause
   */
  public List<ProfileConfig> findProfileConfigs(String whereClause) throws DMException;

  /**
   * Delete a ProfileConfigEntity from the DM inventory.
   * 
   * @param config
   *          ProfileConfigEntity
   */
  public void deleteProfileConfig(ProfileConfig config) throws DMException;

  /**
   * Add or Update the value of ProfileAttributeEntity own by the
   * ProfileConfig. 
   * 
   * If not ProfileAttributeValue was found by the attributeName, will create a single value, binary mode value object. 
   * If found it, will override the attribute to single value, binary mode value's object. 
   * 
   * all of multi-value item will be deleted.
   * 
   * Caution: Assign null to value is permitted. this will set the value to
   * null, AttributeValue will not be deleted!
   * 
   * Caution: Order of AttributeValue will automaticlly increased! The
   * AttributeValue added lastestly will be bottom.
   * 
   * @param name
   * @param value
   * @throws DMException
   */
  public void setAttributeValue(ProfileConfig config, String name, InputStream value) throws DMException, IOException;

  /**
   * Add or UpdateEntity the value of ProfileAttributeEntity ownen by the
   * ProfileConfigEntity. If not found ProfileAttributeValueEntity by the
   * attributename, will create a single value, text mode value object. If found
   * it, will override the attribute to single value, text mode value's object.
   * all of multi-value item will be deleted.
   * 
   * Caution: Assign null to value is permitted. this will set the value to
   * null, AttributeValue will not be deleted!
   * 
   * Caution: Order of AttributeValue will automaticlly increased! The
   * AttributeValue added lastestly will be bottom.
   * 
   * @param name
   * @param value
   * @throws DMException
   */
  public void setAttributeValue(ProfileConfig config, String name, String value) throws DMException;

  /**
   * Add or update the AttributeValue specified by the name. This is modifier of
   * AttributeValue in multiple-value mode.
   * 
   * Caution: Assign null to value is permitted. this will set the value to
   * null, AttributeValue will not be deleted!
   * 
   * Caution: Order of AttributeValue will automaticlly increased! The
   * AttributeValue added lastestly will be bottom.
   * 
   * @param name
   *          Attribute's name
   * @param value
   *          String[] array of multi-value
   * @throws DMException
   */
  public void setAttributeValue(ProfileConfig config, String name, String value[]) throws DMException;

  /**
   * Add or update the value of AttributeValue specified by the name. This is
   * modifier of AttributeValue in multiple-value, binary mode.
   * 
   * Caution: Assign null to value is permitted. this will set the value to
   * null, AttributeValue will not be deleted!
   * 
   * Caution: Order of AttributeValue will automaticlly increased! The
   * AttributeValue added lastestly will be bottom.
   * 
   * @param name
   *          Attribute's name
   * @param value
   *          String[] array of multi-value
   * @throws DMException
   */
  public void setAttributeValue(ProfileConfig config, String name, InputStream value[]) throws DMException, IOException;
}
