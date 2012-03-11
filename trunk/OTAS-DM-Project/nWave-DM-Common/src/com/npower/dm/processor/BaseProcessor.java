/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/BaseProcessor.java,v 1.43 2008/12/12 04:16:09 zhao Exp $
  * $Revision: 1.43 $
  * $Date: 2008/12/12 04:16:09 $
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.Alert;
import sync4j.framework.core.AlertCode;
import sync4j.framework.core.Util;
import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.GetManagementOperation;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.engine.dm.TreeNode;
import sync4j.framework.engine.dm.UserAlertManagementOperation;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.tracking.DMJobLogger;
import com.npower.dm.tracking.DMJobLoggerFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.43 $ $Date: 2008/12/12 04:16:09 $
 */
public abstract class BaseProcessor implements ManagementProcessor, Serializable {
  
  private static transient Log log = LogFactory.getLog(BaseProcessor.class);
  
  //
  // Some phone doesn't recognise the Alert command and a
  // TreeDiscovery session fails because the phone doesn't send
  // the last message.
  // As workaround, we add also a Get operation so the phone sends
  // the last message.
  //
  protected boolean addDummyAlert = false;

  /**
   * Constance
   */
  public static final String MESSAGE_DUMMY_ALERT_OPERATION = "MESSAGE_DUMMY_ALERT_OPERATION";
  
  public static final ResourceBundle resourceBundle =
  ResourceBundle.getBundle("com.npower.dm.processor.Processor", Locale.getDefault());
  
  public static final UserAlertManagementOperation DUMMY_ALERT_OPERATION =
  UserAlertManagementOperation.getDisplay(resourceBundle.getString(MESSAGE_DUMMY_ALERT_OPERATION), 5, 15);

  /**
   * Name of processor.
   */
  private String name = null;

  /**
   * Hold the session informations.
   */
  protected SessionContext sessionContext = null;
  //protected String sessionId      = null;
  //protected Principal principal   = null;
  //protected int type              = 0;
  //protected DevInfo devInfo       = null;
  //protected DeviceDMState dmState = null;
  //protected ManagementInitialization init = null;

  //private transient ManagementBeanFactory beanFactory = null;
  //protected transient Device device = null;
  private long deviceID = 0;

  /**
   * Couter for steps.
   */
  protected int step = 0;
  
  /**
   * 当前Processor所属的JobQueueProcessor.
   * 由于可能采用QueueProcessor调度所有的Job, 此参数用于记录此Processor所属的JobQueueProcessor
   */
  private JobQueueProcessor parentProcessor = null;

  /**
   * 
   */
  protected BaseProcessor() {
    super();
  }

  /**
   * The readObject method is responsible for reading from the stream and restoring the classes fields,
   * and restore the transient fields.
   */
  protected void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    log = LogFactory.getLog(BaseProcessor.class);
    
