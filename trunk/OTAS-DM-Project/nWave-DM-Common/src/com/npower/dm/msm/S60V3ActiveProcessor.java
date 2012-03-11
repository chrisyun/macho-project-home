/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/S60V3ActiveProcessor.java,v 1.1 2008/04/08 11:32:15 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/04/08 11:32:15 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.msm;

import sync4j.framework.engine.dm.TreeNode;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.Software;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/04/08 11:32:15 $
 */
public class S60V3ActiveProcessor extends S60V3OperationProcessor {

  /**
   * 
   */
  public S60V3ActiveProcessor() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.msm.S60V3OperationProcessor#getExecuteTagetNode(com.npower.dm.core.Software)
   */
  public TreeNode getExecuteTagetNode(Software software) {
    String baseNodePath = this.getSoftwareDeployedBaseNodePath(software);
    TreeNode treeNode = new TreeNode(baseNodePath  + "/Operations/Activate");
    treeNode.setFormat(DDFNode.DDF_FORMAT_NODE);
    return treeNode;
  }

}
