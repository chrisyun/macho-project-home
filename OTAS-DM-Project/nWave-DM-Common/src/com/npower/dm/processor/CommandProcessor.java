/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/CommandProcessor.java,v 1.21 2008/08/04 04:25:28 zhao Exp $
  * $Revision: 1.21 $
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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;

import com.npower.dm.command.Compiler;
import com.npower.dm.command.Compiler4CommandScript;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * Make a processor for command scripts<br>
 * When SessionHandler pass a job into this processor by ProcessorSelector, 
 * must be assign the job ID to the DMState.mssid.
 * <br>
 * <br>
 * Status code  Meaning
 * (200) OK    The command accessed leaf node and it completed successfully.
 * (215) Not executed  Command was not executed, as a result of user interaction and user chose to abort or cancel.
 * (216) Atomic roll back OK   Command was inside Atomic element and Atomic failed. This command was rolled back successfully.
 * (401) Unauthorized  The originator's authentication credentials specify a principal with insufficient rights to complete the command.
 * (404) Not Found The specified data item doesn't exist on the recipient. This may also imply that the stated URI  for the location of the new management object cannot be resolved
 * (405) Command not allowed   Command not allowed. The requested command is not allowed on the target.
 * (407) Authentication required   No authentication credentials were specified. A suitable challenge can also be returned.
 * (413) Request entity too large  The data item to be transferred is too large (e.g., there are restrictions on the size of data items transferred to the recipient).
 * (414) URI too long  URI in command is too long. Either string presenting URI or segment in URI is too long or URI has too many segments.
 * (415) Unsupported media type or format  The media type or format for the data item is not supported by the recipient.
 * (418) Already exists    The requested Add command failed because the target already exists.
 * (420) Device full   The recipient device storage is full.
 * (425) Permission denied The server does not have the proper ACL permissions.
 * (500) Command failed    Non-specific errors created by the recipient while attempting to complete the command.
 * (516) Atomic roll back failed   Command was inside Atomic element and Atomic failed. This command was not rolled back successfully. Server should take action to try to recover client back into original state.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.21 $ $Date: 2008/08/04 04:25:28 $
 */
public class CommandProcessor extends BaseProcessor implements ManagementProcessor, Serializable {

  private static transient Log log = LogFactory.getLog(TreeDiscoveryProcessor.class);

  
  /**
   * 
   */
  public CommandProcessor() {
    super();
  }

  // Private methods ****************************************************************************************
  /**
   * Compile the script.
   * @throws DMException
   * @throws ManagementException 
   */
  private List<ManagementOperation> fullfileOperations(ManagementBeanFactory factory) throws DMException, ManagementException {
    if (log.isDebugEnabled()) {
      log.debug("Compile the command job, id: " + this.sessionContext.getDmstate().mssid);
    }
    ProvisionJobBean jobBean = factory.createProvisionJobBean();
    ProvisionJob job = jobBean.loadJobByID(this.sessionContext.getDmstate().mssid);
    if (job == null) {
       throw new DMException("Could not found the jobID: " + this.sessionContext.getDmstate().mssid);
    }
    if (!job.getJobType().equalsIgnoreCase(ProvisionJob.JOB_TYPE_SCRIPT)) {
       throw new DMException("The job's type wrong, must assign a script job to this processor. jobID: " + this.sessionContext.getDmstate().mssid);
    }
    
    Compiler compile = new Compiler4CommandScript(job.getScriptString());
    List<ManagementOperation> opers = compile.compile();
    if (opers == null || opers.size() == 0) {
       throw new DMException("Error in compile the script, the operation is empty.");
    }
    
    // Append a Dummy operation.
    appendDummyOperation(opers);
    return opers;
  }

  /**
   * The readObject method is responsible for reading from the stream and restoring the classes fields,
   * and restore the transient fields.
   */
  protected void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    super.readObject(in);
    log = LogFactory.getLog(CommandProcessor.class);
  }  

  /**
   * Called when a management session is started for the given principal. 
   * sessionId is the content of the SessionID element of the OTA DM message.
   *
   * @param sessionId the content of the SessionID element of the OTA DM message
   * @param principal the principal who started the management session
   * @param type the management session type (as specified in the message Alert)
   * @param devInfo device info of the device under management
   * @param dmstate device management state
   *
   * @throws ManagementException in case of errors
   * 
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
       log.debug("sessionId: " + sessionId          );
       log.debug("principal: " + principal          );
       log.debug("type: "      + type               );
       log.debug("deviceId: "  + devInfo            );
    }
   
    ManagementBeanFactory factory = null;
    try {
        // Load Device boundled with this Session
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
        
    } catch(Exception ex) {
      throw new ManagementException("Could not load device: from DM inventory.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

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
  public void endSession(int completionCode) throws ManagementException {
    if (log.isDebugEnabled()) {
       log.debug("End a DM management session with sessionId: " + sessionContext.getSessionId());
    }
    try {
        // update the jobStatus for end session
        super.setJobStatus4EndSession(completionCode);  
        
    } catch(Exception ex) {
      throw new ManagementException("Could not load device: from DM inventory.", ex);
    } finally {
      // Tracking Job
      this.trackJobLog(this.sessionContext);
    }
  }

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
  public ManagementOperation[] getNextOperations() throws ManagementException {
    ManagementOperation[] operations = null;

    if (step == 0) {
       ManagementBeanFactory factory = null;
       try {
           factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
           List<ManagementOperation> list = this.fullfileOperations(factory);
           operations = (ManagementOperation[])list.toArray(new ManagementOperation[0]);
       } catch (Exception ex) {
         throw new ManagementException("Error caculating the operations", ex);
       } finally {
         if (factory != null) {
            factory.release();
         }
       }
    } else {
      operations = new ManagementOperation[0];
    }
    return operations;
  }

  /**
   * Called to set the results of a set of management operations. 
   *
   * @param results the results of a set of management operations. 
   *
   * @throws ManagementException in case of errors
   * 
   * @see sync4j.framework.engine.dm.ManagementProcessor#setOperationResults(sync4j.framework.engine.dm.ManagementOperationResult[])
   */
  public void setOperationResults(ManagementOperationResult[] results) throws ManagementException {

    step++;

    String id = sessionContext.getDmstate().id;

    if (log.isDebugEnabled()) {
        log.debug("update results for id: " + id);
    }
    // Update the results into DM inventory.
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        factory.beginTransaction();
        this.updateResults(factory, this.getDeviceID(), results);
        factory.commit();
    } catch (Exception ex) {
      factory.rollback();
      throw new ManagementException("Could not save results in CommandProcessor.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    sessionContext.getDmstate().state = DeviceDMState.STATE_COMPLETED;
  }


}
