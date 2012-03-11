/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/client/RegistryItem.java,v 1.2 2007/08/29 06:21:00 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/08/29 06:21:00 $
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
package com.npower.dm.client;

import java.util.HashSet;
import java.util.Set;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFNodeAccessType;
import com.npower.dm.core.DDFNodeMIMEType;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/08/29 06:21:00 $
 */
public class RegistryItem {

  private String name;

  private Object value;

  private RegistryItem parent;

  private Set<RegistryItem> children = new HashSet<RegistryItem>();

  private DDFNode ddfNode;

  /**
   * 
   */
  public RegistryItem() {
    super();
  }
  /**
   * @return the accessTypes
   */
  public Set<DDFNodeAccessType> getAccessTypes() {
    return this.getDdfNode().getDdfNodeAccessTypes();
  }

  /**
   * @return the children
   */
  public Set<RegistryItem> getChildren() {
    return children;
  }
  /**
   * @param children the children to set
   */
  public void setChildren(Set<RegistryItem> children) {
    this.children = children;
  }
  /**
   * @return the ddfNode
   */
  public DDFNode getDdfNode() {
    return ddfNode;
  }
  /**
   * @param ddfNode the ddfNode to set
   */
  public void setDdfNode(DDFNode ddfNode) {
    this.ddfNode = ddfNode;
  }

  /**
   * @return the mIMETypes
   */
  public Set<DDFNodeMIMEType> getMIMETypes() {
    return this.getDdfNode().getDdfNodeMIMETypes();
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * @return the parent
   */
  public RegistryItem getParent() {
    return parent;
  }
  /**
   * @param parent the parent to set
   */
  public void setParent(RegistryItem parent) {
    this.parent = parent;
  }

  /**
   * @return the value
   */
  public Object getValue() {
    return value;
  }
  /**
   * @param value the value to set
   */
  public void setValue(Object value) {
    this.value = value;
  }
  
  /**
   * Return path of this node.
   * @return
   */
  public String getPath() {
    StringBuffer result = new StringBuffer();
    result.append(this.getName());
    
    RegistryItem parent = this.getParent();
    if (parent != null) {
       result.insert(0, "/");
       result.insert(0, parent.getPath());
    }
    return result.toString();
  }

}
