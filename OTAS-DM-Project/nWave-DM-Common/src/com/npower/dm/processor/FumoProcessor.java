/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/FumoProcessor.java,v 1.19 2008/08/04 04:25:28 zhao Exp $
  * $Revision: 1.19 $
  * $Date: 2008/08/04 04:25:28 $
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.config.Configuration;
import sync4j.framework.config.ConfigurationConstants;
import sync4j.framework.config.ConfigurationException;
import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.AddManagementOperation;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ExecManagementOperation;
import sync4j.framework.engine.dm.GetManagementOperation;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.ReplaceManagementOperation;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.engine.dm.TreeNode;

import com.npower.dl.DownloadURLCaculatorImpl;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Image;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Update;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * Step#1 Confirm Device image version
 * Step#2 Replace UpdatePkgData
 * Step#3 Exec 'Update'
 * Step#4 Finished
 * @author Zhao DongLu
 * @version $Revision: 1.19 $ $Date: 2008/08/04 04:25:28 $
 */
public class FumoProcessor extends BaseProcessor implements ManagementProcessor, Serializable {

  private static transient Log        log                = LogFactory.getLog(FumoProcessor.class);
  
  private static String DEFAULT_FUMO_ROOT_NODE = "./FUMO/OTASUPD";
  
  private static final int STEP_Get_Device_Details = 100;
  private static final int STEP_Detect_FUMO_Root_Node = 200;
  private static final int STEP_Create_FUMO_Root_Node = 300;
  private static final int STEP_Modify_Update_Node = 400;
  private static final int STEP_Exec_Update_Node = 500;
  private static final int STEP_Finished = 600;
  /**
   * 
   */
  public FumoProcessor() {
    super();
  }
  
  // private methods -----------------------------------------------------------------------------
  
  /**
   * Return Download Server URI: http(s)://server:port
   * @return
   * @throws ConfigurationException
   */
  private String getDownloadServerURI() throws ConfigurationException {
    String serverDownlaodURI = Configuration.getConfiguration().getStringValue(ConfigurationConstants.CFG_SERVER_DOWNLOAD_URI);
    log.info("FUMO Download URL, config.download.server.uri: " + serverDownlaodURI);

    return serverDownlaodURI;
  }

  /**
   * Return URI for download descriptor.
   * @param update
   * @return
   * @throws ConfigurationException
   */
  private String getDownloadDescriptorURI(Update update) throws ConfigurationException, DMException {
    String dlServerURI = getDownloadServerURI();
    DownloadURLCaculatorImpl caculator = new DownloadURLCaculatorImpl();
    caculator.setDownloadServerURL(dlServerURI);
    String pkgURL = caculator.getDownloadDescriptorURL(Long.toString(update.getID()));

    log.info("FUMO Download URL: package URL: " + pkgURL);
    return pkgURL;
  }

  /**
   * Return the FUMO Update node path.
   * <pre>
   * For example:
   * "/.../.../Update"
   * </pre>
   * @param factory
   * @param device
   * @return
   * @throws DMException
   */
  private String getFumoUpdateNodePath(ManagementBeanFactory factory, Device device) throws DMException {
    Model model = device.getModel();
    String nodePath = model.getFirmwareUpdateNode();
    if (StringUtils.isEmpty(nodePath)) {
       nodePath = DEFAULT_FUMO_ROOT_NODE + "/Update";
    }
    return nodePath;
  }
  
  /**
   * Return the FUMO PkgData node path.
   * <pre>
   * For example:
   * "/.../.../Update/PkgData"
   * </pre>
   * @param factory
   * @param device
   * @return
   * @throws DMException
   */
  private String getFumoPkgDataNodePath(ManagementBeanFactory factory, Device device) throws DMException {
    String path = this.getFumoUpdateNodePath(factory, device);
    if (!path.endsWith("/")) {
       path = path + "/";
    }
    return path + "PkgData";
  }
  
  /**
   * Return the FUMO Download and Update node path.
   * <pre>
   * For example:
   * "/.../.../DownloadAndUpdate"
   * </pre>
   * @param factory
   * @param device
   * @return
   * @throws DMException
   */
  private String getFumoDownloadAndUpdateNodePath(ManagementBeanFactory factory, Device device) throws DMException {
    Model model = device.getModel();
    String nodePath = model.getFirmwareDownloadAndUpdateNode();
    if (StringUtils.isEmpty(nodePath)) {
       nodePath = DEFAULT_FUMO_ROOT_NODE + "/DownloadAndUpdate";
    }
    return nodePath;
  }
  
