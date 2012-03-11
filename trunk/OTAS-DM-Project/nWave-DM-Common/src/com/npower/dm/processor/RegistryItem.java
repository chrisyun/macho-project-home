/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/RegistryItem.java,v 1.6 2008/12/04 09:59:46 zhao Exp $
  * $Revision: 1.6 $
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
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.ProfileNodeMapping;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/12/04 09:59:46 $
 */
public class RegistryItem implements Serializable, Comparable<RegistryItem> {
  
  /**
   * 节点在Registry被添加的次序, 值越大添加到Registry越晚
   */
  private int index = 0;
  
  /**
   * Name of item
   */
  private String name = null;
  
  /**
   * Flag: indicate the name is generated.
   */
  private boolean generated = false;
  
  /**
   * Value of item
   */
  private Object value = null;

  private DDFNode ddfNode = null;
  private ProfileNodeMapping profileNodeMapping = null;
  private String typeOfProfileAttribute = null;
  
  /**
   * Parent of item
   */
  private RegistryItem parent = null;
  
  /**
   * Children of item
   */
  private List<RegistryItem> children = new ArrayList<RegistryItem>();

  /**
   * Default constractor
   */
  public RegistryItem(int index) {
    super();
    this.index = index;
  }

  /**
   * @return the typeOfProfileAttribute
   */
  public String getTypeOfProfileAttribute() {
    return typeOfProfileAttribute;
  }

  /**
   * @param typeOfProfileAttribute the typeOfProfileAttribute to set
   */
  public void setTypeOfProfileAttribute(String typeOfProfileAttribute) {
    this.typeOfProfileAttribute = typeOfProfileAttribute;
  }

  // Private methods -----------------------------------------------------------------
  /**
   * 
   * Return path of item.
   * Eg:
   *   ./a/b
   *   ./
   *   ./a
   *   ./a/${NodeName:bbbb}/c
   *   ./a/d/${NodeName}/c
   *   
   * @return
   */
  private String getDDFPath() {
    RegistryItem parent = this.getParent();
    if (parent == null) {
       return this.getName();
    }
    
    StringBuffer result = new StringBuffer();
    if (StringUtils.isEmpty(this.getDdfNode().getName())) {
       // Dynamic Node
       if (StringUtils.isEmpty(this.getName())) {
          result.append("${NodeName}");
       } else {
         result.append("${NodeName:" + this.getName() + "}");
       }
    } else {
      // Static Node
      result.append(this.getName());
    }
    
    if (parent != null) {
          result.insert(0, '/');
          result.insert(0, parent.getDDFPath());
    }
    return result.toString();
  }
  
  // Public methods -----------------------------------------------------------------
  /**
   * Return children of this item.
   * @return the children
   */
  public List<RegistryItem> getChildren() {
    // Sort children by deepth ASC
    Set<RegistryItem> sortedSet = new java.util.TreeSet<RegistryItem>(this.children);
    List<RegistryItem> result = new ArrayList<RegistryItem>(sortedSet);
    return result;
  }

  /**
   * Add children into this item.
   * @param children the children to set
   */
  public void setChildren(List<RegistryItem> children) {
    this.children = children;
    for (RegistryItem item: children) {
        item.setParent(this);
    }
  }
  
  /**
   * Add a child into this item.
   * @param child
   */
  public void addChild(RegistryItem child) {
    this.children.add(child);
    child.setParent(this);
  }

  /**
   * Return DDF definition of this item.
   * @return the ddfNode
   */
  public DDFNode getDdfNode() {
    return ddfNode;
  }

  /**
   * Set DDF definition of this item.
   * @param ddfNodeID the ddfNodeID to set
   */
  public void setDdfNode(DDFNode ddfNode) {
    this.ddfNode = ddfNode;
  }

  /**
   * Return name of this item.
   * @return the name
   */
  public String getName() {
    if (this.getParent() == null) {
       // Is Root!
       return this.name;
    }
    if (StringUtils.isNotEmpty(this.getDdfNode().getName())) {
       return this.getDdfNode().getName();
    } else {
      return name;
    }
  }

  /**
   * Set name of this item.
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the generated
   */
  public boolean isGenerated() {
    return generated;
  }

