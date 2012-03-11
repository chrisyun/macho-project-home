/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/DDFNodeEntity.java,v 1.14 2008/12/11 05:21:29 zhao Exp $
 * $Revision: 1.14 $
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFNodeMIMEType;
import com.npower.dm.core.DMException;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.14 $ $Date: 2008/12/11 05:21:29 $
 */
public class DDFNodeEntity extends AbstractDDFNode implements java.io.Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1L;

  

  // Constructors
  /**
   * default constructor The DDF Node by default is a leaf node, permanently and
   * max occurrence is -1.
   * 
   */
  public DDFNodeEntity() {
    // Set default value
    super.setIsDynamic(false);
    super.setIsLeafNode(true);
    super.setMaxOccurrence(-1L);
  }

  /*
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  /*
  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof DDFNode))
      return false;
    DDFNode castOther = (DDFNode) other;
    return new EqualsBuilder().append(this.getID(), castOther.getID()).isEquals();
  }
  */
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(this.getID()).toHashCode();
  }
  

  /**
   * Using for validating the DDF in process of Digester, not save into DM
   * inventory. make sure the <path> od first node in DDF is begining "./" or
   * "."
   * 
   * Caution: The validation for the path had been commentted. In some vender's DDF each
   * node included the <Path>, so validation always failure!
   * 
   * @param path
   * @throws DMException
   */
  public void setPath(String path) throws DMException {
    // Ignore the validation. See the caution in the method's JavaDoc.
    if (true) {
      return;
    }
    if (path != null && !path.equals("./") && !path.equals(".")) {
      throw new DMException("Make sure the <path> included by first node in DDF is begining './' or '.'");
    }
  }

  // Methods of DDF Format for Common Digester
  // *************************************************************
  /**
   * DDFFormat Setter for Digester
   * 
   * @param value
   */
  public void setFormatB64(String value) {
    this.setFormat(DDFNode.DDF_FORMAT_B64);
  }

  /**
   * DDFFormat Setter for Digester
   * 
   * @param value
   */
  public void setFormatBin(String value) {
    this.setFormat(DDFNode.DDF_FORMAT_BIN);
  }

  /**
   * DDFFormat Setter for Digester
   * 
   * @param value
   */
  public void setFormatBool(String value) {
    this.setFormat(DDFNode.DDF_FORMAT_BOOL);
  }

  /**
   * DDFFormat Setter for Digester
   * 
   * @param value
   */
  public void setFormatChr(String value) {
    this.setFormat(DDFNode.DDF_FORMAT_CHR);
  }

  /**
   * DDFFormat Setter for Digester
   * 
   * @param value
   */
  public void setFormatInt(String value) {
    this.setFormat(DDFNode.DDF_FORMAT_INT);
  }

  /**
   * DDFFormat Setter for Digester
   * 
   * @param value
   */
  public void setFormatNode(String value) {
    this.setFormat(DDFNode.DDF_FORMAT_NODE);
  }

  /**
   * DDFFormat Setter for Digester
   * 
   * @param value
   */
  public void setFormatXml(String value) {
    this.setFormat(DDFNode.DDF_FORMAT_XML);
  }

  /**
   * DDFFormat Setter for Digester
   * 
   * @param value
   */
  public void setFormatNull(String value) {
    this.setFormat(DDFNode.DDF_FORMAT_NULL);
  }

  // Methods of DDF Occurrence for Common Digester
  // ****************************************************************
  /**
   * Occurrence Setter for Digester
   * 
   * @param value
   */
  public void setOccurrenceOne(String value) {
    this.setOccurrence(DDF_OCCURRENCE_One);
  }

  /**
   * Occurrence Setter for Digester
   * 
   * @param value
   */
  public void setOccurrenceZeroOrOne(String value) {
    this.setOccurrence(DDF_OCCURRENCE_ZeroOrOne);
  }

  /**
   * Occurrence Setter for Digester
   * 
   * @param value
   */
  public void setOccurrenceZeroOrMore(String value) {
    this.setOccurrence(DDF_OCCURRENCE_ZeroOrMore);
  }

  /**
   * Occurrence Setter for Digester
   * 
   * @param value
   */
  public void setOccurrenceOneOrMore(String value) {
    this.setOccurrence(DDF_OCCURRENCE_OneOrMore);
  }

  /**
   * Occurrence Setter for Digester
   * 
   * @param value
   */
  public void setOccurrenceZeroOrN(String value) {
    this.setOccurrence(DDF_OCCURRENCE_ZeroOrN);
  }

  /**
   * Occurrence Setter for Digester
   * 
   * @param value
   */
  public void setOccurrenceOneOrN(String value) {
    this.setOccurrence(DDF_OCCURRENCE_OneOrN);
  }

  // Methods of DDF scope for Common Digester
  /**
   * Set Permanent scope for common digester.
   * 
   * @param value
   */
  public void setScopePermanent(String value) {
    this.setIsDynamic(false);
  }

  /**
   * Set Dynamic scope for common digester.
   * 
   * @param value
   */
  public void setScopeDynamic(String value) {
    this.setIsDynamic(true);
  }

  // Methods of Access Types for Common Digester
  /**
   * Set Access Type for Common Digester
   * 
   * @param value
   */
  public void setAccessTypeAdd(String value) {
    boolean exists = false;
    Set types = this.getDdfNodeAccessTypes();
    for (Iterator i = types.iterator(); i.hasNext();) {
      DDFNodeAccessTypeEntity t = (DDFNodeAccessTypeEntity) i.next();
      if (ACCESS_TYPE_ADD.equalsIgnoreCase(t.getID().getAccessType())) {
        exists = true;
        break;
      }
    }
    if (!exists) {
      DDFNodeAccessTypeID id = new DDFNodeAccessTypeID();
      id.setAccessType(ACCESS_TYPE_ADD);
      DDFNodeAccessTypeEntity type = new DDFNodeAccessTypeEntity(id, this);

      types.add(type);
      this.setDdfNodeAccessTypes(types);
    }
  }

  /**
   * Set Access Type for Common Digester
   * 
   * @param value
   */
  public void setAccessTypeCopy(String value) {
    boolean exists = false;
    Set types = this.getDdfNodeAccessTypes();
    for (Iterator i = types.iterator(); i.hasNext();) {
      DDFNodeAccessTypeEntity t = (DDFNodeAccessTypeEntity) i.next();
      if (ACCESS_TYPE_COPY.equalsIgnoreCase(t.getID().getAccessType())) {
        exists = true;
        break;
      }
    }
    if (!exists) {
      DDFNodeAccessTypeID id = new DDFNodeAccessTypeID();
      id.setAccessType(ACCESS_TYPE_COPY);
      DDFNodeAccessTypeEntity type = new DDFNodeAccessTypeEntity(id, this);

      types.add(type);
      this.setDdfNodeAccessTypes(types);
    }
  }

  /**
   * Set Access Type for Common Digester
   * 
   * @param value
   */
  public void setAccessTypeDelete(String value) {
    boolean exists = false;
    Set types = this.getDdfNodeAccessTypes();
    for (Iterator i = types.iterator(); i.hasNext();) {
      DDFNodeAccessTypeEntity t = (DDFNodeAccessTypeEntity) i.next();
      if (ACCESS_TYPE_DELETE.equalsIgnoreCase(t.getID().getAccessType())) {
        exists = true;
        break;
      }
    }
    if (!exists) {
      DDFNodeAccessTypeID id = new DDFNodeAccessTypeID();
      id.setAccessType(ACCESS_TYPE_DELETE);
      DDFNodeAccessTypeEntity type = new DDFNodeAccessTypeEntity(id, this);

      types.add(type);
      this.setDdfNodeAccessTypes(types);
    }
  }

  /**
   * Set Access Type for Common Digester
   * 
   * @param value
   */
  public void setAccessTypeExec(String value) {
    boolean exists = false;
    Set types = this.getDdfNodeAccessTypes();
    for (Iterator i = types.iterator(); i.hasNext();) {
      DDFNodeAccessTypeEntity t = (DDFNodeAccessTypeEntity) i.next();
      if (ACCESS_TYPE_EXEC.equalsIgnoreCase(t.getID().getAccessType())) {
        exists = true;
        break;
      }
    }
    if (!exists) {
      DDFNodeAccessTypeID id = new DDFNodeAccessTypeID();
      id.setAccessType(ACCESS_TYPE_EXEC);
      DDFNodeAccessTypeEntity type = new DDFNodeAccessTypeEntity(id, this);

      types.add(type);
      this.setDdfNodeAccessTypes(types);
    }
  }

  /**
   * Set Access Type for Common Digester
   * 
   * @param value
   */
  public void setAccessTypeGet(String value) {
    boolean exists = false;
    Set types = this.getDdfNodeAccessTypes();
    for (Iterator i = types.iterator(); i.hasNext();) {
      DDFNodeAccessTypeEntity t = (DDFNodeAccessTypeEntity) i.next();
      if (ACCESS_TYPE_GET.equalsIgnoreCase(t.getID().getAccessType())) {
        exists = true;
        break;
      }
    }
    if (!exists) {
      DDFNodeAccessTypeID id = new DDFNodeAccessTypeID();
      id.setAccessType(ACCESS_TYPE_GET);
      DDFNodeAccessTypeEntity type = new DDFNodeAccessTypeEntity(id, this);

      types.add(type);
      this.setDdfNodeAccessTypes(types);
    }
  }

  /**
   * Set Access Type for Common Digester
   * 
   * @param value
   */
  public void setAccessTypeReplace(String value) {
    boolean exists = this.imply(ACCESS_TYPE_REPLACE);

    if (!exists) {
      DDFNodeAccessTypeID id = new DDFNodeAccessTypeID();
      id.setAccessType(ACCESS_TYPE_REPLACE);
      DDFNodeAccessTypeEntity type = new DDFNodeAccessTypeEntity(id, this);

      Set types = this.getDdfNodeAccessTypes();
      types.add(type);
      this.setDdfNodeAccessTypes(types);
    }
  }

  /**
   * Set or add the MIME Type into the node for Common Digester
   * 
   * @param value
   */
  public void setMimeTypeString(String value) {
    if (value == null || value.trim().length() == 0) {
       value = DDFNode.DEFAULT_MIMETYPE;
    }
    DDFNodeMIMETypeID id = new DDFNodeMIMETypeID();
    id.setMimeType(value);
    DDFNodeMIMEType type = new DDFNodeMIMETypeEntity(id, this);
    Set types = this.getDdfNodeMIMETypes();
    types.add(type);
  }

  /**
   * Add a child node into the node.
   * 
   * @param node
   */
  public void add(DDFNode node) {
    this.getChildren().add(node);

    this.setIsLeafNode(false);
    // Link the child node to DDFTreeEntity as same as parent node
    node.setDdfTree(this.getDdfTree());
    // Link the child node to parent node
    node.setParentDDFNode(this);
  }

  // Common methods
  // ********************************************************************************************
  /**
   * Check the permission by Access Type: Add, Delete, Exec, Get, Replace
   * 
   * @param accessType
   * @return
   */
  public boolean imply(String accessType) {
    boolean permit = false;
    Set types = this.getDdfNodeAccessTypes();
    for (Iterator i = types.iterator(); i.hasNext();) {
      DDFNodeAccessTypeEntity t = (DDFNodeAccessTypeEntity) i.next();
      if (accessType.equalsIgnoreCase(t.getID().getAccessType())) {
        permit = true;
        break;
      }
    }
    return permit;
  }

  /**
   * return the MIMEType of the node. If the node didn't defined a mime type,
   * and DFType of the node is DDFName, the method will return null.
   * 
   * @return
   */
  public String getMIMETypeString() {
    Set types = this.getDdfNodeMIMETypes();
    if (!types.isEmpty()) {
      DDFNodeMIMEType type = (DDFNodeMIMEType) types.iterator().next();
      return type.getID().getMimeType();
    }
    return null;
  }

  public void setName(String name) {
    name = (name == null || name.trim().length() == 0) ? null : name.trim();
    super.setName(name);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.DDFNode#getVector()
   */
  public List<DDFNode> getVector() {
    List<DDFNode> result = new ArrayList<DDFNode>();
    DDFNode currentNode = this;
    do {
       if (!".".equals(currentNode.getName())) {
          result.add(0, currentNode);
       }
       currentNode = currentNode.getParentDDFNode();
    } while (currentNode != null);
    return result;
  }
  
  /**
   * caculate the nodePath.
   * If the node's name is null, will instead with "${NodeName}" defined by DDFNode.NAME_OF_DYNAMIC_NODE.
   * The path will start with "./"
   * @return
   */
  public String getNodePath() {
    
    StringBuffer result = new StringBuffer();
    String name = this.getName();
    name = (name == null)?DDFNode.NAME_OF_DYNAMIC_NODE:name;
    result.append(name);
    DDFNode parent = this.getParentDDFNode();
    while (parent != null) {
          result.insert(0, "/");
          name = parent.getName();
          name = (name == null)?DDFNode.NAME_OF_DYNAMIC_NODE:name;
          result.insert(0, name);
          parent = parent.getParentDDFNode();
    }
    String path = result.toString();
    if (!path.startsWith("./")) {
       return "./" + path;
    } else {
      return path;
    }
  }
  
  /**
   * Caculate relative node path.
   * For exmaple:
   *   RootNode: /AP/A1
   *   Node: /AP/A1/B1/B2/B3
   *   Will return result: B1/B2/B3
   *   
   * @param rootNode
   * @return
   * @throws DMException
   */
  public String caculateRelativeNodePath(DDFNode rootNode) throws DMException {
    assert rootNode != null: "RootNode is null";

    if (this.getID() == rootNode.getID()) {
       return "";
    }
    DDFNode parent = this.getParentDDFNode();
    boolean isParent = false;
    while (parent != null) {
          if (parent.getID() == rootNode.getID()) {
             isParent = true;
             break;
          }
          parent = parent.getParentDDFNode();
    }
    if (!isParent) {
       throw new DMException("The RootNode: " + rootNode.getID() + " doesn't include the childnode: " + this.getID());
    }
    
    StringBuffer result = new StringBuffer();
    String name = this.getName();
    name = (name == null)?"${NodeName}":name;
    result.append(name);
    parent = this.getParentDDFNode();
    while (parent != null && parent.getID() != rootNode.getID()) {
          result.insert(0, "/");
          name = parent.getName();
          name = (name == null)?"${NodeName}":name;
          result.insert(0, name);
          parent = parent.getParentDDFNode();
    }
    return result.toString();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(T)
   */
  public int compareTo(DDFNode other) {
    return this.getNodePath().compareTo(other.getNodePath());
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return this.getNodePath();
  }

}
