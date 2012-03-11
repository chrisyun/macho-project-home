/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/UpdateImageBean.java,v 1.9 2008/11/19 07:52:20 chenlei Exp $
 * $Revision: 1.9 $
 * $Date: 2008/11/19 07:52:20 $
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

import com.npower.dm.core.DMException;
import com.npower.dm.core.Image;
import com.npower.dm.core.ImageUpdateStatus;
import com.npower.dm.core.Model;
import com.npower.dm.core.Update;

/**
 * All operations against Update and Images will be conducted through this Bean.
 * @author Zhao DongLu
 * 
 */
public interface UpdateImageBean extends BaseBean {

  public abstract Update newUpdateInstance(Image from, Image to) throws DMException;

  public abstract Update newUpdateInstance(Image from, Image to, InputStream bytes) throws DMException, IOException;

  /**
   * Add or update a UpdateEntity into DM inventory.
   * 
   * @param update
   * @throws DMException
   */
  public abstract void update(Update update) throws DMException;

  /**
   * Load an UpdateEntity form DM inventory by it's ID
   * 
   * @param id
   * @return UpdateEntity
   * @throws DMException
   */
  public abstract Update getUpdateByID(String id) throws DMException;
  
  /**
   * Load update 
   * @param model
   * @param fromVersion
   * @param toVersion
   * @return
   * @throws DMException
   */
  public abstract Update getUpdate(Model model, String fromVersion, String toVersion) throws DMException;

  /**
   * Find a UpdateEntity which fromImage has versionID for specified model.
   * 
   * @param model
   * @param versionId
   * @return
   * @throws DMException
   */
  public abstract List<Update> findUpdatesByFromImageVersionID(Model model, String versionId) throws DMException;

  /**
   * Find a UpdateEntity which ToImage has versionID for specified model.
   * 
   * @param model
   * @param versionId
   * @return
   * @throws DMException
   */
  public abstract List<Update> findUpdatesByToImageVersionID(Model model, String versionId) throws DMException;

  /**
   * Delete an UpdateEntity form DM Inventory.
   * 
   * @param update
   * @throws DMException
   */
  public abstract void delete(Update update) throws DMException;

  public abstract Image newImageInstance(Model model, String externalId, boolean applicableToAllCarriers,
      String description) throws DMException;

  /**
   * Add or update an ImageEntity into DM inventory.
   * 
   * @param update
   * @throws DMException
   */
  public abstract void update(Image image) throws DMException;

  /**
   * Load an ImageEntity form DM inventory by it's ID
   * 
   * @param id
   * @return UpdateEntity
   * @throws DMException
   */
  public abstract Image getImageByID(String id) throws DMException;

  /**
   * Find a ImageEntity for the specified model and version.
   * 
   * @param model
   * @param version
   * @return
   * @throws DMException
   */
  public abstract Image getImageByVersionID(Model model, String version) throws DMException;

  /**
   * Delete an ImageEntity form DM Inventory.
   * 
   * @param update
   * @throws DMException
   */
  public abstract void delete(Image image) throws DMException;

  /**
   * Find all of released updates for the ModelEntity. Sorted
   * by image's versionID desc
   * 
   * @param model
   * @return
   * @throws DMException
   */
  public abstract List<Update> findUpdates(Model model) throws DMException;

  /**
   * Find all of released updates.
   * @return
   * @throws DMException
   */
  public List<Update> findReleasedUpdates() throws DMException;
  
  /**
   * Find all of released updates for the ModelEntity. Sorted
   * by image's versionID desc
   * 
   * All of update's status is "RELEASED"!
   * 
   * @param model
   * @return
   * @throws DMException
   */
  public abstract List<Update> findReleasedUpdates(Model model) throws DMException;

  /**
   * Find all of released updates from the VersionId for the ModelEntity. Sorted
   * by image's versionID desc
   * 
   * All of update's status is "RELEASED"!
   * 
   * @param model
   * @param versionId
   * @return
   * @throws DMException
   */
  public abstract List<Update> findReleasedUpdates(Model model, String versionId) throws DMException;

  /**
   * Find all of availiable updates to upgrade from versionID in specified
   * model.
   * 
   * All of update's status is "RELEASED"!
   * 
   * Sorted by image's versionID asc
   * 
   * @param model
   * @param versionId
   * @return Array of updates.
   * @throws DMException
   */
  public abstract List<Update> findUpdates4Upgrade(Model model, String versionId) throws DMException;

  /**
   * Find all of availiable updates to downgrade from versionID in specified
   * model.
   * 
   * All of update's status is "RELEASED"!
   * 
   * Sorted by image's versionID desc
   * 
   * @param model
   * @param versionId
   * @return Array of updates.
   * @throws DMException
   */
  public abstract List<Update> findUpdates4Downgrade(Model model, String versionId) throws DMException;

  /**
   * Get a Status from DM inventory.
   * 
   * @param releaseStatus
   * @return
   */
  public abstract ImageUpdateStatus getImageUpdateStatus(Long releaseStatus) throws DMException;

}