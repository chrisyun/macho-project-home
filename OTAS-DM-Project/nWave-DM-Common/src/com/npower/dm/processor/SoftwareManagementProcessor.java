/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/SoftwareManagementProcessor.java,v 1.5 2008/08/04 04:25:28 zhao Exp $
  * $Revision: 1.5 $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.msm.SoftwareManagementJobAdapter;
import com.npower.dm.msm.SoftwareManagementJobAdapterImpl;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/08/04 04:25:28 $
 */
public class SoftwareManagementProcessor extends BaseProcessor implements ManagementProcessor, Serializable {

  private static transient Log log = LogFactory.getLog(SoftwareManagementProcessor.class);
  
  /**
   * ManagementProcessor for software management job
   */
  private ManagementProcessor processor = null;
  /**
   * 
   */
  public SoftwareManagementProcessor() {
    super();
  }
  
  // Private methods ------------------------------------------------------------------------------------------
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
   * 根据任务类型和型号选择一个适合的DM Processor
   * @param factory
   * @param job
   * @param device
   * @return
   * @throws DMException
   */
  private ManagementProcessor getSoftwareManagementProcessor(ManagementBeanFactory factory, ProvisionJob job, Device device) throws DMException {
    SoftwareManagementJobAdapter adapter = new SoftwareManagementJobAdapterImpl(factory);
    ManagementProcessor processor = adapter.getProcessor(job.getJobType(), device.getExternalId());
    if (processor != null && processor instanceof BaseProcessor) {
       ((BaseProcessor)processor).setParentProcessor(this.getParentProcessor());
    }
    return processor;
  }

  

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
        
        ProvisionJob job = this.getProvisionJob(factory);
        this.processor = this.getSoftwareManagementProcessor(factory, job, device);
        if (this.processor == null) {
           throw new DMException("Could not found a software management processor for jobID: "
              + this.sessionContext.getDmstate().mssid + ", device: " + device.getExternalId());
        }
    } catch (Exception ex) {
      throw new ManagementException("Error in beginSession: ", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
    try {
        this.processor.beginSession(session);
    } catch (Exception ex) {
      throw new ManagementException("Error in beginSession: ", ex);
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
        // Call endSession
        this.processor.endSession(completionCode);
        
        // update the jobStatus for end session
        setJobStatus4EndSession(completionCode);
    } catch (Exception ex) {
      throw new ManagementException("End of DM SoftwareManagementProcessor error", ex);
    } finally {
      this.trackJobLog(this.sessionContext);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#getNextOperations()
   */
  @Override
  public ManagementOperation[] getNextOperations() throws ManagementException {
    return this.processor.getNextOperations();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#setOperationResults(sync4j.framework.engine.dm.ManagementOperationResult[])
   */
  @Override
  public void setOperationResults(ManagementOperationResult[] results) throws ManagementException {
    this.processor.setOperationResults(results);
  }

}
