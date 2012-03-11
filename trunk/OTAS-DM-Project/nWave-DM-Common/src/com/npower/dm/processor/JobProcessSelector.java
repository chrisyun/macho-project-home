/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/JobProcessSelector.java,v 1.29 2008/06/16 10:11:14 zhao Exp $
  * $Revision: 1.29 $
  * $Date: 2008/06/16 10:11:14 $
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
package com.npower.dm.processor;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.config.Configuration;
import sync4j.framework.config.ConfigurationConstants;
import sync4j.framework.core.Alert;
import sync4j.framework.core.ComplexData;
import sync4j.framework.core.Item;
import sync4j.framework.core.MetInf;
import sync4j.framework.core.Meta;
import sync4j.framework.core.Source;
import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.protocol.ManagementInitialization;
import sync4j.framework.server.dm.ProcessorSelector;
import sync4j.framework.tools.beans.BeanInitializationException;
import sync4j.framework.tools.beans.LazyInitBean;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Update;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.UpdateImageBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * This is an implementation of ProcessorSelector that selects a management
 * processor looking up the DM Provision jobs .
 * <p>
 * If no device doesn't found for the given session id, the processor specified by
 * <i>errorProcessor</i> is used.
 * <p>
 * If queue for the given device is empty, the processor specified by
 * <i>defaultProcessor</i> is used.
 * <p>
 * If found queued job for the given device, the processor will be selected and specified
 * based on JobType:
 * <li>CommandProcessor</li>
 * <li>TreeDiscoveryProcessor</li>
 * <li>ProfileAssignmentProcessor</li>
 * <li>ProfileReAssignmentProcessor</li>
 * 
 * 
 * <p>
 *
 * <p>
 *
 *
 * @author Zhao DongLu
 * @version $Revision: 1.29 $ $Date: 2008/06/16 10:11:14 $
 */
public class JobProcessSelector implements ProcessorSelector, LazyInitBean, ConfigurationConstants {

  private static Log log = LogFactory.getLog(JobProcessSelector.class);
  /**
   * The default processor server bean name
   */
  private String defaultProcessor;

  /**
   * The error processor server bean name
   */
  private String errorProcessor;

  /**
   * If true, will always return JobQueueProcessor.
   */
  private boolean alwaysQueueProcessor = true;

  /**
   *
   */
  public JobProcessSelector() {
    super();
  }
  
  class ProcessorRecord {
    private String processName = null;
    private long jobID = 0;
    /**
     * 
     */
    public ProcessorRecord() {
      super();
    }
    /**
     * @param jobID
     * @param processName
     */
    public ProcessorRecord(long jobID, String processName) {
      super();
      this.jobID = jobID;
      this.processName = processName;
    }
    /**
     * @return Returns the processName.
     */
    public String getProcessName() {
      return processName;
    }
    /**
     * @param processName The processName to set.
     */
    public void setProcessName(String processName) {
      this.processName = processName;
    }
    /**
     * @return Returns the jobID.
     */
    public long getJobID() {
      return jobID;
    }
    /**
     * @param jobID The jobID to set.
     */
    public void setJobID(long jobID) {
      this.jobID = jobID;
    }
    
    
  }

  // Setter and Getters *******************************************************************
  /**
   * Sets defaultProcessor
   *
   * @param defaultProcessor the new default processor name
   */
  public void setDefaultProcessor(String defaultProcessor) {
      this.defaultProcessor = defaultProcessor;
  }

  /**
   * Returns defaultProcessor
   *
   * @return defaultProcessor property value
   */
  public String getDefaultProcessor() {
      return this.defaultProcessor;
  }

  /**
   * Sets errorProcessor
   *
   * @param errorProcessor the new error processor name
   */
  public void setErrorProcessor(String errorProcessor) {
      this.errorProcessor = errorProcessor;
  }

  /**
   * Returns errorProcessor
   *
   * @return errorProcessor property value
   */
  public String getErrorProcessor() {
      return this.errorProcessor;
  }

//  /**
//   * Sets the jobProcessor
//   * @return
//   */
//  public String getJobProcessor() {
//    return jobProcessor;
//  }
//
//  /**
//   * Return jobProcessor
//   * @param jobProcessor
//   */
//  public void setJobProcessor(String jobProcessor) {
//    this.jobProcessor = jobProcessor;
//  }

  /**
   * Processor name postfix (to be appended to the operation name).
   */
  private String namePostfix;

