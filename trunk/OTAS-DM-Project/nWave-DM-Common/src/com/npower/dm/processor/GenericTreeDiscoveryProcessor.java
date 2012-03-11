/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/GenericTreeDiscoveryProcessor.java,v 1.9 2008/11/04 06:16:05 zhao Exp $
  * $Revision: 1.9 $
  * $Date: 2008/11/04 06:16:05 $
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;

/**
 * Discovery a device tree.
 * Before call this processor, please setQueuedNodePathsToDiscovery(...) 
 * 
 * The ManagementProcessor interface defines the structure of an object able
 * to handle management sessions.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/11/04 06:16:05 $
 */
public class GenericTreeDiscoveryProcessor extends BaseProcessor implements ManagementProcessor, Serializable {

  private static transient Log log = LogFactory.getLog(GenericTreeDiscoveryProcessor.class);

  /**
   * Queued node paths to discovery.
   * 初始设置包含所有需要采集的节点路径, 运行中将包括所有需要进一步采集的子节点的路径.
   * 当此数组为空, 将停止采集.
   */
  private List<String> queuedNodePathsToDiscovery        = new ArrayList<String>();
  
  /**
   * 初始设置包含所有需要采集的节点路径, 与queuedNodePathsToDiscovery相同,但运行中其值不会发生变化.
   */
  private List<String> nodePaths4Clean = new ArrayList<String>();
  
  /**
   * Map for results.
   */
  private java.util.Map<String, Object> results = new TreeMap<String, Object>();

  /**
   * Indicate weather if does the dummy alert has been sent.
   */
  private boolean dummyAlertAlreadySent = false;

  /**
   * Total Nodes per Get operation.
   */
  private int totalNodesPerGet = 1;
  
  /**
   * If true, will save result in every "setOperationResult(...)"
   * If false, will save result in "endSession(...)"
   */
  private boolean saveResultPerStep = true;


  /**
   * 
   */
  public GenericTreeDiscoveryProcessor() {
    super();
  }

  /**
   * @param queuedNodePathsToDiscovery
   */
  public GenericTreeDiscoveryProcessor(List<String> queuedNodePathsToDiscovery) {
    super();
    this.queuedNodePathsToDiscovery = queuedNodePathsToDiscovery;
    this.nodePaths4Clean = new ArrayList<String>(queuedNodePathsToDiscovery);
  }

  /**
   * @return Returns the queuedNodePathsToDiscovery.
   */
  public List<String> getQueuedNodePathsToDiscovery() {
    return queuedNodePathsToDiscovery;
  }

  /**
   * @param queuedNodePathsToDiscovery The queuedNodePathsToDiscovery to set.
   */
  public void setQueuedNodePathsToDiscovery(List<String> queuedNodePathsToDiscovery) {
    this.queuedNodePathsToDiscovery = queuedNodePathsToDiscovery;
    this.nodePaths4Clean = new ArrayList<String>(queuedNodePathsToDiscovery);
  }

