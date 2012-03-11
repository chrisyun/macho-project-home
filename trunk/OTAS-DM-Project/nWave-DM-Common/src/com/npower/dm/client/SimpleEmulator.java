/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/client/SimpleEmulator.java,v 1.6 2007/08/29 06:21:00 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2007/08/29 06:21:00 $
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

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.TreeManagementOperation;
import sync4j.framework.engine.dm.TreeNode;

import com.npower.dm.core.DMException;

/**
 * Emulator a OMA Client. Return the results based on the following policies: 1.
 * If resultList is not empty, fetch the result from the resultList. 2.
 * Otherwise, for each operations always return status code: 200
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2007/08/29 06:21:00 $
 */
public class SimpleEmulator implements DMClientEmulator {

  private List<List<ManagementOperationResult>> resultsList = null;

  private int step = 0;

  /**
   * 
   */
  public SimpleEmulator() {
    super();
  }

  /**
   * Set the result for every steps. For example:
   * 
   * List resultsList = new ArrayList();
   * 
   * List step1 = new ArrayList(); step1.add(new ManagementResult(....));
   * step1.add(new ManagementResult(....)); step1.add(new
   * ManagementResult(....)); List step2 = new ArrayList(); step2.add(new
   * ManagementResult(....)); step2.add(new ManagementResult(....));
   * 
   * resultsList.add(step1); resultsList.add(step2);
   * 
   * OMAClientEmulator emulator = new SimpleEmulator(resultsList);
   * 
   * @param resultsList
   *          Array of List, record the results for every steps.
   */
  public SimpleEmulator(List<List<ManagementOperationResult>> resultsList) {
    this();
    this.resultsList = resultsList;
  }

  /**
   * Read and decode resultList from the file.
   * 
   * @param resultsList
   */
  public SimpleEmulator(File file) throws IOException {
    this();

    XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));
    ArrayList<List<ManagementOperationResult>> operationsList = (ArrayList<List<ManagementOperationResult>>) decoder.readObject();
    this.resultsList = operationsList;

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.client.OMAClientEmulator#process(sync4j.framework.engine.dm.ManagementOperation[])
   */
  public ManagementOperationResult[] process(ManagementOperation[] operations) throws DMException {
    if (this.resultsList == null) {
      return this.reflection(operations, 200);
    }
    if (step < this.resultsList.size()) {
      List<ManagementOperationResult> list = this.resultsList.get(step);
      step++;
      return (ManagementOperationResult[]) list.toArray(new ManagementOperationResult[0]);
    }
    return new ManagementOperationResult[0];
  }

  /**
   * 自动响应操作结果, 所有操作自动响应为成功
   * 
   * @param statusCode
   * @return
   */
  private ManagementOperationResult[] reflection(ManagementOperation[] operations, int statusCode) {
    ManagementOperationResult[] result = new ManagementOperationResult[operations.length];
    for (int j = 0; j < operations.length; j++) {
      result[j] = new ManagementOperationResult();
      result[j].setCommand(operations[j].getDescription());
      result[j].setStatusCode(statusCode);
      if (operations[j] instanceof TreeManagementOperation) {
        Map<String, TreeNode> nodes = ((TreeManagementOperation) operations[j]).getNodes();
        Map<String, String> resultNodes = new TreeMap<String, String>();
        for (String nodePath : nodes.keySet()) {
          TreeNode treeNode = nodes.get(nodePath);
          String value = null;
          if (treeNode != null && treeNode.getValue() != null) {
            value = treeNode.getValue().toString();
          }
          resultNodes.put(nodePath, value);
        }
        result[j].setNodes(resultNodes);
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.client.OMAClientEmulator#reset()
   */
  public void reset() {
    this.step = 0;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.client.OMAClientEmulator#initialize()
   */
  public void initialize() {
  }

}
