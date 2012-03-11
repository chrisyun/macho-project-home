/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/DDFTreeBean.java,v 1.13 2008/12/11 05:21:29 zhao Exp $
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
package com.npower.dm.management;

import java.io.InputStream;
import java.util.Set;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;

/**
 * All operations against DDFTree, DDFNode will be conducted through this DDFTreeBean.
 * @author Zhao DongLu
 * 
 */
public interface DDFTreeBean extends BaseBean {

  // Method's related with DDFTreeEntity
  /**
   * Return a DDFTreeEntity found by id.
   * 
   * @param id
   *          String
   * @throws DMException
   */
  public abstract DDFTree getDDFTreeByID(String id) throws DMException;

  /**
   * Return a DDFTreeEntity found by id.
   * @param id
   * @return
   * @throws DMException
   */
  public abstract DDFTree getDDFTreeByID(long id) throws DMException;
  
  /**
   * Add a DDFTreeEntity into DeviceEntity Inventory.
   * 
   * @param tree
   * @throws DMException
   */
  public abstract void addDDFTree(DDFTree tree) throws DMException;

  /**
   * Parsing a DDF inputstream and return the DDFTreeEntity result based on the
   * content of input.
   * 
   * @param in
   *          InputStream
   * @return DDFTreeEntity
   * @throws DMException
   */
  public abstract DDFTree parseDDFTree(InputStream in) throws DMException;

  /**
   * Delete the DDFNodeEntity, all of children nodes, MIMETypes and AccessTypes
   * related with these nodes.
   * 
   * @param node
   * @throws DMException
   */
  public abstract void delete(DDFNode node) throws DMException;

  /**
   * Delete the DDFTreeEntity and DDF_Nodes, MIMETypes, AccessTypes related with
   * the Tree.
   * 
   * @param tree
   * @throws DMException
   */
  public abstract void delete(DDFTree tree) throws DMException;
 
  /**
   * Find DDF Node by id.
   * @param id
   * @return
   * @throws DMException
   */
  public abstract DDFNode getDDFNodeByID(String id) throws DMException;
  
  /**
   * Find DDF Node by id.
   * @param id
   * @return
   * @throws DMException
   */
  public abstract DDFNode getDDFNodeByID(long id) throws DMException;
  
  /**
   * Find the DDF node in all of trees owned by the ModelEntity. More
   * information about nodePath and process of finding, see alse
   * findDDFNodeByNodePath(DDFTreeEntity tree, String nodePath)
   * 
   * @param model
   * @param nodePath
   * @return
   * @throws DMException
   */
  public abstract DDFNode findDDFNodeByNodePath(Model model, String nodePath) throws DMException;

  /**
   * <pre>
   * Find a DDFNodeEntity in DDFTreeEntity by the node Path.
   * 
   * Example Tree (This Tree include three RootNode: A, B, C), the diagram is
   * structure of the tree :
   * 
   * A -- A1 -- A11 
   *   |- A2 -- A21
   * 
   * B -- B1 -- B12 
   *   |- B2 -- B21
   * 
   * C -- NULL -- C12 
   *           |- C13
   * 
   * ************************************************* 
   * Node Path            Results 
   * ./A/A1/               A1
   * /A/A1                 A1 
   * ./C/${NodeName}/C12   C12
   * ./C/${NodeName:}/C12  C12
   * ./C/${NodeName:aaa}/C12  C12
   * ./C/${NodeName:bbb}/C12  C12
   * ./C/${NodeName:1111}/C12  C12
   * ./C/C1/C13            null 
   * ./A                   A 
   * ./                    throw DMException 
   * /                     throw DMException 
   * ./D                   null
   * *************************************************
   * 
   * Notice: ${NodeName} is indicator which means dynamic node. When
   * ${NodeName} apear in NodePath, must match a DDFNodeEntity which name is
   * null or "".
   * 
   * Notice: ${NodeName}, ${NodeName:}, ${NodeName:aaaaaa} are validate format.
   * 
   * </pre>
   * @param tree
   * @param nodePath
   * @return
   * @throws DMException
   */
  public abstract DDFNode findDDFNodeByNodePath(DDFTree tree, String nodePath) throws DMException;

  /**
   * Recusion method for find a DDFNodeEntity in Set of nodes by nodePath. The
   * first path in the nodePath will matched with one of the rootNodesSet.
   * 
   * For exmaple: RootNodeSet like as:
   * 
   * a1/a12/a121 b1/b12/b121
   * 
   * nodePath like as: b1/b12/b121
   * 
   * ************************************************************************************************
   * Bug 20060405 , case: RootNodeSet Likes as: aa/aa1/aaa1 aaa/aaa1/aaaa1
   * 
   * nodePath like as : aaa/aaa1
   * 
   * This case will cause a error, first node "" will matched, so recurse into a
   * error node path aa/*.
   * 
   * Fixed!
   * 
   * @param rootNodesSet
   * @param nodePath
   * @return
   */
  public abstract DDFNode findDDFNode(Set<DDFNode> rootNodesSet, String nodePath);

  /**
   * <pre>
   * Find DDF Node by relative path.
   * 
   * For exmaple:
   *  base node is: ./a/b/c
   *  relative path: d/e/f
   *  will return ddf node: ./a/b/c/d/e/f
   *  
   * </pre>
   * @param baseNode
   * @param nodePath
   * @return
   */
  public abstract DDFNode findDDFNodeByRelativePath(Model model, DDFNode baseNode, String nodeRelativePath) throws DMException;
}