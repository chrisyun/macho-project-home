/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDDFNode.java,v 1.4 2006/12/28 10:02:04 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2006/12/28 10:02:04 $
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFTree;

/**
 * Represent AttributeTypeValue.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2006/12/28 10:02:04 $
 */
public abstract class AbstractDDFNode implements java.io.Serializable, DDFNode {

  // Fields

  private long ID;

  private DDFTree ddfTree;

  private DDFNode parentDDFNode;

  private boolean isLeafNode;

  private String name;

  private String description;

  private String title;

  private String format;

  private String occurrence;

  private long maxOccurrence;

  private boolean isDynamic;

  private String ddfDocumentName;

  private String value;

  private String defaultValue;

  private Set ddfNodeMIMETypes = new HashSet(0);

  private Set ddfNodeAccessTypes = new HashSet(0);

  private Set children = new HashSet(0);

  private Set profileParameters = new HashSet(0);

  private Set mappingNodeNames = new HashSet(0);

  private Set profileNodeMappings = new HashSet(0);

  private Set profileMappings = new HashSet(0);

  // Constructors


  /** default constructor */
  public AbstractDDFNode() {
    super();
  }

  /** minimal constructor */
  public AbstractDDFNode(boolean isLeafNode) {
    this.isLeafNode = isLeafNode;
  }

