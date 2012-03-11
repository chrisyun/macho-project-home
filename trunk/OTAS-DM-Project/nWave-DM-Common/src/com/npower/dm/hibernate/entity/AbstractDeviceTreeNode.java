/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDeviceTreeNode.java,v 1.8 2007/03/28 05:16:41 zhao Exp $
 * $Revision: 1.8 $
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
package com.npower.dm.hibernate.entity;

import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.DMException;
import com.npower.dm.core.DeviceTreeNode;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2007/03/28 05:16:41 $
 */
public abstract class AbstractDeviceTreeNode implements java.io.Serializable, DeviceTreeNode, Comparable {

  // Fields

  private long ID;

  private boolean isLeafNode = true;

  private String name;

  private DeviceTreeNode parentNode;

  private String format;

  private String type;

  private String MSize;

  private long MVersion;

  private String MTitle;

  private String MTStamp;

  private String MACL;

  private Date lastRead;

  private String itemDataKind;

  private String updateId;

  private String rawData;

  private Blob binaryData;

  private long changeVersion;

  private Set DeviceTrees = new HashSet(0);

  private Set children = new TreeSet();

  // Constructors

  /** default constructor */
  public AbstractDeviceTreeNode() {
    super();
  }


  /** full constructor */
  public AbstractDeviceTreeNode(String nodeName, DeviceTreeNode parentNode) {
    this.name = nodeName;
    this.parentNode = parentNode;
  }

  // Property accessors

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTreeNode#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long deviceTreeNodeId) {
    this.ID = deviceTreeNodeId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTreeNode#getIsLeafNode()
   */
  public boolean getIsLeafNode() {
    return this.isLeafNode;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTreeNode#setIsLeafNode(long)
   */
  public void setIsLeafNode(boolean isLeafNode) {
    this.isLeafNode = isLeafNode;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTreeNode#getName()
   */
  public String getName() {
    return this.name;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTreeNode#setName(java.lang.String)
   */
  public void setName(String nodeName) {
    this.name = nodeName;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTreeNode#getParentNode()
   */
  public DeviceTreeNode getParentNode() {
    return this.parentNode;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTreeNode#setParentNode(long)
   */
  public void setParentNode(DeviceTreeNode parentNode) {
    this.setIsLeafNode(false);
    this.parentNode = parentNode;
  }

  public String getFormat() {
    return this.format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMSize() {
    return this.MSize;
  }

  public void setMSize(String MSize) {
    this.MSize = MSize;
  }

  public long getMVersion() {
    return this.MVersion;
  }

  public void setMVersion(long MVersion) {
    this.MVersion = MVersion;
  }

  public String getMTitle() {
    return this.MTitle;
  }

  public void setMTitle(String MTitle) {
    this.MTitle = MTitle;
  }

  public String getMTStamp() {
    return this.MTStamp;
  }

  public void setMTStamp(String MTstamp) {
    this.MTStamp = MTstamp;
  }

  public String getMACL() {
    return this.MACL;
  }

  public void setMACL(String MAcl) {
    this.MACL = MAcl;
  }

  public Date getLastRead() {
    return this.lastRead;
  }

  public void setLastRead(Date lastRead) {
    this.lastRead = lastRead;
  }

  public String getItemDataKind() {
    return this.itemDataKind;
  }

  public void setItemDataKind(String itemDataKind) {
    this.itemDataKind = itemDataKind;
  }

  public String getUpdateId() {
    return this.updateId;
  }

  public void setUpdateId(String updateId) {
    this.updateId = updateId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTreeNode#getRawData()
   */
  public String getRawData() {
    return this.rawData;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTreeNode#setRawData(java.sql.Clob)
   */
  public void setRawData(String rawData) {
    this.rawData = rawData;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTreeNode#getBinaryData()
   */
  public Blob getBinaryData() {
    return this.binaryData;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.DeviceTreeNode#setBinaryData(java.sql.Blob)
   */
  public void setBinaryData(Blob binaryData) {
    this.binaryData = binaryData;
  }

  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getChildren()
   */
  public Set getChildren() {
    return this.children;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setChildren(java.util.Set)
   */
  public void setChildren(Set nwDmDdfNodes) {
    this.children = nwDmDdfNodes;
  }

  public Set getDeviceTrees() {
    return this.DeviceTrees;
  }

  public void setDeviceTrees(Set nwDmDtrees) {
    this.DeviceTrees = nwDmDtrees;
  }
  
  /**
   * Read the Clob data into a string
   * @return
   * @throws DMException
   */
  public String getStringValue() throws DMException {
    return this.getRawData();
    /*
    Clob clob = this.getRawData();
    if (clob == null) {
       return null;
    }
    try {
        BufferedReader reader = new BufferedReader(clob.getCharacterStream());
        String line = reader.readLine();
        StringBuffer buffer = new StringBuffer();
        while (line != null) {
              buffer.append(line);
              line = reader.readLine();
        }
        return buffer.toString();
    } catch (SQLException e) {
      throw new DMException(e);
    } catch (IOException e) {
      throw new DMException(e);
    }
    */
  }
  
  /**
   * Set a string value into RawData
   * @throws DMException
   */
  public void setStringValue(String value) throws DMException {
    this.setRawData(value);
    /*
    if (value != null) {
       this.setRawData(Hibernate.createClob(value));
    } else {
      this.setRawData(null);
    }
    */
  }
  
  // Implements Comparable interface ****************************************************
  public int compareTo(Object other) {
    if (other == null) {
       return 1;
    }
    if (other instanceof DeviceTreeNode) {
       DeviceTreeNode otherNode = (DeviceTreeNode)other;
       return this.getName().compareTo(otherNode.getName());
    } else {
      return -1;
    }
  }
  

}