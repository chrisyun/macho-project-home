/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/S60V3OperationProcessor.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/06/16 10:11:15 $
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
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ExecManagementOperation;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.engine.dm.TreeNode;

import com.npower.dm.core.Device;
import com.npower.dm.core.Software;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.processor.GenericTreeDiscoveryProcessor;
import com.npower.dm.server.engine.EngineConfig;

/**
 * DM Processor for Software Management
 * Adapted for: Nokia S60 3rd platform.
 * <pre>
 * The following command script tested with Nokia 6120:
 * 
<?xml version="1.0" encoding="UTF-8"?>
<Script>
  <Sequence>
    <Add>
      <InteriorNode>
        <Target>./SCM/Download/MSNApp</Target>
      </InteriorNode>
    </Add>
    <Add>
      <LeafNode>
        <Target>./SCM/Download/MSNApp/Name</Target>
        <Data format="chr">MSN</Data>
      </LeafNode>
    </Add>
    <Add>
      <LeafNode>
        <Target>./SCM/Download/MSNApp/Version</Target>
        <Data format="chr">1.0</Data>
      </LeafNode>
    </Add>
  </Sequence>
  
  <Replace>
    <LeafNode>
      <Target>./SCM/Download/MSNApp/URI</Target>
      <Data format="chr">http://www.otas.mobi:8080/download/msn_mobile_3.5_for_nokia_6120_544.sisx</Data>
    </LeafNode>
  </Replace>
  
  <Replace>
    <LeafNode>
      <Target>./SCM/Download/MSNApp/InstallOpts</Target>
      <Data format="chr" type="text/xml"><![CDATA[ <InstOpts>
<StdOpt name="drive" value="c"/>
<StdOpt name="lang" value="*" />
<StdOpt name="upgrade" value="yes"/>
<StdOpt name="kill" value="yes"/>
<StdSymOpt name="pkginfo" value="yes"/>
<StdSymOpt name="optionals" value="yes"/>
<StdSymOpt name="ocsp" value="no"/>
<StdSymOpt name="capabilities" value="yes"/>
<StdSymOpt name="untrusted" value="yes"/>
<StdSymOpt name="ignoreocspwarn" value="yes"/>
<StdSymOpt name="ignorewarn" value="yes"/>
<StdSymOpt name="fileoverwrite" value="yes"/>
</InstOpts> ]]></Data>
    </LeafNode>
  </Replace>
  
  <Exec>
    <Item>
      <Target>./SCM/Download/MSNApp/Operations/DownloadAndInstallAndActivate</Target>
    </Item>
  </Exec>
  
</Script>
 * </pre>
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public abstract class S60V3OperationProcessor extends BaseS60V3Processor implements ManagementProcessor, Serializable {

  private static transient Log log = LogFactory.getLog(S60V3OperationProcessor.class);
  
  /**
   * 检查Software是否存在.
   * 
   */
  private static final int STEP_Get_Software_Nodes_Information = 100;
  
  /**
   * 执行软件安装任务
   * 
   */
  private static final int STEP_Execute_Operation = 400;
  
  /**
   * 卸载完成后再次检查Software是否存在.
   * 
   */
  private static final int STEP_Get_Software_Nodes_Information_Again = 500;
  
  private static final int STEP_Finished = 800;
  
  private ManagementProcessor processor4Discovery = null;

  /**
   * 
   */
  public S60V3OperationProcessor() {
    super();
  }
  
  // Private methods ------------------------------------------------------------------------

  private ManagementOperation[] getNextOperations4Execute(ManagementBeanFactory factory, Device device,
      Software software) {
    List<ManagementOperation> operations = new ArrayList<ManagementOperation>();

    {
      ExecManagementOperation oper1 = new ExecManagementOperation();
      TreeNode treeNode = getExecuteTagetNode(software);
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
        //queuedNodePathsToDiscovery.add(this.getSoftwareDeployedBaseNodePath(this.getTargetSoftware(factory)));
        queuedNodePathsToDiscovery.add(this.getBaseScomoDeployedNode());
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
                   if (!this.isInstalled(factory, device, software)) {
                      // 检测到软件未安装, 无需运行后续步骤
                      log.info(software.getExternalId() + " none exists, give up un-installation process for device: ." + device.getExternalId());
                      return new ManagementOperation[0];
                   }
                 }
               }
               case STEP_Execute_Operation: {
                 this.step = STEP_Execute_Operation;
                 ManagementOperation[] operations = this.getNextOperations4Execute(factory, device, software);
                 if (operations != null && operations.length > 0) {
                    return operations;
                 }
               }
               case STEP_Get_Software_Nodes_Information_Again: {
                 this.step = STEP_Get_Software_Nodes_Information_Again;
                 ManagementOperation[] operations = this.processor4Discovery.getNextOperations();
                 if (operations != null && operations.length > 0) {
                    return operations;
                 } else {
                   this.processor4Discovery.endSession(DeviceDMState.STATE_COMPLETED);
                   return new ManagementOperation[0];
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
            case STEP_Execute_Operation: {
                 this.step = STEP_Get_Software_Nodes_Information_Again;
                 for (ManagementOperationResult result: results) {
                     String command = result.getCommand();
                     if (command.equalsIgnoreCase("Exec")) {
                        statusCode = result.getStatusCode();
                     }
                 }
                 // Get root nodes which will discovery.
                 List<String> queuedNodePathsToDiscovery = new ArrayList<String>();
                 // Deployed node path
                 //queuedNodePathsToDiscovery.add(this.getSoftwareDeployedBaseNodePath(this.getTargetSoftware(factory)));
                 queuedNodePathsToDiscovery.add(this.getBaseScomoDeployedNode());
                 this.processor4Discovery = new GenericTreeDiscoveryProcessor(queuedNodePathsToDiscovery);
                 this.processor4Discovery.beginSession(this.sessionContext);
                 break;
            }
            case STEP_Get_Software_Nodes_Information_Again:
              this.processor4Discovery.setOperationResults(results);
              break;
            case STEP_Finished:
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
        setJobStatus4EndSession(completionCode);
    } catch (Exception ex) {
      throw new ManagementException("End of DM S60V3UninstallProcessor4DL error", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * 返回用于执行的节点.
   * @param software
   * @return
   */
  public abstract TreeNode getExecuteTagetNode(Software software);

}
