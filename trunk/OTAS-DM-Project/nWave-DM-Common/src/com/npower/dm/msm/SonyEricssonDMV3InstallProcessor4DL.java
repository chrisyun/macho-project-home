/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/SonyEricssonDMV3InstallProcessor4DL.java,v 1.2 2009/02/26 10:38:06 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2009/02/26 10:38:06 $
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

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.config.ConfigurationConstants;
import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ExecManagementOperation;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.ReplaceManagementOperation;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.engine.dm.TreeManagementOperation;
import sync4j.framework.engine.dm.TreeNode;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Software;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.processor.BaseProcessor;
import com.npower.dm.processor.GenericTreeDiscoveryProcessor;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.util.MessageResources;
import com.npower.sms.SmsException;

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
 * @version $Revision: 1.2 $ $Date: 2009/02/26 10:38:06 $
 */
public class SonyEricssonDMV3InstallProcessor4DL extends BaseProcessor implements ManagementProcessor, Serializable {

  private static transient Log log = LogFactory.getLog(SonyEricssonDMV3InstallProcessor4DL.class);
  
  /**
   * 检查Software Download节点是否存在.
   * 例如: 检查./SCM/Download/MSNApp节点是否存在
   * 
   */
  private static final int STEP_Get_Software_Nodes_Information = 100;
  
  /**
   * 设置软件安装节点的各个参数.
   * 例如:
   *   设置download URL和installation options
   */
  private static final int STEP_Setup_Software_Node_Attributes = 300;
  
  /**
   * 执行软件安装任务
   * 
   */
  private static final int STEP_Execute_Installation = 400;

  private static final int STEP_Finished = 1000;
  
  private ManagementProcessor processor4Discovery = null;

  /**
   * Flag
   * 表示软件是否已经被安装, 无需继续安装
   */
  private boolean deployed  = false;
  
  /**
   * 
   */
  public SonyEricssonDMV3InstallProcessor4DL() {
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
   * 计算软件下载的URL
   * @param software
   * @return
   */
  private String getSoftwareDownloadURI(ManagementBeanFactory factory, Software software) throws DMException, ManagementException {
    //String downloadURI = "http://www.otas.mobi:8080/download/msn_mobile_3.5_for_nokia_6120_544.sisx";
    SoftwareBean bean = factory.createSoftwareBean();
    Device device = this.getDevice(factory);
    Model model = device.getModel();
    String serverDownlaodURIPattern = BaseS60V3Processor.getDownloadServerProperties().getProperty(ConfigurationConstants.CFG_SERVER_SOFTWARE_DOWNLOAD_URI);
    String url = bean.getSoftwareDownloadURL(software, model, serverDownlaodURIPattern);
    log.info("Software Download URL: " + url);
    return url;
  }


  /**
   * 生成创建Software安装节点的DM命令
   * @param softwareExternalID
   * @return
   */
  private ManagementOperation[] getNextOperations4SetupDownloadNode(ManagementBeanFactory factory, Software software) throws DMException, ManagementException {
    List<ManagementOperation> operations = new ArrayList<ManagementOperation>();
    {
      TreeManagementOperation oper4 = new ReplaceManagementOperation();
      String uri = this.getSoftwareDownloadURI(factory, software);
      // SonyEricsson URI必须提供JAD下载, 提供JAR无效!
      // 由于目前SoftwareDownloadServlet支持自动为jar软件包生成jad.
      uri = StringUtils.replace(uri, ".jar", ".jad");
      
      TreeNode treeNode = new TreeNode("./Com.SonyEricsson/Content/JavaApplications/Download/JAD-URL", uri);
      treeNode.setFormat(DDFNode.DDF_FORMAT_CHR);
      oper4.addTreeNode(treeNode);
      operations.add(oper4);
    }
    
    if (!operations.isEmpty()) {
       // Name detection operations 
       this.appendDummyOperation(operations);
       return (ManagementOperation[]) operations.toArray(new ManagementOperation[0]);
    } else {
      return new ManagementOperation[0];
    }
  }

  /**
   * 生成执行Download并安装的命令
   * @param software
   * @return
   */
  private ManagementOperation[] getNextOperations4ExecuteDownloadNode(ManagementBeanFactory factory, Software software) {
    List<ManagementOperation> operations = new ArrayList<ManagementOperation>();

    {
      ExecManagementOperation oper1 = new ExecManagementOperation();
      TreeNode treeNode = new TreeNode("./Com.SonyEricsson/Content/JavaApplications/Download");
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

  /**
   * 软件已经存在, 发送短信息.
   * @param device
   * @param software
   * @throws Exception
   * @throws SmsException
   * @throws IOException
   */
  private void sendExistsMessage(Device device, Software software) throws Exception, SmsException, IOException {
    MessageResources resource = BaseS60V3Processor.getResources();
    String text = resource.getMessage(BaseS60V3Processor.getLocale(device), 
                                      "dm.msm.software.installed.exists.nokia.s60.3rd.message", 
                                      software.getName());
    // Success!
    BaseS60V3Processor.sendTextMessage(device, text);
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
        // update the jobStatus for end session
        if (completionCode == DeviceDMState.STATE_COMPLETED) {
           if (!this.deployed) {
              // Waiting Client Initialized Session, client report the state of firmware update
              completionCode = DeviceDMState.WAITING_CLIENT_INITIALIZED_SESSION;
           } else {
             // 软件已经存在, 发送短信息提示
             Device device = this.getDevice(factory);
             Software software = this.getTargetSoftware(factory);
             this.sendExistsMessage(device, software);
           }
        }
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
                   if (this.isInstalledAndActived(factory, device, software)) {
                      // 检测到软件已经安装, 无需运行后续步骤
                      this.deployed = true;
                      this.step = STEP_Finished;
                      log.info(software.getExternalId() + " had been deployed, give up installation process for device: ." + device.getExternalId());
                      return new ManagementOperation[0];
                   }
                 }
               }
               case STEP_Setup_Software_Node_Attributes: {
                 this.step = STEP_Setup_Software_Node_Attributes;
                 ManagementOperation[] operations = this.getNextOperations4SetupDownloadNode(factory, software);
                 if (operations != null && operations.length > 0) {
                    return operations;
                 }
               }
               case STEP_Execute_Installation: {
                 this.step = STEP_Execute_Installation;
                 ManagementOperation[] operations = this.getNextOperations4ExecuteDownloadNode(factory, software);
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
   */
  private boolean isInstalledAndActived(ManagementBeanFactory factory, Device device, Software software) {
    // TODO Please detect it
    return false;
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
            case STEP_Setup_Software_Node_Attributes: {
                 this.step = STEP_Execute_Installation;
                 for (ManagementOperationResult result: results) {
                     String command = result.getCommand();
                     if (command.equalsIgnoreCase("Replace")) {
                        statusCode = result.getStatusCode();
                     }
                 }
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
