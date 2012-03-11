/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/Registry.java,v 1.5 2008/12/04 09:59:46 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2008/12/04 09:59:46 $
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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.util.DDFTreeHelper;
import com.npower.dm.util.DMUtil;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2008/12/04 09:59:46 $
 */
public class Registry implements Serializable {
  
  private RegistryItem root = null;

  private int nameCounter = 0;
  
  private int index4Item = 0;
  
  /**
   * Default Constractor
   */
  public Registry() {
    super();
    initialize();
  }

  /**
   * 
   */
  private void initialize() {
    this.root = new RegistryItem(index4Item++);
    this.root.setParent(null);
    this.root.setName(".");
  }
  
  /**
   * Return all of items below the item.
   * @param result
   * @param item
   */
  private void getItems(List<RegistryItem> result, RegistryItem item) {
    for (RegistryItem child: item.getChildren()) {
        result.add(child);
        if (child.getChildren().size() > 0) {
           this.getItems(result, child);
        }
    }
  }
  
  
  
  // Public methods -----------------------------------------------------------------------------------
  
  /**
   * Clear registry.
   */
  public void clear() {
    this.initialize();
  }
  
  /**
   * <pre>
   * Find a item wich matched with node path.
   * 
   * Eg: 
   * NodePath
   *  ./a/b/c
   *  ./aa/${NodeName:bb}/cc/ddd
   *  ./aa/${NodeName:bb}/cc/${NodeName}/eee
   *  
   * </pre>
   * @param nodePath
   *        Node Path
   * @return
   */
  public RegistryItem find(String nodePath) {
    if (nodePath.equals(".")) {
       return this.root;
    }
    List<String> paths = DDFTreeHelper.getPathVector(nodePath);
    for (RegistryItem item: this.getItems()) {
        int position = item.getPosition();
        if (position != paths.size()) {
           continue;
        }

        boolean equals = true;
        int k = 0;
        
        List<RegistryItem> ancestors = item.getAncestor();
        // add current item into ancestors
        ancestors.add(item);
        
        for (RegistryItem ancestor: ancestors) {
            String currentPath = paths.get(k++);
            
            String pathName = DDFTreeHelper.getName(currentPath);
            String ancestorName = ancestor.getName();
            
            if (ancestorName != null && ancestorName.equals(pathName)) {
               continue;
            }
            if (ancestor.getDdfNode().getName() == null) {
               // Dynamic node
               if (pathName == null) {
                  continue;
               }
               
            }
            equals = false;
            break;
        }
        if (equals) {
           return item;
        }
    }
    return null;
  }
  
  /**
   * <pre>
   * Add a registry item.
   * 
   * Eg: 
   * NodePath
   *  ./a/b/c
   *  ./aa/${NodeName:bb}/cc/ddd
   *  ./aa/${NodeName:bb}/cc/${NodeName}/eee
   *  
   *  </pre>
   * @param nodePath
   *        Node Path.
   * @param node
   *        DDF definition.
   * @return
   */
  public RegistryItem addRegistryItem(String nodePath, DDFNode node) {
    if (StringUtils.isEmpty(nodePath)) {
       nodePath = node.getNodePath();
    }
    
    RegistryItem item = this.find(nodePath);
    if (item != null) {
       // Exists!
       return item;
    }
    
    DDFNode parentDDFNode = node.getParentDDFNode();
    String parentPath = DDFTreeHelper.getParentPath(nodePath);
    
    RegistryItem parentItem = null;
    if (parentDDFNode != null) {
       parentItem = this.addRegistryItem(parentPath, parentDDFNode);
    } else {
      parentItem = this.root;
    }
    
    item = new RegistryItem(index4Item++);
    if (node.getName() == null) {
       // Dynamic Node
       String name = DDFTreeHelper.getName(nodePath);
       if (StringUtils.isEmpty(name)) {
          // Generate Dynamic Name
         this.generateNameFor(item);
       } else {
         item.setName(name);
         item.setGenerated(false);
       }
    } else {
      List<String> path = DDFTreeHelper.getPathVector(nodePath);
      if (path.size() == 0) {
         return null;
      }
      String name = path.get(path.size() - 1);
      item.setName(name);
    }
    
    item.setDdfNode(node);
    parentItem.addChild(item);
    
    return item;
  }
  
  /**
   * Add a item into registry.
   * @param basePath
   * @param nodeMapping
   * @return
   */
  public RegistryItem addRegistryItem(String basePath, ProfileNodeMapping nodeMapping) {
    DDFNode ddfNode = nodeMapping.getDdfNode();

    // 根据相对路径或绝对路径计算实际路径
    String nodePath = null;
    String relativePath = nodeMapping.getNodeRelativePath();
    if (StringUtils.isNotEmpty(relativePath)) {
       nodePath = DDFTreeHelper.concat(basePath, relativePath);
    } else {
      nodePath = ddfNode.getNodePath();
    }
    // 创建注册项
    RegistryItem item = this.addRegistryItem(nodePath, ddfNode);
    if (item != null) {
       item.setProfileNodeMapping(nodeMapping);
    }
    return item;
  }
  
  /**
   * Generate a new name for item.
   * Generator a Node name: Name_Prefix + counter.
   * @param item
   * @return
   */
  public String generateNameFor(RegistryItem item) {
    nameCounter++;
    String newName = DMUtil.NAME_PREFIX_FOR_NO_NAME_NODE + nameCounter;
    item.setName(newName);
    item.setGenerated(true);
    return newName;
  }
  
  /**
   * Return all of descendant items.
   * @return
   */
  public List<RegistryItem> getItems() {
    List<RegistryItem> result = new ArrayList<RegistryItem>();
    RegistryItem item = this.root;
    getItems(result, item);
    return result;
  }
  
  /**
   * Return all of dynamic items.
   * @return
   */
  public List<RegistryItem> getDynamicItems() {
    List<RegistryItem> result = new ArrayList<RegistryItem>();
    for (RegistryItem item: this.getItems()) {
        if (item.getDdfNode().getName() == null) {
           result.add(item);
        }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    for (RegistryItem item: this.getItems()) {
        for (int i = 0; i < item.getPosition(); i++) {
            buffer.append("    ");
        }
        buffer.append(item.toString());
        buffer.append('\n');
    }
    return buffer.toString();
  }

}
