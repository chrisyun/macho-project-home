/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/DeviceTreeNode.java,v 1.5 2007/03/28 05:16:41 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2007/03/28 05:16:41 $
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
package com.npower.dm.core;

import java.io.InputStream;
import java.util.Set;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/03/28 05:16:41 $
 */
public interface DeviceTreeNode {
  
  /**
   * Constance: Name of root node.
   */
  public final static String ROOT_NODE_NAME = ".";

  /**
   * Return the node's ID
   * @return
   */
  public abstract long getID();

  /**
   * Return true, if the node is leaf.
   * @return
   */
  public abstract boolean getIsLeafNode();

  /**
   * Set true, if this is a leaf.
   * @param isLeafNode
   */
  public abstract void setIsLeafNode(boolean isLeafNode);

  /**
   * Return the name of this node.
   * @return
   */
  public abstract String getName();

  /**
   * Set a name of this node.
   * @param nodeName
   */
  public abstract void setName(String nodeName);

  /**
   * Return Format
   * @return
   */
  public String getFormat();

  /**
   * Set format
   * @param MFormat
   */
  public void setFormat(String MFormat);

  /**
   * Return Type
   * @return
   */
  public String getType();

  /**
   * Set type
   * @param MType
   */
  public void setType(String MType);

  /**
   * Return the parent of this node.
   * @return
   */
  public abstract DeviceTreeNode getParentNode();

  /**
   * Set parent for this node.
   * @param parentNode
   */
  public abstract void setParentNode(DeviceTreeNode parentNode);

  /**
   * Return the set of children.
   * @return Return <code>Set</code> of {@link com.npower.dm.core.DeviceTreeNode} objects.
   */
  public abstract Set<DeviceTreeNode> getChildren();

  /**
   * Return the value of this node.
   * Read the Clob data into a string
   * @return
   * @throws DMException
   */
  public abstract String getStringValue() throws DMException;
  
  /**
   * Set a value for this node.
   * 
   * Set a string value into RawData
   * @throws DMException
   */
  public abstract void setStringValue(String value) throws DMException;
  
  /**
   * caculate the nodePath.
   * The path will start with "./"
   * @return
   */
  public String getNodePath();
  
  /**
   * Set binary data, assign bytes as content of Binary.
   * @param bytes
   */
  public void setBinary(byte[] bytes);
  
  /**
   * Return binary data as InputStream
   * @return
   * @throws DMException
   */
  public InputStream getInputStream() throws DMException;

  /**
   * Return size of binary data
   * @return
   * @throws Exception
   */
  public int getSize() throws Exception;

  /**
   * Return binary data as byte[].
   * @return
   * @throws DMException
   */
  public byte[] getBytes() throws DMException ;
  
}