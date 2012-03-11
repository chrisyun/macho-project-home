/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/ProfileTemplateBean.java,v 1.13 2007/12/27 03:31:28 zhao Exp $
 * $Revision: 1.13 $
 * $Date: 2007/12/27 03:31:28 $
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

import java.io.InputStream;
import java.util.List;

import com.npower.dm.core.AttributeTypeValue;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;

/**
 * All operations against ProfileTemplate wil be conducted through this Bean.
 * @author Zhao DongLu
 * 
 */
public interface ProfileTemplateBean extends BaseBean {

  // Methods relate with ProfileCategoryEntity
  // ****************************************************************

  /**
   * Create a instance of profile category
   */
  public abstract ProfileCategory newProfileCategoryInstance() throws DMException;

  /**
   * Create a instance of profile category
   * @param name
   * @param description
   * @return
   * @throws DMException
   */
  public abstract ProfileCategory newProfileCategoryInstance(String name, String description) throws DMException;

  /**
   * Add a category
   * 
   * @param category
   * @throws DMException
   */
  public abstract void updateCategory(ProfileCategory category) throws DMException;

  /**
   * Load ProfileCategoryEntity by ID
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract ProfileCategory getProfileCategoryByID(String id) throws DMException;

  /**
   * Load a ProfileCategoryEntity by name
   * 
   * @param name
   * @return
   * @throws DMException
   */
  public abstract ProfileCategory getProfileCategoryByName(String name) throws DMException;

  /**
   * Retrieve all of ProfileCategories by where Clause. Where Clause is "from
   * ProfileCategoryEntity where..."
   * 
   * @return List Array of CarrierEntity
   * @throws DMException
   */
  public abstract List<ProfileCategory> findProfileCategories(String whereClause) throws DMException;

  /**
   * Retrieve all of ProfileCategories
   * 
   * @return List Array of CarrierEntity
   * @throws DMException
   */
  public abstract List<ProfileCategory> getAllOfProfileCategories() throws DMException;

  /**
   * Delete a profileCategory. it will remove all of related with
   * profileCategory.
   * 
   * @param country
   * @throws DMException
   */
  public abstract void deleteCategory(ProfileCategory profileCategory) throws DMException;

  /**
   * Parsing ProfileCategoryEntity form InputStream which is a xml file defined
   * by ProfileMetaData.dtd.
   * 
   * @param in
   *          InputStream
   * @return List Array of ProfileCategoryEntity class
   * @throws DMException
   */
  public abstract List<ProfileCategory> parseCategory(InputStream in) throws DMException;

  /**
   * Import ProfileCategoryEntity from XMLInpurtStream defined by
   * ProfileMetaData.xml. The Import program will ignore ProfileCategoryEntity
   * which is exists by Category's name.
   * 
   * @param in
   *          Total categories in XML
   * @throws DMException
   */
  public abstract int importCategory(InputStream in) throws DMException;
  
  /**
   * Create a instance of ProfileAttributeType
   * @return
   * @throws DMException
   */
  public abstract ProfileAttributeType newAttributeTypeInstance() throws DMException;

  /**
   * Add or Update a AttributeType into DM Inventory
   * 
   * @param attributeTyep
   *          ProfileAttributeTypeEntity
   * @throws DMException
   */
  public abstract void updateAttributeType(ProfileAttributeType attributeType) throws DMException;

  /**
   * Load ProfileAttributeTypeEntity by ID
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract ProfileAttributeType getProfileAttributeTypeByID(String id) throws DMException;

  /**
   * Load a ProfileAttributeTypeEntity by name
   * 
   * @param name
   * @return
   * @throws DMException
   */
  public abstract ProfileAttributeType getProfileAttributeTypeByName(String name) throws DMException;

  /**
   * Retrieve all of ProfileAttributeTypeEntity by where Clause. Where Clause is
   * "from ProfileAttributeTypeEntity where..."
   * 
   * @return List Array of CarrierEntity
   * @throws DMException
   */
  public abstract List<ProfileAttributeType> findProfileAttributeTypes(String whereClause) throws DMException;

  /**
   * Delete a profileCategory. it will remove all of related with
   * profileCategory.
   * 
   * @param country
   * @throws DMException
   */
  public abstract void deleteAttributeType(ProfileAttributeType type) throws DMException;

  /**
   * Parsing ProfileAttributeTypeEntity form InputStream which is a xml file
   * defined by ProfileMetaData.dtd.
   * 
   * @param in
   *          InputStream
   * @return List Array of ProfileAttributeTypes class
   * @throws DMException
   */
  public abstract List<ProfileAttributeType> parseProfileAttributeType(InputStream in) throws DMException;

