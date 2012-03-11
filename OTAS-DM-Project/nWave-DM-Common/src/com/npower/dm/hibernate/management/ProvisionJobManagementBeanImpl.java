/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/ProvisionJobManagementBeanImpl.java,v 1.55 2008/12/09 06:31:45 zhao Exp $
  * $Revision: 1.55 $
  * $Date: 2008/12/09 06:31:45 $
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
package com.npower.dm.hibernate.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.server.store.NotFoundException;

import com.npower.dm.command.Compiler4CommandScript;
import com.npower.dm.core.APLinkValueNotFoundException;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Software;
import com.npower.dm.core.Update;
import com.npower.dm.hibernate.entity.DeviceEntity;
import com.npower.dm.hibernate.entity.DeviceProvisionRequestEntity;
import com.npower.dm.hibernate.entity.Element4Provision;
import com.npower.dm.hibernate.entity.JobAssignments;
import com.npower.dm.hibernate.entity.JobAssignmentsID;
import com.npower.dm.hibernate.entity.JobState;
import com.npower.dm.hibernate.entity.Node4DiscoveryJob;
import com.npower.dm.hibernate.entity.ProfileAssignmentEntity;
import com.npower.dm.hibernate.entity.ProvisionRequest;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileAssignmentBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SearchBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.55 $ $Date: 2008/12/09 06:31:45 $
 */
public class ProvisionJobManagementBeanImpl extends AbstractBean implements ProvisionJobBean {

