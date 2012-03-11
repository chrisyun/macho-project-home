package com.npower.dm.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sync4j.framework.engine.dm.AddManagementOperation;
import sync4j.framework.engine.dm.AtomicManagementOperation;
import sync4j.framework.engine.dm.DeleteManagementOperation;
import sync4j.framework.engine.dm.ExecManagementOperation;
import sync4j.framework.engine.dm.GetManagementOperation;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ReplaceManagementOperation;
import sync4j.framework.engine.dm.SequenceManagementOperation;
import sync4j.framework.engine.dm.TreeManagementOperation;
import sync4j.framework.engine.dm.TreeNode;
import sync4j.framework.engine.dm.UserAlertManagementOperation;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.hibernate.entity.ModelDDFTree;
import com.npower.dm.hibernate.entity.ModelEntity;

public class DMClientEmulatorImpl implements DMClientEmulator {
  
  //private static Log log = LogFactory.getLog(DMClientEmulatorImpl.class);

  private Model model;

  private Registry registry;

  /**
   * Default constructor
   */
  public DMClientEmulatorImpl() {
    super();
  }

  /**
   * Constructor
   * @param model
   * @param registry
   */
  public DMClientEmulatorImpl(Model model, Registry registry) {
    super();
    
    this.registry = registry;
    this.model = model;
  }
  
  // static methods *******************************************************************
  
  private static String getParentNodePath(String nodePath) {
    int lastIndex = nodePath.lastIndexOf("/");
    if (lastIndex >= 0) {
       return nodePath.substring(0, lastIndex);
    }
    return null;
  }
  
  private static DDFNode findDDFNodeByNodePath(Model model, String nodePath) throws DMException {
    Set<ModelDDFTree> set = ((ModelEntity)model).getModelDDFTrees();
    for (Iterator<ModelDDFTree> i = set.iterator(); i.hasNext();) {
        ModelDDFTree mTree = (ModelDDFTree) i.next();
        DDFTree tree = mTree.getDdfTree();
        
        Set<DDFNode> nodes = tree.getRootDDFNodes();
        DDFNode result = findDDFNodeByNodePath(nodePath, nodes);
        if (result != null) {
           return result;
        }
    }
    return null;
  }

  /**
   * @param nodePath
   * @param nodes
   */
  private static DDFNode findDDFNodeByNodePath(String nodePath, Set<DDFNode> nodes) {
    for (DDFNode node: nodes) {
        String ddfNodePath = node.getNodePath();
        String[] nodePathList = nodePath.split("/");
        String[] ddfNodePathList = ddfNodePath.split("/");
        if (nodePathList.length == ddfNodePathList.length) {
           boolean found = true;
           for (int j = 0; j < nodePathList.length; j++) {
               if (!ddfNodePathList[j].equals(DDFNode.NAME_OF_DYNAMIC_NODE)
                   && !ddfNodePathList[j].equals(nodePathList[j])) {
                  found = false;
                  break;
               }
           }
           if (found) {
              return node;
           }
        }
        Set<DDFNode> children = node.getChildren();
        DDFNode result = findDDFNodeByNodePath(nodePath, children);
        if (result != null) {
           return result;
        }
    }
    return null;
  }

  // Setter and Getter methods ********************************************************

  /**
   * @return the registry
   */
  public Registry getRegistry() {
    return registry;
  }

  /**
   * @param registry the registry to set
   */
  public void setRegistry(Registry registry) {
    this.registry = registry;
  }

  /**
   * @return the nodel
   */
  public Model getModel() {
    return model;
  }

  /**
   * @param model the model to set
   */
  public void setModel(Model model) {
    this.model = model;
  }

  // Private methods *******************************************************************

