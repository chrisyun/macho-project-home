/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/ProfileAssignmentBean.java,v 1.5 2006/06/22 03:20:01 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2006/06/22 03:20:01 $
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
import com.npower.dm.core.Device;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;

/**
 * All operations against ProfileAssignment will be conducted through this ProfileAssignmentBean.
 * @author Zhao DongLu
 * 
 */
public interface ProfileAssignmentBean extends BaseBean {

  // public methods
  // ******************************************************************************************

  public abstract ProfileAssignment newProfileAssignmentInstance(ProfileConfig profileConfig, Device device)
      throws DMException;

  /**
   * Add a ProfileAssignmentEntity into DM inventory.
   * 
   * @param assignment
   *          ProfileAssignmentEntity
   */
  public abstract void update(ProfileAssignment assignment) throws DMException;

  /**
   * Get and find a ProfileAssignmentEntity by ID
   * 
   * @param id
   *          String
   */
  public abstract ProfileAssignment getProfileAssignmentByID(String id) throws DMException;


  /**
   * Fina and list all of the ProfileAssignmentEntity by the HibernateSQL. For
   * Exmaple: "from ProfileAssignmentEntity where ...."
   * 
   * @param whereClause
   *          String
   */
  public abstract List<ProfileAssignment> findProfileAssignments(String whereClause) throws DMException;

  /**
   * Find all of ProfileAssignmentEntity by the status for the specified device ,
   * and status.
   * 
   * If status is Null, will return all of status.
   * 
   * @param device
   *          DeviceEntity
   * @param status
   *          Status of assignment
   * @return Array of ProfileAssignmentEntity
   * @throws DMException
   */
  public abstract List<ProfileAssignment> findProfileAssignmentsByStatus(Device device, String status) throws DMException;

  /**
   * Delete a ProfileConfigEntity from the DM inventory.
   * 
   * @param assignment
   *          ProfileAssignmentEntity
   */
  public abstract void deleteProfileAssignment(ProfileAssignment assignment) throws DMException;

  /**
   * Add or UpdateEntity the value of ProfileAttributeEntity ownen by the
   * ProfileAssignmentEntity. If not found ProfileAttributeValueEntity by the
   * attributename, will create a single value, binary mode value object. If
   * found it, will override the attribute to single value, binary mode value's
   * object. all of multi-value item will be deleted.
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
  public void setAttributeValue(ProfileAssignment assignment, String name, InputStream value) throws DMException, IOException;

  /**
   * Add or UpdateEntity the value of ProfileAttributeEntity ownen by the
   * ProfileAssignmentEntity. If not found ProfileAttributeValueEntity by the
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
  public void setAttributeValue(ProfileAssignment assignment, String name, String value) throws DMException;

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
  public void setAttributeValue(ProfileAssignment assignment, String name, String value[]) throws DMException;

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
  public void setAttributeValue(ProfileAssignment assignment, String name, InputStream value[]) throws DMException, IOException;

  /**
   * Set attribute values of the ProfileAssignmentEntity, and the order of
   * attributes will based on the index of array.
   * 
   * TODO compose testcases for this method.
   * 
   * @param values
   * @throws DMException
   */
  public void setAttributeValues(ProfileAssignment assignment, ProfileAttributeValue[] values) throws DMException;
}