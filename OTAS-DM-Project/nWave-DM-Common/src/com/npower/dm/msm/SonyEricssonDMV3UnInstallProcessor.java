/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/SonyEricssonDMV3UnInstallProcessor.java,v 1.1 2008/11/10 14:52:59 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/11/10 14:52:59 $
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
package com.npower.dm.msm;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeleteManagementOperation;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.engine.dm.TreeNode;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Software;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.processor.BaseProcessor;
import com.npower.dm.processor.GenericTreeDiscoveryProcessor;
import com.npower.dm.server.engine.EngineConfig;

/**
 * DM Processor for Software Management
 * Adapted for: SonyEricsson DM Client V3.0 platform.
 * <pre>
 * The following command script tested with SonyEricsson K810i:
 * 
<?xml version="1.0" encoding="UTF-8"?>
<Script>
  <Replace> 
    <LeafNode> 
      <Target>./Com.SonyEricsson/Content/JavaApplications/Download/JAD-URL</Target>  
      <Data format="chr">http://www.otas.mobi:8080/download/1225939719984Ow1I.jad</Data> 
    </LeafNode> 
  </Replace>  
  <Exec>
    <Item>
      <Target>./Com.SonyEricsson/Content/JavaApplications/Download</Target>
    </Item>
  </Exec>   
</Script>
 * </pre>
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/11/10 14:52:59 $
 */
public class SonyEricssonDMV3UnInstallProcessor extends BaseProcessor implements ManagementProcessor, Serializable {

  private static transient Log log = LogFactory.getLog(SonyEricssonDMV3UnInstallProcessor.class);
  
  /**
   * 检查Software Download节点是否存在.
   * 例如: 检查./SCM/Download/MSNApp节点是否存在
   * 
   */
  private static final int STEP_Get_Software_Nodes_Information = 100;
  
  /**
   * 执行软件安装任务
   * 
   */
  private static final int STEP_Execute_Installation = 400;

  private static final int STEP_Finished = 1000;
  
  private ManagementProcessor processor4Discovery = null;

  /**
   * 
   */
  public SonyEricssonDMV3UnInstallProcessor() {
    super();
  }
  
  // Private methods ------------------------------------------------------------------------

  /**
   * Return the ProvisionJob which will be processed in this processor.
   * @param factory
   * @return
   * @throws DMException
   */
  private ProvisionJob getProvisionJob(ManagementBeanFactory factory) throws DMException {
    ProvisionJobBean jobBean = factory.createProvisionJobBean();
    ProvisionJob job = jobBean.loadJobByID(this.sessionContext.getDmstate().mssid);
    return job;
  }
  
  /**
   * 返回目标软件
   * @param factory
   * @return
   * @throws DMException
   */
  private Software getTargetSoftware(ManagementBeanFactory factory) throws DMException {
    ProvisionJob currentJob = this.getProvisionJob(factory);
    Software software = currentJob.getTargetSoftware();
    return software;
  }

  /**
   * 生成执行Download并安装的命令
   * @param software
   * @return
   * @throws DMException 
   * @throws ManagementException 
   */
  private ManagementOperation[] getNextOperations4Uninstall(ManagementBeanFactory factory, Software software) throws ManagementException, DMException {
    List<ManagementOperation> operations = new ArrayList<ManagementOperation>();

    {
      DeleteManagementOperation oper1 = new DeleteManagementOperation();
      String targetNode = this.getInstalledNodePath(factory, this.getDevice(factory), this.getTargetSoftware(factory));
      TreeNode treeNode = new TreeNode(targetNode);
      treeNode.setFormat(DDFNode.DDF_FORMAT_NODE);
      oper1.addTreeNode(treeNode);
      operations.add(oper1);
    }

    if (!operations.isEmpty()) {
       // Name detection operations 
       this.appendDummyOperation(operations);
       return (ManagementOperation[]) operations.toArray(new ManagementOperation[0]);
    } else {
      return new ManagementOperation[0];
    }
  }