  /**
   * Import ProfileAttributeTypeEntity from XMLInpurtStream defined by
   * ProfileMetaData.xml. The Import program will ignore
   * ProfileAttributeTypeEntity which is exists by Category's name.
   * 
   * @param in
   *          Total ProfileAttributeTypes in XML
   * @throws DMException
   */
  public abstract int importProfileAttributeType(InputStream in) throws DMException;

  /**
   * Create an AttributeTypeValue for the ProfileAttributeType
   * @return
   * @throws DMException
   */
  public abstract AttributeTypeValue newAttributeTypeValueInstance(ProfileAttributeType type) throws DMException;
  
  /**
   * Update or add a Attribute TypeValue
   * @param attributeTypeValue
   * @throws DMException
   */
  public abstract void update(AttributeTypeValue attributeTypeValue) throws DMException;
  
  /**
   * Delete a AttributeValueType
   * @param attributeTypeValue
   * @throws DMException
   */
  public abstract void delete(AttributeTypeValue attributeTypeValue) throws DMException;

  /**
   * Find a Attribute Type by it's ID
   * @param id
   * @return
   * @throws DMException
   */
  public abstract AttributeTypeValue getAttributeTypeValueByID(String id) throws DMException;

  // Methods relate with ProfileAttributeEntity
  // **********************************************************
  /**
   * Create an instance of ProfileAttribute
   * @throws DMException
   */
  public abstract ProfileAttribute newAttributeInstance() throws DMException;

  /**
   * Add a Attribute into DeviceEntity Inventory. If the attribute reference a
   * ProfileAttributeTypeEntity,make sure the ProfileAttributeEntity exist in DM
   * inventory. Notice: The Attribute which will be added must include a
   * AttributeType.
   * 
   * @param attribute
   *          Attribute
   * @throws DMException
   */
  public abstract void update(ProfileAttribute attribute) throws DMException;

  /**
   * Fina a ProfileAttributeTypeEntity by ID.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract ProfileAttribute getProfileAttributeByID(String id) throws DMException;

  /**
   * Find a ProfileAttributeEntity by name for specified template by template's
   * name. If a name reference more attributes, will throw a DMException. In DM
   * inventory, it's allowed which a name reference more attributes. but from
   * this method, this will failures, if you want load all of attributes which
   * own the name, please call findXXXX().
   * 
   * @param name
   * @return
   * @throws DMException
   */
  public abstract ProfileAttribute getProfileAttributeByName(String templateName, String name) throws DMException;

  /**
   * Find Profile Attributes.
   * For exmaple: 
   * 
   * @param clause
   * @return
   * @throws DMException
   */
  public abstract List<ProfileAttribute> findProfileAttributes(String clause) throws DMException;

  /**
   * Delete a profileAttribute. 
   * 
   * @param country
   * @throws DMException
   */
  public abstract void delete(ProfileAttribute attr) throws DMException;

  // Methods relate with ProfileTemplateEntity
  // **********************************************************
  
  /**
   * Create an instance of ProfileTemplate
   * @throws DMException
   */
  public abstract ProfileTemplate newTemplateInstance() throws DMException;
  
  
  /**
   * Add a Profile Template into DeviceEntity Inventory. 
   * Make sure the category which be referenced exists in DM
   * inventory. Notice: The Template which will be added must include a
   * ProfileCategoryEntity.
   * 
   * @param template
   *          ProfileTemplateEntity
   * @throws DMException
   */
  public abstract void update(ProfileTemplate template) throws DMException;

  /**
   * Fina a ProfileTemplateEntity by ID.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract ProfileTemplate getTemplateByID(String id) throws DMException;

  /**
   * Find a ProfileTemplateEntity by name. If a name reference more
   * ProfileTemplates, will throw a DMException.
   * 
   * @param name
   * @return
   * @throws DMException
   */
  public abstract ProfileTemplate getTemplateByName(String name) throws DMException;

  public abstract List<ProfileTemplate> findTemplates(String clause) throws DMException;

  /**
   * Delete a ProfileTemplateEntity.
   * 
   * @param country
   * @throws DMException
   */
  public abstract void delete(ProfileTemplate template) throws DMException;

  /**
   * Parsing ProfileTemplates from InputStream which is XML defined by
   * ProfileMetaData.DTD.
   * 
   * @param in
   * @return Array of instance of ProfileTemplateEntity.class
   * @throws DMException
   */
  public abstract List<ProfileTemplate> parseProfileTemplate(InputStream in) throws DMException;

  /**
   * 
   * @param in
   * @return
   * @throws DMException
   */
  public abstract int importProfileTemplates(InputStream in) throws DMException;

}