  /**
   * Return the node of PkgURL in OMA Download mode.
   * <pre>
   * For example:
   * "/.../.../DownloadAndUpdate/PkgURL"
   * </pre>
   * @param factory
   * @param device
   * @return
   */
  private String getFumoPkgUrlNodePath(ManagementBeanFactory factory, Device device) throws DMException {
    String path = this.getFumoDownloadAndUpdateNodePath(factory, device);
    if (!path.endsWith("/")) {
       path = path + "/";
    }
    return path + "PkgURL";
  }
  
  /**
   * Guess or caculate Fumo Root Node.
   * @param factory
   * @param device
   * @return
   * @throws DMException
   */
  private String getFumoRootNode(ManagementBeanFactory factory, Device device) throws DMException {
    String downloadNode = this.getFumoDownloadAndUpdateNodePath(factory, device);
    String rootNode = downloadNode.substring(0, downloadNode.length() - "/DownloadAndUpdate".length());
    return rootNode;
  }

  /**
   * @return
   * @throws ManagementException 
   */
  private ManagementOperation[] step4ReplaceUpdateData(ManagementBeanFactory factory) throws ManagementException {
    try {
        ProvisionJob job = this.getCurrentJob(factory);
        Image targetImage = job.getTargetImage();
        Device device = this.getDevice(factory);
        Update update = device.getUpdate();
        log.info("Firmware update, job target version: " + targetImage.getVersionId());
        if (targetImage.getID() != update.getToImage().getID()) {
           throw new ManagementException("Device target image mis-matched with Job's target image.");
        }
        DeviceBean deviceBean = factory.createDeviceBean();
        
        String currentVersion = deviceBean.getCurrentFirmwareVersionId(device.getID());    
        
        log.info("Device's current version: " + currentVersion);
        if (StringUtils.isEmpty(currentVersion) || !update.getFromImage().getVersionId().equals(currentVersion)) {
           throw new ManagementException("Device source version mis-matched with the update source version specified by the job: " + job.getID());
        }
        
        // Matched!!! continue ...
        ReplaceManagementOperation replaceOperation = new ReplaceManagementOperation();
        if (device.getModel().getSupportedDownloadMethods()) {
           // FUMO DL mode
           TreeNode updateNode = new TreeNode();
           // Get Download Node
           String nodeName = this.getFumoPkgUrlNodePath(factory, device);
           updateNode.setName(nodeName);
           // Get Download URL for OMA Download Descriptor
           String pkgURL = getDownloadDescriptorURI(update);
           updateNode.setValue(pkgURL);
           replaceOperation.addTreeNode(updateNode);
           log.info("FUMO DL mode, replace " + nodeName + ": " + pkgURL);
        } else {
          // FUMO DM mode
          byte[] updateBytes = update.getBytes();
          TreeNode updateNode = new TreeNode();
          updateNode.setType(TreeNode.FORMAT_BINARY);
          // Get Download Node
          String nodeName = this.getFumoPkgDataNodePath(factory, device);
          updateNode.setName(nodeName);
          // Get Firmware data into data node
          updateNode.setValue(updateBytes);
          replaceOperation.addTreeNode(updateNode);
          log.info("FUMO DM mode, replace " + updateNode.getName() + " with " + updateBytes.length + " bytes");
        }
        
        return new ManagementOperation[]{replaceOperation};
    } catch (DMException e) {
      throw new ManagementException("Error in getNextOperations() of " + FumoProcessor.class.getName());
    }
  }

  /**
   * @return
   * @throws ManagementException 
   */
  private ManagementOperation[] step4ExecUpdate(ManagementBeanFactory factory) throws ManagementException {
    try {
        Device device = this.getDevice(factory);
        ExecManagementOperation execOperation = new ExecManagementOperation();
        TreeNode node = new TreeNode();
        if (device.getModel().getSupportedDownloadMethods()) {
           // FUMO DL mode
           node.setName(this.getFumoDownloadAndUpdateNodePath(factory, device));
           log.info("FUMO DM mode, Exec Update: " + node.getName());
        } else {
          // FUMO DM mode
          node.setName(this.getFumoUpdateNodePath(factory, device));
          log.info("FUMO DL mode, Exec Update: " + node.getName());
        }
        node.setFormat(DDFNode.DDF_FORMAT_NODE);
        node.getType();
        execOperation.addTreeNode(node);
        
        return new ManagementOperation[]{execOperation};
    } catch (Exception e) {
      throw new ManagementException("Error in getNextOperations() of " + FumoProcessor.class.getName());
    }
  }

