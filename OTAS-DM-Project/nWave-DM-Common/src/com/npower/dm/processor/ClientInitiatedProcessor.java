/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/ClientInitiatedProcessor.java,v 1.8 2008/06/16 10:11:14 zhao Exp $
  * $Revision: 1.8 $
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

import java.security.Principal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.Alert;
import sync4j.framework.core.ComplexData;
import sync4j.framework.core.Item;
import sync4j.framework.core.MetInf;
import sync4j.framework.core.Meta;
import sync4j.framework.core.Source;
import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.protocol.ManagementInitialization;

import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2008/06/16 10:11:14 $
 */
public class ClientInitiatedProcessor extends BaseProcessor implements ManagementProcessor {

  private static transient Log        log                = LogFactory.getLog(ClientInitiatedProcessor.class);
  
  private int step = 0;
  /**
   * 
   */
  public ClientInitiatedProcessor() {
    super();
  }

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
    DeviceDMState dmState   = context.getDmstate();
    ManagementInitialization init = context.getManagementInitialization();

    if (log.isDebugEnabled()) {
      log.debug("Starting a new DM management session");
      log.debug("sessionId: " + sessionId);
      log.debug("principal: " + principal);
      log.debug("type: " + type);
      log.debug("deviceId: " + devInfo);
    }
    
    step = 0;
    
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
  
        // Update DevInfo
        super.updateDevInfo(devInfo);
        
        Alert alert = init.getClientAlert();
        String alertType = null;
        String alertCodeStr = "0";
        String alertURI = null;
        String alertMark = null;
        
        if (alert != null) {
           // Check Client Alert content
           List<Item> items = alert.getItems();
           for (Item item: items) {
               ComplexData cData = item.getData();
               // "405"
               alertCodeStr = cData.getData();
               
               Meta meta = item.getMeta();
               MetInf metInf = meta.getMetInf();
               // "org.openmobilealliance.firmwareupdate.downloadandupdate"
               alertType = metInf.getType();
               
               // "critical"
               alertMark = metInf.getMark();
               
               Source source = item.getSource();
               if (source != null) {
                  // "./Fw/FwPkg01"
                  alertURI = source.getLocURI();
               }
           }
        }
        
        if (StringUtils.isNotEmpty(alertType)) {
           if (alertType.equalsIgnoreCase("org.openmobilealliance.firmwareupdate.downloadandupdate")
               || alertType.equalsIgnoreCase("org.openmobilealliance.firmwareupdate.update")
               || alertType.equalsIgnoreCase("org.openmobilealliance.firmwareupdate.download")
               || alertType.equalsIgnoreCase("org.openmobilealliance.dm.firmwareupdate.downloadandupdate")
               || alertType.equalsIgnoreCase("org.openmobilealliance.dm.firmwareupdate.update")
               || alertType.equalsIgnoreCase("org.openmobilealliance.dm.firmwareupdate.download")) {
              // FUMO ALERT, Clean FUMO Job status with Alert code.
              long fumoJobID = 0;
              
              /*
              ProvisionJobBean jobBean = factory.createProvisionJobBean();
              List<ProvisionJob> jobs = jobBean.findJobsQueued(device);
              if (jobs.size() > 0) {
                 for (ProvisionJob job: jobs) {
                     if (job.getJobType().equals(ProvisionJob.JOB_TYPE_FIRMWARE)) {
                        fumoJobID = job.getID();
                        break;
                     }
                 }
              }
              */
              
              // Get current job to change status
              ProvisionJobStatus provReq = device.getInProgressDeviceProvReq();
              if (provReq != null) {
                fumoJobID = provReq.getProvisionElement().getProvisionRequest().getID();
              }
              
              if (fumoJobID == 0) {
                 return;
              }
              int alertCode = 0;
              try {
                  alertCode = Integer.parseInt(alertCodeStr);
              } catch (Exception e) {
                log.error("Unknown Alert code: " + alertCodeStr);
              }
              log.info("FUMO update failure, cause: Alert(Type: " + alertType + ", Data: " + alertCode + ", LocURI: " + alertURI + ", Mark: " + alertMark + ")");
              if (alertCode >= 200 && alertCode < 300) {
                 // Success!
                 // Set job status and alert code
                 this.setJobStatus(fumoJobID, device.getID(), ProvisionJobStatus.DEVICE_JOB_STATE_DONE, "Alert code:" + alertCode);
                 dmState.state = DeviceDMState.STATE_COMPLETED;
              } else {
                // Failure!
                // Set job status and alert code
                this.setJobStatus(fumoJobID, device.getID(), ProvisionJobStatus.DEVICE_JOB_STATE_ERROR, "Alert code:" + alertCode);
                dmState.state = DeviceDMState.STATE_ERROR;
              }
           }
        }
        
  
    } catch (Exception ex) {
      throw new ManagementException("Error in beginSession: ", ex);
    } finally {
      if (factory == null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#endSession(int)
   */
  @Override
  public void endSession(int completionCode) throws ManagementException {
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#getNextOperations()
   */
  @Override
  public ManagementOperation[] getNextOperations() throws ManagementException {
    if (step == 0) {
       step++;
       ManagementBeanFactory factory = null;
       try {
           factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
           List<ManagementOperation> list = getPreconfiguredOperationsForGetDeviceDetail(factory, this.getDeviceID());
           ManagementOperation[] operations = (ManagementOperation[])list.toArray(new ManagementOperation[0]);
           return operations;
       } catch (Exception e) {
         throw new ManagementException("Error in " + this.getClass().getName(), e);
       } finally {
         if (factory != null) {
            factory.release();
         }
       }
    } else {
      return new ManagementOperation[0];
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#setOperationResults(sync4j.framework.engine.dm.ManagementOperationResult[])
   */
  @Override
  public void setOperationResults(ManagementOperationResult[] results) throws ManagementException {
    this.logResult(results);
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        if (results != null && results.length > 0) {
           factory.beginTransaction();
           this.updateResults(factory, this.getDeviceID(), results);
           factory.commit();
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