  /**
   * Processor name prefix (to be prepended to the operation name).
   */
  private String namePrefix;

  /**
   * Sets namePrefix
   *
   * @param namePrefix the new processor name prefix
   */
  public void setNamePrefix(String namePrefix) {
      this.namePrefix = namePrefix;
  }

  /**
   * Returns namePrefix
   *
   * @return namePrefix property value
   */
  public String getNamePrefix() {
      return namePrefix;
  }

  /**
   * Sets namePostfix
   *
   * @param namePostfix the new processor name postfix
   */
  public void setNamePostfix(String namePostfix) {
      this.namePostfix = namePostfix;
  }

  /**
   * Returns namePostfix
   *
   * @return namePostfix property value
   */
  public String getNamePostfix() {
      return namePostfix;
  }

  /**
   * @return the alwaysQueueProcessor
   */
  public boolean isAlwaysQueueProcessor() {
    return alwaysQueueProcessor;
  }

  /**
   * @param alwaysQueueProcessor the alwaysQueueProcessor to set
   */
  public void setAlwaysQueueProcessor(boolean alwaysQueueProcessor) {
    this.alwaysQueueProcessor = alwaysQueueProcessor;
  }

  // Private methods ---------------------------------------------------------------------------
  /**
   * Get a processor for client initiated.
   * @param dms
   * @param devInfo
   * @param init
   * @return
   */
  private ManagementProcessor getProcessor4ClientInitiated(DeviceDMState dms, DevInfo devInfo, ManagementInitialization init) {
    Alert alert = null;
    if (init != null) {
       alert = init.getClientAlert();
    }
    
    boolean isClientInitiated = false;
    if (alert != null) {
       // Check Client Alert content
       List<Item> items = alert.getItems();
       for (Item item: items) {
           ComplexData cData = item.getData();
           // "405"
           String data = cData.getData();
           
           Meta meta = item.getMeta();
           MetInf metInf = meta.getMetInf();
           // "org.openmobilealliance.firmwareupdate.downloadandupdate"
           String type = metInf.getType();
           // "critical"
           String mark = metInf.getMark();
           
           Source source = item.getSource();
           // "./Fw/FwPkg01"
           String locURI = null;
           if (source != null) {
              locURI = source.getLocURI();
           }
           
           log.info("Client Initiated Alert command:" +
                    "\n\tCommand: " + alert.getName() +
                    "\n\tData: " + data +
                    "\n\tType: " + type +
                    "\n\tMark: " + mark +
                    "\n\tLocURI: " + locURI);
           if (StringUtils.isNotEmpty(type)) {
              // Got Client Initiated session from Phone terminal.
              if (type.equalsIgnoreCase("org.openmobilealliance.dm.firmwareupdate.userrequest") || type.equalsIgnoreCase("org.openmobilealliance.firmwareupdate.userrequest")) {
                // User initited FOTA Update.
                log.info("Firmware update request by user.");
                ManagementBeanFactory factory = null;
                try {
                    // 1. Checking availiable Update package.
                    factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
                    UpdateImageBean updateBean = factory.createUpdateImageBean();
                    String deviceExternalID = dms.deviceId;
                    DeviceBean deviceBean = factory.createDeviceBean();
                    Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
                    if (device == null) {
                       throw new ManagementException("Could not load device: from DM inventory.");
                    }
                    String currentVersion = deviceBean.getCurrentFirmwareVersionId(device.getID());
                    log.info("Firmware update request by user. device current version: " + currentVersion);
                    List<Update> updates = updateBean.findUpdates4Upgrade(device.getModel(), currentVersion);
                    log.info("Firmware update request by user, found total of availiable upgrade firmwares: " + updates.size());
                    if (updates != null && updates.size() > 0) {
                       ProvisionJobBean jobBean = factory.createProvisionJobBean();
                       // Using last update.
                       try {
                           // Submit a Firmware Job.
                           factory.beginTransaction();
                           ProvisionJob job = jobBean.newJob4FirmwareUpdate(device, updates.get(updates.size() - 1));
                           factory.commit();
                           log.info("Firmware update request by user, found more than one update package, create a job ID: " + job.getID());
                       } catch (Exception ex) {
                         factory.rollback();
                       }
                    }
                } catch (Exception ex) {
                  log.error(ex.getMessage(), ex);
                } finally {
                  if (factory != null) {
                     factory.release();
                  }
                }
                isClientInitiated = false;
                break;
              } else if (type.startsWith("org.openmobilealliance.firmwareupdate") || type.startsWith("org.openmobilealliance.dm.firmwareupdate")) {
                // FOTA Alert: Terminal notificate the Update status.
                isClientInitiated = true;
                break;
              }
           }
       }
    }
    if (isClientInitiated) {
       return new ClientInitiatedProcessor();
    } else {
      return null;
    }
  }