  /**
   * Initialize Registry.
   * @return
   */
  private void buildDefaultRegistry(Model model) {
    Set<DDFTree> ddfTrees = model.getDDFTrees();
    for (DDFTree ddfTree : ddfTrees) {
        Set<DDFNode> ddfNodes = ddfTree.getRootDDFNodes();
        DDFNode rootNode = ddfNodes.iterator().next();
        Set<DDFNode> children = rootNode.getChildren();
        buildRegistry(this.getRegistry().getRoot(), children);
    }
  }

  /**
   * Initialize Registry.
   * @param parentItem
   *        Parent registry item
   * @param ddfNodes
   *        Children ddf nodes
   */
  private void buildRegistry(RegistryItem parentItem, Set<DDFNode> ddfNodes) {
    for (DDFNode ddfNode : ddfNodes) {
        if (ddfNode.getName() == null) {
           continue;
        }
        if (ddfNode.imply(DDFNode.ACCESS_TYPE_ADD)) {
           continue;
        }
        
        RegistryItem item = new RegistryItem();
        item.setName(ddfNode.getName());
        item.setDdfNode(ddfNode);
        item.setValue(ddfNode.getValue());
        item.setParent(parentItem);
        parentItem.getChildren().add(item);
  
        Set<DDFNode> children = ddfNode.getChildren();
        this.buildRegistry(item, children);
    }
  }

  private List<ManagementOperationResult> processAddOperation(AddManagementOperation operation) throws DMException {
    Map<String, TreeNode> nodes = operation.getNodes();
    List<ManagementOperationResult> results = new ArrayList<ManagementOperationResult>();
    for (String nodePath : nodes.keySet()) {
        TreeNode treeNode = nodes.get(nodePath);
        Object value = null;
        if (treeNode != null && treeNode.getValue() != null) {
           value = treeNode.getValue();
        }
        RegistryItem item = this.registry.findItem(nodePath);
        if (item != null) {
           // 418: Already exists
           ManagementOperationResult result = new ManagementOperationResult();
           result.setCommand(operation.getDescription());
           result.setNodes(operation.getNodes());
           result.setStatusCode(418);
           Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
           nodes4Result.put(nodePath, new TreeNode(nodePath));
           result.setNodes(nodes4Result);
           results.add(result);
           continue;
        } else {
          String parentPath = getParentNodePath(nodePath);
          RegistryItem parentItem = this.registry.findItem(parentPath);
          DDFNode ddfNode = findDDFNodeByNodePath(this.model, nodePath);
          if (parentItem == null && ddfNode != null) {
             // Parent not found, create parent
             this.registry.addItem(parentPath, null);
             parentItem = this.registry.findItem(parentPath);
             // Link DDF
             parentItem.setDdfNode(ddfNode.getParentDDFNode());
          }
          
          if (parentItem == null) {
             // 404: Parent Not Found
             ManagementOperationResult result = new ManagementOperationResult();
             result.setCommand(operation.getDescription());
             result.setNodes(operation.getNodes());
             result.setStatusCode(404);
             Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
             nodes4Result.put(nodePath, new TreeNode(nodePath));
             result.setNodes(nodes4Result);
             results.add(result);
             continue;
          } else {
            // Check access
            if (ddfNode == null || !ddfNode.imply(DDFNode.ACCESS_TYPE_ADD)) {
               // 425: Permission denied
               ManagementOperationResult result = new ManagementOperationResult();
               result.setCommand(operation.getDescription());
               result.setNodes(operation.getNodes());
               result.setStatusCode(425);
               Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
               nodes4Result.put(nodePath, new TreeNode(nodePath));
               result.setNodes(nodes4Result);
               results.add(result);
               continue;
            }
            
            // Add, Success
            this.registry.addItem(nodePath, value);
            RegistryItem currentItem = this.registry.findItem(nodePath);
            // Link DDF
            currentItem.setDdfNode(ddfNode);
            // Create children items
            this.buildRegistry(currentItem, ddfNode.getChildren());
            
            ManagementOperationResult result = new ManagementOperationResult();
            result.setCommand(operation.getDescription());
            result.setNodes(operation.getNodes());
            result.setStatusCode(200);
            Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
            nodes4Result.put(nodePath, new TreeNode(nodePath));
            result.setNodes(nodes4Result);
            results.add(result);
          }
        }
    }
    return results;
  }

