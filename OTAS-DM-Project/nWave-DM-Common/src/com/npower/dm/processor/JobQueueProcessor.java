/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/JobQueueProcessor.java,v 1.19 2008/08/04 04:25:28 zhao Exp $
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

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;

import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * Processor for JobQueue, this processor served for the device which has been 
 * provisioned more than one jobs.
 * <br>
 * <pre>
 * 次处理器将自动一次调度运行所有排队作业. 
 * 如果没有任务等待运行, 将调度如下任务：
 * 1. GenericProcessor.
 * 
 * 
 * </pre>
 * @author Zhao DongLu
 * @version $Revision: 1.19 $ $Date: 2008/08/04 04:25:28 $
 */
public class JobQueueProcessor extends BaseProcessor {

  private static transient Log log = LogFactory.getLog(JobQueueProcessor.class);

  
  private ManagementProcessor currentProcessor = null;

  /**
   * 由于记录当前QueueProcessor在此次Session处理周期中不进行处理的Job, 主要为JobQueueProcessor调度处理的各个子Processor在动态创建Job后，通知
   * QueueProcessor对这些任务暂不作处理, 等待下一次Session周期再进行处理.
   */
  private Set<Long> excludedJobIDs = new HashSet<Long>();

  /**
   * 
   */
  public JobQueueProcessor() {
    super();
  }

  /**
   * @return Returns the excludedJobIDs.
   */
  public Set<Long> getExcludedJobIDs() {
    return excludedJobIDs;
  }

  /**
   * @param excludedJobIDs The excludedJobIDs to set.
   */
  public void setExcludedJobIDs(Set<Long> excludedJobIDs) {
    this.excludedJobIDs = excludedJobIDs;
  }

  // Private methods ****************************************************************************************
  
  /**
   * 根据Processor Name获取Processor instance.
   * @param processorClassName
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   */
  private ManagementProcessor getProcessorByName(String processorClassName) throws InstantiationException,
      IllegalAccessException, ClassNotFoundException {
    ManagementProcessor processor = (ManagementProcessor)(Class.forName(processorClassName).newInstance());
    return processor;
  }
  
  /**
   * 根据JobQueue的状况, 返回当前任务对应的Processor.
   * @param factory
   * @return
   * @throws ManagementException
   */
  private ManagementProcessor getNextProcessor(ManagementBeanFactory factory) throws ManagementException {
    ProvisionJobBean bean = factory.createProvisionJobBean();
    try {
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByID(Long.toString(this.getDeviceID()));
        List<ProvisionJob> jobs = bean.findJobsQueued(device);
        ManagementProcessor processor = null;
        if (jobs.size() > 0) {
           ProvisionJob job = jobs.get(0);
           if (!this.excludedJobIDs.contains(new Long(job.getID()))) {
              // 参见excludedJobIDs的说明, 对特别申明的Job暂不作处理
              String processorClassName = JobProcessSelector.getProcessorByJobType(job.getJobType());
              processor = getProcessorByName(processorClassName);
             
              // Set job ID to mssid
              this.sessionContext.getDmstate().mssid = "" + job.getID();
           }
        }
        if (processor != null && processor instanceof BaseProcessor) {
           ((BaseProcessor)processor).setParentProcessor(this);
        }
        return processor;
    } catch (Exception ex) {
      throw new ManagementException("Error in getNextProcessor()", ex);
    }
  }

  /**
   * 获取第一个Processor
   * @param factory
   * @return
   * @throws ManagementException
   */
  private ManagementProcessor getFirstProcessor(ManagementBeanFactory factory) throws ManagementException {
    try {
        ManagementProcessor processor = this.getNextProcessor(factory);
        if (processor == null) {
           processor = getDefaultProcessor();
        }
        if (processor != null && processor instanceof BaseProcessor) {
           ((BaseProcessor)processor).setParentProcessor(this);
        }
        return processor;
    } catch (Exception ex) {
      throw new ManagementException("Error in getNextProcessor()", ex);
    }
  }

  /**
   * 返回Default Processor.
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   */
  private ManagementProcessor getDefaultProcessor() throws InstantiationException, IllegalAccessException,
      ClassNotFoundException {
    String processorClassName = GenericProcessor.class.getName();
    ManagementProcessor processor = getProcessorByName(processorClassName);
    return processor;
  }
  /**
   * The readObject method is responsible for reading from the stream and restoring the classes fields,
   * and restore the transient fields.
   */
  protected void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    super.readObject(in);
    log = LogFactory.getLog(JobQueueProcessor.class);
  }  
  
  // Public methods *****************************************************************************************
  
  // Implements ManagementProcessor interface ***************************************************************
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
        
        this.currentProcessor = this.getFirstProcessor(factory);
        if (this.currentProcessor == null) {
           throw new ManagementException("no any jobs wait for processing for the device: " + this.getDeviceID());
        }
        this.currentProcessor.beginSession(context);
    } catch(Exception ex) {
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
        /**
         * 注意：
         * 更新当前Job的状态, 由于所有的Job状态都有每一个Processor自己完成, 直观上看不需要再JobQueueProcessor中
         * 更新状态. 但由于SessionHandle处理某一个Job失败时, 会调用JobQueueProcessor的endSession方法, 通知
         * processor任务失败. 因此在JobQueueProcessor中必须把状态通知到当前处于doing状态的Job, 并对该状态进行
         * 修改, 否则当前Job将永远处于doing状态.
         * 
         * 由于QueueProcessor不对应唯一的一个Job, 但this.dmState.mssid总是保存和记录当前正在处理的任务, 因此, 
         * setJobStatus4EndSession()能够正确的对当前的Job状态进行设置.
         * 
         * this.dmState.mssid由getNextProcessor()方法进行维护, 每次调用该方法获取下一个Processor, 将会将jobID保存
         * 到this.dmState.mssid中.
         * 
         */
        // update the jobStatus for end session
        super.setJobStatus4EndSession(completionCode);
    } catch(Exception ex) {
      throw new ManagementException("Could not load device: from DM inventory.", ex);
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
        ManagementOperation[] operations = this.currentProcessor.getNextOperations();
        if (operations == null || operations.length == 0) {
           this.currentProcessor.endSession(DeviceDMState.STATE_COMPLETED);
           // Get next processor
           factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
           this.currentProcessor = this.getNextProcessor(factory);
           if (this.currentProcessor == null) {
              // nothing in job of queue.
              return new ManagementOperation[0];
           } else {
             // Init next processor.
             this.currentProcessor.beginSession(this.sessionContext);
             operations = this.currentProcessor.getNextOperations();
             return operations;
           }
        } else {
          return operations;
        }
    } catch (Exception ex) {
      throw new ManagementException("Failure in get next processor.", ex);
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
    if (this.currentProcessor != null) {
       this.currentProcessor.setOperationResults(results);
    }
  }

}
