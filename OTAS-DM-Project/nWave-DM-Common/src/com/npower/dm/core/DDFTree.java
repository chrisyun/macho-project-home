/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/DDFTree.java,v 1.5 2007/01/17 04:31:00 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2007/01/17 04:31:00 $
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

import java.util.Set;

/**
 * <p>Represent a DDFTree.</p>
 * @author Zhao DongLu
 * @version $Revision: 1.5 $ $Date: 2007/01/17 04:31:00 $
 * 
 */
public interface DDFTree {

  /**
   * Return the ID
   * @return
   */
  public abstract long getID();

  /**
   * Return the specification version of this DDF Tree.
   * @return
   */
  public abstract String getSpecVersion();

  /**
   * Set a specification version for this DDFTree.
   * @param specVersion
   */
  public abstract void setSpecVersion(String specVersion);

  /**
   * Return the name of manufacturer of this Tree.
   * @return
   */
  public abstract String getManufacturer();

  /**
   * Set a manufacturer's name for this tree.
   * @param manufacturer
   */
  public abstract void setManufacturer(String manufacturer);

  /**
   * Return the model's name of this tree.
   * @return
   */
  public abstract String getModel();

  /**
   * Set a model for this tree.
   * @param model
   */
  public abstract void setModel(String model);

  /**
   * Return a set of root nodes.
   * @return Return a <code>Set</code> of {@link com.npower.dm.core.DDFNode} objects.
   */
  public abstract Set<DDFNode> getRootDDFNodes();

  //public abstract Set getModelDDFTrees();

}