  /**
   * @param generated the generated to set
   */
  public void setGenerated(boolean generated) {
    this.generated = generated;
  }

  /**
   * Return parent item of this item.
   * @return the parent
   */
  public RegistryItem getParent() {
    return parent;
  }

  /**
   * Set parent item of this item.
   * @param parent the parent to set
   */
  public void setParent(RegistryItem parent) {
    this.parent = parent;
  }

  /**
   * Return profile node mapping.
   * @return the profileNodeMappingID
   */
  public ProfileNodeMapping getProfileNodeMapping() {
    return profileNodeMapping;
  }

  /**
   * Set profile node mapping.
   * @param profileNodeMappingID the profileNodeMappingID to set
   */
  public void setProfileNodeMapping(ProfileNodeMapping profileNodeMapping) {
    this.profileNodeMapping = profileNodeMapping;
    if (this.profileNodeMapping != null && this.profileNodeMapping.getProfileAttribute() != null && this.profileNodeMapping.getProfileAttribute().getProfileAttribType() != null) {
       this.typeOfProfileAttribute = this.profileNodeMapping.getProfileAttribute().getProfileAttribType().getName();
    }
  }

  /**
   * Return value of this item.
   * @return the value
   */
  public Object getValue() {
    return value;
  }

  /**
   * Set value of this item.
   * @param value the value to set
   */
  public void setValue(Object value) {
    this.value = value;
  }
  
  /**
   * Return real path of this item.
   * Eg:
   *   ./a/b
   *   ./
   *   ./a
   *   ./a/b/c
   *   
   * @return
   */
  public String getPath() {
    RegistryItem parent = this.getParent();
    if (parent == null) {
       return this.getName();
    }
    
    StringBuffer result = new StringBuffer();
    result.append(this.getName());
    if (parent != null) {
          result.insert(0, '/');
          result.insert(0, parent.getPath());
    }
    return result.toString();
  }
  
  /**
   * Return deepth of the sub-tree
   * 返回包含的子树的最大深度
   * @return
   */
  public int getDeepth() {
    if (this.getChildren() == null || this.getChildren().size() == 0) {
       return 0;
    } else {
      int deepth = 0;
      for (RegistryItem item: this.getChildren()) {
          int childDeepth = item.getDeepth();
          // 找到最深的子树
          if (deepth < childDeepth) {
             deepth = childDeepth;
          }
      }
      return 1 + deepth;
    }
  }
  
  /**
   * Position of item from Root item.
   * @return
   */
  public int getPosition() {
    if (this.getParent() == null) {
       return 0;
    } else {
      return 1 + this.getParent().getPosition();
    }
  }
  
  /**
   * <pre>
   * Return all of ancestor sort by older in desc.
   * For example:
   * Current Item: ./A/B/C/D will return:
   * ./A
   * ./A/B
   * ./A/B/C
   * </pre>
   * @return
   */
  public List<RegistryItem> getAncestor() {
    List<RegistryItem> result = new ArrayList<RegistryItem>();
    RegistryItem item = this.getParent();
    while (item.getParent() != null) {
          result.add(0, item);
          item = item.getParent();
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[");
    String n = (this.getName() == null)?"":this.getName();
    buffer.append(n);
    buffer.append(": ");
    buffer.append(this.getDDFPath());
    buffer.append(", ");
    buffer.append(this.getPath());
    buffer.append("]");
    return buffer.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(RegistryItem o) {
    int result = this.getPosition() - o.getPosition();
    if (result != 0) {
       // 根据所处的位置，越处于顶端的越小
       return result;
    }
    result = this.getDeepth() - o.getDeepth();
    if (result != 0) {
       // 根据所包含子树的深度, 深度越小则越小
       return result;
    } else {
      // 根据加入Registry的次序, 加入越早, 则越小
      result = this.getIndex() - o.getIndex();
      if (result != 0) {
         return result;
      } else {
        // 否则根据节点名称
        String name = this.getName();
        if (name == null) {
           return -1;
        } else {
          return name.compareTo(o.getName());
        }
      }
    }
  }

  /**
   * @return the index
   */
  public int getIndex() {
    return index;
  }

  /**
   * @param index the index to set
   */
  public void setIndex(int index) {
    this.index = index;
  }

}