  /**
   * <pre>
   * 根据当前设备的JobQueue的状态提取Processor.
   * 1. 如果JobQueue是空
   * 2. 如果JobQueue中有一个Job, 根据其类型提取Processor
   * 3. 如果JobQueue中有多个Job, 返回QueueProcessor.
   * </pre>
   * @param jobBean
   * @param device
   * @return
   * @throws DMException
   */
  private ProcessorRecord getProcessorByJobQueue(ProvisionJobBean jobBean, Device device) throws DMException {
    ProcessorRecord result = new ProcessorRecord();
     List<ProvisionJob> jobs = jobBean.findJobsQueued(device);
     if (jobs == null || jobs.size() == 0) {
        if (log.isDebugEnabled()) {
           log.debug("No job in queue for this device: " + device.getID() + " , reset the DMState.mssid=0");
        }
        // No any job queued for this device.
        // result.setProcessName(this.getDefaultProcessor());
        result.setProcessName(com.npower.dm.processor.JobQueueProcessor.class.getName());
        // Generic Processor: job id is 0
        result.setJobID(0);
     } else if (jobs.size() == 1) {
       // 根据配置决定是否总是返回QueueProcessor
       if (this.isAlwaysQueueProcessor()) {
          // Always queue processor.
         result.setProcessName(com.npower.dm.processor.JobQueueProcessor.class.getName());
       } else {
         // Bundle a processor against the job type
         ProvisionJob job = (ProvisionJob)jobs.get(0);
         if (log.isDebugEnabled()) {
            log.debug("Job queue size is " + jobs.size() + ", select a processor for the first job: " + job.getID());
         }
         result.setProcessName(JobProcessSelector.getProcessorByJobType(job.getJobType()));
         // Caution: Job-bundle processor must assign the jobID into the DeviceDMState
         result.setJobID(job.getID());
       }
     } else {
       // multiple jobs, using queue processor.
       if (log.isDebugEnabled()) {
         log.debug("Job queue size is " + jobs.size() );
       }
       result.setProcessName(com.npower.dm.processor.JobQueueProcessor.class.getName());
     }
    return result;
  }
  
