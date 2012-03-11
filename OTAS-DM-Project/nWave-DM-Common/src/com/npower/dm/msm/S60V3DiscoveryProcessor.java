/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/S60V3DiscoveryProcessor.java,v 1.2 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.2 $
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
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;

import com.npower.dm.core.Device;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.processor.GenericTreeDiscoveryProcessor;
import com.npower.dm.server.engine.EngineConfig;

/**
 * Discovery a device tree.
 * 
 * The ManagementProcessor interface defines the structure of an object able
 * to handle management sessions.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/16 10:11:15 $
 */
public class S60V3DiscoveryProcessor extends BaseS60V3Processor implements ManagementProcessor, Serializable {

  private static transient Log log = LogFactory.getLog(S60V3DiscoveryProcessor.class);

  private ManagementProcessor processor = null;
  
  /**
   * 
   */
  public S60V3DiscoveryProcessor() {
    super();
  }

  // Private methods ****************************************************************************************
  /**
   * The readObject method is responsible for reading from the stream and restoring the classes fields,
   * and restore the transient fields.
   */
  protected void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    super.readObject(in);
    log = LogFactory.getLog(S60V3DiscoveryProcessor.class);
  }  


  // Public methods *****************************************************************************************
  
  // Implements ManagementProcessor interface ***************************************************************

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

        // Get root nodes which will discovery.
        List<String> queuedNodePathsToDiscovery = new ArrayList<String>();
        queuedNodePathsToDiscovery.add(this.getBaseScomoDeployedNode());
        this.processor = new GenericTreeDiscoveryProcessor(queuedNodePathsToDiscovery);
        
        this.processor.beginSession(context);
        
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
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        // update the jobStatus for end session
        super.setJobStatus4EndSession(completionCode);  
    
        this.processor.endSession(completionCode);  
        
    } catch(Exception ex) {
      throw new ManagementException("Could not load device: from DM inventory.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
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
    ManagementOperation[] operations = this.processor.getNextOperations();
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
    this.processor.setOperationResults(results);
  }

}
