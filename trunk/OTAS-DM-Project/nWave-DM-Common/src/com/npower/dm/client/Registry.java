/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/client/Registry.java,v 1.2 2006/12/26 11:31:22 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2006/12/26 11:31:22 $
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

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/12/26 11:31:22 $
 */
public interface Registry {

  /**
   * @return the root
   */
  public abstract RegistryItem getRoot();

  /**
   * Find registry item by its path.
   * @param nodePath
   * @return
   */
  public abstract RegistryItem findItem(String nodePath);

  /**
   * add the node path
   * 
   * @param nodePath
   * @param value
   */
  public abstract void addItem(String nodePath, Object value);

  /**
   * delete the nodepath
   * 
   * @param nodePath
   */
  public abstract void deleteItem(String nodePath);

  /**
   * get the value of the nodepath
   * 
   * @param nodePath
   * @return
   * @throws DMException
   */
  public abstract Object getValue(String nodePath) throws DMException;

  /**
   * set the value of nodepath
   * 
   * @param nodePath
   * @param value
   */
  public abstract void setValue(String nodePath, Object value) throws DMException;

  /**
   * judgment existent of nodepath
   * 
   * @param nodePath
   * @return
   * @throws DMException
   */
  public abstract boolean exists(String nodePath) throws DMException;

}