  /**
   * Get a processor for server initiated.
   * @param dms
   * @param devInfo
   * @param init
   * @return
   */
  private ManagementProcessor getProcessor4ServerInitiated(DeviceDMState dms, DevInfo devInfo, ManagementInitialization init) {
    if (log.isDebugEnabled()) {
       log.debug("select a processor for DMState: " + dms);
    }
    String deviceExternalID = devInfo.getDevId();
    if (log.isDebugEnabled()) {
       log.debug("device id: " + deviceExternalID);
    }
    if (deviceExternalID.toUpperCase().startsWith("IMEI:")) {
       // Clear the prefix "IMEI:"
       //deviceExternalID = deviceExternalID.substring(5, deviceExternalID.length());
    }
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        if (device == null) {
           // Return a defaultProcessor for none-register device.
           if (log.isDebugEnabled()) {
              log.debug("No job for this processor, reset the DMState.mssid=0");
           }
           dms.mssid = "0";
           return getProcessorByName(this.getErrorProcessor());
        }
        ProvisionJobBean jobBean = factory.createProvisionJobBean();

        /**
         * Selector创建和选择Processor时，根据如下原则:
         * 1. 如果DeviceDMState的mssid为0, 则选择使用DefaultProcessor.
         * 2. 如果DeviceDMState的mssid包含有效的JobID, 将根据该jobID所对应的JobType选择Processor.
         * 3. 如果mssid中包含非0的jobID, 但该JobID所对应的设备Job状态为Done, 则抛出RuntimeException, 表明该mssid的Job无效
         * 
         * 注意：对非0的JobID处理，主要为JobQueueProcessor提供支持, 在JobQueueProcessor中,将对getProcessor(...)调用前送入mssid为对应的JobID.
         */
        String processorName = null;
        long jobID = 0;
        try {
            if (dms.mssid != null) {
               jobID = new Long(dms.mssid).longValue();
            }
        } catch (NumberFormatException e) {
        }
        if (jobID <= 1) {
           // Find job
           if (log.isDebugEnabled()) {
             log.debug("No jobID assigned for ProcessorSelector, Find jobs from DM inventory.");
           }
           // 根据JobQueue的情况, 获取正确的Processor
           ProcessorRecord result = getProcessorByJobQueue(jobBean, device);
           jobID = result.getJobID();
           processorName = result.getProcessName();
        } else {
          // Load Job, checking the job status.
          ProvisionJob job = jobBean.loadJobByID(jobID);
          if (job == null) {
             throw new RuntimeException("Invalidate jobID: " + jobID + ", no exists!");
          }
          processorName = JobProcessSelector.getProcessorByJobType(job.getJobType());
          jobID = job.getID();
        }
        // Store the jobID into dmState.mssid
        if (log.isDebugEnabled()) {
           log.debug("Set the jobID: " + jobID + " to the DMState.mssid");
        }
        dms.mssid = Long.toString(jobID);
        
        if (log.isDebugEnabled()) {
           log.debug("selected processor: " + processorName);
        }

        log.info("selected processor: " + processorName + " for device: " + device.getID());
        ManagementProcessor processor = getProcessorByName(processorName);
        
        return processor;
    } catch (Exception e) {
      log.error("Error select procesor: " + e.getMessage(), e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    return null;
  }

  /**
   * Get a processor by the name.
   * @return
   */
  private ManagementProcessor getProcessorByName(String processorName) throws DMException {
    try {
        Object object = Configuration.getConfiguration().getBeanInstanceByName(processorName);
        ManagementProcessor processor = null;
        if (object instanceof ManagementProcessor) {
           processor = (ManagementProcessor)object;
        }
        return processor;
    } catch (Exception e){
      throw new DMException("Error creating the management processor: " + e.getMessage(), e);
    }
  }

  // Implements interface *****************************************************************************
  /* (non-Javadoc)
   * @see sync4j.framework.tools.beans.LazyInitBean#init()
   */
  public void init() throws BeanInitializationException {
    if (this.namePostfix == null) {
       this.namePostfix = "";
    }

    if (this.namePrefix == null) {
       this.namePrefix = "";
    }
  
  }

  /* (non-Javadoc)
   * @see sync4j.framework.server.dm.ProcessorSelector#getProcessor(sync4j.framework.engine.dm.DeviceDMState, sync4j.framework.core.dm.ddf.DevInfo)
   */
  public ManagementProcessor getProcessor(DeviceDMState dms, DevInfo devInfo, ManagementInitialization init) {
    if (log.isDebugEnabled()) {
       log.debug("select a processor for DMState: " + dms);
    }
    ManagementProcessor processor = this.getProcessor4ClientInitiated(dms, devInfo, init);
    if (processor != null) {
       log.debug("select a client initiated processor for DMState: " + dms);
       return processor;
    }
    processor = this.getProcessor4ServerInitiated(dms, devInfo, init);
    log.debug("select a server initiated processor for DMState: " + dms);
    return processor;
  }

  /**
   * Find a name of Processor based on the Job's Type.
   * @param jobs
   */
  public static String getProcessorByJobType(String jobType) {
    String processorName = null;
    if (jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_DISCOVERY)) {
       processorName = TreeDiscoveryProcessor.class.getName();
    } else if (jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_SCRIPT)) {
      processorName = CommandProcessor.class.getName();
    } else if (jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE)) {
      processorName = SmartProfileAssignmentProcessor.class.getName();
    } else if (jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_RE_ASSIGN_PROFILE)) {
      processorName = SmartProfileAssignmentProcessor.class.getName();
    } else if (jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_FIRMWARE)) {
      processorName = FumoProcessor.class.getName();
    } else if (jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL) ||
               jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL_STAGE_2) ||
               jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_SOFTWARE_UN_INSTALL) ||
               jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_SOFTWARE_ACTIVE) ||
               jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_SOFTWARE_DEACTIVE) ||
               jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_SOFTWARE_UPGRADE) ||
               jobType.equalsIgnoreCase(ProvisionJob.JOB_TYPE_SOFTWARE_DISCOVERY)
               ) {
      processorName = SoftwareManagementProcessor.class.getName();
    }
    return processorName;
  }


}
