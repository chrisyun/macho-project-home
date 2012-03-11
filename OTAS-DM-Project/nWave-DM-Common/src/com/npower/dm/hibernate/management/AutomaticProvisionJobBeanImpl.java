/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/AutomaticProvisionJobBeanImpl.java,v 1.5 2007/02/02 05:50:42 zhao Exp $
  * $Revision: 1.5 $
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
package com.npower.dm.hibernate.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.dm.core.AutomaticProvisionJob;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.hibernate.entity.AutoJobNodesDiscover;
import com.npower.dm.hibernate.entity.AutoJobNodesDiscoverId;
import com.npower.dm.hibernate.entity.AutoJobProfileConfig;
import com.npower.dm.hibernate.entity.AutoJobProfileConfigId;
import com.npower.dm.hibernate.entity.AutoProvisionJobEntity;
import com.npower.dm.management.AutomaticProvisionJobBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileAssignmentBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.selector.AutomaticProvisionJobSelector;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/02/02 05:50:42 $
 */
public class AutomaticProvisionJobBeanImpl extends AbstractBean implements AutomaticProvisionJobBean {

  /**
   * 
   */
  public AutomaticProvisionJobBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  public AutomaticProvisionJobBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }
  
  // Private methods -------------------------------------------------------------------------------
  /**
   * @param jobID
   * @param newState
   * @throws DMException
   */
  private void updateJobState(long jobID, String newState) throws DMException {
    try {
        AutomaticProvisionJob job = this.loadJobByID(jobID);
        job.setState(newState);
        Session session = this.getHibernateSession();
        session.saveOrUpdate(job);
        
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  
  
  // Public methods --------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#newJob4Assignment(com.npower.dm.management.AutomaticProvisionJobSelector, com.npower.dm.core.ProfileConfig)
   */
  public AutomaticProvisionJob newJob4Assignment(AutomaticProvisionJobSelector jobSelector, ProfileConfig[] profiles)
      throws DMException {
    if (profiles == null || profiles.length == 0) {
       throw new DMException("Must specified profiles for automatic provision job.");
    }
   
    try {
        Session session = this.getHibernateSession();
        AutoProvisionJobEntity job = new AutoProvisionJobEntity();
        job.setJobSelector(jobSelector);
        job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
        job.setJobType(AutomaticProvisionJob.JOB_TYPE_ASSIGN_PROFILE);
        job.setJobTypeForDisplay(AutomaticProvisionJob.JOB_TYPE_ASSIGN_PROFILE);
        job.setState(AutomaticProvisionJob.JOB_STATE_APPLIED);
        job.setBeginTime(new Date());
        session.saveOrUpdate(job);
       
        for (int i = 0; i < profiles.length; i++) {
            if (profiles[i] == null) {
               continue;
            }
            AutoJobProfileConfigId id = new AutoJobProfileConfigId(job, (long)i);
            AutoJobProfileConfig profileJob = new AutoJobProfileConfig(id, profiles[i]);
            session.saveOrUpdate(profileJob);
            job.getAutoJobProfileConfigs().add(profileJob);
        }
        return job;
    } catch (HibernateException e) {
      throw new DMException(e);
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#newJob4Command(com.npower.dm.management.AutomaticProvisionJobSelector, java.lang.String)
   */
  public AutomaticProvisionJob newJob4Command(AutomaticProvisionJobSelector jobSelector, String scripts)
      throws DMException {
    if (StringUtils.isEmpty(scripts)) {
       throw new DMException("Must specified scripts for automatic provision job.");
    }
   
    try {
        Session session = this.getHibernateSession();
        AutoProvisionJobEntity job = new AutoProvisionJobEntity();

        job.setJobSelector(jobSelector);
        job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
        job.setJobType(AutomaticProvisionJob.JOB_TYPE_SCRIPT);
        job.setJobTypeForDisplay(AutomaticProvisionJob.JOB_TYPE_SCRIPT);
        job.setState(AutomaticProvisionJob.JOB_STATE_APPLIED);
        job.setBeginTime(new Date());
        job.setScript(scripts);
        session.saveOrUpdate(job);
       
        return job;
    } catch (HibernateException e) {
      throw new DMException(e);
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#newJob4Discovery(com.npower.dm.management.AutomaticProvisionJobSelector, java.lang.String[])
   */
  public AutomaticProvisionJob newJob4Discovery(AutomaticProvisionJobSelector jobSelector, String[] nodePaths)
      throws DMException {
    if (nodePaths == null || nodePaths.length == 0) {
       throw new DMException("Must specified node path for automatic provision job.");
    }
    
    try {
        Session session = this.getHibernateSession();
        AutoProvisionJobEntity job = new AutoProvisionJobEntity();
        job.setJobSelector(jobSelector);
        job.setType(AutomaticProvisionJob.TYPE_AUTO_REG);
        job.setJobType(AutomaticProvisionJob.JOB_TYPE_DISCOVERY);
        job.setJobTypeForDisplay(AutomaticProvisionJob.JOB_TYPE_DISCOVERY);
        job.setState(AutomaticProvisionJob.JOB_STATE_APPLIED);
        job.setBeginTime(new Date());
        session.saveOrUpdate(job);
        
        for (int i = 0; i < nodePaths.length; i++) {
            if (StringUtils.isEmpty(nodePaths[i])) {
               continue;
            }
            AutoJobNodesDiscoverId id = new AutoJobNodesDiscoverId(job, (long)i);
            AutoJobNodesDiscover discovery = new AutoJobNodesDiscover(id, nodePaths[i]);
            session.saveOrUpdate(discovery);
            job.getAutoJobNodesDiscovers().add(discovery);
        }
        return job;
    } catch (HibernateException e) {
      throw new DMException(e);
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#update(com.npower.dm.core.AutomaticProvisionJob)
   */
  public void update(AutomaticProvisionJob job) throws DMException {
    try {
        Session session = this.getHibernateSession();
        session.saveOrUpdate(job);
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#loadJobByID(java.lang.String)
   */
  public AutomaticProvisionJob loadJobByID(String jobID) throws DMException {
    assert jobID != null: "Invalidate jobID: " + jobID;
    try {
        return this.loadJobByID((new Long(jobID)).longValue());
    } catch (Exception ex) {
      throw new DMException("Invalidate jobID: " + jobID);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#loadJobByID(long)
   */
  public AutomaticProvisionJob loadJobByID(long jobID) throws DMException {
    assert jobID > 0: "Invlidate jobID: " + jobID;
    
    try {
        Session session = this.getHibernateSession();
        AutomaticProvisionJob job = (AutomaticProvisionJob)session.get(AutoProvisionJobEntity.class, new Long(jobID));
        return job;
    } catch (HibernateException e) {
      throw new DMException("Invalidate jobID: " + jobID, e);
    }
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#getSelectedJobs(com.npower.dm.core.Device)
   */
  public List<AutomaticProvisionJob> getSelectedJobs(Device device) throws DMException {
    List<AutomaticProvisionJob> result = new ArrayList<AutomaticProvisionJob>();
    try {
        Session session = this.getHibernateSession();
        Criteria mainCriteria = session.createCriteria(AutoProvisionJobEntity.class);
        mainCriteria.add(Expression.eq("state", AutomaticProvisionJob.JOB_STATE_APPLIED));
        mainCriteria.addOrder(Order.asc("beginTime"));
        mainCriteria.addOrder(Order.asc("ID"));
        
        List<AutomaticProvisionJob> jobs = (List<AutomaticProvisionJob>)mainCriteria.list();
        for (AutomaticProvisionJob job: jobs) {
            AutomaticProvisionJobSelector selector = job.getJobSelector();
            if (selector == null) {
               result.add(job);
            } else if (selector.matched(job, device)) {
              result.add(job);
            }
        }
        return result;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#findAllJobs()
   */
  public List<AutomaticProvisionJob> findAllJobs() throws DMException {
    try {
        Session session = this.getHibernateSession();
        Criteria mainCriteria = session.createCriteria(AutoProvisionJobEntity.class);
        mainCriteria.addOrder(Order.asc("beginTime"));
        mainCriteria.addOrder(Order.asc("ID"));
        
        List<AutomaticProvisionJob> jobs = (List<AutomaticProvisionJob>)mainCriteria.list();
        return jobs;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#cancel(java.lang.String)
   */
  public void cancel(String jobID) throws DMException {
    this.updateJobState(new Long(jobID), AutomaticProvisionJob.JOB_STATE_CANCELLED);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#cancel(long)
   */
  public void cancel(long jobID) throws DMException {
    this.updateJobState(jobID, AutomaticProvisionJob.JOB_STATE_CANCELLED);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#delete(java.lang.String)
   */
  public void delete(String jobID) throws DMException {
    this.delete(new Long(jobID));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#delete(long)
   */
  public void delete(long jobID) throws DMException {
    try {
        Session session = this.getHibernateSession();
        AutomaticProvisionJob job = this.loadJobByID(jobID);
        if (job != null) {
           session.delete(job);
        }
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#disable(java.lang.String)
   */
  public void disable(String jobID) throws DMException {
    this.updateJobState(new Long(jobID), AutomaticProvisionJob.JOB_STATE_DISABLE);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#disable(long)
   */
  public void disable(long jobID) throws DMException {
    this.updateJobState(jobID, AutomaticProvisionJob.JOB_STATE_DISABLE);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#enable(java.lang.String)
   */
  public void enable(String jobID) throws DMException {
    this.updateJobState(new Long(jobID), AutomaticProvisionJob.JOB_STATE_APPLIED);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#enable(long)
   */
  public void enable(long jobID) throws DMException {
    this.updateJobState(jobID, AutomaticProvisionJob.JOB_STATE_APPLIED);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobBean#submitSelectedJobs(com.npower.dm.core.Device)
   */
  public List<ProvisionJob> submitSelectedJobs(Device device) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    ProvisionJobBean jobBean = factory.createProvisionJobBean();

    List<AutomaticProvisionJob> autoJobs = this.getSelectedJobs(device);
    List<ProvisionJob> result = new ArrayList<ProvisionJob>();
    for (AutomaticProvisionJob autoJob: autoJobs) {
        if (AutomaticProvisionJob.JOB_TYPE_DISCOVERY.equals(autoJob.getJobType())) {
           // Create a device group.
           DeviceBean deviceBean = factory.createDeviceBean();
           DeviceGroup group = deviceBean.newDeviceGroup();
          
           deviceBean.add(group, device);
           deviceBean.update(group);
          
           // Create Job
           String[] nodeURIs = autoJob.getDiscoveryNodes().toArray(new String[]{});
           ProvisionJob job = jobBean.newJob4Discovery(group, nodeURIs);
           job.setName(autoJob.getName());
           job.setDescription(autoJob.getDescription());
           job.setScheduledTime(new Date());
           jobBean.update(job);
           result.add(job);
        } else if (AutomaticProvisionJob.JOB_TYPE_SCRIPT.equals(autoJob.getJobType())) {
          // Create a device group.
          DeviceBean deviceBean = factory.createDeviceBean();
          DeviceGroup group = deviceBean.newDeviceGroup();
         
          deviceBean.add(group, device);
          deviceBean.update(group);
         
          // Create Job
          ProvisionJob job = jobBean.newJob4Command(group, autoJob.getScript());
          job.setName(autoJob.getName());
          job.setDescription(autoJob.getDescription());
          job.setScheduledTime(new Date());
          jobBean.update(job);
          result.add(job);
        } else if (AutomaticProvisionJob.JOB_TYPE_ASSIGN_PROFILE.equals(autoJob.getJobType())) {
          ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
          for (ProfileConfig profile: autoJob.getProfileConfigs()) {
              ProfileAssignment assignment = assignmentBean.newProfileAssignmentInstance(profile, device);
              List<ProvisionJob> jobs = jobBean.newJobs4Assignment(assignment, 
                                                                   autoJob.getName(), 
                                                                   autoJob.getDescription(), 
                                                                   new Date());
              result.addAll(jobs);
          }
        }
    }
    return result;
  }

}