    in.defaultReadObject();
  }  
  
  // private methods ****************************************************************************************
  
  /**
   * Set the status of job related with the device.
   * @param jobID
   *        Job ID
   * @param deviceID
   *        Device ID
   * @param status
   *        Status code
   * @throws DMException
   */
  protected void setJobStatus(long jobID, long deviceID, String status) throws DMException {
    this.setJobStatus(jobID, deviceID, status, null);
  }
  
  /**
   * Set the status of job related with the device.
   * @param jobID
   *        Job ID
   * @param deviceID
   *        Device ID
   * @param status
   *        Status code
   * @param causeInformation
   *        Information
   * @throws DMException
   */
  protected void setJobStatus(long jobID, long deviceID, String status, String causeInformation) throws DMException {
    if (jobID <= 0) {
       // Job ID must be greater than 0.
       return;
    }
    
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        
        ProvisionJob job = jobBean.loadJobByID(jobID);
        if (job == null) {
           throw new DMException("JobID: " + jobID + " is not exists!");
        }
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByID(Long.toString(deviceID));
        if (device == null) {
           throw new DMException("Could not found a device by ID: " + device);
        }
        ProvisionJobStatus jobStatus = jobBean.getProvisionJobStatus(job, device);
        if (jobStatus == null) {
           throw new DMException("Could not found job status by jobID: " + jobID + " and deviceID: " + device.getID());
        }
        
        factory.beginTransaction();
        // Set status to job
        jobStatus.setState(status);
        jobStatus.setCause(causeInformation);
        jobBean.update(jobStatus);
        if (ProvisionJobStatus.DEVICE_JOB_STATE_DOING.equalsIgnoreCase(status) ||
            ProvisionJobStatus.DEVICE_JOB_STATE_WAITING_CLIENT_INITIALIZED_SESSION.equalsIgnoreCase(status)) {
           // Begin: Set in progress job status to device
           device.setInProgressDeviceProvReq(jobStatus);
        } else {
          // End: Set NULL to in_progress_job_status for device
          device.setInProgressDeviceProvReq(null);
        }
        // 由于Notification的调度算法需要计算subscriber.getNotificationTime() + duration, 
        // 重新设置Notification Time, 防止当一个作业刚刚完成, 又提交一个新作业后, 需较长时间的等待, 才能发起新的Notification.
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        Subscriber subscriber = device.getSubscriber();
        subscriber.setNotificationTime(null);
        subscriberBean.update(subscriber);
        
        deviceBean.update(device);
        factory.commit();

        if (log.isDebugEnabled()) {
           log.debug("update job status: " + status + " jobID: " + jobID + ", deviceID: " + device.getID());
        }

    } catch (Exception ex) {
      factory.rollback();
      throw new DMException("Could not change the job status for processor.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  
  
  // protected methods **************************************************************************************
  /**
   * Returns a list of the operations to get the following nodes:
   * <ui>
   * <li>./DevDetail/Hwv</li>
   * <li>./DevDetail/SwV</li>
   * <li>./DevDetail/FwV</li>
   * <li>./DevDetail/OEM</li>
   * <li>./DevDetail/DevTyp</li>
   * <li>./DevInfo/Lang</li>
   * <li>./DevInfo/DmV</li>
   * <li>./DevInfo/Mod</li>
   * <li>./DevInfo/Man</li>
   * </ui>
   * @return ArrayList
   * @throws DMException 
   */
  protected List<ManagementOperation> getPreconfiguredOperationsForGetDeviceDetail(ManagementBeanFactory factory, long deviceID) throws DMException {
    List<ManagementOperation> operations = new ArrayList<ManagementOperation>();
    List<String> nodesList = new ArrayList<String>();

    nodesList.add("./DevInfo/Man");
    nodesList.add("./DevInfo/Mod");
    nodesList.add("./DevDetail/DevTyp");
    nodesList.add("./DevDetail/OEM");
    nodesList.add("./DevInfo/Lang");
    nodesList.add("./DevDetail/FwV");
    nodesList.add("./DevDetail/SwV");
    nodesList.add("./DevDetail/HwV");
    nodesList.add("./DevInfo/DmV");
    
    // Add Firmware version node
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByID("" + deviceID);
    Model model = device.getModel();
    String firmwareVersionNode = model.getFirmwareVersionNode();
    if (StringUtils.isNotEmpty(firmwareVersionNode)) {
       if (!firmwareVersionNode.startsWith(".")) {
          if (firmwareVersionNode.startsWith("/")) {
             firmwareVersionNode = "." + firmwareVersionNode;
          } else {
            firmwareVersionNode = "./" + firmwareVersionNode;
          }
       }
       boolean exists = false;
       for (String node: nodesList) {
           if (node.equals(firmwareVersionNode)) {
              exists = true;
              break;
           }
       }
       if (!exists) {
          nodesList.add(firmwareVersionNode);
       }
    }
    
    // Generate command list
    int numNodes = nodesList.size();
    GetManagementOperation getOp = null;
    Map<String, TreeNode> nodes = null;
    for (int i=0; i<numNodes; i++) {
        getOp = new GetManagementOperation();
        nodes = new HashMap<String, TreeNode>();
        nodes.put(nodesList.get(i), new TreeNode(nodesList.get(i)));
        getOp.setNodes(nodes);
        operations.add(getOp);
    }

    if (operations == null) {
       operations = new ArrayList<ManagementOperation>();
    }
    if (this.addDummyAlert) {
       int numOperations = operations.size();
       if (numOperations > 0) {
          if (!BaseProcessor.isDummyOperation((ManagementOperation)operations.get(numOperations - 1)) ) {
             operations.add(BaseProcessor.DUMMY_ALERT_OPERATION);
          }
       } else {
         operations.add(BaseProcessor.DUMMY_ALERT_OPERATION);
       }
    }
    return operations;
  }
  
  /**
   * Return Node name
   * @param nodePath
   *        Path of node.
   * @return
   */
  protected static String getNodeNameFromNodePath(String nodePath) {
    if (nodePath == null || nodePath.trim().length() == 0 || nodePath.trim().endsWith("/")) {
       return null;
    }
    if (nodePath.equals("./") || nodePath.equals(".")) {
       return ".";
    }
    int index = nodePath.lastIndexOf("/");
    if (index < 0) {
       return nodePath;
    } else {
      String name = nodePath.substring(index + 1, nodePath.length());
      return name;
    }
  }
  
  /**
   * Return current instance of ManagementBeanFactory
   * @return the factory
   */
  /*
  protected ManagementBeanFactory getManagementBeanFactory() throws ManagementException {
    return this.beanFactory;
  }
  */
  /**
   * Common utility, save the DevInfo into Device tree.
   * @param factory
   * @param devInfo
   * @throws ManagementException
   */
  protected void updateDevInfo(DevInfo devInfo) throws ManagementException {
    assert (devInfo != null):"DevInfo is null";
    
    if (log.isDebugEnabled()) {
       log.debug("Update devInfo, device ID:" + devInfo.getDevId());
    }
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        DeviceBean deviceBean = factory.createDeviceBean();
        
        Device device = deviceBean.getDeviceByExternalID(devInfo.getDevId());
        if (device == null) {
           throw new ManagementException("Error in update devInfo, could not found device by ID: " + devInfo.getDevId());
        }
        // Save into DM inventory.
        factory.beginTransaction();
        deviceBean.updateDeviceTreeNode("" + device.getID(), "./DevInfo/Man", devInfo.getMan());
        deviceBean.updateDeviceTreeNode("" + device.getID(), "./DevInfo/Mod", devInfo.getMod());
        deviceBean.updateDeviceTreeNode("" + device.getID(), "./DevInfo/Lang", devInfo.getLang());
        deviceBean.updateDeviceTreeNode("" + device.getID(), "./DevInfo/DmV", devInfo.getFwV());
        deviceBean.updateDeviceTreeNode("" + device.getID(), "./DevInfo/DevId", devInfo.getDevId());
        factory.commit();
    } catch (Exception ex) {
      factory.rollback();
      throw new ManagementException("Could not save the devInfo into DM inventory.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * Log results.
   * @param results
   */
  protected void logResult(ManagementOperationResult[] results) {
    log.info("Processor got the result responsed from device ...");
    for (ManagementOperationResult result: results) {
        log.info("Command: " + result.getCommand() + ", status: " + result.getStatusCode());
        Map<String, Object> nodes = result.getNodes();
        if (nodes == null) {
           continue;
        }
        for (String node: nodes.keySet()) {
            log.info("\tNode Path: " + node);
        }
    }
  }

  /**
   * Common utility, save the result of DM session into DM inventory.
   * Checking the status code and DM commands, Update will be occurenced 
   * only the status is 200 and Commond is one of Add, Replace, Delete, Get.
   *  
   * @param factory
   * @param dmState
   * @param results
   * @throws ManagementException
   */
  protected void updateResults(ManagementBeanFactory factory, long deviceID, ManagementOperationResult[] results) throws ManagementException {
    try {
        DeviceBean deviceBean = factory.createDeviceBean();
        for (int i = 0; results != null && i < results.length; i++ ) {
            ManagementOperationResult result = results[i];
            String command = result.getCommand();
            int status = result.getStatusCode();
            if (log.isDebugEnabled()) {
               log.debug("operation result:" + command + ":" + status);
            }
            if (status == 200) {
               // Status code: 200 --- Success
               if (command.equalsIgnoreCase("GET") || command.equalsIgnoreCase("ADD") || command.equalsIgnoreCase("REPLACE")) {
                  // Sychronize the DeviceTree with the current operation
                  Map<String, Object> nodes = result.getNodes();
                  if (!nodes.isEmpty()) {
                     for (String nodePath: nodes.keySet()) {
                         Object node = nodes.get(nodePath);
                         Object nodeValue = node;
                         // Save into DM inventory.
                         deviceBean.updateDeviceTreeNode("" + deviceID, nodePath, nodeValue);
                         if (log.isDebugEnabled()) {
                            log.debug("update result:" + command + ":" + status + ":" + nodePath + ":" + nodeValue);
                         }
                     }
                  }
               } else if (command.equalsIgnoreCase("DELETE")) {
                 // Sychronize the DeviceTree with the current operation
                 Map<String, ?> nodes = result.getNodes();
                 if (!nodes.isEmpty()) {
                    Set<String> keySet = nodes.keySet();
                    for (Iterator<String> keyNames = keySet.iterator(); keyNames.hasNext(); ) {
                        String nodePath = (String)keyNames.next();
                        DeviceTreeNode node = deviceBean.findDeviceTreeNode(deviceID, nodePath);
                        deviceBean.delete(node);
                        if (log.isDebugEnabled()) {
                           log.debug("update result:" + command + ":" + status + ":" + nodePath);
                        }
                    }
                 }
               }
            }
        }
    } catch (DMException ex) {
      throw new ManagementException("Could not save the results into DM inventory.", ex);
    }
  }

  /**
   * @param deviceExternalID
   * @throws DMException
   */
  protected Device loadDeviceByExternalID(ManagementBeanFactory factory, String deviceExternalID) throws DMException {
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
    if (log.isDebugEnabled()) {
       log.debug("device(" + device.getID() + ") loaded by externalID: " + deviceExternalID);
    }
    return device;
  }
  
  /**
   * Set job status for beginSession(). If job ID less than 1, will do nothing.
   * @throws DMException
   */
  protected void setJobStatus4BeginSession() throws DMException {
    // Set the job status to Doing
    long jobID = new Long(this.sessionContext.getDmstate().mssid).longValue();
    if (jobID <= 0) {
       // Job ID must be greater than 0.
       return;
    }
    this.setJobStatus(jobID, 
                       this.getDeviceID(), ProvisionJobStatus.DEVICE_JOB_STATE_DOING);
  }

  /**
   * Set the jobStatus for endSession process. If jobID less than 1, will do nothing.
   * @param completionCode
   * @throws DMException
   */
  protected void setJobStatus4EndSession(int completionCode) throws DMException {
    long jobID = new Long(this.sessionContext.getDmstate().mssid).longValue();
    if (jobID <= 0) {
       return;
    }
    switch (completionCode) {
       case DeviceDMState.STATE_COMPLETED:
            this.setJobStatus(jobID, this.getDeviceID(), ProvisionJobStatus.DEVICE_JOB_STATE_DONE);
            break;
       case DeviceDMState.STATE_ABORTED:
            this.setJobStatus(jobID, this.getDeviceID(), ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED);
            break;
       case DeviceDMState.STATE_ERROR:
            this.setJobStatus(jobID, this.getDeviceID(), ProvisionJobStatus.DEVICE_JOB_STATE_ERROR);
            break;
       case DeviceDMState.STATE_UNKNOWN:
            this.setJobStatus(jobID, this.getDeviceID(), ProvisionJobStatus.DEVICE_JOB_STATE_UNKNOWN);
            break;
       case DeviceDMState.WAITING_CLIENT_INITIALIZED_SESSION:
            this.setJobStatus(jobID, this.getDeviceID(), ProvisionJobStatus.DEVICE_JOB_STATE_WAITING_CLIENT_INITIALIZED_SESSION);
            break;
       default: break;
             
    }
  }

  /**
   * Checks if the given operation is the Dummy Alert Operation
   * @param operation ManagementOperation
   * @return true if the given operation is the Dummy Alert Operation,
   *         false otherwise
   */
  protected static boolean isDummyOperation(ManagementOperation operation) {
      if (operation == null) {
          return false;
      }
      if (!(operation instanceof UserAlertManagementOperation)) {
          return false;
      }
      UserAlertManagementOperation alertOperation =
          (UserAlertManagementOperation)operation;
  
      if (alertOperation.getAlertCode() != AlertCode.DISPLAY)  {
          return false;
      }
  
      String[] alerts = alertOperation.getAlerts();
      if (alerts == null || alerts.length != 1) {
          return false;
      }
      if (alerts[0] == null ||
          !(resourceBundle.getString(MESSAGE_DUMMY_ALERT_OPERATION).equals(alerts[0]))) {
          return false;
      }
  
      return true;
  }
  
  
  /**
   * Submit a Discovery Job into Job Queue.
   * @param nodePath
   * @throws DMException
   * @throws ManagementException
   */
  protected void submitDiscoveryJob(long parentJobID, String[] nodePath) throws DMException, ManagementException {
    if (nodePath == null || nodePath.length == 0) {
       return;
    }
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        
        ProvisionJob parentJob = jobBean.loadJobByID(parentJobID);
        
        factory.beginTransaction();
        DeviceGroup group = deviceBean.newDeviceGroup();
        Device device = this.getDevice(factory);
        deviceBean.add(group, device);
        
        deviceBean.update(group);
        ProvisionJob job = jobBean.newJob4Discovery(group, nodePath);
        job.setRequiredNotification(false);
        if (parentJob != null) {
           job.setParent(parentJob);
        }
        jobBean.update(job);
  
        factory.commit();
    } catch (Exception e) {
      if (factory != null) {
         factory.rollback();
      }
      throw new ManagementException("Failure in submit a discovery job.", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * Append a dummy operation for this operations.
   * <br>
   * Dummy alert operation
   * Some phone doesn't recognise the Alert command and a
   * session fails because the phone doesn't send
   * the last message.
   * As workaround, we add also a Get operation so the phone sends
   * the last message.
   * 
   * @param opers
   */
  protected void appendDummyOperation(List<ManagementOperation> opers) {
    if (this.addDummyAlert) {
       int numOperations = opers.size();
       if (numOperations > 0) {
          if (!BaseProcessor.isDummyOperation((ManagementOperation)opers.get(numOperations - 1)) ) {
             opers.add(BaseProcessor.DUMMY_ALERT_OPERATION);
          }
       } else {
         opers.add(BaseProcessor.DUMMY_ALERT_OPERATION);
       }
    }
  }
  
  protected void dump(Log log, ManagementOperation[] operations) {
    for (int i = 0; operations != null && i < operations.length; i++ ) {
        log.debug(operations[i]);
    }
  }
  
  /**
   * @param sessionContext
   */
  protected void trackJobLog(SessionContext sessionContext) {
    try {
      DMJobLogger jobLogger = DMJobLoggerFactory.newInstance().getLogger();
      String deviceExtID = sessionContext.getDmstate().deviceId;
      long jobID = 0;
      if (StringUtils.isNotEmpty(sessionContext.getDmstate().mssid)) {
         jobID = Long.parseLong(sessionContext.getDmstate().mssid);
      }
      Date startTime = sessionContext.getDmstate().start;
      Date endTime = sessionContext.getDmstate().end;
      String dmSessionID = sessionContext.getHttpSessionId();
      String sessionStatus = Character.toString((char)sessionContext.getDmstate().state);
      jobLogger.log(dmSessionID, jobID, deviceExtID, this, startTime, endTime, sessionStatus);
    } catch (NumberFormatException e) {
      log.error("Failure to tracking job log.", e);
    } catch (Exception e) {
      log.error("Failure to tracking job log.", e);
    }
  }

  /**
   * @return the deviceID
   */
  protected long getDeviceID() {
    return deviceID;
  }

  /**
   * @param deviceID the deviceID to set
   */
  protected void setDeviceID(long deviceID) {
    this.deviceID = deviceID;
  }

  /**
   * @return the device
   * @throws ManagementException 
   * @throws DMException 
   */
  protected Device getDevice(ManagementBeanFactory factory) throws ManagementException, DMException {
    DeviceBean deviceBean = factory.createDeviceBean();
    Device device = deviceBean.getDeviceByID(Long.toString(this.getDeviceID()));
    return device;
  }


  // Public methods *****************************************************************************************
  
  /**
   * Sets management processor name
   */
  public void setName(String name) {
      this.name = name;
  }

  
  // ManagementProcessor interface ***************************************************************
  /*
  private boolean initialized = false;
  protected void initialize(String sessionId, Principal principal, int type, DevInfo devInfo, DeviceDMState dmState, ManagementInitialization init) {
    if (this.initialized) {
       return;
    }
    try {
        this.beanFactory = ManagementBeanFactory.newInstance();
        this.initialized = true;
    } catch (DMException e) {
      log.error("Could not initialize a ManagementBeanFactory for Processor", e);
    }
  }
  */
  /**
   * Called when a management session is started for the given principal. 
   * sessionId is the content of the SessionID element of the OTA DM message.
   * 
   * This method maybe call more than once before call endSession().
   *
   * @param sessionId the content of the SessionID element of the OTA DM message
   * @param principal the principal who started the management session
   * @param type the management session type (as specified in the message Alert)
   * @param devInfo device info of the device under management
   * @param dmstate device management state
   * @param init Initialization package of the SyncML DM protocol
   *
   * @throws ManagementException in case of errors
   * 
   * @see sync4j.framework.engine.dm.ManagementProcessor#beginSession(sync4j.framework.engine.dm.SessionContext)
   */
  public abstract void beginSession(SessionContext session)
      throws ManagementException;
  
  /**
   * Called when the management session is closed. CompletionCode can be one 
   * of:
   * <ul>
   *  <li>DM_SESSION_SUCCESS</li>
   *  <li>DM_SESSION_ABORT</li>
   *  <li>DM_SESSION_FAILED</li>
   *
   * CompletionCode defined by DeviceDMState.STATE_XXXX
   * 
   * @param completionCode the management session competion code
   *
   * @throws ManagementException in case of errors
   * 
   * @see sync4j.framework.engine.dm.ManagementProcessor#endSession(int)
   */
  public abstract void endSession(int completionCode) throws ManagementException;

  /**
   * Called to retrieve the next management operations to be performed.
   *
   * @returns an array of ManagementOperation representing the management
   *          operations to be performed.
   *
   * @throws ManagementException in case of errors
   * 
   * @see sync4j.framework.engine.dm.ManagementProcessor#getNextOperations()
   */
  public abstract ManagementOperation[] getNextOperations() throws ManagementException;
  
  /**
   * Called to set the results of a set of management operations. 
   *
   * @param results the results of a set of management operations. 
   *
   * @throws ManagementException in case of errors
   * 
   * @see sync4j.framework.engine.dm.ManagementProcessor#setOperationResults(sync4j.framework.engine.dm.ManagementOperationResult[])
   */
  public abstract void setOperationResults(ManagementOperationResult[] results) throws ManagementException;
  
  /**
   * Name to uniquely identify the management processor instance (each 
   * installed management processor should have a different name in its 
   * configuration file).
   *
   * @return the management processor name
   * 
   * @see sync4j.framework.engine.dm.ManagementProcessor#getName()
   */
  public String getName() {
    return this.name;
  }

  /* (non-Javadoc)
   * @see sync4j.framework.engine.dm.ManagementProcessor#setGenericAlert(sync4j.framework.core.Alert[])
   */
  public void setGenericAlert(Alert[] genericAlerts) throws ManagementException {
    int numAlerts = genericAlerts.length;
    log.info("SetGenericAlerts (num. alerts: " + numAlerts + ")");

    for (int i=0; i<numAlerts; i++) {
        log.info("Alert[" + i + "]: " + Util.toXML(genericAlerts[i]));
    }
  }

  // Private methods ****************************************************************************************
  /** 
   * This method used to detected weather if ManagementBeanfactory has been released.
   * 
   * @see java.lang.Object#finalize()
   */
  protected void finalize() throws Throwable {
    //log.debug("Finalize Processor: " + this.getName());
  }

  /**
   * @return the addDummyAlert
   */
  public boolean isAddDummyAlert() {
    return addDummyAlert;
  }

  /**
   * @param addDummyAlert the addDummyAlert to set
   */
  public void setAddDummyAlert(boolean addDummyAlert) {
    this.addDummyAlert = addDummyAlert;
  }

  /**
   * @return Returns the parentProcessor.
   */
  public JobQueueProcessor getParentProcessor() {
    return parentProcessor;
  }

  /**
   * @param parentProcessor The parentProcessor to set.
   */
  public void setParentProcessor(JobQueueProcessor parentProcessor) {
    this.parentProcessor = parentProcessor;
  }

  
}
