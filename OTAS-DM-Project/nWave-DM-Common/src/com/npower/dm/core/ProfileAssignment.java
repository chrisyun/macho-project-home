/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ProfileAssignment.java,v 1.5 2009/01/21 07:53:10 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2009/01/21 07:53:10 $
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
package com.npower.dm.core;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.npower.dm.hibernate.entity.ProfileParameterEntity;

/**
 * <p>Represent a assignment of ProfileConfig</p>
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2009/01/21 07:53:10 $
 * 
 */
public interface ProfileAssignment {

  /**
   * Assignment Status: pending
   */
  public static final String STATUS_PENDING = "pending";

  /**
   * Assignment Status: done
   */
  public static final String STATUS_DONE = "done";

  /**
   * Assignment Status: done and deletion
   */
  public static final String STATUS_DONE_DELETION = "deletion";

  /**
   * Assignment Status: pending and deletion
   */
  public static final String STATUS_PENDING_DELETION = "pending_deletion";
  
  /**
   * Assignment Status: 表示当前的配置信息由采集后经分析器识别的当前设备配置
   */
  public static final String STATUS_RECOGNIZED = "recognized";

  /**
   * Return the ID.
   * @return
   */
  public abstract long getID();

  /**
   * Return the ProfileConfig which assigned by this Assignment.
   * @return
   */
  public abstract ProfileConfig getProfileConfig();

  /**
   * Set an ProfileConfig for this Assignment.
   * @param nwDmProfileConfig
   */
  public abstract void setProfileConfig(ProfileConfig nwDmProfileConfig);

  /**
   * Return the device which will be assigned by this ProfileAssignment.
   * @return
   */
  public abstract Device getDevice();

  /**
   * Boundle a device with this ProfileAssignment.
   * @param nwDmDevice
   */
  public abstract void setDevice(Device nwDmDevice);

  /**
   * Return Root Node path in the DDF Tree for this Assignment.
   * @return
   */
  public abstract String getProfileRootNodePath();

  /**
   * Set the Root Node path for this Assignment.
   * @param profileRootNodePath
   */
  public abstract void setProfileRootNodePath(String profileRootNodePath);

  /**
   * Return the status of this Assignment.The status be defined in this interfact, please see list of constances of this interface.
   * @return
   */
  public abstract String getStatus();

  /**
   * Set the status
   * @param status
   */
  public abstract void setStatus(String status);

  /**
   * Return time of lastly sent to device.
   * @return
   */
  public abstract Date getLastSentToDevice();

  /**
   * Set the time of lastly sen to device.
   * @param lastSentToDevice
   */
  public abstract void setLastSentToDevice(Date lastSentToDevice);

  /**
   * Return the index of assignment.
   * @return
   */
  public abstract long getAssignmentIndex();

  /**
   * Set the index of the assignment.
   * @param assignmentIndex
   */
  public abstract void setAssignmentIndex(long assignmentIndex);

  /**
   * Find a ProfileAttributeValueEntity by the name from the
   * ProfileAssignmentEntity.
   * 
   * If nothing found, will return null.
   * 
   * @param attributeName
   * @return
   * @throws DMException
   */
  public ProfileAttributeValue getProfileAttributeValue(String attributeName) throws DMException;


  /**
   * Return all of ProfileAttributeValues owened by the ProfileAssignmentValue.
   * 
   * @return ProfileAttributeValueEntity[]
   */
  public ProfileAttributeValue[] getAttributeValues();
  
  /**
   * Return all of values of attributes. This method will return the name-value pair inclued ProfileConfig and ProfileTemplate.
   * This method only used for JSP Display.
   * @return
   */
  public List<NameValuePair> getAllOfNameValuePairs() throws DMException;
  
  /**
   * 
   * @return
   */
  public abstract Set<ProfileParameterEntity> getProfileParameters();

  /**
   * 
   * @return
   */
  //public abstract Set getProfileAssignValues();

  /**
   * 
   * @return
   */
  //public abstract Set getJobAssignmentses();

  /**
   * Getter of the LastUpdatedBy
   */
  public abstract String getLastUpdatedBy();

  /**
   * Setter of the LastUpdatedBy
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Getter of the LastUpdatedTime
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * Getter of the ChangeVersion
   * @return
   */
  public abstract long getChangeVersion();

  

}