/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/DeviceRegistry.java,v 1.1 2007/01/11 05:20:25 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/01/11 05:20:25 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.processor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/01/11 05:20:25 $
 */
public class DeviceRegistry implements Serializable {

  private Set<String> registry = new TreeSet<String>();
  
  /**
   * Default constructor.
   */
  public DeviceRegistry() {
    super();
  }
  
  /**
   * Detect path.
   * @param path
   * @return
   */
  public boolean exists(String path) {
    return registry.contains(path);
  }
  
  /**
   * Save node into registry.
   * @param path
   */
  public void setNode(String path) {
    this.registry.add(path);
  }

  /**
   * Save nodes into registry.
   * @param paths
   */
  public void setNodes(Set<String> paths) {
    this.registry.addAll(paths);
  }

  /**
   * Save nodes into registry.
   * @param paths
   */
  public void setNodes(List<String> paths) {
    this.registry.addAll(paths);
  }

}
