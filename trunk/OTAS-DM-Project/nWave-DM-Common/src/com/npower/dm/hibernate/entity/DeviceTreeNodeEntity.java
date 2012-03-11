/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/DeviceTreeNodeEntity.java,v 1.11 2008/12/11 05:21:29 zhao Exp $
 * $Revision: 1.11 $
 * $Date: 2008/12/11 05:21:29 $
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.Hibernate;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.DeviceTreeNode;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.11 $ $Date: 2008/12/11 05:21:29 $
 */
public class DeviceTreeNodeEntity extends AbstractDeviceTreeNode implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public DeviceTreeNodeEntity() {
    super();
  }

  /** full constructor */
  public DeviceTreeNodeEntity(String nodeName, DeviceTreeNode parentNode, String value) {
    super(nodeName, parentNode);
    //Clob clob = null;
    if (value != null) {
       //clob = Hibernate.createClob(value);
       //this.setRawData(clob);
      this.setRawData(value);
    }
    if (parentNode != null && parentNode.getIsLeafNode()) {
       parentNode.setIsLeafNode(false);
    }
  }

  /** full constructor */
  public DeviceTreeNodeEntity(String nodeName, DeviceTreeNode parentNode) {
    super(nodeName, parentNode);
    if (parentNode != null && parentNode.getIsLeafNode()) {
       parentNode.setIsLeafNode(false);
    }
  }

  /**
   * caculate the nodePath.
   * The path will start with "./"
   * @return
   */
  public String getNodePath() {
    
    StringBuffer result = new StringBuffer();
    String name = this.getName();
    result.append(name);
    DeviceTreeNode parent = this.getParentNode();
    while (parent != null) {
          result.insert(0, "/");
          name = parent.getName();
          result.insert(0, name);
          parent = parent.getParentNode();
    }
    return result.toString();
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.core.DeviceTreeNode#setBinary(byte[])
   */
  public void setBinary(byte[] bytes) {
    this.setBinaryData(Hibernate.createBlob(bytes));
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.core.DeviceTreeNode#getInputStream()
   */
  public InputStream getInputStream() throws DMException {
    try {
      Blob binData = this.getBinaryData();
      if (binData != null) {
         return binData.getBinaryStream();
      }
      return null;
    } catch (SQLException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.DeviceTreeNode#getSize()
   */
  public int getSize() throws Exception {
    if (DDFNode.DDF_FORMAT_BIN.equalsIgnoreCase(this.getFormat())) {
       return this.getBinarySize();
    } else {
      String content = this.getStringValue();
      return (content == null)?0:content.length();
    }
  }
  
  
  private int getBinarySize() throws Exception {
    int size = 0;
    
    try {
      InputStream ins = this.getInputStream();
      if (ins == null) {
         return 0;
      }
      int b = ins.read();
      while (b >= 0) {
            size++;
            b = ins.read();
      }
      ins.close();
    } catch (DMException e) {
      throw e;
    } catch (IOException e) {
      throw e;
    }
    return size;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.DeviceTreeNode#getBytes()
   */
  public byte[] getBytes() throws DMException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
        byte[] buf = new byte[512];
        InputStream in = this.getInputStream();
        if (in == null) {
           return new byte[0];
        }
        int len = in.read(buf);
        while (len > 0) {
              out.write(buf, 0, len);
              len = in.read(buf);
        }
        in.close();
        return out.toByteArray();
    } catch (IOException e) {
      throw new DMException("Error in read binary bytes.", e);
    }
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return this.getNodePath();
  }

}