  /**
   * Check and return the status code of Replace command.
   * @param results
   * @return
   */
  private int checkReplaceCommand(ManagementOperationResult[] results) {
    for (ManagementOperationResult result: results) {
        String command = result.getCommand();
        if (command.equalsIgnoreCase("Replace")) {
           int statusCode = result.getStatusCode();
           return statusCode;
        }
    }
    return 0;
  }

  /**
   * Check and return the status code of Exec command.
   * @param results
   * @return
   */
  private int checkExecCommand(ManagementOperationResult[] results) {
    for (ManagementOperationResult result: results) {
        String command = result.getCommand();
        if (command.equalsIgnoreCase("Exec")) {
           int statusCode = result.getStatusCode();
           return statusCode;
        }
    }
    return 0;
  }

  /**
   * @param factory
   * @return
   * @throws DMException
   */
  private ProvisionJob getCurrentJob(ManagementBeanFactory factory) throws DMException {
    ProvisionJobBean jobBean = factory.createProvisionJobBean();
    ProvisionJob job = jobBean.loadJobByID(this.sessionContext.getDmstate().mssid);
    if (job == null) {
      throw new DMException("Could not found the jobID: " + this.sessionContext.getDmstate().mssid);
    }
    if (!job.getJobType().equalsIgnoreCase(ProvisionJob.JOB_TYPE_FIRMWARE)) {
      throw new DMException("The job's type wrong, must assign a FirmwareUpdate job to this processor. jobID: "
          + this.sessionContext.getDmstate().mssid);
    }
    return job;
  }
  