  private List<ManagementOperationResult> processDeleteOperation(DeleteManagementOperation operation) throws DMException {
    Map<String, TreeNode> nodes = operation.getNodes();
    List<ManagementOperationResult> results = new ArrayList<ManagementOperationResult>();
    for (String nodePath : nodes.keySet()) {
        RegistryItem item = this.registry.findItem(nodePath);
        if (item == null) {
           // 404: Not found
           ManagementOperationResult result = new ManagementOperationResult();
           result.setCommand(operation.getDescription());
           result.setNodes(operation.getNodes());
           result.setStatusCode(404);
           Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
           nodes4Result.put(nodePath, new TreeNode(nodePath));
           result.setNodes(nodes4Result);
           results.add(result);
           continue;
        } else {
            // Check access
            DDFNode ddfNode = findDDFNodeByNodePath(this.model, nodePath);
            if (ddfNode == null || !ddfNode.imply(DDFNode.ACCESS_TYPE_DELETE)) {
               // 425: Permission denied
               ManagementOperationResult result = new ManagementOperationResult();
               result.setCommand(operation.getDescription());
               result.setNodes(operation.getNodes());
               result.setStatusCode(425);
               Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
               nodes4Result.put(nodePath, new TreeNode(nodePath));
               result.setNodes(nodes4Result);
               results.add(result);
               continue;
            }
            
            // Delete, Success
            this.registry.deleteItem(nodePath);
            
            ManagementOperationResult result = new ManagementOperationResult();
            result.setCommand(operation.getDescription());
            result.setNodes(operation.getNodes());
            result.setStatusCode(200);
            Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
            nodes4Result.put(nodePath, new TreeNode(nodePath));
            result.setNodes(nodes4Result);
            results.add(result);
        }
    }
    return results;
  }
  
  private List<ManagementOperationResult> processReplaceOperation(ReplaceManagementOperation operation) throws DMException {
    Map<String, TreeNode> nodes = operation.getNodes();
    List<ManagementOperationResult> results = new ArrayList<ManagementOperationResult>();
    for (String nodePath : nodes.keySet()) {
        TreeNode treeNode = nodes.get(nodePath);
        Object value = null;
        if (treeNode != null && treeNode.getValue() != null) {
           value = treeNode.getValue();
        }
        RegistryItem item = this.registry.findItem(nodePath);
        if (item == null) {
           // (404) Not Found
           ManagementOperationResult result = new ManagementOperationResult();
           result.setCommand(operation.getDescription());
           result.setNodes(operation.getNodes());
           result.setStatusCode(404);
           Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
           nodes4Result.put(nodePath, new TreeNode(nodePath));
           result.setNodes(nodes4Result);
           results.add(result);
           continue;
        } else {
            // Check access
            DDFNode ddfNode = findDDFNodeByNodePath(this.model, nodePath);
            if (ddfNode == null || !ddfNode.imply(DDFNode.ACCESS_TYPE_REPLACE)) {
               // 425: Permission denied
               ManagementOperationResult result = new ManagementOperationResult();
               result.setCommand(operation.getDescription());
               result.setNodes(operation.getNodes());
               result.setStatusCode(425);
               Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
               nodes4Result.put(nodePath, new TreeNode(nodePath));
               result.setNodes(nodes4Result);
               results.add(result);
               continue;
            }
            
            // Replace, Success
            this.setValue(nodePath, value);
            
            ManagementOperationResult result = new ManagementOperationResult();
            result.setCommand(operation.getDescription());
            result.setNodes(operation.getNodes());
            result.setStatusCode(200);
            Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
            nodes4Result.put(nodePath, new TreeNode(nodePath));
            result.setNodes(nodes4Result);
            results.add(result);
        }
    }
    return results;
  }

