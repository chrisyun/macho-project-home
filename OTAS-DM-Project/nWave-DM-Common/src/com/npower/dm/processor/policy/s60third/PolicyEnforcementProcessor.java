/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/policy/s60third/PolicyEnforcementProcessor.java,v 1.1 2008/12/03 10:26:34 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/12/03 10:26:34 $
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
package com.npower.dm.processor.policy.s60third;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.engine.dm.ExecManagementOperation;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.engine.dm.TreeNode;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/12/03 10:26:34 $
 */
public abstract class PolicyEnforcementProcessor implements Serializable {

  private static transient Log log = LogFactory.getLog(PolicyEnforcementProcessor.class);

  private String contentOfPolicy = null;
  
  /**
   * 
   */
  public PolicyEnforcementProcessor() {
    super();
  }

  /**
   * @param contentOfPolicy
   *        需要安装到终端的策略信息, 策略信息基于XACML格式.
   */
  public PolicyEnforcementProcessor(String contentOfPolicy) {
    super();
    this.contentOfPolicy = contentOfPolicy;
  }

  /**
   * @return the contentOfPolicy
   */
  public String getContentOfPolicy() {
    return contentOfPolicy;
  }

  /**
   * @param contentOfPolicy the contentOfPolicy to set
   */
  public void setContentOfPolicy(String contentOfPolicy) {
    this.contentOfPolicy = contentOfPolicy;
  }

  // Private methods ****************************************************************************************

  // Protected methods ****************************************************************************************
  /**
   * The readObject method is responsible for reading from the stream and restoring the classes fields,
   * and restore the transient fields.
   */
  protected void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    log = LogFactory.getLog(PolicyEnforcementProcessor.class);
  }  


  // Public methods ****************************************************************************************
  /**
   * Return Operation Commands.
   * 
   * @throws DMException
   * @throws ManagementException 
   */
  public List<ManagementOperation> getNextOperations(ManagementBeanFactory factory, SessionContext context) throws DMException, ManagementException {
    if (log.isDebugEnabled()) {
      log.debug("Generate operation commands job, id: " + context.getDmstate().mssid);
    }
    List<ManagementOperation> opers = null;
    TreeNode treeNode = new TreeNode("./PolicyMgmt/Delivery/Sink");
    treeNode.setFormat(DDFNode.DDF_FORMAT_XML);
    treeNode.setValue(this.getContentOfPolicy());

    ExecManagementOperation oper1 = new ExecManagementOperation();
    oper1.addTreeNode(treeNode);
    opers.add(oper1);

    return opers;
  }

  /**
   * Record responses from terminal for last commands.
   * @param results
   * @throws ManagementException
   */
  public void setOperationResults(ManagementOperationResult[] results) throws ManagementException {
    
  }
  
  /**
   * 指示当前任务是否正确运行.
   * @return
   * @throws ManagementException
   */
  public boolean isSuccess() throws ManagementException {
    return true;
  }
}