  // Public methods --------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#beginSession(sync4j.framework.engine.dm.SessionContext)
   */
  @Override
  public void beginSession(SessionContext context) throws ManagementException {
    this.sessionContext = context;

    String        sessionId = context.getSessionId();
    Principal     principal         = context.getPrincipal();
    int           type      = context.getType();
    DevInfo       devInfo   = context.getDevInfo();

    if (log.isDebugEnabled()) {
      log.debug("Starting a new DM management session");
      log.debug("sessionId: " + sessionId);
      log.debug("principal: " + principal);
      log.debug("type: " + type);
      log.debug("deviceId: " + devInfo);
    }

    step = STEP_Get_Device_Details;
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        // Load Device boundled with this Session
        String deviceExternalID = this.sessionContext.getDmstate().deviceId;
        Device device = loadDeviceByExternalID(factory, deviceExternalID);
        if (device == null) {
          throw new ManagementException("Could not load device: from DM inventory.");
        }
        this.setDeviceID(device.getID());
  
        // Set the job status
        super.setJobStatus4BeginSession();
  
        // Update DevInfo
        super.updateDevInfo(devInfo);
  
    } catch (Exception ex) {
      throw new ManagementException("Error in beginSession: ", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#endSession(int)
   */
  @Override
  public void endSession(int completionCode) throws ManagementException {
    if (log.isDebugEnabled()) {
      log.debug("End a DM management session with sessionId: " + sessionContext.getSessionId());
    }

    try {
        // update the jobStatus for end session
        if (completionCode == DeviceDMState.STATE_COMPLETED) {
          // Waiting Client Initialized Session, client report the state of firmware update
          completionCode = DeviceDMState.WAITING_CLIENT_INITIALIZED_SESSION;
        }
        setJobStatus4EndSession(completionCode);
    } catch (Exception ex) {
      throw new ManagementException("End of DM " + FumoProcessor.class.getName() + " error", ex);
    } finally {
      // Tracking Job
      this.trackJobLog(this.sessionContext);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#getNextOperations()
   */
  @Override
  public ManagementOperation[] getNextOperations() throws ManagementException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        switch (step) {
               case STEP_Get_Device_Details: 
                     // Get dev details
                     List<ManagementOperation> list = getPreconfiguredOperationsForGetDeviceDetail(factory, this.getDeviceID());
                     ManagementOperation[] operations = (ManagementOperation[])list.toArray(new ManagementOperation[0]);
                     return operations;
               case STEP_Detect_FUMO_Root_Node: {
                     String fumoRootNode = this.getFumoRootNode(factory, this.getDevice(factory));
                     Map<String, TreeNode> nodes = new HashMap<String, TreeNode>();
                     nodes.put(fumoRootNode, new TreeNode(fumoRootNode));
                     GetManagementOperation getOp = new GetManagementOperation();
                     getOp.setNodes(nodes);
                     
                     List<ManagementOperation> opers = new ArrayList<ManagementOperation>();
                     opers.add(getOp);
                     operations = (ManagementOperation[])opers.toArray(new ManagementOperation[0]);
                     return operations;
               }
               case STEP_Create_FUMO_Root_Node: {
                    String fumoRootNode = this.getFumoRootNode(factory, this.getDevice(factory));
                    TreeNode tNode = new TreeNode(fumoRootNode, "");
                    tNode.setFormat(DDFNode.DDF_FORMAT_NODE);
                    AddManagementOperation addOp = new AddManagementOperation();
                    addOp.addTreeNode(tNode);
                    
                    List<ManagementOperation> opers = new ArrayList<ManagementOperation>();
                    opers.add(addOp);
                    operations = (ManagementOperation[])opers.toArray(new ManagementOperation[0]);
                    return operations;
               }
               case STEP_Modify_Update_Node:
                     // Replace Update data
                     return this.step4ReplaceUpdateData(factory);
               case STEP_Exec_Update_Node:
                     // Exec Update node
                     return this.step4ExecUpdate(factory);
               case STEP_Finished:
               default:
                     // End
                     return new ManagementOperation[0];
        }
    } catch (Exception e) {
      throw new ManagementException("Error in getNextOperation()", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }


  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#setOperationResults(sync4j.framework.engine.dm.ManagementOperationResult[])
   */
  @Override
  public void setOperationResults(ManagementOperationResult[] results) throws ManagementException {
    if (log.isDebugEnabled()) {
        log.debug("update results for id: " + sessionContext.getDmstate().id);
    }
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        int statusCode = 0; 
        switch (step) {
        case STEP_Get_Device_Details: 
               // Response for Get dev details
               if (results != null && results.length > 0) {
                  factory.beginTransaction();
                  this.updateResults(factory, this.getDeviceID(), results);
                  factory.commit();
               }
               // Set next step
               step = STEP_Detect_FUMO_Root_Node;
               break;
        case STEP_Detect_FUMO_Root_Node:
               // Check Fumo Root Node
               for (ManagementOperationResult result: results) {
                   String command = result.getCommand();
                   if (command.equalsIgnoreCase("Get")) {
                      statusCode = result.getStatusCode();
                      break;
                   }
               }
               if (statusCode == 404) {
                  // Not found Fumo Root node, create it.
                  step = STEP_Create_FUMO_Root_Node;
               } else {
                 // Found Fumo root node, goto step: update.
                 step = STEP_Modify_Update_Node;
               }
               break;
        case STEP_Create_FUMO_Root_Node:
               step = STEP_Modify_Update_Node;
               break;
        case STEP_Modify_Update_Node:
               // Set next step
               step = STEP_Exec_Update_Node;

               // Response for Replace Update data
               this.logResult(results);
               
               statusCode = this.checkReplaceCommand(results);
               log.info("Replace PkgData status code: " + statusCode);
               if (statusCode != 200) {
                  throw new ManagementException("Firmware update error, Replace commnad status code is " + statusCode);
               }
               break;
        case STEP_Exec_Update_Node:
               // Set next step
               step = STEP_Finished;
     
               // Response for Exec Update
               this.logResult(results);
               
               statusCode = this.checkExecCommand(results);
               log.info("Exec Command status code: " + statusCode);
               if (statusCode != 200) {
                  throw new ManagementException("Firmware update error, Exec commnad status code is " + statusCode);
               }
               break;
        case STEP_Finished:
        default:
               // Response
               step = STEP_Finished;
               this.logResult(results);
        }
    } catch(Exception ex) {
      factory.rollback();
      throw new ManagementException("Error in setOperationResults(...)", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