  private List<ManagementOperationResult> processGetOperation(GetManagementOperation operation) throws DMException {
    Map<String, TreeNode> nodes = operation.getNodes();
    List<ManagementOperationResult> results = new ArrayList<ManagementOperationResult>();
    for (String nodePath : nodes.keySet()) {
        RegistryItem item = this.registry.findItem(nodePath);
        
        if (item == null) {
           // (404) Not Found
           ManagementOperationResult result = new ManagementOperationResult();
           result.setCommand(operation.getDescription());
           result.setStatusCode(404);
           Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
           nodes4Result.put(nodePath, new TreeNode(nodePath));
           result.setNodes(nodes4Result);
           results.add(result);
        } else {
            // Check access
            DDFNode ddfNode = findDDFNodeByNodePath(this.model, nodePath);
            if ( (!nodePath.startsWith("./DevDetail") && !nodePath.startsWith("./DevInfo"))
                && (ddfNode == null || !ddfNode.imply(DDFNode.ACCESS_TYPE_GET))) {
               // 425: Permission denied
               ManagementOperationResult result = new ManagementOperationResult();
               result.setCommand(operation.getDescription());
               result.setStatusCode(425);
               Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
               nodes4Result.put(nodePath, new TreeNode(nodePath));
               result.setNodes(nodes4Result);
               results.add(result);
            } else {
              // Get, Success
              RegistryItem currentItem = this.registry.findItem(nodePath);
              TreeNode node = new TreeNode(nodePath);
              if (currentItem.getDdfNode() != null && currentItem.getDdfNode().getFormat().equals(DDFNode.DDF_FORMAT_NODE)) {
                 Set<RegistryItem> children = currentItem.getChildren();
                 if (children.size() > 0) {
                    StringBuffer v = new StringBuffer();
                    for (RegistryItem child: children) {
                        v.append(child.getName());
                        v.append('/');
                    }
                    String value = v.toString().substring(0, v.length() -1);
                    node.setValue(value);
                 }
                 node.setFormat(DDFNode.DDF_FORMAT_NODE);
              } else {
                node.setFormat(DDFNode.DDF_FORMAT_CHR);
                node.setValue(currentItem.getValue());
              }
              Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
              nodes4Result.put(nodePath, node);
              
              ManagementOperationResult result = new ManagementOperationResult();
              result.setCommand(operation.getDescription());
              result.setStatusCode(200);
              result.setNodes(nodes4Result);
              results.add(result);
            }
        }
    }
    return results;
  }

  private List<ManagementOperationResult> processExecOperation(ExecManagementOperation operation) throws DMException {
    Map<String, TreeNode> nodes = operation.getNodes();
    List<ManagementOperationResult> results = new ArrayList<ManagementOperationResult>();
    for (String nodePath : nodes.keySet()) {
        RegistryItem item = this.registry.findItem(nodePath);
        
        if (item == null) {
           // (404) Not Found
           ManagementOperationResult result = new ManagementOperationResult();
           result.setCommand(operation.getDescription());
           result.setStatusCode(404);
           Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
           nodes4Result.put(nodePath, new TreeNode(nodePath));
           result.setNodes(nodes4Result);
           results.add(result);
        } else {
            // Check access
            DDFNode ddfNode = findDDFNodeByNodePath(this.model, nodePath);
            if (ddfNode == null || !ddfNode.imply(DDFNode.ACCESS_TYPE_EXEC)) {
               // 425: Permission denied
               ManagementOperationResult result = new ManagementOperationResult();
               result.setCommand(operation.getDescription());
               result.setStatusCode(425);
               Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
               nodes4Result.put(nodePath, new TreeNode(nodePath));
               result.setNodes(nodes4Result);
               results.add(result);
            } else {
              // Exec, Success
              ManagementOperationResult result = new ManagementOperationResult();
              result.setCommand(operation.getDescription());
              result.setStatusCode(200);
              Map<String, TreeNode> nodes4Result = new HashMap<String, TreeNode>();
              nodes4Result.put(nodePath, new TreeNode(nodePath));
              result.setNodes(nodes4Result);
              results.add(result);
            }
        }
    }
    return results;
  }
  // Public methods ***********************************************************************

