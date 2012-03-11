/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/SmartProfileAssignmentProcessor.java,v 1.37 2008/12/16 06:31:43 zhao Exp $
  * $Revision: 1.37 $
  * $Date: 2008/12/16 06:31:43 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.AddManagementOperation;
import sync4j.framework.engine.dm.DeleteManagementOperation;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.GetManagementOperation;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementOperations;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.ReplaceManagementOperation;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.engine.dm.TreeManagementOperation;
import sync4j.framework.engine.dm.TreeNode;

import com.npower.dm.core.APLinkValueNotFoundException;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttributeType;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileAssignmentBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.util.DDFTreeHelper;

/**
 * begin
 *    根据ProfileMapping, 创建Registry
 * 
 * process
 * 1. 获取动态节点, 并为其计算或生成name
 * 2. 检查生成的name是否在设备端存在, 如果存在则重复第一步, 直到所有节点名称在设备端不存在
 * 3. 根据Mapping的规则和DDF规则, 生成DM Commands, 提交到设备端执行
 * 4. 执行成功后, 进行Discovery, 同步设备树
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.37 $ $Date: 2008/12/16 06:31:43 $
 */
public class SmartProfileAssignmentProcessor extends BaseProcessor implements ManagementProcessor, Serializable {

  private static transient Log log = LogFactory.getLog(SmartProfileAssignmentProcessor.class);
  
  private static final int STEP_DELETE_OLD_PROFILE = 1;
  private static final int STEP_DETECT_NODE_NAME = 2;
  private static final int STEP_ASSIGN_PROFILE = 3;
  private static final int STEP_FINISHED = 100;
  
  /**
   * Registry for compile ProfileAssginment 
   */
  private Registry localRegistry = null;
  
  /**
   * Record device tree information for generating dynamic node's name
   */
  private DeviceRegistry deviceRegistry = new DeviceRegistry();
  
  /**
   * Record step in process.
   */
  private int nextStep = 0;
  
  /**
   * Flag: indicate failure of success status for current processor.
   */
  private boolean success = true;
  
  /**
   * Default constructor.
   */
  public SmartProfileAssignmentProcessor() {
    super();
  }

