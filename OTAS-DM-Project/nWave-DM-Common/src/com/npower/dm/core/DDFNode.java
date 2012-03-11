/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/DDFNode.java,v 1.13 2008/12/11 05:21:29 zhao Exp $
 * $Revision: 1.13 $
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
package com.npower.dm.core;

import java.util.List;
import java.util.Set;

/**
 * <p>Represent a DDF Node.</p>
 * @author Zhao DongLu
 * @version $Revision: 1.13 $ $Date: 2008/12/11 05:21:29 $
 */
public interface DDFNode extends Comparable<DDFNode> {
  
  /**
   * Name of Dynamic Node
   */
  public static final String NAME_OF_DYNAMIC_NODE = "${NodeName}";

  /**
   * DDF Format
   */
  public static final String DDF_FORMAT_B64 = "b64";

  public static final String DDF_FORMAT_BIN = "bin";

  public static final String DDF_FORMAT_BOOL = "bool";

  public static final String DDF_FORMAT_CHR = "chr";

  public static final String DDF_FORMAT_INT = "int";

  public static final String DDF_FORMAT_NODE = "node";

  public static final String DDF_FORMAT_NULL = null;

  public static final String DDF_FORMAT_XML = "xml";

  /**
   * DDF Occurrence
   */
  public static final String DDF_OCCURRENCE_One = "One";

  public static final String DDF_OCCURRENCE_ZeroOrOne = "ZeroOrOne";

  public static final String DDF_OCCURRENCE_ZeroOrMore = "ZeroOrMore";

  public static final String DDF_OCCURRENCE_OneOrMore = "OneOrMore";

  public static final String DDF_OCCURRENCE_ZeroOrN = "ZeroOrN";

  public static final String DDF_OCCURRENCE_OneOrN = "OneOrN";

  /**
   * Access Types
   */
  public static final String ACCESS_TYPE_ADD = "Add";

  public static final String ACCESS_TYPE_COPY = "Copy";

  public static final String ACCESS_TYPE_DELETE = "Delete";

  public static final String ACCESS_TYPE_EXEC = "Exec";

  public static final String ACCESS_TYPE_GET = "Get";

  public static final String ACCESS_TYPE_REPLACE = "Replace";

  /**
   * Default Text Mime Type
   */
  public static final String DEFAULT_MIMETYPE = "text/plain";

  /**
   * Default Binary Mime Type
   */
  public static final String MIMETYPE_BINARY = "application/octet-stream";

  /**
   * Return ID.
   * @return
   */
  public abstract long getID();

  /**
   * Return the DDFTree which own this DDF Node.
   * @return
   */
  public abstract DDFTree getDdfTree();

  /**
   * Boudle the node to a ddf tree.
   * 
   * @param tree
   */
  public abstract void setDdfTree(DDFTree tree);

  /**
   * Return the parent node of this node
   * @return
   */
  public abstract DDFNode getParentDDFNode();

  /**
   * Set a parent node for this node.
   * @param nwDmDdfNode
   */
  public abstract void setParentDDFNode(DDFNode nwDmDdfNode);

  /**
   * Return true, if this node is leaf.
   * @return
   */
  public abstract boolean getIsLeafNode();

  /**
   * Set true, if this node is leaf.
   * @param isLeafNode
   */
  public abstract void setIsLeafNode(boolean isLeafNode);

  /**
   * Return the name of this node.
   * @return
   */
  public abstract String getName();

  /**
   * Set a name for this node
   * @param name
   */
  public abstract void setName(String name);

  /**
   * Return the description of this node.
   * @return
   */
  public abstract String getDescription();

  /**
   * Set a description for this node.
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * Return the title of this node. 
   * @return
   */
  public abstract String getTitle();

  /**
   * Set a title for this node.
   * @param title
   */
  public abstract void setTitle(String title);

  /**
   * Return the format of this node.
   * @return
   */
  public abstract String getFormat();

  /**
   * Set a format for this node 
   * @param format
   */
  public abstract void setFormat(String format);

  /**
   * Return the occurrence of this node.
   * @return
   */
  public abstract String getOccurrence();

  /**
   * Set a occurrence for this node.
   * @param occurrence
   */
  public abstract void setOccurrence(String occurrence);

  /**
   * return the maxmium occurrence .
   * @return
   */
  public abstract long getMaxOccurrence();

