/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/AutomaticProvisionJob.java,v 1.4 2007/02/02 03:30:24 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2007/02/02 03:30:24 $
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
package com.npower.dm.core;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.npower.dm.management.selector.AutomaticProvisionJobSelector;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/02/02 03:30:24 $
 */
public interface AutomaticProvisionJob {

  /**
   * Constance: JOB_STATE
   */
  public final static String JOB_STATE_APPLIED      = "Applied";
  public final static String JOB_STATE_DISABLE      = "Disable";
  public final static String JOB_STATE_CANCELLED    = "Cancelled";

  /**
   * Constance: TYPE
   */
  public final static String TYPE_AUTO_REG    = "AutoRegistration";
  
  /**
   * Constance: JOB_TYPE
   */
  public final static String JOB_TYPE_DISCOVERY             = "Discovery";
  public final static String JOB_TYPE_ASSIGN_PROFILE        = "Assign";
  public final static String JOB_TYPE_DELETE_PROFILE        = "DeleteProfile";
  public final static String JOB_TYPE_SCRIPT                = "Script";
  public final static String JOB_TYPE_WORKFLOW              = "Workflow";

  /**
   * Job ID
   * @return
   */
  public abstract long getID();

  /**
   * Set Job ID
   * @param ID
   */
  public abstract void setID(long ID);

  /**
   * Return carrier which owned this job.
   * @return
   */
  public abstract Carrier getCarrier();

  /**
   * Set owner
   * @param carrier
   */
  public abstract void setCarrier(Carrier carrier);

  /**
   * Return type of job.
   * Defined by Constances: TYPE_xxx
   * @return
   */
  public abstract String getType();

  /**
   * Set type of job
   * Defined by Constance TYPE_xxx
   * @param type
   */
  public abstract void setType(String type);

  /**
   * Return job type.
   * Defined bu contances: JOB_TYPE_xxx
   * @return
   */
  public abstract String getJobType();

  /**
   * Set job type
   * Defined bu contances: JOB_TYPE_xxx
   * @param jobType
   */
  public abstract void setJobType(String jobType);

  /**
   * Return display label for job type.
   * @return
   */
  public abstract String getJobTypeForDisplay();

  /**
   * Set display label for job type.
   * @param jobTypeForDisplay
   */
  public abstract void setJobTypeForDisplay(String jobTypeForDisplay);

  /**
   * Return state of job.
   * Defined bu contances: JOB_STATE_xxx
   * @return
   */
  public abstract String getState();

  /**
   * Set state of job
   * Defined bu contances: JOB_STATE_xxx
   * @param state
   */
  public abstract void setState(String state);

  /**
   * Return name of job
   * @return
   */
  public abstract String getName();

  /**
   * Set name of job
   * @param name
   */
  public abstract void setName(String name);

  /**
   * Return memo of job
   * @return
   */
  public abstract String getDescription();

  /**
   * set memo of job
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * Return begin time
   * @return
   */
  public abstract Date getBeginTime();

  /**
   * Set begin time
   * @param beginTime
   */
  public abstract void setBeginTime(Date beginTime);

  /**
   * Return end time
   * @return
   */
  public abstract Date getEndTime();

  /**
   * Set end time
   * @param endTime
   */
  public abstract void setEndTime(Date endTime);

  /**
   * @return
   */
  public abstract String getCriteria();

  /**
   * @param criteria
   */
  public abstract void setCriteria(String criteria);

  /**
   * @return
   */
  public abstract String getScript();

  /**
   * @param script
   */
  public abstract void setScript(String script);
  
  /**
   * If the job type is Discovery, return node paths for discoverying.
   * @return
   */
  public List<String> getDiscoveryNodes();
  
  /**
   * Set the node paths for discoverying.
   * @param nodes
   */
  //public void setDiscoveryNodes(List<String> nodes);
  
  /**
   * If the job type is "Assignment", return profiles for assignment.
   * @return
   */
  public List<ProfileConfig> getProfileConfigs();
  
  /**
   * Set the profiles for assignment.
   * @param configs
   */
  //public void setProfileConfigs(List<ProfileConfig> configs);
  
  /**
   * Selector for this job.
   * @return
   */
  public AutomaticProvisionJobSelector getJobSelector() throws IOException;
  
  /**
   * Set selector for this job.
   * @param selector
   */
  public void setJobSelector(AutomaticProvisionJobSelector selector) throws IOException;

  /**
   * @return
   */
  public abstract long getChangeVersion();

  /**
   * @param changeVersion
   */
  public abstract void setChangeVersion(long changeVersion);

  /**
   * @return
   */
  //public abstract long getRunning();

  /**
   * @param running
   */
  //public abstract void setRunning(long running);

  /**
   * @return
   */
  //public abstract Set getAutoJobStates();

  /**
   * @param autoJobStates
   */
  //public abstract void setAutoJobStates(Set autoJobStates);

}