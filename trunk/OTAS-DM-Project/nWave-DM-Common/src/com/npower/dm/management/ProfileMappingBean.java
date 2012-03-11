/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/ProfileMappingBean.java,v 1.15 2007/03/06 06:40:41 zhao Exp $
 * $Revision: 1.15 $
 * $Date: 2007/03/06 06:40:41 $
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

import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileNodeMapping;

/**
 * All operations against ProfileMapping will be conducted through this ProfileMappingBean.
 * @author Zhao DongLu
 * 
 */
public interface ProfileMappingBean extends BaseBean {

  // Methods related with ProfileMappingEntity.class
  // *******************************************************************
  /**
   * Add a ProfileMappingEntity into DMInventory, and add iteratlly all Mapping
   * nodes.
   * 
   * @param mapping
   * @throws DMException
   */
  public abstract void update(ProfileMapping mapping) throws DMException;

  /**
   * Create a ProfileMapping instance.
   * @return
   * @throws DMException
   */
  public abstract ProfileMapping newProfileMappingInstance() throws DMException;
  /**
   * Delete a ProfileMappingEntity from DMInventory.
   * 
   * @param mapping
   * @throws DMException
   */
  public abstract void delete(ProfileMapping mapping) throws DMException;

  /**
   * Load a Profile Mapping from DM Inventory.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract ProfileMapping getProfileMappingByID(long id) throws DMException;

  /**
   * Create a Profile Node Mapping instance.
   * @return
   * @throws DMException
   */
  public abstract ProfileNodeMapping newProfileNodeMappingInstance() throws DMException;
  
  /**
   * Find a ProfileNodeMapping by ID
   * @param id
   * @return
   * @throws DMException
   */
  public abstract ProfileNodeMapping getProfileNodeMappingByID(String id) throws DMException;
  
  /**
   * Delete a Profile NodeMapping
   * @param nodeMapping
   * @throws DMException
   */
  public abstract void delete(ProfileNodeMapping nodeMapping) throws DMException;
  
  /**
   * Update the ProfileNodeMapping.
   * @param nodeMapping
   * @throws DMException
   */
  public abstract void update(ProfileNodeMapping nodeMapping) throws DMException;
  
  /**
   * <pre>
   * Parsing a XML which defined by Mapping DDF and introducing a set of profile
   * mappings.
   * 
   * Caution:
   * If defaultManufacturerExternalID and defaultModelExternalID are not null, 
   * will overwrite modelExternalID specified by XML mapping. All of profile mappings defined
   * in XML inputstream will be import into the model which been specified by "defaultModelExternalID".
   * 
   * </pre>
   * @param in
   *        InputStream
   * @param defaultManufacturerExternalID
   * @param defaultModelExternalID
   * @return List Arrary of ProfileMappingEntity
   * @throws DMException
   */
  public abstract List<ProfileMapping> parsingProfileMapping(InputStream in, String defaultManufacturerExternalID, String defaultModelExternalID) throws DMException;

  /**
   * Parsing a XML which defined by Mapping DDF and introducing a set of profile
   * mappings.
   * 
   * @param in
   *        InputStream
   * @return List Arrary of ProfileMappingEntity
   * @throws DMException
   */
  public abstract List<ProfileMapping> parsingProfileMapping(InputStream in) throws DMException;

  /**
   * <pre>
   * Import ProfileMappingEntity from XMLInpurtStream defined by
   * ProfileMappingEntity XML file. The Import program will ignore
   * ProfileMappingEntity which is exists by ProfileMappingEntity name.
   * 
   * Caution:
   * If defaultManufacturerExternalID and defaultModelExternalID are not null, 
   * will overwrite modelExternalID specified by XML mapping. All of profile mappings defined
   * in XML inputstream will be import into the model which been specified by "defaultModelExternalID".
   * 
   * </pre>
   * @param in
   *        InputStream
   * @param defaultManufacturerExternalID
   * @param defaultModelExternalID
   * @return Array of ProfileMappingEntity
   * @throws DMException
   */
  public abstract List<ProfileMapping> importProfileMapping(InputStream in, String defaultManufacturerExternalID, String defaultModelExternalID) throws DMException;

  /**
   * Import ProfileMappingEntity from XMLInpurtStream defined by
   * ProfileMappingEntity XML file. The Import program will ignore
   * ProfileMappingEntity which is exists by ProfileMappingEntity name.
   * 
   * @@return
   *          Array of ProfileMappingEntity
   * @throws DMException
   */
  public abstract List<ProfileMapping> importProfileMapping(InputStream in) throws DMException;

}