  /**
   * judgment existent of nodepath
   * 
   * @param nodePath
   * @return
   * @throws DMException
   */
  public boolean exists(String nodePath) throws DMException {
    return this.registry.exists(nodePath);
  }

  /**
   * get the value of the nodepath
   * 
   * @param nodePath
   * @return
   * @throws DMException
   */
  public Object getValue(String nodePath) throws DMException {
    return this.registry.getValue(nodePath);
  }

  /**
   * set the value of nodepath
   * 
   * @param nodePath
   * @param value
   */
  public void setValue(String nodePath, Object value) throws DMException {
    if (this.exists(nodePath)) {
       this.registry.setValue(nodePath, value);
    } else {
      this.registry.addItem(nodePath, value);
      RegistryItem item = this.registry.findItem(nodePath);
      DDFNode ddfNode = findDDFNodeByNodePath(this.model, nodePath);
      item.setDdfNode(ddfNode);
    }
  }
  
  // -------------------------------------------------------------------------------------
  // Implements OMAClientEmulator interface ***********************************************
 
  /* (non-Javadoc)
   * @see com.npower.dm.client.OMAClientEmulator#initialize()
   */
  public void initialize() {
    this.buildDefaultRegistry(this.model);
    // Build DevInfo
    // Build DevDetail
  }

  /* (non-Javadoc)
   * @see com.npower.dm.client.OMAClientEmulator#process(sync4j.framework.engine.dm.ManagementOperation[])
   */
  public ManagementOperationResult[] process(ManagementOperation[] operations) throws DMException {

    List<ManagementOperationResult> results = new ArrayList<ManagementOperationResult>();
    for (int j = 0; j < operations.length; j++) {
        if (operations[j] instanceof TreeManagementOperation) {
          // Add
          if (operations[j] instanceof AddManagementOperation) {
             List<ManagementOperationResult> result4Add = this.processAddOperation((AddManagementOperation)operations[j]);
             results.addAll(result4Add);
          } else if (operations[j] instanceof DeleteManagementOperation) {
            // Delete
            List<ManagementOperationResult> result4Delete = this.processDeleteOperation((DeleteManagementOperation)operations[j]);
            results.addAll(result4Delete);
          } else if (operations[j] instanceof GetManagementOperation) {
            // Get
            List<ManagementOperationResult> result4Get = this.processGetOperation((GetManagementOperation)operations[j]);
            results.addAll(result4Get);
          } else if (operations[j] instanceof ReplaceManagementOperation) {
            // Replace
            List<ManagementOperationResult> result4Replace = this.processReplaceOperation((ReplaceManagementOperation)operations[j]);
            results.addAll(result4Replace);
          } else if (operations[j] instanceof ExecManagementOperation) {
            // Get
            List<ManagementOperationResult> result4Exec = this.processExecOperation((ExecManagementOperation)operations[j]);
            results.addAll(result4Exec);
          }
      } else if (operations[j] instanceof UserAlertManagementOperation) {
        throw new DMException("Unsupport Alert operation");
      } else if (operations[j] instanceof SequenceManagementOperation) {
        //throw new DMException("Unsupport Sequence operation");
        SequenceManagementOperation oper = (SequenceManagementOperation)operations[j];
        return this.process(oper.getOperations());
      } else if (operations[j] instanceof AtomicManagementOperation) {
        throw new DMException("Unsupport Atomic operation");
      }

    }
    return (ManagementOperationResult[]) results.toArray(new ManagementOperationResult[0]);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.client.OMAClientEmulator#reset()
   */
  public void reset() {
    this.initialize();
  }

}
