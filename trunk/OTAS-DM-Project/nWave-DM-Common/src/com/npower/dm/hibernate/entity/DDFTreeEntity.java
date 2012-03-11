/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/DDFTreeEntity.java,v 1.4 2006/06/04 14:31:57 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2006/06/04 14:31:57 $
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

import java.util.Set;
import java.util.TreeSet;

import com.npower.dm.core.DDFNode;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2006/06/04 14:31:57 $
 */
public class DDFTreeEntity extends AbstractDDFTree implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public DDFTreeEntity() {
  }

  /** full constructor */
  public DDFTreeEntity(String specVersion, String manufacturer, String model, Set nwDmModelDdfTrees, Set nwDmDdfNodes) {
    super(specVersion, manufacturer, model, nwDmModelDdfTrees, nwDmDdfNodes);
  }

  /**
   * Add a root node into the DDFTreeEntity
   * 
   * @param node
   */
  public void add(DDFNode node) {
    this.getDdfNodes().add(node);
    // Link the node to the tree
    node.setDdfTree(this);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.DDFTree#getRootDDFNodes()
   */
  public Set getRootDDFNodes() {
    Set<DDFNode> nodes = this.getDdfNodes();
    Set result = new TreeSet();
    for (DDFNode node: nodes) {
        if (node.getParentDDFNode() == null) {
           result.add(node);
        }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(T)
   */
  public int compareTo(Object o) {
    if (o != null && o instanceof DDFTreeEntity) {
       return (int)(this.getID() - ((DDFTreeEntity)o).getID());
    } else {
      return -1;
    }
  }

}