  /**
   * The readObject method is responsible for reading from the stream and
   * restoring the classes fields, and restore the transient fields.
   */
  protected void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    super.readObject(in);
    log = LogFactory.getLog(SmartProfileAssignmentProcessor.class);
  }
  
  // Private methods ------------------------------------------------------------------------------------------------
  /**
   * Return current profile assignment.
   * ProfileAssignment will be retrived by jobID which defined in dmState.mssid.
   * @param factory
   *        ManagementBeanFactory
   * @return
   *        ProfileAssignment.
   * @throws DMException
   * @throws ManagementException
   */
  private ProfileAssignment getCurrentProfileAssignment(ManagementBeanFactory factory) throws DMException, ManagementException {
    ProvisionJob job = getProvisionJob(factory);
    if (job == null) {
       throw new DMException("Could not found the jobID: " + this.sessionContext.getDmstate().mssid);
    }
    if (!job.getJobType().equalsIgnoreCase(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE)
        && !job.getJobType().equalsIgnoreCase(ProvisionJob.JOB_TYPE_RE_ASSIGN_PROFILE)) {
       throw new DMException("The job's type wrong, must assign a assignment job to this processor. jobID: "
          + this.sessionContext.getDmstate().mssid);
    }

    ProvisionJobBean jobBean = factory.createProvisionJobBean();
    ProfileAssignment profileAssignment = jobBean.getProfileAssignment(job, this.getDevice(factory));
    return profileAssignment;
  }

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
   * Get ProfileMapping definition.
   * @param assignment
   * @return
   */
  private ProfileMapping getProfileMapping(ProfileAssignment assignment) {
    // Get ProfileConfig
    ProfileConfig config = assignment.getProfileConfig();
    ProfileTemplate template = config.getProfileTemplate();
    Device device = assignment.getDevice();

    // Retrieve ProfileMapping.
    Model model = device.getModel();
    ProfileMapping mapping = model.getProfileMap(template);
    return mapping;
  }

  
  /**
   * Generate Server-side Registry according to ProfileMapping policy.
   * @param factory
   *        Instance of ManagementBeanFactory
   * @return
   * @throws DMException
   * @throws ManagementException
   */
  private Registry generateLocalRegistry(ManagementBeanFactory factory) throws DMException, ManagementException {
    // Get ProfileAssignment of current job.
    ProfileAssignment assignment = getCurrentProfileAssignment(factory);
    // Get ProfileMapping for current device
    ProfileMapping mapping = getProfileMapping(assignment);
    
    DDFNode rootNode = mapping.getRootDDFNode();
    String rootNodePath = mapping.getRootNodePath();
    if (StringUtils.isEmpty(rootNodePath)) {
       rootNodePath = rootNode.getNodePath();
    }
    
    // Generate Local registry.
    Registry registry = new Registry();
    
    // Detect share root node mode
    if (mapping.getShareRootNode()) {
       // ShareRoot Mode, Re-caculate the Root Node Path from linked profile assgingment.
       // In share mode, this Profile assignment will share root name with referencing profile (eg: NAP Profile)
       ProvisionJobBean jobBean = factory.createProvisionJobBean();
       ProfileAssignment linkedAssignment = jobBean.getLinkedProfileAssignment(assignment);
       String sharedRootNodePath = null;
       if (linkedAssignment != null) {
          sharedRootNodePath = linkedAssignment.getProfileRootNodePath();
       }
       if (StringUtils.isEmpty(sharedRootNodePath)) {
          throw new DMException("In shared root node mode, but could not found RootNodePath from Linked ProfileAssignment.");
       }
       rootNodePath = sharedRootNodePath;
    }
    registry.addRegistryItem(rootNodePath, rootNode);

    
    ProvisionJobBean jobBean = factory.createProvisionJobBean();
    for (ProfileNodeMapping nodeMapping: (Set<ProfileNodeMapping>)mapping.getProfileNodeMappings()) {
        // Caculate value
        Object value = null;
        try {
            value = jobBean.caculateProfileAssignmentValue(assignment, nodeMapping);
        } catch (APLinkValueNotFoundException ex) {
          // Could not found ProfileAssignment linked by current profile assgingment
          if (! nodeMapping.getProfileAttribute().getIsRequired()) {
             continue;
          }
          throw ex;
        }
        
        // Add a item into Server-side registry.
        RegistryItem item = registry.addRegistryItem(rootNodePath, nodeMapping);
        item.setValue(value);
        
    }
    return registry;
  }

  
  // private methods for stage# DetectNames --------------------------------------------------------------
  
  /**
   * Generate ManagementOperations for Stage#: detect device tree for generating dynamic node's name.
   * @return
   *        Array of ManagementOperation.
   * @throws ManagementException
   */
  private ManagementOperation[] getNextOperations4DetectNames() throws ManagementException {
    List<ManagementOperation> operations = new ArrayList<ManagementOperation>();
    
    // Get all of dynamics nodes
    List<RegistryItem> dynamicNodes = this.localRegistry.getDynamicItems();
    for (RegistryItem dynamicItem: dynamicNodes) {
        RegistryItem item = dynamicItem.getParent();
        String nodePath = item.getPath();
        // Generate a GET operation to detect the generated-name.
        GetManagementOperation oper = new GetManagementOperation();
        TreeNode tNode = new TreeNode(nodePath, null);
        tNode.setFormat(item.getDdfNode().getFormat());
        oper.addTreeNode(tNode);
        
        operations.add(oper);
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
   * Convert to NodeMap into ArrayList<String>, this operation will ignore value of Node
   * @param nodes
   * @return
   */
  private ArrayList<String> getNodesList(Map<String, Object> nodes) {
    ArrayList<String> nodesList = new ArrayList<String>();
    for (String key: nodes.keySet()) {
        Object value = nodes.get(key);
        if (value instanceof TreeNode) {
           String format = ((TreeNode)value).getFormat();
            if (format.equalsIgnoreCase(TreeNode.FORMAT_NODE)) {
               value = ((TreeNode)value).getValue();
               if (value != null) {
                  StringTokenizer st = new StringTokenizer((String)value, "/");
                  while (st.hasMoreTokens()) {
                        String temp = key + "/" + st.nextToken();
                        nodesList.add(temp);
                  }
               } else {
                 nodesList.add(key);
               }
            } else {
              nodesList.add(key);
            }
        } else {
          nodesList.add(key);
        }
    }
    return nodesList;
  }
  
  /**
   * Process result for stage# DetectNames.
   * @param results
   *        DM Operation results from terminal.
   * @throws ManagementException
   */
  private void setOperationResults4DetectNames(ManagementOperationResult[] results) throws ManagementException {
    // NextStep: Assign Profile
    nextStep = STEP_ASSIGN_PROFILE;
    
    for (ManagementOperationResult result : results) {
        String command = result.getCommand();
        int status = result.getStatusCode();
        if (command.equalsIgnoreCase("Get")) {
          if (status == 200) {
             // Node exists!
             this.deviceRegistry.setNodes(this.getNodesList(result.getNodes()));
          }
        }
    }
  }
  
  // private methods for stage# AssignProfile --------------------------------------------------------------
  
  /**
   * Generate ManagementOperation for Stage#: Generating DM commands for assignment profile.
   * @return
   *        Array of ManagementOperations.
   * @throws ManagementException
   */
  private ManagementOperation[] getNextOperations4AssignProfile() throws ManagementException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        List<ManagementOperation> operations = new ArrayList<ManagementOperation>();
        
        // 整理节点, 让SELF_LINK和SELF_NAME的节点最后处理, "最后处理"能够确保所需要的ProfileRootNodePath可以正确计算. 
        List<RegistryItem> items4Stage1 = new ArrayList<RegistryItem>();
        List<RegistryItem> items4Stage2 = new ArrayList<RegistryItem>();
        for (RegistryItem item: this.localRegistry.getItems()) {
            if (ProfileAttributeType.TYPE_SELF_LINK.equalsIgnoreCase(item.getTypeOfProfileAttribute())
                || ProfileAttributeType.TYPE_SELF_NAME.equalsIgnoreCase(item.getTypeOfProfileAttribute())) {
              items4Stage2.add(item);
            } else {
              items4Stage1.add(item);
            }
        }
        List<RegistryItem> items = new ArrayList<RegistryItem>();
        items.addAll(items4Stage1);
        items.addAll(items4Stage2);
        
        // 转换为ManagementOperation
        for (RegistryItem item: items) {
            if (item.getDdfNode().getFormat().equals(DDFNode.DDF_FORMAT_NODE) &&
                !item.getDdfNode().imply(DDFNode.ACCESS_TYPE_ADD) &&
                !item.getDdfNode().imply(DDFNode.ACCESS_TYPE_EXEC) &&
                !item.getDdfNode().imply(DDFNode.ACCESS_TYPE_REPLACE) &&
                !item.getDdfNode().imply(DDFNode.ACCESS_TYPE_DELETE)) {
              // Not Allow to Add, Exec, Replace, Delete, Ignore It.
              continue;
            }
            
            // Caculate Node path for the item.
            String nodePath = caculateNodePath(factory, item);
            // 重新更新预先计算的根节点路径
            this.fixValue4SelfType();
            
            DDFNode ddfNode = item.getDdfNode();
            TreeManagementOperation oper = null;
            String format = null;
            ProfileNodeMapping profileNodeMapping = item.getProfileNodeMapping();
            if (profileNodeMapping != null) {
              format = profileNodeMapping.getValueFormat();
            }
            if (StringUtils.isEmpty(format)) {
               // 如果NodeMapping中未定义format, 则使用DDF节点的Format
               format = ddfNode.getFormat();
            }
            if (profileNodeMapping != null && StringUtils.isNotEmpty(profileNodeMapping.getCommand())) {
               // 创建NodeMapping中指定的命令
               Class<? extends ManagementOperation> clazz = ManagementOperations.getOperation(profileNodeMapping.getCommand()).getClazz();
               oper = (TreeManagementOperation)clazz.newInstance();
            } else if (ddfNode.imply(DDFNode.ACCESS_TYPE_ADD)) {
               // Create the Dynamic node
               oper = new AddManagementOperation();
            } else if (!format.equals(DDFNode.DDF_FORMAT_NODE)) {
              // 叶子节点
              if (ddfNode.imply(DDFNode.ACCESS_TYPE_REPLACE)) {
                 // 叶子节点, 且允许Replace操作
                 oper = new ReplaceManagementOperation();
              } else {
                continue;
              }
            }
            TreeNode tNode = null;
            if (format.equals(DDFNode.DDF_FORMAT_NODE)) {
               tNode = new TreeNode(nodePath);
               // Set Formate of node
               tNode.setFormat(DDFNode.DDF_FORMAT_NODE);
            } else {
              // Retrieve value for this DDF Node
              Object value = item.getValue();
              if (value == null && oper instanceof AddManagementOperation) {
                 // 当value为null, 并且为Add command时，不向设备端发送Add指令
                 // 此处理逻辑为了保证一些标志字段，例如：/AP/${NodeName}/Px/${NodeName}/PxAuthInf/${NodeName}/PxAuthID
                 // 如果空值发送Add指令，将出现ErrorCode: 404
                 tNode = null;
              } else if (value == null && oper instanceof ReplaceManagementOperation) {
                // Fix Bug#416 Comments#3
                // 当value为null, 并且为Add command时，不向设备端发送Replace指令
                // 可能此种处理逻辑对Replace来说欠妥, 难道某个节点的值不能取空值, 即：原来有值, 现在通过Replace修改为空值
                // 可以考虑在NodeMapping规则中增加allowEmptyValue来解决上述问题, 目前暂时按照空值时不发送Replace指令来处理.
                tNode = null;
              } else {
                tNode = new TreeNode(nodePath);
                if (value == null && value instanceof String) {
                   value = "";
                } else if (value == null && value instanceof byte[]) {
                  value = new byte[0];
                } else if (value instanceof byte[] && (
                    format.equalsIgnoreCase(DDFNode.DDF_FORMAT_XML) ||
                    format.equalsIgnoreCase(DDFNode.DDF_FORMAT_CHR))) {
                  value = new String((byte[])value, "UTF-8");
                } else if (value instanceof byte[] && 
                    format.equalsIgnoreCase(DDFNode.DDF_FORMAT_BIN)) {
                  // convert "bin" to "b64"
                  format = DDFNode.DDF_FORMAT_B64;
                }
                tNode.setValue(value);
                
                if (profileNodeMapping != null && StringUtils.isNotEmpty(profileNodeMapping.getDefaultValueMimeType())) {
                   tNode.setType(profileNodeMapping.getDefaultValueMimeType());
                }
                // Always setFormat after setValue().
                tNode.setFormat(format);
              }
            }
            
            if (oper != null && tNode != null) {
               // Add operation into array of operations.
               oper.addTreeNode(tNode);
               operations.add(oper);
            }
        }
        
        this.appendDummyOperation(operations);
        return (ManagementOperation[]) operations.toArray(new ManagementOperation[0]);
    } catch (Exception ex) {
      throw new ManagementException("Error in SmartProfileAssigngmentProcessor: ", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * Caculate Node path for the item.
   * @param item
   *        Registry Item
   * @return
   *        Node Path for Provisioning.
   */
  private String caculateNodePath(ManagementBeanFactory factory, RegistryItem item) throws ManagementException, DMException {
    String nodePath = item.getPath();
    
    // Get ProfileAssignment of current job.
    ProfileAssignment assignment = getCurrentProfileAssignment(factory);
    // Get ProfileMapping for current device
    ProfileMapping mapping = getProfileMapping(assignment);
    DDFNode rootNode = mapping.getRootDDFNode();
    if (mapping.getShareRootNode() && rootNode.getID() == item.getDdfNode().getID()) {
       // Is Share Root Node, always return the node path
       // 由于是ShareRoot模式，并且当前的节点为ProfileMapping RootNode, 因此不检查重复性，直接使用NodePath.
       if (!this.deviceRegistry.exists(nodePath)) {
          throw new ManagementException("In ProfileMapping Share Root mode, but the shared root node none-exists in device: " + nodePath);
       }
       return nodePath;
    }
    
    // Check path exists?
    while (item.getDdfNode().getName() == null && this.deviceRegistry.exists(nodePath)) {
          // Re-generate name for dynamic node, until which not found in device tree.
          this.localRegistry.generateNameFor(item);
          nodePath = item.getPath();
    }
    return nodePath;
  }

  /**
   * Receive result of management operation for stage# AssignProfile.
   * @param results
   *        Results of ManagementOperations.
   * @throws ManagementException
   */
  private void setOperationResults4AssignProfile(ManagementOperationResult[] results) throws ManagementException {
    // NextStep: Finished
    nextStep = STEP_FINISHED;
    
    // Checking status of operation
    for (ManagementOperationResult result : results) {
        String command = result.getCommand();
        int status = result.getStatusCode();
        if (status == 200) {
          continue;
        }
        
        if (command.equalsIgnoreCase("Add")) {
           if (status != 200) {
              // Something error! maybe ignore it???
              // 忽略中间节点的创建错误.
              this.handleError(result, command, status);
              continue;
           }
        }
        
        // Set success flag to false.
        this.success = false;
        this.handleError(result, command, status);
        
        // Throw exception to terminate this session.
        throw new ManagementException("Client response an error in ProfileAssignmentProcessor, Command: " + command
            + ", status: " + status);
  
    }
  }

  /**
   * Handle error.
   * @param result
   * @param command
   * @param status
   * @return
   */
  private void handleError(ManagementOperationResult result, String command, int status) {
    // Un-handle error
    log.error("Client response an error in ProfileAssignmentProcessor, Command: " + command + ", status: " + status
        + ", Detail: ");
    Map<String, Object> nodes = result.getNodes();
    for (String oldPath : nodes.keySet()) {
      log.error("Target URI: " + oldPath);
      log.error("Value: " + nodes.get(oldPath));
    }
  }
  
  // private methods for stage# Delete old profile --------------------------------------------------------------
  
  /**
   * Generate ManagementOperation for Stage#: DeleteOldProfile
   * @return
   *        Array of ManagementOperations.
   * @throws ManagementException
   */
  private ManagementOperation[] getNextOperations4DeleteOldProfile() throws ManagementException {
    List<ManagementOperation> operations = new ArrayList<ManagementOperation>();
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProfileAssignment assignment = this.getCurrentProfileAssignment(factory);
        String profileRootNodePath = assignment.getProfileRootNodePath();
        
        ProfileMapping mapping = getProfileMapping(assignment);
        DDFNode rootNode = mapping.getRootDDFNode();
        
        TreeManagementOperation oper = new DeleteManagementOperation();
        if (rootNode.imply(DDFNode.ACCESS_TYPE_DELETE)) {
           if (rootNode.getFormat().equals(DDFNode.DDF_FORMAT_NODE)) {
              TreeNode tNode = new TreeNode(profileRootNodePath);
              // Set Formate of node
              tNode.setFormat(DDFNode.DDF_FORMAT_NODE);
              oper.addTreeNode(tNode);
           } else {
             TreeNode tNode = new TreeNode(profileRootNodePath, null);
             oper.addTreeNode(tNode);
           }
        }
        if (oper != null && oper.getNodes() != null && oper.getNodes().size() > 0) {
           operations.add(oper);
        }
    } catch (Exception ex) {
      throw new ManagementException("Error in SmartProfileAssigngmentProcessor: ", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }

    this.appendDummyOperation(operations);
    return (ManagementOperation[]) operations.toArray(new ManagementOperation[0]);
  }

  /**
   * Receive result of management operation for stage# Delete Old Profile.
   * @param results
   *        Results of ManagementOperations.
   * @throws ManagementException
   */
  private void setOperationResults4DeleteOldProfile(ManagementOperationResult[] results) throws ManagementException {
    // NextStep: Detect node names
    nextStep = STEP_DETECT_NODE_NAME;
    
    // Checking status of operation
    for (ManagementOperationResult result : results) {
        String command = result.getCommand();
        int status = result.getStatusCode();
        if (command.equalsIgnoreCase("Delete")) {
          if (status == 200) {
             // Node exists, and deleted!
             
          } else {
            // Node not found, and delete error
          }
        }
    }
  }
  
  /**
   * 返回当前配置下发成功后, 在终端设备上的根节点路径
   * @param rootNode
   * @return
   */
  private String getProfileRootNodePath(ProfileMapping mapping) {
    DDFNode rootNode = mapping.getRootDDFNode();
    String profileRootNodePath = null;
     for (RegistryItem item: this.localRegistry.getItems()) {
         DDFNode node = item.getDdfNode();
         if (node.getID() == rootNode.getID()) {
            profileRootNodePath = item.getPath();
            break;
         }
     }
    return profileRootNodePath;
  }


  /**
   * Submit a discovery job to discover the device tree.
   * @param mapping
   * @param profileRootNodePath
   * @throws DMException
   * @throws ManagementException
   */
  private void submitDiscoveryJob(ManagementBeanFactory factory, ProfileMapping mapping, String profileRootNodePath) throws DMException,
      ManagementException {
    if (StringUtils.isNotEmpty(mapping.getDiscoveryNodePaths())) {
       // 根据Mapping指定的采集目标节点, 创建信息采集任务
       this.submitDiscoveryJob(this.getProvisionJob(factory).getID(), StringUtils.split(mapping.getDiscoveryNodePaths(), ",;"));
    } else {
      // 自动计算需要采集的节点, 创建信息采集任务
      String nodePath4Discovery = null;
      DDFNode rootDdfNode = mapping.getRootDDFNode();
      if (rootDdfNode != null && !rootDdfNode.getIsDynamic()) {
         // DDF Node 存在，并且不是Dynamic Node
         nodePath4Discovery = profileRootNodePath;
      } else {
        // Discovery parent node of this profile assignment.
        nodePath4Discovery = DDFTreeHelper.getParentPath(profileRootNodePath);
        if (nodePath4Discovery == null || nodePath4Discovery.equals(".")) {
           // For performance issue, if node path is ".", will only discover profileRootNodePath.
           nodePath4Discovery = profileRootNodePath;
        }
      }
      this.submitDiscoveryJob(this.getProvisionJob(factory).getID(), new String[]{nodePath4Discovery});
    }
  }
  
  // Implements ManagementProcessor interface -----------------------------------------------------------------------

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
        
        // Generate Local registry
        this.localRegistry = this.generateLocalRegistry(factory);
        if (log.isDebugEnabled()) {
           log.debug("Local registry ... ");
           log.debug(this.localRegistry.toString());
        }
  
        ProvisionJob job = this.getProvisionJob(factory);
        if (job.getJobType().equals(ProvisionJob.JOB_TYPE_ASSIGN_PROFILE)) {
           // Next Step: Detect node names
           this.nextStep = STEP_DETECT_NODE_NAME;
        } else if (job.getJobType().equals(ProvisionJob.JOB_TYPE_RE_ASSIGN_PROFILE)) {
          ProfileAssignment assignment = this.getCurrentProfileAssignment(factory);
          ProfileMapping mapping = this.getProfileMapping(assignment);
          if (mapping.getShareRootNode()) {
             // Next Step: Detect node names
             this.nextStep = STEP_DETECT_NODE_NAME;
          } else {
            // Next Step: Delete old profile
            this.nextStep = STEP_DELETE_OLD_PROFILE;
          }
        } else {
          throw new DMException("The job's type wrong, must assign a assignment job to this processor. jobID: "
              + this.sessionContext.getDmstate().mssid);
        }
        
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
        // update the jobStatus for end session
        setJobStatus4EndSession(completionCode);
  
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        if (this.success && completionCode == DeviceDMState.STATE_COMPLETED) {
           if (this.localRegistry == null) {
             return;
           }
           // 提取供应成功的根节点路径，并保存到ProfileAssignment中
           ProfileAssignment profileAssignment = getCurrentProfileAssignment(factory);
           ProfileConfig config = profileAssignment.getProfileConfig();
           ProfileTemplate template = config.getProfileTemplate();
  
           Model model = this.getDevice(factory).getModel();
           ProfileMapping mapping = model.getProfileMap(template);
  
           // 当前配置下发成功后, 在终端设备上的根节点路径
           String profileRootNodePath = getProfileRootNodePath(mapping);
           assert profileRootNodePath != null : "Could not find the root node path for ProfileAssignment.";
           
           // 保存下发成功后的配置根节点信息
           ProfileAssignmentBean bean = factory.createProfileAssignmentBean();
           factory.beginTransaction();
           profileAssignment.setProfileRootNodePath(profileRootNodePath);
           profileAssignment.setLastSentToDevice(new Date());
           bean.update(profileAssignment);
           factory.commit();
           
           // 下面的条件检查中包含了对ShareRootNode模式的ProfileAssignment重复做Discovery检查的问题，
           //   由于Share Root模式两个Profile分别被同步，Root Node又一样，因此只需要提交一个Discovery Job即可
           if (mapping.isNeedToDiscovery() && StringUtils.isNotEmpty(profileRootNodePath) && !mapping.getShareRootNode()) {
              // Submit a discovery job to discover the device tree.
              submitDiscoveryJob(factory, mapping, profileRootNodePath);
           }
        }
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new ManagementException("End of DM ProfileAssignmentProcessor error", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
      // Tracking Job
      this.trackJobLog(this.sessionContext);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#getNextOperations()
   */
  @Override
  public ManagementOperation[] getNextOperations() throws ManagementException {
    switch (this.nextStep) {
      case STEP_DELETE_OLD_PROFILE: {
           this.nextStep = STEP_DELETE_OLD_PROFILE;
           ManagementOperation[] operations = this.getNextOperations4DeleteOldProfile();
           if (operations != null && operations.length > 0) {
              return operations;
           }
      }
      case STEP_DETECT_NODE_NAME: {
           this.nextStep = STEP_DETECT_NODE_NAME;
           ManagementOperation[] operations =  this.getNextOperations4DetectNames();
           if (operations != null && operations.length > 0) {
              return operations;
           }
      }
      case STEP_ASSIGN_PROFILE: {
           this.nextStep = STEP_ASSIGN_PROFILE;
           // 检测名称结束, 修正SelfLink和SelfName类型的值
           this.fixValue4SelfType();
           
           ManagementOperation[] operations = this.getNextOperations4AssignProfile();
           if (operations != null && operations.length > 0) {
              return operations;
           }
      }
      default :
              return new ManagementOperation[0];
    }
  }

  /**
   * 由于涉及SelfLink和SelfName等两种ProfileAttributeType, 这两种类型只有在名称重复检测结束后, 才能填充准确的值.
   * 本方法用于扫描Local Registry中是否有这两种类型的属性, 为其填充值.
   * @throws DMException 
   * @throws ManagementException 
   */
  private void fixValue4SelfType() throws ManagementException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        String profileRootPath = this.getProfileRootNodePath(this.getProfileMapping(this.getCurrentProfileAssignment(factory)));
        for (RegistryItem item: this.localRegistry.getItems()) {
            if (ProfileAttributeType.TYPE_SELF_LINK.equals(item.getTypeOfProfileAttribute())) {
               item.setValue(profileRootPath);
            } else if (ProfileAttributeType.TYPE_SELF_NAME.equals(item.getTypeOfProfileAttribute())) {
              int endIndex = profileRootPath.lastIndexOf('/');
              if (endIndex > 0 && endIndex < profileRootPath.length()) { 
                 item.setValue(profileRootPath.substring(endIndex + 1, profileRootPath.length()));
              }
            }
        }
    } catch (Exception ex) {
      throw new ManagementException("Failure to fix value for SELF_LINK and SELF_NAME", ex);
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
    switch (nextStep) {
    case STEP_DELETE_OLD_PROFILE:
           this.setOperationResults4DeleteOldProfile(results);
           break;
    case STEP_DETECT_NODE_NAME:
           this.setOperationResults4DetectNames(results);
           break;
    case STEP_ASSIGN_PROFILE:
           this.setOperationResults4AssignProfile(results);
           break;
    default:
           break;
    }
  }

}