  /**
   * Set the maxmium occurence,
   * @param maxOccurrence
   */
  public abstract void setMaxOccurrence(long maxOccurrence);

  /**
   * Return true, if this node is dynamic.
   * @return
   */
  public abstract boolean getIsDynamic();

  /**
   * Set true, if this node is dynamic.
   * @param isDynamic
   */
  public abstract void setIsDynamic(boolean isDynamic);

  /**
   * Return the ddf document name of this node.
   * @return
   */
  public abstract String getDdfDocumentName();

  /**
   * Set a document name for this node.
   * @param ddfDocumentName
   */
  public abstract void setDdfDocumentName(String ddfDocumentName);

  /**
   * Return the value of this node.
   * @return
   */
  public abstract String getValue();

  /**
   * Set a value for this node.
   * @param value
   */
  public abstract void setValue(String value);

  /**
   * Return the default value of this node.
   * @return
   */
  public abstract String getDefaultValue();

  /**
   * Set a default value for this node.
   * @param defaultValue
   */
  public abstract void setDefaultValue(String defaultValue);

  /**
   * Return a set of children.
   * @return Return a <code>Set</code> of {@link com.npower.dm.core.DDFNode} objects.
   */
  public abstract Set<DDFNode> getChildren();

  //public abstract void setChildren(Set ddfNodes);

  /**
   * Return a set of mime types.
   * @return Return a <code>Set</code> of {@link com.npower.dm.core.DDFNodeMIMEType} objects.
   */
  public abstract Set<DDFNodeMIMEType> getDdfNodeMIMETypes();

  /**
   * Set mime types 
   * @param ddfNodeMimeTypes  A <code>Set</code> of {@link com.npower.dm.core.DDFNodeMIMEType} objects.
   */
  //public abstract void setDdfNodeMIMETypes(Set ddfNodeMimeTypes);

  /**
   * Return a set of access types.
   * @return Return a <code>Set</code> of {@link com.npower.dm.core.DDFNodeAccessType} objects.
   * @return
   */
  public abstract Set<DDFNodeAccessType> getDdfNodeAccessTypes();

  /**
   * Set access types 
   * @param ddfNodeMimeTypes  A <code>Set</code> of {@link com.npower.dm.core.DDFNodeAccessType} objects.
   */
  //public abstract void setDdfNodeAccessTypes(Set ddfNodeAccessTypes);

  /**
   * Return a set of NodeMappings which reference this NodeMapping.
   * @return Return a <code>Set</code> of {@link com.npower.dm.core.ProfileNodeMapping} objects.
   */
  public abstract Set<ProfileNodeMapping> getProfileNodeMappings();
  
  //public abstract void setProfileNodeMappings(Set profileNodeMappings);

  //public abstract Set getMappingNodeNames();

  //public abstract void setMappingNodeNames(Set mappingNodeNames);

  /**
   * Return a set of ProfileMappings which reference this DDFNode as RootNode.
   * @return
   */
  public abstract Set<ProfileMapping> getProfileMappings();

  //public abstract void setProfileMappings(Set profileMappings);

  //public abstract Set getProfileParameters();

  //public abstract void setProfileParameters(Set profileParameters);

  /**
   * Check the permission by Access Type: Add, Delete, Exec, Get, Replace
   * 
   * @param accessType
   * @return
   */
  public boolean imply(String accessType);

  /**
   * Return the MIMEType of the node. If the node didn't defined a mime type,
   * and DFType of the node is DDFName, the method will return null.
   * 
   * @return
   */
  public abstract String getMIMETypeString();
  
  /**
   * caculate the nodePath.
   * If the node's name is null, will instead with "${NodeName}" defined by DDFNode.NAME_OF_DYNAMIC_NODE.
   * The path will start with "./"
   * @return
   */
  public String getNodePath();
  
  /**
   * 返回Vector.
   * 注：结果中不包含根节点.
   * 
   * 例如: 如果当前节点为/A/B/C, 则返回的数组对应DDF节点为: {A, B, C}.
   * 
   * @return
   */
  public List<DDFNode> getVector();
  
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
  public String caculateRelativeNodePath(DDFNode rootNode) throws DMException;
  
  /**
   * Dump the DDFNode to the String for displaying purpose.
   * 
   * @param node
   * @return
   */
  public abstract String dump();
}