  /** full constructor */
  public AbstractDDFNode(DDFTree nwDmDdfTree, DDFNode nwDmDdfNode, boolean isLeafNode, String name, String description,
      String title, String format, String occurrence, long maxOccurrence, boolean isDynamic, String ddfDocumentName,
      String value, String defaultValue, Set nwDmProfileParameters, Set nwDmDdfNodeMimeTypes, Set nwDmMappingNodeNames,
      Set nwDmProfileNodeMappings, Set nwDmDdfNodes, Set nwDmProfileMappings, Set nwDmDdfNodeAccessTypes) {
    this.ddfTree = nwDmDdfTree;
    this.parentDDFNode = nwDmDdfNode;
    this.isLeafNode = isLeafNode;
    this.name = name;
    this.description = description;
    this.title = title;
    this.format = format;
    this.occurrence = occurrence;
    this.maxOccurrence = maxOccurrence;
    this.isDynamic = isDynamic;
    this.ddfDocumentName = ddfDocumentName;
    this.value = value;
    this.defaultValue = defaultValue;
    this.profileParameters = nwDmProfileParameters;
    this.ddfNodeMIMETypes = nwDmDdfNodeMimeTypes;
    this.mappingNodeNames = nwDmMappingNodeNames;
    this.profileNodeMappings = nwDmProfileNodeMappings;
    this.children = nwDmDdfNodes;
    this.profileMappings = nwDmProfileMappings;
    this.ddfNodeAccessTypes = nwDmDdfNodeAccessTypes;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getID()
   */
  public long getID() {
    return this.ID;
  }

  public void setID(long ddfNodeId) {
    this.ID = ddfNodeId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getDdfTree()
   */
  public DDFTree getDdfTree() {
    return this.ddfTree;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setDdfTree(com.npower.dm.hibernate.DDFTree)
   */
  public void setDdfTree(DDFTree nwDmDdfTree) {
    this.ddfTree = nwDmDdfTree;
  }

  public DDFNode getParentDDFNode() {
    return this.parentDDFNode;
  }

  public void setParentDDFNode(DDFNode nwDmDdfNode) {
    this.parentDDFNode = nwDmDdfNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getIsLeafNode()
   */
  public boolean getIsLeafNode() {
    return this.isLeafNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setIsLeafNode(boolean)
   */
  public void setIsLeafNode(boolean isLeafNode) {
    this.isLeafNode = isLeafNode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getTitle()
   */
  public String getTitle() {
    return this.title;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setTitle(java.lang.String)
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getFormat()
   */
  public String getFormat() {
    return this.format;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setFormat(java.lang.String)
   */
  public void setFormat(String format) {
    this.format = format;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getOccurrence()
   */
  public String getOccurrence() {
    return this.occurrence;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setOccurrence(java.lang.String)
   */
  public void setOccurrence(String occurrence) {
    this.occurrence = occurrence;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getMaxOccurrence()
   */
  public long getMaxOccurrence() {
    return this.maxOccurrence;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setMaxOccurrence(long)
   */
  public void setMaxOccurrence(long maxOccurrence) {
    this.maxOccurrence = maxOccurrence;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getIsDynamic()
   */
  public boolean getIsDynamic() {
    return this.isDynamic;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setIsDynamic(boolean)
   */
  public void setIsDynamic(boolean isDynamic) {
    this.isDynamic = isDynamic;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getDdfDocumentName()
   */
  public String getDdfDocumentName() {
    return this.ddfDocumentName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setDdfDocumentName(java.lang.String)
   */
  public void setDdfDocumentName(String ddfDocumentName) {
    this.ddfDocumentName = ddfDocumentName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getValue()
   */
  public String getValue() {
    return this.value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setValue(java.lang.String)
   */
  public void setValue(String value) {
    this.value = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getDefaultValue()
   */
  public String getDefaultValue() {
    return this.defaultValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setDefaultValue(java.lang.String)
   */
  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getProfileParameters()
   */
  public Set getProfileParameters() {
    return this.profileParameters;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setProfileParameters(java.util.Set)
   */
  public void setProfileParameters(Set nwDmProfileParameters) {
    this.profileParameters = nwDmProfileParameters;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getDdfNodeMIMETypes()
   */
  public Set getDdfNodeMIMETypes() {
    return this.ddfNodeMIMETypes;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setDdfNodeMIMETypes(java.util.Set)
   */
  public void setDdfNodeMIMETypes(Set nwDmDdfNodeMimeTypes) {
    this.ddfNodeMIMETypes = nwDmDdfNodeMimeTypes;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getMappingNodeNames()
   */
  public Set getMappingNodeNames() {
    return this.mappingNodeNames;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setMappingNodeNames(java.util.Set)
   */
  public void setMappingNodeNames(Set nwDmMappingNodeNames) {
    this.mappingNodeNames = nwDmMappingNodeNames;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getProfileNodeMappings()
   */
  public Set getProfileNodeMappings() {
    return this.profileNodeMappings;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setProfileNodeMappings(java.util.Set)
   */
  public void setProfileNodeMappings(Set nwDmProfileNodeMappings) {
    this.profileNodeMappings = nwDmProfileNodeMappings;
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

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getProfileMappings()
   */
  public Set getProfileMappings() {
    return this.profileMappings;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setProfileMappings(java.util.Set)
   */
  public void setProfileMappings(Set nwDmProfileMappings) {
    this.profileMappings = nwDmProfileMappings;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#getDdfNodeAccessTypes()
   */
  public Set getDdfNodeAccessTypes() {
    return this.ddfNodeAccessTypes;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.DDFNode#setDdfNodeAccessTypes(java.util.Set)
   */
  public void setDdfNodeAccessTypes(Set nwDmDdfNodeAccessTypes) {
    this.ddfNodeAccessTypes = nwDmDdfNodeAccessTypes;
  }

  /**
   * Implements DDFNode methods
   */
  public String dump() {
    return dumpToString(this);
  }

  /**
   * Dump the DDFNodeEntity to the String for displaying purpose.
   * 
   * @param node
   * @return
   */
  public static String dumpToString(DDFNode node) {
    String prefix = "";
    StringBuffer buffer = new StringBuffer();
    dump(buffer, prefix, node);
    return buffer.toString();
  }

  /**
   * Resursion Method for dmup all of DDFNodeEntity children.
   * 
   * @param result
   * @param prefix
   * @param node
   * @return
   */
  private static String dump(StringBuffer result, String prefix, DDFNode node) {
    if (node == null) {
      result.append("Node is NULL");
      return result.toString();
    }
    result.append(prefix);
    result.append("+-");
    result.append((node.getName() == null) ? DDFNode.NAME_OF_DYNAMIC_NODE : node.getName());
    result.append("\n");

    Set children = node.getChildren();
    for (Iterator i = children.iterator(); i.hasNext();) {
      DDFNode child = (DDFNode) i.next();
      dump(result, prefix + "  ", child);
    }
    return result.toString();

  }
}