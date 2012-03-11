/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/AutomaticProvisionJobBean.java,v 1.4 2007/02/02 05:50:42 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2007/02/02 05:50:42 $
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
package com.npower.dm.management;

import java.util.List;

import com.npower.dm.core.AutomaticProvisionJob;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.management.selector.AutomaticProvisionJobSelector;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/02/02 05:50:42 $
 */
public interface AutomaticProvisionJobBean extends BaseBean {

  /**
   * Create a automatic job for discovery.<br>
   * For example:<br>
   *   Root Node will be "."<br>
   *   DevDetail will be "/DevDetail" or "DevDetail"<br>
   * 
   * @param selector
   *        JobSelector to detect which devices will execute this job.
    * @param nodePath
   *        <code>String[]</code> Array of Node Path for discovery.
   * @return
   * @throws DMException
   */
  public abstract AutomaticProvisionJob newJob4Discovery(AutomaticProvisionJobSelector selector, String[] nodePath) throws DMException;

  /**
   * Create a automaic job for command script.
   * @param selector
   *        JobSelector to detect which devices will execute this job.
   * @param scripts 
   *        Comamnd script.
   *  
   * @return
   * @throws DMException
   */
  public abstract AutomaticProvisionJob newJob4Command(AutomaticProvisionJobSelector selector, String scripts) throws DMException;
  
  /**
   * Create a automaic job to assign a profile.
   * @param selector
   *        JobSelector to detect which devices will execute this job.
   * @param profiles
   *        ProfileConfig[]
   * @return
   * @throws DMException
   */
  public abstract AutomaticProvisionJob newJob4Assignment(AutomaticProvisionJobSelector selector, ProfileConfig[] profiles) throws DMException;
  
  //public abstract List<AutomaticProvisionJob> newJobs4Assignment(ProfileAssignment assignment, String jobName, String jobDescription, Date scheduledTime) throws DMException;
  
  /**
   * Save job into DM inventory.
   * @param job
   * @throws DMException
   */
  public abstract void update(AutomaticProvisionJob job) throws DMException;
  
  /**
   * Load a job by it's jobID from DM inventory.
   * @param jobID   String job's ID
   * @return
   * @throws DMException
   */
  public abstract AutomaticProvisionJob loadJobByID(String jobID) throws DMException;
  
  /**
   * Load a job by it's jobID from DM inventory.
   * @param id   long job's ID
   * @return
   * @throws DMException
   */
  public abstract AutomaticProvisionJob loadJobByID(long id) throws DMException;

  /**
   * Return all of jobs which be selected for the device.
   * @param device
   *        Device
   * @return
   * @throws DMException
   */
  public List<AutomaticProvisionJob> getSelectedJobs(Device device) throws DMException;
  
  /**
   * Submit all of selected automatic jobs into device job queue.
   * @param device
   *        Device
   * @return
   * @throws DMException
   */
  public List<ProvisionJob> submitSelectedJobs(Device device) throws DMException;
  
  /**
   * Find all of automatic jobs.
   * @return
   * @throws DMException
   */
  public List<AutomaticProvisionJob> findAllJobs() throws DMException;
  
  /**
   * Cancel a job.
   * @param jobID
   * @throws DMException
   */
  public abstract void cancel(String jobID) throws DMException;
  
  /**
   * Cancel a job.
   * @param jobID
   * @throws DMException
   */
  public abstract void cancel(long jobID) throws DMException;
  
  /**
   * Disable a job.
   * @param jobID
   * @throws DMException
   */
  public abstract void disable(String jobID) throws DMException;

  /**
   * Disable a job.
   * @param jobID
   * @throws DMException
   */
  public abstract void disable(long jobID) throws DMException;
  
  /**
   * Enable a job
   * @param jobID
   * @throws DMException
   */
  public void enable(String jobID) throws DMException;

  /**
   * Enable a job
   * @param jobID
   * @throws DMException
   */
  public void enable(long jobID) throws DMException;
  /**
   * Delete the job from DM inventory.
   * 
   * @param job
   * @throws DMException
   */
  public abstract void delete(String jobID) throws DMException;
  
  /**
   * Delete the job from DM inventory.
   * 
   * @param job
   * @throws DMException
   */
  public abstract void delete(long jobID) throws DMException;
  
  
}