  /**
   * 
   */
  protected ProvisionJobManagementBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  ProvisionJobManagementBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }
  
  // Private methods ***********************************************************************************************
  private ProvisionRequest createDefaultProvisionJob() {
    ProvisionRequest request = new ProvisionRequest();
    /*
    request.setJobType(ProvisionJob.JOB_TYPE_DISCOVERY);
    request.setJobTypeForDisplay(ProvisionJob.JOB_TYPE_DISCOVERY);
    request.setScheduledTime(new Date());
    request.setState(ProvisionJob.JOB_STATE_APPLIED);
    request.setTargetType(ProvisionJob.JOB_ELEMENT_TYPE_SINGLE);
    
    request.setNotificationEndTime(24.0);
    request.setNotificationStartTime(0.0);
    request.setRate(0);
    request.setRunning(true);

    Locale locale = Locale.getDefault();
    request.setAppLanguage(locale.toString());
    
    request.setAskBeforeApply(false);
    request.setAskBeforeDown(false);
    request.setAskCount(0);
    request.setAskInterval(0);
    request.setAskPermissionOnTrigger(false);
    */
    //request.setCommandPackageId();
    //request.setCriteria();

    //request.setDescription();
    //request.setProvisionPhoneNumbers();
    
    //request.setScript();
    //request.setTargetImage();
    //request.setTargetImageDescription();
    //request.setUiMode();
    //request.setUpdateWorkflow();
    //request.setWorkflowName();
    return request;    
  }

  /**
   * Copy properties from ProvisionRequest to ProvisionJobStatus.
   * 
   * @param request
   * @param dpRequest
   */
  private void copyTo(ProvisionRequest request, ProvisionJobStatus dpRequest) {
    
    dpRequest.setAskBeforeApply(request.getAskBeforeApply());
    dpRequest.setAskBeforeDown(request.getAskBeforeDown());
    dpRequest.setAskCount(request.getAskCount());
    dpRequest.setAskInterval(request.getAskInterval());
    dpRequest.setAskPermissionOnTrigger(request.getAskPermissionOnTrigger());
    //dpRequest.setCurrentJobAdapterId();
    //dpRequest.setInstallationState();
    //dpRequest.setInstallingImage();
    //dpRequest.setJobState();
    dpRequest.setNotificationEndTime(request.getNotificationEndTime());
    dpRequest.setNotificationStartTime(request.getNotificationStartTime());
    //dpRequest.setOldCurrentImage();
    //dpRequest.setPathIndex();
    //dpRequest.setPendingDeviceId();
    //dpRequest.setPendingDeviceJobIndex();
    dpRequest.setRate(request.getPriority());
    dpRequest.setReadyToNotify(request.getRunning());
    dpRequest.setScheduledTime(request.getScheduledTime());
    dpRequest.setState(ProvisionJobStatus.DEVICE_JOB_STATE_READY);
    //dpRequest.setToImage();
    dpRequest.setUiMode(request.getUiMode());
    //dpRequest.setWorkflowEntryId();
    
  }
  
  /**
   * @param jobID
   * @param newState
   * @throws DMException
   */
  private void updateJobState(long jobID, String newState) throws DMException {
    try {
        ProvisionJob job = this.loadJobByID(jobID);
        job.setState(newState);
        Session session = this.getHibernateSession();
        session.saveOrUpdate(job);
        
        Criteria mainCriteria = session.createCriteria(DeviceProvisionRequestEntity.class);
        Criteria subCriteria = mainCriteria.createCriteria("provisionElement");
        subCriteria.add(Expression.eq("provisionRequest", job));
        List<DeviceProvisionRequestEntity> deviceStatusList = subCriteria.list();
        for (int i = 0; i < deviceStatusList.size(); i++) {
            DeviceProvisionRequestEntity deviceStatus = (DeviceProvisionRequestEntity)deviceStatusList.get(i);
            String state = deviceStatus.getState();
            // Where the device's status is not done, broadcast the provisionjob's status to device' status.
            if (!state.equalsIgnoreCase(ProvisionJobStatus.DEVICE_JOB_STATE_DONE)) {
               if (newState.equalsIgnoreCase(ProvisionJob.JOB_STATE_CANCELLED)) {
                  deviceStatus.setState(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED);
               } else if (newState.equalsIgnoreCase(ProvisionJob.JOB_STATE_DISABLE)) {
                 deviceStatus.setState(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED);
               } else if (newState.equalsIgnoreCase(ProvisionJob.JOB_STATE_APPLIED)) {
                 deviceStatus.setState(ProvisionJobStatus.DEVICE_JOB_STATE_READY);
               }
            }
            //session.saveOrUpdate(deviceStatus);
            session.merge(deviceStatus);
        }
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /**
   * 从ProfileAssignment中提取参数attributeName的值. 提取逻辑如下：
   * 1. 从ProfileAssignment中提取值
   * 2. 如果无法提取到, 则从ProfileAssignment对应的ProfileConfig中提取
   * @param assignment
   * @param attributeName
   * @return
   * @throws DMException
   */
  private ProfileAttributeValue getAttributeValue(ProfileAssignment assignment, String attributeName) throws DMException {
    ProfileAttributeValue attriValue = null;
    attriValue = assignment.getProfileAttributeValue(attributeName);
    if (attriValue == null) {
       // Get value from profile config
       ProfileConfig config = assignment.getProfileConfig();
       attriValue = config.getProfileAttributeValue(attributeName);
    }
    return attriValue;
  }

  /**
   * Find Linked Profile Assignment by linkedCategory name.
   * 例如：对于一个MMS Profile Template来说, 它将引用一个Proxy Template, 
   * 而Proxy Template通常还将引用一个NAP Template. 通过本方法, 可以查询处这个MMS Profile
   * 所引用的Proxy和NAP Profile Assignment.
   * <pre>
   * proxyAssignment = this.getLinkedProfileAssignment(mmsAssgingment, ProfileCategory.PROXY_CATEGORY_NAME);
   * napAssignment = this.getLinkedProfileAssignment(mmsAssgingment, ProfileCategory.NAP_CATEGORY_NAME);
   * </pre>
   * 
   * @param assignment
   * @param linkedCategory
   * @return
   * @throws DMException
   */
  private ProfileAssignment getLinkedProfileAssignment(ProfileAssignment assignment, String linkedCategory) throws DMException {
    ProfileConfig config = assignment.getProfileConfig();
    Device device = assignment.getDevice();
    
    ProfileAssignment linkedAssignment = null;
    ProfileConfig linkedProfile = null;
    if (StringUtils.isEmpty(linkedCategory)) {
       if (config.getProfileTemplate().getNeedsNap()) {
          linkedProfile = config.getNAPProfile();
       } else if (config.getProfileTemplate().getNeedsProxy()) {
         linkedProfile = config.getProxyProfile();
       }
    } else {
      if (linkedCategory.equalsIgnoreCase(ProfileCategory.PROXY_CATEGORY_NAME) && config.getProfileTemplate().getNeedsProxy()) {
         // Case#1 :需要查找的LinkedProfileCategory为Proxy, 且当前的Profile直接引用一个Proxy Profile
         linkedProfile = config.getProxyProfile();
      } else if (linkedCategory.equalsIgnoreCase(ProfileCategory.NAP_CATEGORY_NAME)) {
        if (config.getProfileTemplate().getNeedsNap()) {
           // Case#2 :需要查找的LinkedProfileCategory为NAP, 且当前的Profile直接引用一个NAP Profile
           linkedProfile = config.getNAPProfile();
        } else {
          // Case#3 :需要查找的LinkedProfileCategory为NAP, 但当前的Profile不直接引用一个NAP Profile, 可能是间接引用
          if (config.getProfileTemplate().getNeedsProxy()) {
             ProfileConfig linkedProxyProfile = config.getProxyProfile();
             if (linkedProxyProfile != null && linkedProxyProfile.getProfileTemplate().getNeedsNap()) {
                linkedProfile = linkedProxyProfile.getNAPProfile();
             }
          }
        }
      }
    }
    
    // Retrieve Linked ProfileAssigment
    if (linkedProfile != null) {
       // Found linked assignment
       // 目前算法上存在一些不足：
       // 1. 数据库中并没有唯一的条件帮助查找LinkedProfile的Assignment
       // 2. 因此当前采用查找LinkedProfile最后一次发送成功的Assignment来确定当前Linked Profile Assignment.
       ManagementBeanFactory factory = this.getManagementBeanFactory();
       SearchBean searchBean = factory.createSearchBean();
       Criteria mainCrt = searchBean.newCriteriaInstance(ProfileAssignmentEntity.class);
       Criteria deviceStateCrt = mainCrt.createCriteria("jobAssignmentses").createCriteria("jobState").createCriteria("deviceProvisionRequests");
       deviceStateCrt.add(Expression.eq("device", device));
       deviceStateCrt.add(Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_DONE));
       mainCrt.add(Expression.eq("profileConfig", linkedProfile));
       mainCrt.add(Expression.eq("device", device));
       // 按照时间降序排列
       mainCrt.addOrder(Order.desc("lastSentToDevice"));
       List<ProfileAssignment> linkedAssignments = mainCrt.list();
       if (linkedAssignments.size() > 0) {
          linkedAssignment = linkedAssignments.get(0);
       }
    }
    return linkedAssignment;
  }

  /**
   * @param session
   * @param group
   * @param software
   * @return
   * @throws DMException
   */
  private ProvisionRequest netSoftwareInstallJob(Session session, DeviceGroup group, Software software)
      throws DMException {
    // Create a ProvisionRequest
    ProvisionRequest request = createDefaultProvisionJob();
    request.setJobMode(ProvisionJob.JOB_MODE_DM);
    request.setJobType(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL);
    // Set target image version.
    request.setTargetSoftware(software);
    if (software != null) {
       // Target image description id target version ID.
       request.setTargetImageDescription(software.getExternalId());
    }

    request.setScheduledTime(new Date());
    request.setState(ProvisionJob.JOB_STATE_APPLIED);
    request.setTargetType(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE);
    request.setLastUpdatedTime(new Date());
    session.saveOrUpdate(request);
    
    // Create a Element4Provision
    Element4Provision element = new Element4Provision(group, request);
    element.setProvisionRequest(request);
    //elements.setState();
    session.saveOrUpdate(element);
    request.getProvisionElements().add(element);
    
    // Create device_prov_req
    for (Device dev: group.getDevices()) {
        ProvisionJobStatus dpRequest = new DeviceProvisionRequestEntity(element, dev);
        dpRequest.setTargetSoftware(software);
        
        copyTo(request, dpRequest);
        session.saveOrUpdate(dpRequest);
        
        // Link to Element3Provision
        element.getDeviceProvReqs().add(dpRequest);
        
        // Link to Device
        if (dev instanceof DeviceEntity) {
           ((DeviceEntity)dev).getProvisionJobStatus().add(dpRequest);
        }
    }
    return request;
  }

  // Public methods ************************************************************************************************
  
  // Implements ProvisionJobBean ***********************************************************************************
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4Discovery(com.npower.dm.core.DeviceGroup, java.lang.String[])
   */
  public ProvisionJob newJob4Discovery(DeviceGroup group, String[] nodePath) throws DMException {
    assert group != null && group.getID() > 0: "Invlidate group!";
    assert nodePath != null && nodePath.length > 0: "Invalidate Node Path " + nodePath;

    try {
        Session session = this.getHibernateSession();
        // Create a ProvisionRequest
        ProvisionRequest request = createDefaultProvisionJob();
        request.setJobMode(ProvisionJob.JOB_MODE_DM);
        request.setJobType(ProvisionJob.JOB_TYPE_DISCOVERY);
        request.setScheduledTime(new Date());
        request.setState(ProvisionJob.JOB_STATE_APPLIED);
        request.setTargetType(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE);
        request.setLastUpdatedTime(new Date());

        session.saveOrUpdate(request);
        
        // Create a Element4Provision
        Element4Provision element = new Element4Provision(group, request);
        //elements.setSourceImage();
        //elements.setState();
        session.saveOrUpdate(element);
        
        Set<Device> devices = group.getDevices();
        for (Iterator<Device> i = devices.iterator(); i.hasNext(); ) {
            Device device = (Device)i.next();
            ProvisionJobStatus dpRequest = new DeviceProvisionRequestEntity(element, device);
            
            copyTo(request, dpRequest);
            session.saveOrUpdate(dpRequest);
            
            // Link to Element3Provision
            element.getDeviceProvReqs().add(dpRequest);
            
            // Link to Device
            if (device instanceof DeviceEntity) {
               ((DeviceEntity)device).getProvisionJobStatus().add(dpRequest);
            }
        }
        
        // Create a Node4DiscoveryJob
        for (int i = 0; i < nodePath.length; i++) {
            Node4DiscoveryJob nodeJob = new Node4DiscoveryJob(request, nodePath[i]);
            session.saveOrUpdate(nodeJob);
            request.getDiscoveryJobNodes().add(nodeJob);
        }
        this.update(request);
        return request;
    } catch (HibernateException e) {
      throw new DMException(e);
    } catch (DMException e) {
      throw e;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4Discovery(com.npower.dm.core.Carrier, com.npower.dm.core.Device, java.lang.String[])
   */
  public ProvisionJob newJob4Discovery(Device device, String[] nodePath) throws DMException {
    assert nodePath != null: "Invalidate Node Path " + nodePath;
    
    try {
        Session session = this.getHibernateSession();
        DeviceBean deviceBean = this.getManagementBeanFactory().createDeviceBean();
        DeviceGroup group = deviceBean.newDeviceGroup();
        deviceBean.add(group, device);
        session.saveOrUpdate(group);
        
        return this.newJob4Discovery(group, nodePath);
    } catch (HibernateException e) {
      throw new DMException(e);
    } catch (DMException e) {
      throw e;
    }
  }


  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4Command(com.npower.dm.core.Device, java.io.InputStream)
   */
  public ProvisionJob newJob4Command(Device device, String scripts) throws DMException {
    assert device != null: "Invalidate Device ";
    
    // Verify the command script
    try {
       Compiler4CommandScript compiler = new Compiler4CommandScript(scripts);
       List<ManagementOperation> operations = compiler.compile();
       if (operations.isEmpty()) {
          throw new DMException("Compile error in the script.");
       }
      
    } catch (DMException ex) {
      throw new DMException("Compile error in the script.", ex);
    }

    try {
        Session session = this.getHibernateSession();
        DeviceBean deviceBean = this.getManagementBeanFactory().createDeviceBean();
        DeviceGroup group = deviceBean.newDeviceGroup();
        deviceBean.add(group, device);
        session.saveOrUpdate(group);
        
        return this.newJob4Command(group, scripts);
    } catch (HibernateException e) {
      throw new DMException(e);
    } catch (DMException e) {
      throw e;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4Command(com.npower.dm.core.DeviceGroup, java.io.InputStream)
   */
  public ProvisionJob newJob4Command(DeviceGroup group, String scripts) throws DMException {
    assert group != null && group.getID() > 0: "Invlidate group!";
    assert scripts != null: "Invalidate input for scripts ";

    // Verify the command script
    try {
        Compiler4CommandScript compiler = new Compiler4CommandScript(scripts);
        List<ManagementOperation> operations = compiler.compile();
        if (operations.isEmpty()) {
           throw new DMException("Compile error in the script.");
        }
    } catch (DMException ex) {
      throw new DMException("Compile error in the script.", ex);
    }
    
    try {
        Session session = this.getHibernateSession();
        // Create a ProvisionRequest
        ProvisionRequest request = createDefaultProvisionJob();
        request.setJobMode(ProvisionJob.JOB_MODE_DM);
        request.setJobType(ProvisionJob.JOB_TYPE_SCRIPT);
        request.setScheduledTime(new Date());
        request.setState(ProvisionJob.JOB_STATE_APPLIED);
        request.setTargetType(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE);
        //Clob clob = Hibernate.createClob(scripts);
        //request.setScript(clob);
        request.setScript(scripts);
        request.setLastUpdatedTime(new Date());
        session.saveOrUpdate(request);
        
        // Create a Element4Provision
        Element4Provision element = new Element4Provision(group, request);
        //elements.setSourceImage();
        //elements.setState();
        session.saveOrUpdate(element);
        
        Set<Device> devices = group.getDevices();
        for (Iterator<Device> i = devices.iterator(); i.hasNext(); ) {
            Device device = (Device)i.next();
            ProvisionJobStatus dpRequest = new DeviceProvisionRequestEntity(element, device);
            
            copyTo(request, dpRequest);
            session.saveOrUpdate(dpRequest);
            
            // Link to Element3Provision
            element.getDeviceProvReqs().add(dpRequest);
            
            // Link to Device
            if (device instanceof DeviceEntity) {
               ((DeviceEntity)device).getProvisionJobStatus().add(dpRequest);
            }
        }
        return request;
    } catch (DMException e) {
      throw e;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4Assignment(com.npower.dm.core.ProfileAssignment)
   */
  public ProvisionJob newJob4Assignment(ProfileAssignment assignment) throws DMException {
    assert (assignment != null):"Could not assign a NULL profile";
    assert (assignment.getDevice() != null):"The device related with the profile is NULL!";
    try {
        Session session = this.getHibernateSession();
        boolean resend = false;
        // Update ProfileAssignment
        session.saveOrUpdate(assignment);

        if (assignment.getID() == 0) {
           resend = true;
        } else {
          {
            // Checking the old assignment
            Criteria mainCriteria = session.createCriteria(DeviceProvisionRequestEntity.class);
            Criteria subCriteria = mainCriteria.createCriteria("jobState");
            Criteria subCriteria4Assi = subCriteria.createCriteria("jobAssignmentses");
            Criteria sub = subCriteria4Assi.createCriteria("profileAssignment");
            sub.add(Expression.eq("ID", new Long(assignment.getID())));
            // 检查是否存在Ready状态的Job
            mainCriteria.add(Expression.or(
                                      Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_READY),
                                      Expression.or(
                                                   Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_NOTIFIED),
                                                   Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_DOING)
                                                   )
                                          ));
            List<DeviceProvisionRequestEntity> list = mainCriteria.list();
            if (list.size() > 0) {
               ProvisionJobStatus status = (ProvisionJobStatus)list.get(0);
               return status.getProvisionElement().getProvisionRequest();
            }
          }
          {
            // 确定是否是Re-Assign. 算法如下：
            // 如果ProfileAssignment ID已经存在, 并且该ProfileAssignment已经同步过或同步过程失败, 则将ProfileAssingment标记为Re-Assign
            // 注意: 此处检测并没有检查相应设备是否已经DONE或Error, 或许应该针对设备进行检查.
            Criteria mainCriteria = session.createCriteria(DeviceProvisionRequestEntity.class);
            Criteria subCriteria = mainCriteria.createCriteria("jobState");
            Criteria subCriteria4Assi = subCriteria.createCriteria("jobAssignmentses");
            Criteria sub = subCriteria4Assi.createCriteria("profileAssignment");
            sub.add(Expression.eq("ID", new Long(assignment.getID())));
            mainCriteria.add(Expression.or(
                                           Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_DONE), 
                                           Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_ERROR))
                                           );
            List<DeviceProvisionRequestEntity> list = mainCriteria.list();
            resend = (list.size() > 0)?true:false;
          }
        }
        
        // Create a ProvisionRequest
        ProvisionRequest request = createDefaultProvisionJob();
        if (resend) {
           request.setJobType(ProvisionJob.JOB_TYPE_RE_ASSIGN_PROFILE);
        } else {
          request.setJobType(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE);
        }
        request.setJobMode(ProvisionJob.JOB_MODE_DM);
        request.setScheduledTime(new Date());
        request.setState(ProvisionJob.JOB_STATE_APPLIED);
        request.setTargetType(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE);
        request.setLastUpdatedTime(new Date());
        session.saveOrUpdate(request);
        
        // Create Job_State
        JobState jobState = new JobState();
        jobState.setAskBeforeApply(request.getAskBeforeApply());
        jobState.setAskBeforeDown(request.getAskBeforeDown());
        jobState.setAskCount(request.getAskCount());
        jobState.setAskInterval(request.getAskInterval());
        jobState.setDevice(assignment.getDevice());
        //jobState.setAskUser();
        //jobState.setInstallationState();
        //jobState.setJobAdapter();
        jobState.setJobStateType(request.getJobType());
        jobState.setProvisionRequest(request);
        jobState.setUiMode(request.getUiMode());
        session.saveOrUpdate(jobState);

        // Create a DeviceGroup
        DeviceBean deviceBean = this.getManagementBeanFactory().createDeviceBean();
        DeviceGroup group = deviceBean.newDeviceGroup();
        deviceBean.add(group, assignment.getDevice());
        session.saveOrUpdate(group);
        
        // Create a Element4Provision
        Element4Provision element = new Element4Provision(group, request);
        //elements.setSourceImage();
        //elements.setState();
        session.saveOrUpdate(element);
        
        Set<Device> devices = group.getDevices();
        for (Iterator<Device> i = devices.iterator(); i.hasNext(); ) {
            Device device = (Device)i.next();
            ProvisionJobStatus dpRequest = new DeviceProvisionRequestEntity(element, device);
            dpRequest.setJobState(jobState);
            
            copyTo(request, dpRequest);
            session.saveOrUpdate(dpRequest);
            
            // Link to Element3Provision
            element.getDeviceProvReqs().add(dpRequest);
            
            // Link to Device
            if (device instanceof DeviceEntity) {
               ((DeviceEntity)device).getProvisionJobStatus().add(dpRequest);
            }
            // Link to Job_State
            jobState.getDeviceProvisionRequests().add(dpRequest);
            
        }
        
        // Create Job_Assignment
        JobAssignmentsID jobAsisgnmentID = new JobAssignmentsID();
        jobAsisgnmentID.setAssignmentIndex(System.currentTimeMillis());
        jobAsisgnmentID.setJobStateId(jobState.getID());
        jobAsisgnmentID.setProfileAssignmentId(assignment.getID());
        JobAssignments jobAssignment = new JobAssignments(jobAsisgnmentID, assignment, jobState);
        session.saveOrUpdate(jobAssignment);
        
        return request;
        
    } catch (DMException e) {
      throw e;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4FirmwareUpdate(com.npower.dm.core.Device, com.npower.dm.core.Update)
   */
  public ProvisionJob newJob4FirmwareUpdate(Device dev, Update update) throws DMException {
    assert dev != null && dev.getID() > 0: "Invlidate device!";
    assert update != null && update.getID() > 0: "Invalidate update!";

    try {
        Session session = this.getHibernateSession();
        DeviceBean deviceBean = this.getManagementBeanFactory().createDeviceBean();
        DeviceGroup group = deviceBean.newDeviceGroup();
        deviceBean.add(group, dev);
        session.saveOrUpdate(group);
        
        return this.newJob4FirmwareUpdate(group, update);
    } catch (HibernateException e) {
      throw new DMException(e);
    } catch (DMException e) {
      throw e;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4FirmwareUpdate(com.npower.dm.core.DeviceGroup, com.npower.dm.core.Update)
   */
  public ProvisionJob newJob4FirmwareUpdate(DeviceGroup group, Update update) throws DMException {
    assert group != null && group.getID() > 0: "Invlidate group!";
    assert update != null && update.getID() > 0: "Invalidate update!";
    try {
        Session session = this.getHibernateSession();
        DeviceBean deviceBean = this.getManagementBeanFactory().createDeviceBean();
  
        // Create a ProvisionRequest
        ProvisionRequest request = createDefaultProvisionJob();
        request.setJobMode(ProvisionJob.JOB_MODE_DM);
        request.setJobType(ProvisionJob.JOB_TYPE_FIRMWARE);
        // Set target image version.
        request.setTargetImage(update.getToImage());
        // Target image description id target version ID.
        request.setTargetImageDescription(update.getToImage().getVersionId());
  
        request.setScheduledTime(new Date());
        request.setState(ProvisionJob.JOB_STATE_APPLIED);
        request.setTargetType(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE);
        request.setLastUpdatedTime(new Date());
        session.saveOrUpdate(request);
        
        // Create a Element4Provision
        Element4Provision element = new Element4Provision(group, request);
        element.setSourceImage(update.getFromImage());
        //elements.setState();
        session.saveOrUpdate(element);
        
        // Create device_prov_req
        Set<Device> devices = group.getDevices();
        for (Iterator<Device> i = devices.iterator(); i.hasNext(); ) {
            Device device = (Device)i.next();
            ProvisionJobStatus dpRequest = new DeviceProvisionRequestEntity(element, device);
            dpRequest.setToImage(update.getToImage());
            dpRequest.setOldCurrentImage(update.getFromImage());
            
            copyTo(request, dpRequest);
            session.saveOrUpdate(dpRequest);
            
            // Link to Element3Provision
            element.getDeviceProvReqs().add(dpRequest);
            
            // Link to Device
            if (device instanceof DeviceEntity) {
               ((DeviceEntity)device).getProvisionJobStatus().add(dpRequest);
            }
            device.setUpdate(update);
            deviceBean.update(device);
        }
  
        return request;
    } catch (HibernateException e) {
      throw new DMException(e);
    } catch (DMException e) {
      throw e;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#update(com.npower.dm.core.ProvisionJob)
   */
  public void update(ProvisionJob job) throws DMException {
    try {
        Session session = this.getHibernateSession();
        job.setLastUpdatedTime(new Date());
        session.saveOrUpdate(job);
        session.flush();
        session.refresh(job, LockMode.UPGRADE);
        //session.refresh(job, LockMode.UPGRADE);

        // Update all of status related with the job.
        String newState = job.getState();
        this.updateJobState(job.getID(), newState);
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#update(com.npower.dm.core.ProvisionJobStatus)
   */
  public void update(ProvisionJobStatus jobStatus) throws DMException {
    try {
        Session session = this.getHibernateSession();
        jobStatus.setLastUpdatedTime(new Date());
        //session.saveOrUpdate(jobStatus);
        session.merge(jobStatus);
        
        // 检查是否所有的任务都完成, 设置完成标志
        if (jobStatus.isFinished()) {
           ProvisionJob job = jobStatus.getProvisionElement().getProvisionRequest();
           for (ProvisionJobStatus status:job.getAllOfProvisionJobStatus()) {
               if (!status.isFinished()) {
                  return;
               }
           }
           job.setState(ProvisionJob.JOB_STATE_FINISHED);
           session.merge(job);
           //this.updateJobState(job.getID(), ProvisionJob.JOB_STATE_FINISHED);
        }
        
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#loadJobByID(java.lang.String)
   */
  public ProvisionJob loadJobByID(String jobID) throws DMException {
    assert jobID != null: "Invalidate jobID: " + jobID;
    try {
        return this.loadJobByID((new Long(jobID)).longValue());
    } catch (Exception ex) {
      throw new DMException("Invalidate jobID: " + jobID);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#loadJobByID(long)
   */
  public ProvisionJob loadJobByID(long jobID) throws DMException {
    assert jobID > 0: "Invlidate jobID: " + jobID;
    
    try {
        Session session = this.getHibernateSession();
        ProvisionJob job = (ProvisionJob)session.get(ProvisionRequest.class, new Long(jobID));
        return job;
    } catch (HibernateException e) {
      throw new DMException("Invalidate jobID: " + jobID, e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#cancel(java.lang.String)
   */
  public void cancel(String jobID) throws DMException {
    this.cancel(new Long(jobID).longValue());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#cancel(long)
   */
  public void cancel(long jobID) throws DMException {
    String newState = ProvisionJob.JOB_STATE_CANCELLED;
    updateJobState(jobID, newState);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#disable(java.lang.String)
   */
  public void disable(String jobID) throws DMException {
    this.disable(new Long(jobID).longValue());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#disable(long)
   */
  public void disable(long jobID) throws DMException {
    String newState = ProvisionJob.JOB_STATE_DISABLE;
    updateJobState(jobID, newState);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#disable(java.lang.String)
   */
  public void enable(String jobID) throws DMException {
    this.enable(new Long(jobID).longValue());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#disable(long)
   */
  public void enable(long jobID) throws DMException {
    String newState = ProvisionJob.JOB_STATE_APPLIED;
    updateJobState(jobID, newState);
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#delete(java.lang.String)
   */
  public void delete(String jobID) throws DMException {
    this.delete(new Long(jobID).longValue());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#delete(long)
   */
  public void delete(long jobID) throws DMException {
    try {
        Session session = this.getHibernateSession();
        ProvisionJob job = this.loadJobByID(jobID);
        if (job != null) {
           session.delete(job);
        }
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#findJobs(java.lang.String, java.lang.String, com.npower.dm.core.Carrier, com.npower.dm.core.Device)
   */
  public List<ProvisionJob> findJobs(String jobType, String jobState) throws DMException {
    try {
        Session session = this.getHibernateSession();
        Criteria mainCriteria = session.createCriteria(ProvisionRequest.class);
        mainCriteria.addOrder(Order.asc("scheduledTime"));
        mainCriteria.addOrder(Order.asc("ID"));
        mainCriteria.add(Expression.eq("jobMode", ProvisionJob.JOB_MODE_DM));
        
        if (StringUtils.isNotEmpty(jobType)) {
           mainCriteria.add(Expression.eq("jobType", jobType));
        }
        if (StringUtils.isNotEmpty(jobState)) {
           Criteria elementCriteria = mainCriteria.createCriteria("provisionElements");
          
           // Set status criteria
           Criteria statusCriteria = elementCriteria.createCriteria("deviceProvReqs");
           statusCriteria.add(Expression.eq("state", jobState));
        }
        return mainCriteria.list();
    } catch (HibernateException ex) {
      throw new DMException(ex);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#findJobs(java.lang.String, java.lang.String, com.npower.dm.core.Carrier, com.npower.dm.core.Device)
   */
  public List<ProvisionJob> findJobs(String jobType, String jobState, Device device) throws DMException {
    try {
        Session session = this.getHibernateSession();
        Criteria mainCriteria = session.createCriteria(ProvisionRequest.class);
        mainCriteria.add(Expression.eq("jobMode", ProvisionJob.JOB_MODE_DM));
        mainCriteria.add(Expression.eq("jobType", jobType));
        //mainCriteria.add(Expression.eq("state", jobState));
        mainCriteria.addOrder(Order.asc("scheduledTime"));
        mainCriteria.addOrder(Order.asc("ID"));
        
        Criteria elementCriteria = mainCriteria.createCriteria("provisionElements");
        
        // Set status criteria
        Criteria statusCriteria = elementCriteria.createCriteria("deviceProvReqs");
        statusCriteria.add(Expression.eq("state", jobState));
        statusCriteria.add(Expression.eq("device", device));
        // Set search by device
        //Criteria groupCriteria = elementCriteria.createCriteria("deviceGroup");
        //Criteria deviceCriteria = groupCriteria.createCriteria("devicesForDeviceGroup");
        //deviceCriteria.add(Expression.eq("device", device));
        
        return mainCriteria.list();
    } catch (HibernateException ex) {
      throw new DMException(ex);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#findJobsQueued(java.lang.String, com.npower.dm.core.Device, boolean)
   */
  public List<ProvisionJob> findJobsQueued(String jobType, Device device, boolean scheduled) throws DMException {
    try {
        Session session = this.getHibernateSession();
        Criteria mainCriteria = session.createCriteria(ProvisionRequest.class);
        mainCriteria.add(Expression.eq("jobMode", ProvisionJob.JOB_MODE_DM));
        mainCriteria.add(Expression.eq("jobType", jobType));
        mainCriteria.add(Expression.eq("state", ProvisionJob.JOB_STATE_APPLIED));
        if (scheduled) {
           mainCriteria.add(Expression.le("scheduledTime", new Date()));
        }
        mainCriteria.addOrder(Order.asc("scheduledTime"));
        mainCriteria.addOrder(Order.asc("ID"));
        
        Criteria elementCriteria = mainCriteria.createCriteria("provisionElements");
        Criteria deviceStatus = elementCriteria.createCriteria("deviceProvReqs");
        deviceStatus.add(Expression.eq("device", device));
        // DeviceJobStatus are READY, NOTIFIED or Doing
        Disjunction states = Expression.disjunction();
        states.add(Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_READY));
        states.add(Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_NOTIFIED));
        states.add(Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_DOING));
        deviceStatus.add(states);

        // Set search by Carrier
        //Criteria carrierCrit = elementCriteria.createCriteria("carrier");
        //carrierCrit.add(Expression.eq("ID", new Long(carrier.getID())));
        
        // Set search by device
        Criteria groupCriteria = elementCriteria.createCriteria("deviceGroup");
        Criteria deviceCriteria = groupCriteria.createCriteria("devicesForDeviceGroup");
        deviceCriteria.add(Expression.eq("device", device));
        
        return mainCriteria.list();
    } catch (HibernateException ex) {
      throw new DMException(ex);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#findJobsQueued(com.npower.dm.core.Device, boolean)
   */
  public List<ProvisionJob> findJobsQueued(Device device, boolean scheduled) throws DMException {
    try {
        Session session = this.getHibernateSession();
        Criteria mainCriteria = session.createCriteria(ProvisionRequest.class);
        mainCriteria.add(Expression.eq("jobMode", ProvisionJob.JOB_MODE_DM));
        mainCriteria.add(Expression.eq("state", ProvisionJob.JOB_STATE_APPLIED));
        if (scheduled) {
           mainCriteria.add(Expression.le("scheduledTime", new Date()));
        }
        mainCriteria.addOrder(Order.asc("scheduledTime"));
        mainCriteria.addOrder(Order.asc("ID"));
        
        Criteria elementCriteria = mainCriteria.createCriteria("provisionElements");
        Criteria deviceStatus = elementCriteria.createCriteria("deviceProvReqs");
        deviceStatus.add(Expression.eq("device", device));
        // DeviceJobStatus are READY, NOTIFIED or Doing
        Disjunction states = Expression.disjunction();
        states.add(Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_READY));
        states.add(Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_NOTIFIED));
        states.add(Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_DOING));
        deviceStatus.add(states);
        
        // Set search by Carrier
        //Criteria carrierCrit = elementCriteria.createCriteria("carrier");
        //carrierCrit.add(Expression.eq("ID", new Long(carrier.getID())));
        
        // Set search by device
        Criteria groupCriteria = elementCriteria.createCriteria("deviceGroup");
        Criteria deviceCriteria = groupCriteria.createCriteria("devicesForDeviceGroup");
        deviceCriteria.add(Expression.eq("device", device));
        
        return mainCriteria.list();
    } catch (HibernateException ex) {
      throw new DMException(ex);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#findJobsQueued(java.lang.String, com.npower.dm.core.Device)
   */
  public List<ProvisionJob> findJobsQueued(String jobType, Device device) throws DMException {
    return this.findJobsQueued(jobType, device, true);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#findJobsQueued(com.npower.dm.core.Device)
   */
  public List<ProvisionJob> findJobsQueued(Device device) throws DMException {
    return this.findJobsQueued(device, true);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#findJobsQueued(boolean)
   */
  public List<ProvisionJob> findJobsQueued(boolean scheduled) throws DMException {
    try {
        Session session = this.getHibernateSession();
        Criteria mainCriteria = session.createCriteria(ProvisionRequest.class);
        mainCriteria.add(Expression.eq("jobMode", ProvisionJob.JOB_MODE_DM));
        mainCriteria.add(Expression.eq("state", ProvisionJob.JOB_STATE_APPLIED));
        if (scheduled) {
           mainCriteria.add(Expression.le("scheduledTime", new Date()));
        }
        mainCriteria.addOrder(Order.asc("scheduledTime"));
        mainCriteria.addOrder(Order.asc("ID"));
        
        Criteria elementCriteria = mainCriteria.createCriteria("provisionElements");
        Criteria deviceStatus = elementCriteria.createCriteria("deviceProvReqs");
        // DeviceJobStatus are READY, NOTIFIED or Doing
        Disjunction states = Expression.disjunction();
        states.add(Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_READY));
        states.add(Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_NOTIFIED));
        states.add(Expression.eq("state", ProvisionJobStatus.DEVICE_JOB_STATE_DOING));
        deviceStatus.add(states);
        
        return mainCriteria.list();
    } catch (HibernateException ex) {
      throw new DMException(ex);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getProvisionJob4Device(com.npower.dm.core.Device)
   */
  public ProvisionJobStatus getProvisionJobStatus(ProvisionJob job, Device device) throws DMException {
    Session session = null;
    try {
        session = this.getHibernateSession();
        Criteria mainCriteria = session.createCriteria(DeviceProvisionRequestEntity.class);
        mainCriteria.add(Expression.eq("device", device));

        Criteria elementCriteria = mainCriteria.createCriteria("provisionElement");
        elementCriteria.add(Expression.eq("provisionRequest", job));

        List<DeviceProvisionRequestEntity> states = mainCriteria.list();
        if (states != null && states.size() == 1) {
           return (ProvisionJobStatus)states.get(0);
        } else {
          return null;
          /*
          if (states == null || states.size() == 0) {
             // This device is not the target of this job.
             throw new DMException("error in data constrain, none status boundled with the device.");
          } else {
            throw new DMException("error in data constrain, multiple status boundled with the device.");
          }
          */
        }
    } catch (Exception ex) {
      throw new DMException(ex);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#getProfileAssignment(com.npower.dm.core.Device)
   */
  public ProfileAssignment getProfileAssignment(ProvisionJob job, Device device) throws DMException {
    Session session = null;
    try {
        session = this.getHibernateSession();
        Criteria mainCriteria = session.createCriteria(ProfileAssignmentEntity.class);
        mainCriteria.add(Expression.eq("device", device));

        Criteria subCriteria = mainCriteria.createCriteria("jobAssignmentses");
        Criteria jobStateCriteria = subCriteria.createCriteria("jobState");
        jobStateCriteria.add(Expression.eq("provisionRequest", job));

        List<ProfileAssignmentEntity> results = mainCriteria.list();
        if (results != null && results.size() == 1) {
           return (ProfileAssignment)results.get(0);
        } else {
          if (results == null || results.size() == 0) {
             // This device is not the target of this job.
             return null;
          } else {
            throw new DMException("error in data constrain, multiple ProfileAssignment boundled with the device:" + device.getID() + " and Job: " + job.getID() + ".");
          }
        }
    } catch (Exception ex) {
      throw new DMException(ex);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#getLinkedProfileAssignment(com.npower.dm.core.ProfileAssignment)
   */
  public ProfileAssignment getLinkedProfileAssignment(ProfileAssignment assignment) throws DMException {
    return this.getLinkedProfileAssignment(assignment, null);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#caculateProfileAssignmentValue(com.npower.dm.core.ProfileAssignment, com.npower.dm.core.ProfileNodeMapping)
   */
  public Object caculateProfileAssignmentValue(ProfileAssignment assignment, ProfileNodeMapping nodeMapping) throws APLinkValueNotFoundException, DMException {
    String mappingType = nodeMapping.getMappingType();
    Object value = null;
    if (mappingType.equalsIgnoreCase(ProfileNodeMapping.NODE_MAPPING_TYPE_VALUE)) {
       // Value mapping mode.
       value = nodeMapping.getValue();
    } else if (mappingType.equalsIgnoreCase(ProfileNodeMapping.NODE_MAPPING_TYPE_ATTRIBUTE)) {
      // Attribute mapping mode
      ProfileAttribute attribute = nodeMapping.getProfileAttribute();
      if (attribute == null) {
         throw new NullPointerException("ProfileNodeMapping: " + nodeMapping.getID() + " referenced a Null ProfileAttribute");
      }
      String attributeTypeName = attribute.getProfileAttribType().getName();
      // 提取参数值
      if (attributeTypeName != null 
          && (attributeTypeName.equals(ProfileAttributeType.TYPE_APLINK) || attributeTypeName.equals(ProfileAttributeType.TYPE_APNAME))) {
         // APLink or APName modes
         
         // Retrieve Linked Profile
         ProfileAssignment linkedAssignment = this.getLinkedProfileAssignment(assignment);
        
         if (linkedAssignment != null) {
            value = linkedAssignment.getProfileRootNodePath();
         }
         if (value == null) {
            // Could not found ProfileAssignment which will be linked by current profile assgingment
            throw new APLinkValueNotFoundException("Could not found value for APLink: " + nodeMapping.getDdfNode().getNodePath());
         }
         
         if (attributeTypeName.equals(ProfileAttributeType.TYPE_APNAME)) {
            // APName mode, extract and return name
            
            String linkedPath = (String)value;
            int endIndex = linkedPath.lastIndexOf('/');
            if (endIndex > 0 && endIndex < linkedPath.length()) { 
               value = linkedPath.substring(endIndex + 1, linkedPath.length());
            }
         }
      } else if (attributeTypeName != null 
          && (attributeTypeName.equals(ProfileAttributeType.TYPE_SELF_LINK) || attributeTypeName.equals(ProfileAttributeType.TYPE_SELF_NAME))) {
        
      } else {
        // In general attribute mode, 两种处理模式:
        // 1. Attribute name 引用自己的参数值
        // 2. Attribute name 引用Linked Profile的参数值
        String linkedProfileCategoryName = nodeMapping.getCategoryName();
        String attributeName = attribute.getName();
        ProfileAttributeValue attriValue = null;
        if (StringUtils.isEmpty(linkedProfileCategoryName)) {
           // Case#1: Attribute name 引用自己的参数值
           // Get value from assginment and profile config
           attriValue = getAttributeValue(assignment, attributeName);
        } else {
          // Case#2. Attribute name 引用Linked Profile的参数值
          
          // Retrieve Linked Profile
          ProfileAssignment linkedAssignment = this.getLinkedProfileAssignment(assignment, linkedProfileCategoryName);
          if (linkedAssignment == null) {
             throw new DMException("Could not found linked profile assignment for linked category: " + linkedProfileCategoryName);
          }
          // 查找引用的ProfileAssignment中的参数值
          if (! linkedProfileCategoryName.equalsIgnoreCase(linkedAssignment.getProfileConfig().getProfileTemplate().getProfileCategory().getName())) {
             throw new DMException("Error in caculate node mapping value in linked profile category mode: AttrName: " + attributeName +
                                   ", LinkedCategory: " + linkedProfileCategoryName + ", " + nodeMapping.getDdfNode().getNodePath());
          }
          
          // 从Linked Profile中提取参数
          attriValue = getAttributeValue(linkedAssignment, attributeName);
          if (attriValue == null) {
             throw new DMException("Missing value in ProfileAssignment: " + linkedAssignment.getProfileConfig().getName() + ", attribute name: "
                   + attributeName);
          }
          
        }
        if (attriValue == null) {
           // Fix Bug#416
           // Profile Config中提取不到参数时，从Template中提取缺省值
           value = nodeMapping.getProfileAttribute().getDefaultValue();
        } else if (attributeTypeName != null && attributeTypeName.equals(ProfileAttributeType.TYPE_BINARY)) {
          // Get binary value
          value = attriValue.getBytes();
        } else {
          // Get text value
          value = attriValue.getStringValue();
        }
        
        if (value != null && value instanceof String) {
           // Translate value
           value = nodeMapping.translateValue((String)value);
        }
      }
    }
    return value;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#copyTo(sync4j.framework.engine.dm.DeviceDMState)
   */
  public void copy(ProvisionJob job, DeviceDMState dms) throws NotFoundException {
    // Load DeviceState from DM queued.
    String deviceExternalID = dms.deviceId;
    
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);

        dms.operation = job.getJobType();
        dms.mssid = (new Long(job.getID())).toString();
        // Copy status to DeviceDMState
        ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
        if (status.getState().equals(ProvisionJobStatus.DEVICE_JOB_STATE_READY)) {
           dms.state = DeviceDMState.STATE_MANAGEABLE;
        } else if (status.getState().equals(ProvisionJobStatus.DEVICE_JOB_STATE_DOING)) {
          dms.state = DeviceDMState.STATE_IN_PROGRESS;
        } else if (status.getState().equals(ProvisionJobStatus.DEVICE_JOB_STATE_DONE)) {
          dms.state = DeviceDMState.STATE_COMPLETED;
        } else if (status.getState().equals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED)) {
          dms.state = DeviceDMState.STATE_ABORTED;
        } else if (status.getState().equals(ProvisionJobStatus.DEVICE_JOB_STATE_ERROR)) {
          dms.state = DeviceDMState.STATE_ERROR;
        }
    } catch (DMException ex) {
      throw new NotFoundException("Error reading the device DM state " + dms.toString(), ex);
    } finally {
      if (factory != null) {
         //factory.release();
      }
    }
    
  }


  /* (non-Javadoc)
   * @see com.npower.dm.core.ProvisionJob#copyFrom(sync4j.framework.engine.dm.DeviceDMState)
   */
  public void copy(DeviceDMState dms, ProvisionJob job) throws DMException {
    // Load DeviceState from DM queued.
    String deviceExternalID = dms.deviceId;
    
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);

        // Copy status from DeviceDMState
        ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
        if (dms.state == DeviceDMState.STATE_MANAGEABLE) {
           status.setState(ProvisionJobStatus.DEVICE_JOB_STATE_READY);
        } else if (dms.state == DeviceDMState.STATE_IN_PROGRESS) {
          status.setState(ProvisionJobStatus.DEVICE_JOB_STATE_DOING);
        } else if (dms.state == DeviceDMState.STATE_COMPLETED) {
          status.setState(ProvisionJobStatus.DEVICE_JOB_STATE_DONE);
        } else if (dms.state == DeviceDMState.STATE_ABORTED) {
          status.setState(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED);
        } else if (dms.state == DeviceDMState.STATE_ERROR) {
          status.setState(ProvisionJobStatus.DEVICE_JOB_STATE_ERROR);
        } else if (dms.state == DeviceDMState.STATE_IN_PROGRESS) {
          status.setState(ProvisionJobStatus.DEVICE_JOB_STATE_WAITING_CLIENT_INITIALIZED_SESSION);
        }
        Session session = this.getHibernateSession();
        //session.saveOrUpdate(status);
        session.merge(status);
        session.saveOrUpdate(job);
    } catch (DMException ex) {
      throw new DMException("Error reading the device DM state " + dms.toString(), ex);
    } finally {
      if (factory != null) {
         //factory.release();
      }
    }
    
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJobs4Assignment(com.npower.dm.core.ProfileAssignment, java.lang.String, java.lang.String, java.util.Date)
   */
  public List<ProvisionJob> newJobs4Assignment(ProfileAssignment assignment, String jobName, String jobDescription, Date scheduledTime) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
    
    Device device = assignment.getDevice();
    ProfileConfig profile = assignment.getProfileConfig();
    ProfileTemplate template = profile.getProfileTemplate();
    
    List<ProvisionJob> result = new ArrayList<ProvisionJob>();
    
    // 2. Linked NAP Assignment Job
    if (template.getNeedsNap()) {
       ProfileConfig napProfile = profile.getNAPProfile();
       if (napProfile == null) {
          throw new DMException("The profile: " + profile.getID() + " required NAP profile, but missing NAP Profile.");
       }
       ProfileAssignment napAssignment = this.getLinkedProfileAssignment(assignment);
       if (napAssignment == null) {
          // Not found linked nap profile, create a new assignment for nap profile.
          napAssignment = assignmentBean.newProfileAssignmentInstance(napProfile, device);
          // Update assignment
          assignmentBean.update(napAssignment);
       }
       
       // Create a NAP Profle Job and set job properties.
       ProvisionJob napJob = this.newJob4Assignment(napAssignment);
       napJob.setName(jobName);
       napJob.setDescription(jobDescription);
       napJob.setScheduledTime(scheduledTime);
       this.update(napJob);
       
       result.add(napJob);
    }
    
    // 3. Linked Proxy Assignment Job
    if (template.getNeedsProxy()) {
       ProfileConfig proxyProfile = profile.getProxyProfile();
       if (proxyProfile == null) {
          throw new DMException("The profile: " + profile.getID() + " required Proxy profile, but missing Proxy Profile.");
       }
       ProfileAssignment proxyAssignment = this.getLinkedProfileAssignment(assignment);
       if (proxyAssignment == null) {
          // Create a new linked proxy profile assignment.
          proxyAssignment = assignmentBean.newProfileAssignmentInstance(proxyProfile, device);
          // Update assignment
          assignmentBean.update(proxyAssignment);
       }
       
       ProfileTemplate proxyTemplate = proxyProfile.getProfileTemplate();
       if (proxyTemplate.getNeedsNap()) {
          // Create NAP Profile which linked by Proxy Profile.
          ProfileConfig napProfile = proxyProfile.getNAPProfile();
          if (proxyProfile == null) {
             throw new DMException("The profile: " + proxyProfile.getID() + " required NAP profile, but missing NAP Profile.");
          }
          ProfileAssignment napAssignment = this.getLinkedProfileAssignment(proxyAssignment);
          if (napAssignment == null) {
             // Not found linked nap profile, create a new assignment for nap profile.
             napAssignment = assignmentBean.newProfileAssignmentInstance(napProfile, device);
             // Update assignment
             assignmentBean.update(napAssignment);
          }
          
          // Create a NAP Profle Job which linked by Proxy Profile and set job properties.
          ProvisionJob napJob = this.newJob4Assignment(napAssignment);
          napJob.setName(jobName);
          napJob.setDescription(jobDescription);
          napJob.setScheduledTime(scheduledTime);
          this.update(napJob);
          
          result.add(napJob);
       }
       
       // Create a Proxy Profle Job and set job properties.
       ProvisionJob proxyJob = this.newJob4Assignment(proxyAssignment);
       proxyJob.setName(jobName);
       proxyJob.setDescription(jobDescription);
       proxyJob.setScheduledTime(scheduledTime);
       this.update(proxyJob);
       
       result.add(proxyJob);
    }
    
    // 4. Main assignment Job
    // Update assignment
    assignmentBean.update(assignment);
    ProvisionJob job = this.newJob4Assignment(assignment);
    job.setName(jobName);
    job.setDescription(jobDescription);
    job.setScheduledTime(scheduledTime);
    this.update(job);
    
    // Link parent relationship
    for (ProvisionJob child: result) {
        child.setParent(job);
        this.update(child);
    }
    
    result.add(job);
    return result;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareInstall(com.npower.dm.core.DeviceGroup, com.npower.dm.core.Software)
   */
  public ProvisionJob newJob4SoftwareInstall(DeviceGroup group, Software software) throws DMException {
    assert group != null && group.getID() > 0: "Invlidate group!";
    assert software != null && software.getId() > 0: "Invalidate update!";

    try {
        Session session = this.getHibernateSession();
        ProvisionRequest request = netSoftwareInstallJob(session, group, software);
        return request;
    } catch (HibernateException e) {
      throw new DMException(e);
    } catch (DMException e) {
      throw e;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareInstall(com.npower.dm.core.Device, com.npower.dm.core.Software)
   */
  public ProvisionJob newJob4SoftwareInstall(Device device, Software software) throws DMException {
    assert device != null && device.getID() > 0: "Invlidate device!";
    assert software != null && software.getId() > 0: "Invalidate update!";

    try {
        Session session = this.getHibernateSession();
        DeviceBean deviceBean = this.getManagementBeanFactory().createDeviceBean();
        DeviceGroup group = deviceBean.newDeviceGroup();
        deviceBean.add(group, device);
        session.saveOrUpdate(group);
        
        ProvisionRequest request = netSoftwareInstallJob(session, group, software);
        return request;
    } catch (HibernateException e) {
      throw new DMException(e);
    } catch (DMException e) {
      throw e;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareUnInstall(com.npower.dm.core.Device, com.npower.dm.core.Software)
   */
  public ProvisionJob newJob4SoftwareUnInstall(Device device, Software software) throws DMException {
    ProvisionJob job = this.newJob4SoftwareInstall(device, software);
    job.setJobType(ProvisionJob.JOB_TYPE_SOFTWARE_UN_INSTALL);
    return job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareActivation(com.npower.dm.core.Device, com.npower.dm.core.Software)
   */
  public ProvisionJob newJob4SoftwareActivation(Device device, Software software) throws DMException {
    ProvisionJob job = this.newJob4SoftwareInstall(device, software);
    job.setJobType(ProvisionJob.JOB_TYPE_SOFTWARE_ACTIVE);
    return job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareDeactivation(com.npower.dm.core.Device, com.npower.dm.core.Software)
   */
  public ProvisionJob newJob4SoftwareDeactivation(Device device, Software software) throws DMException {
    ProvisionJob job = this.newJob4SoftwareInstall(device, software);
    job.setJobType(ProvisionJob.JOB_TYPE_SOFTWARE_DEACTIVE);
    return job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareActivation(com.npower.dm.core.DeviceGroup, com.npower.dm.core.Software)
   */
  public ProvisionJob newJob4SoftwareActivation(DeviceGroup group, Software software) throws DMException {
    ProvisionJob job = this.newJob4SoftwareInstall(group, software);
    job.setJobType(ProvisionJob.JOB_TYPE_SOFTWARE_ACTIVE);
    return job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareDeactivation(com.npower.dm.core.DeviceGroup, com.npower.dm.core.Software)
   */
  public ProvisionJob newJob4SoftwareDeactivation(DeviceGroup group, Software software) throws DMException {
    ProvisionJob job = this.newJob4SoftwareInstall(group, software);
    job.setJobType(ProvisionJob.JOB_TYPE_SOFTWARE_DEACTIVE);
    return job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareUnInstall(com.npower.dm.core.DeviceGroup, com.npower.dm.core.Software)
   */
  public ProvisionJob newJob4SoftwareUnInstall(DeviceGroup group, Software software) throws DMException {
    ProvisionJob job = this.newJob4SoftwareInstall(group, software);
    job.setJobType(ProvisionJob.JOB_TYPE_SOFTWARE_UN_INSTALL);
    return job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareInstall4Stage2(com.npower.dm.core.DeviceGroup, com.npower.dm.core.Software)
   */
  public ProvisionJob newJob4SoftwareInstall4Stage2(DeviceGroup group, Software software) throws DMException {
    ProvisionJob job = this.newJob4SoftwareInstall(group, software);
    job.setJobType(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL_STAGE_2);
    return job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareInstall4Stage2(com.npower.dm.core.Device, com.npower.dm.core.Software)
   */
  public ProvisionJob newJob4SoftwareInstall4Stage2(Device device, Software software) throws DMException {
    ProvisionJob job = this.newJob4SoftwareInstall(device, software);
    job.setJobType(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL_STAGE_2);
    return job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareDiscovery(com.npower.dm.core.Device)
   */
  public ProvisionJob newJob4SoftwareDiscovery(Device device) throws DMException {
    ProvisionJob job = this.newJob4SoftwareInstall(device, null);
    job.setJobType(ProvisionJob.JOB_TYPE_SOFTWARE_DISCOVERY);
    return job;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ProvisionJobBean#newJob4SoftwareDiscovery(com.npower.dm.core.DeviceGroup)
   */
  public ProvisionJob newJob4SoftwareDiscovery(DeviceGroup group) throws DMException {
    ProvisionJob job = this.newJob4SoftwareInstall(group, null);
    job.setJobType(ProvisionJob.JOB_TYPE_SOFTWARE_DISCOVERY);
    return job;
  }

}
