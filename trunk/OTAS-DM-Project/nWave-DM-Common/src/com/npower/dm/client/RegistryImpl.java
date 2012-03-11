/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/client/RegistryImpl.java,v 1.2 2006/12/28 06:51:43 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2006/12/28 06:51:43 $
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/12/28 06:51:43 $
 */
public class RegistryImpl implements Registry {

  private static Log log = LogFactory.getLog(RegistryImpl.class);
  
  private RegistryItem root;
  
  /**
   * Default Constrctuor
   */
  public RegistryImpl() {
    super();
    this.root = new RegistryItem();
    this.root.setName(".");
    this.root.setParent(null);
  }
  
  // Private methods -----------------------------------------------------------

  /**
   * Find registry item by its path.
   * 
   * @param nodePath
   * @param rootItem
   *        root item
   */
  private RegistryItem getRegistryItem(String nodePath, RegistryItem item) {
    if (item.getPath().equals(nodePath)) {
       return item;
    }
    
    for (RegistryItem children: item.getChildren()) {
        RegistryItem result = this.getRegistryItem(nodePath, children);
        if (result != null) {
           return result;
        }
    }
    return null;
  }
  
  // Public methods -----------------------------------------------------------

  /* (non-Javadoc)
   * @see com.npower.dm.client.Registry#getRoot()
   */
  public RegistryItem getRoot() {
    return root;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.client.Registry#findItem(java.lang.String)
   */
  public RegistryItem findItem(String nodePath) {
    if (StringUtils.isEmpty(nodePath)) {
       return null;
    }
    
    nodePath = nodePath.trim();
    if (nodePath.startsWith("/")) {
       nodePath = "." + nodePath;
    }
    if (!nodePath.startsWith("./")) {
       nodePath = "./" + nodePath;
    }
    RegistryItem rootItem = this.getRoot();
    return getRegistryItem(nodePath, rootItem);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.client.Registry#addItem(java.lang.String, java.lang.Object)
   */
  public void addItem(String nodePath, Object value) {
    String path = nodePath;
    if (path.startsWith("./")) {
      path = nodePath.substring(2, path.length());
    }

    // 找被增加节点的父节点
    int index = path.lastIndexOf("/");
    RegistryItem item = null;
    if (index > 0) {
       path = path.substring(0, index);
       item = this.findItem(path);
    } else {
      item = this.getRoot();
    }
    RegistryItem additem = new RegistryItem();
    additem.setName(nodePath.substring(nodePath.lastIndexOf("/") + 1, nodePath.length()));
    additem.setValue(value);

    additem.setParent(item);
    item.getChildren().add(additem);
    
    log.info("Registry:Add item:" + nodePath + " with value: " + value);

  }

  /* (non-Javadoc)
   * @see com.npower.dm.client.Registry#deleteItem(java.lang.String)
   */
  public void deleteItem(String nodePath) {

    String path = nodePath;
    if (path.startsWith("./")) {
       path = nodePath.substring(2, path.length());
    }

    RegistryItem item = this.findItem(path);

    if (item != null) {
      RegistryItem parentitem = item.getParent();
      parentitem.getChildren().remove(item);
      item.getChildren().clear();

    }

  }

  /* (non-Javadoc)
   * @see com.npower.dm.client.Registry#getValue(java.lang.String)
   */
  public Object getValue(String nodePath) throws DMException {

    String path = nodePath;
    if (path.startsWith("./")) {
      path = nodePath.substring(2, path.length());
    }

    RegistryItem item = this.findItem(path);

    if (item != null) {
      return item.getValue();
    }

    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.client.Registry#setValue(java.lang.String, java.lang.Object)
   */
  public void setValue(String nodePath, Object value) throws DMException {

    String path = nodePath;
    if (path.startsWith("./")) {
      path = nodePath.substring(2, path.length());
    }
    RegistryItem item = this.findItem(path);
    item.setValue(value);
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.client.Registry#exists(java.lang.String)
   */
  public boolean exists(String nodePath) throws DMException {

    String path = nodePath;
    if (path.startsWith("./")) {
      path = nodePath.substring(2, path.length());
    }
    RegistryItem item = this.findItem(path);

    if (item != null) {
      return true;
    }

    return false;

  }

}