  // Private methods ****************************************************************************************
  /**
   * The readObject method is responsible for reading from the stream and restoring the classes fields,
   * and restore the transient fields.
   */
  protected void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    super.readObject(in);
    log = LogFactory.getLog(GenericTreeDiscoveryProcessor.class);
  }  

  /**
   * Convert to NodeMap into ArrayList
   * @param nodes
   * @return
   */
  private ArrayList<String> getNodesList(Map<String, Object> nodes) {
    ArrayList<String> nodesList = new ArrayList<String>();
    for (String key: nodes.keySet()) {
        Object node = nodes.get(key);
        if (node instanceof TreeNode) {
            String format = ((TreeNode)node).getFormat();
            if (format.equalsIgnoreCase(TreeNode.FORMAT_NODE)) {
               String value = (String)((TreeNode)node).getValue();
               if (value != null) {
                   StringTokenizer st = new StringTokenizer(value, "/");
                   while (st.hasMoreTokens()) {
                         String temp = key + "/" + st.nextToken();
                         nodesList.add(temp);
                         results.put(temp, node);
                   }
               }
            } else {
              results.put(key, node);
            }
        } else {
          results.put(key, node);
        }
    }
    return nodesList;
  }
  
  /**
   * Clear up original device tree
   * @param deviceId
   */
  private void clearOriginalDeviceTree() throws DMException, ManagementException {
    if (log.isDebugEnabled()) {
       log.debug("Clear Device Tree ");
    }
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        factory.beginTransaction();
        DeviceBean deviceBean = factory.createDeviceBean();
        for (String rootNodePath: this.nodePaths4Clean) {
           DeviceTreeNode node = deviceBean.findDeviceTreeNode(this.getDeviceID(), rootNodePath);
           if (node != null ) {
              deviceBean.delete(node);
              if (log.isDebugEnabled()) {
                 log.debug("Clear Device Tree Node:" + rootNodePath + ", nodeID: " + node.getID());
              }
           }
        }
        factory.commit();
    } catch (Exception ex) {
      factory.rollback();
      throw new ManagementException("Could not clear device tree.", ex);
    } finally {
      factory.release();
    }
  }

  /**
   * Save results.
   * @throws DMException
   * @throws ManagementException
   */
  private void saveResults() throws ManagementException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        DeviceBean deviceBean = factory.createDeviceBean();
        // Update device tree based on this session.
        Map<String, Object> results = this.results;
        Set<String> keys = results.keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
            String nodePath = (String)iterator.next();
            Object nodeValue = results.get(nodePath);
            // Save into DM inventory.
            factory.beginTransaction();
            deviceBean.updateDeviceTreeNode(this.getDeviceID() + "", nodePath, nodeValue);
            factory.commit();
        }
        // Clear result set to release memory.
        this.results.clear();
    } catch (Exception ex) {
      factory.rollback();
      throw new ManagementException("Could not save results in TreeDiscoveryProcessor.", ex);
    } finally {
      factory.release();
    }
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

    DevInfo       devInfo   = context.getDevInfo();
    //DeviceDMState dmState   = context.getDmstate();

    // 清除结果集合
    this.results.clear();
    
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
        
        // Clean original device tree.
        this.clearOriginalDeviceTree();
        
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
   * @param completionCode the management session competion code
   *
   * @throws ManagementException in case of errors
   * 
   * @see sync4j.framework.engine.dm.ManagementProcessor#endSession(int)
   */
  public void endSession(int completionCode) throws ManagementException {
    try {
        // update the jobStatus for end session
        super.setJobStatus4EndSession(completionCode);  
      
        // Dump the results into DeviceTree
        this.saveResults();

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
    ArrayList<ManagementOperation> operationsList         = new ArrayList<ManagementOperation>();
    ManagementOperation[] operations = null;

    Map<String, TreeNode> nodes = new HashMap<String, TreeNode>();

    int numNodes = this.queuedNodePathsToDiscovery.size();

    if (numNodes == 0) {
       if (!dummyAlertAlreadySent) {
          // Dummy alert operation

          //
          // Some phone doesn't recognise the Alert command and a
          // TreeDiscovery session fails because the phone doesn't send
          // the last message.
          // As workaround, we add also a Get operation so the phone sends
          // the last message.
          //
          GetManagementOperation dummyGet = new GetManagementOperation();
          Map<String, TreeNode> dummyNode = new HashMap<String, TreeNode>();
          dummyNode.put("./DevInfo/Man", new TreeNode("./DevInfo/Man"));
          
          dummyGet.setNodes(dummyNode);
          if (this.addDummyAlert) {
             operations = new ManagementOperation[] {
                        dummyGet,
                        UserAlertManagementOperation.getDisplay(BaseProcessor.resourceBundle.getString(BaseProcessor.MESSAGE_DUMMY_ALERT_OPERATION), 5, 15)
                        };
          } else {
            operations = new ManagementOperation[] {
                dummyGet};
          }
          dummyAlertAlreadySent = true;
       } else {
         operations = new ManagementOperation[0];
       }
       return operations;
    }

    ManagementOperation o = null;

    // Boundle 10xnode into a GetOperation.
    for (int i = 0; i < numNodes; i++) {
        if ( ((i % totalNodesPerGet) == 0) ) {
           if (i != 0) {
              ((GetManagementOperation)o).setNodes(nodes);
           }
           o = new GetManagementOperation();
           operationsList.add(o);
           nodes = new HashMap<String, TreeNode>();
        }
        nodes.put( (String)this.queuedNodePathsToDiscovery.get(i), 
                   new TreeNode((String)this.queuedNodePathsToDiscovery.get(i)));
    }

    ((GetManagementOperation)o).setNodes(nodes);

    operations = (ManagementOperation[])operationsList.toArray(new ManagementOperation[0]);
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
    String deviceID = sessionContext.getDmstate().deviceId;

    if (log.isDebugEnabled()) {
        log.debug("setOperationResults for id: " + deviceID);
    }

    int numResults = results.length;
    //nodesList = new ArrayList();

    if (!dummyAlertAlreadySent) {
       for (int i = 0; i < numResults; i++) {
           int statusCode = results[i].getStatusCode();
           if (statusCode == 200) {
              List<String> nodePaths = getNodesList(results[i].getNodes());
              queuedNodePathsToDiscovery.addAll(nodePaths);
           }
           Map<String, Object> nodes = results[i].getNodes();
           for (String key: nodes.keySet()) {
               // Remove from queue
               this.queuedNodePathsToDiscovery.remove(key);
           }
       }
    }
    
    if (this.saveResultPerStep) {
       // Save Result per Step
       this.saveResults();
    }

    if (queuedNodePathsToDiscovery.size() == 0) {
       if (log.isDebugEnabled()) {
          log.debug("No other nodes to get");
       }
       //
       // Session completed
       //
       sessionContext.getDmstate().state = DeviceDMState.STATE_COMPLETED;
    }
  }

}