  // Public methods ------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#beginSession(sync4j.framework.engine.dm.SessionContext)
   */
  @Override
  public void beginSession(SessionContext session) throws ManagementException {
    this.sessionContext = session;

    String        sessionId = session.getSessionId();
    Principal     principal = session.getPrincipal();
    int           type      = session.getType();
    DevInfo       devInfo   = session.getDevInfo();

    if (log.isDebugEnabled()) {
      log.debug("Starting a new DM management session");
      log.debug("sessionId: " + sessionId);
      log.debug("principal: " + principal);
      log.debug("type: " + type);
      log.debug("deviceId: " + devInfo);
    }

    ManagementBeanFactory factory = null;
    try {
        // Load Device bundled with this Session
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
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
        
        // Set next step
        step = STEP_Get_Software_Nodes_Information;
        
        // Get root nodes which will discovery.
        List<String> queuedNodePathsToDiscovery = new ArrayList<String>();
        // Deployed node path
        queuedNodePathsToDiscovery.add("./Com.SonyEricsson/Content/JavaApplications/Installed");
        this.processor4Discovery = new GenericTreeDiscoveryProcessor(queuedNodePathsToDiscovery);
        
        this.processor4Discovery.beginSession(session);
        
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

    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        setJobStatus4EndSession(completionCode);
    } catch (Exception ex) {
      throw new ManagementException("End of DM S60V3InstallProcessor4DL error", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
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
        Software software = getTargetSoftware(factory);
        Device device = this.getDevice(factory);
        switch (step) {
               case STEP_Get_Software_Nodes_Information: {
                 ManagementOperation[] operations = this.processor4Discovery.getNextOperations();
                 if (operations != null && operations.length > 0) {
                    return operations;
                 } else {
                   this.processor4Discovery.endSession(DeviceDMState.STATE_COMPLETED);
                   if (!this.isInstalledAndActived(factory, device, software)) {
                      // 检测到软件未安装, 无需运行后续步骤
                      this.step = STEP_Finished;
                      log.info(software.getExternalId() + " not installed, give up uninstallation process for device: ." + device.getExternalId());
                      return new ManagementOperation[0];
                   }
                 }
               }
               case STEP_Execute_Installation: {
                 this.step = STEP_Execute_Installation;
                 ManagementOperation[] operations = this.getNextOperations4Uninstall(factory, software);
                 if (operations != null && operations.length > 0) {
                    return operations;
                 }
               }
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

  /**
   * 检测软件是否已经安装
   * @param factory
   * @param device
   * @param software
   * @return
   * @throws DMException 
   */
  private boolean isInstalledAndActived(ManagementBeanFactory factory, Device device, Software software) throws DMException {
    SoftwareManagementJobAdapter adapter = new SoftwareManagementJobAdapterImpl(factory);
    List<SoftwareNodeInfo> infos = adapter.getDeployedSoftwares(device.getExternalId());
    Software target = this.getTargetSoftware(factory);
    for (SoftwareNodeInfo info: infos) {
        if (target.getName().equalsIgnoreCase(info.getSoftwareName())
            && target.getVersion().equalsIgnoreCase(info.getSoftwareVersion())
            && target.getVendor().getName().equalsIgnoreCase(info.getVendorName())) {
           return true;
        }
    }
    return false;
  }

  private String getInstalledNodePath(ManagementBeanFactory factory, Device device, Software software) throws DMException {
    SoftwareManagementJobAdapter adapter = new SoftwareManagementJobAdapterImpl(factory);
    List<SoftwareNodeInfo> infos = adapter.getDeployedSoftwares(device.getExternalId());
    Software target = this.getTargetSoftware(factory);
    for (SoftwareNodeInfo info: infos) {
        if (target.getName().equalsIgnoreCase(info.getSoftwareName())
            && target.getVersion().equalsIgnoreCase(info.getSoftwareVersion())
            && target.getVendor().getName().equalsIgnoreCase(info.getVendorName())) {
           return info.getNodePath();
        }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#setOperationResults(sync4j.framework.engine.dm.ManagementOperationResult[])
   */
  @Override
  public void setOperationResults(ManagementOperationResult[] results) throws ManagementException {
    @SuppressWarnings("unused")
    int statusCode = 0; 
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        switch (this.step) {
            case STEP_Get_Software_Nodes_Information: {
                 this.processor4Discovery.setOperationResults(results);
                 break;
            }
            case STEP_Execute_Installation: {
                 this.step = STEP_Finished;
                 for (ManagementOperationResult result: results) {
                     String command = result.getCommand();
                     if (command.equalsIgnoreCase("Exec")) {
                        statusCode = result.getStatusCode();
                     }
                 }
                 // Create stage 2 job to response for CLIENT_INITIALIZED_SESSION
                 //createStage2Job(factory);
                 break;
            }
            default :
                 break;
        } // End of switch
    
    } catch (Exception e) {
      throw new ManagementException("Error in setOperationResults()", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
